/**
 * 
 */
package conquerablehalls.FortressOfResistance;

import gnu.trove.TIntLongHashMap;

import com.l2jserver.gameserver.cache.HtmCache;
import com.l2jserver.gameserver.datatables.ClanTable;
import com.l2jserver.gameserver.datatables.NpcTable;
import com.l2jserver.gameserver.model.L2Clan;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.clanhall.ClanHallSiegeEngine;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * @author BiggBoss
 * Fortress of Resistance clan hall siege Script
 */
public final class FortressOfResistance extends ClanHallSiegeEngine
{
	private static final String qn = "FortressOfResistance";
	
	private final int MESSENGER = 35382;
	private final int BLOODY_LORD_NURKA = 35375;
	
	private final int[][] NURKA_COORDS =
	{
		{45109,112124,-1900},	// 30%
		{47653,110816,-2110},	// 40%
		{47247,109396,-2000}	// 30%
	};
	
	private L2Spawn _nurka; 
	private TIntLongHashMap _damageToNurka = new TIntLongHashMap();
	private NpcHtmlMessage _messengerMsg;
	
	/**
	 * @param questId
	 * @param name
	 * @param descr
	 */
	public FortressOfResistance(int questId, String name, String descr, final int hallId)
	{
		super(questId, name, descr, hallId);
		addFirstTalkId(MESSENGER);
		addKillId(BLOODY_LORD_NURKA);
		addAttackId(BLOODY_LORD_NURKA);
		buildMessengerMessage();
		
		try
		{
			_nurka = new L2Spawn(NpcTable.getInstance().getTemplate(BLOODY_LORD_NURKA));
			_nurka.setAmount(1);
			_nurka.setRespawnDelay(10800);
			
			int[] coords = NURKA_COORDS[0];
			/*
			int chance = Rnd.get(100) + 1;
			if(chance <= 30)
				coords = NURKA_COORDS[0];
			else if(chance > 30 && chance <= 70)
				coords = NURKA_COORDS[1];
			else
				coords = NURKA_COORDS[2];
			*/
			
			_nurka.setLocx(coords[0]);
			_nurka.setLocy(coords[1]);
			_nurka.setLocz(coords[2]);
		}
		catch(Exception e)
		{
			_log.warning(getName()+": Couldnt set the Bloody Lord Nurka spawn");
			e.printStackTrace();
		}
	}
	
	private final void buildMessengerMessage()
	{
		String html = HtmCache.getInstance().getHtm(null, "data/scripts/conquerablehalls/FortressOfResistance/partisan_ordery_brakel001.htm");
		if(html != null)
		{
			_messengerMsg = new NpcHtmlMessage(5);
			_messengerMsg.setHtml(html);
			_messengerMsg.replace("%nextSiege%", _hall.getSiegeDate().getTime().toString());
		}
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		player.sendPacket(_messengerMsg);
		return null;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance player, int damage, boolean isPet)
	{
		if(!_hall.isInSiege())
			return null;
		
		int clanId = player.getClanId();
		if(clanId > 0)
		{
			long clanDmg = _damageToNurka.get(clanId) + damage;
			_damageToNurka.put(clanId, clanDmg);
			
		}
		return null;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		if(!_hall.isInSiege())
			return null;
		
		_missionAccomplished = true;
		
		synchronized(this)
		{
			npc.getSpawn().stopRespawn();
			npc.deleteMe();
			cancelSiegeTask();
			endSiege();
		}
		return null;
	}
	
	@Override
	public L2Clan getWinner()
	{
		int winnerId = 0;
		long counter = 0;
		for(int i : _damageToNurka.keys())
		{	
			long dam = _damageToNurka.get(i);
			if(dam > counter)
			{
				winnerId = i;
				counter = dam;
			}
		}
		return ClanTable.getInstance().getClan(winnerId);
	}
	
	@Override
	public void onSiegeStarts()
	{
		_nurka.init();
	}
	
	@Override
	public void onSiegeEnds()
	{
		buildMessengerMessage();
	}
		
	public static void main(String[] args)
	{
		new FortressOfResistance(-1, qn, "conquerablehalls", FORTRESS_RESSISTANCE);
	}

}
