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

package cz.masci.drd.ui.app.battle.wizard.view;

import static cz.masci.springfx.mvci.util.BuilderUtils.enhanceValidatedNodeWithSupportingText;
import static cz.masci.springfx.mvci.util.MFXBuilderUtils.createTextField;
import static cz.masci.springfx.mvci.util.constraint.ConstraintUtils.isNotEmptyWhenPropertyIsNotEmpty;
import static cz.masci.springfx.mvci.util.constraint.ConstraintUtils.isNumberOrEmptyWhenPropertyIsNotEmpty;
import static cz.masci.springfx.mvci.util.constraint.ConstraintUtils.isNumberWhenPropertyIsNotEmpty;

import cz.masci.drd.ui.app.battle.wizard.model.BattleDuellistDetailModel;
import cz.masci.springfx.mvci.model.list.ListModel;
import cz.masci.springfx.mvci.util.property.PropertyUtils;
import cz.masci.springfx.mvci.view.builder.DetailViewBuilder;
import io.github.palexdev.materialfx.builders.layout.VBoxBuilder;
import javafx.geometry.Insets;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import org.reactfx.value.Var;

public class BattleDuellistDetailViewBuilder extends DetailViewBuilder<BattleDuellistDetailModel> implements Builder<Region> {


  public BattleDuellistDetailViewBuilder(ListModel<BattleDuellistDetailModel> viewModel) {
    super(viewModel);
  }

  @Override
  public Region build() {
    Var<BattleDuellistDetailModel> selectedProperty = viewModel.selectedElementProperty();

    var nameTextField = createTextField("Název", Double.MAX_VALUE);
    var nameIsNotEmptyConstraint = isNotEmptyWhenPropertyIsNotEmpty(nameTextField.textProperty(), selectedProperty, "Název");
    var nameTextFieldWithValidation = enhanceValidatedNodeWithSupportingText(nameTextField, PropertyUtils.not(nameTextField.delegateFocusedProperty())::addListener, nameIsNotEmptyConstraint);

    var offenseTextField = createTextField("", 200.0);
    var offenseIsNumberOrNotEmptyConstraint = isNumberOrEmptyWhenPropertyIsNotEmpty(offenseTextField.textProperty(), selectedProperty, "Útočné číslo");
    var offenseTextFieldWithValidation = enhanceValidatedNodeWithSupportingText(offenseTextField, PropertyUtils.not(offenseTextField.delegateFocusedProperty())::addListener, offenseIsNumberOrNotEmptyConstraint);
    var defenseTextField = createTextField("", 200.0);
    var defenseIsNumberOrNotEmptyConstraint = isNumberOrEmptyWhenPropertyIsNotEmpty(defenseTextField.textProperty(), selectedProperty, "Obranné číslo");
    var defense
    var damageTextField = createTextField("", 200.0);
    var damageIsNumberOrNotEmptyConstraint = isNumberOrEmptyWhenPropertyIsNotEmpty(damageTextField.textProperty(), selectedProperty, "Útočnost");
    var liveTextField = createTextField("", 200.0);
    var liveIsNumberOrNotEmptyConstraint = isNumberOrEmptyWhenPropertyIsNotEmpty(liveTextField.textProperty(), selectedProperty, "Životy");


    bindBidirectional(nameTextField.textProperty(), BattleDuellistDetailModel::nameProperty);
    bindBidirectional(offenseTextField.textProperty(), BattleDuellistDetailModel::attackProperty);
    bindBidirectional(defenseTextField.textProperty(), BattleDuellistDetailModel::defenseProperty);
    bindBidirectional(damageTextField.textProperty(), BattleDuellistDetailModel::damageProperty);
    bindBidirectional(liveTextField.textProperty(), BattleDuellistDetailModel::liveProperty);

    viewModel.setOnFocusView(nameTextField::requestFocus);

    return VBoxBuilder.vBox()
                      .setSpacing(5.0)
                      .addChildren(nameTextFieldWithValidation)
                      .setPadding(new Insets(5.0, 5.0, 10.0, 5.0))
                      .setPrefWidth(400)
                      .getNode();
  }
}
