#
# Created by DraX on 2005.08.23
#

print "importing village master data: Heine                  ...done"

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_PILGRIM_ID     = 2721
MARK_OF_TRUST_ID       = 2734
MARK_OF_HEALER_ID      = 2820
MARK_OF_REFORMER_ID    = 2821
MARK_OF_LIFE_ID        = 3140
HIGH_PRIEST_MAXIMILIAN = 7120
HIGH_PRIEST_HOLLINT    = 7191
HIGH_PRIEST_ORVEN      = 7857
HIGH_PRIEST_SQUILLARI  = 7905

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = "No Quest"
   
   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "7120-01.htm":
     return "7120-01.htm"

   if event == "7120-02.htm":
     return "7120-02.htm"

   if event == "7120-03.htm":
     return "7120-03.htm"

   if event == "7120-04.htm":
     return "7120-04.htm"

   if event == "7120-05.htm":
     return "7120-05.htm"

   if event == "7120-06.htm":
     return "7120-06.htm"

   if event == "7120-07.htm":
     return "7120-07.htm"

   if event == "7120-08.htm":
     return "7120-08.htm"

   if event == "7120-09.htm":
     return "7120-09.htm"

   if event == "7120-10.htm":
     return "7120-10.htm"

   if event == "7120-11.htm":
     return "7120-11.htm"

   if event == "class_change_30":
     if ClassId in [ClassId.oracle]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_PILGRIM_ID) == 0 or st.getQuestItemsCount(MARK_OF_LIFE_ID) == 0 or st.getQuestItemsCount(MARK_OF_HEALER_ID) == 0:
            htmltext = "7120-12.htm"
          else:
            htmltext = "7120-13.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_PILGRIM_ID) == 0 or st.getQuestItemsCount(MARK_OF_LIFE_ID) == 0 or st.getQuestItemsCount(MARK_OF_HEALER_ID) == 0:
            htmltext = "7120-14.htm"
          else:
            st.takeItems(MARK_OF_PILGRIM_ID,1)
            st.takeItems(MARK_OF_LIFE_ID,1)
            st.takeItems(MARK_OF_HEALER_ID,1)
            st.player.setClassId(30)
            st.player.setBaseClass(30)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7120-15.htm"

   if event == "class_change_16":
     if ClassId in [ClassId.cleric]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_PILGRIM_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_HEALER_ID) == 0:
            htmltext = "7120-16.htm"
          else:
            htmltext = "7120-17.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_PILGRIM_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_HEALER_ID) == 0:
            htmltext = "7120-18.htm"
          else:
            st.takeItems(MARK_OF_PILGRIM_ID,1)
            st.takeItems(MARK_OF_TRUST_ID,1)
            st.takeItems(MARK_OF_HEALER_ID,1)
            st.player.setClassId(16)
            st.player.setBaseClass(16)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7120-19.htm"

   if event == "class_change_17":
     if ClassId in [ClassId.cleric]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_PILGRIM_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_REFORMER_ID) == 0:
            htmltext = "7120-20.htm"
          else:
            htmltext = "7120-21.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_PILGRIM_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_REFORMER_ID) == 0:
            htmltext = "7120-22.htm"
          else:
            st.takeItems(MARK_OF_PILGRIM_ID,1)
            st.takeItems(MARK_OF_TRUST_ID,1)
            st.takeItems(MARK_OF_REFORMER_ID,1)
            st.player.setClassId(17)
            st.player.setBaseClass(17)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7120-23.htm"
          
   st.setState(COMPLETED)
   st.exitQuest(1)
   return htmltext


 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # Elfs and Humanss got accepted
   if npcId == HIGH_PRIEST_MAXIMILIAN or HIGH_PRIEST_HOLLINT or HIGH_PRIEST_ORVEN or HIGH_PRIEST_SQUILLARI and Race in [Race.elf, Race.human]:
     if ClassId in [ClassId.oracle]:
       st.setState(STARTED)
       return "7120-01.htm"
     elif ClassId in [ClassId.cleric]:
       st.setState(STARTED)
       return "7120-05.htm"
     elif ClassId in [ClassId.elder, ClassId.bishop, ClassId.prophet]:
       st.exitQuest(1)
       return "7120-25.htm"
     elif ClassId in [ClassId.wizard, ClassId.elvenWizard, ClassId.swordSinger, ClassId.silverRanger]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7120-26.htm"
     elif ClassId in [ClassId.sorceror, ClassId.necromancer, ClassId.warlock, ClassId.elvenKnight]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7120-26.htm"
     elif ClassId in [ClassId.spellsinger, ClassId.elementalSummoner, ClassId.plainsWalker]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7120-26.htm"
     elif ClassId in [ClassId.warrior, ClassId.knight, ClassId.rogue, ClassId.warlord, ClassId.paladin]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7120-26.htm"
     elif ClassId in [ClassId.treasureHunter, ClassId.gladiator, ClassId.darkAvenger, ClassId.hawkeye]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7120-26.htm"
     elif ClassId in [ClassId.fighter, ClassId.elvenFighter, ClassId.elvenScout, ClassId.templeKnight]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7120-26.htm"
     elif ClassId in [ClassId.elvenMage] or Race not in [Race.darkelf, Race.orc, Race.dwarf]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7120-24.htm"
     else:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7120-26.htm"

   # All other Races must be out
   else:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "7120-26.htm"

QUEST     = Quest(7120,"7120_maximilian_occupation_change","village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7120)
QUEST.addStartNpc(7191)
QUEST.addStartNpc(7857)
QUEST.addStartNpc(7905)

STARTED.addTalkId(7120)
STARTED.addTalkId(7191)
STARTED.addTalkId(7857)
STARTED.addTalkId(7905)
