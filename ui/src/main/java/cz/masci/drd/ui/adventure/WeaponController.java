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

import cz.masci.commons.springfx.controller.AbstractMFXMasterController;
import cz.masci.commons.springfx.controller.AbstractMasterController;
import cz.masci.commons.springfx.fxml.annotation.FxmlController;
import cz.masci.commons.springfx.service.CrudService;
import cz.masci.drd.dto.WeaponDTO;
import cz.masci.drd.ui.util.MFXTableRowCellFactory;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
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
public class WeaponController extends AbstractMFXMasterController<WeaponDTO> {

  public WeaponController(FxWeaver fxWeaver, CrudService<WeaponDTO> itemService) {
    super(fxWeaver, itemService, WeaponDetailDialogController.class);
  }

  
  @Override
  protected void init() {
    MFXTableColumn<WeaponDTO> name = new MFXTableColumn<>("Název");
    name.setPrefWidth(150.0);
    name.setRowCellFactory(new MFXTableRowCellFactory<>(WeaponDTO::getName));

    MFXTableColumn<WeaponDTO> strength = new MFXTableColumn<>("Útočné číslo");
    strength.setPrefWidth(100.0);
    strength.setRowCellFactory(new MFXTableRowCellFactory<>(WeaponDTO::getStrength));

    MFXTableColumn<WeaponDTO> damage = new MFXTableColumn<>("Útočnost");
    damage.setPrefWidth(100.0);
    damage.setRowCellFactory(new MFXTableRowCellFactory<>(WeaponDTO::getDamage));
    
    addColumns(name, strength, damage);
    
    setDetailController(WeaponDetailEditorController.class);
    setRowFactory("edited-row");
  }
  
}
