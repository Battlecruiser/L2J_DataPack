# Maked by Mr. Have fun! Version 0.2
print "importing quests: 261: Dream Of Moneylender1"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GIANT_SPIDER_LEG_ID = 1087
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
      htmltext = "7222-03.htm"
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
   if npcId == 7222 and int(st.get("cond"))==0 :
      if int(st.get("cond"))<15 :
        if st.getPlayer().getLevel() >= 15 :
          htmltext = "7222-02.htm"
          return htmltext
        else:
          htmltext = "7222-01.htm"
          st.exitQuest(1)
      else:
        htmltext = "7222-01.htm"
        st.exitQuest(1)
   elif npcId == 7222 and int(st.get("cond"))==1 :
      if st.getQuestItemsCount(GIANT_SPIDER_LEG_ID) >= 8 :
        if int(st.get("id")) != 261 :
          st.set("id","261")
          st.giveItems(ADENA_ID,1000)
          st.addExpAndSp(600,0)
          st.takeItems(GIANT_SPIDER_LEG_ID,st.getQuestItemsCount(GIANT_SPIDER_LEG_ID))
          htmltext = "7222-05.htm"
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
      else:
        htmltext = "7222-04.htm"
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 460 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(GIANT_SPIDER_LEG_ID)<8 :
          st.giveItems(GIANT_SPIDER_LEG_ID,1)
          if st.getQuestItemsCount(GIANT_SPIDER_LEG_ID) == 8 :
            st.playSound("ItemSound.quest_middle")
          else:
            st.playSound("ItemSound.quest_itemget")
   elif npcId == 308 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(GIANT_SPIDER_LEG_ID)<8 :
          st.giveItems(GIANT_SPIDER_LEG_ID,1)
          if st.getQuestItemsCount(GIANT_SPIDER_LEG_ID) == 8 :
            st.playSound("ItemSound.quest_middle")
          else:
            st.playSound("ItemSound.quest_itemget")
   elif npcId == 466 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(GIANT_SPIDER_LEG_ID)<8 :
          st.giveItems(GIANT_SPIDER_LEG_ID,1)
          if st.getQuestItemsCount(GIANT_SPIDER_LEG_ID) == 8 :
            st.playSound("ItemSound.quest_middle")
          else:
            st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(261,"261_DreamOfMoneylender1","Dream Of Moneylender1")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7222)

STARTING.addTalkId(7222)

STARTED.addTalkId(7222)

STARTED.addKillId(308)
STARTED.addKillId(460)
STARTED.addKillId(466)

STARTED.addQuestDrop(460,GIANT_SPIDER_LEG_ID,1)
STARTED.addQuestDrop(308,GIANT_SPIDER_LEG_ID,1)
STARTED.addQuestDrop(466,GIANT_SPIDER_LEG_ID,1)
