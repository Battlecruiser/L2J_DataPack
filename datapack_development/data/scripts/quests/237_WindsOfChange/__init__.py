#Created by Bloodshed
import sys
from com.l2jserver.gameserver.model.quest			import State
from com.l2jserver.gameserver.model.quest			import QuestState
from com.l2jserver.gameserver.model.quest.jython	import QuestJython as JQuest

qn = "237_WindsOfChange"

FLAUEN		= 30899
IASON		= 30969
ROMAN		= 30897
MORELYN		= 30925
HELVETICA	= 32641
ATHENIA		= 32643

FLAUENS_LETTER		= 14862
DOSKOZER_LETTER		= 14863
ATHENIA_LETTER		= 14864
VICINITY_OF_FOS		= 14865
SUPPORT_CERTIFICATE	= 14866

class Quest (JQuest) :

	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [FLAUENS_LETTER, DOSKOZER_LETTER, ATHENIA_LETTER]

	def onAdvEvent (self,event,npc, player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return
		if event == "30899-06.htm" :
			st.set("cond","1")
			st.setState(State.STARTED)
			st.giveItems(FLAUENS_LETTER,1)
			st.playSound("ItemSound.quest_accept")
		elif event == "30969-02.htm" :
			st.takeItems(FLAUENS_LETTER,1)
		elif event == "30969-05.htm" :
			st.set("cond","2")
			st.playSound("ItemSound.quest_middle")
		elif event == "30897-03.htm" :
			st.set("cond","3")
			st.playSound("ItemSound.quest_middle")
		elif event == "30925-03.htm" :
			st.set("cond","4")
			st.playSound("ItemSound.quest_middle")
		elif event == "30969-09.htm" :
			st.giveItems(DOSKOZER_LETTER,1)
			st.set("cond","5")
			st.playSound("ItemSound.quest_middle")
		elif event == "30969-10.htm" :
			st.giveItems(ATHENIA_LETTER,1)
			st.set("cond","6")
			st.playSound("ItemSound.quest_middle")
		elif event == "32641-02.htm" :
			st.giveItems(57,213876)
			st.takeItems(DOSKOZER_LETTER,1)
			st.giveItems(VICINITY_OF_FOS,1)
			st.addExpAndSp(892773,60012)
			st.setState(State.COMPLETED)
			st.exitQuest(False)
			st.playSound("ItemSound.quest_finish")
		elif event == "32643-02.htm" :
			st.giveItems(57,213876)
			st.takeItems(ATHENIA_LETTER,1)
			st.giveItems(SUPPORT_CERTIFICATE,1)
			st.addExpAndSp(892773,60012)
			st.setState(State.COMPLETED)
			st.exitQuest(False)
			st.playSound("ItemSound.quest_finish")
		return htmltext

	def onTalk (self,npc,player) :
		htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>" 
		st = player.getQuestState(qn) 
		if not st : return htmltext

		npcId = npc.getNpcId()
		cond = st.getInt("cond")
		id = st.getState()
		if npcId == FLAUEN :
			if id == State.COMPLETED :
				htmltext = "30899-09.htm"
			elif cond == 0 :
				if player.getLevel() >= 82 :
					htmltext = "30899-01.htm"
				else :
					htmltext = "30899-00.htm"
					st.exitQuest(1)
			elif cond >= 1 and cond <= 4 :
				htmltext = "30899-07.htm"
			elif cond >= 5 :
				htmltext = "30899-08.htm"
		elif npcId == IASON :
			if id == State.COMPLETED :
				htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
			elif cond == 1 :
				htmltext = "30969-01.htm"
			elif cond == 2 :
				htmltext = "30969-06.htm"
			elif cond == 4 :
				htmltext = "30969-07.htm"
			elif cond == 5 or cond == 6 :
				htmltext = "30969-11.htm"
		elif npcId == ROMAN :
			if cond == 2 :
				htmltext = "30897-01.htm"
			elif cond == 3 or cond == 4:
				htmltext = "30897-04.htm"
		elif npcId == MORELYN :
			if cond == 3 :
				htmltext = "30925-01.htm"
			elif cond == 4 :
				htmltext = "30925-04.htm"
		elif npcId == HELVETICA :
			if cond == 5 and id == State.COMPLETED :
				htmltext = "32641-03.htm"
			elif cond == 5 :
				htmltext = "32641-01.htm"
		elif npcId == ATHENIA :
			if cond == 6 and id == State.COMPLETED :
				htmltext = "32643-03.htm"
			elif cond == 6 :
				htmltext = "32643-01.htm"
		return htmltext

QUEST		= Quest(237,qn,"Winds of Change")

QUEST.addStartNpc(FLAUEN)

QUEST.addTalkId(FLAUEN)
QUEST.addTalkId(IASON)
QUEST.addTalkId(ROMAN)
QUEST.addTalkId(MORELYN)
QUEST.addTalkId(HELVETICA)
QUEST.addTalkId(ATHENIA)