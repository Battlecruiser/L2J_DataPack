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

package handlers.voicedcommandhandlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.l2j.Config;
import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.GameTimeController;
import net.sf.l2j.gameserver.SevenSigns;
import net.sf.l2j.gameserver.ThreadPoolManager;
import net.sf.l2j.gameserver.ai.CtrlIntention;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.instancemanager.CoupleManager;
import net.sf.l2j.gameserver.instancemanager.GrandBossManager;
import net.sf.l2j.gameserver.instancemanager.SiegeManager;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.entity.TvTEvent;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.ConfirmDlg;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;
import net.sf.l2j.gameserver.network.serverpackets.SetupGauge;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.util.Broadcast;

/**
 * @author evill33t
 *
 */
public class Wedding implements IVoicedCommandHandler
{
	static final Logger _log = Logger.getLogger(Wedding.class.getName());
	private static final String[] _voicedCommands =
	{
		"divorce",
		"engage",
		"gotolove"
	};
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.IVoicedCommandHandler#useVoicedCommand(java.lang.String, net.sf.l2j.gameserver.model.actor.instance.L2PcInstance, java.lang.String)
	 */
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		if (command.startsWith("engage"))
			return engage(activeChar);
		else if (command.startsWith("divorce"))
			return divorce(activeChar);
		else if (command.startsWith("gotolove"))
			return goToLove(activeChar);
		return false;
	}
	
	public boolean divorce(L2PcInstance activeChar)
	{
		if (activeChar.getPartnerId() == 0)
			return false;
		
		int _partnerId = activeChar.getPartnerId();
		int _coupleId = activeChar.getCoupleId();
		int AdenaAmount = 0;
		
		if (activeChar.isMarried())
		{
			activeChar.sendMessage("You are now divorced.");
			
			AdenaAmount = (activeChar.getAdena() / 100) * Config.L2JMOD_WEDDING_DIVORCE_COSTS;
			activeChar.getInventory().reduceAdena("Wedding", AdenaAmount, activeChar, null);
			
		}
		else
			activeChar.sendMessage("You have broken up as a couple.");
		
		L2PcInstance partner;
		partner = (L2PcInstance) L2World.getInstance().findObject(_partnerId);
		
		if (partner != null)
		{
			partner.setPartnerId(0);
			if (partner.isMarried())
				partner.sendMessage("Your spouse has decided to divorce you.");
			else
				partner.sendMessage("Your fiance has decided to break the engagement with you.");
			
			// give adena
			if (AdenaAmount > 0)
				partner.addAdena("WEDDING", AdenaAmount, null, false);
		}
		
		CoupleManager.getInstance().deleteCouple(_coupleId);
		return true;
	}
	
	public boolean engage(L2PcInstance activeChar)
	{
		// check target
		if (activeChar.getTarget() == null)
		{
			activeChar.sendMessage("You have no one targeted.");
			return false;
		}
		
		// check if target is a l2pcinstance
		if (!(activeChar.getTarget() instanceof L2PcInstance))
		{
			activeChar.sendMessage("You can only ask another player to engage you.");
			
			return false;
		}
		
		// check if player is already engaged
		if (activeChar.getPartnerId() != 0)
		{
			activeChar.sendMessage("You are already engaged.");
			if (Config.L2JMOD_WEDDING_PUNISH_INFIDELITY)
			{
				activeChar.startAbnormalEffect((short) 0x2000); // give player a Big Head
				// lets recycle the sevensigns debuffs
				int skillId;
				
				int skillLevel = 1;
				
				if (activeChar.getLevel() > 40)
					skillLevel = 2;
				
				if (activeChar.isMageClass())
					skillId = 4361;
				else
					skillId = 4362;
				
				L2Skill skill = SkillTable.getInstance().getInfo(skillId, skillLevel);
				
				if (activeChar.getFirstEffect(skill) == null)
				{
					skill.getEffects(activeChar, activeChar);
					SystemMessage sm = new SystemMessage(SystemMessageId.YOU_FEEL_S1_EFFECT);
					sm.addSkillName(skill);
					activeChar.sendPacket(sm);
				}
			}
			return false;
		}
		
		L2PcInstance ptarget = (L2PcInstance) activeChar.getTarget();
		
		// check if player target himself
		if (ptarget.getObjectId() == activeChar.getObjectId())
		{
			activeChar.sendMessage("Is there something wrong with you, are you trying to go out with youself?");
			return false;
		}
		
		if (ptarget.isMarried())
		{
			activeChar.sendMessage("Player already married.");
			return false;
		}
		
		if (ptarget.isEngageRequest())
		{
			activeChar.sendMessage("Player already asked by someone else.");
			return false;
		}
		
		if (ptarget.getPartnerId() != 0)
		{
			activeChar.sendMessage("Player already engaged with someone else.");
			return false;
		}
		
		if (ptarget.getAppearance().getSex() == activeChar.getAppearance().getSex() && !Config.L2JMOD_WEDDING_SAMESEX)
		{
			activeChar.sendMessage("Gay marriage is not allowed on this server!");
			return false;
		}
		
		// check if target has player on friendlist
		boolean FoundOnFriendList = false;
		int objectId;
		java.sql.Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement;
			statement = con.prepareStatement("SELECT friendId FROM character_friends WHERE charId=?");
			statement.setInt(1, ptarget.getObjectId());
			ResultSet rset = statement.executeQuery();
			
			while (rset.next())
			{
				objectId = rset.getInt("friendId");
				if (objectId == activeChar.getObjectId())
					FoundOnFriendList = true;
			}
			statement.close();
		}
		catch (Exception e)
		{
			_log.warning("could not read friend data:" + e);
		}
		finally
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{
			}
		}
		
		if (!FoundOnFriendList)
		{
			activeChar.sendMessage("The player you want to ask is not on your friends list, you must first be on each others friends list before you choose to engage.");
			return false;
		}
		
		ptarget.setEngageRequest(true, activeChar.getObjectId());
		// $s1
		ConfirmDlg dlg = new ConfirmDlg(SystemMessageId.S1.getId()).addString(activeChar.getName() + " is asking to engage you. Do you want to start a new relationship?");
		ptarget.sendPacket(dlg);
		return true;
	}
	
	public boolean goToLove(L2PcInstance activeChar)
	{
		if (!activeChar.isMarried())
		{
			activeChar.sendMessage("You're not married.");
			return false;
		}
		
		if (activeChar.getPartnerId() == 0)
		{
			activeChar.sendMessage("Couldn't find your fiance in the Database - Inform a Gamemaster.");
			_log.severe("Married but couldn't find parter for " + activeChar.getName());
			return false;
		}
		
		if (GrandBossManager.getInstance().getZone(activeChar) != null)
		{
			activeChar.sendMessage("You are inside a Boss Zone.");
			return false;
		}
		
		L2PcInstance partner;
		partner = (L2PcInstance) L2World.getInstance().findObject(activeChar.getPartnerId());
		if (partner == null)
		{
			activeChar.sendMessage("Your partner is not online.");
			return false;
		}
		else if (activeChar.getInstanceId() != partner.getInstanceId())
		{
			activeChar.sendMessage("Your partner is in another World!");
			return false;
		}
		else if (partner.isInJail())
		{
			activeChar.sendMessage("Your partner is in Jail.");
			return false;
		}
		else if (GrandBossManager.getInstance().getZone(partner) != null)
		{
			activeChar.sendMessage("Your partner is inside a Boss Zone.");
			return false;
		}
		else if (partner.isInOlympiadMode())
		{
			activeChar.sendMessage("Your partner is in the Olympiad now.");
			return false;
		}
		else if (partner.atEvent)
		{
			activeChar.sendMessage("Your partner is in an event.");
			return false;
		}
		else if (partner.isInDuel())
		{
			activeChar.sendMessage("Your partner is in a duel.");
			return false;
		}
		else if (partner.isFestivalParticipant())
		{
			activeChar.sendMessage("Your partner is in a festival.");
			return false;
		}
		else if (partner.isInParty() && partner.getParty().isInDimensionalRift())
		{
			activeChar.sendMessage("Your partner is in dimensional rift.");
			return false;
		}
		else if (partner.inObserverMode())
		{
			activeChar.sendMessage("Your partner is in the observation.");
			return false;
		}
		else if (SiegeManager.getInstance().getSiege(partner) != null && SiegeManager.getInstance().getSiege(partner).getIsInProgress())
		{
			activeChar.sendMessage("Your partner is in a siege, you cannot go to your partner.");
			return false;
		}
		else if (partner.isIn7sDungeon() && !activeChar.isIn7sDungeon())
		{
			int playerCabal = SevenSigns.getInstance().getPlayerCabal(activeChar);
			boolean isSealValidationPeriod = SevenSigns.getInstance().isSealValidationPeriod();
			int compWinner = SevenSigns.getInstance().getCabalHighestScore();
			
			if (isSealValidationPeriod)
			{
				if (playerCabal != compWinner)
				{
					activeChar.sendMessage("Your Partner is in a Seven Signs Dungeon and you are not in the winner Cabal!");
					return false;
				}
			}
			else
			{
				if (playerCabal == SevenSigns.CABAL_NULL)
				{
					activeChar.sendMessage("Your Partner is in a Seven Signs Dungeon and you are not registered!");
					return false;
				}
			}
		}
		else if (!TvTEvent.onEscapeUse(partner.getObjectId()))
		{
			activeChar.sendMessage("Your partner is in an event.");
			return false;
		}
		else if (activeChar.isInJail())
		{
			activeChar.sendMessage("You are in Jail!");
			return false;
		}
		else if (activeChar.isInOlympiadMode())
		{
			activeChar.sendMessage("You are in the Olympiad now.");
			return false;
		}
		else if (activeChar.atEvent)
		{
			activeChar.sendMessage("You are in an event.");
			return false;
		}
		else if (activeChar.isInDuel())
		{
			activeChar.sendMessage("You are in a duel!");
			return false;
		}
		else if (activeChar.inObserverMode())
		{
			activeChar.sendMessage("You are in the observation.");
			return false;
		}
		else if (SiegeManager.getInstance().getSiege(activeChar) != null && SiegeManager.getInstance().getSiege(activeChar).getIsInProgress())
		{
			activeChar.sendMessage("You are in a siege, you cannot go to your partner.");
			return false;
		}
		else if (activeChar.isFestivalParticipant())
		{
			activeChar.sendMessage("You are in a festival.");
			return false;
		}
		else if (activeChar.isInParty() && activeChar.getParty().isInDimensionalRift())
		{
			activeChar.sendMessage("You are in the dimensional rift.");
			return false;
		}
		// Thanks nbd
		else if (!TvTEvent.onEscapeUse(activeChar.getObjectId()))
		{
			activeChar.sendPacket(ActionFailed.STATIC_PACKET);
			return false;
		}
		
		int teleportTimer = Config.L2JMOD_WEDDING_TELEPORT_DURATION * 1000;
		
		activeChar.sendMessage("After " + teleportTimer / 60000 + " min. you will be teleported to your fiance.");
		activeChar.getInventory().reduceAdena("Wedding", Config.L2JMOD_WEDDING_TELEPORT_PRICE, activeChar, null);
		
		activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		//SoE Animation section
		activeChar.setTarget(activeChar);
		activeChar.disableAllSkills();
		
		MagicSkillUse msk = new MagicSkillUse(activeChar, 1050, 1, teleportTimer, 0);
		Broadcast.toSelfAndKnownPlayersInRadius(activeChar, msk, 810000/*900*/);
		SetupGauge sg = new SetupGauge(0, teleportTimer);
		activeChar.sendPacket(sg);
		//End SoE Animation section
		
		EscapeFinalizer ef = new EscapeFinalizer(activeChar, partner.getX(), partner.getY(), partner.getZ(), partner.isIn7sDungeon());
		// continue execution later
		activeChar.setSkillCast(ThreadPoolManager.getInstance().scheduleGeneral(ef, teleportTimer));
		activeChar.forceIsCasting(GameTimeController.getGameTicks() + teleportTimer / GameTimeController.MILLIS_IN_TICK);
		
		return true;
	}
	
	static class EscapeFinalizer implements Runnable
	{
		private L2PcInstance _activeChar;
		private int _partnerx;
		private int _partnery;
		private int _partnerz;
		private boolean _to7sDungeon;
		
		EscapeFinalizer(L2PcInstance activeChar, int x, int y, int z, boolean to7sDungeon)
		{
			_activeChar = activeChar;
			_partnerx = x;
			_partnery = y;
			_partnerz = z;
			_to7sDungeon = to7sDungeon;
		}
		
		public void run()
		{
			if (_activeChar.isDead())
				return;
			
			if(SiegeManager.getInstance().getSiege(_partnerx, _partnery, _partnerz) != null && SiegeManager.getInstance().getSiege(_partnerx, _partnery, _partnerz).getIsInProgress())
			{
				_activeChar.sendMessage("Your partner is in siege, you can't go to your partner.");
				return;
			}
			
			_activeChar.setIsIn7sDungeon(_to7sDungeon);
			_activeChar.enableAllSkills();
			_activeChar.setIsCastingNow(false);
			
			try
			{
				_activeChar.teleToLocation(_partnerx, _partnery, _partnerz);
			}
			catch (Exception e)
			{
				_log.log(Level.SEVERE, "", e);
			}
		}
	}
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.IVoicedCommandHandler#getVoicedCommandList()
	 */
	public String[] getVoicedCommandList()
	{
		return _voicedCommands;
	}
}
