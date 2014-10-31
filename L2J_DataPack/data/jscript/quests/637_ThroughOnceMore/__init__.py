# Made by BiTi! v0.2
# v0.2.1 by DrLecter
import sys

from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "637_ThroughOnceMore"

#Drop rate
CHANCE=40
#Npc
FLAURON = 32010
#Items
VISITORSMARK,NECROHEART,MARK = 8064,8066,8067

class Quest (JQuest) :


 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if htmltext == "32010-04.htm" :
       st.set("cond","1")
       st.setState(STARTED)
       st.takeItems(VISITORSMARK,1)
       st.playSound("ItemSound.quest_accept")
    return htmltext

 def onTalk (self, npc, player):
   htmltext = "<html><head><body>I have nothing to say to you</body></html>"
   st = player.getQuestState(qn)
   if st :
     id = st.getState()
     cond = st.getInt("cond")
     if cond == 0 and id == CREATED :
        if st.getPlayer().getLevel()>72 and st.getQuestItemsCount(VISITORSMARK) :
           htmltext = "32010-02.htm"
        else:
           htmltext = "32010-01.htm"
           st.exitQuest(1)
     elif id == STARTED :
       if cond == 2 and st.getQuestItemsCount(NECROHEART)==10:
          htmltext = "32010-05.htm"
          st.takeItems(NECROHEART,10)
          st.giveItems(MARK,1)
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
       else :
          htmltext = "32010-04.htm"
   return htmltext

 def onKill (self, npc, player):
   st = player.getQuestState(qn)
   if st :
     if st.getState() == STARTED :
       count = st.getQuestItemsCount(NECROHEART)
       if st.getInt("cond")==1 and st.getRandom(100)<CHANCE and count<10 :
          st.giveItems(NECROHEART,1)
          if count == 9 :
             st.playSound("ItemSound.quest_middle")
             st.set("cond","2")
          else:
             st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(637,qn,"Through the Gate Once More")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(FLAURON)

QUEST.addTalkId(FLAURON)

for mob in range(21565,21568):
    QUEST.addKillId(mob)

STARTED.addQuestDrop(FLAURON,NECROHEART,1)

print "importing quests: 637: Through the Gate Once More"
