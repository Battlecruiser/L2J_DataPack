from net.sf.l2j.gameserver.instancemanager        import InstanceManager
from net.sf.l2j.gameserver.model                  import L2ItemInstance
from net.sf.l2j.gameserver.model.actor            import L2Summon
from net.sf.l2j.gameserver.model.entity           import Instance
from net.sf.l2j.gameserver.model.itemcontainer    import PcInventory
from net.sf.l2j.gameserver.model.quest            import State
from net.sf.l2j.gameserver.model.quest            import QuestState
from net.sf.l2j.gameserver.model.quest.jython     import QuestJython as JQuest
from net.sf.l2j.gameserver.network.serverpackets  import CreatureSay
from net.sf.l2j.gameserver.network.serverpackets  import InventoryUpdate
from net.sf.l2j.gameserver.network.serverpackets  import MagicSkillUse
from net.sf.l2j.gameserver.network.serverpackets  import SystemMessage
from net.sf.l2j.gameserver.network                import SystemMessageId
from net.sf.l2j.gameserver.util                   import Util
from net.sf.l2j.util                              import Rnd


qn = "DarkCloudMansion"

debug = False

#Items
CC = 9690 #Contaminated Crystal

#NPCs
YIYEN       = 32282
SOFaith     = 32288 #Symbol of Faith
SOAdversity = 32289 #Symbol of Adversity
SOAdventure = 32290 #Symbol of Anventure
SOTruth     = 32291 #Symbol of Truth
BSM         = 32324 #Black Stone Monolith
SC          = 22402 #Shadow Column

#Mobs
CCG = [18369,18370] #Chromatic Crystal Golem
BM  = [22272,22273,22274] #Beleth's Minions
HG  = [22264,22264] #[22318,22319] #Hall Guards
BS  = [18371,18372,18373,18374,18375,18376,18377] #Beleth's Samples

#Doors/Walls
D1 = 24230001 #Starting Room
D2 = 24230002 #First Room
D3 = 24230005 #Second Room
D4 = 24230003 #Third Room
D5 = 24230004 #Forth Room
D6 = 24230006 #Fifth Room
W1 = 24230007 #Wall 1
W2 = 24230008 #Wall 2
W3 = 24230009 #Wall 3
W4 = 24230010 #Wall 4
W5 = 24230011 #Wall 5
W6 = 24230012 #Wall 6
W7 = 24230013 #Wall 7

#Second room - random monolith order
order = [
         [1,2,3,4,5,6],
         [6,5,4,3,2,1],
         [4,5,6,3,2,1],
         [2,6,3,5,1,4],
         [4,1,5,6,2,3],
         [3,5,1,6,2,4],
         [6,1,3,4,5,2],
         [5,6,1,2,4,3],
         [5,2,6,3,4,1],
         [1,5,2,6,3,4],
         [1,2,3,6,5,4],
         [6,4,3,1,5,2],
         [3,5,2,4,1,6],
         [3,2,4,5,1,6],
         [5,4,3,1,6,2]
        ]

#Second room - golem spawn locatons - random
golems = [
          [CCG[0],148060,181389],
          [CCG[1],147910,181173],
          [CCG[0],147810,181334],
          [CCG[1],147713,181179],
          [CCG[0],147569,181410],
          [CCG[1],147810,181517],
          [CCG[0],147805,181281]
         ]

#forth room - random shadow column
rows = [
       [1,1,0,1,0],
       [0,1,1,0,1],
       [1,0,1,1,0],
       [0,1,0,1,1],
       [1,0,1,0,1]
      ]

#Fifth room - beleth order
beleths = [
           [1,0,1,0,1,0,0],
           [0,0,1,0,1,1,0],
           [0,0,0,1,0,1,1],
           [1,0,1,1,0,0,0],
           [1,1,0,0,0,1,0],
           [0,1,0,1,0,1,0],
           [0,0,0,1,1,1,0],
           [1,0,1,0,0,1,0],
           [0,1,1,0,0,0,1]
          ]

class PyObject:
	pass

def openDoor(doorId,instanceId):
	for door in InstanceManager.getInstance().getInstance(instanceId).getDoors():
		if door.getDoorId() == doorId:
			door.openMe()

