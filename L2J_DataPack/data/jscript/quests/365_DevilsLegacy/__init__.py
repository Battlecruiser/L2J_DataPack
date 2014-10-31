# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
RANDOLF = 7095
#MOBS
MOBS=[836,12545,845,1629,1630,12544]
#CHANCE OF DROP
CHANCE_OF_DROP = 20
#ITEMS
TREASURE_CHEST = 5873

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "7095-1.htm" :
     st.set("cond","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "7095-5.htm" :
     count = st.getQuestItemsCount(TREASURE_CHEST)
     if count :
        reward = (count*1600)
        st.takeItems(TREASURE_CHEST,-1)
        st.giveItems(57,reward)
     else:
        htmltext="You don't have required items"
   elif event == "7095-6.htm" :
     st.playSound("ItemSound.quest_finish")
     st.exitQuest(1)
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   cond=st.getInt("cond")
   if cond == 0 :
     if st.getPlayer().getLevel() >= 39 :
       htmltext = "7095-0.htm"
     else :
       htmltext = "7095-0a.htm"
       st.exitQuest(1)
   elif cond == 1 :
     if not st.getQuestItemsCount(TREASURE_CHEST) :
        htmltext = "7095-2.htm"
     else :
        htmltext = "7095-4.htm"
   return htmltext

 def onKill (self,npc,st):
   chance = st.getRandom(100)
   if chance < CHANCE_OF_DROP :
     st.giveItems(TREASURE_CHEST,1)
     st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(365,"365_DevilsLegacy","Devil's Legacy")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(RANDOLF)
CREATED.addTalkId(RANDOLF)
STARTED.addTalkId(RANDOLF)
for mob in MOBS:
    STARTED.addKillId(mob)

STARTED.addQuestDrop(RANDOLF,TREASURE_CHEST,1)
print "importing quests: 365: Devil's Legacy"
