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

package cz.masci.drd.ui.battle.action.impl;

import cz.masci.drd.dto.DuellistDTO;
import cz.masci.drd.dto.GroupDTO;
import cz.masci.drd.dto.actions.Action;
import cz.masci.drd.dto.actions.ActionResult;
import cz.masci.drd.service.BattleService;
import cz.masci.drd.ui.battle.action.SelectActionControl;
import java.util.List;
import java.util.function.Predicate;
import javafx.scene.Node;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;

@Component
public class CloseCombatSelectActionControl implements SelectActionControl {

  @Getter
  private final Node view;

  private final CloseCombatSelectActionController controller;

  public CloseCombatSelectActionControl(FxWeaver fxWeaver) {
    var fxControllerAndView = fxWeaver.load(CloseCombatSelectActionController.class);
    controller = fxControllerAndView.getController();
    view = fxControllerAndView.getView().orElseThrow();
  }

  @Override
  public void initAction(BattleService battleService, DuellistDTO duellist) {
    var duellistList = battleService.getGroups().stream()
        .flatMap(group -> group.getDuellists().stream())
        .filter(Predicate.not(duellist::equals))
        .toList();
    controller.initDuellists(duellistList);
  }

  @Override
  public String getName() {
    return "Útok z blízka";
  }

  @Override
  public Action getAction() {
    return new Action() {
      @Override
      public boolean isPrepared() {
        return false;
      }

      @Override
      public ActionResult execute() {
        return null;
      }

      @Override
      public int order() {
        return 0;
      }

    };
  }
}
