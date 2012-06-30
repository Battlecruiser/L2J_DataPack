/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Group Template AI:<br>
 * This folder contains AI scripts for group templates.<br>
 * That is, if many different mobs share the same behavior, a group AI script can be created for all of them.<br>
 * Such group templates ought to be here.<br>
 * <br>
 * Group templates can be sub-classed.<br>
 * In other words, a group may inherit from another group.<br>
 * For example, one group template might define mobs that cast spells.<br>
 * Another template may then define the AI for mobs that cast spells AND use shots.<br>
 * In that case, instead of rewriting all the attack and spell-use AI, we can inherit from the first group template, then add the new behaviors, and split up the NPC registrations appropriately.<br>
 * <br>
 * "NPC registrations" refers to the addition of NPCs in the various events of the scripts, such as onAttack, onKill, etc.<br>
 * Those are done by using methods such as addKillId(..) etc.<br>
 * @see quests
 * @author Fulminus, Zoey76
 */
package ai.group_template;