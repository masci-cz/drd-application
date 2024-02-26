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
    String prevText = "";
    BooleanProperty prevDisable = new SimpleBooleanProperty(!children.hasPrevious());
    String nextText = "";
    BooleanProperty nextDisable = new SimpleBooleanProperty(false);
    String title = "";

    if (currentStep instanceof BattlePreparationController) {
      log.info("Current step: BattlePreparationController");
      prevText = battlePreparationWizardViewModel.getPrevText();
      prevDisable.or(battlePreparationWizardViewModel.prevDisableProperty());
      nextText = StringUtils.isBlank(battlePreparationWizardViewModel.getNextText()) ? "Přehled" : battlePreparationWizardViewModel.getNextText();
      nextDisable.or(battlePreparationWizardViewModel.nextDisableProperty());
      title = "Příprava";
    }
    if (currentStep instanceof BattleDuellistSummaryController) {
      log.info("Current step: BattleDuellistSummaryController");
      prevText = StringUtils.isBlank(battleDuellistSummaryWizardViewModel.getPrevText()) ? "Příprava" : battleDuellistSummaryWizardViewModel.getPrevText();
      prevDisable.or(battleDuellistSummaryWizardViewModel.prevDisableProperty());
      nextText = StringUtils.isBlank(battleDuellistSummaryWizardViewModel.getNextText()) ? "Bitva" : battlePreparationWizardViewModel.getNextText();
      nextDisable.or(battleDuellistSummaryWizardViewModel.nextDisableProperty());
      title = "Souhrn bojovníků";
    }
    if (currentStep instanceof BattleController) {
      log.info("Current step: BattleController");
      prevText = StringUtils.isBlank(battleWizardViewModel.getPrevText()) ? "Ukončit bitvu" : battleWizardViewModel.getPrevText();
      prevDisable.or(battleWizardViewModel.prevDisableProperty());
      nextText = StringUtils.isBlank(battleWizardViewModel.getNextText()) ? "Konec kola" : battlePreparationWizardViewModel.getNextText();
      nextDisable.or(battleWizardViewModel.nextDisableProperty());
      title = "Bitva";
    }
    if (currentStep instanceof BattleSummaryController) {
      log.info("Current step: BattleSummaryController");
      prevText = StringUtils.isBlank(battleSummaryWizardViewModel.getPrevText()) ? "Další kolo" : battleSummaryWizardViewModel.getPrevText();
      prevDisable.or(battlePreparationWizardViewModel.prevDisableProperty());
      nextText = StringUtils.isBlank(battleSummaryWizardViewModel.getNextText()) ? "Další kolo" : battlePreparationWizardViewModel.getNextText();
      nextDisable.or(battleSummaryWizardViewModel.nextDisableProperty());
      title = "Souhrn kola";
    }

    wizardViewModel.setPrevText(prevText);
    wizardViewModel.prevDisableProperty().bind(prevDisable);
    wizardViewModel.setNextText(nextText);
    wizardViewModel.nextDisableProperty().bind(nextDisable);
    wizardViewModel.setTitle(title);
  }
}
