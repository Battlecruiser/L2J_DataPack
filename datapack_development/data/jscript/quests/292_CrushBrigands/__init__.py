# Maked by Mr. Have fun! Version 0.2
print "importing quests: 292: Crush Brigands"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GOBLIN_NECKLACE_ID = 1483
GOBLIN_PENDANT_ID = 1484
GOBLIN_LORD_PENDANT_ID = 1485
SUSPICIOUS_MEMO_ID = 1486
SUSPICIOUS_CONTRACT_ID = 1487
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      htmltext = "7532-03.htm"
    elif event == "7532_1" :
          htmltext = "7532-06.htm"
          st.takeItems(SUSPICIOUS_MEMO_ID,st.getQuestItemsCount(SUSPICIOUS_MEMO_ID))
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
    elif event == "7532_2" :
          htmltext = "7532-07.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7532 and int(st.get("cond"))==0 :
      if int(st.get("cond")) < 15 :
        if st.getPlayer().getRace().ordinal() != 4 :
          htmltext = "7532-00.htm"
        elif st.getPlayer().getLevel() >= 5 :
          htmltext = "7532-02.htm"
          st.set("cond","1")
          return htmltext
        else:
          htmltext = "7532-01.htm"
      else:
        htmltext = "7532-01.htm"
   elif npcId == 7532 and int(st.get("cond"))==1 and st.getQuestItemsCount(GOBLIN_NECKLACE_ID)<1 and st.getQuestItemsCount(GOBLIN_PENDANT_ID)<1 and st.getQuestItemsCount(GOBLIN_LORD_PENDANT_ID)<1 and st.getQuestItemsCount(SUSPICIOUS_MEMO_ID)==0 and st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID)==0 :
      htmltext = "7532-04.htm"
   elif npcId == 7532 and int(st.get("cond"))==1 and st.getQuestItemsCount(GOBLIN_NECKLACE_ID)+st.getQuestItemsCount(GOBLIN_PENDANT_ID)+st.getQuestItemsCount(GOBLIN_LORD_PENDANT_ID)>=1 and st.getQuestItemsCount(SUSPICIOUS_MEMO_ID)==0 and st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID)==0 :
      htmltext = "7532-05.htm"
      st.giveItems(ADENA_ID,st.getQuestItemsCount(GOBLIN_NECKLACE_ID)*12+st.getQuestItemsCount(GOBLIN_PENDANT_ID)*36+st.getQuestItemsCount(GOBLIN_LORD_PENDANT_ID)*33)
      st.takeItems(GOBLIN_NECKLACE_ID,st.getQuestItemsCount(GOBLIN_NECKLACE_ID))
      st.takeItems(GOBLIN_PENDANT_ID,st.getQuestItemsCount(GOBLIN_PENDANT_ID))
      st.takeItems(GOBLIN_LORD_PENDANT_ID,st.getQuestItemsCount(GOBLIN_LORD_PENDANT_ID))
   elif npcId == 7532 and int(st.get("cond"))==1 and st.getQuestItemsCount(SUSPICIOUS_MEMO_ID)==1 and st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID)==0 :
      htmltext = "7532-08.htm"
      st.giveItems(ADENA_ID,st.getQuestItemsCount(GOBLIN_NECKLACE_ID)*12+st.getQuestItemsCount(GOBLIN_PENDANT_ID)*36+st.getQuestItemsCount(GOBLIN_LORD_PENDANT_ID)*33)
      st.takeItems(GOBLIN_NECKLACE_ID,st.getQuestItemsCount(GOBLIN_NECKLACE_ID))
      st.takeItems(GOBLIN_PENDANT_ID,st.getQuestItemsCount(GOBLIN_PENDANT_ID))
      st.takeItems(GOBLIN_LORD_PENDANT_ID,st.getQuestItemsCount(GOBLIN_LORD_PENDANT_ID))
   elif npcId == 7532 and int(st.get("cond"))==1 and st.getQuestItemsCount(SUSPICIOUS_MEMO_ID)>=2 and st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID)==0 :
      htmltext = "7532-09.htm"
      st.giveItems(ADENA_ID,st.getQuestItemsCount(GOBLIN_NECKLACE_ID)*12+st.getQuestItemsCount(GOBLIN_PENDANT_ID)*36+st.getQuestItemsCount(GOBLIN_LORD_PENDANT_ID)*33)
      st.takeItems(GOBLIN_NECKLACE_ID,st.getQuestItemsCount(GOBLIN_NECKLACE_ID))
      st.takeItems(GOBLIN_PENDANT_ID,st.getQuestItemsCount(GOBLIN_PENDANT_ID))
      st.takeItems(GOBLIN_LORD_PENDANT_ID,st.getQuestItemsCount(GOBLIN_LORD_PENDANT_ID))
   elif npcId == 7532 and int(st.get("cond"))==1 and st.getQuestItemsCount(SUSPICIOUS_MEMO_ID)==0 and st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID)==1 :
      htmltext = "7532-10.htm"
      st.giveItems(ADENA_ID,st.getQuestItemsCount(GOBLIN_NECKLACE_ID)*12+st.getQuestItemsCount(GOBLIN_PENDANT_ID)*36+st.getQuestItemsCount(GOBLIN_LORD_PENDANT_ID)*33+st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID)*100)
      st.takeItems(GOBLIN_NECKLACE_ID,st.getQuestItemsCount(GOBLIN_NECKLACE_ID))
      st.takeItems(GOBLIN_PENDANT_ID,st.getQuestItemsCount(GOBLIN_PENDANT_ID))
      st.takeItems(GOBLIN_LORD_PENDANT_ID,st.getQuestItemsCount(GOBLIN_LORD_PENDANT_ID))
      st.takeItems(SUSPICIOUS_CONTRACT_ID,st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID))
   elif npcId == 7533 and int(st.get("cond"))==1 and st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID)==0 :
      htmltext = "7533-01.htm"
   elif npcId == 7533 and int(st.get("cond"))==1 and st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID)==1 :
      htmltext = "7533-02.htm"
      st.giveItems(ADENA_ID,st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID)*120)
      st.takeItems(SUSPICIOUS_CONTRACT_ID,st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID))
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 327 :
      st.set("id","0")
      if int(st.get("cond")) :
        n = st.getRandom(10)
        if n > 5 :
          st.giveItems(GOBLIN_NECKLACE_ID,1)
          st.playSound("ItemSound.quest_itemget")
        elif n > 4 :
          if st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID) < 1 and st.getQuestItemsCount(SUSPICIOUS_MEMO_ID) < 3 :
            st.giveItems(SUSPICIOUS_MEMO_ID,1)
            st.playSound("ItemSound.quest_itemget")
          elif st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID) < 1 and st.getQuestItemsCount(SUSPICIOUS_MEMO_ID) == 3 :
            st.giveItems(SUSPICIOUS_CONTRACT_ID,1)
            st.takeItems(SUSPICIOUS_MEMO_ID,st.getQuestItemsCount(SUSPICIOUS_MEMO_ID))
            st.playSound("ItemSound.quest_middle")
   elif npcId == 322 :
      st.set("id","0")
      if int(st.get("cond")) :
        n = st.getRandom(10)
        if n > 5 :
          st.giveItems(GOBLIN_NECKLACE_ID,1)
          st.playSound("ItemSound.quest_itemget")
        elif n > 4 :
          if st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID) < 1 and st.getQuestItemsCount(SUSPICIOUS_MEMO_ID) < 3 :
            st.giveItems(SUSPICIOUS_MEMO_ID,1)
            st.playSound("ItemSound.quest_itemget")
          elif st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID) < 1 and st.getQuestItemsCount(SUSPICIOUS_MEMO_ID) == 3 :
            st.giveItems(SUSPICIOUS_CONTRACT_ID,1)
            st.takeItems(SUSPICIOUS_MEMO_ID,st.getQuestItemsCount(SUSPICIOUS_MEMO_ID))
            st.playSound("ItemSound.quest_middle")
   elif npcId == 323 :
      st.set("id","0")
      if int(st.get("cond")) :
        n = st.getRandom(10)
        if n > 5 :
          st.giveItems(GOBLIN_PENDANT_ID,1)
          st.playSound("ItemSound.quest_itemget")
        elif n > 4 :
          if st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID) < 1 and st.getQuestItemsCount(SUSPICIOUS_MEMO_ID) < 3 :
            st.giveItems(SUSPICIOUS_MEMO_ID,1)
            st.playSound("ItemSound.quest_itemget")
          elif st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID) < 1 and st.getQuestItemsCount(SUSPICIOUS_MEMO_ID) == 3 :
            st.giveItems(SUSPICIOUS_CONTRACT_ID,1)
            st.takeItems(SUSPICIOUS_MEMO_ID,st.getQuestItemsCount(SUSPICIOUS_MEMO_ID))
            st.playSound("ItemSound.quest_middle")
   elif npcId == 324 :
      st.set("id","0")
      if int(st.get("cond")) :
        n = st.getRandom(10)
        if n > 5 :
          st.giveItems(GOBLIN_NECKLACE_ID,1)
          st.playSound("ItemSound.quest_itemget")
        elif n > 4 :
          if st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID) < 1 and st.getQuestItemsCount(SUSPICIOUS_MEMO_ID) < 3 :
            st.giveItems(SUSPICIOUS_MEMO_ID,1)
            st.playSound("ItemSound.quest_itemget")
          elif st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID) < 1 and st.getQuestItemsCount(SUSPICIOUS_MEMO_ID) == 3 :
            st.giveItems(SUSPICIOUS_CONTRACT_ID,1)
            st.takeItems(SUSPICIOUS_MEMO_ID,st.getQuestItemsCount(SUSPICIOUS_MEMO_ID))
            st.playSound("ItemSound.quest_middle")
   elif npcId == 528 :
      st.set("id","0")
      if int(st.get("cond")) :
        n = st.getRandom(10)
        if n > 5 :
          st.giveItems(GOBLIN_LORD_PENDANT_ID,1)
          st.playSound("ItemSound.quest_itemget")
        elif n > 4 :
          if st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID) < 1 and st.getQuestItemsCount(SUSPICIOUS_MEMO_ID) < 3 :
            st.giveItems(SUSPICIOUS_MEMO_ID,1)
            st.playSound("ItemSound.quest_itemget")
          elif st.getQuestItemsCount(SUSPICIOUS_CONTRACT_ID) < 1 and st.getQuestItemsCount(SUSPICIOUS_MEMO_ID) == 3 :
            st.giveItems(SUSPICIOUS_CONTRACT_ID,1)
            st.takeItems(SUSPICIOUS_MEMO_ID,st.getQuestItemsCount(SUSPICIOUS_MEMO_ID))
            st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(292,"292_CrushBrigands","Crush Brigands")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7532)

