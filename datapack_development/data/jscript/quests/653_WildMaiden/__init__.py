# Made by DrLecter, based on a Polo script and a DoomIta contribution
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
from net.sf.l2j.gameserver.model.actor.instance import L2NpcInstance
from net.sf.l2j.gameserver.serverpackets import MagicSkillUser
from net.sf.l2j.gameserver.datatables import SpawnTable

qn = "653_WildMaiden"
#Npc
SUKI = 32013
GALIBREDO = 30181

#Items
SOE = 736

def findNpc(npcId,player) :
    npclist=[]
    for spawn in SpawnTable.getInstance().getSpawnTable().values():
        if spawn.getNpcid() == npcId:
            instance=spawn.getLastSpawn()
            npclist.append(instance)
    for npc in npclist:
        if player.isInsideRadius(npc, 1600, 1, 0):
           return npc
    return instance


class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    player=st.getPlayer()
    if event == "32013-04.htm" :
      if st.getQuestItemsCount(SOE):
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.takeItems(SOE,1)
        htmltext = "32013-03.htm"
        npc=findNpc(SUKI,player)
        npc.broadcastPacket(MagicSkillUser(npc,npc,2013,1,20000,0))
        st.startQuestTimer("suki_timer",20000)
    elif event == "32013-04a.htm" :
        st.exitQuest(1)
        st.playSound("ItemSound.quest_giveup")
    elif event == "suki_timer":
        npc=findNpc(SUKI,player)
        npc.deleteMe()
        htmltext=None
    return htmltext

 def onTalk (self,npc,player):
   st = player.getQuestState(qn)
   htmltext = "<html><head><body>I have nothing to say you!</body></html>"
   if not st : return htmltext
   npcId = npc.getNpcId()
   id = st.getState()
   cond=st.getInt("cond")
   if npcId == SUKI and id == CREATED:
       if player.getLevel() >= 36 :
           htmltext = "32013-02.htm"
       else:
           htmltext = "32013-01.htm"
           st.exitQuest(1)
   elif npcId == GALIBREDO and st.getInt("cond")==1 :
       htmltext = "30181-01.htm"
       st.giveItems(57,2883)
       st.playSound("ItemSound.quest_finish")
       st.exitQuest(1)
   return htmltext


QUEST       = Quest(653,qn,"Wild Maiden")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(SUKI)

QUEST.addTalkId(SUKI)
QUEST.addTalkId(GALIBREDO)

print "importing quests: 653: Wild Maiden"
