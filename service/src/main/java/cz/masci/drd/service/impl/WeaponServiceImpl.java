/*
 * Copyright (C) 2021 Daniel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cz.masci.drd.service.impl;

import cz.masci.drd.dto.WeaponDTO;
import cz.masci.drd.model.Weapon;
import cz.masci.drd.persistence.WeaponRepository;
import cz.masci.drd.service.WeaponMapper;
import cz.masci.drd.service.WeaponService;
import cz.masci.springfx.exception.CrudException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeaponServiceImpl extends AbstractService<Weapon, WeaponDTO> implements WeaponService {

  private final WeaponRepository weaponRepository;
  private final WeaponMapper mapper;
      
  @Override
  public List<WeaponDTO> list() throws CrudException {
    return list(weaponRepository::findAll);
  }

  @Override
  public WeaponDTO save(WeaponDTO item) throws CrudException {
    return apply(item, weaponRepository::save);
  }

  @Override
  public void delete(WeaponDTO item) throws CrudException {
    accept(item, weaponRepository::delete);
  }

  @Override
  protected Weapon mapToEntity(WeaponDTO item) {
    return mapper.mapToEntity(item);
  }

  @Override
  protected WeaponDTO mapToDto(Weapon item) {
    return mapper.mapToDto(item);
  }
  
}
