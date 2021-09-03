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

@Service
@RequiredArgsConstructor
public class MonsterServiceImpl implements MonsterService {

    private final MonsterRepository monsterRepository;
    private final MonsterMapper mapper;
    
    @Override
    public Optional<MonsterDTO> getMonster(Long id) {
        return monsterRepository.findById(id).map(test -> mapper.mapToDto(test));
    }
    
}
