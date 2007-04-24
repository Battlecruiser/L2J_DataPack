# Maked by Mr. Have fun! Version 0.2
print "importing quests: 163: Legacy Of Poet"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "163_LegacyOfPoet"

RUMIELS_POEM_1_ID = 1038
RUMIELS_POEM_3_ID = 1039
RUMIELS_POEM_4_ID = 1040
RUMIELS_POEM_5_ID = 1041
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      htmltext = "30220-07.htm"
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
    return htmltext


 def onTalk (self,npc,player):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   id = st.getState()
   npcId = npc.getNpcId()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 30220 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if int(st.get("cond"))<15 :
        if st.getPlayer().getRace().ordinal() != 1 and st.getPlayer().getRace().ordinal() != 3 and st.getPlayer().getRace().ordinal() != 4 and st.getPlayer().getRace().ordinal() != 0 :
          htmltext = "30220-00.htm"
        elif st.getPlayer().getLevel() >= 11 :
          htmltext = "30220-03.htm"
          return htmltext
        else:
          htmltext = "30220-02.htm"
          st.exitQuest(1)
      else:
        htmltext = "30220-02.htm"
        st.exitQuest(1)
   elif npcId == 30220 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 30220 and int(st.get("cond")) :
      if st.getQuestItemsCount(RUMIELS_POEM_1_ID) == 1 and st.getQuestItemsCount(RUMIELS_POEM_3_ID) == 1 and st.getQuestItemsCount(RUMIELS_POEM_4_ID) == 1 and st.getQuestItemsCount(RUMIELS_POEM_5_ID) == 1 and int(st.get("onlyone")) == 0 :
        if int(st.get("id")) != 163 :
          st.set("id","163")
          htmltext = "30220-09.htm"
          st.giveItems(ADENA_ID,13890)
          st.takeItems(RUMIELS_POEM_1_ID,1)
          st.takeItems(RUMIELS_POEM_3_ID,1)
          st.takeItems(RUMIELS_POEM_4_ID,1)
          st.takeItems(RUMIELS_POEM_5_ID,1)
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.set("onlyone","1")
      else:
        htmltext = "30220-08.htm"
   return htmltext

 def onKill (self,npc,player):
   st = player.getQuestState(qn)
   if not st : return 
   if st.getState() != STARTED : return 

   npcId = npc.getNpcId()
   if npcId == 20372 :
        st.set("id","0")
        if int(st.get("cond")) == 1 :
          if st.getRandom(10) == 0 and st.getQuestItemsCount(RUMIELS_POEM_1_ID) == 0 :
            st.giveItems(RUMIELS_POEM_1_ID,1)
            if st.getQuestItemsCount(RUMIELS_POEM_1_ID)+st.getQuestItemsCount(RUMIELS_POEM_3_ID)+st.getQuestItemsCount(RUMIELS_POEM_4_ID)+st.getQuestItemsCount(RUMIELS_POEM_5_ID) == 4 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
          if st.getRandom(10)>7 and st.getQuestItemsCount(RUMIELS_POEM_3_ID) == 0 :
            st.giveItems(RUMIELS_POEM_3_ID,1)
            if st.getQuestItemsCount(RUMIELS_POEM_1_ID)+st.getQuestItemsCount(RUMIELS_POEM_3_ID)+st.getQuestItemsCount(RUMIELS_POEM_4_ID)+st.getQuestItemsCount(RUMIELS_POEM_5_ID) == 4 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
          if st.getRandom(10)>7 and st.getQuestItemsCount(RUMIELS_POEM_4_ID) == 0 :
            st.giveItems(RUMIELS_POEM_4_ID,1)
            if st.getQuestItemsCount(RUMIELS_POEM_1_ID)+st.getQuestItemsCount(RUMIELS_POEM_3_ID)+st.getQuestItemsCount(RUMIELS_POEM_4_ID)+st.getQuestItemsCount(RUMIELS_POEM_5_ID) == 4 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
          if st.getRandom(10)>5 and st.getQuestItemsCount(RUMIELS_POEM_5_ID) == 0 :
            st.giveItems(RUMIELS_POEM_5_ID,1)
            if st.getQuestItemsCount(RUMIELS_POEM_1_ID)+st.getQuestItemsCount(RUMIELS_POEM_3_ID)+st.getQuestItemsCount(RUMIELS_POEM_4_ID)+st.getQuestItemsCount(RUMIELS_POEM_5_ID) == 4 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 20373 :
        st.set("id","0")
        if int(st.get("cond")) == 1 :
          if st.getRandom(10) == 0 and st.getQuestItemsCount(RUMIELS_POEM_1_ID) == 0 :
            st.giveItems(RUMIELS_POEM_1_ID,1)
            if st.getQuestItemsCount(RUMIELS_POEM_1_ID)+st.getQuestItemsCount(RUMIELS_POEM_3_ID)+st.getQuestItemsCount(RUMIELS_POEM_4_ID)+st.getQuestItemsCount(RUMIELS_POEM_5_ID) == 4 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
          if st.getRandom(10)>7 and st.getQuestItemsCount(RUMIELS_POEM_3_ID) == 0 :
            st.giveItems(RUMIELS_POEM_3_ID,1)
            if st.getQuestItemsCount(RUMIELS_POEM_1_ID)+st.getQuestItemsCount(RUMIELS_POEM_3_ID)+st.getQuestItemsCount(RUMIELS_POEM_4_ID)+st.getQuestItemsCount(RUMIELS_POEM_5_ID) == 4 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
          if st.getRandom(10)>7 and st.getQuestItemsCount(RUMIELS_POEM_4_ID) == 0 :
            st.giveItems(RUMIELS_POEM_4_ID,1)
            if st.getQuestItemsCount(RUMIELS_POEM_1_ID)+st.getQuestItemsCount(RUMIELS_POEM_3_ID)+st.getQuestItemsCount(RUMIELS_POEM_4_ID)+st.getQuestItemsCount(RUMIELS_POEM_5_ID) == 4 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
          if st.getRandom(10)>5 and st.getQuestItemsCount(RUMIELS_POEM_5_ID) == 0 :
            st.giveItems(RUMIELS_POEM_5_ID,1)
            if st.getQuestItemsCount(RUMIELS_POEM_1_ID)+st.getQuestItemsCount(RUMIELS_POEM_3_ID)+st.getQuestItemsCount(RUMIELS_POEM_4_ID)+st.getQuestItemsCount(RUMIELS_POEM_5_ID) == 4 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(163,qn,"Legacy Of Poet")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30220)

QUEST.addTalkId(30220)

QUEST.addKillId(20372)
QUEST.addKillId(20373)

STARTED.addQuestDrop(20372,RUMIELS_POEM_1_ID,1)
STARTED.addQuestDrop(20373,RUMIELS_POEM_1_ID,1)
STARTED.addQuestDrop(20372,RUMIELS_POEM_3_ID,1)
STARTED.addQuestDrop(20373,RUMIELS_POEM_3_ID,1)
STARTED.addQuestDrop(20372,RUMIELS_POEM_4_ID,1)
STARTED.addQuestDrop(20373,RUMIELS_POEM_4_ID,1)
STARTED.addQuestDrop(20372,RUMIELS_POEM_5_ID,1)
STARTED.addQuestDrop(20373,RUMIELS_POEM_5_ID,1)
