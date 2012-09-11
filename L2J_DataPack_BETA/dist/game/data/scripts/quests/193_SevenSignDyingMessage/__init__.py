# 2010-06-26 by Gnacik
# Based on official server Franz

import sys
from com.l2jserver.gameserver.ai import CtrlIntention
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest
from com.l2jserver.gameserver.network.serverpackets import ExStartScenePlayer
from com.l2jserver.gameserver.network.serverpackets import NpcSay

qn = "193_SevenSignDyingMessage"

# NPCs
HOLLINT     = 30191
CAIN        = 32569
ERIC        = 32570
ATHEBALDT   = 30760
SHILENSEVIL = 27343

# ITEMS
JACOB_NECK    = 13814
DEADMANS_HERB = 13816
SCULPTURE     = 14353

class Quest (JQuest) :
	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [JACOB_NECK, DEADMANS_HERB, SCULPTURE]

	def onAdvEvent(self, event, npc, player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return

		if event == "30191-02.htm" :
			st.set("cond","1")
			st.setState(State.STARTED)
			st.giveItems(JACOB_NECK, 1)
			st.playSound("ItemSound.quest_accept")
		elif event == "32569-05.htm" :
			st.set("cond","2")
			st.takeItems(JACOB_NECK,1)
			st.playSound("ItemSound.quest_middle")
		elif event == "32570-02.htm" :
			st.set("cond","3")
			st.giveItems(DEADMANS_HERB, 1)
			st.playSound("ItemSound.quest_middle")
		elif event.isdigit() :
			if int(event) == 9 :
				st.takeItems(DEADMANS_HERB,1)
				st.set("cond","4")
				st.playSound("ItemSound.quest_middle")
				player.showQuestMovie(int(event))
				return ""
		elif event == "32569-09.htm" :
				npc.broadcastPacket(NpcSay(npc.getObjectId(),0,npc.getNpcId(),player.getName() + "! That stranger must be defeated!"))
				monster = self.addSpawn(SHILENSEVIL, 82624, 47422, -3220, 0, False, 60000, True)
				monster.broadcastPacket(NpcSay(monster.getObjectId(),0,monster.getNpcId(),"You are not the owner of that item!"))
				monster.setRunning()
				monster.addDamageHate(player,0,999)
				monster.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, st.getPlayer())
		elif event == "32569-13.htm" :
			st.set("cond","6")
			st.takeItems(SCULPTURE,1)
			st.playSound("ItemSound.quest_middle")
		elif event == "30760-02.htm" :
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

		if npcId == HOLLINT :
			first = player.getQuestState("Q00192_SevenSignSeriesOfDoubt")
			if st.getState() == State.COMPLETED :
				htmltext = Quest.getAlreadyCompletedMsg(player)
			elif first and first.getState() == State.COMPLETED and st.getState() == State.CREATED and player.getLevel() >= 79 :
				htmltext = "30191-01.htm"
			elif cond == 1 :
				htmltext = "30191-03.htm"
			else :
				htmltext = "30191-00.htm"
				st.exitQuest(True)
		elif npcId == CAIN :
			if cond == 1 :
				htmltext = "32569-01.htm"
			elif cond == 2 :
				htmltext = "32569-06.htm"
			elif cond == 3 :
				htmltext = "32569-07.htm"
			elif cond == 4 :
				htmltext = "32569-08.htm"
			elif cond == 5 :
				htmltext = "32569-10.htm"
		elif npcId == ERIC :
			if cond == 2 :
				htmltext = "32570-01.htm"
			elif cond == 3 :
				htmltext = "32570-03.htm"
		elif npcId == ATHEBALDT and cond == 6:
				htmltext = "30760-01.htm"
		return htmltext

	def onKill(self, npc, player, isPet) :
		st = player.getQuestState(qn)
		if not st : return
		if npc.getNpcId() == SHILENSEVIL and st.getInt("cond") == 4 :
			npc.broadcastPacket(NpcSay(npc.getObjectId(),0,npc.getNpcId(),player.getName() + "... You may have won this time... But next time, I will surely capture you!"))
			st.giveItems(SCULPTURE, 1)
			st.set("cond", "5")
		return

QUEST	= Quest(193,qn,"Seven Signs Dying Message")

QUEST.addStartNpc(HOLLINT)
QUEST.addTalkId(HOLLINT)
QUEST.addTalkId(CAIN)
QUEST.addTalkId(ERIC)
QUEST.addTalkId(ATHEBALDT)
QUEST.addKillId(SHILENSEVIL)
