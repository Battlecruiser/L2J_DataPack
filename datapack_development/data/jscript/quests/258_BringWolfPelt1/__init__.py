# Made by Mr. Have fun! - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

WOLF_PELT = 702
REWARDS={429:[1,6],42:[1,19],41:[1,19],462:[1,19],18:[1,20],426:[1,5],29:[1,2],22:[1,2],390:[1,3]}

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "30001-03.htm" :
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
    return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   if int(st.get("cond"))==0 :
     if st.getPlayer().getLevel() >= 3 :
       htmltext = "30001-02.htm"
     else:
       htmltext = "30001-01.htm"
       st.exitQuest(1)
   else :
     if st.getQuestItemsCount(WOLF_PELT) < 40 :
       htmltext = "30001-05.htm"
     else :
       st.takeItems(WOLF_PELT,-1)
       count=0
       while not count :
          for item in REWARDS.keys() :
              qty,chance=REWARDS[item]
              if st.getRandom(100) < chance and count == 0 :
                 st.giveItems(item,st.getRandom(qty)+1)
                 count+=1
       if chance < 7 :
         st.playSound("ItemSound.quest_jackpot")
       htmltext = "30001-06.htm"
       st.exitQuest(1)
       st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npc,st):
   count = st.getQuestItemsCount(WOLF_PELT)
   if count<40 :
     st.giveItems(WOLF_PELT,1)
     if count == 39 :
       st.playSound("ItemSound.quest_middle")
       st.set("cond","2")
     else:
       st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(258,"258_BringWolfPelt1","Bring Wolf Pelt1")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30001)

CREATED.addTalkId(30001)
STARTING.addTalkId(30001)
STARTED.addTalkId(30001)
COMPLETED.addTalkId(30001)

STARTED.addKillId(20120)
STARTED.addKillId(20442)

STARTED.addQuestDrop(20120,WOLF_PELT,1)

print "importing quests: 258: Bring Wolf Pelt1"
