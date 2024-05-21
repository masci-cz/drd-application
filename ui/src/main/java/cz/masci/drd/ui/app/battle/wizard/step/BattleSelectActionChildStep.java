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

package cz.masci.drd.ui.app.battle.wizard.step;

import cz.masci.drd.dto.DuellistDTO;
import cz.masci.drd.dto.actions.CombatAction;
import cz.masci.drd.dto.actions.MagicAction;
import cz.masci.drd.dto.actions.OtherAction;
import cz.masci.drd.dto.actions.PrepareAction;
import cz.masci.drd.dto.actions.ShootAction;
import cz.masci.drd.dto.actions.SpeechAction;
import cz.masci.drd.dto.actions.WaitAction;
import cz.masci.drd.ui.app.battle.wizard.model.BattleSelectActionModel;
import cz.masci.drd.ui.app.battle.wizard.model.SelectActionCloseCombatModel;
import cz.masci.drd.ui.app.battle.wizard.model.SelectActionModel;
import cz.masci.drd.ui.app.battle.wizard.view.BattleSelectActionViewBuilder;
import cz.masci.drd.ui.app.battle.wizard.view.SelectActionViewBuilderFactory;
import cz.masci.drd.ui.util.wizard.controller.step.impl.TitleLeafStep;
import java.util.List;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Region;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BattleSelectActionChildStep extends TitleLeafStep {

  private final BattleSelectActionViewBuilder viewBuilder;
  private final BattleSelectActionModel viewModel;

  public BattleSelectActionChildStep(DuellistDTO duellist, List<String> actions, List<DuellistDTO> duellists) {
    super("Vyberte akci pro bojovníka - " + duellist.getName() + " ze skupiny " + duellist.getGroupName());

    var actionViewModel = new SelectActionCloseCombatModel(duellist, duellists);
    viewModel = new BattleSelectActionModel(actions.stream()
                                                   .map(name -> new SelectActionModel(name, new SimpleObjectProperty<>(actionViewModel),
                                                       SelectActionViewBuilderFactory.createSelectActionViewBuilder(name, actionViewModel)
                                                                                     .build()))
                                                   .toList());
    viewBuilder = new BattleSelectActionViewBuilder(viewModel);
  }

  @Override
  public void completeStep() {
    if (isValid()) {
      log.debug("Selected action: {}", viewModel.getSelectedAction());
      setDuellistAction(viewModel.getSelectedAction());
    }
    super.completeStep();
  }

  @Override
  public BooleanExpression valid() {
    return viewModel.validProperty();
  }

  @Override
  public Region view() {
    return viewBuilder.build();
  }

  private void setDuellistAction(SelectActionModel model) {
    var action = model.action().getValue();
    var actor = action.getAttacker();
    var receiver = action.getSelectedDefender();
    actor.setSelectedAction(switch (model.name()) {
      case "Útok na blízko" -> new CombatAction(actor, receiver);
      case "Kouzlení" -> new MagicAction(actor, receiver, "Kouzlo");
      case "Příprava" -> new PrepareAction(actor);
      case "Útok na dálku" -> new ShootAction(actor, receiver);
      case "Mluvení" -> new SpeechAction(actor);
      case "Jiná akce" -> new OtherAction(actor, "Jiná akce");
      case "Čekání" -> new WaitAction(actor);
      default -> null;
    });
  }
}
