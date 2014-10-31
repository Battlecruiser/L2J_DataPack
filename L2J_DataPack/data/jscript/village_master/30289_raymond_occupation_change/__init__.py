#
# Created by DraX on 2005.08.13
#

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
qn = "30289_raymond_occupation_change"
MARK_OF_FAITH_ID    = 1201
ETERNITY_DIAMOND_ID = 1230
LEAF_OF_ORACLE_ID   = 1235
BEAD_OF_SEASON_ID   = 1292
HIGH_PRIEST_RAYMOND = 30289

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = "No Quest"
   
   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "30289-01.htm":
     return "30289-01.htm"

   if event == "30289-02.htm":
     return "30289-02.htm"

   if event == "30289-03.htm":
     return "30289-03.htm"

   if event == "30289-04.htm":
     return "30289-04.htm"

   if event == "30289-05.htm":
     return "30289-05.htm"

   if event == "30289-06.htm":
     return "30289-06.htm"

   if event == "30289-07.htm":
     return "30289-07.htm"

   if event == "30289-08.htm":
     return "30289-08.htm"

   if event == "30289-09.htm":
     return "30289-09.htm"

   if event == "30289-10.htm":
     return "30289-10.htm"

   if event == "30289-11.htm":
     return "30289-11.htm"

   if event == "30289-12.htm":
     return "30289-12.htm"

   if event == "30289-13.htm":
     return "30289-13.htm"

   if event == "30289-14.htm":
     return "30289-14.htm"

   if event == "class_change_26":
     if ClassId in [ClassId.elvenMage]:
        if Level <= 19 and st.getQuestItemsCount(ETERNITY_DIAMOND_ID) == 0:
          htmltext = "30289-15.htm"
        if Level <= 19 and st.getQuestItemsCount(ETERNITY_DIAMOND_ID) >= 1:
          htmltext = "30289-16.htm"
        if Level >= 20 and st.getQuestItemsCount(ETERNITY_DIAMOND_ID) == 0:
          htmltext = "30289-17.htm"
        if Level >= 20 and st.getQuestItemsCount(ETERNITY_DIAMOND_ID) >= 1:
          st.takeItems(ETERNITY_DIAMOND_ID,1)
          st.getPlayer().setClassId(26)
          st.getPlayer().setBaseClass(26)
          st.getPlayer().broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "30289-18.htm"

   if event == "class_change_29":
     if ClassId in [ClassId.elvenMage]:
        if Level <= 19 and st.getQuestItemsCount(LEAF_OF_ORACLE_ID) == 0:
          htmltext = "30289-19.htm"
        if Level <= 19 and st.getQuestItemsCount(LEAF_OF_ORACLE_ID) >= 1:
          htmltext = "30289-20.htm"
        if Level >= 20 and st.getQuestItemsCount(LEAF_OF_ORACLE_ID) == 0:
          htmltext = "30289-21.htm"
        if Level >= 20 and st.getQuestItemsCount(LEAF_OF_ORACLE_ID) >= 1:
          st.takeItems(LEAF_OF_ORACLE_ID,1)
          st.getPlayer().setClassId(29)
          st.getPlayer().setBaseClass(29)
          st.getPlayer().broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "30289-22.htm"

   if event == "class_change_11":
     # ClassId.mage seems buggy !?
     if ClassId not in [ClassId.fighter]:
        if Level <= 19 and st.getQuestItemsCount(BEAD_OF_SEASON_ID) == 0:
          htmltext = "30289-23.htm"
        if Level <= 19 and st.getQuestItemsCount(BEAD_OF_SEASON_ID) >= 1:
          htmltext = "30289-24.htm"
        if Level >= 20 and st.getQuestItemsCount(BEAD_OF_SEASON_ID) == 0:
          htmltext = "30289-25.htm"
        if Level >= 20 and st.getQuestItemsCount(BEAD_OF_SEASON_ID) >= 1:
          st.takeItems(BEAD_OF_SEASON_ID,1)
          st.getPlayer().setClassId(11)
          st.getPlayer().setBaseClass(11)
          st.getPlayer().broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "30289-26.htm"

   if event == "class_change_15":
     # ClassId.mage seems buggy !?
     if ClassId not in [ClassId.fighter]:
        if Level <= 19 and st.getQuestItemsCount(MARK_OF_FAITH_ID) == 0:
          htmltext = "30289-27.htm"
        if Level <= 19 and st.getQuestItemsCount(MARK_OF_FAITH_ID) >= 1:
          htmltext = "30289-28.htm"
        if Level >= 20 and st.getQuestItemsCount(MARK_OF_FAITH_ID) == 0:
          htmltext = "30289-29.htm"
        if Level >= 20 and st.getQuestItemsCount(MARK_OF_FAITH_ID) >= 1:
          st.takeItems(MARK_OF_FAITH_ID,1)
          st.getPlayer().setClassId(15)
          st.getPlayer().setBaseClass(15)
          st.getPlayer().broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "30289-30.htm"
          
   st.setState(COMPLETED)
   st.exitQuest(1)
   return htmltext


 def onTalk (Self,npc,player):
   st = player.getQuestState(qn)
   npcId = npc.getNpcId()
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # Elfs and Humanss got accepted
   if npcId == HIGH_PRIEST_RAYMOND and Race in [Race.elf, Race.human]:
     if ClassId in [ClassId.elvenMage]:
       st.setState(STARTED)
       return "30289-01.htm"
     if ClassId in [ClassId.wizard, ClassId.cleric, ClassId.elvenWizard, ClassId.oracle]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30289-31.htm"
     if ClassId in [ClassId.sorceror, ClassId.necromancer, ClassId.bishop, ClassId.warlock, ClassId.prophet]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30289-32.htm"
     if ClassId in [ClassId.spellsinger, ClassId.elder, ClassId.elementalSummoner]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30289-32.htm"
     if ClassId in [ClassId.warrior, ClassId.knight, ClassId.rogue, ClassId.warlord, ClassId.paladin]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30289-33.htm"
     if ClassId in [ClassId.treasureHunter, ClassId.gladiator, ClassId.darkAvenger, ClassId.hawkeye]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30289-33.htm"
     # ClassId.mage seems buggy !?
     if ClassId not in [ClassId.fighter]:
       st.setState(STARTED)
       return "30289-08.htm"
     else:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30289-33.htm"

   # All other Races must be out
   if npcId == HIGH_PRIEST_RAYMOND and Race in [Race.dwarf, Race.darkelf, Race.orc]:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "30289-33.htm"

QUEST     = Quest(30289,qn,"village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(30289)

QUEST.addTalkId(30289)
