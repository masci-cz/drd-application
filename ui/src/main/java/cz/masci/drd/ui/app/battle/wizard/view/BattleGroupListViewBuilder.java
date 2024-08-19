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

package cz.masci.drd.ui.app.battle.wizard.view;

import static cz.masci.drd.ui.util.ViewBuilderUtils.buildAddButton;

import cz.masci.drd.ui.app.battle.wizard.model.BattleGroupDetailModel;
import cz.masci.drd.ui.app.battle.wizard.model.BattleGroupListModel;
import cz.masci.springfx.mvci.util.builder.MFXTableViewBuilder;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.geometry.Insets;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BattleGroupListViewBuilder implements Builder<Region> {

  private final BattleGroupListModel viewModel;

  @Override
  public Region build() {
    return new StackPane(buildTable(), buildAddButton(viewModel, new Insets(30.0)));
  }

  private MFXTableView<BattleGroupDetailModel> buildTable() {
    var table = MFXTableViewBuilder.builder(viewModel)
        .column("Název", BattleGroupDetailModel::getName, Double.MAX_VALUE)
        .build();
    table.setFooterVisible(false);
    return table;
  }
}
