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

import cz.masci.drd.dto.AdventureDTO;
import cz.masci.drd.model.Adventure;
import cz.masci.drd.persistence.AdventureRepository;
import cz.masci.drd.service.AdventureMapper;
import cz.masci.springfx.exception.CrudException;
import java.util.List;
import java.util.Optional;
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
public class AdventureServiceImplTest {

  @Mock
  private AdventureMapper adventureMapper;

  @Mock
  private AdventureRepository adventureRepository;

  @InjectMocks
  private AdventureServiceImpl adventureService;

  @Test
  void getById() throws CrudException {
    var mockAdventure = mock(AdventureDTO.class);

    when(adventureRepository.findById(any())).thenReturn(Optional.of(mock(Adventure.class)));
    when(adventureMapper.mapToDto(any())).thenReturn(mockAdventure);

    var result = adventureService.getById(1l);
    assertThat(result)
        .isPresent()
        .get()
        .isSameAs(mockAdventure);
  }

  @Test
  void list() throws CrudException {
    var expectedAdventures = List.of(mock(AdventureDTO.class), mock(AdventureDTO.class));
    var mockAdventureEntityList = List.of(mock(Adventure.class), mock(Adventure.class));

    var i = 0;
    for (var entity : mockAdventureEntityList) {
      when(adventureMapper.mapToDto(entity)).thenReturn(expectedAdventures.get(i++));
    }
    when(adventureRepository.findAll()).thenReturn(mockAdventureEntityList);

    var result = adventureService.list();

    assertThat(result)
        .isNotNull()
        .hasSameSizeAs(expectedAdventures)
        .containsAll(expectedAdventures);
  }

  @Test
  void save() throws CrudException {
    var mockAdventure = mock(AdventureDTO.class);

    when(adventureRepository.save(any())).thenReturn(mock(Adventure.class));
    when(adventureMapper.mapToEntity(any())).thenReturn(mock(Adventure.class));
    when(adventureMapper.mapToDto(any())).thenReturn(mockAdventure);

    var result = adventureService.save(mock(AdventureDTO.class));

    assertThat(result).isSameAs(mockAdventure);
  }

  @Test
  void delete() throws CrudException {
    when(adventureMapper.mapToEntity(any())).thenReturn(mock(Adventure.class));

    adventureService.delete(mock(AdventureDTO.class));

    verify(adventureRepository).delete(any());
  }
}
