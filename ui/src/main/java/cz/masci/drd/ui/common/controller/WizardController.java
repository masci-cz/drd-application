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

package cz.masci.drd.ui.common.controller;

import cz.masci.drd.ui.common.view.WizardViewBuilder;
import cz.masci.springfx.mvci.controller.ViewProvider;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import org.springframework.stereotype.Component;

@Component
public class WizardController implements ViewProvider<Region> {

  private final WizardViewBuilder builder;

  private int counter = 1;

  public WizardController() {
    builder = new WizardViewBuilder(this::getPrevView, this::getNextView, createView(counter));
  }

  @Override
  public Region getView() {
    return builder.build();
  }

  private Optional<Region> getNextView() {
    return counter >= 10 ? Optional.empty() : Optional.of(createView(++counter));
  }

  private Optional<Region> getPrevView() {
    return counter <= 1 ? Optional.empty() : Optional.of(createView(--counter));
  }

  private Region createView(int number) {
    var label = new Label(String.format("PANE %d", number));
    label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    label.setAlignment(Pos.CENTER);
    return label;
  }

}
