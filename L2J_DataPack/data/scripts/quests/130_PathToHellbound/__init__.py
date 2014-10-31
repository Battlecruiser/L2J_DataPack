# Made by Cromir Version 0.1
import sys
from com.l2jserver.gameserver.instancemanager import HellboundManager
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest

qn = "130_PathToHellbound"

#NPCs
GALATE = 32292
CASIAN = 30612

#ITEMS
CASIAN_BLUE_CRY = 12823

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
     self.questItemIds = [CASIAN_BLUE_CRY]

 def onEvent (self,event,st) :
   htmltext = event
   if event == "30612-03.htm" :
     st.set("cond","1")
     st.setState(State.STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "32292-03.htm" :
	 st.set("cond","2")
	 st.playSound("ItemSound.quest_middle")
   elif event == "30612-05.htm" :
	 st.set("cond","3")
	 st.giveItems(CASIAN_BLUE_CRY,1)
	 st.playSound("ItemSound.quest_middle")
   elif event == "32292-06.htm" :
	 st.takeItems(CASIAN_BLUE_CRY,-1)
	 st.playSound("ItemSound.quest_finish")
	 st.exitQuest(False)
   return htmltext

 def onTalk (self,npc,player):
   st = player.getQuestState(qn)
   htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
   if not st: return htmltext

   npcId = npc.getNpcId()
   id = st.getState()
   cond = st.getInt("cond")

   if id == State.COMPLETED :
     htmltext = "<html><body>This quest has already been completed.</body></html>"

   elif npcId == CASIAN :
     if cond == 0 :
       if not HellboundManager.getInstance().isLocked() :
         if player.getLevel() >= 78 :
           htmltext = "30612-01.htm"
         else :
           htmltext = "30612-00.htm"
           st.exitQuest(1)
       else :
         htmltext = "30612-hellbound-locked.htm"
         st.exitQuest(1)
     elif cond == 1 :
       htmltext = "30612-03a.htm"
     elif cond == 2 :
       htmltext = "30612-04.htm"
     elif cond == 3 :
       htmltext = "30612-05a.htm"
   elif npcId == GALATE :
     if cond == 1 :
       htmltext = "32292-01.htm"
     elif cond == 2 :
       htmltext = "32292-03a.htm"
     elif cond == 3 :
       if st.getQuestItemsCount(CASIAN_BLUE_CRY) == 1 :
         htmltext = "32292-04.htm"
       else :
         htmltext = "Incorrect item count."
   return htmltext

QUEST     = Quest(130,qn,"Path to Hellbound")

QUEST.addStartNpc(CASIAN)

QUEST.addTalkId(CASIAN)
QUEST.addTalkId(GALATE)