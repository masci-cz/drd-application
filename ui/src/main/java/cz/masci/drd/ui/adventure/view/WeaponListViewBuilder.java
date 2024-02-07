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
import cz.masci.springfx.mvci.view.builder.ButtonBuilder;
import cz.masci.springfx.mvci.view.impl.DirtyMFXTableRow;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import javafx.collections.MapChangeListener.Change;
import javafx.collections.ObservableMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;
import org.reactfx.EventStreams;

@RequiredArgsConstructor
public class WeaponListViewBuilder implements Builder<Region> {

  private final WeaponListModel viewModel;

  @Override
  public Region build() {
    return new StackPane(buildTable(), buildAddButton());
  }

  private Region buildAddButton() {
    var result = ButtonBuilder.builder().text("+").styleClass("filled").command(viewModel::createItem).build(MFXButton::new);
    StackPane.setAlignment(result, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(result, new Insets(30.0, 30.0, 80.0, 30.0));

    return result;
  }

  private MFXTableView<WeaponDetailModel> buildTable() {
    var result = new MFXTableView<>(viewModel.getElements());
    result.setMaxHeight(Double.MAX_VALUE);

    var nameColumn = createTableColumn("Název", WeaponDetailModel::getName);
    var strengthColumn = createTableColumn("Útočné číslo", WeaponDetailModel::getStrength);
    var damageColumn = createTableColumn("Útočnost", WeaponDetailModel::getDamage);

    result.getTableColumns().addAll(List.of(nameColumn, strengthColumn, damageColumn));
    result.setTableRowFactory(data -> new DirtyMFXTableRow<>(result, data, "dirty-row"));
    result.getSelectionModel().setAllowsMultipleSelection(false);
    ObservableMap<Integer, WeaponDetailModel> selectionProperty = result.getSelectionModel().selectionProperty();
    EventStreams.changesOf(selectionProperty)
        .filter(Change::wasAdded)
        .map(Change::getValueAdded)
        .feedTo(viewModel.selectedElementProperty());
    viewModel.setOnUpdateElementsProperty(result::update);
    viewModel.setOnSelectElement(item -> result.getSelectionModel().selectItem(item));

    return result;
  }

  private <T> MFXTableColumn<T> createTableColumn(String title, Function<T, String> extractor) {
    var result = new MFXTableColumn<>(title, Comparator.comparing(extractor));
    result.setRowCellFactory(item -> new MFXTableRowCell<>(extractor));

    return result;
  }

}
