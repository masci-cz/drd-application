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
import cz.masci.drd.service.exception.BattleException;
import cz.masci.drd.ui.battle.control.BattleDuellistEditor;
import cz.masci.drd.ui.battle.control.BattleGroupEditor;
import cz.masci.drd.ui.util.slide.SlideService;
import javafx.beans.binding.BooleanExpression;
import javafx.event.Event;
import javafx.fxml.FXML;
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
  private SlideService slideService;
  private BattleGroupEditor battleGroupEditor;

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
    var battleGroupEditorLoader = fxWeaver.load(BattleGroupEditor.class);
    var battleGroupEditorView = battleGroupEditorLoader.getView().orElseThrow();
    centerPane.getChildren().add(battleGroupEditorView);

    // init listeners
    battleGroupEditor = battleGroupEditorLoader.getController();
    btnNext.disableProperty().bind(BooleanExpression.booleanExpression(battleGroupEditor.validProperty()).not());

    // init label
    lblTitle.setText(battleGroupEditor.getTitle());

    // init slide service
    slideService = new SlideService(mainPane.widthProperty());
    slideService.getNodeList().add(battleGroupEditorView);
  }

  @FXML
  private void onNext(Event event) {
    battleService.createBattle();
    try {
      battleService.addGroupList(battleGroupEditor.getGroupNames());
      battleGroupEditor.getGroupNames().forEach(name -> {
        var battleDuellistEditorNodeFxControllerAndView = fxWeaver.load(BattleDuellistEditor.class);
        var node = battleDuellistEditorNodeFxControllerAndView.getView().orElseThrow();
        slideService.getNodeList().add(node);
        centerPane.getChildren().add(node);
      });

      slideService.next();
    } catch (BattleException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  private void onPrev(Event event) {
    slideService.prev();
  }
}
