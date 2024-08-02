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
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class SimpleCompositeStep implements CompositeStep {

  @Setter
  @Getter
  private HierarchicalStep parent;
  protected final List<HierarchicalStep> steps = new ArrayList<>();

  @Getter
  private int currentIdx = -1;
  @Getter
  private boolean doStep = false;
  private CompositeStep currentChildIterator;

  protected abstract String getPrevText();

  protected abstract String getNextText();

  /**
   * When going to previous step, there is no validation because we assume that go back is
   * in most examples enabled.
   *
   * @return Previous step
   */
  @Override
  public HierarchicalStep prev() {
    // is already first step
    if (currentIdx < 0) {
      doStep = false;
      currentChildIterator = null;
      log.debug("Previous step ({}): Is the first step - get step from parent", this.getClass().getSimpleName());
      return applyOnCompositeStepOr(parent, CompositeStep::prev, null);
    }

    boolean hasChildIterator = prepareChildIterator();

    if (!hasChildIterator && doStep) {
      log.debug("Previous step ({}): Get {}. step", this.getClass().getSimpleName(), currentIdx);
      doStep = false;
      return steps.get(currentIdx);
    }

    if (currentChildIterator != null && hasChildIterator) {
      log.debug("Previous step ({}): Get step from {}. child", this.getClass().getSimpleName(), currentIdx);
      var prevStep = currentChildIterator.prev();
      doStep = false;
      currentChildIterator = null;
      return prevStep;
    }

    log.debug("Previous step ({}): There is no more steps in {}. child", this.getClass().getSimpleName(), currentIdx);
    currentIdx--;
    doStep = true;
    return prev();
  }

  /**
   * Get the next step only when is valid. Otherwise, it should return current
   *
   * @return Next step
   */
  @Override
  public HierarchicalStep next() {
    // is already last step
    if (currentIdx >= steps.size()) {
      doStep = false;
      currentChildIterator = null;
      log.debug("Next step ({}): Is the last step - get step from parent", this.getClass().getSimpleName());
      return applyOnCompositeStepOr(parent, CompositeStep::next, null);
    }

    boolean hasChildIterator = prepareChildIterator();

    if (!hasChildIterator && doStep) {
      log.debug("Next step ({}): Get {}. step", this.getClass().getSimpleName(), currentIdx);
      doStep = false;
      return steps.get(currentIdx);
    }

    if (isValid(hasChildIterator)) {
      if (currentChildIterator != null && hasChildIterator) {
        log.debug("Next step ({}): Get step from {}. child", this.getClass().getSimpleName(), currentIdx);
        var nextStep = currentChildIterator.next();
        doStep = false;
        currentChildIterator = null;
        return nextStep;
      } else {
        log.debug("Next step ({}): There is no more steps in {}. child", this.getClass().getSimpleName(), currentIdx);
        currentIdx++;
        doStep = true;
        return next();
      }
    }

    log.debug("Next step ({}): Step is not valid - Get {}. child", this.getClass().getSimpleName(), currentIdx);
    doStep = false;
    currentChildIterator = null;
    return null;
  }

  @Override
  public HierarchicalStep current() {
    log.debug("Get current {}. step", currentIdx);
    if (!isValidIndex(currentIdx)) {
      throw new RuntimeException("Go to non valid step index");
    }

    if (prepareChildIterator()) {
      var step = currentChildIterator.current();
      currentChildIterator = null;
      return step;
    }

    return steps.get(currentIdx);
  }

  @Override
  public HierarchicalStep goToStep(int index) {
    log.debug("Go to {}. step from {}. step", index, currentIdx);
    if (!isValidIndex(index)) {
      throw new RuntimeException("Go to non valid step index");
    }

    int delta = currentIdx - index;
    if (delta > 0) {
      for (int i = 0; i < delta; i++) {
        var step = currentIdx - i < steps.size() ? steps.get(currentIdx - i) : null;
        if (step instanceof CompositeStep compositeStep) {
          compositeStep.reset();
        }
      }
    }
    // TODO: forward each composite step to the end

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

  public void removeStep(int index) {
    if (isValidIndex(index)) {
      steps.remove(index);
    }
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

  private boolean isValid(boolean hasChildIterator) {
    if (hasChildIterator) {
      return currentChildIterator.isValid();
    }
    return !isValidIndex(currentIdx) || steps.get(currentIdx).isValid();
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
