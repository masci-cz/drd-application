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

package cz.masci.drd.ui.battle.slide;

import javafx.beans.value.ObservableBooleanValue;

public interface SlideController {
  /**
   * Returns slide title
   *
   * @return Slide title
   */
  String getTitle();

  /**
   * Previous button title
   *
   * @return Previous button title
   */
  String getPrevTitle();

  /**
   * Next button title
   *
   * @return Next button title
   */
  String getNextTitle();

  /**
   * Returns prev valid property. It is <code>true</code> when the slide is valid, and it is possible move to the prev slide.
   *
   * @return Valid property
   */
  ObservableBooleanValue validPrevProperty();

  /**
   * Returns next valid property. It is <code>true</code> when the slide is valid, and it is possible move to the next slide.
   *
   * @return Valid property
   */
  ObservableBooleanValue validNextProperty();
}
