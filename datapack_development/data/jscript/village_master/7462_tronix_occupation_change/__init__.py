#
# Created by DraX on 2005.08.17
#

print "importing village master data: Dion Castle Town       ...done"

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GAZE_OF_ABYSS_ID     = 1244
IRON_HEART_ID        = 1252
JEWEL_OF_DARKNESS_ID = 1261
ORB_OF_ABYSS_ID      = 1270
GRAND_MASTER_TRONIX  = 7462

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = "No Quest"
   
   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "7462-01.htm":
     return "7462-01.htm"

   if event == "7462-02.htm":
     return "7462-02.htm"

   if event == "7462-03.htm":
     return "7462-03.htm"

   if event == "7462-04.htm":
     return "7462-04.htm"

   if event == "7462-05.htm":
     return "7462-05.htm"

   if event == "7462-06.htm":
     return "7462-06.htm"

   if event == "7462-07.htm":
     return "7462-07.htm"

   if event == "7462-08.htm":
     return "7462-08.htm"

   if event == "7462-09.htm":
     return "7462-09.htm"

   if event == "7462-10.htm":
     return "7462-10.htm"

   if event == "7462-11.htm":
     return "7462-11.htm"

   if event == "7462-12.htm":
     return "7462-12.htm"

   if event == "7462-13.htm":
     return "7462-13.htm"

   if event == "7462-14.htm":
     return "7462-14.htm"

   if event == "class_change_32":
     if ClassId in [ClassId.darkFighter]:
        if Level <= 19 and st.getQuestItemsCount(GAZE_OF_ABYSS_ID) == 0:
          htmltext = "7462-15.htm"
        if Level <= 19 and st.getQuestItemsCount(GAZE_OF_ABYSS_ID) >= 1:
          htmltext = "7462-16.htm"
        if Level >= 20 and st.getQuestItemsCount(GAZE_OF_ABYSS_ID) == 0:
          htmltext = "7462-17.htm"
        if Level >= 20 and st.getQuestItemsCount(GAZE_OF_ABYSS_ID) >= 1:
          st.takeItems(GAZE_OF_ABYSS_ID,1)
          st.player.setClassId(32)
          st.player.setBaseClass(32)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "7462-18.htm"

   if event == "class_change_35":
     if ClassId in [ClassId.darkFighter]:
        if Level <= 19 and st.getQuestItemsCount(IRON_HEART_ID) == 0:
          htmltext = "7462-19.htm"
        if Level <= 19 and st.getQuestItemsCount(IRON_HEART_ID) >= 1:
          htmltext = "7462-20.htm"
        if Level >= 20 and st.getQuestItemsCount(IRON_HEART_ID) == 0:
          htmltext = "7462-21.htm"
        if Level >= 20 and st.getQuestItemsCount(IRON_HEART_ID) >= 1:
          st.takeItems(IRON_HEART_ID,1)
          st.player.setClassId(35)
          st.player.setBaseClass(35)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "7462-22.htm"

   if event == "class_change_39":
     if ClassId in [ClassId.darkMage]:
        if Level <= 19 and st.getQuestItemsCount(JEWEL_OF_DARKNESS_ID) == 0:
          htmltext = "7462-23.htm"
        if Level <= 19 and st.getQuestItemsCount(JEWEL_OF_DARKNESS_ID) >= 1:
          htmltext = "7462-24.htm"
        if Level >= 20 and st.getQuestItemsCount(JEWEL_OF_DARKNESS_ID) == 0:
          htmltext = "7462-25.htm"
        if Level >= 20 and st.getQuestItemsCount(JEWEL_OF_DARKNESS_ID) >= 1:
          st.takeItems(JEWEL_OF_DARKNESS_ID,1)
          st.player.setClassId(39)
          st.player.setBaseClass(39)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "7462-26.htm"

   if event == "class_change_42":
     if ClassId in [ClassId.darkMage]:
        if Level <= 19 and st.getQuestItemsCount(ORB_OF_ABYSS_ID) == 0:
          htmltext = "7462-27.htm"
        if Level <= 19 and st.getQuestItemsCount(ORB_OF_ABYSS_ID) >= 1:
          htmltext = "7462-28.htm"
        if Level >= 20 and st.getQuestItemsCount(ORB_OF_ABYSS_ID) == 0:
          htmltext = "7462-29.htm"
        if Level >= 20 and st.getQuestItemsCount(ORB_OF_ABYSS_ID) >= 1:
          st.takeItems(ORB_OF_ABYSS_ID,1)
          st.player.setClassId(42)
          st.player.setBaseClass(42)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "7462-30.htm"
          
   st.setState(COMPLETED)
   st.exitQuest(1)
   return htmltext


 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # DarkElfs got accepted
   if npcId == GRAND_MASTER_TRONIX and Race in [Race.darkelf]:
     if ClassId in [ClassId.darkFighter]:
       st.setState(STARTED)
       return "7462-01.htm"
     if ClassId in [ClassId.darkMage]:
       st.setState(STARTED)
       return "7462-08.htm"
     if ClassId in [ClassId.palusKnight, ClassId.assassin, ClassId.darkWizard, ClassId.shillienOracle]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7462-31.htm"
     if ClassId in [ClassId.shillienKnight, ClassId.abyssWalker, ClassId.bladedancer, ClassId.phantomRanger]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7462-32.htm"
     if ClassId in [ClassId.spellhowler, ClassId.shillenElder, ClassId.phantomSummoner]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7462-32.htm"

   # All other Races must be out
   if npcId == GRAND_MASTER_TRONIX and Race in [Race.dwarf, Race.human, Race.elf, Race.orc]:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "7462-33.htm"

QUEST     = Quest(7462,"7462_tronix_occupation_change","village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7462)

STARTED.addTalkId(7462)
