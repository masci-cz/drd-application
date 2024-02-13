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

package cz.masci.drd.ui.monster.interactor;

import cz.masci.commons.springfx.exception.CrudException;
import cz.masci.drd.service.MonsterService;
import cz.masci.drd.ui.monster.model.MonsterDetailModel;
import cz.masci.drd.ui.util.interactor.CRUDInteractor;
import cz.masci.springfx.mvci.model.detail.DirtyModel;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonsterInteractor implements CRUDInteractor<MonsterDetailModel> {

  private final MonsterService monsterService;
  private final MonsterModelMapper monsterModelMapper;

  @Override
  public List<MonsterDetailModel> list() throws CrudException {
    return monsterService.list().stream()
        .map(monsterModelMapper::mapToModel)
        .peek(DirtyModel::rebaseline)
        .collect(Collectors.toList());
  }

  @Override
  public MonsterDetailModel save(MonsterDetailModel model) throws CrudException {
    var dto = monsterModelMapper.mapToDto(model);
    var savedDto = monsterService.save(dto);
    return monsterModelMapper.mapToModel(savedDto);
  }

  @Override
  public void delete(MonsterDetailModel model) throws CrudException {
    monsterService.delete(monsterModelMapper.mapToDto(model));
  }
}
