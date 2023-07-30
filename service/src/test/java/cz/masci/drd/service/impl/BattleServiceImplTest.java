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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cz.masci.drd.dto.BattleState;
import cz.masci.drd.dto.DuellistDTO;
import cz.masci.drd.dto.actions.Action;
import cz.masci.drd.dto.actions.ActionType;
import cz.masci.drd.service.exception.BattleException;
import org.junit.jupiter.api.Test;

class BattleServiceImplTest {

  private static final String GROUP_1 = "GROUP 1";
  private static final String GROUP_2 = "GROUP 2";

  private final BattleServiceImpl battleService = new BattleServiceImpl();

  // region createBattle

  @Test
  void createBattle_initialState() {
    assertNull(battleService.getState());
  }

  @Test
  void createBattle() {
    battleService.createBattle();

    assertEquals(BattleState.NEW, battleService.getState());
  }

  // endregion

  // region addGroup

  @Test
  void addGroup_wrongBattleState() {
    assertThrows(BattleException.class, () -> battleService.addGroup(GROUP_1));
  }

  @Test
  void addGroup_emptyName() {
    battleService.createBattle();
    assertThrows(BattleException.class, () -> battleService.addGroup(" "));
  }

  @Test
  void addGroup_sameName() throws BattleException {
    battleService.createBattle();
    battleService.addGroup(GROUP_1);
    assertThrows(BattleException.class, () -> battleService.addGroup(GROUP_1));
  }

  @Test
  void addGroup() throws BattleException {
    battleService.createBattle();
    battleService.addGroup(GROUP_1);

    var group = battleService.getGroupDuellists(GROUP_1);

    assertNotNull(group);
  }

  // endregion

  // region removeGroup

  @Test
  void removeGroup_wrongBattleState() {
    assertThrows(BattleException.class, () -> battleService.removeGroup(GROUP_1));
  }

  @Test
  void removeGroup() throws BattleException {
    battleService.createBattle();
    battleService.addGroup(GROUP_1);

    assertNotNull(battleService.getGroupDuellists(GROUP_1));

    battleService.removeGroup(GROUP_1);

    assertThrows(BattleException.class, () -> battleService.getGroupDuellists(GROUP_1));
  }

  // endregion

  // region startBattle

  @Test
  void startBattle_wrongBattleState() {
    assertThrows(BattleException.class, battleService::startBattle);
  }

  @Test
  void prepare_fewGroups() throws BattleException {
    battleService.createBattle();
    battleService.addGroup(GROUP_1);

    assertThrows(BattleException.class, battleService::startBattle);
  }

  @Test
  void startBattle_fewDuellists() throws BattleException {
    battleService.createBattle();
    battleService.addGroup(GROUP_1);
    battleService.addGroup(GROUP_2);

    battleService.getGroupDuellists(GROUP_1).add(new DuellistDTO());

    assertThrows(BattleException.class, battleService::startBattle);
  }

  @Test
  void startBattle() throws BattleException {
    battleService.createBattle();
    battleService.addGroup(GROUP_1);
    battleService.addGroup(GROUP_2);
    battleService.getGroupDuellists(GROUP_1).add(new DuellistDTO());
    battleService.getGroupDuellists(GROUP_2).add(new DuellistDTO());

    battleService.startBattle();

    assertEquals(BattleState.PREPARATION, battleService.getState());
  }

  // endregion

  // region setGroupInitiative

  @Test
  void setGroupInitiative_notExistingGroup() {
    assertThrows(BattleException.class, () -> battleService.setGroupInitiative(GROUP_1, 1));
  }

  @Test
  void setGroupInitiative_wrongBattleState() throws BattleException {
    battleService.createBattle();
    battleService.addGroup(GROUP_1);

    assertThrows(BattleException.class, () -> battleService.setGroupInitiative(GROUP_1, 1));
  }

  @Test
  void setGroupInitiative() throws BattleException {
    battleService.createBattle();
    battleService.addGroup(GROUP_1);
    battleService.addGroup(GROUP_2);
    battleService.getGroupDuellists(GROUP_1).add(new DuellistDTO());
    battleService.getGroupDuellists(GROUP_2).add(new DuellistDTO());

    battleService.startBattle();

    battleService.setGroupInitiative(GROUP_1, 2);
    battleService.setGroupInitiative(GROUP_2, 1);
  }

  // endregion

  // region startRound

