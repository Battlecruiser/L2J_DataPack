# Made by Kerberos v1.0 on 2008/07/26
# this script is part of the Official L2J Datapack Project.
# Visit http://www.l2jdp.com/forum/ for more details.

import sys
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest

qn = "269_InventionAmbition"

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
     self.questItemIds = [10866]

 def onAdvEvent (self,event,npc, player) :
    htmltext = event
    st = player.getQuestState(qn)
    if not st : return
    if event == "32486-03.htm" :
      st.set("cond","1")
      st.setState(State.STARTED)
      st.playSound("ItemSound.quest_accept")
    elif event == "32486-05.htm" :
      st.exitQuest(1)
      st.playSound("ItemSound.quest_finish")
    return htmltext

 def onTalk (self,npc,player):
   htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   id = st.getState()
   EnergyOres = st.getQuestItemsCount(10866)
   if id == State.CREATED :
      if player.getLevel() < 18 :
         htmltext = "32486-00.htm"
         st.exitQuest(1)
      else :
         htmltext = "32486-01.htm"
   elif EnergyOres > 0:
      htmltext = "32486-07.htm"
      bonus = 0
      if EnergyOres >= 20:
         bonus = 2044
      st.giveItems(57,EnergyOres*50+bonus)
      st.takeItems(10866,-1)
   else :
      htmltext = "32486-04.htm"
   return htmltext

 def onKill(self,npc,player,isPet):
   st = player.getQuestState(qn)
   if not st : return 
   if st.getState() != State.STARTED : return 
   
   if st.getRandom(10)<6 :
     st.giveItems(10866,1)
     st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(269,qn,"Invention Ambition")

QUEST.addStartNpc(32486)
QUEST.addTalkId(32486)
for mob in range(21124,21132) :
    QUEST.addKillId(mob)