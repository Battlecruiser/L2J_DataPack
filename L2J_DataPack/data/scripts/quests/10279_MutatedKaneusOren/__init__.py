# 2010-06-29 by Gnacik
# Based on official server Franz and Rpg

import sys
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest
from com.l2jserver.util import Rnd

qn = "10279_MutatedKaneusOren"

# NPCs
MOUEN            = 30196
ROVIA            = 30189
KAIM_ABIGORE     = 18566
KNIGHT_MONTAGNAR = 18568

# Items
TISSUE_KA    = 13836
TISSUE_KM    = 13837

class Quest (JQuest) :
	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [TISSUE_KA, TISSUE_KM]

	def onAdvEvent(self, event, npc, player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return

		if event == "30196-03.htm" :
			st.setState(State.STARTED)
			st.set("cond","1")
			st.playSound("ItemSound.quest_accept")
		elif event == "30189-03.htm" :
			st.unset("cond")
			st.rewardItems(57,100000)
			st.exitQuest(False)
			st.playSound("ItemSound.quest_finish")
		return htmltext

	def onTalk (self, npc, player) :
		htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
		st = player.getQuestState(qn)
		if not st : return htmltext

		npcId = npc.getNpcId()
		cond = st.getInt("cond")

		if npcId == MOUEN :
			if st.getState() == State.COMPLETED :
				htmltext = "30196-06.htm"
			elif st.getState() == State.CREATED and player.getLevel() >= 48 :
				htmltext = "30196-01.htm"
			elif st.getState() == State.CREATED and player.getLevel() < 48 :
				htmltext = "30196-00.htm"
			elif st.getQuestItemsCount(TISSUE_KA) > 0 and st.getQuestItemsCount(TISSUE_KM) > 0 :
				htmltext = "30196-05.htm"
			elif cond == 1 :
				htmltext = "30196-04.htm"
		elif npcId == ROVIA :
				if st.getState() == State.COMPLETED :
					htmltext = "<html><head><body>This quest has already been completed.<br></body></html>"
				elif st.getQuestItemsCount(TISSUE_KA) > 0 and st.getQuestItemsCount(TISSUE_KM) > 0 :
					htmltext = "30189-02.htm"
				else :
					htmltext = "30189-01.htm"
		return htmltext

	def onKill(self, npc, player, isPet) :
		npcId = npc.getNpcId()
		party = player.getParty()
		if party :
			PartyMembers = []
			for member in party.getPartyMembers().toArray() :
				st = member.getQuestState(qn)
				if st and st.getState() == State.STARTED and st.getInt("cond") == 1 :
					if npcId == KAIM_ABIGORE and st.getQuestItemsCount(TISSUE_KA) == 0 :
						PartyMembers.append(st)
					elif npcId == TISSUE_KM and st.getQuestItemsCount(TISSUE_KM) == 0 :
						PartyMembers.append(st)
			if len(PartyMembers) == 0 : return
			winnerst = PartyMembers[Rnd.get(len(PartyMembers))]
			if npcId == KAIM_ABIGORE and winnerst.getQuestItemsCount(TISSUE_KA) == 0 :
				winnerst.giveItems(TISSUE_KA,1)
				winnerst.playSound("ItemSound.quest_itemget")
			elif npcId == KNIGHT_MONTAGNAR and winnerst.getQuestItemsCount(TISSUE_KM) == 0 :
				winnerst.giveItems(TISSUE_KM,1)
				winnerst.playSound("ItemSound.quest_itemget")
		else :
			st = player.getQuestState(qn)
			if not st : return
			if st.getState() != State.STARTED : return

			if npcId == KAIM_ABIGORE and st.getQuestItemsCount(TISSUE_KA) == 0 :
				st.giveItems(TISSUE_KA,1)
				st.playSound("ItemSound.quest_itemget")
			elif npcId == KNIGHT_MONTAGNAR and st.getQuestItemsCount(TISSUE_KM) == 0 :
				st.giveItems(TISSUE_KM,1)
				st.playSound("ItemSound.quest_itemget")
		return

QUEST	= Quest(10279,qn,"Mutated Kaneus - Oren")

QUEST.addStartNpc(MOUEN)
QUEST.addTalkId(MOUEN)
QUEST.addTalkId(ROVIA)

QUEST.addKillId(KAIM_ABIGORE)
QUEST.addKillId(KNIGHT_MONTAGNAR)
