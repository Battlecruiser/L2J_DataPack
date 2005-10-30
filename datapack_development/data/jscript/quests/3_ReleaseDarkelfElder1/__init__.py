# Maked by Mr. Have fun! Version 0.2
print "importing quests: 3: Release Darkelf Elder1"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

ONYX_BEAST_EYE_ID = 1081
TAINT_STONE_ID = 1082
SUCCUBUS_BLOOD_ID = 1083
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("cond","1")
      st.set("id","0")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      htmltext = "7141-03.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7141 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if int(st.get("cond"))<15 :
        if st.getPlayer().getRace().ordinal() != 2 :
          htmltext = "7141-00.htm"
          st.exitQuest(1)
        elif st.getPlayer().getLevel() >= 16 :
          htmltext = "7141-02.htm"
          return htmltext
        else:
          htmltext = "7141-01.htm"
          st.exitQuest(1)
      else:
        htmltext = "7141-01.htm"
        st.exitQuest(1)
   elif npcId == 7141 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7141 and int(st.get("cond"))==1 and int(st.get("onlyone"))==0 :
      if st.getQuestItemsCount(ONYX_BEAST_EYE_ID) >= 1 and st.getQuestItemsCount(TAINT_STONE_ID) >= 1 and st.getQuestItemsCount(SUCCUBUS_BLOOD_ID) >= 1 :
        if int(st.get("id")) != 3 :
          st.set("id","3")
          st.giveItems(ADENA_ID,4900)
          st.addExpAndSp(5000,0)
          st.takeItems(ONYX_BEAST_EYE_ID,st.getQuestItemsCount(ONYX_BEAST_EYE_ID))
          st.takeItems(TAINT_STONE_ID,st.getQuestItemsCount(TAINT_STONE_ID))
          st.takeItems(SUCCUBUS_BLOOD_ID,st.getQuestItemsCount(SUCCUBUS_BLOOD_ID))
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.set("onlyone","1")
          htmltext = "7141-06.htm"
      else:
        htmltext = "7141-04.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 31 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(ONYX_BEAST_EYE_ID) == 0 :
          st.giveItems(ONYX_BEAST_EYE_ID,1)
          st.playSound("ItemSound.quest_middle")
   elif npcId == 41 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(TAINT_STONE_ID) == 0 :
          st.giveItems(TAINT_STONE_ID,1)
          st.playSound("ItemSound.quest_middle")
   elif npcId == 46 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(TAINT_STONE_ID) == 0 :
          st.giveItems(TAINT_STONE_ID,1)
          st.playSound("ItemSound.quest_middle")
   elif npcId == 48 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(SUCCUBUS_BLOOD_ID) == 0 :
          st.giveItems(SUCCUBUS_BLOOD_ID,1)
          st.playSound("ItemSound.quest_middle")
   elif npcId == 52 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(SUCCUBUS_BLOOD_ID) == 0 :
          st.giveItems(SUCCUBUS_BLOOD_ID,1)
          st.playSound("ItemSound.quest_middle")
   elif npcId == 57 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(SUCCUBUS_BLOOD_ID) == 0 :
          st.giveItems(SUCCUBUS_BLOOD_ID,1)
          st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(3,"3_ReleaseDarkelfElder1","Release Darkelf Elder1")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7141)

STARTING.addTalkId(7141)

STARTED.addTalkId(7141)

STARTED.addKillId(31)
STARTED.addKillId(41)
STARTED.addKillId(46)
STARTED.addKillId(48)
STARTED.addKillId(52)
STARTED.addKillId(57)

STARTED.addQuestDrop(31,ONYX_BEAST_EYE_ID,1)
STARTED.addQuestDrop(41,TAINT_STONE_ID,1)
STARTED.addQuestDrop(46,TAINT_STONE_ID,1)
STARTED.addQuestDrop(48,SUCCUBUS_BLOOD_ID,1)
STARTED.addQuestDrop(52,SUCCUBUS_BLOOD_ID,1)
STARTED.addQuestDrop(57,SUCCUBUS_BLOOD_ID,1)
