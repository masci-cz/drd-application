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

package cz.masci.drd.ui.app.monster.view;

import static cz.masci.drd.ui.util.ViewBuilderUtils.buildAddButton;
import static cz.masci.drd.ui.util.ViewBuilderUtils.initSelectionModel;

import cz.masci.drd.ui.app.monster.model.MonsterDetailModel;
import cz.masci.drd.ui.app.monster.model.MonsterListModel;
import cz.masci.drd.ui.util.ViewBuilderUtils;
import cz.masci.springfx.mvci.view.impl.DirtyMFXTableRow;
import io.github.palexdev.materialfx.controls.MFXTableView;
import java.util.List;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MonsterListViewBuilder implements Builder<Region> {

  private final MonsterListModel viewModel;

  @Override
  public Region build() {
    return new StackPane(buildTable(), buildAddButton(viewModel));
  }

  private MFXTableView<MonsterDetailModel> buildTable() {
    var result = new MFXTableView<>(viewModel.getElements());
    result.setMaxHeight(Double.MAX_VALUE);
    result.setMaxWidth(Double.MAX_VALUE);

    var nameColumn = ViewBuilderUtils.createTableColumn("Jméno nestvůry", MonsterDetailModel::getName);
    nameColumn.setPrefWidth(300);
    var viabilityColumn = ViewBuilderUtils.createTableColumn("Životaschopnost", MonsterDetailModel::getViability);

    result.getTableColumns().addAll(List.of(nameColumn, viabilityColumn));
    result.setTableRowFactory(data -> new DirtyMFXTableRow<>(result, data, "dirty-row"));
    result.getSelectionModel().setAllowsMultipleSelection(false);

    initSelectionModel(result.getSelectionModel(), result::update, viewModel);

    return result;
  }

}
