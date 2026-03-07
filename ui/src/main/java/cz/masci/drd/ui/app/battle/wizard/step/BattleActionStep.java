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

import cz.masci.drd.dto.actions.*;
import cz.masci.drd.ui.app.battle.wizard.interactor.BattleInteractor;
import cz.masci.drd.ui.app.battle.wizard.model.action.CombatActionModel;
import cz.masci.drd.ui.app.battle.wizard.model.action.MagicActionModel;
import cz.masci.drd.ui.app.battle.wizard.model.action.SimpleActionModel;
import cz.masci.drd.ui.app.battle.wizard.view.action.CloseCombatActionViewBuilder;
import cz.masci.drd.ui.app.battle.wizard.view.action.MagicActionViewBuilder;
import cz.masci.drd.ui.app.battle.wizard.view.action.ShootActionViewBuilder;
import cz.masci.drd.ui.app.battle.wizard.view.action.SimpleActionViewBuilder;
import cz.masci.drd.ui.util.wizard.model.StepDirection;
import cz.masci.drd.ui.util.wizard.model.WizardHierarchicalStep;
import cz.masci.wizard.api.step.LeafStep;
import cz.masci.wizard.api.step.Step;
import cz.masci.wizard.simple.SimpleHierarchicalStep;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BattleActionStep extends WizardHierarchicalStep {

    private final BattleInteractor interactor;

    @Override
    protected void initStep(SimpleHierarchicalStep<WizardHierarchicalStep> step) {
        interactor.startRound();
        prepareSteps(step);
    }

    @Override
    protected void updateStatus(SimpleHierarchicalStep<WizardHierarchicalStep> step) {
        if (step.getCurrentIdx() == step.getChildrenCount()) {
            interactor.endRound();
        }
    }

    @Override
    protected boolean skipStep(Integer idx, Step step) {
        if (step instanceof LeafStep<?> leafStep) {
            if (leafStep.getValue() instanceof BattleActionChildStep<?> action) {
                return !action.isActorAlive();
            }
        }
        return false;
    }

    @Override
    protected void checkStep(SimpleHierarchicalStep<WizardHierarchicalStep> step, StepDirection direction) {
        if (direction == StepDirection.PREV) {
            interactor.cancelRound();
            // reset because we want to let parent step to react on prev step
            step.reset();
        }
    }

    @Override
    protected String getPrevText(int idx) {
        return "Zrušit kolo";
    }

    @Override
    protected String getNextText(int idx) {
        return "Další";
    }

    private void prepareSteps(SimpleHierarchicalStep<WizardHierarchicalStep> parentStep) {
        parentStep.clearChildren();
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
            parentStep.addChild(step.getStep());
        }
    }

}
