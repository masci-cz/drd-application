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

import cz.masci.commons.springfx.exception.CrudException;
import cz.masci.drd.dto.CharacterDto;
import cz.masci.drd.model.GameCharacter;
import cz.masci.drd.persistence.CharacterRepository;
import cz.masci.drd.service.CharacterService;
import cz.masci.drd.service.mapper.CharacterMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CharacterServiceImpl extends AbstractService<GameCharacter, CharacterDto> implements CharacterService {

    private final CharacterRepository characterRepository;
    private final CharacterMapper mapper;

    @Override
    public Optional<CharacterDto> findById(Long id) throws CrudException {
        return get(() -> characterRepository.findById(id));
    }

    @Override
    public List<CharacterDto> list() throws CrudException {
        return list(characterRepository::findAll);
    }

    @Override
    public CharacterDto save(CharacterDto characterDto) throws CrudException {
        return apply(characterDto, characterRepository::save);
    }

    @Override
    public void delete(CharacterDto characterDto) throws CrudException {
        accept(characterDto, characterRepository::delete);
    }

    @Override
    protected GameCharacter mapToEntity(CharacterDto item) {
        return mapper.mapToEntity(item);
    }

    @Override
    protected CharacterDto mapToDto(GameCharacter item) {
        return mapper.mapToDto(item);
    }
}
