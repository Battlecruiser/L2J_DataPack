# Whisper of Dreams, part 2 version 0.1 
# by DrLecter
print "importing quests:",
import sys
from net.sf.l2j import Config
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#Quest info
QUEST_NUMBER,QUEST_NAME,QUEST_DESCRIPTION = 375,"WhisperOfDreams2","Whisper of Dreams, part 2"

#Variables
#Quest items drop rate in %
DROP_RATE=15*Config.RATE_DROP_QUEST
#Alternative rewards. Set this to a non-zero value and recipes will be 100% instead of 60%
ALT_RP_100=0

#Quest items
MSTONE,K_HORN,CH_SKULL=range(5887,5890)

#Quest collections
REWARDS = range(5346,5355,2)

#Messages
default   = "<html><head><body>I have nothing to say to you.</body></html>"

#NPCs
MANAKIA = 30515

#Mobs & Drop
DROPLIST = {20624:[CH_SKULL],20629:[K_HORN]}

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "30515-6.htm" :
       if st.getQuestItemsCount(MSTONE):
         st.takeItems(MSTONE,1)
         st.setState(STARTED)
         st.set("cond","1")
         st.playSound("ItemSound.quest_accept")
       else:
         htmltext=default
    elif event == "30515-7.htm" :
       st.playSound("ItemSound.quest_finish")
       st.exitQuest(1)
    return htmltext

 def onTalk (self,npc,st):
   htmltext = default
   id = st.getState()
   if id == CREATED :
      st.set("cond","0")
      htmltext = "30515-1.htm"
      if st.getPlayer().getLevel() < 60 :
         htmltext = "30515-2.htm"
         st.exitQuest(1)
      elif not st.getQuestItemsCount(MSTONE) :
         htmltext = "30515-3.htm"
         st.exitQuest(1)
   elif id == STARTED :
      if st.getQuestItemsCount(CH_SKULL)==st.getQuestItemsCount(K_HORN)==100 :
         st.takeItems(CH_SKULL,-1)
         st.takeItems(K_HORN,-1)
         item=REWARDS[st.getRandom(len(REWARDS))]
         if ALT_RP_100 : item += 1
         st.giveItems(item,1)
         htmltext="30515-4.htm"
      else :
         htmltext = "30515-5.htm"
   return htmltext

 def onKill (self,npc,st) :
    npcid = npc.getNpcId()
    item  = DROPLIST[npcid][0]
    count = st.getQuestItemsCount(item)
    if count < 100 and st.getRandom(100) < DROP_RATE :
       st.giveItems(item,1)
       if count + 1 >= 100 :
          st.playSound("ItemSound.quest_middle")
       else :
          st.playSound("ItemSound.quest_itemget")
    return  

# Quest class and state definition
QUEST       = Quest(QUEST_NUMBER, str(QUEST_NUMBER)+"_"+QUEST_NAME, QUEST_DESCRIPTION)

CREATED     = State('Start',     QUEST)
STARTED     = State('Started',   QUEST,True)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

# Quest NPC starter initialization
QUEST.addStartNpc(MANAKIA)
# Quest initialization
CREATED.addTalkId(MANAKIA)
STARTED.addTalkId(MANAKIA)

for i in DROPLIST.keys() :
  STARTED.addKillId(i)
  STARTED.addQuestDrop(i,DROPLIST[i][0],1)

print str(QUEST_NUMBER)+": "+QUEST_DESCRIPTION
