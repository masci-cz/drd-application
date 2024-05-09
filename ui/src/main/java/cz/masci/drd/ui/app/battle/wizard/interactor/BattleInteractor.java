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

import cz.masci.drd.dto.GroupDTO;
import cz.masci.drd.service.BattleService;
import cz.masci.drd.service.exception.BattleException;
import cz.masci.drd.ui.app.battle.wizard.model.BattleDuellistDetailModel;
import cz.masci.drd.ui.app.battle.wizard.model.BattleGroupDetailModel;
import java.util.List;
import java.util.stream.Stream;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// TODO make up exception handling propagate to user
@RequiredArgsConstructor
@Service
public class BattleInteractor {

  private final BattleService battleService;
  private final BattleMapper battleMapper;

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

  public Stream<GroupDTO> getGroups() {
    return battleService.getGroups()
                        .stream();
  }

  public void setDuellists(String groupName, ObservableList<BattleDuellistDetailModel> elements) {
    var group = battleService.getGroup(groupName);
    if (group != null) {
      group.getDuellists()
           .addAll(elements.stream()
                           .map(battleMapper::mapDuellistFromModel)
                           .toList());
    }
  }
}
