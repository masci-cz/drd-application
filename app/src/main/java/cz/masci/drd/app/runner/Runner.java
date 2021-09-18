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
package cz.masci.drd.app.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import cz.masci.drd.service.MonsterService;

/**
 *
 * @author Daniel
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Runner implements ApplicationRunner {

    private final MonsterService monsterService;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Running DrD application");
        printTest(0l);
        printTest(4l);
    }

    private void printTest(long id) {
        var test = monsterService.getMonster(id);
        
        if (test.isPresent()) {
            log.info("Found record: {}", test.get());
        } else {
            log.info("Record id = {} not found", id);
        }
    }
}
