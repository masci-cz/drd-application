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

package cz.masci.drd.ui.battle.action.controller;

import cz.masci.commons.springfx.fxml.annotation.FxmlController;
import cz.masci.drd.dto.actions.CombatAction;
import cz.masci.drd.ui.battle.slide.controller.BattleSlideController;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@FxmlView("fxml/close-combat-action.fxml")
@FxmlController
public class CloseCombatActionController implements ChangeListener<String>, BattleSlideController {

  @Getter
  private CombatAction action;
  @Getter
  private final BooleanProperty finishedProperty = new SimpleBooleanProperty(false);

  @FXML
  @Getter
  private VBox root;
  @FXML
  private Label attack;
  @FXML
  private Label attackAttacker;
  @FXML
  private Label attackDefender;
  @FXML
  private TextField attackRoll;
  @FXML
  private Label baseAttack;
  @FXML
  private Label baseDefense;
  @FXML
  private Label defense;
  @FXML
  private Label defenseAttacker;
  @FXML
  private HBox defenseDefended;
  @FXML
  private Label defenseDefender;
  @FXML
  private HBox defenseNotDefended;
  @FXML
  private TextField defenseResult;
  @FXML
  private TextField defenseRoll;

  @FXML
  void initialize() {
    // attack part
    attackAttacker.setText("");
    attackDefender.setText("");
    baseAttack.setText("");
    attackRoll.setText("");
    attackRoll.textProperty().addListener(this);
    attack.setText("");
    // defend part
    defenseAttacker.setText("");
    defenseDefender.setText("");
    baseDefense.setText("");
    defenseRoll.setText("");
    defenseRoll.textProperty().addListener(this);
    defense.setText("");
    // combat result
    finishedProperty.bind(defenseDefended.visibleProperty().or(defenseNotDefended.visibleProperty()));
//    defenseResult.textProperty().addListener((observable, oldValue, newValue) -> finishedProperty.set(StringUtils.isNotBlank(newValue) && action.getResult() != null && action.getResult().success()));
    // hide combat result
    defenseDefended.setVisible(false);
    defenseNotDefended.setVisible(false);
  }

  public void initAction(CombatAction action) {
    this.action = action;
    // attack part
    attackAttacker.setText(action.getAttacker().getName());
    attackDefender.setText(action.getDefender().getName());
    baseAttack.setText(String.valueOf(action.getAttacker().getAttack()));
    // defend part
    defenseAttacker.setText(action.getDefender().getName());
    defenseDefender.setText(action.getAttacker().getName());
    baseDefense.setText(String.valueOf(action.getDefender().getDefense()));
  }

  @Override
  public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    setIntegerValueOrNull(attackRoll::getText, action::setAttackerDiceRoll);
    setIntegerValueOrNull(defenseRoll::getText, action::setDefenderDiceRoll);
    if (action.isPrepared()) {
      action.execute();
      var actionResult = action.getResult();
      attack.setText(String.valueOf(actionResult.attack()));
      defense.setText(String.valueOf(actionResult.defense()));
      if (actionResult.success()) {
        defenseResult.setText(String.valueOf(actionResult.life()));
        defenseNotDefended.setVisible(true);
      } else {
        defenseDefended.setVisible(true);
      }
    } else {
      defenseDefended.setVisible(false);
      defenseNotDefended.setVisible(false);
      defenseResult.setText(null);
    }
  }

  private void setIntegerValueOrNull(Supplier<String> source, Consumer<Integer> destination) {
    destination.accept(StringUtils.isBlank(source.get()) ? null : Integer.parseInt(source.get()));
  }
}
