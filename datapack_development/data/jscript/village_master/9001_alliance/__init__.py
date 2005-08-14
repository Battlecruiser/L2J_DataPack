#
# Created by DraX on 2005.08.12
#

print "importing village master data: 9001_alliance"

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GRAND_MASTER_BITZ       = 7026
HIGH_PRIEST_BIOTIN      = 7031
HIERARCH_ASTERIOS       = 7154
GRAND_MASTER_RAINS      = 7288
HIGH_PRIEST_RAYMOND     = 7289
GRAND_MASTER_TOBIAS     = 7297
TETRARCH_THIFIELL       = 7358
WAREHOUSE_CHIEF_RIKADIO = 7503
HEAD_BLACKSMITH_MENDIO  = 7504
HIGH_PREFECT_DRIKUS     = 7505
WAREHOUSE_CHIEF_REED    = 7520
HEAD_BLACKSMITH_BRONK   = 7525
KAKAI_LORD_OF_FLAME     = 7565


class Quest (JQuest) :

 def onEvent (self,event,st):
   
   htmltext = event

   if event == "9001-01.htm":
     st.exitQuest(True)
     return "9001-01.htm"

   if event == "9001-02.htm":
     st.exitQuest(True)
     return "9001-02.htm"

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onTalk (Self,npcId,st):
   
   if npcId == GRAND_MASTER_TOBIAS or GRAND_MASTER_BITZ or HIGH_PRIEST_BIOTIN or HIERARCH_ASTERIOS or TETRARCH_THIFIELL or WAREHOUSE_CHIEF_REED or HEAD_BLACKSMITH_BRONK or KAKAI_LORD_OF_FLAME or GRAND_MASTER_RAINS or HIGH_PRIEST_RAYMOND or WAREHOUSE_CHIEF_RIKADIO or HEAD_BLACKSMITH_MENDIO or HIGH_PREFECT_DRIKUS:
     st.exitQuest(True)
     return "9001-01.htm"

QUEST   = Quest(9001,"9001_alliance","village_master")
CREATED = State('Start',QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7026)
QUEST.addStartNpc(7031)
QUEST.addStartNpc(7154)
QUEST.addStartNpc(7288)
QUEST.addStartNpc(7289)
QUEST.addStartNpc(7297)
QUEST.addStartNpc(7358)
QUEST.addStartNpc(7503)
QUEST.addStartNpc(7504)
QUEST.addStartNpc(7505)
QUEST.addStartNpc(7520)
QUEST.addStartNpc(7525)
QUEST.addStartNpc(7565)

STARTED.addTalkId(7026)
STARTED.addTalkId(7031)
STARTED.addTalkId(7154)
STARTED.addTalkId(7288)
STARTED.addTalkId(7289)
STARTED.addTalkId(7297)
STARTED.addTalkId(7358)
STARTED.addTalkId(7503)
STARTED.addTalkId(7504)
STARTED.addTalkId(7505)
STARTED.addTalkId(7520)
STARTED.addTalkId(7525)
STARTED.addTalkId(7565)