#Made by Kerb
import sys

from net.sf.l2j import Config
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest 

qn = "644_GraveRobberAnnihilation" 

#Drop rate
DROP_CHANCE = 75
#Npc
KARUDA = 32017
#Items
ORC_GOODS = 8088
#Rewards
VARNISH_ID = 1865
ANIMAL_SKIN_ID = 1867
IRON_ORE_ID,COAL_ID,CHARCOAL_ID,ANIMAL_BONE_ID, = range(1869,1873)
#Mobs
MOBS = [ 22003,22004,22005,22006,22008 ]

class Quest (JQuest) :

 def onEvent (self,event,st) :
   cond = st.getInt("cond")
   htmltext = event
   if event == "32017-03.htm" :
      if st.getPlayer().getLevel() < 20 : 
         htmltext = "32017-02.htm"
         st.exitQuest(1)
      else :
         st.set("cond","1")
         st.setState(STARTING)
         st.playSound("ItemSound.quest_accept")
   elif event == "32017-06.htm" :
     st.setState(STARTED)
   elif event == "VARNISH" :
     st.takeItems(ORC_GOODS,-1)
     st.giveItems(VARNISH_ID,30)
     st.playSound("ItemSound.quest_finish")
     st.exitQuest(1)
     return
   elif event == "ANIMAL_SKIN" :
     st.takeItems(ORC_GOODS,-1)
     st.giveItems(ANIMAL_SKIN_ID,40)
     st.playSound("ItemSound.quest_finish")
     st.exitQuest(1)
     return
   elif event == "ANIMAL_BONE" :
     st.takeItems(ORC_GOODS,-1)
     st.giveItems(ANIMAL_BONE_ID,40)
     st.playSound("ItemSound.quest_finish")
     st.exitQuest(1)
     return
   elif event == "CHARCOAL" :
     st.takeItems(ORC_GOODS,-1)
     st.giveItems(CHARCOAL_ID,30)
     st.playSound("ItemSound.quest_finish")
     st.exitQuest(1)
     return
   elif event == "COAL" :
     st.takeItems(ORC_GOODS,-1)
     st.giveItems(COAL_ID,30)
     st.playSound("ItemSound.quest_finish")
     st.exitQuest(1)
     return
   elif event == "IRON_ORE" :
     st.takeItems(ORC_GOODS,-1)
     st.giveItems(IRON_ORE_ID,30)
     st.playSound("ItemSound.quest_finish")
     st.exitQuest(1)
     return

   return htmltext

 def onTalk (self,npc,player):
   htmltext = "<html><body>You are either not carrying out your quest or don't meet the criteria.</body></html>"
   st = player.getQuestState(qn)
   if st :
     npcId = npc.getNpcId()
     id = st.getState()
     cond = st.getInt("cond")
     if id == STARTED :
       if st.getQuestItemsCount(ORC_GOODS) == 120 : 
          htmltext = "32017-06.htm"
       else :
          htmltext = "32017-01.htm"
     elif cond == 0 :
         htmltext = "32017-01.htm"
     elif cond == 1 :
         htmltext = "32017-04.htm"
     elif cond == 2 :
         htmltext = "32017-05.htm"
   return htmltext

 def onKill (self,npc,player):
   partyMember = self.getRandomPartyMember(player,"1")
   if not partyMember: return
   st = partyMember.getQuestState(qn)
   if st :
      if st.getState() == STARTING :
         count = st.getQuestItemsCount(ORC_GOODS)
         if st.getInt("cond") == 1 and count < 120 :
            chance = DROP_CHANCE * Config.RATE_DROP_QUEST
            numItems, chance = divmod(chance,100)
            if st.getRandom(100) < chance : 
               numItems += 1
            if numItems :
               if count + numItems >= 120 :
                  numItems = 120 - count
                  st.playSound("ItemSound.quest_middle")
                  st.set("cond","2")
               else:
                  st.playSound("ItemSound.quest_itemget")   
               st.giveItems(ORC_GOODS,int(numItems))       
   return


QUEST       = Quest(644, qn, "Grave Robber Annihilation")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting',  QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(KARUDA)
QUEST.addTalkId(KARUDA) 

for i in MOBS :
  QUEST.addKillId(i)

STARTED.addQuestDrop(KARUDA,ORC_GOODS,1)