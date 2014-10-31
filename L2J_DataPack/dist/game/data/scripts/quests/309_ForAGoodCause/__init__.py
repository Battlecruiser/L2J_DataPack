#Created by Bloodshed
import sys
from com.l2jserver.gameserver.model.quest			import State
from com.l2jserver.gameserver.model.quest			import QuestState
from com.l2jserver.gameserver.model.quest.jython	import QuestJython as JQuest

qn = "309_ForAGoodCause"

ATRA	= 32647

MUCROKIANS				= [22650, 22651, 22652, 22653]
MUCROKIAN_FANATIC		= 22650
MUCROKIAN_ASCETIC		= 22651
MUCROKIAN_SAVIOR		= 22652
MUCROKIAN_PREACHER		= 22653
CONTAMINATED_MUCROKIAN	= 22654
CHANGED_MUCROKIAN		= 22655

MUCROKIAN_HIDE			= 14873
FALLEN_MUCROKIAN_HIDE	= 14874

MUCROKIAN_HIDE_CHANCE	= 50
FALLEN_HIDE_CHANCE		= 50

REC_DYNASTY_EARRINGS_70	= 9985
REC_DYNASTY_NECKLACE_70	= 9986
REC_DYNASTY_RING_70		= 9987
REC_DYNASTY_SIGIL_60	= 10115

class Quest (JQuest) :

	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [MUCROKIAN_HIDE, FALLEN_MUCROKIAN_HIDE]

	def onRecipeExchangeRequest (self,event,st,recipe) :
		h1 = st.getQuestItemsCount(MUCROKIAN_HIDE)
		h2 = st.getQuestItemsCount(FALLEN_MUCROKIAN_HIDE)
		if h2 >= 1:
			if h2 >= (int(event)/2) :
				st.giveItems(recipe,1)
				st.takeItems(FALLEN_MUCROKIAN_HIDE,(int(event)/2))
				st.playSound("ItemSound.quest_finish")
				return "32647-16.htm"
			else :
				h1a = int(event) - (h2*2)
				if h1 >= h1a :
					st.giveItems(recipe,1)
					st.takeItems(FALLEN_MUCROKIAN_HIDE,h2)
					st.takeItems(MUCROKIAN_HIDE,h1a)
					st.playSound("ItemSound.quest_finish")
					return "32647-16.htm"
				else :
					return "32647-15.htm"
		elif h1 >= int(event) :
			st.giveItems(recipe,1)
			st.takeItems(MUCROKIAN_HIDE,int(event))
			st.playSound("ItemSound.quest_finish")
			return "32647-16.htm"
		else :
			return "32647-15.htm"

	def onAdvEvent (self,event,npc,player) :
		htmltext = event
		st = player.getQuestState(qn)
		qs = player.getQuestState("239_WontYouJoinUs")
		if not st : return
		if event == "32647-05.htm" :
			self.questItemIds = [MUCROKIAN_HIDE, FALLEN_MUCROKIAN_HIDE]
			st.set("cond","1")
			st.setState(State.STARTED)
			st.playSound("ItemSound.quest_accept")
		elif event == "claimreward" :
			if qs :
				if qs.getState() == State.COMPLETED :
					htmltext = "32647-11.htm"
				else :
					htmltext = "32647-10.htm"
			else :
				htmltext = "32647-09.htm"
		elif event.isdigit() :
			if int(event) == 288 or int(event) == 346 :
				htmltext = self.onRecipeExchangeRequest(event,st,REC_DYNASTY_EARRINGS_70)
			elif int(event) == 384 or int(event) == 462 :
				htmltext = self.onRecipeExchangeRequest(event,st,REC_DYNASTY_NECKLACE_70)
			elif int(event) == 192 or int(event) == 232 :
				htmltext = self.onRecipeExchangeRequest(event,st,REC_DYNASTY_RING_70)
			elif int(event) == 310 or int(event) == 372 :
				htmltext = self.onRecipeExchangeRequest(event,st,REC_DYNASTY_SIGIL_60)
		elif event == "32647-14.htm" or event == "32647-07.htm" :
			self.questItemIds = [] #NPC Quest Cancel does not remove the collected Quest Items.
			st.exitQuest(1)
			st.playSound("ItemSound.quest_finish")
		return htmltext

	def onTalk (self,npc,player) :
		htmltext = Quest.getNoQuestMsg(player)
		st = player.getQuestState(qn)
		if not st : return htmltext

		npcId = npc.getNpcId()
		cond = st.getInt("cond")
		qs2 = player.getQuestState("308_ReedFieldMaintenance")
		if npcId == ATRA :
			if qs2 and qs2.getState() == State.STARTED :
				htmltext = "32647-17.htm"
			elif cond == 0 :
				if player.getLevel() >= 82 :
					htmltext = "32647-01.htm"
				else :
					htmltext = "32647-00.htm"
					st.exitQuest(1)
			elif st.getState() == State.STARTED :
				if st.getQuestItemsCount(MUCROKIAN_HIDE) >= 1 or st.getQuestItemsCount(FALLEN_MUCROKIAN_HIDE) >= 1:
					htmltext = "32647-08.htm"
				else :
					htmltext = "32647-06.htm"
		return htmltext

	def onKill(self,npc,player,isPet) :
		st = player.getQuestState(qn)
		if not st : return
		if st.getState() != State.STARTED : return
		npcId = npc.getNpcId()
		cond = st.getInt("cond")
		if cond == 1 and npcId in MUCROKIANS :
			if st.getRandom(100) < MUCROKIAN_HIDE_CHANCE :
				st.giveItems(MUCROKIAN_HIDE,1)
				st.playSound("ItemSound.quest_itemget")
		elif cond == 1 and npcId == CHANGED_MUCROKIAN :
			if st.getRandom(100) < FALLEN_HIDE_CHANCE :
				st.giveItems(FALLEN_MUCROKIAN_HIDE,1)
				st.playSound("ItemSound.quest_itemget")
		elif cond == 1 and npcId == CONTAMINATED_MUCROKIAN :
			if st.getRandom(100) < 10 :
				st.giveItems(MUCROKIAN_HIDE,1)
				st.playSound("ItemSound.quest_itemget")
		return

QUEST		= Quest(309,qn,"For A Good Cause")

QUEST.addStartNpc(ATRA)
QUEST.addTalkId(ATRA)

QUEST.addKillId(MUCROKIAN_FANATIC)
QUEST.addKillId(MUCROKIAN_ASCETIC)
QUEST.addKillId(MUCROKIAN_SAVIOR)
QUEST.addKillId(MUCROKIAN_PREACHER)
QUEST.addKillId(CONTAMINATED_MUCROKIAN)
QUEST.addKillId(CHANGED_MUCROKIAN)