def checkConditions(player, new):
	party = player.getParty()
	if not party:
		player.sendPacket(SystemMessage.sendString("You are not currently in a party, so you cannot enter."))
		return False
	if party and party.getMemberCount() > 2:
		player.sendPacket(SystemMessage.sendString("You cannot enter due to the party having exceeded the limit."))
		return False
	for partyMember in party.getPartyMembers().toArray():
		if not partyMember.getLevel() >= 78:
			player.sendPacket(SystemMessage.sendString(partyMember.getName()+"s level requirement is not sufficient and cannot be entered."))
			return False
		if not Util.checkIfInRange(1000, player, partyMember, True) and new:
			player.sendPacket(SystemMessage.sendString(partyMember.getName()+" is in a location which cannot be entered, therefore it cannot be processed."))
			return False
	return True

def teleportplayer(self,player,teleto):
	player.setInstanceId(teleto.instanceId)
	player.teleToLocation(teleto.x, teleto.y, teleto.z)
	pet = player.getPet()
	if pet != None :
		pet.setInstanceId(teleto.instanceId)
		pet.teleToLocation(teleto.x, teleto.y, teleto.z)
	return

def enterInstance(self,player,template,teleto):
	instanceId = 0
	party = player.getParty()
	#check for exising instances of party members
	if party :
		for partyMember in party.getPartyMembers().toArray():
			st = partyMember.getQuestState(qn)
			if not st : st = self.newQuestState(partyMember)
			if partyMember.getInstanceId()!=0:
				instanceId = partyMember.getInstanceId()
				if debug: print "DarkCloudMansion: found party member in instance:"+str(instanceId)
	else :
		if player.getInstanceId()!=0:
			instanceId = player.getInstanceId()
	#exising instance
	if instanceId != 0:
		if not checkConditions(player,False):
			return 0
		foundworld = False
		for worldid in self.world_ids:
			if worldid == instanceId:
				foundworld = True
		if not foundworld:
			player.sendPacket(SystemMessage.sendString("You have entered another instance zone, therefore you cannot enter corresponding dungeon."))
			return 0
		teleto.instanceId = instanceId
		teleportplayer(self,player,teleto)
		return instanceId
	#new instance
	else:
		if not checkConditions(player,True):
			return 0
		instanceId = InstanceManager.getInstance().createDynamicInstance(template)
		if not instanceId in self.world_ids:
			world = PyObject()
			world.rewarded=[]
			world.instanceId = instanceId
			self.worlds[instanceId]=world
			self.world_ids.append(instanceId)
			print "DarkCloudMansion: started " + template + " Instance: " +str(instanceId) + " created by player: " + str(player.getName())
			runStartRoom(self,world)
		# teleports player
		teleto.instanceId = instanceId
		for partyMember in party.getPartyMembers().toArray():
			teleportplayer(self,partyMember,teleto)
		return instanceId
	return instanceId

def exitInstance(player,tele):
	player.setInstanceId(0)
	player.teleToLocation(tele.x, tele.y, tele.z)
	pet = player.getPet()
	if pet != None :
		pet.setInstanceId(0)
		pet.teleToLocation(tele.x, tele.y, tele.z)

def runStartRoom(self,world):
	world.status=0
	world.startRoom = PyObject()
	world.startRoom.npclist = {}
	newNpc = self.addSpawn(BM[0],146817,180335,-6117,0,False,0,False, world.instanceId)
	world.startRoom.npclist[newNpc]=False
	newNpc = self.addSpawn(BM[0],146741,180589,-6117,0,False,0,False, world.instanceId)
	world.startRoom.npclist[newNpc]=False
	if debug: print "DarkCloudMansion: first room spawned in instance " + str(world.instanceId)

def spawnHall(self,world):
	world.Hall = PyObject()
	world.Hall.npclist = {}
	newNpc = self.addSpawn(BM[1],147217,180112,-6117,0,False,0,False, world.instanceId)
	world.Hall.npclist[newNpc]=False
	newNpc = self.addSpawn(BM[2],147217,180209,-6117,0,False,0,False, world.instanceId)
	world.Hall.npclist[newNpc]=False
	newNpc = self.addSpawn(BM[1],148521,180112,-6117,0,False,0,False, world.instanceId)
	world.Hall.npclist[newNpc]=False
	newNpc = self.addSpawn(BM[0],148521,180209,-6117,0,False,0,False, world.instanceId)
	world.Hall.npclist[newNpc]=False
	newNpc = self.addSpawn(BM[1],148525,180910,-6117,0,False,0,False, world.instanceId)
	world.Hall.npclist[newNpc]=False
	newNpc = self.addSpawn(BM[2],148435,180910,-6117,0,False,0,False, world.instanceId)
	world.Hall.npclist[newNpc]=False
	newNpc = self.addSpawn(BM[1],147242,180910,-6117,0,False,0,False, world.instanceId)
	world.Hall.npclist[newNpc]=False
	newNpc = self.addSpawn(BM[2],147242,180819,-6117,0,False,0,False, world.instanceId)
	world.Hall.npclist[newNpc]=False
	if debug: print "DarkCloudMansion: hall spawned"

