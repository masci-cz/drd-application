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

import static cz.masci.drd.ui.util.PropertyUtility.FALSE_PROPERTY;

import cz.masci.commons.springfx.fxml.annotation.FxmlController;
import cz.masci.drd.service.BattleService;
import cz.masci.drd.service.exception.BattleException;
import cz.masci.drd.ui.battle.slide.BattleSlideController;
import cz.masci.drd.ui.util.slide.SlideService;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.value.ObservableBooleanValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("fxml/battle-group-slide.fxml")
@FxmlController
@RequiredArgsConstructor
@Slf4j
public class BattleGroupSlideController extends BasicBattleSlideController implements BattleSlideController {

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

  // region interface

  @Override
  public void onBeforeNext(BattleService battleService, SlideService<BattleSlideController, Node> slideService) {
    // don't create
    battleService.createBattle();
    log.debug("Size of slides before: {}", slideService.getSlides().size());
    try {
      var groupList = lstGroups.getItems().stream().toList();
      battleService.addGroupList(groupList);
      for (int i = 0; i < groupList.size(); i++) {
        var name = groupList.get(i);
        var battleDuellistEditorNodeFxControllerAndView = fxWeaver.load(BattleDuellistSlideController.class);
        var battleDuellistSlideController = battleDuellistEditorNodeFxControllerAndView.getController();
        battleDuellistSlideController.setGroup(battleService.getGroup(name));
        battleDuellistSlideController.setFirstGroup(i == 0);
        battleDuellistSlideController.setLastGroup(i == groupList.size() - 1);
        slideService.getSlides().add(battleDuellistEditorNodeFxControllerAndView);
      }
      lstGroups.getItems().clear();
    } catch (BattleException e) {
      throw new RuntimeException(e);
    }
    log.debug("Size of slides after: {}", slideService.getSlides().size());
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
    return FALSE_PROPERTY;
  }

  @Override
  public ObservableBooleanValue validNextProperty() {
    return Bindings.size(lstGroups.getItems()).greaterThanOrEqualTo(2);
  }

  // endregion

  // region FX

  @FXML
  private void initialize() {
    btnAdd.setDisable(true);
    btnEdit.setDisable(true);
    btnDelete.setDisable(true);

    // init bindings

    BooleanExpression filledForm = txtName.textProperty().isNotEmpty();
    BooleanExpression selectedItem = lstGroups.getSelectionModel().selectedItemProperty().isNotNull();

    btnAdd.disableProperty().bind(filledForm.not());
    btnEdit.disableProperty().bind(filledForm.and(selectedItem).not());
    btnDelete.disableProperty().bind(selectedItem.not());
  }

  @FXML
  private void onAddGroup() {
    lstGroups.getItems().add(txtName.getText());
    txtName.setText(null);
    txtName.requestFocus();
  }

  @FXML
  private void onEditGroup() {
    var index = lstGroups.getSelectionModel().getSelectedIndex();
    lstGroups.getItems().set(index, txtName.getText());
    lstGroups.getSelectionModel().clearSelection();
  }

  @FXML
  private void onDeleteGroup() {
    var index = lstGroups.getSelectionModel().getSelectedIndex();
    lstGroups.getItems().remove(index);
    lstGroups.getSelectionModel().clearSelection();
  }

  // endregion

}
