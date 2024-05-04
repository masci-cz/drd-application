package cz.masci.drd.ui.util.wizard.controller;

import cz.masci.drd.ui.util.wizard.controller.step.CompositeStep;
import cz.masci.drd.ui.util.wizard.controller.step.Step;
import cz.masci.drd.ui.util.wizard.model.WizardViewModel;
import cz.masci.drd.ui.util.wizard.view.WizardViewBuilder;
import java.util.Optional;
import javafx.scene.layout.Region;

public class WizardController {

  private final WizardViewBuilder builder;
  private final CompositeStep root;
  private final WizardViewModel wizardViewModel;

  public WizardController(CompositeStep root) {
    wizardViewModel = new WizardViewModel();
    builder = new WizardViewBuilder(this::getPrevView, this::getNextView, wizardViewModel);
    this.root = root;
  }

  public Region getView() {
    Step step = root.next();
    updateWizardViewModel(step);
    return builder.build(step.view());
  }

  private Optional<Region> getNextView() {
    Step step = root.next();
    updateWizardViewModel(step);
    return Optional.ofNullable(step.view());
  }

  private Optional<Region> getPrevView() {
    Step step = root.prev();
    updateWizardViewModel(step);
    return Optional.ofNullable(step.view());
  }

  private void updateWizardViewModel(Step step) {
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
