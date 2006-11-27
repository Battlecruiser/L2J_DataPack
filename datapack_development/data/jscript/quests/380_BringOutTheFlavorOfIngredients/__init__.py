# Made by disKret & DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
ROLLANT = 30069

#MOBS
DIRE_WOLF = 20205
KADIF_WEREWOLF = 20206
GIANT_MIST_LEECH = 20225

#ITEMS
RITRONS_FRUIT,MOON_FACE_FLOWER,LEECH_FLUIDS = range(5895,5898)
ANTIDOTE = 1831
RITRON_JELLY = 5960
JELLY_RECIPE = 5959

#mob:[chance,item,max]
DROPLIST = {
DIRE_WOLF:[10,RITRONS_FRUIT,4],
KADIF_WEREWOLF:[50,MOON_FACE_FLOWER,20],
GIANT_MIST_LEECH:[50,LEECH_FLUIDS,10]
}

#CHANCE
RECIPE_CHANCE = 55

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "30069-4.htm" :
     st.set("cond","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "30069-12.htm" :
     if st.getInt("cond") == 6 :
        st.giveItems(JELLY_RECIPE,1)
        st.playSound("ItemSound.quest_finish")
     else :
        htmltext = "I'll squeeze the jelly from your eyes"
     st.exitQuest(1)
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   cond=st.getInt("cond")
   if cond == 0 :
     if st.getPlayer().getLevel() >= 24 :
       htmltext = "30069-1.htm"
     else:
       htmltext = "30069-0.htm"
       st.exitQuest(1)
   elif cond == 1 :
     htmltext = "30069-6.htm"
   elif cond == 2 :
     if st.getQuestItemsCount(ANTIDOTE) >= 2 and st.getQuestItemsCount(RITRONS_FRUIT) == 4 and st.getQuestItemsCount(MOON_FACE_FLOWER) == 20 and st.getQuestItemsCount(LEECH_FLUIDS) == 10 :
        st.takeItems(RITRONS_FRUIT,-1)
        st.takeItems(MOON_FACE_FLOWER,-1)
        st.takeItems(LEECH_FLUIDS,-1)
        st.takeItems(ANTIDOTE,2)
        st.set("cond","3")
        htmltext = "30069-7.htm"
     else :
        htmltext = "30069-6.htm"
   elif cond == 3 :
     st.set("cond","4")
     htmltext = "30069-8.htm"
   elif cond == 4 :
     st.set("cond","5")
     htmltext = "30069-9.htm"
   elif cond == 5 :
     st.set("cond","6")
     htmltext = "30069-10.htm"
   elif cond == 6 :
     st.giveItems(RITRON_JELLY,1)
     if st.getRandom(100) < RECIPE_CHANCE :
        htmltext = "30069-11.htm"
     else :
        htmltext = "30069-13.htm"
        st.playSound("ItemSound.quest_finish")
        st.exitQuest(1)
   return htmltext

 def onKill (self,npc,st):
   if st.getInt("cond") == 1 :
      chance,item,max = DROPLIST[npc.getNpcId()]
      if st.getRandom(100) < chance and st.getQuestItemsCount(item) < max :
         st.giveItems(item,1)
         if st.getQuestItemsCount(RITRONS_FRUIT) == 4 and st.getQuestItemsCount(MOON_FACE_FLOWER) == 20 and st.getQuestItemsCount(LEECH_FLUIDS) == 10 :
            st.set("cond","2")
            st.playSound("ItemSound.quest_middle")
         else :
            st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(380,"380_BringOutTheFlavorOfIngredients","Bring Out The Flavor Of Ingredients")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(ROLLANT)

CREATED.addTalkId(ROLLANT)
STARTED.addTalkId(ROLLANT)

for mob in DROPLIST.keys():
    STARTED.addKillId(mob)

for item in range(5895,5898):
    STARTED.addQuestDrop(ROLLANT,item,1)

print "importing quests: 380: Bring Out The Flavor Of Ingredients"
