# Made by Hawkin
import sys
from net.sf.l2j import Config
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
CAPTAIN = 31553
#ITEMS
CLAWS = 7250
COIN = 7251
#CHANCES
MAX=1000
CHANCE={
    21508:500,
    21509:431,
    21510:521,
    21511:576,
    21512:746,
    21513:530,
    21514:538,
    21515:545,
    21516:553,
    21517:560
}

default="<html><head><body>I have nothing to say you</body></html>"

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)
 
 def onEvent (self,event,st) :
   htmltext = event
   if event == "31553-1.htm" :
     if st.getPlayer().getLevel() >= 66 :
       st.set("cond","1")
       st.setState(STARTED)
       st.playSound("ItemSound.quest_accept")
     else:
       htmltext=default
       st.exitQuest(1)
   elif event == "31553-3.htm" :
     if st.getQuestItemsCount(CLAWS) >= 100 :
       st.takeItems(CLAWS,100)
       st.giveItems(COIN,20)
     else :
       htmltext = "31553-3a.htm"
   elif event == "31553-5.htm" :
     st.playSound("ItemSound.quest_finish")
     st.exitQuest(1)
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = default
   npcId = npc.getNpcId()
   id = st.getState()
   cond = st.getInt("cond")
   if cond == 0 and (st.getQuestItemsCount(7246)==1 or st.getQuestItemsCount(7247)==1):
     if st.getPlayer().getLevel() >= 66 :
       htmltext = "31553-0.htm"
     else:
       htmltext = "31553-0a.htm"
       st.exitQuest(1)
   elif st.getQuestItemsCount(CLAWS) >= 100 :
     htmltext = "31553-2.htm"
   else :
     htmltext = "31553-1a.htm"
   return htmltext

 def onKill (self,npc,st):
    random = st.getRandom(MAX)
    chance = CHANCE[npc.getNpcId()]*Config.RATE_DROP_QUEST
    bonus = int(divmod(chance,MAX+1)[0])
    if random<chance :
       st.giveItems(CLAWS,1+bonus)
       if st.getQuestItemsCount(CLAWS) % 100 == 0 :
          st.playSound("ItemSound.quest_middle")
       else:
          st.playSound("ItemSound.quest_itemget")
    return

QUEST       = Quest(629,"629_CleanUpTheSwampOfScreams","Clean Up the Swamp of Screams")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST,True)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(CAPTAIN)

CREATED.addTalkId(CAPTAIN)
STARTED.addTalkId(CAPTAIN)

for mobs in range(21508,21518) :
  STARTED.addKillId(mobs)

STARTED.addQuestDrop(CAPTAIN,CLAWS,1)

print "importing quests: 629: Clean Up the Swamp of Screams"
