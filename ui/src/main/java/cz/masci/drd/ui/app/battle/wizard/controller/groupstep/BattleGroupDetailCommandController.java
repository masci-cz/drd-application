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

package cz.masci.drd.ui.app.battle.wizard.controller.groupstep;

import cz.masci.drd.ui.app.battle.wizard.model.BattleGroupDetailModel;
import cz.masci.drd.ui.app.battle.wizard.model.BattleGroupListModel;
import cz.masci.springfx.mvci.controller.ViewProvider;
import cz.masci.springfx.mvci.controller.impl.OperableDetailController;
import cz.masci.springfx.mvci.view.builder.ButtonBuilder;
import cz.masci.springfx.mvci.view.builder.CommandsViewBuilder;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.List;
import javafx.scene.layout.Region;

public class BattleGroupDetailCommandController implements ViewProvider<Region> {

  private final OperableDetailController<String, BattleGroupDetailModel> operableDetailController;
  private final CommandsViewBuilder viewBuilder;


  public BattleGroupDetailCommandController(BattleGroupListModel viewModel) {
    this.operableDetailController = new OperableDetailController<>(viewModel.selectedElementProperty(), viewModel);
    this.viewBuilder = new CommandsViewBuilder(
        List.of(
            ButtonBuilder.builder().text("Uložit").command(this::saveElement).styleClass("filledTonal").disableExpression(operableDetailController.saveDisabledProperty()).build(MFXButton::new),
            ButtonBuilder.builder().text("Zrušit").command(this::discardDirtyElement).styleClass("outlined").disableExpression(operableDetailController.discardDisabledProperty()).build(MFXButton::new),
            ButtonBuilder.builder().text("Smazat").command(this::deleteElement).disableExpression(operableDetailController.deleteDisabledProperty()).styleClass("outlined").build(MFXButton::new)
        )
    );
  }

  @Override
  public Region getView() {
    return viewBuilder.build();
  }

  private void discardDirtyElement() {
    operableDetailController.discard();
  }

  private void saveElement(Runnable postGuiStuff) {
    operableDetailController.update((item, afterSave) -> {
          item.setId(item.getName());
          afterSave.accept(item);
        }
    );
    // this must be called - otherwise the button disable status will not change
    postGuiStuff.run();
  }

  private void deleteElement(Runnable postGuiStuff) {
    operableDetailController.remove((item, afterDelete) -> afterDelete.run());
    // this must be called - otherwise the button disable status will not change
    postGuiStuff.run();
  }

}
