import sys

from net.sf.l2j.gameserver.ai import CtrlIntention
from net.sf.l2j.gameserver.datatables import SkillTable
from net.sf.l2j.gameserver.model.quest import Quest as JQuest
from net.sf.l2j.gameserver.network.serverpackets import NpcSay
from net.sf.l2j.util import Rnd

class trees(JQuest) :

    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)

    def onAdvEvent (self,event,npc,pc) :
        if npc:
           npc.deleteMe()
        return

    def onKill (self,npc,player,isPet):
        npcId = npc.getNpcId()
        if npcId in range(27185,27189) :
           for x in xrange(20):
               newNpc = self.addSpawn(27189,npc)
               killer = player
               if isPet :
                   killer = player.getPet()
               newNpc.setRunning()
               newNpc.addDamageHate(killer,0,999)
               newNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, killer)
               self.startQuestTimer("despawn",300000, newNpc, None)
               if Rnd.get(2) :
                  skill = SkillTable.getInstance().getInfo(4243,1)
                  if skill != None and killer:
                     skill.getEffects(newNpc, killer)
        return 


QUEST		= trees(-2,"fairy trees","ai")

for i in range(27185,27189):
    QUEST.addKillId(i)
QUEST.addSpawnId(27189)
