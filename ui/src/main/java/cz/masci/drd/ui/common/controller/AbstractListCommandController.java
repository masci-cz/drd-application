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
import cz.masci.springfx.mvci.controller.ViewProvider;
import cz.masci.springfx.mvci.controller.impl.OperableManagerController;
import cz.masci.springfx.mvci.model.detail.DetailModel;
import cz.masci.springfx.mvci.model.list.impl.BaseListModel;
import cz.masci.springfx.mvci.util.builder.BackgroundTaskBuilder;
import cz.masci.springfx.mvci.view.builder.ButtonBuilder;
import cz.masci.springfx.mvci.view.builder.CommandsViewBuilder;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.scene.layout.Region;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractListCommandController<I, E extends DetailModel<I>> implements ViewProvider<Region> {
  private final CRUDInteractor<E> interactor;
  private final StatusBarViewModel statusBarViewModel;
  private final OperableManagerController<I, E> operableManagerController;
  private final CommandsViewBuilder viewBuilder;

  public AbstractListCommandController(BaseListModel<I, E> viewModel, StatusBarViewModel statusBarViewModel, CRUDInteractor<E> interactor) {
    this.operableManagerController = new OperableManagerController<>(viewModel, viewModel.getElements());
    this.statusBarViewModel = statusBarViewModel;
    this.interactor = interactor;
    viewBuilder = new CommandsViewBuilder(
        List.of(
            ButtonBuilder.builder().text("Obnovit").command(this::load).styleClass("outlined").build(MFXButton::new),
            ButtonBuilder.builder().text("Uložit Vše").command(this::save).styleClass("filledTonal").build(MFXButton::new),
            ButtonBuilder.builder().text("Zrušit Vše").command(this::discard).styleClass("outlined").build(MFXButton::new)
        )
    );
  }

  @Override
  public Region getView() {
    return viewBuilder.build();
  }

  protected abstract String getItemDisplayInfo(E item);

  private void load(Runnable postGuiStuff) {
    BackgroundTaskBuilder
        .task(interactor::list)
        .postGuiCall(postGuiStuff)
        .onSucceeded(operableManagerController::addAll)
        .start();
  }

  private void save(Runnable postGuiStuff) {
    statusBarViewModel.clearMessage();
    AtomicInteger savedCount = new AtomicInteger(0);
    BackgroundTaskBuilder
        .task(() -> {
          operableManagerController.update((item, afterSave) -> {
            try {
              var savedItem = interactor.save(item);
              runInFXThread(() -> afterSave.accept(savedItem));
              savedCount.incrementAndGet();
            } catch (Exception e) {
              statusBarViewModel.setErrorMessage(String.format("Něco se pokazilo při ukládání %s : %s", getItemDisplayInfo(item), e.getLocalizedMessage()));
            }
          });
          return null;
        })
        .onSucceeded(unused -> statusBarViewModel.setInfoMessage(String.format("Bylo uloženo %d záznamů", savedCount.get())))
        .postGuiCall(postGuiStuff)
        .start();
  }

  private void discard() {
    statusBarViewModel.clearMessage();
    operableManagerController.discard();
    statusBarViewModel.setInfoMessage("Byly zrušeny provedené změny");
  }

}
