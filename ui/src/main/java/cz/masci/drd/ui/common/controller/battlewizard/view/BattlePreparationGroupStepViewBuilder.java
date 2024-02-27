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

package cz.masci.drd.ui.common.controller.battlewizard.view;

import cz.masci.drd.ui.util.ViewBuilderUtils;
import io.github.palexdev.materialfx.builders.layout.VBoxBuilder;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BattlePreparationGroupStepViewBuilder implements Builder<Region> {

  private final StringProperty groupCount;

  @Override
  public Region build() {
    MFXTextField groupNumber = ViewBuilderUtils.createTextField("Počet skupin", Double.MAX_VALUE);
    groupNumber.textProperty().bindBidirectional(groupCount);
    return VBoxBuilder.vBox()
                           .setAlignment(Pos.CENTER)
                           .addChildren(groupNumber)
                           .getNode();
  }
}
