# Contributed by t0rm3nt0r (tormentor2000@mail.ru) to the Official L2J Datapack Project.
# Visit http://www.l2jdp.com/forum/ for more details.

import sys
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest

#Complete - 100%.
qn = "284_MuertosFeather"

#NPC'S
TREVOR = 32166

#ITEM'S
FEATHER = 9748

#MOB'S
MOBS = range(22239,22241)+range(22242,22244)+range(22245,22247)

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
  
 def onAdvEvent (self,event,npc, player) :
     htmltext = event
     st = player.getQuestState(qn)
     if not st : return
     feather = st.getQuestItemsCount(FEATHER)
     if event == "32166-03.htm" :
       st.set("cond","1")
       st.setState(State.STARTED)
       st.playSound("ItemSound.quest_accept")
     elif event == "32166-06.htm" :
       st.giveItems(57,feather*45)
       st.takeItems(FEATHER,-1)
     elif event == "32166-08.htm" :
       st.takeItems(FEATHER,-1)
       st.exitQuest(1)
     return htmltext

 def onTalk (self,npc,player):
     npcId = npc.getNpcId()
     htmltext = Quest.getNoQuestMsg(player)
     st = player.getQuestState(qn)
     if not st : return htmltext
     id = st.getState()
     cond = st.getInt("cond")
     feather = st.getQuestItemsCount(FEATHER)
     if id == State.CREATED and npcId == TREVOR :
       if player.getLevel() < 11 :
         htmltext = "32166-02.htm"
         st.exitQuest(1)
       else :
         htmltext = "32166-01.htm"
     elif id == State.STARTED and npcId == TREVOR :
       if not feather :
         htmltext = "32166-04.htm"
       else :
         htmltext = "32166-05.htm"
     return htmltext
    
 def onKill(self,npc,player,isPet) :
     st = player.getQuestState(qn)
     if not st: return
     if st.getState() == State.STARTED :
       npcId = npc.getNpcId()
       chance = self.getRandom(100)
       if (npcId in MOBS) and (chance < 70) : #Retail statistic info. 20 mob's - 14 feathers
         st.giveItems(FEATHER,1)
         st.playSound("ItemSound.quest_itemget")
     return

QUEST       = Quest(284, qn, "Muertos Feather")

QUEST.addStartNpc(TREVOR)

QUEST.addTalkId(TREVOR)

for mob in MOBS :
    QUEST.addKillId(mob)