package cz.masci.drd.ui.adventure.controller;

import cz.masci.drd.ui.adventure.interactor.WeaponInteractor;
import cz.masci.drd.ui.adventure.model.WeaponListModel;
import cz.masci.drd.ui.adventure.view.WeaponDetailWithCommandViewBuilder;
import cz.masci.drd.ui.common.model.StatusBarViewModel;
import cz.masci.springfx.mvci.controller.ViewProvider;
import javafx.scene.layout.Region;

public class WeaponDetailWithCommandController implements ViewProvider<Region> {

  private final WeaponDetailWithCommandViewBuilder builder;

  public WeaponDetailWithCommandController(WeaponListModel viewModel, StatusBarViewModel statusBarViewModel, WeaponInteractor interactor) {
    var detailController = new WeaponDetailController(viewModel);
    var detailCommandController = new WeaponDetailCommandController(viewModel, statusBarViewModel, interactor);

    builder = new WeaponDetailWithCommandViewBuilder(detailController.getView(), detailCommandController.getView());
  }

  @Override
  public Region getView() {
    return builder.build();
  }
}
