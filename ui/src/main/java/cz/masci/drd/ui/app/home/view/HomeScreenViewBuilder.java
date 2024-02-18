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

package cz.masci.drd.ui.app.home.view;

import cz.masci.springfx.mvci.view.builder.ButtonBuilder;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HomeScreenViewBuilder implements Builder<Region> {

  private final Runnable onAdventuresAction;
  private final Runnable onMonstersAction;
  private final Runnable onWeaponsAction;
  private final Runnable onBattleAction;

  @Override
  public Region build() {
    var btnAdventures = createButton("Dobrodružství", onAdventuresAction);
    var btnMonsters = createButton("Příšery", onMonstersAction);
    var btnWeapons = createButton("Zbraně", onWeaponsAction);
    var btnBattle = createButton("Bitva", onBattleAction);

    var result = new TilePane();
    result.getChildren().addAll(btnAdventures, btnMonsters, btnWeapons, btnBattle);
    result.setPrefColumns(2);
    result.setHgap(20.0);
    result.setVgap(20.0);
    result.setPadding(new Insets(10.0));

    return result;
  }

  private Node createButton(String text, Runnable command) {
    var button = ButtonBuilder.builder()
        .command(command)
        .text(text)
        .styleClass("tile")
        .build(MFXButton::new);
    button.setMaxWidth(Double.MAX_VALUE);
    button.setMinHeight(100.0);
    return button;
  }
}
