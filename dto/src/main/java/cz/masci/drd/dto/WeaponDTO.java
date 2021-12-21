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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Daniel
 */
public class WeaponDTO implements Modifiable {
  /**
   * Id
   */
  private LongProperty idProperty;
  /**
   * Weapon name
   */
  private StringProperty nameProperty;
  /**
   * Weapon strength - útočné číslo
   */
  private IntegerProperty strengthProperty;
  /**
   * Weapon damage - útočnost
   */
  private IntegerProperty damageProperty;
  
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

  
  public final IntegerProperty strengthProperty() {
    if (strengthProperty == null) {
      strengthProperty = new SimpleIntegerProperty();
    }
    return strengthProperty;
  }
  
  public Integer getStrength() {
    return strengthProperty().get();
  }
  
  public void setStrength(Integer strength) {
    strengthProperty().set(strength);
  }
  
  public final IntegerProperty damageProperty() {
    if (damageProperty == null) {
      damageProperty = new SimpleIntegerProperty();
    }
    return damageProperty;
  }
  
  public Integer getDamage() {
    return damageProperty().get();
  }
  
  public void setDamage(Integer damage) {
    damageProperty().set(damage);
  }
}
