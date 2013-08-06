# Made by Mr. - Version 0.3 by DrLecter
import sys
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest import Quest as JQuest

qn = "313_CollectSpores"

FUNGUS_SAC = 1118
ADENA = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
     self.questItemIds = [FUNGUS_SAC]

 def onEvent (self,event,st) :
    htmltext = event
    if event == "30150-05.htm" :
      st.set("cond","1")
      st.setState(State.STARTED)
      st.playSound("ItemSound.quest_accept")
    return htmltext

 def onTalk (self,npc,player):
   htmltext = Quest.getNoQuestMsg(player)
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getId()
   id = st.getState()
   cond=st.getInt("cond")
   if cond == 0 :
     if player.getLevel() >= 8 :
       htmltext = "30150-03.htm"
     else:
       htmltext = "30150-02.htm"
       st.exitQuest(1)
   else :
     if st.getQuestItemsCount(FUNGUS_SAC)<10 :
       htmltext = "30150-06.htm"
     else :
       st.playSound("ItemSound.quest_finish")
       st.giveItems(ADENA,3500)
       st.takeItems(FUNGUS_SAC,-1)
       htmltext = "30150-07.htm"
       st.exitQuest(1)
   return htmltext

 def onKill(self,npc,player,isPet):
   st = player.getQuestState(qn)
   if not st : return 
   if st.getState() != State.STARTED : return 
   
   if st.getQuestItemsCount(FUNGUS_SAC)<10 and self.getRandom(100) < 50 :
     st.giveItems(FUNGUS_SAC,1)
     if st.getQuestItemsCount(FUNGUS_SAC) == 10 :
       st.playSound("ItemSound.quest_middle")
     else:
       st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(313,qn,"Collect Spores")

QUEST.addStartNpc(30150)

QUEST.addTalkId(30150)

QUEST.addKillId(20509)