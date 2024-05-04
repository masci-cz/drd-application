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

package cz.masci.drd.ui.app.battle.wizard.controller;

import cz.masci.drd.ui.app.battle.wizard.interactor.BattleInteractor;
import cz.masci.drd.ui.app.battle.wizard.model.BattleGroupListModel;
import cz.masci.drd.ui.app.battle.wizard.view.BattleGroupListViewBuilder;
import cz.masci.drd.ui.util.wizard.controller.step.impl.TitleLeafStep;
import cz.masci.springfx.mvci.controller.impl.SimpleController;
import cz.masci.springfx.mvci.util.builder.BackgroundTaskBuilder;
import cz.masci.springfx.mvci.view.builder.BorderPaneBuilder;
import javafx.scene.layout.Region;

public class BattleGroupStepController extends TitleLeafStep {

  private final BorderPaneBuilder viewBuilder;
  private final BattleGroupListModel viewModel;
  private final BattleInteractor interactor;

  public BattleGroupStepController(BattleInteractor interactor) {
    super("Skupiny");

    this.interactor = interactor;
    viewModel = new BattleGroupListModel();

    var battleGroupListViewBuilder = new BattleGroupListViewBuilder(viewModel);
    var listController = new SimpleController<>(battleGroupListViewBuilder);
    var detailController = new BattleGroupStepDetailController(viewModel, interactor);

    viewBuilder = BorderPaneBuilder.builder()
        .withCenter(listController.getView())
        .withTop(detailController.getView());
  }

  @Override
  public Region view() {
    load();
    return viewBuilder.build();
  }

  private void load() {
    viewModel.getElements().clear();
    BackgroundTaskBuilder.task(interactor::list).onSucceeded(viewModel.getElements()::setAll).start();
  }

}
