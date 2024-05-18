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

import static cz.masci.drd.ui.util.ViewBuilderUtils.createMFXComboBoxStringConverter;

import cz.masci.drd.ui.app.battle.wizard.model.SelectActionModel;
import cz.masci.springfx.mvci.view.builder.BorderPaneBuilder;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;
import org.reactfx.value.Val;

@RequiredArgsConstructor
public class BattleSelectActionViewBuilder implements Builder<Region> {

  private final ObjectProperty<SelectActionModel> selectedAction;
  private final List<SelectActionModel> actions;

  @Override
  public Region build() {
    MFXComboBox<SelectActionModel> actionTypeComboBox = new MFXComboBox<>();
    actionTypeComboBox.getItems()
                      .addAll(actions);
    actionTypeComboBox.setConverter(createMFXComboBoxStringConverter(SelectActionModel::name));
    actionTypeComboBox.setMaxWidth(Double.MAX_VALUE);

    BorderPane.setMargin(actionTypeComboBox, new Insets(5.0, 10.0, 5.0, 10.0));
    var borderPane = BorderPaneBuilder.builder()
                                      .withTop(actionTypeComboBox)
                                      .build();

    selectedAction.bind(actionTypeComboBox.getSelectionModel()
                                          .selectedItemProperty());
    borderPane.centerProperty()
              .bind(Val.wrap(actionTypeComboBox.getSelectionModel()
                                               .selectedItemProperty())
                       .map(SelectActionModel::view)
                       .orElseConst(null));

    return borderPane;
  }
}
