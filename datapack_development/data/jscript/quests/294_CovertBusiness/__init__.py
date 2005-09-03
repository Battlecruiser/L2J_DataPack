# Maked by Mr. Have fun! Version 0.2
print "importing quests: 294: Covert Business"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

BAT_FANG_ID = 1491
RING_OF_RACCOON_ID = 1508
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
      htmltext = "7534-03.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7534 and int(st.get("cond"))==0 :
      if int(st.get("cond")) < 15 :
        if st.getPlayer().getRace().ordinal() != 4 :
          htmltext = "7534-00.htm"
        elif st.getPlayer().getLevel() >= 10 :
          htmltext = "7534-02.htm"
          return htmltext
        else:
          htmltext = "7534-01.htm"
      else:
        htmltext = "7534-01.htm"
   elif npcId == 7534 and int(st.get("cond"))==1 and st.getQuestItemsCount(BAT_FANG_ID)<100 :
      htmltext = "7534-04.htm"
   elif npcId == 7534 and int(st.get("cond"))==1 and st.getQuestItemsCount(BAT_FANG_ID)>=100 :
      if int(st.get("id")) != 294 :
        st.set("id","294")
        if st.getQuestItemsCount(RING_OF_RACCOON_ID) < 1 :
          htmltext = "7534-05.htm"
          st.giveItems(RING_OF_RACCOON_ID,1)
          st.addExpAndSp(0,60)
          st.takeItems(BAT_FANG_ID,st.getQuestItemsCount(BAT_FANG_ID))
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
        elif st.getQuestItemsCount(RING_OF_RACCOON_ID) >= 1 :
          htmltext = "7534-06.htm"
          st.giveItems(ADENA_ID,2400)
          st.addExpAndSp(0,60)
          st.takeItems(BAT_FANG_ID,st.getQuestItemsCount(BAT_FANG_ID))
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 480 :
      st.set("id","0")
      if int(st.get("cond")) and st.getQuestItemsCount(BAT_FANG_ID) < 100 :
        n = st.getRandom(10)
        if n > 5 :
          if st.getQuestItemsCount(BAT_FANG_ID) == 99 :
            st.giveItems(BAT_FANG_ID,1)
            st.playSound("ItemSound.quest_middle")
          else:
            st.giveItems(BAT_FANG_ID,1)
            st.playSound("ItemSound.quest_itemget")
        elif n > 2 :
          if st.getQuestItemsCount(BAT_FANG_ID) >= 98 :
            st.giveItems(BAT_FANG_ID,100-st.getQuestItemsCount(BAT_FANG_ID))
            st.playSound("ItemSound.quest_middle")
          else:
            st.giveItems(BAT_FANG_ID,2)
            st.playSound("ItemSound.quest_itemget")
        else:
          if st.getQuestItemsCount(BAT_FANG_ID) >= 97 :
            st.giveItems(BAT_FANG_ID,100-st.getQuestItemsCount(BAT_FANG_ID))
            st.playSound("ItemSound.quest_middle")
          else:
            st.giveItems(BAT_FANG_ID,3)
            st.playSound("ItemSound.quest_itemget")
   elif npcId == 370 :
      st.set("id","0")
      if int(st.get("cond")) and st.getQuestItemsCount(BAT_FANG_ID) < 100 :
        n = st.getRandom(10)
        if n > 6 :
          if st.getQuestItemsCount(BAT_FANG_ID) == 99 :
            st.giveItems(BAT_FANG_ID,1)
            st.playSound("ItemSound.quest_middle")
          else:
            st.giveItems(BAT_FANG_ID,1)
            st.playSound("ItemSound.quest_itemget")
        elif n > 3 :
          if st.getQuestItemsCount(BAT_FANG_ID) >= 98 :
            st.giveItems(BAT_FANG_ID,100-st.getQuestItemsCount(BAT_FANG_ID))
            st.playSound("ItemSound.quest_middle")
          else:
            st.giveItems(BAT_FANG_ID,2)
            st.playSound("ItemSound.quest_itemget")
        elif n > 1 :
          if st.getQuestItemsCount(BAT_FANG_ID) >= 97 :
            st.giveItems(BAT_FANG_ID,100-st.getQuestItemsCount(BAT_FANG_ID))
            st.playSound("ItemSound.quest_middle")
          else:
            st.giveItems(BAT_FANG_ID,3)
            st.playSound("ItemSound.quest_itemget")
        else:
          if st.getQuestItemsCount(BAT_FANG_ID) >= 96 :
            st.giveItems(BAT_FANG_ID,100-st.getQuestItemsCount(BAT_FANG_ID))
            st.playSound("ItemSound.quest_middle")
          else:
            st.giveItems(BAT_FANG_ID,4)
            st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(294,"294_CovertBusiness","Covert Business")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7534)

STARTED.addTalkId(7534)

STARTED.addKillId(370)
STARTED.addKillId(480)

STARTED.addQuestDrop(480,BAT_FANG_ID,1)
STARTED.addQuestDrop(370,BAT_FANG_ID,1)
