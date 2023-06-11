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

package cz.masci.drd.ui.battle;

import cz.masci.drd.service.BattleService;
import cz.masci.drd.service.exception.BattleException;
import cz.masci.drd.ui.battle.manager.BattleSlideManager;
import cz.masci.drd.ui.battle.manager.dto.BattleSlidePropertiesDTO;
import cz.masci.drd.ui.battle.manager.impl.BaseBattleSlideManager;
import cz.masci.drd.ui.battle.manager.impl.BattleSelectActionSlideManager;
import cz.masci.drd.ui.battle.manager.impl.BattleDuellistSlideManager;
import cz.masci.drd.ui.battle.manager.impl.BattleGroupSlideManager;
import cz.masci.drd.ui.util.slide.SlideService;
import cz.masci.drd.ui.util.slide.impl.SlideServiceImpl.SlideFactor;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BattleFactory {
  private final BattleService battleService;

  private final SlideService slideService;
  private final FxWeaver fxWeaver;

  private FxControllerAndView<BattleNewController, Parent> battleControllerAndView;
  private BattleGroupSlideManager battleGroupSlide;
  private final List<BattleDuellistSlideManager> battleDuellistSlides = new ArrayList<>();
  private final List<BattleSelectActionSlideManager> battleSelectActionSlides = new ArrayList<>();

  private BattleSlideManager<?> currentBattleSlideManager;
  private int index;
  private BattleSlideState battleSlideState;
  private final BattleSlidePropertiesDTO battleSlideProperties = new BattleSlidePropertiesDTO();

  public void show(Stage stage) {
    battleService.createBattle();

    if (battleControllerAndView == null) {
      battleControllerAndView = fxWeaver.load(BattleNewController.class);
    }
    // init controller
    var controller = battleControllerAndView.getController();
    controller.setBattleFactory(this);
    controller.initControls(battleSlideProperties);
    // init 1.slide - battle group slide
    initBattleGroupSlide(controller.getCenterPane());
    Parent root = battleControllerAndView.getView().orElseThrow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void slideBackward(Pane pane) {
    switch (battleSlideState) {
      case GROUPS_SETUP -> {
        // Nothing to do
      }
      case GROUP_DUELLISTS_SETUP -> slideDuellistSlide(SlideFactor.PREV, pane);
      case SELECT_ACTION_SETUP -> slideActionSelectionSlide(SlideFactor.PREV, pane);
    }
  }

  public void slideForward(Pane pane) {
    switch (battleSlideState) {
      case GROUPS_SETUP -> slideGroupSlide(pane);
      case GROUP_DUELLISTS_SETUP -> slideDuellistSlide(SlideFactor.NEXT, pane);
      case SELECT_ACTION_SETUP -> slideActionSelectionSlide(SlideFactor.NEXT, pane);
    }
  }

  private void slideGroupSlide(Pane pane) {
    initBattleDuellistSlides();

    index = 0;
    var futureSlide = battleDuellistSlides.get(index);

    slide(SlideFactor.NEXT, futureSlide, pane, () -> battleSlideState = BattleSlideState.GROUP_DUELLISTS_SETUP);
  }

  private void slideDuellistSlide(SlideFactor slideFactor, Pane pane) {
    if (SlideFactor.NEXT.equals(slideFactor)) {
      index++;
    } else {
      index--;
    }

    if (index == battleDuellistSlides.size()) {
      initBattleSelectActionSlides();
    }

    var futureSlide = (index < 0) ? battleGroupSlide : (index == battleDuellistSlides.size()) ? battleSelectActionSlides.get(0) : battleDuellistSlides.get(index);

    slide(slideFactor, futureSlide, pane, () -> {
      if (index < 0) {
        battleSlideState = BattleSlideState.GROUPS_SETUP;
        battleDuellistSlides.clear();
        index = 0;
        battleService.exitBattle();
      }
      if (index == battleDuellistSlides.size()) {
        battleSlideState = BattleSlideState.SELECT_ACTION_SETUP;
        index = 0;
      }
    });
  }

  private void slideActionSelectionSlide(SlideFactor slideFactor, Pane pane) {
    if (SlideFactor.NEXT.equals(slideFactor)) {
      index++;
    } else {
      index--;
    }

    var futureSlide = (index < 0) ? battleDuellistSlides.get(battleDuellistSlides.size() - 1) : (index == battleSelectActionSlides.size()) ? null : battleSelectActionSlides.get(index);

    slide(slideFactor, futureSlide, pane, () -> {
      if (index < 0) {
        battleSlideState = BattleSlideState.GROUP_DUELLISTS_SETUP;
        battleSelectActionSlides.clear();
        index = battleDuellistSlides.size() - 1;
      }
    });
  }

  private void slide(SlideFactor slideFactor, BaseBattleSlideManager<?> futureSlide, Pane pane, Runnable onSlideFinished) {
    var futureNode = futureSlide.getView();
    var currentNode = currentBattleSlideManager.getView();

    slideService.slide(slideFactor, currentNode, futureNode, pane, () -> {
      currentBattleSlideManager = futureSlide;
      if (onSlideFinished != null) {
        onSlideFinished.run();
      }
      currentBattleSlideManager.initProperties(battleSlideProperties);
    });
  }

  private void initBattleGroupSlide(Pane pane) {
    battleGroupSlide = new BattleGroupSlideManager(fxWeaver);
    // init view
    battleGroupSlide.initProperties(battleSlideProperties);
    pane.getChildren().add(battleGroupSlide.getView());
    // init state
    battleSlideState = BattleSlideState.GROUPS_SETUP;
    currentBattleSlideManager = battleGroupSlide;
    index = 0;
  }

  private void initBattleDuellistSlides() {
    try {
      var battleGroupSlideController = battleGroupSlide.getController();
      var groupList = battleGroupSlideController.getGroups().stream().toList();
      battleService.addGroupList(groupList);
      for (int i = 0; i < groupList.size(); i++) {
        var name = groupList.get(i);
        var battleDuellistSlide = new BattleDuellistSlideManager(fxWeaver, battleService.getGroup(name), i == 0, i == groupList.size() - 1);
        battleDuellistSlides.add(battleDuellistSlide);
      }
      battleGroupSlideController.getGroups().clear();
    } catch (BattleException e) {
      throw new RuntimeException(e);
    }
  }

  private void initBattleSelectActionSlides() {
    var lastGroupIndex = battleService.getGroups().size() - 1;
    for (int groupIndex = 0; groupIndex < battleService.getGroups().size(); groupIndex++) {
      var group = battleService.getGroups().get(groupIndex);
      var lastDuellistIndex = battleService.getGroups().size() - 1;
      for (int duellistIndex = 0; duellistIndex < group.getDuellists().size(); duellistIndex++) {
        var duellist = group.getDuellists().get(duellistIndex);
        battleSelectActionSlides.add(new BattleSelectActionSlideManager(fxWeaver, battleService, group, duellist, groupIndex == 0 && duellistIndex == 0, groupIndex == lastGroupIndex && duellistIndex == lastDuellistIndex));
      }
    }
  }

  private enum BattleSlideState {
    GROUPS_SETUP,
    SELECT_ACTION_SETUP,
    GROUP_DUELLISTS_SETUP
  }
}
