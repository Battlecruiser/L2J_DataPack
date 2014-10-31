# Created by Gnacik
# 2010-02-16 based on official Franz server

import sys
from com.l2jserver.gameserver.model.quest				import State
from com.l2jserver.gameserver.model.quest				import QuestState
from com.l2jserver.gameserver.model.quest.jython	import QuestJython as JQuest

qn = "311_ExpulsionOfEvilSpirits"

CHAIREN	= 32655

SOUL_CORE = 14881
SOUL_PENDANT = 14848
RAGNA_ORCS_AMULET = 14882

DROP_CHANCE = 20

MOBS = [22691,22692,22693,22694,22695,22696,22697,22698,22699,22700,22701,22702]


class Quest (JQuest) :

	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [SOUL_CORE,RAGNA_ORCS_AMULET]

	def onExchangeRequest (self,event,st,qty) :
		st.giveItems(int(event),1)
		st.takeItems(RAGNA_ORCS_AMULET,qty)
		st.playSound("ItemSound.quest_finish")
		return "32655-13ok.htm"

	def onAdvEvent (self,event,npc,player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return
		if event == "32655-yes.htm" :
			st.set("cond","1")
			st.setState(State.STARTED)
			st.playSound("ItemSound.quest_accept")
		elif event.isdigit() :
			f = st.getQuestItemsCount(RAGNA_ORCS_AMULET)
			if int(event) == 9482 and f >= 488 :						# Recipe: Sealed Dynasty Breast Plate (60%)
				htmltext = self.onExchangeRequest(event,st,488)
			elif int(event) == 9483 and f >= 305 :						# Recipe: Sealed Dynasty Gaiter (60%)
				htmltext = self.onExchangeRequest(event,st,305)
			elif int(event) == 9484 and f >= 183 :						# Recipe: Sealed Dynasty Helmet (60%)
				htmltext = self.onExchangeRequest(event,st,183)
			elif int(event) == 9485 and f >= 122 :						# Recipe: Sealed Dynasty Gauntlet (60%)
				htmltext = self.onExchangeRequest(event,st,122)
			elif int(event) == 9486 and f >= 122 :						# Recipe: Sealed Dynasty Boots (60%)
				htmltext = self.onExchangeRequest(event,st,122)
			elif int(event) == 9487 and f >= 366 :						# Recipe: Sealed Dynasty Leather Armor (60%)
				htmltext = self.onExchangeRequest(event,st,366)
			elif int(event) == 9488 and f >= 229 :						# Recipe: Sealed Dynasty Leather Leggings (60%)
				htmltext = self.onExchangeRequest(event,st,229)
			elif int(event) == 9489 and f >= 183 :						# Recipe: Sealed Dynasty Leather Helmet (60%)
				htmltext = self.onExchangeRequest(event,st,183)
			elif int(event) == 9490 and f >= 122 :						# Recipe: Sealed Dynasty Leather Gloves (60%)
				htmltext = self.onExchangeRequest(event,st,122)
			elif int(event) == 9491 and f >= 122 :						# Recipe: Sealed Dynasty Leather Boots (60%)
				htmltext = self.onExchangeRequest(event,st,122)
			elif int(event) == 9497 and f >= 129 :						# Recipe: Sealed Dynasty Shield (60%)
				htmltext = self.onExchangeRequest(event,st,129)
			elif int(event) == 9625 and f >= 667 :						# Giant's Codex - Oblivion
				htmltext = self.onExchangeRequest(event,st,667)
			elif int(event) == 9626 and f >= 1000 :						# Giant's Codex - Discipline
				htmltext = self.onExchangeRequest(event,st,1000)
			elif int(event) == 9628 and f >= 24 :						# Leonard
				htmltext = self.onExchangeRequest(event,st,24)
			elif int(event) == 9629 and f >= 43 :						# Leonard
				htmltext = self.onExchangeRequest(event,st,43)
			elif int(event) == 9630 and f >= 36 :						# Adamantine
				htmltext = self.onExchangeRequest(event,st,36)
			else :
				htmltext = "32655-13no.htm"
		elif event == "32655-14.htm" :
			if st.getQuestItemsCount(SOUL_CORE) >= 10 :
				st.takeItems(SOUL_CORE,10)
				st.giveItems(SOUL_PENDANT,1)
			else:
				htmltext = "32655-14no.htm"
		elif event == "32655-quit.htm" :
			st.unset("cond")
			st.exitQuest(1)
			st.playSound("ItemSound.quest_finish")
		return htmltext

	def onTalk (self,npc,player) :
		htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
		st = player.getQuestState(qn)
		if not st : return htmltext

		npcId = npc.getNpcId()
		cond = st.getInt("cond")

		if npcId == CHAIREN:
			if cond == 0:
				if player.getLevel() >= 80 :
					htmltext = "32655-01.htm"
				else :
					htmltext = "32655-lvl.htm"
					st.exitQuest(1)
			elif st.getState() == State.STARTED :
				if st.getQuestItemsCount(SOUL_CORE) >= 1 or st.getQuestItemsCount(RAGNA_ORCS_AMULET) >= 1 :
					htmltext = "32655-12.htm"
				else :
					htmltext = "32655-10.htm"
		return htmltext

	def onKill(self,npc,player,isPet) :
		st = player.getQuestState(qn)
		if not st : return
		if st.getState() != State.STARTED : return

		npcId = npc.getNpcId()
		cond = st.getInt("cond")
		if cond == 1 and npcId in MOBS :
			rand = st.getRandom(100)
			if rand == 1 :
				st.giveItems(SOUL_CORE,1)
				st.playSound("ItemSound.quest_itemget")
			elif rand < DROP_CHANCE :
				st.giveItems(RAGNA_ORCS_AMULET,1)
				st.playSound("ItemSound.quest_itemget")
		return

QUEST		= Quest(311,qn,"Expulsion of Evil Spirits")

QUEST.addStartNpc(CHAIREN)
QUEST.addTalkId(CHAIREN)

for i in MOBS :
	QUEST.addKillId(i)