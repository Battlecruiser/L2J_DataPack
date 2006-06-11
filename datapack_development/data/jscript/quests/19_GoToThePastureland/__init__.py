# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

htmlhead = "<html><head><body>"
htmlfoot = "</body></html>"

#NPC
VLADIMIR = 8302
TUNATUN  = 8537

#ITEMS
BEAST_MEAT = 7547

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "8302-1.htm" :
     st.giveItems(BEAST_MEAT,1)
     st.set("cond","1")
     st.set("id","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "8537-1.htm" :
     st.takeItems(BEAST_MEAT,1)
     st.giveItems(57,30000)
     st.unset("cond")
     st.setState(COMPLETED)
     st.playSound("ItemSound.quest_finish")
   return htmltext

 def onTalk (self,npc,st):
   htmltext = htmlhead + "I have nothing to say you" + htmlfoot
   npcId = npc.getNpcId()
   id    = st.getState()
   cond  = st.getInt("cond")

   if id == CREATED :
     if st.getPlayer().getLevel() >= 63 :
       htmltext = "8302-0.htm"
     else:
       htmltext = htmlhead + "Quest for characters level 63 or above." + htmlfoot
       st.exitQuest(1)
   elif id == COMPLETED :
     htmltext = htmlhead + "This quest has already been completed." + htmlfoot
   elif npcId == VLADIMIR and cond >= 1 :
     htmltext = "8302-2.htm"
   elif npcId == TUNATUN and cond >= 1 :
     htmltext = "8537-0.htm"
   return htmltext

qnum  = 19
qdef  = str(qnum) + "_GoToThePastureland"
qname = "Go to the Pastureland!"

QUEST     = Quest(qnum,qdef,qname)
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(VLADIMIR)

CREATED.addTalkId(VLADIMIR)

STARTED.addTalkId(VLADIMIR)
STARTED.addTalkId(TUNATUN)

COMPLETED.addTalkId(VLADIMIR)

STARTED.addQuestDrop(VLADIMIR,BEAST_MEAT,1)

print "importing quests: " + str(qnum) + ": " + qname