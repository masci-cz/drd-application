package cz.masci.drd.ui.monster.controller;

import cz.masci.drd.ui.common.model.StatusBarViewModel;
import cz.masci.drd.ui.monster.interactor.MonsterInteractor;
import cz.masci.drd.ui.monster.model.MonsterDetailModel;
import cz.masci.drd.ui.monster.model.MonsterListModel;
import cz.masci.drd.ui.util.controller.AbstractDetailCommandController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MonsterDetailCommandController extends AbstractDetailCommandController<Long, MonsterDetailModel> {

  public MonsterDetailCommandController(MonsterListModel viewModel, StatusBarViewModel statusBarViewModel, MonsterInteractor interactor) {
    super(viewModel, statusBarViewModel, interactor);
  }

  @Override
  protected String getDeleteConfirmDialogTitle() {
    return "Smazat nestvůru";
  }

  @Override
  protected String getDeleteConfirmDialogContent() {
    return "Opravdu chcete smazat vybranou nestvůru?";
  }

  @Override
  protected String getItemDisplayInfo(MonsterDetailModel item) {
    return item.getName();
  }

}
