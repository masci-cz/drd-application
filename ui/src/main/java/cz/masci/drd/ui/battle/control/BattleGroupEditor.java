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

package cz.masci.drd.ui.battle.control;

import cz.masci.commons.springfx.fxml.annotation.FxmlController;
import java.util.List;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@FxmlView("fxml/battle-group-editor.fxml")
@FxmlController
public class BattleGroupEditor {

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
  private final BooleanProperty validProperty = new SimpleBooleanProperty();
//  private final ObservableList<String> groups = FXCollections.observableList(new ArrayList<>());

  public BooleanProperty validProperty() {
    return validProperty;
  }

  public String getTitle() {
    return "Skupiny";
  }

  public List<String> getGroupNames() {
    return lstGroups.getItems().stream().toList();
  }

  @FXML
  private void initialize() {
    btnAdd.setDisable(true);
    btnEdit.setDisable(true);
    btnDelete.setDisable(true);

    // init list
//    lstGroups.setItems(groups);

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
//      groups.add(txtName.getText());
      lstGroups.getItems().add(txtName.getText());
      txtName.setText(null);
      txtName.requestFocus();
    }
  }

  @FXML
  private void onEditGroup(ActionEvent event) {
    if (isFilledName() && isSelectedName()) {
      var index = lstGroups.getSelectionModel().getSelectedIndex();
//      groups.set(index, txtName.getText());
      lstGroups.getItems().set(index, txtName.getText());
      lstGroups.getSelectionModel().clearSelection();
    }
  }

  @FXML
  private void onDeleteGroup(ActionEvent event) {
    if (isSelectedName()) {
      var index = lstGroups.getSelectionModel().getSelectedIndex();
//      groups.remove(index);
      lstGroups.getItems().remove(index);
      lstGroups.getSelectionModel().clearSelection();
    }
  }

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
        validProperty.setValue(change.getList().size() > 1);
  }

  private boolean isFilledName() {
    return filledNameProperty.get();
  }

  private boolean isSelectedName() {
    return selectedNameProperty.get();
  }
}
