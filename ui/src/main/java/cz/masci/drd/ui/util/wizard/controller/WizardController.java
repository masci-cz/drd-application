package cz.masci.drd.ui.util.wizard.controller;

import cz.masci.drd.ui.util.wizard.controller.step.CompositeStep;
import cz.masci.drd.ui.util.wizard.controller.step.HierarchicalStep;
import cz.masci.drd.ui.util.wizard.model.WizardViewModel;
import cz.masci.drd.ui.util.wizard.view.WizardViewBuilder;
import java.util.Optional;
import javafx.scene.layout.Region;

public class WizardController {

  private final WizardViewBuilder builder;
  private final CompositeStep root;
  private final WizardViewModel wizardViewModel;

  private HierarchicalStep currentStep = null;

  public WizardController(CompositeStep root) {
    wizardViewModel = new WizardViewModel();
    builder = new WizardViewBuilder(this::getPrevView, this::getNextView, wizardViewModel);
    this.root = root;
  }

  public Region getView() {
    currentStep = root.next();
    updateWizardViewModel(currentStep);
    return builder.build(currentStep.view());
  }

  private Optional<Region> getNextView() {
    currentStep.executeBeforeNext();
    var step = root.next();
    if (step != null) {
      currentStep = step;
      updateWizardViewModel(currentStep);
    }
    return Optional.ofNullable(currentStep.view());
  }

  private Optional<Region> getPrevView() {
    currentStep.executeBeforePrev();
    var step = root.prev();
    if (step != null) {
      currentStep = step;
      updateWizardViewModel(currentStep);
    }
    return Optional.ofNullable(currentStep.view());
  }

  private void updateWizardViewModel(HierarchicalStep step) {
    wizardViewModel.titleProperty().set(step.title());
    wizardViewModel.prevTextProperty().set(step.prevText());
    if (wizardViewModel.prevDisableProperty().isBound()) {
      wizardViewModel.prevDisableProperty().unbind();
    }
    wizardViewModel.prevDisableProperty().bind(step.prevDisabled());
    wizardViewModel.nextTextProperty().set(step.nextText());
    if (wizardViewModel.nextDisableProperty().isBound()) {
      wizardViewModel.nextDisableProperty().unbind();
    }
    wizardViewModel.nextDisableProperty().bind(step.nextDisabled());
  }
}
