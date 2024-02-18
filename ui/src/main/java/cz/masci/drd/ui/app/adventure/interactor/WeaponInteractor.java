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
import cz.masci.drd.service.WeaponService;
import cz.masci.drd.ui.app.adventure.model.WeaponDetailModel;
import cz.masci.drd.ui.common.interactor.CRUDInteractor;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeaponInteractor implements CRUDInteractor<WeaponDetailModel> {
  private final WeaponService weaponService;
  private final WeaponModelMapper weaponModelMapper;

  public List<WeaponDetailModel> list() throws CrudException {
    return weaponService.list().stream()
        .map(weaponModelMapper::mapToModel)
        .peek(WeaponDetailModel::rebaseline)
        .collect(Collectors.toList());
  }

  public WeaponDetailModel save(WeaponDetailModel model) throws CrudException {
    var dto = weaponModelMapper.mapToDto(model);
    var savedDto = weaponService.save(dto);
    return weaponModelMapper.mapToModel(savedDto);
  }

  public void delete(WeaponDetailModel model) throws CrudException {
    weaponService.delete(weaponModelMapper.mapToDto(model));
  }
}
