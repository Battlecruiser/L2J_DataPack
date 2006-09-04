# Made by Mr. - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

PREDATORS_FANG = 1334
EMERALD = 1337
BLUE_ONYX = 1338
ONYX = 1339
GLASS_SHARD = 1336
REC_LEATHER_BOOT = 2176
REC_SPIRITSHOT = 3032

DROP={530:[[0,8,1]],534:[[4,10,1],[0,4,2]],537:[[0,10,2]],525:[[5,10,2],[0,5,3]]}

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "12091-03.htm" :
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
     if st.getPlayer().getRace().ordinal() != 1 :
       htmltext = "12091-00.htm"
       st.exitQuest(1)
     elif st.getPlayer().getLevel()<3 :
       htmltext = "12091-01.htm"
       st.exitQuest(1)
     else :
          htmltext = "12091-02.htm"
   else :
     if st.getQuestItemsCount(PREDATORS_FANG)<100 :
       htmltext = "12091-04.htm"
     else :
       st.takeItems(PREDATORS_FANG,-1)
       n = st.getRandom(100)
       if n<2 :
          st.giveItems(EMERALD,1)
          st.giveItems(REC_SPIRITSHOT,1)
          st.playSound("ItemSound.quest_jackpot")
       elif n<20 :
          st.giveItems(BLUE_ONYX,1)
          st.giveItems(REC_LEATHER_BOOT,1)
       elif n<45 :
          st.giveItems(ONYX,1)
       else:
          st.giveItems(GLASS_SHARD,1)
       htmltext = "12091-05.htm"
       st.exitQuest(1)
       st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npc,st):
   if st.getInt("cond") == 1:
      npcId = npc.getNpcId()
      count = st.getQuestItemsCount(PREDATORS_FANG)
      chance = st.getRandom(10)
      qty = 0
      for i in DROP[npcId] :
         if i[0] <= chance < i[1] :
            qty = i[2]
      if qty :
        if count+qty>100 :
          qty=100-count
        if count+qty==100 :
          st.playSound("ItemSound.quest_middle")
          st.set("cond","2")
        else :
          st.playSound("ItemSound.quest_itemget")
        st.giveItems(PREDATORS_FANG,qty)
   return

QUEST       = Quest(266,"266_PleaOfPixies","Plea Of Pixies")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(12091)

CREATED.addTalkId(12091)
STARTING.addTalkId(12091)
STARTED.addTalkId(12091)
COMPLETED.addTalkId(12091)

STARTED.addKillId(525)
STARTED.addKillId(530)
STARTED.addKillId(534)
STARTED.addKillId(537)

STARTED.addQuestDrop(530,PREDATORS_FANG,1)

print "importing quests: 266: Plea Of Pixies"
