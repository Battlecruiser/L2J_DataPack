#
# Created by DraX on 2005.08.13 modified by Ariakas on 2005.09.19
#

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
qn = "30504_mendio_occupation_change"
PASS_FINAL_ID           = 1635
HEAD_BLACKSMITH_MENDIO  = 30504

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):

   htmltext = "No Quest"

   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "30504-01.htm":
     return "30504-01.htm"

   if event == "30504-02.htm":
     return "30504-02.htm"

   if event == "30504-03.htm":
     return "30504-03.htm"

   if event == "30504-04.htm":
     return "30504-04.htm"

   if event == "class_change_56":
     if ClassId in [ClassId.dwarvenFighter]:
        if Level <= 19 and st.getQuestItemsCount(PASS_FINAL_ID) == 0:
          htmltext = "30504-05.htm"
        if Level <= 19 and st.getQuestItemsCount(PASS_FINAL_ID) >= 1:
          htmltext = "30504-06.htm"
        if Level >= 20 and st.getQuestItemsCount(PASS_FINAL_ID) == 0:
          htmltext = "30504-07.htm"
        if Level >= 20 and st.getQuestItemsCount(PASS_FINAL_ID) >= 1:
          st.takeItems(PASS_FINAL_ID,1)
          st.getPlayer().setClassId(56)
          st.getPlayer().setBaseClass(56)
          st.getPlayer().broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          htmltext = "30504-08.htm"

   st.setState(COMPLETED)
   st.exitQuest(1)
   return htmltext

 def onTalk (Self,npc,player):
   st = player.getQuestState(qn)
   npcId = npc.getNpcId()
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # Dwarfs got accepted
   if npcId == HEAD_BLACKSMITH_MENDIO and Race in [Race.dwarf]:
     if ClassId in [ClassId.dwarvenFighter]:
       htmltext = "30504-01.htm"
       st.setState(STARTED)
       return htmltext
     if ClassId in [ClassId.scavenger, ClassId.artisan]:
       htmltext = "30504-09.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext
     if ClassId in [ClassId.bountyHunter, ClassId.warsmith]:
       htmltext = "30504-10.htm"
       st.setState(COMPLETED)
       st.exitQuest(1)
       return htmltext

   # All other Races must be out
   if npcId == HEAD_BLACKSMITH_MENDIO and Race in [Race.elf, Race.darkelf, Race.orc, Race.human]:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "30504-11.htm"

QUEST   = Quest(30504,qn,"village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(30504)

QUEST.addTalkId(30504)
