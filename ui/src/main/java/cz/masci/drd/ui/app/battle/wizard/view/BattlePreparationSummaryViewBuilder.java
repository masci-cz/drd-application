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

import cz.masci.drd.ui.app.battle.wizard.model.BattlePreparationSummaryGroupModel;
import cz.masci.springfx.mvci.util.MFXBuilderUtils;
import io.github.palexdev.materialfx.builders.layout.VBoxBuilder;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BattlePreparationSummaryViewBuilder implements Builder<Region> {

  private final ObservableList<BattlePreparationSummaryGroupModel> groups;

  @Override
  public Region build() {
    var groupTable = new MFXTableView<>(groups);
    groupTable.setFooterVisible(false);
    groupTable.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    groupTable.getSelectionModel().setAllowsMultipleSelection(false);
    groupTable.getTableColumns().add(
        MFXBuilderUtils.createTableColumn("Název", BattlePreparationSummaryGroupModel::name)
    );

    return VBoxBuilder.vBox()
        .addChildren(groupTable)
        .setPrefWidth(400.0)
        .setPadding(new Insets(5.0))
        .getNode();
  }
}
