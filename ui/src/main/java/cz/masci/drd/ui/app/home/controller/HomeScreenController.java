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

package cz.masci.drd.ui.app.home.controller;

import cz.masci.drd.ui.app.adventure.controller.AdventureListDetailController;
import cz.masci.drd.ui.app.adventure.controller.WeaponListDetailController;
import cz.masci.drd.ui.app.battle.wizard.controller.BattleWizardController;
import cz.masci.drd.ui.app.home.view.HomeScreenViewBuilder;
import cz.masci.drd.ui.app.monster.controller.MonsterListDetailController;
import cz.masci.springfx.mvci.controller.ViewProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HomeScreenController implements ViewProvider<Region> {

  private final Map<StageType, Stage> stages = new HashMap<>();

  private final AdventureListDetailController adventureListDetailController;
  private final MonsterListDetailController monsterListDetailController;
  private final WeaponListDetailController weaponListDetailController;
  private final BattleWizardController battleWizardController;

  @Override
  public Region getView() {
    return new HomeScreenViewBuilder(
        createAction(StageType.ADVENTURES, getSceneForView(adventureListDetailController, 800, 600)),
        createAction(StageType.MONSTERS, getSceneForView(monsterListDetailController, 800, 800)),
        createAction(StageType.WEAPONS, getSceneForView(weaponListDetailController, 800, 600)),
        createAction(StageType.BATTLE, getSceneForView(battleWizardController, 800, 600))
    ).build();
  }

  public void doOnCloseRequest(WindowEvent ignoredEvent) {
    stages.values().forEach(Stage::close);
  }

  private Runnable createAction(StageType stageType, Supplier<Scene> sceneSupplier) {
    return () -> {
      var stage = stages.get(stageType);
      if (stage == null) {
        Scene scene = sceneSupplier.get();
        stage = new Stage();
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> stages.remove(stageType));
        stage.setTitle(stageType.getTitle());
        stage.show();
        stages.put(stageType, stage);
      } else {
        stage.requestFocus();
      }
    };
  }

  private Supplier<Scene> getSceneForView(ViewProvider<?> viewProvider, double width, double height) {
    return () -> new Scene(viewProvider.getView(), width, height);
  }

  @Getter
  @RequiredArgsConstructor
  private enum StageType {
    ADVENTURES("Dobrodružství"),
    MONSTERS("Příšery"),
    WEAPONS("Zbraně"),
    BATTLE("Bitva");

    private final String title;
  }
}
