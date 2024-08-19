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
import cz.masci.drd.ui.util.wizard.controller.step.HierarchicalStep;
import cz.masci.drd.ui.util.wizard.controller.step.impl.SimpleCompositeStep;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.springframework.stereotype.Component;

@Component
public class BattleRootStep extends SimpleCompositeStep {

  private final BooleanProperty prevDisabled = new SimpleBooleanProperty(false);
  private final BooleanProperty nextDisabled = new SimpleBooleanProperty(false);

  public BattleRootStep(BattleInteractor interactor) {
    addStep(new BattleGroupStep(interactor));
    addStep(new BattleDuellistStep(interactor));
    // TODO group all actions steps under BattleRoundStep
    addStep(new BattlePreparationStep(interactor));
    addStep(new BattleSelectActionStep(interactor));
    addStep(new BattleInitiativeStep(interactor));
    addStep(new BattleActionStep(interactor));
    addStep(new BattleRoundSummaryStep(interactor));
  }

  @Override
  protected String getPrevText() {
    return switch (getCurrentIdx()) {
      case 1 -> "Zrušit bitvu";
      case 2 -> "Bojovníci";
      case 3 -> "Přehled";
      case 4 -> "Výběr akcí";
      case 5 -> "Zrušit kolo";
      case 6 -> "Další kolo";
      default -> "Předchozí";
    };
  }

  @Override
  protected String getNextText() {
    return switch (getCurrentIdx()) {
      case 0 -> "Bojovníci";
      case 1 -> "Přehled";
      case 2 -> "Výběr akcí";
      case 3 -> "Iniciativa";
      case 4 -> "Spustit bitvu";
      case 5 -> "Výsledek kola";
      case 6 -> "Další kolo";
      default -> "Další";
    };
  }

  @Override
  public String nextText() {
    return getNextText();
  }

  @Override
  public BooleanExpression prevDisabled() {
    prevDisabled.set(!hasPrev());
    return prevDisabled;
  }

  @Override
  public BooleanExpression nextDisabled() {
    nextDisabled.set(!hasNext() && getCurrentIdx() != 6);
    return nextDisabled;
  }

  @Override
  public HierarchicalStep prev() {
    if (getCurrentIdx() == 5 || getCurrentIdx() == 6) {
      return goToStep(2);
    }
    return super.prev();
  }

  @Override
  public HierarchicalStep next() {
    if (getCurrentIdx() == 7) {
      return goToStep(2);
    }
    return super.next();
  }
}
