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

package cz.masci.drd.ui.util;

import cz.masci.drd.ui.common.model.AbstractListModel;
import cz.masci.springfx.mvci.model.detail.DetailModel;
import cz.masci.springfx.mvci.view.builder.ButtonBuilder;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.function.Function;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ViewBuilderUtils {

  public static <T, E extends DetailModel<T>> Region buildAddButton(AbstractListModel<T, E> viewModel) {
    return buildAddButton(viewModel, new Insets(30.0, 30.0, 80.0, 30.0));
  }

  public static <T, E extends DetailModel<T>> Region buildAddButton(AbstractListModel<T, E> viewModel, Insets insets) {
    var result = ButtonBuilder.builder().text("+").styleClass("filled").command(viewModel::createItem).build(MFXButton::new);
    StackPane.setAlignment(result, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(result, insets);

    return result;
  }

  public static <T> StringConverter<T> createMFXComboBoxStringConverter(Function<T, String> converter) {
    return createMFXComboBoxStringConverter(converter, "");
  }

  public static <T> StringConverter<T> createMFXComboBoxStringConverter(Function<T, String> converter, String defaultValue) {
    return new StringConverter<>() {
      @Override
      public String toString(T object) {
        return object != null ? converter.apply(object) : defaultValue;
      }

      @Override
      public T fromString(String string) {
        return null;
      }
    };
  }

}
