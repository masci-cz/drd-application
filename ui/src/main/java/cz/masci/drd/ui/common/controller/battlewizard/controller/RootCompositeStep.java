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

package cz.masci.drd.ui.common.controller.battlewizard.controller;

import javafx.beans.property.SimpleBooleanProperty;

public class RootCompositeStep extends CompositeStep {
  @Override
  public void updateNextDisable() {
    if (nextDisableProperty().isBound()) {
      nextDisableProperty().unbind();
    }
    if (currentStep.isPresent()) {
      var step = currentStep.getValue();
      var noNextStep = !children.hasNext() && !step.hasNext();
      nextDisableProperty().bind(step.nextDisableProperty().or(new SimpleBooleanProperty(noNextStep)));
    } else {
      setNextDisable(!children.hasNext());
    }
  }

  @Override
  public void updatePrevDisable() {
    if (prevDisableProperty().isBound()) {
      prevDisableProperty().unbind();
    }
    if (currentStep.isPresent()) {
      var step = currentStep.getValue();
      var noPrevStep = !children.hasPrevious() && !step.hasPrevious();
      prevDisableProperty().bind(step.prevDisableProperty().or(new SimpleBooleanProperty(noPrevStep)));
    } else {
      setPrevDisable(!children.hasPrevious());
    }
  }
}
