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
import cz.masci.drd.ui.app.battle.slide.controller.BattleSlideController;
import cz.masci.drd.ui.app.battle.dto.BattleSlidePropertiesDTO;
import cz.masci.drd.ui.app.battle.slide.presenter.BattleSlide;
import cz.masci.drd.ui.util.iterator.ObservableListIterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javafx.scene.Node;
import net.rgielen.fxweaver.core.FxWeaver;

/**
 * {@link BattleSlide} implementation displaying multiple steps.
 * <p>
 * Only {@link BattleSlide#initProperties(BattleSlidePropertiesDTO)} and {@link BattleSlide#doBeforeSlide()}
 * with appropriate {@link BattleSlideMultipleControllers#getControllers()} method needs to be implemented in the child class.
 * </p>
 *
 * @param <T> Class of the controller.
 */
public abstract class BattleSlideMultipleControllers<T extends BattleSlideController> extends BaseBattleSlide<T> {

  protected final FxWeaver fxWeaver;
  protected ObservableListIterator<T> iterator;

  public BattleSlideMultipleControllers(FxWeaver fxWeaver, BattleService battleService) {
    super(battleService);
    this.fxWeaver = fxWeaver;
  }

  protected abstract List<T> getControllers();

  /**
   * This method could be overridden to run some initialization before the controllers list is got.
   */
  protected void preInit() {
    // usually do nothing
  }

  @Override
  public void reset() {
    iterator = null;
  }

  @Override
  public void init() {
    preInit();
    iterator = new ObservableListIterator<>(getControllers());
  }

  @Override
  public T getController() {
    return iterator.getCurrent();
  }

  @Override
  public Node getCurrentView() {
    return getRootOrNull(ObservableListIterator::getCurrent);
  }

  @Override
  public Node previousView() {
    return getRootOrNull(ObservableListIterator::previous);
  }

  @Override
  public Node nextView() {
    return getRootOrNull(ObservableListIterator::next);
  }

  @Override
  public Node previousInitView() {
    return previousView();
  }

  @Override
  public Node nextInitView() {
    return nextView();
  }

  @Override
  public boolean hasPrevious() {
    return iterator.hasPrevious();
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  private Node getRootOrNull(Function<ObservableListIterator<T>, T> controller) {
    return Optional.ofNullable(controller.apply(iterator)).map(T::getRoot).orElse(null);
  }
}
