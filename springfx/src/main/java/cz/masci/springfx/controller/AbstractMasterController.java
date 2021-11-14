/*
 * Copyright (C) 2021 Daniel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cz.masci.springfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import cz.masci.springfx.service.CrudService;
import cz.masci.springfx.service.EditControllerService;
import cz.masci.springfx.data.Modifiable;
import cz.masci.springfx.utility.StyleChangingRowFactory;
import java.util.List;
import javafx.collections.FXCollections;
import cz.masci.springfx.service.ObservableListMap;

/**
 * Abstract controller for master-detail view.
 * <p>
 * Displays table view with items loaded by the item service.
 * <p>
 * Displays three buttons for actions:
 * <ul>
 *   <li>new item</li>
 *   <li>save all</li>
 *   <li>delete</li
 * </ul>
 * <p>
 * 
 * @author Daniel Masek
 *
 * @param <T> Item type
 */
@Slf4j
@RequiredArgsConstructor
@FxmlView("fxml/master-view.fxml")
public abstract class AbstractMasterController<T extends Modifiable> {

  private final FxWeaver fxWeaver;
  private final CrudService<T> itemService;
  private final String itemKey;
  private final Class<? extends EditControllerService<T>> editControllerClass;
  private ObservableListMap observableListMap;

  @FXML
  protected BorderPane borderPane;

  @FXML
  protected TableView<T> tableView;

  @FXML
  protected VBox items;

  /**
   * Open edit dialog and save new item defined in edit controller.
   * 
   * @param event Action event
   */
  @FXML
  public void onNewItem(ActionEvent event) {
    log.debug("New item action occured");
    
    FxControllerAndView<? extends EditControllerService<T>, DialogPane> editor = fxWeaver.load(editControllerClass);
    Dialog<T> dialog = new Dialog<>();
    dialog.setTitle("New Item");
    dialog.setDialogPane(editor.getView().get());
    dialog.setResultConverter(editor.getController().getResultConverter());
    dialog.showAndWait()
            .ifPresent(item -> {
              var savedItem = itemService.save(item);
              tableView.getItems().add(savedItem);
              tableView.getSelectionModel().select(savedItem);
              tableView.scrollTo(savedItem);
            });
  }

  /**
   * Get modified item list from observable list map and save them all.
   * At the end removes them from observable list map.
   * <p>
   * Open alert dialog.
   * 
   * @param event Action event
   */
  @FXML
  public void onSaveAll(ActionEvent event) {
    log.debug("Save all action occured");
    
    Alert alert = new Alert(AlertType.INFORMATION, "Saving all items");
    alert.showAndWait().ifPresent(button -> {
      List<T> modifiedItemList = observableListMap.getAll(itemKey);
      modifiedItemList.stream()
              .forEach(item -> {
                itemService.save(item);
                observableListMap.remove(item);
              });
    });
  }

  /**
   * Open alert dialog and delete selected item.
   * 
   * @param event Action event
   */
  @FXML
  public void onDelete(ActionEvent event) {
    log.debug("Delete action occured");
    
    Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure to delete selected item?");
    alert.showAndWait().ifPresent(button -> {
      T item = tableView.getSelectionModel().getSelectedItem();
      tableView.getItems().remove(item);
      itemService.delete(item);
      observableListMap.remove(item);
    });
  }

  /**
   * Set observable list map.
   * <p>
   * It is set by Spring injection.
   * 
   * @param observableListMap Observable list map to set
   */
  @Autowired
  public final void setObservableListMap(ObservableListMap observableListMap) {
    this.observableListMap = observableListMap;
  }

  /**
   * Initialiaze FX controller.
   * <p>
   * Load items from item service and set in table view.
   */
  public final void initialize() {
    log.debug("Initialize");
    
    init();
    var newList = FXCollections.<T>observableArrayList();
    newList.addAll(itemService.list());
    tableView.setItems(newList);
  }

  /**
   * Clear table view collumns.
   */
  public void clearCollumns() {
    log.trace("Clear table view collumns");
    
    tableView.getColumns().clear();
  }

  /**
   * Add table view collumns.
   * 
   * @param collumns Collumns to add
   */
  public void addCollumns(TableColumn<T, ?>... collumns) {
    log.trace("Add table view collumns: {}", collumns);
    
    tableView.getColumns().addAll(collumns);
  }

  /**
   * Set the detail controller.
   * 
   * @param <E> Detail controller type
   * @param detailController Controller to set
   */
  public <E extends AbstractDetailController<T>> void setDetailController(Class<E> detailController) {
    FxControllerAndView<E, Node> detailView = fxWeaver.load(detailController);
    
    borderPane.setCenter(detailView.getView().get());
    detailView.getController().setItemKey(itemKey);

    tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      detailView.getController().setItem(newValue);
    });

  }

  protected void setRowFactory(String styleClass) {
    tableView.setRowFactory(new StyleChangingRowFactory<>(styleClass, itemKey, observableListMap));
  }

  /**
   * Delegate titles and collumns initialization to subclass.
   */
  protected abstract void init();

}
