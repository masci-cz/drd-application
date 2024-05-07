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

package cz.masci.drd.ui.util.wizard.controller.step.impl;

import cz.masci.drd.ui.util.wizard.controller.step.CompositeStep;
import cz.masci.drd.ui.util.wizard.controller.step.HierarchicalStep;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.BooleanExpression;
import lombok.Getter;
import lombok.Setter;

public abstract class SimpleCompositeStep implements CompositeStep {

  @Setter
  @Getter
  private HierarchicalStep parent;
  private final List<HierarchicalStep> steps = new ArrayList<>();

  @Getter
  private int currentIdx = -1;
  @Getter
  private boolean doStep = false;
  private CompositeStep currentChildIterator;

  protected abstract String getPrevText();

  protected abstract String getNextText();

  @Override
  public HierarchicalStep prev() {
    // is already first step
    if (currentIdx < 0) {
      doStep = false;
      currentChildIterator = null;
      System.out.printf("Previous step (%s): Is already first\n", this);
      return applyOnCompositeStepOr(parent, CompositeStep::prev, null);
    }

    boolean hasChildIterator = prepareChildIterator();

    if (!hasChildIterator && doStep) {
      doStep = false;
      System.out.printf("Previous step (%s): Step from children\n", this);
      return steps.get(currentIdx);
    }

    if (isValid(hasChildIterator).get()) {
      if (currentChildIterator != null && hasChildIterator) {
        var prevStep = currentChildIterator.prev();
        doStep = false;
        currentChildIterator = null;
        System.out.printf("Previous step (%s): Step from child iterator\n", this);
        return prevStep;
      } else {
        currentIdx--;
        doStep = true;
        System.out.printf("Previous step (%s): Child iterator has no more steps\n", this);
        return prev();
      }
    }

    doStep = false;
    currentChildIterator = null;
    return null;
  }

  @Override
  public HierarchicalStep next() {
    // is already last step
    if (currentIdx >= steps.size()) {
      doStep = false;
      currentChildIterator = null;
      System.out.printf("Next step (%s): Is already first\n", this);
      return applyOnCompositeStepOr(parent, CompositeStep::next, null);
    }

    boolean hasChildIterator = prepareChildIterator();

    if (!hasChildIterator && doStep) {
      doStep = false;
      System.out.printf("Next step (%s): Step from children\n", this);
      return steps.get(currentIdx);
    }

    if (isValid(hasChildIterator).get()) {
      if (currentChildIterator != null && hasChildIterator) {
        var nextStep = currentChildIterator.next();
        doStep = false;
        currentChildIterator = null;
        System.out.printf("Next step (%s): Step from child iterator\n", this);
        return nextStep;
      } else {
        currentIdx++;
        doStep = true;
        System.out.printf("Next step (%s): Child iterator has no more steps\n", this);
        return next();
      }
    }

    doStep = false;
    currentChildIterator = null;
    return null;
  }

  @Override
  public HierarchicalStep goToStep(int index) {
    System.out.printf("Call goToStep(): %s, current idx: %d, new idx: %d\n", getClass(), currentIdx, index);
    if (!isValidIndex(index)) {
      return null;
    }

    currentIdx = index;

    if (prepareChildIterator()) {
      currentChildIterator.reset();
      var step = currentChildIterator.next();
      currentChildIterator = null;
      return step;
    }

    return steps.get(currentIdx);
  }

  @Override
  public void reset() {
    currentIdx = -1;
  }

  @Override
  public boolean hasPrev() {
    var result = hasPrev(prepareChildIterator());
    currentChildIterator = null;
    return result;
  }

  @Override
  public boolean hasNext() {
    var result = hasNext(prepareChildIterator());
    currentChildIterator = null;
    return result;
  }

  @Override
  public String prevText() {
    String text = null;
    if (currentIdx > 0) {
      text = getPrevText();
    } else if (parent != null) {
      text = parent.prevText();
    }
    return text;
  }

  @Override
  public String nextText() {
    String text = null;
    if (currentIdx < steps.size() - 1) {
      text = getNextText();
    } else if (parent != null) {
      text = parent.nextText();
    }
    return text;
  }

  public void addStep(HierarchicalStep step) {
    steps.add(step);
    step.setParent(this);
  }

  public void clearSteps() {
    steps.clear();
  }

  protected boolean isValidIndex(int index) {
    return index >= 0 && index < steps.size();
  }

  private boolean prepareChildIterator() {
    if (isValidIndex(currentIdx) && steps.get(currentIdx) instanceof CompositeStep compositeStep && (currentChildIterator == null || !currentChildIterator.equals(compositeStep))) {
      currentChildIterator = compositeStep;
      return true;
    } else {
      return false;
    }
  }

  private BooleanExpression isValid(boolean hasChildIterator) {
    if (hasChildIterator) {
      return currentChildIterator.valid();
    }
    return isValidIndex(currentIdx) ? steps.get(currentIdx)
                                           .valid() : TRUE_PROPERTY;
  }

  private boolean hasPrev(boolean hasChildIterator) {
    if (hasChildIterator) {
      return currentChildIterator.hasPrev() || currentIdx > 0;
    } else if (currentIdx > 0) {
      currentChildIterator = null;
      return true;
    }
    return false;
  }

  private boolean hasNext(boolean hasChildIterator) {
    if (hasChildIterator) {
      return currentChildIterator.hasNext() || currentIdx < steps.size() - 1;
    } else if (currentIdx < steps.size() - 1) {
      currentChildIterator = null;
      return true;
    }
    return false;
  }
}
