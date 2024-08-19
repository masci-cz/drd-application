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
import cz.masci.drd.ui.app.battle.wizard.view.BattleInitiativeViewBuilder;
import cz.masci.drd.ui.util.wizard.controller.step.impl.TitleLeafStep;
import cz.masci.springfx.mvci.util.constraint.ConditionUtils;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Region;

public class BattleInitiativeChildStep extends TitleLeafStep {

  private final BattleInitiativeViewBuilder builder;
  private final GroupDTO group;
  private final StringProperty initiative = new SimpleStringProperty();

  public BattleInitiativeChildStep(GroupDTO group) {
    super("Iniciativa skupiny - " + group.getName());

    this.group = group;
    builder = new BattleInitiativeViewBuilder(initiative);
  }

  @Override
  public BooleanExpression valid() {
    return ConditionUtils.isNumber(initiative);
  }

  @Override
  public void completeStep() {
    if (isValid()) {
      var initiativeInt = Integer.parseInt(initiative.get());
      group.setInitiative(initiativeInt);
    }
    super.completeStep();
  }

  @Override
  public Region view() {
    return builder.build();
  }
}
