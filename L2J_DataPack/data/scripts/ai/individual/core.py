import sys
from net.sf.l2j.util import Rnd
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
from net.sf.l2j.gameserver.serverpackets import NpcSay
from net.sf.l2j.gameserver.serverpackets import PlaySound

# Boss: Core
class core(JQuest) :

    # init function.  Add in here variables that you'd like to be inherited by subclasses (if any)
    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)
        self.Core = 29006
        self.Minions = [29007,29008,29011]
        self.FirstAttacked = False

    def onAttack (self,npc,player,damage,isPet):
        objId=npc.getObjectId()
        if self.FirstAttacked:
           if Rnd.get(100) : return
           npc.broadcastPacket(NpcSay(objId,0,npc.getNpcId(),"Removing intruders."))
        else :
           self.FirstAttacked = True
           npc.broadcastPacket(NpcSay(objId,0,npc.getNpcId(),"A non-permitted target has been discovered."))
           npc.broadcastPacket(NpcSay(objId,0,npc.getNpcId(),"Starting intruder removal system."))
        return 

    def onKill(self,npc,player,isPet):
        npcId = npc.getNpcId()
        if npcId == self.Core:
            objId=npc.getObjectId()
            npc.broadcastPacket(PlaySound(1, "BS02_D", 1, objId, npc.getX(), npc.getY(), npc.getZ()))
            npc.broadcastPacket(NpcSay(objId,0,npcId,"A fatal error has occurred."))
            npc.broadcastPacket(NpcSay(objId,0,npcId,"System is being shut down..."))
            npc.broadcastPacket(NpcSay(objId,0,npcId,"......"))
            self.FirstAttacked = False
            self.addSpawn(31842,16502,110165,-6394,0,False,900000)
            self.addSpawn(31842,18948,110166,-6397,0,False,900000)
        elif self.FirstAttacked :
            self.addSpawn(npcId,17726,108915,-6480,npc.getHeading(),True,0)
        return 

# now call the constructor (starts up the ai)
QUEST		= core(-1,"core","ai")

QUEST.addKillId(QUEST.Core)
QUEST.addAttackId(QUEST.Core)

for minion in QUEST.Minions :
    QUEST.addKillId(minion)