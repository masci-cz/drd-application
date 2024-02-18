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

package cz.masci.drd.ui.app.battle.action.controller;

import cz.masci.commons.springfx.fxml.annotation.FxmlController;
import cz.masci.drd.dto.actions.Action;
import cz.masci.drd.dto.actions.SingleActorAction;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@FxmlView("fxml/single-actor-action.fxml")
@FxmlController
public class SingleActorActionController implements BattleSlideActionController {

  @Getter
  private SingleActorAction action;
  @Getter
  private final BooleanProperty finishedProperty = new SimpleBooleanProperty(true);

  @FXML
  @Getter
  private VBox root;
  @FXML
  private Label text;

  @FXML
  void initialize() {
    text.setText("");
  }

  public void initAction(Action<String> action) {
    action.execute();
    text.setText(action.getResult());
  }

  @Override
  public String getAttackerName() {
    return action != null ? action.getActor().getName() : "";
  }

  @Override
  public void applyAction() {

  }

  @Override
  public void updateLifeDescription() {

  }
}
