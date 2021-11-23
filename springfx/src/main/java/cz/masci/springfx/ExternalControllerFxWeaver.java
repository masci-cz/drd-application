/*
 * Copyright (C) 2021 Daniel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cz.masci.springfx;

import cz.masci.springfx.annotation.FxmlController;
import cz.masci.springfx.annotation.FxmlRoot;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.util.BuilderFactory;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxLoadException;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import net.rgielen.fxweaver.core.SimpleFxControllerAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.stereotype.Component;

/**
 * Extending FxWeaver class to be able load fxml files only without controller
 * specification fx:controller. It set FXMLLoader controller before loading fxml
 * file.
 *
 * @author Daniel Masek
 */
@Component
@Slf4j
public class ExternalControllerFxWeaver extends FxWeaver {

  private final Callback<Class<?>, Object> beanFactory;
  private final BuilderFactory builderFactory;

  @Autowired
  public ExternalControllerFxWeaver(ConfigurableApplicationContext context) {
    super(context::getBean, context::close);

    beanFactory = (requiredType) -> context.getBean(requiredType);
    builderFactory = (type) -> {
      if (getFxmlViewMergedAnnotation(type).isPresent()) {
        log.debug("Found FxmlView annotation for type {}", type);
        return () -> this.loadController(type);
      }
      return null;
    };

  }

  /**
   * Load controller instance, potentially weaved with a FXML view declaring the
   * given class as fx:controller.
   * <p>
   * The possible FXML resource is inferred from a {@link FxmlView} annotation
   * at the controller class or the simple classname and package of said class
   * if it was not annotated like this. If the FXML file is resolvable, the
   * defined view within will be loaded by {@link FXMLLoader}. The controller
   * will then be instantiated based on the fx:controller attribute, using the
   * bean factory from {@link #FxWeaver(Callback, Runnable)}. If the bean
   * factory is based on a dependency management framework such as Spring, Guice
   * or CDI, this means that the instance will be fully managed and injected as
   * declared.
   * <p>
   * If the controller class does not come with a resolvable FXML view resource,
   * the controller will be instantiated by the given bean factory directly.
   *
   * @param controllerClass The controller class of which a weaved instance
   * should be provided
   * @param location The location of the FXML view to load as a classloader
   * resource. May be <code>null</code> or not resolvable, in which case the
   * controller will be directly instantiated by the given bean factory.
   * @param resourceBundle The optional {@link ResourceBundle} to use for view
   * creation. May be <code>null</code>
   * @param <V> The view type
   * @param <C> The controller type
   * @return A {@link SimpleFxControllerAndView} container with the managed
   * instance of the requested controller and the corresponding view, if
   * applicable
   * @see #FxWeaver(Callback, Runnable)
   * @see FXMLLoader
   */
  @Override
  protected <C, V extends Node> FxControllerAndView<C, V> load(Class<C> controllerClass, String location, ResourceBundle resourceBundle) {
    return Optional.ofNullable(buildFxmlUrl(controllerClass))
            .map(url -> this.<C, V>loadByView(controllerClass, url, resourceBundle))
            .orElseGet(() -> SimpleFxControllerAndView.ofController(getBean(controllerClass)));
  }

  private <C, V extends Node> FxControllerAndView<C, V> loadByView(Class<C> controllerClass, URL url, ResourceBundle resourceBundle) {
    return loadByViewUsingFxmlLoader(new FXMLLoader(), controllerClass, url, resourceBundle);
  }

  <C, V extends Node> FxControllerAndView<C, V> loadByViewUsingFxmlLoader(FXMLLoader loader, Class<C> controllerClass, URL url, ResourceBundle resourceBundle) {
    log.debug("Loading FXML from {}", url.getFile());
    try ( InputStream fxmlStream = url.openStream()) {
      loader.setLocation(url);
      loader.setControllerFactory(beanFactory);
      loader.setBuilderFactory(builderFactory);
      if (resourceBundle != null) {
        loader.setResources(resourceBundle);
      }

      C controller = null;
      MergedAnnotations controllerAnnotations = MergedAnnotations.from(controllerClass, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY);

      if (controllerAnnotations.isPresent(FxmlController.class)) {
        log.debug("FxmlController found");
        controller = getBean(controllerClass);
        loader.setController(controller);
      }

      if (controllerAnnotations.isPresent(FxmlRoot.class)) {
        log.debug("FxmlRoot found");
        loader.setRoot(controller);
      }

      V view = loader.load(fxmlStream);

      return SimpleFxControllerAndView.of(controller, view);
    } catch (IOException e) {
      throw new FxLoadException("Unable to load FXML file " + url, e);
    }
  }

  /**
   * Build a FXML view location reference for controller classes, based on
   * {@link FxmlView} annotation or simple classname.
   *
   * @param c The class to build a FXML location for. If it does not contain a
   * {@link FxmlView} annotation to specify resource to load, it is assumed that
   * the view resides in the same package, named {c.getSimpleName()}.fxml
   * @return a resource location suitable for loading by
   * {@link Class#getResource(String)}
   */
  @Override
  protected String buildFxmlReference(Class<?> c) {
    return getFxmlViewMergedAnnotation(c)
            .getValue("value", String.class)
            .orElse(c.getSimpleName() + ".fxml");
  }

  /**
   * Check existence of FxmlView annotation on the class. If the annotation is
   * found, builds URL of the file defined in the annotation based on the class.
   *
   * @param c Checking class
   * @return URL of found resource file
   */
  private URL buildFxmlUrl(Class<?> c) {
    log.debug("buildFxmlUrl from {}", c);
    var fxmlViewAnnotation = getFxmlViewMergedAnnotation(c);

    if (fxmlViewAnnotation.isPresent()) {
      var value = fxmlViewAnnotation.getValue("value", String.class).get();
      return ((Class) fxmlViewAnnotation.getSource()).getResource(value);
    }

    return null;
  }

  /**
   * Get the {@link MergedAnnotation<FxmlView>} annotation from the class. If
   * there is no such annotation returns MergedAnnotation.missing()
   *
   * @param c The class to get a {@link MergedAnnotation<FxmlView>} annotation
   * from
   * @return a MergedAnnotation
   */
  private MergedAnnotation<FxmlView> getFxmlViewMergedAnnotation(Class<?> c) {
    MergedAnnotations annotations = MergedAnnotations.from(c, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY);

    return annotations.get(FxmlView.class);
  }
}
