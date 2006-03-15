# Made by Fulminus, version 0.1

import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

PIPETTE_KNIFE = 4665
REIRIAS_SOUL_ORB = 4666
KERMONS_INFERNIUM_SCEPTER = 4667
GOLCONDAS_INFERNIUM_SCEPTER = 4668
HALLATES_INFERNIUM_SCEPTER = 4669
REORINS_HAMMER = 4670
REORINS_MOLD = 4671
INFERNIUM_VARNISH = 4672
RED_PIPETTE_KNIFE = 4673
STAR_OF_DESTINY = 5011
CRYSTAL_B = 1460

#Leorin, Cliff, Ferris, Zenkin, Kaspar, Kernon's Chest, Golkonda's Chest, Hallate's Chest
NPC=[8002,7182,7847,7178,7833,8028,8029,8030]

#mobId=[cond,spawn,dropId,rate]
DROPLIST={
10035: [1,0,REIRIAS_SOUL_ORB,100], 				#Shilen’s Messenger Cabrio
10054: [2,8028,0,0], 							#Demon Kernon
10126: [2,8029,0,0], 							#Golkonda, the Longhorn General
10220: [2,8030,0,0], 							#Death Lord Hallate
12372: [7,0,RED_PIPETTE_KNIFE,10]				#Baium...NOTE: rate may need adjustment
}

## START: Weapon exchange section (any top B grade for 2nd best A grade)
# TopBGradeWeaponData{ weaponType: (max pdam, max mdam)}
#(top weapon has at least one of pdam or mdam equal to the max for its type)
TopBGradeWeaponData = { \
"Big Sword":(213,91), \
"Blunt":(194,132), \
"Bow":(400,100), \
"Dagger":(170,122), \
"Dual Fist":(236,99), \
"Etc":(170,143), \
"Pole":(194,99), \
"Sword":(194,122) }

