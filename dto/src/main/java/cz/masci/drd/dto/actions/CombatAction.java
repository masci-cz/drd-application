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
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class CombatAction implements Action<CombatActionResult> {

  @Getter
  private final DuellistDTO attacker;
  @Getter
  private final DuellistDTO defender;

  private Integer attackerDiceRoll;
  private Integer defenderDiceRoll;

  private CombatActionResult result;

  @Override
  public boolean isPrepared() {
    return
        Objects.nonNull(attackerDiceRoll)
        &&
        Objects.nonNull(defenderDiceRoll);
  }

  @Override
  public void execute() {
    if (!isPrepared()) {
      throw new RuntimeException("Action is not prepared");
    }

    int attack = attacker.getAttack() + attackerDiceRoll;
    int defense = defender.getDefense() + defenderDiceRoll;
    boolean success = attack > defense;
    int attackResult = attack - defense + attacker.getDamage();
    Integer life = success ? (attackResult <= 0 ? 1 : attackResult) : null;
    result = new CombatActionResult(attack, defense, success, life);
  }

  @Override
  public ActionType getActionType() {
    return ActionType.CLOSE_COMBAT;
  }
}
