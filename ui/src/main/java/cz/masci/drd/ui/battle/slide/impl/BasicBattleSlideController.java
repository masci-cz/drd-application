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

import static cz.masci.drd.ui.util.PropertyUtility.FALSE_PROPERTY;
import static cz.masci.drd.ui.util.PropertyUtility.TRUE_PROPERTY;

import cz.masci.drd.service.BattleService;
import cz.masci.drd.ui.battle.slide.BattleSlideController;
import cz.masci.drd.ui.util.slide.SlideQueueService;
import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.Node;

public class BasicBattleSlideController implements BattleSlideController {
  @Override
  public void onBeforePrev(BattleService battleService, SlideQueueService<BattleSlideController, Node> slideService) {

  }

  @Override
  public void onAfterPrev(BattleService battleService, SlideQueueService<BattleSlideController, Node> slideService) {

  }

  @Override
  public void onBeforeNext(BattleService battleService, SlideQueueService<BattleSlideController, Node> slideService) {

  }

  @Override
  public void onAfterNext(BattleService battleService, SlideQueueService<BattleSlideController, Node> slideService) {

  }

  @Override
  public String getTitle() {
    return "NO TITLE";
  }

  @Override
  public String getPrevTitle() {
    return "Předchozí";
  }

  @Override
  public String getNextTitle() {
    return "Další";
  }

  @Override
  public ObservableBooleanValue validPrevProperty() {
    return TRUE_PROPERTY;
  }

  @Override
  public ObservableBooleanValue validNextProperty() {
    return FALSE_PROPERTY;
  }
}
