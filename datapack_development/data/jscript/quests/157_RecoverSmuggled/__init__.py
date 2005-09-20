# Maked by Mr. Have fun! Version 0.2
print "importing quests: 157: Recover Smuggled"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

ADAMANTITE_ORE_ID = 1024
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        htmltext = "7005-05.htm"
    elif event == "157_1" :
          htmltext = "7005-04.htm"
          return htmltext
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7005 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if int(st.get("cond"))<15 :
          if st.getPlayer().getLevel() >= 5 :
            htmltext = "7005-03.htm"
          else:
            htmltext = "7005-02.htm"
        else:
          htmltext = "7005-02.htm"
   elif npcId == 7005 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7005 and int(st.get("cond"))!=0 and st.getQuestItemsCount(ADAMANTITE_ORE_ID)<20 :
        htmltext = "7005-06.htm"
   elif npcId == 7005 and int(st.get("cond"))!=0 and st.getQuestItemsCount(ADAMANTITE_ORE_ID)>=20 and int(st.get("onlyone"))==0 :
        if int(st.get("id")) != 157 :
          st.set("id","157")
          st.takeItems(ADAMANTITE_ORE_ID,st.getQuestItemsCount(ADAMANTITE_ORE_ID))
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.set("onlyone","1")
          st.giveItems(ADENA_ID,1500)
          htmltext = "7005-07.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 121 :
        st.set("id","0")
        if int(st.get("cond")) != 0 and st.getQuestItemsCount(ADAMANTITE_ORE_ID)<20 and st.getRandom(10)<4 :
            st.giveItems(ADAMANTITE_ORE_ID,1)
            if st.getQuestItemsCount(ADAMANTITE_ORE_ID) == 20 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(157,"157_RecoverSmuggled","Recover Smuggled")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7005)

STARTING.addTalkId(7005)

STARTED.addTalkId(7005)

STARTED.addKillId(121)

STARTED.addQuestDrop(121,ADAMANTITE_ORE_ID,1)
