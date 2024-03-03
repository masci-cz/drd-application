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

package cz.masci.drd.ui.common.controller;

import cz.masci.drd.ui.common.controller.battlewizard.controller.BattleWizardController;
import cz.masci.drd.ui.common.model.WizardViewModel;
import cz.masci.drd.ui.common.view.WizardViewBuilder;
import cz.masci.springfx.mvci.controller.ViewProvider;
import java.util.Objects;
import java.util.Optional;
import javafx.scene.layout.Region;
import org.springframework.stereotype.Component;

@Component
public class WizardController implements ViewProvider<Region> {

  private final WizardViewBuilder builder;
  private final BattleWizardController stepProvider;

  public WizardController() {
    var wizardViewModel = new WizardViewModel();
    builder = new WizardViewBuilder(this::getPrevView, this::getNextView, wizardViewModel);
    stepProvider = new BattleWizardController();

    wizardViewModel.titleProperty().bind(stepProvider.titleProperty());
    wizardViewModel.nextTextProperty().bind(stepProvider.nextTextProperty());
    wizardViewModel.prevTextProperty().bind(stepProvider.prevTextProperty());
    wizardViewModel.nextDisableProperty().bind(stepProvider.nextDisableProperty());
    wizardViewModel.prevDisableProperty().bind(stepProvider.prevDisableProperty());
  }

  @Override
  public Region getView() {
    var step = stepProvider.next();
    Objects.requireNonNull(step, "Wrong initialization of wizard");
    return builder.build(step.getView());
  }

  private Optional<Region> getNextView() {
    var step = stepProvider.next();
    return Optional.ofNullable(step).map(WizardStep::getView);
  }

  private Optional<Region> getPrevView() {
    var step = stepProvider.previous();
    return Optional.ofNullable(step).map(WizardStep::getView);
  }

}
