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

package cz.masci.drd.dto;

import cz.masci.drd.dto.actions.Action;
import cz.masci.drd.dto.actions.ActionResult;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class DuellistDTO {
  /**
   * Name of the duellist
   */
  String name;
  /**
   * Selected action for next round
   */
  Action<? extends ActionResult> selectedAction;
  List<WeaponDTO> weapons = new ArrayList<>();
  /**
   * Original live count
   */
  int originalLive;
  /**
   * Current live count
   */
  int currentLive;
  /**
   * Defense number
   */
  int defense;
  /**
   * Temporal attribute for offense number
   */
  int attack;
  /**
   * Temporal attribute for damage bonus
   */
  int damage;
}
