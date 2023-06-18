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

package cz.masci.drd.ui.battle.action;

import cz.masci.drd.dto.DuellistDTO;
import cz.masci.drd.dto.actions.Action;
import java.util.List;
import javafx.scene.Node;

public interface SelectActionControl {

  /**
   * Return control view
   *
   * @return Control view
   */
  Node getView();

  void initAction(DuellistDTO actor, List<DuellistDTO> duellists);

  Action getAction();

  String getName();
}
