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
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class DuellistDTO {
  private String groupName;
  /**
   * Name of the duellist
   */
  private String name;
  /**
   * Selected action for next round
   */
  private Action<?> selectedAction;
  private List<WeaponDTO> weapons = new ArrayList<>();
  /**
   * Original live count
   */
  private int originalLive;
  /**
   * Current live count
   */
  private int currentLive;
  /**
   * Defense number
   */
  private int defense;
  /**
   * Temporal attribute for offense number
   */
  private int attack;
  /**
   * Temporal attribute for damage bonus
   */
  private int damage;
}
