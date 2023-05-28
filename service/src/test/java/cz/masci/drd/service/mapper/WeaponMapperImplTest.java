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
package cz.masci.drd.service.mapper;

import static cz.masci.drd.service.impl.TestConstants.LONG_ID;
import static cz.masci.drd.service.impl.TestConstants.WEAPON_DAMAGE;
import static cz.masci.drd.service.impl.TestConstants.WEAPON_NAME;
import static cz.masci.drd.service.impl.TestConstants.WEAPON_STRENGTH;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import cz.masci.drd.service.impl.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Daniel
 */
public class WeaponMapperImplTest {

  private WeaponMapperImpl weaponMapper;

  @BeforeEach
  void init() {
    weaponMapper = new WeaponMapperImpl();
  }
  
  @Test
  public void mapToDto_null() {
    var result = weaponMapper.mapToDto(null);
    assertNull(result);
  }
  
  @Test
  public void mapToDto() {
    var result = weaponMapper.mapToDto(TestUtils.createWeaponEntity());
    
    assertAll("Weapon entity",
        () -> assertEquals(LONG_ID, result.getId()),
        () -> assertEquals(WEAPON_NAME, result.getName()),
        () -> assertEquals(WEAPON_STRENGTH, result.getStrength()),
        () -> assertEquals(WEAPON_DAMAGE, result.getDamage())
    );
  }
  
  @Test
  public void mapToEntity_null() {
    var result = weaponMapper.mapToEntity(null);
    assertNull(result);
  }
  
  @Test
  public void mapToEntity() {
    var result = weaponMapper.mapToEntity(TestUtils.createWeapon());
    
    assertAll("Weapon entity",
        () -> assertEquals(LONG_ID, result.getId()),
        () -> assertEquals(WEAPON_NAME, result.getName()),
        () -> assertEquals(WEAPON_STRENGTH, result.getStrength()),
        () -> assertEquals(WEAPON_DAMAGE, result.getDamage())
    );    
  }
}
