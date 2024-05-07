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

  /**
   * Retrieves the previous button text for the current step.
   *
   * @return The previous button text, or null if not available.
   */
  String prevText();

  /**
   * Retrieves the next button text for the current step.
   *
   * @return The next button text, or null if not available.
   */
  String nextText();

  /**
   * This method performs any necessary actions before move to other step.
   *
   * @see Step#valid()
   */
  default void completeStep() {
    // do nothing
  }

  /**
   * Retrieves the expression that determines whether the previous button should be disabled.
   *
   * @return The BooleanExpression that determines whether the previous button should be disabled.
   */
  default BooleanExpression prevDisabled() {
    return Optional.ofNullable(getParent())
                   .map(HierarchicalStep::prevDisabled)
                   .map(parentPrevDisabled -> parentPrevDisabled.or(valid().not()))
                   .orElse(valid().not());
  }

  /**
   * Retrieves the expression that determines whether the next button should be disabled.
   *
   * @return The BooleanExpression that determines whether the next button should be disabled.
   */
  default BooleanExpression nextDisabled() {
    return Optional.ofNullable(getParent())
                   .map(HierarchicalStep::nextDisabled)
                   .map(parentNextDisabled -> parentNextDisabled.or(valid().not()))
                   .orElse(valid().not());
  }

  /**
   * Executes any necessary logic before going to the previous step. This method is called before the navigation to the previous step is performed. Implement
   * this method to perform any actions or validations that need to be done before proceeding to the previous step.
   */
  default void executeBeforePrev() {
    completeStep();
    Optional.ofNullable(getParent())
            .ifPresent(HierarchicalStep::executeBeforePrev);
  }

  /**
   * Executes any necessary logic before moving to the next step. This method is called before the navigation to the next step is performed. Implement this
   * method to perform any actions or validations that need to be done before proceeding to the next step.
   */
  default void executeBeforeNext() {
    completeStep();
    Optional.ofNullable(getParent())
            .ifPresent(HierarchicalStep::executeBeforeNext);
  }

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

}
