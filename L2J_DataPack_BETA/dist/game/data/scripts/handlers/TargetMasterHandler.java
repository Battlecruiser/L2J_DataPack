/**
 * 
 */
package handlers;

import handlers.targethandlers.TargetAlly;
import handlers.targethandlers.TargetArea;
import handlers.targethandlers.TargetAreaCorpseMob;
import handlers.targethandlers.TargetAreaSummon;
import handlers.targethandlers.TargetAreaUndead;
import handlers.targethandlers.TargetAura;
import handlers.targethandlers.TargetAuraCorpseMob;
import handlers.targethandlers.TargetBehindArea;
import handlers.targethandlers.TargetBehindAura;
import handlers.targethandlers.TargetClan;
import handlers.targethandlers.TargetClanMember;
import handlers.targethandlers.TargetCorpseAlly;
import handlers.targethandlers.TargetCorpseClan;
import handlers.targethandlers.TargetCorpseMob;
import handlers.targethandlers.TargetCorpsePet;
import handlers.targethandlers.TargetCorpsePlayer;
import handlers.targethandlers.TargetEnemySummon;
import handlers.targethandlers.TargetFlagPole;
import handlers.targethandlers.TargetFrontArea;
import handlers.targethandlers.TargetFrontAura;
import handlers.targethandlers.TargetGround;
import handlers.targethandlers.TargetHoly;
import handlers.targethandlers.TargetOne;
import handlers.targethandlers.TargetOwnerPet;
import handlers.targethandlers.TargetParty;
import handlers.targethandlers.TargetPartyClan;
import handlers.targethandlers.TargetPartyMember;
import handlers.targethandlers.TargetPartyNotMe;
import handlers.targethandlers.TargetPartyOther;
import handlers.targethandlers.TargetPet;
import handlers.targethandlers.TargetSelf;
import handlers.targethandlers.TargetSummon;
import handlers.targethandlers.TargetUndead;
import handlers.targethandlers.TargetUnlockable;

import java.util.logging.Logger;

import com.l2jserver.gameserver.handler.TargetHandler;

/**
 * @author UnAfraid
 *
 */
public class TargetMasterHandler
{
	private static Logger _log = Logger.getLogger(TargetMasterHandler.class.getName());
	
	public static void loadTargetHandlers()
	{
		TargetHandler.getInstance().registerSkillTargetType(new TargetAlly());
		TargetHandler.getInstance().registerSkillTargetType(new TargetArea());
		TargetHandler.getInstance().registerSkillTargetType(new TargetAreaCorpseMob());
		TargetHandler.getInstance().registerSkillTargetType(new TargetAreaSummon());
		TargetHandler.getInstance().registerSkillTargetType(new TargetAreaUndead());
		TargetHandler.getInstance().registerSkillTargetType(new TargetAura());
		TargetHandler.getInstance().registerSkillTargetType(new TargetAuraCorpseMob());
		TargetHandler.getInstance().registerSkillTargetType(new TargetBehindArea());
		TargetHandler.getInstance().registerSkillTargetType(new TargetBehindAura());
		TargetHandler.getInstance().registerSkillTargetType(new TargetClan());
		TargetHandler.getInstance().registerSkillTargetType(new TargetClanMember());
		TargetHandler.getInstance().registerSkillTargetType(new TargetCorpseAlly());
		TargetHandler.getInstance().registerSkillTargetType(new TargetCorpseClan());
		TargetHandler.getInstance().registerSkillTargetType(new TargetCorpseMob());
		TargetHandler.getInstance().registerSkillTargetType(new TargetCorpsePet());
		TargetHandler.getInstance().registerSkillTargetType(new TargetCorpsePlayer());
		TargetHandler.getInstance().registerSkillTargetType(new TargetEnemySummon());
		TargetHandler.getInstance().registerSkillTargetType(new TargetFlagPole());
		TargetHandler.getInstance().registerSkillTargetType(new TargetFrontArea());
		TargetHandler.getInstance().registerSkillTargetType(new TargetFrontAura());
		TargetHandler.getInstance().registerSkillTargetType(new TargetGround());
		TargetHandler.getInstance().registerSkillTargetType(new TargetHoly());
		TargetHandler.getInstance().registerSkillTargetType(new TargetOne());
		TargetHandler.getInstance().registerSkillTargetType(new TargetOwnerPet());
		TargetHandler.getInstance().registerSkillTargetType(new TargetParty());
		TargetHandler.getInstance().registerSkillTargetType(new TargetPartyClan());
		TargetHandler.getInstance().registerSkillTargetType(new TargetPartyMember());
		TargetHandler.getInstance().registerSkillTargetType(new TargetPartyNotMe());
		TargetHandler.getInstance().registerSkillTargetType(new TargetPartyOther());
		TargetHandler.getInstance().registerSkillTargetType(new TargetPet());
		TargetHandler.getInstance().registerSkillTargetType(new TargetSelf());
		TargetHandler.getInstance().registerSkillTargetType(new TargetSummon());
		TargetHandler.getInstance().registerSkillTargetType(new TargetUndead());
		TargetHandler.getInstance().registerSkillTargetType(new TargetUnlockable());
	}
	
	public static void main(String[] args)
	{
		loadTargetHandlers();
		_log.config("Loaded " + TargetHandler.getInstance().size() + " Target handlers");
	}
}
