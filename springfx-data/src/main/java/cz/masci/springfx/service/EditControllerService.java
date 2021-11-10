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
import javafx.scene.control.ButtonType;
import javafx.util.Callback;

/**
 * This service is used by Master-Detail View Controllers. It is used to convert
 * javaFx dialog button type to edited/created item.
 * 
 * @author Daniel Masek
 * 
 * @param <T> Edited item type
 */
public interface EditControllerService<T extends Modifiable> {
  
  /**
   * Returns converter from {@link javafx.scene.control.ButtonType} to edited item type.
   * 
   * @return ButtonType to edited item Converter 
   */
  Callback<ButtonType, T> getResultConverter();
}