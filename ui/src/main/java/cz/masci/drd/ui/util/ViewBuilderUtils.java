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

import static cz.masci.springfx.mvci.util.BuilderUtils.INVALID_PSEUDO_CLASS;
import static cz.masci.springfx.mvci.util.BuilderUtils.createValidationSupportingText;

import cz.masci.drd.ui.common.model.AbstractListModel;
import cz.masci.springfx.mvci.model.detail.DetailModel;
import cz.masci.springfx.mvci.view.builder.ButtonBuilder;
import io.github.palexdev.materialfx.builders.control.TextFieldBuilder;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.enums.FloatMode;
import io.github.palexdev.materialfx.selection.base.IMultipleSelectionModel;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Validated;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener.Change;
import javafx.collections.ObservableMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import lombok.experimental.UtilityClass;
import org.reactfx.EventStreams;

// TODO Move to commons-springfx library
@UtilityClass
public class ViewBuilderUtils {

  public <T extends Node & Validated> Region enhanceValidatedNodeWithSupportingText(T validatedNode, Consumer<ChangeListener<? super Boolean>> revalidateFlagListener, Constraint... inputConstraints) {
    return enhanceValidatedNodeWithSupportingText(validatedNode, createValidationSupportingText(), revalidateFlagListener, inputConstraints);
  }

  public <T extends Node & Validated> Region enhanceValidatedNodeWithSupportingText(T validatedNode, Label supportingText, Consumer<ChangeListener<? super Boolean>> revalidateFlagListener, Constraint... inputConstraints) {
    Arrays.stream(inputConstraints).forEach(validatedNode.getValidator()::constraint);
    validatedNode.getValidator().validProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        supportingText.setVisible(false);
        supportingText.setManaged(false); // disable
        validatedNode.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, false);
      }
    });
    revalidateFlagListener.accept((observable, oldValue, newValue) -> {
      if (!oldValue && newValue) {
        List<Constraint> constraints = validatedNode.validate();
        if (!constraints.isEmpty()) {
          validatedNode.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, true);
          supportingText.setText(constraints.get(0).getMessage());
          supportingText.setVisible(true);
          supportingText.setManaged(true);
        }
      }
    });
    var result = new VBox(validatedNode, supportingText);
    result.setAlignment(Pos.TOP_LEFT);

    return result;
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

  public static <T, E extends DetailModel<T>> Region buildAddButton(AbstractListModel<T, E> viewModel) {
    var result = ButtonBuilder.builder().text("+").styleClass("filled").command(viewModel::createItem).build(MFXButton::new);
    StackPane.setAlignment(result, Pos.BOTTOM_RIGHT);
    StackPane.setMargin(result, new Insets(30.0, 30.0, 80.0, 30.0));

    return result;
  }

  public static <T> MFXTableColumn<T> createTableColumn(String title, Function<T, String> extractor) {
    var result = new MFXTableColumn<>(title, Comparator.comparing(extractor));
    result.setRowCellFactory(item -> new MFXTableRowCell<>(extractor));

    return result;
  }

  public static <T, E extends DetailModel<T>> void initSelectionModel(IMultipleSelectionModel<E> selectionModel, Runnable update, AbstractListModel<T, E> viewModel) {
    ObservableMap<Integer, E> selectionProperty = selectionModel.selectionProperty();
    EventStreams.changesOf(selectionProperty)
        .filter(Change::wasAdded)
        .map(Change::getValueAdded)
        .feedTo(viewModel.selectedElementProperty());
    viewModel.setOnUpdateElementsProperty(update);
    viewModel.setOnSelectElement(selectionModel::selectItem);
  }

  public static Builder<Region> createDetailWithCommandViewBuilder(Region detailView, Region commandView) {
    return () -> {
      VBox.setVgrow(detailView, Priority.ALWAYS);
      return new VBox(detailView, commandView);
    };
  }
}
