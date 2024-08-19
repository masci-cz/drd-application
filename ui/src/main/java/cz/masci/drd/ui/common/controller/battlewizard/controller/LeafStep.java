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

package cz.masci.drd.ui.common.controller.battlewizard.controller;

import cz.masci.drd.ui.common.controller.WizardStep;
import cz.masci.drd.ui.common.model.WizardViewModel;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LeafStep extends WizardViewModel implements WizardStep {

  private WizardStep current;
  private Region view;

  public LeafStep(Builder<? extends Region> viewBuilder) {
    view = viewBuilder.build();
  }

  @Override
  public WizardStep next() {
    return doStep();
  }

  @Override
  public WizardStep previous() {
    return doStep();
  }

  @Override
  public WizardStep getCurrent() {
    return current;
  }

  @Override
  public Region getView() {
    return view;
  }

  protected <T extends Region> void setView(T view) {
    this.view = view;
  }

  private WizardStep doStep() {
    current = (current == null) ? this : null;
    return current;
  }

}
