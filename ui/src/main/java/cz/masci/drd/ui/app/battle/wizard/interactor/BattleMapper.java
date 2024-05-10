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
import cz.masci.drd.ui.app.battle.wizard.model.BattleDuellistDetailModel;
import cz.masci.drd.ui.app.battle.wizard.model.BattlePreparationSummaryDuellistModel;
import cz.masci.drd.ui.app.battle.wizard.model.BattlePreparationSummaryGroupModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BattleMapper {

  @Mapping(target = "originalLive", source = "live")
  @Mapping(target = "currentLive", source = "live")
  @Mapping(target = "weapons", ignore = true)
  @Mapping(target = "selectedAction", ignore = true)
  DuellistDTO mapDuellistFromModel(BattleDuellistDetailModel model);

  @Mapping(target = "live", source = "originalLive")
  BattlePreparationSummaryDuellistModel mapDuellistToModel(DuellistDTO dto);

  BattlePreparationSummaryGroupModel mapGroupToModel(GroupDTO dto);
}
