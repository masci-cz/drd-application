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
import cz.masci.drd.dto.GroupDTO;
import cz.masci.drd.service.BattleService;
import cz.masci.drd.service.exception.BattleException;
import cz.masci.drd.ui.battle.slide.BattleSlideController;
import cz.masci.drd.ui.util.slide.SlideService;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@FxmlView("fxml/battle-group-slide.fxml")
@FxmlController
@RequiredArgsConstructor
public class BattleGroupSlideController implements BattleSlideController {

  private final FxWeaver fxWeaver;

  @FXML
  private TextField txtName;
  @FXML
  private Button btnAdd;
  @FXML
  private Button btnEdit;
  @FXML
  private Button btnDelete;
  @FXML
  private ListView<String> lstGroups;

  private final BooleanProperty filledNameProperty = new SimpleBooleanProperty();
  private final BooleanProperty selectedNameProperty = new SimpleBooleanProperty();
  private final BooleanProperty validNextProperty = new SimpleBooleanProperty();

  // region interface

  @Override
  public void onPrev(BattleService battleService, SlideService<BattleSlideController, Node> slideService) {
    // For now do nothing
  }

  @Override
  public void onNext(BattleService battleService, SlideService<BattleSlideController, Node> slideService) {
    // don't create
    if (battleService.getState() == null) {
      battleService.createBattle();

      try {
        var groupList = lstGroups.getItems().stream().toList();
        battleService.addGroupList(groupList);
        for (int i = 0; i < groupList.size(); i++) {
          var name = groupList.get(i);
          var battleDuellistEditorNodeFxControllerAndView = fxWeaver.load(BattleDuellistSlideController.class);
          var battleDuellistSlideController = battleDuellistEditorNodeFxControllerAndView.getController();
          battleDuellistSlideController.setGroup(new GroupDTO(name));
          battleDuellistSlideController.setLastGroup(i == groupList.size() - 1);
          slideService.getSlides().add(battleDuellistEditorNodeFxControllerAndView);
        }
      } catch (BattleException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public String getTitle() {
    return "Skupiny";
  }


  @Override
  public String getPrevTitle() {
    return null;
  }

  @Override
  public String getNextTitle() {
    return "Bojovníci";
  }

  @Override
  public ObservableBooleanValue validPrevProperty() {
    return new SimpleBooleanProperty(false);
  }

  @Override
  public ObservableBooleanValue validNextProperty() {
    return validNextProperty;
  }

  // endregion

  // region FXML

  @FXML
  private void initialize() {
    btnAdd.setDisable(true);
    btnEdit.setDisable(true);
    btnDelete.setDisable(true);

    // init listeners
    txtName.textProperty().addListener(nonEmptyStringChangeListener());
    lstGroups.getSelectionModel().selectedItemProperty().addListener(selectedStringChangeListener());
    lstGroups.getItems().addListener(groupListChangeListener());

    // init bindings
    btnAdd.disableProperty().bind(BooleanExpression.booleanExpression(filledNameProperty).not());
    btnEdit.disableProperty().bind(BooleanExpression.booleanExpression(selectedNameProperty).and(filledNameProperty).not());
    btnDelete.disableProperty().bind(BooleanExpression.booleanExpression(selectedNameProperty).not());
  }

  @FXML
  private void onAddGroup(ActionEvent event) {
    if (isFilledName()) {
      lstGroups.getItems().add(txtName.getText());
      txtName.setText(null);
      txtName.requestFocus();
    }
  }

  @FXML
  private void onEditGroup(ActionEvent event) {
    if (isFilledName() && isSelectedName()) {
      var index = lstGroups.getSelectionModel().getSelectedIndex();
      lstGroups.getItems().set(index, txtName.getText());
      lstGroups.getSelectionModel().clearSelection();
    }
  }

  @FXML
  private void onDeleteGroup(ActionEvent event) {
    if (isSelectedName()) {
      var index = lstGroups.getSelectionModel().getSelectedIndex();
      lstGroups.getItems().remove(index);
      lstGroups.getSelectionModel().clearSelection();
    }
  }

  // endregion

  // region utils

  private ChangeListener<String> nonEmptyStringChangeListener() {
    return (observable, oldValue, newValue) ->
      filledNameProperty.setValue(StringUtils.isNotBlank(newValue));
  }

  private ChangeListener<String> selectedStringChangeListener() {
    return (observable, oldValue, newValue) ->
      selectedNameProperty.setValue(newValue != null);
  }

  private ListChangeListener<String> groupListChangeListener() {
    return change ->
        validNextProperty.setValue(change.getList().size() > 1);
  }

  private boolean isFilledName() {
    return filledNameProperty.get();
  }

  private boolean isSelectedName() {
    return selectedNameProperty.get();
  }

  // endregion
}
