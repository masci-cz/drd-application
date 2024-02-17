package cz.masci.drd.ui.util.controller;

import cz.masci.drd.ui.common.model.StatusBarViewModel;
import cz.masci.drd.ui.common.view.DeleteConfirmDialog;
import cz.masci.drd.ui.util.BackgroundTaskBuilder;
import cz.masci.drd.ui.util.ConcurrentUtils;
import cz.masci.drd.ui.util.interactor.CRUDInteractor;
import cz.masci.springfx.mvci.controller.ViewProvider;
import cz.masci.springfx.mvci.model.detail.DetailModel;
import cz.masci.springfx.mvci.model.detail.DirtyModel;
import cz.masci.springfx.mvci.model.detail.ValidModel;
import cz.masci.springfx.mvci.model.list.ListModel;
import cz.masci.springfx.mvci.view.builder.ButtonBuilder;
import cz.masci.springfx.mvci.view.builder.CommandsViewBuilder;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import lombok.extern.slf4j.Slf4j;
import org.reactfx.value.Val;

@Slf4j
public abstract class AbstractDetailCommandController<E, T extends DetailModel<E>> implements ViewProvider<Region> {
  // external properties
  private final ListModel<T> viewModel;
  private final Val<T> selectedItemProperty;
  private final CRUDInteractor<T> interactor;
  private final StatusBarViewModel statusBarViewModel;
  // internal properties
  private final CommandsViewBuilder viewBuilder;
  private final BooleanProperty saveDisableProperty = new SimpleBooleanProperty(true);
  private final BooleanProperty discardDisableProperty = new SimpleBooleanProperty(true);
  private final BooleanProperty deleteDisableProperty = new SimpleBooleanProperty(true);

  public AbstractDetailCommandController(ListModel<T> viewModel, StatusBarViewModel statusBarViewModel, CRUDInteractor<T> interactor) {
    this.viewModel = viewModel;
    this.selectedItemProperty = Val.wrap(viewModel.selectedElementProperty());
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

  protected abstract String getDeleteConfirmDialogTitle();

  protected abstract String getDeleteConfirmDialogContent();

  protected abstract String getItemDisplayInfo(T item);

  private void initDisableProperties() {
    // delete disabled => not selected
    Val<Boolean> dirtyProperty = selectedItemProperty.flatMap(DirtyModel::isDirtyProperty);
    Val<Boolean> validProperty = selectedItemProperty.flatMap(ValidModel::validProperty);
    Val<Boolean> saveDisable = Val.combine(dirtyProperty, validProperty, (dirty, valid) -> !dirty || !valid);
    deleteDisableProperty.bind(Bindings.createBooleanBinding(selectedItemProperty::isEmpty, selectedItemProperty));
    saveDisableProperty.bind(Bindings.createBooleanBinding(() -> selectedItemProperty.isEmpty() || saveDisable.getOrElse(true), selectedItemProperty, saveDisable));
    discardDisableProperty.bind(Bindings.createBooleanBinding(() -> selectedItemProperty.isEmpty() || !dirtyProperty.getOrElse(true), selectedItemProperty, dirtyProperty));
  }

  private void discardDirtyItem() {
    if (!discardDisableProperty.get()) {
      selectedItemProperty.ifPresent(item -> {
        if (item.isTransient()) {
          viewModel.removeElement(item);
        } else {
          item.reset();
        }
      });
    }
  }

  private void saveItem(Runnable postGuiStuff) {
    if (!saveDisableProperty.get()) {
      log.debug("Clicked save item");
      selectedItemProperty.ifPresent(item ->
        BackgroundTaskBuilder
            .task(() -> {
              log.debug("Saving item {}", item);
              var savedItem = interactor.save(item);
              ConcurrentUtils.runInFXThread(() -> {
                if (item.isTransient()) {
                  item.setId(savedItem.getId());
                }
                item.rebaseline();
              });
              return savedItem;
            })
            .onFailed(task -> {
              var e = task.getException();
              statusBarViewModel.setErrorMessage(String.format("Něco se pokazilo při ukládání %s : %s", getItemDisplayInfo(item), e.getLocalizedMessage()));
              log.error("Error when saving item", e);
            })
            .onSucceeded(savedItem -> statusBarViewModel.setInfoMessage(String.format("Záznam byl uložen %s", getItemDisplayInfo(savedItem))))
            .postGuiCall(postGuiStuff)
            .start()
      );
    }
  }

  private void showDeleteItemConfirmDialog(Runnable postGuiStuff) {
    DeleteConfirmDialog.show(getDeleteConfirmDialogTitle(), getDeleteConfirmDialogContent(), this::deleteItem);
    postGuiStuff.run();
  }

  private void deleteItem(Runnable postGuiStuff) {
    if (!deleteDisableProperty.get()) {
      selectedItemProperty.ifPresent(item -> BackgroundTaskBuilder
          .task(() -> {
            log.debug("Deleting item {}", getItemDisplayInfo(item));
            interactor.delete(item);
            ConcurrentUtils.runInFXThread(() -> viewModel.removeElement(item));
            return item;
          })
          .onFailed(task -> statusBarViewModel.setErrorMessage(String.format("Něco se pokazilo při mazání %s : %s", getItemDisplayInfo(item), task.getException().getLocalizedMessage())))
          .onSucceeded(deletedItem -> statusBarViewModel.setInfoMessage(String.format("Záznam byl smazán %s", getItemDisplayInfo(deletedItem))))
          .postGuiCall(postGuiStuff)
          .start());
    }
  }

}
