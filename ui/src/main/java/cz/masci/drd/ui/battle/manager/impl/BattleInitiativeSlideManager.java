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
import cz.masci.drd.ui.battle.slide.impl.BattleInitiativeSlideController;
import javafx.beans.binding.Bindings;
import net.rgielen.fxweaver.core.FxWeaver;

public class BattleInitiativeSlideManager extends BaseBattleSlideManager<BattleInitiativeSlideController> {

  private final boolean firstItem;
  private final boolean lastItem;
  private final GroupDTO group;

  public BattleInitiativeSlideManager(FxWeaver fxWeaver, GroupDTO group, boolean firstItem, boolean lastItem) {
    super(fxWeaver, BattleInitiativeSlideController.class);
    this.firstItem = firstItem;
    this.lastItem = lastItem;
    this.group = group;
  }

  @Override
  public void doBeforeSlide() {
    super.doBeforeSlide();
    group.setInitiative(controller.getInitiative());
  }

  @Override
  public void initProperties(BattleSlidePropertiesDTO properties) {
    properties.getPrevDisableProperty().set(false);
    properties.getPrevTextProperty().set(firstItem ? "Výběr akcí" : "Předchozí");
    properties.getNextDisableProperty().bind(Bindings.isNull(controller.initiativeProperty()));
    properties.getNextTextProperty().set(lastItem ? "Spustit bitvu" : "Další");
    properties.getTitleProperty().set("Iniciativa skupiny " + group.getName());
  }
}
