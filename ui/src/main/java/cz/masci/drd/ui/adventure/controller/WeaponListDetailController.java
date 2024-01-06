/*
 * Copyright (c) 2024
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

package cz.masci.drd.ui.adventure.controller;

import cz.masci.drd.ui.adventure.model.WeaponDetailModel;
import cz.masci.drd.ui.adventure.model.WeaponListModel;
import cz.masci.springfx.mvci.controller.ViewProvider;
import cz.masci.springfx.mvci.view.builder.ListDetailViewBuilder;
import javafx.scene.layout.Region;

public class WeaponListDetailController implements ViewProvider<Region> {

  private final ListDetailViewBuilder viewBuilder;
  private final WeaponListModel viewModel;

  public WeaponListDetailController() {
    viewModel = new WeaponListModel();
    var listController = new WeaponListController(viewModel);
    var detailController = new WeaponDetailController(viewModel.selectedItemProperty());

    viewBuilder = new ListDetailViewBuilder(listController.getView(), detailController.getView(), null);
  }

  @Override
  public Region getView() {
    initFakeData();
    return viewBuilder.build();
  }

  private void initFakeData() {
    viewModel.getItems().add(createModel(0, "Tesak", "5", "2"));
    viewModel.getItems().add(createModel(1, "Mec", "8", "0"));
    viewModel.getItems().add(createModel(2, "Nuz", "2", "0"));
    viewModel.getItems().add(createModel(3, "Savle", "4", "-1"));
    viewModel.getItems().add(createModel(4, "Dyka", "3", "0"));
  }

  private WeaponDetailModel createModel(Integer id, String name, String strength, String damage) {
    var result = new WeaponDetailModel();
    result.setId(id);
    result.setName(name);
    result.setStrength(strength);
    result.setDamage(damage);
    result.rebaseline();

    return result;

  }
}
