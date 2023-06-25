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

import cz.masci.drd.ui.battle.dto.BattleSlidePropertiesDTO;
import cz.masci.drd.ui.battle.slide.presenter.BattleSlide;
import cz.masci.drd.ui.battle.slide.presenter.impl.BattleDuellistSlide;
import cz.masci.drd.ui.battle.slide.presenter.impl.BattleGroupSlide;
import cz.masci.drd.ui.battle.slide.presenter.impl.BattleInitiativeSlide;
import cz.masci.drd.ui.battle.slide.presenter.impl.BattleSelectActionSlide;
import cz.masci.drd.ui.util.slide.SlideService;
import cz.masci.drd.ui.util.slide.impl.SlideServiceImpl.SlideFactor;
import java.util.function.Consumer;
import java.util.function.Supplier;
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
  private final SlideService slideService;
  private final FxWeaver fxWeaver;

  private FxControllerAndView<BattleController, Parent> battleControllerAndView;
  private final BattleGroupSlide battleGroupSlide;
  private final BattleDuellistSlide battleDuellistSlide;
  private final BattleSelectActionSlide battleSelectActionSlide;
  private final BattleInitiativeSlide battleInitiativeSlide;

  private BattleSlide<?> currentBattleSlide;
  private BattleSlideState battleSlideState;
  private final BattleSlidePropertiesDTO battleSlideProperties = new BattleSlidePropertiesDTO();

  // region show
  public void show(Stage stage) {
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
      case GROUP_DUELLISTS_SETUP -> slide(SlideFactor.PREV, pane, () -> battleGroupSlide, futureSlide -> {
        if (futureSlide instanceof BattleGroupSlide) {
          futureSlide.reset();
          battleSlideState = BattleSlideState.GROUPS_SETUP;
        }
      });
      case SELECT_ACTION_SETUP -> slide(SlideFactor.PREV, pane, () -> battleDuellistSlide, futureSlide -> {
        if (futureSlide instanceof BattleDuellistSlide) {
          battleSlideState = BattleSlideState.GROUP_DUELLISTS_SETUP;
        }
      });
      case INITIATIVE_SETUP -> slide(SlideFactor.PREV, pane, () -> battleSelectActionSlide, futureSlide -> {
        if (futureSlide instanceof BattleSelectActionSlide) {
          battleSlideState = BattleSlideState.SELECT_ACTION_SETUP;
        }
      });
    }
  }

  public void slideForward(Pane pane) {
    currentBattleSlide.doBeforeSlide();
    switch (battleSlideState) {
      case GROUPS_SETUP -> slide(SlideFactor.NEXT, pane, () -> battleDuellistSlide, futureSlide -> battleSlideState = BattleSlideState.GROUP_DUELLISTS_SETUP);
      case GROUP_DUELLISTS_SETUP -> slide(SlideFactor.NEXT, pane, () -> battleSelectActionSlide, futureSlide -> {
        if (futureSlide instanceof BattleSelectActionSlide) {
          battleSlideState = BattleSlideState.SELECT_ACTION_SETUP;
        }
      });
      case SELECT_ACTION_SETUP -> slide(SlideFactor.NEXT, pane, () -> battleInitiativeSlide, futureSlide -> {
        if (futureSlide instanceof BattleInitiativeSlide) {
          battleSlideState = BattleSlideState.INITIATIVE_SETUP;
        }
      });
      case INITIATIVE_SETUP -> slide(SlideFactor.NEXT, pane, () -> null, futureSlide -> {});
    }
  }

  private void slide(SlideFactor slideFactor, Pane pane, Supplier<BattleSlide<?>> futureSlideSupplier, Consumer<BattleSlide<?>> onSlideFinished) {
    var currentNode = currentBattleSlide.getCurrentView();
    var futureNode = switch (slideFactor) {
      case PREV -> currentBattleSlide.previousView();
      case NEXT -> currentBattleSlide.nextView();
    };

    var futureSlide = (futureNode == null || (!currentBattleSlide.hasNext() && !currentBattleSlide.hasPrevious())) ? futureSlideSupplier.get() :
        currentBattleSlide;

    if (futureNode == null || (!currentBattleSlide.hasNext() && !currentBattleSlide.hasPrevious())) {
      switch (slideFactor) {
        case PREV -> futureNode = futureSlide.previousView();
        case NEXT -> {
          futureSlide.init();
          futureNode = futureSlide.nextView();
        }
      };
    }

    slideService.slide(slideFactor, currentNode, futureNode, pane, () -> {
      if (onSlideFinished != null) {
        onSlideFinished.accept(futureSlide);
      }
      currentBattleSlide = futureSlide;
      currentBattleSlide.initProperties(battleSlideProperties);
    });
  }

  private void initBattleGroupSlide(Pane pane) {
    // init view
    battleGroupSlide.init();
    battleGroupSlide.initProperties(battleSlideProperties);
    pane.getChildren().add(battleGroupSlide.getCurrentView());
    // init state
    battleSlideState = BattleSlideState.GROUPS_SETUP;
    currentBattleSlide = battleGroupSlide;
  }

  // endregion

  private enum BattleSlideState {
    GROUPS_SETUP,
    GROUP_DUELLISTS_SETUP,
    SELECT_ACTION_SETUP,
    INITIATIVE_SETUP
  }
}
