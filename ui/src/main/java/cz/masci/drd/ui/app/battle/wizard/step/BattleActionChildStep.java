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

package cz.masci.drd.ui.app.battle.wizard.step;

import cz.masci.drd.ui.app.battle.wizard.interactor.BattleInteractor;
import cz.masci.drd.ui.app.battle.wizard.model.action.BattleActionModel;
import cz.masci.drd.ui.util.wizard.controller.step.impl.TitleLeafStep;
import javafx.beans.binding.BooleanExpression;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class BattleActionChildStep<T extends BattleActionModel> extends TitleLeafStep {

  private final Builder<Region> builder;
  private final T viewModel;
  private final BattleInteractor interactor;

  public BattleActionChildStep(T viewModel, Builder<Region> builder, BattleInteractor interactor) {
    super(String.format("Proveďte akci pro bojovníka %s", viewModel.getActor().getName()));
    this.viewModel = viewModel;
    this.builder = builder;
    this.interactor = interactor;
  }

  @Override
  public Region view() {
    return builder.build();
  }

  @Override
  public void completeStep() {
    interactor.addRoundHistory(viewModel.execute());
  }

  @Override
  public void executeBeforePrev() {
    viewModel.cancel();
    super.executeBeforePrev();
  }

  @Override
  public BooleanExpression valid() {
    return viewModel.validProperty();
  }

  public boolean isActorAlive() {
    return viewModel.isAlive();
  }
}
