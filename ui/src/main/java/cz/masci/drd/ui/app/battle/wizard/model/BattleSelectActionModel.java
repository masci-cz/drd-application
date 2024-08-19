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

import cz.masci.drd.ui.util.ConstraintUtils;
import cz.masci.springfx.mvci.model.detail.ValidModel;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.MFXValidator;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;

public class BattleSelectActionModel implements ValidModel {
  @Getter
  private final MFXValidator validator = new MFXValidator();
  @Getter
  private final List<SelectActionModel> actions;
  private final ObjectProperty<SelectActionModel> selectedAction = new SimpleObjectProperty<>();

  public BattleSelectActionModel(List<SelectActionModel> actions) {
    this.actions = actions;
    validator.constraint(Constraint.of("Vyberte akci", selectedAction.isNotNull()));
    validator.constraint(ConstraintUtils.createConstraint("Akce je nevalidní", selectedAction, SelectActionModel::action));
  }

  public SelectActionModel getSelectedAction() {
    return selectedAction.get();
  }

  public ObjectProperty<SelectActionModel> selectedActionProperty() {
    return selectedAction;
  }

  public void setSelectedAction(SelectActionModel selectedAction) {
    this.selectedAction.set(selectedAction);
  }
}
