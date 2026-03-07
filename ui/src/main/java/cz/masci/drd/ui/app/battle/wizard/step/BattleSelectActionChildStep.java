/*
 * Copyright (C) 2026 Daniel Masek
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cz.masci.drd.ui.app.battle.wizard.step;

import cz.masci.drd.dto.DuellistDTO;
import cz.masci.drd.dto.actions.*;
import cz.masci.drd.ui.app.battle.wizard.model.BattleSelectActionModel;
import cz.masci.drd.ui.app.battle.wizard.model.SelectActionModel;
import cz.masci.drd.ui.app.battle.wizard.model.SelectedActionModel;
import cz.masci.drd.ui.app.battle.wizard.view.BattleSelectActionViewBuilder;
import cz.masci.drd.ui.app.battle.wizard.view.SelectActionViewBuilderFactory;
import cz.masci.drd.ui.util.wizard.model.WizardLeafStep;
import cz.masci.wizard.simple.SimpleLeafStep;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.SimpleObjectProperty;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class BattleSelectActionChildStep extends WizardLeafStep {

  private final BattleSelectActionModel viewModel;

  public BattleSelectActionChildStep(DuellistDTO actor, List<String> actions, List<DuellistDTO> duellists) {
    super("Vyberte akci pro bojovníka - " + actor.getName() + " ze skupiny " + actor.getGroupName());

    viewModel = new BattleSelectActionModel(actions.stream()
                                                   .map(name -> createSelectActionModel(name, actor, duellists))
                                                   .toList());

    super.setBuilder(new BattleSelectActionViewBuilder(viewModel));
  }

  @Override
  protected void complete(SimpleLeafStep<WizardLeafStep> wizardLeafStepV3SimpleLeafStep) {
    if (viewModel.isValid()) {
      setDuellistAction(viewModel.getSelectedAction());
    }
  }

  @Override
  public BooleanExpression valid() {
    return viewModel.validProperty();
  }

  private SelectActionModel createSelectActionModel(String name, DuellistDTO actor, List<DuellistDTO> duellists) {
    var actionViewModel = new SelectedActionModel(actor, duellists);
    return new SelectActionModel(name, new SimpleObjectProperty<>(actionViewModel),
        SelectActionViewBuilderFactory.createSelectActionViewBuilder(name, actionViewModel)
                                      .build());
  }

  private void setDuellistAction(SelectActionModel model) {
    var action = model.action().getValue();
    var actor = action.getActor();
    var consumer = action.getConsumer();
    var spell = action.getSpell();
    var comment = action.getComment();

    actor.setSelectedAction(switch (model.name()) {
      case "Útok na blízko" -> new CombatAction(actor, consumer);
      case "Kouzlení" -> new MagicAction(actor, consumer, spell);
      case "Příprava" -> new PrepareAction(actor);
      case "Útok na dálku" -> new ShootAction(actor, consumer);
      case "Mluvení" -> new SpeechAction(actor);
      case "Jiná akce" -> new OtherAction(actor, comment);
      case "Čekání" -> new WaitAction(actor);
      default -> null;
    });
  }
}
