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
import cz.masci.drd.ui.battle.dto.BattleSlidePropertiesDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("fxml/battle.fxml")
@FxmlController
@RequiredArgsConstructor
public class BattleController {

  @Setter
  private BattleFactory battleFactory;

  @FXML
  private BorderPane mainPane;
  @FXML
  @Getter
  private AnchorPane centerPane;
  @FXML
  private Label lblTitle;
  @FXML
  private Button btnPrev;
  @FXML
  private Button btnNext;

  public void initControls(BattleSlidePropertiesDTO battleSlideProperties) {
    btnPrev.disableProperty().bind(battleSlideProperties.getPrevDisableProperty());
    btnPrev.textProperty().bind(battleSlideProperties.getPrevTextProperty());
    btnNext.disableProperty().bind(battleSlideProperties.getNextDisableProperty());
    btnNext.textProperty().bind(battleSlideProperties.getNextTextProperty());
    lblTitle.textProperty().bind(battleSlideProperties.getTitleProperty());
  }

  @FXML
  private void initialize() {
    btnPrev.setDisable(true);
    btnNext.setDisable(true);

//    battleSlideService.init(centerPane);
  }

  @FXML
  private void slide(ActionEvent event) {
    if (this.btnPrev.equals(event.getSource())) {
      battleFactory.slideBackward(centerPane);
    } else if (this.btnNext.equals(event.getSource())) {
      battleFactory.slideForward(centerPane);
    }
  }

}

