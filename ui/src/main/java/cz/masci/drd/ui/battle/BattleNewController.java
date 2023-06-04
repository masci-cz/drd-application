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
import cz.masci.drd.ui.battle.service.BattleSlideService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("fxml/battle-new.fxml")
@FxmlController
@RequiredArgsConstructor
public class BattleNewController {

  private final BattleSlideService battleSlideService;

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

    // init controls
    btnPrev.disableProperty().bind(battleSlideService.prevDisableProperty());
    btnPrev.textProperty().bind(battleSlideService.prevTextProperty());
    btnNext.disableProperty().bind(battleSlideService.nextDisableProperty());
    btnNext.textProperty().bind(battleSlideService.nextTextProperty());
    lblTitle.textProperty().bind(battleSlideService.titleProperty());

    battleSlideService.init(centerPane);
  }

  @FXML
  private void onPrev() {
    battleSlideService.slideBackward(centerPane);
  }

  @FXML
  private void onNext() {
    battleSlideService.slideForward(centerPane);
  }

}

