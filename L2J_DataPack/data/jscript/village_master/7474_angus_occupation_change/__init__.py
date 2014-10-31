#
# Created by DraX on 2005.08.20
#

print "importing village master data: Giran Castle Town      ...done"

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_CHALLENGER_ID  = 2627
MARK_OF_DUTY_ID        = 2633
MARK_OF_SEEKER_ID      = 2673
MARK_OF_SCHOLAR_ID     = 2674
MARK_OF_PILGRIM_ID     = 2721
MARK_OF_DUELIST_ID     = 2762
MARK_OF_SEARCHER_ID    = 2809
MARK_OF_REFORMER_ID    = 2821
MARK_OF_MAGUS_ID       = 2840
MARK_OF_FATE_ID        = 3172
MARK_OF_SAGITTARIUS_ID = 3293
MARK_OF_WITCHCRAFT_ID  = 3307
MARK_OF_SUMMONER_ID    = 3336
GRAND_MASTER_BRECSON   = 7195
GRAND_MASTER_ANGUS     = 7474
GRAND_MASTER_MEDOWN    = 7699
GRAND_MASTER_OLTLIN    = 7862
GRAND_MASTER_XAIRAKIN  = 7910
GRAND_MAGISTER_FAIREN  = 7175

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = "No Quest"
   
   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "7474-01.htm":
     return "7474-01.htm"

   if event == "7474-02.htm":
     return "7474-02.htm"

   if event == "7474-03.htm":
     return "7474-03.htm"

   if event == "7474-04.htm":
     return "7474-04.htm"

   if event == "7474-05.htm":
     return "7474-05.htm"

   if event == "7474-06.htm":
     return "7474-06.htm"

   if event == "7474-07.htm":
     return "7474-07.htm"

   if event == "7474-08.htm":
     return "7474-08.htm"

   if event == "7474-09.htm":
     return "7474-09.htm"

   if event == "7474-10.htm":
     return "7474-10.htm"

   if event == "7474-11.htm":
     return "7474-11.htm"

   if event == "7474-12.htm":
     return "7474-12.htm"

   if event == "7474-13.htm":
     return "7474-13.htm"

   if event == "7474-14.htm":
     return "7474-14.htm"

   if event == "7474-15.htm":
     return "7474-15.htm"

   if event == "7474-16.htm":
     return "7474-16.htm"

   if event == "7474-17.htm":
     return "7474-17.htm"

   if event == "7474-18.htm":
     return "7474-18.htm"

   if event == "7474-19.htm":
     return "7474-19.htm"

   if event == "7474-20.htm":
     return "7474-20.htm"

   if event == "7474-21.htm":
     return "7474-21.htm"

   if event == "7474-22.htm":
     return "7474-22.htm"

   if event == "7474-23.htm":
     return "7474-23.htm"

   if event == "7474-24.htm":
     return "7474-24.htm"

   if event == "7474-25.htm":
     return "7474-25.htm"

   if event == "class_change_33":
     if ClassId in [ClassId.palusKnight]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_DUTY_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_WITCHCRAFT_ID) == 0:
            htmltext = "7474-26.htm"
          else:
            htmltext = "7474-27.htm"
        elif Level >= 40:
          if st.getQuestItemsCount(MARK_OF_DUTY_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_WITCHCRAFT_ID) == 0:
            htmltext = "7474-28.htm"
          else:
            st.takeItems(MARK_OF_DUTY_ID,1)
            st.takeItems(MARK_OF_FATE_ID,1)
            st.takeItems(MARK_OF_WITCHCRAFT_ID,1)
            st.player.setClassId(33)
            st.player.setBaseClass(33)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7474-29.htm"

   if event == "class_change_34":
     if ClassId in [ClassId.palusKnight]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_CHALLENGER_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_DUELIST_ID) == 0:
            htmltext = "7474-30.htm"
          else:
            htmltext = "7474-31.htm"
        elif Level >= 40:
          if st.getQuestItemsCount(MARK_OF_CHALLENGER_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_DUELIST_ID) == 0:
            htmltext = "7474-32.htm"
          else:
            st.takeItems(MARK_OF_CHALLENGER_ID,1)
            st.takeItems(MARK_OF_FATE_ID,1)
            st.takeItems(MARK_OF_DUELIST_ID,1)
            st.player.setClassId(34)
            st.player.setBaseClass(34)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7474-33.htm"

   if event == "class_change_43":
     if ClassId in [ClassId.shillienOracle]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_PILGRIM_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_REFORMER_ID) == 0:
            htmltext = "7474-34.htm"
          else:
            htmltext = "7474-35.htm"
        elif Level >= 40:
          if st.getQuestItemsCount(MARK_OF_PILGRIM_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_REFORMER_ID) == 0:
            htmltext = "7474-36.htm"
          else:
            st.takeItems(MARK_OF_PILGRIM_ID,1)
            st.takeItems(MARK_OF_FATE_ID,1)
            st.takeItems(MARK_OF_REFORMER_ID,1)
            st.player.setClassId(43)
            st.player.setBaseClass(43)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7474-37.htm"

   if event == "class_change_36":
     if ClassId in [ClassId.assassin]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_SEEKER_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_SEARCHER_ID) == 0:
            htmltext = "7474-38.htm"
          else:
            htmltext = "7474-39.htm"
        elif Level >= 40:
          if st.getQuestItemsCount(MARK_OF_SEEKER_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_SEARCHER_ID) == 0:
            htmltext = "7474-40.htm"
          else:
            st.takeItems(MARK_OF_SEEKER_ID,1)
            st.takeItems(MARK_OF_FATE_ID,1)
            st.takeItems(MARK_OF_SEARCHER_ID,1)
            st.player.setClassId(36)
            st.player.setBaseClass(36)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7474-41.htm"

   if event == "class_change_37":
     if ClassId in [ClassId.assassin]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_SEEKER_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_SAGITTARIUS_ID) == 0:
            htmltext = "7474-42.htm"
          else:
            htmltext = "7474-43.htm"
        elif Level >= 40:
          if st.getQuestItemsCount(MARK_OF_SEEKER_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_SAGITTARIUS_ID) == 0:
            htmltext = "7474-44.htm"
          else:
            st.takeItems(MARK_OF_SEEKER_ID,1)
            st.takeItems(MARK_OF_FATE_ID,1)
            st.takeItems(MARK_OF_SAGITTARIUS_ID,1)
            st.player.setClassId(37)
            st.player.setBaseClass(37)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7474-45.htm"

   if event == "class_change_40":
     if ClassId in [ClassId.darkWizard]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_SCHOLAR_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_MAGUS_ID) == 0:
            htmltext = "7474-46.htm"
          else:
            htmltext = "7474-47.htm"
        elif Level >= 40:
          if st.getQuestItemsCount(MARK_OF_SCHOLAR_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_MAGUS_ID) == 0:
            htmltext = "7474-48.htm"
          else:
            st.takeItems(MARK_OF_SCHOLAR_ID,1)
            st.takeItems(MARK_OF_FATE_ID,1)
            st.takeItems(MARK_OF_MAGUS_ID,1)
            st.player.setClassId(40)
            st.player.setBaseClass(40)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7474-49.htm"

   if event == "class_change_41":
     if ClassId in [ClassId.darkWizard]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_SCHOLAR_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_SUMMONER_ID) == 0:
            htmltext = "7474-50.htm"
          else:
            htmltext = "7474-51.htm"
        elif Level >= 40:
          if st.getQuestItemsCount(MARK_OF_SCHOLAR_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_SUMMONER_ID) == 0:
            htmltext = "7474-52.htm"
          else:
            st.takeItems(MARK_OF_SCHOLAR_ID,1)
            st.takeItems(MARK_OF_FATE_ID,1)
            st.takeItems(MARK_OF_SUMMONER_ID,1)
            st.player.setClassId(41)
            st.player.setBaseClass(41)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7474-53.htm"
          
   st.setState(COMPLETED)
   st.exitQuest(1)
   return htmltext


 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # DarkElfs got accepted
   if npcId == GRAND_MASTER_ANGUS or GRAND_MASTER_BRECSON or GRAND_MASTER_MEDOWN or GRAND_MASTER_OLTLIN or GRAND_MASTER_XAIRAKIN and Race in [Race.darkelf]:
     if ClassId in [ClassId.palusKnight]:
       st.setState(STARTED)
       return "7474-01.htm"
     elif ClassId in [ClassId.shillienOracle]:
       st.setState(STARTED)
       return "7474-08.htm"
     elif ClassId in [ClassId.assassin]:
       st.setState(STARTED)
       return "7474-12.htm"
     elif ClassId in [ClassId.darkWizard]:
       st.setState(STARTED)
       return "7474-19.htm"
     elif ClassId in [ClassId.shillienKnight, ClassId.abyssWalker, ClassId.bladedancer, ClassId.phantomRanger]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7474-54.htm"
     elif ClassId in [ClassId.spellhowler, ClassId.shillenElder, ClassId.phantomSummoner]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7474-54.htm"
     elif ClassId in [ClassId.darkFighter, ClassId.darkMage]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7474-55.htm"
     else:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7474-56.htm"

   elif npcId == GRAND_MAGISTER_FAIREN and Race in [Race.darkelf]:
     if ClassId in [ClassId.shillienOracle]:
       st.setState(STARTED)
       return "7474-08.htm"
     elif ClassId in [ClassId.darkWizard]:
       st.setState(STARTED)
       return "7474-19.htm"
     elif ClassId in [ClassId.spellhowler, ClassId.shillenElder, ClassId.phantomSummoner]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7474-54.htm"
     elif ClassId in [ClassId.darkMage]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7474-55.htm"
     else:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "7474-56.htm"

   # All other Races must be out
   else:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "7474-56.htm"

QUEST   = Quest(7474,"7474_angus_occupation_change","village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7175)
QUEST.addStartNpc(7195)
QUEST.addStartNpc(7474)
QUEST.addStartNpc(7699)
QUEST.addStartNpc(7862)
QUEST.addStartNpc(7910)

STARTED.addTalkId(7175)
STARTED.addTalkId(7195)
STARTED.addTalkId(7474)
STARTED.addTalkId(7699)
STARTED.addTalkId(7862)
STARTED.addTalkId(7910)
