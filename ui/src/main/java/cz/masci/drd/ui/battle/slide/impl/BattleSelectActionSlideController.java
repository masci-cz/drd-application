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
import cz.masci.drd.dto.DuellistDTO;
import cz.masci.drd.service.BattleService;
import cz.masci.drd.ui.battle.action.SelectActionControl;
import cz.masci.drd.ui.battle.action.ActionService;
import cz.masci.drd.ui.converter.ActionStringConverter;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@FxmlView("fxml/battle-select-action-slide.fxml")
@FxmlController
@RequiredArgsConstructor
@Slf4j
public class BattleSelectActionSlideController {

  private final ActionService actionService;

  @Setter
  @Getter
  private DuellistDTO duellist;

  @Setter
  private BattleService battleService;

  @FXML
  private BorderPane pane;

  @FXML
  private ChoiceBox<SelectActionControl> actionBox;

  @FXML
  public void initialize() {
    actionBox.setConverter(new ActionStringConverter("Vyberte akci"));
    actionBox.setItems(FXCollections.observableList(actionService.getActions()));

    actionBox.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> {
          if (newValue == null) {
            pane.setCenter(null);
          } else {
            pane.setCenter(newValue.getView());
            initAnchors(newValue.getView());
            newValue.initAction(battleService, duellist);
          }
        }
    );

  }

  public ReadOnlyObjectProperty<SelectActionControl> selectedAction() {
    return actionBox.getSelectionModel().selectedItemProperty();
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
