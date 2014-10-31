#
# Created by DraX on 2005.08.23
#

print "importing village master data: Heine                  ...done"

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
qn = "30120_maximilian_occupation_change"
MARK_OF_PILGRIM_ID     = 2721
MARK_OF_TRUST_ID       = 2734
MARK_OF_HEALER_ID      = 2820
MARK_OF_REFORMER_ID    = 2821
MARK_OF_LIFE_ID        = 3140
HIGH_PRIEST_MAXIMILIAN = 30120
HIGH_PRIEST_HOLLINT    = 30191
HIGH_PRIEST_ORVEN      = 30857
HIGH_PRIEST_SQUILLARI  = 30905

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = "No Quest"
   
   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "30120-01.htm":
     return "30120-01.htm"

   if event == "30120-02.htm":
     return "30120-02.htm"

   if event == "30120-03.htm":
     return "30120-03.htm"

   if event == "30120-04.htm":
     return "30120-04.htm"

   if event == "30120-05.htm":
     return "30120-05.htm"

   if event == "30120-06.htm":
     return "30120-06.htm"

   if event == "30120-07.htm":
     return "30120-07.htm"

   if event == "30120-08.htm":
     return "30120-08.htm"

   if event == "30120-09.htm":
     return "30120-09.htm"

   if event == "30120-10.htm":
     return "30120-10.htm"

   if event == "30120-11.htm":
     return "30120-11.htm"

   if event == "class_change_30":
     if ClassId in [ClassId.oracle]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_PILGRIM_ID) == 0 or st.getQuestItemsCount(MARK_OF_LIFE_ID) == 0 or st.getQuestItemsCount(MARK_OF_HEALER_ID) == 0:
            htmltext = "30120-12.htm"
          else:
            htmltext = "30120-13.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_PILGRIM_ID) == 0 or st.getQuestItemsCount(MARK_OF_LIFE_ID) == 0 or st.getQuestItemsCount(MARK_OF_HEALER_ID) == 0:
            htmltext = "30120-14.htm"
          else:
            st.takeItems(MARK_OF_PILGRIM_ID,1)
            st.takeItems(MARK_OF_LIFE_ID,1)
            st.takeItems(MARK_OF_HEALER_ID,1)
            st.getPlayer().setClassId(30)
            st.getPlayer().setBaseClass(30)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30120-15.htm"

   if event == "class_change_16":
     if ClassId in [ClassId.cleric]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_PILGRIM_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_HEALER_ID) == 0:
            htmltext = "30120-16.htm"
          else:
            htmltext = "30120-17.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_PILGRIM_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_HEALER_ID) == 0:
            htmltext = "30120-18.htm"
          else:
            st.takeItems(MARK_OF_PILGRIM_ID,1)
            st.takeItems(MARK_OF_TRUST_ID,1)
            st.takeItems(MARK_OF_HEALER_ID,1)
            st.getPlayer().setClassId(16)
            st.getPlayer().setBaseClass(16)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30120-19.htm"

   if event == "class_change_17":
     if ClassId in [ClassId.cleric]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_PILGRIM_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_REFORMER_ID) == 0:
            htmltext = "30120-20.htm"
          else:
            htmltext = "30120-21.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_PILGRIM_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_REFORMER_ID) == 0:
            htmltext = "30120-22.htm"
          else:
            st.takeItems(MARK_OF_PILGRIM_ID,1)
            st.takeItems(MARK_OF_TRUST_ID,1)
            st.takeItems(MARK_OF_REFORMER_ID,1)
            st.getPlayer().setClassId(17)
            st.getPlayer().setBaseClass(17)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30120-23.htm"
          
   st.setState(COMPLETED)
   st.exitQuest(1)
   return htmltext


 def onTalk (Self,npc,player):
   st = player.getQuestState(qn)
   npcId = npc.getNpcId()
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # Elfs and Humanss got accepted
   if Race in [Race.elf, Race.human]:
     if ClassId in [ClassId.oracle]:
       st.setState(STARTED)
       return "30120-01.htm"
     elif ClassId in [ClassId.cleric]:
       st.setState(STARTED)
       return "30120-05.htm"
     elif ClassId in [ClassId.elder, ClassId.bishop, ClassId.prophet]:
       st.exitQuest(1)
       return "30120-25.htm"
     elif ClassId in [ClassId.wizard, ClassId.elvenWizard, ClassId.swordSinger, ClassId.silverRanger]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30120-26.htm"
     elif ClassId in [ClassId.sorceror, ClassId.necromancer, ClassId.warlock, ClassId.elvenKnight]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30120-26.htm"
     elif ClassId in [ClassId.spellsinger, ClassId.elementalSummoner, ClassId.plainsWalker]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30120-26.htm"
     elif ClassId in [ClassId.warrior, ClassId.knight, ClassId.rogue, ClassId.warlord, ClassId.paladin]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30120-26.htm"
     elif ClassId in [ClassId.treasureHunter, ClassId.gladiator, ClassId.darkAvenger, ClassId.hawkeye]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30120-26.htm"
     elif ClassId in [ClassId.fighter, ClassId.elvenFighter, ClassId.elvenScout, ClassId.templeKnight]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30120-26.htm"
     elif ClassId in [ClassId.elvenMage] or Race not in [Race.darkelf, Race.orc, Race.dwarf]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30120-24.htm"
     else:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30120-26.htm"

   # All other Races must be out
   else:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "30120-26.htm"

QUEST     = Quest(30120,qn,"village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(30120)
QUEST.addStartNpc(30191)
QUEST.addStartNpc(30857)
QUEST.addStartNpc(30905)
QUEST.addStartNpc(31276)
QUEST.addStartNpc(31321)
QUEST.addStartNpc(31279)
QUEST.addStartNpc(31755)
QUEST.addStartNpc(31968)
QUEST.addStartNpc(32095)

QUEST.addTalkId(30120)
QUEST.addTalkId(30191)
QUEST.addTalkId(30857)
QUEST.addTalkId(30905)
QUEST.addTalkId(31276)
QUEST.addTalkId(31321)
QUEST.addTalkId(31279)
QUEST.addTalkId(31755)
QUEST.addTalkId(31968)
QUEST.addTalkId(32095)


