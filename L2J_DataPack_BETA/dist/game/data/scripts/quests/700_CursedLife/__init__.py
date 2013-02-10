# 2010-08-27 by Gnacik
# Based on official server Franz

import sys
from com.l2jserver import Config
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest

qn = "700_CursedLife"

# NPCs
ORBYU = 32560

# ITEMS
SWALLOWED_SKULL = 13872
SWALLOWED_STERNUM = 13873
SWALLOWED_BONES = 13874

# MOBS
MOBS = [22602,22603,22604,22605]

DROPLIST = {
	 0:SWALLOWED_BONES,
	75:SWALLOWED_STERNUM,
	95:SWALLOWED_SKULL
}

class Quest (JQuest) :
	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [SWALLOWED_SKULL,SWALLOWED_STERNUM,SWALLOWED_BONES]

	def onAdvEvent(self, event, npc, player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return

		if event == "32560-03.htm" :
			st.setState(State.STARTED)
			st.set("cond","1")
			st.playSound("ItemSound.quest_accept")
		elif event == "32560-quit.htm" :
			st.unset("cond")
			st.exitQuest(True)
			st.playSound("ItemSound.quest_finish")
		return htmltext

	def onTalk (self, npc, player) :
		htmltext = Quest.getNoQuestMsg(player)
		st = player.getQuestState(qn)
		if not st : return htmltext

		cond = st.getInt("cond")

		if npc.getNpcId() == ORBYU :
			first = player.getQuestState("Q10273_GoodDayToFly")
			if first and first.getState() == State.COMPLETED and st.getState() == State.CREATED and player.getLevel() >= 75 :
				htmltext = "32560-01.htm"
			elif cond == 1 :
				count1 = st.getQuestItemsCount(SWALLOWED_BONES)
				count2 = st.getQuestItemsCount(SWALLOWED_STERNUM)
				count3 = st.getQuestItemsCount(SWALLOWED_SKULL)
				if count1 > 0 or count2 > 0 or count3 > 0 :
					reward = (count1 * 500) + (count2 * 5000) + (count3 * 50000)
					st.takeItems(SWALLOWED_BONES,-1)
					st.takeItems(SWALLOWED_STERNUM,-1)
					st.takeItems(SWALLOWED_SKULL,-1)
					st.giveItems(57,reward)
					st.playSound("ItemSound.quest_itemget")
					htmltext = "32560-06.htm"
				else :
					htmltext = "32560-04.htm"
			elif cond == 0 :
				htmltext = "32560-00.htm"
		return htmltext

	def onKill(self, npc, player, isPet) :
		st = player.getQuestState(qn)
		if not st : return

		if st.getInt("cond") == 1 and npc.getNpcId() in MOBS :
			chance = self.getRandom(100)
			if chance < 5 :
				st.giveItems(SWALLOWED_SKULL,1)
			elif chance < 20 :
				st.giveItems(SWALLOWED_STERNUM,1)
			else :
				st.giveItems(SWALLOWED_BONES,1)
			st.playSound("ItemSound.quest_itemget")
		return

QUEST	= Quest(700,qn,"Cursed Life")

QUEST.addStartNpc(ORBYU)
QUEST.addTalkId(ORBYU)

for i in MOBS :
	QUEST.addKillId(i)
