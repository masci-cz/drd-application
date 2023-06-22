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
import cz.masci.drd.ui.battle.slide.controller.BattleSlideController;
import javafx.beans.binding.BooleanExpression;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import lombok.Getter;
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
public class BattleGroupSlideController implements BattleSlideController {

  @FXML
  @Getter
  private VBox root;
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

  // region scene

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
    if (!lstGroups.getItems().contains(txtName.getText())) {
      lstGroups.getItems().add(txtName.getText());
      txtName.setText(null);
    }
    txtName.requestFocus();
  }

  @FXML
  private void onEditGroup() {
    if (!lstGroups.getItems().contains(txtName.getText())) {
      var index = lstGroups.getSelectionModel().getSelectedIndex();
      lstGroups.getItems().set(index, txtName.getText());
      lstGroups.getSelectionModel().clearSelection();
    }
  }

  @FXML
  private void onDeleteGroup() {
    var index = lstGroups.getSelectionModel().getSelectedIndex();
    lstGroups.getItems().remove(index);
    lstGroups.getSelectionModel().clearSelection();
  }

  // endregion

  // region others
  public ObservableList<String> getGroups() {
    return lstGroups.getItems();
  }

  // endregion
}
