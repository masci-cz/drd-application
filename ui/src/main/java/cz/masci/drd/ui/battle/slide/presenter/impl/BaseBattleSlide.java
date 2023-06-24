/*
 * Copyright (c) 2023
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

package cz.masci.drd.ui.battle.slide.presenter.impl;

import cz.masci.drd.service.BattleService;
import cz.masci.drd.ui.battle.slide.presenter.BattleSlide;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;

@RequiredArgsConstructor
public abstract class BaseBattleSlide<T> implements BattleSlide<T> {

  protected final BattleService battleService;
}
