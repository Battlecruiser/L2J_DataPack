package instances.Pailaka;

import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.instancemanager.InstanceManager.InstanceWorld;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.Instance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.model.zone.L2ZoneType;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.util.Rnd;

public class PailakaSongOfIceAndFire extends Quest
{
	private static final String qn = "128_PailakaSongOfIceAndFire";

	private static final int MIN_LEVEL = 36;
	private static final int MAX_LEVEL = 42;
	private static final int EXIT_TIME = 5;
	private static final int INSTANCE_ID = 43;
	private static final int[] TELEPORT = { -52875, 188232, -4696 };
	private static final int ZONE = 20108;

	private static final int ADLER1 = 32497;
	private static final int ADLER2 = 32510;
	private static final int SINAI = 32500;
	private static final int INSPECTOR = 32507;
	private static final int[] NPCS = { ADLER1, ADLER2, SINAI, INSPECTOR };

	private static final int HILLAS = 18610;
	private static final int PAPION = 18609;
	private static final int KINSUS = 18608;
	private static final int GARGOS = 18607;
	private static final int ADIANTUM = 18620;
	private static final int BLOOM = 18616;
	private static final int BOTTLE = 32492;
	private static final int BRAZIER = 32493;
	private static final int[] MONSTERS =
	{ HILLAS, PAPION, KINSUS, GARGOS, ADIANTUM, BLOOM, BOTTLE, BRAZIER,
		18611, 18612, 18613, 18614, 18615 };

	private static final int SWORD = 13034;
	private static final int ENH_SWORD1 = 13035;
	private static final int ENH_SWORD2 = 13036;
	private static final int BOOK1 = 13130;
	private static final int BOOK2 = 13131;
	private static final int BOOK3 = 13132;
	private static final int BOOK4 = 13133;
	private static final int BOOK5 = 13134;
	private static final int BOOK6 = 13135;
	private static final int BOOK7 = 13136;
	private static final int WATER_ESSENCE = 13038;
	private static final int FIRE_ESSENCE = 13039;
	private static final int SHIELD_POTION = 13032;
	private static final int HEAL_POTION = 13033;
	private static final int FIRE_ENHANCER = 13040;
	private static final int WATER_ENHANCER = 13041;
	private static final int[] ITEMS = { SWORD, ENH_SWORD1, ENH_SWORD2, BOOK1, BOOK2, BOOK3, BOOK4, BOOK5, BOOK6, BOOK7,
		WATER_ESSENCE, FIRE_ESSENCE, SHIELD_POTION, HEAL_POTION, FIRE_ENHANCER, WATER_ENHANCER };

	private static final int[][] DROPLIST =
	{
		// must be sorted by npcId !
		// npcId, itemId, chance
		{ BLOOM,   SHIELD_POTION, 30 },
		{ BLOOM,     HEAL_POTION, 80 },
		{ BOTTLE,  SHIELD_POTION, 10 },
		{ BOTTLE, WATER_ENHANCER, 40 },
		{ BOTTLE,    HEAL_POTION, 80 },
		{ BRAZIER, SHIELD_POTION, 10 },
		{ BRAZIER, FIRE_ENHANCER, 40 },
		{ BRAZIER,   HEAL_POTION, 80 }
	};

	private static final int[][] HP_HERBS_DROPLIST = 
	{
		// itemId, count, chance
		{ 8602, 1, 10 }, { 8601, 1, 40 }, { 8600, 1, 70 }
	};

	private static final int[][] MP_HERBS_DROPLIST =
	{
		// itemId, count, chance
		{ 8605, 1, 10 }, { 8604, 1, 40 }, { 8603, 1, 70 }
	};

	private static final int[] REWARDS = { 13294, 13293, 13129 };

	private static final String EMPTY = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>";

	private static final void dropHerb(L2Npc mob, L2PcInstance player, int[][] drop)
	{
		final int chance = Rnd.get(100);
		for (int i = 0; i < drop.length; i++)
		{
			if (chance < drop[i][2])
			{
				((L2MonsterInstance)mob).dropItem(player, drop[i][0], drop[i][1]);
				return;
			}
		}
	}

	private static final void dropItem(L2Npc mob, L2PcInstance player)
	{
		final int npcId = mob.getNpcId();
		final int chance = Rnd.get(100);
		for (int i = 0; i < DROPLIST.length; i++)
		{
			int[] drop = DROPLIST[i];
			if (npcId == drop[0])
			{
				if (chance < drop[2])
				{
					((L2MonsterInstance)mob).dropItem(player, drop[1], Rnd.get(1,6));
					return;
				}
			}
			if (npcId < drop[0])
				return; // not found
		}
	}

