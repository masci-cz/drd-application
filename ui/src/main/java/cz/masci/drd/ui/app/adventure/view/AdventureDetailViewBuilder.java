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

package cz.masci.drd.ui.app.adventure.view;

import static cz.masci.springfx.mvci.util.MFXBuilderUtils.createTextField;
import static cz.masci.springfx.mvci.util.constraint.ConstraintUtils.isNotEmptyWhenPropertyIsNotEmpty;

import cz.masci.drd.ui.app.adventure.model.AdventureDetailModel;
import cz.masci.springfx.mvci.model.list.ListModel;
import cz.masci.springfx.mvci.util.BuilderUtils;
import cz.masci.springfx.mvci.util.property.PropertyUtils;
import cz.masci.springfx.mvci.view.builder.DetailViewBuilder;
import io.github.palexdev.materialfx.builders.layout.VBoxBuilder;
import javafx.beans.property.Property;
import javafx.geometry.Insets;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class AdventureDetailViewBuilder extends DetailViewBuilder<AdventureDetailModel> implements Builder<Region> {

  public AdventureDetailViewBuilder(ListModel<AdventureDetailModel> viewModel) {
    super(viewModel);
  }

  @Override
  public Region build() {
    Property<AdventureDetailModel> selectedProperty = viewModel.selectedElementProperty();
    // create text fields with validation
    var nameTextField = createTextField("Název", Double.MAX_VALUE);
    var nameConstraint = isNotEmptyWhenPropertyIsNotEmpty(nameTextField.textProperty(), selectedProperty, "Název");
    var nameTextFieldWithValidation = BuilderUtils.enhanceValidatedNodeWithSupportingText(nameTextField, PropertyUtils.not(nameTextField.delegateFocusedProperty())::addListener, nameConstraint);

    bindBidirectional(nameTextField.textProperty(), AdventureDetailModel::nameProperty);

    // set on focus view
    viewModel.setOnFocusView(nameTextField::requestFocus);

    return VBoxBuilder.vBox()
        .setSpacing(5.0)
        .addChildren(nameTextFieldWithValidation)
        .setPadding(new Insets(5.0, 5.0, 5.0, 10.0))
        .setPrefWidth(400.0)
        .getNode();
  }
}
