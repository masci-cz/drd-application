/*
 * Copyright (C) 2026 Daniel Masek
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cz.masci.drd.ui.app.battle.wizard.step;

import cz.masci.drd.ui.app.battle.wizard.interactor.BattleInteractor;
import cz.masci.drd.ui.util.wizard.model.StepDirection;
import cz.masci.drd.ui.util.wizard.model.WizardHierarchicalStep;
import cz.masci.wizard.simple.SimpleHierarchicalStep;
import javafx.collections.FXCollections;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BattlePreparationStep extends WizardHierarchicalStep {

    private final BattleInteractor interactor;

    @Override
    protected void initStep(SimpleHierarchicalStep<WizardHierarchicalStep> step) {
        step.clearChildren();
        step.addChild(new BattlePreparationSummaryStep(FXCollections.observableList(interactor.getPreparationSummary())).getStep());
    }

    @Override
    protected void checkStep(SimpleHierarchicalStep<WizardHierarchicalStep> step, StepDirection direction) {
        if (step.getCurrentIdx() < 0 && StepDirection.PREV.equals(direction)) {
            step.setCurrentIdx(0);
        }
    }

    @Override
    protected boolean shouldCancelStep(Integer idc, SimpleHierarchicalStep<WizardHierarchicalStep> step, StepDirection direction) {
        return step.getCurrentIdx() < 0 && StepDirection.PREV.equals(direction);
    }

    @Override
    protected String getPrevText(int idx) {
        return "Bojovníci";
    }

    @Override
    protected String getNextText(int idx) {
        return "Spustit bitvu";
    }
}
