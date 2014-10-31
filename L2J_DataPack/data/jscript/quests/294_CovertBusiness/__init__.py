# Made by Mr. - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

BAT_FANG = 1491
RING_OF_RACCOON = 1508
ADENA = 57
DROP = {
480:[[6,10,1],[3,6,2],[0,3,3]],
370:[[7,10,1],[4,7,2],[2,4,3],[0,2,4]]
}
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7534-03.htm" :
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
     if st.getPlayer().getRace().ordinal() != 4 :
       htmltext = "7534-00.htm"
       st.exitQuest(1)
     elif st.getPlayer().getLevel() >= 10 :
       htmltext = "7534-02.htm"
     else:
       htmltext = "7534-01.htm"
       st.exitQuest(1)
   else:
     if st.getQuestItemsCount(BAT_FANG)<100 :
       htmltext = "7534-04.htm"
     else :
       if st.getQuestItemsCount(RING_OF_RACCOON) ==0 :
         htmltext = "7534-05.htm"
         st.giveItems(RING_OF_RACCOON,1)
       else :
         htmltext = "7534-06.htm"
         st.giveItems(ADENA,2400)
       st.addExpAndSp(0,600)
       st.takeItems(BAT_FANG,-1)
       st.exitQuest(1)
       st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npc,st):
   if st.getInt("cond") == 1:
     npcId = npc.getNpcId()
     count=st.getQuestItemsCount(BAT_FANG)
     chance = st.getRandom(10)
     for i in DROP[npcId]:
        if i[0]<=chance<i[1]:
           qty=i[2]
     if count+qty>100 :
       qty=100-count
     if count+qty==100:
       st.playSound("ItemSound.quest_middle")
       st.set("cond","2")
     else :
       st.playSound("ItemSound.quest_itemget")
     st.giveItems(BAT_FANG,qty)
   return

QUEST       = Quest(294,"294_CovertBusiness","Covert Business")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7534)

CREATED.addTalkId(7534)
STARTING.addTalkId(7534)
STARTED.addTalkId(7534)
COMPLETED.addTalkId(7534)

STARTED.addKillId(370)
STARTED.addKillId(480)

STARTED.addQuestDrop(480,BAT_FANG,1)

print "importing quests: 294: Covert Business"
