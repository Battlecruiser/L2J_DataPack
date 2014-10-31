#Created by Bloodshed
import sys
from com.l2jserver.gameserver.model.quest			import State
from com.l2jserver.gameserver.model.quest			import QuestState
from com.l2jserver.gameserver.model.quest.jython	import QuestJython as JQuest

qn = "238_SuccesFailureOfBusiness"

HELVETICA			= 32641

BRAZIER_OF_PURITY	= 18806
EVIL_SPIRITS		= 22658
GUARDIAN_SPIRITS	= 22659

VICINITY_OF_FOS				= 14865
BROKEN_PIECE_OF_MAGIC_FORCE	= 14867
GUARDIAN_SPIRIT_FRAGMENT	= 14868

class Quest (JQuest) :

	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [BROKEN_PIECE_OF_MAGIC_FORCE,GUARDIAN_SPIRIT_FRAGMENT]

	def onAdvEvent (self,event,npc, player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return
		if event == "32461-03.htm" :
			st.set("cond","1")
			st.setState(State.STARTED)
			st.playSound("ItemSound.quest_accept")
		elif event == "32461-06.htm" :
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
		qs2 = st.getPlayer().getQuestState("239_WontYouJoinUs")
		if npcId == HELVETICA :
			if id == State.COMPLETED :
				htmltext = "32461-09.htm"
			elif cond == 0 :
				if qs2 :
					if qs2.getState() == State.COMPLETED :
						htmltext = "32461-10.htm"
				elif qs :
					if qs.getState() == State.COMPLETED and player.getLevel() >= 82 :
						htmltext = "32461-01.htm"
					else :
						htmltext = "32461-00.htm"
						st.exitQuest(1)
				else :
					htmltext = "32461-00.htm"
					st.exitQuest(1)
			elif cond == 1 :
				htmltext = "32461-04.htm"
			elif cond == 2 :
				htmltext = "32461-05.htm"
				st.takeItems(BROKEN_PIECE_OF_MAGIC_FORCE,10)
			elif cond == 3 :
				htmltext = "32461-07.htm"
			elif cond == 4 and st.getQuestItemsCount(GUARDIAN_SPIRIT_FRAGMENT) == 20 :
				htmltext = "32461-08.htm"
				st.giveItems(57,283346)
				st.takeItems(VICINITY_OF_FOS,1)
				st.takeItems(GUARDIAN_SPIRIT_FRAGMENT,20)
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
		if cond == 1 and npcId == BRAZIER_OF_PURITY :
			if st.getQuestItemsCount(BROKEN_PIECE_OF_MAGIC_FORCE) <= 9 :
				st.giveItems(BROKEN_PIECE_OF_MAGIC_FORCE,1)
				st.playSound("ItemSound.quest_itemget")
				if st.getQuestItemsCount(BROKEN_PIECE_OF_MAGIC_FORCE) == 10 :
					st.set("cond","2")
					st.playSound("ItemSound.quest_middle")
		elif cond == 3 and npcId == EVIL_SPIRITS or cond == 3 and npcId == GUARDIAN_SPIRITS :
			if st.getRandom(100) < 80 :
				if st.getQuestItemsCount(GUARDIAN_SPIRIT_FRAGMENT) <= 19 :
					st.giveItems(GUARDIAN_SPIRIT_FRAGMENT,1)
					st.playSound("ItemSound.quest_itemget")
					if st.getQuestItemsCount(GUARDIAN_SPIRIT_FRAGMENT) == 20 :
						st.set("cond","4")
						st.playSound("ItemSound.quest_middle")
		return
		
		
QUEST		= Quest(238,qn,"Succes/Failure of Business")

QUEST.addStartNpc(HELVETICA)

QUEST.addTalkId(HELVETICA)

QUEST.addKillId(BRAZIER_OF_PURITY)
QUEST.addKillId(EVIL_SPIRITS)
QUEST.addKillId(GUARDIAN_SPIRITS)