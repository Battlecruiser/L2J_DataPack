#
# Created by DraX on 2005.08.21 modified by Ariakas on 2005.09.19
#

print "importing village master data: Tower of Aden          ...done"

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
qn = "30513_penatus_occupation_change"
MARK_OF_CHALLENGER_ID   = 2627
MARK_OF_PILGRIM_ID      = 2721
MARK_OF_DUELIST_ID      = 2762
MARK_OF_WARSPIRIT_ID    = 2879
MARK_OF_GLORY_ID        = 3203
MARK_OF_CHAMPION_ID     = 3276
MARK_OF_LORD_ID         = 3390
HIGH_PREFECT_PENATUS    = 30513
HIGH_PREFECT_KARIA      = 30681
HIGH_PREFECT_GARVARENTZ = 30704
HIGH_PREFECT_LADANZA    = 30865
HIGH_PREFECT_TUSHKU     = 30913
HIGH_PREFECT_AKLAN      = 31288
HIGH_PREFECT_LAMBAC     = 31326


class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):

   htmltext = "No Quest"

   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "30513-01.htm":
     return "30513-01.htm"

   if event == "30513-02.htm":
     return "30513-02.htm"

   if event == "30513-03.htm":
     return "30513-03.htm"

   if event == "30513-04.htm":
     return "30513-04.htm"

   if event == "30513-05.htm":
     return "30513-05.htm"

   if event == "30513-06.htm":
     return "30513-06.htm"

   if event == "30513-07.htm":
     return "30513-07.htm"

   if event == "30513-08.htm":
     return "30513-08.htm"

   if event == "30513-09.htm":
     return "30513-09.htm"

   if event == "30513-10.htm":
     return "30513-10.htm"

   if event == "30513-11.htm":
     return "30513-11.htm"

   if event == "30513-12.htm":
     return "30513-12.htm"

   if event == "30513-13.htm":
     return "30513-13.htm"

   if event == "30513-14.htm":
     return "30513-14.htm"

   if event == "30513-15.htm":
     return "30513-15.htm"

   if event == "class_change_48":
     if ClassId in [ClassId.orcMonk]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_CHALLENGER_ID) == 0 or st.getQuestItemsCount(MARK_OF_GLORY_ID) == 0 or st.getQuestItemsCount(MARK_OF_DUELIST_ID) == 0:
            htmltext = "30513-16.htm"
          else:
            htmltext = "30513-17.htm"
        elif Level >= 40:
          if st.getQuestItemsCount(MARK_OF_CHALLENGER_ID) == 0 or st.getQuestItemsCount(MARK_OF_GLORY_ID) == 0 or st.getQuestItemsCount(MARK_OF_DUELIST_ID) == 0:
            htmltext = "30513-18.htm"
          else:
            st.takeItems(MARK_OF_CHALLENGER_ID,1)
            st.takeItems(MARK_OF_GLORY_ID,1)
            st.takeItems(MARK_OF_DUELIST_ID,1)
            st.getPlayer().setClassId(48)
            st.getPlayer().setBaseClass(48)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30513-19.htm"

   if event == "class_change_46":
     if ClassId in [ClassId.orcRaider]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_CHALLENGER_ID) == 0 or st.getQuestItemsCount(MARK_OF_GLORY_ID) == 0 or st.getQuestItemsCount(MARK_OF_CHAMPION_ID) == 0:
            htmltext = "30513-20.htm"
          else:
            htmltext = "30513-21.htm"
        elif Level >= 40:
          if st.getQuestItemsCount(MARK_OF_CHALLENGER_ID) == 0 or st.getQuestItemsCount(MARK_OF_GLORY_ID) == 0 or st.getQuestItemsCount(MARK_OF_CHAMPION_ID) == 0:
            htmltext = "30513-22.htm"
          else:
            st.takeItems(MARK_OF_CHALLENGER_ID,1)
            st.takeItems(MARK_OF_GLORY_ID,1)
            st.takeItems(MARK_OF_CHAMPION_ID,1)
            st.getPlayer().setClassId(46)
            st.getPlayer().setBaseClass(46)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30513-23.htm"

   if event == "class_change_51":
     if ClassId in [ClassId.orcShaman]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_PILGRIM_ID) == 0 or st.getQuestItemsCount(MARK_OF_GLORY_ID) == 0 or st.getQuestItemsCount(MARK_OF_LORD_ID) == 0:
            htmltext = "30513-24.htm"
          else:
            htmltext = "30513-25.htm"
        elif Level >= 40:
          if st.getQuestItemsCount(MARK_OF_PILGRIM_ID) == 0 or st.getQuestItemsCount(MARK_OF_GLORY_ID) == 0 or st.getQuestItemsCount(MARK_OF_LORD_ID) == 0:
            htmltext = "30513-26.htm"
          else:
            st.takeItems(MARK_OF_PILGRIM_ID,1)
            st.takeItems(MARK_OF_GLORY_ID,1)
            st.takeItems(MARK_OF_LORD_ID,1)
            st.getPlayer().setClassId(51)
            st.getPlayer().setBaseClass(51)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30513-27.htm"

   if event == "class_change_52":
     if ClassId in [ClassId.orcShaman]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_PILGRIM_ID) == 0 or st.getQuestItemsCount(MARK_OF_GLORY_ID) == 0 or st.getQuestItemsCount(MARK_OF_WARSPIRIT_ID) == 0:
            htmltext = "30513-28.htm"
          else:
            htmltext = "30513-29.htm"
        elif Level >= 40:
          if st.getQuestItemsCount(MARK_OF_PILGRIM_ID) == 0 or st.getQuestItemsCount(MARK_OF_GLORY_ID) == 0 or st.getQuestItemsCount(MARK_OF_WARSPIRIT_ID) == 0:
            htmltext = "30513-30.htm"
          else:
            st.takeItems(MARK_OF_PILGRIM_ID,1)
            st.takeItems(MARK_OF_GLORY_ID,1)
            st.takeItems(MARK_OF_WARSPIRIT_ID,1)
            st.getPlayer().setClassId(52)
            st.getPlayer().setBaseClass(52)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30513-31.htm"

   st.setState(COMPLETED)
   st.exitQuest(1)
   return htmltext

 def onTalk (Self,npc,player):
   st = player.getQuestState(qn)
   npcId = npc.getNpcId()

   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()

   # orcs got accepted
   if Race in [Race.orc]:
     if ClassId in [ClassId.orcMonk]:
       htmltext = "30513-01.htm"
       st.setState(STARTED)
       return htmltext
     elif ClassId in [ClassId.orcRaider]:
       htmltext = "30513-05.htm"
       st.setState(STARTED)
       return htmltext
     elif ClassId in [ClassId.orcShaman]:
       htmltext = "30513-09.htm"
       st.setState(STARTED)
       return htmltext
     elif ClassId in [ClassId.destroyer, ClassId.tyrant, ClassId.overlord, ClassId.warcryer]:
       htmltext = "30513-32.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext
     elif ClassId in [ClassId.orcFighter, ClassId.orcMage]:
       htmltext = "30513-33.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext
     else:
       htmltext = "30513-34.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext

   # All other Races must be out
   else:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "30513-34.htm"

QUEST   = Quest(30513,qn,"village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(30513)
QUEST.addStartNpc(30681)
QUEST.addStartNpc(30704)
QUEST.addStartNpc(30865)
QUEST.addStartNpc(30913)
QUEST.addStartNpc(31288)
QUEST.addStartNpc(31326)
QUEST.addStartNpc(31977)

QUEST.addTalkId(30513)
QUEST.addTalkId(30681)
QUEST.addTalkId(30704)
QUEST.addTalkId(30865)
QUEST.addTalkId(30913)
QUEST.addTalkId(31288)
QUEST.addTalkId(31326)
QUEST.addTalkId(31977)
