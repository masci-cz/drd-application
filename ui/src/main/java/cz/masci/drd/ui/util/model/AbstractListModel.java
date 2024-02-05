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
import java.util.function.Consumer;
import javafx.collections.ObservableList;
import lombok.Setter;
import org.reactfx.value.Var;

public abstract class AbstractListModel<E extends DetailModel<?>> implements ListModel<E> {
  protected final DirtyListProperty<E> items = new DirtyListProperty<>();
  protected final Var<E> selectedItem;
  @Setter
  protected Runnable onChangeItemProperty;
  @Setter
  private Consumer<E> onSelectItem;
  @Setter
  private Runnable onRequestFocusDetailView;

  public AbstractListModel(E initialValue) {
    selectedItem = Var.newSimpleVar(initialValue);
  }

  protected abstract E newItem();

  @Override
  public ObservableList<E> getItems() {
    return items.get();
  }

  @Override
  public Var<E> selectedItemProperty() {
    return selectedItem;
  }

  @Override
  public void remove(E item) {
    selectedItem.setValue(null);
    items.remove(item);
  }

  @Override
  public void changeItemProperty() {
    if (onChangeItemProperty != null) {
      onChangeItemProperty.run();
    }
  }

  public void createItem() {
    var item = newItem();
    items.add(item);
    selectItem(item);
    requestFocusDetailView();
  }

  @Override
  public void selectItem(E item) {
    if (onSelectItem != null) {
      onSelectItem.accept(item);
    }
  }

  public void requestFocusDetailView() {
    if (onRequestFocusDetailView != null) {
      onRequestFocusDetailView.run();
    }
  }
}
