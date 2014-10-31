# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
LARA = 7063
BRIGHT = 7466
EMILY = 7620

#MOBS
WASP_WORKER = 934
WASP_LEADER = 935

#ITEMS
FRUIT_BASKET = 7136
AVELLAN_SPICE = 7137
HONEY_POUCH = 7138

#REWARDS
ADENA = 57
VARNISH = 1865

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   cond = st.getInt("cond")
   if event == "7620-1.htm" :
     st.set("cond","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "7620-3.htm" and st.getQuestItemsCount(HONEY_POUCH)==100:
     st.takeItems(HONEY_POUCH,100)
     st.set("cond","3")
   elif event == "7063-1.htm" and cond == 3:
     st.giveItems(AVELLAN_SPICE,1)
     st.set("cond","4")
   elif event == "7620-5.htm" and st.getQuestItemsCount(AVELLAN_SPICE):
     st.takeItems(AVELLAN_SPICE,1)
     st.set("cond","5")
   elif event == "7466-1.htm" and cond == 5:
     st.giveItems(FRUIT_BASKET,1)
     st.set("cond","6")
   elif event == "7620-7.htm" and st.getQuestItemsCount(FRUIT_BASKET):
     st.takeItems(FRUIT_BASKET,1)
     st.giveItems(ADENA,25000)
     st.giveItems(VARNISH,50)
     st.unset("cond")
     st.playSound("ItemSound.quest_finish")
     st.exitQuest(1)
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   npcId = npc.getNpcId()
   id = st.getState()
   cond = st.getInt("cond")
   if npcId == EMILY and cond == 0 :
     if st.getPlayer().getLevel() >= 34 and st.getPlayer().getLevel() <= 40 :
       htmltext = "7620-0.htm"
     else:
       htmltext = "7620-0a.htm"
       st.exitQuest(1)
   elif npcId == EMILY and st.getQuestItemsCount(HONEY_POUCH) == 100 :
     htmltext = "7620-2.htm"
   elif npcId == LARA and cond == 3 :
     htmltext = "7063-0.htm"
   elif npcId == EMILY and st.getQuestItemsCount(AVELLAN_SPICE) == 1 :
     htmltext = "7620-4.htm"
   elif npcId == BRIGHT and cond == 5 :
     htmltext = "7466-0.htm"
   elif npcId == EMILY and st.getQuestItemsCount(FRUIT_BASKET) == 1 :
     htmltext = "7620-6.htm"
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   count = st.getQuestItemsCount(HONEY_POUCH)
   if int(st.get("cond")) == 1 and count < 100 :
     st.giveItems(HONEY_POUCH,1)
     if count == 99 :
       st.playSound("ItemSound.quest_middle")
       st.set("cond","2")
     else :
       st.playSound("ItemSound.quest_itemget")	
   return

QUEST       = Quest(299,"299_GatherIngredientsForPie","Gather Ingredients For A Pie")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7620)
CREATED.addTalkId(7620)
STARTED.addTalkId(7620)
STARTED.addTalkId(7063)
STARTED.addTalkId(7466)
STARTED.addKillId(WASP_LEADER)
STARTED.addKillId(WASP_WORKER)
STARTED.addQuestDrop(WASP_WORKER,HONEY_POUCH,1)
STARTED.addQuestDrop(EMILY,AVELLAN_SPICE,1)
STARTED.addQuestDrop(EMILY,FRUIT_BASKET,1)

print "importing quests: 299: Gather Ingredients For A Pie"
