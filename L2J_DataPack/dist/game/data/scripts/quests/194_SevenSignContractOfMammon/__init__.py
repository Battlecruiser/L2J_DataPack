# 2010-06-26 by Gnacik
# Based on official server Franz

import sys
import time
from com.l2jserver.gameserver.datatables					import SkillTable
from com.l2jserver.gameserver.model.actor.instance		import L2PcInstance
from com.l2jserver.gameserver.model.quest					import State
from com.l2jserver.gameserver.model.quest					import QuestState
from com.l2jserver.gameserver.model.quest.jython		import QuestJython as JQuest
from com.l2jserver.gameserver.network.serverpackets	import ExStartScenePlayer

qn = "194_SevenSignContractOfMammon"

# NPCs
ATHEBALDT = 30760
COLIN     = 32571
FROG      = 32572
TESS      = 32573
KUTA      = 32574
CLAUDIA   = 31001

# ITEMS
INTRODUCTION   = 13818
FROG_KING_BEAD = 13820
CANDY_POUCH    = 13821
NATIVES_GLOVE  = 13819

def transformPlayer(npc, player, transid) :
	if player.isTransformed() == True :
		player.untransform()
		time.sleep(2)
	for effect in player.getAllEffects() :
		if effect.getAbnormalType() == "speed_up":
			effect.exit()
	npc.setTarget(player)
	npc.doCast(SkillTable.getInstance().getInfo(transid,1))
	return

def checkPlayer(player, transid) :
	effect = player.getFirstEffect(transid)
	if effect :
		return True
	return False

class Quest (JQuest) :
	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [INTRODUCTION, FROG_KING_BEAD, CANDY_POUCH, NATIVES_GLOVE]

	def onAdvEvent(self, event, npc, player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return

		if event == "30760-02.htm" :
			st.set("cond","1")
			st.setState(State.STARTED)
			st.playSound("ItemSound.quest_accept")
		elif event == "30760-07.htm" :
			st.set("cond","3")
			st.giveItems(INTRODUCTION, 1)
			st.playSound("ItemSound.quest_middle")
		elif event == "32571-04.htm" :
			st.set("cond","4")
			st.takeItems(INTRODUCTION,1)
			transformPlayer(npc, player, 6201)
			st.playSound("ItemSound.quest_middle")
		elif event == "32571-06.htm" or event == "32571-14.htm" or event == "32571-22.htm":
			if player.isTransformed() == True:
				player.untransform()
		elif event == "32571-08.htm" :
			transformPlayer(npc, player, 6201)
		elif event == "32572-04.htm" :
			st.set("cond","5")
			st.giveItems(FROG_KING_BEAD,1)
			st.playSound("ItemSound.quest_middle")
		elif event == "32571-10.htm" :
			st.set("cond","6")
			st.takeItems(FROG_KING_BEAD,1)
			st.playSound("ItemSound.quest_middle")
		elif event == "32571-12.htm" :
			st.set("cond","7")
			transformPlayer(npc, player, 6202)
			st.playSound("ItemSound.quest_middle")
		elif event == "32571-16.htm" :
			transformPlayer(npc, player, 6202)
		elif event == "32573-03.htm" :
			st.set("cond","8")
			st.giveItems(CANDY_POUCH,1)
			st.playSound("ItemSound.quest_middle")
		elif event == "32571-18.htm" :
			st.set("cond","9")
			st.takeItems(CANDY_POUCH,1)
			st.playSound("ItemSound.quest_middle")
		elif event == "32571-20.htm" :
			st.set("cond","10")
			transformPlayer(npc, player, 6203)
			st.playSound("ItemSound.quest_middle")
		elif event == "32571-24.htm" :
			transformPlayer(npc, player, 6203)
		elif event == "32574-04.htm" :
			st.set("cond","11")
			st.giveItems(NATIVES_GLOVE,1)
			st.playSound("ItemSound.quest_middle")
		elif event == "32571-26.htm" :
			st.set("cond","12")
			st.takeItems(NATIVES_GLOVE,1)
			st.playSound("ItemSound.quest_middle")
		elif event.isdigit() :
			if int(event) == 10 :
				st.set("cond","2")
				st.playSound("ItemSound.quest_middle")
				player.showQuestMovie(int(event))
				return ""
		elif event == "31001-03.htm" :
			st.addExpAndSp(52518015,5817677)
			st.unset("cond")
			st.setState(State.COMPLETED)
			st.exitQuest(False)
			st.playSound("ItemSound.quest_finish")
		return htmltext

	def onTalk (self, npc, player) :
		htmltext = Quest.getNoQuestMsg(player)
		st = player.getQuestState(qn)
		if not st : return htmltext

		npcId = npc.getNpcId()
		cond = st.getInt("cond")
		id = st.getState()
		if npcId == ATHEBALDT :
			second = player.getQuestState("193_SevenSignDyingMessage")
			if st.getState() == State.COMPLETED :
				htmltext = Quest.getAlreadyCompletedMsg(player)
			elif second and second.getState() == State.COMPLETED and id == State.CREATED and player.getLevel() >= 79 :
				htmltext = "30760-01.htm"
			elif cond == 1 :
				htmltext = "30760-03.htm"
			elif cond == 2 :
				htmltext = "30760-05.htm"
			elif cond == 3 :
				htmltext = "30760-08.htm"
			else:
				htmltext = "30760-00.htm"
				st.exitQuest(True)
		elif npcId == COLIN :
			if cond == 3 :
				htmltext = "32571-01.htm"
			elif cond == 4 :
				if checkPlayer(player, 6201):
					htmltext = "32571-05.htm"
				else :
					htmltext = "32571-07.htm"
			elif cond == 5 :
				htmltext = "32571-09.htm"
			elif cond == 6 :
				htmltext = "32571-11.htm"
			elif cond == 7 :
				if checkPlayer(player, 6202):
					htmltext = "32571-13.htm"
				else :
					htmltext = "32571-15.htm"
			elif cond == 8 :
					htmltext = "32571-17.htm"
			elif cond == 9 :
					htmltext = "32571-19.htm"
			elif cond == 10 :
				if checkPlayer(player, 6203):
					htmltext = "32571-21.htm"
				else :
					htmltext = "32571-23.htm"
			elif cond == 11 :
				htmltext = "32571-25.htm"
		elif npcId == FROG :
			if checkPlayer(player, 6201):
				if cond == 4:
					htmltext = "32572-01.htm"
				elif cond == 5:
					htmltext = "32572-05.htm"
			else :
				htmltext = "32572-00.htm"
		elif npcId == TESS :
			if checkPlayer(player, 6202):
				if cond == 7:
					htmltext = "32573-01.htm"
				elif cond == 8:
					htmltext = "32573-04.htm"
			else :
				htmltext = "32573-00.htm"
		elif npcId == KUTA :
			if checkPlayer(player, 6203):
				if cond == 10:
					htmltext = "32574-01.htm"
				elif cond == 11:
					htmltext = "32574-05.htm"
			else :
				htmltext = "32574-00.htm"
		elif npcId == CLAUDIA :
			if cond == 12 :
				htmltext = "31001-01.htm"
		return htmltext

QUEST	= Quest(194,qn,"Seven Sign Contract Of Mammon")

QUEST.addStartNpc(ATHEBALDT)
QUEST.addTalkId(ATHEBALDT)
QUEST.addTalkId(COLIN)
QUEST.addTalkId(FROG)
QUEST.addTalkId(TESS)
QUEST.addTalkId(KUTA)
QUEST.addTalkId(CLAUDIA)