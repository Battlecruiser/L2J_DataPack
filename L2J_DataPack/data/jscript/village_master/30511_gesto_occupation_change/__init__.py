#
# Created by DraX on 2005.08.21 modified by Ariakas on 2005.09.19
#

print "importing village master data: Town of Oren           ...done"

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
qn = "30511_gesto_occupation_change"
MARK_OF_SEARCHER_ID     = 2809
MARK_OF_GUILDSMAN_ID    = 3119
MARK_OF_PROSPERITY_ID   = 3238
WAREHOUSE_CHIEF_GESTO   = 30511
WAREHOUSE_CHIEF_CROOP   = 30676
WAREHOUSE_CHIEF_BRAXT   = 30685
WAREHOUSE_CHIEF_KLUMP   = 30845
WAREHOUSE_CHIEF_NATOOLS = 30894
WAREHOUSE_CHIEF_MONA   = 31269
WAREHOUSE_CHIEF_DONALD   = 31314

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):

   htmltext = "No Quest"

   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "30511-01.htm":
     return "30511-01.htm"

   if event == "30511-02.htm":
     return "30511-02.htm"

   if event == "30511-03.htm":
     return "30511-03.htm"

   if event == "30511-04.htm":
     return "30511-04.htm"

   if event == "class_change_55":
     if ClassId in [ClassId.scavenger]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_SEARCHER_ID) == 0 or st.getQuestItemsCount(MARK_OF_GUILDSMAN_ID) == 0 or st.getQuestItemsCount(MARK_OF_PROSPERITY_ID) == 0:
            htmltext = "30511-05.htm"
          else:
            htmltext = "30511-06.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_SEARCHER_ID) == 0 or st.getQuestItemsCount(MARK_OF_GUILDSMAN_ID) == 0 or st.getQuestItemsCount(MARK_OF_PROSPERITY_ID) == 0:
            htmltext = "30511-07.htm"
          else:
            st.takeItems(MARK_OF_SEARCHER_ID,1)
            st.takeItems(MARK_OF_GUILDSMAN_ID,1)
            st.takeItems(MARK_OF_PROSPERITY_ID,1)
            st.getPlayer().setClassId(55)
            st.getPlayer().setBaseClass(55)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30511-08.htm"

   st.setState(COMPLETED)
   st.exitQuest(1)
   return htmltext

 def onTalk (Self,npc,player):
   st = player.getQuestState(qn)
   npcId = npc.getNpcId()

   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()

   # Dwarfs got accepted
   if Race in [Race.dwarf]:
     if ClassId in [ClassId.scavenger]:
       htmltext = "30511-01.htm"
       st.setState(STARTED)
       return htmltext
     elif ClassId in [ClassId.dwarvenFighter]:
       htmltext = "30511-09.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext
     elif ClassId in [ClassId.bountyHunter, ClassId.warsmith]:
       htmltext = "30511-10.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext
     else:
       htmltext = "30511-11.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext

   # All other Races must be out
   else:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "30511-11.htm"

QUEST   = Quest(30511,qn,"village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(30511)
QUEST.addStartNpc(30676)
QUEST.addStartNpc(30685)
QUEST.addStartNpc(30845)
QUEST.addStartNpc(30894)
QUEST.addStartNpc(31269)
QUEST.addStartNpc(31314)
QUEST.addStartNpc(31958)

QUEST.addTalkId(30511)
QUEST.addTalkId(30676)
QUEST.addTalkId(30685)
QUEST.addTalkId(30845)
QUEST.addTalkId(30894)
QUEST.addTalkId(31269)
QUEST.addTalkId(31314)
QUEST.addTalkId(31958)
