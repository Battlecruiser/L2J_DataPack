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
package handlers.SkillHandlers;

import net.sf.l2j.gameserver.handler.ISkillHandler;
import net.sf.l2j.gameserver.handler.SkillHandler;
import net.sf.l2j.gameserver.model.L2Character;
import net.sf.l2j.gameserver.model.L2ItemInstance;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.InventoryUpdate;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.templates.skills.L2SkillType;
import net.sf.l2j.util.Rnd;

/**
 * @author Charus
 */
public class MysteriousCube implements ISkillHandler
{
	private static final L2SkillType[]	SKILL_IDS	=
													{ L2SkillType.MYSTERIOUS_CUBE };

	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		int rewardId;
		int rnd = Rnd.get(1000);
		
		if (rnd < 350)
			rewardId = 10633; // rough blue
		else if (rnd < 450)
			rewardId = 10642; // fine blue
		else if (rnd < 550)
			rewardId = 10634; // rough yellow
		else if (rnd < 770)
			rewardId = 10643; // fine yellow
		else if (rnd < 820)
			rewardId = 10635; // rough green
		else if (rnd < 860)
			rewardId = 10644; // fine green
		else if (rnd < 900)
			rewardId = 10636; // rough red
		else if (rnd < 950)
			rewardId = 10645; // fine red
		else if (rnd < 990)
			rewardId = 10637; // rough white
		else
			rewardId = 10646; // fine white

		rewardPlayer(activeChar, rewardId);
	}

	private void rewardPlayer(L2Character activeChar, int itemId)
	{
		L2PcInstance player = (L2PcInstance) activeChar;
		L2ItemInstance createdItem = player.getInventory().addItem("MystetiousCube", itemId, 1, player, player);
		SystemMessage msg;
		msg = new SystemMessage(SystemMessageId.EARNED_ITEM);
		msg.addItemName(createdItem);
		player.sendPacket(msg);

		InventoryUpdate iu = new InventoryUpdate();
		iu.addModifiedItem(createdItem);
		player.sendPacket(iu);
	}

	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
	
    public static void main(String[] args)
    {
    	SkillHandler.getInstance().registerSkillHandler(new MysteriousCube());
    }
}