# Maked by Mr. Have fun! Version 0.2
print "importing quests: 317: Catch The Wind"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

WIND_SHARD_ID = 1078
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
        htmltext = "7361-04.htm"
    elif event == "317_2" :
            if int(st.get("id")) != 317 :
              st.set("id","317")
              if st.getQuestItemsCount(WIND_SHARD_ID) :
                st.giveItems(ADENA_ID,30*st.getQuestItemsCount(WIND_SHARD_ID))
              st.takeItems(WIND_SHARD_ID,st.getQuestItemsCount(WIND_SHARD_ID))
              st.set("cond","0")
              st.setState(COMPLETED)
              st.playSound("ItemSound.quest_finish")
              htmltext = "7361-08.htm"
    elif event == "317_3" :
            if st.getQuestItemsCount(WIND_SHARD_ID) :
              st.giveItems(ADENA_ID,30*st.getQuestItemsCount(WIND_SHARD_ID))
            st.takeItems(WIND_SHARD_ID,st.getQuestItemsCount(WIND_SHARD_ID))
            htmltext = "7361-09.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7361 and int(st.get("cond"))==0 :
        if int(st.get("cond"))<15 :
          if st.getPlayer().getLevel() >= 18 :
            htmltext = "7361-03.htm"
            return htmltext
          else:
            htmltext = "7361-02.htm"
            st.exitQuest(1)
        else:
          htmltext = "7361-02.htm"
          st.exitQuest(1)
   elif npcId == 7361 and int(st.get("cond"))!=0 and st.getQuestItemsCount(WIND_SHARD_ID)==0 :
        htmltext = "7361-05.htm"
   elif npcId == 7361 and int(st.get("cond"))!=0 and st.getQuestItemsCount(WIND_SHARD_ID)!=0 :
        htmltext = "7361-07.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 36 :
        st.set("id","0")
        if int(st.get("cond")) != 0 :
          st.giveItems(WIND_SHARD_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 44 :
        st.set("id","0")
        if int(st.get("cond")) != 0 :
          st.giveItems(WIND_SHARD_ID,1)
          st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(317,"317_CatchTheWind","Catch The Wind")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7361)

STARTING.addTalkId(7361)

STARTED.addTalkId(7361)

STARTED.addKillId(36)
STARTED.addKillId(44)

STARTED.addQuestDrop(36,WIND_SHARD_ID,1)
STARTED.addQuestDrop(44,WIND_SHARD_ID,1)
