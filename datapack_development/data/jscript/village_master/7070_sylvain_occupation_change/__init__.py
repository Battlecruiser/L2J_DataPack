#
# Created by DraX on 2005.08.17
#

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_FAITH_ID    = 1201
ETERNITY_DIAMOND_ID = 1230
LEAF_OF_ORACLE_ID   = 1235
BEAD_OF_SEASON_ID   = 1292
HIGH_PRIEST_SYLVAIN = 7070

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = "No Quest"
   
   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "7070-01.htm":
     return "7070-01.htm"

   if event == "7070-02.htm":
     return "7070-02.htm"

   if event == "7070-03.htm":
     return "7070-03.htm"

   if event == "7070-04.htm":
     return "7070-04.htm"

   if event == "7070-05.htm":
     return "7070-05.htm"

   if event == "7070-06.htm":
     return "7070-06.htm"

   if event == "7070-07.htm":
     return "7070-07.htm"

   if event == "7070-08.htm":
     return "7070-08.htm"

   if event == "7070-09.htm":
     return "7070-09.htm"

   if event == "7070-10.htm":
     return "7070-10.htm"

   if event == "7070-11.htm":
     return "7070-11.htm"

   if event == "7070-12.htm":
     return "7070-12.htm"

   if event == "7070-13.htm":
     return "7070-13.htm"

   if event == "7070-14.htm":
     return "7070-14.htm"

   if event == "class_change_26":
     if ClassId in [ClassId.elvenMage]:
        if Level <= 19 and st.getQuestItemsCount(ETERNITY_DIAMOND_ID) == 0:
          htmltext = "7070-15.htm"
        if Level <= 19 and st.getQuestItemsCount(ETERNITY_DIAMOND_ID) >= 1:
          htmltext = "7070-16.htm"
        if Level >= 20 and st.getQuestItemsCount(ETERNITY_DIAMOND_ID) == 0:
          htmltext = "7070-17.htm"
        if Level >= 20 and st.getQuestItemsCount(ETERNITY_DIAMOND_ID) >= 1:
          st.takeItems(ETERNITY_DIAMOND_ID,1)
          st.player.setClassId(26)
          st.player.setBaseClass(26)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "7070-18.htm"

   if event == "class_change_29":
     if ClassId in [ClassId.elvenMage]:
        if Level <= 19 and st.getQuestItemsCount(LEAF_OF_ORACLE_ID) == 0:
          htmltext = "7070-19.htm"
        if Level <= 19 and st.getQuestItemsCount(LEAF_OF_ORACLE_ID) >= 1:
          htmltext = "7070-20.htm"
        if Level >= 20 and st.getQuestItemsCount(LEAF_OF_ORACLE_ID) == 0:
          htmltext = "7070-21.htm"
        if Level >= 20 and st.getQuestItemsCount(LEAF_OF_ORACLE_ID) >= 1:
          st.takeItems(LEAF_OF_ORACLE_ID,1)
          st.player.setClassId(29)
          st.player.setBaseClass(29)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "7070-22.htm"

   if event == "class_change_11":
     # ClassId.mage seems buggy !?
     if ClassId not in [ClassId.fighter]:
        if Level <= 19 and st.getQuestItemsCount(BEAD_OF_SEASON_ID) == 0:
          htmltext = "7070-23.htm"
        if Level <= 19 and st.getQuestItemsCount(BEAD_OF_SEASON_ID) >= 1:
          htmltext = "7070-24.htm"
        if Level >= 20 and st.getQuestItemsCount(BEAD_OF_SEASON_ID) == 0:
          htmltext = "7070-25.htm"
        if Level >= 20 and st.getQuestItemsCount(BEAD_OF_SEASON_ID) >= 1:
          st.takeItems(BEAD_OF_SEASON_ID,1)
          st.player.setClassId(11)
          st.player.setBaseClass(11)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "7070-26.htm"

   if event == "class_change_15":
     # ClassId.mage seems buggy !?
     if ClassId not in [ClassId.fighter]:
        if Level <= 19 and st.getQuestItemsCount(MARK_OF_FAITH_ID) == 0:
          htmltext = "7070-27.htm"
        if Level <= 19 and st.getQuestItemsCount(MARK_OF_FAITH_ID) >= 1:
          htmltext = "7070-28.htm"
        if Level >= 20 and st.getQuestItemsCount(MARK_OF_FAITH_ID) == 0:
          htmltext = "7070-29.htm"
        if Level >= 20 and st.getQuestItemsCount(MARK_OF_FAITH_ID) >= 1:
          st.takeItems(MARK_OF_FAITH_ID,1)
          st.player.setClassId(15)
          st.player.setBaseClass(15)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "7070-30.htm"
          
   st.setState(COMPLETED)
   st.exitQuest(1)
   return htmltext


 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # Elfs and Humanss got accepted
   if npcId == HIGH_PRIEST_SYLVAIN and Race in [Race.elf, Race.human]:
     if ClassId in [ClassId.elvenMage]:
       st.setState(STARTED)
       return "7070-01.htm"
     if ClassId in [ClassId.wizard, ClassId.cleric, ClassId.elvenWizard, ClassId.oracle]:
       htmltext = "7070-31.htm"
     if ClassId in [ClassId.sorceror, ClassId.necromancer, ClassId.bishop, ClassId.warlock, ClassId.prophet]:
       htmltext = "7070-32.htm"
     if ClassId in [ClassId.spellsinger, ClassId.elder, ClassId.elementalSummoner]:
       htmltext = "7070-32.htm"
     if ClassId in [ClassId.warrior, ClassId.knight, ClassId.rogue, ClassId.warlord, ClassId.paladin]:
       htmltext = "7070-33.htm"
     if ClassId in [ClassId.treasureHunter, ClassId.gladiator, ClassId.darkAvenger, ClassId.hawkeye]:
       htmltext = "7070-33.htm"
     # ClassId.mage seems buggy !?
     if ClassId not in [ClassId.fighter]:
       st.setState(STARTED)
       return "7070-08.htm"
     else:
       htmltext = "7070-33.htm"
     
     st.setState(COMPLETED)
     st.exitQuest(1)
     return htmltext

   # All other Races must be out
   if npcId == HIGH_PRIEST_SYLVAIN and Race in [Race.dwarf, Race.darkelf, Race.orc]:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "7070-33.htm"

QUEST     = Quest(7070,"7070_sylvain_occupation_change","village_master")
CREATED   = State('Start',       QUEST)
STARTED   = State('Started',     QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7070)

STARTED.addTalkId(7070)
