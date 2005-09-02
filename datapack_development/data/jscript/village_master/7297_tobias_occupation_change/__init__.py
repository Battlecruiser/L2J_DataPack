#
# Created by DraX on 2005.08.08
#

print "importing village master data: Gludio Castle Town     ...done"

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GAZE_OF_ABYSS_ID     = 1244
IRON_HEART_ID        = 1252
JEWEL_OF_DARKNESS_ID = 1261
ORB_OF_ABYSS_ID      = 1270
GRAND_MASTER_TOBIAS  = 7297

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = event
   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "7297-01.htm":
     st.exitQuest(1)
     return "7297-01.htm"

   if event == "7297-02.htm":
     st.exitQuest(1)
     return "7297-02.htm"

   if event == "7297-03.htm":
     st.exitQuest(1)
     return "7297-03.htm"

   if event == "7297-04.htm":
     st.exitQuest(1)
     return "7297-04.htm"

   if event == "7297-05.htm":
     st.exitQuest(1)
     return "7297-05.htm"

   if event == "7297-06.htm":
     st.exitQuest(1)
     return "7297-06.htm"

   if event == "7297-07.htm":
     st.exitQuest(1)
     return "7297-07.htm"

   if event == "7297-08.htm":
     st.exitQuest(1)
     return "7297-08.htm"

   if event == "7297-09.htm":
     st.exitQuest(1)
     return "7297-09.htm"

   if event == "7297-10.htm":
     st.exitQuest(1)
     return "7297-10.htm"

   if event == "7297-11.htm":
     st.exitQuest(1)
     return "7297-11.htm"

   if event == "7297-12.htm":
     st.exitQuest(1)
     return "7297-12.htm"

   if event == "7297-13.htm":
     st.exitQuest(1)
     return "7297-13.htm"

   if event == "7297-14.htm":
     st.exitQuest(1)
     return "7297-14.htm"

   if event == "class_change_32":
     if ClassId in [ClassId.darkFighter]:
        if Level <= 19 and st.getQuestItemsCount(GAZE_OF_ABYSS_ID) == 0:
          st.exitQuest(1)
          return "7297-15.htm"
        if Level <= 19 and st.getQuestItemsCount(GAZE_OF_ABYSS_ID) >= 1:
          st.exitQuest(1)
          return "7297-16.htm"
        if Level >= 20 and st.getQuestItemsCount(GAZE_OF_ABYSS_ID) == 0:
          st.exitQuest(1)
          return "7297-17.htm"
        if Level >= 20 and st.getQuestItemsCount(GAZE_OF_ABYSS_ID) >= 1:
          st.takeItems(GAZE_OF_ABYSS_ID,1)
          st.player.setClassId(32)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          st.exitQuest(1)
          return "7297-18.htm"

   if event == "class_change_35":
     if ClassId in [ClassId.darkFighter]:
        if Level <= 19 and st.getQuestItemsCount(IRON_HEART_ID) == 0:
          st.exitQuest(1)
          return "7297-19.htm"
        if Level <= 19 and st.getQuestItemsCount(IRON_HEART_ID) >= 1:
          st.exitQuest(1)
          return "7297-20.htm"
        if Level >= 20 and st.getQuestItemsCount(IRON_HEART_ID) == 0:
          st.exitQuest(1)
          return "7297-21.htm"
        if Level >= 20 and st.getQuestItemsCount(IRON_HEART_ID) >= 1:
          st.takeItems(IRON_HEART_ID,1)
          st.player.setClassId(35)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          st.exitQuest(1)
          return "7297-22.htm"

   if event == "class_change_39":
     if ClassId in [ClassId.darkMage]:
        if Level <= 19 and st.getQuestItemsCount(JEWEL_OF_DARKNESS_ID) == 0:
          st.exitQuest(1)
          return "7297-23.htm"
        if Level <= 19 and st.getQuestItemsCount(JEWEL_OF_DARKNESS_ID) >= 1:
          st.exitQuest(1)
          return "7297-24.htm"
        if Level >= 20 and st.getQuestItemsCount(JEWEL_OF_DARKNESS_ID) == 0:
          st.exitQuest(1)
          return "7297-25.htm"
        if Level >= 20 and st.getQuestItemsCount(JEWEL_OF_DARKNESS_ID) >= 1:
          st.takeItems(JEWEL_OF_DARKNESS_ID,1)
          st.player.setClassId(39)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          st.exitQuest(1)
          return "7297-26.htm"

   if event == "class_change_42":
     if ClassId in [ClassId.darkMage]:
        if Level <= 19 and st.getQuestItemsCount(ORB_OF_ABYSS_ID) == 0:
          st.exitQuest(1)
          return "7297-27.htm"
        if Level <= 19 and st.getQuestItemsCount(ORB_OF_ABYSS_ID) >= 1:
          st.exitQuest(1)
          return "7297-28.htm"
        if Level >= 20 and st.getQuestItemsCount(ORB_OF_ABYSS_ID) == 0:
          st.exitQuest(1)
          return "7297-29.htm"
        if Level >= 20 and st.getQuestItemsCount(ORB_OF_ABYSS_ID) >= 1:
          st.takeItems(ORB_OF_ABYSS_ID,1)
          st.player.setClassId(42)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          st.exitQuest(1)
          return "7297-30.htm"


 def onTalk (Self,npcId,st):
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # DarkElf´s got accepted
   if npcId == GRAND_MASTER_TOBIAS and Race in [Race.darkelf]:
     if ClassId in [ClassId.darkFighter]:
       st.exitQuest(1)
       return "7297-01.htm"
     if ClassId in [ClassId.darkMage]:
       st.exitQuest(1)
       return "7297-08.htm"
     if ClassId in [ClassId.palusKnight, ClassId.assassin, ClassId.darkWizard, ClassId.shillienOracle]:
       st.exitQuest(1)
       return "7297-31.htm"
     if ClassId in [ClassId.shillienKnight, ClassId.abyssWalker, ClassId.bladedancer, ClassId.phantomRanger]:
       st.exitQuest(1)
       return "7297-32.htm"
     if ClassId in [ClassId.spellhowler, ClassId.shillenElder, ClassId.phantomSummoner]:
       st.exitQuest(1)
       return "7297-32.htm"

   # All other Races must be out
   if npcId == GRAND_MASTER_TOBIAS and Race in [Race.dwarf, Race.human, Race.elf, Race.orc]:
     st.exitQuest(1)
     return "7297-33.htm"

QUEST   = Quest(7297,"7297_tobias_occupation_change","village_master")
CREATED = State('Start',QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7297)

STARTED.addTalkId(7297)