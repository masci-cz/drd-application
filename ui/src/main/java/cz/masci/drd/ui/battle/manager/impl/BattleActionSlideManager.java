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
import cz.masci.drd.ui.battle.manager.dto.BattleSlidePropertiesDTO;
import cz.masci.drd.ui.battle.slide.impl.BattleActionSlideController;
import net.rgielen.fxweaver.core.FxWeaver;

public class BattleActionSlideManager extends BaseBattleSlideManager<BattleActionSlideController> {

  private final boolean firstItem;
  private final boolean lastItem;
  private final String groupName;

  public BattleActionSlideManager(FxWeaver fxWeaver, GroupDTO group, DuellistDTO duellist, boolean firstItem, boolean lastItem) {
    super(fxWeaver, BattleActionSlideController.class);
    this.firstItem = firstItem;
    this.lastItem = lastItem;
    this.groupName = group.getName();
  }

  @Override
  public void initProperties(BattleSlidePropertiesDTO properties) {
    properties.getPrevDisableProperty().set(false);
    properties.getPrevTextProperty().set(firstItem ? "Bojovnci" : "Předchozí");
    if (lastItem) {
      properties.getNextDisableProperty().unbind();
      properties.getNextDisableProperty().set(true);
    } else {
      properties.getNextDisableProperty().bind(controller.selectedAction().isNotNull());
    }

    properties.getNextTextProperty().set(lastItem ? "Iniciativa" : "Další");
    properties.getTitleProperty().set("Bojovníci skupiny " + groupName);
  }
}
