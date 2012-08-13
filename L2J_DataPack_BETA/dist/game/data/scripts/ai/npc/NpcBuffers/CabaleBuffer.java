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
package ai.npc.NpcBuffers;

import java.util.Collection;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.SevenSigns;
import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.MagicSkillUse;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @author UnAfraid
 */
public class CabaleBuffer extends AbstractNpcAI
{
	private static final int DISTANCE_TO_WATCH_OBJECT = 900;
	
	protected CabaleBuffer(String name, String descr)
	{
		super(name, descr);
		addFirstTalkId(SevenSigns.ORATOR_NPC_ID, SevenSigns.PREACHER_NPC_ID);
		addSpawnId(SevenSigns.ORATOR_NPC_ID, SevenSigns.PREACHER_NPC_ID);
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		return null;
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		ThreadPoolManager.getInstance().scheduleGeneral(new CabaleAI(npc), 3000);
		return super.onSpawn(npc);
	}
	
	protected class CabaleAI implements Runnable
	{
		private final L2Npc _npc;
		
		protected CabaleAI(L2Npc npc)
		{
			_npc = npc;
		}
		
		@Override
		public void run()
		{
			if ((_npc == null) || !_npc.isVisible())
			{
				return;
			}
			
			boolean isBuffAWinner = false;
			boolean isBuffALoser = false;
			
			final int winningCabal = SevenSigns.getInstance().getCabalHighestScore();
			int losingCabal = SevenSigns.CABAL_NULL;
			
			if (winningCabal == SevenSigns.CABAL_DAWN)
			{
				losingCabal = SevenSigns.CABAL_DUSK;
			}
			else if (winningCabal == SevenSigns.CABAL_DUSK)
			{
				losingCabal = SevenSigns.CABAL_DAWN;
			}
			
			Collection<L2PcInstance> plrs = _npc.getKnownList().getKnownPlayers().values();
			for (L2PcInstance player : plrs)
			{
				if ((player == null) || player.isInvul())
				{
					continue;
				}
				
				final int playerCabal = SevenSigns.getInstance().getPlayerCabal(player.getObjectId());
				
				if ((playerCabal == winningCabal) && (playerCabal != SevenSigns.CABAL_NULL) && (_npc.getNpcId() == SevenSigns.ORATOR_NPC_ID))
				{
					if (!player.isMageClass())
					{
						if (handleCast(player, 4364))
						{
							isBuffAWinner = true;
							continue;
						}
					}
					else
					{
						if (handleCast(player, 4365))
						{
							isBuffAWinner = true;
							continue;
						}
					}
				}
				else if ((playerCabal == losingCabal) && (playerCabal != SevenSigns.CABAL_NULL) && (_npc.getNpcId() == SevenSigns.PREACHER_NPC_ID))
				{
					if (!player.isMageClass())
					{
						if (handleCast(player, 4361))
						{
							isBuffALoser = true;
							continue;
						}
					}
					else
					{
						if (handleCast(player, 4362))
						{
							isBuffALoser = true;
							continue;
						}
					}
				}
				
				if (isBuffAWinner && isBuffALoser)
				{
					break;
				}
			}
			
			ThreadPoolManager.getInstance().scheduleGeneral(this, 3000);
		}
		
		/**
		 * For each known player in range, cast either the positive or negative buff. <BR>
		 * The stats affected depend on the player type, either a fighter or a mystic. <BR>
		 * <BR>
		 * Curse of Destruction (Loser)<BR>
		 * - Fighters: -25% Accuracy, -25% Effect Resistance<BR>
		 * - Mystics: -25% Casting Speed, -25% Effect Resistance<BR>
		 * <BR>
		 * <BR>
		 * Blessing of Prophecy (Winner) - Fighters: +25% Max Load, +25% Effect Resistance<BR>
		 * - Mystics: +25% Magic Cancel Resist, +25% Effect Resistance<BR>
		 * @param player 
		 * @param skillId 
		 * @return 
		 */
		private boolean handleCast(L2PcInstance player, int skillId)
		{
			int skillLevel = (player.getLevel() > 40) ? 1 : 2;
			
			if (player.isDead() || !player.isVisible() || !_npc.isInsideRadius(player, DISTANCE_TO_WATCH_OBJECT, false, false))
			{
				return false;
			}
			
			L2Skill skill = SkillTable.getInstance().getInfo(skillId, skillLevel);
			if (player.getFirstEffect(skill) == null)
			{
				skill.getEffects(_npc, player);
				_npc.broadcastPacket(new MagicSkillUse(_npc, player, skill.getId(), skillLevel, skill.getHitTime(), 0));
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.YOU_FEEL_S1_EFFECT);
				sm.addSkillName(skill);
				player.sendPacket(sm);
				return true;
			}
			
			return false;
		}
	}
	
	public static void main(String[] args)
	{
		new CabaleBuffer(CabaleBuffer.class.getSimpleName(), "ai/npc");
	}
}
