#
# Created by DraX on 2005.08.18 modified by Ariakas on 2005.09.19
#

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_RAIDER_ID   = 1592
KHAVATARI_TOTEM_ID  = 1615
MASK_OF_MEDIUM_ID   = 1631
HIGH_PREFECT_CASTOR = 7508

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):

   htmltext = "No Quest"

   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "7508-01.htm":
     return "7508-01.htm"

   if event == "7508-02.htm":
     return "7508-02.htm"

   if event == "7508-03.htm":
     return "7508-03.htm"

   if event == "7508-04.htm":
     return "7508-04.htm"

   if event == "7508-05.htm":
     return "7508-05.htm"

   if event == "7508-06.htm":
     return "7508-06.htm"

   if event == "7508-07.htm":
     return "7508-07.htm"

   if event == "7508-08.htm":
     return "7508-08.htm"

   if event == "class_change_45":
     if ClassId in [ClassId.orcFighter]:
        if Level <= 19 and st.getQuestItemsCount(MARK_OF_RAIDER_ID) == 0:
          htmltext = "7508-09.htm"
        if Level <= 19 and st.getQuestItemsCount(MARK_OF_RAIDER_ID) >= 1:
          htmltext = "7508-10.htm"
        if Level >= 20 and st.getQuestItemsCount(MARK_OF_RAIDER_ID) == 0:
          htmltext = "7508-11.htm"
        if Level >= 20 and st.getQuestItemsCount(MARK_OF_RAIDER_ID) >= 1:
          st.takeItems(MARK_OF_RAIDER_ID,1)
          st.player.setClassId(45)
          st.player.setBaseClass(45)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "7508-12.htm"

   if event == "class_change_47":
     if ClassId in [ClassId.orcFighter]:
        if Level <= 19 and st.getQuestItemsCount(KHAVATARI_TOTEM_ID) == 0:
          htmltext = "7508-13.htm"
        if Level <= 19 and st.getQuestItemsCount(KHAVATARI_TOTEM_ID) >= 1:
          htmltext = "7508-14.htm"
        if Level >= 20 and st.getQuestItemsCount(KHAVATARI_TOTEM_ID) == 0:
          htmltext = "7508-15.htm"
        if Level >= 20 and st.getQuestItemsCount(KHAVATARI_TOTEM_ID) >= 1:
          st.takeItems(KHAVATARI_TOTEM_ID,1)
          st.player.setClassId(47)
          st.player.setBaseClass(47)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "7508-16.htm"

   if event == "class_change_50":
     if ClassId in [ClassId.orcMage]:
        if Level <= 19 and st.getQuestItemsCount(MASK_OF_MEDIUM_ID) == 0:
          htmltext = "7508-17.htm"
        if Level <= 19 and st.getQuestItemsCount(MASK_OF_MEDIUM_ID) >= 1:
          htmltext = "7508-18.htm"
        if Level >= 20 and st.getQuestItemsCount(MASK_OF_MEDIUM_ID) == 0:
          htmltext = "7508-19.htm"
        if Level >= 20 and st.getQuestItemsCount(MASK_OF_MEDIUM_ID) >= 1:
          st.takeItems(MASK_OF_MEDIUM_ID,1)
          st.player.setClassId(50)
          st.player.setBaseClass(50)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "7508-20.htm"

   st.setState(COMPLETED)
   st.exitQuest(1)
   return htmltext

 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()

   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()

   # orcs got accepted
   if npcId == HIGH_PREFECT_CASTOR and Race in [Race.orc]:
     if ClassId in [ClassId.orcFighter]:
       htmltext = "7508-01.htm"
       st.setState(STARTED)
       return htmltext
     if ClassId in [ClassId.orcMage]:
       htmltext = "7508-06.htm"
       st.setState(STARTED)
       return htmltext
     if ClassId in [ClassId.orcRaider, ClassId.orcMonk, ClassId.orcShaman]:
       htmltext = "7508-21.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext
     if ClassId in [ClassId.destroyer, ClassId.tyrant, ClassId.overlord, ClassId.warcryer]:
       htmltext = "7508-22.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext

   # All other Races must be out
   if npcId == HIGH_PREFECT_CASTOR and Race in [Race.elf, Race.darkelf, Race.dwarf, Race.human]:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "7508-23.htm"

QUEST   = Quest(7508,"7508_castor_occupation_change","village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7508)

STARTED.addTalkId(7508)
