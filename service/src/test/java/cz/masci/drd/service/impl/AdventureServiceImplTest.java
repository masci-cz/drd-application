/*
 * Copyright (C) 2025 Daniel Masek
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
import cz.masci.drd.service.mapper.AdventureMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static cz.masci.drd.service.impl.TestConstants.LONG_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
  @SneakyThrows
  void getById() {
    var mockAdventure = mock(AdventureDTO.class);

    when(adventureRepository.findById(any())).thenReturn(Optional.of(mock(Adventure.class)));
    when(adventureMapper.mapToDto(any())).thenReturn(mockAdventure);

    var result = adventureService.getById(LONG_ID);
    assertThat(result)
        .isPresent()
        .get()
        .isSameAs(mockAdventure);
  }

  @Test
  @SneakyThrows
  void list() {
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
  @SneakyThrows
  void save() {
    var mockAdventure = mock(AdventureDTO.class);

    when(adventureRepository.save(any())).thenReturn(mock(Adventure.class));
    when(adventureMapper.mapToEntity(any())).thenReturn(mock(Adventure.class));
    when(adventureMapper.mapToDto(any())).thenReturn(mockAdventure);

    var result = adventureService.save(mock(AdventureDTO.class));

    assertThat(result).isSameAs(mockAdventure);
  }

  @Test
  @SneakyThrows
  void delete() {
    when(adventureMapper.mapToEntity(any())).thenReturn(mock(Adventure.class));

    adventureService.delete(mock(AdventureDTO.class));

    verify(adventureRepository).delete(any());
  }
}
