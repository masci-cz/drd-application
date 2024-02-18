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
import cz.masci.drd.ui.app.battle.dto.BattleSlidePropertiesDTO;
import cz.masci.drd.ui.app.battle.slide.controller.impl.BattleInitiativeSlideController;
import java.util.List;
import javafx.beans.binding.Bindings;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;

@Component
public class BattleInitiativeSlide extends BattleSlideMultipleControllers<BattleInitiativeSlideController> {

  public BattleInitiativeSlide(FxWeaver fxWeaver, BattleService battleService) {
    super(fxWeaver, battleService);
  }

  @Override
  public void doBeforeSlide() {
    var group = getController().getGroup();
    group.setInitiative(getController().getInitiative());
  }

  @Override
  public void initProperties(BattleSlidePropertiesDTO properties) {
    properties.getPrevDisableProperty().set(false);
    properties.getPrevTextProperty().set(hasPrevious() ? "Předchozí" : "Výběr akcí");
    properties.getNextDisableProperty().bind(Bindings.isNull(getController().initiativeProperty()));
    properties.getNextTextProperty().set(hasNext() ? "Další" : "Spustit bitvu");
    properties.getTitleProperty().set("Iniciativa skupiny " + getController().getGroup().getName());
  }

  @Override
  protected List<BattleInitiativeSlideController> getControllers() {
    return battleService.getGroups().stream().map(group -> {
      var controller = fxWeaver.loadController(BattleInitiativeSlideController.class);
      controller.setGroup(group);
      return controller;
    }).toList();
  }
}
