# Made by disKret & DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
HARLAN = 30074

#MOBS
ENKU_CHAMPION = 20291
ENKU_SHAMAN = 20292

#CHANCE FOR DROP
CHANCE = 100

#ITEMS
LEAF = 5893
STONE = 5894

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   leaf = st.getQuestItemsCount(LEAF)
   stone = st.getQuestItemsCount(STONE)
   if event == "30074-3.htm" :
     st.set("cond","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "30074-6.htm" :
     if leaf == 80 and stone == 100 :
        st.takeItems(LEAF,leaf)
        st.takeItems(STONE,stone)
        item = st.getRandom(3)
        st.giveItems(5956+item,1)
        htmltext = "30074-"+str(6+item)+".htm"
        st.playSound("ItemSound.quest_finish")
        st.exitQuest(1)
     else :
        htmltext = "30074-4.htm"
   elif event == "30074-2a.htm" :
     st.exitQuest(1)
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   cond = st.getInt("cond")
   leaf = st.getQuestItemsCount(LEAF)
   stone = st.getQuestItemsCount(STONE)
   if cond == 0 :
     if st.getPlayer().getLevel() >= 20 :
       htmltext = "30074-0.htm"
     else:
       htmltext = "30074-0a.htm"
       st.exitQuest(1)
   elif cond == 1 :
     if leaf < 80 and stone  < 100 :
       htmltext = "30074-4.htm"
     elif leaf == 80 and stone < 100 :
       htmltext = "30074-4a.htm"
     elif leaf < 80 and stone == 100 :
       htmltext = "30074-4b.htm"
   elif cond == 2 and leaf == 80 and stone == 100 :
       htmltext = "30074-5.htm"
   return htmltext

 def onKill (self,npc,st):
   chance = st.getRandom(100)
   npcId = npc.getNpcId()
   if npcId == ENKU_CHAMPION and chance < CHANCE and st.getQuestItemsCount(LEAF) < 80 :
      st.giveItems(LEAF,1)
      if st.getQuestItemsCount(LEAF) == 80 and st.getQuestItemsCount(STONE) == 100 :
         st.playSound("ItemSound.quest_middle")
         st.set("cond","2")
      else :
         st.playSound("ItemSound.quest_itemget")
   elif npcId == ENKU_SHAMAN and chance < CHANCE and st.getQuestItemsCount(STONE) < 100 :
      st.giveItems(STONE,1)
      if st.getQuestItemsCount(LEAF) == 80 and st.getQuestItemsCount(STONE) == 100 :
         st.playSound("ItemSound.quest_middle")
         st.set("cond","2")
      else :
         st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(379,"379_FantasyWine","Fantasy Wine")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(HARLAN)
CREATED.addTalkId(HARLAN)
STARTED.addTalkId(HARLAN)

STARTED.addKillId(ENKU_CHAMPION)
STARTED.addKillId(ENKU_SHAMAN)

STARTED.addQuestDrop(HARLAN,LEAF,1)
STARTED.addQuestDrop(HARLAN,STONE,1)

print "importing quests: 379: Fantasy Wine"
