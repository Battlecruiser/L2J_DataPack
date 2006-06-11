# Maked by Mr. Have fun! Version 0.2
import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

htmlhead = "<html><head><body>"
htmlfoot = "</body></html>"

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
     if st.getQuestItemsCount(DARINGS_LETTER) == 0 :
       st.giveItems(DARINGS_LETTER,1)
     st.set("cond","1")
     st.set("id","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   return htmltext

 def onTalk (self,npc,st):
   htmltext = htmlhead + "I have nothing to say you" + htmlfoot
   npcId = npc.getNpcId()
   id    = st.getState()
   cond  = st.getInt("cond")
   ItemsCount_DL = st.getQuestItemsCount(DARINGS_LETTER)
   ItemsCount_RK = st.getQuestItemsCount(RAPUNZELS_KERCHIEF)
   ItemsCount_DR = st.getQuestItemsCount(DARINGS_RECEIPT)
   ItemsCount_BP = st.getQuestItemsCount(BAULS_POTION)

   if id == CREATED :
     if st.getPlayer().getLevel() >= 2 :
       htmltext = "7048-02.htm"
     else:
       htmltext = htmlhead + "Quest for characters level 2 and above." + htmlfoot
       st.exitQuest(1)
   elif id == COMPLETED :
     htmltext = htmlhead + "This quest has already been completed." + htmlfoot
   elif npcId == ROXXY and cond >= 1 :
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
   elif npcId == DARIN and cond >= 1 and ItemsCount_RK > 0 :
     htmltext = "7048-08.htm"
     st.takeItems(RAPUNZELS_KERCHIEF,-1)
     st.giveItems(DARINGS_RECEIPT,1)
     st.set("cond","3")
     st.set("id","3")
     st.playSound("ItemSound.quest_middle")
   elif npcId == BAULRO and cond >= 1 :
     if ItemsCount_DR > 0 :
       htmltext = "7033-01.htm"
       st.takeItems(DARINGS_RECEIPT,-1)
       st.giveItems(BAULS_POTION,1)
       st.set("cond","4")
       st.set("id","4")
       st.playSound("ItemSound.quest_middle")
     elif ItemsCount_BP > 0 :
       htmltext = "7033-02.htm"
   elif npcId == DARIN and cond >= 1 and ItemsCount_RK == 0 :
     if ItemsCount_DR > 0 :
       htmltext = "7048-09.htm"
     elif ItemsCount_BP > 0 :
       htmltext = "7048-10.htm"
       st.takeItems(BAULS_POTION,-1)
       st.giveItems(NECKLACE,1)
       st.unset("cond")
       st.setState(COMPLETED)
       st.playSound("ItemSound.quest_finish")
     else:
       htmltext = "7048-07.htm"
   return htmltext

qnum  = 1
qdef  = str(qnum) + "_LettersOfLove1"
qname = "Letters of Love"

QUEST     = Quest(qnum,qdef,qname)
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(DARIN)

CREATED.addTalkId(DARIN)

STARTED.addTalkId(ROXXY)
STARTED.addTalkId(BAULRO)
STARTED.addTalkId(DARIN)

COMPLETED.addTalkId(DARIN)

STARTED.addQuestDrop(DARIN,DARINGS_LETTER,1)
STARTED.addQuestDrop(DARIN,RAPUNZELS_KERCHIEF,1)
STARTED.addQuestDrop(DARIN,DARINGS_RECEIPT,1)
STARTED.addQuestDrop(DARIN,BAULS_POTION,1)

print "importing quests: " + str(qnum) + ": " + qname