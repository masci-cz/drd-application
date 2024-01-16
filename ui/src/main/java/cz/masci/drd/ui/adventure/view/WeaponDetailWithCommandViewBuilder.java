package cz.masci.drd.ui.adventure.view;

import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WeaponDetailWithCommandViewBuilder implements Builder<Region> {

  private final Region detailView;
  private final Region commandView;

  @Override
  public Region build() {
    VBox.setVgrow(detailView, Priority.ALWAYS);
    return new VBox(detailView, commandView);
  }
}
