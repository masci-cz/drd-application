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

import cz.masci.drd.ui.adventure.model.WeaponDetailModel;
import cz.masci.drd.ui.adventure.model.WeaponListModel;
import cz.masci.drd.ui.util.ConstraintUtils;
import cz.masci.drd.ui.util.PropertyUtils;
import cz.masci.drd.ui.util.ViewBuilderUtils;
import cz.masci.springfx.mvci.util.BuilderUtils;
import io.github.palexdev.materialfx.builders.control.TextFieldBuilder;
import io.github.palexdev.materialfx.builders.layout.VBoxBuilder;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.enums.FloatMode;
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
    Var<WeaponDetailModel> selectedProperty = viewModel.selectedItemProperty();
    // TODO update constraint to check nullable selected property for all text fields
    var nameTextField = createTextField("Název", Double.MAX_VALUE);
    var nameConstraint = ConstraintUtils.isNotEmpty(nameTextField.textProperty(), "Název");
    var nameTextFieldWithValidation = BuilderUtils.enhanceValidatedNodeWithSupportingText(nameTextField, PropertyUtils.not(nameTextField.delegateFocusedProperty())::addListener, nameConstraint);

    var strengthTextField = createTextField("Útočné číslo", 250.0);
    var strengthNotEmptyConstraint = ConstraintUtils.isNotEmpty(strengthTextField.textProperty(), "Útočné číslo");
    var strengthIsNumberConstraint = ConstraintUtils.isNumber(strengthTextField.textProperty(), "Útočné číslo");
    var strengthTextFieldWithValidation = ViewBuilderUtils.enhanceValidatedNodeWithSupportingText(strengthTextField, PropertyUtils.not(strengthTextField.delegateFocusedProperty())::addListener, strengthNotEmptyConstraint, strengthIsNumberConstraint);

    var damageTextField = createTextField("Útočnost", 250.0);
    var damageIsEmptyConstraint = ConstraintUtils.isNotEmpty(damageTextField.textProperty(), "Útočnost");
    var damageIsNumberConstraint = ConstraintUtils.isNumber(damageTextField.textProperty(), "Útočnost");
    var damageTextFieldWithValidation = ViewBuilderUtils.enhanceValidatedNodeWithSupportingText(damageTextField, PropertyUtils.not(damageTextField.delegateFocusedProperty())::addListener, damageIsEmptyConstraint, damageIsNumberConstraint);

    // create nullable properties
    Var<String> nameProperty = selectedProperty.selectVar(WeaponDetailModel::nameProperty);
    Var<String> strengthProperty = selectedProperty.selectVar(WeaponDetailModel::strengthProperty);
    Var<String> damageProperty = selectedProperty.selectVar(WeaponDetailModel::damageProperty);
    // bind nullable properties to text fields
    nameTextField.textProperty().bindBidirectional(nameProperty);
    strengthTextField.textProperty().bindBidirectional(strengthProperty);
    damageTextField.textProperty().bindBidirectional(damageProperty);
    // listen to changes and update source
    ChangeListener<String> changeListener = (obs, oldValue, newValue) -> viewModel.changeItemProperty();
    nameProperty.observeChanges(changeListener);
    strengthProperty.observeChanges(changeListener);
    damageProperty.observeChanges(changeListener);

//    List<Triple<StringProperty, Function<WeaponDetailModel, StringProperty>, String>> propertiesToBind = List.of(
//        // textProperty -> model property -> default value
//        Triple.of(nameTextField.textProperty(), WeaponDetailModel::nameProperty, ""),
//        Triple.of(strengthTextField.textProperty(), WeaponDetailModel::strengthProperty, ""),
//        Triple.of(damageTextField.textProperty(), WeaponDetailModel::damageProperty, "")
//    );
//    BindingUtils.bindNullableBidirectional(selectedItemProperty, propertiesToBind);

    viewModel.setOnRequestFocusDetailView(nameTextField::requestFocus);

    return VBoxBuilder.vBox()
        .setSpacing(5.0)
        .addChildren(nameTextFieldWithValidation, strengthTextFieldWithValidation, damageTextFieldWithValidation)
        .setPadding(new Insets(5.0))
        .getNode();
  }

  /**
   * Creates and returns a MFXTextField with the specified floating text and max width.
   *
   * @param floatingText the floating text to be displayed in the text field
   * @param maxWidth the max width used for display
   * @return a new MFXTextField object with the specified floating text
   */
  public static MFXTextField createTextField(String floatingText, Double maxWidth) {
    return TextFieldBuilder.textField()
        .setFloatMode(FloatMode.BORDER)
        .setFloatingText(floatingText)
        .setMaxWidth(maxWidth)
        .getNode();
  }

}
