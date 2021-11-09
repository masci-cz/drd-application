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

import cz.masci.drd.dto.MonsterDTO;
import cz.masci.springfx.service.EditControllerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/**
 *
 * @author Daniel
 */
@Component
@Slf4j
@Getter
@FxmlView("fxml/monster-edit.fxml")
public class MonsterEditController implements EditControllerService<MonsterDTO> {

  @FXML
  private DialogPane dialog;

  @FXML
  private TextField name;

  @FXML
  private TextArea description;

  public void initialize() {
    Button btOk = (Button) dialog.lookupButton(ButtonType.OK);
    btOk.addEventFilter(ActionEvent.ACTION, event -> {
      if (!validate()) {
        event.consume();
      }
    });
  }

  @Override
  public Callback<ButtonType, MonsterDTO> getResultConverter() {
    return (buttonType) -> {
      if (ButtonType.OK.equals(buttonType) && validate()) {
        return MonsterDTO.builder().name(name.getText()).description(description.getText()).build();
      }
      return null;
    };
  }

  private boolean validate() {
    return !name.getText().isBlank();
  }
}
