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

package cz.masci.drd.ui.app.battle.wizard.controller.duelliststep;

import static cz.masci.springfx.mvci.util.BuilderUtils.createDetailWithCommandViewBuilder;

import cz.masci.drd.ui.app.battle.wizard.model.BattleDuellistListModel;
import cz.masci.drd.ui.app.battle.wizard.view.BattleDuellistDetailViewBuilder;
import cz.masci.springfx.mvci.controller.ViewProvider;
import cz.masci.springfx.mvci.controller.impl.SimpleController;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class BattleDuellistDetailController implements ViewProvider<Region> {

  private final Builder<Region> builder;

  public BattleDuellistDetailController(BattleDuellistListModel viewModel) {
    var detailViewBuilder = new BattleDuellistDetailViewBuilder(viewModel);
    var detailController = new SimpleController<>(detailViewBuilder);
    var detailCommandController = new BattleDuellistDetailCommandController(viewModel);

    builder = createDetailWithCommandViewBuilder(detailController.getView(), detailCommandController.getView());
  }

  @Override
  public Region getView() {
    return builder.build();
  }
}
