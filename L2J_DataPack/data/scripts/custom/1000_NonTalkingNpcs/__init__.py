# Script is used for preventing displaying html for npcs that dont have html on retail
# Visit http://forum.l2jdp.com for more details.
import sys
from net.sf.l2j.gameserver.model.quest import Quest as JQuest
from net.sf.l2j.gameserver.network.serverpackets      import ActionFailed

NPCs = [31557,31606,31671,31672,31673,31674,32026,32030,32031,32032]

class Quest (JQuest) :
    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)

    def onFirstTalk (self,npc,player):
        player.sendPacket(ActionFailed.STATIC_PACKET)
        return None

QUEST      = Quest(-1,".","custom")
for i in NPCs :
  QUEST.addFirstTalkId(i)
