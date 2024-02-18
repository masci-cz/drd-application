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

package cz.masci.drd.ui.app.monster.controller;

import cz.masci.drd.ui.app.monster.interactor.MonsterInteractor;
import cz.masci.drd.ui.common.model.StatusBarViewModel;
import cz.masci.drd.ui.app.monster.model.MonsterDetailModel;
import cz.masci.drd.ui.app.monster.model.MonsterListModel;
import cz.masci.drd.ui.common.controller.AbstractDetailCommandController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MonsterDetailCommandController extends AbstractDetailCommandController<Long, MonsterDetailModel> {

  public MonsterDetailCommandController(MonsterListModel viewModel, StatusBarViewModel statusBarViewModel, MonsterInteractor interactor) {
    super(viewModel, statusBarViewModel, interactor);
  }

  @Override
  protected String getDeleteConfirmDialogTitle() {
    return "Smazat nestvůru";
  }

  @Override
  protected String getDeleteConfirmDialogContent() {
    return "Opravdu chcete smazat vybranou nestvůru?";
  }

  @Override
  protected String getItemDisplayInfo(MonsterDetailModel item) {
    return item.getName();
  }

}
