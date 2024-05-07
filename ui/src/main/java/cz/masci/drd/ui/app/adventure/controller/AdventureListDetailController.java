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

import cz.masci.drd.ui.app.adventure.interactor.AdventureInteractor;
import cz.masci.drd.ui.app.adventure.model.AdventureListModel;
import cz.masci.drd.ui.common.controller.StatusBarController;
import cz.masci.drd.ui.common.model.StatusBarViewModel;
import cz.masci.springfx.mvci.controller.ViewProvider;
import cz.masci.springfx.mvci.util.builder.BackgroundTaskBuilder;
import cz.masci.springfx.mvci.view.builder.BorderPaneBuilder;
import javafx.scene.layout.Region;
import org.springframework.stereotype.Component;

@Component
public class AdventureListDetailController implements ViewProvider<Region> {

  private final BorderPaneBuilder viewBuilder;
  private final AdventureListModel viewModel;
  private final AdventureInteractor interactor;

  public AdventureListDetailController(AdventureInteractor interactor) {
    this.interactor = interactor;
    viewModel = new AdventureListModel();
    var statusBarViewModel = new StatusBarViewModel();

    var listController = new AdventureListController(viewModel);
    var detailController = new AdventureDetailController(viewModel, statusBarViewModel, interactor);
    var listCommandController = new AdventureListCommandController(viewModel, statusBarViewModel, interactor);
    var statusBarController = new StatusBarController(statusBarViewModel);

    viewBuilder = BorderPaneBuilder.builder()
                                   .withCenter(listController.getView())
                                   .withRight(detailController.getView())
                                   .withTop(listCommandController.getView())
                                   .withBottom(statusBarController.getView());
  }

  @Override
  public Region getView() {
    load();
    return viewBuilder.build();
  }

  private void load() {
    viewModel.getElements().clear();
    BackgroundTaskBuilder.task(interactor::list).onSucceeded(viewModel.getElements()::setAll).start();
  }
}