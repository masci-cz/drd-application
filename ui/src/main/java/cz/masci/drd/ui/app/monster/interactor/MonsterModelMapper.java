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

package cz.masci.drd.ui.app.monster.interactor;

import cz.masci.drd.dto.MonsterDTO;
import cz.masci.drd.ui.app.monster.model.MonsterDetailModel;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MonsterModelMapper {

  MonsterDetailModel mapToModel(MonsterDTO dto);

  @Mapping(target = "combativeness", source = "combativeness", conditionQualifiedByName = "isNotBlank")
  @Mapping(target = "conviction", source = "conviction", conditionQualifiedByName = "isNotBlank")
  MonsterDTO mapToDto(MonsterDetailModel model);

  @Named("isNotBlank")
  @Condition
  default boolean isNotBlank(String value) {
    return StringUtils.isNotBlank(value);
  }
}
