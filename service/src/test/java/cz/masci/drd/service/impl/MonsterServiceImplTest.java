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

import cz.masci.drd.dto.MonsterDTO;
import cz.masci.drd.model.Monster;
import cz.masci.drd.persistence.MonsterRepository;
import cz.masci.drd.service.MonsterMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author Daniel
 */
@ExtendWith(MockitoExtension.class)
public class MonsterServiceImplTest {

    @Mock
    private MonsterMapper monsterMapper;

    @Mock
    private MonsterRepository monsterRepository;

    @InjectMocks
    private MonsterServiceImpl monsterService;

    @Test
    void testSomeMethod() {
        var mockMonster = mock(MonsterDTO.class);
        
        when(monsterRepository.findById(any())).thenReturn(Optional.of(mock(Monster.class)));
        when(monsterMapper.mapToDto(any())).thenReturn(mockMonster);

        var result = monsterService.getMonster(1l);

        assertTrue(result.isPresent());
        
        var monster = result.get();
        
        assertThat(monster).isSameAs(mockMonster);
    }

}
