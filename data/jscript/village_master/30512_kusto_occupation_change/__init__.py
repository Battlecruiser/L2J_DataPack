#
# Created by DraX on 2005.08.22 modified by Ariakas on 2005.09.19
#

print "importing village master data: Hunters Village        ...done"

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
qn = "30512_kusto_occupation_change"
MARK_OF_MAESTRO_ID      = 2867
MARK_OF_GUILDSMAN_ID    = 3119
MARK_OF_PROSPERITY_ID   = 3238
HEAD_BLACKSMITH_KUSTO   = 30512
HEAD_BLACKSMITH_FLUTTER = 30677
HEAD_BLACKSMITH_VERGARA = 30687
HEAD_BLACKSMITH_FERRIS  = 30847
HEAD_BLACKSMITH_ROMAN   = 30897
HEAD_BLACKSMITH_NOEL    = 31272
HEAD_BLACKSMITH_LOMBERT = 31317

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):

   htmltext = "No Quest"

   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "30512-01.htm":
     return "30512-01.htm"

   if event == "30512-02.htm":
     return "30512-02.htm"

   if event == "30512-03.htm":
     return "30512-03.htm"

   if event == "30512-04.htm":
     return "30512-04.htm"

   if event == "class_change_57":
     if ClassId in [ClassId.artisan]:
        if Level <= 39:
          if st.getQuestItemsCount(MARK_OF_MAESTRO_ID) == 0 or st.getQuestItemsCount(MARK_OF_GUILDSMAN_ID) == 0 or st.getQuestItemsCount(MARK_OF_PROSPERITY_ID) == 0:
            htmltext = "30512-05.htm"
          else:
            htmltext = "30512-06.htm"
        else:
          if st.getQuestItemsCount(MARK_OF_MAESTRO_ID) == 0 or st.getQuestItemsCount(MARK_OF_GUILDSMAN_ID) == 0 or st.getQuestItemsCount(MARK_OF_PROSPERITY_ID) == 0:
            htmltext = "30512-07.htm"
          else:
            st.takeItems(MARK_OF_MAESTRO_ID,1)
            st.takeItems(MARK_OF_GUILDSMAN_ID,1)
            st.takeItems(MARK_OF_PROSPERITY_ID,1)
            st.getPlayer().setClassId(57)
            st.getPlayer().setBaseClass(57)
            st.getPlayer().broadcastUserInfo()
            st.playSound("ItemSound.quest_fanfare_2")
            htmltext = "30512-08.htm"

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
     if ClassId in [ClassId.artisan]:
       htmltext = "30512-01.htm"
       st.setState(STARTED)
       return htmltext
     elif ClassId in [ClassId.dwarvenFighter]:
       htmltext = "30512-09.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext
     elif ClassId in [ClassId.warsmith, ClassId.bountyHunter]:
       htmltext = "30512-10.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext
     else:
       htmltext = "30512-11.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext

   # All other Races must be out
   else:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "30512-11.htm"

QUEST   = Quest(30512,qn,"village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(30512)
QUEST.addStartNpc(30677)
QUEST.addStartNpc(30687)
QUEST.addStartNpc(30847)
QUEST.addStartNpc(30897)
QUEST.addStartNpc(31272)
QUEST.addStartNpc(31317)
QUEST.addStartNpc(31961)

QUEST.addTalkId(30512)
QUEST.addTalkId(30677)
QUEST.addTalkId(30687)
QUEST.addTalkId(30847)
QUEST.addTalkId(30897)
QUEST.addTalkId(31272)
QUEST.addTalkId(31317)
QUEST.addTalkId(31961)
