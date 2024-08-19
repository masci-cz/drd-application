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

import cz.masci.springfx.mvci.model.detail.impl.BaseDetailModel;
import cz.masci.springfx.mvci.model.dirty.DirtyStringProperty;

public class AdventureDetailModel extends BaseDetailModel<Long> {

  private final DirtyStringProperty name = new DirtyStringProperty("");

  public AdventureDetailModel() {
    addComposites(name);
    addConstraints(isNotEmpty(name, "Název"));
  }

  // region Setters and Getters
  public String getName() {
    return name.get();
  }

  public DirtyStringProperty nameProperty() {
    return name;
  }

  public void setName(String name) {
    this.name.set(name);
  }
  // endregion

  @Override
  public boolean isTransient() {
    return getId() == null || getId() <= 0;
  }
}
