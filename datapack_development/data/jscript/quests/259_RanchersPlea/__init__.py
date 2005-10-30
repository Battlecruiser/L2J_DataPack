# Maked by Mr. Have fun! Version 0.2
print "importing quests: 259: Ranchers Plea"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GIANT_SPIDER_SKIN_ID = 1495
ADENA_ID = 57
HEALING_POTION_ID = 1061
WOODEN_ARROW_ID = 17

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      htmltext = "7497-03.htm"
    elif event == "7497_1" :
          htmltext = "7497-06.htm"
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
    elif event == "7497_2" :
          htmltext = "7497-07.htm"
    elif event == "7405_1" :
          htmltext = "7405-03.htm"
    elif event == "7405_2" :
          htmltext = "7405-04.htm"
          st.giveItems(HEALING_POTION_ID,1)
          st.takeItems(GIANT_SPIDER_SKIN_ID,10)
    elif event == "7405_3" :
          htmltext = "7405-05.htm"
          st.giveItems(WOODEN_ARROW_ID,50)
          st.takeItems(GIANT_SPIDER_SKIN_ID,10)
    elif event == "7405_4" :
          if st.getQuestItemsCount(GIANT_SPIDER_SKIN_ID) >= 10 :
            htmltext = "7405-06.htm"
          elif st.getQuestItemsCount(GIANT_SPIDER_SKIN_ID) < 10 :
            htmltext = "7405-07.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7497 and int(st.get("cond"))==0 :
      if int(st.get("cond")) < 15 :
        if st.getPlayer().getLevel() >= 15 :
          htmltext = "7497-02.htm"
          return htmltext
        else:
          htmltext = "7497-01.htm"
          st.exitQuest(1)
      else:
        htmltext = "7497-01.htm"
        st.exitQuest(1)
   elif npcId == 7497 and int(st.get("cond"))==1 and st.getQuestItemsCount(GIANT_SPIDER_SKIN_ID)<1 :
      htmltext = "7497-04.htm"
   elif npcId == 7497 and int(st.get("cond"))==1 and st.getQuestItemsCount(GIANT_SPIDER_SKIN_ID)>=1 :
      if int(st.get("id")) != 259 :
        st.set("id","259")
        htmltext = "7497-05.htm"
      st.giveItems(ADENA_ID,st.getQuestItemsCount(GIANT_SPIDER_SKIN_ID)*25)
      st.takeItems(GIANT_SPIDER_SKIN_ID,st.getQuestItemsCount(GIANT_SPIDER_SKIN_ID))
   elif npcId == 7405 and int(st.get("cond"))==1 and st.getQuestItemsCount(GIANT_SPIDER_SKIN_ID)<10 :
      htmltext = "7405-01.htm"
   elif npcId == 7405 and int(st.get("cond"))==1 and st.getQuestItemsCount(GIANT_SPIDER_SKIN_ID)>=10 :
      htmltext = "7405-02.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 103 :
      st.set("id","0")
      if int(st.get("cond")) :
        st.giveItems(GIANT_SPIDER_SKIN_ID,1)
        st.playSound("ItemSound.quest_itemget")
   elif npcId == 106 :
      st.set("id","0")
      if int(st.get("cond")) :
        st.giveItems(GIANT_SPIDER_SKIN_ID,1)
        st.playSound("ItemSound.quest_itemget")
   elif npcId == 108 :
      st.set("id","0")
      if int(st.get("cond")) :
        st.giveItems(GIANT_SPIDER_SKIN_ID,1)
        st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(259,"259_RanchersPlea","Ranchers Plea")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7497)

STARTING.addTalkId(7497)

STARTED.addTalkId(7405)
STARTED.addTalkId(7497)

STARTED.addKillId(103)
STARTED.addKillId(106)
STARTED.addKillId(108)

STARTED.addQuestDrop(103,GIANT_SPIDER_SKIN_ID,1)
STARTED.addQuestDrop(106,GIANT_SPIDER_SKIN_ID,1)
STARTED.addQuestDrop(108,GIANT_SPIDER_SKIN_ID,1)
