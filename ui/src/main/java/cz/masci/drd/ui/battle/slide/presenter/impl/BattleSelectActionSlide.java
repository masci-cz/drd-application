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
import cz.masci.drd.ui.battle.action.ActionService;
import cz.masci.drd.ui.battle.action.SelectAction;
import cz.masci.drd.ui.battle.dto.BattleSlidePropertiesDTO;
import cz.masci.drd.ui.battle.slide.controller.impl.BattleSelectActionSlideController;
import java.util.List;
import java.util.Optional;
import javafx.scene.Node;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;

@Component
public class BattleSelectActionSlide extends BattleSlideMultipleControllers<BattleSelectActionSlideController> {

  private final ActionService actionService;

  public BattleSelectActionSlide(FxWeaver fxWeaver, BattleService battleService, ActionService actionService) {
    super(fxWeaver, battleService);
    this.actionService = actionService;
  }

  @Override
  public void initProperties(BattleSlidePropertiesDTO properties) {
    properties.getPrevDisableProperty().set(false);
    properties.getPrevTextProperty().set(hasPrevious() ? "Předchozí" : "Bojovníci");
    properties.getNextDisableProperty().bind(getController().selectedAction().isNull());
    properties.getNextTextProperty().set(hasNext() ? "Další" : "Iniciativa");
    properties.getTitleProperty().setValue(String.format("Vyberte akci pro bojovníka %s ze skupiny %s", getController().getDuellist().getName(), getController().getGroupName()));
  }

  @Override
  public void doBeforeSlide() {
    getController().getDuellist().setSelectedAction(Optional.ofNullable(getController().selectedAction().get()).map(SelectAction::getAction).orElse(null));
  }

  @Override
  protected void preInit() {
    super.preInit();
    try {
      battleService.startBattle();
    } catch (BattleException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void reset() {
    super.reset();
    try {
      battleService.resetPreparation();
    } catch (BattleException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected List<BattleSelectActionSlideController> getControllers() {
    return battleService.getGroups().stream()
        .flatMap(group ->
            group.getDuellists().stream()
                .map(duellist -> {
                  var controller = fxWeaver.loadController(BattleSelectActionSlideController.class);
                  var actions = actionService.initSelectActionControls().stream().peek(action -> action.initAction(duellist, battleService.getAllDuellists())).toList();
                  controller.initActions(actions);
                  controller.setDuellist(duellist);
                  controller.setGroupName(group.getName());
                  return controller;
                })
        )
        .toList();
  }

  @Override
  public Node previousInitView() {
    return nextView();
  }
}
