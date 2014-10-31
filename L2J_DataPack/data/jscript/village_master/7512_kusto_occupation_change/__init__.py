#
# Created by DraX on 2005.08.22 modified by Ariakas on 2005.09.19
#

print "importing village master data: Hunters Village        ...done"

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_MAESTRO_ID      = 2867
MARK_OF_GUILDSMAN_ID    = 3119
MARK_OF_PROSPERITY_ID   = 3238
HEAD_BLACKSMITH_KUSTO   = 7512
HEAD_BLACKSMITH_FLUTTER = 7677
HEAD_BLACKSMITH_VERGARA = 7687
HEAD_BLACKSMITH_FERRIS  = 7847
HEAD_BLACKSMITH_ROMAN   = 7897

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):

   htmltext = "No Quest"

   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "7512-01.htm":
     return "7512-01.htm"

   if event == "7512-02.htm":
     return "7512-02.htm"

   if event == "7512-03.htm":
     return "7512-03.htm"

   if event == "7512-04.htm":
     return "7512-04.htm"

   if event == "class_change_57":
     if ClassId in [ClassId.artisan]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_MAESTRO_ID) == 0 or st.getQuestItemsCount(MARK_OF_GUILDSMAN_ID) == 0 or st.getQuestItemsCount(MARK_OF_PROSPERITY_ID) == 0:
            htmltext = "7512-05.htm"
          else:
            htmltext = "7512-06.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_MAESTRO_ID) == 0 or st.getQuestItemsCount(MARK_OF_GUILDSMAN_ID) == 0 or st.getQuestItemsCount(MARK_OF_PROSPERITY_ID) == 0:
            htmltext = "7512-07.htm"
          else:
            st.takeItems(MARK_OF_MAESTRO_ID,1)
            st.takeItems(MARK_OF_GUILDSMAN_ID,1)
            st.takeItems(MARK_OF_PROSPERITY_ID,1)
            st.player.setClassId(57)
            st.player.setBaseClass(57)
            st.player.broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "7512-08.htm"

   st.setState(COMPLETED)
   st.exitQuest(1)
   return htmltext

 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()

   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()

   # Dwarfs got accepted
   if npcId == HEAD_BLACKSMITH_KUSTO or HEAD_BLACKSMITH_VERGARA or HEAD_BLACKSMITH_FLUTTER or HEAD_BLACKSMITH_FERRIS or HEAD_BLACKSMITH_ROMAN and Race in [Race.dwarf]:
     if ClassId in [ClassId.artisan]:
       htmltext = "7512-01.htm"
       st.setState(STARTED)
       return htmltext
     elif ClassId in [ClassId.dwarvenFighter]:
       htmltext = "7512-09.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext
     elif ClassId in [ClassId.warsmith, ClassId.bountyHunter]:
       htmltext = "7512-10.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext
     else:
       htmltext = "7512-11.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext

   # All other Races must be out
   else:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "7512-11.htm"

QUEST   = Quest(7512,"7512_kusto_occupation_change","village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7512)
QUEST.addStartNpc(7677)
QUEST.addStartNpc(7687)
QUEST.addStartNpc(7847)
QUEST.addStartNpc(7897)

STARTED.addTalkId(7512)
STARTED.addTalkId(7677)
STARTED.addTalkId(7687)
STARTED.addTalkId(7847)
STARTED.addTalkId(7897)
