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

package cz.masci.drd.ui.adventure.model;

import cz.masci.drd.ui.util.ConstraintUtils;
import cz.masci.drd.ui.util.model.DetailModel;
import cz.masci.springfx.mvci.model.dirty.DirtyStringProperty;
import io.github.palexdev.materialfx.validation.MFXValidator;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;

public class WeaponDetailModel implements DetailModel<Long> {
  private final LongProperty id = new SimpleLongProperty();
  private final DirtyStringProperty name = new DirtyStringProperty("");
  private final DirtyStringProperty strength = new DirtyStringProperty("");
  private final DirtyStringProperty damage = new DirtyStringProperty("");
  @Getter
  private final CompositeDirtyProperty composite = new CompositeDirtyProperty();
  @Getter
  private final MFXValidator validator = new MFXValidator();

  public WeaponDetailModel() {
    composite.addAll(name, strength, damage);
    validator.constraint(ConstraintUtils.isNotEmpty(name, "Název zbraně"));
    validator.constraint(ConstraintUtils.isNotEmpty(strength, "Útočné číslo zbraně"));
    validator.constraint(ConstraintUtils.isNumber(strength, "Útočné číslo zbraně"));
    validator.constraint(ConstraintUtils.isNotEmpty(damage, "Útočnost zbraně"));
    validator.constraint(ConstraintUtils.isNumber(damage, "Útočnost zbraně"));
  }

  // region Setters and Getters
  @Override
  public Long getId() {
    return id.get();
  }

  public LongProperty idProperty() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id.set(id);
  }

  public String getName() {
    return name.get();
  }

  public StringProperty nameProperty() {
    return name;
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public String getStrength() {
    return strength.get();
  }

  public StringProperty strengthProperty() {
    return strength;
  }

  public void setStrength(String strength) {
    this.strength.set(strength);
  }

  public String getDamage() {
    return damage.get();
  }

  public StringProperty damageProperty() {
    return damage;
  }

  public void setDamage(String damage) {
    this.damage.set(damage);
  }

  // endregion

  @Override
  public boolean isTransient() {
    return getId() == null || getId() <= 0;
  }
}
