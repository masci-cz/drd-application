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

package cz.masci.drd.ui.app.adventure.interactor;

import cz.masci.commons.springfx.exception.CrudException;
import cz.masci.drd.service.AdventureService;
import cz.masci.drd.ui.app.adventure.model.AdventureDetailModel;
import cz.masci.drd.ui.common.interactor.CRUDInteractor;
import cz.masci.springfx.mvci.model.detail.DirtyModel;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdventureInteractor implements CRUDInteractor<AdventureDetailModel> {

  private final AdventureService adventureService;
  private final AdventureModelMapper adventureModelMapper;

  @Override
  public List<AdventureDetailModel> list() throws CrudException {
    return adventureService.list().stream()
        .map(adventureModelMapper::mapToModel)
        .peek(DirtyModel::rebaseline)
        .collect(Collectors.toList());
  }

  @Override
  public AdventureDetailModel save(AdventureDetailModel item) throws CrudException {
    var dto = adventureModelMapper.mapToDto(item);
    var savedDto = adventureService.save(dto);
    return adventureModelMapper.mapToModel(savedDto);
  }

  @Override
  public void delete(AdventureDetailModel item) throws CrudException {
    adventureService.delete(adventureModelMapper.mapToDto(item));
  }
}
