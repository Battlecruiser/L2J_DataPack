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
package quests.TerritoryWarScripts;

import java.util.Calendar;
import java.util.StringTokenizer;

import quests.Q00147_PathtoBecominganEliteMercenary.Q00147_PathtoBecominganEliteMercenary;

import com.l2jserver.gameserver.SevenSigns;
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
	
	public static String qn = "TerritoryWarSuperClass";
	
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
			Calendar startTWDate = Calendar.getInstance();
			if (loadGlobalQuestVar("nextTWStartDate").equalsIgnoreCase(""))
			{
				startTWDate.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
				startTWDate.set(Calendar.HOUR_OF_DAY, 20);
				startTWDate.set(Calendar.MINUTE, 0);
				startTWDate.set(Calendar.SECOND, 0);
				if (startTWDate.getTimeInMillis() < System.currentTimeMillis())
				{
					startTWDate.add(Calendar.DAY_OF_MONTH, 7);
				}
				if (!SevenSigns.getInstance().isDateInSealValidPeriod(startTWDate))
				{
					startTWDate.add(Calendar.DAY_OF_MONTH, 7);
				}
				saveGlobalQuestVar("nextTWStartDate", String.valueOf(startTWDate.getTimeInMillis()));
			}
			else
			{
				startTWDate.setTimeInMillis(Long.parseLong(loadGlobalQuestVar("nextTWStartDate")));
				if ((startTWDate.getTimeInMillis() < System.currentTimeMillis()) && SevenSigns.getInstance().isSealValidationPeriod() && (SevenSigns.getInstance().getMilliToPeriodChange() > 172800000))
				{
					startTWDate.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
					startTWDate.set(Calendar.HOUR_OF_DAY, 20);
					startTWDate.set(Calendar.MINUTE, 0);
					startTWDate.set(Calendar.SECOND, 0);
					if (startTWDate.getTimeInMillis() < System.currentTimeMillis())
					{
						startTWDate.add(Calendar.DAY_OF_MONTH, 7);
					}
					if (!SevenSigns.getInstance().isDateInSealValidPeriod(startTWDate))
					{
						startTWDate.add(Calendar.DAY_OF_MONTH, 7);
					}
					saveGlobalQuestVar("nextTWStartDate", String.valueOf(startTWDate.getTimeInMillis()));
				}
			}
			TerritoryWarManager.getInstance().setTWStartTimeInMillis(startTWDate.getTimeInMillis());
			_log.info("Next TerritoryWarTime: " + startTWDate.getTime());
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
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if ((npc != null) || (player != null))
		{
			return null;
		}
		StringTokenizer st = new StringTokenizer(event, " ");
		event = st.nextToken(); // Get actual command
		if (event.equalsIgnoreCase("setNextTWDate"))
		{
			Calendar startTWDate = Calendar.getInstance();
			startTWDate.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
			startTWDate.set(Calendar.HOUR_OF_DAY, 20);
			startTWDate.set(Calendar.MINUTE, 0);
			startTWDate.set(Calendar.SECOND, 0);
			if (startTWDate.getTimeInMillis() < System.currentTimeMillis())
			{
				startTWDate.add(Calendar.DAY_OF_MONTH, 7);
			}
			if (!SevenSigns.getInstance().isDateInSealValidPeriod(startTWDate))
			{
				startTWDate.add(Calendar.DAY_OF_MONTH, 7);
			}
			saveGlobalQuestVar("nextTWStartDate", String.valueOf(startTWDate.getTimeInMillis()));
			TerritoryWarManager.getInstance().setTWStartTimeInMillis(startTWDate.getTimeInMillis());
			_log.info("Next TerritoryWarTime: " + startTWDate.getTime());
		}
		else if (event.equalsIgnoreCase("setTWDate") && st.hasMoreTokens())
		{
			Calendar startTWDate = Calendar.getInstance();
			startTWDate.setTimeInMillis(Long.parseLong(st.nextToken()));
			saveGlobalQuestVar("nextTWStartDate", String.valueOf(startTWDate.getTimeInMillis()));
			TerritoryWarManager.getInstance().setTWStartTimeInMillis(startTWDate.getTimeInMillis());
		}
		return null;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance player, int damage, boolean isSummon)
	{
		if ((npc.getCurrentHp() == npc.getMaxHp()) && Util.contains(NPC_IDS, npc.getNpcId()))
		{
			int territoryId = getTerritoryIdForThisNPCId(npc.getNpcId());
			if ((territoryId >= 81) && (territoryId <= 89))
			{
				for (L2PcInstance pl : L2World.getInstance().getAllPlayersArray())
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
							st.setCond(1);
							st.setState(State.STARTED, false);
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
		if (npc.getNpcId() == CATAPULT_ID)
		{
			manager.territoryCatapultDestroyed(TERRITORY_ID - 80);
			manager.giveTWPoint(killer, TERRITORY_ID, 4);
			manager.announceToParticipants(new ExShowScreenMessage(npcString[0], 2, 10000), 135000, 13500);
			handleBecomeMercenaryQuest(killer, true);
		}
		else if (Util.contains(LEADER_IDS, npc.getNpcId()))
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
						if (wardSpawn.getNpcId() == ward.getTerritoryId())
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
		
		for (L2PcInstance player : L2World.getInstance().getAllPlayersArray())
		{
			if (player.getSiegeSide() > 0)
			{
				TerritoryWarSuperClass territoryQuest = _forTheSakeScripts.get(player.getSiegeSide());
				if (territoryQuest == null)
				{
					continue;
				}
				QuestState st = player.getQuestState(territoryQuest.getName());
				if (st == null)
				{
					st = territoryQuest.newQuestState(player);
				}
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
					st.setState(State.COMPLETED, false);
					st.exitQuest(false);
					for (Quest q : _protectTheScripts.values())
					{
						st = player.getQuestState(q.getName());
						if (st != null)
						{
							st.setState(State.COMPLETED, false);
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
		QuestState st = player.getQuestState(Q00147_PathtoBecominganEliteMercenary.class.getSimpleName());
		if ((st != null) && st.isStarted())
		{
			if (catapult)
			{
				if (st.isCond(2))
				{
					st.setCond(4);
				}
				else if (st.isCond(1))
				{
					st.setCond(3);
				}
			}
			else
			{
				if (st.isCond(1) || st.isCond(3))
				{
					// Get
					int _kills = st.getInt("kills");
					// Increase
					_kills++;
					// Save
					st.set("kills", String.valueOf(_kills));
					// Check
					if (_kills >= 10)
					{
						if (st.isCond(1))
						{
							st.setCond(2);
						}
						else if (st.isCond(3))
						{
							st.setCond(4);
						}
					}
				}
			}
		}
	}
	
	private static void handleStepsForHonor(L2PcInstance player)
	{
		int kills = 0;
		int cond = 0;
		// Additional Handle for Quest
		QuestState _sfh = player.getQuestState("176_StepsForHonor");
		if ((_sfh != null) && _sfh.isStarted())
		{
			cond = _sfh.getCond();
			if ((cond == 1) || (cond == 3) || (cond == 5) || (cond == 7))
			{
				// Get kills
				kills = _sfh.getInt("kills");
				// Increase
				kills++;
				// Save
				_sfh.set("kills", String.valueOf(kills));
				// Check
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
		new TerritoryWarSuperClass(-1, qn, "Territory_War");
		
		// initialize subclasses
		// "For The Sake" quests
		TerritoryWarSuperClass gludio = new TheTerritoryGludio();
		_forTheSakeScripts.put(gludio.TERRITORY_ID, gludio);
		TerritoryWarSuperClass dion = new TheTerritoryDion();
		_forTheSakeScripts.put(dion.TERRITORY_ID, dion);
		TerritoryWarSuperClass giran = new TheTerritoryGiran();
		_forTheSakeScripts.put(giran.TERRITORY_ID, giran);
		TerritoryWarSuperClass oren = new TheTerritoryOren();
		_forTheSakeScripts.put(oren.TERRITORY_ID, oren);
		TerritoryWarSuperClass aden = new TheTerritoryAden();
		_forTheSakeScripts.put(aden.TERRITORY_ID, aden);
		TerritoryWarSuperClass innadril = new TheTerritoryInnadril();
		_forTheSakeScripts.put(innadril.TERRITORY_ID, innadril);
		TerritoryWarSuperClass goddard = new TheTerritoryGoddard();
		_forTheSakeScripts.put(goddard.TERRITORY_ID, goddard);
		TerritoryWarSuperClass rune = new TheTerritoryRune();
		_forTheSakeScripts.put(rune.TERRITORY_ID, rune);
		TerritoryWarSuperClass schuttgart = new TheTerritorySchuttgart();
		_forTheSakeScripts.put(schuttgart.TERRITORY_ID, schuttgart);
		// "Protect the" quests
		TerritoryWarSuperClass catapult = new ProtectTheCatapult();
		_protectTheScripts.put(catapult.getQuestIntId(), catapult);
		TerritoryWarSuperClass military = new ProtectTheMilitary();
		_protectTheScripts.put(military.getQuestIntId(), military);
		TerritoryWarSuperClass religious = new ProtectTheReligious();
		_protectTheScripts.put(religious.getQuestIntId(), religious);
		TerritoryWarSuperClass supplies = new ProtectTheSupplies();
		_protectTheScripts.put(supplies.getQuestIntId(), supplies);
		TerritoryWarSuperClass economic = new ProtectTheEconomic();
		_protectTheScripts.put(economic.getQuestIntId(), economic);
		// "Kill the" quests
		TerritoryWarSuperClass knights = new KillTheKnights();
		for (int i : knights.CLASS_IDS)
		{
			_killTheScripts.put(i, knights);
		}
		TerritoryWarSuperClass warriors = new KillTheWarriors();
		for (int i : warriors.CLASS_IDS)
		{
			_killTheScripts.put(i, warriors);
		}
		TerritoryWarSuperClass wizards = new KillTheWizards();
		for (int i : wizards.CLASS_IDS)
		{
			_killTheScripts.put(i, wizards);
		}
		TerritoryWarSuperClass priests = new KillThePriests();
		for (int i : priests.CLASS_IDS)
		{
			_killTheScripts.put(i, priests);
		}
		TerritoryWarSuperClass keys = new KillTheKeyTargets();
		for (int i : keys.CLASS_IDS)
		{
			_killTheScripts.put(i, keys);
		}
	}
}
