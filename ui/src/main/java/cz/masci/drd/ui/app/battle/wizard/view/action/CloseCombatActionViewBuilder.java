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

import cz.masci.drd.ui.app.battle.wizard.model.action.CloseCombatActionModel;
import cz.masci.springfx.mvci.util.MFXBuilderUtils;
import io.github.palexdev.materialfx.builders.layout.FlowPaneBuilder;
import io.github.palexdev.materialfx.builders.layout.HBoxBuilder;
import io.github.palexdev.materialfx.builders.layout.VBoxBuilder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CloseCombatActionViewBuilder implements Builder<Region> {

  private final CloseCombatActionModel viewModel;

  @Override
  public Region build() {
    var attackRoll = MFXBuilderUtils.createTextField("Útok", 50.0);
    var defenseRoll = MFXBuilderUtils.createTextField("Obrana", 50.0);
    var defenseLifeResult = MFXBuilderUtils.createTextField("Obrana", 50.0);
    var defenderDefendedBox = HBoxBuilder.hBox()
                                         .addChildren(new Label("Obránce se ubránil"))
                                         .getNode();
    var defenderNotDefendedBox = HBoxBuilder.hBox()
                                            .setAlignment(Pos.CENTER_LEFT)
                                            .addChildren(
                                                new Label("Obránce byl zasažen za :"),
                                                defenseLifeResult,
                                                new Label(" životů")
                                            )
                                            .getNode();
    var attackResult = new Label();
    var defenseResult = new Label();

    // bind
    attackResult.textProperty()
                .bind(viewModel.getAttackResult());
    defenseLifeResult.textProperty()
                     .bind(viewModel.lifeProperty()
                                    .asString(""));
    defenseResult.textProperty()
                 .bind(viewModel.getDefenseResult());
    viewModel.bindRollAttack(attackRoll.textProperty());
    viewModel.bindRollDefense(defenseRoll.textProperty());
    defenderDefendedBox.visibleProperty().bind(viewModel.successProperty().not());
    defenderNotDefendedBox.visibleProperty().bind(viewModel.successProperty());

    return VBoxBuilder.vBox()
                      .setSpacing(5.0)
                      .addChildren(
                          FlowPaneBuilder.flowPane()
                                         .addChildren(
                                             new Label("Bojovník"),
                                             new Label(viewModel.getAttackerName()),
                                             new Label("útočí na"),
                                             new Label(viewModel.getDefenderName()),
                                             new Label("útokem :"),
                                             new Label(viewModel.getBaseAttack()),
                                             new Label("+"),
                                             attackRoll,
                                             new Label("="),
                                             attackResult,
                                             new Label("s počtem životů"),
                                             new Label(viewModel.getAttackerLife())
                                         )
                                         .setMaxWidth(Double.MAX_VALUE)
                                         .getNode(),
                          FlowPaneBuilder.flowPane()
                                         .addChildren(
                                             new Label("Bojovník"),
                                             new Label(viewModel.getDefenderName()),
                                             new Label("se brání útoku"),
                                             new Label(viewModel.getAttackerName()),
                                             new Label("obranou :"),
                                             new Label(viewModel.getBaseDefense()),
                                             new Label("+"),
                                             defenseRoll,
                                             new Label("="),
                                             defenseResult,
                                             new Label("s počtem životů"),
                                             new Label(viewModel.getDefenderLife())
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