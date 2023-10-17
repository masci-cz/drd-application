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
import cz.masci.drd.dto.AdventureDTO;
import cz.masci.drd.ui.adventure.control.AdventureDetailControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.util.Callback;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
@Component
@Slf4j
@Getter
@FxmlView("fxml/adventure-detail-dialog.fxml")
@FxmlController
public class AdventureDetailDialogController implements EditDialogControllerService<AdventureDTO> {

  @FXML
  private DialogPane dialog;

  @FXML
  private AdventureDetailControl editor;

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
  public Callback<ButtonType, AdventureDTO> getResultConverter() {
    return (buttonType) -> {
      if (ButtonType.OK.equals(buttonType) && !editor.isInvalid()) {
        var adventure = new AdventureDTO();

        adventure.setName(editor.getName());

        return adventure;
      }
      
      return null;
    };
  }

}
