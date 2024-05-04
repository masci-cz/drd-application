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

package cz.masci.drd.ui.util.wizard.controller.step;

import java.util.Optional;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;

public interface HierarchicalStep extends Step {
  BooleanProperty TRUE_PROPERTY = new ReadOnlyBooleanWrapper(true);

  void setParent(HierarchicalStep parent);

  HierarchicalStep getParent();

  @Override
  default BooleanExpression valid() {
    return Optional.ofNullable(getParent())
                   .map(Step::valid)
                   .orElse(TRUE_PROPERTY);
  }

  @Override
  default String title() {
    return Optional.ofNullable(getParent())
                   .map(Step::title)
                   .orElse(null);
  }

  @Override
  default BooleanExpression prevDisabled() {
    return Optional.ofNullable(getParent())
                   .map(Step::prevDisabled)
                   .map(parentPrevDisabled -> parentPrevDisabled.or(valid().not()))
                   .orElse(valid().not());
  }

  @Override
  default BooleanExpression nextDisabled() {
    return Optional.ofNullable(getParent())
                   .map(Step::nextDisabled)
                   .map(parentNextDisabled -> parentNextDisabled.or(valid().not()))
                   .orElse(valid().not());
  }
}
