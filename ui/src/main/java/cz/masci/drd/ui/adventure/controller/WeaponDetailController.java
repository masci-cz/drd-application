package cz.masci.drd.ui.adventure.controller;

import cz.masci.drd.ui.adventure.interactor.WeaponInteractor;
import cz.masci.drd.ui.adventure.model.WeaponListModel;
import cz.masci.drd.ui.adventure.view.WeaponDetailViewBuilder;
import cz.masci.drd.ui.common.model.StatusBarViewModel;
import cz.masci.drd.ui.util.ViewBuilderUtils;
import cz.masci.springfx.mvci.controller.ViewProvider;
import cz.masci.springfx.mvci.controller.impl.SimpleController;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class WeaponDetailController implements ViewProvider<Region> {

  private final Builder<Region> builder;

  public WeaponDetailController(WeaponListModel viewModel, StatusBarViewModel statusBarViewModel, WeaponInteractor interactor) {
    var detailViewBuilder = new WeaponDetailViewBuilder(viewModel);
    var detailController = new SimpleController<>(detailViewBuilder);
    var detailCommandController = new WeaponDetailCommandController(viewModel, statusBarViewModel, interactor);

    builder = ViewBuilderUtils.createDetailWithCommandViewBuilder(detailController.getView(), detailCommandController.getView());
  }

  @Override
  public Region getView() {
    return builder.build();
  }
}
