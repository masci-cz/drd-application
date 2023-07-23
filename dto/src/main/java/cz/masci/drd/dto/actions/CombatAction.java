/*
 * Copyright (c) 2023
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

package cz.masci.drd.dto.actions;

import cz.masci.drd.dto.DuellistDTO;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Data
public class CombatAction implements Action<CombatActionResult> {

  private final DuellistDTO attacker;
  private final DuellistDTO defender;

  private Integer attackerDiceRoll;
  private Integer defenderDiceRoll;

  private CombatActionResult combatActionResult;

  @Override
  public boolean isPrepared() {
    return
        Objects.nonNull(attackerDiceRoll)
        &&
        Objects.nonNull(defenderDiceRoll);
  }

  @Override
  public CombatActionResult getResult() {
    return combatActionResult;
  }

  @Override
  public void execute() {
    if (!isPrepared()) {
      throw new RuntimeException("Action is not prepared");
    }

    int attack = attacker.getAttack() + attackerDiceRoll;
    int defense = defender.getDefense() + defenderDiceRoll;
    boolean success = attack > defense;
    int result = attack - defense + attacker.getDamage();
    Integer life = success ? (result <= 0 ? 1 : attack) : null;
    combatActionResult = new CombatActionResult(attack, defense, success, life);
    //    return () -> {
//      StringBuilder result = new StringBuilder();
//      result.append(attacker.getName());
//      result.append(" útočí útočným číslem [");
//      result.append(attackerDiceRoll);
//      result.append("] na ");
//      result.append(defender.getName());
//      result.append(", který se brání obranným číslem [");
//      result.append(defenderDiceRoll);
//      result.append("].\n");
//      if (attackerDiceRoll.compareTo(defenderDiceRoll) > 0) {
//        result.append("Útočník zranil obránce za [");
//        result.append(attackerDiceRoll - defenderDiceRoll + attacker.getDamage()); // TODO at least one life should be taken
//        result.append("] životů,");
//      } else {
//        result.append("Obránce se útoku ubránil a nebyl zraněn.");
//      }
//
//      return result.toString();
//    };
  }

  @Override
  public int order() {
    return 3;
  }
}
