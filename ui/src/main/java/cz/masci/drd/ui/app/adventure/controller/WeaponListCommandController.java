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

package cz.masci.drd.ui.app.adventure.controller;

import cz.masci.drd.ui.app.adventure.interactor.WeaponInteractor;
import cz.masci.drd.ui.app.adventure.model.WeaponDetailModel;
import cz.masci.drd.ui.app.adventure.model.WeaponListModel;
import cz.masci.drd.ui.common.controller.AbstractListCommandController;
import cz.masci.drd.ui.common.model.StatusBarViewModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeaponListCommandController extends AbstractListCommandController<Long, WeaponDetailModel> {

  public WeaponListCommandController(WeaponListModel viewModel, StatusBarViewModel statusBarViewModel, WeaponInteractor interactor) {
    super(viewModel, statusBarViewModel, interactor);
  }

  @Override
  protected String getItemDisplayInfo(WeaponDetailModel item) {
    return item.getName();
  }

}
