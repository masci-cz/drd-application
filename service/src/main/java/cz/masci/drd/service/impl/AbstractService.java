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

import cz.masci.springfx.data.Modifiable;
import cz.masci.springfx.exception.CrudException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 *
 * 
 * @author Daniel
 * 
 * @param <E> Entity
 * @param <T> Dto
 * 
 */
public abstract class AbstractService<E, T extends Modifiable> {

  protected Optional<T> getOne(Supplier<Optional<E>> entityLoader, Function<E, T> mapper) throws CrudException {
    Optional<T> dto;

    try {
      dto = entityLoader.get().map(mapper);
    } catch (Exception ex) {
      throw CrudException.createReadException(ex);
    }

    return dto;
  }
  
  protected List<T> getList(Supplier<List<E>> entityLoader, Function<E, T> mapper) throws CrudException {
    List<T> dtos;

    try {
      dtos = entityLoader.get().stream().map(mapper).collect(Collectors.toList());
    } catch (Exception ex) {
      throw CrudException.createReadException(ex);
    }

    return dtos;
  }
  
  protected T apply(Supplier<E> entitySupplier, Function<E, T> mapper) throws CrudException {
    T dto;
    
    try {
      dto = mapper.apply(entitySupplier.get());
    } catch (Exception ex) {
      throw CrudException.createWriteException(ex);
    }
    
    return dto;
  }
}
