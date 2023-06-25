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

package cz.masci.drd.ui.battle.slide.presenter;

import cz.masci.drd.ui.battle.dto.BattleSlidePropertiesDTO;
import javafx.scene.Node;

public interface BattleSlide<T> {

  void initProperties(BattleSlidePropertiesDTO properties);

  /**
   * This method is called when previous/next slide is requested.
   * Should set battle service attributes based on the slide inputs.
   */
  void doBeforeSlide();

  /**
   * This method is called whenever the slide properties should be reset.
   */
  void reset();

  /**
   * Initiate slide based on battle service.
   */
  void init();

  T getController();
  Node getCurrentView();
  Node previousView();
  Node nextView();

  /**
   * Move index to the previous slide. If there is no other slide it returns <code>false</code>
   * otherwise returns <code>true</code>.
   *
   * @return <code>true</code> if there is new slide. Otherwise returns <code>false</code>
   */
  boolean hasPrevious();

  /**
   * Move index to the next slide. If there is no other slide it returns <code>false</code>
   * otherwise returns <code>true</code>.
   *
   * @return <code>true</code> if there is new slide. Otherwise returns <code>false</code>
   */
  boolean hasNext();

}
