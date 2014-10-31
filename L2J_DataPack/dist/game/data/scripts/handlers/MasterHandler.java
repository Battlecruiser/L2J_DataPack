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

import handlers.actionhandlers.L2ArtefactInstanceAction;
import handlers.actionhandlers.L2DecoyAction;
import handlers.actionhandlers.L2DoorInstanceAction;
import handlers.actionhandlers.L2DoorInstanceActionShift;
import handlers.actionhandlers.L2ItemInstanceAction;
import handlers.actionhandlers.L2ItemInstanceActionShift;
import handlers.actionhandlers.L2NpcAction;
import handlers.actionhandlers.L2NpcActionShift;
import handlers.actionhandlers.L2PcInstanceAction;
import handlers.actionhandlers.L2PcInstanceActionShift;
import handlers.actionhandlers.L2PetInstanceAction;
import handlers.actionhandlers.L2StaticObjectInstanceAction;
import handlers.actionhandlers.L2StaticObjectInstanceActionShift;
import handlers.actionhandlers.L2SummonAction;
import handlers.actionhandlers.L2SummonActionShift;
import handlers.actionhandlers.L2TrapAction;
import handlers.admincommandhandlers.AdminAdmin;
import handlers.admincommandhandlers.AdminAnnouncements;
import handlers.admincommandhandlers.AdminBBS;
import handlers.admincommandhandlers.AdminBan;
import handlers.admincommandhandlers.AdminBuffs;
import handlers.admincommandhandlers.AdminCache;
import handlers.admincommandhandlers.AdminCamera;
import handlers.admincommandhandlers.AdminChangeAccessLevel;
import handlers.admincommandhandlers.AdminClan;
import handlers.admincommandhandlers.AdminCreateItem;
import handlers.admincommandhandlers.AdminCursedWeapons;
import handlers.admincommandhandlers.AdminDebug;
import handlers.admincommandhandlers.AdminDelete;
import handlers.admincommandhandlers.AdminDisconnect;
import handlers.admincommandhandlers.AdminDoorControl;
import handlers.admincommandhandlers.AdminEditChar;
import handlers.admincommandhandlers.AdminEditNpc;
import handlers.admincommandhandlers.AdminEffects;
import handlers.admincommandhandlers.AdminElement;
import handlers.admincommandhandlers.AdminEnchant;
import handlers.admincommandhandlers.AdminEventEngine;
import handlers.admincommandhandlers.AdminEvents;
import handlers.admincommandhandlers.AdminExpSp;
import handlers.admincommandhandlers.AdminFightCalculator;
import handlers.admincommandhandlers.AdminFortSiege;
import handlers.admincommandhandlers.AdminGeoEditor;
import handlers.admincommandhandlers.AdminGeodata;
import handlers.admincommandhandlers.AdminGm;
import handlers.admincommandhandlers.AdminGmChat;
import handlers.admincommandhandlers.AdminGraciaSeeds;
import handlers.admincommandhandlers.AdminHeal;
import handlers.admincommandhandlers.AdminHelpPage;
import handlers.admincommandhandlers.AdminInstance;
import handlers.admincommandhandlers.AdminInstanceZone;
import handlers.admincommandhandlers.AdminInvul;
import handlers.admincommandhandlers.AdminKick;
import handlers.admincommandhandlers.AdminKill;
import handlers.admincommandhandlers.AdminLevel;
import handlers.admincommandhandlers.AdminLogin;
import handlers.admincommandhandlers.AdminMammon;
import handlers.admincommandhandlers.AdminManor;
import handlers.admincommandhandlers.AdminMenu;
import handlers.admincommandhandlers.AdminMessages;
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
import handlers.admincommandhandlers.AdminShowQuests;
import handlers.admincommandhandlers.AdminShutdown;
import handlers.admincommandhandlers.AdminSiege;
import handlers.admincommandhandlers.AdminSkill;
import handlers.admincommandhandlers.AdminSpawn;
import handlers.admincommandhandlers.AdminSummon;
import handlers.admincommandhandlers.AdminTarget;
import handlers.admincommandhandlers.AdminTeleport;
import handlers.admincommandhandlers.AdminTerritoryWar;
import handlers.admincommandhandlers.AdminTest;
import handlers.admincommandhandlers.AdminTvTEvent;
import handlers.admincommandhandlers.AdminUnblockIp;
import handlers.admincommandhandlers.AdminVitality;
import handlers.admincommandhandlers.AdminZone;
import handlers.bypasshandlers.Augment;
import handlers.bypasshandlers.Buy;
import handlers.bypasshandlers.BuyShadowItem;
import handlers.bypasshandlers.CPRecovery;
import handlers.bypasshandlers.ChatLink;
import handlers.bypasshandlers.ClanWarehouse;
import handlers.bypasshandlers.DrawHenna;
import handlers.bypasshandlers.Festival;
import handlers.bypasshandlers.FortSiege;
import handlers.bypasshandlers.ItemAuctionLink;
import handlers.bypasshandlers.Link;
import handlers.bypasshandlers.Loto;
import handlers.bypasshandlers.ManorManager;
import handlers.bypasshandlers.Multisell;
import handlers.bypasshandlers.Observation;
import handlers.bypasshandlers.OlympiadObservation;
import handlers.bypasshandlers.OlympiadManagerLink;
import handlers.bypasshandlers.PlayerHelp;
import handlers.bypasshandlers.PrivateWarehouse;
import handlers.bypasshandlers.QuestLink;
import handlers.bypasshandlers.QuestList;
import handlers.bypasshandlers.ReceivePremium;
import handlers.bypasshandlers.ReleaseAttribute;
import handlers.bypasshandlers.RemoveDeathPenalty;
import handlers.bypasshandlers.RemoveHennaList;
import handlers.bypasshandlers.RentPet;
import handlers.bypasshandlers.RideWyvern;
import handlers.bypasshandlers.Rift;
import handlers.bypasshandlers.SkillList;
import handlers.bypasshandlers.SupportBlessing;
import handlers.bypasshandlers.SupportMagic;
import handlers.bypasshandlers.TerritoryStatus;
import handlers.bypasshandlers.TerritoryWar;
import handlers.bypasshandlers.VoiceCommand;
import handlers.bypasshandlers.Wear;
import handlers.chathandlers.ChatAll;
import handlers.chathandlers.ChatAlliance;
import handlers.chathandlers.ChatBattlefield;
import handlers.chathandlers.ChatClan;
import handlers.chathandlers.ChatHeroVoice;
import handlers.chathandlers.ChatParty;
import handlers.chathandlers.ChatPartyMatchRoom;
import handlers.chathandlers.ChatPartyRoomAll;
import handlers.chathandlers.ChatPartyRoomCommander;
import handlers.chathandlers.ChatPetition;
import handlers.chathandlers.ChatShout;
import handlers.chathandlers.ChatTell;
import handlers.chathandlers.ChatTrade;
import handlers.itemhandlers.BeastSoulShot;
import handlers.itemhandlers.BeastSpice;
import handlers.itemhandlers.BeastSpiritShot;
import handlers.itemhandlers.BlessedSpiritShot;
import handlers.itemhandlers.Book;
import handlers.itemhandlers.Disguise;
import handlers.itemhandlers.Elixir;
import handlers.itemhandlers.EnchantAttribute;
import handlers.itemhandlers.EnchantScrolls;
import handlers.itemhandlers.EnergyStarStone;
import handlers.itemhandlers.EventItem;
import handlers.itemhandlers.ExtractableItems;
import handlers.itemhandlers.FishShots;
import handlers.itemhandlers.Harvester;
import handlers.itemhandlers.ItemSkills;
import handlers.itemhandlers.ItemSkillsTemplate;
import handlers.itemhandlers.ManaPotion;
import handlers.itemhandlers.Maps;
import handlers.itemhandlers.MercTicket;
import handlers.itemhandlers.NicknameColor;
import handlers.itemhandlers.PaganKeys;
import handlers.itemhandlers.PetFood;
import handlers.itemhandlers.Recipes;
import handlers.itemhandlers.RollingDice;
import handlers.itemhandlers.ScrollOfResurrection;
import handlers.itemhandlers.Seed;
import handlers.itemhandlers.SevenSignsRecord;
import handlers.itemhandlers.SoulShots;
import handlers.itemhandlers.SpecialXMas;
import handlers.itemhandlers.SpiritShot;
import handlers.itemhandlers.SummonItems;
import handlers.itemhandlers.TeleportBookmark;
import handlers.skillhandlers.BalanceLife;
import handlers.skillhandlers.BallistaBomb;
import handlers.skillhandlers.BeastSkills;
import handlers.skillhandlers.Blow;
import handlers.skillhandlers.Cancel;
import handlers.skillhandlers.Charge;
import handlers.skillhandlers.CombatPointHeal;
import handlers.skillhandlers.Continuous;
import handlers.skillhandlers.CpDam;
import handlers.skillhandlers.CpDamPercent;
import handlers.skillhandlers.Craft;
import handlers.skillhandlers.DeluxeKey;
import handlers.skillhandlers.Detection;
import handlers.skillhandlers.Disablers;
import handlers.skillhandlers.Dummy;
import handlers.skillhandlers.Extractable;
import handlers.skillhandlers.Fishing;
import handlers.skillhandlers.FishingSkill;
import handlers.skillhandlers.GetPlayer;
import handlers.skillhandlers.GiveReco;
import handlers.skillhandlers.GiveSp;
import handlers.skillhandlers.GiveVitality;
import handlers.skillhandlers.Harvest;
import handlers.skillhandlers.Heal;
import handlers.skillhandlers.HealPercent;
import handlers.skillhandlers.InstantJump;
import handlers.skillhandlers.ManaHeal;
import handlers.skillhandlers.Manadam;
import handlers.skillhandlers.Mdam;
import handlers.skillhandlers.NornilsPower;
import handlers.skillhandlers.Pdam;
import handlers.skillhandlers.RefuelAirShip;
import handlers.skillhandlers.Resurrect;
import handlers.skillhandlers.ShiftTarget;
import handlers.skillhandlers.Soul;
import handlers.skillhandlers.Sow;
import handlers.skillhandlers.Spoil;
import handlers.skillhandlers.StealBuffs;
import handlers.skillhandlers.StrSiegeAssault;
import handlers.skillhandlers.SummonFriend;
import handlers.skillhandlers.Sweep;
import handlers.skillhandlers.TakeCastle;
import handlers.skillhandlers.TakeFort;
import handlers.skillhandlers.TransformDispel;
import handlers.skillhandlers.Trap;
import handlers.skillhandlers.Unlock;
import handlers.usercommandhandlers.Birthday;
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
import handlers.voicedcommandhandlers.ChatAdmin;
import handlers.voicedcommandhandlers.Debug;
import handlers.voicedcommandhandlers.Lang;
import handlers.voicedcommandhandlers.TvTVoicedInfo;
import handlers.voicedcommandhandlers.Wedding;
import handlers.voicedcommandhandlers.stats;

