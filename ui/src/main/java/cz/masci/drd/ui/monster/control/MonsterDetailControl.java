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
package cz.masci.drd.ui.monster.control;

import cz.masci.springfx.annotation.FxmlRoot;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniel
 */
@Service
@Scope("prototype")
@FxmlRoot("fxml/monster-detail.fxml")
public class MonsterDetailControl extends GridPane {

  @FXML
  private TextField name;
  @FXML
  private TextField viability;
  @FXML
  private TextField attack;
  @FXML
  private TextField defence;
  @FXML
  private TextField endurance;
  @FXML
  private TextField dimension;
  @FXML
  private TextField combativeness;
  @FXML
  private TextField vulnerability;
  @FXML
  private TextField moveability;
  @FXML
  private TextField stamina;
  @FXML
  private TextField intelligence;
  @FXML
  private TextField conviction;
  @FXML
  private TextField treasure;
  @FXML
  private TextField experience;
  @FXML
  private TextArea description;

  public StringProperty nameProperty() {
    return name.textProperty();
  }
  
  public StringProperty viabilityProperty() {
    return viability.textProperty();
  }
  
  public StringProperty attackProperty() {
    return attack.textProperty();
  }
  
  public StringProperty defenceProperty() {
    return defence.textProperty();
  }
  
  public StringProperty enduranceProperty() {
    return endurance.textProperty();
  }
  
  public StringProperty dimensionProperty() {
    return dimension.textProperty();
  }
  
  public StringProperty combativenessProperty() {
    return combativeness.textProperty();
  }
  
  public StringProperty vulnerabilityProperty() {
    return vulnerability.textProperty();
  }
  
  public StringProperty moveabilityProperty() {
    return moveability.textProperty();
  }
  
  public StringProperty staminaProperty() {
    return stamina.textProperty();
  }
  
  public StringProperty intelligenceProperty() {
    return intelligence.textProperty();
  }
  
  public StringProperty convictionProperty() {
    return conviction.textProperty();
  }
  
  public StringProperty treasureProperty() {
    return treasure.textProperty();
  }
  
  public StringProperty experienceProperty() {
    return experience.textProperty();
  }
  
  public StringProperty descriptionProperty() {
    return description.textProperty();
  }

  public String getName() {
    return nameProperty().get();
  }

  public void setName(String name) {
    nameProperty().set(name);
  }

  public String getViability() {
    return viabilityProperty().get();
  }

  public void setViability(String viability) {
    viabilityProperty().set(viability);
  }

  public String getAttack() {
    return attackProperty().get();
  }

  public void setAttack(String attack) {
    attackProperty().set(attack);
  }

  public String getDefence() {
    return defenceProperty().get();
  }

  public void setDefence(String defence) {
    defenceProperty().set(defence);
  }

  public String getEndurance() {
    return enduranceProperty().get();
  }

  public void setEndurance(String endurance) {
    enduranceProperty().set(endurance);
  }

  public String getDimension() {
    return dimensionProperty().get();
  }

  public void setDimension(String dimension) {
    dimensionProperty().set(dimension);
  }

  public String getCombativeness() {
    return combativenessProperty().get();
  }

  public void setCombativeness(String combativeness) {
    combativenessProperty().set(combativeness);
  }

  public String getVulnerability() {
    return vulnerabilityProperty().get();
  }

  public void setVulnerability(String vulnerability) {
    vulnerabilityProperty().set(vulnerability);
  }

  public String getMoveability() {
    return moveabilityProperty().get();
  }

  public void setMoveability(String moveability) {
    moveabilityProperty().set(moveability);
  }

  public String getStamina() {
    return staminaProperty().get();
  }

  public void setStamina(String stamina) {
    staminaProperty().set(stamina);
  }

  public String getIntelligence() {
    return intelligenceProperty().get();
  }

  public void setIntelligence(String intelligence) {
    intelligenceProperty().set(intelligence);
  }

  public String getConviction() {
    return convictionProperty().get();
  }

  public void setConviction(String conviction) {
    convictionProperty().set(conviction);
  }

  public String getTreasure() {
    return treasureProperty().get();
  }

  public void setTreasure(String treasure) {
    treasureProperty().set(treasure);
  }

  public String getExperience() {
    return experienceProperty().get();
  }

  public void setExperience(String experience) {
    experienceProperty().set(experience);
  }

  public String getDescription() {
    return descriptionProperty().get();
  }

  public void setDescription(String description) {
    descriptionProperty().set(description);
  }
  
  
}
