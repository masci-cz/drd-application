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

import cz.masci.drd.ui.adventure.AdventureController;
import cz.masci.drd.ui.adventure.WeaponController;
import cz.masci.drd.ui.battle.BattleFactory;
import cz.masci.drd.ui.monster.MonsterController;
import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@FxmlView("fxml/home-screen.fxml")
@RequiredArgsConstructor
@Slf4j
public class HomeScreen  {

  private final ApplicationContext applicationContext;
  private final FxWeaver fxWeaver;
  private final Map<String, Stage> stages = new HashMap<>();

  @FXML
  public void btnAction(ActionEvent event) {
    if (event.getSource() instanceof Button source) {
      var stage = stages.get(source.getId());
      if (stage == null) {
        Scene scene = switch (source.getId()) {
          case "btnAdventures" -> getScene(AdventureController.class);
          case "btnMonsters" -> getScene(MonsterController.class);
          case "btnWeapons" -> getScene(WeaponController.class);
          case "btnBattle" -> getSceneForBattleController();
          default -> new Scene(new Label("Wrong source id"));
        };
        stage = new Stage();
        stage.setScene(scene);
        stages.put(source.getId(), stage);
        stage.show();
      } else {
        stage.requestFocus();
      }
    }
  }

  private Scene getSceneForBattleController() {
    var bean = applicationContext.getBean(BattleFactory.class);
    return bean.getScene();
  }

  private <T> Scene getScene(Class<T> controllerClass) {
    BorderPane view = fxWeaver.loadView(controllerClass);
    return new Scene(view, 600, 400);
  }
}
