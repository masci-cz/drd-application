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
import cz.masci.drd.ui.adventure.control.AdventureDetailControl;
import cz.masci.springfx.annotation.FxmlController;
import cz.masci.springfx.controller.AbstractDetailController;
import cz.masci.springfx.service.ObservableListMap;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
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
@FxmlView("fxml/adventure-detail-editor.fxml")
@FxmlController
public class AdventureDetailEditorController extends AbstractDetailController<AdventureDTO> {

  @FXML
  private AdventureDetailControl editor;

  public AdventureDetailEditorController(ObservableListMap observableListMap) {
    super(observableListMap);
  }

  @Override
  protected List<ObservableValue<String>> initObservableValues() {
    return List.of(
        editor.nameProperty()
    );
  }

  @Override
  protected void fillInputs(AdventureDTO item) {
    if (item == null) {
      editor.setName("");
    } else {
      editor.setName(item.getName());
    }
  }

  @Override
  protected void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    if (editor.nameProperty().equals(observable)) {
      getItem().setName(newValue);
    }
  }

}
