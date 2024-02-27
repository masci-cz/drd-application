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

import cz.masci.drd.ui.common.model.WizardViewModel;
import java.util.Arrays;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class BattlePreparationController extends MultiStep {

  private final WizardViewModel childrenWizardViewModel = new WizardViewModel();

  public BattlePreparationController(WizardViewModel wizardViewModel) {
    super(wizardViewModel);
    setChildren(
        Arrays.asList(
            new BattlePreparationGroupController(childrenWizardViewModel),
            new BattlePreparationDuellistController(childrenWizardViewModel)
        )
    );
  }

  @Override
  protected void updateWizardViewModel() {
    String prevText = "Předchozí";
    BooleanProperty prevDisable = new SimpleBooleanProperty(false);
    String nextText = "Další";
    BooleanProperty nextDisable = new SimpleBooleanProperty(false);
    String title = "Preparation";

    // TODO change view model based on current step
    if (currentStep instanceof BattlePreparationGroupController) {
    }

    if (currentStep instanceof BattlePreparationDuellistController) {
    }

    wizardViewModel.setPrevText(prevText);
    wizardViewModel.prevDisableProperty().bind(prevDisable);
    wizardViewModel.setNextText(nextText);
    wizardViewModel.nextDisableProperty().bind(nextDisable);
    wizardViewModel.setTitle(title);
  }

}
