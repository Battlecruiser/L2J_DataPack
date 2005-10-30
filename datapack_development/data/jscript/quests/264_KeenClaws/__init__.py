# Maked by Mr. Have fun! Version 0.2
print "importing quests: 264: Keen Claws"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

WOLF_CLAW1_ID = 1367
WOODEN_HELMET_ID = 43
ADENA_ID = 57
LEATHER_SANDALS_ID = 36
HOSE_ID = 462
HEALING_POTION_ID = 1061
SHORT_GLOVES_ID = 48
CLOTH_SHOES_ID = 35

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      htmltext = "7136-03.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7136 and int(st.get("cond"))==0 :
      if int(st.get("cond"))<15 :
        if st.getPlayer().getLevel() >= 3 :
          htmltext = "7136-02.htm"
          return htmltext
        else:
          htmltext = "7136-01.htm"
          st.exitQuest(1)
      else:
        htmltext = "7136-01.htm"
        st.exitQuest(1)
   elif npcId == 7136 and int(st.get("cond"))==1 and st.getQuestItemsCount(WOLF_CLAW1_ID)<50 :
      htmltext = "7136-04.htm"
   elif npcId == 7136 and (int(st.get("cond"))==1) and (st.getQuestItemsCount(WOLF_CLAW1_ID)>=50) :
      if int(st.get("id")) != 264 :
        st.set("id","264")
        st.takeItems(WOLF_CLAW1_ID,st.getQuestItemsCount(WOLF_CLAW1_ID))
        n = st.getRandom(17)
        if n == 0 :
          st.giveItems(WOODEN_HELMET_ID,1)
          st.playSound("ItemSound.quest_jackpot")
        elif n<2 :
          st.giveItems(ADENA_ID,1000)
        elif n<5 :
          st.giveItems(LEATHER_SANDALS_ID,1)
        elif n<8 :
          st.giveItems(HOSE_ID,1)
          st.giveItems(ADENA_ID,50)
        elif n<11 :
          st.giveItems(HEALING_POTION_ID,1)
        elif n<14 :
          st.giveItems(SHORT_GLOVES_ID,1)
        else:
          st.giveItems(CLOTH_SHOES_ID,1)
        htmltext = "7136-05.htm"
        st.set("cond","0")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 3 :
        st.set("id","0")
        if st.getQuestItemsCount(WOLF_CLAW1_ID)<50 and int(st.get("cond")) :
          n = st.getRandom(20)
          if n<5 :
            if st.getQuestItemsCount(WOLF_CLAW1_ID) == 49 :
              st.giveItems(WOLF_CLAW1_ID,1)
              st.playSound("ItemSound.quest_middle")
            elif st.getQuestItemsCount(WOLF_CLAW1_ID) == 48 :
              st.giveItems(WOLF_CLAW1_ID,2)
              st.playSound("ItemSound.quest_middle")
            else:
              st.giveItems(WOLF_CLAW1_ID,2)
              st.playSound("ItemSound.quest_itemget")
          elif n<10 :
              st.giveItems(WOLF_CLAW1_ID,4)
              if st.getQuestItemsCount(WOLF_CLAW1_ID) == 46 :
                st.giveItems(WOLF_CLAW1_ID,4)
                st.playSound("ItemSound.quest_middle")
              elif st.getQuestItemsCount(WOLF_CLAW1_ID) == 47 :
                st.giveItems(WOLF_CLAW1_ID,3)
                st.playSound("ItemSound.quest_middle")
              elif st.getQuestItemsCount(WOLF_CLAW1_ID) == 48 :
                st.giveItems(WOLF_CLAW1_ID,2)
                st.playSound("ItemSound.quest_middle")
              elif st.getQuestItemsCount(WOLF_CLAW1_ID) == 49 :
                st.giveItems(WOLF_CLAW1_ID,1)
                st.playSound("ItemSound.quest_middle")
              else:
                st.giveItems(WOLF_CLAW1_ID,4)
                st.playSound("ItemSound.quest_itemget")
   elif npcId == 456 :
        st.set("id","0")
        if st.getQuestItemsCount(WOLF_CLAW1_ID)<50 and int(st.get("cond")) :
          n = st.getRandom(5)
          if n<4 :
            if st.getQuestItemsCount(WOLF_CLAW1_ID) == 49 :
              st.giveItems(WOLF_CLAW1_ID,1)
              st.playSound("ItemSound.quest_middle")
            else:
              st.giveItems(WOLF_CLAW1_ID,1)
              st.playSound("ItemSound.quest_itemget")
          else:
            if st.getQuestItemsCount(WOLF_CLAW1_ID) == 49 :
              st.giveItems(WOLF_CLAW1_ID,1)
              st.playSound("ItemSound.quest_middle")
            elif st.getQuestItemsCount(WOLF_CLAW1_ID) == 48 :
              st.giveItems(WOLF_CLAW1_ID,2)
              st.playSound("ItemSound.quest_middle")
            else:
              st.giveItems(WOLF_CLAW1_ID,2)
              st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(264,"264_KeenClaws","Keen Claws")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7136)

STARTING.addTalkId(7136)

STARTED.addTalkId(7136)

STARTED.addKillId(3)
STARTED.addKillId(456)

STARTED.addQuestDrop(3,WOLF_CLAW1_ID,1)
STARTED.addQuestDrop(456,WOLF_CLAW1_ID,1)
