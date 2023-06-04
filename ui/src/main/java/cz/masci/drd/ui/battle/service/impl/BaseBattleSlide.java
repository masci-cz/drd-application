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

package cz.masci.drd.ui.battle.service.impl;

import cz.masci.drd.ui.battle.service.BattleSlide;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxWeaver;

public abstract class BaseBattleSlide<T> implements BattleSlide<T> {

  @Getter
  protected final T controller;
  @Getter
  protected final Node view;

  public BaseBattleSlide(FxWeaver fxWeaver, Class<T> controllerClass) {
    var fxControllerAndView = fxWeaver.load(controllerClass);
    controller = fxControllerAndView.getController();
    view = fxControllerAndView.getView().orElseThrow();
    initAnchors(view);
  }

  private void initAnchors(Node node) {
    if (node != null) {
      AnchorPane.setTopAnchor(node, 10.0);
      AnchorPane.setRightAnchor(node, 10.0);
      AnchorPane.setBottomAnchor(node, 10.0);
      AnchorPane.setLeftAnchor(node, 10.0);
    }
  }


}
