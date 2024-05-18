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

import cz.masci.drd.dto.GroupDTO;
import cz.masci.drd.ui.util.wizard.controller.step.impl.TitleLeafStep;
import cz.masci.drd.ui.util.wizard.view.TestBattleStepViewBuilder;
import javafx.scene.layout.Region;

public class BattleInitiativeLeafStep extends TitleLeafStep {

  private final TestBattleStepViewBuilder builder;
  private final GroupDTO group;

  public BattleInitiativeLeafStep(GroupDTO group) {
    super("Iniciativa skupiny - " + group.getName());

    this.group = group;
    builder = new TestBattleStepViewBuilder("Iniciativa skupiny - " + group.getName());
  }

  @Override
  public void completeStep() {
    group.setInitiative(4);
    super.completeStep();
  }

  @Override
  public Region view() {
    return builder.build();
  }
}
