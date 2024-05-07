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

import cz.masci.drd.ui.app.battle.wizard.controller.battlegroupstep.BattleGroupDetailController;
import cz.masci.drd.ui.app.battle.wizard.interactor.BattleInteractor;
import cz.masci.drd.ui.app.battle.wizard.model.BattleGroupDetailModel;
import cz.masci.drd.ui.app.battle.wizard.model.BattleGroupListModel;
import cz.masci.drd.ui.app.battle.wizard.view.BattleGroupListViewBuilder;
import cz.masci.drd.ui.util.wizard.controller.step.impl.TitleLeafStep;
import cz.masci.springfx.mvci.controller.impl.SimpleController;
import cz.masci.springfx.mvci.util.builder.BackgroundTaskBuilder;
import cz.masci.springfx.mvci.util.builder.ListChangeListenerBuilder;
import cz.masci.springfx.mvci.view.builder.BorderPaneBuilder;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.collections.transformation.FilteredList;
import javafx.scene.layout.Region;

public class BattleGroupStepController extends TitleLeafStep {

  private final BorderPaneBuilder viewBuilder;
  private final BattleGroupListModel viewModel;
  private final BattleInteractor interactor;

  private final FilteredList<BattleGroupDetailModel> filteredList;

  public BattleGroupStepController(BattleInteractor interactor) {
    super("Skupiny");

    this.interactor = interactor;
    viewModel = new BattleGroupListModel();
    filteredList = new FilteredList<>(viewModel.getElements(), detailModel -> detailModel.isValid() && !detailModel.isDirty());
//    filteredList.predicateProperty().bind(Bindings.createObjectBinding(() -> item -> item.isValid() && !item.isDirty(), viewModel.getElements()));
    Bindings.size(filteredList).addListener((observable, oldValue, newValue) -> System.out.println("Size changed to " + newValue));
    viewModel.getElements().addListener(
        new ListChangeListenerBuilder<BattleGroupDetailModel>()
            .onUpdated(detailModel -> System.out.printf("Battle group list onUpdated: %s, valid = %s, dirty = %s\n", detailModel, detailModel.isValid(), detailModel.isDirty()))
            .onAdd(detailModel -> System.out.printf("Battle group list onAdd: %s, valid = %s, dirty = %s\n", detailModel, detailModel.isValid(), detailModel.isDirty()))
            .onPermutated(detailModel -> System.out.printf("Battle group list onPermutated: %s, valid = %s, dirty = %s\n", detailModel, detailModel.isValid(), detailModel.isDirty()))
            .onRemove(detailModel -> System.out.printf("Battle group list onRemove: %s, valid = %s, dirty = %s\n", detailModel, detailModel.isValid(), detailModel.isDirty()))
            .build()
    );

    var battleGroupListViewBuilder = new BattleGroupListViewBuilder(viewModel);
    var listController = new SimpleController<>(battleGroupListViewBuilder);
    var detailController = new BattleGroupDetailController(viewModel);

    viewBuilder = BorderPaneBuilder.builder()
        .withCenter(listController.getView())
        .withTop(detailController.getView());
  }

  @Override
  public Region view() {
    load();
    return viewBuilder.build();
  }

  @Override
  public void execute() {
    if (isValid()) {
      interactor.addGroupList(viewModel.getElements());
    }
  }

  @Override
  public BooleanExpression valid() {
    return Bindings.size(filteredList).greaterThanOrEqualTo(2);
  }

  private void load() {
    viewModel.getElements().clear();
    BackgroundTaskBuilder.task(interactor::list).onSucceeded(viewModel.getElements()::setAll).start();
  }

}
