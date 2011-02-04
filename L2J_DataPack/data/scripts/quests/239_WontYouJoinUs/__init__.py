#Created by Bloodshed
import sys
from com.l2jserver.gameserver.model.quest			import State
from com.l2jserver.gameserver.model.quest			import QuestState
from com.l2jserver.gameserver.model.quest.jython	import QuestJython as JQuest

qn = "239_WontYouJoinUs"

ATHENIA	= 32643

WASTE_LANDFILL_MACHINE	= 18805
SUPPRESSOR				= 22656
EXTERMINATOR			= 22657

SUPPORT_CERIFICATE			= 14866
DESTROYED_MACHINE_PIECE		= 14869
ENCHANTED_GOLEM_FRAGMENT	= 14870

class Quest (JQuest) :

	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [DESTROYED_MACHINE_PIECE,ENCHANTED_GOLEM_FRAGMENT]

	def onAdvEvent (self,event,npc, player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return
		if event == "32643-03.htm" :
			st.set("cond","1")
			st.setState(State.STARTED)
			st.playSound("ItemSound.quest_accept")
		elif event == "32643-07.htm" :
			st.set("cond","3")
			st.playSound("ItemSound.quest_middle")
		return htmltext

	def onTalk (self,npc,player) :
		htmltext = Quest.getNoQuestMsg(player) 
		st = player.getQuestState(qn) 
		if not st : return htmltext

		npcId = npc.getNpcId()
		cond = st.getInt("cond")
		id = st.getState()
		qs = st.getPlayer().getQuestState("237_WindsOfChange")
		qs2 = st.getPlayer().getQuestState("238_SuccesFailureOfBusiness")
		if npcId == ATHENIA :
			if id == State.COMPLETED :
				htmltext = "32643-11.htm"
			elif cond == 0 :
				if qs2 :
					if qs2.getState() == State.COMPLETED :
						htmltext = "32643-12.htm"
				elif qs :
					if qs.getState() == State.COMPLETED and player.getLevel() >= 82 :
						htmltext = "32643-01.htm"
					else :
						htmltext = "32643-00.htm"
						st.exitQuest(1)
				else :
					htmltext = "32643-00.htm"
					st.exitQuest(1)
			elif cond == 1 :
				if st.getQuestItemsCount(DESTROYED_MACHINE_PIECE) >= 1:
					htmltext = "32643-05.htm"
				else :
					htmltext = "32643-04.htm"
			elif cond == 2 :
				htmltext = "32643-06.htm"
				st.takeItems(DESTROYED_MACHINE_PIECE,10)
			elif cond == 3 :
				if st.getQuestItemsCount(ENCHANTED_GOLEM_FRAGMENT) >= 1:
					htmltext = "32643-08.htm"
				else :
					htmltext = "32643-09.htm"
			elif cond == 4 and st.getQuestItemsCount(ENCHANTED_GOLEM_FRAGMENT) == 20 :
				htmltext = "32643-10.htm"
				st.giveItems(57,283346)
				st.takeItems(SUPPORT_CERIFICATE,1)
				st.takeItems(ENCHANTED_GOLEM_FRAGMENT,20)
				st.addExpAndSp(1319736,103553)
				st.setState(State.COMPLETED)
				st.exitQuest(False)
				st.playSound("ItemSound.quest_finish")
		return htmltext

	def onKill(self,npc,player,isPet) :
		st = player.getQuestState(qn)
		if not st : return 
		if st.getState() != State.STARTED : return
		
		npcId = npc.getNpcId()
		cond = st.getInt("cond")
		if cond == 1 and npcId == WASTE_LANDFILL_MACHINE :
			if st.getQuestItemsCount(DESTROYED_MACHINE_PIECE) <= 9 :
				st.giveItems(DESTROYED_MACHINE_PIECE,1)
				st.playSound("ItemSound.quest_itemget")
				if st.getQuestItemsCount(DESTROYED_MACHINE_PIECE) == 10 :
					st.set("cond","2")
					st.playSound("ItemSound.quest_middle")
		elif cond == 3 and npcId == SUPPRESSOR or cond == 3 and npcId == EXTERMINATOR :
			if st.getRandom(100) < 80 :
				if st.getQuestItemsCount(ENCHANTED_GOLEM_FRAGMENT) <= 19 :
					st.giveItems(ENCHANTED_GOLEM_FRAGMENT,1)
					st.playSound("ItemSound.quest_itemget")
					if st.getQuestItemsCount(ENCHANTED_GOLEM_FRAGMENT) == 20 :
						st.set("cond","4")
						st.playSound("ItemSound.quest_middle")
		return
		
		
QUEST		= Quest(239,qn,"Won't You Join Us?")

QUEST.addStartNpc(ATHENIA)
QUEST.addTalkId(ATHENIA)

QUEST.addKillId(WASTE_LANDFILL_MACHINE)
QUEST.addKillId(SUPPRESSOR)
QUEST.addKillId(EXTERMINATOR)