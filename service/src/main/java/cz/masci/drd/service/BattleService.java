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

package cz.masci.drd.service;

import cz.masci.drd.dto.BattleState;
import cz.masci.drd.dto.DuellistDTO;
import cz.masci.drd.dto.GroupDTO;
import cz.masci.drd.dto.actions.Action;
import cz.masci.drd.dto.actions.ActionResult;
import cz.masci.drd.service.exception.BattleException;
import java.util.List;
import java.util.Queue;

public interface BattleService {
  /**
   * <p>Create new battle.</p>
   * <p>Clear groups and action list. Set the battle state.</p>
   */
  void createBattle();

  /**
   * <p>Start the battle</p>
   * <p>Checks the battle state and preparation for next step.</p>
   * <p><b>CONDITION:</b> At least two groups has to be set and each group contains at least one duellist {@link DuellistDTO}</p>
   * <p>Sets battle state to {@link BattleState#PREPARATION}</p>
   *
   * @throws BattleException Throws an error when step is not ready or battle state is not {@link BattleState#NEW}
   */
  void startBattle() throws BattleException;

  /**
   * <p>Reset the preparation state</p>
   * <p>Checks the battle state and set the state to {@link BattleState#NEW}</p>
   *
   * @throws BattleException Throws an error when battle state is not {@link BattleState#PREPARATION}
   */
  void resetPreparation() throws BattleException;
  /**
   * <p>Exit battle in any state</p>
   */
  void exitBattle();

  /**
   * <p>Start next round</p>
   * <p>Checks the battle state and preparation for next step.</p>
   * <p><b>CONDITION:</b> Each group has to have set initiative and all duellists has to have assigned action</p>
   * <p>Sets battle state to {@link BattleState#ROUND}</p>
   *
   * @throws BattleException Throws an error when step is not ready or battle state is not {@link BattleState#PREPARATION}
   */
  void startRound() throws BattleException;

  /**
   * <p>Cancel round</p>
   * <p>Checks the battle state.</p>
   * <p>Sets battle state to {@link BattleState#PREPARATION} without checking all actions were executed</p>
   *
   * @throws BattleException Throws an error when battle state is not {@link BattleState#ROUND}
   */
  void cancelRound() throws BattleException;

  /**
   * <p>End round</p>
   * <p>Checks the battle state and actions execution.</p>
   * <p><b>CONDITION:</b> All actions have to be executed</p>
   * <p>Sets battle state to {@link BattleState#PREPARATION} or {@link BattleState#END} based on living duellists</p>
   *
   * @throws BattleException Throws an error when battle state is not {@link BattleState#ROUND}
   */
  void endRound() throws BattleException;

  /**
   * <p>Add group list to the battle</p>
   *
   * @param groupNames List of group names
   * @throws BattleException Throws an error when battle state is not {@link BattleState#NEW}
   */
  void addGroupList(List<String> groupNames) throws BattleException;

  /**
   * <p>Add group to the battle</p>
   *
   * @param name Group name
   * @throws BattleException Throws an error when battle state is not {@link BattleState#NEW}
   */
  void addGroup(String name) throws BattleException;

  /**
   * <p>Remove group from the battle</p>
   *
   * @param name Group name
   * @throws BattleException Throws an error when battle state is not {@link BattleState#NEW}
   */
  void removeGroup(String name) throws BattleException;

  /**
   * <p>Returns group by name</p>
   *
   * @param name Group name
   * @return Group
   */
  GroupDTO getGroup(String name);

  /**
   * <p>Returns list of groups</p>
   *
   * @return List of group
   */
  List<GroupDTO> getGroups();

  /**
   * <p>Set group initiative</p>
   *
   * @param name Group name
   * @throws BattleException Throws an error when the group doesn't exist and battle state is not {@link BattleState#PREPARATION}
   */
  void setGroupInitiative(String name, int value) throws BattleException;

  /**
   * <p>Get group initiative</p>
   *
   * @param name Group name
   * @return Group initiative
   * @throws BattleException Throws an error when the group doesn't exist and battle state is not {@link BattleState#PREPARATION}
   */
  Integer getGroupInitiative(String name) throws BattleException;

  /**
   * <p>Return duellist list for specified group</p>
   *
   * @param name Group name
   * @return List of duellists in the group
   * @throws BattleException Throws an error when the group doesn't exist
   */
  List<DuellistDTO> getGroupDuellists(String name) throws BattleException;

  List<DuellistDTO> getAllDuellists();

  /**
   * Return current battle state.
   *
   * @return Current battle state
   */
  BattleState getState();

  /**
   * Return action list
   *
   * @return Action list
   */
  Queue<? extends Action<? extends ActionResult>> getActions();

}
