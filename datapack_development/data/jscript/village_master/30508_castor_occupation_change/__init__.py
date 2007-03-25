#
# Created by DraX on 2005.08.18 modified by Ariakas on 2005.09.19
#

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
qn = "30508_castor_occupation_change"
MARK_OF_RAIDER_ID   = 1592
KHAVATARI_TOTEM_ID  = 1615
MASK_OF_MEDIUM_ID   = 1631
HIGH_PREFECT_CASTOR = 30508

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):

   htmltext = "No Quest"

   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "30508-01.htm":
     return "30508-01.htm"

   if event == "30508-02.htm":
     return "30508-02.htm"

   if event == "30508-03.htm":
     return "30508-03.htm"

   if event == "30508-04.htm":
     return "30508-04.htm"

   if event == "30508-05.htm":
     return "30508-05.htm"

   if event == "30508-06.htm":
     return "30508-06.htm"

   if event == "30508-07.htm":
     return "30508-07.htm"

   if event == "30508-08.htm":
     return "30508-08.htm"

   if event == "class_change_45":
     if ClassId in [ClassId.orcFighter]:
        if Level <= 19 and st.getQuestItemsCount(MARK_OF_RAIDER_ID) == 0:
          htmltext = "30508-09.htm"
        if Level <= 19 and st.getQuestItemsCount(MARK_OF_RAIDER_ID) >= 1:
          htmltext = "30508-10.htm"
        if Level >= 20 and st.getQuestItemsCount(MARK_OF_RAIDER_ID) == 0:
          htmltext = "30508-11.htm"
        if Level >= 20 and st.getQuestItemsCount(MARK_OF_RAIDER_ID) >= 1:
          st.takeItems(MARK_OF_RAIDER_ID,1)
          st.getPlayer().setClassId(45)
          st.getPlayer().setBaseClass(45)
          st.getPlayer().broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "30508-12.htm"

   if event == "class_change_47":
     if ClassId in [ClassId.orcFighter]:
        if Level <= 19 and st.getQuestItemsCount(KHAVATARI_TOTEM_ID) == 0:
          htmltext = "30508-13.htm"
        if Level <= 19 and st.getQuestItemsCount(KHAVATARI_TOTEM_ID) >= 1:
          htmltext = "30508-14.htm"
        if Level >= 20 and st.getQuestItemsCount(KHAVATARI_TOTEM_ID) == 0:
          htmltext = "30508-15.htm"
        if Level >= 20 and st.getQuestItemsCount(KHAVATARI_TOTEM_ID) >= 1:
          st.takeItems(KHAVATARI_TOTEM_ID,1)
          st.getPlayer().setClassId(47)
          st.getPlayer().setBaseClass(47)
          st.getPlayer().broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "30508-16.htm"

   if event == "class_change_50":
     if ClassId in [ClassId.orcMage]:
        if Level <= 19 and st.getQuestItemsCount(MASK_OF_MEDIUM_ID) == 0:
          htmltext = "30508-17.htm"
        if Level <= 19 and st.getQuestItemsCount(MASK_OF_MEDIUM_ID) >= 1:
          htmltext = "30508-18.htm"
        if Level >= 20 and st.getQuestItemsCount(MASK_OF_MEDIUM_ID) == 0:
          htmltext = "30508-19.htm"
        if Level >= 20 and st.getQuestItemsCount(MASK_OF_MEDIUM_ID) >= 1:
          st.takeItems(MASK_OF_MEDIUM_ID,1)
          st.getPlayer().setClassId(50)
          st.getPlayer().setBaseClass(50)
          st.getPlayer().broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "30508-20.htm"

   st.setState(COMPLETED)
   st.exitQuest(1)
   return htmltext

 def onTalk (Self,npc,player):
   st = player.getQuestState(qn)
   npcId = npc.getNpcId()

   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()

   # orcs got accepted
   if npcId == HIGH_PREFECT_CASTOR and Race in [Race.orc]:
     if ClassId in [ClassId.orcFighter]:
       htmltext = "30508-01.htm"
       st.setState(STARTED)
       return htmltext
     if ClassId in [ClassId.orcMage]:
       htmltext = "30508-06.htm"
       st.setState(STARTED)
       return htmltext
     if ClassId in [ClassId.orcRaider, ClassId.orcMonk, ClassId.orcShaman]:
       htmltext = "30508-21.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext
     if ClassId in [ClassId.destroyer, ClassId.tyrant, ClassId.overlord, ClassId.warcryer]:
       htmltext = "30508-22.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext

   # All other Races must be out
   if npcId == HIGH_PREFECT_CASTOR and Race in [Race.elf, Race.darkelf, Race.dwarf, Race.human]:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "30508-23.htm"

QUEST   = Quest(30508,qn,"village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(30508)

QUEST.addTalkId(30508)
