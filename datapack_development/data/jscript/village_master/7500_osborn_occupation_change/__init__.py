#
# Created by DraX on 2005.08.13
#

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest


MARK_OF_RAIDER_ID   = 1592
KHAVATARI_TOTEM_ID  = 1615
MASK_OF_MEDIUM_ID   = 1631
HIGH_PREFECT_OSBORN = 7500

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = event
   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "7500-01.htm":
     st.exitQuest(1)
     return "7500-01.htm"

   if event == "7500-02.htm":
     st.exitQuest(1)
     return "7500-02.htm"

   if event == "7500-03.htm":
     st.exitQuest(1)
     return "7500-03.htm"

   if event == "7500-04.htm":
     st.exitQuest(1)
     return "7500-04.htm"

   if event == "7500-05.htm":
     st.exitQuest(1)
     return "7500-05.htm"

   if event == "7500-06.htm":
     st.exitQuest(1)
     return "7500-06.htm"

   if event == "7500-07.htm":
     st.exitQuest(1)
     return "7500-07.htm"

   if event == "7500-08.htm":
     st.exitQuest(1)
     return "7500-08.htm"

   if event == "class_change_45":
     if ClassId in [ClassId.orcFighter]:
        if Level <= 19 and st.getQuestItemsCount(MARK_OF_RAIDER_ID) == 0:
          st.exitQuest(1)
          return "7500-09.htm"
        if Level <= 19 and st.getQuestItemsCount(MARK_OF_RAIDER_ID) >= 1:
          st.exitQuest(1)
          return "7500-10.htm"
        if Level >= 20 and st.getQuestItemsCount(MARK_OF_RAIDER_ID) == 0:
          st.exitQuest(1)
          return "7500-11.htm"
        if Level >= 20 and st.getQuestItemsCount(MARK_OF_RAIDER_ID) >= 1:
          st.takeItems(MARK_OF_RAIDER_ID,1)
          st.player.setClassId(45)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          st.exitQuest(1)
          return "7500-12.htm"

   if event == "class_change_47":
     if ClassId in [ClassId.orcFighter]:
        if Level <= 19 and st.getQuestItemsCount(KHAVATARI_TOTEM_ID) == 0:
          st.exitQuest(1)
          return "7500-13.htm"
        if Level <= 19 and st.getQuestItemsCount(KHAVATARI_TOTEM_ID) >= 1:
          st.exitQuest(1)
          return "7500-14.htm"
        if Level >= 20 and st.getQuestItemsCount(KHAVATARI_TOTEM_ID) == 0:
          st.exitQuest(1)
          return "7500-15.htm"
        if Level >= 20 and st.getQuestItemsCount(KHAVATARI_TOTEM_ID) >= 1:
          st.takeItems(KHAVATARI_TOTEM_ID,1)
          st.player.setClassId(47)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          st.exitQuest(1)
          return "7500-16.htm"

   if event == "class_change_50":
     if ClassId in [ClassId.orcMage]:
        if Level <= 19 and st.getQuestItemsCount(MASK_OF_MEDIUM_ID) == 0:
          st.exitQuest(1)
          return "7500-17.htm"
        if Level <= 19 and st.getQuestItemsCount(MASK_OF_MEDIUM_ID) >= 1:
          st.exitQuest(1)
          return "7500-18.htm"
        if Level >= 20 and st.getQuestItemsCount(MASK_OF_MEDIUM_ID) == 0:
          st.exitQuest(1)
          return "7500-19.htm"
        if Level >= 20 and st.getQuestItemsCount(MASK_OF_MEDIUM_ID) >= 1:
          st.takeItems(MASK_OF_MEDIUM_ID,1)
          st.player.setClassId(50)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          st.exitQuest(1)
          return "7500-20.htm"

 def onTalk (Self,npcId,st):
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # orc´s got accepted
   if npcId == HIGH_PREFECT_OSBORN and Race in [Race.orc]:
     if ClassId in [ClassId.orcFighter]:
       st.exitQuest(1)
       return "7500-01.htm"
     if ClassId in [ClassId.orcMage]:
       st.exitQuest(1)
       return "7500-06.htm"
     if ClassId in [ClassId.orcRaider, ClassId.orcMonk, ClassId.orcShaman]:
       st.exitQuest(1)
       return "7500-21.htm"
     if ClassId in [ClassId.destroyer, ClassId.tyrant, ClassId.overlord, ClassId.warcryer]:
       st.exitQuest(1)
       return "7500-22.htm"

   # All other Races must be out
   if npcId == HIGH_PREFECT_OSBORN and Race in [Race.elf, Race.darkelf, Race.dwarf, Race.human]:
     st.exitQuest(1)
     return "7500-23.htm"

QUEST   = Quest(7500,"7500_osborn_occupation_change","village_master")
CREATED = State('Start',QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7500)

STARTED.addTalkId(7500)