# Maked by Mr. Have fun! Version 0.2
print "importing quests: 273: Invaders Of Holyland"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

BLACK_SOULSTONE_ID = 1475
RED_SOULSTONE_ID = 1476
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
      htmltext = "7566-03.htm"
    elif event == "7566_1" :
          htmltext = "7566-07.htm"
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
    elif event == "7566_2" :
          htmltext = "7566-08.htm"
          st.set("cond","1")
          st.setState(STARTED)
          st.playSound("ItemSound.quest_accept")
    return htmltext


 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7566 and int(st.get("cond"))==0 :
      if int(st.get("cond")) < 15 :
        if st.getPlayer().getRace().ordinal() != 3 :
          htmltext = "7566-00.htm"
          st.exitQuest(1)
        elif st.getPlayer().getLevel() < 6 :
          htmltext = "7566-01.htm"
          st.exitQuest(1)
        else:
          htmltext = "7566-02.htm"
          return htmltext
      else:
        htmltext = "7566-01.htm"
        st.exitQuest(1)
   elif npcId == 7566 and int(st.get("cond")) :
      if int(st.get("id")) != 273 :
        st.set("id","273")
        if st.getQuestItemsCount(BLACK_SOULSTONE_ID)+st.getQuestItemsCount(RED_SOULSTONE_ID) == 0 :
          htmltext = "7566-04.htm"
        elif st.getQuestItemsCount(RED_SOULSTONE_ID) == 0 :
          htmltext = "7566-05.htm"
          n = st.getQuestItemsCount(BLACK_SOULSTONE_ID)
          st.giveItems(ADENA_ID,n*5)
          st.takeItems(BLACK_SOULSTONE_ID,n)
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
        else:
          htmltext = "7566-06.htm"
          n = st.getQuestItemsCount(BLACK_SOULSTONE_ID)
          if n :
            st.giveItems(ADENA_ID,n*5)
            st.takeItems(BLACK_SOULSTONE_ID,n)
          n = st.getQuestItemsCount(RED_SOULSTONE_ID)
          st.giveItems(ADENA_ID,n*50)
          st.takeItems(RED_SOULSTONE_ID,n)
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 311 :
      st.set("id","0")
      if int(st.get("cond")) :
        n = st.getRandom(100)
        if n <= 90 :
          st.giveItems(BLACK_SOULSTONE_ID,1)
        else:
          st.giveItems(RED_SOULSTONE_ID,1)
        st.playSound("ItemSound.quest_itemget")
   elif npcId == 312 :
      st.set("id","0")
      if int(st.get("cond")) :
        n = st.getRandom(100)
        if n <= 87 :
          st.giveItems(BLACK_SOULSTONE_ID,1)
        else:
          st.giveItems(RED_SOULSTONE_ID,1)
        st.playSound("ItemSound.quest_itemget")
   elif npcId == 313 :
      st.set("id","0")
      if int(st.get("cond")) :
        n = st.getRandom(100)
        if n <= 77 :
          st.giveItems(BLACK_SOULSTONE_ID,1)
        else:
          st.giveItems(RED_SOULSTONE_ID,1)
        st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(273,"273_InvadersOfHolyland","Invaders Of Holyland")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7566)

STARTING.addTalkId(7566)

STARTED.addTalkId(7566)

STARTED.addKillId(311)
STARTED.addKillId(312)
STARTED.addKillId(313)

STARTED.addQuestDrop(311,BLACK_SOULSTONE_ID,1)
STARTED.addQuestDrop(312,BLACK_SOULSTONE_ID,1)
STARTED.addQuestDrop(313,BLACK_SOULSTONE_ID,1)
STARTED.addQuestDrop(311,RED_SOULSTONE_ID,1)
STARTED.addQuestDrop(312,RED_SOULSTONE_ID,1)
STARTED.addQuestDrop(313,RED_SOULSTONE_ID,1)
