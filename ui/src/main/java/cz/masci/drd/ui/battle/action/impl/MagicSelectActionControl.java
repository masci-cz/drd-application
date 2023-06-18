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
import cz.masci.drd.dto.actions.Action;
import cz.masci.drd.dto.actions.MagicAction;
import cz.masci.drd.ui.battle.action.SelectActionControl;
import java.util.List;
import javafx.scene.Node;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class MagicSelectActionControl implements SelectActionControl {

  @Getter
  private final Node view;
  private final MagicSelectActionController controller;

  private DuellistDTO actor;

  public MagicSelectActionControl(FxWeaver fxWeaver) {
    var fxControllerAndView = fxWeaver.load(MagicSelectActionController.class);
    controller = fxControllerAndView.getController();
    view = fxControllerAndView.getView().orElseThrow();
  }

  @Override
  public void initAction(DuellistDTO actor, List<DuellistDTO> duellists) {
    controller.initDuellists(duellists);
    this.actor = actor;
  }

  @Override
  public String getName() {
    return "Kouzlení";
  }

  @Override
  public Action getAction() {
    return new MagicAction(actor, controller.getDuellistBox().getValue(), controller.getSpellTxt().getText());
  }
}
