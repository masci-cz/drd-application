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

import cz.masci.drd.ui.app.battle.wizard.model.SelectedActionModel;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class SelectActionViewBuilderFactory {

  public static Builder<Region> createSelectActionViewBuilder(String name, SelectedActionModel viewModel) {
    return switch (name) {
      case "Útok na blízko",
           "Útok na dálku" -> new SelectActionCombatViewBuilder(viewModel);
      case "Kouzlení" -> new SelectActionMagicViewBuilder(viewModel);
      case "Jiná akce" -> new SelectActionCommentViewBuilder(viewModel);
      case "Příprava",
           "Mluvení",
           "Čekání" -> Region::new;
      default -> throw new RuntimeException("No view builder for SelectActionView exists");
    };
  }
}


