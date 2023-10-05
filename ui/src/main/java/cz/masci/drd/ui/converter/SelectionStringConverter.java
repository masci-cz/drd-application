/*
 * Copyright (c) 2023
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

package cz.masci.drd.ui.converter;

import jakarta.validation.constraints.NotNull;
import javafx.util.StringConverter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class SelectionStringConverter<T> extends StringConverter<T> {

  protected final String selectionText;

  @Override
  public String toString(T object) {
    return object == null ? selectionText : convert(object);
  }

  @Override
  public T fromString(String string) {
    return null;
  }

  protected abstract String convert(@NotNull T object);
}
