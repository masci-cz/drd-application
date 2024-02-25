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

import java.util.List;
import java.util.ListIterator;
import java.util.function.Supplier;

@Deprecated
public class WizardListIterator<E> {
  protected ListIterator<E> iterator;
  protected E current;
  protected int size;
  protected boolean hasPrevious;
  protected boolean hasNext;

  public WizardListIterator(List<E> list) {
    iterator = list.listIterator(0);
    size = list.size();
    hasPrevious = iterator.hasPrevious();
    hasNext = iterator.hasNext();
  }

  public E next() {
    return get(iterator::hasNext, iterator::next);
  }

  public E prev() {
    return get(iterator::hasPrevious, iterator::previous);
  }

  private E get(Supplier<Boolean> hasFuture, Supplier<E> getFuture) {
    var lastPreviousIndex = iterator.previousIndex();
    var lastNextIndex = iterator.nextIndex();

    if (hasFuture.get()) {
      var future = getFuture.get();
      if (future.equals(current)) {
        if (hasFuture.get()) {
          lastPreviousIndex = iterator.previousIndex();
          lastNextIndex = iterator.nextIndex();
          current = getFuture.get();
        } else {
          current = null;
        }
      } else {
        current = future;
      }
    } else {
      current = null;
    }

    // update hasPrevious and hasNext status
    hasPrevious = Math.max(iterator.previousIndex(), lastPreviousIndex) > 0;
    hasNext = Math.max(iterator.nextIndex(), lastNextIndex) < size;

    return current;
  }
}
