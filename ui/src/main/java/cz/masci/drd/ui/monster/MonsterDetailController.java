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
import cz.masci.springfx.controller.AbstractDetailController;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import cz.masci.springfx.service.ObservableListMap;

/**
 *
 * @author Daniel
 */
@Component
@Slf4j
@FxmlView("fxml/monster-detail.fxml")
public class MonsterDetailController extends AbstractDetailController<MonsterDTO> {

  @FXML
  private Label name;

  @FXML
  private TextArea description;

  public MonsterDetailController(ObservableListMap observableListMap) {
    super(observableListMap);
  }
  
  @Override
  protected List<ObservableValue<String>> initObservableValues() {
    log.debug("Init observable values");

    return List.of(
            description.textProperty()
    );
  }

  @Override
  protected void fillInputs(MonsterDTO item) {
    log.debug("Fill inputs {}", item);

    if (item == null) {
      name.setText("");
      description.setText("");
    } else {
      name.setText(item.getName());
      description.setText(item.getDescription());
    }
  }

  @Override
  protected void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    log.debug("changes - newValue: {}", newValue);
    log.debug("changes - observable: {}", observable);
    
    if (description.textProperty().equals(observable)) {
      getItem().setDescription(newValue);
    }
  }

}
