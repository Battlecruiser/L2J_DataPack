/**
 * 
 */
package conquerablehalls.flagwar.BanditStronghold;

import com.l2jserver.gameserver.instancemanager.ZoneManager;
import com.l2jserver.gameserver.model.L2CharPosition;
import com.l2jserver.gameserver.model.zone.type.L2ResidenceHallTeleportZone;

import conquerablehalls.flagwar.FlagWar;

/**
 * @author BiggBoss
 */
public final class BanditStronghold extends FlagWar
{
	static
	{
		qn = "BanditStronghold";
		
		FLAG_RED			= 35423;
		FLAG_YELLOW			= 35424;
		FLAG_GREEN			= 35425;
		FLAG_BLUE			= 35426;
		FLAG_PURPLE			= 35427;
		
		ALLY_1				= 35428;
		ALLY_2				= 35429;
		ALLY_3				= 35430;
		ALLY_4				= 35431;
		ALLY_5				= 35432;
		
		TELEPORT_1			= 35561;
				
		MESSENGER 			= 35437;

		FLAG_COORDS[0] = new int[]{83607,-17541,-1829};
		FLAG_COORDS[1] = new int[]{84965,-16140,-1829};
		FLAG_COORDS[2] = new int[]{83280,-15171,-1832};
		FLAG_COORDS[3] = new int[]{81629,-15004,-1831};
		FLAG_COORDS[4] = new int[]{81596,-16562,-1848};
		
		ALLY_COORDS[0] = new int[] {83409,-17362,-1829};
		ALLY_COORDS[1] = new int[] {85074,-16408,-1829};
		ALLY_COORDS[2] = new int[] {83366,-15371,-1835};
		ALLY_COORDS[3] = new int[] {81889,-14986,-1833};
		ALLY_COORDS[4] = new int[] {81431,-16272,-1856};
		
		java.util.Collection<L2ResidenceHallTeleportZone> zoneList 
		= ZoneManager.getInstance().getAllZones(L2ResidenceHallTeleportZone.class);
		
		for(L2ResidenceHallTeleportZone teleZone : zoneList)
		{
			if(teleZone.getResidenceId() != BANDIT_STRONGHOLD)
				continue;
			
			int id = teleZone.getResidenceZoneId();
			
			if(id < 0 || id >= 5)						// Shouldnt happen
				continue;
			
			TELE_ZONES[id] = teleZone;
		}
			
		QUEST_REWARD 		= 5009;
		CENTER 				= new L2CharPosition(82882,-16280,-1894,0);
	}
	
	public BanditStronghold(int questId, String name, String descr, int hallId)
	{
		super(questId, name, descr, hallId);
	}

	@Override
	public String getFlagHtml(int flag)
	{
		String result = null;
		
		switch(flag)
		{
			case 35423:
				result = "messenger_flag1.htm";
				break;
			case 35424:
				result = "messenger_flag2.htm";
				break;
			case 35425:
				result = "messenger_flag3.htm";
				break;
			case 35426:
				result = "messenger_flag4.htm";
				break;
			case 35427:
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
			case 35428:
				result = "messenger_ally1result.htm";
				break;
			case 35429:
				result = "messenger_ally2result.htm";
				break;
			case 35430:
				result = "messenger_ally3result.htm";
				break;
			case 35431:
				result = "messenger_ally4result.htm";
				break;
			case 35432:
				result = "messenger_ally5result.htm";
				break;
		}
		
		return result;
	}
	
	public static void main(String[] args)
	{
		new BanditStronghold(-1, qn, "conquerablehalls/flagwar", BANDIT_STRONGHOLD);
	}
}