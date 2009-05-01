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
package handlers;

import handlers.admincommandhandlers.AdminAdmin;
import handlers.admincommandhandlers.AdminAnnouncements;
import handlers.admincommandhandlers.AdminBBS;
import handlers.admincommandhandlers.AdminBan;
import handlers.admincommandhandlers.AdminBuffs;
import handlers.admincommandhandlers.AdminCache;
import handlers.admincommandhandlers.AdminChangeAccessLevel;
import handlers.admincommandhandlers.AdminCreateItem;
import handlers.admincommandhandlers.AdminCursedWeapons;
import handlers.admincommandhandlers.AdminDelete;
import handlers.admincommandhandlers.AdminDisconnect;
import handlers.admincommandhandlers.AdminDoorControl;
import handlers.admincommandhandlers.AdminEditChar;
import handlers.admincommandhandlers.AdminEditNpc;
import handlers.admincommandhandlers.AdminEffects;
import handlers.admincommandhandlers.AdminElement;
import handlers.admincommandhandlers.AdminEnchant;
import handlers.admincommandhandlers.AdminEventEngine;
import handlers.admincommandhandlers.AdminExpSp;
import handlers.admincommandhandlers.AdminFightCalculator;
import handlers.admincommandhandlers.AdminFortSiege;
import handlers.admincommandhandlers.AdminGeoEditor;
import handlers.admincommandhandlers.AdminGeodata;
import handlers.admincommandhandlers.AdminGm;
import handlers.admincommandhandlers.AdminGmChat;
import handlers.admincommandhandlers.AdminHeal;
import handlers.admincommandhandlers.AdminHelpPage;
import handlers.admincommandhandlers.AdminInstance;
import handlers.admincommandhandlers.AdminInvul;
import handlers.admincommandhandlers.AdminKick;
import handlers.admincommandhandlers.AdminKill;
import handlers.admincommandhandlers.AdminLevel;
import handlers.admincommandhandlers.AdminLogin;
import handlers.admincommandhandlers.AdminMammon;
import handlers.admincommandhandlers.AdminManor;
import handlers.admincommandhandlers.AdminMenu;
import handlers.admincommandhandlers.AdminMobGroup;
import handlers.admincommandhandlers.AdminMonsterRace;
import handlers.admincommandhandlers.AdminPForge;
import handlers.admincommandhandlers.AdminPathNode;
import handlers.admincommandhandlers.AdminPetition;
import handlers.admincommandhandlers.AdminPledge;
import handlers.admincommandhandlers.AdminPolymorph;
import handlers.admincommandhandlers.AdminQuest;
import handlers.admincommandhandlers.AdminRepairChar;
import handlers.admincommandhandlers.AdminRes;
import handlers.admincommandhandlers.AdminRide;
import handlers.admincommandhandlers.AdminShop;
import handlers.admincommandhandlers.AdminShutdown;
import handlers.admincommandhandlers.AdminSiege;
import handlers.admincommandhandlers.AdminSkill;
import handlers.admincommandhandlers.AdminSpawn;
import handlers.admincommandhandlers.AdminSummon;
import handlers.admincommandhandlers.AdminTarget;
import handlers.admincommandhandlers.AdminTeleport;
import handlers.admincommandhandlers.AdminTest;
import handlers.admincommandhandlers.AdminTvTEvent;
import handlers.admincommandhandlers.AdminUnblockIp;
import handlers.admincommandhandlers.AdminZone;
import handlers.chathandlers.ChatAll;
import handlers.chathandlers.ChatAlliance;
import handlers.chathandlers.ChatClan;
import handlers.chathandlers.ChatHeroVoice;
import handlers.chathandlers.ChatParty;
import handlers.chathandlers.ChatPartyRoomAll;
import handlers.chathandlers.ChatPartyRoomCommander;
import handlers.chathandlers.ChatPetition;
import handlers.chathandlers.ChatShout;
import handlers.chathandlers.ChatTell;
import handlers.chathandlers.ChatTrade;
import handlers.itemhandlers.BallistaBombItem;
import handlers.itemhandlers.BeastSoulShot;
import handlers.itemhandlers.BeastSpice;
import handlers.itemhandlers.BeastSpiritShot;
import handlers.itemhandlers.BlessedSpiritShot;
import handlers.itemhandlers.Book;
import handlers.itemhandlers.CharChangePotions;
import handlers.itemhandlers.CrystalCarol;
import handlers.itemhandlers.EnchantAttribute;
import handlers.itemhandlers.EnchantScrolls;
import handlers.itemhandlers.EnergyStone;
import handlers.itemhandlers.ExtractableItems;
import handlers.itemhandlers.Firework;
import handlers.itemhandlers.FishShots;
import handlers.itemhandlers.Harvester;
import handlers.itemhandlers.Key;
import handlers.itemhandlers.Maps;
import handlers.itemhandlers.MercTicket;
import handlers.itemhandlers.MysteryPotion;
import handlers.itemhandlers.PaganKeys;
import handlers.itemhandlers.PetFood;
import handlers.itemhandlers.Potions;
import handlers.itemhandlers.Recipes;
import handlers.itemhandlers.Remedy;
import handlers.itemhandlers.RollingDice;
import handlers.itemhandlers.ScrollOfEscape;
import handlers.itemhandlers.ScrollOfResurrection;
import handlers.itemhandlers.Scrolls;
import handlers.itemhandlers.Seed;
import handlers.itemhandlers.SevenSignsRecord;
import handlers.itemhandlers.SoulCrystals;
import handlers.itemhandlers.SoulShots;
import handlers.itemhandlers.SpecialXMas;
import handlers.itemhandlers.SpiritLake;
import handlers.itemhandlers.SpiritShot;
import handlers.itemhandlers.SummonItems;
import handlers.skillhandlers.BalanceLife;
import handlers.skillhandlers.BallistaBomb;
import handlers.skillhandlers.BeastFeed;
import handlers.skillhandlers.Blow;
import handlers.skillhandlers.Charge;
import handlers.skillhandlers.CombatPointHeal;
import handlers.skillhandlers.Continuous;
import handlers.skillhandlers.CpDam;
import handlers.skillhandlers.Craft;
import handlers.skillhandlers.DeluxeKey;
import handlers.skillhandlers.Disablers;
import handlers.skillhandlers.DrainSoul;
import handlers.skillhandlers.Dummy;
import handlers.skillhandlers.Extractable;
import handlers.skillhandlers.Fishing;
import handlers.skillhandlers.FishingSkill;
import handlers.skillhandlers.GetPlayer;
import handlers.skillhandlers.GiveSp;
import handlers.skillhandlers.Harvest;
import handlers.skillhandlers.Heal;
import handlers.skillhandlers.InstantJump;
import handlers.skillhandlers.ManaHeal;
import handlers.skillhandlers.Manadam;
import handlers.skillhandlers.Mdam;
import handlers.skillhandlers.Pdam;
import handlers.skillhandlers.Recall;
import handlers.skillhandlers.Resurrect;
import handlers.skillhandlers.ShiftTarget;
import handlers.skillhandlers.Soul;
import handlers.skillhandlers.Sow;
import handlers.skillhandlers.Spoil;
import handlers.skillhandlers.StrSiegeAssault;
import handlers.skillhandlers.SummonFriend;
import handlers.skillhandlers.SummonTreasureKey;
import handlers.skillhandlers.Sweep;
import handlers.skillhandlers.TakeCastle;
import handlers.skillhandlers.TakeFort;
import handlers.skillhandlers.TransformDispel;
import handlers.skillhandlers.Trap;
import handlers.skillhandlers.Unlock;
import handlers.usercommandhandlers.ChannelDelete;
import handlers.usercommandhandlers.ChannelLeave;
import handlers.usercommandhandlers.ChannelListUpdate;
import handlers.usercommandhandlers.ClanPenalty;
import handlers.usercommandhandlers.ClanWarsList;
import handlers.usercommandhandlers.DisMount;
import handlers.usercommandhandlers.Escape;
import handlers.usercommandhandlers.InstanceZone;
import handlers.usercommandhandlers.Loc;
import handlers.usercommandhandlers.Mount;
import handlers.usercommandhandlers.OlympiadStat;
import handlers.usercommandhandlers.PartyInfo;
import handlers.usercommandhandlers.Time;
import handlers.voicedcommandhandlers.Banking;
import handlers.voicedcommandhandlers.Wedding;
import handlers.voicedcommandhandlers.stats;