	private static final void teleportPlayer(L2PcInstance player, int[] coords, int instanceId)
	{
		player.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		player.setInstanceId(instanceId);
		player.teleToLocation(coords[0], coords[1], coords[2], true);
	}

	private final synchronized void enterInstance(L2PcInstance player)
	{
		//check for existing instances for this player
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		if (world != null)
		{
			if (world.templateId != INSTANCE_ID)
			{
				player.sendPacket(new SystemMessage(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER));
				return;
			}
			Instance inst = InstanceManager.getInstance().getInstance(world.instanceId);
			if (inst != null)
				teleportPlayer(player, TELEPORT, world.instanceId);
			return;
		}
		//New instance
		else
		{
			final int instanceId = InstanceManager.getInstance().createDynamicInstance("PailakaSongOfIceAndFire.xml");

			world = InstanceManager.getInstance().new InstanceWorld();
			world.instanceId = instanceId;
			world.templateId = INSTANCE_ID;
			InstanceManager.getInstance().addWorld(world);

			world.allowed.add(player.getObjectId());
			teleportPlayer(player, TELEPORT, instanceId);
		}
		
	}

	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(qn);
		if (st == null)
			return EMPTY;

		final int cond = st.getInt("cond");
		if (event.equalsIgnoreCase("enter"))
		{
			enterInstance(player);
			return null;
		}
		else if (event.equalsIgnoreCase("32497-03.htm"))
		{
			if (cond == 0)
			{
				st.set("cond","1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
			}
		}
		else if (event.equalsIgnoreCase("32500-06.htm"))
		{
			if (cond == 1)
			{
				st.set("cond","2");
				st.playSound("ItemSound.quest_itemget");
				st.giveItems(SWORD, 1);
				st.giveItems(BOOK1, 1);
			}
		}
		else if (event.equalsIgnoreCase("32507-04.htm"))
		{
			if (cond == 3)
			{
				st.set("cond","4");
				st.playSound("ItemSound.quest_middle");
				st.takeItems(SWORD, -1);
				st.takeItems(WATER_ESSENCE, -1);
				st.takeItems(BOOK2, -1);
				st.giveItems(BOOK3, 1);
				st.giveItems(ENH_SWORD1, 1);
			}
		}
		else if (event.equalsIgnoreCase("32507-08.htm"))
		{
			if (cond == 6)
			{
				st.set("cond","7");
				st.playSound("ItemSound.quest_itemget");
				st.takeItems(ENH_SWORD1, -1);
				st.takeItems(BOOK5, -1);
				st.takeItems(FIRE_ESSENCE, -1);
				st.giveItems(ENH_SWORD2, 1);
				st.giveItems(BOOK6, 1);
			}
		}
		else if (event.equalsIgnoreCase("32510-02.htm"))
		{
			st.unset("cond");
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(false);

			Instance inst = InstanceManager.getInstance().getInstance(npc.getInstanceId());
			inst.setDuration(EXIT_TIME * 60000);
			inst.setEmptyDestroyTime(0);

			if (inst.containsPlayer(player.getObjectId()))
			{
				player.setVitalityPoints(20000, true);
				st.addExpAndSp(810000, 50000);
				for (int id : REWARDS)
					st.giveItems(id, 1);
			}
		}
		return event;
	}

