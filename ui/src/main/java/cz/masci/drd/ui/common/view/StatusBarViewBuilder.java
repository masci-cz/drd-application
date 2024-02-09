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

package cz.masci.drd.ui.common.view;

import cz.masci.drd.ui.common.model.StatusBarViewModel;
import javafx.beans.binding.BooleanExpression;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatusBarViewBuilder implements Builder<Region> {

  private final StatusBarViewModel viewModel;

  @Override
  public Region build() {
    BooleanExpression showMessage = viewModel.messageProperty().isNotEmpty();
    BooleanExpression showMessageAsError = showMessage.and(viewModel.isErrorProperty());
    BooleanExpression showMessageAsInfo = showMessageAsError.not();

    Text messageLabel = new Text();
    messageLabel.textProperty().bind(viewModel.messageProperty());
    messageLabel.visibleProperty().bind(showMessageAsInfo);
    messageLabel.managedProperty().bind(showMessageAsInfo);
    messageLabel.getStyleClass().add("status-bar-info");

    Text errorLabel = new Text();
    errorLabel.textProperty().bind(viewModel.messageProperty());
    errorLabel.visibleProperty().bind(showMessageAsError);
    errorLabel.managedProperty().bind(showMessageAsError);
    errorLabel.getStyleClass().add("status-bar-error");

    HBox result = new HBox();
    result.getChildren().addAll(messageLabel, errorLabel);
    result.getStyleClass().add("status-bar");

    return result;
  }
}
