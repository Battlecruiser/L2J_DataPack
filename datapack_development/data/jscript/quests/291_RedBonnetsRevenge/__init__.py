# Maked by Mr. Have fun! Version 0.2
print "importing quests: 291: Red Bonnets Revenge"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

BLACK_WOLF_PELT_ID = 1482
GRANDMAS_PEARL_ID = 1502
GRANDMAS_MIRROR_ID = 1503
GRANDMAS_NECKLACE_ID = 1504
GRANDMAS_HAIRPIN_ID = 1505

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      htmltext = "7553-03.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7553 and int(st.get("cond"))==0 :
      if int(st.get("cond")) < 15 :
        if st.getPlayer().getLevel() < 4 :
          htmltext = "7553-01.htm"
        else:
          htmltext = "7553-02.htm"
          return htmltext
      else:
        htmltext = "7553-02.htm"
   elif npcId == 7553 and int(st.get("cond")) :
      if st.getQuestItemsCount(BLACK_WOLF_PELT_ID) < 40 :
        htmltext = "7553-04.htm"
      else:
        if int(st.get("id")) != 291 :
          st.set("id","291")
          htmltext = "7553-05.htm"
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.takeItems(BLACK_WOLF_PELT_ID,st.getQuestItemsCount(BLACK_WOLF_PELT_ID))
          n = st.getRandom(100)
          if n <= 2 :
            st.giveItems(GRANDMAS_PEARL_ID,1)
          elif n <= 20 :
            st.giveItems(GRANDMAS_MIRROR_ID,1)
          elif n <= 45 :
            st.giveItems(GRANDMAS_NECKLACE_ID,1)
          else:
            st.giveItems(GRANDMAS_HAIRPIN_ID,1)
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 317 :
      st.set("id","0")
      if int(st.get("cond")) and st.getQuestItemsCount(BLACK_WOLF_PELT_ID) < 40 :
        if st.getQuestItemsCount(BLACK_WOLF_PELT_ID) < 39 :
          st.playSound("ItemSound.quest_itemget")
        else:
          st.playSound("ItemSound.quest_middle")
        st.giveItems(BLACK_WOLF_PELT_ID,1)
   return

QUEST       = Quest(291,"291_RedBonnetsRevenge","Red Bonnets Revenge")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7553)

STARTING.addTalkId(7553)

STARTED.addTalkId(7553)

STARTED.addKillId(317)

STARTED.addQuestDrop(317,BLACK_WOLF_PELT_ID,1)
