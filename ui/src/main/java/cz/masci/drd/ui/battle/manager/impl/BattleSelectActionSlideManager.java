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

package cz.masci.drd.ui.battle.manager.impl;

import cz.masci.drd.dto.DuellistDTO;
import cz.masci.drd.dto.GroupDTO;
import cz.masci.drd.service.BattleService;
import cz.masci.drd.ui.battle.action.ActionService;
import cz.masci.drd.ui.battle.manager.dto.BattleSlidePropertiesDTO;
import cz.masci.drd.ui.battle.slide.impl.BattleSelectActionSlideController;
import net.rgielen.fxweaver.core.FxWeaver;

public class BattleSelectActionSlideManager extends BaseBattleSlideManager<BattleSelectActionSlideController> {

  private final boolean firstItem;
  private final boolean lastItem;
  private final String groupName;
  private final DuellistDTO duellist;

  public BattleSelectActionSlideManager(FxWeaver fxWeaver, ActionService actionService, BattleService battleService, GroupDTO group, DuellistDTO duellist, boolean firstItem, boolean lastItem) {
    super(fxWeaver, BattleSelectActionSlideController.class);
    this.firstItem = firstItem;
    this.lastItem = lastItem;
    this.groupName = group.getName();
    this.duellist = duellist;
    var actionControls = actionService.getActions().stream().peek(action -> action.initAction(duellist, battleService.getAllDuellists())).toList();
    controller.initActions(actionControls);
  }

  @Override
  public void initProperties(BattleSlidePropertiesDTO properties) {
    properties.getPrevDisableProperty().set(false);
    properties.getPrevTextProperty().set(firstItem ? "Bojovníci" : "Předchozí");
    if (lastItem) {
      properties.getNextDisableProperty().unbind();
      properties.getNextDisableProperty().set(true);
    } else {
      properties.getNextDisableProperty().bind(controller.selectedAction().isNull());
    }

    properties.getNextTextProperty().set(lastItem ? "Iniciativa" : "Další");
    properties.getTitleProperty().setValue(String.format("<html>Vyberte akci pro bojovníka <b>%s</b> ze skupiny <b>%s</b></html>", duellist.getName(), groupName));
  }

  @Override
  public void doBeforeSlide() {
    super.doBeforeSlide();
    duellist.setSelectedAction(controller.selectedAction().get().getAction());
  }
}
