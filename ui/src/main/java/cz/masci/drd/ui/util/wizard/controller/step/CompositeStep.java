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

import java.util.function.Function;
import javafx.scene.layout.Region;

public interface CompositeStep extends HierarchicalStep {
  HierarchicalStep prev();

  HierarchicalStep next();

  HierarchicalStep current();

  HierarchicalStep goToStep(int index);

  void reset();

  boolean hasPrev();

  boolean hasNext();

  // Composite step should not have a view
  @Override
  default Region view() {
    return null;
  }

  default <R> R applyOnCompositeStepOr(HierarchicalStep step, Function<CompositeStep, R> iteratorStepFunction, R defaultValue) {
    return step instanceof CompositeStep compositeStep ? iteratorStepFunction.apply(compositeStep) : defaultValue;
  }
}
