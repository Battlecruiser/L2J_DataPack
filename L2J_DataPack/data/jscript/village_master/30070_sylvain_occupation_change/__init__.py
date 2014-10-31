#
# Created by DraX on 2005.08.17
#

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
qn = "30070_sylvain_occupation_change"
MARK_OF_FAITH_ID    = 1201
ETERNITY_DIAMOND_ID = 1230
LEAF_OF_ORACLE_ID   = 1235
BEAD_OF_SEASON_ID   = 1292
HIGH_PRIEST_SYLVAIN = 30070

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = "No Quest"
   
   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "30070-01.htm":
     return "30070-01.htm"

   if event == "30070-02.htm":
     return "30070-02.htm"

   if event == "30070-03.htm":
     return "30070-03.htm"

   if event == "30070-04.htm":
     return "30070-04.htm"

   if event == "30070-05.htm":
     return "30070-05.htm"

   if event == "30070-06.htm":
     return "30070-06.htm"

   if event == "30070-07.htm":
     return "30070-07.htm"

   if event == "30070-08.htm":
     return "30070-08.htm"

   if event == "30070-09.htm":
     return "30070-09.htm"

   if event == "30070-10.htm":
     return "30070-10.htm"

   if event == "30070-11.htm":
     return "30070-11.htm"

   if event == "30070-12.htm":
     return "30070-12.htm"

   if event == "30070-13.htm":
     return "30070-13.htm"

   if event == "30070-14.htm":
     return "30070-14.htm"

   if event == "class_change_26":
     if ClassId in [ClassId.elvenMage]:
        if Level <= 19 and st.getQuestItemsCount(ETERNITY_DIAMOND_ID) == 0:
          htmltext = "30070-15.htm"
        if Level <= 19 and st.getQuestItemsCount(ETERNITY_DIAMOND_ID) >= 1:
          htmltext = "30070-16.htm"
        if Level >= 20 and st.getQuestItemsCount(ETERNITY_DIAMOND_ID) == 0:
          htmltext = "30070-17.htm"
        if Level >= 20 and st.getQuestItemsCount(ETERNITY_DIAMOND_ID) >= 1:
          st.takeItems(ETERNITY_DIAMOND_ID,1)
          st.getPlayer().setClassId(26)
          st.getPlayer().setBaseClass(26)
          st.getPlayer().broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "30070-18.htm"

   if event == "class_change_29":
     if ClassId in [ClassId.elvenMage]:
        if Level <= 19 and st.getQuestItemsCount(LEAF_OF_ORACLE_ID) == 0:
          htmltext = "30070-19.htm"
        if Level <= 19 and st.getQuestItemsCount(LEAF_OF_ORACLE_ID) >= 1:
          htmltext = "30070-20.htm"
        if Level >= 20 and st.getQuestItemsCount(LEAF_OF_ORACLE_ID) == 0:
          htmltext = "30070-21.htm"
        if Level >= 20 and st.getQuestItemsCount(LEAF_OF_ORACLE_ID) >= 1:
          st.takeItems(LEAF_OF_ORACLE_ID,1)
          st.getPlayer().setClassId(29)
          st.getPlayer().setBaseClass(29)
          st.getPlayer().broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "30070-22.htm"

   if event == "class_change_11":
     # ClassId.mage seems buggy !?
     if ClassId not in [ClassId.fighter]:
        if Level <= 19 and st.getQuestItemsCount(BEAD_OF_SEASON_ID) == 0:
          htmltext = "30070-23.htm"
        if Level <= 19 and st.getQuestItemsCount(BEAD_OF_SEASON_ID) >= 1:
          htmltext = "30070-24.htm"
        if Level >= 20 and st.getQuestItemsCount(BEAD_OF_SEASON_ID) == 0:
          htmltext = "30070-25.htm"
        if Level >= 20 and st.getQuestItemsCount(BEAD_OF_SEASON_ID) >= 1:
          st.takeItems(BEAD_OF_SEASON_ID,1)
          st.getPlayer().setClassId(11)
          st.getPlayer().setBaseClass(11)
          st.getPlayer().broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "30070-26.htm"

   if event == "class_change_15":
     # ClassId.mage seems buggy !?
     if ClassId not in [ClassId.fighter]:
        if Level <= 19 and st.getQuestItemsCount(MARK_OF_FAITH_ID) == 0:
          htmltext = "30070-27.htm"
        if Level <= 19 and st.getQuestItemsCount(MARK_OF_FAITH_ID) >= 1:
          htmltext = "30070-28.htm"
        if Level >= 20 and st.getQuestItemsCount(MARK_OF_FAITH_ID) == 0:
          htmltext = "30070-29.htm"
        if Level >= 20 and st.getQuestItemsCount(MARK_OF_FAITH_ID) >= 1:
          st.takeItems(MARK_OF_FAITH_ID,1)
          st.getPlayer().setClassId(15)
          st.getPlayer().setBaseClass(15)
          st.getPlayer().broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "30070-30.htm"
          
   st.setState(COMPLETED)
   st.exitQuest(1)
   return htmltext


 def onTalk (Self,npc,player):
   st = player.getQuestState(qn)
   npcId = npc.getNpcId()
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # Elfs and Humanss got accepted
   if npcId == HIGH_PRIEST_SYLVAIN and Race in [Race.elf, Race.human]:
     if ClassId in [ClassId.elvenMage]:
       st.setState(STARTED)
       return "30070-01.htm"
     if ClassId in [ClassId.wizard, ClassId.cleric, ClassId.elvenWizard, ClassId.oracle]:
       htmltext = "30070-31.htm"
     if ClassId in [ClassId.sorceror, ClassId.necromancer, ClassId.bishop, ClassId.warlock, ClassId.prophet]:
       htmltext = "30070-32.htm"
     if ClassId in [ClassId.spellsinger, ClassId.elder, ClassId.elementalSummoner]:
       htmltext = "30070-32.htm"
     if ClassId in [ClassId.warrior, ClassId.knight, ClassId.rogue, ClassId.warlord, ClassId.paladin]:
       htmltext = "30070-33.htm"
     if ClassId in [ClassId.treasureHunter, ClassId.gladiator, ClassId.darkAvenger, ClassId.hawkeye]:
       htmltext = "30070-33.htm"
     # ClassId.mage seems buggy !?
     if ClassId not in [ClassId.fighter]:
       st.setState(STARTED)
       return "30070-08.htm"
     else:
       htmltext = "30070-33.htm"
     
     st.setState(COMPLETED)
     st.exitQuest(1)
     return htmltext

   # All other Races must be out
   if npcId == HIGH_PRIEST_SYLVAIN and Race in [Race.dwarf, Race.darkelf, Race.orc]:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "30070-33.htm"

QUEST     = Quest(30070,qn,"village_master")
CREATED   = State('Start',       QUEST)
STARTED   = State('Started',     QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(30070)

QUEST.addTalkId(30070)
