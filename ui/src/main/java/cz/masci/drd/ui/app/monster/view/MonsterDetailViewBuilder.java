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

package cz.masci.drd.ui.app.monster.view;

import static cz.masci.springfx.mvci.util.BuilderUtils.enhanceValidatedNodeWithSupportingText;
import static cz.masci.springfx.mvci.util.MFXBuilderUtils.createTextField;
import static cz.masci.springfx.mvci.util.constraint.ConstraintUtils.isNotEmptyWhenPropertyIsNotEmpty;
import static cz.masci.springfx.mvci.util.constraint.ConstraintUtils.isNumberOrEmptyWhenPropertyIsNotEmpty;
import static cz.masci.springfx.mvci.util.constraint.ConstraintUtils.isNumberWhenPropertyIsNotEmpty;

import cz.masci.drd.ui.app.monster.model.MonsterDetailModel;
import cz.masci.springfx.mvci.model.list.ListModel;
import cz.masci.springfx.mvci.util.property.PropertyUtils;
import cz.masci.springfx.mvci.view.builder.DetailViewBuilder;
import io.github.palexdev.materialfx.builders.layout.HBoxBuilder;
import io.github.palexdev.materialfx.builders.layout.VBoxBuilder;
import javafx.beans.property.Property;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class MonsterDetailViewBuilder extends DetailViewBuilder<MonsterDetailModel> implements Builder<Region> {

  public MonsterDetailViewBuilder(ListModel<MonsterDetailModel> viewModel) {
    super(viewModel);
  }

  @Override
  public Region build() {
    Property<MonsterDetailModel> selectedProperty = viewModel.selectedElementProperty();

    var nameTextField = createTextField("Jméno nestvůry", Double.MAX_VALUE);
    var nameIsNotEmptyConstraint = isNotEmptyWhenPropertyIsNotEmpty(nameTextField.textProperty(), selectedProperty, "Jméno nestvůry");
    var nameTextFieldWithValidation = enhanceValidatedNodeWithSupportingText(nameTextField, PropertyUtils.not(nameTextField.delegateFocusedProperty())::addListener, nameIsNotEmptyConstraint);

    var viabilityTextField = createTextField("Životaschopnost", Double.MAX_VALUE);
    var viabilityIsNotEmptyConstraint = isNotEmptyWhenPropertyIsNotEmpty(viabilityTextField.textProperty(), selectedProperty, "Životaschopnost");
    var viabilityTextFieldWithValidation = enhanceValidatedNodeWithSupportingText(viabilityTextField, PropertyUtils.not(viabilityTextField.delegateFocusedProperty())::addListener, viabilityIsNotEmptyConstraint);

    var attackTextField = createTextField("Útočné číslo", Double.MAX_VALUE);
    var attackIsNotEmptyConstraint = isNotEmptyWhenPropertyIsNotEmpty(attackTextField.textProperty(), selectedProperty,  "Útočné číslo");
    var attackTextFieldWithValidation = enhanceValidatedNodeWithSupportingText(attackTextField, PropertyUtils.not(attackTextField.delegateFocusedProperty())::addListener, attackIsNotEmptyConstraint);

    var defenceTextField = createTextField("Obranné číslo", Double.MAX_VALUE);
    var defenceIsNotEmptyConstraint = isNotEmptyWhenPropertyIsNotEmpty(defenceTextField.textProperty(), selectedProperty, "Obranné číslo");
    var defenceIsNumberConstraint = isNumberWhenPropertyIsNotEmpty(defenceTextField.textProperty(), selectedProperty, "Obranné číslo");
    var defenceTextFieldWithValidation = enhanceValidatedNodeWithSupportingText(defenceTextField, PropertyUtils.not(defenceTextField.delegateFocusedProperty())::addListener, defenceIsNotEmptyConstraint, defenceIsNumberConstraint);

    var enduranceTextField = createTextField("Odolnost", Double.MAX_VALUE);
    var enduranceIsNotEmptyConstraint = isNumberWhenPropertyIsNotEmpty(enduranceTextField.textProperty(), selectedProperty, "Odolnost");
    var enduranceTextFieldWithValidation = enhanceValidatedNodeWithSupportingText(enduranceTextField, PropertyUtils.not(enduranceTextField.delegateFocusedProperty())::addListener, enduranceIsNotEmptyConstraint);

    var dimensionTextField = createTextField("Velikost", Double.MAX_VALUE);
    var dimensionIsNotEmptyConstraint = isNotEmptyWhenPropertyIsNotEmpty(dimensionTextField.textProperty(), selectedProperty, "Velikost");
    var dimensionTextFieldWithValidation = enhanceValidatedNodeWithSupportingText(dimensionTextField, PropertyUtils.not(dimensionTextField.delegateFocusedProperty())::addListener, dimensionIsNotEmptyConstraint);

    var combativenessTextField = createTextField("Bojovnost", Double.MAX_VALUE);
    var combativenessIsNumberConstraint = isNumberOrEmptyWhenPropertyIsNotEmpty(combativenessTextField.textProperty(), selectedProperty, "Bojovnost");
    var combativenessTextFieldWithValidation = enhanceValidatedNodeWithSupportingText(combativenessTextField, PropertyUtils.not(combativenessTextField.delegateFocusedProperty())::addListener, combativenessIsNumberConstraint);

    var vulnerabilityTextField = createTextField("Zranitelnost", Double.MAX_VALUE);
    var vulnerabilityIsNotEmptyConstraint = isNotEmptyWhenPropertyIsNotEmpty(vulnerabilityTextField.textProperty(), selectedProperty, "Zranitelnost");
    var vulnerabilityTextFieldWithValidation = enhanceValidatedNodeWithSupportingText(vulnerabilityTextField, PropertyUtils.not(vulnerabilityTextField.delegateFocusedProperty())::addListener, vulnerabilityIsNotEmptyConstraint);

    var moveabilityTextField = createTextField("Pohyblivost", Double.MAX_VALUE);
    var moveabilityIsNotEmptyConstraint = isNotEmptyWhenPropertyIsNotEmpty(moveabilityTextField.textProperty(), selectedProperty, "Pohyblivost");
    var moveabilityTextFieldWithValidation = enhanceValidatedNodeWithSupportingText(moveabilityTextField, PropertyUtils.not(moveabilityTextField.delegateFocusedProperty())::addListener, moveabilityIsNotEmptyConstraint);

    var staminaTextField = createTextField("Vytrvalost", Double.MAX_VALUE);

    var intelligenceTextField = createTextField("Inteligence", Double.MAX_VALUE);
    var intelligenceIsNotEmptyConstraint = isNotEmptyWhenPropertyIsNotEmpty(intelligenceTextField.textProperty(), selectedProperty, "Inteligence");
    var intelligenceIsNumberConstraint = isNumberWhenPropertyIsNotEmpty(intelligenceTextField.textProperty(), selectedProperty, "Inteligence");
    var intelligenceTextFieldWithValidation = enhanceValidatedNodeWithSupportingText(intelligenceTextField, PropertyUtils.not(intelligenceTextField.delegateFocusedProperty())::addListener, intelligenceIsNotEmptyConstraint, intelligenceIsNumberConstraint);

    var convictionTextField = createTextField("Přesvědčení", Double.MAX_VALUE);
    var convictionIsNotEmptyConstraint = isNumberOrEmptyWhenPropertyIsNotEmpty(convictionTextField.textProperty(), selectedProperty, "Přesvědčení");
    var convictionTextFieldWithValidation = enhanceValidatedNodeWithSupportingText(convictionTextField, PropertyUtils.not(convictionTextField.delegateFocusedProperty())::addListener, convictionIsNotEmptyConstraint);

    var treasureTextField = createTextField("Poklady", Double.MAX_VALUE);
    var treasureIsNotEmptyConstraint = isNotEmptyWhenPropertyIsNotEmpty(treasureTextField.textProperty(), selectedProperty, "Poklady");
    var treasureTextFieldWithValidation = enhanceValidatedNodeWithSupportingText(treasureTextField, PropertyUtils.not(treasureTextField.delegateFocusedProperty())::addListener, treasureIsNotEmptyConstraint);

    var experienceTextField = createTextField("Zkušenost", Double.MAX_VALUE);
    var experienceIsNotEmptyConstraint = isNotEmptyWhenPropertyIsNotEmpty(experienceTextField.textProperty(), selectedProperty, "Zkušenost");
    var experienceTextFieldWithValidation = enhanceValidatedNodeWithSupportingText(experienceTextField, PropertyUtils.not(experienceTextField.delegateFocusedProperty())::addListener, experienceIsNotEmptyConstraint);

    var descriptionTextField = createTextField("Popis", Double.MAX_VALUE);

    bindBidirectional(nameTextField.textProperty(), MonsterDetailModel::nameProperty);
    bindBidirectional(viabilityTextField.textProperty(), MonsterDetailModel::viabilityProperty);
    bindBidirectional(attackTextField.textProperty(), MonsterDetailModel::attackProperty);
    bindBidirectional(defenceTextField.textProperty(), MonsterDetailModel::defenceProperty);
    bindBidirectional(enduranceTextField.textProperty(), MonsterDetailModel::enduranceProperty);
    bindBidirectional(dimensionTextField.textProperty(), MonsterDetailModel::dimensionProperty);
    bindBidirectional(combativenessTextField.textProperty(), MonsterDetailModel::combativenessProperty);
    bindBidirectional(vulnerabilityTextField.textProperty(), MonsterDetailModel::vulnerabilityProperty);
    bindBidirectional(moveabilityTextField.textProperty(), MonsterDetailModel::moveabilityProperty);
    bindBidirectional(staminaTextField.textProperty(), MonsterDetailModel::staminaProperty);
    bindBidirectional(intelligenceTextField.textProperty(), MonsterDetailModel::intelligenceProperty);
    bindBidirectional(convictionTextField.textProperty(), MonsterDetailModel::convictionProperty);
    bindBidirectional(treasureTextField.textProperty(), MonsterDetailModel::treasureProperty);
    bindBidirectional(experienceTextField.textProperty(), MonsterDetailModel::experienceProperty);
    bindBidirectional(descriptionTextField.textProperty(), MonsterDetailModel::descriptionProperty);

    viewModel.setOnFocusView(nameTextField::requestFocus);

    var row1 = HBoxBuilder.hBox()
        .setSpacing(20.0)
        .addChildren(viabilityTextFieldWithValidation, attackTextFieldWithValidation)
        .setMaxWidth(Double.MAX_VALUE)
        .getNode();
    HBox.setHgrow(viabilityTextFieldWithValidation, Priority.ALWAYS);
    HBox.setHgrow(attackTextFieldWithValidation, Priority.ALWAYS);
    var row2 = HBoxBuilder.hBox()
        .setSpacing(20.0)
        .addChildren(defenceTextFieldWithValidation, enduranceTextFieldWithValidation)
        .setMaxWidth(Double.MAX_VALUE)
        .getNode();
    HBox.setHgrow(defenceTextFieldWithValidation, Priority.ALWAYS);
    HBox.setHgrow(enduranceTextFieldWithValidation, Priority.ALWAYS);
    var row3 = HBoxBuilder.hBox()
        .setSpacing(20.0)
        .addChildren(dimensionTextFieldWithValidation, combativenessTextFieldWithValidation)
        .setMaxWidth(Double.MAX_VALUE)
        .getNode();
    HBox.setHgrow(dimensionTextFieldWithValidation, Priority.ALWAYS);
    HBox.setHgrow(combativenessTextFieldWithValidation, Priority.ALWAYS);
    var row4 = HBoxBuilder.hBox()
        .setSpacing(20.0)
        .addChildren(vulnerabilityTextFieldWithValidation, moveabilityTextFieldWithValidation)
        .setMaxWidth(Double.MAX_VALUE)
        .getNode();
    HBox.setHgrow(vulnerabilityTextFieldWithValidation, Priority.ALWAYS);
    HBox.setHgrow(moveabilityTextFieldWithValidation, Priority.ALWAYS);
    var row5 = HBoxBuilder.hBox()
        .setSpacing(20.0)
        .addChildren(staminaTextField, intelligenceTextFieldWithValidation)
        .setMaxWidth(Double.MAX_VALUE)
        .getNode();
    HBox.setHgrow(staminaTextField, Priority.ALWAYS);
    HBox.setHgrow(intelligenceTextFieldWithValidation, Priority.ALWAYS);
    var row6 = HBoxBuilder.hBox()
        .setSpacing(20.0)
        .addChildren(convictionTextFieldWithValidation, treasureTextFieldWithValidation)
        .setMaxWidth(Double.MAX_VALUE)
        .getNode();
    HBox.setHgrow(convictionTextFieldWithValidation, Priority.ALWAYS);
    HBox.setHgrow(treasureTextFieldWithValidation, Priority.ALWAYS);

    return VBoxBuilder.vBox()
        .setSpacing(10.0)
        .addChildren(nameTextFieldWithValidation, row1, row2, row3, row4, row5, row6, experienceTextFieldWithValidation, descriptionTextField)
        .setPadding(new Insets(5.0, 5.0, 5.0, 10.0))
        .getNode();
  }
}
