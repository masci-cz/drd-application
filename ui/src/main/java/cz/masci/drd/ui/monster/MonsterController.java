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
package cz.masci.drd.ui.monster;

import cz.masci.commons.springfx.controller.AbstractMasterController;
import cz.masci.commons.springfx.fxml.annotation.FxmlController;
import cz.masci.drd.dto.MonsterDTO;
import cz.masci.drd.service.MonsterService;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;

/**
 *
 * @author Daniel
 */
@Component
@Slf4j
@FxmlController
public class MonsterController extends AbstractMasterController<MonsterDTO> {

  private TableColumn<MonsterDTO, String> name;

  private TableColumn<MonsterDTO, String> description;

  public MonsterController(FxWeaver fxWeaver, MonsterService itemService) {
    super(fxWeaver, itemService, MonsterDetailDialogController.class);
  }

  @Override
  protected void init() {
    log.debug("Init table view");

    name = new TableColumn<>("Jméno");
    name.setPrefWidth(100.0);
    name.setCellValueFactory(new PropertyValueFactory<>("name"));

    description = new TableColumn<>("Popis");
    description.setPrefWidth(200.0);
    description.setCellValueFactory(new PropertyValueFactory<>("description"));

    addColumns(name, description);

    setDetailController(MonsterDetailEditorController.class);
    setRowFactory("edited-row");
  }

}
