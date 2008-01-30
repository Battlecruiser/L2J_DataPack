# Contributed by t0rm3nt0r (tormentor2000@mail.ru) to the Official L2J Datapack Project.
# Visit http://forum.l2jdp.com for more details.

import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#Complete - 95%. Need add reward for 20+ items.
qn = "283_TheFewTheProudTheBrave"

#NPC'S
PERWAN = 32133

#ITEM'S
CLAW = 9747

#MOB'S
SPIDER = 22244

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
  
 def onEvent (self,event,st) :
     htmltext = event
     claw = st.getQuestItemsCount(CLAW)
     if event == "32133-03.htm" :
       st.set("cond","1")
       st.setState(State.STARTED)
       st.playSound("ItemSound.quest_accept")
     elif event == "32133-06.htm" :
       st.giveItems(57,claw*45) #Need add reward for 20+ items
       st.takeItems(CLAW,-1)
     elif event == "32133-08.htm" :
       st.takeItems(CLAW,-1)
       st.exitQuest(1)
     return htmltext

 def onTalk (self,npc,player):
     npcId = npc.getNpcId()
     htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
     st = player.getQuestState(qn)
     if not st : return htmltext
     id = st.getState()
     cond = st.getInt("cond")
     claw = st.getQuestItemsCount(CLAW)
     if id == State.CREATED and npcId == PERWAN :
       if player.getLevel() < 15 :
         htmltext = "32133-02.htm"
         st.exitQuest(1)
       else :
         htmltext = "32133-01.htm"
     elif id == State.STARTED and npcId == PERWAN :
       if not claw :
         htmltext = "32133-04.htm"
       else :
         htmltext = "32133-05.htm"
     return htmltext
    
 def onKill(self,npc,player,isPet) :
     st = player.getQuestState(qn)
     if not st: return
     if st.getState() == State.STARTED :
       npcId = npc.getNpcId()
       chance = st.getRandom(100)
       if (npcId == SPIDER) and (chance < 35) : #Retail statistic info. 64 mob's - 22 claw
         st.giveItems(CLAW,1)
         st.playSound("ItemSound.quest_itemget")
     return

QUEST       = Quest(283, qn, "The Few, The Proud, The Brave")

QUEST.addStartNpc(PERWAN)

QUEST.addTalkId(PERWAN)

QUEST.addKillId(SPIDER)