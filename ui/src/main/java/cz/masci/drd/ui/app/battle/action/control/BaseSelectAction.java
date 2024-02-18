/*
 * Copyright (c) 2023-2024
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

package cz.masci.drd.ui.app.battle.action.control;

import cz.masci.drd.dto.DuellistDTO;
import cz.masci.drd.ui.app.battle.action.SelectAction;
import java.util.List;
import javafx.scene.Node;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseSelectAction implements SelectAction {

  @Getter
  protected Node view;

  protected DuellistDTO actor;

  public BaseSelectAction() {

  }

  public BaseSelectAction(Node view) {
    this.view = view;
  }

  @Override
  public void initAction(DuellistDTO actor, List<DuellistDTO> duellists) {
    log.trace("Init select action [{}]", this);
    this.actor = actor;
  }
}
