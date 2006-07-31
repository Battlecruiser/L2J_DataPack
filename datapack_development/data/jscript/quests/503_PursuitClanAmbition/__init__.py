# Written by
# questdevs Team

import sys
from java.util									import Iterator
from net.sf.l2j.gameserver.serverpackets		import CreatureSay 
from net.sf.l2j.gameserver.model.quest			import State
from net.sf.l2j.gameserver.model.quest			import QuestState
from net.sf.l2j.gameserver.model.quest.jython	import QuestJython as JQuest
from net.sf.l2j								import L2DatabaseFactory

qn = "503_PursuitClanAmbition"
qd = "Pursuit Clan Ambition"

# Items
# first part
G_Let_Martien = 3866
Th_Wyrm_Eggs = 3842
Drake_Eggs = 3841
Bl_Wyrm_Eggs = 3840
Mi_Drake_Eggs = 3839
Brooch = 3843
Bl_Anvil_Coin = 3871

# second Part
G_Let_Balthazar = 3867
Recipe_Power_Stone = 3838
Power_Stones = 3846
Nebulite_Crystals = 3844
Broke_Power_Stone = 3845

# third part
G_Let_Rodemai = 3868
Imp_Keys = 3847
Scepter_Judgement = 3869

# the final item
Proof_Aspiration = 3870


EggList = [Mi_Drake_Eggs,Bl_Wyrm_Eggs,Drake_Eggs,Th_Wyrm_Eggs]

# NPC = Martien,Athrea,Kalis,Gustaf,Fritz,Lutz,Kurtz,Kusto,Balthazar,Rodemai,Coffer,Cleo
NPC=[7645,7758,7759,7760,7761,7762,7763,7512,7764,7868,7765,7766]
STATS=["cond","Fritz","Lutz","Kurtz","ImpGraveKeeper"]

# DROPLIST = step,chance,maxcount,item 
# condition,maxcount,chance,itemList = DROPLIST[npcId]
DROPLIST = {
282: [2,10,20,[Th_Wyrm_Eggs]], 										# Thunder Wyrm 1
243: [2,10,15,[Th_Wyrm_Eggs]], 										# Thunder Wyrm 2
137: [2,10,20,[Drake_Eggs]], 										# Drake 1
285: [2,10,25,[Drake_Eggs]], 										# Drake 2
5178:[2,10,100,[Bl_Wyrm_Eggs]],										# Blitz Wyrm
654: [5,10,25,[Broke_Power_Stone,Power_Stones,Nebulite_Crystals]],	# Giant Soldier
656: [5,10,35,[Broke_Power_Stone,Power_Stones,Nebulite_Crystals]],	# Giant Scouts
668: [10,0,15,[]],													# Grave Guard
5179:[10,6,80,[Imp_Keys]], 											# GraveKeyKeeper
5181:[10,0,100,[]]													# Imperial Gravekeeper
}

def suscribe_members(st) :
	clan=st.getPlayer().getClan().getClanId()
	con=L2DatabaseFactory.getInstance().getConnection()
	offline=con.prepareStatement("SELECT obj_Id FROM characters WHERE clanid=? AND online=0")
	offline.setInt(1, clan)
	rs=offline.executeQuery()
	while (rs.next()) :
		char_id=rs.getInt("obj_Id")
		try :
			insertion = con.prepareStatement("INSERT INTO character_quests (char_id,name,var,value) VALUES (?,?,?,?)")
			insertion.setInt(1, char_id)
			insertion.setString(2, qn)
			insertion.setString(3, "<state>")
			insertion.setString(4, "Progress")
			insertion.executeUpdate()
			insertion.close();
		except :
			try : insertion.close()
			except : pass
	try :
		con.close()
	except :
		pass

def offlineMemberExit(st) :
	clan=st.getPlayer().getClan().getClanId()
	con=L2DatabaseFactory.getInstance().getConnection()
	offline=con.prepareStatement("DELETE FROM character_quests WHERE name = ? and char_id IN (SELECT obj_id FROM characters WHERE clanId =? AND online=0")
	offline.setString(1, qn)
	offline.setInt(2, clan)
	try :
		offline.executeUpdate()
		offline.close()
		con.close()
	except :
		try : con.close()
		except : pass

