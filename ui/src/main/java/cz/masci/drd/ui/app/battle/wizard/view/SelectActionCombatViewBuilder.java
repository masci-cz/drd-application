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

import cz.masci.drd.dto.DuellistDTO;
import cz.masci.drd.ui.app.battle.wizard.model.SelectedActionModel;
import cz.masci.drd.ui.common.view.MFXComboBoxBuilder;
import io.github.palexdev.materialfx.builders.layout.VBoxBuilder;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.MFXValidator;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SelectActionCombatViewBuilder implements Builder<Region> {

  private final SelectedActionModel viewModel;

  @Override
  public Region build() {
    MFXComboBox<DuellistDTO> duellistComboBox = MFXComboBoxBuilder.<DuellistDTO>builder()
                                                                  .promptText("Vyberte obránce")
                                                                  .floatingText("Obránce")
                                                                  .converter(duellist -> String.format("%s - %s", duellist.getGroupName(), duellist.getName()))
                                                                  .items(viewModel.duellistsProperty()
                                                                                  .filtered(duellist -> !viewModel.getActor()
                                                                                                                  .equals(duellist)))
                                                                  .selectedItemProperty(viewModel.consumerProperty())
                                                                  .maxWidth(Double.MAX_VALUE)
                                                                  .build();

    var result = VBoxBuilder.vBox()
                            .addChildren(duellistComboBox)
                            .setMaxWidth(Double.MAX_VALUE)
                            .setPadding(new Insets(5.0))
                            .getNode();
    BorderPane.setMargin(result, new Insets(5.0));

    // enhance viewModel constraints
    var validator = new MFXValidator();
    validator.constraint(Constraint.of("Vyberte obránce", viewModel.consumerProperty()
                                                                   .isNotNull()));
    viewModel.getValidator()
             .dependsOn(validator);

    return result;
  }
}
