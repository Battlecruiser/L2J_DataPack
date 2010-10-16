# 2010-06-29 by Gnacik
# Based on official server Franz and Rpg

import sys
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest
from com.l2jserver.util import Rnd

qn = "10276_MutatedKaneusGludio"

# NPCs
BATHIS       = 30332
ROHMER       = 30344
TOMLAN_KAMOS = 18554
OL_ARIOSH    = 18555

# Items
TISSUE_TK    = 13830
TISSUE_OA    = 13831

class Quest (JQuest) :
	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [TISSUE_TK, TISSUE_OA]

	def onAdvEvent(self, event, npc, player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return

		if event == "30332-03.htm" :
			st.setState(State.STARTED)
			st.set("cond","1")
			st.playSound("ItemSound.quest_accept")
		elif event == "30344-03.htm" :
			st.unset("cond")
			st.rewardItems(57,8500)
			st.exitQuest(False)
			st.playSound("ItemSound.quest_finish")
		return htmltext

	def onTalk (self, npc, player) :
		htmltext = Quest.getNoQuestMsg(player)
		st = player.getQuestState(qn)
		if not st : return htmltext

		npcId = npc.getNpcId()
		cond = st.getInt("cond")

		if npcId == BATHIS :
			if st.getState() == State.COMPLETED :
				htmltext = "30332-06.htm"
			elif st.getState() == State.CREATED and player.getLevel() >= 18 :
				htmltext = "30332-01.htm"
			elif st.getState() == State.CREATED and player.getLevel() < 18 :
				htmltext = "30332-00.htm"
			elif st.getQuestItemsCount(TISSUE_TK) > 0 and st.getQuestItemsCount(TISSUE_OA) > 0 :
				htmltext = "30332-05.htm"
			elif cond == 1 :
				htmltext = "30332-04.htm"
		elif npcId == ROHMER :
				if st.getState() == State.COMPLETED :
					htmltext = Quest.getAlreadyCompletedMsg(player)
				elif st.getQuestItemsCount(TISSUE_TK) > 0 and st.getQuestItemsCount(TISSUE_OA) > 0 :
					htmltext = "30344-02.htm"
				else :
					htmltext = "30344-01.htm"
		return htmltext

	def onKill(self, npc, player, isPet) :
		npcId = npc.getNpcId()
		party = player.getParty()
		if party :
			PartyMembers = []
			for member in party.getPartyMembers().toArray() :
				st = member.getQuestState(qn)
				if st and st.getState() == State.STARTED and st.getInt("cond") == 1 :
					if npcId == TOMLAN_KAMOS and st.getQuestItemsCount(TISSUE_TK) == 0 :
						PartyMembers.append(st)
					elif npcId == TISSUE_OA and st.getQuestItemsCount(TISSUE_OA) == 0 :
						PartyMembers.append(st)
			if len(PartyMembers) == 0 : return
			winnerst = PartyMembers[Rnd.get(len(PartyMembers))]
			if npcId == TOMLAN_KAMOS and winnerst.getQuestItemsCount(TISSUE_TK) == 0 :
				winnerst.giveItems(TISSUE_TK,1)
				winnerst.playSound("ItemSound.quest_itemget")
			elif npcId == OL_ARIOSH and winnerst.getQuestItemsCount(TISSUE_OA) == 0 :
				winnerst.giveItems(TISSUE_OA,1)
				winnerst.playSound("ItemSound.quest_itemget")
		else :
			st = player.getQuestState(qn)
			if not st : return
			if st.getState() != State.STARTED : return

			if npcId == TOMLAN_KAMOS and st.getQuestItemsCount(TISSUE_TK) == 0 :
				st.giveItems(TISSUE_TK,1)
				st.playSound("ItemSound.quest_itemget")
			elif npcId == OL_ARIOSH and st.getQuestItemsCount(TISSUE_OA) == 0 :
				st.giveItems(TISSUE_OA,1)
				st.playSound("ItemSound.quest_itemget")
		return

QUEST	= Quest(10276,qn,"Mutated Kaneus - Gludio")

QUEST.addStartNpc(BATHIS)
QUEST.addTalkId(BATHIS)
QUEST.addTalkId(ROHMER)

QUEST.addKillId(TOMLAN_KAMOS)
QUEST.addKillId(OL_ARIOSH)
