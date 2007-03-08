# Script for Pagan Temple Teleporters
# Needed for Quests 636 and 637
# v1.1 Done by BiTi

import sys
from net.sf.l2j.gameserver.model.actor.instance import L2PcInstance
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

NPCS=[32034,32036,32039,32040]

# Main Quest Code
class Quest (JQuest):

  def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

  def onTalk (self,npc,st):
    npcId = npc.getNpcId()
    htmltext = ""
    if not st.getQuestItemsCount(8067) or st.getPlayer().getLevel() < 73 :
       htmltext = '<html><head>Teleport available only for characters with Pagans Mark and level 73 or above.</body></html>'
    else:
       if npcId == 32034 :
          st.getPlayer().teleToLocation(-16324,-37147,-10724)
       elif npcId == 32036 :
          st.getPlayer().teleToLocation(-16324,-44638,-10724)
       elif not st.getQuestItemsCount(8064) :
          htmltext = '<html><head>Teleport available only for characters with Pagans Mark or Visitors Mark and level 73 or above.</body></html>'
       else :
          if npcId == 32039 :
             st.getPlayer().teleToLocation(-12241,-35884,-10856)
          elif npcId == 32040 :
             st.getPlayer().teleToLocation(36640,-51218,718)
    st.exitQuest(1)
    return htmltext

# Quest class and state definition
QUEST       = Quest(1630, "1630_PaganTeleporters", "Teleporters")
CREATED     = State('Start', QUEST)

# Quest initialization
QUEST.setInitialState(CREATED)
# Quest NPC starter initialization
for npc in NPCS :
    QUEST.addStartNpc(npc)
    CREATED.addTalkId(npc)

print "importing teleport data: 1630_PaganTeleporters"
