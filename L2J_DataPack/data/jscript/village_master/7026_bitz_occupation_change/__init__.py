#
# Created by DraX on 2005.08.08
#
# Updated by ElgarL on 28.09.2005
#

print "importing village master data: Talking Island Village ...done"

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GRAND_MASTER_BITZ = 7026

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st):
   
   htmltext = "No Quest"
   
   if event == "7026-01.htm":
     htmltext = event

   if event == "7026-02.htm":
     htmltext = event

   if event == "7026-03.htm":
     htmltext = event

   if event == "7026-04.htm":
     htmltext = event

   if event == "7026-05.htm":
     htmltext = event

   if event == "7026-06.htm":
     htmltext = event

   if event == "7026-07.htm":
     htmltext = event

   return htmltext

 
 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   
   Race    = st.getPlayer().getRace()
   pcId = st.getPlayer().getClassId().getId()
   
   # Humans got accepted
   if npcId == GRAND_MASTER_BITZ and Race in [Race.human]:
#fighter
     if pcId == 0x00:
       htmltext = "7026-01.htm"
#warrior, knight, rogue
     if pcId == 0x01 or pcId == 0x04 or pcId == 0x07:
       htmltext = "7026-08.htm"
#warlord, paladin, treasureHunter
     if pcId == 0x03 or pcId == 0x05 or pcId == 0x08:
       htmltext = "7026-09.htm"
#gladiator, darkAvenger, hawkeye
     if pcId == 0x02 or pcId == 0x06 or pcId == 0x09:
       htmltext = "7026-09.htm"
#mage, wizard, cleric]:
     if pcId == 0x0a or pcId == 0x0b or pcId == 0x0f:
       htmltext = "7026-10.htm"
#sorceror, necromancer, warlock, bishop, prophet
     if pcId == 0x0c or pcId == 0x0d or pcId == 0x0e or pcId == 0x10 or pcId == 0x11:
       htmltext = "7026-10.htm"
       
     st.setState(STARTED)
     return htmltext

   # All other Races must be out
   if npcId == GRAND_MASTER_BITZ and Race in [Race.dwarf, Race.darkelf, Race.elf, Race.orc]:
     st.setState(COMPLETED)
     st.exitQuest(1)
     return "7026-10.htm"

QUEST     = Quest(7026,"7026_bitz_occupation_change","village_master")
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7026)

STARTED.addTalkId(7026)
