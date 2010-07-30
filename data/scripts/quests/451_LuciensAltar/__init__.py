#Created by Bloodshed
import sys
from java.lang	import System
from java.util	import Calendar
from com.l2jserver.gameserver.model.quest			import State
from com.l2jserver.gameserver.model.quest			import QuestState
from com.l2jserver.gameserver.model.quest.jython	import QuestJython as JQuest

qn = "451_LuciensAltar"

DAICHIR	= 30537

REPLENISHED_BEAD	= 14877
DISCHARGED_BEAD		= 14878
ADENA	= 57

ALTARS	= {
	32706:1,
	32707:2,
	32708:4,
	32709:8,
	32710:16
	}

RESET_HOUR = 6
RESET_MIN  = 30

class Quest (JQuest) :

	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [REPLENISHED_BEAD,DISCHARGED_BEAD]

	def onAdvEvent (self,event,npc,player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return
		if event == "30537-03.htm" :
			st.set("cond","1")
			st.set("altars_state","0")
			st.setState(State.STARTED)
			st.giveItems(REPLENISHED_BEAD,5)
			st.playSound("ItemSound.quest_accept")
		return htmltext

	def onTalk (self,npc,player) :
		htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>" 
		st = player.getQuestState(qn) 
		if not st : return htmltext

		npcId = npc.getNpcId()
		cond = st.getInt("cond")
		if npcId == DAICHIR :
			if cond == 0 :
				reset = st.get("reset")
				remain = 0
				if reset and reset.isdigit() :
					remain = long(reset) - System.currentTimeMillis()
				if remain <= 0 :
					if player.getLevel() >= 80 :
						htmltext = "30537-01.htm"
					else :
						htmltext = "30537-00.htm"
						st.exitQuest(True)
				else :
					htmltext = "30537-06.htm"
			elif cond == 1 :
				htmltext = "30537-04.htm"
			elif cond == 2 :
				htmltext = "30537-05.htm"
				st.giveItems(ADENA,127690)
				st.takeItems(DISCHARGED_BEAD,5)
				st.setState(State.COMPLETED)
				st.unset("cond")
				st.unset("altars_state")
				st.exitQuest(False)
				st.playSound("ItemSound.quest_finish")
				reset = Calendar.getInstance()
				reset.set(Calendar.MINUTE, RESET_MIN)
				# if time is >= RESET_HOUR - roll to the next day
				if reset.get(Calendar.HOUR_OF_DAY) >= RESET_HOUR :
					reset.add(Calendar.DATE, 1)
				reset.set(Calendar.HOUR_OF_DAY, RESET_HOUR)
				st.set("reset",str(reset.getTimeInMillis()))
		elif cond == 1 and npcId in ALTARS.keys() :
			idx = ALTARS[npcId]
			state = st.getInt("altars_state")
			if (state & idx) == 0 :
				st.set("altars_state",str(state | idx))
				st.takeItems(REPLENISHED_BEAD,1)
				st.giveItems(DISCHARGED_BEAD,1)
				st.playSound("ItemSound.quest_itemget")
				if st.getQuestItemsCount(DISCHARGED_BEAD) == 5 :
					st.set("cond","2")
					st.playSound("ItemSound.quest_middle")
				htmltext = "recharge.htm"
			else :
				htmltext = "findother.htm"
		return htmltext

QUEST		= Quest(451,qn,"Lucien's Altar")

QUEST.addStartNpc(DAICHIR)
QUEST.addTalkId(DAICHIR)

for altarId in ALTARS.keys() :
	QUEST.addTalkId(altarId)