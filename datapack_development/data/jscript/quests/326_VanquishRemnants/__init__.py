# Maked by Mr. Have fun! Version 0.2
print "importing quests: 326: Vanquish Remnants"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

RED_CROSS_BADGE_ID = 1359
BLUE_CROSS_BADGE_ID = 1360
BLACK_CROSS_BADGE_ID = 1361
ADENA_ID = 57
BLACK_LION_MARK_ID = 1369

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        htmltext = "7435-03.htm"
    elif event == "7435_1" :
            htmltext = "7435-07.htm"
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
    elif event == "7435_2" :
            htmltext = "7435-08.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7435 and int(st.get("cond"))==0 :
        if int(st.get("cond"))<15 :
          if st.getPlayer().getLevel() >= 21 :
            htmltext = "7435-02.htm"
            return htmltext
          else:
            htmltext = "7435-01.htm"
            st.exitQuest(1)
        else:
          htmltext = "7435-01.htm"
          st.exitQuest(1)
   elif npcId == 7435 and int(st.get("cond"))==1 :
        if int(st.get("id")) != 326 :
          st.set("id","326")
          if st.getQuestItemsCount(RED_CROSS_BADGE_ID)+st.getQuestItemsCount(BLUE_CROSS_BADGE_ID)+st.getQuestItemsCount(BLACK_CROSS_BADGE_ID) == 0 :
            htmltext = "7435-04.htm"
          elif st.getQuestItemsCount(RED_CROSS_BADGE_ID)+st.getQuestItemsCount(BLUE_CROSS_BADGE_ID)+st.getQuestItemsCount(BLACK_CROSS_BADGE_ID)<100 :
            st.giveItems(ADENA_ID,60*st.getQuestItemsCount(RED_CROSS_BADGE_ID)+65*st.getQuestItemsCount(BLUE_CROSS_BADGE_ID)+70*st.getQuestItemsCount(BLACK_CROSS_BADGE_ID))
            st.takeItems(RED_CROSS_BADGE_ID,st.getQuestItemsCount(RED_CROSS_BADGE_ID))
            st.takeItems(BLUE_CROSS_BADGE_ID,st.getQuestItemsCount(BLUE_CROSS_BADGE_ID))
            st.takeItems(BLACK_CROSS_BADGE_ID,st.getQuestItemsCount(BLACK_CROSS_BADGE_ID))
            htmltext = "7435-05.htm"
          elif st.getQuestItemsCount(RED_CROSS_BADGE_ID)+st.getQuestItemsCount(BLUE_CROSS_BADGE_ID)+st.getQuestItemsCount(BLACK_CROSS_BADGE_ID) >= 100 and st.getQuestItemsCount(BLACK_LION_MARK_ID) ==  0 :
            st.giveItems(BLACK_LION_MARK_ID,1)
            st.giveItems(ADENA_ID,60*st.getQuestItemsCount(RED_CROSS_BADGE_ID)+65*st.getQuestItemsCount(BLUE_CROSS_BADGE_ID)+70*st.getQuestItemsCount(BLACK_CROSS_BADGE_ID))
            st.takeItems(RED_CROSS_BADGE_ID,st.getQuestItemsCount(RED_CROSS_BADGE_ID))
            st.takeItems(BLUE_CROSS_BADGE_ID,st.getQuestItemsCount(BLUE_CROSS_BADGE_ID))
            st.takeItems(BLACK_CROSS_BADGE_ID,st.getQuestItemsCount(BLACK_CROSS_BADGE_ID))
            htmltext = "7435-06.htm"
          elif st.getQuestItemsCount(RED_CROSS_BADGE_ID)+st.getQuestItemsCount(BLUE_CROSS_BADGE_ID)+st.getQuestItemsCount(BLACK_CROSS_BADGE_ID) >= 100 and st.getQuestItemsCount(BLACK_LION_MARK_ID) > 0 :
            st.giveItems(ADENA_ID,60*st.getQuestItemsCount(RED_CROSS_BADGE_ID)+65*st.getQuestItemsCount(BLUE_CROSS_BADGE_ID)+70*st.getQuestItemsCount(BLACK_CROSS_BADGE_ID))
            st.takeItems(RED_CROSS_BADGE_ID,st.getQuestItemsCount(RED_CROSS_BADGE_ID))
            st.takeItems(BLUE_CROSS_BADGE_ID,st.getQuestItemsCount(BLUE_CROSS_BADGE_ID))
            st.takeItems(BLACK_CROSS_BADGE_ID,st.getQuestItemsCount(BLACK_CROSS_BADGE_ID))
            htmltext = "7435-09.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 53 :
      st.set("id","0")
      if int(st.get("cond")) :
        if st.getRandom(2)<1 :
          st.giveItems(RED_CROSS_BADGE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 437 :
      st.set("id","0")
      if int(st.get("cond")) :
        if st.getRandom(2)<1 :
          st.giveItems(RED_CROSS_BADGE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 58 :
      st.set("id","0")
      if int(st.get("cond")) :
        if st.getRandom(2)<1 :
          st.giveItems(RED_CROSS_BADGE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 61 :
      st.set("id","0")
      if int(st.get("cond")) :
        if st.getRandom(2)<1 :
          st.giveItems(BLUE_CROSS_BADGE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 63 :
      st.set("id","0")
      if int(st.get("cond")) :
        if st.getRandom(2)<1 :
          st.giveItems(BLUE_CROSS_BADGE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 436 :
      st.set("id","0")
      if int(st.get("cond")) :
        if st.getRandom(2)<1 :
          st.giveItems(BLUE_CROSS_BADGE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 439 :
      st.set("id","0")
      if int(st.get("cond")) :
        if st.getRandom(2)<1 :
          st.giveItems(BLUE_CROSS_BADGE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 438 :
      st.set("id","0")
      if int(st.get("cond")) :
        if st.getRandom(10)<7 :
          st.giveItems(BLACK_CROSS_BADGE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 66 :
      st.set("id","0")
      if int(st.get("cond")) :
        if st.getRandom(2)<1 :
          st.giveItems(BLACK_CROSS_BADGE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(326,"326_VanquishRemnants","Vanquish Remnants")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7435)

STARTING.addTalkId(7435)

STARTED.addTalkId(7435)

STARTED.addKillId(436)
STARTED.addKillId(437)
STARTED.addKillId(438)
STARTED.addKillId(439)
STARTED.addKillId(53)
STARTED.addKillId(58)
STARTED.addKillId(61)
STARTED.addKillId(63)
STARTED.addKillId(66)

STARTED.addQuestDrop(53,RED_CROSS_BADGE_ID,1)
STARTED.addQuestDrop(437,RED_CROSS_BADGE_ID,1)
STARTED.addQuestDrop(58,RED_CROSS_BADGE_ID,1)
STARTED.addQuestDrop(61,BLUE_CROSS_BADGE_ID,1)
STARTED.addQuestDrop(63,BLUE_CROSS_BADGE_ID,1)
STARTED.addQuestDrop(436,BLUE_CROSS_BADGE_ID,1)
STARTED.addQuestDrop(439,BLUE_CROSS_BADGE_ID,1)
STARTED.addQuestDrop(438,BLACK_CROSS_BADGE_ID,1)
STARTED.addQuestDrop(66,BLACK_CROSS_BADGE_ID,1)
