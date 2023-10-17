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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObservableListIteratorTest {

  private static final TestItem FIRST = new TestItem("first");
  private static final TestItem MIDDLE = new TestItem("middle");
  private static final TestItem LAST = new TestItem("last");

  private ObservableListIterator<TestItem> observableListIterator;

  @BeforeEach
  void setUp() {
    observableListIterator = new ObservableListIterator<>(new ArrayList<>(List.of(FIRST, MIDDLE, LAST)));
  }

  // region After init
  @Test
  void previous_afterInit() {
    var result = observableListIterator.previous();

    assertNull(result);
  }

  @Test
  void next_afterInit() {
    var result = observableListIterator.next();

    assertEquals(FIRST, result);
  }

  @Test
  void hasPrevious_afterInit() {
    var result = observableListIterator.hasPrevious();

    assertFalse(result);
  }

  @Test
  void hasNext_afterInit() {
    var result = observableListIterator.hasNext();

    assertTrue(result);
  }

  @Test
  void getCurrent_afterInit() {
    var result = observableListIterator.getCurrent();

    assertNull(result);
  }

  @Test
  void getHasPreviousProperty_afterInit() {
    var result = observableListIterator.getHasPreviousProperty();

    assertAll(() -> assertNotNull(result), () -> assertFalse(result.get()));
  }

  @Test
  void getHasNextProperty_afterInit() {
    var result = observableListIterator.getHasNextProperty();

    assertAll(() -> assertNotNull(result), () -> assertTrue(result.get()));
  }

  @Test
  void getCurrentProperty_afterInit() {
    var result = observableListIterator.getCurrentProperty();

    assertAll(() -> assertNotNull(result), () -> assertNull(result.get()));
  }
  // endregion

  // region On first
  @Test
  void previous_onFirst() {
    observableListIterator.next();
    var result = observableListIterator.previous();

    assertNull(result);
  }

  @Test
  void next_onFirst() {
    observableListIterator.next();
    var result = observableListIterator.next();

    assertEquals(MIDDLE, result);
  }

  @Test
  void hasPrevious_onFirst() {
    observableListIterator.next();
    var result = observableListIterator.hasPrevious();

    assertFalse(result);
  }

  @Test
  void hasNext_onFirst() {
    observableListIterator.next();
    var result = observableListIterator.hasNext();

    assertTrue(result);
  }

  @Test
  void getCurrent_onFirst() {
    observableListIterator.next();
    var result = observableListIterator.getCurrent();

    assertEquals(FIRST, result);
  }

  @Test
  void getHasPreviousProperty_onFirst() {
    observableListIterator.next();
    var result = observableListIterator.getHasPreviousProperty();

    assertAll(() -> assertNotNull(result), () -> assertFalse(result.get()));
  }

  @Test
  void getHasNextProperty_onFirst() {
    observableListIterator.next();
    var result = observableListIterator.getHasNextProperty();

    assertAll(() -> assertNotNull(result), () -> assertTrue(result.get()));
  }

  @Test
  void getCurrentProperty_onFirst() {
    observableListIterator.next();
    var result = observableListIterator.getCurrentProperty();

    assertAll(() -> assertNotNull(result), () -> assertEquals(FIRST, result.get()));
  }
  // endregion

  // region On middle
  @Test
  void previous_onMiddle() {
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.previous();

    assertEquals(FIRST, result);
  }

  @Test
  void next_onMiddle() {
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.next();

    assertEquals(LAST, result);
  }

  @Test
  void hasPrevious_onMiddle() {
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.hasPrevious();

    assertTrue(result);
  }

  @Test
  void hasNext_onMiddle() {
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.hasNext();

    assertTrue(result);
  }

  @Test
  void getCurrent_onMiddle() {
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.getCurrent();

    assertEquals(MIDDLE, result);
  }

  @Test
  void getHasPreviousProperty_onMiddle() {
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.getHasPreviousProperty();

    assertAll(() -> assertNotNull(result), () -> assertTrue(result.get()));
  }

  @Test
  void getHasNextProperty_onMiddle() {
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.getHasNextProperty();

    assertAll(() -> assertNotNull(result), () -> assertTrue(result.get()));
  }

  @Test
  void getCurrentProperty_onMiddle() {
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.getCurrentProperty();

    assertAll(() -> assertNotNull(result), () -> assertEquals(MIDDLE, result.get()));
  }
  // endregion

  // region On last
  @Test
  void previous_onLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.previous();

    assertEquals(MIDDLE, result);
  }

  @Test
  void next_onLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.next();

    assertNull(result);
  }

  @Test
  void hasPrevious_onLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.hasPrevious();

    assertTrue(result);
  }

  @Test
  void hasNext_onLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.hasNext();

    assertFalse(result);
  }

  @Test
  void getCurrent_onLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.getCurrent();

    assertEquals(LAST, result);
  }

  @Test
  void getHasPreviousProperty_onLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.getHasPreviousProperty();

    assertAll(() -> assertNotNull(result), () -> assertTrue(result.get()));
  }

  @Test
  void getHasNextProperty_onLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.getHasNextProperty();

    assertAll(() -> assertNotNull(result), () -> assertFalse(result.get()));
  }

  @Test
  void getCurrentProperty_onLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.getCurrentProperty();

    assertAll(() -> assertNotNull(result), () -> assertEquals(LAST, result.get()));
  }
  // endregion

  // region After last
  @Test
  void previous_afterLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.previous();

    assertEquals(LAST, result);
  }

  @Test
  void next_afterLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.next();

    assertNull(result);
  }

  @Test
  void hasPrevious_afterLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.hasPrevious();

    assertTrue(result);
  }

  @Test
  void hasNext_afterLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.hasNext();

    assertFalse(result);
  }

  @Test
  void getCurrent_afterLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.getCurrent();

    assertNull(result);
  }

  @Test
  void getHasPreviousProperty_afterLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.getHasPreviousProperty();

    assertAll(() -> assertNotNull(result), () -> assertTrue(result.get()));
  }

  @Test
  void getHasNextProperty_afterLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.getHasNextProperty();

    assertAll(() -> assertNotNull(result), () -> assertFalse(result.get()));
  }

  @Test
  void getCurrentProperty_afterLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    var result = observableListIterator.getCurrentProperty();

    assertAll(() -> assertNotNull(result), () -> assertNull(result.get()));
  }
  // endregion

  // region Back - On last
  @Test
  void previous_back_onLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    var result = observableListIterator.previous();

    assertEquals(MIDDLE, result);
  }

  @Test
  void next_back_onLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    var result = observableListIterator.next();

    assertNull(result);
  }

  @Test
  void hasPrevious_back_onLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    var result = observableListIterator.hasPrevious();

    assertTrue(result);
  }

  @Test
  void hasNext_back_onLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    var result = observableListIterator.hasNext();

    assertFalse(result);
  }

  @Test
  void getCurrent_back_onLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    var result = observableListIterator.getCurrent();

    assertEquals(LAST, result);
  }

  @Test
  void getHasPreviousProperty_back_onLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    var result = observableListIterator.getHasPreviousProperty();

    assertAll(() -> assertNotNull(result), () -> assertTrue(result.get()));
  }

  @Test
  void getHasNextProperty_back_onLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    var result = observableListIterator.getHasNextProperty();

    assertAll(() -> assertNotNull(result), () -> assertFalse(result.get()));
  }

  @Test
  void getCurrentProperty_back_onLast() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    var result = observableListIterator.getCurrentProperty();

    assertAll(() -> assertNotNull(result), () -> assertEquals(LAST, result.get()));
  }
  // endregion

  // region Back - On middle
  @Test
  void previous_back_onMiddle() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.previous();

    assertEquals(FIRST, result);
  }

  @Test
  void next_back_onMiddle() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.next();

    assertEquals(LAST, result);
  }

  @Test
  void hasPrevious_back_onMiddle() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.hasPrevious();

    assertTrue(result);
  }

  @Test
  void hasNext_back_onMiddle() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.hasNext();

    assertTrue(result);
  }

  @Test
  void getCurrent_back_onMiddle() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.getCurrent();

    assertEquals(MIDDLE, result);
  }

  @Test
  void getHasPreviousProperty_back_onMiddle() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.getHasPreviousProperty();

    assertAll(() -> assertNotNull(result), () -> assertTrue(result.get()));
  }

  @Test
  void getHasNextProperty_back_onMiddle() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.getHasNextProperty();

    assertAll(() -> assertNotNull(result), () -> assertTrue(result.get()));
  }

  @Test
  void getCurrentProperty_back_onMiddle() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.getCurrentProperty();

    assertAll(() -> assertNotNull(result), () -> assertEquals(MIDDLE, result.get()));
  }
  // endregion

  // region Back - On first
  @Test
  void previous_back_onFirst() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.previous();

    assertNull(result);
  }

  @Test
  void next_back_onFirst() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.next();

    assertEquals(MIDDLE, result);
  }

  @Test
  void hasPrevious_back_onFirst() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.hasPrevious();

    assertFalse(result);
  }

  @Test
  void hasNext_back_onFirst() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.hasNext();

    assertTrue(result);
  }

  @Test
  void getCurrent_back_onFirst() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.getCurrent();

    assertEquals(FIRST, result);
  }

  @Test
  void getHasPreviousProperty_back_onFirst() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.getHasPreviousProperty();

    assertAll(() -> assertNotNull(result), () -> assertFalse(result.get()));
  }

  @Test
  void getHasNextProperty_back_onFirst() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.getHasNextProperty();

    assertAll(() -> assertNotNull(result), () -> assertTrue(result.get()));
  }

  @Test
  void getCurrentProperty_back_onFirst() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.getCurrentProperty();

    assertAll(() -> assertNotNull(result), () -> assertEquals(FIRST, result.get()));
  }
  // endregion

  // region Back - Before first
  @Test
  void previous_back_beforeFirst() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.previous();

    assertNull(result);
  }

  @Test
  void next_back_beforeFirst() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.next();

    assertEquals(FIRST, result);
  }

  @Test
  void hasPrevious_back_beforeFirst() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.hasPrevious();

    assertFalse(result);
  }

  @Test
  void hasNext_back_beforeFirst() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.hasNext();

    assertTrue(result);
  }

  @Test
  void getCurrent_back_beforeFirst() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.getCurrent();

    assertNull(result);
  }

  @Test
  void getHasPreviousProperty_back_beforeFirst() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.getHasPreviousProperty();

    assertAll(() -> assertNotNull(result), () -> assertFalse(result.get()));
  }

  @Test
  void getHasNextProperty_back_beforeFirst() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.getHasNextProperty();

    assertAll(() -> assertNotNull(result), () -> assertTrue(result.get()));
  }

  @Test
  void getCurrentProperty_back_beforeFirst() {
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.next();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    observableListIterator.previous();
    var result = observableListIterator.getCurrentProperty();

    assertAll(() -> assertNotNull(result), () -> assertNull(result.get()));
  }
  // endregion

  @Test
  void steps() {
    // on init
    assertNull(observableListIterator.getCurrent());
    // forward - first item
    assertEquals(FIRST, observableListIterator.next());
    assertEquals(FIRST, observableListIterator.getCurrent());
    assertNull(observableListIterator.previous());
    assertNull(observableListIterator.getCurrent());
    // forward - middle item
    observableListIterator.next();
    assertEquals(MIDDLE, observableListIterator.next());
    assertEquals(MIDDLE, observableListIterator.getCurrent());
    assertEquals(FIRST, observableListIterator.previous());
    assertEquals(FIRST, observableListIterator.getCurrent());
    // forward - last item
    observableListIterator.next();
    assertEquals(LAST, observableListIterator.next());
    assertEquals(LAST, observableListIterator.getCurrent());
    assertEquals(MIDDLE, observableListIterator.previous());
    assertEquals(MIDDLE, observableListIterator.getCurrent());
    // after last
    observableListIterator.next();
    assertNull(observableListIterator.next());
    assertNull(observableListIterator.getCurrent());
    assertEquals(LAST, observableListIterator.previous());
    assertEquals(LAST, observableListIterator.getCurrent());
    // backward - last item
    observableListIterator.next();
    assertEquals(LAST, observableListIterator.previous());
    assertEquals(LAST, observableListIterator.getCurrent());
    assertNull(observableListIterator.next());
    assertNull(observableListIterator.getCurrent());
    // backward - middle item
    observableListIterator.previous();
    assertEquals(MIDDLE, observableListIterator.previous());
    assertEquals(MIDDLE, observableListIterator.getCurrent());
    assertEquals(LAST, observableListIterator.next());
    assertEquals(LAST, observableListIterator.getCurrent());
    // backward - first item
    observableListIterator.previous();
    assertEquals(FIRST, observableListIterator.previous());
    assertEquals(FIRST, observableListIterator.getCurrent());
    assertEquals(MIDDLE, observableListIterator.next());
    assertEquals(MIDDLE, observableListIterator.getCurrent());
    // before first
    observableListIterator.previous();
    assertNull(observableListIterator.previous());
    assertNull(observableListIterator.getCurrent());
    assertEquals(FIRST, observableListIterator.next());
    assertEquals(FIRST, observableListIterator.getCurrent());
  }

  @Test
  void two_items() {
    ObservableListIterator<TestItem> iterator = new ObservableListIterator<>(new ArrayList<>(List.of(FIRST, LAST)));

    assertAll(
        () -> assertNull(iterator.getCurrent()),
        () -> assertEquals(FIRST, iterator.next()),
        () -> assertFalse(iterator.hasPrevious()),
        () -> assertTrue(iterator.hasNext()),

        () -> assertEquals(LAST, iterator.next()),
        () -> assertTrue(iterator.hasPrevious()),
        () -> assertFalse(iterator.hasNext()),

        () -> assertEquals(FIRST, iterator.previous()),
        () -> assertFalse(iterator.hasPrevious()),
        () -> assertTrue(iterator.hasNext()),

        () -> assertEquals(LAST, iterator.next()),
        () -> assertTrue(iterator.hasPrevious()),
        () -> assertFalse(iterator.hasNext())
    );
  }

  private record TestItem(String name) {
  }

  ;
}