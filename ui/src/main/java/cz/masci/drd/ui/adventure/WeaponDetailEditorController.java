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
import cz.masci.drd.ui.adventure.control.WeaponDetailControl;
import cz.masci.springfx.annotation.FxmlController;
import cz.masci.springfx.controller.AbstractDetailController;
import cz.masci.springfx.service.ObservableListMap;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
@Component
@FxmlView("fxml/weapon-detail-editor.fxml")
@FxmlController
public class WeaponDetailEditorController extends AbstractDetailController<WeaponDTO> {

  @FXML
  private WeaponDetailControl editor;

  public WeaponDetailEditorController(ObservableListMap observableListMap) {
    super(observableListMap);
  }
  
  @Override
  protected List<ObservableValue<String>> initObservableValues() {
    return List.of(
        editor.nameProperty(),
        editor.strengthProperty(),
        editor.damageProperty()
    );
  }

  @Override
  protected void fillInputs(WeaponDTO item) {
    if (item == null) {
      editor.setName("");
      editor.setStrength("");
      editor.setDamage("");
    } else {
      editor.setName(item.getName());
      editor.setStrength(item.getStrength().toString());
      editor.setDamage(item.getDamage().toString());
    }
  }

  @Override
  protected void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    if (editor.nameProperty().equals(observable)) {
      getItem().setName(newValue);
    }
    
    if (editor.strengthProperty().equals(observable)) {
      getItem().setStrength(Integer.parseInt(newValue));
    }
    
    if (editor.damageProperty().equals(observable)) {
      getItem().setDamage(Integer.parseInt(newValue));
    }
  }

  
}