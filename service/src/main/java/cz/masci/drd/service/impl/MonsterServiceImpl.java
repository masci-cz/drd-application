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
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import cz.masci.drd.persistence.MonsterRepository;
import cz.masci.drd.service.MonsterService;
import cz.masci.drd.service.MonsterMapper;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonsterServiceImpl implements MonsterService {

  private final MonsterRepository monsterRepository;
  private final MonsterMapper mapper;

  @Override
  public Optional<MonsterDTO> getById(Long id) {
    return monsterRepository.findById(id).map(test -> mapper.mapToDto(test));
  }

  @Override
  public List<MonsterDTO> list() {
    return monsterRepository.findAll().stream().map(mapper::mapToDto).collect(Collectors.toList());
  }

  @Override
  public MonsterDTO save(MonsterDTO monster) {
    var entity = monsterRepository.save(mapper.mapToEntity(monster));
    return mapper.mapToDto(entity);
  }

  @Override
  public MonsterDTO delete(MonsterDTO item) {
    monsterRepository.delete(mapper.mapToEntity(item));
    return item;
  }

}
