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

package cz.masci.drd.ui.util.iterator;

import java.util.List;
import java.util.ListIterator;
import java.util.function.Supplier;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObservableListIterator<E> implements Iterable<E> {

  private final ListIterator<E> iterator;
  private final int size;

  @Getter
  private final BooleanProperty hasPreviousProperty;
  @Getter
  private final BooleanProperty hasNextProperty;
  @Getter
  private final ObjectProperty<E> currentProperty = new SimpleObjectProperty<>();

  public ObservableListIterator(List<E> list) {
    iterator = list.listIterator(0);
    size = list.size();

    hasPreviousProperty = new SimpleBooleanProperty(iterator.hasPrevious());
    hasNextProperty = new SimpleBooleanProperty(iterator.hasNext());

    log.trace("init");
    log.trace("hasPrevious: {}", hasPreviousProperty.get());
    log.trace("hasNext: {}", hasNextProperty.get());
    log.trace("current: {}", currentProperty.get());
  }

  @Override
  public E previous() {
    return get(iterator::hasPrevious, iterator::previous);
  }

  @Override
  public E next() {
    return get(iterator::hasNext, iterator::next);
  }

  public boolean hasPrevious() {
    return hasPreviousProperty.get();
  }

  public boolean hasNext() {
    return hasNextProperty.get();
  }

  @Override
  public E getCurrent() {
    return currentProperty.get();
  }

  private E get(Supplier<Boolean> hasFutureElement, Supplier<E> futureElement) {
    var lastPreviousIndex = iterator.previousIndex();
    var lastNextIndex = iterator.nextIndex();

    log.trace("get element");
    if (hasFutureElement.get()) {
      var future = futureElement.get();
      if (future.equals(currentProperty.get())) {
        if (hasFutureElement.get()) {
          lastPreviousIndex = iterator.previousIndex();
          lastNextIndex = iterator.nextIndex();
          currentProperty.set(futureElement.get());
        } else {
          currentProperty.set(null);
        }
      } else {
        currentProperty.set(future);
      }
    } else {
      currentProperty.set(null);
    }

    // update hasPrevious and hasNext status
    hasPreviousProperty.set(Math.max(iterator.previousIndex(), lastPreviousIndex) > 0);
    hasNextProperty.set(Math.max(iterator.nextIndex(), lastNextIndex) < size);

    log.trace("hasPrevious: {}", hasPreviousProperty.get());
    log.trace("hasNext: {}", hasNextProperty.get());
    log.trace("current: {}", currentProperty.get());
    log.trace("previousIndex: {}", iterator.previousIndex());
    log.trace("nextIndex: {}", iterator.nextIndex());

    return currentProperty.get();
  }
}
