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
SPECIAL_DRINK = 7197
FEE_OF_DRINK = 7198

#REWARDS
ADENA = 57
HASTE_POTION = 1062

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "8521-1.htm" :
     st.set("cond","1")
     st.setState(STARTED)
     st.giveItems(SPECIAL_DRINK,5)
     st.playSound("ItemSound.quest_accept")
   if event == "8547-1.htm" :
     st.takeItems(SPECIAL_DRINK,1)
     st.giveItems(FEE_OF_DRINK,1)
     st.set("cond","2")
   if event == "8546-1.htm" :
     st.takeItems(SPECIAL_DRINK,1)
     st.giveItems(FEE_OF_DRINK,1)
     st.set("cond","3")
   if event == "8545-1.htm" :
     st.takeItems(SPECIAL_DRINK,1)
     st.giveItems(FEE_OF_DRINK,1)
     st.set("cond","4")
   if event == "8544-1.htm" :
     st.takeItems(SPECIAL_DRINK,1)
     st.giveItems(FEE_OF_DRINK,1)
     st.set("cond","5")
   if event == "8543-1.htm" :
     st.takeItems(SPECIAL_DRINK,1)
     st.giveItems(FEE_OF_DRINK,1)
     st.set("cond","6")
   if event == "8521-3.htm" :
     st.takeItems(FEE_OF_DRINK,5)
     st.giveItems(ADENA,18800)
     st.giveItems(HASTE_POTION,1)
     st.playSound("ItemSound.quest_finish")
     st.exitQuest(1)
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   npcId = npc.getNpcId()
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   cond = int(st.get("cond"))
   if npcId == 8521 and cond == 0 :
     if st.getPlayer().getLevel() >= 68 and st.getPlayer().getLevel() <= 73 :
       htmltext = "8521-0.htm"
     else:
       st.exitQuest(1)
   elif npcId == 8547 and cond == 1 and st.getQuestItemsCount(SPECIAL_DRINK) :
     htmltext = "8547-0.htm"
   elif npcId == 8546 and cond == 2 and st.getQuestItemsCount(SPECIAL_DRINK) :
     htmltext = "8546-0.htm"
   elif npcId == 8545 and cond == 3 and st.getQuestItemsCount(SPECIAL_DRINK) :
     htmltext = "8545-0.htm"
   elif npcId == 8544 and cond == 4 and st.getQuestItemsCount(SPECIAL_DRINK) :
     htmltext = "8544-0.htm"
   elif npcId == 8543 and cond == 5 and st.getQuestItemsCount(SPECIAL_DRINK) :
     htmltext = "8543-0.htm"
   elif npcId == 8521 and cond == 6 and st.getQuestItemsCount(FEE_OF_DRINK) == 5 :
     htmltext = "8521-2.htm"
   return htmltext

QUEST       = Quest(622,"622_DeliveryOfSpecialLiquor","Delivery of special liquor")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(8521)

CREATED.addTalkId(8521)
STARTED.addTalkId(8521)

for i in range(8543,8548):
    STARTED.addTalkId(i)

STARTED.addQuestDrop(8521,SPECIAL_DRINK,1)
STARTED.addQuestDrop(8521,FEE_OF_DRINK,1)

print "importing quests: 622: Delivery of special liquor"