  @Test
  void startRound_wrongState() {
    // enabled only in PREPARATION state
    assertThrows(BattleException.class, battleService::startRound);
  }

  @Test
  void startRound_duellistsAreNotReady() throws BattleException {
    // prepare groups
    var duellist1 = new DuellistDTO();
    var duellist2 = new DuellistDTO();
    battleService.createBattle();
    battleService.addGroup(GROUP_1);
    battleService.addGroup(GROUP_2);
    battleService.getGroupDuellists(GROUP_1).add(duellist1);
    battleService.getGroupDuellists(GROUP_2).add(duellist2);

    battleService.startBattle();

    // check duellist has set action
    assertThrows(BattleException.class, battleService::startRound);
  }

  @Test
  void startRound_initiativeIsNotSet() throws BattleException {
    // prepare groups
    var duellist1 = new DuellistDTO();
    var duellist2 = new DuellistDTO();
    battleService.createBattle();
    battleService.addGroup(GROUP_1);
    battleService.addGroup(GROUP_2);
    battleService.getGroupDuellists(GROUP_1).add(duellist1);
    battleService.getGroupDuellists(GROUP_2).add(duellist2);

    battleService.startBattle();

    // prepare duellists
    duellist1.setSelectedAction(new SimpleAction(ActionType.CLOSE_COMBAT));
    duellist2.setSelectedAction(new SimpleAction(ActionType.RANGE_COMBAT));

    // check initiative
    assertThrows(BattleException.class, battleService::startRound);
  }

  @Test
  void startRound() throws BattleException {
    // prepare groups
    var duellist1group1 = new DuellistDTO();
    var duellist2group2 = new DuellistDTO();
    var duellist3group1 = new DuellistDTO();
    var duellist4group2 = new DuellistDTO();
    battleService.createBattle();
    battleService.addGroup(GROUP_1);
    battleService.addGroup(GROUP_2);
    battleService.getGroupDuellists(GROUP_1).add(duellist1group1);
    battleService.getGroupDuellists(GROUP_2).add(duellist2group2);
    battleService.getGroupDuellists(GROUP_1).add(duellist3group1);
    battleService.getGroupDuellists(GROUP_2).add(duellist4group2);

    battleService.startBattle();
    // prepare actions
    var action1 = new SimpleAction(ActionType.MAGIC);
    var action2 = new SimpleAction(ActionType.RANGE_COMBAT);
    var action3 = new SimpleAction(ActionType.CLOSE_COMBAT);
    var action4 = new SimpleAction(ActionType.SPEECH);

    // prepare duellists
    duellist4group2.setSelectedAction(action3);
    duellist2group2.setSelectedAction(action4);
    duellist3group1.setSelectedAction(action1);
    duellist1group1.setSelectedAction(action2);

    // prepare initiative
    battleService.setGroupInitiative(GROUP_1, 2);
    battleService.setGroupInitiative(GROUP_2, 1);

    battleService.startRound();

    assertEquals(BattleState.ROUND, battleService.getState());

    assertThat(battleService.getActions()).containsOnly(action3, action4, action1, action2);
  }

  // endregion

  // region endRound

  @Test
  void endRound_wrongState() {
    // enabled only in ROUND state
    assertThrows(BattleException.class, battleService::endRound);
  }

  @Test
  void endRound_actionListIsNotEmpty() throws BattleException {
    // prepare groups
    var duellist1group1 = new DuellistDTO();
    var duellist2group2 = new DuellistDTO();
    var duellist3group1 = new DuellistDTO();
    var duellist4group2 = new DuellistDTO();
    battleService.createBattle();
    battleService.addGroup(GROUP_1);
    battleService.addGroup(GROUP_2);
    battleService.getGroupDuellists(GROUP_1).add(duellist1group1);
    battleService.getGroupDuellists(GROUP_2).add(duellist2group2);
    battleService.getGroupDuellists(GROUP_1).add(duellist3group1);
    battleService.getGroupDuellists(GROUP_2).add(duellist4group2);

    battleService.startBattle();
    // prepare actions
    var action1 = new SimpleAction(ActionType.MAGIC);
    var action2 = new SimpleAction(ActionType.RANGE_COMBAT);
    var action3 = new SimpleAction(ActionType.CLOSE_COMBAT);
    var action4 = new SimpleAction(ActionType.SPEECH);

    // prepare duellists
    duellist4group2.setSelectedAction(action3);
    duellist2group2.setSelectedAction(action4);
    duellist3group1.setSelectedAction(action1);
    duellist1group1.setSelectedAction(action2);

    // prepare initiative
    battleService.setGroupInitiative(GROUP_1, 2);
    battleService.setGroupInitiative(GROUP_2, 1);

    battleService.startRound();

    assertThrows(BattleException.class, battleService::endRound);
  }