	@Override
	public final String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		return npc.getNpcId() + ".htm";
	}

	@Override
	public final String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(qn);
		if (st == null)
			return EMPTY;

		final int cond = st.getInt("cond");
		switch (npc.getNpcId())
		{
			case ADLER1:
				switch (st.getState())
				{
					case State.CREATED:
						if (player.getLevel() < MIN_LEVEL)
							return "32497-05.htm";
						if (player.getLevel() > MAX_LEVEL)
							return "32497-06.htm";
						return "32497-01.htm";
					case State.STARTED:
						if (cond > 1)
							return "32497-00.htm";
						else
							return "32497-03.htm";
					case State.COMPLETED:
						return "32497-07.htm";
					default:
						return "32497-01.htm";
				}
			case SINAI:
				if (cond > 1)
					return "32500-00.htm";
				else
					return "32500-01.htm";
			case INSPECTOR:
				switch (st.getInt("cond"))
				{
					case 1:
						return "32507-01.htm";
					case 2:
						return "32507-02.htm";
					case 3:
						return "32507-03.htm";
					case 4:
					case 5:
						return "32507-05.htm";
					case 6:
						return "32507-06.htm";
					default:
						return "32507-09.htm";
				}
			case ADLER2:
				if (st.getState() == State.COMPLETED)
					return "32510-00.htm";
				else if (cond == 9)
					return "32510-01.htm";
		}
		return EMPTY;
	}

	@Override
	public final String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet)
	{
		if (!npc.isDead())
			npc.doDie(attacker);

		return super.onAttack(npc, attacker, damage, isPet);
	}

	@Override
	public final String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = player.getQuestState(qn);
		if (st == null || st.getState() != State.STARTED)
			return null;

		final int cond = st.getInt("cond");
		switch (npc.getNpcId())
		{
			case HILLAS:
				if (cond == 2)
				{
					st.set("cond", "3");
					st.playSound("ItemSound.quest_itemget");
					st.takeItems(BOOK1, -1);
					st.giveItems(BOOK2, 1);
					st.giveItems(WATER_ESSENCE, 1);
				}
				addSpawn(PAPION, -53903, 181484, -4555, 30456, false, 0, false, npc.getInstanceId());
				break;
			case PAPION:
				if (cond == 4)
				{
					st.takeItems(BOOK3, -1);
					st.giveItems(BOOK4, 1);
					st.set("cond", "5");
					st.playSound("ItemSound.quest_itemget");
				}
				addSpawn(KINSUS, -61415, 181418, -4818, 63852, false, 0, false, npc.getInstanceId());
				break;
			case KINSUS:
				if (cond == 5)
				{
					st.set("cond", "6");
					st.playSound("ItemSound.quest_itemget");
					st.takeItems(BOOK4, -1);
					st.giveItems(BOOK5, 1);
					st.giveItems(FIRE_ESSENCE, 1);
				}
				addSpawn(GARGOS, -61354, 183624, -4821, 63613, false, 0, false, npc.getInstanceId());
				break;
			case GARGOS:
				if (cond == 7)
				{
					st.set("cond", "8");
					st.playSound("ItemSound.quest_itemget");
					st.takeItems(BOOK6, -1);
					st.giveItems(BOOK7, 1);
				}
				addSpawn(ADIANTUM, -53297, 185027, -4617, 1512, false, 0, false, npc.getInstanceId());
				break;
			case ADIANTUM:
				if (cond == 8)
				{
					st.set("cond", "9");
					st.playSound("ItemSound.quest_middle");
					st.takeItems(BOOK7, -1);
					addSpawn(ADLER2, -53297, 185027, -4617, 33486, false, 0, false, npc.getInstanceId());
				}
				break;
			case BOTTLE:
			case BRAZIER:
			case BLOOM:
				dropItem(npc, player);
				break;
			default:
				// hardcoded herb drops
				dropHerb(npc, player, HP_HERBS_DROPLIST);
				dropHerb(npc, player, MP_HERBS_DROPLIST);
				break;
		}
		return super.onKill(npc, player, isPet);
	}

	@Override
	public String onExitZone(L2Character character, L2ZoneType zone)
	{
		if (character instanceof L2PcInstance
				&& !character.isDead()
				&& !character.isTeleporting()
				&& ((L2PcInstance)character).isOnline() > 0)
		{
			InstanceWorld world = InstanceManager.getInstance().getWorld(character.getInstanceId());
			if (world != null && world.templateId == INSTANCE_ID)
				ThreadPoolManager.getInstance().scheduleGeneral(new Teleport(character, world.instanceId), 1000);
		}
		return super.onExitZone(character,zone);
	}

	static final class Teleport implements Runnable
	{
		private final L2Character _char;
		private final int _instanceId;

		public Teleport(L2Character c, int id)
		{
			_char = c;
			_instanceId = id;
		}

		public void run()
		{
			try
			{
				teleportPlayer((L2PcInstance)_char, TELEPORT, _instanceId);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public PailakaSongOfIceAndFire(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(ADLER1);
		for (int npcId : NPCS)
		{
			addFirstTalkId(npcId);
			addTalkId(npcId);
		}
		addAttackId(BOTTLE);
		addAttackId(BRAZIER);
		for (int mobId : MONSTERS)
			addKillId(mobId);
		addExitZoneId(ZONE);
		questItemIds = ITEMS;
	}

	public static void main(String[] args)
	{
		new PailakaSongOfIceAndFire(128, qn, "Pailaka - Song of Ice and Fire");
	}
}