import java.util.logging.Logger;

import com.l2jserver.Config;
import com.l2jserver.gameserver.handler.ActionHandler;
import com.l2jserver.gameserver.handler.AdminCommandHandler;
import com.l2jserver.gameserver.handler.BypassHandler;
import com.l2jserver.gameserver.handler.ChatHandler;
import com.l2jserver.gameserver.handler.ItemHandler;
import com.l2jserver.gameserver.handler.SkillHandler;
import com.l2jserver.gameserver.handler.UserCommandHandler;
import com.l2jserver.gameserver.handler.VoicedCommandHandler;


/**
 *
 * @author  nBd
 */
public class MasterHandler
{
	private static Logger _log = Logger.getLogger(MasterHandler.class.getName());
	
	private static void loadActionHandlers()
	{
		ActionHandler.getInstance().registerActionHandler(new L2ArtefactInstanceAction());
		ActionHandler.getInstance().registerActionHandler(new L2DecoyAction());
		ActionHandler.getInstance().registerActionHandler(new L2DoorInstanceAction());
		ActionHandler.getInstance().registerActionHandler(new L2ItemInstanceAction());
		ActionHandler.getInstance().registerActionHandler(new L2NpcAction());
		ActionHandler.getInstance().registerActionHandler(new L2PcInstanceAction());
		ActionHandler.getInstance().registerActionHandler(new L2PetInstanceAction());
		ActionHandler.getInstance().registerActionHandler(new L2StaticObjectInstanceAction());
		ActionHandler.getInstance().registerActionHandler(new L2SummonAction());
		ActionHandler.getInstance().registerActionHandler(new L2TrapAction());
		_log.config("Loaded " + ActionHandler.getInstance().size() + "  ActionHandlers");
	}
	
