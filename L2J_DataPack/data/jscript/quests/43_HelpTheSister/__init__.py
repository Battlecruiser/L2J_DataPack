#quest by zerghase

from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

COOPER=7829
GALLADUCCI=7097

CRAFTED_DAGGER=220
MAP_PIECE=7550
MAP=7551
PET_TICKET=7584

SPECTER=171
SORROW_MAIDEN=197

MAX_COUNT=30
MIN_LEVEL=26

class Quest (JQuest) :
	def onEvent(self, event, st):
		htmltext=event
		if event=="1":
			htmltext="7829-01.htm"
			st.set("cond","1")
			st.setState(STARTED)
			st.playSound("ItemSound.quest_accept")
		elif event=="3" and st.getQuestItemsCount(CRAFTED_DAGGER):
			htmltext="7829-03.htm"
			st.takeItems(CRAFTED_DAGGER,1)
			st.set("cond","2")
		elif event=="4" and st.getQuestItemsCount(MAP_PIECE)>=MAX_COUNT:
			htmltext="7829-05.htm"
			st.takeItems(MAP_PIECE,MAX_COUNT)
			st.giveItems(MAP,1)
			st.set("cond", "4")
		elif event=="5" and st.getQuestItemsCount(MAP):
			htmltext="7097-06.htm"
			st.takeItems(MAP,1)
			st.set("cond","5")
		elif event=="7":
			htmltext="7829-07.htm"
			st.giveItems(PET_TICKET,1)
			st.setState(COMPLETED)
			st.exitQuest(0)
		return htmltext

	def onTalk(self, npc, st):
		npcId=npc.getNpcId()
		htmltext="<html><head><body>I have nothing to say you</body></html>"
		id=st.getState()
		if id==CREATED:
			if st.getPlayer().getLevel()>=MIN_LEVEL:
				htmltext="7829-00.htm"
			else:
				st.exitQuest(1)
				htmltext="<html><head><body>This quest can only be taken by characters that have a minimum level of %s. Return when you are more experienced.</body></html>" % MIN_LEVEL
		elif id==STARTED:
			cond=int(st.get("cond"))
			if npcId==COOPER:
				if cond==1:
					if not st.getQuestItemsCount(CRAFTED_DAGGER):
						htmltext="7829-01a.htm"
					else:
						htmltext="7829-02.htm"
				elif cond==2:
					htmltext="7829-03a.htm"
				elif cond==3:
						htmltext="7829-04.htm"
				elif cond==4:
					htmltext="7829-05a.htm"
				elif cond==5:
					htmltext="7829-06.htm"
			elif npcId==GALLADUCCI:
				if cond==4 and st.getQuestItemsCount(MAP):
					htmltext="7097-05.htm"
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

QUEST=Quest(43,"43_HelpTheSister","Help The Sister!")
CREATED=State('Start', QUEST)
STARTED=State('Started', QUEST)
COMPLETED=State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(COOPER)

CREATED.addTalkId(COOPER)

STARTED.addTalkId(COOPER)
STARTED.addTalkId(GALLADUCCI)

STARTED.addKillId(SPECTER)
STARTED.addKillId(SORROW_MAIDEN)

print "importing quests: 43: Help The Sister!"