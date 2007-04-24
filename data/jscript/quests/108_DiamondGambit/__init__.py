# Maked by Mr. Have fun! Version 0.2
import sys
from net.sf.l2j import Config 
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "108_DiamondGambit"

GOUPHS_CONTRACT_ID = 1559
REEPS_CONTRACT_ID = 1560
ELVEN_WINE_ID = 1561
BRONPS_DICE_ID = 1562
BRONPS_CONTRACT_ID = 1563
AQUAMARINE_ID = 1564
CHRYSOBERYL_ID = 1565
GEM_BOX1_ID = 1566
COAL_PIECE_ID = 1567
BRONPS_LETTER_ID = 1568
BERRY_TART_ID = 1569
BAT_DIAGRAM_ID = 1570
STAR_DIAMOND_ID = 1571
SILVERSMITH_HAMMER_ID = 1511

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
          htmltext = "30523-03.htm"
          st.giveItems(GOUPHS_CONTRACT_ID,1)
          st.set("cond","1")
          st.setState(STARTED)
          st.playSound("ItemSound.quest_accept")
    elif event == "30555_1" :
          htmltext = "30555-02.htm"
          st.takeItems(REEPS_CONTRACT_ID,1)
          st.giveItems(ELVEN_WINE_ID,1)
    elif event == "30526_1" :
          htmltext = "30526-02.htm"
          st.takeItems(BRONPS_DICE_ID,1)
          st.giveItems(BRONPS_CONTRACT_ID,1)
    return htmltext


 def onTalk (Self,npc,player):

   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   if npcId == 30523 and id == COMPLETED :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 30523 and int(st.get("cond"))==0 :
          if st.getPlayer().getRace().ordinal() != 4 :
            htmltext = "30523-00.htm"
            st.exitQuest(1)
          elif st.getPlayer().getLevel() >= 10 :
            htmltext = "30523-02.htm"
            return htmltext
          else:
            htmltext = "30523-01.htm"
            st.exitQuest(1)
   elif npcId == 30523 and int(st.get("cond"))==1 and st.getQuestItemsCount(GOUPHS_CONTRACT_ID) :
          htmltext = "30523-04.htm"
   elif npcId == 30523 and int(st.get("cond"))==1 and (st.getQuestItemsCount(REEPS_CONTRACT_ID) or st.getQuestItemsCount(ELVEN_WINE_ID) or st.getQuestItemsCount(BRONPS_DICE_ID) or st.getQuestItemsCount(BRONPS_CONTRACT_ID)) :
          htmltext = "30523-05.htm"
   elif npcId == 30523 and int(st.get("cond"))==1 and st.getQuestItemsCount(GEM_BOX1_ID) :
          htmltext = "30523-06.htm"
          st.takeItems(GEM_BOX1_ID,1)
          st.giveItems(COAL_PIECE_ID,1)
   elif npcId == 30523 and int(st.get("cond"))==1 and (st.getQuestItemsCount(BRONPS_LETTER_ID) or st.getQuestItemsCount(COAL_PIECE_ID) or st.getQuestItemsCount(BERRY_TART_ID) or st.getQuestItemsCount(BAT_DIAGRAM_ID)) :
          htmltext = "30523-07.htm"
   elif npcId == 30523 and int(st.get("cond"))==1 and st.getQuestItemsCount(STAR_DIAMOND_ID) :
            htmltext = "30523-08.htm"
            st.giveItems(SILVERSMITH_HAMMER_ID,1)
            for item in range(4412,4417) :
               st.giveItems(item,int(10*Config.RATE_QUESTS_REWARD))   # Echo crystals
            st.giveItems(1060,int(100*Config.RATE_QUESTS_REWARD))     # Lesser Healing Potions
            st.takeItems(STAR_DIAMOND_ID,-1)
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
   elif id == STARTED :
       if npcId == 30516 and int(st.get("cond"))==1 and st.getQuestItemsCount(GOUPHS_CONTRACT_ID) and st.getQuestItemsCount(REEPS_CONTRACT_ID)==0 :
              htmltext = "30516-01.htm"
              st.giveItems(REEPS_CONTRACT_ID,1)
              st.takeItems(GOUPHS_CONTRACT_ID,1)
       elif npcId == 30516 and int(st.get("cond"))==1 and st.getQuestItemsCount(GOUPHS_CONTRACT_ID)==0 and st.getQuestItemsCount(REEPS_CONTRACT_ID) :
              htmltext = "30516-02.htm"
       elif npcId == 30516 and int(st.get("cond"))==1 and st.getQuestItemsCount(GOUPHS_CONTRACT_ID)==0 and st.getQuestItemsCount(REEPS_CONTRACT_ID)==0 :
              htmltext = "30516-03.htm"
       elif npcId == 30555 and int(st.get("cond"))==1 and st.getQuestItemsCount(REEPS_CONTRACT_ID)==0 and st.getQuestItemsCount(ELVEN_WINE_ID)==0 :
              htmltext = "30555-01.htm"
       elif npcId == 30555 and int(st.get("cond"))==1 and st.getQuestItemsCount(REEPS_CONTRACT_ID) and st.getQuestItemsCount(ELVEN_WINE_ID)==0 :
              htmltext = "30555-02.htm"
              st.giveItems(ELVEN_WINE_ID,1)
              st.takeItems(REEPS_CONTRACT_ID,1)
       elif npcId == 30555 and int(st.get("cond"))==1 and st.getQuestItemsCount(REEPS_CONTRACT_ID)==0 and st.getQuestItemsCount(ELVEN_WINE_ID) :
              htmltext = "30555-03.htm"
       elif npcId == 30555 and int(st.get("cond"))==1 and st.getQuestItemsCount(GEM_BOX1_ID)==1 :
              htmltext = "30555-04.htm"
       elif npcId == 30555 and int(st.get("cond"))==1 and st.getQuestItemsCount(GEM_BOX1_ID)==0 and st.getQuestItemsCount(REEPS_CONTRACT_ID)==0 and st.getQuestItemsCount(ELVEN_WINE_ID)==0 :
              htmltext = "30555-05.htm"
       elif npcId == 30529 and int(st.get("cond"))==1 and st.getQuestItemsCount(ELVEN_WINE_ID) and st.getQuestItemsCount(BRONPS_DICE_ID)==0 :
              htmltext = "30529-01.htm"
              st.giveItems(BRONPS_DICE_ID,1)
              st.takeItems(ELVEN_WINE_ID,1)
       elif npcId == 30529 and int(st.get("cond"))==1 and st.getQuestItemsCount(ELVEN_WINE_ID)==0 and st.getQuestItemsCount(BRONPS_DICE_ID) :
              htmltext = "30529-02.htm"
       elif npcId == 30529 and int(st.get("cond"))==1 and st.getQuestItemsCount(ELVEN_WINE_ID)==0 and st.getQuestItemsCount(BRONPS_DICE_ID)==0 :
              htmltext = "30529-03.htm"
       elif npcId == 30526 and int(st.get("cond"))==1 and st.getQuestItemsCount(BRONPS_DICE_ID) :
              htmltext = "30526-01.htm"
       elif npcId == 30526 and int(st.get("cond"))==1 and st.getQuestItemsCount(BRONPS_CONTRACT_ID) and (st.getQuestItemsCount(AQUAMARINE_ID)+st.getQuestItemsCount(CHRYSOBERYL_ID)<20) :
              htmltext = "30526-03.htm"
       elif npcId == 30526 and int(st.get("cond"))==1 and st.getQuestItemsCount(BRONPS_CONTRACT_ID) and (st.getQuestItemsCount(AQUAMARINE_ID)+st.getQuestItemsCount(CHRYSOBERYL_ID)>=20) :
              htmltext = "30526-04.htm"
              st.takeItems(BRONPS_CONTRACT_ID,1)
              st.takeItems(AQUAMARINE_ID,st.getQuestItemsCount(AQUAMARINE_ID))
              st.takeItems(CHRYSOBERYL_ID,st.getQuestItemsCount(CHRYSOBERYL_ID))
              st.giveItems(GEM_BOX1_ID,1)
       elif npcId == 30526 and int(st.get("cond"))==1 and st.getQuestItemsCount(GEM_BOX1_ID) :
              htmltext = "30526-05.htm"
       elif npcId == 30526 and int(st.get("cond"))==1 and st.getQuestItemsCount(COAL_PIECE_ID) :
              htmltext = "30526-06.htm"
              st.takeItems(COAL_PIECE_ID,1)
              st.giveItems(BRONPS_LETTER_ID,1)
       elif npcId == 30526 and int(st.get("cond"))==1 and st.getQuestItemsCount(BRONPS_LETTER_ID) :
              htmltext = "30526-07.htm"
       elif npcId == 30526 and int(st.get("cond"))==1 and st.getQuestItemsCount(BERRY_TART_ID) or st.getQuestItemsCount(BAT_DIAGRAM_ID) or st.getQuestItemsCount(STAR_DIAMOND_ID) :
              htmltext = "30526-08.htm"
       elif npcId == 30521 and int(st.get("cond"))==1 and st.getQuestItemsCount(BRONPS_LETTER_ID) and st.getQuestItemsCount(BERRY_TART_ID)==0 :
              htmltext = "30521-01.htm"
              st.giveItems(BERRY_TART_ID,1)
              st.takeItems(BRONPS_LETTER_ID,1)
       elif npcId == 30521 and int(st.get("cond"))==1 and st.getQuestItemsCount(BRONPS_LETTER_ID)==0 and st.getQuestItemsCount(BERRY_TART_ID) :
              htmltext = "30521-02.htm"
       elif npcId == 30521 and int(st.get("cond"))==1 and st.getQuestItemsCount(BRONPS_LETTER_ID)==0 and st.getQuestItemsCount(BERRY_TART_ID)==0 :
              htmltext = "30521-03.htm"
       elif npcId == 30522 and int(st.get("cond"))==1 and st.getQuestItemsCount(BAT_DIAGRAM_ID)==0 and st.getQuestItemsCount(BERRY_TART_ID) and st.getQuestItemsCount(STAR_DIAMOND_ID)==0 :
              htmltext = "30522-01.htm"
              st.giveItems(BAT_DIAGRAM_ID,1)
              st.takeItems(BERRY_TART_ID,1)
       elif npcId == 30522 and int(st.get("cond"))==1 and st.getQuestItemsCount(BAT_DIAGRAM_ID) and st.getQuestItemsCount(BERRY_TART_ID)==0 and st.getQuestItemsCount(STAR_DIAMOND_ID)==0 :
              htmltext = "30522-02.htm"
       elif npcId == 30522 and int(st.get("cond"))==1 and st.getQuestItemsCount(BAT_DIAGRAM_ID)==0 and st.getQuestItemsCount(BERRY_TART_ID)==0 and st.getQuestItemsCount(STAR_DIAMOND_ID) :
              htmltext = "30522-03.htm"
       elif npcId == 30522 and int(st.get("cond"))==1 and st.getQuestItemsCount(BAT_DIAGRAM_ID)==0 and st.getQuestItemsCount(BERRY_TART_ID)==0 and st.getQuestItemsCount(STAR_DIAMOND_ID)==0 :
              htmltext = "30522-04.htm"
   return htmltext

 def onKill (self,npc,player):
   st = player.getQuestState(qn)
   if not st : return
   if st.getState() != STARTED : return

   npcId = npc.getNpcId()
   if npcId == 20323 :
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(BRONPS_CONTRACT_ID) :
          if st.getRandom(10) < 8 :
            if st.getQuestItemsCount(AQUAMARINE_ID)+st.getQuestItemsCount(CHRYSOBERYL_ID) == 19 :
              if st.getQuestItemsCount(AQUAMARINE_ID) < 10 :
                st.giveItems(AQUAMARINE_ID,1)
                st.playSound("ItemSound.quest_middle")
            else:
              if st.getQuestItemsCount(AQUAMARINE_ID) < 10 :
                st.giveItems(AQUAMARINE_ID,1)
                st.playSound("ItemSound.quest_itemget")
          if st.getRandom(10) < 8 :
            if st.getQuestItemsCount(AQUAMARINE_ID)+st.getQuestItemsCount(CHRYSOBERYL_ID) == 19 :
              if st.getQuestItemsCount(CHRYSOBERYL_ID) < 10 :
                st.giveItems(CHRYSOBERYL_ID,1)
                st.playSound("ItemSound.quest_middle")
            elif st.getQuestItemsCount(AQUAMARINE_ID)+st.getQuestItemsCount(CHRYSOBERYL_ID) < 20 :
                if st.getQuestItemsCount(CHRYSOBERYL_ID) < 10 :
                  st.giveItems(CHRYSOBERYL_ID,1)
                  st.playSound("ItemSound.quest_itemget")
   elif npcId == 20324 :
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(BRONPS_CONTRACT_ID) :
          if st.getRandom(10) < 6 :
            if st.getQuestItemsCount(AQUAMARINE_ID)+st.getQuestItemsCount(CHRYSOBERYL_ID) == 19 :
              if st.getQuestItemsCount(AQUAMARINE_ID) < 10 :
                st.giveItems(AQUAMARINE_ID,1)
                st.playSound("ItemSound.quest_middle")
            else:
              if st.getQuestItemsCount(AQUAMARINE_ID) < 10 :
                st.giveItems(AQUAMARINE_ID,1)
                st.playSound("ItemSound.quest_itemget")
          if st.getRandom(10) < 6 :
            if st.getQuestItemsCount(AQUAMARINE_ID)+st.getQuestItemsCount(CHRYSOBERYL_ID) == 19 :
              if st.getQuestItemsCount(CHRYSOBERYL_ID) < 10 :
                st.giveItems(CHRYSOBERYL_ID,1)
                st.playSound("ItemSound.quest_middle")
            elif st.getQuestItemsCount(AQUAMARINE_ID)+st.getQuestItemsCount(CHRYSOBERYL_ID) < 20 :
                if st.getQuestItemsCount(CHRYSOBERYL_ID) < 10 :
                  st.giveItems(CHRYSOBERYL_ID,1)
                  st.playSound("ItemSound.quest_itemget")
   elif npcId == 20480 :
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(BAT_DIAGRAM_ID) and st.getQuestItemsCount(STAR_DIAMOND_ID) == 0 :
          if st.getRandom(10) < 2 :
            st.giveItems(STAR_DIAMOND_ID,1)
            st.takeItems(BAT_DIAGRAM_ID,1)
            st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(108,qn,"Diamond Gambit")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30523)

