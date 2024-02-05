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

package cz.masci.drd.ui.util.controller;

import cz.masci.drd.ui.common.model.StatusBarViewModel;
import cz.masci.drd.ui.util.ConcurrentUtils;
import cz.masci.drd.ui.util.interactor.CRUDInteractor;
import cz.masci.drd.ui.util.model.DetailModel;
import cz.masci.drd.ui.util.model.DirtyModel;
import cz.masci.drd.ui.util.model.ListModel;
import cz.masci.springfx.mvci.controller.ViewProvider;
import cz.masci.springfx.mvci.view.builder.ButtonBuilder;
import cz.masci.springfx.mvci.view.builder.CommandsViewBuilder;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import javafx.scene.layout.Region;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractListCommandController<E, T extends DetailModel<E>> implements ViewProvider<Region> {
  private final ListModel<T> viewModel;
  private final CRUDInteractor<T> interactor;
  private final StatusBarViewModel statusBarViewModel;
  private final CommandsViewBuilder viewBuilder;

  public AbstractListCommandController(ListModel<T> viewModel, StatusBarViewModel statusBarViewModel, CRUDInteractor<T> interactor) {
    this.viewModel = viewModel;
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

  protected abstract String getItemDisplayInfo(T item);

  private void load(Runnable postGuiStuff) {
    viewModel.getItems().clear();
    ConcurrentUtils.startBackgroundTask(interactor::list, postGuiStuff, viewModel.getItems()::setAll);
  }

  private void save(Runnable postGuiStuff) {
    statusBarViewModel.clearMessage();
    AtomicInteger savedCount = new AtomicInteger(0);
    ConcurrentUtils.startBackgroundTask(() -> {
      getDirtyItems().forEach(item -> {
        try {
          var savedItem = interactor.save(item);
          ConcurrentUtils.runInFXThread(() -> {
            if (item.isTransient()) {
              item.setId(savedItem.getId());
            }
            item.rebaseline();
          });
          savedCount.incrementAndGet();
        } catch (Exception e) {
          statusBarViewModel.setErrorMessage(String.format("Něco se pokazilo při ukládání %s : %s", getItemDisplayInfo(item), e.getLocalizedMessage()));
        }
      });
      return null;
    }, () -> {
      statusBarViewModel.setInfoMessage(String.format("Bylo uloženo %d záznamů", savedCount.get()));
      postGuiStuff.run();
    });
  }

  private void discard() {
    statusBarViewModel.clearMessage();
    getDirtyItems().forEach(DirtyModel::reset);  // TODO remove transient records
    statusBarViewModel.setInfoMessage("Byly zrušeny provedené změny");
  }

  private Stream<T> getDirtyItems() {
    return viewModel.getItems().stream().filter(DirtyModel::isDirty);
  }
}
