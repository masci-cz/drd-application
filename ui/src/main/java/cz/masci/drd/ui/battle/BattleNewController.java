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

import cz.masci.commons.springfx.fxml.annotation.FxmlController;
import cz.masci.drd.service.BattleService;
import cz.masci.drd.ui.battle.slide.BattleSlideController;
import cz.masci.drd.ui.battle.slide.impl.BattleGroupSlideController;
import cz.masci.drd.ui.util.slide.SlideService;
import javafx.beans.binding.BooleanExpression;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("fxml/battle-new.fxml")
@FxmlController
@RequiredArgsConstructor
public class BattleNewController {

  private final FxWeaver fxWeaver;
  private final BattleService battleService;
  private SlideService<BattleSlideController, Node> slideService;

  @FXML
  private BorderPane mainPane;
  @FXML
  private AnchorPane centerPane;
  @FXML
  private Label lblTitle;
  @FXML
  private Button btnPrev;
  @FXML
  private Button btnNext;


  @FXML
  private void initialize() {
    btnPrev.setDisable(true);
    btnNext.setDisable(true);

    // load battle group editor
    var battleGroupSlideControllerAndView = fxWeaver.load(BattleGroupSlideController.class);

    // init slide service
    slideService = new SlideService<>(mainPane.widthProperty(), centerPane);
    slideService.getSlides().add(battleGroupSlideControllerAndView);

    // init controls
    initControls();
    initAnchors(battleGroupSlideControllerAndView.getView().orElse(null));
  }

  @FXML
  private void onNext(Event event) {
    var currentController = slideService.getCurrentController();

    slideService.slideForward(
        () -> currentController.onBeforeNext(battleService, slideService),
        this::initAnchors,
        () -> {
          currentController.onAfterNext(battleService, slideService);
          initControls();
        }
    );
  }

  @FXML
  private void onPrev(Event event) {
    var currentController = slideService.getCurrentController();

    slideService.slideBackward(
        () -> currentController.onBeforePrev(battleService, slideService),
        this::initAnchors,
        () -> {
          currentController.onAfterPrev(battleService, slideService);
          initControls();
        }
    );
  }

  private void initControls() {
    var battleSlideController = slideService.getCurrentController();

    btnPrev.setText(battleSlideController.getPrevTitle());
    btnPrev.disableProperty().unbind();
    btnPrev.disableProperty().bind(BooleanExpression.booleanExpression(battleSlideController.validPrevProperty()).not());
    btnNext.setText(battleSlideController.getNextTitle());
    btnNext.disableProperty().unbind();
    btnNext.disableProperty().bind(BooleanExpression.booleanExpression(battleSlideController.validNextProperty()).not());

    // init label
    lblTitle.setText(battleSlideController.getTitle());
  }

  private void initAnchors(Node node) {
    if (node != null) {
      AnchorPane.setTopAnchor(node, 10.0);
      AnchorPane.setRightAnchor(node, 10.0);
      AnchorPane.setBottomAnchor(node, 10.0);
      AnchorPane.setLeftAnchor(node, 10.0);
    }
  }
}