  @Test
  void endRound_continueBattle() throws BattleException {
    // prepare groups
    var duellist1group1 = new DuellistDTO();
    var duellist2group2 = new DuellistDTO();
    var duellist3group1 = new DuellistDTO();
    var duellist4group2 = new DuellistDTO();
    battleService.createBattle();
    battleService.addGroup(GROUP_1);
    battleService.addGroup(GROUP_2);
    battleService.getGroupDuellists(GROUP_1).add(duellist1group1);
    battleService.getGroupDuellists(GROUP_2).add(duellist2group2);
    battleService.getGroupDuellists(GROUP_1).add(duellist3group1);
    battleService.getGroupDuellists(GROUP_2).add(duellist4group2);

    battleService.startBattle();
    // prepare actions
    var action1 = new SimpleAction(ActionType.MAGIC);
    var action2 = new SimpleAction(ActionType.RANGE_COMBAT);
    var action3 = new SimpleAction(ActionType.CLOSE_COMBAT);
    var action4 = new SimpleAction(ActionType.SPEECH);

    // prepare duellists
    duellist4group2.setSelectedAction(action3);
    duellist2group2.setSelectedAction(action4);
    duellist3group1.setSelectedAction(action1);
    duellist1group1.setSelectedAction(action2);

    // prepare initiative
    battleService.setGroupInitiative(GROUP_2, 2);
    battleService.setGroupInitiative(GROUP_1, 1);

    battleService.startRound();

    // check actions FIFO
    var actions = battleService.getActions();
    assertEquals(action3, actions.poll());
    assertEquals(action4, actions.poll());
    assertEquals(action1, actions.poll());
    assertEquals(action2, actions.poll());
    assertNull(actions.poll());

    battleService.endRound();

    assertEquals(BattleState.PREPARATION, battleService.getState());
  }

  // TODO: Finish end battle condition
  void endRound_endBattle() throws BattleException {
    // prepare groups
    var duellist1group1 = new DuellistDTO();
    var duellist2group2 = new DuellistDTO();
    var duellist3group1 = new DuellistDTO();
    var duellist4group2 = new DuellistDTO();
    battleService.createBattle();
    battleService.addGroup(GROUP_1);
    battleService.addGroup(GROUP_2);
    battleService.getGroupDuellists(GROUP_1).add(duellist1group1);
    battleService.getGroupDuellists(GROUP_2).add(duellist2group2);
    battleService.getGroupDuellists(GROUP_1).add(duellist3group1);
    battleService.getGroupDuellists(GROUP_2).add(duellist4group2);

    battleService.startBattle();
    // prepare actions
    var action1 = new SimpleAction(ActionType.MAGIC);
    var action2 = new SimpleAction(ActionType.RANGE_COMBAT);
    var action3 = new SimpleAction(ActionType.CLOSE_COMBAT);
    var action4 = new SimpleAction(ActionType.SPEECH);

    // prepare duellists
    duellist4group2.setSelectedAction(action3);
    duellist2group2.setSelectedAction(action4);
    duellist3group1.setSelectedAction(action1);
    duellist1group1.setSelectedAction(action2);

    // prepare initiative
    battleService.setGroupInitiative(GROUP_2, 2);
    battleService.setGroupInitiative(GROUP_1, 1);

    battleService.startRound();

    // check actions FIFO
    var actions = battleService.getActions();
    assertEquals(action3, actions.poll());
    assertEquals(action4, actions.poll());
    assertEquals(action1, actions.poll());
    assertEquals(action2, actions.poll());
    assertNull(actions.poll());

    // TODO: set condition for end battle
    assertEquals(BattleState.END, battleService.getState());
  }

  // endregion

  // region utils

  private record SimpleAction(ActionType actionType) implements Action<String> {

    @Override
    public boolean isPrepared() {
      return true;
    }

    @Override
    public String getResult() {
      return "OK";
    }

    @Override
    public void execute() {

    }

    @Override
    public ActionType getActionType() {
      return actionType;
    }

  }

  // endregion
}