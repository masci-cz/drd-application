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
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import cz.masci.drd.persistence.MonsterRepository;
import cz.masci.drd.service.MonsterService;
import cz.masci.drd.service.MonsterMapper;
import cz.masci.springfx.exception.CrudException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonsterServiceImpl implements MonsterService {

  private final MonsterRepository monsterRepository;
  private final MonsterMapper mapper;

  @Override
  public Optional<MonsterDTO> getById(Long id) throws CrudException {
    Optional<MonsterDTO> monster;

    try {
      monster = monsterRepository.findById(id).map(test -> mapper.mapToDto(test));
    } catch (Exception ex) {
      throw CrudException.createReadException(ex);
    }

    return monster;
  }

  @Override
  public List<MonsterDTO> list() throws CrudException {
    List<MonsterDTO> monsters;

    try {
      monsters = monsterRepository.findAll().stream().map(mapper::mapToDto).collect(Collectors.toList());
    } catch (Exception ex) {
      throw CrudException.createReadException(ex);
    }

    return monsters;
  }

  @Override
  public MonsterDTO save(MonsterDTO monster) throws CrudException {
    Monster entity;
    
    try {
      entity = monsterRepository.save(mapper.mapToEntity(monster));
    } catch (Exception ex) {
      throw CrudException.createWriteException(ex);
    }
    
    return mapper.mapToDto(entity);
  }

  @Override
  public MonsterDTO delete(MonsterDTO item) throws CrudException {
    try {
      monsterRepository.delete(mapper.mapToEntity(item));
    } catch (Exception ex) {
      throw CrudException.createWriteException(ex);
    }
    
    return item;
  }

}
