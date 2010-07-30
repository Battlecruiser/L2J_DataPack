# Made by Bloodshed 
import sys
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest
from com.l2jserver.util import Rnd

qn = "131_BirdInACage"

#NPCs
KANIS = 32264
PARME = 32271

#ITEMS
ECHO_CRYSTAL = 9783
PARMES_LETTER = 9784

class Quest (JQuest) :

 def __init__(self, id, name, descr):
     JQuest.__init__(self, id, name, descr)
     self.questItemIds = [ECHO_CRYSTAL,PARMES_LETTER]

 def onEvent (self,event,st) :
   htmltext = event
   if event == "32264-02.htm" :
     st.set("cond", "1")
     st.setState(State.STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "32264-08.htm" :
     st.giveItems(ECHO_CRYSTAL, 1)
     st.set("cond", "2")
     st.playSound("ItemSound.quest_middle")
   elif event == "32271-03.htm" :
     st.set("cond", "3")
     st.giveItems(PARMES_LETTER, 1)
     st.playSound("ItemSound.quest_middle")
     x = Rnd.get(-100, 100)
     y = Rnd.get(-100, 100)
     st.getPlayer().teleToLocation(143472 + x, 191040 + y, -3696)
     st.getPlayer().setInstanceId(0)
   elif event == "32264-12.htm" :
     if st.getQuestItemsCount(PARMES_LETTER) :
       st.takeItems(PARMES_LETTER, 1)
     st.playSound("ItemSound.quest_middle")
   elif event == "32264-13.htm" :
     st.takeItems(ECHO_CRYSTAL, 1)
     st.addExpAndSp(1304752, 0)
     st.playSound("ItemSound.quest_finish")
     st.exitQuest(False)
   return htmltext

 def onTalk (self, npc, player):
   st = player.getQuestState(qn)
   htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
   if not st : return htmltext

   npcId = npc.getNpcId()
   cond = st.getInt("cond")
   if st.getState() == State.COMPLETED :
     htmltext = "<html><body>This quest has already been completed.</body></html>"
   elif npcId == KANIS :
     if cond == 0 :
       if player.getLevel() >= 78 :
         htmltext = "32264-01.htm"
       else :
         htmltext = "32264-00.htm"
         st.exitQuest(1)
     elif cond == 1 :
       htmltext = "32264-03.htm"
     elif cond == 2 :
       htmltext = "32264-08a.htm"
     elif cond == 3 :
       if st.getQuestItemsCount(PARMES_LETTER) == 1 :
         htmltext = "32264-11.htm"
       elif st.getQuestItemsCount(PARMES_LETTER) == 0 :
         htmltext = "32264-12.htm"
   elif npcId == PARME :
     if cond == 2 :
       htmltext = "32271-01.htm"
   return htmltext

QUEST = Quest(131,qn,"Bird in a Cage")

QUEST.addStartNpc(KANIS)

QUEST.addTalkId(KANIS)
QUEST.addTalkId(PARME)