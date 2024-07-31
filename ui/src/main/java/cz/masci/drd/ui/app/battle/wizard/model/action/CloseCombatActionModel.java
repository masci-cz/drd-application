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
import cz.masci.drd.ui.util.expression.ParseStringAddBaseIntegerBinding;
import cz.masci.springfx.mvci.util.constraint.ConditionUtils;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.IntegerExpression;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;

public class CloseCombatActionModel implements BattleActionModel {
  private final StringProperty rollAttack = new SimpleStringProperty();
  private final StringProperty rollDefense = new SimpleStringProperty();

  private final BooleanExpression valid;
  private final BooleanExpression success;
  private final IntegerExpression life;
  private int lifeResult;
  private boolean isCancelled;

  private final IntegerExpression attack;
  private final IntegerExpression defense;

  private final DuellistDTO attacker;
  private final DuellistDTO defender;

  public CloseCombatActionModel(DuellistDTO attacker, DuellistDTO defender) {
    this.attacker = attacker;
    this.defender = defender;

    // A = base attack + roll attack
    attack = new ParseStringAddBaseIntegerBinding(attacker.getAttack(), rollAttack);
    // B = base defense + roll defense
    defense = new ParseStringAddBaseIntegerBinding(defender.getDefense(), rollDefense);
    // diff = A - B
    NumberBinding diff = attack.subtract(defense);
    // roll attack and roll defense are numbers
    valid = ConditionUtils.isNumber(rollAttack)
                          .and(ConditionUtils.isNumber(rollDefense));
    // is valid and diff > 0
    success = valid.and(diff.greaterThan(0));
    // is valid => diff + damage OR 1
    life = new IntegerBinding() {
      {
        super.bind(success, diff);
      }

      @Override
      protected int computeValue() {
        return success.get() ? computeDamage(diff.intValue(), attacker.getDamage()) : 0;
      }

      private int computeDamage(int diff, int damage) {
        int num = diff + damage;
        return num <= 0 ? 1 : num;
      }
    };
  }

  @Override
  public void execute() {
    if (isValid() && isSuccess() && !isCancelled) {
      defender.setCurrentLive(defender.getCurrentLive() - lifeResult);
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

  public void bindRollAttack(ObservableStringValue observable) {
    if (rollAttack.isBound()) {
      rollAttack.unbind();
    }
    rollAttack.bind(observable);
  }

  public void bindLife(StringProperty property) {
    // listen to changes on the observable
    property.addListener((obs, oldVal, newVal) -> {
      try {
        lifeResult = Integer.parseInt(newVal);
      } catch (NumberFormatException e) {
        lifeResult = 0;
      }
    });
    // bind observable to changes in computed life
    life.addListener((obs, oldVal, newVal) -> property.setValue(newVal.toString()));
  }

  public void bindRollDefense(ObservableStringValue observable) {
    if (rollDefense.isBound()) {
      rollDefense.unbind();
    }
    rollDefense.bind(observable);
  }

  public boolean isValid() {
    return valid.get();
  }

  public boolean isSuccess() {
    return success.get();
  }

  public BooleanExpression successProperty() {
    return success;
  }

  public String getAttackerName() {
    return attacker.getName();
  }

  public String getDefenderName() {
    return defender.getName();
  }

  public String getBaseAttack() {
    return String.valueOf(attacker.getAttack());
  }

  public String getBaseDefense() {
    return String.valueOf(defender.getDefense());
  }

  public String getAttackerLife() {
    return String.format("%d/%d", attacker.getCurrentLive(), attacker.getOriginalLive());
  }

  public String getDefenderLife() {
    return String.format("%d/%d", defender.getCurrentLive(), defender.getOriginalLive());
  }

  public StringExpression getAttackResult() {
    return attack.asString();
  }

  public StringExpression getDefenseResult() {
    return defense.asString();
  }
}
