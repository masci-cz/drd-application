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

/**
 * Base action
 */
public interface Action<T extends ActionResult> extends Comparable<Action<T>> {

  boolean isPrepared();

  /**
   * Returns null if the action was not executed yet. Otherwise, returns executed result.
   *
   * @return Null or executed result.
   */
  T getResult();
  void execute();
  int order();

  default int compareTo(Action o) {
    return Integer.compare(order(), o.order());
  }
}
