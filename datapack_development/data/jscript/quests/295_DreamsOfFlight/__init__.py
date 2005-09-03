# Maked by Mr. Have fun! Version 0.2
print "importing quests: 295: Dreams Of Flight"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

FLOATING_STONE_ID = 1492
RING_OF_FIREFLY_ID = 1509
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      htmltext = "7536-03.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7536 and int(st.get("cond"))==0 :
      if int(st.get("cond")) < 15 :
        if st.getPlayer().getLevel() >= 11 :
          htmltext = "7536-02.htm"
          return htmltext
        else:
          htmltext = "7536-01.htm"
      else:
        htmltext = "7536-01.htm"
   elif npcId == 7536 and int(st.get("cond"))==1 and st.getQuestItemsCount(FLOATING_STONE_ID)<50 :
      htmltext = "7536-04.htm"
   elif npcId == 7536 and int(st.get("cond"))==1 and st.getQuestItemsCount(FLOATING_STONE_ID)>=50 :
      if int(st.get("id")) != 295 :
        st.set("id","295")
        if st.getQuestItemsCount(RING_OF_FIREFLY_ID) < 1 :
          htmltext = "7536-05.htm"
          st.giveItems(RING_OF_FIREFLY_ID,1)
          st.addExpAndSp(0,60)
          st.takeItems(FLOATING_STONE_ID,st.getQuestItemsCount(FLOATING_STONE_ID))
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
        elif st.getQuestItemsCount(RING_OF_FIREFLY_ID) >= 1 :
          htmltext = "7536-06.htm"
          st.giveItems(ADENA_ID,2400)
          st.addExpAndSp(0,60)
          st.takeItems(FLOATING_STONE_ID,st.getQuestItemsCount(FLOATING_STONE_ID))
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 153 :
      st.set("id","0")
      if int(st.get("cond")) and st.getQuestItemsCount(FLOATING_STONE_ID) < 50 :
        if st.getRandom(100) > 25 :
          if st.getQuestItemsCount(FLOATING_STONE_ID) == 49 :
            st.giveItems(FLOATING_STONE_ID,1)
            st.playSound("ItemSound.quest_middle")
          else:
            st.giveItems(FLOATING_STONE_ID,1)
            st.playSound("ItemSound.quest_itemget")
        else:
          if st.getQuestItemsCount(FLOATING_STONE_ID) >= 48 :
            st.giveItems(FLOATING_STONE_ID,50-st.getQuestItemsCount(FLOATING_STONE_ID))
            st.playSound("ItemSound.quest_middle")
          else:
            st.giveItems(FLOATING_STONE_ID,2)
            st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(295,"295_DreamsOfFlight","Dreams Of Flight")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7536)

STARTED.addTalkId(7536)

STARTED.addKillId(153)

STARTED.addQuestDrop(153,FLOATING_STONE_ID,1)
