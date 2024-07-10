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

import cz.masci.springfx.mvci.util.MFXBuilderUtils;
import io.github.palexdev.materialfx.builders.layout.FlowPaneBuilder;
import io.github.palexdev.materialfx.builders.layout.HBoxBuilder;
import io.github.palexdev.materialfx.builders.layout.VBoxBuilder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class CloseCombatActionViewBuilder implements Builder<Region> {

  @Override
  public Region build() {
    var attackRoll = MFXBuilderUtils.createTextField("Útok", 50.0);
    var defenseRoll = MFXBuilderUtils.createTextField("Obrana", 50.0);
    var defenseResult = MFXBuilderUtils.createTextField("Obrana", 50.0);
    var defenderDefendedBox = HBoxBuilder.hBox()
                                         .addChildren(new Label("Obránce se ubránil"))
                                         .getNode();
    var defenderNotDefendedBox = HBoxBuilder.hBox()
                                            .setAlignment(Pos.CENTER_LEFT)
                                            .addChildren(
                                                new Label("Obránce byl zasažen za :"),
                                                defenseResult,
                                                new Label(" životů")
                                            )
                                            .getNode();

    return VBoxBuilder.vBox()
                      .setSpacing(5.0)
                      .addChildren(
                          FlowPaneBuilder.flowPane()
                                         .addChildren(
                                             new Label("Bojovník XYZ útočí na ZYX útokem : X + "),
                                             attackRoll,
                                             new Label(" = Z s počtem životů 5/6")
                                         )
                                         .setMaxWidth(Double.MAX_VALUE)
                                         .getNode(),
                          FlowPaneBuilder.flowPane()
                                         .addChildren(
                                             new Label("Bojovník ZYX se brání útoku XYZ obranou : Y + "),
                                             defenseRoll,
                                             new Label(" = W s počtem životů 6/6")
                                         )
                                         .setMaxWidth(Double.MAX_VALUE)
                                         .getNode(),
                          defenderDefendedBox,
                          defenderNotDefendedBox
                      )
                      .setPadding(new Insets(5))
                      .getNode();
  }

}
