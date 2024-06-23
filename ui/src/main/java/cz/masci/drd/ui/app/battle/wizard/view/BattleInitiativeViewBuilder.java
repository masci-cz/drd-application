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

package cz.masci.drd.ui.app.battle.wizard.view;

import cz.masci.springfx.mvci.util.BuilderUtils;
import cz.masci.springfx.mvci.util.MFXBuilderUtils;
import cz.masci.springfx.mvci.util.constraint.ConstraintUtils;
import cz.masci.springfx.mvci.util.property.PropertyUtils;
import io.github.palexdev.materialfx.builders.layout.VBoxBuilder;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BattleInitiativeViewBuilder implements Builder<Region> {

  private final StringProperty initiative;

  @Override
  public Region build() {
    var initiativeText = MFXBuilderUtils.createTextField("Iniciativa", Double.MAX_VALUE);
    var initiativeTextConstraint = ConstraintUtils.isNumber(initiativeText.textProperty(), "Iniciativa");
    var initiativeTextWithValidation = BuilderUtils.enhanceValidatedNodeWithSupportingText(initiativeText, PropertyUtils.not(initiativeText.delegateFocusedProperty())::addListener, initiativeTextConstraint);

    initiativeText.textProperty().bindBidirectional(initiative);

    return VBoxBuilder.vBox()
        .setSpacing(5.0)
        .addChildren(initiativeTextWithValidation)
        .setPadding(new Insets(5.0))
        .setMaxWidth(Double.MAX_VALUE)
        .getNode();
  }
}
