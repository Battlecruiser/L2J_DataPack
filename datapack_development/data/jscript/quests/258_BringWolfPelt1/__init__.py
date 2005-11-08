# Maked by Mr. Have fun! Version 0.2
print "importing quests: 258: Bring Wolf Pelt1"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

WOLF_PELT_ID = 702
LEATHER_TUNIC_ID = 429
LEATHER_CAP_ID = 42
CLOTH_CAP_ID = 41
HOSE_ID = 462
LEATHER_SHIELD_ID = 18

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      htmltext = "7001-03.htm"
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
   if npcId == 7001 and int(st.get("cond"))==0 :
      if int(st.get("cond"))<15 :
        if st.getPlayer().getLevel() >= 3 :
          htmltext = "7001-02.htm"
          return htmltext
        else:
          htmltext = "7001-01.htm"
          st.exitQuest(1)
      else:
        htmltext = "7001-01.htm"
        st.exitQuest(1)
   elif npcId == 7001 and (int(st.get("cond"))==1) and (st.getQuestItemsCount(WOLF_PELT_ID)>=0) and (st.getQuestItemsCount(WOLF_PELT_ID)<40) :
      htmltext = "7001-05.htm"
   elif npcId == 7001 and (int(st.get("cond"))==1) and (st.getQuestItemsCount(WOLF_PELT_ID)>=40) :
        if int(st.get("id")) != 258 :
          st.set("id","258")
          st.takeItems(WOLF_PELT_ID,40)
          n = st.getRandom(16)
          if n == 0 :
            st.giveItems(LEATHER_TUNIC_ID,1)
            st.playSound("ItemSound.quest_jackpot")
          elif n < 6 :
            st.giveItems(LEATHER_CAP_ID,1)
          elif n < 9 :
            st.giveItems(CLOTH_CAP_ID,1)
          elif n < 13 :
            st.giveItems(HOSE_ID,1)
          else:
            st.giveItems(LEATHER_SHIELD_ID,1)
          htmltext = "7001-06.htm"
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 120 :
        st.set("id","0")
        if st.getQuestItemsCount(WOLF_PELT_ID)<40 and int(st.get("cond")) :
          st.giveItems(WOLF_PELT_ID,1)
          if st.getQuestItemsCount(WOLF_PELT_ID) == 40 :
            st.playSound("ItemSound.quest_middle")
          else:
            st.playSound("ItemSound.quest_itemget")
   elif npcId == 442 :
        st.set("id","0")
        if st.getQuestItemsCount(WOLF_PELT_ID)<40 and int(st.get("cond")) :
          st.giveItems(WOLF_PELT_ID,1)
          if st.getQuestItemsCount(WOLF_PELT_ID) == 40 :
            st.playSound("ItemSound.quest_middle")
          else:
            st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(258,"258_BringWolfPelt1","Bring Wolf Pelt1")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7001)

STARTING.addTalkId(7001)

STARTED.addTalkId(7001)

STARTED.addKillId(120)
STARTED.addKillId(442)

STARTED.addQuestDrop(120,WOLF_PELT_ID,1)
STARTED.addQuestDrop(442,WOLF_PELT_ID,1)
