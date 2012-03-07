# 2010-08-11 by Gnacik
# Based on Freya PTS
import sys
from com.l2jserver import Config
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest

qn = "310_OnlyWhatRemains"

# NPCs
KINTAIJIN = 32640

GROW_ACCELERATOR = 14832
MULTI_COLORED_JEWEL = 14835
DIRTY_BEAD = 14880

DROP_CHANCE = 60

MOBS = range(22617,22634)

class Quest (JQuest) :
	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.questItemIds = [DIRTY_BEAD]

	def onAdvEvent(self, event, npc, player) :
		htmltext = event
		st = player.getQuestState(qn)
		if not st : return

		if event == "32640-04.htm" :
			st.set("cond","1")
			st.setState(State.STARTED)
			st.playSound("ItemSound.quest_accept")
		elif event == "32640-quit.htm" :
			st.unset("cond")
			st.exitQuest(True)
			st.playSound("ItemSound.quest_finish")
		return htmltext

	def onTalk (self, npc, player) :
		htmltext = Quest.getNoQuestMsg(player)
		st = player.getQuestState(qn)
		if not st : return htmltext

		npcId = npc.getNpcId()
		cond = st.getInt("cond")
		if st.getState() == State.CREATED :
			if npcId == KINTAIJIN :
				first = player.getQuestState("240_ImTheOnlyOneYouCanTrust")
				if first and first.getState() == State.COMPLETED and player.getLevel() >= 81 :
					htmltext = "32640-01.htm"
				else :
					htmltext = "32640-00.htm"
		elif st.getState() == State.STARTED :
			if npcId == KINTAIJIN and cond == 1 :
				beads = st.getQuestItemsCount(DIRTY_BEAD)
				if beads == 0 :
					htmltext = "32640-08.htm"
				elif beads < 500 :
					htmltext = "32640-09.htm"
				elif beads >= 500 :
					st.takeItems(DIRTY_BEAD,500)
					st.giveItems(GROW_ACCELERATOR,1)
					st.giveItems(MULTI_COLORED_JEWEL,1)
					htmltext = "32640-10.htm"
		return htmltext

	def onKill(self, npc, player, isPet) :
		partyMember = self.getRandomPartyMember(player,"1")
		if not partyMember : return
		st = partyMember.getQuestState(qn)
		if not st : return

		if st.getInt("cond") == 1 and npc.getNpcId() in MOBS :
			chance = DROP_CHANCE*Config.RATE_QUEST_DROP
			numItems, chance = divmod(chance,100)
			if self.getRandom(100) < chance : 
				numItems += 1
			if numItems :
				st.giveItems(DIRTY_BEAD, int(numItems))
				st.playSound("ItemSound.quest_itemget")
		return

QUEST	= Quest(310,qn,"Only What Remains")

QUEST.addStartNpc(KINTAIJIN)
QUEST.addTalkId(KINTAIJIN)

for i in MOBS :
	QUEST.addKillId(i)