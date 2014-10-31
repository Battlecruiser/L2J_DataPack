# Maked by Mr. Have fun! Version 0.2
import sys
from net.sf.l2j import Config
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GOBLIN_NECKLACE = 1483
GOBLIN_PENDANT = 1484
GOBLIN_LORD_PENDANT = 1485
SUSPICIOUS_MEMO = 1486
SUSPICIOUS_CONTRACT = 1487
ADENA = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7532-03.htm" :
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
    elif event == "7532-06.htm" :
      st.takeItems(SUSPICIOUS_MEMO,-1)
      st.exitQuest(1)
      st.playSound("ItemSound.quest_finish")
    return htmltext

 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   if npcId == 7532 :
     if int(st.get("cond"))==0 :
       if st.getPlayer().getRace().ordinal() != 4 :
         htmltext = "7532-00.htm"
         st.exitQuest(1)
       elif st.getPlayer().getLevel() >= 5 :
         htmltext = "7532-02.htm"
         return htmltext
       else:
         htmltext = "7532-01.htm"
         st.exitQuest(1)
     else :
      neckl=st.getQuestItemsCount(GOBLIN_NECKLACE)
      penda=st.getQuestItemsCount(GOBLIN_PENDANT)
      lordp=st.getQuestItemsCount(GOBLIN_LORD_PENDANT)
      smemo=st.getQuestItemsCount(SUSPICIOUS_MEMO)
      scont=st.getQuestItemsCount(SUSPICIOUS_CONTRACT)
      if neckl==penda==lordp==smemo==scont==0 :
        htmltext = "7532-04.htm"
      else :
        st.takeItems(GOBLIN_NECKLACE,-1)
        st.takeItems(GOBLIN_PENDANT,-1)
        st.takeItems(GOBLIN_LORD_PENDANT,-1)
        if scont == 0 :
          if smemo == 1 :
            htmltext = "7532-08.htm"
          elif smemo >= 2 :
            htmltext = "7532-09.htm"
          else :
            htmltext = "7532-05.htm"
        else :
           htmltext = "7532-10.htm"
           st.takeItems(SUSPICIOUS_CONTRACT,-1)
        st.giveItems(ADENA,12*neckl+36*penda+33*lordp+100*scont*int(Config.RATE_DROP_ADENA))
   elif npcId == 7533 :
      if st.getQuestItemsCount(SUSPICIOUS_CONTRACT)==0 :
        htmltext = "7533-01.htm"
      else :
        htmltext = "7533-02.htm"
        st.giveItems(ADENA,st.getQuestItemsCount(SUSPICIOUS_CONTRACT)*120*int(Config.RATE_DROP_ADENA))
        st.takeItems(SUSPICIOUS_CONTRACT,-1)
   return htmltext

 def onKill (self,npc,st) :
   npcId = npc.getNpcId()
   if npcId in [322, 323]: item = GOBLIN_NECKLACE
   if npcId in [324, 327]: item = GOBLIN_PENDANT
   if npcId == 528 : item = GOBLIN_LORD_PENDANT
   if int(st.get("cond")) :
     n = st.getRandom(10)
     if n > 5 :
       st.giveItems(item,1)
       st.playSound("ItemSound.quest_itemget")
     elif n > 4 :
       if st.getQuestItemsCount(SUSPICIOUS_CONTRACT) == 0 :
          if st.getQuestItemsCount(SUSPICIOUS_MEMO) < 3 :
            st.giveItems(SUSPICIOUS_MEMO,1)
            st.playSound("ItemSound.quest_itemget")
          else :
            st.giveItems(SUSPICIOUS_CONTRACT,1)
            st.takeItems(SUSPICIOUS_MEMO,-1)
            st.playSound("ItemSound.quest_middle")
            st.set("cond","2")
   return

QUEST       = Quest(292,"292_CrushBrigands","Crush Brigands")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7532)

CREATED.addTalkId(7532)
STARTING.addTalkId(7532)
STARTED.addTalkId(7532)
COMPLETED.addTalkId(7532)

STARTED.addTalkId(7533)

STARTED.addKillId(322)
STARTED.addKillId(323)
STARTED.addKillId(324)
STARTED.addKillId(327)
STARTED.addKillId(528)

STARTED.addQuestDrop(327,GOBLIN_NECKLACE,1)
STARTED.addQuestDrop(323,GOBLIN_PENDANT,1)
STARTED.addQuestDrop(528,GOBLIN_LORD_PENDANT,1)
STARTED.addQuestDrop(528,SUSPICIOUS_CONTRACT,1)
STARTED.addQuestDrop(327,SUSPICIOUS_MEMO,1)

print "importing quests: 292: Crush Brigands"
