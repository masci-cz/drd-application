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

package cz.masci.drd.ui.common.controller.battlewizard.controller;

import cz.masci.drd.ui.common.controller.battlewizard.view.BattlePreparationGroupStepViewBuilder;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class BattlePreparationGroupController extends LeafStep {

  public BattlePreparationGroupController() {
    IntegerProperty groupCount = new SimpleIntegerProperty();
    setView(new BattlePreparationGroupStepViewBuilder(groupCount).build());

    nextDisableProperty().bind(groupCount.asObject().isNull().or(groupCount.lessThan(2)));
    setTitle("Groups");
  }
}
