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
import cz.masci.drd.ui.battle.control.BattleGroupEditor;
import javafx.beans.binding.BooleanExpression;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

  @FXML
  private BorderPane mainPane;
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
    mainPane.setCenter(battleGroupEditorLoader.getView().orElseThrow());

    // init listeners
    var battleGroupEditorController = battleGroupEditorLoader.getController();
    btnNext.disableProperty().bind(BooleanExpression.booleanExpression(battleGroupEditorController.validProperty()).not());

    // init label
    lblTitle.setText(battleGroupEditorController.getTitle());
  }
}
