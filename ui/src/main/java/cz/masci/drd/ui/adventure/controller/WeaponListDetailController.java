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
import cz.masci.drd.ui.adventure.model.WeaponListModel;
import cz.masci.drd.ui.common.controller.StatusBarController;
import cz.masci.drd.ui.common.model.StatusBarViewModel;
import cz.masci.drd.ui.util.ConcurrentUtils;
import cz.masci.springfx.mvci.controller.ViewProvider;
import cz.masci.springfx.mvci.view.builder.ListDetailViewBuilder;
import javafx.scene.layout.Region;
import org.springframework.stereotype.Component;

@Component
public class WeaponListDetailController implements ViewProvider<Region> {

  private final ListDetailViewBuilder viewBuilder;
  private final WeaponListModel viewModel;
  private final WeaponInteractor interactor;

  public WeaponListDetailController(WeaponInteractor interactor) {
    this.interactor = interactor;
    viewModel = new WeaponListModel();
    var statusBarViewModel = new StatusBarViewModel();

    var listController = new WeaponListController(viewModel);
    var detailController = new WeaponDetailWithCommandController(viewModel, statusBarViewModel, interactor);
    var managerController = new WeaponManagerController(viewModel, statusBarViewModel, interactor);
    var statusBarController = new StatusBarController(statusBarViewModel);

    viewBuilder = ListDetailViewBuilder.builder()
        .withLeft(listController.getView())
        .withCenter(detailController.getView())
        .withTop(managerController.getView())
        .withBottom(statusBarController.getView());
  }

  @Override
  public Region getView() {
    load();
    return viewBuilder.build();
  }

  private void load() {
    viewModel.getItems().clear();
    ConcurrentUtils.startBackgroundTask(interactor::list, items -> viewModel.getItems().setAll(items));
  }

}
