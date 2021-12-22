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
import cz.masci.springfx.exception.CrudException;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author Daniel
 */
@ExtendWith(MockitoExtension.class)
public class WeaponServiceImplTest {

  @Mock
  private WeaponRepository weaponRepository;

  @Mock
  private WeaponMapper weaponMapper;

  @InjectMocks
  private WeaponServiceImpl weaponService;

  @Test
  public void list() throws CrudException {
    var expectedWeapons = List.of(mock(WeaponDTO.class), mock(WeaponDTO.class));
    var mockWeaponsEntityList = List.of(mock(Weapon.class), mock(Weapon.class));
    
    var i = 0;
    for (var entity: mockWeaponsEntityList) {
      when(weaponMapper.mapToDto(entity)).thenReturn(expectedWeapons.get(i++));
    }
    when(weaponRepository.findAll()).thenReturn(mockWeaponsEntityList);
    
    var result = weaponService.list();
    
    assertThat(result)
        .isNotNull()
        .hasSameSizeAs(expectedWeapons)
        .containsAll(expectedWeapons);
  }

  @Test
  public void save() throws CrudException {
    var mockWeapon = mock(WeaponDTO.class);
    
    when(weaponRepository.save(any())).thenReturn(mock(Weapon.class));
    when(weaponMapper.mapToEntity(any())).thenReturn(mock(Weapon.class));
    when(weaponMapper.mapToDto(any())).thenReturn(mockWeapon);
    
    var result = weaponService.save(mock(WeaponDTO.class));
    
    assertThat(result).isSameAs(mockWeapon);
  }

  @Test
  public void delete() throws CrudException {
    when(weaponMapper.mapToEntity(any())).thenReturn(mock(Weapon.class));
    
    weaponService.delete(mock(WeaponDTO.class));
    
    verify(weaponRepository).delete(any());
  }
}
