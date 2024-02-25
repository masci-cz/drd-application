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
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WizardViewModel {
  private final BooleanProperty prevDisable = new SimpleBooleanProperty(false);
  private final BooleanProperty nextDisable = new SimpleBooleanProperty(false);
  private final StringProperty prevText = new SimpleStringProperty("");
  private final StringProperty nextText = new SimpleStringProperty("");
  private final StringProperty title = new SimpleStringProperty("");

  // region getters and setters
  public boolean isPrevDisable() {
    return prevDisable.get();
  }

  public BooleanProperty prevDisableProperty() {
    return prevDisable;
  }

  public void setPrevDisable(boolean prevDisable) {
    this.prevDisable.set(prevDisable);
  }

  public boolean isNextDisable() {
    return nextDisable.get();
  }

  public BooleanProperty nextDisableProperty() {
    return nextDisable;
  }

  public void setNextDisable(boolean nextDisable) {
    this.nextDisable.set(nextDisable);
  }

  public String getPrevText() {
    return prevText.get();
  }

  public StringProperty prevTextProperty() {
    return prevText;
  }

  public void setPrevText(String prevText) {
    this.prevText.set(prevText);
  }

  public String getNextText() {
    return nextText.get();
  }

  public StringProperty nextTextProperty() {
    return nextText;
  }

  public void setNextText(String nextText) {
    this.nextText.set(nextText);
  }

  public String getTitle() {
    return title.get();
  }

  public StringProperty titleProperty() {
    return title;
  }

  public void setTitle(String title) {
    this.title.set(title);
  }
  // endregion
}
