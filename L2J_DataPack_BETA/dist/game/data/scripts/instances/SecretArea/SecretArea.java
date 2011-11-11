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
package instances.SecretArea;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.instancemanager.InstanceManager.InstanceWorld;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.Instance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 ** @author Gladicek Secret Area in the Keucereus Fortress (For Quest 10272 Light Fragment)
 */
public class SecretArea extends Quest
{
	private static final String qn = "SecretArea";
	
	private static final int INSTANCE_ID = 117;
	private static final String _ENTER = "<html><head><body>Soldier Ginby:<br>Hurry! Come back before anybody sees you!</body></html>";
	private static final String _EXIT = "<html><head><body>Shilen Priest Lelrikia:<br>Doomed creature, either you obey the power of Shilen or you fight.Regardless of your decision, the shadow of death will not simply fade away...</body></html>";
	private static final int GINBY = 32566;
	private static final int LELRIKIA = 32567;
	private static final int ENTER = 0;
	private static final int EXIT = 1;
	private static final Location[] TELEPORTS =
	{
		new Location(-23758, -8959, -5384), new Location(-185057, 242821, 1576)
	};
	
	private class SecretAreaWorld extends InstanceWorld
	{
		
	}
	
	private void teleportPlayer(L2PcInstance player, Location loc, int instanceId)
	{
		player.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		player.setInstanceId(instanceId);
		player.teleToLocation(loc, false);
		if (player.getPet() != null)
		{
			player.getPet().getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
			player.getPet().setInstanceId(instanceId);
			player.getPet().teleToLocation(loc, false);
		}
	}
	
	protected void enterInstance(L2PcInstance player)
	{
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		if (world != null)
		{
			if (!(world instanceof SecretAreaWorld))
			{
				player.sendPacket(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER);
				return;
			}
			Instance inst = InstanceManager.getInstance().getInstance(world.instanceId);
			if (inst != null)
			{
				teleportPlayer(player, TELEPORTS[ENTER], world.instanceId);
			}
		}
		else
		{
			final int instanceId = InstanceManager.getInstance().createDynamicInstance("SecretArea.xml");
			
			world = new SecretAreaWorld();
			world.instanceId = instanceId;
			world.templateId = INSTANCE_ID;
			world.status = 0;
			InstanceManager.getInstance().addWorld(world);
			
			world.allowed.add(player.getObjectId());
			teleportPlayer(player, TELEPORTS[ENTER], instanceId);
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		if (st == null)
			st = newQuestState(player);
		
		if (npc.getNpcId() == GINBY && event.equalsIgnoreCase("enter"))
		{	
			enterInstance(player);
			return _ENTER;	
		}
		else if (npc.getNpcId() == LELRIKIA && event.equalsIgnoreCase("exit"))
		{
			teleportPlayer(player, TELEPORTS[EXIT], 0);
			return _EXIT;
		}
		return htmltext;
	}
	
	public SecretArea(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(GINBY);
		addTalkId(GINBY);
		addTalkId(LELRIKIA);
	}
	
	public static void main(String[] args)
	{
		new SecretArea(-1, qn, "instances");
	}
}
