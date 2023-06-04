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

import cz.masci.drd.service.BattleService;
import cz.masci.drd.service.exception.BattleException;
import cz.masci.drd.ui.battle.service.BattleSlide;
import cz.masci.drd.ui.battle.service.BattleSlideService;
import cz.masci.drd.ui.battle.service.dto.BattleSlidePropertiesDTO;
import cz.masci.drd.ui.util.slide.SlideService;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BattleSlideServiceImpl implements BattleSlideService {

  private final BattleService battleService;

  private final SlideService slideService;
  private final FxWeaver fxWeaver;

  private BattleGroupSlide battleGroupSlide;
  private final List<BattleDuellistSlide> battleDuellistSlideList = new ArrayList<>(2);

  private BattleSlide<?> currentBattleSlide;
  private int index;
  private BattleSlideState battleSlideState;
  private final BattleSlidePropertiesDTO battleSlideProperties = new BattleSlidePropertiesDTO();

  @Override
  public void init(Pane pane) {
    battleGroupSlide = new BattleGroupSlide(fxWeaver);
    // init view
    battleGroupSlide.initProperties(battleSlideProperties);
    pane.getChildren().add(battleGroupSlide.getView());
    // init state
    battleSlideState = BattleSlideState.GROUPS_SETUP;
    currentBattleSlide = battleGroupSlide;
    index = 0;
  }

  @Override
  public void slideBackward(Pane pane) {
    switch (battleSlideState) {
      case GROUP_DUELLISTS_SETUP -> {
        index = index - 1;
        var futureSlide = (index < 0) ? battleGroupSlide : battleDuellistSlideList.get(index);
        var futureNode = futureSlide.getView();
        var currentNode = currentBattleSlide.getView();

        slideService.slideBackward(currentNode, futureNode, pane, () -> {
          currentBattleSlide = futureSlide;
          if (index < 0) {
            battleSlideState = BattleSlideState.GROUPS_SETUP;
            battleDuellistSlideList.clear();
            index = 0;
            battleService.exitBattle();
          }
          currentBattleSlide.initProperties(battleSlideProperties);
        });
      }
    }
  }

  @Override
  public void slideForward(Pane pane) {
    switch (battleSlideState) {
      case GROUPS_SETUP -> {
        try {
          battleService.createBattle();
          var battleGroupSlideController = battleGroupSlide.getController();
          var groupList = battleGroupSlideController.getGroups().stream().toList();
          battleService.addGroupList(groupList);
          for (int i = 0; i < groupList.size(); i++) {
            var name = groupList.get(i);
            var battleDuellistSlide = new BattleDuellistSlide(fxWeaver, battleService.getGroup(name), i == 0, i == groupList.size() - 1);
            battleDuellistSlideList.add(battleDuellistSlide);
          }
          battleGroupSlideController.getGroups().clear();

          index = 0;
          var futureSlide = battleDuellistSlideList.get(index);
          var futureNode = futureSlide.getView();
          var currentNode = currentBattleSlide.getView();

          slideService.slideForward(currentNode, futureNode, pane, () -> {
            currentBattleSlide = futureSlide;
            battleSlideState = BattleSlideState.GROUP_DUELLISTS_SETUP;
            currentBattleSlide.initProperties(battleSlideProperties);
          });
        } catch (BattleException e) {
          throw new RuntimeException(e);
        }
      }
      case GROUP_DUELLISTS_SETUP -> {
        index = index + 1;
        var futureSlide = (index == battleDuellistSlideList.size())
            ? null  // TODO new initiative state
            : battleDuellistSlideList.get(index);
        var futureNode = futureSlide.getView();
        var currentNode = currentBattleSlide.getView();

        slideService.slideForward(currentNode, futureNode, pane, () -> {
          currentBattleSlide = futureSlide;
          if (index == battleDuellistSlideList.size()) {
            // TODO new initiative state
          } else {
            currentBattleSlide.initProperties(battleSlideProperties);
          }
        });
      }
    }
  }

  @Override
  public BooleanProperty prevDisableProperty() {
    return battleSlideProperties.getPrevDisableProperty();
  }

  @Override
  public BooleanProperty nextDisableProperty() {
    return battleSlideProperties.getNextDisableProperty();
  }

  @Override
  public StringProperty titleProperty() {
    return battleSlideProperties.getTitleProperty();
  }

  @Override
  public StringProperty prevTextProperty() {
    return battleSlideProperties.getPrevTextProperty();
  }

  @Override
  public StringProperty nextTextProperty() {
    return battleSlideProperties.getNextTextProperty();
  }

  private enum BattleSlideState {
    GROUPS_SETUP,
    GROUP_DUELLISTS_SETUP
  }
}
