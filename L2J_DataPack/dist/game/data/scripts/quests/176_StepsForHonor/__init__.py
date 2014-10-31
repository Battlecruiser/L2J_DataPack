# 2010-08-01 by Gnacik
# Based on official server Franz

import sys
from com.l2jserver import Config
from com.l2jserver.gameserver.instancemanager import TerritoryWarManager
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest

qn = "176_StepsForHonor"

# NPCs
RAPIDUS = 36479

# ITEMS
CLOAK = 14603

class Quest (JQuest) :
	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = []

	def onAdvEvent(self, event, npc, player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return

		if event == "36479-02.htm" :
			st.setState(State.STARTED)
			st.set("cond","1")
			st.playSound("ItemSound.quest_accept")
		return htmltext

	def onTalk (self, npc, player) :
		htmltext = Quest.getNoQuestMsg(player)
		st = player.getQuestState(qn)
		if not st : return htmltext

		cond = st.getInt("cond")

		if npc.getNpcId() == RAPIDUS :
			if st.getState() == State.STARTED :
				if TerritoryWarManager.getInstance().isTWInProgress() :
					htmltext = "36479-tw.htm"
				elif cond == 1 :
					htmltext = "36479-03.htm"
				elif cond == 2 :
					st.playSound("ItemSound.quest_middle")
					st.set("cond","3")
					htmltext = "36479-04.htm"
				elif cond == 3 :
					htmltext = "36479-05.htm"
				elif cond == 4 :
					st.playSound("ItemSound.quest_middle")
					st.set("cond","5")
					htmltext = "36479-06.htm"
				elif cond == 5 :
					htmltext = "36479-07.htm"
				elif cond == 6 :
					st.playSound("ItemSound.quest_middle")
					st.set("cond","7")
					htmltext = "36479-08.htm"
				elif cond == 7 :
					htmltext = "36479-09.htm"
				elif cond == 8 :
					st.giveItems(CLOAK,1)
					st.exitQuest(False)
					st.playSound("ItemSound.quest_finish")
					htmltext = "36479-10.htm"
			elif st.getState() == State.CREATED :
				if player.getLevel() >= 80 :
					htmltext = "36479-01.htm"
				else :
					htmltext = "36479-00.htm"
			elif st.getState() == State.COMPLETED :
				htmltext = "36479-11.htm"

		return htmltext

QUEST	= Quest(176,qn,"Steps for honor")

QUEST.addStartNpc(RAPIDUS)
QUEST.addTalkId(RAPIDUS)
