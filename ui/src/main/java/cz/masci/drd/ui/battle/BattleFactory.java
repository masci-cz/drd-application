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
import cz.masci.drd.ui.battle.action.ActionService;
import cz.masci.drd.ui.battle.slide.presenter.BattleSlide;
import cz.masci.drd.ui.battle.manager.dto.BattleSlidePropertiesDTO;
import cz.masci.drd.ui.battle.manager.impl.BattleInitiativeSlideManager;
import cz.masci.drd.ui.battle.manager.impl.BattleSelectActionSlideManager;
import cz.masci.drd.ui.battle.manager.impl.BattleDuellistSlideManager;
import cz.masci.drd.ui.battle.slide.presenter.impl.BattleDuellistSlide;
import cz.masci.drd.ui.battle.slide.presenter.impl.BattleGroupSlide;
import cz.masci.drd.ui.util.slide.SlideService;
import cz.masci.drd.ui.util.slide.impl.SlideServiceImpl.SlideFactor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
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
  private final ActionService actionService;

  private final SlideService slideService;
  private final FxWeaver fxWeaver;

  private FxControllerAndView<BattleController, Parent> battleControllerAndView;
  private final BattleGroupSlide battleGroupSlide;
  private final BattleDuellistSlide battleDuellistSlide;
  private final List<BattleDuellistSlideManager> battleDuellistSlides = new ArrayList<>();
  private final List<BattleSelectActionSlideManager> battleSelectActionSlides = new ArrayList<>();
  private final List<BattleInitiativeSlideManager> battleInitiativeSlides = new ArrayList<>();

  private BattleSlide<?> currentBattleSlide;
  private int index;
  private BattleSlideState battleSlideState;
  private final BattleSlidePropertiesDTO battleSlideProperties = new BattleSlidePropertiesDTO();

  // region show
  public void show(Stage stage) {
//    battleService.createBattle();

    if (battleControllerAndView == null) {
      battleControllerAndView = fxWeaver.load(BattleController.class);
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
  // endregion

  // region slides
  public void slideBackward(Pane pane) {
    currentBattleSlide.doBeforeSlide();
    switch (battleSlideState) {
      case GROUPS_SETUP -> {
        // Nothing to do
      }
      case GROUP_DUELLISTS_SETUP -> slideDuellistSlide(SlideFactor.PREV, pane);
      case SELECT_ACTION_SETUP -> slideActionSelectionSlide(SlideFactor.PREV, pane);
      case INITIATIVE_SETUP -> slideInitiativeSlide(SlideFactor.PREV, pane);
    }
  }

  public void slideForward(Pane pane) {
    currentBattleSlide.doBeforeSlide();
    switch (battleSlideState) {
      case GROUPS_SETUP -> slideGroupSlide(pane);
      case GROUP_DUELLISTS_SETUP -> slideDuellistSlide(SlideFactor.NEXT, pane);
      case SELECT_ACTION_SETUP -> slideActionSelectionSlide(SlideFactor.NEXT, pane);
      case INITIATIVE_SETUP -> slideInitiativeSlide(SlideFactor.NEXT, pane);
    }
  }

  private void slideGroupSlide(Pane pane) {
    var futureSlide = battleDuellistSlide;
    futureSlide.init();

    slide(SlideFactor.NEXT, futureSlide, pane, (current, future) -> battleSlideState = BattleSlideState.GROUP_DUELLISTS_SETUP);
  }

  private void slideDuellistSlide(SlideFactor slideFactor, Pane pane) {
    BattleSlide<?> futureSlide = currentBattleSlide;

    switch (slideFactor) {
      case PREV -> {
        if (!currentBattleSlide.hasPrevious()) {
          futureSlide = battleGroupSlide;
          futureSlide.reset();
        }
      }
      case NEXT -> {
        if (!currentBattleSlide.hasNext()) {
          initBattleSelectActionSlides();
          index = 0;
          futureSlide = battleSelectActionSlides.get(index);
        }
      }
    }

    slide(slideFactor, futureSlide, pane, (current, future) -> {
      if (future instanceof BattleGroupSlide) {
        battleSlideState = BattleSlideState.GROUPS_SETUP;
        current.reset();
      }
      if (future instanceof BattleSelectActionSlideManager) {
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

    if (index == battleSelectActionSlides.size()) {
      initBattleInitiativeActionSlides();
    }

    var futureSlide = (index < 0) ? battleDuellistSlide : (index == battleSelectActionSlides.size()) ? battleInitiativeSlides.get(0) : battleSelectActionSlides.get(index);

    slide(slideFactor, futureSlide, pane, () -> {
      if (index < 0) {
        battleSlideState = BattleSlideState.GROUP_DUELLISTS_SETUP;
        battleSelectActionSlides.clear();
        index = battleDuellistSlides.size() - 1;
      } else if (index == battleSelectActionSlides.size()) {
        battleSlideState = BattleSlideState.INITIATIVE_SETUP;
        index = 0;
      }
    });
  }

  private void slideInitiativeSlide(SlideFactor slideFactor, Pane pane) {
    if (SlideFactor.NEXT.equals(slideFactor)) {
      index++;
    } else {
      index--;
    }

    var futureSlide = (index < 0) ? battleSelectActionSlides.get(battleSelectActionSlides.size() - 1) : (index == battleInitiativeSlides.size()) ? null : battleInitiativeSlides.get(index);

    slide(slideFactor, futureSlide, pane, () -> {
      if (index < 0) {
        battleSlideState = BattleSlideState.SELECT_ACTION_SETUP;
        battleInitiativeSlides.clear();
        index = battleSelectActionSlides.size() - 1;
      }
    });
  }

  private void slide(SlideFactor slideFactor, BattleSlide<?> futureSlide, Pane pane, Runnable onSlideFinished) {
    var futureNode = futureSlide.getCurrentView();
    var currentNode = currentBattleSlide.getCurrentView();

    slideService.slide(slideFactor, currentNode, futureNode, pane, () -> {
      currentBattleSlide = futureSlide;
      if (onSlideFinished != null) {
        onSlideFinished.run();
      }
      currentBattleSlide.initProperties(battleSlideProperties);
    });
  }

  private void slide(SlideFactor slideFactor, BattleSlide<?> futureSlide, Pane pane, BiConsumer<BattleSlide<?>, BattleSlide<?>> onSlideFinished) {
    var currentNode = currentBattleSlide.getCurrentView();
    var futureNode = SlideFactor.PREV.equals(slideFactor) ? futureSlide.getPreviousView() : futureSlide.getNextView();

    slideService.slide(slideFactor, currentNode, futureNode, pane, () -> {
      if (onSlideFinished != null) {
        onSlideFinished.accept(currentBattleSlide, futureSlide);
      }
      currentBattleSlide = futureSlide;
      currentBattleSlide.initProperties(battleSlideProperties);
    });
  }

  // endregion

  // region init slides
  private void initBattleGroupSlide(Pane pane) {
    // init view
    battleGroupSlide.initProperties(battleSlideProperties);
    battleGroupSlide.init();
    pane.getChildren().add(battleGroupSlide.getCurrentView());
    // init state
    battleSlideState = BattleSlideState.GROUPS_SETUP;
    currentBattleSlide = battleGroupSlide;
    index = 0;
  }

  private void initBattleSelectActionSlides() {
    var lastGroupIndex = battleService.getGroups().size() - 1;
    for (int groupIndex = 0; groupIndex < battleService.getGroups().size(); groupIndex++) {
      var group = battleService.getGroups().get(groupIndex);
      var lastDuellistIndex = battleService.getGroups().size() - 1;
      for (int duellistIndex = 0; duellistIndex < group.getDuellists().size(); duellistIndex++) {
        var duellist = group.getDuellists().get(duellistIndex);
        battleSelectActionSlides.add(new BattleSelectActionSlideManager(fxWeaver, actionService, battleService, group, duellist, groupIndex == 0 && duellistIndex == 0, groupIndex == lastGroupIndex && duellistIndex == lastDuellistIndex));
      }
    }
  }

  private void initBattleInitiativeActionSlides() {
    var lastGroupIndex = battleService.getGroups().size() - 1;
    for (int groupIndex = 0; groupIndex < battleService.getGroups().size(); groupIndex++) {
      var group = battleService.getGroups().get(groupIndex);
        battleInitiativeSlides.add(new BattleInitiativeSlideManager(fxWeaver, group, groupIndex == 0, groupIndex == lastGroupIndex));
    }
  }
  // endregion

  // region utils
//  private <T> BattleSlideManager<T> getIndexedFutureSlide() {
//    (index < 0) ? battleGroupSlide : (index == battleDuellistSlides.size()) ? battleSelectActionSlides.get(0) : battleDuellistSlides.get(index);
//    (index < 0) ? battleDuellistSlides.get(battleDuellistSlides.size() - 1) : (index == battleSelectActionSlides.size()) ? battleInitiativeSlides.get(0) : battleSelectActionSlides.get(index);
//    (index < 0) ? battleSelectActionSlides.get(battleSelectActionSlides.size() - 1) : (index == battleInitiativeSlides.size()) ? null : battleInitiativeSlides.get(index);
//  }

  // endregion
  private enum BattleSlideState {
    GROUPS_SETUP,
    GROUP_DUELLISTS_SETUP,
    SELECT_ACTION_SETUP,
    INITIATIVE_SETUP
  }
}
