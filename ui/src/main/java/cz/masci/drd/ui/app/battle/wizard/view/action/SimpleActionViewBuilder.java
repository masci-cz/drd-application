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

package cz.masci.drd.ui.app.battle.wizard.view.action;

import cz.masci.drd.ui.app.battle.wizard.model.action.SimpleActionModel;
import io.github.palexdev.materialfx.builders.layout.FlowPaneBuilder;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleActionViewBuilder implements Builder<Region> {

  private final SimpleActionModel viewModel;

  @Override
  public Region build() {
    return FlowPaneBuilder.flowPane()
        .addChildren(new Label(viewModel.action()))
        .setMaxWidth(Double.MAX_VALUE)
        .setPadding(new Insets(10, 10, 10, 10))
        .getNode();
  }
}
