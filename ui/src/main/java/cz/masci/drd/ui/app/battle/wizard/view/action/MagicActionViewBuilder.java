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

import cz.masci.drd.ui.app.battle.wizard.model.action.MagicActionModel;
import cz.masci.springfx.mvci.util.MFXBuilderUtils;
import io.github.palexdev.materialfx.builders.layout.FlowPaneBuilder;
import io.github.palexdev.materialfx.builders.layout.VBoxBuilder;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MagicActionViewBuilder implements Builder<Region> {

  private final MagicActionModel viewModel;

  @Override
  public Region build() {
    var defenseLifeResult = MFXBuilderUtils.createTextField("Životy", 50.0);
    viewModel.bindLife(defenseLifeResult.textProperty());

    return VBoxBuilder.vBox()
                      .setSpacing(5.0)
                      .addChildren(
                          FlowPaneBuilder.flowPane()
                                         .setHGap(10.0)
                                         .addChildren(
                                             new Label("Bojovník"),
                                             new Label(viewModel.getAttackerName()),
                                             new Label("sesílá kouzlo"),
                                             new Label(viewModel.getSpell()),
                                             new Label("na bojovníka"),
                                             new Label(viewModel.getDefenderName()),
                                             new Label("s počtem životů"),
                                             new Label(viewModel.getAttackerLife())
                                         )
                                         .setMaxWidth(Double.MAX_VALUE)
                                         .getNode(),
                          FlowPaneBuilder.flowPane()
                                         .setHGap(10.0)
                                         .addChildren(
                                             new Label("Bojovník"),
                                             new Label(viewModel.getDefenderName()),
                                             new Label("byl zasažen za"),
                                             defenseLifeResult,
                                             new Label("životů"),
                                             new Label("s počtem životů"),
                                             new Label(viewModel.getDefenderLife())
                                         )
                                         .setMaxWidth(Double.MAX_VALUE)
                                         .getNode()
                      )
                      .setPadding(new Insets(5))
                      .getNode();
  }
}
