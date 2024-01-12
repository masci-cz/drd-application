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
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import javafx.scene.layout.Region;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeaponManagerController implements ViewProvider<Region> {
  private final WeaponListModel viewModel;
  private final StatusBarViewModel statusBarViewModel;
  private final WeaponInteractor interactor;
  private final CommandsViewBuilder viewBuilder;

  public WeaponManagerController(WeaponListModel viewModel, StatusBarViewModel statusBarViewModel, WeaponInteractor interactor) {
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

  private void load(Runnable postGuiStuff) {
    viewModel.getItems().clear();
    ConcurrentUtils.startBackgroundTask(interactor::list, postGuiStuff, viewModel.getItems()::setAll);
  }

  private void save(Runnable postGuiStuff) {
    log.debug("Clicked save all weapons");
    statusBarViewModel.clearMessage();
    AtomicInteger savedCount = new AtomicInteger(0);
    ConcurrentUtils.startBackgroundTask(() -> {
      getDirtyItems().forEach(item -> {
        try {
          log.debug("Saving item {}", item);
          var savedItem = interactor.save(item);
          ConcurrentUtils.runInFXThread(() -> {
            if (item.getId() <= 0) {
              item.setId(savedItem.getId());
            }
            item.rebaseline();
          });
          savedCount.incrementAndGet();
        } catch (Exception e) {
          statusBarViewModel.setErrorMessage(String.format("Něco se pokazilo při ukládání %s : %s", item.getName(), e.getLocalizedMessage()));
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
    getDirtyItems().forEach(DirtyModel::reset);
    statusBarViewModel.setInfoMessage("Byly zrušeny provedené změny");
  }

  private Stream<WeaponDetailModel> getDirtyItems() {
    return viewModel.getItems().stream().filter(DirtyModel::isDirty);
  }
}
