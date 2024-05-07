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

package cz.masci.drd.ui.app.battle.wizard.controller;

import cz.masci.drd.ui.app.battle.wizard.interactor.BattleInteractor;
import cz.masci.drd.ui.util.wizard.controller.step.impl.SimpleCompositeStep;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.springframework.stereotype.Component;

@Component
public class BattleRootStepController extends SimpleCompositeStep {

  private final BooleanProperty prevDisabled = new SimpleBooleanProperty(false);
  private final BooleanProperty nextDisabled = new SimpleBooleanProperty(false);

  public BattleRootStepController(BattleInteractor interactor) {
    addStep(new BattleGroupStepController(interactor));
    addStep(new BattleDuellistStepController(interactor));
    addStep(new BattleSelectActionStepController());
    addStep(new BattleInitiativeStepController());
  }

  @Override
  protected String getPrevText() {
    return switch (getCurrentIdx()) {
      case 1 -> "Zrušit bitvu";
      case 2 -> "Bojovníci";
      case 3 -> "Výběr akcí";
      default -> "Předchozí";
    };
  }

  @Override
  protected String getNextText() {
    return switch (getCurrentIdx()) {
      case 0 -> "Bojovníci";
      case 1 -> "Výběr akcí";
      case 2 -> "Iniciativa";
      case 3 -> "Spustit bitvu";
      default -> "Další";
    };
  }

  @Override
  public BooleanExpression prevDisabled() {
    prevDisabled.set(!hasPrev());
    return prevDisabled;
  }

  @Override
  public BooleanExpression nextDisabled() {
    nextDisabled.set(!hasNext());
    return nextDisabled;
  }
}
