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

import cz.masci.drd.ui.app.battle.wizard.model.SelectActionCloseCombatModel;
import cz.masci.drd.ui.app.battle.wizard.model.SelectActionModel;
import cz.masci.drd.ui.app.battle.wizard.view.BattleSelectActionViewBuilder;
import cz.masci.drd.ui.app.battle.wizard.view.SelectActionCloseCombatViewBuilder;
import cz.masci.drd.ui.util.wizard.controller.step.impl.TitleLeafStep;
import java.util.List;
import javafx.scene.layout.Region;

public class BattleSelectActionChildStep extends TitleLeafStep {

  private final BattleSelectActionViewBuilder viewBuilder;

  public BattleSelectActionChildStep(String duellistName, List<String> actions, List<String> duellists) {
    super("Vyberte akci pro bojovníka - " + duellistName);

    var viewModel = new SelectActionCloseCombatModel(duellistName, duellists);
    viewBuilder = new BattleSelectActionViewBuilder(actions.stream()
                                                           .map(name -> new SelectActionModel(name, new SelectActionCloseCombatViewBuilder(viewModel).build()))
                                                           .toList());
  }

  @Override
  public Region view() {
    return viewBuilder.build();
  }
}
