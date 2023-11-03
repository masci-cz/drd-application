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

import cz.masci.commons.springfx.fxml.annotation.FxmlController;
import cz.masci.commons.springfx.service.EditDialogControllerService;
import cz.masci.drd.dto.WeaponDTO;
import cz.masci.drd.ui.adventure.control.WeaponDetailControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.util.Callback;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
@Component
@Getter
@FxmlView("fxml/weapon-detail-dialog.fxml")
@FxmlController
public class WeaponDetailDialogController implements EditDialogControllerService<WeaponDTO> {

  @FXML
  private DialogPane dialog;

  @FXML
  private WeaponDetailControl editor;

  /**
   * Initializes the controller class.
   */
  public void initialize() {
    Button btOk = (Button) dialog.lookupButton(ButtonType.OK);
    btOk.addEventFilter(ActionEvent.ACTION, event -> {
      if (editor.isInvalid()) {
        event.consume();
      }
    });
    btOk.disableProperty().bind(editor.invalidProperty());
  }

  @Override
  public Callback<ButtonType, WeaponDTO> getResultConverter() {
    return (buttonType) -> {
      if (ButtonType.OK.equals(buttonType) && !editor.isInvalid()) {
        var weapon = new WeaponDTO();

        try {
          weapon.setName(editor.getName());
          weapon.setStrength(Integer.parseInt(editor.getStrength()));
          weapon.setDamage(Integer.parseInt(editor.getDamage()));
        } catch (NumberFormatException numberFormatException) {
          Alert alert = new Alert(Alert.AlertType.ERROR, "Chyba konverze");
          alert.showAndWait();
          return null;
        }
        
        return weapon;
      }

      return null;
    };
  }

}
