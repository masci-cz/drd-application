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
import cz.masci.drd.service.exception.BattleException;
import cz.masci.drd.ui.app.battle.slide.controller.impl.BattleGroupSlideController;
import cz.masci.drd.ui.app.battle.dto.BattleSlidePropertiesDTO;
import javafx.beans.binding.Bindings;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BattleGroupSlide extends BattleSlideSingleController<BattleGroupSlideController> {

  public BattleGroupSlide(FxWeaver fxWeaver, BattleService battleService) {
    super(fxWeaver, battleService, BattleGroupSlideController.class);
  }

  @Override
  public void initProperties(BattleSlidePropertiesDTO properties) {
    properties.getPrevDisableProperty().set(true);
    properties.getPrevTextProperty().set(null);
    properties.getNextDisableProperty().bind((Bindings.size(controller.getGroups()).lessThan(2)));
    properties.getNextTextProperty().set("Bojovníci");
    properties.getTitleProperty().set("Skupiny");
  }

  @Override
  public void doBeforeSlide() {
    var groupList = controller.getGroups().stream().toList();
    try {
      battleService.addGroupList(groupList);
    } catch (BattleException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void reset() {
    controller.getGroups().clear();
    battleService.exitBattle();
  }

  @Override
  public void init() {
    battleService.createBattle();
  }

}