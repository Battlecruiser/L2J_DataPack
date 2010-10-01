# 2010-06-26 by Gnacik
# Based on official server Franz

import sys
from com.l2jserver import Config
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest

qn = "699_GuardianOfTheSkies"

# NPCs
LEKON = 32557

# ITEMS
GOLDEN_FEATHER = 13871

# MOBS
MOBS = [22614,22615,25623,25633]

# SETTINGS
DROP_CHANCE = 80

class Quest (JQuest) :
	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [GOLDEN_FEATHER]

	def onAdvEvent(self, event, npc, player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return

		if event == "32557-03.htm" :
			st.setState(State.STARTED)
			st.set("cond","1")
			st.playSound("ItemSound.quest_accept")
		elif event == "32557-quit.htm" :
			st.unset("cond")
			st.exitQuest(True)
			st.playSound("ItemSound.quest_finish")
		return htmltext

	def onTalk (self, npc, player) :
		htmltext = Quest.getNoQuestMsg(player)
		st = player.getQuestState(qn)
		if not st : return htmltext

		cond = st.getInt("cond")

		if npc.getNpcId() == LEKON :
			first = player.getQuestState("10273_GoodDayToFly")
			if first and first.getState() == State.COMPLETED and st.getState() == State.CREATED and player.getLevel() >= 75 :
				htmltext = "32557-01.htm"
			elif cond == 1 :
				itemcount = st.getQuestItemsCount(GOLDEN_FEATHER)
				if itemcount > 0 :
					st.takeItems(GOLDEN_FEATHER, -1)
					st.rewardItems(57,itemcount * 2300)
					st.playSound("ItemSound.quest_itemget")
					htmltext = "32557-06.htm"
				else :
					htmltext = "32557-04.htm"
			elif cond == 0 :
				htmltext = "32557-00.htm"
		return htmltext

	def onKill(self, npc, player, isPet) :
		st = player.getQuestState(qn)
		if not st : return

		if st.getInt("cond") == 1 and npc.getNpcId() in MOBS :
			numItems, chance = divmod(DROP_CHANCE * Config.RATE_QUEST_DROP,100)
			if st.getRandom(100) < chance :
				numItems += 1
			if numItems :
				st.giveItems(GOLDEN_FEATHER,1)
				st.playSound("ItemSound.quest_itemget")
		return

QUEST	= Quest(699,qn,"Guardian of the Skies")

QUEST.addStartNpc(LEKON)
QUEST.addTalkId(LEKON)

for i in MOBS :
	QUEST.addKillId(i)