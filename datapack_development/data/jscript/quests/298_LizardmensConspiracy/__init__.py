# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

PATROLS_REPORT = 7182
SHINING_GEM = 7183
SHINING_RED_GEM = 7184

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "7333-1a.htm" :
     st.set("cond","1")
     st.giveItems(PATROLS_REPORT,1)
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   if event == "7344-1.htm" :
     st.takeItems(PATROLS_REPORT,1)
     st.set("cond","2")
   if event == "7344-3.htm" :
     if st.getQuestItemsCount(SHINING_RED_GEM) == st.getQuestItemsCount(SHINING_GEM) == 50 :
       st.takeItems(SHINING_GEM,-1)
       st.takeItems(SHINING_RED_GEM,-1)
       st.addExpAndSp(0,42000)
       st.playSound("ItemSound.quest_finish")
       st.exitQuest(1)
     else :
       htmltext = "You don't have required items"
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   npcId = npc.getNpcId()
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   cond = int(st.get("cond"))
   if npcId == 7333 and cond == 0  :
     if st.getPlayer().getLevel() >= 25 :
       htmltext = "7333-0a.htm"
     else:
       st.exitQuest(1)
   elif npcId == 7344 :
     if cond == 1 :
       htmltext = "7344-0.htm"
     elif cond == 3 :
       htmltext = "7344-2.htm"
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   if npcId in [925,926] :
     count = st.getQuestItemsCount(SHINING_RED_GEM)
     if count < 50 :
       st.giveItems(SHINING_RED_GEM,1)
       if st.getQuestItemsCount(SHINING_GEM) == 50 and count == 49 :
         st.playSound("ItemSound.quest_middle")
         st.set("cond","3")
       else :
         st.playSound("ItemSound.quest_itemget")
   if npcId in [922,923,924] :
     count = st.getQuestItemsCount(SHINING_GEM)
     if count < 50 :
       st.giveItems(SHINING_GEM,1)
       if count == 49 and st.getQuestItemsCount(SHINING_RED_GEM) == 50 :
         st.playSound("ItemSound.quest_middle")
         st.set("cond","3")
       else :
         st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(298,"298_LizardmensConspiracy","Lizardmen's Conspiracy")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7333)

CREATED.addTalkId(7333)
STARTED.addTalkId(7333)
STARTED.addTalkId(7344)

for i in range(922,927) :
    STARTED.addKillId(i)

STARTED.addQuestDrop(925,SHINING_RED_GEM,1)
STARTED.addQuestDrop(924,SHINING_GEM,1)

print "importing quests: 298: Lizardmen's Conspiracy"
