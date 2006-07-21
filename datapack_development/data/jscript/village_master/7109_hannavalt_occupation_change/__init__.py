#
# Created by DraX on 2005.08.23
#

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest


MARK_OF_CHALLENGER_ID  = 2627
MARK_OF_DUTY_ID        = 2633
MARK_OF_SEEKER_ID      = 2673
MARK_OF_TRUST_ID       = 2734
MARK_OF_DUELIST_ID     = 2762
MARK_OF_SEARCHER_ID    = 2809
MARK_OF_HEALER_ID      = 2820
MARK_OF_LIFE_ID        = 3140
MARK_OF_CHAMPION_ID    = 3276
MARK_OF_SAGITTARIUS_ID = 3293
MARK_OF_WITCHCRAFT_ID  = 3307
GRAND_MASTER_HANNAVALT = 7109
GRAND_MASTER_BLACKBIRD = 7187
GRAND_MASTER_SIRIA     = 7689
GRAND_MASTER_SEDRICK   = 7849
GRAND_MASTER_MARCUS    = 7900

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = "No Quest"
   
   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "7109-01.htm":
     return "7109-01.htm"

   if event == "7109-02.htm":
     return "7109-02.htm"

   if event == "7109-03.htm":
     return "7109-03.htm"

   if event == "7109-04.htm":
     return "7109-04.htm"

   if event == "7109-05.htm":
     return "7109-05.htm"

   if event == "7109-06.htm":
     return "7109-06.htm"

   if event == "7109-07.htm":
     return "7109-07.htm"

   if event == "7109-08.htm":
     return "7109-08.htm"

   if event == "7109-09.htm":
     return "7109-09.htm"

   if event == "7109-10.htm":
     return "7109-10.htm"

   if event == "7109-11.htm":
     return "7109-11.htm"

   if event == "7109-12.htm":
     return "7109-12.htm"

   if event == "7109-13.htm":
     return "7109-13.htm"

   if event == "7109-14.htm":
     return "7109-14.htm"

   if event == "7109-15.htm":
     return "7109-15.htm"

   if event == "7109-16.htm":
     return "7109-16.htm"

   if event == "7109-17.htm":
     return "7109-17.htm"

   if event == "7109-18.htm":
     return "7109-18.htm"

   if event == "7109-19.htm":
     return "7109-19.htm"

   if event == "7109-20.htm":
     return "7109-20.htm"

   if event == "7109-21.htm":
     return "7109-21.htm"

   if event == "7109-22.htm":
     return "7109-22.htm"

   if event == "7109-23.htm":
     return "7109-23.htm"

   if event == "7109-24.htm":
     return "7109-24.htm"

   if event == "7109-25.htm":
     return "7109-25.htm"

   if event == "7109-26.htm":
     return "7109-26.htm"

   if event == "7109-27.htm":
     return "7109-27.htm"

   if event == "7109-28.htm":
     return "7109-28.htm"

   if event == "7109-29.htm":
     return "7109-29.htm"

   if event == "7109-30.htm":
     return "7109-30.htm"

   if event == "7109-31.htm":
     return "7109-31.htm"

   if event == "7109-32.htm":
     return "7109-32.htm"

   if event == "7109-33.htm":
     return "7109-33.htm"

   if event == "7109-34.htm":
     return "7109-34.htm"

   if event == "7109-35.htm":
     return "7109-35.htm"

   if event == "class_change_20":
     if ClassId in [ClassId.elvenKnight]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_DUTY_ID) == 0 or st.getQuestItemsCount(MARK_OF_LIFE_ID) == 0 or st.getQuestItemsCount(MARK_OF_HEALER_ID) == 0:
            htmltext = "7109-36.htm"
          else:
            htmltext = "7109-37.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_DUTY_ID) == 0 or st.getQuestItemsCount(MARK_OF_LIFE_ID) == 0 or st.getQuestItemsCount(MARK_OF_HEALER_ID) == 0:
            htmltext = "7109-38.htm"
          else:
            st.takeItems(MARK_OF_DUTY_ID,1)
            st.takeItems(MARK_OF_LIFE_ID,1)
            st.takeItems(MARK_OF_HEALER_ID,1)
            st.player.setClassId(20)
            st.player.setBaseClass(20)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7109-39.htm"

   if event == "class_change_21":
     if ClassId in [ClassId.elvenKnight]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_CHALLENGER_ID) == 0 or st.getQuestItemsCount(MARK_OF_LIFE_ID) == 0 or st.getQuestItemsCount(MARK_OF_DUELIST_ID) == 0:
            htmltext = "7109-40.htm"
          else:
            htmltext = "7109-41.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_CHALLENGER_ID) == 0 or st.getQuestItemsCount(MARK_OF_LIFE_ID) == 0 or st.getQuestItemsCount(MARK_OF_DUELIST_ID) == 0:
            htmltext = "7109-42.htm"
          else:
            st.takeItems(MARK_OF_CHALLENGER_ID,1)
            st.takeItems(MARK_OF_LIFE_ID,1)
            st.takeItems(MARK_OF_DUELIST_ID,1)
            st.player.setClassId(21)
            st.player.setBaseClass(21)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7109-43.htm"

   if event == "class_change_5":
     if ClassId in [ClassId.knight]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_DUTY_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_HEALER_ID) == 0:
            htmltext = "7109-44.htm"
          else:
            htmltext = "7109-45.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_DUTY_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_HEALER_ID) == 0:
            htmltext = "7109-46.htm"
          else:
            st.takeItems(MARK_OF_DUTY_ID,1)
            st.takeItems(MARK_OF_TRUST_ID,1)
            st.takeItems(MARK_OF_HEALER_ID,1)
            st.player.setClassId(5)
            st.player.setBaseClass(5)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7109-47.htm"

   if event == "class_change_6":
     if ClassId in [ClassId.knight]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_DUTY_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_WITCHCRAFT_ID) == 0:
            htmltext = "7109-48.htm"
          else:
            htmltext = "7109-49.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_DUTY_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_WITCHCRAFT_ID) == 0:
            htmltext = "7109-50.htm"
          else:
            st.takeItems(MARK_OF_DUTY_ID,1)
            st.takeItems(MARK_OF_TRUST_ID,1)
            st.takeItems(MARK_OF_WITCHCRAFT_ID,1)
            st.player.setClassId(6)
            st.player.setBaseClass(6)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7109-51.htm"

   if event == "class_change_8":
     if ClassId in [ClassId.rogue]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_SEEKER_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_SEARCHER_ID) == 0:
            htmltext = "7109-52.htm"
          else:
            htmltext = "7109-53.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_SEEKER_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_SEARCHER_ID) == 0:
            htmltext = "7109-54.htm"
          else:
            st.takeItems(MARK_OF_SEEKER_ID,1)
            st.takeItems(MARK_OF_TRUST_ID,1)
            st.takeItems(MARK_OF_SEARCHER_ID,1)
            st.player.setClassId(8)
            st.player.setBaseClass(8)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7109-55.htm"

   if event == "class_change_9":
     if ClassId in [ClassId.rogue]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_SEEKER_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_SAGITTARIUS_ID) == 0:
            htmltext = "7109-56.htm"
          else:
            htmltext = "7109-57.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_SEEKER_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_SAGITTARIUS_ID) == 0:
            htmltext = "7109-58.htm"
          else:
            st.takeItems(MARK_OF_SEEKER_ID,1)
            st.takeItems(MARK_OF_TRUST_ID,1)
            st.takeItems(MARK_OF_SAGITTARIUS_ID,1)
            st.player.setClassId(9)
            st.player.setBaseClass(9)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7109-59.htm"

   if event == "class_change_23":
     if ClassId in [ClassId.elvenScout]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_SEEKER_ID) == 0 or st.getQuestItemsCount(MARK_OF_LIFE_ID) == 0 or st.getQuestItemsCount(MARK_OF_SEARCHER_ID) == 0:
            htmltext = "7109-60.htm"
          else:
            htmltext = "7109-61.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_SEEKER_ID) == 0 or st.getQuestItemsCount(MARK_OF_LIFE_ID) == 0 or st.getQuestItemsCount(MARK_OF_SEARCHER_ID) == 0:
            htmltext = "7109-62.htm"
          else:
            st.takeItems(MARK_OF_SEEKER_ID,1)
            st.takeItems(MARK_OF_LIFE_ID,1)
            st.takeItems(MARK_OF_SEARCHER_ID,1)
            st.player.setClassId(23)
            st.player.setBaseClass(23)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7109-63.htm"

   if event == "class_change_24":
     if ClassId in [ClassId.elvenScout]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_SEEKER_ID) == 0 or st.getQuestItemsCount(MARK_OF_LIFE_ID) == 0 or st.getQuestItemsCount(MARK_OF_SAGITTARIUS_ID) == 0:
            htmltext = "7109-64.htm"
          else:
            htmltext = "7109-65.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_SEEKER_ID) == 0 or st.getQuestItemsCount(MARK_OF_LIFE_ID) == 0 or st.getQuestItemsCount(MARK_OF_SAGITTARIUS_ID) == 0:
            htmltext = "7109-66.htm"
          else:
            st.takeItems(MARK_OF_SEEKER_ID,1)
            st.takeItems(MARK_OF_LIFE_ID,1)
            st.takeItems(MARK_OF_SAGITTARIUS_ID,1)
            st.player.setClassId(24)
            st.player.setBaseClass(24)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7109-67.htm"

   if event == "class_change_2":
     if ClassId in [ClassId.warrior]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_CHALLENGER_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_DUELIST_ID) == 0:
            htmltext = "7109-68.htm"
          else:
            htmltext = "7109-69.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_CHALLENGER_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_DUELIST_ID) == 0:
            htmltext = "7109-70.htm"
          else:
            st.takeItems(MARK_OF_CHALLENGER_ID,1)
            st.takeItems(MARK_OF_TRUST_ID,1)
            st.takeItems(MARK_OF_DUELIST_ID,1)
            st.player.setClassId(2)
            st.player.setBaseClass(2)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7109-71.htm"

   if event == "class_change_3":
     if ClassId in [ClassId.warrior]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_CHALLENGER_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_CHAMPION_ID) == 0:
            htmltext = "7109-72.htm"
          else:
            htmltext = "7109-73.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_CHALLENGER_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_CHAMPION_ID) == 0:
            htmltext = "7109-74.htm"
          else:
            st.takeItems(MARK_OF_CHALLENGER_ID,1)
            st.takeItems(MARK_OF_TRUST_ID,1)
            st.takeItems(MARK_OF_CHAMPION_ID,1)
            st.player.setClassId(3)
            st.player.setBaseClass(3)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7109-75.htm"
          
   st.setState(COMPLETED)
   st.exitQuest(1)
   return htmltext


 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # Humans and Elfs got accepted
   if npcId == GRAND_MASTER_HANNAVALT or GRAND_MASTER_SIRIA or GRAND_MASTER_BLACKBIRD or GRAND_MASTER_SEDRICK or GRAND_MASTER_MARCUS and Race in [Race.elf, Race.human]:
     if ClassId in [ClassId.elvenKnight]:
       st.setState(STARTED)
       return "7109-01.htm"
     elif ClassId in [ClassId.knight]:
       st.setState(STARTED)
       return "7109-08.htm"
     elif ClassId in [ClassId.rogue]:
       st.setState(STARTED)
       return "7109-15.htm"
     elif ClassId in [ClassId.elvenScout]:
       st.setState(STARTED)
       return "7109-22.htm"
     elif ClassId in [ClassId.warrior]:
       st.setState(STARTED)
       return "7109-29.htm"
     elif ClassId in [ClassId.elvenFighter, ClassId.fighter]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7109-76.htm"     
     elif ClassId in [ClassId.templeKnight, ClassId.plainsWalker, ClassId.swordSinger, ClassId.silverRanger]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7109-77.htm"
     elif ClassId in [ClassId.warlord, ClassId.paladin, ClassId.treasureHunter]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7109-77.htm"
     elif ClassId in [ClassId.gladiator, ClassId.darkAvenger, ClassId.hawkeye]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7109-77.htm"
     else:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7109-78.htm"

   # All other Races must be out
   else:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "7109-78.htm"

QUEST     = Quest(7109,"7109_hannavalt_occupation_change","village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7109)
QUEST.addStartNpc(7187)
QUEST.addStartNpc(7689)
QUEST.addStartNpc(7849)
QUEST.addStartNpc(7900)

STARTED.addTalkId(7109)
STARTED.addTalkId(7187)
STARTED.addTalkId(7689)
STARTED.addTalkId(7849)
STARTED.addTalkId(7900)
