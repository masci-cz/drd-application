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
import cz.masci.drd.dto.DuellistDTO;
import cz.masci.drd.dto.GroupDTO;
import cz.masci.drd.service.BattleService;
import cz.masci.drd.ui.battle.slide.BattleSlideController;
import cz.masci.drd.ui.util.slide.SlideQueueService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableListValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@FxmlView("fxml/battle-duellist-slide.fxml")
@FxmlController
@Slf4j
public class BattleDuellistSlideController extends BasicBattleSlideController implements BattleSlideController {

  @FXML
  private Button btnAdd;
  @FXML
  private Button btnEdit;
  @FXML
  private Button btnDelete;
  @FXML
  private TextField txtName;
  @FXML
  private TextField txtOffense;
  @FXML
  private TextField txtDefense;
  @FXML
  private TextField txtLive;
  @FXML
  private TableView<DuellistDTO> tblDuellist;

  @Getter
  private final BooleanProperty lastGroup = new SimpleBooleanProperty();
  @Getter
  private final BooleanProperty firstGroup = new SimpleBooleanProperty();

  @Setter
  @Getter
  private GroupDTO group;

  // region interface

  @Override
  public void onAfterPrev(BattleService battleService, SlideQueueService<BattleSlideController, Node> slideService) {
    if (isFirstGroup()) {
      List<FxControllerAndView<?, ?>> slidesToRemove = new ArrayList<>();
      slideService.getSlides().forEach(nodeFxControllerAndView -> {
        var controller = nodeFxControllerAndView.getController();
        if (controller instanceof BattleDuellistSlideController) {
          slidesToRemove.add(nodeFxControllerAndView);
        }
      });
      slidesToRemove.forEach(slide -> slideService.getSlides().remove(slide));
      battleService.exitBattle();
    }
  }

  @Override
  public String getTitle() {
    return "Bojovníci skupiny: " + Objects.requireNonNullElse(group, new GroupDTO("UNDEFINED")).getName();
  }

  @Override
  public String getPrevTitle() {
    return isFirstGroup() ? "Zrušit bitvu" : "Předchozí";
  }

  @Override
  public String getNextTitle() {
    return isLastGroup() ? "Spustit bitvu" : "Další";
  }

  @Override
  public ObservableBooleanValue validNextProperty() {
    return Bindings.not(lastGroup).and(Bindings.size(tblDuellist.getItems()).greaterThanOrEqualTo(1));
  }

  public boolean isLastGroup() {
    return lastGroup.get();
  }

  public void setLastGroup(boolean value) {
    lastGroup.set(value);
  }

  public boolean isFirstGroup() {
    return firstGroup.get();
  }

  public void setFirstGroup(boolean value) {
    firstGroup.set(value);
  }

  public ObservableList<DuellistDTO> getDuellists() {
    return tblDuellist.getItems();
  }

  // endregion

  // region FX

  @FXML
  private void initialize() {
    btnAdd.setDisable(true);
    btnEdit.setDisable(true);
    btnDelete.setDisable(true);

    BooleanExpression filledForm = txtName.textProperty().isNotEmpty()
        .and(txtOffense.textProperty().isNotEmpty())
        .and(txtDefense.textProperty().isNotEmpty())
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
    TableColumn<DuellistDTO, Integer> colLive = new TableColumn<>("Životy");
    colLive.setCellValueFactory(new PropertyValueFactory<>("originalLive"));

    tblDuellist.getColumns().addAll(colName, colOffense, colDefense, colLive);
  }

  @FXML
  private void onAddDuellist() {
    var duellist = new DuellistDTO();
    updateDuellist(duellist);

    if (tblDuellist.getItems().stream().map(DuellistDTO::getName).noneMatch(txtName.getText()::equals)) {
      tblDuellist.getItems().add(duellist);
    }
  }

  @FXML
  private void onEditDuellist() {
    var duellist = tblDuellist.getSelectionModel().getSelectedItem();
    var index = tblDuellist.getSelectionModel().getSelectedIndex();
    if (tblDuellist.getItems().stream().filter(item -> item != duellist).map(DuellistDTO::getName).noneMatch(txtName.getText()::equals)) {
      updateDuellist(duellist);
      tblDuellist.getItems().set(index, duellist);
    }
  }

  @FXML
  private void onDeleteDuellist() {
    var duellist = tblDuellist.getSelectionModel().getSelectedItem();
    tblDuellist.getItems().remove(duellist);
  }

  private void updateDuellist(DuellistDTO duellist) {
    duellist.setName(txtName.getText());
    duellist.setAttack(Integer.parseInt(txtOffense.getText()));
    duellist.setDefense(Integer.parseInt(txtDefense.getText()));
    duellist.setOriginalLive(Integer.parseInt(txtLive.getText()));
  }

}
