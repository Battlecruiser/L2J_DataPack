# Created by Gnacik
# 2010-02-20

import sys
from com.l2jserver.gameserver.model.quest				import State
from com.l2jserver.gameserver.model.quest				import QuestState
from com.l2jserver.gameserver.model.quest.jython	import QuestJython as JQuest

qn = "61_LawEnforcement"

# NPCs
LIANE = 32222
KEKROPUS = 32138
EINDBURGH = 32469

class Quest (JQuest) :

	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)

	def onAdvEvent (self,event,npc,player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return
		if event == "32222-05.htm":
			st.set("cond","1")
			st.setState(State.STARTED)
			st.playSound("ItemSound.quest_accept")
		elif event == "32138-09.htm":
			st.set("cond","2")
			st.playSound("ItemSound.quest_middle")
		elif event == "32469-08.htm" or event == "32469-09.htm":
			player.setClassId(136)
			player.broadcastUserInfo()
			st.giveItems(57,26000)
			st.exitQuest(False);
			st.playSound("ItemSound.quest_finish")
		return htmltext

	def onTalk (self,npc,player) :
		htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
		st = player.getQuestState(qn)
		if not st : return htmltext

		npcId = npc.getNpcId()
		cond = st.getInt("cond")

		if npcId == LIANE:
			if cond == 0:
				if player.getRace().ordinal() == 5:
					if player.getClassId().ordinal() == 135 and player.getLevel() >= 76:
						htmltext = "32222-01.htm"
					else:
						htmltext = "32222-02.htm"
				else:
					htmltext = "32222-03.htm"
			elif cond == 1:
				htmltext = "32222-06.htm"
		elif npcId == KEKROPUS:
			if cond == 1:
				htmltext = "32138-01.htm"
			elif cond == 2:
				htmltext = "32138-10.htm"
		elif npcId == EINDBURGH:
			if cond == 2:
				htmltext = "32469-01.htm"
		return htmltext

QUEST		= Quest(61,qn,"Law Enforcement")

QUEST.addStartNpc(LIANE)
QUEST.addTalkId(LIANE)
QUEST.addTalkId(KEKROPUS)
QUEST.addTalkId(EINDBURGH)
