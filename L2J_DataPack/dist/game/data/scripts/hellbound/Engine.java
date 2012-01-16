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
package hellbound;

import java.io.File;
import java.util.Map;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilderFactory;

import javolution.util.FastMap;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.l2jserver.Config;
import com.l2jserver.gameserver.Announcements;
import com.l2jserver.gameserver.datatables.DoorTable;
import com.l2jserver.gameserver.instancemanager.HellboundManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;

public class Engine extends Quest implements Runnable
{
	private static final int UPDATE_INTERVAL = 10000;
	
	private static final int[][] DOOR_LIST =
	{
		{
			19250001, 5
		},
		{
			19250002, 5
		},
		{
			20250001, 9
		},
		{
			20250002, 7
		}
	};
	
	private static final int[] MAX_TRUST =
	{
		0, 300000, 600000, 1000000, 1010000, 1400000, 1490000, 2000000, 2000001, 2500000, 4000000, 0
	};
	
	private static final String ANNOUNCE = "Hellbound now has reached level: %lvl%";
	
	private int _cachedLevel = -1;
	
	private static Map<Integer, PointsInfoHolder> pointsInfo = new FastMap<Integer, PointsInfoHolder>();
	
	// Holds info about points for mob killing
	private class PointsInfoHolder
	{
		protected int pointsAmount;
		protected int minHbLvl;
		protected int maxHbLvl;
		protected int lowestTrustLimit;
		
		protected PointsInfoHolder(int points, int min, int max, int trust)
		{
			pointsAmount = points;
			minHbLvl = min;
			maxHbLvl = max;
			lowestTrustLimit = trust;
		}
	}
	
