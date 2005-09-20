# Maked by Mr. Have fun! Version 0.2
print "importing quests: 276: Hestui Totem"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

KASHA_PARASITE_ID = 1480
KASHA_CRYSTAL_ID = 1481
HESTUIS_TOTEM_ID = 1500

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      htmltext = "7571-03.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7571 and int(st.get("cond"))==0 :
      if int(st.get("cond")) < 15 :
        if st.getPlayer().getRace().ordinal() != 3 :
          htmltext = "7571-00.htm"
        elif st.getPlayer().getLevel() < 15 :
          htmltext = "7571-01.htm"
        else:
          htmltext = "7571-02.htm"
          return htmltext
      else:
        htmltext = "7571-02.htm"
   elif npcId == 7571 and int(st.get("cond")) :
      if st.getQuestItemsCount(KASHA_CRYSTAL_ID) < 1 :
        htmltext = "7571-04.htm"
      else:
        if int(st.get("id")) != 276 :
          st.set("id","276")
          htmltext = "7571-05.htm"
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          n = st.getQuestItemsCount(KASHA_CRYSTAL_ID)
          st.takeItems(KASHA_CRYSTAL_ID,n)
          st.takeItems(KASHA_PARASITE_ID,st.getQuestItemsCount(KASHA_PARASITE_ID))
          st.giveItems(HESTUIS_TOTEM_ID,1)
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 479 :
      st.set("id","0")
      if int(st.get("cond")) and st.getQuestItemsCount(KASHA_CRYSTAL_ID) < 1 :
        n0 = st.getQuestItemsCount(KASHA_PARASITE_ID)
        n = st.getRandom(100)
        n2 = 1
        if n >= 79 :
          st.spawnNpc(5044)
          st.takeItems(KASHA_PARASITE_ID,n0)
          n2 = 0
        elif n >= 69 :
          if n0 <= 20 :
            st.spawnNpc(5044)
            st.takeItems(KASHA_PARASITE_ID,n0)
            n2 = 0
        elif n >= 59 :
          if n0 <= 15 :
            st.spawnNpc(5044)
            st.takeItems(KASHA_PARASITE_ID,n0)
            n2 = 0
        elif n >= 49 :
          if n0 <= 10 :
            st.spawnNpc(5044)
            st.takeItems(KASHA_PARASITE_ID,n0)
            n2 = 0
        elif n >= 39 :
          if n0 <= 2 :
            st.spawnNpc(5044)
            st.takeItems(KASHA_PARASITE_ID,n0)
            n2 = 0
        if n2 :
          st.giveItems(KASHA_PARASITE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 5044 :
      st.set("id","0")
      if int(st.get("cond")) and st.getQuestItemsCount(KASHA_CRYSTAL_ID) < 1 :
        st.giveItems(KASHA_CRYSTAL_ID,1)
        st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(276,"276_HestuiTotem","Hestui Totem")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7571)

STARTING.addTalkId(7571)

STARTED.addTalkId(7571)

STARTED.addKillId(479)
STARTED.addKillId(5044)

STARTED.addQuestDrop(5044,KASHA_CRYSTAL_ID,1)
STARTED.addQuestDrop(479,KASHA_PARASITE_ID,1)
