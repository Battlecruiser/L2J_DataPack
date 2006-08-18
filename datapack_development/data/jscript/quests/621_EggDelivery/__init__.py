# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
JEREMY = 8521
PULIN = 8543
NAFF = 8544
CROCUS = 8545
KUBER = 8546
BEORIN = 8547

#QUEST ITEMS
BOILED_EGGS = 7195
FEE_OF_EGGS = 7196

#REWARDS
ADENA = 57
HASTE_POTION = 734

#Chance to get an S-grade random recipe instead of just adena and haste potion
RPCHANCE=10
#Change this value to 1 if you wish 100% recipes, default 70%
ALT_RP100=0

#MESSAGES
default="<html><head><body>I have nothing to say you</body></html>"

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   cond=st.getInt("cond")
   if event == "8521-1.htm" :
     if cond==0:
       st.set("cond","1")
       st.setState(STARTED)
       st.giveItems(BOILED_EGGS,5)
       st.playSound("ItemSound.quest_accept")
     else:
       htmltext=default
   if event == "8543-1.htm" :
     if st.getQuestItemsCount(BOILED_EGGS):
       if cond==1:
         st.takeItems(BOILED_EGGS,1)
         st.giveItems(FEE_OF_EGGS,1)
         st.set("cond","2")
       else:
         htmltext=default
     else:
       htmltext="LMFAO!"
       st.extiQuest(1)
   if event == "8544-1.htm" :
     if st.getQuestItemsCount(BOILED_EGGS):
       if cond==2:
         st.takeItems(BOILED_EGGS,1)
         st.giveItems(FEE_OF_EGGS,1)
         st.set("cond","3")
       else:
         htmltext=default
     else:
       htmltext="LMFAO!"
       st.extiQuest(1)
   if event == "8545-1.htm" :
     if st.getQuestItemsCount(BOILED_EGGS):
       if cond==3:
         st.takeItems(BOILED_EGGS,1)
         st.giveItems(FEE_OF_EGGS,1)
         st.set("cond","4")
       else:
         htmltext=default
     else:
       htmltext="LMFAO!"
       st.extiQuest(1)
   if event == "8546-1.htm" :
     if st.getQuestItemsCount(BOILED_EGGS):
       if cond==4:
         st.takeItems(BOILED_EGGS,1)
         st.giveItems(FEE_OF_EGGS,1)
         st.set("cond","5")
       else:
         htmltext=default
     else:
       htmltext="LMFAO!"
       st.extiQuest(1)
   if event == "8547-1.htm" :
     if st.getQuestItemsCount(BOILED_EGGS):
       if cond==5:
         st.takeItems(BOILED_EGGS,1)
         st.giveItems(FEE_OF_EGGS,1)
         st.set("cond","6")
       else:
         htmltext=default
     else:
       htmltext="LMFAO!"
       st.extiQuest(1)
   if event == "8521-3.htm" :
     if st.getQuestItemsCount(FEE_OF_EGGS) == 5:
        st.takeItems(FEE_OF_EGGS,5)
        if st.getRandom(100) < RPCHANCE :
          st.giveItems(range(6847+ALT_RP100,6853,2)[st.getRandom(3)],1)
        else:
          st.giveItems(ADENA,18800)
          st.giveItems(HASTE_POTION,1)
        st.playSound("ItemSound.quest_finish")
        st.exitQuest(1)
     else:
        htmltext=default
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = default
   npcId = npc.getNpcId()
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   cond = int(st.get("cond"))
   if npcId == 8521 and cond == 0 :
     if st.getPlayer().getLevel() >= 68 and st.getPlayer().getLevel() <= 73 :
       htmltext = "8521-0.htm"
     else :
       st.exitQuest(1)
   elif npcId == 8543 and cond == 1 and st.getQuestItemsCount(BOILED_EGGS) :
     htmltext = "8543-0.htm"
   elif npcId == 8544 and cond == 2 and st.getQuestItemsCount(BOILED_EGGS) :
     htmltext = "8544-0.htm"
   elif npcId == 8545 and cond == 3 and st.getQuestItemsCount(BOILED_EGGS) :
     htmltext = "8545-0.htm"
   elif npcId == 8546 and cond == 4 and st.getQuestItemsCount(BOILED_EGGS) :
     htmltext = "8546-0.htm"
   elif npcId == 8547 and cond == 5 and st.getQuestItemsCount(BOILED_EGGS) :
     htmltext = "8547-0.htm"
   elif npcId == 8521 and cond == 6 and st.getQuestItemsCount(FEE_OF_EGGS) == 5 :
     htmltext = "8521-2.htm"
   return htmltext

QUEST       = Quest(621,"621_EggDelivery","Egg Delivery")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(8521)

CREATED.addTalkId(8521)
STARTED.addTalkId(8521)

for i in range(8543,8548):
    STARTED.addTalkId(i)

STARTED.addQuestDrop(8521,BOILED_EGGS,1)
STARTED.addQuestDrop(8521,FEE_OF_EGGS,1)

print "importing quests: 621: Egg Delivery"
