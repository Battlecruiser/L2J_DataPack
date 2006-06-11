#quest by zerghase
import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

htmlhead = "<html><head><body>"
htmlfoot = "</body></html>"

#NPCs
COOPER     = 7829
GALLADUCCI = 7097

#MOBS
SPECTER       = 171
SORROW_MAIDEN = 197

#ITEMS
CRAFTED_DAGGER = 220
MAP_PIECE      = 7550
MAP            = 7551

MAX_COUNT = 30

#REWARD
PET_TICKET = 7584

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent(self, event, st):
   htmltext = event
   if event == "7829-01.htm" :
     st.set("cond","1")
     st.set("id","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "7829-03.htm" and st.getQuestItemsCount(CRAFTED_DAGGER) :
     st.takeItems(CRAFTED_DAGGER,1)
     st.set("cond","2")
     st.set("id","2")
     st.playSound("ItemSound.quest_middle")
   elif event == "7829-05.htm" and st.getQuestItemsCount(MAP_PIECE) >= MAX_COUNT :
     st.takeItems(MAP_PIECE,MAX_COUNT)
     st.giveItems(MAP,1)
     st.set("cond", "4")
     st.set("id", "4")
     st.playSound("ItemSound.quest_middle")
   elif event == "7097-06.htm" and st.getQuestItemsCount(MAP) :
     st.takeItems(MAP,1)
     st.set("cond","5")
     st.set("id","5")
     st.playSound("ItemSound.quest_middle")
   elif event == "7829-07.htm" :
     st.giveItems(PET_TICKET,1)
     st.unset("cond")
     st.setState(COMPLETED)
     st.playSound("ItemSound.quest_finish")
   return htmltext

 def onTalk(self, npc, st):
   htmltext = htmlhead + "I have nothing to say you" + htmlfoot
   npcId = npc.getNpcId()
   id    = st.getState()
   cond  = int(st.get("cond"))

   if id == CREATED :
     if st.getPlayer().getLevel() >= 26 :
       htmltext = "7829-00.htm"
     else:
       htmltext = htmlhead + "Quest for characters level 26 and above." + htmlfoot
       st.exitQuest(1)
   elif id == COMPLETED :
     htmltext=htmlhead + "This quest has already been completed." + htmlfoot
   elif npcId == COOPER and cond == 1 :
     if not st.getQuestItemsCount(CRAFTED_DAGGER):
       htmltext = "7829-01a.htm"
     else:
       htmltext = "7829-02.htm"
   elif npcId == COOPER and cond == 2 :
     htmltext = "7829-03a.htm"
   elif npcId == COOPER and cond == 3 :
      htmltext = "7829-04.htm"
   elif npcId == COOPER and cond == 4 :
     htmltext = "7829-05a.htm"
   elif npcId == COOPER and cond == 5 :
     htmltext = "7829-06.htm"
   elif npcId == GALLADUCCI and cond == 4 and st.getQuestItemsCount(MAP) :
     htmltext = "7097-05.htm"
   return htmltext

 def onKill(self, npc, st):
   if int(st.get("cond")) == 2:
     pieces = st.getQuestItemsCount(MAP_PIECE)
     if pieces < MAX_COUNT - 1:
       st.giveItems(MAP_PIECE,1)
       st.playSound("ItemSound.quest_itemget")
     elif pieces == MAX_COUNT - 1:
       st.giveItems(MAP_PIECE,1)
       st.set("cond", "3")
       st.set("id", "3")
       st.playSound("ItemSound.quest_middle")

qnum  = 43
qdef  = str(qnum) + "_HelpTheSister"
qname = "Help the Sister!"

QUEST     = Quest(qnum,qdef,qname)
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(COOPER)

CREATED.addTalkId(COOPER)

STARTED.addTalkId(COOPER)
STARTED.addTalkId(GALLADUCCI)

COMPLETED.addTalkId(COOPER)

STARTED.addKillId(SPECTER)
STARTED.addKillId(SORROW_MAIDEN)

STARTED.addQuestDrop(COOPER,CRAFTED_DAGGER,1)
STARTED.addQuestDrop(COOPER,MAP_PIECE,1)
STARTED.addQuestDrop(COOPER,MAP,1)

print "importing quests: " + str(qnum) + ": " + qname