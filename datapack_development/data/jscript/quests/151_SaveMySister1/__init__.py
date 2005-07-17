# Maked by Mr. Have fun! Version 0.2
print "importing quests: 151: Save My Sister1"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

POISON_SAC_ID = 703
FEVER_MEDICINE_ID = 704
SWIFT_ATTACK_POTION_ID = 735

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        htmltext = "7050-03.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7050 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if int(st.get("cond"))<15 :
          if st.getPlayer().getLevel() >= 15 :
            htmltext = "7050-02.htm"
            st.set("cond","1")
            return htmltext
          else:
            htmltext = "7050-01.htm"
        else:
          htmltext = "7050-01.htm"
   elif npcId == 7050 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7050 and int(st.get("cond"))==1 and st.getQuestItemsCount(FEVER_MEDICINE_ID)==1 and int(st.get("onlyone"))==0 :
      if int(st.get("id")) != 151 :
        st.set("id","151")
        st.giveItems(SWIFT_ATTACK_POTION_ID,2)
        st.takeItems(FEVER_MEDICINE_ID,1)
        htmltext = "7050-06.htm"
        st.set("cond","0")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
        st.set("onlyone","1")
   elif npcId == 7050 and int(st.get("cond"))==1 and st.getQuestItemsCount(POISON_SAC_ID)==1 :
      htmltext = "7050-05.htm"
   elif npcId == 7050 and int(st.get("cond"))==1 and st.getQuestItemsCount(POISON_SAC_ID)==0 and st.getQuestItemsCount(FEVER_MEDICINE_ID)==0 :
        htmltext = "7050-04.htm"
   elif npcId == 7032 and int(st.get("cond"))==1 and st.getQuestItemsCount(POISON_SAC_ID)>0 :
      if st.getQuestItemsCount(POISON_SAC_ID) == 1 :
        st.giveItems(FEVER_MEDICINE_ID,1)
        st.takeItems(POISON_SAC_ID,1)
        htmltext = "7032-01.htm"
      elif st.getQuestItemsCount(FEVER_MEDICINE_ID) == 1 :
        htmltext = "7032-02.htm"
   elif npcId == 7032 and int(st.get("cond"))==1 and st.getQuestItemsCount(FEVER_MEDICINE_ID)>0 :
      htmltext = "7032-02.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 103 :
      st.set("id","0")
      if st.getQuestItemsCount(POISON_SAC_ID) == 0 and int(st.get("cond")) :
        if st.getRandom(5) == 0 :
          st.giveItems(POISON_SAC_ID,1)
          st.playSound("ItemSound.quest_middle")
   elif npcId == 106 :
      st.set("id","0")
      if st.getQuestItemsCount(POISON_SAC_ID) == 0 and int(st.get("cond")) :
        if st.getRandom(5) == 0 :
          st.giveItems(POISON_SAC_ID,1)
          st.playSound("ItemSound.quest_middle")
   elif npcId == 108 :
      st.set("id","0")
      if st.getQuestItemsCount(POISON_SAC_ID) == 0 and int(st.get("cond")) :
        if st.getRandom(5) == 0 :
          st.giveItems(POISON_SAC_ID,1)
          st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(151,"151_SaveMySister1","Save My Sister1")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7050)

STARTED.addTalkId(7032)
STARTED.addTalkId(7050)

STARTED.addKillId(103)
STARTED.addKillId(106)
STARTED.addKillId(108)

STARTED.addQuestDrop(7032,FEVER_MEDICINE_ID,1)
STARTED.addQuestDrop(103,POISON_SAC_ID,1)
STARTED.addQuestDrop(106,POISON_SAC_ID,1)
STARTED.addQuestDrop(108,POISON_SAC_ID,1)