# returns leaders quest cond, if he is offline will read out of database :)
def getLeaderVar(st, var) :
	try :
		clan = st.getPlayer().getClan()  
		if clan == None:
			return -1
		leader=clan.getLeader().getPlayerInstance()
		if leader != None :
			return int(leader.getQuestState(qn).get(var))
	except :
		pass
	leaderId=st.getPlayer().getClan().getLeaderId()
	con=L2DatabaseFactory.getInstance().getConnection()
	offline=con.prepareStatement("SELECT value FROM character_quests WHERE char_id=? AND var=? AND name=?")
	offline.setInt(1, leaderId)
	offline.setString(2, var)
	offline.setString(3, qn)
	rs=offline.executeQuery()
	if rs :
		rs.next()
		try :
			val=rs.getInt("value")
			con.close()
		except :
			val=-1
			try : con.close()
			except : pass
	else :
		val=-1
	return int(val)

# set's leaders quest cond, if he is offline will read out of database :)
# for now, if the leader is not logged in, this assumes that the variable
# has already been inserted once (initialized, in some sense).
def setLeaderVar(st, var, value) :
	clan = st.getPlayer().getClan()  
	if clan == None: return   
	leader=clan.getLeader().getPlayerInstance()
	if leader != None :
		leader.getQuestState(qn).set(var,value)
	else :
		leaderId=st.getPlayer().getClan().getLeaderId()
		con=L2DatabaseFactory.getInstance().getConnection()
		offline=con.prepareStatement("UPDATE character_quests SET value=? WHERE char_id=? AND var=? AND name=?")
		offline.setString(1, value)
		offline.setInt(2, leaderId)
		offline.setString(3, var)
		offline.setString(4, qn)
		try :
			offline.executeUpdate()
			offline.close()
			con.close()
		except :
			try : con.close()
			except : pass 
	return

def checkEggs(st):
	count = 0
	for item in EggList:
		if st.getQuestItemsCount(item) > 9:
			count+=1
	if count > 3 :
		return 1
	else:
		return 0

def giveItem(item,maxcount,st):
	count = st.getQuestItemsCount(item)
	if count < maxcount:
		st.giveItems(item,1)
		if count == maxcount-1:
			st.playSound("ItemSound.quest_middle")
		else:
			st.playSound("ItemSound.quest_itemget")
	return

def exit503(completed,st):
		if completed:
			st.giveItems(Proof_Aspiration,1)
			st.addExpAndSp(0,250000)
			for var in STATS:
				st.unset(var)
			st.setState(COMPLETED)
		else:
			st.exitQuest(1)
		st.takeItems(Scepter_Judgement,-1)
		st.getPcSpawn().removeAllSpawn()
		try:
			members = st.getPlayer().getClan().getOnlineMembers("")[0]
			for i in members:
				st.getPlayer().getClan().getClanMember(i).getPlayerInstance().getQuestState(qn).exitQuest(1)
			offlineMemberExit(st)
		except:
			return "You dont have any members in your Clan, so you can't finish the Pursuit of Aspiration"
		return "Congratulations, you have finished the Pursuit of Clan Ambition"

class Quest (JQuest) :

	def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

	def onEvent (self,event,st) :
		htmltext = event
# Events Gustaf
		if event == "7760-08.htm" :
			st.giveItems(G_Let_Martien,1)
			for var in STATS:
				st.set(var,"1")
			st.setState(PROGRESS)
		elif event == "7760-12.htm" :
			st.giveItems(G_Let_Balthazar,1)
			st.set("cond","4")
		elif event == "7760-16.htm" :
			st.giveItems(G_Let_Rodemai,1)
			st.set("cond","7")
		elif event == "7760-20.htm" :
			exit503(1,st)
		elif event == "7760-22.htm" :
			st.set("cond","13")
		elif event == "7760-23.htm" :
			exit503(1,st)
