# Made by Hawkin
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
CAPTAIN = 31553

#ITEMS
CLAWS = 7250
COIN = 7251

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)
 
 def onEvent (self,event,st) :
   htmltext = event
   if event == "31553-1.htm" :
     st.set("cond","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
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
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   npcId = npc.getNpcId()
   id = st.getState()
   cond = st.getInt("cond")
   if npcId == CAPTAIN :
      if cond == 0 :
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
    random = st.getRandom(1000)
    if npc.getNpcId() == 21508 :
        chance = 500
    if npc.getNpcId() == 21509 :
        chance = 431
    if npc.getNpcId() == 21510 :
        chance = 521
    if npc.getNpcId() == 21511 :
        chance = 576
    if npc.getNpcId() == 21512 :
        chance = 746
    if npc.getNpcId() == 21513 :
        chance = 530
    if npc.getNpcId() == 21514 :
        chance = 538
    if npc.getNpcId() == 21515 :
        chance = 545
    if npc.getNpcId() == 21516 :
        chance = 553
    if npc.getNpcId() == 21517 :
        chance = 560
    if random<chance :
         st.giveItems(CLAWS,1)
         st.playSound("ItemSound.quest_itemget")	
    return

QUEST       = Quest(629,"629_CleanUpTheSwampOfScreams","Clean Up the Swamp of Screams")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST,True)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(31553)

CREATED.addTalkId(31553)
STARTED.addTalkId(31553)

for mobs in range(21508,21517) :
  STARTED.addKillId(mobs)

print "importing quests: 629: Clean Up the Swamp of Screams"
