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

import cz.masci.drd.dto.WeaponDTO;
import cz.masci.springfx.annotation.FxmlController;
import cz.masci.springfx.controller.AbstractMasterController;
import cz.masci.springfx.service.CrudService;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;

/**
 *
 * @author Daniel
 */
@Component
@FxmlController
public class WeaponController extends AbstractMasterController<WeaponDTO> {

  private TableColumn<WeaponDTO, String> name;
  private TableColumn<WeaponDTO, Integer> strength;
  private TableColumn<WeaponDTO, Integer> damage;

  public WeaponController(FxWeaver fxWeaver, CrudService<WeaponDTO> itemService) {
    super(fxWeaver, itemService, WeaponDTO.class.getSimpleName(), WeaponDetailDialogController.class);
  }

  
  @Override
  protected void init() {
    name = new TableColumn<>("Název");
    name.setPrefWidth(150.0);
    name.setCellValueFactory(new PropertyValueFactory<>("name"));
    
    strength = new TableColumn<>("Útočné číslo");
    strength.setPrefWidth(100.0);
    strength.setCellValueFactory(new PropertyValueFactory<>("strength"));
    
    damage = new TableColumn<>("Útočnost");
    damage.setPrefWidth(100.0);
    damage.setCellValueFactory(new PropertyValueFactory<>("damage"));
    
    addCollumns(name, strength, damage);
    
    setDetailController(WeaponDetailEditorController.class);
    setRowFactory("edited-row");
  }
  
}
