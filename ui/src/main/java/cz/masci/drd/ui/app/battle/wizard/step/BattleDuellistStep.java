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
import cz.masci.drd.ui.util.wizard.model.WizardHierarchicalStep;
import cz.masci.drd.ui.util.wizard.model.WizardStepProvider;
import cz.masci.wizard.simple.SimpleHierarchicalStep;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BattleDuellistStep extends WizardHierarchicalStep {

    private final BattleInteractor interactor;

    @Override
    protected void initStep(SimpleHierarchicalStep<WizardHierarchicalStep> step) {
        step.clearChildren();
        interactor.getGroupsNames()
                .map(name -> new BattleDuellistChildStep(interactor, name))
                .map(WizardStepProvider::getStep)
                .forEach(step::addChild);
    }

    @Override
    protected String getPrevText(int idx) {
        return "Předchozí";
    }

    @Override
    protected String getNextText(int idx) {
        return "Další";
    }

}
