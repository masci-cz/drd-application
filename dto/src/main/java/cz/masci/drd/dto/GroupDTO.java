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

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GroupDTO implements Comparable<GroupDTO> {
  /**
   * Unique group name
   */
  private final String name;
  /**
   * list of duellists in this group
   */
  private final List<DuellistDTO> duellists = new ArrayList<>();
  /**
   * Group initiative for next round
   */
  private Integer initiative;

  @Override
  public int compareTo(GroupDTO o) {
    return initiative.compareTo(o.getInitiative());
  }
}
