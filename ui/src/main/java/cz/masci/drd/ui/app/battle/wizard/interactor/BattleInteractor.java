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

package cz.masci.drd.ui.app.battle.wizard.interactor;

import cz.masci.drd.dto.DuellistDTO;
import cz.masci.drd.dto.GroupDTO;
import cz.masci.drd.dto.actions.Action;
import cz.masci.drd.service.BattleService;
import cz.masci.drd.service.exception.BattleException;
import cz.masci.drd.ui.app.battle.wizard.model.BattleDuellistDetailModel;
import cz.masci.drd.ui.app.battle.wizard.model.BattleGroupDetailModel;
import cz.masci.drd.ui.app.battle.wizard.model.BattlePreparationSummaryGroupModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// TODO make up exception handling propagate to user
@RequiredArgsConstructor
@Service
public class BattleInteractor {

  private final BattleService battleService;
  private final BattleMapper battleMapper;
  @Getter
  private final List<String> roundHistory = new ArrayList<>();

  public void createBattle(final List<BattleGroupDetailModel> list) {
    try {
      battleService.createBattle();
      battleService.addGroupList(list.stream()
                                     .map(BattleGroupDetailModel::getName)
                                     .toList());
    } catch (BattleException e) {
      throw new RuntimeException(e);
    }
  }

  public Stream<String> getGroupsNames() {
    return battleService.getGroups()
                        .stream()
                        .map(GroupDTO::getName);
  }

  public Stream<GroupDTO> getGroups() {
    return battleService.getGroups()
                        .stream();
  }

  public void setDuellists(String groupName, ObservableList<BattleDuellistDetailModel> elements) {
    var group = battleService.getGroup(groupName);
    if (group != null) {
      group.getDuellists()
           .clear();
      group.getDuellists()
           .addAll(elements.stream()
                           .map(duellist -> battleMapper.mapDuellistFromModel(groupName, duellist))
                           .toList());
    }
  }

  public List<BattlePreparationSummaryGroupModel> getPreparationSummary() {
    return battleService.getGroups()
                        .stream()
                        .map(battleMapper::mapGroupToModel)
                        .toList();
  }

  public List<String> getActionTypes() {
    return List.of("Útok na blízko", "Kouzlení", "Příprava", "Útok na dálku", "Mluvení", "Jiná akce", "Čekání");
  }

  public List<DuellistDTO> getAllDuellists() {
    return battleService.getAllDuellists();
  }

  public void startRound() {
    try {
      battleService.startRound();
      roundHistory.clear();
    } catch (BattleException e) {
      throw new RuntimeException(e);
    }
  }

  public void endRound() {
    try {
      battleService.endRound();
    } catch (BattleException e) {
      throw new RuntimeException(e);
    }
  }

  public void cancelRound() {
    try {
      battleService.cancelRound();
    } catch (BattleException e) {
      throw new RuntimeException(e);
    }
  }

  public void addRoundHistory(String roundHistory) {
    this.roundHistory.add(roundHistory);
  }

  public boolean hasAction() {
    return !battleService.getActions().isEmpty();
  }

  public Action<?> pollAction() {
    return battleService.getActions().poll();
  }

  public void prepareRound() {
    try {
      switch (battleService.getState()) {
        case NEW -> battleService.startBattle();
        case ROUND -> battleService.endRound();
      }
    } catch (BattleException e) {
      throw new RuntimeException(e);
    }
  }
}
