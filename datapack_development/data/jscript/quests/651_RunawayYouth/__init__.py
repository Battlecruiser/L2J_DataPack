# Made by Polo & DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
from net.sf.l2j.gameserver.model.actor.instance import L2NpcInstance
from net.sf.l2j.gameserver.serverpackets import MagicSkillUser
from net.sf.l2j.gameserver.datatables import SpawnTable

qn = "651_RunawayYouth"
#Npc
IVAN = 32014
BATIDAE = 31989

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
    if event == "32014-04.htm" :
      if st.getQuestItemsCount(SOE):
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.takeItems(SOE,1)
        htmltext = "32014-03.htm"
        npc=findNpc(IVAN,player)
        npc.broadcastPacket(MagicSkillUser(npc,npc,2013,1,20000,0))
        st.startQuestTimer("ivan_timer",20000)
    elif event == "32014-04a.htm" :
        st.exitQuest(1)
        st.playSound("ItemSound.quest_giveup")
    elif event == "ivan_timer":
        npc=findNpc(IVAN,player)
        npc.deleteMe()
        htmltext=None
    return htmltext

 def onTalk (Self,npc,player):
   st = player.getQuestState(qn)
   htmltext = "<html><head><body>I have nothing to say you!</body></html>"
   if not st : return htmltext
   npcId = npc.getNpcId()
   id = st.getState()
   cond=st.getInt("cond")
   if npcId == IVAN and id == CREATED:
       if st.getPlayer().getLevel()>=26 :
           htmltext = "32014-02.htm"
       else:
           htmltext = "32014-01.htm"
           st.exitQuest(1)
   elif npcId == BATIDAE and st.getInt("cond")==1 :
       htmltext = "31989-01.htm"
       st.giveItems(57,2883)
       st.playSound("ItemSound.quest_finish")
       st.exitQuest(1)
   return htmltext


QUEST       = Quest(651,qn,"Runaway Youth")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(IVAN)

QUEST.addTalkId(IVAN)
QUEST.addTalkId(BATIDAE)

print "importing quests: 651: Runaway Youth"
