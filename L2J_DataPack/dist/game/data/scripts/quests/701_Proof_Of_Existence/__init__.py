# 2010-06-26 by Gnacik
# Based on official server Franz

import sys
from com.l2jserver import Config
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest

qn = "701_Proof_Of_Existence"

# NPCs
ARTIUS = 32559

# ITEMS
DEADMANS_REMAINS = 13875

# MOBS
MOBS = [22606,22607,22608,22609]

# SETTINGS
DROP_CHANCE = 80

class Quest (JQuest) :
	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [DEADMANS_REMAINS]

	def onAdvEvent(self, event, npc, player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return

		if event == "32559-03.htm" :
			st.setState(State.STARTED)
			st.set("cond","1")
			st.playSound("ItemSound.quest_accept")
		elif event == "32559-quit.htm" :
			st.unset("cond")
			st.exitQuest(True)
			st.playSound("ItemSound.quest_finish")
		return htmltext

	def onTalk (self, npc, player) :
		htmltext = Quest.getNoQuestMsg(player)
		st = player.getQuestState(qn)
		if not st : return htmltext

		cond = st.getInt("cond")

		if npc.getNpcId() == ARTIUS :
			first = player.getQuestState("10273_GoodDayToFly")
			if first and first.getState() == State.COMPLETED and st.getState() == State.CREATED and player.getLevel() >= 78 :
				htmltext = "32559-01.htm"
			elif cond == 1 :
				itemcount = st.getQuestItemsCount(DEADMANS_REMAINS)
				if itemcount > 0 :
					st.takeItems(DEADMANS_REMAINS, -1)
					st.rewardItems(57,itemcount * 2500)
					st.playSound("ItemSound.quest_itemget")
					htmltext = "32559-06.htm"
				else :
					htmltext = "32559-04.htm"
			elif cond == 0 :
				htmltext = "32559-00.htm"
		return htmltext

	def onKill(self, npc, player, isPet) :
		st = player.getQuestState(qn)
		if not st : return

		if st.getInt("cond") == 1 and npc.getNpcId() in MOBS :
			numItems, chance = divmod(DROP_CHANCE * Config.RATE_QUEST_DROP,100)
			if self.getRandom(100) < chance :
				numItems += 1
			if numItems :
				st.giveItems(DEADMANS_REMAINS,1)
				st.playSound("ItemSound.quest_itemget")
		return

QUEST	= Quest(701,qn,"Proof of Existence")

QUEST.addStartNpc(ARTIUS)
QUEST.addTalkId(ARTIUS)

for i in MOBS :
	QUEST.addKillId(i)