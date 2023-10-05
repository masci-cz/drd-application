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
import cz.masci.drd.service.mapper.AdventureMapper;
import cz.masci.drd.service.AdventureService;
import cz.masci.commons.springfx.exception.CrudException;
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
    return get(() -> adventureRepository.findById(id));
  }

  @Override
  public List<AdventureDTO> list() throws CrudException {
    return list(adventureRepository::findAll);
  }

  @Override
  public AdventureDTO save(AdventureDTO item) throws CrudException {
    return apply(item, adventureRepository::save);
  }

  @Override
  public void delete(AdventureDTO item) throws CrudException {
    accept(item, adventureRepository::delete);
  }

  @Override
  protected Adventure mapToEntity(AdventureDTO item) {
    return mapper.mapToEntity(item);
  }

  @Override
  protected AdventureDTO mapToDto(Adventure item) {
    return mapper.mapToDto(item);
  }

}
