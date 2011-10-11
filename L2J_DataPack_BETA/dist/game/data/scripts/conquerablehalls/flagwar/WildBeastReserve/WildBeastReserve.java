/**
 * 
 */
package conquerablehalls.flagwar.WildBeastReserve;

import com.l2jserver.gameserver.instancemanager.ZoneManager;
import com.l2jserver.gameserver.model.L2CharPosition;
import com.l2jserver.gameserver.model.zone.type.L2ResidenceHallTeleportZone;

import conquerablehalls.flagwar.FlagWar;

/**
 * @author BiggBoss
 */
public final class WildBeastReserve extends FlagWar
{
	static
	{
		qn = "WildBeastReserve";
		
		FLAG_RED					= 35607;	// White flag
		FLAG_YELLOW					= 35608;	// Red flag
		FLAG_GREEN					= 35609;	// Blue flag
		FLAG_BLUE					= 35610;	// Green flag
		FLAG_PURPLE					= 35611;	// Black flag
		
		ALLY_1						= 35618;
		ALLY_2						= 35619;
		ALLY_3						= 35620;
		ALLY_4						= 35621;
		ALLY_5						= 35622;
		
		TELEPORT_1 					= 35613;
		
		MESSENGER 					= 35627;
		
		FLAG_COORDS[0] = new int[]{59489,-91704,-1359};
		FLAG_COORDS[1] = new int[]{57448,-90896,-1359};
		FLAG_COORDS[2] = new int[]{56013,-92601,-1358};
		FLAG_COORDS[3] = new int[]{57270,-94211,-1360};
		FLAG_COORDS[4] = new int[]{59478,-94270,-1355};
		
		ALLY_COORDS[0] = new int[]{59342,-91632,-1360};
		ALLY_COORDS[1] = new int[]{57325,-91094,-1354};
		ALLY_COORDS[2] = new int[]{56184,-92742,-1358};
		ALLY_COORDS[3] = new int[]{57511,-94009,-1368};
		ALLY_COORDS[4] = new int[]{57537,-93921,-1360};
		
		java.util.Collection<L2ResidenceHallTeleportZone> zoneList 
		= ZoneManager.getInstance().getAllZones(L2ResidenceHallTeleportZone.class);
		
		for(L2ResidenceHallTeleportZone teleZone : zoneList)
		{
			if(teleZone.getResidenceId() != BEAST_FARM)
				continue;
			
			int id = teleZone.getResidenceZoneId();
			
			if(id < 0 || id >= 5)						// Shouldnt happen
				continue;
			
			TELE_ZONES[id] = teleZone;
		}
		
		QUEST_REWARD = 0;
		CENTER = new L2CharPosition(57762,-92696,-1359,0);
	}

	public WildBeastReserve(int questId, String name, String descr, int hallId)
	{
		super(questId, name, descr, hallId);
	}

	@Override
	public String getFlagHtml(int flag)
	{
		String result = null;
		
		switch(flag)
		{
			case 35607:
				result = "messenger_flag1.htm";
				break;
			case 35608:
				result = "messenger_flag2.htm";
				break;
			case 35609:
				result = "messenger_flag3.htm";
				break;
			case 35610:
				result = "messenger_flag4.htm";
				break;
			case 35611:
				result = "messenger_flag5.htm";
				break;
		}
		
		return result;
	}

	@Override
	public String getAllyHtml(int ally)
	{
		String result = null;
		
		switch(ally)
		{
			case 35618:
				result = "messenger_ally1result.htm";
				break;
			case 35619:
				result = "messenger_ally2result.htm";
				break;
			case 35620:
				result = "messenger_ally3result.htm";
				break;
			case 35621:
				result = "messenger_ally4result.htm";
				break;
			case 35622:
				result = "messenger_ally5result.htm";
				break;
		}
		
		return result;
	}
	
	@Override
	public boolean canPayRegistration()
	{
		return false;
	}
	
	public static void main(String[] args)
	{
		new WildBeastReserve(-1, qn, "conquerablehalls/flagwar", BEAST_FARM);
	}
}
