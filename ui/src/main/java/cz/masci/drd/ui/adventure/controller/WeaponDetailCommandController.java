package cz.masci.drd.ui.adventure.controller;

import cz.masci.drd.ui.adventure.interactor.WeaponInteractor;
import cz.masci.drd.ui.adventure.model.WeaponDetailModel;
import cz.masci.drd.ui.adventure.model.WeaponListModel;
import cz.masci.drd.ui.common.model.StatusBarViewModel;
import cz.masci.drd.ui.util.controller.AbstractDetailCommandController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeaponDetailCommandController extends AbstractDetailCommandController<Long, WeaponDetailModel> {

  public WeaponDetailCommandController(WeaponListModel viewModel, StatusBarViewModel statusBarViewModel, WeaponInteractor interactor) {
    super(viewModel, statusBarViewModel, interactor);
  }

  @Override
  protected String getDeleteConfirmDialogTitle() {
    return "Smazat zbraň";
  }

  @Override
  protected String getDeleteConfirmDialogContent() {
    return "Opravdu chcete smazat vybranou zbraň?";
  }

  @Override
  protected String getItemDisplayInfo(WeaponDetailModel item) {
    return item.getName();
  }

}
