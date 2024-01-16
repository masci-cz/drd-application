package cz.masci.drd.ui.adventure.controller;

import cz.masci.drd.ui.adventure.interactor.WeaponInteractor;
import cz.masci.drd.ui.adventure.model.WeaponDetailModel;
import cz.masci.drd.ui.adventure.model.WeaponListModel;
import cz.masci.drd.ui.common.model.StatusBarViewModel;
import cz.masci.drd.ui.util.ConcurrentUtils;
import cz.masci.springfx.mvci.controller.ViewProvider;
import cz.masci.springfx.mvci.view.builder.ButtonBuilder;
import cz.masci.springfx.mvci.view.builder.CommandsViewBuilder;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeaponDetailCommandController implements ViewProvider<Region> {

  private final WeaponListModel viewModel;
  private final ObjectProperty<WeaponDetailModel> viewModelProperty;
  private final StatusBarViewModel statusBarViewModel;
  private final WeaponInteractor interactor;
  private final CommandsViewBuilder viewBuilder;
  private final BooleanProperty saveDisableProperty = new SimpleBooleanProperty(true);
  private final BooleanProperty discardDisableProperty = new SimpleBooleanProperty(true);

  public WeaponDetailCommandController(WeaponListModel viewModel, StatusBarViewModel statusBarViewModel, WeaponInteractor interactor) {
    this.viewModel = viewModel;
    this.viewModelProperty = viewModel.selectedItemProperty();
    this.statusBarViewModel = statusBarViewModel;
    this.interactor = interactor;
    this.viewBuilder =
        new CommandsViewBuilder(
            List.of(
                ButtonBuilder.builder().text("Uložit").command(this::saveCharacter).styleClass("filledTonal").disableExpression(saveDisableProperty).build(MFXButton::new),
                ButtonBuilder.builder().text("Zrušit").command(this::discardDirtyItem).styleClass("outlined").disableExpression(discardDisableProperty).build(MFXButton::new)
            ),
            Pos.CENTER_RIGHT
        );
    initDisableProperties();
  }

  @Override
  public Region getView() {
    return viewBuilder.build();
  }

  private void initDisableProperties() {
    viewModelProperty.addListener((observable, oldValue, newValue) -> {
      saveDisableProperty.unbind();
      discardDisableProperty.unbind();

      if (newValue == null) {
        saveDisableProperty.set(true);
        discardDisableProperty.set(true);
      } else {
        saveDisableProperty.bind(Bindings.createBooleanBinding(() -> !newValue.isDirty() || !newValue.isValid(), newValue.isDirtyProperty(), newValue.getValidator().validProperty()));
        discardDisableProperty.bind(Bindings.createBooleanBinding(() -> !newValue.isDirty(), newValue.isDirtyProperty()));
      }
    });
  }

  private void discardDirtyItem() {
    if (!discardDisableProperty.get()) {
      var property = viewModelProperty.get();
      if (property.getId() <= 0) {
        viewModel.removeItem(property);
      } else {
        property.reset();
      }
    }
  }

  private void saveCharacter(Runnable postFetchGuiStuff) {
    if (!saveDisableProperty.get()) {
      log.debug("Clicked save weapon");
      var item = viewModelProperty.get();
      ConcurrentUtils.startBackgroundTask(() -> {
          try {
            log.debug("Saving item {}", item);
            var savedItem = interactor.save(item);
            ConcurrentUtils.runInFXThread(() -> {
              if (item.getId() <= 0) {
                item.setId(savedItem.getId());
              }
              item.rebaseline();
            });
          } catch (Exception e) {
            statusBarViewModel.setErrorMessage(String.format("Něco se pokazilo při ukládání %s : %s", item.getName(), e.getLocalizedMessage()));
          }
        return null;
      }, () -> {
        statusBarViewModel.setInfoMessage("Záznam byl uložen");
        postFetchGuiStuff.run();
      });
    }
  }
}
