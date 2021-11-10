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
package cz.masci.springfx.service;

import cz.masci.springfx.data.Modifiable;
import java.util.List;
import java.util.function.Supplier;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * This interface manages mofified items used by Master-Detail View Controller.
 * Each item is managed by different list differentiated by key identifier.
 *
 * @author Daniel Masek
 */
public interface ModifiableService {

  /**
   * Add or update item in the list identified by item class.
   *
   * @param <T> Item type
   * @param item Item to add
   */
  <T extends Modifiable> void add(T item);

  /**
   * Add or update item in the list identified by key param.
   *
   * @param <T> Item type
   * @param key Identifier of list where to add item
   * @param item Item to add
   */
  <T extends Modifiable> void add(String key, T item);

  /**
   * Remove item from list identified by item.
   *
   * @param <T> Item type
   * @param item Item to remove
   */
  <T extends Modifiable> void remove(T item);

  /**
   * Remove item from list identified by key param.
   *
   * @param <T> Item type
   * @param key List identifier
   * @param item Item to remove
   */
  <T extends Modifiable> void remove(String key, T item);

  /**
   * Test the list identified by item class if contains the item.
   *
   * @param <T> Item type
   * @param item Item to test
   * @return <code>TRUE</code> if the list contains the item
   */
  <T extends Modifiable> boolean contains(T item);

  /**
   * Test the list identified by key param if contains the item.
   *
   * @param <T> Item type
   * @param key List identifier
   * @param item Item to test
   * @return <code>TRUE</code> if the list contains the item
   */
  <T extends Modifiable> boolean contains(String key, T item);

  /**
   * Get all items from list identified by clazz param.
   *
   * @param <T> Item type
   * @param key List identifier
   * @return
   */
  <T extends Modifiable> List<T> getAll(Class<T> key);

  /**
   * Get all items from list identified by key param.
   *
   * @param <T> Item type
   * @param key List identifier
   * @return Item list
   */
  <T extends Modifiable> List<T> getAll(String key);

  /**
   * Add list change listener on list identified by clazz param.
   *
   * @param <T> Item type
   * @param key List identifier
   * @param changeListener {@link ListChangeListener}
   */
  <T extends Modifiable> void addListener(Class<T> key, ListChangeListener<T> changeListener);

  /**
   * Add list change listener on list identified by key param.
   *
   * @param key List identifier
   * @param changeListener {@link ListChangeListener}
   */
  void addListener(String key, ListChangeListener<? extends Modifiable> changeListener);

  /**
   * Remove list change listener from list identified by clazz param.
   *
   * @param <T> Item type
   * @param clazz List identifier
   * @param changeListener {@link ListChangeListener}
   */
  <T extends Modifiable> void removeListener(Class<T> clazz, ListChangeListener<T> changeListener);

  /**
   * Remove list change listener from list identified by key param.
   *
   * @param key List identifier
   * @param changeListener {@link ListChangeListener}
   */
  void removeListener(String key, ListChangeListener<? extends Modifiable> changeListener);
}
