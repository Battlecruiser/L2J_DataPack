#
# Created by DraX on 2005.08.14
#

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MEDALLION_OF_WARRIOR_ID    = 1145
SWORD_OF_RITUAL_ID         = 1161
BEZIQUES_RECOMMENDATION_ID = 1190
ELVEN_KNIGHT_BROOCH_ID     = 1204
REORIA_RECOMMENDATION_ID   = 1217
GRAND_MASTER_RAMOS         = 7373

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = "No Quest"
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   Level   = st.getPlayer().getLevel()

   if event == "7373-01.htm":
     return "7373-01.htm"

   if event == "7373-02.htm":
     return "7373-02.htm"

   if event == "7373-03.htm":
     return "7373-03.htm"

   if event == "7373-04.htm":
     return "7373-04.htm"

   if event == "7373-05.htm":
     return "7373-05.htm"

   if event == "7373-06.htm":
     return "7373-06.htm"

   if event == "7373-07.htm":
     return "7373-07.htm"

   if event == "7373-08.htm":
     return "7373-08.htm"

   if event == "7373-09.htm":
     return "7373-09.htm"

   if event == "7373-10.htm":
     return "7373-10.htm"

   if event == "7373-11.htm":
     return "7373-11.htm"

   if event == "7373-12.htm":
     return "7373-12.htm"

   if event == "7373-13.htm":
     return "7373-13.htm"

   if event == "7373-14.htm":
     return "7373-14.htm"

   if event == "7373-15.htm":
     return "7373-15.htm"

   if event == "7373-16.htm":
     return "7373-16.htm"

   if event == "7373-17.htm":
     return "7373-17.htm"

   if event == "class_change_19":
     if ClassId in [ClassId.elvenFighter]:
        if Level <= 19 and st.getQuestItemsCount(ELVEN_KNIGHT_BROOCH_ID) == 0:
          htmltext = "7373-18.htm"
        if Level <= 19 and st.getQuestItemsCount(ELVEN_KNIGHT_BROOCH_ID) >= 1:
          htmltext = "7373-19.htm"
        if Level >= 20 and st.getQuestItemsCount(ELVEN_KNIGHT_BROOCH_ID) == 0:
          htmltext = "7373-20.htm"
        if Level >= 20 and st.getQuestItemsCount(ELVEN_KNIGHT_BROOCH_ID) >= 1:
          st.takeItems(ELVEN_KNIGHT_BROOCH_ID,1)
          st.player.setClassId(19)
          st.player.setBaseClass(19)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "7373-21.htm"

   if event == "class_change_22":
     if ClassId in [ClassId.elvenFighter]:
        if Level <= 19 and st.getQuestItemsCount(REORIA_RECOMMENDATION_ID) == 0:
          htmltext = "7373-22.htm"
        if Level <= 19 and st.getQuestItemsCount(REORIA_RECOMMENDATION_ID) >= 1:
          htmltext = "7373-23.htm"
        if Level >= 20 and st.getQuestItemsCount(REORIA_RECOMMENDATION_ID) == 0:
          htmltext = "7373-24.htm"
        if Level >= 20 and st.getQuestItemsCount(REORIA_RECOMMENDATION_ID) >= 1:
          st.takeItems(REORIA_RECOMMENDATION_ID,1)
          st.player.setClassId(22)
          st.player.setBaseClass(22)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "7373-25.htm"

   if event == "class_change_1":
     if ClassId in [ClassId.fighter]:
        if Level <= 19 and st.getQuestItemsCount(MEDALLION_OF_WARRIOR_ID) == 0:
          htmltext = "7373-26.htm"
        if Level <= 19 and st.getQuestItemsCount(MEDALLION_OF_WARRIOR_ID) >= 1:
          htmltext = "7373-27.htm"
        if Level >= 20 and st.getQuestItemsCount(MEDALLION_OF_WARRIOR_ID) == 0:
          htmltext = "7373-28.htm"
        if Level >= 20 and st.getQuestItemsCount(MEDALLION_OF_WARRIOR_ID) >= 1:
          st.takeItems(MEDALLION_OF_WARRIOR_ID,1)
          st.player.setClassId(1)
          st.player.setBaseClass(1)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "7373-29.htm"

   if event == "class_change_4":
     if ClassId in [ClassId.fighter]:
        if Level <= 19 and st.getQuestItemsCount(SWORD_OF_RITUAL_ID) == 0:
          htmltext = "7373-30.htm"
        if Level <= 19 and st.getQuestItemsCount(SWORD_OF_RITUAL_ID) >= 1:
          htmltext = "7373-31.htm"
        if Level >= 20 and st.getQuestItemsCount(SWORD_OF_RITUAL_ID) == 0:
          htmltext = "7373-32.htm"
        if Level >= 20 and st.getQuestItemsCount(SWORD_OF_RITUAL_ID) >= 1:
          st.takeItems(SWORD_OF_RITUAL_ID,1)
          st.player.setClassId(4)
          st.player.setBaseClass(4)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "7373-33.htm"

   if event == "class_change_7":
     if ClassId in [ClassId.fighter]:
        if Level <= 19 and st.getQuestItemsCount(BEZIQUES_RECOMMENDATION_ID) == 0:
          htmltext = "7373-34.htm"
        if Level <= 19 and st.getQuestItemsCount(BEZIQUES_RECOMMENDATION_ID) >= 1:
          htmltext = "7373-35.htm"
        if Level >= 20 and st.getQuestItemsCount(BEZIQUES_RECOMMENDATION_ID) == 0:
          htmltext = "7373-36.htm"
        if Level >= 20 and st.getQuestItemsCount(BEZIQUES_RECOMMENDATION_ID) >= 1:
          st.takeItems(BEZIQUES_RECOMMENDATION_ID,1)
          st.player.setClassId(7)
          st.player.setBaseClass(7)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "7373-37.htm"
          
   st.setState(COMPLETED)
   st.exitQuest(1)
   return htmltext


 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # Humans and Elfs got accepted
   if npcId == GRAND_MASTER_RAMOS and Race in [Race.elf, Race.human]:
     if ClassId in [ClassId.elvenFighter]:
       st.setState(STARTED)
       return "7373-01.htm"
     if ClassId in [ClassId.fighter]:
       st.setState(STARTED)
       return "7373-08.htm"
     if ClassId in [ClassId.elvenKnight, ClassId.elvenScout, ClassId.warrior, ClassId.knight, ClassId.rogue]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7373-38.htm"     
     if ClassId in [ClassId.templeKnight, ClassId.plainsWalker, ClassId.swordSinger, ClassId.silverRanger]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7373-39.htm"
     if ClassId in [ClassId.warlord, ClassId.paladin, ClassId.treasureHunter]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7373-39.htm"
     if ClassId in [ClassId.gladiator, ClassId.darkAvenger, ClassId.hawkeye]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7373-39.htm"
     else:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7373-40.htm"

   # All other Races must be out
   if npcId == GRAND_MASTER_RAMOS and Race in [Race.dwarf, Race.darkelf, Race.orc]:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "7373-40.htm"

QUEST     = Quest(7373,"7373_ramos_occupation_change","village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7373)

STARTED.addTalkId(7373)
