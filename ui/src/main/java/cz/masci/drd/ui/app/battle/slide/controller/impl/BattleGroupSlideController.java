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

package cz.masci.drd.ui.app.battle.slide.controller.impl;

import cz.masci.commons.springfx.fxml.annotation.FxmlController;
import cz.masci.drd.ui.app.battle.slide.controller.BattleSlideController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.Optional;
import javafx.beans.binding.BooleanExpression;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
  private MFXTextField txtName;
  @FXML
  private MFXButton btnAdd;
  @FXML
  private MFXButton btnEdit;
  @FXML
  private MFXButton btnDelete;
  @FXML
  private MFXListView<String> lstGroups;

  // region scene

  @FXML
  private void initialize() {
    lstGroups.getSelectionModel().setAllowsMultipleSelection(false);
    btnAdd.setDisable(true);
    btnEdit.setDisable(true);
    btnDelete.setDisable(true);

    // init bindings

    BooleanExpression filledForm = txtName.textProperty().isNotEmpty();
    BooleanExpression selectedItem = lstGroups.getSelectionModel().selectionProperty().emptyProperty().not();

    btnAdd.disableProperty().bind(filledForm.not());
    btnEdit.disableProperty().bind(filledForm.and(selectedItem).not());
    btnDelete.disableProperty().bind(selectedItem.not());
  }

  @FXML
  private void onAddGroup() {
    if (!lstGroups.getItems().contains(txtName.getText())) {
      lstGroups.getItems().add(txtName.getText());
      txtName.setText("");
    }
    txtName.requestFocus();
  }

  @FXML
  private void onEditGroup() {
    if (!lstGroups.getItems().contains(txtName.getText())) {
      var selectedValueIndex = lstGroups.getItems().indexOf(lstGroups.getSelectionModel().getSelectedValue());
      if (selectedValueIndex >= 0) {
        lstGroups.getItems().set(selectedValueIndex, txtName.getText());
        lstGroups.getSelectionModel().clearSelection();
      }
    }
  }

  @FXML
  private void onDeleteGroup() {
    var selectedValue = Optional.ofNullable(lstGroups.getSelectionModel().getSelectedValue());
    selectedValue.ifPresent(value -> lstGroups.getItems().remove(value));
    lstGroups.getSelectionModel().clearSelection();
  }

  // endregion

  // region others
  public ObservableList<String> getGroups() {
    return lstGroups.getItems();
  }

  // endregion
}
