/*
 * Copyright (C) 2021 Daniel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cz.masci.springfx.utility;

import cz.masci.springfx.data.Modifiable;
import java.util.Collections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import cz.masci.springfx.service.ObservableListMap;

/**
 * Row factory for items in observable list map. Add style class to the row if
 * the item row is found in appropriate list.
 *
 * @author Daniel Masek
 * @param <T> Type of table item
 */
public class StyleChangingRowFactory<T extends Modifiable> implements Callback<TableView<T>, TableRow<T>> {

  private final ObservableListMap observableListMap;
  private final String styleClass;
  private final String modifiableKey;
  private final Callback<TableView<T>, TableRow<T>> baseFactory;

  public StyleChangingRowFactory(String styleClass, String modifiableKey, ObservableListMap modifiableService, Callback<TableView<T>, TableRow<T>> baseFactory) {
    this.styleClass = styleClass;
    this.modifiableKey = modifiableKey;
    this.observableListMap = modifiableService;
    this.baseFactory = baseFactory;
  }

  public StyleChangingRowFactory(String styleClass, String modifiableKey, ObservableListMap modifiableService) {
    this(styleClass, modifiableKey, modifiableService, null);
  }

  public StyleChangingRowFactory(String styleClass, Class<T> modifiableKey, ObservableListMap modifiableService, Callback<TableView<T>, TableRow<T>> baseFactory) {
    this(styleClass, modifiableKey.getSimpleName(), modifiableService, baseFactory);
  }

  public StyleChangingRowFactory(String styleClass, Class<T> modifiableKey, ObservableListMap modifiableService) {
    this(styleClass, modifiableKey, modifiableService, null);
  }

  /**
   * Add listener on table item and observable list map to check item existence
   * and therefor style changing.
   *
   * @param tableView Table view to check
   * @return Table row
   */
  @Override
  public TableRow<T> call(TableView<T> tableView) {

    final TableRow<T> row;
    if (baseFactory == null) {
      row = new TableRow<>();
    } else {
      row = baseFactory.call(tableView);
    }

    row.itemProperty().addListener((obs, oldValue, newValue) -> updateStyleClass(row));

    observableListMap.addListener(modifiableKey, (change) -> updateStyleClass(row));

    return row;
  }

  /**
   * Add or remove style class from row style classes based on existence of
   * selected item in the list map.
   *
   * @param row Selected table row
   */
  private void updateStyleClass(TableRow<T> row) {
    final ObservableList<String> rowStyleClasses = row.getStyleClass();
    if (observableListMap.contains(row.getItem())) {
      if (!rowStyleClasses.contains(styleClass)) {
        rowStyleClasses.add(styleClass);
      }
    } else {
      // remove all occurrences of styleClass:
      rowStyleClasses.removeAll(Collections.singleton(styleClass));
    }
  }

}
