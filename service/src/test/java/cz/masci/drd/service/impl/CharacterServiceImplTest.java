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

import cz.masci.drd.dto.CharacterDto;
import cz.masci.drd.model.GameCharacter;
import cz.masci.drd.persistence.CharacterRepository;
import cz.masci.drd.service.mapper.CharacterMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CharacterServiceImplTest {

    @Mock
    private CharacterMapper characterMapper;

    @Mock
    private CharacterRepository characterRepository;

    @InjectMocks
    private CharacterServiceImpl characterService;

    @Test
    @SneakyThrows
    void findById() {
        var mockCharacterDto = mock(CharacterDto.class);

        when(characterRepository.findById(any())).thenReturn(Optional.of(mock(GameCharacter.class)));
        when(characterMapper.mapToDto(any())).thenReturn(mockCharacterDto);

        var result = characterService.findById(1L);

        assertTrue(result.isPresent());

        var character = result.get();

        assertThat(character).isSameAs(mockCharacterDto);
    }

    @Test
    @SneakyThrows
    void list() {
        var expectedCharacters = List.of(mock(CharacterDto.class), mock(CharacterDto.class));
        var mockCharacterEntityList = List.of(mock(GameCharacter.class), mock(GameCharacter.class));

        var i = 0;
        for (var entity : mockCharacterEntityList) {
            when(characterMapper.mapToDto(entity)).thenReturn(expectedCharacters.get(i++));
        }
        when(characterRepository.findAll()).thenReturn(mockCharacterEntityList);

        var result = characterService.list();

        assertThat(result)
                .isNotNull()
                .hasSameSizeAs(expectedCharacters)
                .containsAll(expectedCharacters);
    }

    @Test
    @SneakyThrows
    void save() {
        var mockCharacterDto = mock(CharacterDto.class);

        when(characterRepository.save(any())).thenReturn(mock(GameCharacter.class));
        when(characterMapper.mapToEntity(any())).thenReturn(mock(GameCharacter.class));
        when(characterMapper.mapToDto(any())).thenReturn(mockCharacterDto);

        var result = characterService.save(mock(CharacterDto.class));

        assertThat(result).isSameAs(mockCharacterDto);
    }

    @Test
    @SneakyThrows
    void delete() {
        when(characterMapper.mapToEntity(any())).thenReturn(mock(GameCharacter.class));

        characterService.delete(mock(CharacterDto.class));

        verify(characterRepository).delete(any());
    }
}