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
import cz.masci.drd.dto.actions.Action;
import cz.masci.drd.dto.actions.ShootAction;
import cz.masci.drd.ui.app.battle.action.controller.ShootSelectActionController;
import cz.masci.drd.ui.util.PredicateUtils;
import java.util.function.Predicate;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ShootSelectAction extends MultipleDuellistSelectAction<ShootSelectActionController> {

  public ShootSelectAction(FxWeaver fxWeaver) {
    super(fxWeaver, ShootSelectActionController.class);
  }

  @Override
  protected Predicate<DuellistDTO> getPredicate(DuellistDTO actor) {
    return PredicateUtils.compose(DuellistDTO::getName, actor.getName()::equals).negate();
  }

  @Override
  public String getName() {
    return "Útok na dálku";
  }

  @Override
  public Action getAction() {
    return new ShootAction(actor, controller.getDuellistBox().getValue());
  }
}
