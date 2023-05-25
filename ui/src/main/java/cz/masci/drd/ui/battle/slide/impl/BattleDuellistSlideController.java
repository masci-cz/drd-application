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

package cz.masci.drd.ui.battle.slide.impl;

import cz.masci.commons.springfx.fxml.annotation.FxmlController;
import cz.masci.drd.service.BattleService;
import cz.masci.drd.ui.battle.slide.BattleSlideController;
import cz.masci.drd.ui.util.slide.SlideService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@FxmlView("fxml/battle-duellist-slide.fxml")
@FxmlController
@Slf4j
public class BattleDuellistSlideController implements BattleSlideController {

  @Override
  public void onPrev(BattleService battleService, SlideService<BattleSlideController, Node> slideService) {

  }

  @Override
  public void onNext(BattleService battleService, SlideService<BattleSlideController, Node> slideService) {

  }

  @Override
  public String getTitle() {
    return "Bojovníci";
  }

  @Override
  public String getPrevTitle() {
    return "Zpět";
  }

  @Override
  public String getNextTitle() {
    return "Další";
  }

  @Override
  public BooleanProperty validPrevProperty() {
    return new SimpleBooleanProperty(true);
  }

  @Override
  public BooleanProperty validNextProperty() {
    return new SimpleBooleanProperty(true);
  }
}
