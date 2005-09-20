# Maked by Mr. Have fun! Version 0.2
print "importing quests: 257: Guard Is Busy1"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GLUDIO_LORDS_MARK_ID = 1084
ORC_AMULET_ID = 752
ORC_NECKLACE_ID = 1085
WEREWOLF_FANG_ID = 1086
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
        st.giveItems(GLUDIO_LORDS_MARK_ID,1)
        htmltext = "7039-03.htm"
    elif event == "257_2" :
            htmltext = "7039-05.htm"
            st.takeItems(GLUDIO_LORDS_MARK_ID,1)
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
    elif event == "257_3" :
            htmltext = "7039-06.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7039 and int(st.get("cond"))==0 :
        if int(st.get("cond"))<15 :
          if st.getPlayer().getLevel() >= 6 :
            htmltext = "7039-02.htm"
            return htmltext
          else:
            htmltext = "7039-01.htm"
        else:
          htmltext = "7039-01.htm"
   elif npcId == 7039 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
        htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7039 and int(st.get("cond"))==1 and (st.getQuestItemsCount(ORC_AMULET_ID)<1) and (st.getQuestItemsCount(ORC_NECKLACE_ID)<1) and (st.getQuestItemsCount(WEREWOLF_FANG_ID)<1) :
        htmltext = "7039-04.htm"
   elif npcId == 7039 and int(st.get("cond"))==1 and ((st.getQuestItemsCount(ORC_AMULET_ID)>0) or (st.getQuestItemsCount(ORC_NECKLACE_ID)>0) or (st.getQuestItemsCount(WEREWOLF_FANG_ID)>0)) :
        if int(st.get("id")) != 257 :
          st.set("id","257")
          st.giveItems(ADENA_ID,5*st.getQuestItemsCount(ORC_AMULET_ID)+15*st.getQuestItemsCount(ORC_NECKLACE_ID)+10*st.getQuestItemsCount(WEREWOLF_FANG_ID))
          st.takeItems(ORC_AMULET_ID,st.getQuestItemsCount(ORC_AMULET_ID))
          st.takeItems(ORC_NECKLACE_ID,st.getQuestItemsCount(ORC_NECKLACE_ID))
          st.takeItems(WEREWOLF_FANG_ID,st.getQuestItemsCount(WEREWOLF_FANG_ID))
          htmltext = "7039-07.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 130 :
      st.set("id","0")
      if st.getQuestItemsCount(GLUDIO_LORDS_MARK_ID) and int(st.get("cond")) :
        if st.getRandom(10)<5 :
          st.giveItems(ORC_AMULET_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 131 :
      st.set("id","0")
      if st.getQuestItemsCount(GLUDIO_LORDS_MARK_ID) and int(st.get("cond")) :
        if st.getRandom(10)<5 :
          st.giveItems(ORC_AMULET_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 6 :
      st.set("id","0")
      if st.getQuestItemsCount(GLUDIO_LORDS_MARK_ID) and int(st.get("cond")) :
        if st.getRandom(10)<5 :
          st.giveItems(ORC_AMULET_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 93 :
      st.set("id","0")
      if st.getQuestItemsCount(GLUDIO_LORDS_MARK_ID) and int(st.get("cond")) :
        if st.getRandom(10)<5 :
          st.giveItems(ORC_NECKLACE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 96 :
      st.set("id","0")
      if st.getQuestItemsCount(GLUDIO_LORDS_MARK_ID) and int(st.get("cond")) :
        if st.getRandom(10)<5 :
          st.giveItems(ORC_NECKLACE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 98 :
      st.set("id","0")
      if st.getQuestItemsCount(GLUDIO_LORDS_MARK_ID) and int(st.get("cond")) :
        if st.getRandom(10)<5 :
          st.giveItems(ORC_NECKLACE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 132 :
      st.set("id","0")
      if st.getQuestItemsCount(GLUDIO_LORDS_MARK_ID) and int(st.get("cond")) :
        if st.getRandom(10)<5 :
          st.giveItems(WEREWOLF_FANG_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 343 :
      st.set("id","0")
      if st.getQuestItemsCount(GLUDIO_LORDS_MARK_ID) and int(st.get("cond")) :
        if st.getRandom(10)<4 :
          st.giveItems(WEREWOLF_FANG_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 342 :
      st.set("id","0")
      if st.getQuestItemsCount(GLUDIO_LORDS_MARK_ID) and int(st.get("cond")) :
        if st.getRandom(10)<2 :
          st.giveItems(WEREWOLF_FANG_ID,1)
          st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(257,"257_GuardIsBusy1","Guard Is Busy1")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7039)

STARTING.addTalkId(7039)

STARTED.addTalkId(7039)

STARTED.addKillId(130)
STARTED.addKillId(131)
STARTED.addKillId(132)
STARTED.addKillId(342)
STARTED.addKillId(343)
STARTED.addKillId(6)
STARTED.addKillId(93)
STARTED.addKillId(96)
STARTED.addKillId(98)

STARTED.addQuestDrop(130,ORC_AMULET_ID,1)
STARTED.addQuestDrop(131,ORC_AMULET_ID,1)
STARTED.addQuestDrop(6,ORC_AMULET_ID,1)
STARTED.addQuestDrop(93,ORC_NECKLACE_ID,1)
STARTED.addQuestDrop(96,ORC_NECKLACE_ID,1)
STARTED.addQuestDrop(98,ORC_NECKLACE_ID,1)
STARTED.addQuestDrop(132,WEREWOLF_FANG_ID,1)
STARTED.addQuestDrop(343,WEREWOLF_FANG_ID,1)
STARTED.addQuestDrop(342,WEREWOLF_FANG_ID,1)
STARTED.addQuestDrop(7039,GLUDIO_LORDS_MARK_ID,1)
