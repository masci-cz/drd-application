/*
 * Copyright (c) 2023-2024
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

package cz.masci.drd.ui.app.battle.slide.controller.impl;

import cz.masci.commons.springfx.fxml.annotation.FxmlController;
import cz.masci.drd.dto.GroupDTO;
import cz.masci.drd.ui.app.battle.slide.controller.BattleSlideController;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@FxmlView("fxml/battle-initiative-slide.fxml")
@FxmlController
@Slf4j
public class BattleInitiativeSlideController implements BattleSlideController {

  @Getter
  @Setter
  private GroupDTO group;

  @FXML
  @Getter
  private GridPane root;
  @FXML
  private MFXTextField initiativeTxt;

  public ReadOnlyStringProperty initiativeProperty() {
    return initiativeTxt.textProperty();
  }

  public Integer getInitiative() {
    return StringUtils.isNotBlank(initiativeTxt.getText()) ? Integer.parseInt(initiativeTxt.getText()) : null;
  }
}
