# version 0.1
# by Fulminus

import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
from net.sf.l2j.gameserver.serverpackets import SocialAction
from net.sf.l2j.gameserver.serverpackets import Earthquake
from net.sf.l2j.gameserver.serverpackets import PlaySound
from net.sf.l2j.gameserver.ai import CtrlIntention

# Boss: Baium
class baium (JQuest):

  def __init__(self,id,name,descr):
    JQuest.__init__(self,id,name,descr)
    self.playersInside = []

  def onAdvEvent (self,event,npc,player):
    objId=0
    if event == "baium_timer1" and npc:
      if npc.getNpcId() == 29020 :
        npc.broadcastPacket(SocialAction(npc.getObjectId(),1))
        npc.broadcastPacket(Earthquake(npc.getX(), npc.getY(), npc.getZ(),40,5))
    return

  def onTalk (self,npc,player):
    st = player.getQuestState("baium")
    npcId = npc.getNpcId()
    if npcId == 29025 :
      if st.getInt("ok"):
        if not npc.isBusy():
           npc.setBusy(True)
           npc.setBusyMessage("Attending another player's request")
           npc.deleteMe()
           baium = st.addSpawn(29020,npc)
           baium.broadcastPacket(SocialAction(baium.getObjectId(),2))
           self.startQuestTimer("baium_timer1",15000, baium, None)
           for playerSt in self.playersInside :
             if playerSt :
               playerSt.unset("ok")
      else:
        st.exitQuest(1)
        return "Conditions are not right to wake up Baium"
    elif npcId == 31862 :
      if player.isFlying() :
        print "Player "+player.getName()+" attempted to enter Baium's layer while flying!"
        return '<html><body>Angelic Vortex:<br>You may not enter while flying a wyvern</body></html>'
      if st.getQuestItemsCount(4295) : # bloody fabric
        st.takeItems(4295,1)
        player.teleToLocation(113100,14500,10077)
        st.set("ok","1")
        self.playersInside.append(st)
      else :
        return '<html><body>Angelic Vortex:<br>You do not have enough items</body></html>'
    return

  def onKill(self,npc,player):
    objId=npc.getObjectId()
    npc.broadcastPacket(PlaySound(1, "BS02_D", 1, objId, npc.getX(), npc.getY(), npc.getZ()))
    self.addSpawn(29055,115203,16620,10078,0,False,900000)

# Quest class and state definition
QUEST       = baium(-1, "baium", "ai")
CREATED     = State('Start', QUEST)

# Quest initialization
QUEST.setInitialState(CREATED)
# Quest NPC starter initialization
QUEST.addStartNpc(29025)
QUEST.addStartNpc(31862)
QUEST.addTalkId(29025)
QUEST.addTalkId(31862)
QUEST.addKillId(29020)

print "AI: individuals: Baium...loaded!"
