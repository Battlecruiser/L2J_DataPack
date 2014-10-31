# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
COLEMAN = 7873

#MOBS
TAIK_SEEKER = 666
TAIK_LEADER = 669

#QUEST ITEMS
SUPPLY_ITEM = 5872
SUSPICIOUS_DOCUMENT = 5871
RECIPE_OF_SUPPLY = 5870

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "7873-2.htm" :
     st.set("cond","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "7873-6.htm" :
     st.takeItems(SUPPLY_ITEM,-1)
     st.takeItems(SUSPICIOUS_DOCUMENT,-1)
     st.takeItems(RECIPE_OF_SUPPLY,-1)
     st.playSound("ItemSound.quest_finish")
     st.exitQuest(1)
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   cond=st.getInt("cond")
   supplies = st.getQuestItemsCount(SUPPLY_ITEM)
   if cond == 0 :
     if st.getPlayer().getLevel() >= 52 :
       htmltext = "7873-0.htm"
     else:
       htmltext = "<html><head><body>Quest for characters level 52 or above.</body></html>"
       st.exitQuest(1)
   elif not supplies :
     htmltext = "7873-3.htm"
   elif supplies :
     DOCS = st.getQuestItemsCount(RECIPE_OF_SUPPLY) * 5000 # I dont have an info about reward on this doc
     REWARD = (supplies * 1600) + DOCS
     st.takeItems(SUPPLY_ITEM,-1)
     st.takeItems(RECIPE_OF_SUPPLY,-1)
     st.giveItems(57,REWARD)
     htmltext = "7873-5.htm"
   return htmltext

 def onKill (self,npc,st):
   st.giveItems(SUPPLY_ITEM,1)  
   if st.getRandom(10) == 1 :        # % chance is custom
     st.giveItems(SUSPICIOUS_DOCUMENT,1)
     if st.getQuestItemsCount(SUSPICIOUS_DOCUMENT) == 5 :
       st.takeItems(SUSPICIOUS_DOCUMENT,5)
       st.giveItems(RECIPE_OF_SUPPLY,1)
       st.playSound("ItemSound.quest_itemget")
   st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(360,"360_PlunderTheirSupplies","Plunder Their Supplies")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(COLEMAN)
CREATED.addTalkId(COLEMAN)
STARTED.addTalkId(COLEMAN)

STARTED.addKillId(TAIK_SEEKER)
STARTED.addKillId(TAIK_LEADER)

STARTED.addQuestDrop(COLEMAN,RECIPE_OF_SUPPLY,1)
STARTED.addQuestDrop(COLEMAN,SUPPLY_ITEM,1)
STARTED.addQuestDrop(COLEMAN,SUSPICIOUS_DOCUMENT,1)

print "importing quests: 360: Plunder Their Supplies"
