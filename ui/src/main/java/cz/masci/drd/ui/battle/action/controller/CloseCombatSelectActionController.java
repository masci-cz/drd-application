/*
 * Copyright (c) 2023
 *
 * This file is part of DrD.
 *
 * DrD is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free
 *  Software Foundation, either version 3 of the License, or (at your option)
 *   any later version.
 *
 * DrD is distributed in the hope that it will be useful, but WITHOUT ANY
 *  WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *   FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public
 *    License for more details.
 *
 * You should have received a copy of the GNU General Public License
 *  along with Foobar. If not, see <https://www.gnu.org/licenses/>.
 */

package cz.masci.drd.ui.battle.action.controller;

import cz.masci.commons.springfx.fxml.annotation.FxmlController;
import cz.masci.drd.dto.DuellistDTO;
import cz.masci.drd.ui.converter.DuellistStringConverter;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.util.List;
import javafx.fxml.FXML;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@FxmlView("fxml/close-combat-select-action.fxml")
@FxmlController
@Getter
public class CloseCombatSelectActionController implements MultipleSelectActionController {

  @FXML
  MFXComboBox<DuellistDTO> duellistBox;

  @FXML
  void initialize() {
    duellistBox.setConverter(new DuellistStringConverter("Vyberte obránce"));
  }

  public void initDuellists(List<DuellistDTO> duellists) {
    duellistBox.getItems().addAll(duellists);
  }


}
