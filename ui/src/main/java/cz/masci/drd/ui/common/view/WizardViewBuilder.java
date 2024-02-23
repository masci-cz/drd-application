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

import cz.masci.springfx.mvci.view.builder.ButtonBuilder;
import io.github.palexdev.materialfx.builders.layout.BorderPaneBuilder;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.Optional;
import java.util.function.Supplier;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Builder;

public class WizardViewBuilder implements Builder<Region> {

  private final Supplier<Optional<Region>> onPrevAction;
  private final Supplier<Optional<Region>> onNextAction;
  private Region currentMainContent;

  public WizardViewBuilder(Supplier<Optional<Region>> onPrevAction, Supplier<Optional<Region>> onNextAction, Region currentMainContent) {
    this.onPrevAction = onPrevAction;
    this.onNextAction = onNextAction;
    this.currentMainContent = currentMainContent;
  }

  @Override
  public Region build() {
    var title = new Text("TITLE");
    title.setStyle("-fx-fill: WHITE");
    var top = new HBox();
    top.setAlignment(Pos.CENTER);
    top.setStyle("-fx-background-color: BLUE");
    top.getChildren().add(title);
    HBox.setHgrow(top, Priority.ALWAYS);

    var navigationBox = new VBox();
    navigationBox.setStyle("-fx-background-color: GREEN; -fx-text-fill: WHITE");
    navigationBox.getChildren().add(new Text("NAVIGATION"));

    var main = new AnchorPane();
    main.setStyle("-fx-background-color: YELLOW");
    setMainView(main);

    var prevBtn = ButtonBuilder.builder().text("PREVIOUS").styleClass("outlined").command(() -> onAction(main, onPrevAction)).build(MFXButton::new);
    AnchorPane.setLeftAnchor(prevBtn, 10.0);
    AnchorPane.setBottomAnchor(prevBtn, 5.0);
    AnchorPane.setTopAnchor(prevBtn, 5.0);
    var nextBtn = ButtonBuilder.builder().text("NEXT").styleClass("filled").command(() -> onAction(main, onNextAction)).build(MFXButton::new);
    AnchorPane.setRightAnchor(nextBtn, 10.0);
    AnchorPane.setBottomAnchor(nextBtn, 5.0);
    AnchorPane.setTopAnchor(nextBtn, 5.0);
    var bottom = new AnchorPane();
    bottom.setStyle("-fx-background-color: LIME");
    bottom.getChildren().addAll(prevBtn, nextBtn);

    return new BorderPaneBuilder().setTop(top)
                                  .setLeft(navigationBox)
                                  .setCenter(main)
                                  .setBottom(bottom)
                                  .getNode();
  }

  private void onAction(AnchorPane mainView, Supplier<Optional<Region>> regionSupplier) {
    regionSupplier.get().ifPresent(newView -> {
      currentMainContent = newView;
      setMainView(mainView);
    });
  }

  private void setMainView(AnchorPane mainView) {
    AnchorPane.setTopAnchor(currentMainContent, 0.0);
    AnchorPane.setRightAnchor(currentMainContent, 0.0);
    AnchorPane.setBottomAnchor(currentMainContent, 0.0);
    AnchorPane.setLeftAnchor(currentMainContent, 0.0);
    mainView.getChildren().clear();
    mainView.getChildren().add(currentMainContent);
  }
}
