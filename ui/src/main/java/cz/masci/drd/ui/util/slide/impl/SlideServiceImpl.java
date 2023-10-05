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

package cz.masci.drd.ui.util.slide.impl;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

import cz.masci.drd.ui.util.slide.SlideService;
import cz.masci.drd.ui.util.transition.TranslateTransitionBuilder;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class SlideServiceImpl implements SlideService {
  @Override
  public void slideBackward(Node currentNode, Node futureNode, Pane parentPane, Runnable onSlideFinished) {
    slide(SlideFactor.PREV, currentNode, futureNode, parentPane, onSlideFinished);
  }

  @Override
  public void slideForward(Node currentNode, Node futureNode, Pane parentPane, Runnable onSlideFinished) {
    slide(SlideFactor.NEXT, currentNode, futureNode, parentPane, onSlideFinished);
  }

  @Override
  public void slide(SlideFactor slideFactor, Node currentNode, Node futureNode, Pane parentPane, Runnable onSlideFinished) {
    assertNotNull(currentNode);
    assertNotNull(futureNode);
    
    double parentPaneWidth = parentPane.getWidth();

    // Create translate transition for future node based on SlideType
    futureNode.translateXProperty().set(slideFactor.translateToStartPosition(parentPaneWidth));
    futureNode.setVisible(true);
    parentPane.getChildren().add(futureNode);

    var futureViewTransition = TranslateTransitionBuilder.builder()
        .withByX(slideFactor.translateToEndPosition(parentPaneWidth))
        .withDuration(Duration.millis(500))
        .withNode(futureNode)
        .build();

    // Create translate transition for current node based on SlideType
    var currentViewTransition = TranslateTransitionBuilder.builder()
        .withByX(slideFactor.translateToEndPosition(parentPaneWidth))
        .withDuration(Duration.millis(500))
        .withNode(currentNode)
        .withOnFinishedHandler(event -> {
          currentNode.setVisible(false);
          currentNode.translateXProperty().set(slideFactor.translateToEndPosition(parentPaneWidth));
          parentPane.getChildren().remove(currentNode);

          if (onSlideFinished != null) {
            onSlideFinished.run();
          }
        })
        .build();

    // start both transitions
    futureViewTransition.play();
    currentViewTransition.play();

  }

  @RequiredArgsConstructor
  @Getter
  public enum SlideFactor {
    PREV(1.0),
    NEXT(-1.0);

    private final double factor;

    public double translateToEndPosition(double x) {
      return factor * x;
    }

    public double translateToStartPosition(double x) {
      return -1.0 * factor * x;
    }
  }
}
