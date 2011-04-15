# 2010-06-29 by Gnacik
# Based on official server Franz and Rpg

import sys
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest
from com.l2jserver.util import Rnd

qn = "10277_MutatedKaneusDion"

# NPCs
LUKAS        = 30071
MIRIEN       = 30461
CRIMSON_HATU = 18558
SEER_FLOUROS = 18559

# Items
TISSUE_CH    = 13832
TISSUE_SF    = 13833

class Quest (JQuest) :
	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [TISSUE_CH, TISSUE_SF]

	def onAdvEvent(self, event, npc, player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return

		if event == "30071-03.htm" :
			st.setState(State.STARTED)
			st.set("cond","1")
			st.playSound("ItemSound.quest_accept")
		elif event == "30461-03.htm" :
			st.unset("cond")
			st.rewardItems(57,20000)
			st.exitQuest(False)
			st.playSound("ItemSound.quest_finish")
		return htmltext

	def onTalk (self, npc, player) :
		htmltext = Quest.getNoQuestMsg(player)
		st = player.getQuestState(qn)
		if not st : return htmltext

		npcId = npc.getNpcId()
		cond = st.getInt("cond")

		if npcId == LUKAS :
			if st.getState() == State.COMPLETED :
				htmltext = "30071-06.htm"
			elif st.getState() == State.CREATED and player.getLevel() >= 28 :
				htmltext = "30071-01.htm"
			elif st.getState() == State.CREATED and player.getLevel() < 28 :
				htmltext = "30071-00.htm"
			elif st.getQuestItemsCount(TISSUE_CH) > 0 and st.getQuestItemsCount(TISSUE_SF) > 0 :
				htmltext = "30071-05.htm"
			elif cond == 1 :
				htmltext = "30071-04.htm"
		elif npcId == MIRIEN :
				if st.getState() == State.COMPLETED :
					htmltext = Quest.getAlreadyCompletedMsg(player)
				elif st.getQuestItemsCount(TISSUE_CH) > 0 and st.getQuestItemsCount(TISSUE_SF) > 0 :
					htmltext = "30461-02.htm"
				else :
					htmltext = "30461-01.htm"
		return htmltext

	def onKill(self, npc, player, isPet) :
		npcId = npc.getNpcId()
		party = player.getParty()
		if party :
			PartyMembers = []
			for member in party.getPartyMembers().toArray() :
				st = member.getQuestState(qn)
				if st and st.getState() == State.STARTED and st.getInt("cond") == 1 :
					if npcId == CRIMSON_HATU and st.getQuestItemsCount(TISSUE_CH) == 0 :
						PartyMembers.append(st)
					elif npcId == TISSUE_SF and st.getQuestItemsCount(TISSUE_SF) == 0 :
						PartyMembers.append(st)
			if len(PartyMembers) == 0 : return
			winnerst = PartyMembers[Rnd.get(len(PartyMembers))]
			if npcId == CRIMSON_HATU and winnerst.getQuestItemsCount(TISSUE_CH) == 0 :
				winnerst.giveItems(TISSUE_CH,1)
				winnerst.playSound("ItemSound.quest_itemget")
			elif npcId == SEER_FLOUROS and winnerst.getQuestItemsCount(TISSUE_SF) == 0 :
				winnerst.giveItems(TISSUE_SF,1)
				winnerst.playSound("ItemSound.quest_itemget")
		else :
			st = player.getQuestState(qn)
			if not st : return
			if st.getState() != State.STARTED : return

			if npcId == CRIMSON_HATU and st.getQuestItemsCount(TISSUE_CH) == 0 :
				st.giveItems(TISSUE_CH,1)
				st.playSound("ItemSound.quest_itemget")
			elif npcId == SEER_FLOUROS and st.getQuestItemsCount(TISSUE_SF) == 0 :
				st.giveItems(TISSUE_SF,1)
				st.playSound("ItemSound.quest_itemget")
		return

QUEST	= Quest(10277,qn,"Mutated Kaneus - Dion")

QUEST.addStartNpc(LUKAS)
QUEST.addTalkId(LUKAS)
QUEST.addTalkId(MIRIEN)

QUEST.addKillId(CRIMSON_HATU)
QUEST.addKillId(SEER_FLOUROS)
