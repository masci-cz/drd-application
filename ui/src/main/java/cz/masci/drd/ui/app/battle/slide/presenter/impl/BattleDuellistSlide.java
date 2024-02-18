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

package cz.masci.drd.ui.app.battle.slide.presenter.impl;

import cz.masci.drd.service.BattleService;
import cz.masci.drd.ui.app.battle.slide.controller.impl.BattleDuellistSlideController;
import cz.masci.drd.ui.app.battle.dto.BattleSlidePropertiesDTO;
import java.util.List;
import javafx.beans.binding.Bindings;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;

@Component
public class BattleDuellistSlide extends BattleSlideMultipleControllers<BattleDuellistSlideController> {

  public BattleDuellistSlide(FxWeaver fxWeaver, BattleService battleService) {
    super(fxWeaver, battleService);
  }

  @Override
  public void initProperties(BattleSlidePropertiesDTO properties) {
    properties.getPrevDisableProperty().set(false);
    properties.getPrevTextProperty().set(hasPrevious() ? "Předchozí" : "Zrušit bitvu");
    properties.getNextDisableProperty().bind(Bindings.size(getController().getDuellists()).lessThan(1));
    properties.getNextTextProperty().set(hasNext() ? "Další" : "Výběr akcí");
    properties.getTitleProperty().set("Bojovníci skupiny " + getController().getGroup().getName());
  }

  @Override
  protected List<BattleDuellistSlideController> getControllers() {
    return battleService.getGroups().stream()
        .map(group -> {
          var controller = fxWeaver.loadController(BattleDuellistSlideController.class);
          controller.setGroup(group);
          return controller;
        })
        .toList();
  }

}
