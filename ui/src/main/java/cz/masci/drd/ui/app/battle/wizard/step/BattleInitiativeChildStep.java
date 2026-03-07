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

import cz.masci.drd.dto.GroupDTO;
import cz.masci.drd.ui.app.battle.wizard.view.BattleInitiativeViewBuilder;
import cz.masci.drd.ui.util.wizard.model.WizardLeafStep;
import cz.masci.springfx.mvci.util.constraint.ConditionUtils;
import cz.masci.wizard.simple.SimpleLeafStep;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BattleInitiativeChildStep extends WizardLeafStep {

  private final GroupDTO group;
  private final StringProperty initiative = new SimpleStringProperty();

  public BattleInitiativeChildStep(GroupDTO group) {
    super("Iniciativa skupiny - " + group.getName());

    this.group = group;
    super.setBuilder(new BattleInitiativeViewBuilder(initiative));
  }

  @Override
  public BooleanExpression valid() {
    return ConditionUtils.isNumber(initiative);
  }

  @Override
  protected void complete(SimpleLeafStep<WizardLeafStep> wizardLeafStepV3SimpleLeafStep) {
    var initiativeInt = Integer.parseInt(initiative.get());
    group.setInitiative(initiativeInt);
  }
}
