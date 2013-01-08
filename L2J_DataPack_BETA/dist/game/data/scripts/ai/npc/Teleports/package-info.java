/*
 * Copyright (C) 2004-2013 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Teleporter AIs are used for teleports that are too advanced to be done under normal means.<br>
 * For example, teleporting to the race track allows a player a free teleport and a free return to the same city from which the player went to the race track.<br>
 * Normal teleports do not store any information about the origin.<br>
 * In order to achieve this special teleportation, a teleport script is needed.<br>
 * <br>
 * All implementation details are fully covered by quests.<br>
 * @author Zoey76
 */
package ai.npc.Teleports;