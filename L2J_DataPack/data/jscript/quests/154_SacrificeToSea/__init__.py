# Maked by Mr. Have fun! Version 0.2
print "importing quests: 154: Sacrifice To Sea"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

FOX_FUR_ID = 1032
FOX_FUR_YARN_ID = 1033
MAIDEN_DOLL_ID = 1034
EARING_ID = 113

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        htmltext = "7312-04.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
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
   if npcId == 7312 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if int(st.get("cond"))<15 :
          if st.getPlayer().getLevel() >= 2 :
            htmltext = "7312-03.htm"
            return htmltext
          else:
            htmltext = "7312-02.htm"
            st.exitQuest(1)
        else:
          htmltext = "7312-02.htm"
          st.exitQuest(1)
   elif npcId == 7312 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
        htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7312 and int(st.get("cond"))==1 and (st.getQuestItemsCount(FOX_FUR_YARN_ID)==0 and st.getQuestItemsCount(MAIDEN_DOLL_ID)==0) and st.getQuestItemsCount(FOX_FUR_ID)<10 :
        htmltext = "7312-05.htm"
   elif npcId == 7312 and int(st.get("cond"))==1 and st.getQuestItemsCount(FOX_FUR_ID)>=10 :
        htmltext = "7312-08.htm"
   elif npcId == 7051 and int(st.get("cond"))==1 and st.getQuestItemsCount(FOX_FUR_ID)<10 and st.getQuestItemsCount(FOX_FUR_ID)>0 :
        htmltext = "7051-01.htm"
   elif npcId == 7051 and int(st.get("cond"))==1 and st.getQuestItemsCount(FOX_FUR_ID)>=10 and st.getQuestItemsCount(FOX_FUR_YARN_ID)==0 and st.getQuestItemsCount(MAIDEN_DOLL_ID)==0 and st.getQuestItemsCount(MAIDEN_DOLL_ID)<10 :
        htmltext = "7051-02.htm"
        st.giveItems(FOX_FUR_YARN_ID,1)
        st.takeItems(FOX_FUR_ID,st.getQuestItemsCount(FOX_FUR_ID))
   elif npcId == 7051 and int(st.get("cond"))==1 and st.getQuestItemsCount(FOX_FUR_YARN_ID)>=1 :
        htmltext = "7051-03.htm"
   elif npcId == 7051 and int(st.get("cond"))==1 and st.getQuestItemsCount(MAIDEN_DOLL_ID)==1 :
        htmltext = "7051-04.htm"
   elif npcId == 7312 and int(st.get("cond"))==1 and st.getQuestItemsCount(FOX_FUR_YARN_ID)>=1 :
        htmltext = "7312-06.htm"
   elif npcId == 7055 and int(st.get("cond"))==1 and st.getQuestItemsCount(FOX_FUR_YARN_ID)>=1 :
        htmltext = "7055-01.htm"
        st.giveItems(MAIDEN_DOLL_ID,1)
        st.takeItems(FOX_FUR_YARN_ID,st.getQuestItemsCount(FOX_FUR_YARN_ID))
   elif npcId == 7055 and int(st.get("cond"))==1 and st.getQuestItemsCount(MAIDEN_DOLL_ID)>=1 :
        htmltext = "7055-02.htm"
   elif npcId == 7055 and int(st.get("cond"))==1 and st.getQuestItemsCount(FOX_FUR_YARN_ID)==0 and st.getQuestItemsCount(MAIDEN_DOLL_ID)==0 :
        htmltext = "7055-03.htm"
   elif npcId == 7312 and int(st.get("cond"))==1 and st.getQuestItemsCount(MAIDEN_DOLL_ID)>=1 and int(st.get("onlyone"))==0 :
      if int(st.get("id")) != 154 :
        st.set("id","154")
        htmltext = "7312-07.htm"
        st.takeItems(MAIDEN_DOLL_ID,st.getQuestItemsCount(MAIDEN_DOLL_ID))
        st.giveItems(EARING_ID,1)
        st.addExpAndSp(100,0)
        st.set("cond","0")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
        st.set("onlyone","1")
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 481 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(FOX_FUR_ID)<10 and st.getQuestItemsCount(FOX_FUR_YARN_ID) == 0 :
          if st.getRandom(10)<4 :
            st.giveItems(FOX_FUR_ID,1)
            if st.getQuestItemsCount(FOX_FUR_ID) == 10 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 545 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(FOX_FUR_ID)<10 and st.getQuestItemsCount(FOX_FUR_YARN_ID) == 0 :
          if st.getRandom(10)<4 :
            st.giveItems(FOX_FUR_ID,1)
            if st.getQuestItemsCount(FOX_FUR_ID) == 10 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(154,"154_SacrificeToSea","Sacrifice To Sea")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7312)

STARTING.addTalkId(7312)

STARTED.addTalkId(7051)
STARTED.addTalkId(7055)
STARTED.addTalkId(7312)

STARTED.addKillId(481)
STARTED.addKillId(545)

STARTED.addQuestDrop(481,FOX_FUR_ID,1)
STARTED.addQuestDrop(545,FOX_FUR_ID,1)
STARTED.addQuestDrop(7051,FOX_FUR_YARN_ID,1)
STARTED.addQuestDrop(7055,MAIDEN_DOLL_ID,1)
