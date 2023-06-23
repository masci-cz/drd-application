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

package cz.masci.drd.ui.battle.slide.presenter.impl;

import cz.masci.drd.service.BattleService;
import cz.masci.drd.service.exception.BattleException;
import cz.masci.drd.ui.battle.manager.dto.BattleSlidePropertiesDTO;
import cz.masci.drd.ui.battle.slide.impl.BattleGroupSlideController;
import cz.masci.drd.ui.battle.slide.presenter.BattleSlide;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BattleGroupSlide implements BattleSlide<BattleGroupSlideController> {

  private final BattleService battleService;

  @Getter
  private final BattleGroupSlideController controller;

  public BattleGroupSlide(FxWeaver fxWeaver, BattleService battleService) {
    var fxControllerAndView = fxWeaver.load(BattleGroupSlideController.class);
    controller = fxControllerAndView.getController();
    this.battleService = battleService;
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

  @Override
  public Node getCurrentView() {
    return controller.getRoot();
  }

  @Override
  public Node getPreviousView() {
    return controller.getRoot();
  }

  @Override
  public Node getNextView() {
    return controller.getRoot();
  }

  @Override
  public boolean hasPrevious() {
    return false;
  }

  @Override
  public boolean hasNext() {
    return false;
  }
}