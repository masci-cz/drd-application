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

import cz.masci.drd.ui.app.battle.wizard.model.action.CombatActionModel;

public class ShootActionViewBuilder extends CombatActionViewBuilder {

  public ShootActionViewBuilder(CombatActionModel viewModel) {
    super(viewModel);
  }

  @Override
  protected String getAttackText() {
    return "střílí na";
  }

  @Override
  protected String getDefenseText() {
    return "uhybá střelbě";
  }
}
