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

import cz.masci.drd.ui.app.battle.wizard.controller.BattleGroupDetailController;
import cz.masci.drd.ui.app.battle.wizard.interactor.BattleInteractor;
import cz.masci.drd.ui.app.battle.wizard.model.BattleGroupDetailModel;
import cz.masci.drd.ui.app.battle.wizard.model.BattleGroupListModel;
import cz.masci.drd.ui.app.battle.wizard.view.BattleGroupListViewBuilder;
import cz.masci.drd.ui.util.wizard.model.WizardLeafStep;
import cz.masci.springfx.mvci.controller.impl.SimpleController;
import cz.masci.springfx.mvci.view.builder.BorderPaneBuilder;
import cz.masci.wizard.simple.SimpleLeafStep;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.collections.transformation.FilteredList;

public class BattleGroupStep extends WizardLeafStep {

    private final BattleGroupListModel viewModel;
    private final BattleInteractor interactor;

    private final FilteredList<BattleGroupDetailModel> filteredList;

    public BattleGroupStep(BattleInteractor interactor) {
        super("Skupiny");
        this.interactor = interactor;
        viewModel = new BattleGroupListModel();
        filteredList = new FilteredList<>(viewModel.getElements(), detailModel -> detailModel.isValid() && !detailModel.isDirty());

        var battleGroupListViewBuilder = new BattleGroupListViewBuilder(viewModel);
        var listController = new SimpleController<>(battleGroupListViewBuilder);
        var detailController = new BattleGroupDetailController(viewModel);

        var builder = BorderPaneBuilder.builder()
                .withCenter(listController.getView())
                .withTop(detailController.getView());
        super.setBuilder(builder);
    }

    @Override
    public BooleanExpression valid() {
        return Bindings.size(filteredList).greaterThanOrEqualTo(2);
    }

    @Override
    protected void complete(SimpleLeafStep<WizardLeafStep> step) {
        interactor.createBattle(viewModel.getElements());
    }
}
