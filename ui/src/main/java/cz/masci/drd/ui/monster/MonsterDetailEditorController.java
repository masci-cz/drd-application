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

import cz.masci.commons.springfx.controller.AbstractDetailController;
import cz.masci.commons.springfx.fxml.annotation.FxmlController;
import cz.masci.drd.dto.MonsterDTO;
import cz.masci.drd.ui.monster.control.MonsterDetailControl;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/**
 *
 * @author Daniel
 */
@Component
@Slf4j
@FxmlView("fxml/monster-detail-editor.fxml")
@FxmlController
public class MonsterDetailEditorController extends AbstractDetailController<MonsterDTO> {

  @FXML
  private MonsterDetailControl editor;

  @Override
  protected List<ObservableValue<String>> initObservableValues() {
    log.debug("Init observable values");

    return List.of(
            editor.viabilityProperty(),
            editor.attackProperty(),
            editor.defenceProperty(),
            editor.enduranceProperty(),
            editor.dimensionProperty(),
            editor.combativenessProperty(),
            editor.vulnerabilityProperty(),
            editor.moveabilityProperty(),
            editor.staminaProperty(),
            editor.intelligenceProperty(),
            editor.convictionProperty(),
            editor.treasureProperty(),
            editor.experienceProperty(),
            editor.descriptionProperty()
    );
  }

  @Override
  protected void fillInputs(MonsterDTO item) {
    log.debug("Fill inputs {}", item);

    if (item == null) {
      editor.setName("");
      editor.setViability("");
      editor.setAttack("");
      editor.setDefence("");
      editor.setEndurance("");
      editor.setDimension("");
      editor.setCombativeness("");
      editor.setVulnerability("");
      editor.setMoveability("");
      editor.setStamina("");
      editor.setIntelligence("");
      editor.setConviction("");
      editor.setTreasure("");
      editor.setExperience("");
      editor.setDescription("");
    } else {
      editor.setName(item.getName());
      editor.setViability(item.getViability());
      editor.setAttack(item.getAttack());
      editor.setDefence(item.getDefence());
      editor.setEndurance(item.getEndurance().toString());
      editor.setDimension(item.getDimension());
      editor.setCombativeness(item.getCombativeness().toString());
      editor.setVulnerability(item.getVulnerability());
      editor.setMoveability(item.getMoveability());
      editor.setStamina(item.getStamina());
      editor.setIntelligence(item.getIntelligence().toString());
      editor.setConviction(item.getConviction().toString());
      editor.setTreasure(item.getTreasure());
      editor.setExperience(item.getExperience());
      editor.setDescription(item.getDescription());
    }
  }

  @Override
  protected void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    log.debug("changes - newValue: {}", newValue);
    log.debug("changes - observable: {}", observable);

    if (editor.viabilityProperty().equals(observable)) {
      getItem().setViability(newValue);
    }
    if (editor.attackProperty().equals(observable)) {
      getItem().setAttack(newValue);
    }
    if (editor.defenceProperty().equals(observable)) {
      getItem().setDefence(newValue);
    }
    if (editor.enduranceProperty().equals(observable)) {
      getItem().setEndurance(Integer.parseInt(newValue));
    }
    if (editor.dimensionProperty().equals(observable)) {
      getItem().setDimension(newValue);
    }
    if (editor.combativenessProperty().equals(observable)) {
      getItem().setCombativeness(Integer.parseInt(newValue));
    }
    if (editor.vulnerabilityProperty().equals(observable)) {
      getItem().setVulnerability(newValue);
    }
    if (editor.moveabilityProperty().equals(observable)) {
      getItem().setMoveability(newValue);
    }
    if (editor.staminaProperty().equals(observable)) {
      getItem().setStamina(newValue);
    }
    if (editor.intelligenceProperty().equals(observable)) {
      getItem().setIntelligence(Integer.parseInt(newValue));
    }
    if (editor.convictionProperty().equals(observable)) {
      getItem().setConviction(Integer.parseInt(newValue));
    }
    if (editor.treasureProperty().equals(observable)) {
      getItem().setTreasure(newValue);
    }
    if (editor.experienceProperty().equals(observable)) {
      getItem().setExperience(newValue);
    }
    if (editor.descriptionProperty().equals(observable)) {
      getItem().setDescription(newValue);
    }
  }

}
