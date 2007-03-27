# version 0.1
# by Fulminus

import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
qn = "12535_WakeBaium"
# Main Quest Code
class Quest (JQuest):

  def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

  def onEvent (self,event,st):
    return

  def onTalk (self,npc,player):
    st = player.getQuestState(qn)
    npcId = npc.getNpcId()
    if npcId == 29025 :
      if st.getInt("ok"):
        if not npc.isBusy():
           npc.setBusy(True)
           npc.setBusyMessage("Attending another player's request")
           st.getPcSpawn().addSpawn(29020,npc.getX(),npc.getY(),npc.getZ())
           npc.reduceCurrentHp(9999999, npc)
        st.exitQuest(1)
      else:
        st.exitQuest(1)
        return "Conditions are not right to wake up Baium"
    elif npcId == 31862 :
      if st.getQuestItemsCount(4295) :   # bloody fabric
        st.takeItems(4295,1)
        st.getPlayer().teleToLocation(113100,14500,10077)
        st.set("ok","1")
      else :
        return '<html><head><body>Angelic Vortex:<br>You do not have enough items</body></html>'      
    return

# Quest class and state definition
QUEST       = Quest(12535, "12535_WakeBaium", "Wake Baium")
CREATED     = State('Start',       QUEST)
COMPLETED   = State('Completed',   QUEST)

# Quest initialization
QUEST.setInitialState(CREATED)
# Quest NPC starter initialization
QUEST.addStartNpc(29025)
QUEST.addStartNpc(31862)
QUEST.addTalkId(29025)
QUEST.addTalkId(31862)

print "importing quests: 12535: Wake Up Baium"
