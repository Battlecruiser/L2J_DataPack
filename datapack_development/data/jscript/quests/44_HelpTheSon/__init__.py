#quest by zerghase
import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

htmlhead = "<html><head><body>"
htmlfoot = "</body></html>"

#NPCs
LUNDY  = 7827
DRIKUS = 7505

#MOBS
MAILLE_GUARD=921
MAILLE_SCOUT=920
MAILLE_LIZARDMAN=919

#ITEMS
WORK_HAMMER       = 168
GEMSTONE_FRAGMENT = 7552
GEMSTONE          = 7553

MAX_COUNT = 30

#REWARD
PET_TICKET = 7585

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent(self, event, st):
   htmltext = event
   if event == "7827-01.htm" :
     st.set("cond","1")
     st.set("id","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   if event == "7827-03.htm" and st.getQuestItemsCount(WORK_HAMMER) :
     st.takeItems(WORK_HAMMER,1)
     st.set("cond","2")
     st.set("id","2")
     st.playSound("ItemSound.quest_middle")
   if event == "7827-05.htm" and st.getQuestItemsCount(GEMSTONE_FRAGMENT) >= MAX_COUNT :
     st.takeItems(GEMSTONE_FRAGMENT,MAX_COUNT)
     st.giveItems(GEMSTONE,1)
     st.set("cond", "4")
     st.set("id", "4")
     st.playSound("ItemSound.quest_middle")
   if event == "7505-06.htm" and st.getQuestItemsCount(GEMSTONE) :
     st.takeItems(GEMSTONE,1)
     st.set("cond","5")
     st.set("id","5")
     st.playSound("ItemSound.quest_middle")
   if event == "7827-07.htm" :
     st.giveItems(PET_TICKET,1)
     st.unset("cond")
     st.setState(COMPLETED)
     st.playSound("ItemSound.quest_finish")
   return htmltext

 def onTalk(self, npc, st):
   htmltext = htmlhead + "I have nothing to say you" + htmlfoot
   npcId = npc.getNpcId()
   id    = st.getState()
   cond  = st.getInt("cond")

   if id == CREATED:
     if st.getPlayer().getLevel() >= 24 :
       htmltext = "7827-00.htm"
     else:
       htmltext = htmlhead + "Quest for characters level 24 and above." + htmlfoot
       st.exitQuest(1)
   elif id == COMPLETED :
     htmltext = htmlhead + "This quest has already been completed." + htmlfoot
   elif npcId == LUNDY and cond == 1 :
     if not st.getQuestItemsCount(WORK_HAMMER):
       htmltext = "7827-01a.htm"
     else:
       htmltext = "7827-02.htm"
   elif npcId == LUNDY and cond == 2 :
     htmltext = "7827-03a.htm"
   elif npcId == LUNDY and cond == 3 :
     htmltext = "7827-04.htm"
   elif npcId == LUNDY and cond == 4 :
     htmltext = "7827-05a.htm"
   elif npcId == LUNDY and cond == 5 :
     htmltext = "7827-06.htm"
   elif npcId == DRIKUS and cond == 4 and st.getQuestItemsCount(GEMSTONE) :
     htmltext = "7505-05.htm"
   elif npcId == DRIKUS and cond == 5 :
     htmltext = "7505-06a.htm"
   return htmltext

 def onKill(self, npc, st):
   if int(st.get("cond")) == 2 :
      pieces = st.getQuestItemsCount(GEMSTONE_FRAGMENT)
      if pieces < MAX_COUNT - 1 :
        st.giveItems(GEMSTONE_FRAGMENT,1)
        st.playSound("ItemSound.quest_itemget")
      elif pieces == MAX_COUNT - 1 :
        st.giveItems(GEMSTONE_FRAGMENT,1)
        st.set("cond", "3")
        st.set("id", "3")
        st.playSound("ItemSound.quest_middle")

qnum  = 44
qdef  = str(qnum) + "_HelpTheSon"
qname = "Help the Son!"

QUEST     = Quest(qnum,qdef,qname)
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(LUNDY)

CREATED.addTalkId(LUNDY)

STARTED.addTalkId(LUNDY)
STARTED.addTalkId(DRIKUS)

COMPLETED.addTalkId(LUNDY)

STARTED.addKillId(MAILLE_GUARD)
STARTED.addKillId(MAILLE_SCOUT)
STARTED.addKillId(MAILLE_LIZARDMAN)

STARTED.addQuestDrop(LUNDY,WORK_HAMMER,1)
STARTED.addQuestDrop(LUNDY,GEMSTONE_FRAGMENT,1)
STARTED.addQuestDrop(LUNDY,GEMSTONE,1)

print "importing quests: " + str(qnum) + ": " + qname