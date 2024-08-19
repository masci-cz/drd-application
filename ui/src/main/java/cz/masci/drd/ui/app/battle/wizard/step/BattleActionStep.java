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

import cz.masci.drd.dto.actions.CombatAction;
import cz.masci.drd.dto.actions.MagicAction;
import cz.masci.drd.dto.actions.OtherAction;
import cz.masci.drd.dto.actions.PrepareAction;
import cz.masci.drd.dto.actions.ShootAction;
import cz.masci.drd.dto.actions.SpeechAction;
import cz.masci.drd.dto.actions.WaitAction;
import cz.masci.drd.ui.app.battle.wizard.interactor.BattleInteractor;
import cz.masci.drd.ui.app.battle.wizard.model.action.CombatActionModel;
import cz.masci.drd.ui.app.battle.wizard.model.action.MagicActionModel;
import cz.masci.drd.ui.app.battle.wizard.model.action.SimpleActionModel;
import cz.masci.drd.ui.app.battle.wizard.view.action.CloseCombatActionViewBuilder;
import cz.masci.drd.ui.app.battle.wizard.view.action.MagicActionViewBuilder;
import cz.masci.drd.ui.app.battle.wizard.view.action.ShootActionViewBuilder;
import cz.masci.drd.ui.app.battle.wizard.view.action.SimpleActionViewBuilder;
import cz.masci.drd.ui.util.wizard.controller.step.HierarchicalStep;
import cz.masci.drd.ui.util.wizard.controller.step.impl.SimpleCompositeStep;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BattleActionStep extends SimpleCompositeStep {

  private final BattleInteractor interactor;

  @Override
  public HierarchicalStep next() {
    if (getCurrentIdx() < 0) {
      interactor.startRound();
      prepareSteps();
    }
    if (getCurrentIdx() == steps.size()) {
      interactor.endRound();
    }
    if (isValidIndex(getCurrentIdx())) {
      var newStep = steps.get(getCurrentIdx());
      if (newStep instanceof BattleActionChildStep<?> actionStep) {
        if (!actionStep.isActorAlive()) {
          removeStep(getCurrentIdx());
        }
      }
    }
    // It should be ok to check current step that
    return super.next();
  }

  @Override
  public HierarchicalStep prev() {
    interactor.cancelRound();
    reset();
    return super.prev();
  }

  @Override
  protected String getPrevText() {
    return "Zrušit kolo";
  }

  @Override
  protected String getNextText() {
    return "Další";
  }

  private void prepareSteps() {
    clearSteps();
    while (interactor.hasAction()) {
      var action = interactor.pollAction();
      var step = switch (action) {
        case CombatAction combatAction -> {
          var combatActionModel = new CombatActionModel(combatAction.getAttacker(), combatAction.getDefender());
          var builder = new CloseCombatActionViewBuilder(combatActionModel);
          yield new BattleActionChildStep<>(combatActionModel, builder, interactor);
        }
        case ShootAction shootAction -> {
          var shootActionModel = new CombatActionModel(shootAction.getAttacker(), shootAction.getDefender());
          var builder = new ShootActionViewBuilder(shootActionModel);
          yield new BattleActionChildStep<>(shootActionModel, builder, interactor);
        }
        case MagicAction magicAction -> {
          var magicActionModel = new MagicActionModel(magicAction.getAttacker(), magicAction.getDefender(), magicAction.getSpell());
          var builder = new MagicActionViewBuilder(magicActionModel);
          yield new BattleActionChildStep<>(magicActionModel, builder, interactor);
        }
        case OtherAction simpleAction -> {
          var simpleActionModel = new SimpleActionModel(simpleAction.getActor(), String.format("provádí akci %s", simpleAction.getOther()));
          var builder = new SimpleActionViewBuilder(simpleActionModel);
          yield new BattleActionChildStep<>(simpleActionModel, builder, interactor);
        }
        case PrepareAction simpleAction -> {
          var simpleActionModel = new SimpleActionModel(simpleAction.getActor(), "se připravuje");
          var builder = new SimpleActionViewBuilder(simpleActionModel);
          yield new BattleActionChildStep<>(simpleActionModel, builder, interactor);
        }
        case SpeechAction simpleAction -> {
          var simpleActionModel = new SimpleActionModel(simpleAction.getActor(), "mluví");
          var builder = new SimpleActionViewBuilder(simpleActionModel);
          yield new BattleActionChildStep<>(simpleActionModel, builder, interactor);
        }
        case WaitAction simpleAction -> {
          var simpleActionModel = new SimpleActionModel(simpleAction.getActor(), "vyčkává");
          var builder = new SimpleActionViewBuilder(simpleActionModel);
          yield new BattleActionChildStep<>(simpleActionModel, builder, interactor);
        }
        default -> throw new IllegalStateException("Unexpected value: " + action);
      };
      addStep(step);
    }
  }
  
}
