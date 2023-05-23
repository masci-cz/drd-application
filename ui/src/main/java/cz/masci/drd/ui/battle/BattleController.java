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
import cz.masci.drd.dto.BattleState;
import cz.masci.drd.dto.DuellistDTO;
import cz.masci.drd.service.BattleService;
import cz.masci.drd.service.exception.BattleException;
import cz.masci.drd.ui.battle.action.ActionController;
import cz.masci.drd.ui.battle.action.ActionService;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@FxmlView("fxml/battle.fxml")
@FxmlController
@Slf4j
@RequiredArgsConstructor
public class BattleController {

  private final BattleService battleService;
  private final ActionService actionService;

  @FXML
  private Pane initPane;
  @FXML
  private Pane roundPane;
  @FXML
  private Pane actionPane;
  @FXML
  private ListView<String> groupList;
  @FXML
  private TextField groupName;
  @FXML
  private ListView<DuellistDTO> duellistList;
  @FXML
  private TextField duellistName;
  @FXML
  private TextField duellistAttack;
  @FXML
  private TextField duellistDefense;
  @FXML
  private TextField duellistLive;
  @FXML
  private TextField groupInitiative;
  @FXML
  private ComboBox<FxControllerAndView<ActionController, Node>> duellistAction;

  public void initialize() {
    // init action list
    duellistAction.setItems(FXCollections.observableList(actionService.getActions()));
    // init panes
    initPane.setVisible(true);
    roundPane.setVisible(false);
    // init listeners
    groupList.getSelectionModel().selectedItemProperty().addListener(this::changeDuellistList);
    groupList.getSelectionModel().selectedItemProperty().addListener(this::changeGroupInitiativeText);
    groupInitiative.textProperty().addListener(this::changeGroupInitiative);
    duellistList.getSelectionModel().selectedItemProperty().addListener(this::changeDuellistAction);
    duellistAction.getSelectionModel().selectedItemProperty().addListener(this::changeActionPane);
    // init battle
    battleService.createBattle();
  }

  @FXML
  public void addGroup(ActionEvent event) {
    var name = groupName.getText();

    try {
      battleService.addGroup(name);
      groupList.getItems().add(name);
      groupName.setText(null);
      groupName.requestFocus();
    } catch (BattleException e) {
      log.error(e.getMessage());
    }
  }

  @FXML
  public void addDuellist(ActionEvent event) {
    var groupName = groupList.getSelectionModel().getSelectedItem();

    var duellistDTO = new DuellistDTO();
    duellistDTO.setName(duellistName.getText());
    duellistDTO.setAttack(Integer.parseInt(duellistAttack.getText()));
    duellistDTO.setDefense(Integer.parseInt(duellistDefense.getText()));
    duellistDTO.setOriginalLive(Integer.parseInt(duellistLive.getText()));
    duellistDTO.setCurrentLive(duellistDTO.getOriginalLive());

    try {
      battleService.getGroupDuellists(groupName).add(duellistDTO);
      duellistList.getItems().add(duellistDTO);
      duellistName.setText(null);
      duellistAttack.setText(null);
      duellistDefense.setText(null);
      duellistLive.setText(null);
      duellistName.requestFocus();
    } catch (BattleException e) {
      log.error(e.getMessage());
    }
  }

  @FXML
  public void startBattle(ActionEvent event) {
    try {
      battleService.startBattle();
      initPane.setVisible(false);
      roundPane.setVisible(true);
    } catch (BattleException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  public void nextRound(ActionEvent event) {
    try {
      battleService.startRound();
    } catch (BattleException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  public void back(ActionEvent event) {
    battleService.exitBattle();
    roundPane.setVisible(false);
    initPane.setVisible(true);
  }

  private void changeDuellistList(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    try {
      var groupDuellists = battleService.getGroupDuellists(newValue);
      duellistList.getItems().clear();
      duellistList.getItems().addAll(groupDuellists);
    } catch (BattleException e) {
      throw new RuntimeException(e);
    }
  }

  private void changeGroupInitiativeText(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    try {
      var initiative = battleService.getGroupInitiative(newValue);
      groupInitiative.setText(initiative == null ? "" : String.valueOf(initiative));
    } catch (BattleException e) {
      throw new RuntimeException(e);
    }
  }

  private void changeGroupInitiative(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    try {
      if (StringUtils.isBlank(newValue) || battleService.getState().equals(BattleState.NEW)) {
        return;
      }

      int initiative = Integer.parseInt(newValue);
      var group = groupList.getSelectionModel().getSelectedItem();
      battleService.setGroupInitiative(group, initiative);
    } catch (BattleException | NumberFormatException e) {
      throw new RuntimeException(e);
    }
  }

  private void changeDuellistAction(ObservableValue<? extends DuellistDTO> observable, DuellistDTO oldValue, DuellistDTO newValue) {
    // TODO: set action on selected duellist
    actionPane.getChildren().clear();
    duellistAction.getSelectionModel().clearSelection();
  }

  private void changeActionPane(ObservableValue<? extends FxControllerAndView<ActionController, Node>> observable, FxControllerAndView<ActionController, Node> oldValue, FxControllerAndView<ActionController, Node> newValue) {
    if (newValue != null) {
      newValue.getView().ifPresent(node -> {
        log.debug("Setting new action node {}", node);
        actionPane.getChildren().clear();
        actionPane.getChildren().add(node);
      });
    }
  }
}
