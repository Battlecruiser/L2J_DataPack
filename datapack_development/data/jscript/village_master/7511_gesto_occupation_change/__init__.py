#
# Created by DraX on 2005.08.21 modified by Ariakas on 2005.09.19
#

print "importing village master data: Town of Oren           ...done"

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_SEARCHER_ID     = 2809
MARK_OF_GUILDSMAN_ID    = 3119
MARK_OF_PROSPERITY_ID   = 3238
WAREHOUSE_CHIEF_GESTO   = 7511
WAREHOUSE_CHIEF_CROOP   = 7676
WAREHOUSE_CHIEF_BRAXT   = 7685
WAREHOUSE_CHIEF_KLUMP   = 7845
WAREHOUSE_CHIEF_NATOOLS = 7894

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):

   htmltext = "No Quest"

   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "7511-01.htm":
     return "7511-01.htm"

   if event == "7511-02.htm":
     return "7511-02.htm"

   if event == "7511-03.htm":
     return "7511-03.htm"

   if event == "7511-04.htm":
     return "7511-04.htm"

   if event == "class_change_55":
     if ClassId in [ClassId.scavenger]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_SEARCHER_ID) == 0 or st.getQuestItemsCount(MARK_OF_GUILDSMAN_ID) == 0 or st.getQuestItemsCount(MARK_OF_PROSPERITY_ID) == 0:
            htmltext = "7511-05.htm"
          else:
            htmltext = "7511-06.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_SEARCHER_ID) == 0 or st.getQuestItemsCount(MARK_OF_GUILDSMAN_ID) == 0 or st.getQuestItemsCount(MARK_OF_PROSPERITY_ID) == 0:
            htmltext = "7511-07.htm"
          else:
            st.takeItems(MARK_OF_SEARCHER_ID,1)
            st.takeItems(MARK_OF_GUILDSMAN_ID,1)
            st.takeItems(MARK_OF_PROSPERITY_ID,1)
            st.player.setClassId(55)
            st.player.setBaseClass(55)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7511-08.htm"

   st.setState(COMPLETED)
   st.exitQuest(1)
   return htmltext

 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()

   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()

   # Dwarfs got accepted
   if npcId == WAREHOUSE_CHIEF_GESTO or WAREHOUSE_CHIEF_BRAXT or WAREHOUSE_CHIEF_CROOP or WAREHOUSE_CHIEF_KLUMP or WAREHOUSE_CHIEF_NATOOLS and Race in [Race.dwarf]:
     if ClassId in [ClassId.scavenger]:
       htmltext = "7511-01.htm"
       st.setState(STARTED)
       return htmltext
     elif ClassId in [ClassId.dwarvenFighter]:
       htmltext = "7511-09.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext
     elif ClassId in [ClassId.bountyHunter, ClassId.warsmith]:
       htmltext = "7511-10.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext
     else:
       htmltext = "7511-11.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext

   # All other Races must be out
   else:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "7511-11.htm"

QUEST   = Quest(7511,"7511_gesto_occupation_change","village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7511)
QUEST.addStartNpc(7676)
QUEST.addStartNpc(7685)
QUEST.addStartNpc(7845)
QUEST.addStartNpc(7894)

STARTED.addTalkId(7511)
STARTED.addTalkId(7676)
STARTED.addTalkId(7685)
STARTED.addTalkId(7845)
STARTED.addTalkId(7894)
