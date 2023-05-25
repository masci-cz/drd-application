package cz.masci.drd.ui.util.slide;

import cz.masci.drd.ui.util.transition.TranslateTransitionBuilder;
import java.util.function.Consumer;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxControllerAndView;

@Slf4j
public class SlideService<C, V extends Node> {
  @Getter
  private final ObservableList<FxControllerAndView<? extends C, V>> controllerAndViewList = FXCollections.observableArrayList();
  private final Pane centerPane;
  private final ReadOnlyDoubleProperty widthProperty;
  private Integer currentIndex;

  public SlideService(@NonNull ReadOnlyDoubleProperty widthProperty, @NonNull Pane centerPane) {
    this.widthProperty = widthProperty;
    this.centerPane = centerPane;
    this.currentIndex = null;
    controllerAndViewList.addListener(updateCurrentNode());
  }

  public void prev(Consumer<SlideService<C, V>> preProcess, Consumer<SlideService<C, V>> postProcess) {
    slide(SlideType.PREV, preProcess, postProcess);
//    if (preProcess != null) {
//      preProcess.accept(this);
//    }
//
//    // Check currentIndex
//    if (currentIndex == null || currentIndex == 0) {
//      log.warn("Can't slide previous because it is already first node");
//      return;
//    }
//    // Transform previous node to the left
//    Node previousNode = nodeList.get(currentIndex - 1);
//    previousNode.translateXProperty().set(-widthProperty.doubleValue());
//    previousNode.setVisible(true);
//    // Create translate transition for previous node from left to right
//    var previousNodeTransition = TranslateTransitionBuilder.builder()
//        .withByX(widthProperty.doubleValue())
//        .withDuration(Duration.millis(500))
//        .withNode(previousNode)
//        .withOnFinishedHandler(event -> {
//          if (postProcess != null) {
//            postProcess.accept(this);
//          }
//        })
//        .build();
//    // Create translate transition for current node from left to right
//    var currentNodeTransition = TranslateTransitionBuilder.builder()
//        .withByX(widthProperty.doubleValue())
//        .withDuration(Duration.millis(500))
//        .withNode(currentNode)
//        .withOnFinishedHandler(event -> {
//          currentNode.setVisible(false);
//          currentNode.translateXProperty().set(-widthProperty.doubleValue());
//          currentNode = previousNode;
//          currentIndex -= 1;
//        })
//        .build();
//    previousNodeTransition.play();
//    currentNodeTransition.play();
  }

  public void next(Consumer<SlideService<C, V>> preProcess, Consumer<SlideService<C, V>> postProcess) {
    slide(SlideType.NEXT, preProcess, postProcess);
//    // Check currentIndex
//    if (currentIndex == null || currentIndex == nodeList.size() - 1) {
//      log.warn("Can't slide previous because it is already last node");
//      return;
//    }
//    // Transform next node to the right
//    Node nextNode = nodeList.get(currentIndex + 1);
//    nextNode.translateXProperty().set(widthProperty.doubleValue());
//    nextNode.setVisible(true);
//    // Create translate transition for previous node from right to left
//    var nextNodeTransition = TranslateTransitionBuilder.builder()
//        .withByX(-widthProperty.doubleValue())
//        .withDuration(Duration.millis(500))
//        .withNode(nextNode)
//        .build();
//    // Create translate transition for current node from right to left
//    var currentNodeTransition = TranslateTransitionBuilder.builder()
//        .withByX(-widthProperty.doubleValue())
//        .withDuration(Duration.millis(500))
//        .withNode(currentNode)
//        .withOnFinishedHandler(event -> {
//          currentNode.setVisible(false);
//          currentNode.translateXProperty().set(widthProperty.doubleValue());
//          currentNode = nextNode;
//          currentIndex += 1;
//        })
//        .build();
//    nextNodeTransition.play();
//    currentNodeTransition.play();
  }

  public void set(int index) {
    // Based on current index transform from right to left when index > currentIndex and from left to right when index < currentIndex
  }

  public C getCurrentController() {
    return currentIndex != null ? controllerAndViewList.get(currentIndex).getController() : null;
  }

  private void slide(SlideType slideType, Consumer<SlideService<C, V>> preProcess, Consumer<SlideService<C, V>> postProcess) {
    if (preProcess != null) {
      preProcess.accept(this);
    }

    // Check currentIndex
    if (!isValidStep(slideType)) {
      log.warn("Can't slide because slide type {} is invalid", slideType);
      return;
    }

    var futureIndex = slideType.getFutureIndex(currentIndex);

    // Create translate transition for future node based on SlideType
    Node futureNode = getView(futureIndex);
    futureNode.translateXProperty().set(slideType.translateToStartPosition(widthProperty.doubleValue()));
    futureNode.setVisible(true);
    centerPane.getChildren().add(futureNode);

    var futureNodeTransition = TranslateTransitionBuilder.builder()
        .withByX(slideType.translateToEndPosition(widthProperty.doubleValue()))
        .withDuration(Duration.millis(500))
        .withNode(futureNode)
        .build();

    // Create translate transition for current node based on SlideType
    Node currentNode = getCurrentView();

    var currentNodeTransition = TranslateTransitionBuilder.builder()
        .withByX(slideType.translateToEndPosition(widthProperty.doubleValue()))
        .withDuration(Duration.millis(500))
        .withNode(currentNode)
        .withOnFinishedHandler(event -> {
          currentNode.setVisible(false);
          currentNode.translateXProperty().set(slideType.translateToEndPosition(widthProperty.doubleValue()));
          centerPane.getChildren().remove(currentNode);

          currentIndex = futureIndex;

          if (postProcess != null) {
            postProcess.accept(this);
          }
        })
        .build();

    // start both transitions
    futureNodeTransition.play();
    currentNodeTransition.play();
  }

  /**
   * Set or unset current node based on node list items change.
   *
   * @return ListChangeListener
   */
  private ListChangeListener<? super FxControllerAndView<? extends C, V>> updateCurrentNode() {
    return change -> {
      while (change.next()) {
        if (change.wasAdded()) {
          if (currentIndex == null && change.getList().size() > 0) {
            currentIndex = 0;
            var addedNode = getCurrentView();
            if (!centerPane.getChildren().contains(addedNode)) {
              centerPane.getChildren().add(addedNode);
            }
          }
        } else if (change.wasRemoved()) {
          if (currentIndex != null && controllerAndViewList.size() == 0) {
            currentIndex = null;
          }
        }
      }
    };
  }

  private boolean isValidStep(SlideType slideType) {
    return switch (slideType) {
      case PREV -> currentIndex != null && currentIndex > 0;
      case NEXT -> currentIndex != null && currentIndex < controllerAndViewList.size() - 1;
    };
  }

  private V getCurrentView() {
    return getView(currentIndex);
  }

  private V getView(Integer index) {
    if (index == null) {
      throw new RuntimeException("Can't get view for null index");
    }

    if (index < 0 || index >= controllerAndViewList.size()) {
      throw new IndexOutOfBoundsException("Can't get view for index: " + index);
    }

    return controllerAndViewList.get(index).getView().orElseThrow();
  }

  @RequiredArgsConstructor
  @Getter
  private enum SlideType {
    PREV(-1, 1.0),
    NEXT(1, -1.0);

    private final int indexStep;
    private final double positionMultiplier;

    public int getFutureIndex(int index) {
      return indexStep + index;
    }

    public double translateToEndPosition(double x) {
      return positionMultiplier * x;
    }

    public double translateToStartPosition(double x) {
      return -1.0 * positionMultiplier * x;
    }
  }
}
