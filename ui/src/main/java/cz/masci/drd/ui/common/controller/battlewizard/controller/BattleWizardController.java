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
import java.util.Arrays;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class BattleWizardController extends MultiStep {
  // TODO Think about refactoring to simple tests
  private final WizardViewModel battlePreparationWizardViewModel = new WizardViewModel();
  private final WizardViewModel battleDuellistSummaryWizardViewModel = new WizardViewModel();
  private final WizardViewModel battleWizardViewModel = new WizardViewModel();
  private final WizardViewModel battleSummaryWizardViewModel = new WizardViewModel();

  public BattleWizardController(WizardViewModel wizardViewModel) {
    super(wizardViewModel);
    setChildren(Arrays.asList(
        new BattlePreparationController(battlePreparationWizardViewModel),
        new BattleDuellistSummaryController(battleDuellistSummaryWizardViewModel),
        new BattleController(battleWizardViewModel),
        new BattleSummaryController(battleSummaryWizardViewModel)
    ));
  }

  @Override
  public WizardStep next() {
    if (currentStep instanceof BattleSummaryController) {
      // TODO reinit iterator with different starting position
      super.next();
      if (currentStep == null) {
        children.previous();
        currentStep = children.previous();
        updateWizardViewModel();
      }
      return currentStep;
    }
    return super.next();
  }

  @Override
  protected void updateWizardViewModel() {
//    String prevText = "";
//    BooleanExpression prevDisable = new SimpleBooleanProperty(!children.hasPrevious());
//    String nextText = "";
//    BooleanExpression nextDisable = new SimpleBooleanProperty(false);
//    String title = "";
//
//    if (currentStep instanceof BattlePreparationController) {
//      prevText = battlePreparationWizardViewModel.getPrevText();
//      prevDisable = prevDisable.or(battlePreparationWizardViewModel.prevDisableProperty());
//      nextText = StringUtils.defaultIfBlank(battlePreparationWizardViewModel.getNextText(), "Přehled");
//      nextDisable = nextDisable.or(battlePreparationWizardViewModel.nextDisableProperty());
//      title = "Příprava";
//    }
//    if (currentStep instanceof BattleDuellistSummaryController) {
//      prevText = StringUtils.defaultIfBlank(battleDuellistSummaryWizardViewModel.getPrevText(), "Příprava");
//      prevDisable = prevDisable.or(battleDuellistSummaryWizardViewModel.prevDisableProperty());
//      nextText = StringUtils.defaultIfBlank(battleDuellistSummaryWizardViewModel.getNextText(), "Bitva");
//      nextDisable = nextDisable.or(battleDuellistSummaryWizardViewModel.nextDisableProperty());
//      title = "Souhrn bojovníků";
//    }
//    if (currentStep instanceof BattleController) {
//      prevText = StringUtils.defaultIfBlank(battleWizardViewModel.getPrevText(), "Ukončit bitvu");
//      prevDisable = prevDisable.or(battleWizardViewModel.prevDisableProperty());
//      nextText = StringUtils.defaultIfBlank(battleWizardViewModel.getNextText(), "Konec kola");
//      nextDisable = nextDisable.or(battleWizardViewModel.nextDisableProperty());
//      title = "Bitva";
//    }
//    if (currentStep instanceof BattleSummaryController) {
//      prevText = StringUtils.defaultIfBlank(battleSummaryWizardViewModel.getPrevText(), "Další kolo");
//      prevDisable = prevDisable.or(battlePreparationWizardViewModel.prevDisableProperty());
//      nextText = StringUtils.defaultIfBlank(battleSummaryWizardViewModel.getNextText(), "Další kolo");
//      nextDisable = nextDisable.or(battleSummaryWizardViewModel.nextDisableProperty());
//      title = "Souhrn kola";
//    }
//
//    wizardViewModel.setPrevText(prevText);
//    wizardViewModel.prevDisableProperty().bind(prevDisable);
//    wizardViewModel.setNextText(nextText);
//    wizardViewModel.nextDisableProperty().bind(nextDisable);
//    wizardViewModel.setTitle(title);
        wizardViewModel.setPrevText("Previous");
        wizardViewModel.prevDisableProperty().set(false);
        wizardViewModel.setNextText("Next");
        wizardViewModel.nextDisableProperty().set(false);
        wizardViewModel.setTitle("Title");
  }
}