class Quest (JQuest) :

	def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)
	
	def onEvent (self,event,st) :
		htmltext = event
		#accept quest
		if event == "1" :
			st.set("cond","1")
			st.setState(STARTED)
			htmltext = "8002-04.htm"
		# open Kernon's Chest
		elif event == "8028_01" :
			htmltext = "8028-02.htm"
			st.giveItems(KERMONS_INFERNIUM_SCEPTER,1)
		# open Hallate's Chest
		elif event == "8029_01" :
			htmltext = "8029-02.htm"
			st.giveItems(GOLCONDAS_INFERNIUM_SCEPTER,1)
		# open Golkonda's Chest
		elif event == "8030_01" :
			htmltext = "8030-02.htm"
			st.giveItems(HALLATES_INFERNIUM_SCEPTER,1)
		## FINAL ITEM EXCHANGE SECTION
		elif event == "showWeapons" :
			htmltext = ""
			for Item in st.getPlayer().getInventory().getItems():
				#given an item instance, get the item template to check what type it is
				itemTemplate = Item.getItem()
				weaponType = itemTemplate.getItemType().toString()
				if TopBGradeWeaponData.has_key(weaponType) and not Item.isEquipped() and itemTemplate.getCrystalType() == 0x03:
					# for acceptable types, check if they fit the description of topB from that type.
					pDam, mDam = itemTemplate.getPDamage(), itemTemplate.getMDamage()
					if pDam >= TopBGradeWeaponData[weaponType][0] or mDam >= TopBGradeWeaponData[weaponType][1] :
						htmltext += "<a action=\"bypass -h Quest 234_FatesWhisper selectBGrade_" + str(Item.getObjectId()) +"\">" + itemTemplate.getName() + "+" + str(Item.getEnchantLevel()) + "</a><br>"
			if htmltext == "": 
				htmltext = "You have no acceptable top B grade weapon in your inventory"
			htmltext = "<html><body>Maestro Reorin:<br>Please choose which weapon you wish to give me to melt, from the below list:<br>" + htmltext + "</body></html>"
		elif event.startswith("selectBGrade_"):
			# store the object id of the selected weapon for the trade
			st.set("giveWeapObjectId", event.replace("selectBGrade_", ""))
			# now show the A Grade weapon list
			htmltext = "8002-AGradeList.htm"
		elif event.startswith("selectAGrade_"):
			aGradeItemId = int(event.replace("selectAGrade_", ""))
			bGradeObjId = st.getInt("giveWeapObjectId")
			# to avoid exploitation, check if the stored objectId still corresponds to an existing item
			# and if that item is still not equipped
			Item = st.getPlayer().getInventory().getItemByObjectId(bGradeObjId)
			if Item and not Item.isEquipped() :
				htmltext = "8002-12.htm"
				st.getPlayer().destroyItem("quest_234", bGradeObjId, 1, st.getPlayer(), 0)
				st.giveItems(aGradeItemId,1)
				st.giveItems(STAR_OF_DESTINY,1)
				st.setState(COMPLETED)
				st.unset("cond")
				st.unset("giveWeapObjectId")
			else :
				htmltext = "<html><body>Maestro Reorin:<br>Are you trying to cheat me?!  What happenned to the weapon you were about to give me for the neutralization of Infernum's evil aura?</body></html>"
		return htmltext

	def onTalk(self,npc,st):
		npcId=npc.getNpcId()
		id =  st.getState()
		htmltext = "<html><head><body>I have nothing to say to you.</body></html>"
		# first time when a player join the quest
		if id == CREATED:
			if st.getPlayer().getLevel() >= 75:
				htmltext = "8002-02.htm"
			else:
				htmltext = "8002-01.htm"
				st.exitQuest(1)
			return htmltext
		# if quest is already completed
		elif id == COMPLETED:
			return "<html><head><body>This quest has already been completed.</body></html>"
		# if quest is accepted and in progress
		elif id == STARTED:
			cond =st.getInt("cond")
			if npcId == NPC[0] :
				if cond == 1 and not st.getQuestItemsCount(REIRIAS_SOUL_ORB) :  # waiting for the orb
					htmltext = "8002-04.htm"
				elif cond == 1 :	#got the orb!  Go to the next step (infernium scepter pieces)
					st.takeItems(REIRIAS_SOUL_ORB,1)
					htmltext = "8002-05.htm"
					st.set("cond","2")
				# waiting for infernium scepter pieces
				elif cond == 2 and (st.getQuestItemsCount(KERMONS_INFERNIUM_SCEPTER)+st.getQuestItemsCount(GOLCONDAS_INFERNIUM_SCEPTER)+st.getQuestItemsCount(HALLATES_INFERNIUM_SCEPTER) < 3) :
					htmltext = "8002-05a.htm"
				elif cond == 2 :	#got the infernium scepter pieces!  Go to the next step (infernium Varnish)
					st.takeItems(KERMONS_INFERNIUM_SCEPTER,1)
					st.takeItems(GOLCONDAS_INFERNIUM_SCEPTER,1)
					st.takeItems(HALLATES_INFERNIUM_SCEPTER,1)
					htmltext = "8002-06.htm"
					st.set("cond","3")
				# waiting for infernium varnish
				elif cond == 3 and not st.getQuestItemsCount(INFERNIUM_VARNISH) :
					htmltext = "8002-06a.htm"
				elif cond == 3 :	#got the infernium varnish!  Go to the next step (Leorin's Hammer)
					st.takeItems(INFERNIUM_VARNISH,1)
					htmltext = "8002-07.htm"
					st.set("cond","4")
				# waiting for Leorin's Hammer
				elif cond == 4 and not st.getQuestItemsCount(REORINS_HAMMER) :
					htmltext = "8002-07a.htm"
				elif cond == 4 :	# got Leorin's Hammer!  Go to the next step (Leorin's Mold)
					st.takeItems(REORINS_HAMMER,1)
					htmltext = "8002-08.htm"
					st.set("cond","5")
				elif cond < 8 :	 	# waiting for Leorin's Mold
					htmltext = "8002-08a.htm"
				elif cond == 8 :	# got Leorin's Mold!  Go to the next step (B Crystals)
					st.takeItems(REORINS_MOLD,1)
					htmltext = "8002-09.htm"
					st.set("cond","9")
				# waiting for 984 B Grade Crystals
				elif cond == 9 and (st.getQuestItemsCount(CRYSTAL_B) < 984) :
					htmltext = "8002-09a.htm"
				elif cond == 9 : # got the crystals
					st.takeItems(CRYSTAL_B,984)
					htmltext = "8002-10.htm"
					st.set("cond","10")
				# all is ready.  Now give a menu to trade the B weapon for the player's choice of A Weapon.
				elif cond == 10:
					htmltext = "8002-11.htm"
			## CLIFF.
			# came to take the varnish
			elif npcId == NPC[1] and cond==3 and not st.getQuestItemsCount(INFERNIUM_VARNISH) :
				htmltext = "7182-01.htm"
				st.giveItems(INFERNIUM_VARNISH,1)
			# you already got the varnish...why are you back?
			elif npcId == NPC[1] and (cond>=3 or st.getQuestItemsCount(INFERNIUM_VARNISH)) :
				htmltext = "7182-02.htm"
			## FERRIS
			# go to take the mold			
			elif npcId == NPC[2] and cond==4 and not st.getQuestItemsCount(REORINS_HAMMER) :
				htmltext = "7847-01.htm"	# go to trader Zenkin
				st.giveItems(REORINS_HAMMER,1)
			# I already told you I don't have it!  
			elif npcId == NPC[2] and cond>=4 :
				htmltext = "7847-02.htm"	# go to trader Zenkin
			## ZENKIN
			# go to take mold
			elif npcId == NPC[3] and cond==5 :
				htmltext = "7178-01.htm"	# go to Magister Kaspar
				st.set("cond","6")
			# I already told you I don't have it!  
			elif npcId == NPC[3] and cond>5 :
				htmltext = "7178-02.htm"	# go to Magister Kaspar
			## KASPAR
			elif npcId == NPC[4]:
				# first visit: You have neither plain nor blooded knife.
				if cond==6 :
					htmltext = "7833-01.htm"	# go to Magister Hanellin,etc. Get Baium's Blood with the pipette
					st.giveItems(PIPETTE_KNIFE,1)
					st.set("cond","7")
				# revisit before getting the blood: remind "go get the blood"
				if cond==7 and st.getQuestItemsCount(PIPETTE_KNIFE) and not st.getQuestItemsCount(RED_PIPETTE_KNIFE) :
					htmltext = "7833-02.htm"	# go to Magister Hanellin,etc. Get Baium's Blood with the pipette
				# got the blood and I'm ready to proceed
				if cond==7 and not st.getQuestItemsCount(PIPETTE_KNIFE) and st.getQuestItemsCount(RED_PIPETTE_KNIFE) :
					htmltext = "7833-03.htm"	# great! Here is your mold for Leorin
					st.takeItems(RED_PIPETTE_KNIFE,1)
					st.giveItems(REORINS_MOLD,1)
					st.set("cond","8")
				#revisit after you've gotten the mold: What are you still doing here?
				if st.getInt("cond")>7 :
					htmltext = "7833-04.htm"	# Have you given the mold to Leorin, yet?
			## CHESTS FROM RAIDBOSSES
			elif cond==2 :
				# Kernon's Chest
				if npcId == NPC[5] and st.getQuestItemsCount(KERMONS_INFERNIUM_SCEPTER)==0 :
					htmltext = "8028-01.htm"
				elif npcId == NPC[5] :
					htmltext = "<html><head><body>This chest looks empty</body></html>"
				# Golkonda's Chest
				elif npcId == NPC[6] and st.getQuestItemsCount(GOLCONDAS_INFERNIUM_SCEPTER)==0 :
					htmltext = "8029-01.htm"
				elif npcId == NPC[6] :
					htmltext = "<html><head><body>This chest looks empty</body></html>"
				# Hallate's Chest 
				elif npcId == NPC[7] and st.getQuestItemsCount(HALLATES_INFERNIUM_SCEPTER)==0 :
					htmltext = "8030-01.htm"
				elif npcId == NPC[7] :
					htmltext = "<html><head><body>This chest looks empty</body></html>"
		return htmltext		

	def onAttack (self, npc, st):                   
		npcId = npc.getNpcId()
		value,dummy,dropId,chance = DROPLIST[npcId]
		if value == st.getInt("cond") and npcId==12372 :
			if st.getPlayer().getActiveWeaponItem().getItemId() == PIPETTE_KNIFE and st.getRandom(100)<chance and st.getQuestItemsCount(dropId) == 0:
				st.giveItems(dropId,1)
				st.takeItems(PIPETTE_KNIFE,1)
				st.playSound("Itemsound.quest_itemget")
		return

	def onKill (self,npc,st):
		npcId=npc.getNpcId()
		value,spawnId,dropId,chance = DROPLIST[npcId]
		if st.getInt("cond") == value:
			if chance > 0:
				if st.getRandom(100) < chance and st.getQuestItemsCount(dropId) == 0:
					st.giveItems(dropId,1)
					st.playSound("Itemsound.quest_itemget")
					if npcId == 12372:
						st.takeItems(PIPETTE_KNIFE,1)
			if spawnId > 0 :
				st.getPcSpawn().addSpawn(spawnId,npc.getX(),npc.getY(),npc.getZ(),120000)
		return		


QUEST		= Quest(234,"234_FatesWhisper","Fate's Whisper")
CREATED		= State('Start', QUEST)
STARTED		= State('Started', QUEST)
COMPLETED	= State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(NPC[0])

CREATED.addTalkId(NPC[0])

for npcId in NPC:
	STARTED.addTalkId(npcId)
	
for mobId in DROPLIST.keys() :
	STARTED.addKillId(mobId)

STARTED.addAttackId(12372)	
	
print "importing quests: 234: Fate's Whisper"
