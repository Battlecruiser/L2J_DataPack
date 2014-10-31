# Made by Bloodshed 
import sys
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest

qn = "133_ThatsBloodyHot"

#NPCs
KANIS = 32264
GALATE = 32292

#ITEMS
CRYSTAL_SAMPLE = 9785

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
     self.questItemIds = [CRYSTAL_SAMPLE]

 def onEvent (self,event,st) :
   htmltext = event
   if event == "32264-02.htm" :
     st.set("cond","1")
     st.setState(State.STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "32264-07.htm" :
     st.set("cond","2")
     st.giveItems(CRYSTAL_SAMPLE,1)
     st.playSound("ItemSound.quest_middle")
   elif event == "32292-03.htm" :
     st.takeItems(CRYSTAL_SAMPLE,-1)
   elif event == "32292-04.htm" :
     st.addExpAndSp(670612,0)
     st.playSound("ItemSound.quest_finish")
     st.exitQuest(False)
   return htmltext

 def onTalk (self,npc,player):
   st = player.getQuestState(qn)
   htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
   if not st: return htmltext

   npcId = npc.getNpcId()
   cond = st.getInt("cond")
   if st.getState() == State.COMPLETED :
     htmltext = "<html><body>This quest has already been completed.</body></html>"
   elif npcId == KANIS :
     st2 = player.getQuestState("131_BirdInACage")
     if st2 : 
       if st.getState() == State.CREATED :
         if st2.getState() == State.COMPLETED :
           htmltext = "32264-01.htm"
         else :
           htmltext = "32264-00.htm"
           st.exitQuest(1)
       elif cond == 1 :
         htmltext = "32264-02.htm"
       elif cond == 2 :
         htmltext = "32264-07.htm"
     else :
       htmltext = "32264-00.htm"
       st.exitQuest(1)
   elif npcId == GALATE and cond == 2 :
     if st.getQuestItemsCount(CRYSTAL_SAMPLE) == 1 :
       htmltext = "32292-01.htm"
   return htmltext

QUEST     = Quest(133,qn,"That's Bloody Hot!")

QUEST.addStartNpc(KANIS)

QUEST.addTalkId(KANIS)
QUEST.addTalkId(GALATE)