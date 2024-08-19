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

package cz.masci.drd.ui.app.monster.controller;

import static cz.masci.springfx.mvci.util.BuilderUtils.createDetailWithCommandViewBuilder;

import cz.masci.drd.ui.app.monster.interactor.MonsterInteractor;
import cz.masci.drd.ui.app.monster.model.MonsterListModel;
import cz.masci.drd.ui.app.monster.view.MonsterDetailViewBuilder;
import cz.masci.drd.ui.common.model.StatusBarViewModel;
import cz.masci.springfx.mvci.controller.ViewProvider;
import cz.masci.springfx.mvci.controller.impl.SimpleController;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class MonsterDetailController implements ViewProvider<Region> {

  private final Builder<Region> builder;

  public MonsterDetailController(MonsterListModel viewModel, StatusBarViewModel statusBarViewModel, MonsterInteractor interactor) {
    var detailViewBuilder = new MonsterDetailViewBuilder(viewModel);
    var detailController = new SimpleController<>(detailViewBuilder);
    var detailCommandController = new MonsterDetailCommandController(viewModel, statusBarViewModel, interactor);

    builder = createDetailWithCommandViewBuilder(detailController.getView(), detailCommandController.getView());
  }

  @Override
  public Region getView() {
    return builder.build();
  }
}
