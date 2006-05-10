# Maked by Mr. Have fun! Version 0.2
print "importing quests: 165: Wild Hunt"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

DARK_BEZOAR_ID = 1160
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
      htmltext = "7348-03.htm"
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
   if npcId == 7348 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if int(st.get("cond"))<15 :
        if st.getPlayer().getRace().ordinal() != 2 :
          htmltext = "7348-00.htm"
        elif st.getPlayer().getLevel() >= 3 :
          htmltext = "7348-02.htm"
          return htmltext
        else:
          htmltext = "7348-01.htm"
          st.exitQuest(1)
      else:
        htmltext = "7348-01.htm"
        st.exitQuest(1)
   elif npcId == 7348 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7348 and int(st.get("cond"))==1 :
      if st.getQuestItemsCount(DARK_BEZOAR_ID)<13 :
        htmltext = "7348-04.htm"
      elif st.getQuestItemsCount(DARK_BEZOAR_ID) >= 13 and int(st.get("onlyone")) == 0 :
          if int(st.get("id")) != 165 :
            st.set("id","165")
            htmltext = "7348-05.htm"
            st.takeItems(DARK_BEZOAR_ID,st.getQuestItemsCount(DARK_BEZOAR_ID))
            st.giveItems(LESSER_HEALING_POTION_ID,5)
            st.addExpAndSp(1000,0)
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
            st.set("onlyone","1")
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 529 :
        st.set("id","0")
        if int(st.get("cond")) == 1 :
          if st.getRandom(10)<4 and st.getQuestItemsCount(DARK_BEZOAR_ID)<13 :
            st.giveItems(DARK_BEZOAR_ID,1)
            if st.getQuestItemsCount(DARK_BEZOAR_ID) == 13 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 532 :
        st.set("id","0")
        if int(st.get("cond")) == 1 :
          if st.getRandom(10)<4 and st.getQuestItemsCount(DARK_BEZOAR_ID)<13 :
            st.giveItems(DARK_BEZOAR_ID,1)
            if st.getQuestItemsCount(DARK_BEZOAR_ID) == 13 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 536 :
        st.set("id","0")
        if int(st.get("cond")) == 1 :
          if st.getRandom(10)<4 and st.getQuestItemsCount(DARK_BEZOAR_ID)<13 :
            st.giveItems(DARK_BEZOAR_ID,1)
            if st.getQuestItemsCount(DARK_BEZOAR_ID) == 13 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 456 :
        st.set("id","0")
        if int(st.get("cond")) == 1 :
          if st.getRandom(10)<4 and st.getQuestItemsCount(DARK_BEZOAR_ID)<13 :
            st.giveItems(DARK_BEZOAR_ID,1)
            if st.getQuestItemsCount(DARK_BEZOAR_ID) == 13 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(165,"165_WildHunt","Wild Hunt")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7348)

STARTING.addTalkId(7348)

STARTED.addTalkId(7348)

STARTED.addKillId(456)
STARTED.addKillId(529)
STARTED.addKillId(532)
STARTED.addKillId(536)

STARTED.addQuestDrop(529,DARK_BEZOAR_ID,1)
STARTED.addQuestDrop(532,DARK_BEZOAR_ID,1)
STARTED.addQuestDrop(536,DARK_BEZOAR_ID,1)
STARTED.addQuestDrop(456,DARK_BEZOAR_ID,1)
