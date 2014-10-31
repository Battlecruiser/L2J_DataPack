# Maked by Mr. Have fun! Version 0.2
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPCs 
BOLTER = 7554 
SHARI  = 7517 
GARITA = 7518 
REED   = 7520 
BRUNON = 7526 

#ITEMS 
BOLTERS_LIST         = 1547 
MINING_BOOTS         = 1548 
MINERS_PICK          = 1549 
BOOMBOOM_POWDER      = 1550 
REDSTONE_BEER        = 1551 
BOLTERS_SMELLY_SOCKS = 1552 
 
#REWARD 
NECKLACE = 906 
 
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event 
   if event == "7554-03.htm" : 
     st.giveItems(BOLTERS_LIST,1) 
     st.giveItems(BOLTERS_SMELLY_SOCKS,1) 
     st.set("cond","1") 
     st.set("id","1") 
     st.setState(STARTED) 
     st.playSound("ItemSound.quest_accept") 
   elif event == "7526-02.htm" : 
     st.takeItems(BOLTERS_SMELLY_SOCKS,-1) 
     st.giveItems(MINERS_PICK,1) 
     if st.getQuestItemsCount(BOLTERS_LIST) and (st.getQuestItemsCount(MINING_BOOTS) + st.getQuestItemsCount(MINERS_PICK) + st.getQuestItemsCount(BOOMBOOM_POWDER) + st.getQuestItemsCount(REDSTONE_BEER) >= 4) : 
       st.set("cond","2") 
       st.set("id","2") 
       st.playSound("ItemSound.quest_middle") 
     else: 
       st.playSound("ItemSound.quest_itemget") 
   return htmltext 

 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
 
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
 
   cond    = st.getInt("cond") 
   onlyone = st.getInt("onlyone") 
 
   if npcId == BOLTER and cond == 0 : 
     if onlyone == 1 : 
       htmltext = "<html><head><body>This quest have already been completed.</body></html>" 
     elif st.getPlayer().getLevel() >= 2 : 
       htmltext = "7554-02.htm" 
     else: 
       htmltext = "7554-01.htm" 
       st.exitQuest(1) 
   elif npcId == BOLTER and cond == 1 : 
     htmltext = "7554-04.htm" 
   elif npcId == BOLTER and cond == 2 : 
     htmltext = "7554-06.htm" 
     st.takeItems(MINING_BOOTS,-1) 
     st.takeItems(MINERS_PICK,-1) 
     st.takeItems(BOOMBOOM_POWDER,-1) 
     st.takeItems(REDSTONE_BEER,-1) 
     st.takeItems(BOLTERS_LIST,-1) 
     st.giveItems(NECKLACE,1) 
     st.set("cond","0") 
     st.set("onlyone","1") 
     st.setState(COMPLETED) 
     st.playSound("ItemSound.quest_finish") 
   elif npcId == SHARI and cond == 1 and st.getQuestItemsCount(BOLTERS_LIST) : 
     if st.getQuestItemsCount(BOOMBOOM_POWDER) == 0 : 
       htmltext = "7517-01.htm" 
       st.giveItems(BOOMBOOM_POWDER,1) 
       st.playSound("ItemSound.quest_itemget") 
     else: 
       htmltext = "7517-02.htm" 
   elif npcId == GARITA and cond == 1 and st.getQuestItemsCount(BOLTERS_LIST) : 
     if st.getQuestItemsCount(MINING_BOOTS) == 0 : 
       htmltext = "7518-01.htm" 
       st.giveItems(MINING_BOOTS,1) 
       st.playSound("ItemSound.quest_itemget") 
     else: 
       htmltext = "7518-02.htm" 
   elif npcId == REED and cond == 1 and st.getQuestItemsCount(BOLTERS_LIST) : 
     if st.getQuestItemsCount(REDSTONE_BEER) == 0 : 
       htmltext = "7520-01.htm" 
       st.giveItems(REDSTONE_BEER,1) 
       st.playSound("ItemSound.quest_itemget") 
     else: 
       htmltext = "7520-02.htm" 
   elif npcId == BRUNON and cond == 1 and st.getQuestItemsCount(BOLTERS_LIST) : 
     if st.getQuestItemsCount(MINERS_PICK) == 0 : 
       htmltext = "7526-01.htm" 
     else: 
       htmltext = "7526-03.htm" 
   if st.getQuestItemsCount(BOLTERS_LIST) and (st.getQuestItemsCount(MINING_BOOTS) + st.getQuestItemsCount(MINERS_PICK) + st.getQuestItemsCount(BOOMBOOM_POWDER) + st.getQuestItemsCount(REDSTONE_BEER) >= 4) : 
     st.set("cond","2") 
     st.set("id","2") 
     st.playSound("ItemSound.quest_middle") 
   return htmltext

QUEST     = Quest(5,"5_MinersFavor","Miner's Favor") 
CREATED   = State('Start',     QUEST) 
STARTING  = State('Starting',  QUEST) 
STARTED   = State('Started',   QUEST) 
COMPLETED = State('Completed', QUEST) 

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(BOLTER) 

STARTING.addTalkId(BOLTER) 

STARTED.addTalkId(SHARI) 
STARTED.addTalkId(GARITA) 
STARTED.addTalkId(REED) 
STARTED.addTalkId(BRUNON) 
STARTED.addTalkId(BOLTER) 

STARTED.addQuestDrop(BOLTER,MINING_BOOTS,1) 
STARTED.addQuestDrop(BOLTER,MINERS_PICK,1) 
STARTED.addQuestDrop(BOLTER,BOOMBOOM_POWDER,1) 
STARTED.addQuestDrop(BOLTER,REDSTONE_BEER,1) 
STARTED.addQuestDrop(BOLTER,BOLTERS_LIST,1) 
STARTED.addQuestDrop(BOLTER,BOLTERS_SMELLY_SOCKS,1) 

print "importing quests: 5: Miner's Favor" 