STARTED.addTalkId(7532)
STARTED.addTalkId(7533)

STARTED.addKillId(322)
STARTED.addKillId(323)
STARTED.addKillId(324)
STARTED.addKillId(327)
STARTED.addKillId(528)

STARTED.addQuestDrop(327,GOBLIN_NECKLACE_ID,1)
STARTED.addQuestDrop(322,GOBLIN_NECKLACE_ID,1)
STARTED.addQuestDrop(324,GOBLIN_NECKLACE_ID,1)
STARTED.addQuestDrop(323,GOBLIN_PENDANT_ID,1)
STARTED.addQuestDrop(528,GOBLIN_LORD_PENDANT_ID,1)
STARTED.addQuestDrop(327,SUSPICIOUS_CONTRACT_ID,1)
STARTED.addQuestDrop(322,SUSPICIOUS_CONTRACT_ID,1)
STARTED.addQuestDrop(323,SUSPICIOUS_CONTRACT_ID,1)
STARTED.addQuestDrop(324,SUSPICIOUS_CONTRACT_ID,1)
STARTED.addQuestDrop(528,SUSPICIOUS_CONTRACT_ID,1)
STARTED.addQuestDrop(327,SUSPICIOUS_MEMO_ID,1)
STARTED.addQuestDrop(322,SUSPICIOUS_MEMO_ID,1)
STARTED.addQuestDrop(323,SUSPICIOUS_MEMO_ID,1)
STARTED.addQuestDrop(324,SUSPICIOUS_MEMO_ID,1)
STARTED.addQuestDrop(528,SUSPICIOUS_MEMO_ID,1)
