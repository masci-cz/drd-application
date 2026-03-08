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

package cz.masci.drd.ui.util.wizard.controller;

import cz.masci.drd.ui.util.wizard.model.WizardViewModel;
import cz.masci.drd.ui.util.wizard.model.WizardHierarchicalStep;
import cz.masci.drd.ui.util.wizard.model.WizardLeafStep;
import cz.masci.drd.ui.util.wizard.view.WizardViewBuilder;
import cz.masci.springfx.mvci.controller.ViewProvider;
import cz.masci.wizard.api.step.HierarchicalStep;
import cz.masci.wizard.api.step.StepManager;
import cz.masci.wizard.api.step.StepState;
import cz.masci.wizard.simple.SimpleStepManager;
import cz.masci.wizard.simple.SimpleStepState;
import javafx.beans.binding.Bindings;
import javafx.scene.layout.Region;

import java.util.Optional;
import java.util.function.Consumer;

public class WizardController implements ViewProvider<Region> {
    private final WizardViewBuilder builder;
    private final WizardViewModel wizardViewModel;
    private final StepManager<StepState<WizardHierarchicalStep, WizardLeafStep>, WizardHierarchicalStep, WizardLeafStep> stepManager;

    public WizardController(HierarchicalStep<WizardHierarchicalStep> rootStep) {
        this.stepManager = new SimpleStepManager<>(new SimpleStepState<>(), rootStep);
        this.wizardViewModel = new WizardViewModel();
        this.builder = new WizardViewBuilder(() -> getView(StepManager::prev), () -> getView(StepManager::next), wizardViewModel);
    }

    @Override
    public Region getView() {
        return builder.build(getView(StepManager::next).orElseThrow());
    }

    private Optional<Region> getView(Consumer<StepManager<StepState<WizardHierarchicalStep, WizardLeafStep>, WizardHierarchicalStep, WizardLeafStep>> direction) {
        direction.accept(stepManager);
        var stepState = stepManager.get();

        if (stepState.getStatus().isPresent() && stepState.getValue().isPresent()) {
            var wizardState = stepState.getStatus().get();
            var wizardValue = stepState.getValue().get();
            updateWizardViewModel(wizardState, wizardValue);
        }

        return stepState.getValue().map(WizardLeafStep::getView);
    }

    private void updateWizardViewModel(WizardHierarchicalStep wizardState, WizardLeafStep wizardValue) {
        wizardViewModel.titleProperty().set(wizardValue.getTitle());
        wizardViewModel.prevTextProperty().set(wizardState.getPrevText());
        if (wizardViewModel.prevDisableProperty().isBound()) {
            wizardViewModel.prevDisableProperty().unbind();
        }
        wizardViewModel.prevDisableProperty().bind(wizardState.prevDisabled());
        wizardViewModel.nextTextProperty().set(wizardState.getNextText());
        if (wizardViewModel.nextDisableProperty().isBound()) {
            wizardViewModel.nextDisableProperty().unbind();
        }
        wizardViewModel.nextDisableProperty().bind(Bindings.or(wizardState.nextDisabled(), wizardValue.valid().not()));
    }
}
