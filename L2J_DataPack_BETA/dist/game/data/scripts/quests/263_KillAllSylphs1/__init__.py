# Made by Mr. - Version 0.3 by DrLecter
import sys
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest import Quest as JQuest

qn = "263_KillAllSylphs1"

ORC_AMULET = 1116
ORC_NECKLACE = 1117
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
     self.questItemIds = [ORC_AMULET, ORC_NECKLACE]

 def onAdvEvent (self,event,npc, player) :
    htmltext = event
    st = player.getQuestState(qn)
    if not st : return
    if event == "30346-03.htm" :
      st.set("cond","1")
      st.setState(State.STARTED)
      st.playSound("ItemSound.quest_accept")
    elif event == "30346-06.htm" :
      st.exitQuest(1)
      st.playSound("ItemSound.quest_finish")
    return htmltext

 def onTalk (self,npc,player):
   htmltext = Quest.getNoQuestMsg(player)
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getId()
   id = st.getState()

   if id == State.CREATED :
     st.set("cond","0")
   if st.getInt("cond")==0 :
     if player.getRace().ordinal() != 2 :
       htmltext = "30346-00.htm"
       st.exitQuest(1)
     elif player.getLevel()<8 :
       htmltext = "30346-01.htm"
       st.exitQuest(1)
     else :
       htmltext = "30346-02.htm"
   else :
     amulet = st.getQuestItemsCount(ORC_AMULET)
     necklace = st.getQuestItemsCount(ORC_NECKLACE)
     if amulet == necklace == 0 :
       htmltext = "30346-04.htm"
     else :
       htmltext = "30346-05.htm"
       st.giveItems(ADENA_ID,amulet*20+necklace*30)
       st.takeItems(ORC_AMULET,-1)
       st.takeItems(ORC_NECKLACE,-1)
   return htmltext

 def onKill(self,npc,player,isPet):
   st = player.getQuestState(qn)
   if not st : return 
   if st.getState() != State.STARTED : return 
   
   item=ORC_NECKLACE
   if npc.getId() == 20385 :
     item = ORC_AMULET
   if self.getRandom(10)>4 :
     st.giveItems(item,1)
     st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(263,qn,"Kill All Sylphs1")

QUEST.addStartNpc(30346)

QUEST.addTalkId(30346)

QUEST.addKillId(20385)
QUEST.addKillId(20386)
QUEST.addKillId(20387)
QUEST.addKillId(20388)