QUEST.addTalkId(30523)

QUEST.addTalkId(30516)
QUEST.addTalkId(30521)
QUEST.addTalkId(30522)
QUEST.addTalkId(30523)
QUEST.addTalkId(30526)
QUEST.addTalkId(30529)
QUEST.addTalkId(30555)

QUEST.addKillId(20323)
QUEST.addKillId(20324)
QUEST.addKillId(20480)

STARTED.addQuestDrop(30526,GEM_BOX1_ID,1)
STARTED.addQuestDrop(20480,STAR_DIAMOND_ID,1)
STARTED.addQuestDrop(30523,GOUPHS_CONTRACT_ID,1)
STARTED.addQuestDrop(30516,REEPS_CONTRACT_ID,1)
STARTED.addQuestDrop(30555,ELVEN_WINE_ID,1)
STARTED.addQuestDrop(30526,BRONPS_CONTRACT_ID,1)
STARTED.addQuestDrop(20323,AQUAMARINE_ID,1)
STARTED.addQuestDrop(20323,CHRYSOBERYL_ID,1)
STARTED.addQuestDrop(30523,COAL_PIECE_ID,1)
STARTED.addQuestDrop(30529,BRONPS_DICE_ID,1)
STARTED.addQuestDrop(30526,BRONPS_LETTER_ID,1)
STARTED.addQuestDrop(30521,BERRY_TART_ID,1)
STARTED.addQuestDrop(30522,BAT_DIAGRAM_ID,1)

print "importing quests: 108: Diamond Gambit"
