# Made by Mr. Have fun!
# Version 0.3 by H1GHL4ND3R
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

SILVERY_SPIDERSILK = 1026
UNOS_RECEIPT = 1027
CELS_TICKET = 1028
NIGHTSHADE_LEAF = 1029
LESSER_HEALING_POTION = 1060

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7370-04.htm" :
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.giveItems(SILVERY_SPIDERSILK,1)
    return htmltext

 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     if st.getPlayer().getRace().ordinal() != 1 :
       htmltext = "7370-00.htm"
     elif st.getPlayer().getLevel() >= 3 :
       htmltext = "7370-03.htm"
       st.set("cond","0")
     else:
       htmltext = "7370-02.htm"
       st.exitQuest(1)
   elif id == COMPLETED :
     htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   else :
     try :
       cond = int(st.get("cond"))
     except :
       cond = None
     if cond == 1 :
       if npcId == 7370 :
         htmltext = "7370-05.htm"
       elif npcId == 7147 and st.getQuestItemsCount(SILVERY_SPIDERSILK) :
         st.takeItems(SILVERY_SPIDERSILK,1)
         st.giveItems(UNOS_RECEIPT,1)
         st.set("cond","2")
         htmltext = "7147-01.htm"
     elif cond == 2 :
       if npcId == 7370 :
         htmltext = "7370-05.htm"
       elif npcId == 7147 and st.getQuestItemsCount(UNOS_RECEIPT) :
         htmltext = "7147-02.htm"
       elif npcId == 7149 and st.getQuestItemsCount(UNOS_RECEIPT) :
         st.takeItems(UNOS_RECEIPT,1)
         st.giveItems(CELS_TICKET,1)
         st.set("cond","3")
         htmltext = "7149-01.htm"
     elif cond == 3 :
       if npcId == 7370 :
         htmltext = "7370-05.htm"
       elif npcId == 7149 and st.getQuestItemsCount(CELS_TICKET) :
         htmltext = "7149-02.htm"
       elif npcId == 7152 and st.getQuestItemsCount(CELS_TICKET) :
        st.takeItems(CELS_TICKET,st.getQuestItemsCount(CELS_TICKET))
        st.giveItems(NIGHTSHADE_LEAF,1)
        st.set("cond","4")
        htmltext = "7152-01.htm"
     elif cond == 4 :
        if npcId == 7152 and st.getQuestItemsCount(NIGHTSHADE_LEAF) :
          htmltext = "7152-02.htm"
        elif npcId == 7149 and st.getQuestItemsCount(NIGHTSHADE_LEAF) :
          htmltext = "7149-03.htm"
        elif npcId == 7147 and st.getQuestItemsCount(NIGHTSHADE_LEAF) :
          htmltext = "7147-03.htm"
        elif npcId == 7370 and st.getQuestItemsCount(NIGHTSHADE_LEAF) :
          st.takeItems(NIGHTSHADE_LEAF,1)
          st.giveItems(LESSER_HEALING_POTION,1)
          st.addExpAndSp(1000,0)
          st.unset("cond")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          htmltext = "7370-06.htm"
   return htmltext

QUEST       = Quest(160,"160_NerupasFavor","Nerupas Favor")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7370)

CREATED.addTalkId(7370)
STARTING.addTalkId(7370)
COMPLETED.addTalkId(7370)

STARTED.addTalkId(7147)
STARTED.addTalkId(7149)
STARTED.addTalkId(7152)
STARTED.addTalkId(7370)

STARTED.addQuestDrop(7370,SILVERY_SPIDERSILK,1)
STARTED.addQuestDrop(7147,UNOS_RECEIPT,1)
STARTED.addQuestDrop(7149,CELS_TICKET,1)
STARTED.addQuestDrop(7152,NIGHTSHADE_LEAF,1)

print "importing quests: 160: Nerupas Favor"
