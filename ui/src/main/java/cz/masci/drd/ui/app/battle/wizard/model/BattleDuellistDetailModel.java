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

package cz.masci.drd.ui.app.battle.wizard.model;

import cz.masci.springfx.mvci.model.detail.impl.BaseDetailModel;
import cz.masci.springfx.mvci.model.dirty.DirtyStringProperty;
import cz.masci.springfx.mvci.util.constraint.ConstraintUtils;
import javafx.beans.Observable;

public class BattleDuellistDetailModel extends BaseDetailModel<String> {

  private final DirtyStringProperty name = new DirtyStringProperty("");
  private final DirtyStringProperty attack = new DirtyStringProperty("");
  private final DirtyStringProperty defense = new DirtyStringProperty("");
  private final DirtyStringProperty damage = new DirtyStringProperty("");
  private final DirtyStringProperty live = new DirtyStringProperty("");

  public BattleDuellistDetailModel() {
    addComposites(name, attack, defense, damage, live);
    addConstraints(
        ConstraintUtils.isNotEmpty(name, "Název"),
        ConstraintUtils.isNumberOrEmpty(attack, "Útočné číslo"),
        ConstraintUtils.isNumberOrEmpty(defense, "Obranné číslo"),
        ConstraintUtils.isNumberOrEmpty(damage, "Útočnost"),
        ConstraintUtils.isNumberOrEmpty(live, "Životy")
    );
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

  public String getAttack() {
    return attack.get();
  }

  public DirtyStringProperty attackProperty() {
    return attack;
  }

  public void setAttack(String attack) {
    this.attack.set(attack);
  }

  public String getDefense() {
    return defense.get();
  }

  public DirtyStringProperty defenseProperty() {
    return defense;
  }

  public void setDefense(String defense) {
    this.defense.set(defense);
  }

  public String getDamage() {
    return damage.get();
  }

  public DirtyStringProperty damageProperty() {
    return damage;
  }

  public void setDamage(String damage) {
    this.damage.set(damage);
  }

  public String getLive() {
    return live.get();
  }

  public DirtyStringProperty liveProperty() {
    return live;
  }

  public void setLive(String live) {
    this.live.set(live);
  }

  public static Observable[] asObservables(BattleDuellistDetailModel model) {
    return new Observable[]{
        model.name,
        model.attack,
        model.defense,
        model.damage,
        model.live,
        model.validProperty(),
        model.isDirtyProperty()
    };
  }
}

