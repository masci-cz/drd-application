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

import cz.masci.drd.dto.GroupDTO;
import cz.masci.drd.ui.battle.manager.dto.BattleSlidePropertiesDTO;
import cz.masci.drd.ui.battle.slide.impl.BattleDuellistSlideController;
import javafx.beans.binding.Bindings;
import net.rgielen.fxweaver.core.FxWeaver;

public class BattleDuellistSlideManager extends BaseBattleSlideManager<BattleDuellistSlideController> {

  private final boolean firstItem;
  private final boolean lastItem;

  public BattleDuellistSlideManager(FxWeaver fxWeaver, GroupDTO group, boolean firstItem, boolean lastItem) {
    super(fxWeaver, BattleDuellistSlideController.class);
    this.firstItem = firstItem;
    this.lastItem = lastItem;
    controller.setGroup(group);
  }

  @Override
  public void initProperties(BattleSlidePropertiesDTO properties) {
    properties.getPrevDisableProperty().set(false);
    properties.getPrevTextProperty().set(firstItem ? "Zrušit bitvu" : "Předchozí");
    properties.getNextDisableProperty().bind(Bindings.size(controller.getDuellists()).lessThan(1));
    properties.getNextTextProperty().set(lastItem ? "Spustit bitvu" : "Další");
    properties.getTitleProperty().set("Bojovníci skupiny " + controller.getGroup().getName());
  }
}
