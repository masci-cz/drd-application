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

package cz.masci.drd.ui.common.view;

import cz.masci.drd.ui.util.ViewBuilderUtils;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MFXComboBoxBuilder<T> {
  private String promptText;
  private String floatingText;
  private Function<T, String> converter;
  private ObservableList<T> items;
  private ObjectProperty<T> selectedItemProperty;
  private Double maxWidth;
  private final List<Consumer<MFXComboBox<T>>> enhancements = new ArrayList<>();

  private MFXComboBoxBuilder() {
  }

  public static <T> MFXComboBoxBuilder<T> builder() {
    return new MFXComboBoxBuilder<>();
  }

  public MFXComboBoxBuilder<T> promptText(String promptText) {
    this.promptText = promptText;
    return this;
  }

  public MFXComboBoxBuilder<T> floatingText(String floatingText) {
    this.floatingText = floatingText;
    return this;
  }

  public MFXComboBoxBuilder<T> converter(Function<T, String> converter) {
    this.converter = converter;
    return this;
  }

  public MFXComboBoxBuilder<T> items(ObservableList<T> items) {
    this.items = items;
    return this;
  }

  public MFXComboBoxBuilder<T> items(List<T> items) {
    this.items = FXCollections.observableArrayList(items);
    return this;
  }

  public MFXComboBoxBuilder<T> selectedItemProperty(ObjectProperty<T> selectedItemProperty) {
    this.selectedItemProperty = selectedItemProperty;
    return this;
  }

  public MFXComboBoxBuilder<T> maxWidth(Double maxWidth) {
    this.maxWidth = maxWidth;
    return this;
  }

  public MFXComboBoxBuilder<T> withEnhancement(Consumer<MFXComboBox<T>> enhancement) {
    enhancements.add(enhancement);
    return this;
  }

  public MFXComboBox<T> build() {
    MFXComboBox<T> result = new MFXComboBox<>();
    result.setPromptText(promptText != null ? promptText : "Select");
    result.setFloatingText(floatingText != null ? floatingText : "Select");
    if (converter != null) {
      result.setConverter(ViewBuilderUtils.createMFXComboBoxStringConverter(converter));
    }
    if (items != null) {
      result.setItems(items);
    }
    if (selectedItemProperty != null && items != null) {
      result.getSelectionModel()
            .bindItemBidirectional(selectedItemProperty, items::indexOf, (clearing, newValue, property) -> {
              if (clearing) {
                property.setValue(null);
              } else {
                property.setValue(newValue);
              }
            });
    }
    if (maxWidth != null) {
      result.setMaxWidth(maxWidth);
    }
    enhancements.forEach(enhancement -> enhancement.accept(result));

    return result;
  }
}
