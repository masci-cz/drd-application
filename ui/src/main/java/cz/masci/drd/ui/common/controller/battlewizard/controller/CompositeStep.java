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

import cz.masci.drd.ui.common.controller.WizardStep;
import cz.masci.drd.ui.common.model.WizardViewModel;
import cz.masci.drd.ui.util.iterator.Iterable;
import cz.masci.drd.ui.util.iterator.ObservableListIterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javafx.scene.layout.Region;
import lombok.extern.slf4j.Slf4j;
import org.reactfx.value.Var;

@Slf4j
public class CompositeStep extends WizardViewModel implements WizardStep {
  protected final Var<WizardStep> currentStep = Var.newSimpleVar(null);

  protected ObservableListIterator<? extends WizardStep> children;

  protected void setChildren(List<? extends WizardStep> children) {
    this.children = new ObservableListIterator<>(children);
  }

  @Override
  public WizardStep next() {
    return doStep(Iterable::next).getOrElse(null);
  }

  @Override
  public WizardStep previous() {
    return doStep(Iterable::previous).getOrElse(null);
  }

  @Override
  public WizardStep getCurrent() {
    return currentStep.getValue();
  }

  @Override
  public Region getView() {
    return currentStep.map(WizardStep::getView)
                      .getOrElse(null);
  }

  @Override
  public boolean hasNext() {
    return Optional.ofNullable(children).map(ObservableListIterator::hasNext).orElse(false);
  }

  @Override
  public boolean hasPrevious() {
    return Optional.ofNullable(children).map(ObservableListIterator::hasPrevious).orElse(false);
  }

  private Var<WizardStep> doStep(Function<Iterable<? extends WizardStep>, WizardStep> doStep) {
    currentStep.ifPresent(doStep::apply); // do prev/next on current step
    if (currentStep.map(WizardStep::getCurrent).isEmpty()) {  // current step is empty/null
      doStep.apply(children);  // do prev/next on children
      currentStep.setValue(children.getCurrent());  // set current step
      currentStep.ifPresent(doStep::apply);  // if there is still some step in this composite do the prev/next on the child
    }

    updateTitle();
    updateNextText();
    updatePrevText();
    updateNextDisable();
    updatePrevDisable();

    return currentStep;
  }

  public void updateTitle() {
    currentStep.ifPresent(child -> setTitle(child.getTitle()));
  }

  public void updateNextText() {
    setNextText("Další");
  }

  public void updatePrevText() {
    setPrevText("Předchozí");
  }

  public void updateNextDisable() {
    if (nextDisableProperty().isBound()) {
      nextDisableProperty().unbind();
    }
    var childDisableProperty = currentStep.map(WizardStep::nextDisableProperty);
    if (childDisableProperty.isPresent()) {
      nextDisableProperty().bind(childDisableProperty.getValue());
    } else {
      setNextDisable(false);
    }
  }

  public void updatePrevDisable() {
    if (prevDisableProperty().isBound()) {
      prevDisableProperty().unbind();
    }
    var childDisableProperty = currentStep.map(WizardStep::prevDisableProperty);
    if (childDisableProperty.isPresent()) {
      prevDisableProperty().bind(childDisableProperty.getValue());
    } else {
      setPrevDisable(false);
    }
  }
}
