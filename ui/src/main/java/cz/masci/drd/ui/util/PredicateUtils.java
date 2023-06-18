/*
 * Copyright (c) 2023
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

import java.util.function.Function;
import java.util.function.Predicate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PredicateUtils {

  /**
   * Returns a composed predicate that for every {@code x} will return {@code predicate(function(x))}.
   *
   * <p>Use-case example: {@code compose(User::firstName, Objects::nonNull)}</p>
   */
  public static <T, V> Predicate<T> compose(Function<T, ? extends V> function, Predicate<? super V> predicate) {
    requireNonNull(predicate);
    requireNonNull(function);

    return value -> predicate.test(function.apply(value));
  }
}
