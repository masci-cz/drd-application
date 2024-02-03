package cz.masci.drd.ui.adventure.controller;

import cz.masci.drd.ui.adventure.interactor.WeaponInteractor;
import cz.masci.drd.ui.adventure.model.WeaponDetailModel;
import cz.masci.drd.ui.adventure.model.WeaponListModel;
import cz.masci.drd.ui.common.model.StatusBarViewModel;
import cz.masci.drd.ui.util.ConcurrentUtils;
import cz.masci.drd.ui.util.dirty.DirtyModel;
import cz.masci.springfx.mvci.controller.ViewProvider;
import cz.masci.springfx.mvci.view.builder.ButtonBuilder;
import cz.masci.springfx.mvci.view.builder.CommandsViewBuilder;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import lombok.extern.slf4j.Slf4j;
import org.reactfx.value.Val;

@Slf4j
public class WeaponDetailCommandController implements ViewProvider<Region> {

  private final WeaponListModel viewModel;
  private final Val<WeaponDetailModel> selectedItemProperty;
  private final StatusBarViewModel statusBarViewModel;
  private final WeaponInteractor interactor;
  private final CommandsViewBuilder viewBuilder;
  private final BooleanProperty saveDisableProperty = new SimpleBooleanProperty(true);
  private final BooleanProperty discardDisableProperty = new SimpleBooleanProperty(true);
  private final BooleanProperty deleteDisableProperty = new SimpleBooleanProperty(true);

  public WeaponDetailCommandController(WeaponListModel viewModel, StatusBarViewModel statusBarViewModel, WeaponInteractor interactor) {
    this.viewModel = viewModel;
    this.selectedItemProperty = Val.wrap(viewModel.selectedItemProperty());
    this.statusBarViewModel = statusBarViewModel;
    this.interactor = interactor;
    this.viewBuilder =
        new CommandsViewBuilder(
            List.of(
                ButtonBuilder.builder().text("Uložit").command(this::saveItem).styleClass("filledTonal").disableExpression(saveDisableProperty).build(MFXButton::new),
                ButtonBuilder.builder().text("Zrušit").command(this::discardDirtyItem).styleClass("outlined").disableExpression(discardDisableProperty).build(MFXButton::new),
                ButtonBuilder.builder().text("Smazat").command(this::showDeleteItemConfirmDialog).disableExpression(deleteDisableProperty).styleClass("outlined").build(MFXButton::new)
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
    // delete disabled => not selected
    Val<Boolean> dirtyProperty = selectedItemProperty.flatMap(DirtyModel::isDirtyProperty);
    Val<Boolean> validProperty = selectedItemProperty.flatMap(model -> model.getValidator().validProperty());
    Val<Boolean> saveDisable = Val.combine(dirtyProperty, validProperty, (dirty, valid) -> !dirty || !valid);
    deleteDisableProperty.bind(Bindings.createBooleanBinding(selectedItemProperty::isEmpty, selectedItemProperty));
    saveDisableProperty.bind(Bindings.createBooleanBinding(() -> selectedItemProperty.isEmpty() || saveDisable.getOrElse(true), selectedItemProperty, saveDisable));
    discardDisableProperty.bind(Bindings.createBooleanBinding(() -> selectedItemProperty.isEmpty() || !dirtyProperty.getOrElse(true), selectedItemProperty, dirtyProperty));
  }

  private void discardDirtyItem() {
    if (!discardDisableProperty.get()) {
      selectedItemProperty.ifPresent(item -> {
        if (item.getId() <= 0) {
          viewModel.removeItem(item);
        } else {
          item.reset();
        }
      });
    }
  }

  private void saveItem(Runnable postGuiStuff) {
    if (!saveDisableProperty.get()) {
      log.debug("Clicked save weapon");
      selectedItemProperty.ifPresent(item -> ConcurrentUtils.startBackgroundTask(() -> {
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
        postGuiStuff.run();
      }));
    }
  }

  private void showDeleteItemConfirmDialog(Runnable postGuiStuff) {
    // open dialog ask if item could be deleted
    MFXGenericDialog dialogContent = MFXGenericDialogBuilder.build()
        .setContentText("Opravdu chcete smazat vybranou zbraň?")
        .setShowAlwaysOnTop(false)
        .setShowMinimize(false)
        .get();
    MFXStageDialog dialog = MFXGenericDialogBuilder.build(dialogContent)
        .toStageDialogBuilder()
        .setTitle("Smazat zbraň")
        .setScrimOwner(false)
        .setCenterInOwnerNode(false)
        .initModality(Modality.APPLICATION_MODAL)
        .get();

    dialogContent.addActions(
        (Node) ButtonBuilder.builder().text("Smazat").command(deletePostGuiStuff -> deleteItem(() -> {
          deletePostGuiStuff.run();
          dialog.close();
        })).build(MFXButton::new),
        ButtonBuilder.builder().text("Zrušit").command(dialog::close).build(MFXButton::new)
    );
    dialogContent.setMaxSize(400, 150);

    dialog.showAndWait();

    postGuiStuff.run();
  }

  private void deleteItem(Runnable postGuiStuff) {
    if (!deleteDisableProperty.get()) {
      selectedItemProperty.ifPresent(item -> ConcurrentUtils.startBackgroundTask(() -> {
        try {
          log.debug("Deleting item {}", item);
          interactor.delete(item);
          ConcurrentUtils.runInFXThread(() -> viewModel.getItems().remove(item));
        } catch (Exception e) {
          statusBarViewModel.setErrorMessage(String.format("Něco se pokazilo při mazání %s : %s", item.getName(), e.getLocalizedMessage()));
        }
        return null;
      }, () -> {
        statusBarViewModel.setInfoMessage("Záznam byl smazán");
        postGuiStuff.run();
      }));
    }
  }

}
