#Created by Bloodshed
import sys
from com.l2jserver import Config
from com.l2jserver.gameserver.model.quest			import State
from com.l2jserver.gameserver.model.quest			import QuestState
from com.l2jserver.gameserver.model.quest.jython	import QuestJython as JQuest

qn = "312_TakeAdvantageOfTheCrisis"

FILAUR	= 30535

MINERAL_FRAGMENT	= 14875
DROP_CHANCE	= 20

MINE_MOBS		= [22678,22679,22680,22681,22682,22683,22684,22685,22686,22687,22688,22689,22690]

GRAVE_ROBBERS	= [22678,22679,22680,22681,22682]
SERVITORS		= [22683,22684,22685,22686]
MINE_SPIRITS	= [22687,22688,22689,22690]

class Quest (JQuest) :

	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [MINERAL_FRAGMENT]

	def onExchangeRequest (self,event,st,fragamount) :
		st.rewardItems(int(event),1)
		st.takeItems(MINERAL_FRAGMENT,fragamount)
		st.playSound("ItemSound.quest_finish")
		return "30535-16.htm"

	def onAdvEvent (self,event,npc,player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return
		if event == "30535-06.htm" :
			st.set("cond","1")
			st.setState(State.STARTED)
			st.playSound("ItemSound.quest_accept")
			self.questItemIds = [MINERAL_FRAGMENT]
		elif event.isdigit() :
			f = st.getQuestItemsCount(MINERAL_FRAGMENT)
			if int(event) == 9487 and f >= 366 :
				htmltext = self.onExchangeRequest(event,st,366)
			elif int(event) == 9488 and f >= 229 :
				htmltext = self.onExchangeRequest(event,st,229)
			elif int(event) == 9489 and f >= 183 :
				htmltext = self.onExchangeRequest(event,st,183)
			elif int(event) == 9490 and f >= 122 or int(event) == 9491 and f >= 122 :
				htmltext = self.onExchangeRequest(event,st,122)
			elif int(event) == 9497 and f >= 129 :
				htmltext = self.onExchangeRequest(event,st,129)
			elif int(event) == 9625 and f >= 667 :
				htmltext = self.onExchangeRequest(event,st,667)
			elif int(event) == 9626 and f >= 1000 :
				htmltext = self.onExchangeRequest(event,st,1000)
			elif int(event) == 9628 and f >= 24 :
				htmltext = self.onExchangeRequest(event,st,24)
			elif int(event) == 9629 and f >= 43 :
				htmltext = self.onExchangeRequest(event,st,43)
			elif int(event) == 9630 and f >= 36 :
				htmltext = self.onExchangeRequest(event,st,36)
			else : 
				htmltext = "30535-15.htm"
		elif event == "30535-09.htm" :
			self.questItemIds = []
			st.exitQuest(1)
			st.playSound("ItemSound.quest_finish")
		return htmltext

	def onTalk (self,npc,player) :
		htmltext = Quest.getNoQuestMsg(player) 
		st = player.getQuestState(qn) 
		if not st : return htmltext

		npcId = npc.getNpcId()
		cond = st.getInt("cond")
		id = st.getState()
		if npcId == FILAUR :
			if cond == 0 :
				if player.getLevel() >= 80 :
					htmltext = "30535-01.htm"
				else :
					htmltext = "30535-00.htm"
					st.exitQuest(1)
			elif id == State.STARTED :
				if st.getQuestItemsCount(MINERAL_FRAGMENT) >= 1 :
					htmltext = "30535-10.htm"
				else :
					htmltext = "30535-07.htm"
		return htmltext

	def onKill(self,npc,player,isPet) :
		st = player.getQuestState(qn)
		if not st : return 
		if st.getState() != State.STARTED : return

		npcId = npc.getNpcId()
		cond = st.getInt("cond")
		if cond == 1 and npcId in MINE_MOBS :
			chance = DROP_CHANCE*Config.RATE_QUEST_DROP
			numItems, chance = divmod(chance,100)
			if getRandom(100) < chance : 
				numItems += 1
			if numItems :
				st.giveItems(MINERAL_FRAGMENT,int(numItems))
				st.playSound("ItemSound.quest_itemget")
		return

QUEST		= Quest(312,qn,"Take Advantage of The Crisis!")

QUEST.addStartNpc(FILAUR)
QUEST.addTalkId(FILAUR)

for i in MINE_MOBS :
	QUEST.addKillId(i)