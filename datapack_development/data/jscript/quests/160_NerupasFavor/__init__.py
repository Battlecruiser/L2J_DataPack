# Maked by Mr. Have fun! Version 0.2
print "importing quests: 160: Nerupas Favor"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

SILVERY_SPIDERSILK_ID = 1026
UNOS_RECEIPT_ID = 1027
CELS_TICKET_ID = 1028
NIGHTSHADE_LEAF_ID = 1029
LESSER_HEALING_POTION_ID = 1060

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        if st.getQuestItemsCount(SILVERY_SPIDERSILK_ID) == 0 :
          st.giveItems(SILVERY_SPIDERSILK_ID,1)
        htmltext = "7370-04.htm"
    return htmltext


 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7370 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if int(st.get("cond"))<15 :
          if st.getPlayer().getRace().ordinal() != 1 :
            htmltext = "7370-00.htm"
          elif st.getPlayer().getLevel() >= 3 :
            htmltext = "7370-03.htm"
            return htmltext
          else:
            htmltext = "7370-02.htm"
            st.exitQuest(1)
        else:
          htmltext = "7370-02.htm"
          st.exitQuest(1)
   elif npcId == 7370 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7370 and int(st.get("cond"))!=0 and (st.getQuestItemsCount(SILVERY_SPIDERSILK_ID)!=0 or st.getQuestItemsCount(UNOS_RECEIPT_ID)!=0 or st.getQuestItemsCount(CELS_TICKET_ID)!=0) :
        htmltext = "7370-05.htm"
   elif npcId == 7147 and int(st.get("cond"))!=0 and st.getQuestItemsCount(SILVERY_SPIDERSILK_ID)!=0 :
        st.takeItems(SILVERY_SPIDERSILK_ID,st.getQuestItemsCount(SILVERY_SPIDERSILK_ID))
        if st.getQuestItemsCount(UNOS_RECEIPT_ID) == 0 :
          st.giveItems(UNOS_RECEIPT_ID,1)
        htmltext = "7147-01.htm"
   elif npcId == 7147 and int(st.get("cond"))!=0 and st.getQuestItemsCount(UNOS_RECEIPT_ID)!=0 :
        htmltext = "7147-02.htm"
   elif npcId == 7149 and int(st.get("cond"))!=0 and st.getQuestItemsCount(UNOS_RECEIPT_ID)!=0 :
        st.takeItems(UNOS_RECEIPT_ID,st.getQuestItemsCount(UNOS_RECEIPT_ID))
        if st.getQuestItemsCount(CELS_TICKET_ID) == 0 :
          st.giveItems(CELS_TICKET_ID,1)
        htmltext = "7149-01.htm"
   elif npcId == 7149 and int(st.get("cond"))!=0 and st.getQuestItemsCount(CELS_TICKET_ID)!=0 :
        htmltext = "7149-02.htm"
   elif npcId == 7152 and int(st.get("cond"))!=0 and st.getQuestItemsCount(CELS_TICKET_ID)!=0 :
        st.takeItems(CELS_TICKET_ID,st.getQuestItemsCount(CELS_TICKET_ID))
        if st.getQuestItemsCount(NIGHTSHADE_LEAF_ID) == 0 :
          st.giveItems(NIGHTSHADE_LEAF_ID,1)
          htmltext = "7152-01.htm"
   elif npcId == 7152 and int(st.get("cond"))!=0 and st.getQuestItemsCount(NIGHTSHADE_LEAF_ID)!=0 :
        htmltext = "7152-02.htm"
   elif npcId == 7149 and int(st.get("cond"))!=0 and st.getQuestItemsCount(NIGHTSHADE_LEAF_ID)!=0 :
        htmltext = "7149-03.htm"
   elif npcId == 7147 and int(st.get("cond"))!=0 and st.getQuestItemsCount(NIGHTSHADE_LEAF_ID)!=0 :
        htmltext = "7147-03.htm"
   elif npcId == 7370 and int(st.get("cond"))!=0 and st.getQuestItemsCount(NIGHTSHADE_LEAF_ID)!=0 and int(st.get("onlyone"))==0 :
        if int(st.get("id")) != 160 :
          st.set("id","160")
          st.takeItems(NIGHTSHADE_LEAF_ID,st.getQuestItemsCount(NIGHTSHADE_LEAF_ID))
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.set("onlyone","1")
          st.giveItems(LESSER_HEALING_POTION_ID,1)
          st.addExpAndSp(1000,0)
          htmltext = "7370-06.htm"
   return htmltext

QUEST       = Quest(160,"160_NerupasFavor","Nerupas Favor")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7370)

STARTING.addTalkId(7370)

STARTED.addTalkId(7147)
STARTED.addTalkId(7149)
STARTED.addTalkId(7152)
STARTED.addTalkId(7370)


STARTED.addQuestDrop(7370,SILVERY_SPIDERSILK_ID,1)
STARTED.addQuestDrop(7147,UNOS_RECEIPT_ID,1)
STARTED.addQuestDrop(7149,CELS_TICKET_ID,1)
STARTED.addQuestDrop(7152,NIGHTSHADE_LEAF_ID,1)
