/*
 * Copyright (C) 2004-2014 L2J DataPack
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
package quests.TerritoryWarScripts;

import java.util.Calendar;

import quests.Q00147_PathtoBecominganEliteMercenary.Q00147_PathtoBecominganEliteMercenary;
import quests.Q00148_PathtoBecominganExaltedMercenary.Q00148_PathtoBecominganExaltedMercenary;
import quests.Q00176_StepsForHonor.Q00176_StepsForHonor;

import com.l2jserver.gameserver.instancemanager.CastleManager;
import com.l2jserver.gameserver.instancemanager.GlobalVariablesManager;
import com.l2jserver.gameserver.instancemanager.TerritoryWarManager;
import com.l2jserver.gameserver.instancemanager.TerritoryWarManager.TerritoryNPCSpawn;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.TerritoryWard;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;
import com.l2jserver.gameserver.util.Util;
import com.l2jserver.util.L2FastMap;

/**
 * Territory War quests superclass.
 * @author Gigiikun
 */
public class TerritoryWarSuperClass extends Quest
{
	private static L2FastMap<Integer, TerritoryWarSuperClass> _forTheSakeScripts = new L2FastMap<>();
	private static L2FastMap<Integer, TerritoryWarSuperClass> _protectTheScripts = new L2FastMap<>();
	private static L2FastMap<Integer, TerritoryWarSuperClass> _killTheScripts = new L2FastMap<>();
	
	// "For the Sake of the Territory ..." quests variables
	public int CATAPULT_ID;
	public int TERRITORY_ID;
	public int[] LEADER_IDS;
	public int[] GUARD_IDS;
	public NpcStringId[] npcString = {};
	// "Protect the ..." quests variables
	public int[] NPC_IDS;
	// "Kill The ..."
	public int[] CLASS_IDS;
	public int RANDOM_MIN;
	public int RANDOM_MAX;
	
	public TerritoryWarSuperClass(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		if (questId < 0)
		{
			// Outpost and Ward handled by the Super Class script
			addSkillSeeId(36590);
			
			// Calculate next TW date
			final Calendar cal = Calendar.getInstance();
			
			final long nextSiegeDate = GlobalVariablesManager.getInstance().getLong("nextTWStartDate", 0);
			if (nextSiegeDate > System.currentTimeMillis())
			{
				cal.setTimeInMillis(nextSiegeDate);
			}
			else
			{
				// Let's check if territory war date was in the past
				if (cal.before(Calendar.getInstance()))
				{
					cal.setTimeInMillis(System.currentTimeMillis());
				}
				
				boolean hasOwnedCastle = CastleManager.getInstance().hasOwnedCastle();
				cal.set(Calendar.DAY_OF_WEEK, hasOwnedCastle ? Calendar.SATURDAY : Calendar.SUNDAY);
				cal.set(Calendar.HOUR_OF_DAY, hasOwnedCastle ? 20 : 22);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				if (cal.before(Calendar.getInstance()))
				{
					cal.add(Calendar.WEEK_OF_YEAR, 2);
				}
			}
			TerritoryWarManager.getInstance().setTWStartTimeInMillis(cal.getTimeInMillis());
			_log.info(getClass().getSimpleName() + ": Siege date: " + cal.getTime());
		}
	}
	
	public int getTerritoryIdForThisNPCId(int npcid)
	{
		return 0;
	}
	
