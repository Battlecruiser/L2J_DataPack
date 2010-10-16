# by Gnacik
#
import sys
from com.l2jserver import Config
from com.l2jserver.gameserver.model.quest        import State
from com.l2jserver.gameserver.model.quest        import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest

qn = "146_TheZeroHour"

# NPc's
KAHMAN = 31554
# Items
FANG = 14859

class Quest (JQuest) :

	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [FANG]

	def onAdvEvent (self,event,npc,player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return
		if event == "31554-02.htm" :
			st.set("cond","1")
			st.setState(State.STARTED)
			st.playSound("ItemSound.quest_accept")
		return htmltext

	def onTalk (self, npc, player) :
		htmltext = Quest.getNoQuestMsg(player)
		st = player.getQuestState(qn)
		if not st : return htmltext

		if st.getState() == State.STARTED :
			if st.getQuestItemsCount(FANG) > 0:
				htmltext = "Not Done, sorry"
			else:
				htmltext = "31554-03.htm"
		else:
			if player.getLevel() >= 81 :
				htmltext = "31554-01.htm"
			else:
				htmltext = "31554-00.htm"
		return htmltext


QUEST = Quest(146,qn,"The Zero Hour")

QUEST.addStartNpc(KAHMAN)
QUEST.addTalkId(KAHMAN)