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

package cz.masci.drd.ui.adventure.view;

import static cz.masci.drd.ui.util.ViewBuilderUtils.buildAddButton;
import static cz.masci.drd.ui.util.ViewBuilderUtils.initSelectionModel;

import cz.masci.drd.ui.adventure.model.AdventureDetailModel;
import cz.masci.drd.ui.adventure.model.AdventureListModel;
import cz.masci.drd.ui.util.ViewBuilderUtils;
import cz.masci.springfx.mvci.view.impl.DirtyMFXTableRow;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdventureListViewBuilder implements Builder<Region> {

  private final AdventureListModel viewModel;

  @Override
  public Region build() {
    var result = new MFXTableView<>(viewModel.getElements());
    result.setMaxHeight(Double.MAX_VALUE);
    result.setMaxWidth(Double.MAX_VALUE);

    var nameColumn = ViewBuilderUtils.createTableColumn("Název", AdventureDetailModel::getName);
    nameColumn.setPrefWidth(400);

    result.getTableColumns().add(nameColumn);
    result.setTableRowFactory(data -> new DirtyMFXTableRow<>(result, data, "dirty-row"));
    result.getSelectionModel().setAllowsMultipleSelection(false);

    initSelectionModel(result.getSelectionModel(), result::update, viewModel);

    return new StackPane(result, buildAddButton(viewModel));
  }
}