import java.util.logging.Logger;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.handler.AdminCommandHandler;
import net.sf.l2j.gameserver.handler.ChatHandler;
import net.sf.l2j.gameserver.handler.ItemHandler;
import net.sf.l2j.gameserver.handler.SkillHandler;
import net.sf.l2j.gameserver.handler.UserCommandHandler;
import net.sf.l2j.gameserver.handler.VoicedCommandHandler;

/**
 *
 * @author  nBd
 */
public class MasterHandler
{
	private static Logger _log = Logger.getLogger(MasterHandler.class.getName());
	
	private static void loadAdminHandlers()
	{
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminAdmin());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminAnnouncements());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminBan());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminBBS());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminBuffs());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminCache());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminChangeAccessLevel());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminCreateItem());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminCursedWeapons());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminDelete());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminDisconnect());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminDoorControl());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminEditChar());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminEditNpc());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminEffects());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminElement());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminEnchant());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminEventEngine());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminExpSp());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminFightCalculator());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminFortSiege());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminGeodata());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminGeoEditor());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminGm());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminGmChat());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminHeal());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminHelpPage());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminInstance());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminInvul());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminKick());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminKill());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminLevel());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminLogin());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminMammon());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminManor());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminMenu());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminMobGroup());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminMonsterRace());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminPathNode());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminPetition());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminPForge());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminPledge());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminPolymorph());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminQuest());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminRepairChar());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminRes());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminRide());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminShop());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminShutdown());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminSiege());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminSkill());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminSpawn());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminSummon());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminTarget());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminTeleport());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminTest());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminTvTEvent());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminUnblockIp());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminZone());
		_log.config("Loaded " + AdminCommandHandler.getInstance().size() + "  AdminCommandHandlers");
	}
	
	private static void loadChatHandlers()
	{
		ChatHandler.getInstance().registerChatHandler(new ChatAll());
		ChatHandler.getInstance().registerChatHandler(new ChatAlliance());
		ChatHandler.getInstance().registerChatHandler(new ChatClan());
		ChatHandler.getInstance().registerChatHandler(new ChatHeroVoice());
		ChatHandler.getInstance().registerChatHandler(new ChatParty());
		ChatHandler.getInstance().registerChatHandler(new ChatPartyRoomAll());
		ChatHandler.getInstance().registerChatHandler(new ChatPartyRoomCommander());
		ChatHandler.getInstance().registerChatHandler(new ChatPetition());
		ChatHandler.getInstance().registerChatHandler(new ChatShout());
		ChatHandler.getInstance().registerChatHandler(new ChatTell());
		ChatHandler.getInstance().registerChatHandler(new ChatTrade());
		_log.config("Loaded " + ChatHandler.getInstance().size() + "  ChatHandlers");
	}
	
	private static void loadItemHandlers()
	{
		ItemHandler.getInstance().registerItemHandler(new ScrollOfEscape());
		ItemHandler.getInstance().registerItemHandler(new ScrollOfResurrection());
		ItemHandler.getInstance().registerItemHandler(new SoulShots());
		ItemHandler.getInstance().registerItemHandler(new SpiritShot());
		ItemHandler.getInstance().registerItemHandler(new BlessedSpiritShot());
		ItemHandler.getInstance().registerItemHandler(new BallistaBombItem());
		ItemHandler.getInstance().registerItemHandler(new BeastSoulShot());
		ItemHandler.getInstance().registerItemHandler(new BeastSpiritShot());
		ItemHandler.getInstance().registerItemHandler(new Key());
		ItemHandler.getInstance().registerItemHandler(new PaganKeys());
		ItemHandler.getInstance().registerItemHandler(new Maps());
		ItemHandler.getInstance().registerItemHandler(new Potions());
		ItemHandler.getInstance().registerItemHandler(new Recipes());
		ItemHandler.getInstance().registerItemHandler(new RollingDice());
		ItemHandler.getInstance().registerItemHandler(new MysteryPotion());
		ItemHandler.getInstance().registerItemHandler(new EnchantAttribute());
		ItemHandler.getInstance().registerItemHandler(new EnchantScrolls());
		ItemHandler.getInstance().registerItemHandler(new EnergyStone());
		ItemHandler.getInstance().registerItemHandler(new Book());
		ItemHandler.getInstance().registerItemHandler(new Remedy());
		ItemHandler.getInstance().registerItemHandler(new Scrolls());
		ItemHandler.getInstance().registerItemHandler(new CrystalCarol());
		ItemHandler.getInstance().registerItemHandler(new SoulCrystals());
		ItemHandler.getInstance().registerItemHandler(new SevenSignsRecord());
		ItemHandler.getInstance().registerItemHandler(new CharChangePotions());
		ItemHandler.getInstance().registerItemHandler(new Firework());
		ItemHandler.getInstance().registerItemHandler(new Seed());
		ItemHandler.getInstance().registerItemHandler(new Harvester());
		ItemHandler.getInstance().registerItemHandler(new MercTicket());
		ItemHandler.getInstance().registerItemHandler(new FishShots());
		ItemHandler.getInstance().registerItemHandler(new ExtractableItems());
		ItemHandler.getInstance().registerItemHandler(new PetFood());
		ItemHandler.getInstance().registerItemHandler(new SpecialXMas());
		ItemHandler.getInstance().registerItemHandler(new SummonItems());
		ItemHandler.getInstance().registerItemHandler(new BeastSpice());
		ItemHandler.getInstance().registerItemHandler(new SpiritLake());
		_log.config("Loaded " + ItemHandler.getInstance().size() + " ItemHandlers");
	}
	
	private static void loadSkillHandlers()
	{
		SkillHandler.getInstance().registerSkillHandler(new Blow());
		SkillHandler.getInstance().registerSkillHandler(new Pdam());
		SkillHandler.getInstance().registerSkillHandler(new Mdam());
		SkillHandler.getInstance().registerSkillHandler(new CpDam());
		SkillHandler.getInstance().registerSkillHandler(new Manadam());
		SkillHandler.getInstance().registerSkillHandler(new Heal());
		SkillHandler.getInstance().registerSkillHandler(new CombatPointHeal());
		SkillHandler.getInstance().registerSkillHandler(new ManaHeal());
		SkillHandler.getInstance().registerSkillHandler(new BalanceLife());
		SkillHandler.getInstance().registerSkillHandler(new Charge());
		SkillHandler.getInstance().registerSkillHandler(new Continuous());
		SkillHandler.getInstance().registerSkillHandler(new Resurrect());
		SkillHandler.getInstance().registerSkillHandler(new ShiftTarget());
		SkillHandler.getInstance().registerSkillHandler(new Spoil());
		SkillHandler.getInstance().registerSkillHandler(new Sweep());
		SkillHandler.getInstance().registerSkillHandler(new StrSiegeAssault());
		SkillHandler.getInstance().registerSkillHandler(new SummonFriend());
		SkillHandler.getInstance().registerSkillHandler(new SummonTreasureKey());
		SkillHandler.getInstance().registerSkillHandler(new Disablers());
		SkillHandler.getInstance().registerSkillHandler(new Recall());
		SkillHandler.getInstance().registerSkillHandler(new BallistaBomb());
		SkillHandler.getInstance().registerSkillHandler(new TakeCastle());
		SkillHandler.getInstance().registerSkillHandler(new TakeFort());
		SkillHandler.getInstance().registerSkillHandler(new Unlock());
		SkillHandler.getInstance().registerSkillHandler(new DrainSoul());
		SkillHandler.getInstance().registerSkillHandler(new Craft());
		SkillHandler.getInstance().registerSkillHandler(new Fishing());
		SkillHandler.getInstance().registerSkillHandler(new FishingSkill());
		SkillHandler.getInstance().registerSkillHandler(new BeastFeed());
		SkillHandler.getInstance().registerSkillHandler(new DeluxeKey());
		SkillHandler.getInstance().registerSkillHandler(new Sow());
		SkillHandler.getInstance().registerSkillHandler(new Soul());
		SkillHandler.getInstance().registerSkillHandler(new Harvest());
		SkillHandler.getInstance().registerSkillHandler(new GetPlayer());
		SkillHandler.getInstance().registerSkillHandler(new TransformDispel());
		SkillHandler.getInstance().registerSkillHandler(new Trap());
		SkillHandler.getInstance().registerSkillHandler(new GiveSp());
		SkillHandler.getInstance().registerSkillHandler(new InstantJump());
		SkillHandler.getInstance().registerSkillHandler(new Dummy());
		SkillHandler.getInstance().registerSkillHandler(new Extractable());
		_log.config("Loaded " + SkillHandler.getInstance().size() + " SkillHandlers");
	}
	
	private static void loadUserHandlers()
	{
		UserCommandHandler.getInstance().registerUserCommandHandler(new ClanPenalty());
		UserCommandHandler.getInstance().registerUserCommandHandler(new ClanWarsList());
		UserCommandHandler.getInstance().registerUserCommandHandler(new DisMount());
		UserCommandHandler.getInstance().registerUserCommandHandler(new Escape());
		UserCommandHandler.getInstance().registerUserCommandHandler(new InstanceZone());
		UserCommandHandler.getInstance().registerUserCommandHandler(new Loc());
		UserCommandHandler.getInstance().registerUserCommandHandler(new Mount());
		UserCommandHandler.getInstance().registerUserCommandHandler(new PartyInfo());
		UserCommandHandler.getInstance().registerUserCommandHandler(new Time());
		UserCommandHandler.getInstance().registerUserCommandHandler(new OlympiadStat());
		UserCommandHandler.getInstance().registerUserCommandHandler(new ChannelLeave());
		UserCommandHandler.getInstance().registerUserCommandHandler(new ChannelDelete());
		UserCommandHandler.getInstance().registerUserCommandHandler(new ChannelListUpdate());
		_log.config("Loaded " + UserCommandHandler.getInstance().size() + " UserHandlers");
	}
	
	private static void loadVoicedHandlers()
	{
		VoicedCommandHandler.getInstance().registerVoicedCommandHandler(new stats());
		if (Config.L2JMOD_ALLOW_WEDDING)
			VoicedCommandHandler.getInstance().registerVoicedCommandHandler(new Wedding());
		if (Config.BANKING_SYSTEM_ENABLED)
			VoicedCommandHandler.getInstance().registerVoicedCommandHandler(new Banking());
		_log.config("Loaded " + VoicedCommandHandler.getInstance().size() + " VoicedHandlers");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		_log.config("Loading Handlers...");
		loadAdminHandlers();
		loadChatHandlers();
		loadItemHandlers();
		loadSkillHandlers();
		loadUserHandlers();
		loadVoicedHandlers();
		_log.config("Handlers Loaded...");
	}
}