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

package cz.masci.drd.ui.battle.slide.impl;

import cz.masci.commons.springfx.fxml.annotation.FxmlController;
import cz.masci.drd.ui.battle.action.ActionSelectionControl;
import cz.masci.drd.ui.battle.action.ActionService;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@FxmlView("fxml/battle-action-slide.fxml")
@FxmlController
@RequiredArgsConstructor
@Slf4j
public class BattleActionSlideController {

  private final ActionService actionService;

  @FXML
  private BorderPane pane;

  @FXML
  private ChoiceBox<ActionSelectionControl> actionBox;

  @FXML
  public void initialize() {
    actionBox.setItems(FXCollections.observableList(actionService.getActions()));

    actionBox.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> {
          if (newValue == null) {
            pane.setCenter(null);
          } else {
            pane.setCenter(newValue.getView());
          }
        }
    );
  }

  public ReadOnlyObjectProperty<ActionSelectionControl> selectedAction() {
    return actionBox.getSelectionModel().selectedItemProperty();
  }
}
