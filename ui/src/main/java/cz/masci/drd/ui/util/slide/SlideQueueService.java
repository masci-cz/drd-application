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
public class SlideQueueService<C, V extends Node> {

  private final SlideService slideService;

  @Getter
  private final ObservableList<FxControllerAndView<? extends C, V>> slides = FXCollections.observableArrayList();
  private final Pane centerPane;
  private final ReadOnlyDoubleProperty widthProperty;
  private Integer currentIndex;

  public SlideQueueService(SlideService slideService, @NonNull ReadOnlyDoubleProperty widthProperty, @NonNull Pane centerPane) {
    this.slideService = slideService;
    this.widthProperty = widthProperty;
    this.centerPane = centerPane;
    this.currentIndex = null;
    slides.addListener(updateCurrentSlide());
  }

  public void slideBackward(Runnable doBeforeSlide, Consumer<V> updateFutureView, Runnable doAfterSlide) {
    slide(currentIndex != null ? currentIndex - 1 : 0, doBeforeSlide, updateFutureView, doAfterSlide);
  }

  public void slideForward(Runnable doBeforeSlide, Consumer<V> updateFutureView, Runnable doAfterSlide) {
    slide(currentIndex != null ? currentIndex + 1 : 0, doBeforeSlide, updateFutureView, doAfterSlide);
  }

  public void slideToIndex(int index, Runnable doBeforeSlide, Consumer<V> updateFutureView, Runnable doAfterSlide) {
    slide(index, doBeforeSlide, updateFutureView, doAfterSlide);
  }

  public C getCurrentController() {
    return currentIndex != null ? slides.get(currentIndex).getController() : null;
  }

  public V getCurrentView() {
    return getView(currentIndex);
  }

  /**
   * <p>Slide to the future index view.</p>
   * <p>
   *   It uses three methods to process during the transformation and at the end.<br>
   *   First run the doBeforeSlide method.<br>
   *   Second run the updateFutureView to update future view.<br>
   *   Third run the doAfterSlide method.
   * </p>
   *
   * @param futureIndex New view index to move to
   * @param doBeforeSlide Method to run before sliding
   * @param updateFutureView Method to run for future view
   * @param doAfterSlide Method to run after sliding
   */
  private void slide(int futureIndex, Runnable doBeforeSlide, Consumer<V> updateFutureView, Runnable doAfterSlide) {
    if (doBeforeSlide != null) {
      doBeforeSlide.run();
    }

    // Check currentIndex
    if (!isValidFutureIndex(futureIndex)) {
      throw new IndexOutOfBoundsException(String.format("Can't slide because future index %d is out bounds or same as current index %d", futureIndex, currentIndex));
    }

    // Create translate transition for future node based on SlideType
    V futureNode = getView(futureIndex);
    V currentNode = getCurrentView();

    if (updateFutureView != null) {
      updateFutureView.accept(futureNode);
    }

    if (isSlideForward(futureIndex)) {
      slideService.slideForward(currentNode, futureNode, centerPane, doAfterSlide);
    } else {
      slideService.slideBackward(currentNode, futureNode, centerPane, doAfterSlide);
    }

    currentIndex = futureIndex;
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

  private boolean isSlideForward(int futureIndex) {
    if (currentIndex == null) {
      return true;
    }
    return currentIndex.compareTo(futureIndex) < 0;
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
