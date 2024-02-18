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

import cz.masci.drd.ui.util.ConstraintUtils;
import cz.masci.springfx.mvci.model.detail.DetailModel;
import cz.masci.springfx.mvci.model.dirty.DirtyStringProperty;
import io.github.palexdev.materialfx.validation.MFXValidator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;

public class AdventureDetailModel implements DetailModel<Long> {

  private final ObjectProperty<Long> id = new SimpleObjectProperty<>();
  private final DirtyStringProperty name = new DirtyStringProperty("");
  // dirty and validator
  @Getter
  private final CompositeDirtyProperty composite = new CompositeDirtyProperty();
  @Getter
  private final MFXValidator validator = new MFXValidator();

  public AdventureDetailModel() {
    composite.add(name);
    validator.constraint(ConstraintUtils.isNotEmpty(name, "Název"));
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
  // endregion

  @Override
  public boolean isTransient() {
    return getId() == null || getId() <= 0;
  }
}
