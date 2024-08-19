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
import cz.masci.drd.ui.app.battle.wizard.view.BattleRoundSummaryViewBuilder;
import cz.masci.drd.ui.util.wizard.controller.step.impl.TitleLeafStep;
import javafx.scene.layout.Region;

public class BattleRoundSummaryStep extends TitleLeafStep {

  private final BattleRoundSummaryViewBuilder builder;

  public BattleRoundSummaryStep(BattleInteractor interactor) {
    super("Výsledek kola");

    builder = new BattleRoundSummaryViewBuilder(interactor.getRoundHistory());
  }

  @Override
  public Region view() {
    return builder.build();
  }
}
