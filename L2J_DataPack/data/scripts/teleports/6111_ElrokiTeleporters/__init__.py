# contributed by kerberos_20 to the Official L2J Datapack Project.
# Visit http://www.l2jdp.com/forum/ for more details.
import sys
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest

qn = "6111_ElrokiTeleporters"

class Quest (JQuest) :

 def __init__(self,id,name,descr): 
   JQuest.__init__(self,id,name,descr)
 
 def onTalk (self,npc,player):
    npcId = npc.getNpcId()
    if npcId == 32111 :
        if player.isInCombat() :
           return "32111-no.htm"
        player.teleToLocation(4990,-1879,-3178)
    if npcId == 32112 :
        player.teleToLocation(7557,-5513,-3221)
    return

QUEST       = Quest(-1, qn, "Teleports")

QUEST.addStartNpc(32111)
QUEST.addTalkId(32111)
QUEST.addStartNpc(32112)
QUEST.addTalkId(32112)