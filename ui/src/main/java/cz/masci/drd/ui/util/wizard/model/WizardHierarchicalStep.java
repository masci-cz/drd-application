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

package cz.masci.drd.ui.util.wizard.model;

import cz.masci.wizard.api.step.Step;
import cz.masci.wizard.simple.SimpleHierarchicalStep;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Slf4j
public abstract class WizardHierarchicalStep implements WizardStepProvider {
    private SimpleHierarchicalStep<WizardHierarchicalStep> step;

    @Getter
    private String prevText;
    @Getter
    private String nextText;
    protected final SimpleBooleanProperty prevDisabledProperty = new SimpleBooleanProperty(false);
    protected final SimpleBooleanProperty nextDisabledProperty = new SimpleBooleanProperty(false);

    @Override
    public SimpleHierarchicalStep<WizardHierarchicalStep> getStep() {
        if (step == null) {
            step = SimpleHierarchicalStep.<WizardHierarchicalStep>builder()
                    .children(Stream
                            .ofNullable(getChildren())
                            .flatMap(List::stream)
                            .map(WizardStepProvider::getStep)
                            .toList()
                    )
                    .status(this)
                    // forward hooks
                    .doBeforeEntry(this::initStep)
                    .cancelNextStepPredicate(shouldCancelStep(StepDirection.NEXT))
                    .doBeforeNext(checkStep(StepDirection.NEXT))
                    .doAfterNext(this::doAfterStep)
                    // backward hooks
                    .doBeforePrev(checkStep(StepDirection.PREV))
                    .cancelPrevStepPredicate(shouldCancelStep(StepDirection.PREV))
                    .skipNextStepPredicate(this::skipStep)
                    .doAfterPrev(this::doAfterStep)
                    .build();
        }
        return step;
    }

    public BooleanExpression prevDisabled() {
        return prevDisabledProperty;
    }

    public BooleanExpression nextDisabled() {
        return nextDisabledProperty;
    }

    protected abstract String getPrevText(int idx);

    protected abstract String getNextText(int idx);

    protected List<WizardStepProvider> getChildren() {
        return new ArrayList<>();
    }

    protected void initStep(SimpleHierarchicalStep<WizardHierarchicalStep> step) {
        // Initialize step when first entered
    }

    protected void updateStatus(SimpleHierarchicalStep<WizardHierarchicalStep> step) {
        // Update status after moving to the next step
    }

    protected boolean skipStep(Integer idx, Step step) {
        return false;
    }

    protected void checkStep(SimpleHierarchicalStep<WizardHierarchicalStep> step, StepDirection direction) {
        // Validate or check conditions before moving to the next step
    }

    protected boolean shouldCancelStep(Integer idc, SimpleHierarchicalStep<WizardHierarchicalStep> step, StepDirection direction) {
        return false;
    }

    private Consumer<SimpleHierarchicalStep<WizardHierarchicalStep>> checkStep(StepDirection direction) {
        return step -> checkStep(step, direction);
    }

    private BiPredicate<Integer, SimpleHierarchicalStep<WizardHierarchicalStep>> shouldCancelStep(StepDirection direction) {
        return (idx, step) -> shouldCancelStep(idx, step, direction);
    }

    private void doAfterStep(SimpleHierarchicalStep<WizardHierarchicalStep> step) {
        // Hook after moving to the previous/next step
        var currentIdx = step.getCurrentIdx();
        var parent = Optional.ofNullable(step.getParent());

        prevText = step.isFirstStep() && parent.isPresent() ? step.getParent().getStatus().getPrevText() : getPrevText(currentIdx);
        nextText = step.isLastStep() && parent.isPresent() ? step.getParent().getStatus().getNextText() : getNextText(currentIdx);

        updateStatus(step);
    }
}
