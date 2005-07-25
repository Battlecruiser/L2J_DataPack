# Maked by Mr. Have fun! Version 0.2
print "importing quests: 306: Crystal Of Fireice"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

FLAME_SHARD_ID = 1020
ICE_SHARD_ID = 1021
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      htmltext = "7004-04.htm"
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
    elif event == "306_2" :
      st.set("cond","0")
      st.setState(COMPLETED)
      st.playSound("ItemSound.quest_finish")
      htmltext = "7004-08.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7004 and int(st.get("cond"))==0 :
      if int(st.get("cond"))<15 :
        if st.getPlayer().getLevel() >= 17 :
          htmltext = "7004-03.htm"
          st.set("cond","1")
          return htmltext
        else:
          htmltext = "7004-02.htm"
      else:
        htmltext = "7004-02.htm"
   elif npcId == 7004 and int(st.get("cond"))==1 and st.getQuestItemsCount(FLAME_SHARD_ID)==0 and st.getQuestItemsCount(ICE_SHARD_ID)==0 :
      htmltext = "7004-05.htm"
   elif npcId == 7004 and int(st.get("cond"))==1 and (st.getQuestItemsCount(FLAME_SHARD_ID)>0 or st.getQuestItemsCount(ICE_SHARD_ID)>0) :
      if int(st.get("id")) != 306 :
        st.set("id","306")
        st.giveItems(ADENA_ID,60*st.getQuestItemsCount(FLAME_SHARD_ID)+60*st.getQuestItemsCount(ICE_SHARD_ID))
        st.takeItems(FLAME_SHARD_ID,st.getQuestItemsCount(FLAME_SHARD_ID))
        st.takeItems(ICE_SHARD_ID,st.getQuestItemsCount(ICE_SHARD_ID))
        htmltext = "7004-07.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 110 :
        st.set("id","0")
        if int(st.get("cond")) == 1 :
          if st.getRandom(100)<30 :
            st.giveItems(ICE_SHARD_ID,1)
            st.playSound("ItemSound.quest_itemget")
   elif npcId == 113 :
        st.set("id","0")
        if int(st.get("cond")) == 1 :
          if st.getRandom(100)<40 :
            st.giveItems(ICE_SHARD_ID,1)
            st.playSound("ItemSound.quest_itemget")
   elif npcId == 115 :
        st.set("id","0")
        if int(st.get("cond")) == 1 :
          if st.getRandom(100)<50 :
            st.giveItems(ICE_SHARD_ID,1)
            st.playSound("ItemSound.quest_itemget")
   elif npcId == 109 :
        st.set("id","0")
        if int(st.get("cond")) == 1 :
          if st.getRandom(100)<30 :
            st.giveItems(FLAME_SHARD_ID,1)
            st.playSound("ItemSound.quest_itemget")
   elif npcId == 112 :
        st.set("id","0")
        if int(st.get("cond")) == 1 :
          if st.getRandom(100)<40 :
            st.giveItems(FLAME_SHARD_ID,1)
            st.playSound("ItemSound.quest_itemget")
   elif npcId == 114 :
        st.set("id","0")
        if int(st.get("cond")) == 1 :
          if st.getRandom(100)<50 :
            st.giveItems(FLAME_SHARD_ID,1)
            st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(306,"306_CrystalOfFireice","Crystal Of Fireice")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7004)

STARTED.addTalkId(7004)

STARTED.addKillId(109)
STARTED.addKillId(110)
STARTED.addKillId(112)
STARTED.addKillId(113)
STARTED.addKillId(114)
STARTED.addKillId(115)

STARTED.addQuestDrop(109,FLAME_SHARD_ID,1)
STARTED.addQuestDrop(112,FLAME_SHARD_ID,1)
STARTED.addQuestDrop(114,FLAME_SHARD_ID,1)
STARTED.addQuestDrop(110,ICE_SHARD_ID,1)
STARTED.addQuestDrop(113,ICE_SHARD_ID,1)
STARTED.addQuestDrop(115,ICE_SHARD_ID,1)
