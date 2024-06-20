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

import static cz.masci.springfx.mvci.util.BuilderUtils.enhanceValidatedNodeWithSupportingText;
import static cz.masci.springfx.mvci.util.MFXBuilderUtils.createTextField;
import static cz.masci.springfx.mvci.util.constraint.ConstraintUtils.isNotEmptyWhenPropertyIsNotEmpty;
import static cz.masci.springfx.mvci.util.constraint.ConstraintUtils.isNumberWhenPropertyIsNotEmpty;

import cz.masci.drd.ui.app.adventure.model.WeaponDetailModel;
import cz.masci.springfx.mvci.model.list.ListModel;
import cz.masci.springfx.mvci.util.property.PropertyUtils;
import cz.masci.springfx.mvci.view.builder.DetailViewBuilder;
import io.github.palexdev.materialfx.builders.layout.VBoxBuilder;
import javafx.beans.property.Property;
import javafx.geometry.Insets;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class WeaponDetailViewBuilder extends DetailViewBuilder<WeaponDetailModel> implements Builder<Region> {

  public WeaponDetailViewBuilder(ListModel<WeaponDetailModel> viewModel) {
    super(viewModel);
  }

  @Override
  public Region build() {
    Property<WeaponDetailModel> selectedProperty = viewModel.selectedElementProperty();

    var nameTextField = createTextField("Název", Double.MAX_VALUE);
    var nameisNotEmptyConstraint = isNotEmptyWhenPropertyIsNotEmpty(nameTextField.textProperty(), selectedProperty, "Název");
    var nameTextFieldWithValidation = enhanceValidatedNodeWithSupportingText(nameTextField, PropertyUtils.not(nameTextField.delegateFocusedProperty())::addListener, nameisNotEmptyConstraint);

    var strengthTextField = createTextField("Útočné číslo", 100.0);
    var strengthIsNotEmptyConstraint = isNotEmptyWhenPropertyIsNotEmpty(strengthTextField.textProperty(), selectedProperty, "Útočné číslo");
    var strengthIsNumberConstraint = isNumberWhenPropertyIsNotEmpty(strengthTextField.textProperty(), selectedProperty, "Útočné číslo");
    var strengthTextFieldWithValidation = enhanceValidatedNodeWithSupportingText(strengthTextField, PropertyUtils.not(strengthTextField.delegateFocusedProperty())::addListener, strengthIsNotEmptyConstraint, strengthIsNumberConstraint);

    var damageTextField = createTextField("Útočnost", 100.0);
    var damageIsNotEmptyConstraint = isNotEmptyWhenPropertyIsNotEmpty(damageTextField.textProperty(), selectedProperty,  "Útočnost");
    var damageIsNumberConstraint = isNumberWhenPropertyIsNotEmpty(damageTextField.textProperty(), selectedProperty,  "Útočnost");
    var damageTextFieldWithValidation = enhanceValidatedNodeWithSupportingText(damageTextField, PropertyUtils.not(damageTextField.delegateFocusedProperty())::addListener, damageIsNotEmptyConstraint, damageIsNumberConstraint);

    bindBidirectional(nameTextField.textProperty(), WeaponDetailModel::nameProperty);
    bindBidirectional(strengthTextField.textProperty(), WeaponDetailModel::strengthProperty);
    bindBidirectional(damageTextField.textProperty(), WeaponDetailModel::damageProperty);

    viewModel.setOnFocusView(nameTextField::requestFocus);

    return VBoxBuilder.vBox()
        .setSpacing(10.0)
        .addChildren(nameTextFieldWithValidation, strengthTextFieldWithValidation, damageTextFieldWithValidation)
        .setPadding(new Insets(5.0, 5.0, 5.0, 10.0))
        .getNode();
  }
}
