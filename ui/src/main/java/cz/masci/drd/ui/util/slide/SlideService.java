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

package cz.masci.drd.ui.util.slide;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * This service is responsible for animation of two nodes on parent pane.
 */
public interface SlideService {

  /**
   * <p>
   *   Transforms the future node x position to the left outside the visible part, based on the parent pane width,
   *   and add it as a child to this parent pane.
   * </p>
   * <p>
   *   Translates both nodes to the right.<br>
   *   On finished slide makes the current node not visible and transform its x position back.</br>
   *   Removes the current node from parent pane children list.
   * </p>
   *
   * @param currentNode Current node visible to a user
   * @param futureNode Future node visible to a user after slide is done
   * @param parentPane Parent pane where the current node is as a child and the future node will be placed
   * @param onSlideFinished What should be processed after the slid is finished
   */
  void slideBackward(Node currentNode, Node futureNode, Pane parentPane, Runnable onSlideFinished);

  /**
   * <p>
   *   Transforms the future node x position to the right outside the visible part, based on the parent pane width,
   *   and add it as a child to this parent pane.
   * </p>
   * <p>
   *   Translates both nodes to the left.<br>
   *   On finished slide makes the current node not visible and transform its x position back.</br>
   *   Removes the current node from parent pane children list.
   * </p>
   *
   * @param currentNode Current node visible to a user
   * @param futureNode Future node visible to a user after slide is done
   * @param parentPane Parent pane where the current node is as a child and the future node will be placed
   * @param onSlideFinished What should be processed after the slid is finished
   */
  void slideForward(Node currentNode, Node futureNode, Pane parentPane, Runnable onSlideFinished);
}