def runHall(self,world):
	world.status=1
	openDoor(D1,world.instanceId)
	spawnHall(self,world)

def runFirstRoom(self,world):
	world.status=2
	openDoor(D2,world.instanceId)
	world.FirstRoom = PyObject()
	world.FirstRoom.npclist = {}
	newNpc = self.addSpawn(HG[1],147842,179837,-6117,0,False,0,False, world.instanceId)
	world.FirstRoom.npclist[newNpc]=False
	newNpc = self.addSpawn(HG[0],147711,179708,-6117,0,False,0,False, world.instanceId)
	world.FirstRoom.npclist[newNpc]=False
	newNpc = self.addSpawn(HG[1],147842,179552,-6117,0,False,0,False, world.instanceId)
	world.FirstRoom.npclist[newNpc]=False
	newNpc = self.addSpawn(HG[0],147964,179708,-6117,0,False,0,False, world.instanceId)
	world.FirstRoom.npclist[newNpc]=False
	if debug: print "DarkCloudMansion: spawned first room"  

def runHall2(self,world):
	world.status=3
	spawnHall(self,world)

def runSecondRoom(self,world):
	newNpc = self.addSpawn(SOFaith,147818,179643,-6117,0,False,0,False,world.instanceId)
	world.status=4
	openDoor(D3,world.instanceId)
	world.SecondRoom = PyObject()
	world.SecondRoom.monolith = []
	i = Rnd.get(len(order))
	a,b,c,d,e,f = order[i]
	world.SecondRoom.monolithOrder=[1,0,0,0,0,0,0]
	newNpc = self.addSpawn(BSM,147800,181150,-6117,0,False,0,False, world.instanceId)
	world.SecondRoom.monolith.append([newNpc,a,0])
	newNpc = self.addSpawn(BSM,147900,181215,-6117,0,False,0,False, world.instanceId)
	world.SecondRoom.monolith.append([newNpc,b,0])
	newNpc = self.addSpawn(BSM,147900,181345,-6117,0,False,0,False, world.instanceId)
	world.SecondRoom.monolith.append([newNpc,c,0])
	newNpc = self.addSpawn(BSM,147800,181410,-6117,0,False,0,False, world.instanceId)
	world.SecondRoom.monolith.append([newNpc,d,0])
	newNpc = self.addSpawn(BSM,147700,181345,-6117,0,False,0,False, world.instanceId)
	world.SecondRoom.monolith.append([newNpc,e,0])
	newNpc = self.addSpawn(BSM,147700,181215,-6117,0,False,0,False, world.instanceId)
	world.SecondRoom.monolith.append([newNpc,f,0])
	if debug: print "DarkCloudMansion: spawned second room"

def runHall3(self,world):
	newNpc = self.addSpawn(SOAdversity,147808,181281,-6117,16383,False,0,False,world.instanceId)
	world.status=5
	spawnHall(self,world)

def runThirdRoom(self,world):
	world.status=6
	openDoor(D4,world.instanceId)
	world.ThirdRoom = PyObject()
	world.ThirdRoom.npclist = {}
	newNpc = self.addSpawn(BM[1],148765,180450,-6117,0,False,0,False,world.instanceId)
	world.ThirdRoom.npclist[newNpc]=False
	newNpc = self.addSpawn(BM[2],148865,180190,-6117,0,False,0,False,world.instanceId)
	world.ThirdRoom.npclist[newNpc]=False
	newNpc = self.addSpawn(BM[1],148995,180190,-6117,0,False,0,False,world.instanceId)
	world.ThirdRoom.npclist[newNpc]=False
	newNpc = self.addSpawn(BM[0],149090,180450,-6117,0,False,0,False,world.instanceId)
	world.ThirdRoom.npclist[newNpc]=False
	newNpc = self.addSpawn(BM[1],148995,180705,-6117,0,False,0,False,world.instanceId)
	world.ThirdRoom.npclist[newNpc]=False
	newNpc = self.addSpawn(BM[2],148865,180705,-6117,0,False,0,False,world.instanceId)
	world.ThirdRoom.npclist[newNpc]=False
	if debug: print "DarkCloudMansion: spawned third room"

