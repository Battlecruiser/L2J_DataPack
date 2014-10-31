# Maked by Mr. Have fun! Version 0.2
print "importing quests: 152: Shards Of Golem"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

HARRYS_RECEIPT1_ID = 1008
HARRYS_RECEIPT2_ID = 1009
GOLEM_SHARD_ID = 1010
TOOL_BOX_ID = 1011
COTTON_TUNIC_ID = 1100

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        if st.getQuestItemsCount(HARRYS_RECEIPT1_ID) == 0 :
          st.giveItems(HARRYS_RECEIPT1_ID,1)
        htmltext = "7035-04.htm"
    elif event == "152_2" :
            st.takeItems(HARRYS_RECEIPT1_ID,st.getQuestItemsCount(HARRYS_RECEIPT1_ID))
            if st.getQuestItemsCount(HARRYS_RECEIPT2_ID) == 0 :
              st.giveItems(HARRYS_RECEIPT2_ID,1)
            htmltext = "7283-02.htm"
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
   if npcId == 7035 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if int(st.get("cond"))<15 :
          if st.getPlayer().getLevel() >= 10 :
            htmltext = "7035-03.htm"
            return htmltext
          else:
            htmltext = "7035-02.htm"
            st.exitQuest(1)
        else:
          htmltext = "7035-02.htm"
          st.exitQuest(1)
   elif npcId == 7035 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7035 and int(st.get("cond"))!=0 and st.getQuestItemsCount(HARRYS_RECEIPT1_ID)!=0 and st.getQuestItemsCount(TOOL_BOX_ID)==0 :
        htmltext = "7035-05.htm"
   elif npcId == 7035 and int(st.get("cond"))!=0 and st.getQuestItemsCount(HARRYS_RECEIPT2_ID)!=0 and st.getQuestItemsCount(TOOL_BOX_ID)==0 :
        htmltext = "7035-05.htm"
   elif npcId == 7283 and int(st.get("cond"))!=0 and st.getQuestItemsCount(HARRYS_RECEIPT1_ID)!=0 :
        htmltext = "7283-01.htm"
   elif npcId == 7283 and int(st.get("cond"))!=0 and st.getQuestItemsCount(HARRYS_RECEIPT2_ID)!=0 and st.getQuestItemsCount(GOLEM_SHARD_ID)<5 and st.getQuestItemsCount(TOOL_BOX_ID)==0 :
        htmltext = "7283-03.htm"
   elif npcId == 7283 and int(st.get("cond"))!=0 and st.getQuestItemsCount(HARRYS_RECEIPT2_ID)!=0 and st.getQuestItemsCount(GOLEM_SHARD_ID)>=5 and st.getQuestItemsCount(TOOL_BOX_ID)==0 :
        st.takeItems(GOLEM_SHARD_ID,st.getQuestItemsCount(GOLEM_SHARD_ID))
        if st.getQuestItemsCount(TOOL_BOX_ID) == 0 :
          st.giveItems(TOOL_BOX_ID,1)
        htmltext = "7283-04.htm"
   elif npcId == 7283 and int(st.get("cond"))!=0 and st.getQuestItemsCount(HARRYS_RECEIPT2_ID)!=0 and st.getQuestItemsCount(TOOL_BOX_ID)!=0 :
        htmltext = "7283-05.htm"
   elif npcId == 7035 and int(st.get("cond"))!=0 and st.getQuestItemsCount(TOOL_BOX_ID)!=0 and int(st.get("onlyone"))==0 :
      if int(st.get("id")) != 152 :
        st.set("id","152")
        st.takeItems(TOOL_BOX_ID,st.getQuestItemsCount(TOOL_BOX_ID))
        st.takeItems(HARRYS_RECEIPT2_ID,st.getQuestItemsCount(HARRYS_RECEIPT2_ID))
        st.set("cond","0")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
        st.set("onlyone","1")
        st.giveItems(COTTON_TUNIC_ID,1)
        st.addExpAndSp(5000,0)
        htmltext = "7035-06.htm"
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 16 :
        st.set("id","0")
        if int(st.get("cond")) != 0 and st.getRandom(100)<30 and st.getQuestItemsCount(GOLEM_SHARD_ID)<5 :
          st.giveItems(GOLEM_SHARD_ID,1)
          if st.getQuestItemsCount(GOLEM_SHARD_ID) == 5 :
            st.playSound("ItemSound.quest_middle")
          else:
            st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(152,"152_ShardsOfGolem","Shards Of Golem")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7035)

STARTING.addTalkId(7035)

STARTED.addTalkId(7035)
STARTED.addTalkId(7283)

STARTED.addKillId(16)

STARTED.addQuestDrop(7035,HARRYS_RECEIPT1_ID,1)
STARTED.addQuestDrop(16,GOLEM_SHARD_ID,1)
STARTED.addQuestDrop(7283,TOOL_BOX_ID,1)
STARTED.addQuestDrop(7283,HARRYS_RECEIPT2_ID,1)
