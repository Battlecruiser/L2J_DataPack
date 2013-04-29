# Made by Mr. Have fun!
# Version 0.3 by H1GHL4ND3R
import sys
from com.l2jserver import Config 
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest import Quest as JQuest

qn = "160_NerupasFavor"

SILVERY_SPIDERSILK = 1026
UNOS_RECEIPT = 1027
CELS_TICKET = 1028
NIGHTSHADE_LEAF = 1029
LESSER_HEALING_POTION = 1060

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
     self.questItemIds = [SILVERY_SPIDERSILK, UNOS_RECEIPT, CELS_TICKET, NIGHTSHADE_LEAF]

 def onAdvEvent (self,event,npc, player) :
    htmltext = event
    st = player.getQuestState(qn)
    if not st : return
    if event == "30370-04.htm" :
        st.set("cond","1")
        st.setState(State.STARTED)
        st.playSound("ItemSound.quest_accept")
        st.giveItems(SILVERY_SPIDERSILK,1)
    return htmltext

 def onTalk (self,npc,player):
   htmltext = Quest.getNoQuestMsg(player)
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   id = st.getState()
   if id == State.CREATED :
     if player.getRace().ordinal() != 1 :
       htmltext = "30370-00.htm"
     elif player.getLevel() >= 3 :
       htmltext = "30370-03.htm"
       st.set("cond","0")
     else:
       htmltext = "30370-02.htm"
       st.exitQuest(1)
   elif id == State.COMPLETED :
     htmltext = Quest.getAlreadyCompletedMsg(player)

   elif id == State.STARTED :
     try :
       cond = st.getInt("cond")
     except :
       cond = None
     if cond == 1 :
       if npcId == 30370 :
         htmltext = "30370-05.htm"
       elif npcId == 30147 and st.getQuestItemsCount(SILVERY_SPIDERSILK) :
         st.takeItems(SILVERY_SPIDERSILK,1)
         st.giveItems(UNOS_RECEIPT,1)
         st.set("cond","2")
         st.playSound("ItemSound.quest_middle")
         htmltext = "30147-01.htm"
     elif cond == 2 :
       if npcId == 30370 :
         htmltext = "30370-05.htm"
       elif npcId == 30147 and st.getQuestItemsCount(UNOS_RECEIPT) :
         htmltext = "30147-02.htm"
       elif npcId == 30149 and st.getQuestItemsCount(UNOS_RECEIPT) :
         st.takeItems(UNOS_RECEIPT,1)
         st.giveItems(CELS_TICKET,1)
         st.set("cond","3")
         htmltext = "30149-01.htm"
     elif cond == 3 :
       if npcId == 30370 :
         htmltext = "30370-05.htm"
       elif npcId == 30149 and st.getQuestItemsCount(CELS_TICKET) :
         htmltext = "30149-02.htm"
       elif npcId == 30152 and st.getQuestItemsCount(CELS_TICKET) :
        st.takeItems(CELS_TICKET,st.getQuestItemsCount(CELS_TICKET))
        st.giveItems(NIGHTSHADE_LEAF,1)
        st.set("cond","4")
        htmltext = "30152-01.htm"
     elif cond == 4 :
        if npcId == 30152 and st.getQuestItemsCount(NIGHTSHADE_LEAF) :
          htmltext = "30152-02.htm"
        elif npcId == 30149 and st.getQuestItemsCount(NIGHTSHADE_LEAF) :
          htmltext = "30149-03.htm"
        elif npcId == 30147 and st.getQuestItemsCount(NIGHTSHADE_LEAF) :
          htmltext = "30147-03.htm"
        elif npcId == 30370 and st.getQuestItemsCount(NIGHTSHADE_LEAF) :
          st.takeItems(NIGHTSHADE_LEAF,1)
          st.rewardItems(LESSER_HEALING_POTION,5)
          st.addExpAndSp(1000,0)
          st.unset("cond")
          st.exitQuest(False)
          st.playSound("ItemSound.quest_finish")
          htmltext = "30370-06.htm"
   return htmltext

QUEST       = Quest(160,qn,"Nerupa's Request")

QUEST.addStartNpc(30370)

QUEST.addTalkId(30370)

QUEST.addTalkId(30147)
QUEST.addTalkId(30149)
QUEST.addTalkId(30152)
QUEST.addTalkId(30370)