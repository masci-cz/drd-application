/*
 * Copyright (c) 2023
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

package cz.masci.drd.service.impl;

import cz.masci.drd.dto.BattleState;
import cz.masci.drd.dto.DuellistDTO;
import cz.masci.drd.dto.GroupDTO;
import cz.masci.drd.dto.actions.Action;
import cz.masci.drd.service.BattleService;
import cz.masci.drd.service.exception.BattleException;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class BattleServiceImpl implements BattleService {

  private final Map<String, GroupDTO> groups = new HashMap<>();
  private final Queue<Action> actionList = new ArrayDeque<>();
  private BattleState state;

  @Override
  public void createBattle() {
    clearBattle();
  }

  @Override
  public void startBattle() throws BattleException {
    checkState(BattleState.NEW, "start battle");

    if (groups.size() < 2 || anyHasLessThanOneItem(groups.values(), GroupDTO::getDuellists)) {
      throw new BattleException("At least two battle groups with at least one duellist has to be set");
    }

    state = BattleState.PREPARATION;
  }

  @Override
  public void exitBattle() {
    clearBattle();
  }

  @Override
  public void startRound() throws BattleException {
    checkState(BattleState.PREPARATION, "start battle");
    // check all duellist has set some action
    if (!duellistsAreReady()) {
      throw new BattleException("All duellists action has to be set");
    }
    // check initiatives
    if (groups.values().stream().map(GroupDTO::getInitiative).anyMatch(Objects::isNull)) {
      throw new BattleException("All groups initiative has to be set");
    }
    // order duellists based on initiative and action and fill the actionList for next round
    actionList.clear();
    groups.values().stream().sorted().forEachOrdered(
        group -> actionList.addAll(group.getDuellists().stream().map(DuellistDTO::getSelectedAction).sorted().toList())
    );
    // set new state
    state = BattleState.ROUND;
  }

  public void endRound() throws BattleException {
    checkState(BattleState.ROUND, "next round");
    // check all duellist has executed their action
    if (!actionList.isEmpty()) {
      throw new BattleException("All duellists action has to be executed");
    }

    // clear group initiations and switch to PREPARATION battle state for the next round
    groups.values().forEach(this::clearGroupInitiative);
    state = BattleState.PREPARATION;

    // TODO: DMK clear all groups and actions and switch to null battle state when there is no duellist ready for battle
  }

  @Override
  public BattleState getState() {
    return state;
  }

  @Override
  public void addGroup(String name) throws BattleException {
    checkState(BattleState.NEW, "add group");

    if (StringUtils.isBlank(name)) {
      throw new BattleException("Group name has to be set");
    }

    if (groups.containsKey(name)) {
      throw new BattleException(String.format("Group %s already exists in the battle", name));
    }

    groups.put(name, new GroupDTO(name));
  }

  @Override
  public void addGroupList(List<String> groupNames) throws BattleException {
    for (var name: groupNames) {
      addGroup(name);
    }
  }

  @Override
  public void removeGroup(String name) throws BattleException {
    checkState(BattleState.NEW, "remove group");

    groups.remove(name);
  }

  @Override
  public GroupDTO getGroup(String name) {
    return groups.get(name);
  }

  @Override
  public List<GroupDTO> getGroups() {
    return groups.values().stream().toList();
  }

  @Override
  public void setGroupInitiative(String name, int value) throws BattleException {
    checkState(BattleState.PREPARATION, "set initiative");
    var group = groups.get(name);

    if (group == null) {
      throw new BattleException("Initiative can not be set on non existing group");
    }

    group.setInitiative(value);
  }

  @Override
  public Integer getGroupInitiative(String name) throws BattleException {
    var group = groups.get(name);

    if (group == null) {
      throw new BattleException("Initiative can not be gotten on non existing group");
    }

    return group.getInitiative();
  }

  @Override
  public List<DuellistDTO> getGroupDuellists(String name) throws BattleException {
    if (!groups.containsKey(name)) {
      throw new BattleException("This group does not exists in the battle");
    }

    return groups.get(name).getDuellists();
  }

  @Override
  public List<DuellistDTO> getAllDuellists() {
    return groups.values().stream().flatMap(group -> group.getDuellists().stream()).toList();
  }

  @Override
  public Queue<Action> getActions() {
    return actionList;
  }

  // region private methods

  private boolean duellistsAreReady() {
    return groups.values().stream()
        .flatMap(group -> group.getDuellists().stream())
        .map(DuellistDTO::getSelectedAction)
        .allMatch(Objects::nonNull);
  }

  private <V, E extends Collection<?>> boolean anyHasLessThanOneItem(Collection<V> values, Function<V, E> collectionAttribute) {
    return values.stream().anyMatch(value -> collectionAttribute.apply(value).size() < 1);
  }

  private void checkState(@NotNull BattleState expectedState, @NotNull String stepName) throws BattleException {
    if (!expectedState.equals(state)) {
      throw new BattleException(String.format("Step `%s` is permitted only in `%s` battle state", stepName, expectedState));
    }
  }

  private void clearGroupInitiative(GroupDTO group) {
    group.setInitiative(null);
  }

  private void clearBattle() {
    state = BattleState.NEW;
    groups.clear();
    actionList.clear();
  }

  // endregion
}
