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
package cz.masci.drd.app;

import cz.masci.drd.theme.DrDAppTheme;
import cz.masci.drd.ui.common.controller.WizardController;
import io.github.palexdev.materialfx.theming.JavaFXThemes;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author Daniel
 */
public class JavaFxApplication extends Application {

  private ConfigurableApplicationContext applicationContext;

  @Override
  public void init() {
    String[] args = getParameters().getRaw().toArray(new String[0]);

    this.applicationContext = new SpringApplicationBuilder()
            .sources(SpringApplication.class)
            .run(args);

    UserAgentBuilder.builder()
        .themes(JavaFXThemes.MODENA)
        .themes(MaterialFXStylesheets.forAssemble(false))
        .themes(DrDAppTheme.TOKENS)
        .themes(DrDAppTheme.APP)
        .themes(DrDAppTheme.TABLE_VIEW)
        .setDeploy(true)
        .setDebug(true)
        .setResolveAssets(true)
        .build()
        .setGlobal();
  }

  @Override
  public void start(Stage stage) {
//    var homeScreenController = applicationContext.getBean(HomeScreenController.class);
    var homeScreenController = applicationContext.getBean(WizardController.class);
    Scene scene = new Scene(homeScreenController.getView(), 800, 600);
    stage.setTitle("Aplikace Dračí Doupě");
    stage.setScene(scene);
//    stage.setOnCloseRequest(homeScreenController::doOnCloseRequest);
    stage.show();
  }

  @Override
  public void stop() {
    this.applicationContext.close();
    Platform.exit();
  }
}
