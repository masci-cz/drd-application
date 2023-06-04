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

package cz.masci.drd.ui.battle.service.impl;

import cz.masci.drd.ui.battle.service.dto.BattleSlidePropertiesDTO;
import cz.masci.drd.ui.battle.slide.impl.BattleGroupSlideController;
import javafx.beans.binding.Bindings;
import net.rgielen.fxweaver.core.FxWeaver;

public class BattleGroupSlide extends BaseBattleSlide<BattleGroupSlideController> {

  public BattleGroupSlide(FxWeaver fxWeaver) {
    super(fxWeaver, BattleGroupSlideController.class);
  }

  @Override
  public void initProperties(BattleSlidePropertiesDTO properties) {
    properties.getPrevDisableProperty().set(true);
    properties.getPrevTextProperty().set(null);
    properties.getNextDisableProperty().bind((Bindings.size(controller.getGroups()).lessThan(2)));
    properties.getNextTextProperty().set("Bojovníci");
    properties.getTitleProperty().set("Skupiny");
  }


}