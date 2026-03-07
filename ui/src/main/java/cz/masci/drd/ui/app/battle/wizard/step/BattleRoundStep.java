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
import cz.masci.drd.ui.util.wizard.model.WizardStepProvider;
import cz.masci.wizard.simple.SimpleHierarchicalStep;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BattleRoundStep extends WizardHierarchicalStep {

    private final BattleInteractor interactor;

    @Override
    protected List<WizardStepProvider> getChildren() {
        return List.of(
                new BattlePreparationStep(interactor),
                new BattleSelectActionStep(interactor),
                new BattleInitiativeStep(interactor),
                new BattleActionStep(interactor),
                new BattleRoundSummaryStep(interactor)
        );
    }

    @Override
    protected void checkStep(SimpleHierarchicalStep<WizardHierarchicalStep> step, StepDirection direction) {
        if (step.getCurrentIdx() == 4 || step.getCurrentIdx() == 3 && direction == StepDirection.PREV) {
            step.rewind();
        }
    }

    @Override
    protected boolean shouldCancelStep(Integer idc, SimpleHierarchicalStep<WizardHierarchicalStep> step, StepDirection direction) {
        return step.getCurrentIdx() == 4 || step.getCurrentIdx() == 3 && direction == StepDirection.PREV;
    }

    @Override
    protected String getPrevText(int idx) {
        return switch (idx) {
            case 1 -> "Přehled";
            case 2 -> "Výběr akcí";
            case 3 -> "Zrušit kolo";
            case 4 -> "Další kolo";
            default -> "Předchozí";
        };
    }

    @Override
    protected String getNextText(int idx) {
        return switch (idx) {
            case 0 -> "Výběr akcí";
            case 1 -> "Iniciativa";
            case 2 -> "Spustit bitvu";
            case 3 -> "Výsledek kola";
            default -> "Další";
        };
    }

}
