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
import cz.masci.drd.ui.app.battle.wizard.model.SelectActionCloseCombatModel;
import cz.masci.drd.ui.app.battle.wizard.model.SelectActionModel;
import cz.masci.drd.ui.app.battle.wizard.view.BattleSelectActionViewBuilder;
import cz.masci.drd.ui.app.battle.wizard.view.SelectActionViewBuilderFactory;
import cz.masci.drd.ui.util.wizard.controller.step.impl.TitleLeafStep;
import java.util.List;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Region;
import lombok.extern.slf4j.Slf4j;
import org.reactfx.EventStreams;

@Slf4j
public class BattleSelectActionChildStep extends TitleLeafStep {

  private final BattleSelectActionViewBuilder viewBuilder;
  private final ObjectProperty<SelectActionModel> selectedAction = new SimpleObjectProperty<>();
  private final BooleanProperty valid = new SimpleBooleanProperty(false);

  public BattleSelectActionChildStep(DuellistDTO duellist, List<String> actions, List<DuellistDTO> duellists) {
    super("Vyberte akci pro bojovníka - " + duellist.getName() + " ze skupiny " + duellist.getGroupName());

    EventStreams.changesOf(selectedAction)
                .map(change -> change.getNewValue() != null)
                .feedTo(valid);

    var viewModel = new SelectActionCloseCombatModel(duellist, duellists);
    viewBuilder = new BattleSelectActionViewBuilder(selectedAction, actions.stream()
                                                                           .map(name -> new SelectActionModel(name, viewModel,
                                                                               SelectActionViewBuilderFactory.createSelectActionViewBuilder(name, viewModel)
                                                                                                                                                             .build()))
                                                                           .toList());
  }

  @Override
  public void completeStep() {
    if (isValid()) {
      log.debug("Selected action: {}", selectedAction.get());
      setDuellistAction(selectedAction.get());
    }
    super.completeStep();
  }

  @Override
  public BooleanExpression valid() {
    return valid;
  }

  @Override
  public Region view() {
    return viewBuilder.build();
  }

  private void setDuellistAction(SelectActionModel model) {
    var actor = model.action().getAttacker();
    var receiver = model.action().getSelectedDefender();
    actor.setSelectedAction(
        switch (model.name()) {
          case "Útok na blízko" -> new CombatAction(actor, receiver);
          case "Kouzlení" -> new MagicAction(actor, receiver, "Kouzlo");
          case "Příprava" -> new PrepareAction(actor);
          case "Útok na dálku" -> new ShootAction(actor, receiver);
          case "Mluvení" -> new SpeechAction(actor);
          case "Jiná akce" -> new OtherAction(actor, "Jiná akce");
          case "Čekání" -> new WaitAction(actor);
          default -> null;
        }
      );
  }
}