	private static void loadActionShiftHandlers()
	{
		ActionHandler.getInstance().registerActionShiftHandler(new L2DoorInstanceActionShift());
		ActionHandler.getInstance().registerActionShiftHandler(new L2ItemInstanceActionShift());
		ActionHandler.getInstance().registerActionShiftHandler(new L2NpcActionShift());
		ActionHandler.getInstance().registerActionShiftHandler(new L2PcInstanceActionShift());
		ActionHandler.getInstance().registerActionShiftHandler(new L2StaticObjectInstanceActionShift());
		ActionHandler.getInstance().registerActionShiftHandler(new L2SummonActionShift());
		_log.config("Loaded " + ActionHandler.getInstance().sizeShift() + " ActionShiftHandlers");
	}
	
	private static void loadAdminHandlers()
	{
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminAdmin());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminAnnouncements());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminBan());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminBBS());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminBuffs());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminCache());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminCamera());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminChangeAccessLevel());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminClan());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminCreateItem());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminCursedWeapons());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminDebug());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminDelete());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminDisconnect());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminDoorControl());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminEditChar());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminEditNpc());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminEffects());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminElement());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminEnchant());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminEventEngine());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminEvents());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminExpSp());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminFightCalculator());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminFortSiege());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminGeodata());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminGeoEditor());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminGm());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminGmChat());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminGraciaSeeds());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminHeal());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminHelpPage());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminInstance());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminInstanceZone());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminInvul());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminKick());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminKill());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminLevel());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminLogin());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminMammon());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminManor());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminMenu());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminMessages());
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
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminShowQuests());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminShutdown());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminSiege());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminSkill());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminSpawn());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminSummon());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminTarget());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminTeleport());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminTerritoryWar());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminTest());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminTvTEvent());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminUnblockIp());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminVitality());
		AdminCommandHandler.getInstance().registerAdminCommandHandler(new AdminZone());
		_log.config("Loaded " + AdminCommandHandler.getInstance().size() + "  AdminCommandHandlers");
	}
	
	private static void loadBypassHandlers()
	{
		BypassHandler.getInstance().registerBypassHandler(new Augment());
		BypassHandler.getInstance().registerBypassHandler(new Buy());
		BypassHandler.getInstance().registerBypassHandler(new BuyShadowItem());
		BypassHandler.getInstance().registerBypassHandler(new ChatLink());
		BypassHandler.getInstance().registerBypassHandler(new ClanWarehouse());
		BypassHandler.getInstance().registerBypassHandler(new CPRecovery());
		BypassHandler.getInstance().registerBypassHandler(new DrawHenna());
		BypassHandler.getInstance().registerBypassHandler(new Festival());
		BypassHandler.getInstance().registerBypassHandler(new FortSiege());
		BypassHandler.getInstance().registerBypassHandler(new ItemAuctionLink());
		BypassHandler.getInstance().registerBypassHandler(new Link());
		BypassHandler.getInstance().registerBypassHandler(new Loto());
		BypassHandler.getInstance().registerBypassHandler(new ManorManager());
		BypassHandler.getInstance().registerBypassHandler(new Multisell());
		BypassHandler.getInstance().registerBypassHandler(new Observation());
		BypassHandler.getInstance().registerBypassHandler(new OlympiadObservation());
		BypassHandler.getInstance().registerBypassHandler(new OlympiadManagerLink());
		BypassHandler.getInstance().registerBypassHandler(new QuestLink());
		BypassHandler.getInstance().registerBypassHandler(new PlayerHelp());
		BypassHandler.getInstance().registerBypassHandler(new PrivateWarehouse());
		BypassHandler.getInstance().registerBypassHandler(new QuestList());
		BypassHandler.getInstance().registerBypassHandler(new ReceivePremium());
		BypassHandler.getInstance().registerBypassHandler(new ReleaseAttribute());
		BypassHandler.getInstance().registerBypassHandler(new RemoveDeathPenalty());
		BypassHandler.getInstance().registerBypassHandler(new RemoveHennaList());
		BypassHandler.getInstance().registerBypassHandler(new RentPet());
		BypassHandler.getInstance().registerBypassHandler(new RideWyvern());
		BypassHandler.getInstance().registerBypassHandler(new Rift());
		BypassHandler.getInstance().registerBypassHandler(new SkillList());
		BypassHandler.getInstance().registerBypassHandler(new SupportBlessing());
		BypassHandler.getInstance().registerBypassHandler(new SupportMagic());
		BypassHandler.getInstance().registerBypassHandler(new TerritoryStatus());
		BypassHandler.getInstance().registerBypassHandler(new TerritoryWar());
		BypassHandler.getInstance().registerBypassHandler(new VoiceCommand());
		BypassHandler.getInstance().registerBypassHandler(new Wear());
		_log.config("Loaded " + BypassHandler.getInstance().size() + "  BypassHandlers");
	}
	
	private static void loadChatHandlers()
	{
		ChatHandler.getInstance().registerChatHandler(new ChatAll());
		ChatHandler.getInstance().registerChatHandler(new ChatAlliance());
		ChatHandler.getInstance().registerChatHandler(new ChatBattlefield());
		ChatHandler.getInstance().registerChatHandler(new ChatClan());
		ChatHandler.getInstance().registerChatHandler(new ChatHeroVoice());
		ChatHandler.getInstance().registerChatHandler(new ChatParty());
		ChatHandler.getInstance().registerChatHandler(new ChatPartyMatchRoom());
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
		ItemHandler.getInstance().registerItemHandler(new ScrollOfResurrection());
		ItemHandler.getInstance().registerItemHandler(new SoulShots());
		ItemHandler.getInstance().registerItemHandler(new SpiritShot());
		ItemHandler.getInstance().registerItemHandler(new BlessedSpiritShot());
		ItemHandler.getInstance().registerItemHandler(new BeastSoulShot());
		ItemHandler.getInstance().registerItemHandler(new BeastSpiritShot());
		ItemHandler.getInstance().registerItemHandler(new PaganKeys());
		ItemHandler.getInstance().registerItemHandler(new Maps());
		ItemHandler.getInstance().registerItemHandler(new NicknameColor());
		ItemHandler.getInstance().registerItemHandler(new Recipes());
		ItemHandler.getInstance().registerItemHandler(new RollingDice());
		ItemHandler.getInstance().registerItemHandler(new EnchantAttribute());
		ItemHandler.getInstance().registerItemHandler(new EnchantScrolls());
		ItemHandler.getInstance().registerItemHandler(new ExtractableItems());
		ItemHandler.getInstance().registerItemHandler(new Book());
		ItemHandler.getInstance().registerItemHandler(new SevenSignsRecord());
		ItemHandler.getInstance().registerItemHandler(new ItemSkills());
		ItemHandler.getInstance().registerItemHandler(new ItemSkillsTemplate());
		ItemHandler.getInstance().registerItemHandler(new Seed());
		ItemHandler.getInstance().registerItemHandler(new Harvester());
		ItemHandler.getInstance().registerItemHandler(new MercTicket());
		ItemHandler.getInstance().registerItemHandler(new FishShots());
		ItemHandler.getInstance().registerItemHandler(new PetFood());
		ItemHandler.getInstance().registerItemHandler(new SpecialXMas());
		ItemHandler.getInstance().registerItemHandler(new SummonItems());
		ItemHandler.getInstance().registerItemHandler(new BeastSpice());
		ItemHandler.getInstance().registerItemHandler(new TeleportBookmark());
		ItemHandler.getInstance().registerItemHandler(new Elixir());
		ItemHandler.getInstance().registerItemHandler(new Disguise());
		ItemHandler.getInstance().registerItemHandler(new ManaPotion());
		ItemHandler.getInstance().registerItemHandler(new EnergyStarStone());
		ItemHandler.getInstance().registerItemHandler(new EventItem());
		_log.config("Loaded " + ItemHandler.getInstance().size() + " ItemHandlers");
	}
	
	private static void loadSkillHandlers()
	{
		SkillHandler.getInstance().registerSkillHandler(new Blow());
		SkillHandler.getInstance().registerSkillHandler(new Pdam());
		SkillHandler.getInstance().registerSkillHandler(new Mdam());
		SkillHandler.getInstance().registerSkillHandler(new CpDam());
		SkillHandler.getInstance().registerSkillHandler(new CpDamPercent());
		SkillHandler.getInstance().registerSkillHandler(new Manadam());
		SkillHandler.getInstance().registerSkillHandler(new Heal());
		SkillHandler.getInstance().registerSkillHandler(new HealPercent());
		SkillHandler.getInstance().registerSkillHandler(new CombatPointHeal());
		SkillHandler.getInstance().registerSkillHandler(new ManaHeal());
		SkillHandler.getInstance().registerSkillHandler(new BalanceLife());
		SkillHandler.getInstance().registerSkillHandler(new Charge());
		SkillHandler.getInstance().registerSkillHandler(new Continuous());
		SkillHandler.getInstance().registerSkillHandler(new Detection());
		SkillHandler.getInstance().registerSkillHandler(new Resurrect());
		SkillHandler.getInstance().registerSkillHandler(new ShiftTarget());
		SkillHandler.getInstance().registerSkillHandler(new Spoil());
		SkillHandler.getInstance().registerSkillHandler(new Sweep());
		SkillHandler.getInstance().registerSkillHandler(new StrSiegeAssault());
		SkillHandler.getInstance().registerSkillHandler(new SummonFriend());
		SkillHandler.getInstance().registerSkillHandler(new Disablers());
		SkillHandler.getInstance().registerSkillHandler(new Cancel());
		SkillHandler.getInstance().registerSkillHandler(new StealBuffs());
		SkillHandler.getInstance().registerSkillHandler(new BallistaBomb());
		SkillHandler.getInstance().registerSkillHandler(new TakeCastle());
		SkillHandler.getInstance().registerSkillHandler(new TakeFort());
		SkillHandler.getInstance().registerSkillHandler(new Unlock());
		SkillHandler.getInstance().registerSkillHandler(new Craft());
		SkillHandler.getInstance().registerSkillHandler(new Fishing());
		SkillHandler.getInstance().registerSkillHandler(new FishingSkill());
		SkillHandler.getInstance().registerSkillHandler(new BeastSkills());
		SkillHandler.getInstance().registerSkillHandler(new DeluxeKey());
		SkillHandler.getInstance().registerSkillHandler(new Sow());
		SkillHandler.getInstance().registerSkillHandler(new Soul());
		SkillHandler.getInstance().registerSkillHandler(new Harvest());
		SkillHandler.getInstance().registerSkillHandler(new GetPlayer());
		SkillHandler.getInstance().registerSkillHandler(new TransformDispel());
		SkillHandler.getInstance().registerSkillHandler(new Trap());
		SkillHandler.getInstance().registerSkillHandler(new GiveSp());
		SkillHandler.getInstance().registerSkillHandler(new GiveReco());
		SkillHandler.getInstance().registerSkillHandler(new GiveVitality());
		SkillHandler.getInstance().registerSkillHandler(new InstantJump());
		SkillHandler.getInstance().registerSkillHandler(new Dummy());
		SkillHandler.getInstance().registerSkillHandler(new Extractable());
		SkillHandler.getInstance().registerSkillHandler(new RefuelAirShip());
		SkillHandler.getInstance().registerSkillHandler(new NornilsPower());
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
		UserCommandHandler.getInstance().registerUserCommandHandler(new Birthday());
		_log.config("Loaded " + UserCommandHandler.getInstance().size() + " UserHandlers");
	}
	
	private static void loadVoicedHandlers()
	{
		VoicedCommandHandler.getInstance().registerVoicedCommandHandler(new stats());
		if (Config.L2JMOD_ALLOW_WEDDING)
			VoicedCommandHandler.getInstance().registerVoicedCommandHandler(new Wedding());
		if (Config.BANKING_SYSTEM_ENABLED)
			VoicedCommandHandler.getInstance().registerVoicedCommandHandler(new Banking());
		if (Config.TVT_ALLOW_VOICED_COMMAND)
			VoicedCommandHandler.getInstance().registerVoicedCommandHandler(new TvTVoicedInfo());
		if (Config.L2JMOD_CHAT_ADMIN)
			VoicedCommandHandler.getInstance().registerVoicedCommandHandler(new ChatAdmin());
		if (Config.L2JMOD_MULTILANG_ENABLE && Config.L2JMOD_MULTILANG_VOICED_ALLOW)
			VoicedCommandHandler.getInstance().registerVoicedCommandHandler(new Lang());
		if (Config.L2JMOD_DEBUG_VOICE_COMMAND)
			VoicedCommandHandler.getInstance().registerVoicedCommandHandler(new Debug());
		_log.config("Loaded " + VoicedCommandHandler.getInstance().size() + " VoicedHandlers");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		_log.config("Loading Handlers...");
		loadActionHandlers();
		loadActionShiftHandlers();
		loadAdminHandlers();
		loadBypassHandlers();
		loadChatHandlers();
		loadItemHandlers();
		loadSkillHandlers();
		loadUserHandlers();
		loadVoicedHandlers();
		_log.config("Handlers Loaded...");
	}
}