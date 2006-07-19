# Made by Mr. - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

RED_CROSS_BADGE,BLUE_CROSS_BADGE,BLACK_CROSS_BADGE, = range(1359,1362)
ADENA = 57
BLACK_LION_MARK = 1369

DROPLIST={
53:[RED_CROSS_BADGE,25],
437:[RED_CROSS_BADGE,25],
58:[RED_CROSS_BADGE,25],
61:[BLUE_CROSS_BADGE,25],
63:[BLUE_CROSS_BADGE,25],
436:[BLUE_CROSS_BADGE,25],
439:[BLUE_CROSS_BADGE,25],
438:[BLACK_CROSS_BADGE,35],
66:[BLACK_CROSS_BADGE,25],
}

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7435-03.htm" :
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
    elif event == "7435_1" :
      htmltext = "7435-07.htm"
      st.playSound("ItemSound.quest_finish")
      st.exitQuest(1)
    elif event == "7435_2" :
      htmltext = "7435-08.htm"
    return htmltext


 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   if int(st.get("cond"))==0 :
     if st.getPlayer().getLevel() >= 21 :
       htmltext = "7435-02.htm"
     else:
       htmltext = "7435-01.htm"
       st.exitQuest(1)
   else :
     red=st.getQuestItemsCount(RED_CROSS_BADGE)
     blue=st.getQuestItemsCount(BLUE_CROSS_BADGE)
     black=st.getQuestItemsCount(BLACK_CROSS_BADGE)
     if red+blue+black == 0 :
       htmltext = "7435-04.htm"
     else :
       htmltext = "7435-05.htm"
       st.giveItems(ADENA,60*red+65*blue+70*black)
       st.takeItems(RED_CROSS_BADGE,-1)
       st.takeItems(BLUE_CROSS_BADGE,-1)
       st.takeItems(BLACK_CROSS_BADGE,-1)
       if red+blue+black >= 100 :
         htmltext = "7435-09.htm"
         if st.getQuestItemsCount(BLACK_LION_MARK) ==  0 :
           st.giveItems(BLACK_LION_MARK,1)
           htmltext = "7435-06.htm"
   return htmltext

 def onKill (self,npc,st):
   item,chance=DROPLIST[npc.getNpcId()]
   if st.getRandom(100)<chance :
     st.giveItems(item,1)
     st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(326,"326_VanquishRemnants","Vanquish Remnants")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7435)

CREATED.addTalkId(7435)
STARTING.addTalkId(7435)
STARTED.addTalkId(7435)
COMPLETED.addTalkId(7435)

STARTED.addKillId(436)
STARTED.addKillId(437)
STARTED.addKillId(438)
STARTED.addKillId(439)
STARTED.addKillId(53)
STARTED.addKillId(58)
STARTED.addKillId(61)
STARTED.addKillId(63)
STARTED.addKillId(66)

STARTED.addQuestDrop(53,RED_CROSS_BADGE,1)
STARTED.addQuestDrop(61,BLUE_CROSS_BADGE,1)
STARTED.addQuestDrop(66,BLACK_CROSS_BADGE,1)

print "importing quests: 326: Vanquish Remnants"
