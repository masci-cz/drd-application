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

package cz.masci.drd.ui.common.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public interface WizardModel {
  BooleanProperty prevDisableProperty();

  default boolean isPrevDisable() {
    return prevDisableProperty().get();
  }

  default void setPrevDisable(boolean prevDisable) {
    prevDisableProperty().set(prevDisable);
  }

  BooleanProperty nextDisableProperty();

  default boolean isNextDisable() {
    return nextDisableProperty().get();
  }

  default void setNextDisable(boolean nextDisable) {
    nextDisableProperty().set(nextDisable);
  }

  StringProperty prevTextProperty();

  default String getPrevText() {
    return prevTextProperty().get();
  }

  default void setPrevText(String prevText) {
    prevTextProperty().set(prevText);
  }

  StringProperty nextTextProperty();

  default String getNextText() {
    return nextTextProperty().get();
  }

  default void setNextText(String nextText) {
    nextTextProperty().set(nextText);
  }

  StringProperty titleProperty();

  default String getTitle() {
    return titleProperty().get();
  }

  default void setTitle(String title) {
    titleProperty().set(title);
  }

  boolean hasNext();

  boolean hasPrevious();
}
