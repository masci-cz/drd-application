/*
 * Copyright (C) 2021 Daniel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cz.masci.drd.ui;

import cz.masci.drd.ui.adventure.AdventureController;
import cz.masci.drd.ui.monster.MonsterController;
import cz.masci.springfx.annotation.FxmlController;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/**
 *
 * @author Daniel
 */
@Component
@Slf4j
@FxmlView("fxml/main-scene.fxml")
@FxmlController
@RequiredArgsConstructor
public class MainController {

  private final FxWeaver fxWeaver;

  @FXML
  private Tab tabMonsters;

  @FXML
  private Tab tabAdventures;
  
  @FXML
  public void initialize() {
    log.info("initialize");

    FxControllerAndView<MonsterController, BorderPane> monsterView = fxWeaver.load(MonsterController.class);

    tabMonsters.setContent(monsterView.getView().get());
    
    FxControllerAndView<AdventureController, BorderPane> adventureView = fxWeaver.load(AdventureController.class);
    
    tabAdventures.setContent(adventureView.getView().get());
  }
}
