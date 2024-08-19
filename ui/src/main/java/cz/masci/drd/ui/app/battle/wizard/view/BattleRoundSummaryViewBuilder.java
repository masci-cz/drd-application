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

package cz.masci.drd.ui.app.battle.wizard.view;

import io.github.palexdev.materialfx.builders.layout.VBoxBuilder;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BattleRoundSummaryViewBuilder implements Builder<Region> {

  private final List<String> history;

  @Override
  public Region build() {
    var nodes = history.stream().map(Label::new).toArray(Node[]::new);

    return VBoxBuilder.vBox()
        .setSpacing(5.0)
        .addChildren(nodes)
        .setMaxWidth(Double.MAX_VALUE)
        .setPadding(new Insets(5.0))
        .getNode();
  }
}
