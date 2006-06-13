#quest by zerghase

from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

WATERS=7828
SOPHYA=7735

TRIDENT=291
MAP_PIECE=7548
MAP=7549
PET_TICKET=7583

MONSTER_EYE_DESTROYER=68
MONSTER_EYE_GAZER=266

MAX_COUNT=30
MIN_LEVEL=25

class Quest (JQuest) :
	def onEvent(self, event, st):
		htmltext=event
		if event=="1":
			htmltext="7828-01.htm"
			st.set("cond","1")
			st.setState(STARTED)
			st.playSound("ItemSound.quest_accept")
		elif event=="3" and st.getQuestItemsCount(TRIDENT):
			htmltext="7828-03.htm"
			st.takeItems(TRIDENT,1)
			st.set("cond","2")
		elif event=="4" and st.getQuestItemsCount(MAP_PIECE)>=MAX_COUNT:
			htmltext="7828-05.htm"
			st.takeItems(MAP_PIECE,MAX_COUNT)
			st.giveItems(MAP,1)
			st.set("cond", "4")
		elif event=="5" and st.getQuestItemsCount(MAP):
			htmltext="7735-06.htm"
			st.takeItems(MAP,1)
			st.set("cond","5")
		elif event=="7":
			htmltext="7828-07.htm"
			st.giveItems(PET_TICKET,1)
			st.unset("cond")
			st.setState(COMPLETED)
			st.exitQuest(0)
		return htmltext

	def onTalk(self, npc, st):
		npcId=npc.getNpcId()
		htmltext="<html><head><body>I have nothing to say you</body></html>"
		id=st.getState()
		if id==CREATED:
			if st.getPlayer().getLevel()>=MIN_LEVEL:
				htmltext="7828-00.htm"
			else:
				htmltext="<html><head><body>This quest can only be taken by characters that have a minimum level of %s. Return when you are more experienced.</body></html>" % MIN_LEVEL
				st.exitQuest(1)
		elif id==STARTED:
			cond=int(st.get("cond"))
			if npcId==WATERS:
				if cond==1:
					if not st.getQuestItemsCount(TRIDENT):
						htmltext="7828-01a.htm"
					else:
						htmltext="7828-02.htm"
				elif cond==2:
					htmltext="7828-03a.htm"
				elif cond==3:
						htmltext="7828-04.htm"
				elif cond==4:
					htmltext="7828-05a.htm"
				elif cond==5:
					htmltext="7828-06.htm"
			elif npcId==SOPHYA:
				cond=int(st.get("cond"))
				if cond==4 and st.getQuestItemsCount(MAP):
					htmltext="7735-05.htm"
				elif cond==5:
					htmltext="7735-06a.htm"
		elif id==COMPLETED:
			st.exitQuest(0)
			htmltext="<html><head><body>This quest have already been completed.</body></html>"
		return htmltext

	def onKill(self, npc, st):
		npcId = npc.getNpcId()
		cond=int(st.get("cond"))
		if cond==2:
			pieces=st.getQuestItemsCount(MAP_PIECE)
			if pieces<MAX_COUNT-1:
				st.giveItems(MAP_PIECE,1)
				st.playSound("ItemSound.quest_itemget")
			elif pieces==MAX_COUNT-1:
				st.giveItems(MAP_PIECE,1)
				st.playSound("ItemSound.quest_middle")
				st.set("cond", "3")

QUEST=Quest(42,"42_HelpTheUncle","Help The Uncle!")
CREATED=State('Start', QUEST)
STARTED=State('Started', QUEST)
COMPLETED=State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(WATERS)

CREATED.addTalkId(WATERS)

STARTED.addTalkId(WATERS)
STARTED.addTalkId(SOPHYA)

STARTED.addKillId(MONSTER_EYE_DESTROYER)
STARTED.addKillId(MONSTER_EYE_GAZER)

print "importing quests: 42: Help The Uncle!"