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

package cz.masci.drd.ui.adventure.view;

import static cz.masci.drd.ui.util.ReactFxUtils.selectVarOrElseConst;

import cz.masci.drd.ui.adventure.model.AdventureDetailModel;
import cz.masci.drd.ui.adventure.model.AdventureListModel;
import cz.masci.drd.ui.util.ConstraintUtils;
import cz.masci.drd.ui.util.PropertyUtils;
import cz.masci.drd.ui.util.ViewBuilderUtils;
import cz.masci.springfx.mvci.util.BuilderUtils;
import io.github.palexdev.materialfx.builders.layout.VBoxBuilder;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;
import org.reactfx.value.Var;

@RequiredArgsConstructor
public class AdventureDetailViewBuilder implements Builder<Region> {

  private final AdventureListModel viewModel;

  @Override
  public Region build() {
    Var<AdventureDetailModel> selectedProperty = viewModel.selectedElementProperty();
    // create text fields with validation
    var nameTextField = ViewBuilderUtils.createTextField("Název", Double.MAX_VALUE);
    var nameConstraint = ConstraintUtils.isNotEmptyWhenPropertyIsNotEmpty(nameTextField.textProperty(), selectedProperty, "Název");
    var nameTextFieldWithValidation = BuilderUtils.enhanceValidatedNodeWithSupportingText(nameTextField, PropertyUtils.not(nameTextField.delegateFocusedProperty())::addListener, nameConstraint);
    // create nullable properties
    Var<String> nameProperty = selectVarOrElseConst(selectedProperty, AdventureDetailModel::nameProperty, "");
    // bind nullable properties to text fields
    nameTextField.textProperty().bindBidirectional(nameProperty);
    // listen to changes and update source selected property
    ChangeListener<String> changeListener = ((observable, oldValue, newValue) -> viewModel.updateElementsProperty());
    nameProperty.observeChanges(changeListener);

    // set on focus view
    viewModel.setOnFocusView(nameTextField::requestFocus);

    return VBoxBuilder.vBox()
        .setSpacing(5.0)
        .addChildren(nameTextFieldWithValidation)
        .setPadding(new Insets(5.0))
        .getNode();
  }
}
