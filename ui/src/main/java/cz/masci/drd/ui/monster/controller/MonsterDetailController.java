package cz.masci.drd.ui.monster.controller;

import cz.masci.drd.ui.common.model.StatusBarViewModel;
import cz.masci.drd.ui.monster.interactor.MonsterInteractor;
import cz.masci.drd.ui.monster.model.MonsterListModel;
import cz.masci.drd.ui.monster.view.MonsterDetailViewBuilder;
import cz.masci.drd.ui.util.ViewBuilderUtils;
import cz.masci.springfx.mvci.controller.ViewProvider;
import cz.masci.springfx.mvci.controller.impl.SimpleController;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class MonsterDetailController implements ViewProvider<Region> {

  private final Builder<Region> builder;

  public MonsterDetailController(MonsterListModel viewModel, StatusBarViewModel statusBarViewModel, MonsterInteractor interactor) {
    var detailViewBuilder = new MonsterDetailViewBuilder(viewModel);
    var detailController = new SimpleController<>(detailViewBuilder);
    var detailCommandController = new MonsterDetailCommandController(viewModel, statusBarViewModel, interactor);

    builder = ViewBuilderUtils.createDetailWithCommandViewBuilder(detailController.getView(), detailCommandController.getView());
  }

  @Override
  public Region getView() {
    return builder.build();
  }
}
