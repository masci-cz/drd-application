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

package cz.masci.drd.ui.battle.action.control;

import cz.masci.drd.dto.DuellistDTO;
import cz.masci.drd.dto.actions.CombatAction;
import cz.masci.drd.ui.battle.action.controller.CloseCombatSelectActionController;
import cz.masci.drd.ui.util.PredicateUtils;
import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Slf4j
public class CloseCombatSelectAction extends MultipleDuellistSelectAction<CloseCombatSelectActionController> {

  public CloseCombatSelectAction(FxWeaver fxWeaver) {
    super(fxWeaver, CloseCombatSelectActionController.class);
  }

  @Override
  protected Predicate<DuellistDTO> getPredicate(DuellistDTO actor) {
    return PredicateUtils.compose(DuellistDTO::getName, actor.getName()::equals).negate();
  }

  @Override
  public String getName() {
    return "Útok na blízko";
  }

  @Override
  public CombatAction getAction() {
    return new CombatAction(actor, controller.getDuellistBox().getValue());
  }
}
