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
import cz.masci.drd.ui.app.battle.wizard.model.SelectActionCloseCombatModel;
import io.github.palexdev.materialfx.builders.layout.VBoxBuilder;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.geometry.Insets;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SelectActionCloseCombatViewBuilder implements Builder<Region> {

  private final SelectActionCloseCombatModel viewModel;

  @Override
  public Region build() {
    var duellistComboBox = new MFXComboBox<DuellistDTO>();
    duellistComboBox.setPromptText("Vyberte obránce");
    duellistComboBox.setFloatingText("Obránce");
    duellistComboBox.setItems(viewModel.duellistsProperty().filtered(duellist -> !viewModel.getAttacker().equals(duellist)));
    duellistComboBox.getSelectionModel().bindItemBidirectional(viewModel.selectedDefenderProperty(), viewModel.duellistsProperty()::indexOf, (clearing, newValue, property) -> {
      if (clearing) {
        viewModel.setSelectedDefender(null);
      } else {
        property.setValue(newValue);
      }
    });
    duellistComboBox.setMaxWidth(Double.MAX_VALUE);

    return VBoxBuilder.vBox()
        .addChildren(duellistComboBox)
        .setMaxWidth(Double.MAX_VALUE)
        .setPadding(new Insets(5.0))
        .getNode();
  }
}
