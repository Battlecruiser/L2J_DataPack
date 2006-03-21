# Made by Mr. Have fun! - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

WOLF_CLAW = 1367
WOODEN_HELMET = 43
ADENA = 57
LEATHER_SANDALS = 36
HOSE = 462
HEALING_POTION = 1061
SHORT_GLOVES = 48
CLOTH_SHOES = 35

DROP={3:[[5,10,8],[0,5,2]],456:[[16,20,2],[0,16,1]]}

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7136-03.htm" :
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
       htmltext = "7136-02.htm"
     else:
       htmltext = "7136-01.htm"
       st.exitQuest(1)
   else:
     count=st.getQuestItemsCount(WOLF_CLAW)
     if count<50 :
       htmltext = "7136-04.htm"
     else :
       st.takeItems(WOLF_CLAW,-1)
       n = st.getRandom(17)
       if n == 0 :
          st.giveItems(WOODEN_HELMET,1)
          st.playSound("ItemSound.quest_jackpot")
       elif n<2 :
         st.giveItems(ADENA,1000)
       elif n<5 :
          st.giveItems(LEATHER_SANDALS,1)
       elif n<8 :
          st.giveItems(HOSE,1)
          st.giveItems(ADENA,50)
       elif n<11 :
         st.giveItems(HEALING_POTION,1)
       elif n<14 :
         st.giveItems(SHORT_GLOVES,1)
       else:
          st.giveItems(CLOTH_SHOES,1)
       htmltext = "7136-05.htm"
       st.exitQuest(1)
       st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npc,st):
   if st.getInt("cond") == 1:
      npcId = npc.getNpcId()
      count=st.getQuestItemsCount(WOLF_CLAW)
      chance = st.getRandom(20)
      qty=0
      for i in DROP[npcId]:
         if i[0]<=chance<i[1]:
            qty=i[2]
      if qty :
        if count+qty>50 :
          qty=50-count
        if count+qty==50:
          st.playSound("ItemSound.quest_middle")
          st.set("cond","2")
        else :
          st.playSound("ItemSound.quest_itemget")
        st.giveItems(WOLF_CLAW,qty)
   return

QUEST       = Quest(264,"264_KeenClaws","Keen Claws")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7136)

CREATED.addTalkId(7136)
STARTING.addTalkId(7136)
STARTED.addTalkId(7136)
COMPLETED.addTalkId(7136)

STARTED.addKillId(3)
STARTED.addKillId(456)

STARTED.addQuestDrop(3,WOLF_CLAW,1)

print "importing quests: 264: Keen Claws"

