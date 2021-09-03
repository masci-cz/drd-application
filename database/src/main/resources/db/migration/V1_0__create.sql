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
/**
 * Author:  Daniel
 * Created: 10. 8. 2021
 */

CREATE TABLE MONSTER (
    MONSTER_ID BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    VIABILITY VARCHAR(100) NOT NULL,
    ATTACK VARCHAR(255) NOT NULL,
    DEFENSE VARCHAR(255) NOT NULL,
    ENDURANCE SMALLINT NOT NULL,
    DIMENSION VARCHAR(100) NOT NULL,
    COMBATIVENESS SMALLINT,
    VULNERABILITY VARCHAR(100) NOT NULL,
    MOVEABILITY VARCHAR(100) NOT NULL,
    STAMINA VARCHAR(100),
    INTELLIGENCE SMALLINT NOT NULL,
    CONVICTION SMALLINT,
    TREASURE VARCHAR(50) NOT NULL,
    EXPERIENCE VARCHAR(50) NOT NULL,
    DESCRIPTION VARCHAR
);
