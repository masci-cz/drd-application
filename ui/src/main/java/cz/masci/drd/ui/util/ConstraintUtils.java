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

import static cz.masci.springfx.mvci.util.constraint.ConstraintUtils.isInRange;

import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Constraint.Builder;
import io.github.palexdev.materialfx.validation.MFXValidator;
import io.github.palexdev.materialfx.validation.Severity;
import io.github.palexdev.materialfx.validation.Validated;
import java.util.function.Function;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ObservableValue;
import lombok.experimental.UtilityClass;
import org.reactfx.value.Val;

@UtilityClass
public class ConstraintUtils {

  public final static int ABILITY_SCORE_MIN = 0;
  public final static int ABILITY_SCORE_MAX = 21;

  /**
   * Returns a constraint that validates whether the given IntegerProperty is within the range of 1 to 10 (inclusive).
   *
   * @param property the IntegerProperty to validate
   * @param fieldName the field name used in error message for the constraint
   * @return a constraint that validates whether the given IntegerProperty is within the range of 1 to 10 (inclusive)
   */
  public static Constraint isInAbilityScoresRange(IntegerProperty property, String fieldName) {
    return isInRange(property, fieldName, ABILITY_SCORE_MIN, ABILITY_SCORE_MAX);
  }

  /**
   * Returns a constraint that validates whether the given test satisfies and the object is not empty.
   *
   * @param object the object from which the property is taken
   * @param propertyFnc the object mapper
   * @param test predicate for the constraint to validate
   * @param message error message for the constraint
   * @return a constraint that validates whether the given property satisfies to the test
   */
  public static <T, U extends Validated> Constraint createConstraint(String message, ObservableValue<T> parent, Function<T, ObservableValue<U>> propertyFnc) {
    Val<Boolean> validProperty = Val.flatMap(parent, propertyFnc)
                                    .flatMap(property -> property.getValidator()
                                                                 .validProperty());
    return Builder.build()
                  .setSeverity(Severity.ERROR)
                  .setMessage(message)
                  .setCondition(Bindings.createBooleanBinding(() -> validProperty.getOrElse(false), validProperty))
                  .get();
  }
}
