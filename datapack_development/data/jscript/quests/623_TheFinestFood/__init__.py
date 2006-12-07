# The Finest Food - v0.1 by disKret & DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
JEREMY = 31521

#ITEMS
LEAF_OF_FLAVA,BUFFALO_MEAT,ANTELOPE_HORN = range(7199,7202)

#MOBS, DROPS, CHANCES & REWARDS
BUFFALO,FLAVA,ANTELOPE = [ 21315,21316,21318 ]
DROPLIST = {BUFFALO:[BUFFALO_MEAT,99],FLAVA:[LEAF_OF_FLAVA,99],ANTELOPE:[ANTELOPE_HORN,99]}
REWARDS = [[6849,25000],[6847,65000],[6851,25000],[0,73000]]

class Quest (JQuest) :

 def __init__(self,id,name,descr,party): JQuest.__init__(self,id,name,descr,party)

 def onEvent (self,event,st) :
   cond = st.getInt("cond")
   htmltext = event
   leaf = st.getQuestItemsCount(LEAF_OF_FLAVA)
   meat = st.getQuestItemsCount(BUFFALO_MEAT)
   horn = st.getQuestItemsCount(ANTELOPE_HORN)
   if event == "31521-03.htm" and cond == 0 :
     if st.getPlayer().getLevel() >= 71 :
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
     else :
        htmltext = "31521-02.htm"
        st.exitQuest(1)
   elif event == "31521-07.htm" :
     if cond == 2 and leaf == meat == horn == 100 :
        htmltext = "31521-06.htm"
        st.playSound("ItemSound.quest_finish")
        item,adena=REWARDS[st.getRandom(len(REWARDS))]
        st.giveItems(57,adena)
        if item :
           st.giveItems(item,1)
        else :
           st.addExpAndSp(230000,18250)
        st.takeItems(LEAF_OF_FLAVA,-1)
        st.takeItems(BUFFALO_MEAT,-1)
        st.takeItems(ANTELOPE_HORN,-1)
        st.exitQuest(1)
   return htmltext

 def onTalk (Self,npc,st) :
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   cond = st.getInt("cond")
   leaf = st.getQuestItemsCount(LEAF_OF_FLAVA)
   meat = st.getQuestItemsCount(BUFFALO_MEAT)
   horn = st.getQuestItemsCount(ANTELOPE_HORN)
   if cond == 0 :
      htmltext = "31521-01.htm"
   elif cond == 1 :
      htmltext = "31521-05.htm"
   elif cond == 2 and leaf == meat == horn == 100 :
      htmltext = "31521-04.htm"
   return htmltext

 def onKill (self,npc,st) :
   cond = st.getInt("cond")
   item,chance = DROPLIST[npc.getNpcId()]
   if st.getRandom(100) < chance and st.getQuestItemsCount(item) < 100 :
      st.giveItems(item,1)
      if st.getQuestItemsCount(LEAF_OF_FLAVA) == st.getQuestItemsCount(BUFFALO_MEAT) == st.getQuestItemsCount(ANTELOPE_HORN) == 100 :
         st.set("cond","2")
         st.playSound("ItemSound.quest_middle")
      else :
         st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(623,"623_TheFinestFood","The Finest Food",True)
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(JEREMY)
CREATED.addTalkId(JEREMY)
STARTED.addTalkId(JEREMY)

for mob in DROPLIST.keys() :
  STARTED.addKillId(mob)

for item in range(7199,7202):
    STARTED.addQuestDrop(JEREMY,item,1)

print "importing quests: 623: The Finest Food"
