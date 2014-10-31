#
# Created by DraX on 2005.08.23
#

print "importing village master data: Ivory Tower            ...done"

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
qn = "30115_jurek_occupation_change"
MARK_OF_SCHOLAR_ID      = 2674
MARK_OF_TRUST_ID        = 2734
MARK_OF_MAGUS_ID        = 2840
MARK_OF_LIFE_ID         = 3140
MARK_OF_WITCHCRFAT_ID   = 3307
MARK_OF_SUMMONER_ID     = 3336
GRAND_MAGISTER_JUREK    = 30115
GRAND_MAGISTER_ARKENIAS = 30174
GRAND_MAGISTER_VALLERIA = 30176
GRAND_MAGISTER_SCRAIDE  = 30694
GRAND_MAGISTER_DRIKIYAN = 30854

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = "No Quest"
   
   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "30115-01.htm":
     return "30115-01.htm"

   if event == "30115-02.htm":
     return "30115-02.htm"

   if event == "30115-03.htm":
     return "30115-03.htm"

   if event == "30115-04.htm":
     return "30115-04.htm"

   if event == "30115-05.htm":
     return "30115-05.htm"

   if event == "30115-06.htm":
     return "30115-06.htm"

   if event == "30115-07.htm":
     return "30115-07.htm"

   if event == "30115-08.htm":
     return "30115-08.htm"

   if event == "30115-09.htm":
     return "30115-09.htm"

   if event == "30115-10.htm":
     return "30115-10.htm"

   if event == "30115-11.htm":
     return "30115-11.htm"

   if event == "30115-12.htm":
     return "30115-12.htm"

   if event == "30115-13.htm":
     return "30115-13.htm"

   if event == "30115-14.htm":
     return "30115-14.htm"

   if event == "30115-15.htm":
     return "30115-15.htm"

   if event == "30115-16.htm":
     return "30115-16.htm"

   if event == "30115-17.htm":
     return "30115-17.htm"

   if event == "class_change_27":
     if ClassId in [ClassId.elvenWizard]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_SCHOLAR_ID) == 0 or st.getQuestItemsCount(MARK_OF_LIFE_ID) == 0 or st.getQuestItemsCount(MARK_OF_MAGUS_ID) == 0:
            htmltext = "30115-18.htm"
          else:
            htmltext = "30115-19.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_SCHOLAR_ID) == 0 or st.getQuestItemsCount(MARK_OF_LIFE_ID) == 0 or st.getQuestItemsCount(MARK_OF_MAGUS_ID) == 0:
            htmltext = "30115-20.htm"
          else:
            st.takeItems(MARK_OF_SCHOLAR_ID,1)
            st.takeItems(MARK_OF_LIFE_ID,1)
            st.takeItems(MARK_OF_MAGUS_ID,1)
            st.getPlayer().setClassId(27)
            st.getPlayer().setBaseClass(27)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30115-21.htm"

   if event == "class_change_28":
     if ClassId in [ClassId.elvenWizard]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_SCHOLAR_ID) == 0 or st.getQuestItemsCount(MARK_OF_LIFE_ID) == 0 or st.getQuestItemsCount(MARK_OF_SUMMONER_ID) == 0:
            htmltext = "30115-22.htm"
          else:
            htmltext = "30115-23.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_SCHOLAR_ID) == 0 or st.getQuestItemsCount(MARK_OF_LIFE_ID) == 0 or st.getQuestItemsCount(MARK_OF_SUMMONER_ID) == 0:
            htmltext = "30115-24.htm"
          else:
            st.takeItems(MARK_OF_SCHOLAR_ID,1)
            st.takeItems(MARK_OF_LIFE_ID,1)
            st.takeItems(MARK_OF_SUMMONER_ID,1)
            st.getPlayer().setClassId(28)
            st.getPlayer().setBaseClass(28)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30115-25.htm"

   if event == "class_change_12":
     if ClassId in [ClassId.wizard]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_SCHOLAR_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_MAGUS_ID) == 0:
            htmltext = "30115-26.htm"
          else:
            htmltext = "30115-27.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_SCHOLAR_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_MAGUS_ID) == 0:
            htmltext = "30115-28.htm"
          else:
            st.takeItems(MARK_OF_SCHOLAR_ID,1)
            st.takeItems(MARK_OF_TRUST_ID,1)
            st.takeItems(MARK_OF_MAGUS_ID,1)
            st.getPlayer().setClassId(12)
            st.getPlayer().setBaseClass(12)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30115-29.htm"

   if event == "class_change_13":
     if ClassId in [ClassId.wizard]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_SCHOLAR_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_WITCHCRFAT_ID) == 0:
            htmltext = "30115-30.htm"
          else:
            htmltext = "30115-31.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_SCHOLAR_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_WITCHCRFAT_ID) == 0:
            htmltext = "30115-32.htm"
          else:
            st.takeItems(MARK_OF_SCHOLAR_ID,1)
            st.takeItems(MARK_OF_TRUST_ID,1)
            st.takeItems(MARK_OF_WITCHCRFAT_ID,1)
            st.getPlayer().setClassId(13)
            st.getPlayer().setBaseClass(13)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30115-33.htm"

   if event == "class_change_14":
     if ClassId in [ClassId.wizard]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_SCHOLAR_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_SUMMONER_ID) == 0:
            htmltext = "30115-34.htm"
          else:
            htmltext = "30115-35.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_SCHOLAR_ID) == 0 or st.getQuestItemsCount(MARK_OF_TRUST_ID) == 0 or st.getQuestItemsCount(MARK_OF_SUMMONER_ID) == 0:
            htmltext = "30115-36.htm"
          else:
            st.takeItems(MARK_OF_SCHOLAR_ID,1)
            st.takeItems(MARK_OF_TRUST_ID,1)
            st.takeItems(MARK_OF_SUMMONER_ID,1)
            st.getPlayer().setClassId(14)
            st.getPlayer().setBaseClass(14)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30115-37.htm"
          
   st.setState(COMPLETED)
   st.exitQuest(1)
   return htmltext


 def onTalk (Self,npc,player):
   st = player.getQuestState(qn)
   npcId = npc.getNpcId()
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # Elfs and Humanss got accepted
   if npcId == GRAND_MAGISTER_JUREK or GRAND_MAGISTER_SCRAIDE or GRAND_MAGISTER_DRIKIYAN or GRAND_MAGISTER_VALLERIA or GRAND_MAGISTER_ARKENIAS and Race in [Race.elf, Race.human]:
     if ClassId in [ClassId.elvenWizard]:
       st.setState(STARTED)
       return "30115-01.htm"
     elif ClassId in [ClassId.wizard]:
       st.setState(STARTED)
       return "30115-08.htm"
     elif ClassId in [ClassId.oracle, ClassId.cleric]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30115-40.htm"
     elif ClassId in [ClassId.elder, ClassId.bishop, ClassId.prophet, ClassId.plainsWalker]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30115-40.htm"
     elif ClassId in [ClassId.swordSinger, ClassId.silverRanger, ClassId.elvenKnight]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30115-40.htm"
     elif ClassId in [ClassId.sorceror, ClassId.necromancer, ClassId.warlock]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30115-39.htm"
     elif ClassId in [ClassId.spellsinger, ClassId.elementalSummoner]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30115-39.htm"
     elif ClassId in [ClassId.warrior, ClassId.knight, ClassId.rogue, ClassId.warlord, ClassId.paladin]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30115-40.htm"
     elif ClassId in [ClassId.treasureHunter, ClassId.gladiator, ClassId.darkAvenger, ClassId.hawkeye]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30115-40.htm"
     elif ClassId in [ClassId.fighter, ClassId.elvenFighter, ClassId.elvenScout, ClassId.templeKnight]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30115-40.htm"
     elif ClassId in [ClassId.elvenMage] or Race not in [Race.darkelf, Race.orc, Race.dwarf]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30115-38.htm"
     else:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30115-40.htm"

   # All other Races must be out
   else:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "30115-40.htm"

QUEST     = Quest(30115,qn,"village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(30115)
QUEST.addStartNpc(30174)
QUEST.addStartNpc(30176)
QUEST.addStartNpc(30694)
QUEST.addStartNpc(30854)
QUEST.addStartNpc(31996)

QUEST.addTalkId(30115)
QUEST.addTalkId(30174)
QUEST.addTalkId(30176)
QUEST.addTalkId(30694)
QUEST.addTalkId(30854)
QUEST.addTalkId(31996)
