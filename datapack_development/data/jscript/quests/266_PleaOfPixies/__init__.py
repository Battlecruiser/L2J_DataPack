# Maked by Mr. Have fun! Version 0.2
print "importing quests: 266: Plea Of Pixies"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

PREDATORS_FANG_ID = 1334
EMERALD_ID = 1206
BLUE_ONYX_ID = 1338
ONYX_ID = 695
GLASS_SHARD_ID = 1336

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      htmltext = "12091-03.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 12091 and int(st.get("cond"))==0 :
      if int(st.get("cond"))<15 :
        if st.getPlayer().getLevel() >= 3 and st.getPlayer().getRace().ordinal() == 1 :
          htmltext = "12091-02.htm"
          return htmltext
        elif st.getPlayer().getRace().ordinal() != 1 :
          htmltext = "12091-00.htm"
        elif st.getPlayer().getLevel()<3 :
          htmltext = "12091-01.htm"
      else:
        htmltext = "12091-01.htm"
   elif npcId == 12091 and int(st.get("cond"))==1 and st.getQuestItemsCount(PREDATORS_FANG_ID)<100 :
      htmltext = "12091-04.htm"
   elif npcId == 12091 and (int(st.get("cond"))==1) and (st.getQuestItemsCount(PREDATORS_FANG_ID)>=100) :
      if int(st.get("id")) != 266 :
        st.set("id","266")
        st.takeItems(PREDATORS_FANG_ID,st.getQuestItemsCount(PREDATORS_FANG_ID))
        n = st.getRandom(100)
        if n<2 :
          st.giveItems(EMERALD_ID,1)
          st.playSound("ItemSound.quest_jackpot")
        elif n<20 :
          st.giveItems(BLUE_ONYX_ID,1)
        elif n<45 :
          st.giveItems(ONYX_ID,1)
        else:
          st.giveItems(GLASS_SHARD_ID,1)
        htmltext = "12091-05.htm"
        st.set("cond","0")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 530 :
        st.set("id","0")
        if st.getQuestItemsCount(PREDATORS_FANG_ID)<100 and int(st.get("cond")) :
          if st.getRandom(10)<8 :
            st.giveItems(PREDATORS_FANG_ID,1)
            if st.getQuestItemsCount(PREDATORS_FANG_ID) == 100 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 534 :
        st.set("id","0")
        if st.getQuestItemsCount(PREDATORS_FANG_ID)<100 and int(st.get("cond")) :
          n = st.getRandom(10)
          if n<6 :
            st.giveItems(PREDATORS_FANG_ID,1)
            if st.getQuestItemsCount(PREDATORS_FANG_ID) == 100 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
          elif n<4 :
              st.giveItems(PREDATORS_FANG_ID,2)
              if st.getQuestItemsCount(PREDATORS_FANG_ID) == 100 :
                st.playSound("ItemSound.quest_middle")
              else:
                st.playSound("ItemSound.quest_itemget")
   elif npcId == 537 :
        st.set("id","0")
        if st.getQuestItemsCount(PREDATORS_FANG_ID)<100 and int(st.get("cond")) :
          st.giveItems(PREDATORS_FANG_ID,2)
          if st.getQuestItemsCount(PREDATORS_FANG_ID) == 100 :
            st.playSound("ItemSound.quest_middle")
          else:
            st.playSound("ItemSound.quest_itemget")
   elif npcId == 525 :
        st.set("id","0")
        if st.getQuestItemsCount(PREDATORS_FANG_ID)<100 and int(st.get("cond")) :
          n = st.getRandom(10)
          if n<5 :
            st.giveItems(PREDATORS_FANG_ID,2)
            if st.getQuestItemsCount(PREDATORS_FANG_ID) == 100 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
          else:
            st.giveItems(PREDATORS_FANG_ID,3)
            if st.getQuestItemsCount(PREDATORS_FANG_ID) == 100 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(266,"266_PleaOfPixies","Plea Of Pixies")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(12091)

STARTING.addTalkId(12091)

STARTED.addTalkId(12091)

STARTED.addKillId(525)
STARTED.addKillId(530)
STARTED.addKillId(534)
STARTED.addKillId(537)

STARTED.addQuestDrop(530,PREDATORS_FANG_ID,1)
STARTED.addQuestDrop(534,PREDATORS_FANG_ID,1)
STARTED.addQuestDrop(537,PREDATORS_FANG_ID,1)
STARTED.addQuestDrop(525,PREDATORS_FANG_ID,1)
