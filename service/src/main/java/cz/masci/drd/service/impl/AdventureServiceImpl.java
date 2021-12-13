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
import cz.masci.drd.service.AdventureService;
import cz.masci.springfx.exception.CrudException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdventureServiceImpl extends AbstractService<Adventure, AdventureDTO> implements AdventureService {

  private final AdventureRepository adventureRepository;
  private final AdventureMapper mapper;

  @Override
  public Optional<AdventureDTO> getById(Long id) throws CrudException {
    return getOne(() -> adventureRepository.findById(id), mapper::mapToDto);
  }

  @Override
  public List<AdventureDTO> list() throws CrudException {
    return getList(() -> adventureRepository.findAll(), mapper::mapToDto);
  }

  @Override
  public AdventureDTO save(AdventureDTO item) throws CrudException {
    return apply(() -> adventureRepository.save(mapper.mapToEntity(item)), mapper::mapToDto);
  }

  @Override
  public AdventureDTO delete(AdventureDTO item) throws CrudException {
    return apply(
        () -> {
          Adventure entity = mapper.mapToEntity(item);
          adventureRepository.delete(entity);
          return entity;
        },
        mapper::mapToDto
    );
  }

}
