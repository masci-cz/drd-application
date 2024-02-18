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

package cz.masci.drd.ui.app.battle;

import cz.masci.drd.ui.app.battle.dto.BattleSlidePropertiesDTO;
import cz.masci.drd.ui.app.battle.slide.presenter.BattleSlide;
import cz.masci.drd.ui.app.battle.slide.presenter.impl.BattleActionSlide;
import cz.masci.drd.ui.app.battle.slide.presenter.impl.BattleDuellistSlide;
import cz.masci.drd.ui.app.battle.slide.presenter.impl.BattleGroupSlide;
import cz.masci.drd.ui.app.battle.slide.presenter.impl.BattleSelectActionSlide;
import cz.masci.drd.ui.app.battle.slide.presenter.impl.BattleInitiativeSlide;
import cz.masci.drd.ui.util.SceneProvider;
import cz.masci.drd.ui.util.slide.SlideService;
import cz.masci.drd.ui.util.slide.impl.SlideServiceImpl.SlideFactor;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BattleFactory implements SceneProvider {
  private final SlideService slideService;
  private final FxWeaver fxWeaver;

  private Scene scene;
  private FxControllerAndView<BattleController, Parent> battleControllerAndView;

  private final BattleGroupSlide battleGroupSlide;
  private final BattleDuellistSlide battleDuellistSlide;
  private final BattleSelectActionSlide battleSelectActionSlide;
  private final BattleInitiativeSlide battleInitiativeSlide;
  private final BattleActionSlide battleActionSlide;

  private BattleSlide<?> currentBattleSlide;
  private BattleSlideState battleSlideState;
  private final BattleSlidePropertiesDTO battleSlideProperties = new BattleSlidePropertiesDTO();

  // region getScene
  @Override
  public Scene getScene() {
    if (scene == null) {
      battleControllerAndView = fxWeaver.load(BattleController.class);
      // init controller
      var controller = battleControllerAndView.getController();
      controller.setBattleFactory(this);
      controller.initControls(battleSlideProperties);
      // init 1.slide - battle group slide
      initBattleGroupSlide(controller.getCenterPane());
      scene = new Scene(battleControllerAndView.getView().orElseThrow());
    } else {
      // re-init 1.slide
      resetToBattleGroupSlide();
    }

    return scene;
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
          battleGroupSlide.reset();
          battleSlideState = BattleSlideState.GROUPS_SETUP;
        }
      });
      case SELECT_ACTION_SETUP -> slide(SlideFactor.PREV, pane, () -> battleDuellistSlide, futureSlide -> {
        if (futureSlide instanceof BattleDuellistSlide) {
          battleSelectActionSlide.reset();
          battleSlideState = BattleSlideState.GROUP_DUELLISTS_SETUP;
        }
      });
      case INITIATIVE_SETUP -> slide(SlideFactor.PREV, pane, () -> battleSelectActionSlide, futureSlide -> {
        if (futureSlide instanceof BattleSelectActionSlide) {
          battleSlideState = BattleSlideState.SELECT_ACTION_SETUP;
        }
      });
      case COMBAT -> slide(SlideFactor.PREV, pane, () -> {
        // reset round before slide
        battleActionSlide.reset();
        battleInitiativeSlide.reset();
        battleSelectActionSlide.reset();
        battleSelectActionSlide.init();

        return battleSelectActionSlide;
      }, futureSlide -> {
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
      case GROUP_DUELLISTS_SETUP, COMBAT -> slide(SlideFactor.NEXT, pane, () -> battleSelectActionSlide, futureSlide -> {
        if (futureSlide instanceof BattleSelectActionSlide) {
          battleSlideState = BattleSlideState.SELECT_ACTION_SETUP;
        }
      });
      case SELECT_ACTION_SETUP -> slide(SlideFactor.NEXT, pane, () -> battleInitiativeSlide, futureSlide -> {
        if (futureSlide instanceof BattleInitiativeSlide) {
          battleSlideState = BattleSlideState.INITIATIVE_SETUP;
        }
      });
      case INITIATIVE_SETUP -> slide(SlideFactor.NEXT, pane, () -> battleActionSlide, futureSlide -> {
        if (futureSlide instanceof BattleActionSlide) {
          battleSlideState = BattleSlideState.COMBAT;
        }
      });
    }
  }

  private void slide(SlideFactor slideFactor, Pane pane, Supplier<BattleSlide<?>> futureSlideSupplier, Consumer<BattleSlide<?>> onSlideFinished) {
    var currentNode = currentBattleSlide.getCurrentView();
    // get future node - could be null
    var futureNode = switch (slideFactor) {
      case PREV -> currentBattleSlide.previousView();
      case NEXT -> currentBattleSlide.nextView();
    };

    // get future slide from supplier if there is no other slide in current presenter
    var futureSlide = (futureNode == null || (!currentBattleSlide.hasNext() && !currentBattleSlide.hasPrevious())) ? futureSlideSupplier.get() :
        currentBattleSlide;

    // do previous/next presenter initialization before slide
    if (futureNode == null || (!currentBattleSlide.hasNext() && !currentBattleSlide.hasPrevious())) {
      switch (slideFactor) {
        case PREV -> futureNode = futureSlide.previousInitView();
        case NEXT -> {
          futureSlide.init();
          futureNode = futureSlide.nextInitView();
        }
      }
    }

    slideService.slide(slideFactor, currentNode, futureNode, pane, () -> {
      if (onSlideFinished != null) {
        onSlideFinished.accept(futureSlide);
      }
      currentBattleSlide = futureSlide;
      currentBattleSlide.initProperties(battleSlideProperties);
      currentBattleSlide.doAfterSlide();
    });
  }

  private void initBattleGroupSlide(Pane pane) {
    // init view
    pane.getChildren().add(battleGroupSlide.getCurrentView());
    // init state
    battleGroupSlide.init();
    battleGroupSlide.initProperties(battleSlideProperties);
    battleSlideState = BattleSlideState.GROUPS_SETUP;
    currentBattleSlide = battleGroupSlide;
  }

  private void resetToBattleGroupSlide() {
    var parentPane = battleControllerAndView.getController().getCenterPane();
    parentPane.getChildren().remove(currentBattleSlide.getCurrentView());
    battleGroupSlide.reset();
    battleGroupSlide.init();
    battleGroupSlide.initProperties(battleSlideProperties);
    parentPane.getChildren().add(battleGroupSlide.getCurrentView());

    // reset current slide to battle group slide
    battleSlideState = BattleSlideState.GROUPS_SETUP;
    currentBattleSlide = battleGroupSlide;
    currentBattleSlide.getCurrentView().setVisible(true);
    currentBattleSlide.getCurrentView().setTranslateX(0.0);
  }

  // endregion

  private enum BattleSlideState {
    GROUPS_SETUP,
    GROUP_DUELLISTS_SETUP,
    SELECT_ACTION_SETUP,
    INITIATIVE_SETUP,
    COMBAT
  }
}
