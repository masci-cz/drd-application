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
import org.springframework.util.StringUtils;

public class OtherAction extends SingleActorAction {

  private final String other;

  public OtherAction(DuellistDTO actor, String other) {
    super(actor);
    this.other = other;
  }

  @Override
  public boolean isPrepared() {
    return StringUtils.hasLength(other);
  }

  @Override
  public void execute() {
    result = String.format("Bojovník %s provádí %s", actor.getName(), other);
  }

  @Override
  public ActionType getActionType() {
    return ActionType.OTHER;
  }
}
