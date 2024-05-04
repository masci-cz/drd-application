/*
 * Copyright (c) 2023-2024
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

package cz.masci.drd.ui.app.battle.slide.presenter.impl;

import cz.masci.drd.service.BattleService;
import cz.masci.drd.ui.app.battle.dto.BattleSlidePropertiesDTO;
import cz.masci.drd.ui.app.battle.slide.controller.BattleSlideController;
import cz.masci.drd.ui.app.battle.slide.presenter.BattleSlide;
import javafx.scene.Node;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxWeaver;

/**
 * {@link BattleSlide} implementation displaying single step.
 * <p>
 * Only {@link BattleSlide#initProperties(BattleSlidePropertiesDTO)},
 * {@link BattleSlide#doBeforeSlide()},
 * {@link BattleSlide#init()},
 * {@link BattleSlide#reset()}
 * needs to be implemented in the child class.
 * </p>
 *
 * @param <T> Class of the controller.
 */
@Getter
public abstract class BattleSlideSingleController<T extends BattleSlideController> extends BaseBattleSlide<T> {

  protected T controller;

  public BattleSlideSingleController(FxWeaver fxWeaver, BattleService battleService, Class<T> controllerClass) {
    super(battleService);
    var fxControllerAndView = fxWeaver.load(controllerClass);
    controller = fxControllerAndView.getController();
  }

  @Override
  public Node getCurrentView() {
    return controller.getRoot();
  }

  @Override
  public Node previousView() {
    return getCurrentView();
  }

  @Override
  public Node nextView() {
    return getCurrentView();
  }

  @Override
  public Node previousInitView() {
    return getCurrentView();
  }

  @Override
  public Node nextInitView() {
    return getCurrentView();
  }

  @Override
  public boolean hasPrevious() {
    return false;
  }

  @Override
  public boolean hasNext() {
    return false;
  }

}
