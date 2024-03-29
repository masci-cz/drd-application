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
import cz.masci.drd.dto.DuellistDTO;
import cz.masci.drd.dto.actions.MagicAction;
import io.github.palexdev.materialfx.controls.MFXTextField;
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
@FxmlView("fxml/magic-action.fxml")
@FxmlController
public class MagicBattleSlideActionController implements BattleSlideActionController {

  @Getter
  private MagicAction action;
  @Getter
  private final BooleanProperty finishedProperty = new SimpleBooleanProperty(false);

  @FXML
  @Getter
  private VBox root;
  @FXML
  private Label attackAttacker;
  @FXML
  private Label attackDefender;
  @FXML
  private Label spell;
  @FXML
  private MFXTextField defenseResult;
  @FXML
  private Label lifeDefender;

  @FXML
  void initialize() {
    // attack part
    attackAttacker.setText("");
    attackDefender.setText("");
    // defend part
    lifeDefender.setText("");
    defenseResult.setText("");
    // combat result
    finishedProperty.bind(defenseResult.textProperty().isNotEmpty());
  }

  public void initAction(MagicAction action) {
    this.action = action;
    // attack part
    attackAttacker.setText(action.getAttacker().getName());
    attackDefender.setText(action.getDefender().getName());
    spell.setText(action.getSpell());
  }

  public void applyAction() {
    if (defenseResult.textProperty().isNotEmpty().get()) {
      int finalDefenseResult = Integer.parseInt(defenseResult.getText());
      action.getDefender().setCurrentLive(action.getDefender().getCurrentLive() - finalDefenseResult);
    }
  }

  public void updateLifeDescription() {
    lifeDefender.setText(getLifeDescription(action.getDefender()));
  }

  public String getAttackerName() {
    return action != null ? action.getAttacker().getName() : "";
  }

  private String getLifeDescription(DuellistDTO duellistDTO) {
    return String.format("%d/%d", duellistDTO.getCurrentLive(), duellistDTO.getOriginalLive());
  }
}
