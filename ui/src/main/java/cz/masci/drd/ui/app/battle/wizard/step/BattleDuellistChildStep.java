/*
 * Copyright (C) 2026 Daniel Masek
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cz.masci.drd.ui.app.battle.wizard.step;

import cz.masci.drd.ui.app.battle.wizard.controller.BattleDuellistDetailController;
import cz.masci.drd.ui.app.battle.wizard.interactor.BattleInteractor;
import cz.masci.drd.ui.app.battle.wizard.model.BattleDuellistDetailModel;
import cz.masci.drd.ui.app.battle.wizard.model.BattleDuellistListModel;
import cz.masci.drd.ui.app.battle.wizard.view.BattleDuellistListViewBuilder;
import cz.masci.drd.ui.util.wizard.model.WizardLeafStep;
import cz.masci.springfx.mvci.controller.impl.SimpleController;
import cz.masci.springfx.mvci.view.builder.BorderPaneBuilder;
import cz.masci.wizard.simple.SimpleLeafStep;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.collections.transformation.FilteredList;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BattleDuellistChildStep extends WizardLeafStep {

  private final BattleDuellistListModel viewModel;
  private final BattleInteractor interactor;

  private final FilteredList<BattleDuellistDetailModel> filteredList;
  private final String groupName;

  public BattleDuellistChildStep(BattleInteractor interactor, String groupName) {
    super("Bojovníci skupiny: " + groupName);

    this.groupName = groupName;
    this.interactor = interactor;
    viewModel = new BattleDuellistListModel();
    filteredList = new FilteredList<>(viewModel.getElements(), detailModel -> detailModel.isValid() && !detailModel.isDirty());

    var battleDuellistListViewBuilder = new BattleDuellistListViewBuilder(viewModel);
    var listController = new SimpleController<>(battleDuellistListViewBuilder);
    var detailController = new BattleDuellistDetailController(viewModel);

    var builder = BorderPaneBuilder.builder()
                                   .withCenter(listController.getView())
                                   .withTop(detailController.getView());
    super.setBuilder(builder);
  }

  @Override
  public BooleanExpression valid() {
    return Bindings.size(filteredList)
                   .greaterThanOrEqualTo(1)
                   .and(Bindings.size(viewModel.getElements())
                                .isEqualTo(Bindings.size(filteredList)));
  }

  @Override
  protected void complete(SimpleLeafStep<WizardLeafStep> wizardLeafStepV3SimpleLeafStep) {
    interactor.setDuellists(groupName, viewModel.getElements());
  }
}

