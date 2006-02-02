# Illegitimate Child Of A Goddess version 0.1 
# by DrLecter
print "importing quests:",
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#Quest info
QUEST_NUMBER,QUEST_NAME,QUEST_DESCRIPTION = 358,"IllegitimateChildOfAGoddess","Illegitimate Child Of A Goddess"

#Variables
DROP_RATE=12  #in %
REQUIRED=108 #how many items will be paid for a reward (affects onkill sounds too)

#Quest items
SN_SCALE = 5868

#Rewards
REWARDS=range(6329,6340,2)+range(5364,5367,2)

#Changing this value to non-zero, will turn recipes to 100% instead of 70/60%
ALT_RP_100 = 0

#Messages
default   = "<html><head><body>I have nothing to say to you.</body></html>"

#NPCs
OLTLIN = 7862

#Mobs
MOBS = [ 672,673 ]

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7862-5.htm" :
       st.setState(STARTED)
       st.set("cond","1")
       st.playSound("ItemSound.quest_accept")
    elif event == "7862-6.htm" :
       st.exitQuest(1)
    elif event == "7862-7.htm" :
       if st.getQuestItemsCount(SN_SCALE) >= REQUIRED :
          st.takeItems(SN_SCALE,REQUIRED)
          item=REWARDS[st.getRandom(len(REWARDS))]
          if ALT_RP_100: item +=1
          st.giveItems(item ,1)
          st.exitQuest(1)
          st.playSound("ItemSound.quest_finish")
       else :
          htmltext = "7862-4.htm"
    return htmltext

 def onTalk (self,npc,st):
   htmltext = default
   id = st.getState()
   if id == CREATED :
      st.set("cond","0")
      if st.getPlayer().getLevel() < 63 :
         st.exitQuest(1)
         htmltext = "7862-1.htm"
      else :
         htmltext = "7862-2.htm"
   elif id == STARTED :
      if st.getQuestItemsCount(SN_SCALE) >= REQUIRED :
         htmltext = "7862-3.htm"
      else :
         htmltext = "7862-4.htm"
   return htmltext

 def onKill (self,npc,st) :
     count = st.getQuestItemsCount(SN_SCALE)
     if count < REQUIRED and st.getRandom(100) < DROP_RATE :
        st.giveItems(SN_SCALE,1)
        if count + 1 == REQUIRED :
           st.playSound("ItemSound.quest_middle")
           st.set("cond","2")
        else :
           st.playSound("ItemSound.quest_itemget")
     return

# Quest class and state definition
QUEST       = Quest(QUEST_NUMBER, str(QUEST_NUMBER)+"_"+QUEST_NAME, QUEST_DESCRIPTION)

CREATED     = State('Start',     QUEST)
STARTED     = State('Started',   QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

# Quest NPC starter initialization
QUEST.addStartNpc(OLTLIN)
# Quest initialization
CREATED.addTalkId(OLTLIN)
STARTED.addTalkId(OLTLIN)

for i in MOBS :
  STARTED.addKillId(i)
  STARTED.addQuestDrop(i,SN_SCALE,1)

print str(QUEST_NUMBER)+": "+QUEST_DESCRIPTION
