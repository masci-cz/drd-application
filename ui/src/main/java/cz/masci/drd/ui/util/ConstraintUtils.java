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

import static java.util.Objects.requireNonNull;

import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Constraint.Builder;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConstraintUtils {

  public final static int ABILITY_SCORE_MIN = 0;
  public final static int ABILITY_SCORE_MAX = 21;
  private final static String NUMBER_REGEX = "[-+]?\\d+";

  /**
   * Returns a constraint that validates whether the given StringProperty is not empty.
   *
   * @param property the StringProperty to validate
   * @param fieldName the field name used in error message for the constraint
   * @return a constraint that validates whether the given StringProperty is not empty
   */
  public static Constraint isNotEmpty(StringProperty property, String fieldName) {
    requireNonNull(property);

    return Constraint.Builder.build()
        .setSeverity(Severity.ERROR)
        .setMessage(String.format("Pole %s je povinné", fieldName))
        .setCondition(property.isNotEmpty())
        .get();
  }

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
   * Returns a constraint that validates whether the given IntegerProperty is within the specified range.
   *
   * @param property the IntegerProperty to validate
   * @param fieldName the field name used in the error message for the constraint
   * @param min the minimum value of the range (inclusive)
   * @param max the maximum value of the range (inclusive)
   * @return a constraint that validates whether the given IntegerProperty is within the specified range
   */
  public static Constraint isInRange(IntegerProperty property, String fieldName, int min, int max) {
    requireNonNull(property);

    return Constraint.Builder.build()
        .setSeverity(Severity.ERROR)
        .setMessage(String.format("Pole %s musí být v rozmezí %d až %d", fieldName, min, max))
        .setCondition(property.greaterThanOrEqualTo(min).and(property.lessThanOrEqualTo(max)))
        .get();
  }

  /**
   * Returns a constraint that validates whether the given StringProperty contains only a number.
   *
   * @param property the StringProperty to validate
   * @param fieldName the field name used in the error message for the constraint
   * @return a constraint that validates whether the given StringProperty contains only a number
   */
  public static Constraint isNumber(StringProperty property, String fieldName) {
    requireNonNull(property);

    return Builder.build()
        .setSeverity(Severity.ERROR)
        .setMessage(String.format("Pole %s musí být číslo", fieldName))
        .setCondition(Bindings.createBooleanBinding(() -> property.get() != null && property.get().matches(NUMBER_REGEX), property))
        .get();
  }

  public static <T> Constraint isNotEmptyWhenPropertyIsNotEmpty(StringProperty stringProperty, ObjectProperty<T> nullableProperty, String fieldName) {
    requireNonNull(nullableProperty);
    requireNonNull(stringProperty);

    return Constraint.Builder.build()
        .setSeverity(Severity.ERROR)
        .setMessage(String.format("Pole %s je povinné", fieldName))
        .setCondition(nullableProperty.isNull().or(stringProperty.isNotEmpty()))
        .get();
  }

  public static <T> Constraint isNumberWhenPropertyIsNotEmpty(StringProperty property, ObjectProperty<T> nullableProperty, String fieldName) {
    requireNonNull(property);

    return Builder.build()
        .setSeverity(Severity.ERROR)
        .setMessage(String.format("Pole %s musí být číslo", fieldName))
        .setCondition(nullableProperty.isNull().or(Bindings.createBooleanBinding(() -> property.get() != null && property.get().matches(NUMBER_REGEX), property)))
        .get();
  }

}
