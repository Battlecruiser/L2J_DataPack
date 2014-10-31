# Contributed by t0rm3nt0r (tormentor2000@mail.ru) to the Official L2J Datapack Project
# Visit http://www.l2jdp.com/forum/ for more details.

import sys
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest

qn = "174_SupplyCheck"

#NPC'S
MARCELA = 32173
BENIS = 32170
NIKA = 32167

#ITEM'S
WAREHOUSE_MANIFEST = 9792
GROCERY_STORE_MANIFEST = 9793

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
     self.questItemIds = [WAREHOUSE_MANIFEST, GROCERY_STORE_MANIFEST]
 
 def onAdvEvent (self,event,npc, player) :
     htmltext = event
     st = player.getQuestState(qn)
     if not st : return
     if event == "32173-03.htm" :
       st.set("cond","1")
       st.setState(State.STARTED)
       st.playSound("ItemSound.quest_accept")
     return htmltext

 def onTalk (self,npc,player):
     npcId = npc.getNpcId()
     htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
     st = player.getQuestState(qn)
     if not st : return htmltext
     id = st.getState()
     cond = st.getInt("cond")
     if id == State.COMPLETED :
       htmltext = "<html><body>This quest has already been completed.</body></html>"
     elif id == State.CREATED and npcId == MARCELA :
       if st.getPlayer().getLevel() >= 2 :
         htmltext = "32173-01.htm"
       else :
         htmltext = "32173-02.htm"
         st.exitQuest(1)
     elif id == State.STARTED :
       if npcId == MARCELA : 
         if cond == 1 :
           htmltext = "32173-04.htm"
         elif cond == 2 :
           htmltext = "32173-05.htm"
           st.set("cond","3")
           st.takeItems(WAREHOUSE_MANIFEST,-1)
           st.playSound("ItemSound.quest_middle")
         elif cond == 3 :
           htmltext = "32173-06.htm"
         elif cond == 4 :
           htmltext = "32173-07.htm"
           st.unset("cond")
           st.takeItems(GROCERY_STORE_MANIFEST,-1)
           st.giveItems(23,1)
           st.giveItems(37,1)
           st.giveItems(43,1)
           st.giveItems(49,1)
           st.giveItems(57,2466)
           st.giveItems(2386,1)
           st.playSound("ItemSound.quest_finish")
           st.addExpAndSp(5672,446)
           st.exitQuest(False)
       elif npcId == BENIS :
         if cond == 1 :
           htmltext = "32170-01.htm"
           st.set("cond","2")
           st.giveItems(WAREHOUSE_MANIFEST,1)
           st.playSound("ItemSound.quest_middle")
         elif cond == 2 :
           htmltext = "32170-02.htm"
       elif npcId == NIKA :
         if cond == 3 :
           htmltext = "32167-01.htm"
           st.set("cond","4")
           st.giveItems(GROCERY_STORE_MANIFEST,1)
           st.playSound("ItemSound.quest_middle")
         elif cond == 4 :
           htmltext = "32167-02.htm"
     return htmltext

QUEST       = Quest(174, qn, "Supply Check")

QUEST.addStartNpc(MARCELA)

QUEST.addTalkId(MARCELA)
QUEST.addTalkId(BENIS)
QUEST.addTalkId(NIKA)
