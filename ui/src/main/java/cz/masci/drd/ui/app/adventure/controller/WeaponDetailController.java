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

package cz.masci.drd.ui.app.adventure.controller;

import cz.masci.drd.ui.app.adventure.interactor.WeaponInteractor;
import cz.masci.drd.ui.app.adventure.model.WeaponListModel;
import cz.masci.drd.ui.app.adventure.view.WeaponDetailViewBuilder;
import cz.masci.drd.ui.common.model.StatusBarViewModel;
import cz.masci.drd.ui.util.ViewBuilderUtils;
import cz.masci.springfx.mvci.controller.ViewProvider;
import cz.masci.springfx.mvci.controller.impl.SimpleController;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class WeaponDetailController implements ViewProvider<Region> {

  private final Builder<Region> builder;

  public WeaponDetailController(WeaponListModel viewModel, StatusBarViewModel statusBarViewModel, WeaponInteractor interactor) {
    var detailViewBuilder = new WeaponDetailViewBuilder(viewModel);
    var detailController = new SimpleController<>(detailViewBuilder);
    var detailCommandController = new WeaponDetailCommandController(viewModel, statusBarViewModel, interactor);

    builder = ViewBuilderUtils.createDetailWithCommandViewBuilder(detailController.getView(), detailCommandController.getView());
  }

  @Override
  public Region getView() {
    return builder.build();
  }
}
