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

package cz.masci.drd.ui.battle.slide.controller.impl;

import cz.masci.commons.springfx.fxml.annotation.FxmlController;
import cz.masci.drd.dto.DuellistDTO;
import cz.masci.drd.dto.GroupDTO;
import cz.masci.drd.ui.battle.slide.controller.BattleSlideController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.binding.BooleanExpression;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@FxmlView("fxml/battle-duellist-slide.fxml")
@FxmlController
@Slf4j
public class BattleDuellistSlideController implements BattleSlideController {

  @FXML
  @Getter
  private VBox root;
  @FXML
  private MFXButton btnAdd;
  @FXML
  private MFXButton btnEdit;
  @FXML
  private MFXButton btnDelete;
  @FXML
  private MFXTextField txtName;
  @FXML
  private MFXTextField txtOffense;
  @FXML
  private MFXTextField txtDefense;
  @FXML
  private MFXTextField txtDamage;
  @FXML
  private MFXTextField txtLive;
  @FXML
  private TableView<DuellistDTO> tblDuellist;

  @Setter
  @Getter
  private GroupDTO group;

  // region scene

  @FXML
  private void initialize() {
    btnAdd.setDisable(true);
    btnEdit.setDisable(true);
    btnDelete.setDisable(true);

    BooleanExpression filledForm = txtName.textProperty().isNotEmpty()
        .and(txtOffense.textProperty().isNotEmpty())
        .and(txtDefense.textProperty().isNotEmpty())
        .and(txtDamage.textProperty().isNotEmpty())
        .and(txtLive.textProperty().isNotEmpty());
    BooleanExpression selectedItem = tblDuellist.getSelectionModel().selectedItemProperty().isNotNull();

    // init buttons disable property
    btnAdd.disableProperty().bind(filledForm.not());
    btnEdit.disableProperty().bind(selectedItem.and(filledForm).not());
    btnDelete.disableProperty().bind(selectedItem.not());

    // init table columns
    TableColumn<DuellistDTO, String> colName = new TableColumn<>("Název");
    colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    colName.setPrefWidth(200);
    TableColumn<DuellistDTO, Integer> colOffense = new TableColumn<>("Útočné číslo");
    colOffense.setCellValueFactory(new PropertyValueFactory<>("attack"));
    TableColumn<DuellistDTO, Integer> colDefense = new TableColumn<>("Obranné číslo");
    colDefense.setCellValueFactory(new PropertyValueFactory<>("defense"));
    TableColumn<DuellistDTO, Integer> colDamage = new TableColumn<>("Útočnost");
    colDamage.setCellValueFactory(new PropertyValueFactory<>("damage"));
    TableColumn<DuellistDTO, Integer> colLive = new TableColumn<>("Životy");
    colLive.setCellValueFactory(new PropertyValueFactory<>("originalLive"));

    tblDuellist.getColumns().addAll(colName, colOffense, colDefense, colDamage, colLive);
  }

  @FXML
  private void onAddDuellist() {
    var duellist = new DuellistDTO();
    updateDuellist(duellist);

    if (tblDuellist.getItems().stream().map(DuellistDTO::getName).noneMatch(txtName.getText()::equals)) {
      tblDuellist.getItems().add(duellist);
      group.getDuellists().add(duellist);
    }
  }

  @FXML
  private void onEditDuellist() {
    var duellist = tblDuellist.getSelectionModel().getSelectedItem();
    var index = tblDuellist.getSelectionModel().getSelectedIndex();
    if (tblDuellist.getItems().stream().filter(item -> item != duellist).map(DuellistDTO::getName).noneMatch(txtName.getText()::equals)) {
      updateDuellist(duellist);
      tblDuellist.getItems().set(index, duellist);
      group.getDuellists().set(index, duellist);
    }
  }

  @FXML
  private void onDeleteDuellist() {
    var duellist = tblDuellist.getSelectionModel().getSelectedItem();
    tblDuellist.getItems().remove(duellist);
    group.getDuellists().remove(duellist);
  }

  private void updateDuellist(DuellistDTO duellist) {
    duellist.setName(txtName.getText());
    duellist.setAttack(Integer.parseInt(txtOffense.getText()));
    duellist.setDefense(Integer.parseInt(txtDefense.getText()));
    duellist.setDamage(Integer.parseInt(txtDamage.getText()));
    duellist.setOriginalLive(Integer.parseInt(txtLive.getText()));
    duellist.setCurrentLive(Integer.parseInt(txtLive.getText()));
  }

  // endregion

  // region others

  public ObservableList<DuellistDTO> getDuellists() {
    return tblDuellist.getItems();
  }

  // endregion
}
