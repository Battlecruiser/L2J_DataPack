# Made by Mr. - Version 0.3 by DrLecter
import sys
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest

qn = "329_CuriosityOfDwarf"

GOLEM_HEARTSTONE = 1346
BROKEN_HEARTSTONE = 1365
ADENA = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
     self.questItemIds = [BROKEN_HEARTSTONE, GOLEM_HEARTSTONE]

 def onEvent (self,event,st) :
    htmltext = event
    if event == "30437-03.htm" :
      st.set("cond","1")
      st.setState(State.STARTED)
      st.playSound("ItemSound.quest_accept")
    elif event == "30437-06.htm" :
      st.exitQuest(1)
      st.playSound("ItemSound.quest_finish")
    return htmltext

 def onTalk (self,npc,player):
   htmltext = Quest.getNoQuestMsg(player)
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   id = st.getState()
   if id == State.CREATED :
     st.set("cond","0")
   if st.getInt("cond")==0 :
      if player.getLevel() >= 33 :
         htmltext = "30437-02.htm"
      else:
         htmltext = "30437-01.htm"
         st.exitQuest(1)
   else :
      heart=st.getQuestItemsCount(GOLEM_HEARTSTONE)
      broken=st.getQuestItemsCount(BROKEN_HEARTSTONE)
      if broken+heart>0 :
         st.giveItems(ADENA,50*broken+1000*heart)
         st.takeItems(BROKEN_HEARTSTONE,-1)
         st.takeItems(GOLEM_HEARTSTONE,-1)
         htmltext = "30437-05.htm"
      else:
         htmltext = "30437-04.htm"
   return htmltext

 def onKill(self,npc,player,isPet):
   st = player.getQuestState(qn)
   if not st : return 
   if st.getState() != State.STARTED : return 
   
   npcId = npc.getNpcId()
   n = getRandom(100)
   if npcId == 20085 :
      if n<5 :
         st.giveItems(GOLEM_HEARTSTONE,1)
         st.playSound("ItemSound.quest_itemget")
      elif n<58 :
         st.giveItems(BROKEN_HEARTSTONE,1)
         st.playSound("ItemSound.quest_itemget")
   elif npcId == 20083 :
      if n<6 :
         st.giveItems(GOLEM_HEARTSTONE,1)
         st.playSound("ItemSound.quest_itemget")
      elif n<56 :
         st.giveItems(BROKEN_HEARTSTONE,1)
         st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(329,qn,"Curiosity Of Dwarf")

QUEST.addStartNpc(30437)
QUEST.addTalkId(30437)

QUEST.addKillId(20083)
QUEST.addKillId(20085)