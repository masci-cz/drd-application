package cz.masci.drd.ui.util.slide;

import cz.masci.drd.ui.util.transition.TranslateTransitionBuilder;
import java.util.Objects;
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
  private final ObservableList<FxControllerAndView<? extends C, V>> slides = FXCollections.observableArrayList();
  private final Pane centerPane;
  private final ReadOnlyDoubleProperty widthProperty;
  private Integer currentIndex;

  public SlideService(@NonNull ReadOnlyDoubleProperty widthProperty, @NonNull Pane centerPane) {
    this.widthProperty = widthProperty;
    this.centerPane = centerPane;
    this.currentIndex = null;
    slides.addListener(updateCurrentSlide());
  }

  public void slideBackward(Consumer<SlideService<C, V>> doBefore, Consumer<SlideService<C, V>> doAfter) {
    slide(currentIndex != null ? currentIndex - 1 : 0, doBefore, doAfter);
  }

  public void slideForward(Consumer<SlideService<C, V>> doBefore, Consumer<SlideService<C, V>> doAfter) {
    slide(currentIndex != null ? currentIndex + 1 : 0, doBefore, doAfter);
  }

  public void slideToIndex(int index, Consumer<SlideService<C, V>> doBefore, Consumer<SlideService<C, V>> doAfter) {
    slide(index, doBefore, doAfter);
  }

  public C getCurrentController() {
    return currentIndex != null ? slides.get(currentIndex).getController() : null;
  }

  private void slide(int futureIndex, Consumer<SlideService<C, V>> preProcess, Consumer<SlideService<C, V>> postProcess) {
    if (preProcess != null) {
      preProcess.accept(this);
    }

    // Check currentIndex
    if (!isValidFutureIndex(futureIndex)) {
      throw new IndexOutOfBoundsException(String.format("Can't slide because future index %d is out bounds or same as current index %d", futureIndex, currentIndex));
    }

    var slideStep = mapFutureIndexToSlideFactor(futureIndex);

    // Create translate transition for future node based on SlideType
    Node futureNode = getView(futureIndex);
    futureNode.translateXProperty().set(slideStep.translateToStartPosition(widthProperty.doubleValue()));
    futureNode.setVisible(true);
    centerPane.getChildren().add(futureNode);

    var futureNodeTransition = TranslateTransitionBuilder.builder()
        .withByX(slideStep.translateToEndPosition(widthProperty.doubleValue()))
        .withDuration(Duration.millis(500))
        .withNode(futureNode)
        .build();

    // Create translate transition for current node based on SlideType
    Node currentNode = getCurrentView();

    var currentNodeTransition = TranslateTransitionBuilder.builder()
        .withByX(slideStep.translateToEndPosition(widthProperty.doubleValue()))
        .withDuration(Duration.millis(500))
        .withNode(currentNode)
        .withOnFinishedHandler(event -> {
          currentNode.setVisible(false);
          currentNode.translateXProperty().set(slideStep.translateToEndPosition(widthProperty.doubleValue()));
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
  private ListChangeListener<? super FxControllerAndView<? extends C, V>> updateCurrentSlide() {
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
          if (currentIndex != null && slides.size() == 0) {
            currentIndex = null;
          }
        }
      }
    };
  }

  private boolean isValidFutureIndex(int futureIndex) {
    return futureIndex >= 0
        && futureIndex < slides.size()
        && futureIndex != Objects.requireNonNullElse(currentIndex, -1);
  }

  private SlideFactor mapFutureIndexToSlideFactor(int futureIndex) {
    if (currentIndex == null) {
      return SlideFactor.NEXT;
    }
    return currentIndex.compareTo(futureIndex) > 0 ? SlideFactor.PREV : SlideFactor.NEXT;
  }

  private V getCurrentView() {
    return getView(currentIndex);
  }

  private V getView(Integer index) {
    if (index == null) {
      throw new RuntimeException("Can't get view for null index");
    }

    if (index < 0 || index >= slides.size()) {
      throw new IndexOutOfBoundsException("Can't get view for index: " + index);
    }

    return slides.get(index).getView().orElseThrow();
  }

  @RequiredArgsConstructor
  @Getter
  private enum SlideFactor {
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
