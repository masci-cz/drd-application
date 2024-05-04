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

package cz.masci.drd.ui.app.monster.view;

import static cz.masci.drd.ui.util.ViewBuilderUtils.buildAddButton;

import cz.masci.drd.ui.app.monster.model.MonsterDetailModel;
import cz.masci.drd.ui.app.monster.model.MonsterListModel;
import cz.masci.springfx.mvci.util.builder.MFXTableViewBuilder;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MonsterListViewBuilder implements Builder<Region> {

  private final MonsterListModel viewModel;

  @Override
  public Region build() {
    return new StackPane(buildTable(), buildAddButton(viewModel));
  }

  private MFXTableView<MonsterDetailModel> buildTable() {
    return MFXTableViewBuilder.builder(viewModel)
        .column("Jméno nestvůry", MonsterDetailModel::getName, 300.0)
        .column("Životaschopnost", MonsterDetailModel::getViability)
        .build();
  }

}
