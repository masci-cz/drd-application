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
import cz.masci.drd.service.mapper.MonsterMapper;
import cz.masci.commons.springfx.exception.CrudException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonsterServiceImpl extends AbstractService<Monster, MonsterDTO> implements MonsterService {

  private final MonsterRepository monsterRepository;
  private final MonsterMapper mapper;

  @Override
  public Optional<MonsterDTO> getById(Long id) throws CrudException {
    return get(() -> monsterRepository.findById(id));
  }

  @Override
  public List<MonsterDTO> list() throws CrudException {
    return list(monsterRepository::findAll);
  }

  @Override
  public MonsterDTO save(MonsterDTO monster) throws CrudException {
    return apply(monster, monsterRepository::save);
  }

  @Override
  public void delete(MonsterDTO monster) throws CrudException {
    accept(monster, monsterRepository::delete);
  }

  @Override
  protected Monster mapToEntity(MonsterDTO item) {
    return mapper.mapToEntity(item);
  }

  @Override
  protected MonsterDTO mapToDto(Monster item) {
    return mapper.mapToDto(item);
  }

}
