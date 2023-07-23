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
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MagicAction implements Action<ActionResult> {

  private final DuellistDTO attacker;
  private final DuellistDTO defender;
  private final String spell;

  @Getter
  private ActionResult result;

  @Override
  public boolean isPrepared() {
    return true;
  }

  @Override
  public void execute() {
    result = () -> {
      StringBuilder result = new StringBuilder();
      result.append(attacker.getName());
      result.append(" sesílá kouzlo [");
      result.append(spell);
      result.append("] na ");
      result.append(defender.getName());

      return result.toString();
    };
  }

  @Override
  public int order() {
    return 1;
  }
}
