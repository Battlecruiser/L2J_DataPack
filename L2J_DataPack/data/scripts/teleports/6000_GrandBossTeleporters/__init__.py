#Made by Emperorc
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
from net.sf.l2j.gameserver.datatables import SpawnTable
from net.sf.l2j.util import Rnd
from net.sf.l2j.gameserver.instancemanager import QuestManager
from net.sf.l2j.gameserver.instancemanager import GrandBossManager

qn = "6000_GrandBossTeleporters"

NPCs = [
    13001, #Heart of Warding : Teleport into Lair of Antharas
    31859, #Teleportation Cubic : Teleport out of Lair of Antharas
    ]

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     self.antharasAI = QuestManager.getInstance().getQuest("antharas")
     JQuest.__init__(self,id,name,descr)
 
 def onTalk (self,npc,player):
    npcId = npc.getNpcId()
    htmltext = ""
    if npcId == 13001 : #heart of warding
        htmltext = "13001-01.htm"
        if self.antharasAI :
            status = GrandBossManager.getInstance().getBossStatus(29019)
            if status == 0 or status == 1 : #If entrance to see Antharas is unlocked (he is Dormant or Waiting)
                st = player.getQuestState(qn)
                if st.getQuestItemsCount(3865) > 0 :
                    st.takeItems(3865,1)
                    self.antharasAI.zone.allowPlayerEntry(player,30)
                    x = 179700 + Rnd.get(700)
                    y = 113800 + Rnd.get(2100)
                    player.teleToLocation(x,y,-7709)
                    if status == 0 :
                        antharas = GrandBossManager.getInstance().getBoss(29019)
                        self.antharasAI.startQuestTimer("waiting",1800000, antharas, None)
                        GrandBossManager.getInstance().setBossStatus(29019,1)
                    return
                else :
                    htmltext = "13001-03.htm"
            elif status == 2 :
                htmltext = "13001-02.htm"
    elif npcId == 31859 : #antharas teleport cube
        x = 79800 + Rnd.get(600)
        y = 151200 + Rnd.get(1100)
        player.teleToLocation(x,y,-3534)
        return
    return htmltext

QUEST       = Quest(6000, qn, "Teleports")

for npcid in NPCs :
    QUEST.addStartNpc(npcid)
    QUEST.addTalkId(npcid)