# Maked by Mr. Have fun! Version 0.2
print "importing quests: 263: Kill All Sylphs1"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

ORC_AMULET2_ID = 1116
ORC_NECKLACE2_ID = 1117
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
      htmltext = "7346-03.htm"
    elif event == "263_2" :
          htmltext = "7346-06.htm"
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
    elif event == "263_1" :
          htmltext = "7346-07.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7346 and int(st.get("cond"))==0 :
      if int(st.get("cond"))<15 :
        if st.getPlayer().getLevel() >= 8 and st.getPlayer().getRace().ordinal() == 2 :
          htmltext = "7346-02.htm"
          return htmltext
        elif st.getPlayer().getRace().ordinal() != 2 :
          htmltext = "7346-00.htm"
        elif st.getPlayer().getLevel()<8 :
          htmltext = "7346-01.htm"
      else:
        htmltext = "7346-01.htm"
   elif npcId == 7346 and int(st.get("cond"))==1 and st.getQuestItemsCount(ORC_AMULET2_ID)==0 and st.getQuestItemsCount(ORC_NECKLACE2_ID)==0 :
      htmltext = "7346-04.htm"
   elif npcId == 7346 and int(st.get("cond"))==1 and (st.getQuestItemsCount(ORC_AMULET2_ID)>0 or st.getQuestItemsCount(ORC_NECKLACE2_ID)>0) :
      if int(st.get("id")) != 263 :
        st.set("id","263")
        htmltext = "7346-05.htm"
        st.giveItems(ADENA_ID,st.getQuestItemsCount(ORC_AMULET2_ID)*8+st.getQuestItemsCount(ORC_NECKLACE2_ID)*15)
        st.takeItems(ORC_AMULET2_ID,st.getQuestItemsCount(ORC_AMULET2_ID))
        st.takeItems(ORC_NECKLACE2_ID,st.getQuestItemsCount(ORC_NECKLACE2_ID))
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 385 :
      st.set("id","0")
      if int(st.get("cond")) :
        if st.getRandom(10)>4 :
          st.giveItems(ORC_AMULET2_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 387 :
      st.set("id","0")
      if int(st.get("cond")) :
        if st.getRandom(10)>4 :
          st.giveItems(ORC_NECKLACE2_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 388 :
      st.set("id","0")
      if int(st.get("cond")) :
        if st.getRandom(10)>4 :
          st.giveItems(ORC_NECKLACE2_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 386 :
      st.set("id","0")
      if int(st.get("cond")) :
        if st.getRandom(10)>4 :
          st.giveItems(ORC_NECKLACE2_ID,1)
          st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(263,"263_KillAllSylphs1","Kill All Sylphs1")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7346)

STARTED.addTalkId(7346)

STARTED.addKillId(385)
STARTED.addKillId(386)
STARTED.addKillId(387)
STARTED.addKillId(388)

STARTED.addQuestDrop(385,ORC_AMULET2_ID,1)
STARTED.addQuestDrop(387,ORC_NECKLACE2_ID,1)
STARTED.addQuestDrop(388,ORC_NECKLACE2_ID,1)
STARTED.addQuestDrop(386,ORC_NECKLACE2_ID,1)