def runForthRoom(self,world):
	world.status = 7
	openDoor(D5,world.instanceId)
	world.ForthRoom = PyObject()
	world.ForthRoom.npclist = []
	world.ForthRoom.counter = 0
	temp = []
	templist = []
	xx = 0
	for i in range(0,7):
		temp.append(Rnd.get(len(rows)))
	a,b,c,d,e,f,g = temp
	world.ForthRoom.colmnOrder = []
	world.ForthRoom.colmnOrder.append([a,b,c,d,e,f,g])
	for i in range(0,len(temp)) :
		templist.append(rows[temp[i]])
	for x in range(148660,149285,125) :
		yy = 0
		for y in range(179280,178405,-125) :
			newNpc = self.addSpawn(SC,x,y,-6115,16215,False,0,False,world.instanceId)
			world.ForthRoom.npclist.append([newNpc,templist[yy][xx],yy])
			yy += 1
		xx +=1
	for npc in world.ForthRoom.npclist:
		if npc[1] == 0 :
			npc[0].setIsInvul(True)
	if debug: print "DarkCloudMansion: spawned forth room"

def runFifthRoom(self,world):
	world.status = 8
	openDoor(D6,world.instanceId)
	world.FifthRoom = PyObject()
	world.FifthRoom.npclist = []
	self.addSpawn(SOAdventure,148910,178397,-6117,16383,False,0,False,world.instanceId)
	a,b,c,d,e,f,g = beleths[Rnd.get(len(beleths))]
	world.FifthRoom.belethOrder = []
	world.FifthRoom.belethOrder.append([a,b,c,d,e,f,g])
	temp = [a,b,c,d,e,f,g]
	spawnFifthRoom(self,world)
	if debug: print "DarkCloudMansion: spawned fifth room"

def spawnFifthRoom(self,world):
	idx = 0
	world.FifthRoom.npclist = []
	temp = world.FifthRoom.belethOrder[0]
	for x in range(148720,149175,65):
		newNpc = self.addSpawn(BS[idx],x,182145,-6117,48810,False,0,False,world.instanceId)
		world.FifthRoom.npclist.append([newNpc,idx,temp[idx],0])
		idx += 1

def checkKillProgress(npc,room):
	cont = True
	if npc in room.npclist:
		room.npclist[npc] = True
	for npc in room.npclist.keys():
		if room.npclist[npc] == False:
			cont = False
	if debug: print str(room.npclist)
	return cont

def spawnRndGolem(self,world):
	i = Rnd.get(len(golems))
	mobId,x,y = golems[i]
	newNpc = self.addSpawn(mobId,x,y,-6117,0,False,0,False,world.instanceId)

def checkStone(self,npc,order,npcObj,world) :
	for i in range(1,7) :
		if order[i] == 0 and order[i-1] != 0 :
			if npcObj[1] == i and npcObj[2] == 0 :
				order[i] = 1
				npcObj[2] = 1
				npc.broadcastPacket(MagicSkillUse(npc, npc, 5441, 1, 1, 0))
				return
	spawnRndGolem(self,world)

def endInstance(self,world):
	world.status = 9
	newNpc = self.addSpawn(SOTruth,148911,181940,-6117,16383,False,0,False,world.instanceId)
	world.startRoom = None
	world.Hall = None
	world.SecondRoom = None
	world.ThirdRoom = None
	world.ForthRoom = None
	world.FifthRoom = None
	if debug: print "DarkCloudMansion: finished"

def checkBelethSample(self,world,npc,player,BS):
	for mob in world.FifthRoom.npclist:
		if mob[0] == npc:
			if mob[2] == 1:
				player.sendPacket(CreatureSay(npc.getObjectId(), 0, npc.getName(), "You have done well!"))
				mob[2] = 0
			else:
				player.sendPacket(CreatureSay(npc.getObjectId(), 0, npc.getName(), "This is fake!"))
				mob[2] = 2

	for mob in world.FifthRoom.npclist:
		if mob[2] == 0:
			continue
		else:
			if mob[2] == 2:
				for mob in world.FifthRoom.npclist:
					mob[0].decayMe()
				spawnFifthRoom(self,world)
				return
			else:
				return
			
	for mob in world.FifthRoom.npclist:
		mob[0].decayMe()
	endInstance(self,world)

def allStonesDone(self,world) :
	for npc in world.SecondRoom.monolith :
		if npc[2] == 1 :
			continue
		else:
			return False
	return True

