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
import cz.masci.springfx.mvci.util.MFXBuilderUtils;
import io.github.palexdev.materialfx.builders.layout.VBoxBuilder;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.MFXValidator;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SelectActionMagicViewBuilder implements Builder<Region> {

  private final SelectedActionModel viewModel;

  @Override
  public Region build() {
    MFXComboBox<DuellistDTO> duellistComboBox = MFXComboBoxBuilder.<DuellistDTO>builder()
                                                                  .promptText("Vyberte příjemce kouzla")
                                                                  .floatingText("Příjemce")
                                                                  .converter(duellist -> String.format("%s - %s", duellist.getGroupName(), duellist.getName()))
                                                                  .items(viewModel.duellistsProperty())
                                                                  .selectedItemProperty(viewModel.consumerProperty())
                                                                  .maxWidth(Double.MAX_VALUE)
                                                                  .build();

    MFXTextField spellText = MFXBuilderUtils.createTextField("Kouzlo", Double.MAX_VALUE);
    spellText.textProperty()
             .bindBidirectional(viewModel.spellProperty());

    var result = VBoxBuilder.vBox()
                            .setSpacing(5.0)
                            .addChildren(duellistComboBox, spellText)
                            .setMaxWidth(Double.MAX_VALUE)
                            .setPadding(new Insets(5.0))
                            .getNode();
    BorderPane.setMargin(result, new Insets(5.0));

    // enhance viewModel constraints
    var validator = new MFXValidator();
    validator.constraint(Constraint.of("Vyberte příjemce kouzla", viewModel.consumerProperty()
                                                                           .isNotNull()));
    validator.constraint(Constraint.of("Zadejte kouzlo", viewModel.spellProperty()
                                                                  .isNotEmpty()));
    viewModel.getValidator()
             .dependsOn(validator);

    return result;
  }
}
