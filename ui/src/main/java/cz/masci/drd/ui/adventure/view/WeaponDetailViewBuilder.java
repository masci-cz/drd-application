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

import cz.masci.drd.ui.adventure.model.WeaponDetailModel;
import cz.masci.drd.ui.adventure.model.WeaponListModel;
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
public class WeaponDetailViewBuilder implements Builder<Region> {

  private final WeaponListModel viewModel;

  @Override
  public Region build() {
    Var<WeaponDetailModel> selectedProperty = viewModel.selectedElementProperty();

    var nameTextField = ViewBuilderUtils.createTextField("Název", Double.MAX_VALUE);
    var nameisNotEmptyConstraint = ConstraintUtils.isNotEmptyWhenPropertyIsNotEmpty(nameTextField.textProperty(), selectedProperty, "Název");
    var nameTextFieldWithValidation = BuilderUtils.enhanceValidatedNodeWithSupportingText(nameTextField, PropertyUtils.not(nameTextField.delegateFocusedProperty())::addListener, nameisNotEmptyConstraint);

    var strengthTextField = ViewBuilderUtils.createTextField("Útočné číslo", 100.0);
    var strengthIsNotEmptyConstraint = ConstraintUtils.isNotEmptyWhenPropertyIsNotEmpty(strengthTextField.textProperty(), selectedProperty, "Útočné číslo");
    var strengthIsNumberConstraint = ConstraintUtils.isNumberWhenPropertyIsNotEmpty(strengthTextField.textProperty(), selectedProperty, "Útočné číslo");
    var strengthTextFieldWithValidation = ViewBuilderUtils.enhanceValidatedNodeWithSupportingText(strengthTextField, PropertyUtils.not(strengthTextField.delegateFocusedProperty())::addListener, strengthIsNotEmptyConstraint, strengthIsNumberConstraint);

    var damageTextField = ViewBuilderUtils.createTextField("Útočnost", 100.0);
    var damageIsNotEmptyConstraint = ConstraintUtils.isNotEmptyWhenPropertyIsNotEmpty(damageTextField.textProperty(), selectedProperty,  "Útočnost");
    var damageIsNumberConstraint = ConstraintUtils.isNumberWhenPropertyIsNotEmpty(damageTextField.textProperty(), selectedProperty,  "Útočnost");
    var damageTextFieldWithValidation = ViewBuilderUtils.enhanceValidatedNodeWithSupportingText(damageTextField, PropertyUtils.not(damageTextField.delegateFocusedProperty())::addListener, damageIsNotEmptyConstraint, damageIsNumberConstraint);

    // create nullable properties
    Var<String> nameProperty = selectVarOrElseConst(selectedProperty, WeaponDetailModel::nameProperty, "");
    Var<String> strengthProperty = selectVarOrElseConst(selectedProperty, WeaponDetailModel::strengthProperty, "");
    Var<String> damageProperty = selectVarOrElseConst(selectedProperty, WeaponDetailModel::damageProperty, "");
    // bind nullable properties to text fields
    nameTextField.textProperty().bindBidirectional(nameProperty);
    strengthTextField.textProperty().bindBidirectional(strengthProperty);
    damageTextField.textProperty().bindBidirectional(damageProperty);
    // listen to changes and update source
    ChangeListener<String> changeListener = (obs, oldValue, newValue) -> viewModel.updateElementsProperty();
    nameProperty.observeChanges(changeListener);
    strengthProperty.observeChanges(changeListener);
    damageProperty.observeChanges(changeListener);

    viewModel.setOnFocusView(nameTextField::requestFocus);

    return VBoxBuilder.vBox()
        .setSpacing(10.0)
        .addChildren(nameTextFieldWithValidation, strengthTextFieldWithValidation, damageTextFieldWithValidation)
        .setPadding(new Insets(5.0, 5.0, 5.0, 10.0))
        .getNode();
  }
}