def removeMonoliths(self,world):
	for npc in world.SecondRoom.monolith :
		npc[0].decayMe()

def chkShadowColumn(self,world,npc):
	for mob in world.ForthRoom.npclist:
		if mob[0] == npc :
			for i in range(0,7):
				if mob[2] == i and world.ForthRoom.counter == i :
					openDoor(W1+i,world.instanceId)
					world.ForthRoom.counter += 1 
					if world.ForthRoom.counter == 7:
						runFifthRoom(self,world)

class DarkCloudMansion(JQuest):
	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.worlds = {}
		self.world_ids = []

	def onTalk (self,npc,player):
		npcId = npc.getNpcId()
		if npcId == YIYEN :
			tele = PyObject()
			tele.x = 146534
			tele.y = 180464
			tele.z = -6117
			enterInstance(self, player, "DarkCloudMansion.xml", tele)
		if npc.getInstanceId() in self.worlds:
			world = self.worlds[npc.getInstanceId()]
			if npcId == SOTruth :
				tele = PyObject()
				tele.x = 139968
				tele.y = 150367
				tele.z = -3111
				for partyMember in player.getParty().getPartyMembers().toArray():
					if Util.checkIfInRange(10000, player, partyMember, True) :
						exitInstance(partyMember,tele)
						if partyMember.getObjectId() in world.rewarded:
							pass
						else:
							st = partyMember.getQuestState(qn)
							if not st :
								st = self.newQuestState(partyMember)
							st.giveItems(CC,1);
					
						if debug: print "DarkCloudMansion - id"+str(partyMember.getObjectId())+" added to rewardist"
						world.rewarded.append(partyMember.getObjectId())
				instanceId = npc.getInstanceId()
				instance = InstanceManager.getInstance().getInstance(instanceId)
				if instance.getPlayers() == None or instance.getPlayers().size() < 1 :
					InstanceManager.getInstance().destroyInstance(instanceId)
					self.world_ids.remove(instanceId)
					self.worlds[instanceId]=None
				return
		return

	def onKill(self,npc,player,isPet):
		npcId = npc.getNpcId()
		if npc.getInstanceId() in self.worlds:
			world = self.worlds[npc.getInstanceId()]
			if world.status==0:
				if checkKillProgress(npc,world.startRoom):
					runHall(self,world)
			if world.status==1:
				if checkKillProgress(npc,world.Hall):
					runFirstRoom(self,world)
			if world.status==2:
				if checkKillProgress(npc,world.FirstRoom):
					runHall2(self,world)
			if world.status==3:
				if checkKillProgress(npc,world.Hall):
					runSecondRoom(self,world)
			if world.status==5:
				if checkKillProgress(npc,world.Hall):
					runThirdRoom(self,world)
			if world.status==6:
				if checkKillProgress(npc,world.ThirdRoom):
					runForthRoom(self,world)
			if world.status==7:
					chkShadowColumn(self,world,npc)
			if world.status==8:
					checkBelethSample(self,world,npc,player,BS)
		return

	def onAttack(self,npc,player,damage,isPet,skill):
		npcId = npc.getNpcId()
		world = self.worlds[player.getInstanceId()]
		if world.status == 7:
			for mob in world.ForthRoom.npclist:
				if mob[0] == npc :
					if mob[0].isInvul() and Rnd.get(100) < 12 :
						if debug: print "DarkCloudMansion: spawn room 4 guard"
						newNpc = self.addSpawn(BM[Rnd.get(len(BM))],player.getX(),player.getY(),player.getZ(),0,False,0,False,world.instanceId)

	def onFirstTalk (self,npc,player):
		npcId = npc.getNpcId()
		world = self.worlds[player.getInstanceId()]
		if world.status==4:
			for npcObj in world.SecondRoom.monolith:
				if npcObj[0] == npc:
					checkStone(self,npc,world.SecondRoom.monolithOrder,npcObj,world)
			if allStonesDone(self,world) : 
				removeMonoliths(self,world)
				runHall3(self,world)
		return ""

QUEST = DarkCloudMansion(-1, qn, "DCM")
QUEST.addFirstTalkId(BSM)
QUEST.addStartNpc(YIYEN)
QUEST.addTalkId(YIYEN)
QUEST.addTalkId(SOTruth)
QUEST.addAttackId(SC)
for mob in [18371,18372,18373,18374,18375,18376,18377,22318,22319,22272,22273,22274,18369,18370,22402,22264]:
  QUEST.addKillId(mob)
