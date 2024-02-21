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

import io.github.palexdev.materialfx.builders.layout.BorderPaneBuilder;
import io.github.palexdev.materialfx.builders.layout.HBoxBuilder;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.util.Builder;

public class WizardViewBuilder implements Builder<Region> {

  @Override
  public Region build() {
    var title = new Text("TEST");

    return
        new BorderPaneBuilder()
        .setTop(new HBoxBuilder()
            .setAlignment(Pos.CENTER)
            .setHGrow(title, Priority.ALWAYS)
            .addChildren(title)
            .getNode())
        .getNode();
  }
}