# Events Martien
		elif event == "7645-03.htm":
			st.takeItems(G_Let_Martien,-1)
			st.set("cond","2")
			suscribe_members(st) 
			try:
				members = st.getPlayer().getClan().getOnlineMembers("")[0]
				for i in members:
					st.getPlayer().getClan().getClanMember(int(i)).getPlayerInstance().setQuestState(PROGRESS)
			except:
				return htmltext
# Events Kurtz
		elif event == "7763-03.htm":
			if st.getInt("Kurtz") == 1:
				htmltext = "7763-02.htm"
				st.giveItems(Mi_Drake_Eggs,6)
				st.giveItems(Brooch,1)
				st.set("Kurtz","2")
# Events Lutz
		elif event == "7762-03.htm":
			lutz = st.getInt("Lutz")
			if lutz == 1:
				htmltext = "7762-02.htm"
				st.giveItems(Mi_Drake_Eggs,4)
				st.giveItems(Bl_Wyrm_Eggs,3)
				st.set("Lutz","2")
			st.getPcSpawn().addSpawn(5178,112268,112761,-2770,120000)
			st.getPcSpawn().addSpawn(5178,112234,112705,-2770,120000)
# Events Fritz
		elif event == "7761-03.htm":
			fritz = st.getInt("Fritz")
			if fritz == 1:
				htmltext = "7761-02.htm"
				st.giveItems(Bl_Wyrm_Eggs,3)
				st.set("Fritz","2")
			st.getPcSpawn().addSpawn(5178,103841,116809,-3025,120000)
			st.getPcSpawn().addSpawn(5178,103848,116910,-3020,120000)
# Events Kusto
		elif event == "7512-03.htm":
			st.takeItems(Brooch,1)
			st.giveItems(Bl_Anvil_Coin,1)
			st.set("Kurtz","3")
# Events Balthazar
		elif event == "7764-03.htm":
			st.takeItems(G_Let_Balthazar,-1)
			st.set("cond","5")
			st.set("Kurtz","3")
		elif event == "7764-05.htm":
			st.takeItems(G_Let_Balthazar,-1)
			st.set("cond","5")
		elif event == "7764-06.htm":
			st.takeItems(Bl_Anvil_Coin,-1)
			st.set("Kurtz","4")
			st.giveItems(Recipe_Power_Stone,1)
# Events Rodemai
		elif event == "7868-04.htm":
			st.takeItems(G_Let_Rodemai,-1)
			st.set("cond","8")
		elif event == "7868-06a.htm":
			st.set("cond","10")
		elif event == "7868-10.htm":
			st.set("cond","12")
