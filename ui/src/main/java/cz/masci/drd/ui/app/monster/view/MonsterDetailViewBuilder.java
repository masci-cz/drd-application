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

import static cz.masci.drd.ui.util.ReactFxUtils.selectVarOrElseConst;

import cz.masci.drd.ui.app.monster.model.MonsterDetailModel;
import cz.masci.drd.ui.app.monster.model.MonsterListModel;
import cz.masci.drd.ui.util.ConstraintUtils;
import cz.masci.drd.ui.util.PropertyUtils;
import cz.masci.drd.ui.util.ViewBuilderUtils;
import cz.masci.springfx.mvci.util.BuilderUtils;
import io.github.palexdev.materialfx.builders.layout.HBoxBuilder;
import io.github.palexdev.materialfx.builders.layout.VBoxBuilder;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;
import org.reactfx.value.Var;

@RequiredArgsConstructor
public class MonsterDetailViewBuilder implements Builder<Region> {

  private final MonsterListModel viewModel;

  @Override
  public Region build() {
    Var<MonsterDetailModel> selectedProperty = viewModel.selectedElementProperty();

    var nameTextField = ViewBuilderUtils.createTextField("Jméno nestvůry", Double.MAX_VALUE);
    var nameIsNotEmptyConstraint = ConstraintUtils.isNotEmptyWhenPropertyIsNotEmpty(nameTextField.textProperty(), selectedProperty, "Jméno nestvůry");
    var nameTextFieldWithValidation = BuilderUtils.enhanceValidatedNodeWithSupportingText(nameTextField, PropertyUtils.not(nameTextField.delegateFocusedProperty())::addListener, nameIsNotEmptyConstraint);

    var viabilityTextField = ViewBuilderUtils.createTextField("Životaschopnost", Double.MAX_VALUE);
    var viabilityIsNotEmptyConstraint = ConstraintUtils.isNotEmptyWhenPropertyIsNotEmpty(viabilityTextField.textProperty(), selectedProperty, "Životaschopnost");
    var viabilityTextFieldWithValidation = ViewBuilderUtils.enhanceValidatedNodeWithSupportingText(viabilityTextField, PropertyUtils.not(viabilityTextField.delegateFocusedProperty())::addListener, viabilityIsNotEmptyConstraint);

    var attackTextField = ViewBuilderUtils.createTextField("Útočné číslo", Double.MAX_VALUE);
    var attackIsNotEmptyConstraint = ConstraintUtils.isNotEmptyWhenPropertyIsNotEmpty(attackTextField.textProperty(), selectedProperty,  "Útočné číslo");
    var attackTextFieldWithValidation = ViewBuilderUtils.enhanceValidatedNodeWithSupportingText(attackTextField, PropertyUtils.not(attackTextField.delegateFocusedProperty())::addListener, attackIsNotEmptyConstraint);

    var defenceTextField = ViewBuilderUtils.createTextField("Obranné číslo", Double.MAX_VALUE);
    var defenceIsNotEmptyConstraint = ConstraintUtils.isNotEmptyWhenPropertyIsNotEmpty(defenceTextField.textProperty(), selectedProperty, "Obranné číslo");
    var defenceIsNumberConstraint = ConstraintUtils.isNumberWhenPropertyIsNotEmpty(defenceTextField.textProperty(), selectedProperty, "Obranné číslo");
    var defenceTextFieldWithValidation = ViewBuilderUtils.enhanceValidatedNodeWithSupportingText(defenceTextField, PropertyUtils.not(defenceTextField.delegateFocusedProperty())::addListener, defenceIsNotEmptyConstraint, defenceIsNumberConstraint);

    var enduranceTextField = ViewBuilderUtils.createTextField("Odolnost", Double.MAX_VALUE);
    var enduranceIsNotEmptyConstraint = ConstraintUtils.isNumberWhenPropertyIsNotEmpty(enduranceTextField.textProperty(), selectedProperty, "Odolnost");
    var enduranceTextFieldWithValidation = ViewBuilderUtils.enhanceValidatedNodeWithSupportingText(enduranceTextField, PropertyUtils.not(enduranceTextField.delegateFocusedProperty())::addListener, enduranceIsNotEmptyConstraint);

    var dimensionTextField = ViewBuilderUtils.createTextField("Velikost", Double.MAX_VALUE);
    var dimensionIsNotEmptyConstraint = ConstraintUtils.isNotEmptyWhenPropertyIsNotEmpty(dimensionTextField.textProperty(), selectedProperty, "Velikost");
    var dimensionTextFieldWithValidation = ViewBuilderUtils.enhanceValidatedNodeWithSupportingText(dimensionTextField, PropertyUtils.not(dimensionTextField.delegateFocusedProperty())::addListener, dimensionIsNotEmptyConstraint);

    var combativenessTextField = ViewBuilderUtils.createTextField("Bojovnost", Double.MAX_VALUE);
    var combativenessIsNumberConstraint = ConstraintUtils.isNumberOrEmptyWhenPropertyIsNotEmpty(combativenessTextField.textProperty(), selectedProperty, "Bojovnost");
    var combativenessTextFieldWithValidation = ViewBuilderUtils.enhanceValidatedNodeWithSupportingText(combativenessTextField, PropertyUtils.not(combativenessTextField.delegateFocusedProperty())::addListener, combativenessIsNumberConstraint);

    var vulnerabilityTextField = ViewBuilderUtils.createTextField("Zranitelnost", Double.MAX_VALUE);
    var vulnerabilityIsNotEmptyConstraint = ConstraintUtils.isNotEmptyWhenPropertyIsNotEmpty(vulnerabilityTextField.textProperty(), selectedProperty, "Zranitelnost");
    var vulnerabilityTextFieldWithValidation = ViewBuilderUtils.enhanceValidatedNodeWithSupportingText(vulnerabilityTextField, PropertyUtils.not(vulnerabilityTextField.delegateFocusedProperty())::addListener, vulnerabilityIsNotEmptyConstraint);

    var moveabilityTextField = ViewBuilderUtils.createTextField("Pohyblivost", Double.MAX_VALUE);
    var moveabilityIsNotEmptyConstraint = ConstraintUtils.isNotEmptyWhenPropertyIsNotEmpty(moveabilityTextField.textProperty(), selectedProperty, "Pohyblivost");
    var moveabilityTextFieldWithValidation = ViewBuilderUtils.enhanceValidatedNodeWithSupportingText(moveabilityTextField, PropertyUtils.not(moveabilityTextField.delegateFocusedProperty())::addListener, moveabilityIsNotEmptyConstraint);

    var staminaTextField = ViewBuilderUtils.createTextField("Vytrvalost", Double.MAX_VALUE);

    var intelligenceTextField = ViewBuilderUtils.createTextField("Inteligence", Double.MAX_VALUE);
    var intelligenceIsNotEmptyConstraint = ConstraintUtils.isNotEmptyWhenPropertyIsNotEmpty(intelligenceTextField.textProperty(), selectedProperty, "Inteligence");
    var intelligenceIsNumberConstraint = ConstraintUtils.isNumberWhenPropertyIsNotEmpty(intelligenceTextField.textProperty(), selectedProperty, "Inteligence");
    var intelligenceTextFieldWithValidation = ViewBuilderUtils.enhanceValidatedNodeWithSupportingText(intelligenceTextField, PropertyUtils.not(intelligenceTextField.delegateFocusedProperty())::addListener, intelligenceIsNotEmptyConstraint, intelligenceIsNumberConstraint);

    var convictionTextField = ViewBuilderUtils.createTextField("Přesvědčení", Double.MAX_VALUE);
    var convictionIsNotEmptyConstraint = ConstraintUtils.isNumberOrEmptyWhenPropertyIsNotEmpty(convictionTextField.textProperty(), selectedProperty, "Přesvědčení");
    var convictionTextFieldWithValidation = ViewBuilderUtils.enhanceValidatedNodeWithSupportingText(convictionTextField, PropertyUtils.not(convictionTextField.delegateFocusedProperty())::addListener, convictionIsNotEmptyConstraint);

    var treasureTextField = ViewBuilderUtils.createTextField("Poklady", Double.MAX_VALUE);
    var treasureIsNotEmptyConstraint = ConstraintUtils.isNotEmptyWhenPropertyIsNotEmpty(treasureTextField.textProperty(), selectedProperty, "Poklady");
    var treasureTextFieldWithValidation = ViewBuilderUtils.enhanceValidatedNodeWithSupportingText(treasureTextField, PropertyUtils.not(treasureTextField.delegateFocusedProperty())::addListener, treasureIsNotEmptyConstraint);

    var experienceTextField = ViewBuilderUtils.createTextField("Zkušenost", Double.MAX_VALUE);
    var experienceIsNotEmptyConstraint = ConstraintUtils.isNotEmptyWhenPropertyIsNotEmpty(experienceTextField.textProperty(), selectedProperty, "Zkušenost");
    var experienceTextFieldWithValidation = ViewBuilderUtils.enhanceValidatedNodeWithSupportingText(experienceTextField, PropertyUtils.not(experienceTextField.delegateFocusedProperty())::addListener, experienceIsNotEmptyConstraint);

    var descriptionTextField = ViewBuilderUtils.createTextField("Popis", Double.MAX_VALUE);

    // create nullable properties
    Var<String> nameProperty = selectVarOrElseConst(selectedProperty, MonsterDetailModel::nameProperty, "");
    Var<String> viabilityProperty = selectVarOrElseConst(selectedProperty, MonsterDetailModel::viabilityProperty, "");
    Var<String> attackProperty = selectVarOrElseConst(selectedProperty, MonsterDetailModel::attackProperty, "");
    Var<String> defenceProperty = selectVarOrElseConst(selectedProperty, MonsterDetailModel::defenceProperty, "");
    Var<String> enduranceProperty = selectVarOrElseConst(selectedProperty, MonsterDetailModel::enduranceProperty, "");
    Var<String> dimensionProperty = selectVarOrElseConst(selectedProperty, MonsterDetailModel::dimensionProperty, "");
    Var<String> combativenessProperty = selectVarOrElseConst(selectedProperty, MonsterDetailModel::combativenessProperty, "");
    Var<String> vulnerabilityProperty = selectVarOrElseConst(selectedProperty, MonsterDetailModel::vulnerabilityProperty, "");
    Var<String> moveabilityProperty = selectVarOrElseConst(selectedProperty, MonsterDetailModel::moveabilityProperty, "");
    Var<String> staminaProperty = selectVarOrElseConst(selectedProperty, MonsterDetailModel::staminaProperty, "");
    Var<String> intelligenceProperty = selectVarOrElseConst(selectedProperty, MonsterDetailModel::intelligenceProperty, "");
    Var<String> convictionProperty = selectVarOrElseConst(selectedProperty, MonsterDetailModel::convictionProperty, "");
    Var<String> treasureProperty = selectVarOrElseConst(selectedProperty, MonsterDetailModel::treasureProperty, "");
    Var<String> experienceProperty = selectVarOrElseConst(selectedProperty, MonsterDetailModel::experienceProperty, "");
    Var<String> descriptionProperty = selectVarOrElseConst(selectedProperty, MonsterDetailModel::descriptionProperty, "");

    // bind nullable properties to text fields
    nameTextField.textProperty().bindBidirectional(nameProperty);
    viabilityTextField.textProperty().bindBidirectional(viabilityProperty);
    attackTextField.textProperty().bindBidirectional(attackProperty);
    defenceTextField.textProperty().bindBidirectional(defenceProperty);
    enduranceTextField.textProperty().bindBidirectional(enduranceProperty);
    dimensionTextField.textProperty().bindBidirectional(dimensionProperty);
    combativenessTextField.textProperty().bindBidirectional(combativenessProperty);
    vulnerabilityTextField.textProperty().bindBidirectional(vulnerabilityProperty);
    moveabilityTextField.textProperty().bindBidirectional(moveabilityProperty);
    staminaTextField.textProperty().bindBidirectional(staminaProperty);
    intelligenceTextField.textProperty().bindBidirectional(intelligenceProperty);
    convictionTextField.textProperty().bindBidirectional(convictionProperty);
    treasureTextField.textProperty().bindBidirectional(treasureProperty);
    experienceTextField.textProperty().bindBidirectional(experienceProperty);
    descriptionTextField.textProperty().bindBidirectional(descriptionProperty);

    // listen to changes and update source
    ChangeListener<String> changeListener = (obs, oldValue, newValue) -> viewModel.updateElementsProperty();
    nameProperty.observeChanges(changeListener);
    viabilityProperty.observeChanges(changeListener);
    attackProperty.observeChanges(changeListener);
    defenceProperty.observeChanges(changeListener);
    enduranceProperty.observeChanges(changeListener);
    dimensionProperty.observeChanges(changeListener);
    combativenessProperty.observeChanges(changeListener);
    vulnerabilityProperty.observeChanges(changeListener);
    moveabilityProperty.observeChanges(changeListener);
    staminaProperty.observeChanges(changeListener);
    intelligenceProperty.observeChanges(changeListener);
    convictionProperty.observeChanges(changeListener);
    treasureProperty.observeChanges(changeListener);
    experienceProperty.observeChanges(changeListener);
    descriptionProperty.observeChanges(changeListener);

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
