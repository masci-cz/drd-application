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

import javafx.beans.binding.BooleanExpression;
import javafx.scene.layout.Region;

/**
 * The Step interface represents a single step in a wizard.
 * It provides methods to retrieve information about the step and perform actions associated with the step.
 */
public interface Step {
  // All these methods are calculated from bottom to top

  /**
   * Retrieves the view of the step.
   *
   * @return The view of the step.
   */
  Region view();

  /**
   * Retrieves the title of the step.
   *
   * @return The title of the step.
   */
  String title();

  /**
   * Indicates whether the step is valid to execute.
   *
   * @return boolean expression with validity of the step.
   */
  BooleanExpression valid();

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
   * Retrieves the expression that determines whether the previous button should be disabled.
   *
   * @return The BooleanExpression that determines whether the previous button should be disabled.
   */
  BooleanExpression prevDisabled();

  /**
   * Retrieves the expression that determines whether the next button should be disabled.
   *
   * @return The BooleanExpression that determines whether the next button should be disabled.
   */
  BooleanExpression nextDisabled();

  /**
   * Executes any necessary logic before going to the previous step.
   * This method is called before the navigation to the previous step is performed.
   * Implement this method to perform any actions or validations that need to be done before proceeding to the previous step.
   */
  void executeBeforePrev();

  /**
   * Executes any necessary logic before moving to the next step.
   * This method is called before the navigation to the next step is performed.
   * Implement this method to perform any actions or validations that need to be done before proceeding to the next step.
   */
  void executeBeforeNext();

  default boolean isValid() {
    return valid().get();
  }
}
