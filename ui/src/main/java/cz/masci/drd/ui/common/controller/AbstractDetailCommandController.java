/*
 * Copyright (c) 2024
 *
 * This file is part of DrD.
 *
 * DrD is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free
 *  Software Foundation, either version 3 of the License, or (at your option)
 *   any later version.
 *
 * DrD is distributed in the hope that it will be useful, but WITHOUT ANY
 *  WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *   FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public
 *    License for more details.
 *
 * You should have received a copy of the GNU General Public License
 *  along with Foobar. If not, see <https://www.gnu.org/licenses/>.
 */

package cz.masci.drd.ui.common.controller;

import static cz.masci.springfx.mvci.util.ConcurrentUtils.runInFXThread;

import cz.masci.drd.ui.common.interactor.CRUDInteractor;
import cz.masci.drd.ui.common.model.StatusBarViewModel;
import cz.masci.drd.ui.common.view.DeleteConfirmDialog;
import cz.masci.springfx.mvci.controller.ViewProvider;
import cz.masci.springfx.mvci.controller.impl.OperableDetailController;
import cz.masci.springfx.mvci.model.detail.DetailModel;
import cz.masci.springfx.mvci.model.list.ListModel;
import cz.masci.springfx.mvci.util.builder.BackgroundTaskBuilder;
import cz.masci.springfx.mvci.view.builder.ButtonBuilder;
import cz.masci.springfx.mvci.view.builder.CommandsViewBuilder;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractDetailCommandController<I, E extends DetailModel<I>> implements ViewProvider<Region> {
  // external properties
  private final CRUDInteractor<E> interactor;
  private final StatusBarViewModel statusBarViewModel;
  // internal properties
  private final OperableDetailController<I, E> operableDetailController;
  private final CommandsViewBuilder viewBuilder;

  public AbstractDetailCommandController(ListModel<E> viewModel, StatusBarViewModel statusBarViewModel, CRUDInteractor<E> interactor) {
    operableDetailController = new OperableDetailController<>(viewModel.selectedElementProperty(), viewModel);
    this.statusBarViewModel = statusBarViewModel;
    this.interactor = interactor;
    this.viewBuilder =
        new CommandsViewBuilder(
            List.of(
                ButtonBuilder.builder().text("Uložit").command(this::saveItem).styleClass("filledTonal").disableExpression(operableDetailController.saveDisabledProperty()).build(MFXButton::new),
                ButtonBuilder.builder().text("Zrušit").command(this::discardDirtyItem).styleClass("outlined").disableExpression(operableDetailController.discardDisabledProperty()).build(MFXButton::new),
                ButtonBuilder.builder().text("Smazat").command(this::showDeleteItemConfirmDialog).disableExpression(operableDetailController.deleteDisabledProperty()).styleClass("outlined").build(MFXButton::new)
            ),
            Pos.CENTER_RIGHT
        );
  }

  @Override
  public Region getView() {
    return viewBuilder.build();
  }

  protected abstract String getDeleteConfirmDialogTitle();

  protected abstract String getDeleteConfirmDialogContent();

  protected abstract String getItemDisplayInfo(E item);

  private void discardDirtyItem() {
    operableDetailController.discard();
  }

  private void saveItem(Runnable postGuiStuff) {
      operableDetailController.update((item, afterSave) ->
        BackgroundTaskBuilder
            .task(() -> {
              log.debug("Saving item {}", item);
              var savedItem = interactor.save(item);
              runInFXThread(() -> afterSave.accept(savedItem));
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

  private void showDeleteItemConfirmDialog(Runnable postGuiStuff) {
    DeleteConfirmDialog.show(getDeleteConfirmDialogTitle(), getDeleteConfirmDialogContent(), this::deleteItem);
    postGuiStuff.run();
  }

  private void deleteItem(Runnable postGuiStuff) {
      operableDetailController.remove((item, afterDelete) ->
          BackgroundTaskBuilder
              .task(() -> {
                log.debug("Deleting item {}", getItemDisplayInfo(item));
                interactor.delete(item);
                runInFXThread(afterDelete);
                return item;
              })
              .onFailed(task -> statusBarViewModel.setErrorMessage(String.format("Něco se pokazilo při mazání %s : %s", getItemDisplayInfo(item), task.getException().getLocalizedMessage())))
              .onSucceeded(deletedItem -> statusBarViewModel.setInfoMessage(String.format("Záznam byl smazán %s", getItemDisplayInfo(deletedItem))))
              .postGuiCall(postGuiStuff)
              .start());
  }

}
