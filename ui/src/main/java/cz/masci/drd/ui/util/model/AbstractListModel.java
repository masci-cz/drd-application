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

package cz.masci.drd.ui.util.model;

import cz.masci.springfx.mvci.model.dirty.DirtyListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

public class AbstractListModel<E extends DetailModel<?>> implements ListModel<E> {
  protected final DirtyListProperty<E> items = new DirtyListProperty<>();

  protected final ObjectProperty<E> selectedItem = new SimpleObjectProperty<>();

  @Override
  public ObservableList<E> getItems() {
    return items.get();
  }

  @Override
  public E getSelectedItem() {
    return selectedItem.getValue();
  }

  @Override
  public ObjectProperty<E> selectedItemProperty() {
    return selectedItem;
  }

  @Override
  public void remove(E item) {
    selectedItem.set(null);
    items.remove(item);
  }
}