	private void handleKillTheQuest(L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		int kill = 1;
		int max = 10;
		if (st == null)
		{
			st = newQuestState(player);
		}
		if (!st.isCompleted())
		{
			if (!st.isStarted())
			{
				st.setState(State.STARTED);
				st.setCond(1);
				st.set("kill", "1");
				max = getRandom(RANDOM_MIN, RANDOM_MAX);
				st.set("max", String.valueOf(max));
			}
			else
			{
				kill = st.getInt("kill") + 1;
				max = st.getInt("max");
			}
			if (kill >= max)
			{
				TerritoryWarManager.getInstance().giveTWQuestPoint(player);
				st.addExpAndSp(534000, 51000);
				st.set("doneDate", String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_YEAR)));
				st.exitQuest(true);
				player.sendPacket(new ExShowScreenMessage(npcString[1], 2, 10000));
			}
			else
			{
				st.set("kill", String.valueOf(kill));
				
				ExShowScreenMessage message = new ExShowScreenMessage(npcString[0], 2, 10000);
				message.addStringParameter(String.valueOf(max));
				message.addStringParameter(String.valueOf(kill));
				player.sendPacket(message);
				
			}
		}
		else if (st.getInt("doneDate") != Calendar.getInstance().get(Calendar.DAY_OF_YEAR))
		{
			st.setState(State.STARTED);
			st.setCond(1);
			st.set("kill", "1");
			max = getRandom(RANDOM_MIN, RANDOM_MAX);
			st.set("max", String.valueOf(max));
			
			ExShowScreenMessage message = new ExShowScreenMessage(npcString[0], 2, 10000);
			message.addStringParameter(String.valueOf(max));
			message.addStringParameter(String.valueOf(kill));
			player.sendPacket(message);
		}
		else if (player.isGM())
		{
			// just for test
			player.sendMessage("Cleaning " + getName() + " Territory War quest by force!");
			st.setState(State.STARTED);
			st.setCond(1);
			st.set("kill", "1");
			max = getRandom(RANDOM_MIN, RANDOM_MAX);
			st.set("max", String.valueOf(max));
			
			ExShowScreenMessage message = new ExShowScreenMessage(npcString[0], 2, 10000);
			message.addStringParameter(String.valueOf(max));
			message.addStringParameter(String.valueOf(kill));
			player.sendPacket(message);
		}
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance player, int damage, boolean isSummon)
	{
		if ((npc.getCurrentHp() == npc.getMaxHp()) && Util.contains(NPC_IDS, npc.getId()))
		{
			int territoryId = getTerritoryIdForThisNPCId(npc.getId());
			if ((territoryId >= 81) && (territoryId <= 89))
			{
				for (L2PcInstance pl : L2World.getInstance().getPlayers())
				{
					if (pl.getSiegeSide() == territoryId)
					{
						QuestState st = pl.getQuestState(getName());
						if (st == null)
						{
							st = newQuestState(pl);
						}
						if (!st.isStarted())
						{
							st.setState(State.STARTED, false);
							st.setCond(1);
						}
					}
				}
			}
		}
		return super.onAttack(npc, player, damage, isSummon);
	}
	
	@Override
	public String onDeath(L2Character killer, L2Character victim, QuestState qs)
	{
		if ((killer == victim) || !(victim instanceof L2PcInstance) || (victim.getLevel() < 61))
		{
			return "";
		}
		L2PcInstance actingPlayer = killer.getActingPlayer();
		if ((actingPlayer != null) && (qs.getPlayer() != null))
		{
			if (actingPlayer.getParty() != null)
			{
				for (L2PcInstance pl : actingPlayer.getParty().getMembers())
				{
					if ((pl.getSiegeSide() == qs.getPlayer().getSiegeSide()) || (pl.getSiegeSide() == 0) || !Util.checkIfInRange(2000, killer, pl, false))
					{
						continue;
					}
					if (pl == actingPlayer)
					{
						handleStepsForHonor(actingPlayer);
						handleBecomeMercenaryQuest(actingPlayer, false);
					}
					handleKillTheQuest(pl);
				}
			}
			else if ((actingPlayer.getSiegeSide() != qs.getPlayer().getSiegeSide()) && (actingPlayer.getSiegeSide() > 0))
			{
				handleKillTheQuest(actingPlayer);
				handleStepsForHonor(actingPlayer);
				handleBecomeMercenaryQuest(actingPlayer, false);
			}
			TerritoryWarManager.getInstance().giveTWPoint(actingPlayer, qs.getPlayer().getSiegeSide(), 1);
		}
		return "";
	}
	
	@Override
	public String onEnterWorld(L2PcInstance player)
	{
		int territoryId = TerritoryWarManager.getInstance().getRegisteredTerritoryId(player);
		if (territoryId > 0)
		{
			// register Territory Quest
			TerritoryWarSuperClass territoryQuest = _forTheSakeScripts.get(territoryId);
			QuestState st = player.getQuestState(territoryQuest.getName());
			if (st == null)
			{
				st = territoryQuest.newQuestState(player);
			}
			st.setState(State.STARTED, false);
			st.setCond(1);
			
			// register player on Death
			if (player.getLevel() >= 61)
			{
				TerritoryWarSuperClass killthe = _killTheScripts.get(player.getClassId().getId());
				if (killthe != null)
				{
					st = player.getQuestState(killthe.getName());
					if (st == null)
					{
						st = killthe.newQuestState(player);
					}
					player.addNotifyQuestOfDeath(st);
				}
				else
				{
					_log.warning("TerritoryWar: Missing Kill the quest for player " + player.getName() + " whose class id: " + player.getClassId().getId());
				}
			}
		}
		return null;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		TerritoryWarManager manager = TerritoryWarManager.getInstance();
		if (npc.getId() == CATAPULT_ID)
		{
			manager.territoryCatapultDestroyed(TERRITORY_ID - 80);
			manager.giveTWPoint(killer, TERRITORY_ID, 4);
			manager.announceToParticipants(new ExShowScreenMessage(npcString[0], 2, 10000), 135000, 13500);
			handleBecomeMercenaryQuest(killer, true);
		}
		else if (Util.contains(LEADER_IDS, npc.getId()))
		{
			manager.giveTWPoint(killer, TERRITORY_ID, 3);
		}
		
		if ((killer.getSiegeSide() != TERRITORY_ID) && (TerritoryWarManager.getInstance().getTerritory(killer.getSiegeSide() - 80) != null))
		{
			manager.getTerritory(killer.getSiegeSide() - 80).getQuestDone()[0]++;
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onSkillSee(L2Npc npc, L2PcInstance caster, L2Skill skill, L2Object[] targets, boolean isSummon)
	{
		if (Util.contains(targets, npc))
		{
			if (skill.getId() == 845)
			{
				if (TerritoryWarManager.getInstance().getHQForClan(caster.getClan()) != npc)
				{
					return super.onSkillSee(npc, caster, skill, targets, isSummon);
				}
				npc.deleteMe();
				TerritoryWarManager.getInstance().setHQForClan(caster.getClan(), null);
			}
			else if (skill.getId() == 847)
			{
				if (TerritoryWarManager.getInstance().getHQForTerritory(caster.getSiegeSide()) != npc)
				{
					return super.onSkillSee(npc, caster, skill, targets, isSummon);
				}
				TerritoryWard ward = TerritoryWarManager.getInstance().getTerritoryWard(caster);
				if (ward == null)
				{
					return super.onSkillSee(npc, caster, skill, targets, isSummon);
				}
				if ((caster.getSiegeSide() - 80) == ward.getOwnerCastleId())
				{
					for (TerritoryNPCSpawn wardSpawn : TerritoryWarManager.getInstance().getTerritory(ward.getOwnerCastleId()).getOwnedWard())
					{
						if (wardSpawn.getId() == ward.getTerritoryId())
						{
							wardSpawn.setNPC(wardSpawn.getNpc().getSpawn().doSpawn());
							ward.unSpawnMe();
							ward.setNpc(wardSpawn.getNpc());
						}
					}
				}
				else
				{
					ward.unSpawnMe();
					ward.setNpc(TerritoryWarManager.getInstance().addTerritoryWard(ward.getTerritoryId(), caster.getSiegeSide() - 80, ward.getOwnerCastleId(), true));
					ward.setOwnerCastleId(caster.getSiegeSide() - 80);
					TerritoryWarManager.getInstance().getTerritory(caster.getSiegeSide() - 80).getQuestDone()[1]++;
				}
			}
		}
		return super.onSkillSee(npc, caster, skill, targets, isSummon);
	}
	
	// Used to register NPCs "For the Sake of the Territory ..." quests
	public void registerKillIds()
	{
		addKillId(CATAPULT_ID);
		for (int mobid : LEADER_IDS)
		{
			addKillId(mobid);
		}
		for (int mobid : GUARD_IDS)
		{
			addKillId(mobid);
		}
	}
	
	@Override
	public void setOnEnterWorld(boolean val)
	{
		super.setOnEnterWorld(val);
		
		for (L2PcInstance player : L2World.getInstance().getPlayers())
		{
			if (player.getSiegeSide() > 0)
			{
				TerritoryWarSuperClass territoryQuest = _forTheSakeScripts.get(player.getSiegeSide());
				if (territoryQuest == null)
				{
					continue;
				}
				
				QuestState st = player.hasQuestState(territoryQuest.getName()) ? player.getQuestState(territoryQuest.getName()) : territoryQuest.newQuestState(player);
				if (val)
				{
					st.setState(State.STARTED, false);
					st.setCond(1);
					// register player on Death
					if (player.getLevel() >= 61)
					{
						TerritoryWarSuperClass killthe = _killTheScripts.get(player.getClassId().getId());
						if (killthe != null)
						{
							st = player.getQuestState(killthe.getName());
							if (st == null)
							{
								st = killthe.newQuestState(player);
							}
							player.addNotifyQuestOfDeath(st);
						}
						else
						{
							_log.warning("TerritoryWar: Missing Kill the quest for player " + player.getName() + " whose class id: " + player.getClassId().getId());
						}
					}
				}
				else
				{
					st.exitQuest(false);
					for (Quest q : _protectTheScripts.values())
					{
						st = player.getQuestState(q.getName());
						if (st != null)
						{
							st.exitQuest(false);
						}
					}
					// unregister player on Death
					TerritoryWarSuperClass killthe = _killTheScripts.get(player.getClassIndex());
					if (killthe != null)
					{
						st = player.getQuestState(killthe.getName());
						if (st != null)
						{
							player.removeNotifyQuestOfDeath(st);
						}
					}
				}
			}
		}
	}
	
	private static void handleBecomeMercenaryQuest(L2PcInstance player, boolean catapult)
	{
		int enemyCount = 10, catapultCount = 1;
		QuestState st = player.getQuestState(Q00147_PathtoBecominganEliteMercenary.class.getSimpleName());
		if ((st != null) && st.isCompleted())
		{
			st = player.getQuestState(Q00148_PathtoBecominganExaltedMercenary.class.getSimpleName());
			enemyCount = 30;
			catapultCount = 2;
		}
		
		if ((st != null) && st.isStarted())
		{
			final int cond = st.getCond();
			if (catapult)
			{
				if ((cond == 1) || (cond == 2))
				{
					final int count = st.getInt("catapult") + 1;
					st.set("catapult", String.valueOf(count));
					if (count >= catapultCount)
					{
						st.setCond((cond == 1) ? 3 : 4);
					}
				}
			}
			else if ((cond == 1) || (cond == 3))
			{
				final int kills = st.getInt("kills") + 1;
				st.set("kills", Integer.toString(kills));
				if (kills >= enemyCount)
				{
					st.setCond((cond == 1) ? 2 : 4);
				}
			}
		}
	}
	
	private static void handleStepsForHonor(L2PcInstance player)
	{
		final QuestState _sfh = player.getQuestState(Q00176_StepsForHonor.class.getSimpleName());
		if ((_sfh != null) && _sfh.isStarted())
		{
			final int cond = _sfh.getCond();
			if ((cond == 1) || (cond == 3) || (cond == 5) || (cond == 7))
			{
				final int kills = _sfh.getInt("kills") + 1;
				_sfh.set("kills", kills);
				if ((cond == 1) && (kills >= 9))
				{
					_sfh.setCond(2);
					_sfh.set("kills", "0");
				}
				else if ((cond == 3) && (kills >= 18))
				{
					_sfh.setCond(4);
					_sfh.set("kills", "0");
				}
				else if ((cond == 5) && (kills >= 27))
				{
					_sfh.setCond(6);
					_sfh.set("kills", "0");
				}
				else if ((cond == 7) && (kills >= 36))
				{
					_sfh.setCond(8);
					_sfh.unset("kills");
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		// initialize superclass
		new TerritoryWarSuperClass(-1, TerritoryWarSuperClass.class.getSimpleName(), "Territory War Superclass");
		
		// initialize subclasses
		// "For The Sake" quests
		TerritoryWarSuperClass gludio = new Q00717_ForTheSakeOfTheTerritoryGludio();
		_forTheSakeScripts.put(gludio.TERRITORY_ID, gludio);
		TerritoryWarSuperClass dion = new Q00718_ForTheSakeOfTheTerritoryDion();
		_forTheSakeScripts.put(dion.TERRITORY_ID, dion);
		TerritoryWarSuperClass giran = new Q00719_ForTheSakeOfTheTerritoryGiran();
		_forTheSakeScripts.put(giran.TERRITORY_ID, giran);
		TerritoryWarSuperClass oren = new Q00720_ForTheSakeOfTheTerritoryOren();
		_forTheSakeScripts.put(oren.TERRITORY_ID, oren);
		TerritoryWarSuperClass aden = new Q00721_ForTheSakeOfTheTerritoryAden();
		_forTheSakeScripts.put(aden.TERRITORY_ID, aden);
		TerritoryWarSuperClass innadril = new Q00722_ForTheSakeOfTheTerritoryInnadril();
		_forTheSakeScripts.put(innadril.TERRITORY_ID, innadril);
		TerritoryWarSuperClass goddard = new Q00723_ForTheSakeOfTheTerritoryGoddard();
		_forTheSakeScripts.put(goddard.TERRITORY_ID, goddard);
		TerritoryWarSuperClass rune = new Q00724_ForTheSakeOfTheTerritoryRune();
		_forTheSakeScripts.put(rune.TERRITORY_ID, rune);
		TerritoryWarSuperClass schuttgart = new Q00725_ForTheSakeOfTheTerritorySchuttgart();
		_forTheSakeScripts.put(schuttgart.TERRITORY_ID, schuttgart);
		// "Protect the" quests
		TerritoryWarSuperClass catapult = new Q00729_ProtectTheTerritoryCatapult();
		_protectTheScripts.put(catapult.getId(), catapult);
		TerritoryWarSuperClass supplies = new Q00730_ProtectTheSuppliesSafe();
		_protectTheScripts.put(supplies.getId(), supplies);
		TerritoryWarSuperClass military = new Q00731_ProtectTheMilitaryAssociationLeader();
		_protectTheScripts.put(military.getId(), military);
		TerritoryWarSuperClass religious = new Q00732_ProtectTheReligiousAssociationLeader();
		_protectTheScripts.put(religious.getId(), religious);
		TerritoryWarSuperClass economic = new Q00733_ProtectTheEconomicAssociationLeader();
		_protectTheScripts.put(economic.getId(), economic);
		// "Kill" quests
		TerritoryWarSuperClass knights = new Q00734_PierceThroughAShield();
		for (int i : knights.CLASS_IDS)
		{
			_killTheScripts.put(i, knights);
		}
		TerritoryWarSuperClass warriors = new Q00735_MakeSpearsDull();
		for (int i : warriors.CLASS_IDS)
		{
			_killTheScripts.put(i, warriors);
		}
		TerritoryWarSuperClass wizards = new Q00736_WeakenTheMagic();
		for (int i : wizards.CLASS_IDS)
		{
			_killTheScripts.put(i, wizards);
		}
		TerritoryWarSuperClass priests = new Q00737_DenyBlessings();
		for (int i : priests.CLASS_IDS)
		{
			_killTheScripts.put(i, priests);
		}
		TerritoryWarSuperClass keys = new Q00738_DestroyKeyTargets();
		for (int i : keys.CLASS_IDS)
		{
			_killTheScripts.put(i, keys);
		}
	}
}
