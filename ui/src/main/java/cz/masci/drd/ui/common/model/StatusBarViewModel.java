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

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

public class StatusBarViewModel {

  private final ReadOnlyStringWrapper message = new ReadOnlyStringWrapper();
  private final ReadOnlyBooleanWrapper isError = new ReadOnlyBooleanWrapper();

  public void setInfoMessage(String message) {
    setMessage(message);
    setIsError(false);
  }

  public void setErrorMessage(String message) {
    setMessage(message);
    setIsError(true);
  }

  public void clearMessage() {
    setMessage(null);
  }

  // region Setters and Getters
  public ReadOnlyStringProperty messageProperty() {
    return message.getReadOnlyProperty();
  }

  private void setMessage(String message) {
    this.message.set(message);
  }

  public ReadOnlyBooleanProperty isErrorProperty() {
    return isError.getReadOnlyProperty();
  }

  private void setIsError(boolean isError) {
    this.isError.set(isError);
  }
  // endregion
}
