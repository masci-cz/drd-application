/*
 * Copyright (c) 2023
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

package cz.masci.drd.ui;

import cz.masci.commons.springfx.fxml.annotation.FxmlController;
import cz.masci.drd.ui.adventure.AdventureController;
import cz.masci.drd.ui.adventure.controller.WeaponListDetailController;
import cz.masci.drd.ui.battle.BattleFactory;
import cz.masci.drd.ui.monster.MonsterController;
import cz.masci.springfx.mvci.controller.ViewProvider;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Supplier;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@FxmlView("fxml/home-screen.fxml")
@FxmlController
@RequiredArgsConstructor
@Slf4j
public class HomeScreen implements Initializable {

  private final ApplicationContext applicationContext;
  private final FxWeaver fxWeaver;

  private final Map<StageType, Stage> stages = new HashMap<>();

  @FXML
  private Button btnAdventures;
  @FXML
  private Button btnMonsters;
  @FXML
  private Button btnWeapons;
  @FXML
  private Button btnBattle;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    btnAdventures.setOnAction(btnAction(StageType.ADVENTURES, getScene(AdventureController.class)));
    btnMonsters.setOnAction(btnAction(StageType.MONSTERS, getScene(MonsterController.class)));
    btnWeapons.setOnAction(btnAction(StageType.WEAPONS, getSceneForView(WeaponListDetailController.class, 800, 600)));
    btnBattle.setOnAction(btnAction(StageType.BATTLE, getSceneForBattleController()));
  }

  public void doOnCloseRequest(WindowEvent ignoredEvent) {
    stages.values().forEach(Stage::close);
  }

  private EventHandler<ActionEvent> btnAction(StageType stageType, Supplier<Scene> sceneSupplier) {
    return event -> {
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

  private Supplier<Scene> getSceneForBattleController() {
    var bean = applicationContext.getBean(BattleFactory.class);
    return bean::getScene;
  }

  private <T> Supplier<Scene> getScene(Class<T> controllerClass) {
    return () -> {
      BorderPane view = fxWeaver.loadView(controllerClass);
      return new Scene(view, 800, 1000);
    };
  }

  private Supplier<Scene> getSceneForView(Class<? extends ViewProvider<?>> sceneClass, double width, double height) {
    return () -> {
      var controller = applicationContext.getBean(sceneClass);
      return new Scene(controller.getView(), width, height);
    };
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
