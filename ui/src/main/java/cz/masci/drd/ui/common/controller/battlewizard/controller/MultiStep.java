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
import java.util.function.Function;
import javafx.scene.layout.Region;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MultiStep implements WizardStep {

  protected final WizardViewModel wizardViewModel;
  protected ObservableListIterator<? extends WizardStep> children;
  protected WizardStep currentStep;

  protected void updateWizardViewModel() {
//    wizardViewModel.setPrevText(children.hasPrevious() ? "Předchozí" : "První");
//    wizardViewModel.setPrevDisable(false);
//    wizardViewModel.setNextText(children.hasNext() ? "Další" : "Poslední");
//    wizardViewModel.setNextDisable(false);
//    wizardViewModel.setTitle("Wizard");
  }

  protected void setChildren(List<? extends WizardStep> children) {
    this.children = new ObservableListIterator<>(children);
  }

  @Override
  public WizardStep next() {
    return future(Iterable::next);
  }

  @Override
  public WizardStep previous() {
    return future(Iterable::previous);
  }

  private WizardStep future(Function<Iterable<? extends WizardStep>, WizardStep> futureStep) {
    if (currentStep == null) { // if is not initiated
      currentStep = futureStep.apply(children);
      if (currentStep.hasChildren()) {
        futureStep.apply(currentStep);  // currentStep has to stay one of the children in this class
      }
    } else { // get next step
      // if current step has children get his next child
      if (currentStep.hasChildren()) {
        if (futureStep.apply(currentStep) == null) {
          currentStep = futureStep.apply(children);
          if (currentStep != null && currentStep.hasChildren()) {
            futureStep.apply(currentStep);
          }
        }
      } else {
        currentStep = futureStep.apply(children);
        if (currentStep != null && currentStep.hasChildren()) {
          futureStep.apply(currentStep);
        }
      }
    }
    updateWizardViewModel();
    return currentStep;
  }

  @Override
  public boolean hasChildren() {
    return true;
  }

  @Override
  public Region getView() {
    return currentStep != null ? currentStep.getView() : null;
  }

}
