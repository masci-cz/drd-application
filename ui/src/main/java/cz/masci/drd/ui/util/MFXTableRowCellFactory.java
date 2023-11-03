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

package cz.masci.drd.ui.util;

import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MFXTableRowCellFactory<T> implements Function<T, MFXTableRowCell<T, ?>> {

  private final Function<T, ?> tableRowTypeExtractor;

  @Override
  public MFXTableRowCell<T, ?> apply(T t) {
    return new MFXTableRowCell<>(tableRowTypeExtractor);
  }
}
