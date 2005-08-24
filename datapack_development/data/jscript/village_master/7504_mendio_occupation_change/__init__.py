#
# Created by DraX on 2005.08.13
#

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

PASS_FINAL_ID           = 1635
HEAD_BLACKSMITH_MENDIO  = 7504

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = event
   Race     = st.getPlayer().getRace()
   ClassId  = st.getPlayer().getClassId()
   Level    = st.getPlayer().getLevel()

   if event == "7504-01.htm":
     st.exitQuest(True)
     return "7504-01.htm"

   if event == "7504-02.htm":
     st.exitQuest(True)
     return "7504-02.htm"

   if event == "7504-03.htm":
     st.exitQuest(True)
     return "7504-03.htm"

   if event == "7504-04.htm":
     st.exitQuest(True)
     return "7504-04.htm"

   if event == "class_change_56":
     if ClassId in [ClassId.dwarvenFighter]:
        if Level <= 19 and st.getQuestItemsCount(PASS_FINAL_ID) == 0:
          st.exitQuest(True)
          return "7504-05.htm"
        if Level <= 19 and st.getQuestItemsCount(PASS_FINAL_ID) >= 1:
          st.exitQuest(True)
          return "7504-06.htm"
        if Level >= 20 and st.getQuestItemsCount(PASS_FINAL_ID) == 0:
          st.exitQuest(True)
          return "7504-07.htm"
        if Level >= 20 and st.getQuestItemsCount(PASS_FINAL_ID) >= 1:
          st.takeItems(PASS_FINAL_ID,1)
          st.player.setClassId(56)
          st.player.broadcastUserInfo()
          st.playSound("ItemSound.quest_fanfare_2")
          st.exitQuest(True)
          return "7504-08.htm"

 def onTalk (Self,npcId,st):
   
   Race    = st.getPlayer().getRace()
   ClassId = st.getPlayer().getClassId()
   
   # Dwarf´s got accepted
   if npcId == HEAD_BLACKSMITH_MENDIO and Race in [Race.dwarf]:
     if ClassId in [ClassId.dwarvenFighter]:
       st.exitQuest(True)
       return "7504-01.htm"
     if ClassId in [ClassId.scavenger, ClassId.artisan]:
       st.exitQuest(True)
       return "7504-09.htm"
     if ClassId in [ClassId.bountyHunter, ClassId.warsmith]:
       st.exitQuest(True)
       return "7504-10.htm"

   # All other Races must be out
   if npcId == HEAD_BLACKSMITH_MENDIO and Race in [Race.elf, Race.darkelf, Race.orc, Race.human]:
     st.exitQuest(True)
     return "7504-11.htm"

QUEST   = Quest(7504,"7504_mendio_occupation_change","village_master")
CREATED = State('Start',QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7504)

STARTED.addTalkId(7504)