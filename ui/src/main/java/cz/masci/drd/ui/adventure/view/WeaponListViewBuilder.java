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

import cz.masci.drd.ui.adventure.model.WeaponDetailModel;
import cz.masci.drd.ui.adventure.model.WeaponListModel;
import cz.masci.springfx.mvci.util.ListChangeListenerBuilder;
import cz.masci.springfx.mvci.view.impl.DirtyMFXTableRow;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WeaponListViewBuilder implements Builder<Region> {

  private final WeaponListModel viewModel;

  @Override
  public Region build() {
    return new StackPane(buildTable());
  }

  private Region buildTable() {
    var result = new MFXTableView<>(viewModel.getItems());
    result.setMaxHeight(Double.MAX_VALUE);

    var nameColumn = createTableColumn("Název", WeaponDetailModel::getName);
    var strengthColumn = createTableColumn("Útočné číslo", WeaponDetailModel::getStrength);
    var damageColumn = createTableColumn("Útočnost", WeaponDetailModel::getDamage);

    result.getTableColumns().addAll(List.of(nameColumn, strengthColumn, damageColumn));
    result.setTableRowFactory(data -> new DirtyMFXTableRow<>(result, data, "dirty-row"));
    result.getSelectionModel().setAllowsMultipleSelection(false);
    result.getSelectionModel()
        .selectionProperty()
        .addListener(
            (MapChangeListener<? super Integer, ? super WeaponDetailModel>) (change) -> viewModel.selectedItemProperty().setValue(change.wasAdded() ? change.getValueAdded() : null)
        );

    viewModel.getItems().addListener(createItemPropertyChangeListener(result));

    return result;
  }

  private <T> MFXTableColumn<T> createTableColumn(String title, Function<T, String> extractor) {
    var result = new MFXTableColumn<>(title, Comparator.comparing(extractor));
    result.setRowCellFactory(item -> new MFXTableRowCell<>(extractor));

    return result;
  }

  private ListChangeListener<? super WeaponDetailModel> createItemPropertyChangeListener(MFXTableView<WeaponDetailModel> tableView) {
    ChangeListener<? super String> itemPropertyListener = ((observable, oldValue, newValue) -> tableView.update());

    return new ListChangeListenerBuilder<WeaponDetailModel>()
        .onAdd(addItem -> {
          addItem.nameProperty().addListener(itemPropertyListener);
          addItem.strengthProperty().addListener(itemPropertyListener);
          addItem.damageProperty().addListener(itemPropertyListener);
        })
        .onRemove(remItem -> {
          remItem.nameProperty().removeListener(itemPropertyListener);
          remItem.strengthProperty().removeListener(itemPropertyListener);
          remItem.damageProperty().removeListener(itemPropertyListener);
        })
        .build();
  }

}