# Events Cleo
		elif event == "7766-04.htm":
			st.set("cond","9")
			st.getPcSpawn().addSpawn(7766,160622,21230,-3710,100,["Blood and Honour"],0)
			st.getPcSpawn().addSpawn(7759,160665,21209,-3710,6000,["Ambition and Power"],0)
			st.getPcSpawn().addSpawn(7758,160665,21291,-3710,6000,["War and Death"],0)
		elif event == "7766-08.htm":
			st.takeItems(Scepter_Judgement,-1)
			exit503(0,st)
		return htmltext


	def onTalk(self,npc,st):
		npcId = npc.getNpcId()
		id =  st.getState()
		Martien,Athrea,Kalis,Gustaf,Fritz,Lutz,Kurtz,Kusto,Balthazar,Rodemai,Coffer,Cleo = 7645,7758,7759,7760,7761,7762,7763,7512,7764,7868,7765,7766
		htmltext = "<html><head><body>I have nothing to say.</body></html>"
		isLeader = st.getPlayer().isClanLeader()
		if id == CREATED and npcId == Gustaf:
			for var in STATS:																	# adds all the  vars for initialisation
				st.set(var,"0")
			if st.getPlayer().getClan():														# has Clan
				if isLeader:																	# check if player is clan leader
					clanLevel = st.getPlayer().getClan().getLevel()
					if st.getQuestItemsCount(Proof_Aspiration):									# if he has the proof already, tell him what to do now
						htmltext = "7760-03.htm"
						st.exitQuest(1)
					elif clanLevel > 3:															# if clanLevel > 3 you can take this quest, because repeatable
						htmltext = "7760-04.htm"
					else:																		# if clanLevel < 4 you cant take it
						htmltext = "7760-02.htm"
						st.exitQuest(1)
				else:																			# player isnt a leader
					htmltext = "7760-04t.htm"
					st.exitQuest(1)
			else:																				# no Clan
				htmltext = "7760-01.htm"
				st.exitQuest(1)
			return htmltext
		elif st.getPlayer().getClan() and st.getPlayer().getClan().getLevel() == 5:				# player has level 5 clan already
			return "<html><head><body>This quest has already been completed.</body></html>"
		elif id == COMPLETED:																	# player has proof, and has finished quest as leader
			return "7760.htm"
		else:
			######## Leader Area ######
			if isLeader:
				cond 	= st.getInt("cond")
				kurtz	= st.getInt("Kurtz")
				lutz	= st.getInt("Lutz")
				fritz	= st.getInt("Fritz")
				
				if npcId == Gustaf :
					if cond == 1:
						htmltext = "7760-09.htm"
					elif cond == 2:
						htmltext = "7760-10.htm"
					elif cond == 3:
						htmltext = "7760-11.htm"
					elif cond == 4:
						htmltext = "7760-13.htm"
					elif cond == 5:
						htmltext = "7760-14.htm"
					elif cond == 6:
						htmltext = "7760-15.htm"
					elif cond == 7:
						htmltext = "7760-17.htm"
					elif cond == 12:
						htmltext = "7760-19.htm"
					elif cond == 13:
						htmltext = "7760-24.htm"
					else:
						htmltext = "7760-18.htm"
				elif npcId == Martien :
					if cond == 1:
						htmltext = "7645-02.htm"
					elif cond == 2:
						if checkEggs(st) and kurtz > 1 and lutz > 1 and fritz > 1:
							htmltext = "7645-05.htm"
							st.set("cond","3")
							for item in EggList:
								st.takeItems(item,-1)
						else:
							htmltext = "7645-04.htm"
					elif cond == 3:
						htmltext = "7645-07.htm"
					else:
						htmltext = "7645-08.htm"
				elif cond == 2:												# Dwarven Corpse in DV, only needed if condition is 2
					if npcId == Lutz:
						htmltext = "7762-01.htm"
					elif npcId == Kurtz:
						htmltext = "7763-01.htm"
					elif npcId == Fritz:
						htmltext = "7761-01.htm"
				elif npcId == Kusto:
					if kurtz == 1:
						htmltext = "7512-01.htm"
					elif kurtz == 2:
						htmltext = "7512-02.htm"
					else:
						htmltext = "7512-04.htm"
					return htmltext
				elif npcId == Balthazar:
					if cond == 4:
						if kurtz > 2:
							htmltext = "7764-04.htm"
						else:
							htmltext = "7764-02.htm"
					elif cond == 5:
						if st.getQuestItemsCount(Power_Stones) > 9 and st.getQuestItemsCount(Nebulite_Crystals) > 9:
							htmltext = "7764-08.htm"
							st.takeItems(Power_Stones,-1)
							st.takeItems(Nebulite_Crystals,-1)
							st.takeItems(Brooch,-1)
							st.set("cond","6")
						else:
							htmltext = "7764-07.htm"
					elif cond == 6:
						htmltext = "7764-09.htm"
				elif npcId == Rodemai:
					if cond == 7:
						htmltext = "7868-02.htm"
					elif cond == 8:
						htmltext = "7868-05.htm"
					elif cond == 9:
						htmltext = "7868-06.htm"
					elif cond == 10:
						htmltext = "7868-08.htm"
					elif cond == 11:
						htmltext = "7868-09.htm"
					elif cond == 12:
						htmltext = "7868-11.htm"
				elif npcId == Cleo:
					if cond == 8:
						htmltext = "7766-02.htm"
					elif cond == 9:
						htmltext = "7766-05.htm"
					elif cond == 10:
						htmltext = "7766-06.htm"
					elif cond in [11,12,13]:
						htmltext = "7766-07.htm"
				elif npcId == Coffer:
					if st.getInt("cond") == 10:
						if st.getQuestItemsCount(Imp_Keys) < 6:
							htmltext = "7765-03a.htm"
						elif st.getInt("ImpGraveKeeper") == 3:
							htmltext = "7765-02.htm"
							st.set("cond","11")
							st.takeItems(Imp_Keys,6)
							st.giveItems(Scepter_Judgement,1)
							st.getPcSpawn().removeAllSpawn()
						else:
							htmltext = "<html><head><body>(You and your Clan didn't kill the Imperial Gravekeeper by your own, do it try again.)</body></html>"
					else:
							htmltext = "<html><head><body>(You already have the Scepter of Judgement.)</body></html>"
				elif npcId == Kalis:
					htmltext = "7759-01.htm"
				elif npcId == Athrea:
					htmltext = "7758-01.htm"
				return htmltext
			######## Member Area ######
			else:
				cond = getLeaderVar(st,"cond")
				if npcId == Martien and cond in [1,2,3]:
					htmltext = "7645-01.htm"
				elif npcId == Rodemai :
					if cond in [9,10]:
						htmltext = "7868-07.htm"
					elif cond == 7:
						htmltext = "7868-01.htm"
				elif npcId == Balthazar and cond == 4:
					htmltext = "7764-01.htm"
				elif npcId == Cleo and cond == 8:
					htmltext = "7766-01.htm"
				elif npcId == Kusto and 6 > cond > 2:
					htmltext = "7512-01a.htm"
				elif npcId == Coffer and cond == 10:
					htmltext = "7765-01.htm"
				elif npcId == Gustaf:
					if cond == 3:
						htmltext = "7760-11t.htm"
					elif cond == 4:
						htmltext = "7760-15t.htm"
					elif cond == 12:
						htmltext = "7760-19t.htm"
					elif cond == 13:
						htmltext = "7766-24t.htm"
				return htmltext

	def onAttack(self, npc, st):
		npdId = npc.getNpcId()
		if (npc.getMaxHp()/2) > npc.getCurrentHp():
			if st.getRandom(100) < 4:
				ImpGraveKepperStat = getLeaderVar(st,"ImpGraveKeeper")
				if ImpGraveKepperStat == 1:
					for j in range(2):
						for k in range(2): 
							st.getPcSpawn().addSpawn(5180,npc.getX()+70*pow(-1,j%2),npc.getY()+70*pow(-1,k%2),npc.getZ())
					setLeaderVar(st,"ImpGraveKeeper","2")
				else:
					players = npc.getKnownList().getKnownPlayers().toArray()
					if len(players) :
						player = players[st.getRandom(int(len(players)))]
						player.setXYZ(185462,20342,-3250)
		return

	def onKill (self,npc,st):
		npcId=npc.getNpcId()
		condition,maxcount,chance,itemList = DROPLIST[npcId]
		random = st.getRandom(100)
		cond = getLeaderVar(st,"cond")
		if cond == condition and random < chance:
			if len(itemList) > 1:
				stoneRandom = st.getRandom(3)
				if stoneRandom == 0 :
					if getLeaderVar(st,"Kurtz") < 4:
						return
					else:
						maxcount*=4
				giveItem(itemList[stoneRandom],maxcount,st)
			elif len(itemList) :
				giveItem(itemList[0],maxcount,st)
			else:
				if npcId == 5181:								# Imperial Gravekeeper
					st.getPcSpawn().addSpawn(7765,6000000,["Curse of the gods on the one that defiles the property of the empire!"],0)
					setLeaderVar(st,"ImpGraveKeeper","3")
				else:
					st.getPcSpawn().addSpawn(5179)
		return

QUEST		= Quest(503,qn,"Pursuit of Clan Ambition")
CREATED		= State('Start', QUEST)
PROGRESS	= State('Progress', QUEST)
COMPLETED	= State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(NPC[3])

CREATED.addTalkId(NPC[3])

for npcId in NPC:
	PROGRESS.addTalkId(npcId)

for mobId in DROPLIST.keys():
	PROGRESS.addKillId(mobId)

PROGRESS.addAttackId(5181)

for i in range(3839,3848)+range(3866,3870):
    PROGRESS.addQuestDrop(5181,i,1)

print "importing quests: 503: " + qd
