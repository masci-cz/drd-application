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

import cz.masci.springfx.mvci.controller.ViewProvider;
import cz.masci.wizard.simple.SimpleLeafStep;
import javafx.beans.binding.BooleanExpression;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import lombok.Getter;

public abstract class WizardLeafStep implements WizardStepProvider, ViewProvider<Region> {
    private SimpleLeafStep<WizardLeafStep> step;
    private Region view;
    private Builder<? extends Region> builder;
    private boolean alwaysRebuildView;

    public WizardLeafStep(String title) {
        this(title, null);
    }

    public WizardLeafStep(String title, Builder<? extends Region> builder) {
        this(title, builder, false);
    }

    public WizardLeafStep(String title, Builder<? extends Region> builder, boolean alwaysRebuildView) {
        this.title = title;
        this.builder = builder;
        this.alwaysRebuildView = alwaysRebuildView;
    }

    @Getter
    private final String title;

    @Override
    public SimpleLeafStep<WizardLeafStep> getStep() {
        if (step == null) {
            step = SimpleLeafStep.<WizardLeafStep>builder()
                    .value(this)
                    .name(title)
                    .validator(leaf -> valid().get())
                    .complete(this::complete)
                    .cancel(this::cancel)
                    .build();
        }
        return step;
    }

    @Override
    public final Region getView() {
        if (alwaysRebuildView || view == null && builder != null) {
            view = builder.build();
        }
        return view;
    }

    public abstract BooleanExpression valid();

    protected abstract void complete(SimpleLeafStep<WizardLeafStep> wizardLeafStepV3SimpleLeafStep);

    protected void cancel(SimpleLeafStep<WizardLeafStep> wizardLeafStepV3SimpleLeafStep) {
        // default no-op
    }

    protected void setBuilder(Builder<? extends Region> builder) {
        this.builder = builder;
    }

}
