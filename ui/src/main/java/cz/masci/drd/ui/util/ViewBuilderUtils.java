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

package cz.masci.drd.ui.util;

import static cz.masci.springfx.mvci.util.BuilderUtils.INVALID_PSEUDO_CLASS;
import static cz.masci.springfx.mvci.util.BuilderUtils.createValidationSupportingText;

import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Validated;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import lombok.experimental.UtilityClass;

// TODO Move to commons-springfx library
@UtilityClass
public class ViewBuilderUtils {

  public <T extends Node & Validated> Region enhanceValidatedNodeWithSupportingText(T validatedNode, Consumer<ChangeListener<? super Boolean>> revalidateFlagListener, Constraint... inputConstraints) {
    return enhanceValidatedNodeWithSupportingText(validatedNode, createValidationSupportingText(), revalidateFlagListener, inputConstraints);
  }

  public <T extends Node & Validated> Region enhanceValidatedNodeWithSupportingText(T validatedNode, Label supportingText, Consumer<ChangeListener<? super Boolean>> revalidateFlagListener, Constraint... inputConstraints) {
    Arrays.stream(inputConstraints).forEach(validatedNode.getValidator()::constraint);
    validatedNode.getValidator().validProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        supportingText.setVisible(false);
        supportingText.setManaged(false); // disable
        validatedNode.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, false);
      }
    });
    revalidateFlagListener.accept((observable, oldValue, newValue) -> {
      if (!oldValue && newValue) {
        List<Constraint> constraints = validatedNode.validate();
        if (!constraints.isEmpty()) {
          validatedNode.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, true);
          supportingText.setText(constraints.get(0).getMessage());
          supportingText.setVisible(true);
          supportingText.setManaged(true);
        }
      }
    });
    var result = new VBox(validatedNode, supportingText);
    result.setAlignment(Pos.TOP_LEFT);

    return result;
  }

}
