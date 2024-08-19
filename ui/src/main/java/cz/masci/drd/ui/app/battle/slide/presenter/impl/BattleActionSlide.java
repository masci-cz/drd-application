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

import cz.masci.drd.dto.actions.CombatAction;
import cz.masci.drd.dto.actions.MagicAction;
import cz.masci.drd.dto.actions.OtherAction;
import cz.masci.drd.dto.actions.PrepareAction;
import cz.masci.drd.dto.actions.ShootAction;
import cz.masci.drd.dto.actions.SpeechAction;
import cz.masci.drd.dto.actions.WaitAction;
import cz.masci.drd.service.BattleService;
import cz.masci.drd.service.exception.BattleException;
import cz.masci.drd.ui.app.battle.action.controller.BattleSlideActionController;
import cz.masci.drd.ui.app.battle.action.controller.CloseCombatBattleSlideActionController;
import cz.masci.drd.ui.app.battle.action.controller.MagicBattleSlideActionController;
import cz.masci.drd.ui.app.battle.action.controller.ShootBattleSlideActionController;
import cz.masci.drd.ui.app.battle.action.controller.SingleActorActionController;
import cz.masci.drd.ui.app.battle.dto.BattleSlidePropertiesDTO;
import java.util.ArrayList;
import java.util.List;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;

@Component
public class BattleActionSlide extends BattleSlideMultipleControllers<BattleSlideActionController> {


  public BattleActionSlide(FxWeaver fxWeaver, BattleService battleService) {
    super(fxWeaver, battleService);
  }

  @Override
  public void initProperties(BattleSlidePropertiesDTO properties) {
    properties.getPrevDisableProperty().set(hasPrevious());
    properties.getPrevTextProperty().set(hasPrevious() ? "" : "Zrušit kolo");
    properties.getNextDisableProperty().bind(getController().getFinishedProperty().not());
    properties.getNextTextProperty().set(hasNext() ? "Další" : "Další kolo");
    properties.getTitleProperty().setValue(String.format("Proveďte akci pro bojovníka %s", getController().getAttackerName()));
  }

  @Override
  public void doBeforeSlide() {
    getController().applyAction();
  }

  @Override
  public void doAfterSlide() {
    getController().updateLifeDescription();
  }

  @Override
  protected List<BattleSlideActionController> getControllers() {
    try {
      battleService.startRound();
    } catch (BattleException e) {
      throw new RuntimeException(e);
    }
    List<BattleSlideActionController> controllers = new ArrayList<>();
    while (!battleService.getActions().isEmpty()) {
      var action = battleService.getActions().poll();
      if (action instanceof CombatAction combatAction) {
        var controller = fxWeaver.loadController(CloseCombatBattleSlideActionController.class);
        controller.initAction(combatAction);
        controllers.add(controller);
      }
      if (action instanceof ShootAction shootAction) {
        var controller = fxWeaver.loadController(ShootBattleSlideActionController.class);
        controller.initAction(shootAction);
        controllers.add(controller);
      }
      if (action instanceof MagicAction magicAction) {
        var controller = fxWeaver.loadController(MagicBattleSlideActionController.class);
        controller.initAction(magicAction);
        controllers.add(controller);
      }
      if (action instanceof OtherAction actionInstance) {
        var controller = fxWeaver.loadController(SingleActorActionController.class);
        controller.initAction(actionInstance);
        controllers.add(controller);
      }
      if (action instanceof PrepareAction actionInstance) {
        var controller = fxWeaver.loadController(SingleActorActionController.class);
        controller.initAction(actionInstance);
        controllers.add(controller);
      }
      if (action instanceof SpeechAction actionInstance) {
        var controller = fxWeaver.loadController(SingleActorActionController.class);
        controller.initAction(actionInstance);
        controllers.add(controller);
      }
      if (action instanceof WaitAction actionInstance) {
        var controller = fxWeaver.loadController(SingleActorActionController.class);
        controller.initAction(actionInstance);
        controllers.add(controller);
      }
    }
    return controllers;
  }

  @Override
  public void reset() {
    super.reset();
    try {
      battleService.cancelRound();
    } catch (BattleException e) {
      throw new RuntimeException(e);
    }
  }
}
