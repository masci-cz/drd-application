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

import static cz.masci.springfx.mvci.util.constraint.ConstraintUtils.isNotEmpty;

import cz.masci.springfx.mvci.model.detail.impl.BaseDetailModel;
import cz.masci.springfx.mvci.model.dirty.DirtyStringProperty;
import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import lombok.ToString;

@ToString
public class BattleGroupDetailModel extends BaseDetailModel<String> {
  private final DirtyStringProperty name = new DirtyStringProperty("");

  public BattleGroupDetailModel() {
    addComposites(name);
    addConstraints(isNotEmpty(name, "Název"));
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

  public static Observable[] asObservables(BattleGroupDetailModel model) {
    return new Observable[] {
        model.nameProperty(),
        model.validProperty(),
        model.isDirtyProperty()
    };
  }
}
