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

package cz.masci.drd.ui.app.battle.wizard.step;

import cz.masci.drd.ui.app.battle.wizard.interactor.BattleInteractor;
import cz.masci.drd.ui.app.battle.wizard.view.action.CloseCombatActionViewBuilder;
import cz.masci.drd.ui.util.wizard.controller.step.HierarchicalStep;
import cz.masci.drd.ui.util.wizard.controller.step.impl.SimpleCompositeStep;
import cz.masci.drd.ui.util.wizard.controller.step.impl.SimpleLeafStep;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BattleActionStep extends SimpleCompositeStep {

  private final BattleInteractor interactor;

  @Override
  public HierarchicalStep next() {
    // Initiate action steps same way as in cz.masci.drd.ui.app.battle.slide.presenter.impl.BattleActionSlide.getControllers
    if (getCurrentIdx() < 0) {
      addStep(new SimpleLeafStep("Test", new CloseCombatActionViewBuilder().build()));
//      interactor.startRound();
//      clearSteps();
//      while (interactor.hasAction()) {
//        var action = interactor.pollAction();
//        var step = new BattleActionChildStep();
//        addStep(step);
//      }
    }
    return super.next();
  }

  @Override
  protected String getPrevText() {
    return "Zrušit kolo";
  }

  @Override
  protected String getNextText() {
    return "Další";
  }
}
