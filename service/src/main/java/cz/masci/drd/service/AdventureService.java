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
package cz.masci.drd.service;

import cz.masci.drd.dto.AdventureDTO;
import cz.masci.commons.springfx.exception.CrudException;
import cz.masci.commons.springfx.service.CrudService;
import java.util.Optional;

/**
 *
 * @author Daniel
 */
public interface AdventureService extends CrudService<AdventureDTO> {

  Optional<AdventureDTO> getById(Long id) throws CrudException;
}
