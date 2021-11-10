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

/**
 * Service for CRUD operations on {@link Modifiable} entities.
 * <lt>
 *   <li>Get list of modifiable items</li>
 *   <li>Saves modified item</li>
 *   <li>Delete modified item</li>
 * </lt>
 * 
 * @author Daniel Masek
 * 
 * @param <T> Item type
 */
public interface CrudService<T extends Modifiable> {

  /**
   * Get list of items to display in Master view table.
   *
   * @return List of items
   */
  List<T> list();

  /**
   * Saves created/updated item from edit dialog or detail view.
   *
   * @param item Item to save
   * @return Saved item
   */
  T save(T item);

  /**
   * Deletes item.
   *
   * @param item Item to delete
   * @return Deleted item
   */
  T delete(T item);
}
