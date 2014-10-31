# Created by Gnacik
# 2010-02-09 based on official Franz server
import sys
from com.l2jserver.gameserver.model.quest				import State
from com.l2jserver.gameserver.model.quest				import QuestState
from com.l2jserver.gameserver.model.quest.jython	import QuestJython as JQuest

qn = "377_GiantsExploration2"

# NPC
SOBLING	= 31147

# Items
TITAN_ANCIENT_BOOK = 14847
BOOK1,BOOK2,BOOK3,BOOK4,BOOK5 = [14842,14843,14844,14845,14846]

# Drop Chance
DROP_CHANCE = 50

# Mobs
MOBS = [22661,22662,22663,22664,22665,22666,22667,22668,22669]


class Quest (JQuest) :

	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [TITAN_ANCIENT_BOOK]

	def onExchangeRequest(self,event,st,qty,rem) :
		if st.getQuestItemsCount(BOOK1) >= rem and st.getQuestItemsCount(BOOK2) >= rem and st.getQuestItemsCount(BOOK3) >= rem and st.getQuestItemsCount(BOOK4) >= rem and st.getQuestItemsCount(BOOK5) >= rem :
			st.takeItems(BOOK1,rem)
			st.takeItems(BOOK2,rem)
			st.takeItems(BOOK3,rem)
			st.takeItems(BOOK4,rem)
			st.takeItems(BOOK5,rem)
			st.giveItems(int(event),qty)
			st.playSound("ItemSound.quest_finish")
			return "31147-ok.htm"
		else:
			return "31147-no.htm"

	def onAdvEvent (self,event,npc,player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return
		if event == "31147-02.htm" :
			st.set("cond","1")
			st.setState(State.STARTED)
			st.playSound("ItemSound.quest_accept")
		elif event == "31147-quit.htm" :
			st.unset("cond")
			st.exitQuest(1)
			st.playSound("ItemSound.quest_finish")
		elif event.isdigit() :
			if int(event) == 9625 :											# Giant's Codex - Oblivion
				htmltext = self.onExchangeRequest(event,st,1,5)
			elif int(event) == 9626 :										# Giant's Codex - Discipline
				htmltext = self.onExchangeRequest(event,st,1,5)
			elif int(event) == 9628 :										# Leonard
				htmltext = self.onExchangeRequest(event,st,6,1)
			elif int(event) == 9629 :										# Adamantine
				htmltext = self.onExchangeRequest(event,st,3,1)
			elif int(event) == 9630 :										# Orichalcum
				htmltext = self.onExchangeRequest(event,st,4,1)

		return htmltext

	def onTalk (self,npc,player) :
		htmltext = Quest.getNoQuestMsg(player)
		st = player.getQuestState(qn)
		if not st : return htmltext

		npcId = npc.getNpcId()
		cond = st.getInt("cond")

		if npcId == SOBLING:
			if st.getState() == State.STARTED :
				if st.getQuestItemsCount(BOOK1) > 0 and st.getQuestItemsCount(BOOK2) > 0 and st.getQuestItemsCount(BOOK3) > 0 and st.getQuestItemsCount(BOOK4) > 0 and st.getQuestItemsCount(BOOK5) > 0 :
					htmltext = "31147-03.htm"
				else:
					htmltext = "31147-02a.htm"
			else:
				if player.getLevel() >= 79 :
					htmltext = "31147-01.htm"
				else :
					htmltext = "31147-00.htm"
		return htmltext

	def onKill(self,npc,player,isPet) :
		st = player.getQuestState(qn)
		if not st : return
		if st.getState() != State.STARTED : return

		npcId = npc.getNpcId()
		cond = st.getInt("cond")
		if cond == 1 and npcId in MOBS :
			if st.getRandom(100) < DROP_CHANCE :
				st.giveItems(TITAN_ANCIENT_BOOK,1)
				st.playSound("ItemSound.quest_itemget")
		return

QUEST		= Quest(377,qn,"Exploration of the Giants Cave, Part II")

QUEST.addStartNpc(SOBLING)
QUEST.addTalkId(SOBLING)

for i in MOBS :
	QUEST.addKillId(i)