#quest by zerghase
import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

htmlhead = "<html><head><body>"
htmlfoot = "</body></html>"

#NPCs
WATERS = 7828
SOPHYA = 7735

#MOBS
MONSTER_EYE_DESTROYER = 68
MONSTER_EYE_GAZER     = 266

#ITEMS
TRIDENT    = 291
MAP_PIECE  = 7548
MAP        = 7549

MAX_COUNT = 30

#REWARD
PET_TICKET = 7583

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent(self, event, st):
   htmltext = event
   if event == "7828-01.htm" :
     st.set("cond","1")
     st.set("id","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "7828-03.htm" and st.getQuestItemsCount(TRIDENT) :
     st.takeItems(TRIDENT,1)
     st.set("cond","2")
     st.set("id","2")
     st.playSound("ItemSound.quest_middle")
   elif event == "7828-05.htm" and st.getQuestItemsCount(MAP_PIECE) >= MAX_COUNT :
     st.takeItems(MAP_PIECE,MAX_COUNT)
     st.giveItems(MAP,1)
     st.set("cond", "4")
     st.set("id", "4")
     st.playSound("ItemSound.quest_middle")
   elif event == "7735-06.htm" and st.getQuestItemsCount(MAP) :
     st.takeItems(MAP,1)
     st.set("cond","5")
     st.set("id","5")
     st.playSound("ItemSound.quest_middle")
   elif event == "7828-07.htm" :
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

   if id == CREATED :
     if st.getPlayer().getLevel() >= 25 :
       htmltext = "7828-00.htm"
     else:
       htmltext = htmlhead + "Quest for characters level 25 and above." + htmlfoot
       st.exitQuest(1)
   elif id == COMPLETED :
     htmltext=htmlhead + "This quest has already been completed." + htmlfoot
   elif npcId == WATERS and cond==1 :
     if not st.getQuestItemsCount(TRIDENT):
       htmltext = "7828-01a.htm"
     else:
       htmltext = "7828-02.htm"
   elif npcId == WATERS and cond==2 :
     htmltext = "7828-03a.htm"
   elif npcId == WATERS and cond==3 :
     htmltext = "7828-04.htm"
   elif npcId == WATERS and cond==4 :
     htmltext = "7828-05a.htm"
   elif npcId == WATERS and cond==5 :
     htmltext = "7828-06.htm"
   elif npcId == SOPHYA and cond==4 and st.getQuestItemsCount(MAP) :
     htmltext = "7735-05.htm"
   elif npcId == SOPHYA and cond==5 :
     htmltext = "7735-06a.htm"
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

qnum  = 42
qdef  = str(qnum) + "_HelpTheUncle"
qname = "Help the Uncle!"

QUEST     = Quest(qnum,qdef,qname)
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(WATERS)

CREATED.addTalkId(WATERS)

STARTED.addTalkId(WATERS)
STARTED.addTalkId(SOPHYA)

COMPLETED.addTalkId(WATERS)

STARTED.addKillId(MONSTER_EYE_DESTROYER)
STARTED.addKillId(MONSTER_EYE_GAZER)

STARTED.addQuestDrop(WATERS,TRIDENT,1)
STARTED.addQuestDrop(WATERS,MAP_PIECE,1)
STARTED.addQuestDrop(WATERS,MAP,1)

print "importing quests: " + str(qnum) + ": " + qname