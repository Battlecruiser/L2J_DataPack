# Maked by Mr. Have fun! Version 0.2
print "importing quests: 275: Black Winged Spies"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

DARKWING_BAT_FANG_ID = 1478
VARANGKAS_PARASITE_ID = 1479
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
      htmltext = "7567-03.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7567 and int(st.get("cond"))==0 :
      if int(st.get("cond")) < 15 :
        if st.getPlayer().getRace().ordinal() != 3 :
          htmltext = "7567-00.htm"
        elif st.getPlayer().getLevel() < 11 :
          htmltext = "7567-01.htm"
        else:
          htmltext = "7567-02.htm"
          return htmltext
      else:
        htmltext = "7567-02.htm"
   elif npcId == 7567 and int(st.get("cond")) :
      if st.getQuestItemsCount(DARKWING_BAT_FANG_ID) < 70 :
        htmltext = "7567-04.htm"
      else:
        if int(st.get("id")) != 275 :
          st.set("id","275")
          htmltext = "7567-05.htm"
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.giveItems(ADENA_ID,st.getQuestItemsCount(DARKWING_BAT_FANG_ID)*50)
          st.takeItems(DARKWING_BAT_FANG_ID,st.getQuestItemsCount(DARKWING_BAT_FANG_ID))
          st.takeItems(VARANGKAS_PARASITE_ID,st.getQuestItemsCount(VARANGKAS_PARASITE_ID))
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 316 :
      st.set("id","0")
      if int(st.get("cond")) and st.getQuestItemsCount(DARKWING_BAT_FANG_ID) < 70 :
        if st.getQuestItemsCount(DARKWING_BAT_FANG_ID) < 69 :
          st.playSound("ItemSound.quest_itemget")
        else:
          st.playSound("ItemSound.quest_middle")
        st.giveItems(DARKWING_BAT_FANG_ID,1)
        n = st.getRandom(100)
        if st.getQuestItemsCount(DARKWING_BAT_FANG_ID) > 10 and st.getQuestItemsCount(DARKWING_BAT_FANG_ID) < 66 and n < 10 :
          st.spawnNpc(5043)
          st.giveItems(VARANGKAS_PARASITE_ID,1)
   elif npcId == 5043 :
      st.set("id","0")
      if int(st.get("cond")) and st.getQuestItemsCount(DARKWING_BAT_FANG_ID) < 66 and st.getQuestItemsCount(VARANGKAS_PARASITE_ID) > 0 :
        if st.getQuestItemsCount(DARKWING_BAT_FANG_ID) < 65 :
          st.playSound("ItemSound.quest_itemget")
        else:
          st.playSound("ItemSound.quest_middle")
        st.giveItems(DARKWING_BAT_FANG_ID,5)
        n = st.getQuestItemsCount(VARANGKAS_PARASITE_ID)
        st.takeItems(VARANGKAS_PARASITE_ID,n)
   return

QUEST       = Quest(275,"275_BlackWingedSpies","Black Winged Spies")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7567)

STARTING.addTalkId(7567)

STARTED.addTalkId(7567)

STARTED.addKillId(316)
STARTED.addKillId(5043)

STARTED.addQuestDrop(316,DARKWING_BAT_FANG_ID,1)
STARTED.addQuestDrop(5043,DARKWING_BAT_FANG_ID,1)
STARTED.addQuestDrop(316,VARANGKAS_PARASITE_ID,1)