	private final void onLevelChange(int newLevel)
	{
		try
		{
			HellboundManager.getInstance().setMaxTrust(MAX_TRUST[newLevel]);
			HellboundManager.getInstance().setMinTrust(MAX_TRUST[newLevel - 1]);
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			HellboundManager.getInstance().setMaxTrust(0);
			HellboundManager.getInstance().setMinTrust(0);
		}
		
		HellboundManager.getInstance().updateTrust(0, false);
		HellboundManager.getInstance().doSpawn();
		
		for (int[] doorData : DOOR_LIST)
		{
			try
			{
				L2DoorInstance door = DoorTable.getInstance().getDoor(doorData[0]);
				if (door.getOpen())
				{
					if (newLevel < doorData[1])
					{
						door.closeMe();
					}
				}
				else
				{
					if (newLevel >= doorData[1])
					{
						door.openMe();
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		if (_cachedLevel > 0)
		{
			Announcements.getInstance().announceToAll(ANNOUNCE.replace("%lvl%", String.valueOf(newLevel)));
			_log.info("HellboundEngine: New Level: " + newLevel);
		}
		_cachedLevel = newLevel;
	}
	
	private void loadPointsInfoData()
	{
		String pointsInfoFile = "data/hellboundTrustPoints.xml";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setIgnoringComments(true);
		File file = new File(Config.DATAPACK_ROOT, pointsInfoFile);
		Document doc = null;
		
		if (file.exists())
		{
			try
			{
				doc = factory.newDocumentBuilder().parse(file);
			}
			catch (Exception e)
			{
				_log.log(Level.WARNING, "Could not parse " + pointsInfoFile + " file: " + e.getMessage(), e);
			}
			
			for (Node n = doc.getFirstChild(); n != null; n = n.getNextSibling())
			{
				if ("list".equalsIgnoreCase(n.getNodeName()))
				{
					for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
					{
						if ("npc".equalsIgnoreCase(d.getNodeName()))
						{
							NamedNodeMap attrs = d.getAttributes();
							Node att;
							
							att = attrs.getNamedItem("id");
							if (att == null)
							{
								_log.severe("[Hellbound Trust Points Info] Missing NPC ID, skipping record");
								continue;
							}
							
							int npcId = Integer.parseInt(att.getNodeValue());
							
							att = attrs.getNamedItem("points");
							if (att == null)
							{
								_log.severe("[Hellbound Trust Points Info] Missing reward point info for NPC ID " + npcId + ", skipping record");
								continue;
							}
							int points = Integer.parseInt(att.getNodeValue());
							
							att = attrs.getNamedItem("minHellboundLvl");
							if (att == null)
							{
								_log.severe("[Hellbound Trust Points Info] Missing minHellboundLvl info for NPC ID " + npcId + ", skipping record");
								continue;
							}
							int minHbLvl = Integer.parseInt(att.getNodeValue());
							
							att = attrs.getNamedItem("maxHellboundLvl");
							if (att == null)
							{
								_log.severe("[Hellbound Trust Points Info] Missing maxHellboundLvl info for NPC ID " + npcId + ", skipping record");
								continue;
							}
							int maxHbLvl = Integer.parseInt(att.getNodeValue());
							
							att = attrs.getNamedItem("lowestTrustLimit");
							int lowestTrustLimit = 0;
							if (att != null)
							{
								lowestTrustLimit = Integer.parseInt(att.getNodeValue());
							}
							
							pointsInfo.put(npcId, new PointsInfoHolder(points, minHbLvl, maxHbLvl, lowestTrustLimit));
						}
					}
				}
			}
		}
		else
		{
			_log.warning("Can't locate points info file: " + pointsInfoFile);
		}
		
		_log.info("HellboundEngine: Loaded: " + pointsInfo.size() + " trust point reward data");
	}
	
	@Override
	public void run()
	{
		int level = HellboundManager.getInstance().getLevel();
		if ((level > 0) && (level == _cachedLevel))
		{
			if ((HellboundManager.getInstance().getTrust() == HellboundManager.getInstance().getMaxTrust()) && (level != 4)) // only exclusion is kill of Derek
			{
				level++;
				HellboundManager.getInstance().setLevel(level);
				onLevelChange(level);
			}
		}
		else
		{
			onLevelChange(level); // first run or changed by admin
		}
	}
	
	// Let's try to manage all trust changes for killing here
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		int npcId = npc.getNpcId();
		if (pointsInfo.containsKey(npcId))
		{
			PointsInfoHolder npcInfo = pointsInfo.get(npcId);
			
			if ((HellboundManager.getInstance().getLevel() >= npcInfo.minHbLvl) && (HellboundManager.getInstance().getLevel() <= npcInfo.maxHbLvl) && ((npcInfo.lowestTrustLimit == 0) || (HellboundManager.getInstance().getTrust() > npcInfo.lowestTrustLimit)))
			{
				HellboundManager.getInstance().updateTrust(npcInfo.pointsAmount, true);
			}
			
			if ((npc.getNpcId() == 18465) && (HellboundManager.getInstance().getLevel() == 4))
			{
				HellboundManager.getInstance().setLevel(5);
			}
		}
		
		return super.onKill(npc, killer, isPet);
	}
	
	public Engine(int questId, String name, String descr)
	{
		super(questId, name, descr);
		HellboundManager.getInstance().registerEngine(this, UPDATE_INTERVAL);
		loadPointsInfoData();
		
		// register onKill for all rewardable monsters
		for (int npcId : pointsInfo.keySet())
		{
			addKillId(npcId);
		}
		
		_log.info("HellboundEngine: Mode: levels 0-3");
		_log.info("HellboundEngine: Level: " + HellboundManager.getInstance().getLevel());
		_log.info("HellboundEngine: Trust: " + HellboundManager.getInstance().getTrust());
		if (HellboundManager.getInstance().isLocked())
		{
			_log.info("HellboundEngine: State: locked");
		}
		else
		{
			_log.info("HellboundEngine: State: unlocked");
		}
	}
	
	public static void main(String[] args)
	{
		new Engine(-1, Engine.class.getSimpleName(), "hellbound");
	}
}
