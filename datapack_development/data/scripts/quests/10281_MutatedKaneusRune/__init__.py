# 2010-06-29 by Gnacik
# Based on official server Franz and Rpg

import sys
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest
from com.l2jserver.util import Rnd

qn = "10281_MutatedKaneusRune"

# NPCs
MATHIAS       = 31340
KAYAN         = 31335
WHITE_ALLOSCE = 18577

# Items
TISSUE_WA    = 13840

class Quest (JQuest) :
	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [TISSUE_WA]

	def onAdvEvent(self, event, npc, player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return

		if event == "31340-03.htm" :
			st.setState(State.STARTED)
			st.set("cond","1")
			st.playSound("ItemSound.quest_accept")
		elif event == "31335-03.htm" :
			st.unset("cond")
			st.rewardItems(57,360000)
			st.exitQuest(False)
			st.playSound("ItemSound.quest_finish")
		return htmltext

	def onTalk (self, npc, player) :
		htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
		st = player.getQuestState(qn)
		if not st : return htmltext

		npcId = npc.getNpcId()
		cond = st.getInt("cond")

		if npcId == MATHIAS :
			if st.getState() == State.COMPLETED :
				htmltext = "31340-06.htm"
			elif st.getState() == State.CREATED and player.getLevel() >= 68 :
				htmltext = "31340-01.htm"
			elif st.getState() == State.CREATED and player.getLevel() < 68 :
				htmltext = "31340-00.htm"
			elif st.getQuestItemsCount(TISSUE_WA) > 0 :
				htmltext = "31340-05.htm"
			elif cond == 1 :
				htmltext = "31340-04.htm"
		elif npcId == KAYAN :
				if st.getState() == State.COMPLETED :
					htmltext = "<html><head><body>This quest has already been completed.<br></body></html>"
				elif st.getQuestItemsCount(TISSUE_WA) > 0 :
					htmltext = "31335-02.htm"
				else :
					htmltext = "31335-01.htm"
		return htmltext

	def onKill(self, npc, player, isPet) :
		npcId = npc.getNpcId()
		party = player.getParty()
		if party :
			PartyMembers = []
			for member in party.getPartyMembers().toArray() :
				st = member.getQuestState(qn)
				if st and st.getState() == State.STARTED and st.getInt("cond") == 1 :
					if npcId == WHITE_ALLOSCE and st.getQuestItemsCount(TISSUE_WA) == 0 :
						PartyMembers.append(st)
			if len(PartyMembers) == 0 : return
			winnerst = PartyMembers[Rnd.get(len(PartyMembers))]
			if npcId == WHITE_ALLOSCE and winnerst.getQuestItemsCount(TISSUE_WA) == 0 :
				winnerst.giveItems(TISSUE_WA,1)
				winnerst.playSound("ItemSound.quest_itemget")
		else :
			st = player.getQuestState(qn)
			if not st : return
			if st.getState() != State.STARTED : return

			if npcId == WHITE_ALLOSCE and st.getQuestItemsCount(TISSUE_WA) == 0 :
				st.giveItems(TISSUE_WA,1)
				st.playSound("ItemSound.quest_itemget")
		return

QUEST	= Quest(10281,qn,"Mutated Kaneus - Rune")

QUEST.addStartNpc(MATHIAS)
QUEST.addTalkId(MATHIAS)
QUEST.addTalkId(KAYAN)

QUEST.addKillId(WHITE_ALLOSCE)