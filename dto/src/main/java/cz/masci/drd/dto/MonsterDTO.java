/*
 * Copyright (C) 2021 Daniel
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cz.masci.drd.dto;

import cz.masci.springfx.data.Modifiable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Daniel
 */
public class MonsterDTO implements Modifiable {

  private LongProperty idProperty;
  /**
   * Monster name - Jméno nestvůry
   */
  private StringProperty nameProperty;
  /**
   * Monster viability - Životaschopnost
   */
  private StringProperty viabilityProperty;
  /**
   * Monster attack - Útočné číslo
   */
  private StringProperty attackProperty;
  /**
   * Monster defence - Obranné číslo
   */
  private StringProperty defenceProperty;
  /**
   * Monster endurance - Odolnost
   */
  private IntegerProperty enduranceProperty;
  /**
   * Monster dimension - Velikost
   */
  private StringProperty dimensionProperty;
  /**
   * Monster combativeness - Bojovnost
   */
  private ObjectPropertyBase<Integer> combativenessProperty;
  /**
   * Monster vulnerability - Zranitelnost
   */
  private StringProperty vulnerabilityProperty;
  /**
   * Monster moveability - Pohyblivost
   */
  private StringProperty moveabilityProperty;
  /**
   * Monster stamina - Vytrvalost
   */
  private StringProperty staminaProperty;
  /**
   * Monster intelligence - Inteligence
   */
  private IntegerProperty intelligenceProperty;
  /**
   * Monster conviction - Přesvědčení
   */
  private ObjectPropertyBase<Integer> convictionProperty;
  /**
   * Monster treasure - Poklady
   */
  private StringProperty treasureProperty;
  /**
   * Monster experience - Zkušenost
   */
  private StringProperty experienceProperty;
  /**
   * Monster description - Popis
   */
  private StringProperty descriptionProperty;

  public final LongProperty idProperty() {
    if (idProperty == null) {
      idProperty = new SimpleLongProperty();
    }
    return idProperty;
  }

  public Long getId() {
    return idProperty().get();
  }

  public void setId(Long id) {
    idProperty().set(id);
  }

  public final StringProperty nameProperty() {
    if (nameProperty == null) {
      nameProperty = new SimpleStringProperty();
    }
    return nameProperty;
  }

  public String getName() {
    return nameProperty().get();
  }

  public void setName(String name) {
    nameProperty().set(name);
  }

  public final StringProperty viabilityProperty() {
    if (viabilityProperty == null) {
      viabilityProperty = new SimpleStringProperty();
    }
    return viabilityProperty;
  }

  public String getViability() {
    return viabilityProperty().get();
  }

  public void setViability(String viability) {
    viabilityProperty().set(viability);
  }

  public final StringProperty attackProperty() {
    if (attackProperty == null) {
      attackProperty = new SimpleStringProperty();
    }
    return attackProperty;
  }

  public String getAttack() {
    return attackProperty().get();
  }

  public void setAttack(String attack) {
    attackProperty().set(attack);
  }

  public final StringProperty defenceProperty() {
    if (defenceProperty == null) {
      defenceProperty = new SimpleStringProperty();
    }
    return defenceProperty;
  }

  public String getDefence() {
    return defenceProperty().get();
  }

  public void setDefence(String defence) {
    defenceProperty().set(defence);
  }

  public final IntegerProperty enduranceProperty() {
    if (enduranceProperty == null) {
      enduranceProperty = new SimpleIntegerProperty();
    }
    return enduranceProperty;
  }

  public Integer getEndurance() {
    return enduranceProperty().get();
  }

  public void setEndurance(Integer endurance) {
    enduranceProperty().set(endurance);
  }

  public final StringProperty dimensionProperty() {
    if (dimensionProperty == null) {
      dimensionProperty = new SimpleStringProperty();
    }
    return dimensionProperty;
  }

  public String getDimension() {
    return dimensionProperty().get();
  }

  public void setDimension(String dimension) {
    dimensionProperty().set(dimension);
  }

  public final ObjectPropertyBase<Integer> combativenessProperty() {
    if (combativenessProperty == null) {
      combativenessProperty = new SimpleObjectProperty<>();
    }
    return combativenessProperty;
  }

  public Integer getCombativeness() {
    return combativenessProperty().get();
  }

  public void setCombativeness(Integer combativeness) {
    combativenessProperty().set(combativeness);
  }

  public final StringProperty vulnerabilityProperty() {
    if (vulnerabilityProperty == null) {
      vulnerabilityProperty = new SimpleStringProperty();
    }
    return vulnerabilityProperty;
  }

  public String getVulnerability() {
    return vulnerabilityProperty().get();
  }

  public void setVulnerability(String vulnerability) {
    vulnerabilityProperty().set(vulnerability);
  }

  public final StringProperty moveabilityProperty() {
    if (moveabilityProperty == null) {
      moveabilityProperty = new SimpleStringProperty();
    }
    return moveabilityProperty;
  }

  public String getMoveability() {
    return moveabilityProperty().get();
  }

  public void setMoveability(String moveability) {
    moveabilityProperty().set(moveability);
  }

  public final StringProperty staminaProperty() {
    if (staminaProperty == null) {
      staminaProperty = new SimpleStringProperty();
    }
    return staminaProperty;
  }

  public String getStamina() {
    return staminaProperty().get();
  }

  public void setStamina(String stamina) {
    staminaProperty().set(stamina);
  }

  public final IntegerProperty intelligenceProperty() {
    if (intelligenceProperty == null) {
      intelligenceProperty = new SimpleIntegerProperty();
    }
    return intelligenceProperty;
  }

  public Integer getIntelligence() {
    return intelligenceProperty().get();
  }

  public void setIntelligence(Integer intelligence) {
    intelligenceProperty().set(intelligence);
  }

  public final ObjectPropertyBase<Integer> convictionProperty() {
    if (convictionProperty == null) {
      convictionProperty = new SimpleObjectProperty<>();
    }
    return convictionProperty;
  }

  public Integer getConviction() {
    return convictionProperty().get();
  }

  public void setConviction(Integer conviction) {
    convictionProperty().set(conviction);
  }

  public final StringProperty treasureProperty() {
    if (treasureProperty == null) {
      treasureProperty = new SimpleStringProperty();
    }
    return treasureProperty;
  }

  public String getTreasure() {
    return treasureProperty().get();
  }

  public void setTreasure(String treasure) {
    treasureProperty().set(treasure);
  }

  public final StringProperty experienceProperty() {
    if (experienceProperty == null) {
      experienceProperty = new SimpleStringProperty();
    }
    return experienceProperty;
  }

  public String getExperience() {
    return experienceProperty().get();
  }

  public void setExperience(String experience) {
    experienceProperty().set(experience);
  }

  public final StringProperty descriptionProperty() {
    if (descriptionProperty == null) {
      descriptionProperty = new SimpleStringProperty();
    }
    return descriptionProperty;
  }

  public String getDescription() {
    return descriptionProperty().get();
  }

  public void setDescription(String description) {
    descriptionProperty().set(description);
  }
}
