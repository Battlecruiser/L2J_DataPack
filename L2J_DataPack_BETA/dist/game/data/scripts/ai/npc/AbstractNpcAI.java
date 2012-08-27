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
package ai.npc;

import java.util.logging.Logger;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.gameserver.network.serverpackets.SocialAction;
import com.l2jserver.gameserver.scripting.scriptengine.impl.L2Script;
import com.l2jserver.gameserver.util.Broadcast;

/**
 * Abstract NPC AI class for datapack based AIs.
 * @author UnAfraid, Zoey76
 */
public abstract class AbstractNpcAI extends L2Script
{
	public Logger _log = Logger.getLogger(getClass().getSimpleName());
	
	/**
	 * Simple on first talk event handler.
	 */
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		return npc.getNpcId() + ".html";
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		return null;
	}
	
	public AbstractNpcAI(String name, String descr)
	{
		super(-1, name, descr);
	}
	
	/**
	 * Registers the fallowing events to the current script:<br>
	 * <ul>
	 * 	<li>ON_ATTACK</li>
	 * 	<li>ON_KILL</li>
	 * 	<li>ON_SPAWN</li>
	 * 	<li>ON_SPELL_FINISHED</li>
	 * 	<li>ON_SKILL_SEE</li>
	 * 	<li>ON_FACTION_CALL</li>
	 * 	<li>ON_AGGR_RANGE_ENTER</li>
	 * </ul>
	 * @param mobs
	 */
	public void registerMobs(int... mobs)
	{
		for (int id : mobs)
		{
			addEventId(id, QuestEventType.ON_ATTACK);
			addEventId(id, QuestEventType.ON_KILL);
			addEventId(id, QuestEventType.ON_SPAWN);
			addEventId(id, QuestEventType.ON_SPELL_FINISHED);
			addEventId(id, QuestEventType.ON_SKILL_SEE);
			addEventId(id, QuestEventType.ON_FACTION_CALL);
			addEventId(id, QuestEventType.ON_AGGRO_RANGE_ENTER);
		}
	}
	
	/**
	 * This is used to register all monsters contained in mobs for a particular script
	 * event types defined in types.
	 * @param mobs
	 * @param types
	 */
	public void registerMobs(int[] mobs, QuestEventType... types)
	{
		for (int id : mobs)
		{
			for (QuestEventType type : types)
			{
				addEventId(id, type);
			}
		}
	}
	
	public void registerMobs(Iterable<Integer> mobs, QuestEventType... types)
	{
		for (int id : mobs)
		{
			for (QuestEventType type : types)
			{
				addEventId(id, type);
			}
		}
	}
	
	/**
	 * Broadcasts NpcSay packet to all known players with custom string.
	 * @param npc
	 * @param type
	 * @param text
	 */
	protected void broadcastNpcSay(L2Npc npc, int type, String text)
	{
		Broadcast.toKnownPlayers(npc, new NpcSay(npc.getObjectId(), type, npc.getTemplate().getIdTemplate(), text));
	}
	
	/**
	 * Broadcasts NpcSay packet to all known players with npc string id.
	 * @param npc
	 * @param type
	 * @param stringId
	 */
	protected void broadcastNpcSay(L2Npc npc, int type, NpcStringId stringId)
	{
		Broadcast.toKnownPlayers(npc, new NpcSay(npc.getObjectId(), type, npc.getTemplate().getIdTemplate(), stringId));
	}
	
	/**
	 * Broadcasts NpcSay packet to all known players with custom string in specific radius.
	 * @param npc
	 * @param type
	 * @param text
	 * @param radius
	 */
	protected void broadcastNpcSay(L2Npc npc, int type, String text, int radius)
	{
		Broadcast.toKnownPlayersInRadius(npc, new NpcSay(npc.getObjectId(), type, npc.getTemplate().getIdTemplate(), text), radius);
	}
	
	/**
	 * Broadcasts NpcSay packet to all known players with npc string id in specific radius.
	 * @param npc
	 * @param type
	 * @param stringId
	 * @param radius
	 */
	protected void broadcastNpcSay(L2Npc npc, int type, NpcStringId stringId, int radius)
	{
		Broadcast.toKnownPlayersInRadius(npc, new NpcSay(npc.getObjectId(), type, npc.getTemplate().getIdTemplate(), stringId), radius);
	}
	
	/**
	 * Broadcasts SocialAction packet to self and known players.
	 * @param character
	 * @param actionId
	 */
	protected void broadcastSocialAction(L2Character character, int actionId)
	{
		Broadcast.toSelfAndKnownPlayers(character, new SocialAction(character.getObjectId(), actionId));
	}
	
	/**
	 * Broadcasts SocialAction packet to self and known players in specific radius.
	 * @param character
	 * @param actionId
	 * @param radius
	 */
	protected void broadcastSocialAction(L2Character character, int actionId, int radius)
	{
		Broadcast.toSelfAndKnownPlayersInRadius(character, new SocialAction(character.getObjectId(), actionId), radius);
	}
	
	/**
	 * Give item/reward to the player
	 * @param player
	 * @param holder
	 */
	protected void giveItems(L2PcInstance player, ItemHolder holder)
	{
		super.giveItems(player, holder.getId(), holder.getCount());
	}
	
	/**
	 * Monster is running and attacking the playable.
	 * @param npc
	 * @param playable
	 */
	protected void attackPlayer(L2Attackable npc, L2Playable playable)
	{
		npc.setIsRunning(true);
		npc.addDamageHate(playable, 0, 999);
		npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, playable);
	}
}