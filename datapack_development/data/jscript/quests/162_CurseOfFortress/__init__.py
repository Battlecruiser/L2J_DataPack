# Maked by Mr. Have fun! Version 0.2
print "importing quests: 162: Curse Of Fortress"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

BONE_FRAGMENT3_ID = 1158
ELF_SKULL_ID = 1159
BONE_SHIELD_ID = 625

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      htmltext = "7147-04.htm"
    elif event == "162_1" :
          htmltext = "7147-03.htm"
          st.set("cond","1")
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
   if npcId == 7147 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if int(st.get("cond"))<15 :
        if st.getPlayer().getRace().ordinal() == 2 :
          htmltext = "7147-00.htm"
        elif st.getPlayer().getLevel() >= 12 :
          htmltext = "7147-02.htm"
        else:
          htmltext = "7147-01.htm"
      else:
        htmltext = "7147-01.htm"
   elif npcId == 7147 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7147 and int(st.get("cond"))==1 and (st.getQuestItemsCount(ELF_SKULL_ID)+st.getQuestItemsCount(BONE_FRAGMENT3_ID))<13 :
        htmltext = "7147-05.htm"
   elif npcId == 7147 and int(st.get("cond"))==1 and (st.getQuestItemsCount(ELF_SKULL_ID)+st.getQuestItemsCount(BONE_FRAGMENT3_ID))>=13 and int(st.get("onlyone"))==0 :
        if int(st.get("id")) != 162 :
          st.set("id","162")
          htmltext = "7147-06.htm"
          st.giveItems(BONE_SHIELD_ID,1)
          st.addExpAndSp(2000,0)
          st.takeItems(ELF_SKULL_ID,st.getQuestItemsCount(ELF_SKULL_ID))
          st.takeItems(BONE_FRAGMENT3_ID,st.getQuestItemsCount(BONE_FRAGMENT3_ID))
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.set("onlyone","1")
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 464 :
        st.set("id","0")
        if int(st.get("cond")) == 1 :
          if st.getRandom(4) == 1 and st.getQuestItemsCount(BONE_FRAGMENT3_ID)<10 :
            st.giveItems(BONE_FRAGMENT3_ID,1)
            if st.getQuestItemsCount(BONE_FRAGMENT3_ID) == 10 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 463 :
        st.set("id","0")
        if int(st.get("cond")) == 1 :
          if st.getRandom(4) == 1 and st.getQuestItemsCount(BONE_FRAGMENT3_ID)<10 :
            st.giveItems(BONE_FRAGMENT3_ID,1)
            if st.getQuestItemsCount(BONE_FRAGMENT3_ID) == 10 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 504 :
        st.set("id","0")
        if int(st.get("cond")) == 1 :
          if st.getRandom(4) == 1 and st.getQuestItemsCount(BONE_FRAGMENT3_ID)<10 :
            st.giveItems(BONE_FRAGMENT3_ID,1)
            if st.getQuestItemsCount(BONE_FRAGMENT3_ID) == 10 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 371 :
        st.set("id","0")
        if int(st.get("cond")) == 1 :
          if st.getRandom(4) == 1 and st.getQuestItemsCount(ELF_SKULL_ID)<3 :
            st.giveItems(ELF_SKULL_ID,1)
            if st.getQuestItemsCount(ELF_SKULL_ID) == 3 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 345 :
        st.set("id","0")
        if int(st.get("cond")) == 1 :
          if st.getRandom(4) == 1 and st.getQuestItemsCount(ELF_SKULL_ID)<3 :
            st.giveItems(ELF_SKULL_ID,1)
            if st.getQuestItemsCount(ELF_SKULL_ID) == 3 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 33 :
        st.set("id","0")
        if int(st.get("cond")) == 1 :
          if st.getRandom(4) == 1 and st.getQuestItemsCount(ELF_SKULL_ID)<3 :
            st.giveItems(ELF_SKULL_ID,1)
            if st.getQuestItemsCount(ELF_SKULL_ID) == 3 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(162,"162_CurseOfFortress","Curse Of Fortress")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7147)

STARTED.addTalkId(7147)

STARTED.addKillId(33)
STARTED.addKillId(345)
STARTED.addKillId(371)
STARTED.addKillId(463)
STARTED.addKillId(464)
STARTED.addKillId(504)

STARTED.addQuestDrop(371,ELF_SKULL_ID,1)
STARTED.addQuestDrop(345,ELF_SKULL_ID,1)
STARTED.addQuestDrop(33,ELF_SKULL_ID,1)
STARTED.addQuestDrop(464,BONE_FRAGMENT3_ID,1)
STARTED.addQuestDrop(463,BONE_FRAGMENT3_ID,1)
STARTED.addQuestDrop(504,BONE_FRAGMENT3_ID,1)
