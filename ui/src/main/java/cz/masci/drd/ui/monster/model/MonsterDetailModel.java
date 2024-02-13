/*
 * Copyright (c) 2024
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

package cz.masci.drd.ui.monster.model;

import cz.masci.drd.ui.util.ConstraintUtils;
import cz.masci.springfx.mvci.model.detail.DetailModel;
import cz.masci.springfx.mvci.model.dirty.DirtyStringProperty;
import io.github.palexdev.materialfx.validation.MFXValidator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;

public class MonsterDetailModel implements DetailModel<Long> {

  private final ObjectProperty<Long> id = new SimpleObjectProperty<>();
  /** Monster name - Jméno nestvůry */
  private final DirtyStringProperty name = new DirtyStringProperty("");
  /** Monster viability - Životaschopnost */
  private final DirtyStringProperty viability = new DirtyStringProperty("");
  /** Monster attack - Útočné číslo */
  private final DirtyStringProperty attack = new DirtyStringProperty("");
  /** Monster defence - Obranné číslo */
  private final DirtyStringProperty defence = new DirtyStringProperty("");
  /** Monster endurance - Odolnost */
  private final DirtyStringProperty endurance = new DirtyStringProperty("");
  /** Monster dimension - Velikost */
  private final DirtyStringProperty dimension = new DirtyStringProperty("");
  /** Monster combativeness - Bojovnost */
  private final DirtyStringProperty combativeness = new DirtyStringProperty("");
  /** Monster vulnerability - Zranitelnost */
  private final DirtyStringProperty vulnerability = new DirtyStringProperty("");
  /** Monster moveability - Pohyblivost */
  private final DirtyStringProperty moveability = new DirtyStringProperty("");
  /** Monster stamina - Vytrvalost */
  private final DirtyStringProperty stamina = new DirtyStringProperty("");
  /** Monster intelligence - Inteligence */
  private final DirtyStringProperty intelligence = new DirtyStringProperty("");
  /** Monster conviction - Přesvědčení */
  private final DirtyStringProperty conviction = new DirtyStringProperty("");
  /** Monster treasure - Poklady */
  private final DirtyStringProperty treasure = new DirtyStringProperty("");
  /** Monster experience - Zkušenost */
  private final DirtyStringProperty experience = new DirtyStringProperty("");
  /** Monster description - Popis */
  private final DirtyStringProperty description = new DirtyStringProperty("");

  @Getter
  private final CompositeDirtyProperty composite = new CompositeDirtyProperty();
  @Getter
  private final MFXValidator validator = new MFXValidator();

  public MonsterDetailModel() {
    composite.addAll(name, viability, attack, defence, endurance, dimension, combativeness, vulnerability, moveability,
        stamina, intelligence, conviction, treasure, experience, description);
    validator.constraint(ConstraintUtils.isNotEmpty(name, "Jméno"));
    validator.constraint(ConstraintUtils.isNotEmpty(viability, "Životaschopnost"));
    validator.constraint(ConstraintUtils.isNotEmpty(attack, "Útočné číslo"));
    validator.constraint(ConstraintUtils.isNotEmpty(defence, "Obranné číslo"));
    validator.constraint(ConstraintUtils.isNumber(endurance, "Odolnost"));
    validator.constraint(ConstraintUtils.isNotEmpty(dimension, "Velikost"));
    validator.constraint(ConstraintUtils.isNumberOrEmpty(combativeness, "Bojovnost"));
    validator.constraint(ConstraintUtils.isNotEmpty(vulnerability, "Zranitelnost"));
    validator.constraint(ConstraintUtils.isNotEmpty(moveability, "Pohyblivost"));
    validator.constraint(ConstraintUtils.isNumber(intelligence, "Inteligence"));
    validator.constraint(ConstraintUtils.isNumberOrEmpty(conviction, "Inteligence"));
    validator.constraint(ConstraintUtils.isNotEmpty(treasure, "Poklady"));
    validator.constraint(ConstraintUtils.isNotEmpty(experience, "Zkušenost"));
  }

  // region Setters and Getters
  @Override
  public Long getId() {
    return id.get();
  }

  @Override
  public void setId(Long id) {
    this.id.set(id);
  }

  public String getName() {
    return name.get();
  }

  public DirtyStringProperty nameProperty() {
    return name;
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public String getViability() {
    return viability.get();
  }

  public DirtyStringProperty viabilityProperty() {
    return viability;
  }

  public void setViability(String viability) {
    this.viability.set(viability);
  }

  public String getAttack() {
    return attack.get();
  }

  public DirtyStringProperty attackProperty() {
    return attack;
  }

  public void setAttack(String attack) {
    this.attack.set(attack);
  }

  public String getDefence() {
    return defence.get();
  }

  public DirtyStringProperty defenceProperty() {
    return defence;
  }

  public void setDefence(String defence) {
    this.defence.set(defence);
  }

  public String getEndurance() {
    return endurance.get();
  }

  public DirtyStringProperty enduranceProperty() {
    return endurance;
  }

  public void setEndurance(String endurance) {
    this.endurance.set(endurance);
  }

  public String getDimension() {
    return dimension.get();
  }

  public DirtyStringProperty dimensionProperty() {
    return dimension;
  }

  public void setDimension(String dimension) {
    this.dimension.set(dimension);
  }

  public String getCombativeness() {
    return combativeness.get();
  }

  public DirtyStringProperty combativenessProperty() {
    return combativeness;
  }

  public void setCombativeness(String combativeness) {
    this.combativeness.set(combativeness);
  }

  public String getVulnerability() {
    return vulnerability.get();
  }

  public DirtyStringProperty vulnerabilityProperty() {
    return vulnerability;
  }

  public void setVulnerability(String vulnerability) {
    this.vulnerability.set(vulnerability);
  }

  public String getMoveability() {
    return moveability.get();
  }

  public DirtyStringProperty moveabilityProperty() {
    return moveability;
  }

  public void setMoveability(String moveability) {
    this.moveability.set(moveability);
  }

  public String getStamina() {
    return stamina.get();
  }

  public DirtyStringProperty staminaProperty() {
    return stamina;
  }

  public void setStamina(String stamina) {
    this.stamina.set(stamina);
  }

  public String getIntelligence() {
    return intelligence.get();
  }

  public DirtyStringProperty intelligenceProperty() {
    return intelligence;
  }

  public void setIntelligence(String intelligence) {
    this.intelligence.set(intelligence);
  }

  public String getConviction() {
    return conviction.get();
  }

  public DirtyStringProperty convictionProperty() {
    return conviction;
  }

  public void setConviction(String conviction) {
    this.conviction.set(conviction);
  }

  public String getTreasure() {
    return treasure.get();
  }

  public DirtyStringProperty treasureProperty() {
    return treasure;
  }

  public void setTreasure(String treasure) {
    this.treasure.set(treasure);
  }

  public String getExperience() {
    return experience.get();
  }

  public DirtyStringProperty experienceProperty() {
    return experience;
  }

  public void setExperience(String experience) {
    this.experience.set(experience);
  }

  public String getDescription() {
    return description.get();
  }

  public DirtyStringProperty descriptionProperty() {
    return description;
  }

  public void setDescription(String description) {
    this.description.set(description);
  }
  // endregion

  @Override
  public boolean isTransient() {
    return getId() == null || getId() <= 0;
  }
}
