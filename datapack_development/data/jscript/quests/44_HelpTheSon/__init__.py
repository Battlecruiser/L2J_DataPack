#quest by zerghase

from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

LUNDY=7827
DRIKUS=7505

WORK_HAMMER=168
GEMSTONE_FRAGMENT=7552
GEMSTONE=7553
PET_TICKET=7585

MAILLE_GUARD=921
MAILLE_SCOUT=920
MAILLE_LIZARDMAN=919

MAX_COUNT=30
MIN_LEVEL=24

class Quest (JQuest) :
	def onEvent(self, event, st):
		htmltext=event
		if event=="1":
			htmltext="7827-01.htm"
			st.set("cond","1")
			st.setState(STARTED)
			st.playSound("ItemSound.quest_accept")
		if event=="3" and st.getQuestItemsCount(WORK_HAMMER):
			htmltext="7827-03.htm"
			st.takeItems(WORK_HAMMER,1)
			st.set("cond","2")
		if event=="4" and st.getQuestItemsCount(GEMSTONE_FRAGMENT)>=MAX_COUNT:
			htmltext="7827-05.htm"
			st.takeItems(GEMSTONE_FRAGMENT,MAX_COUNT)
			st.giveItems(GEMSTONE,1)
			st.set("cond", "4")
		if event=="5" and st.getQuestItemsCount(GEMSTONE):
			htmltext="7505-06.htm"
			st.takeItems(GEMSTONE,1)
			st.set("cond","5")
		if event=="7":
			htmltext="7827-07.htm"
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
				htmltext="7827-00.htm"
			else:
				st.exitQuest(1)
				htmltext="<html><head><body>This quest can only be taken by characters that have a minimum level of %s. Return when you are more experienced.</body></html>" % MIN_LEVEL
		elif id==STARTED:
			cond=int(st.get("cond"))
			if npcId==LUNDY:
				if cond==1:
					if not st.getQuestItemsCount(WORK_HAMMER):
						htmltext="7827-01a.htm"
					else:
						htmltext="7827-02.htm"
				elif cond==2:
					htmltext="7827-03a.htm"
				elif cond==3:
						htmltext="7827-04.htm"
				elif cond==4:
					htmltext="7827-05a.htm"
				elif cond==5:
					htmltext="7827-06.htm"
			elif npcId==DRIKUS:
				if cond==4 and st.getQuestItemsCount(GEMSTONE):
					htmltext="7505-05.htm"
				elif cond==5:
					htmltext="7505-06a.htm"
		elif id==COMPLETED:
			st.exitQuest(0)
			htmltext="<html><head><body>This quest have already been completed.</body></html>"
		return htmltext

	def onKill(self, npc, st):
		npcId = npc.getNpcId()
		cond=int(st.get("cond"))
		if cond==2:
			pieces=st.getQuestItemsCount(GEMSTONE_FRAGMENT)
			if pieces<MAX_COUNT-1:
				st.giveItems(GEMSTONE_FRAGMENT,1)
				st.playSound("ItemSound.quest_itemget")
			elif pieces==MAX_COUNT-1:
				st.giveItems(GEMSTONE_FRAGMENT,1)
				st.playSound("ItemSound.quest_middle")
				st.set("cond", "3")

QUEST=Quest(44,"44_HelpTheSon","Help The Son!")
CREATED=State('Start', QUEST)
STARTED=State('Started', QUEST)
COMPLETED=State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(LUNDY)

CREATED.addTalkId(LUNDY)

STARTED.addTalkId(LUNDY)
STARTED.addTalkId(DRIKUS)

STARTED.addKillId(MAILLE_GUARD)
STARTED.addKillId(MAILLE_SCOUT)
STARTED.addKillId(MAILLE_LIZARDMAN)

print "importing quests: 44: Help The Son!"