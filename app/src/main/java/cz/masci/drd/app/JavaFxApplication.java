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
package cz.masci.drd.app;

import cz.masci.drd.ui.MainController;
import cz.masci.drd.ui.battle.BattleController;
import cz.masci.drd.ui.battle.BattleNewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author Daniel
 */
public class JavaFxApplication extends Application {

  private ConfigurableApplicationContext applicationContext;

  @Override
  public void init() {
    System.out.println("JavaFxApplication - init");
    String[] args = getParameters().getRaw().toArray(new String[0]);

    this.applicationContext = new SpringApplicationBuilder()
            .sources(SpringApplication.class)
            .run(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    System.out.println("JavaFxApplication - start");
    FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
//    Parent root = fxWeaver.loadView(MainController.class);
    Parent root = fxWeaver.loadView(BattleNewController.class);
    Scene scene = new Scene(root);
    scene.getStylesheets().add("table-detail.css");
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void stop() {
    System.out.println("JavaFxApplication - stop");
    this.applicationContext.close();
    Platform.exit();
  }
}
