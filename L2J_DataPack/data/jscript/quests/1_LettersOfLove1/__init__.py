# Maked by Mr. Have fun! Version 0.2
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPCs 
DARIN  = 7048 
ROXXY  = 7006 
BAULRO = 7033 

#ITEMS 
DARINGS_LETTER     = 687 
RAPUNZELS_KERCHIEF = 688 
DARINGS_RECEIPT    = 1079 
BAULS_POTION       = 1080 
 
#REWARD 
NECKLACE = 908 
 
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event 
   if event == "7048-06.htm" : 
     st.set("cond","1") 
     st.set("id","1") 
     st.setState(STARTED) 
     st.playSound("ItemSound.quest_accept") 
     if st.getQuestItemsCount(DARINGS_LETTER) == 0 : 
       st.giveItems(DARINGS_LETTER,1) 
   return htmltext 

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>" 
   npcId = npc.getNpcId()
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
 
   cond = st.getInt("cond") 
   onlyone = st.getInt("onlyone") 
   ItemsCount_DL = st.getQuestItemsCount(DARINGS_LETTER) 
   ItemsCount_RK = st.getQuestItemsCount(RAPUNZELS_KERCHIEF) 
   ItemsCount_DR = st.getQuestItemsCount(DARINGS_RECEIPT) 
   ItemsCount_BP = st.getQuestItemsCount(BAULS_POTION) 
 
   if npcId == DARIN and cond == 0 and onlyone == 0 : 
     if st.getPlayer().getLevel() >= 2 : 
       if cond < 15 : 
         htmltext = "7048-02.htm" 
       else: 
         htmltext = "7048-01.htm" 
         st.exitQuest(1) 
     else: 
       htmltext = "<html><head><body>Quest for characters level 2 and above.</body></html>" 
       st.exitQuest(1) 
   elif npcId == DARIN and cond == 0 and onlyone == 1 : 
     htmltext = "<html><head><body>This quest have already been completed.</body></html>" 
   elif npcId == ROXXY and cond and onlyone == 0 : 
     if ItemsCount_RK == 0 and ItemsCount_DL : 
       htmltext = "7006-01.htm" 
       st.takeItems(DARINGS_LETTER,-1) 
       st.giveItems(RAPUNZELS_KERCHIEF,1) 
       st.set("cond","2") 
       st.set("id","2") 
       st.playSound("ItemSound.quest_middle") 
     elif ItemsCount_BP or ItemsCount_DR : 
       htmltext = "7006-03.htm" 
     elif ItemsCount_RK : 
       htmltext = "7006-02.htm" 
   elif npcId == DARIN and cond and ItemsCount_RK > 0 and onlyone == 0 : 
     htmltext = "7048-08.htm" 
     st.takeItems(RAPUNZELS_KERCHIEF,-1) 
     st.giveItems(DARINGS_RECEIPT,1) 
     st.set("cond","3") 
     st.set("id","3") 
     st.playSound("ItemSound.quest_middle") 
   elif npcId == BAULRO and cond and onlyone == 0 : 
     if ItemsCount_DR > 0 : 
       htmltext = "7033-01.htm" 
       st.takeItems(DARINGS_RECEIPT,-1) 
       st.giveItems(BAULS_POTION,1) 
       st.set("cond","4") 
       st.set("id","4") 
       st.playSound("ItemSound.quest_middle") 
     elif ItemsCount_BP > 0 : 
       htmltext = "7033-02.htm" 
   elif npcId == DARIN and cond and ItemsCount_RK == 0 and onlyone == 0 : 
     if ItemsCount_DR > 0 : 
       htmltext = "7048-09.htm" 
     elif ItemsCount_BP > 0 : 
       htmltext = "7048-10.htm" 
       st.takeItems(BAULS_POTION,-1) 
       st.giveItems(NECKLACE,1) 
       st.set("cond","0") 
       st.set("onlyone","1") 
       st.setState(COMPLETED) 
       st.playSound("ItemSound.quest_finish") 
     else: 
       htmltext = "7048-07.htm" 
   return htmltext

QUEST     = Quest(1,"1_LettersOfLove1","Letters of Love") 
CREATED   = State('Start',     QUEST) 
STARTING  = State('Starting',  QUEST) 
STARTED   = State('Started',   QUEST) 
COMPLETED = State('Completed', QUEST) 

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(DARIN) 

STARTING.addTalkId(DARIN) 

STARTED.addTalkId(ROXXY) 
STARTED.addTalkId(BAULRO) 
STARTED.addTalkId(DARIN) 

STARTED.addQuestDrop(DARIN,DARINGS_LETTER,1) 
STARTED.addQuestDrop(DARIN,RAPUNZELS_KERCHIEF,1) 
STARTED.addQuestDrop(DARIN,DARINGS_RECEIPT,1) 
STARTED.addQuestDrop(DARIN,BAULS_POTION,1) 

print "importing quests: 1: Letters Of Love" 
