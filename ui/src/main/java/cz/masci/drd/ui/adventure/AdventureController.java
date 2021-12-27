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
package cz.masci.drd.ui.adventure;

import cz.masci.drd.dto.AdventureDTO;
import cz.masci.springfx.annotation.FxmlController;
import cz.masci.springfx.controller.AbstractMasterController;
import cz.masci.springfx.service.CrudService;
import javafx.scene.control.TableColumn;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Daniel
 */
@Component
@Slf4j
@FxmlController
public class AdventureController extends AbstractMasterController<AdventureDTO> {

  private TableColumn<AdventureDTO, String> name;

  public AdventureController(FxWeaver fxWeaver, CrudService<AdventureDTO> itemService) {
    super(fxWeaver, itemService, AdventureDTO.class.getSimpleName(), AdventureDetailDialogController.class);
  }
  
  
  @Override
  protected void init() {
    name = new TableColumn<>("Název");
    name.setPrefWidth(250);
    name.setCellValueFactory(new PropertyValueFactory<>("name"));
    
    addCollumns(name);
    
    setDetailController(AdventureDetailEditorController.class);
    setRowFactory("edited-row");
  }

}