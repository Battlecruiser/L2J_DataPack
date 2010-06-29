# 2010-06-29 by Gnacik
# Based on official server Franz and Rpg

import sys
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest
from com.l2jserver.util import Rnd

qn = "10278_MutatedKaneusHeine"

# NPCs
GOSTA       = 30916
MINEVIA     = 30907
BLADE_OTIS  = 18562
WEIRD_BUNEI = 18564

# Items
TISSUE_BO   = 13834
TISSUE_WB   = 13835

class Quest (JQuest) :
	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [TISSUE_BO, TISSUE_WB]

	def onAdvEvent(self, event, npc, player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return

		if event == "30916-03.htm" :
			st.setState(State.STARTED)
			st.set("cond","1")
			st.playSound("ItemSound.quest_accept")
		elif event == "30907-03.htm" :
			st.unset("cond")
			st.rewardItems(57,50000)
			st.exitQuest(False)
			st.playSound("ItemSound.quest_finish")
		return htmltext

	def onTalk (self, npc, player) :
		htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
		st = player.getQuestState(qn)
		if not st : return htmltext

		npcId = npc.getNpcId()
		cond = st.getInt("cond")

		if npcId == GOSTA :
			if st.getState() == State.COMPLETED :
				htmltext = "30916-06.htm"
			elif st.getState() == State.CREATED and player.getLevel() >= 38 :
				htmltext = "30916-01.htm"
			elif st.getState() == State.CREATED and player.getLevel() < 38 :
				htmltext = "30916-00.htm"
			elif st.getQuestItemsCount(TISSUE_BO) > 0 and st.getQuestItemsCount(TISSUE_WB) > 0 :
				htmltext = "30916-05.htm"
			elif cond == 1 :
				htmltext = "30916-04.htm"
		elif npcId == MINEVIA :
				if st.getState() == State.COMPLETED :
					htmltext = "<html><head><body>This quest has already been completed.<br></body></html>"
				elif st.getQuestItemsCount(TISSUE_BO) > 0 and st.getQuestItemsCount(TISSUE_WB) > 0 :
					htmltext = "30907-02.htm"
				else :
					htmltext = "30907-01.htm"
		return htmltext

	def onKill(self, npc, player, isPet) :
		npcId = npc.getNpcId()
		party = player.getParty()
		if party :
			PartyMembers = []
			for member in party.getPartyMembers().toArray() :
				st = member.getQuestState(qn)
				if st and st.getState() == State.STARTED and st.getInt("cond") == 1 :
					if npcId == BLADE_OTIS and st.getQuestItemsCount(TISSUE_BO) == 0 :
						PartyMembers.append(st)
					elif npcId == TISSUE_WB and st.getQuestItemsCount(TISSUE_WB) == 0 :
						PartyMembers.append(st)
			if len(PartyMembers) == 0 : return
			winnerst = PartyMembers[Rnd.get(len(PartyMembers))]
			if npcId == BLADE_OTIS and winnerst.getQuestItemsCount(TISSUE_BO) == 0 :
				winnerst.giveItems(TISSUE_BO,1)
				winnerst.playSound("ItemSound.quest_itemget")
			elif npcId == WEIRD_BUNEI and winnerst.getQuestItemsCount(TISSUE_WB) == 0 :
				winnerst.giveItems(TISSUE_WB,1)
				winnerst.playSound("ItemSound.quest_itemget")
		else :
			st = player.getQuestState(qn)
			if not st : return
			if st.getState() != State.STARTED : return

			if npcId == BLADE_OTIS and st.getQuestItemsCount(TISSUE_BO) == 0 :
				st.giveItems(TISSUE_BO,1)
				st.playSound("ItemSound.quest_itemget")
			elif npcId == WEIRD_BUNEI and st.getQuestItemsCount(TISSUE_WB) == 0 :
				st.giveItems(TISSUE_WB,1)
				st.playSound("ItemSound.quest_itemget")
		return

QUEST	= Quest(10278,qn,"Mutated Kaneus - Heine")

QUEST.addStartNpc(GOSTA)
QUEST.addTalkId(GOSTA)
QUEST.addTalkId(MINEVIA)

QUEST.addKillId(BLADE_OTIS)
QUEST.addKillId(WEIRD_BUNEI)
