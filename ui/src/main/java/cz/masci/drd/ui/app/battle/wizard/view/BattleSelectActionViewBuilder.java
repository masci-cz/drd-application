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

import cz.masci.springfx.mvci.view.builder.BorderPaneBuilder;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class BattleSelectActionViewBuilder implements Builder<Region> {

  @Override
  public Region build() {
    MFXComboBox<String> actionTypeComboBox = new MFXComboBox<>();
    actionTypeComboBox.getItems().addAll("Útok na blízko", "Kouzlení", "Příprava", "Útok na dálku", "Mluvení", "Jiná akce", "Čekání");
    return BorderPaneBuilder.builder().withTop(actionTypeComboBox).build();
  }
}
