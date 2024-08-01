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

package cz.masci.drd.ui.app.battle.wizard.model.action;

import cz.masci.drd.dto.DuellistDTO;
import cz.masci.springfx.mvci.util.constraint.ConditionUtils;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

public class MagicActionModel implements BattleActionModel {

  private final DuellistDTO attacker;
  private final DuellistDTO defender;
  @Getter
  private final String spell;

  private final BooleanExpression valid;
  private final StringProperty life;
  private boolean isCancelled;

  public MagicActionModel(DuellistDTO attacker, DuellistDTO defender, String spell) {
    this.attacker = attacker;
    this.defender = defender;
    this.spell = spell;

    life = new SimpleStringProperty();
    valid = ConditionUtils.isNumber(life);
  }

  @Override
  public void execute() {
    if (isValid() && !isCancelled) {
      try {
        var lifeResult = Integer.parseInt(life.getValue());
        defender.setCurrentLive(defender.getCurrentLive() - lifeResult);
      } catch (NumberFormatException e) {
        // do nothing
      }
    }
  }

  @Override
  public void cancel() {
    isCancelled = true;
  }

  @Override
  public BooleanExpression validProperty() {
    return valid;
  }

  public void bindLife(StringProperty property) {
    life.bind(property);
  }

  public boolean isValid() {
    return valid.get();
  }

  public String getAttackerName() {
    return attacker.getName();
  }

  public String getDefenderName() {
    return defender.getName();
  }

  public String getAttackerLife() {
    return String.format("%d/%d", attacker.getCurrentLive(), attacker.getOriginalLive());
  }

  public String getDefenderLife() {
    return String.format("%d/%d", defender.getCurrentLive(), defender.getOriginalLive());
  }
}
