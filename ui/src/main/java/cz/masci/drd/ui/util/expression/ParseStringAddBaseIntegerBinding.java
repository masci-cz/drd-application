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

package cz.masci.drd.ui.util.expression;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.StringProperty;

public class ParseStringAddBaseIntegerBinding extends IntegerBinding {
  private final int base;
  private final StringProperty src;

  public ParseStringAddBaseIntegerBinding(int base, StringProperty src) {
    this.base = base;
    this.src = src;
    super.bind(this.src);
  }

  @Override
  protected int computeValue() {
    int result;
    try {
      result = Integer.parseInt(src.getValue()) + base;
    } catch (NumberFormatException e) {
      result = 0;
    }
    return result;
  }
}
