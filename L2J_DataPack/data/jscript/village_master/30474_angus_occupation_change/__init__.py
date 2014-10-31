#
# Created by DraX on 2005.08.20
#

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
qn = "30474_angus_occupation_change"
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
GRAND_MASTER_BRECSON   = 30195
GRAND_MASTER_ANGUS     = 30474
GRAND_MASTER_MEDOWN    = 30699
GRAND_MASTER_OLTLIN    = 30862
GRAND_MASTER_XAIRAKIN  = 30910
GRAND_MAGISTER_FAIREN  = 30175

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = "No Quest"
   
   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "30474-01.htm":
     return "30474-01.htm"

   if event == "30474-02.htm":
     return "30474-02.htm"

   if event == "30474-03.htm":
     return "30474-03.htm"

   if event == "30474-04.htm":
     return "30474-04.htm"

   if event == "30474-05.htm":
     return "30474-05.htm"

   if event == "30474-06.htm":
     return "30474-06.htm"

   if event == "30474-07.htm":
     return "30474-07.htm"

   if event == "30474-08.htm":
     return "30474-08.htm"

   if event == "30474-09.htm":
     return "30474-09.htm"

   if event == "30474-10.htm":
     return "30474-10.htm"

   if event == "30474-11.htm":
     return "30474-11.htm"

   if event == "30474-12.htm":
     return "30474-12.htm"

   if event == "30474-13.htm":
     return "30474-13.htm"

   if event == "30474-14.htm":
     return "30474-14.htm"

   if event == "30474-15.htm":
     return "30474-15.htm"

   if event == "30474-16.htm":
     return "30474-16.htm"

   if event == "30474-17.htm":
     return "30474-17.htm"

   if event == "30474-18.htm":
     return "30474-18.htm"

   if event == "30474-19.htm":
     return "30474-19.htm"

   if event == "30474-20.htm":
     return "30474-20.htm"

   if event == "30474-21.htm":
     return "30474-21.htm"

   if event == "30474-22.htm":
     return "30474-22.htm"

   if event == "30474-23.htm":
     return "30474-23.htm"

   if event == "30474-24.htm":
     return "30474-24.htm"

   if event == "30474-25.htm":
     return "30474-25.htm"

   if event == "class_change_33":
     if ClassId in [ClassId.palusKnight]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_DUTY_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_WITCHCRAFT_ID) == 0:
            htmltext = "30474-26.htm"
          else:
            htmltext = "30474-27.htm"
        elif Level >= 40:
          if st.getQuestItemsCount(MARK_OF_DUTY_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_WITCHCRAFT_ID) == 0:
            htmltext = "30474-28.htm"
          else:
            st.takeItems(MARK_OF_DUTY_ID,1)
            st.takeItems(MARK_OF_FATE_ID,1)
            st.takeItems(MARK_OF_WITCHCRAFT_ID,1)
            st.getPlayer().setClassId(33)
            st.getPlayer().setBaseClass(33)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30474-29.htm"

   if event == "class_change_34":
     if ClassId in [ClassId.palusKnight]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_CHALLENGER_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_DUELIST_ID) == 0:
            htmltext = "30474-30.htm"
          else:
            htmltext = "30474-31.htm"
        elif Level >= 40:
          if st.getQuestItemsCount(MARK_OF_CHALLENGER_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_DUELIST_ID) == 0:
            htmltext = "30474-32.htm"
          else:
            st.takeItems(MARK_OF_CHALLENGER_ID,1)
            st.takeItems(MARK_OF_FATE_ID,1)
            st.takeItems(MARK_OF_DUELIST_ID,1)
            st.getPlayer().setClassId(34)
            st.getPlayer().setBaseClass(34)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30474-33.htm"

   if event == "class_change_43":
     if ClassId in [ClassId.shillienOracle]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_PILGRIM_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_REFORMER_ID) == 0:
            htmltext = "30474-34.htm"
          else:
            htmltext = "30474-35.htm"
        elif Level >= 40:
          if st.getQuestItemsCount(MARK_OF_PILGRIM_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_REFORMER_ID) == 0:
            htmltext = "30474-36.htm"
          else:
            st.takeItems(MARK_OF_PILGRIM_ID,1)
            st.takeItems(MARK_OF_FATE_ID,1)
            st.takeItems(MARK_OF_REFORMER_ID,1)
            st.getPlayer().setClassId(43)
            st.getPlayer().setBaseClass(43)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30474-37.htm"

   if event == "class_change_36":
     if ClassId in [ClassId.assassin]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_SEEKER_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_SEARCHER_ID) == 0:
            htmltext = "30474-38.htm"
          else:
            htmltext = "30474-39.htm"
        elif Level >= 40:
          if st.getQuestItemsCount(MARK_OF_SEEKER_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_SEARCHER_ID) == 0:
            htmltext = "30474-40.htm"
          else:
            st.takeItems(MARK_OF_SEEKER_ID,1)
            st.takeItems(MARK_OF_FATE_ID,1)
            st.takeItems(MARK_OF_SEARCHER_ID,1)
            st.getPlayer().setClassId(36)
            st.getPlayer().setBaseClass(36)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30474-41.htm"

   if event == "class_change_37":
     if ClassId in [ClassId.assassin]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_SEEKER_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_SAGITTARIUS_ID) == 0:
            htmltext = "30474-42.htm"
          else:
            htmltext = "30474-43.htm"
        elif Level >= 40:
          if st.getQuestItemsCount(MARK_OF_SEEKER_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_SAGITTARIUS_ID) == 0:
            htmltext = "30474-44.htm"
          else:
            st.takeItems(MARK_OF_SEEKER_ID,1)
            st.takeItems(MARK_OF_FATE_ID,1)
            st.takeItems(MARK_OF_SAGITTARIUS_ID,1)
            st.getPlayer().setClassId(37)
            st.getPlayer().setBaseClass(37)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30474-45.htm"

   if event == "class_change_40":
     if ClassId in [ClassId.darkWizard]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_SCHOLAR_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_MAGUS_ID) == 0:
            htmltext = "30474-46.htm"
          else:
            htmltext = "30474-47.htm"
        elif Level >= 40:
          if st.getQuestItemsCount(MARK_OF_SCHOLAR_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_MAGUS_ID) == 0:
            htmltext = "30474-48.htm"
          else:
            st.takeItems(MARK_OF_SCHOLAR_ID,1)
            st.takeItems(MARK_OF_FATE_ID,1)
            st.takeItems(MARK_OF_MAGUS_ID,1)
            st.getPlayer().setClassId(40)
            st.getPlayer().setBaseClass(40)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30474-49.htm"

   if event == "class_change_41":
     if ClassId in [ClassId.darkWizard]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_SCHOLAR_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_SUMMONER_ID) == 0:
            htmltext = "30474-50.htm"
          else:
            htmltext = "30474-51.htm"
        elif Level >= 40:
          if st.getQuestItemsCount(MARK_OF_SCHOLAR_ID) == 0 or st.getQuestItemsCount(MARK_OF_FATE_ID) == 0 or st.getQuestItemsCount(MARK_OF_SUMMONER_ID) == 0:
            htmltext = "30474-52.htm"
          else:
            st.takeItems(MARK_OF_SCHOLAR_ID,1)
            st.takeItems(MARK_OF_FATE_ID,1)
            st.takeItems(MARK_OF_SUMMONER_ID,1)
            st.getPlayer().setClassId(41)
            st.getPlayer().setBaseClass(41)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30474-53.htm"
          
   st.setState(COMPLETED)
   st.exitQuest(1)
   return htmltext


 def onTalk (Self,npc,player):
   st = player.getQuestState(qn)

   npcId = npc.getNpcId()
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # DarkElfs got accepted
   if npcId in [GRAND_MASTER_ANGUS,GRAND_MASTER_BRECSON,GRAND_MASTER_MEDOWN,GRAND_MASTER_OLTLIN,GRAND_MASTER_XAIRAKIN,31324,31285,31331,31334] and Race in [Race.darkelf]:
     if ClassId in [ClassId.palusKnight]:
       st.setState(STARTED)
       return "30474-01.htm"
     elif ClassId in [ClassId.shillienOracle]:
       st.setState(STARTED)
       return "30474-08.htm"
     elif ClassId in [ClassId.assassin]:
       st.setState(STARTED)
       return "30474-12.htm"
     elif ClassId in [ClassId.darkWizard]:
       st.setState(STARTED)
       return "30474-19.htm"
     elif ClassId in [ClassId.shillienKnight, ClassId.abyssWalker, ClassId.bladedancer, ClassId.phantomRanger]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30474-54.htm"
     elif ClassId in [ClassId.spellhowler, ClassId.shillenElder, ClassId.phantomSummoner]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30474-54.htm"
     elif ClassId in [ClassId.darkFighter, ClassId.darkMage]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30474-55.htm"
     else:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30474-56.htm"

   elif npcId == GRAND_MAGISTER_FAIREN and Race in [Race.darkelf]:
     if ClassId in [ClassId.shillienOracle]:
       st.setState(STARTED)
       return "30474-08.htm"
     elif ClassId in [ClassId.darkWizard]:
       st.setState(STARTED)
       return "30474-19.htm"
     elif ClassId in [ClassId.spellhowler, ClassId.shillenElder, ClassId.phantomSummoner]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30474-54.htm"
     elif ClassId in [ClassId.darkMage]:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30474-55.htm"
     else:
       st.setState(COMPLETED)
       st.exitQuest(1)
       return "30474-56.htm"

   # All other Races must be out
   else:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "30474-56.htm"

QUEST   = Quest(30474,qn,"village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
for i in [31328,30195,30699,30474,31324,30862,30175,30910,31285,31331,31334,31974,32096]:
    QUEST.addStartNpc(i)
    QUEST.addTalkId(i)

print "importing village master data: Giran Castle Town      ...done"
