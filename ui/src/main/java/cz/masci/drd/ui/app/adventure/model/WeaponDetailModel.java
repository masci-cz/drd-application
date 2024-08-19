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

package cz.masci.drd.ui.app.adventure.model;

import static cz.masci.springfx.mvci.util.constraint.ConstraintUtils.isNotEmpty;
import static cz.masci.springfx.mvci.util.constraint.ConstraintUtils.isNumber;

import cz.masci.springfx.mvci.model.detail.impl.BaseDetailModel;
import cz.masci.springfx.mvci.model.dirty.DirtyStringProperty;
import javafx.beans.property.StringProperty;

public class WeaponDetailModel extends BaseDetailModel<Long> {
  private final DirtyStringProperty name = new DirtyStringProperty("");
  private final DirtyStringProperty strength = new DirtyStringProperty("");
  private final DirtyStringProperty damage = new DirtyStringProperty("");

  public WeaponDetailModel() {
    addComposites(name, strength, damage);
    addConstraints(
        isNotEmpty(name, "Název zbraně"),
        isNotEmpty(strength, "Útočné číslo zbraně"),
        isNumber(strength, "Útočné číslo zbraně"),
        isNotEmpty(damage, "Útočnost zbraně"),
        isNumber(damage,"Útočnost zbraně")
    );
  }

  // region Setters and Getters
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
