# Collector of Jewels - Version 0.1 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
NELL=30376
#Items
FLARE_SHARD=5882
FREEZING_SHARD=5883
ADENA=57
#MOBS & DROP
DROPLIST={20747:[FREEZING_SHARD,85], #Roxide
          20619:[FREEZING_SHARD,73], #Rowin Undine
          20616:[FREEZING_SHARD,60], #Undine Lakin
          20612:[FLARE_SHARD,77],    #Salamander Rowin
          20609:[FLARE_SHARD,77],    #Salamander Lakin
          20749:[FLARE_SHARD,85]     #Death Fire
          }

class Quest (JQuest) :

 def __init__(self,id,name,descr,party): JQuest.__init__(self,id,name,descr,party)

 def onEvent (self,event,st) :
   htmltext = event
   cond = st.getInt("cond")
   if event == "30376-03.htm" and cond == 0 :
     st.set("cond","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "30376-08.htm" :
     st.exitQuest(1)
     st.playSound("ItemSound.quest_finish")
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   cond=st.getInt("cond")
   flare = st.getQuestItemsCount(FLARE_SHARD)
   freezing = st.getQuestItemsCount(FREEZING_SHARD)
   if cond == 0 :
     if st.getPlayer().getLevel() >= 25 :
       htmltext = "30376-02.htm"
     else:
       htmltext = "30376-01.htm"
       st.exitQuest(1)
   elif cond == 1 :
     htmltext = "30376-04.htm"
   elif cond == 2 and flare == freezing == 50 :
     st.set("cond","3")
     st.giveItems(ADENA,12500)
     st.takeItems(FLARE_SHARD,-1)
     st.takeItems(FREEZING_SHARD,-1)
     htmltext = "30376-05.htm"
   elif cond == 3 :
     htmltext = "30376-09.htm"
   elif cond == 4 and flare == freezing == 200 :
     htmltext = "30376-10.htm"
     st.playSound("ItemSound.quest_finish")
     st.giveItems(ADENA,63500)
     st.takeItems(FLARE_SHARD,-1)
     st.takeItems(FREEZING_SHARD,-1)
     st.exitQuest(1)
   return htmltext

 def onKill (self,npc,st):
   cond = st.getInt("cond")
   if cond in [1,3] :
      item,chance=DROPLIST[npc.getNpcId()]
      if cond == 1 :
        max = 50
      elif cond == 3 :
        max == 200
      if st.getRandom(100) < chance and st.getQuestItemsCount(item) < max :
         st.giveItems(item,1)
         if st.getQuestItemsCount(FLARE_SHARD) == st.getQuestItemsCount(FREEZING_SHARD) == max :
            st.set("cond",str(cond+1))
            st.playSound("ItemSound.quest_middle")
         else :
            st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(369,"369_CollectorOfJewels","Collector of Jewels",True)
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(NELL)

CREATED.addTalkId(NELL)
STARTED.addTalkId(NELL)

for mob in DROPLIST.keys() :
    STARTED.addKillId(mob)

STARTED.addQuestDrop(NELL,FLARE_SHARD,1)
STARTED.addQuestDrop(NELL,FREEZING_SHARD,1)

print "importing quests: 369: Collector of Jewels"
