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

import cz.masci.drd.ui.app.battle.wizard.model.BattlePreparationSummaryDuellistModel;
import cz.masci.drd.ui.app.battle.wizard.model.BattlePreparationSummaryGroupModel;
import cz.masci.springfx.mvci.util.MFXBuilderUtils;
import cz.masci.springfx.mvci.view.builder.BorderPaneBuilder;
import io.github.palexdev.materialfx.controls.MFXTableView;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;
import org.reactfx.EventStreams;
import org.reactfx.value.Val;

@RequiredArgsConstructor
public class BattlePreparationSummaryViewBuilder implements Builder<Region> {

  private final ObservableList<BattlePreparationSummaryGroupModel> groups;

  @Override
  public Region build() {
    ObjectProperty<BattlePreparationSummaryGroupModel> selectedElement = new SimpleObjectProperty<>();
    ObservableMap<Integer, BattlePreparationSummaryGroupModel> selectionProperty;
    ListProperty<BattlePreparationSummaryDuellistModel> duellists = new SimpleListProperty<>(FXCollections.observableArrayList());

    var groupTable = new MFXTableView<>(groups);
    groupTable.setFooterVisible(false);
    groupTable.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    groupTable.getSelectionModel().setAllowsMultipleSelection(false);
    groupTable.getTableColumns().add(
        MFXBuilderUtils.createTableColumn("Název", BattlePreparationSummaryGroupModel::getName)
    );

    var duellistTable = new MFXTableView<>(duellists);
    duellistTable.setFooterVisible(false);
    duellistTable.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    duellistTable.getSelectionModel().setAllowsMultipleSelection(false);
    duellistTable.getTableColumns().addAll(
        List.of(
          MFXBuilderUtils.createTableColumn("Název", BattlePreparationSummaryDuellistModel::name),
          MFXBuilderUtils.createTableColumn("Útočné číslo", BattlePreparationSummaryDuellistModel::attack),
          MFXBuilderUtils.createTableColumn("Obranné číslo", BattlePreparationSummaryDuellistModel::defense),
          MFXBuilderUtils.createTableColumn("Útočnost", BattlePreparationSummaryDuellistModel::damage),
          MFXBuilderUtils.createTableColumn("Životy", BattlePreparationSummaryDuellistModel::live)
        )
    );

    duellists.bind(Val.flatMap(selectedElement, BattlePreparationSummaryGroupModel::duellistsProperty).orElseConst(FXCollections.observableArrayList()));
    selectionProperty = groupTable.getSelectionModel().selectionProperty();

    EventStreams.changesOf(selectionProperty)
                .filter(Change::wasAdded)
                .map(Change::getValueAdded)
                .hook(unused -> duellistTable.getSelectionModel().clearSelection())
                .feedTo(selectedElement);

    return BorderPaneBuilder.builder()
        .withTop(groupTable)
        .withCenter(duellistTable)
        .build();
  }
}
