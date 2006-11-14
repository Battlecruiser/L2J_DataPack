# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
STEDMIEL = 30692
GABRIELLE = 30753
GILMORE = 30754
KANTABILON = 31042
NOEL = 31272
RAHORAKTI = 31336
TALIEN = 31739
CARADINE = 31740
VIRGIL = 31742
KASSANDRA = 31743
OGMAR = 31744

#QUEST ITEM
LEGEND_OF_SEVENTEEN = 7587
MALRUK_SUCCUBUS_CLAW = 7597
ECHO_CRYSTAL = 7589
POETRY_BOOK = 7588
CRIMSON_MOSS = 7598
RAHORAKTIS_MEDICINE = 7599
LUNARGENT = 6029
HELLFIRE_OIL = 6033
VIRGILS_LETTER = 7677

#CHANCE
CHANCE_FOR_QUEST_ITEMS = 100

#MOB
BARAHAM = 27113

class Quest (JQuest) :

 def __init__(self,id,name,descr,party): JQuest.__init__(self,id,name,descr,party)

 def onEvent (self,event,st) :
   htmltext = event
   cond = st.getInt("cond")
   if event == "31739-4.htm" :
     if cond == 0 and st.player.isSubClassActive() :
       st.setState(STARTED)
       st.set("cond","1")
       st.playSound("ItemSound.quest_accept")
   if event == "30753-2.htm" :
     if cond == 1 and st.player.isSubClassActive() :
       st.set("cond","2")
       st.playSound("ItemSound.quest_middle")
   if event == "30754-2.htm" :
     if cond == 2 and st.player.isSubClassActive() :
       st.set("cond","3")
       st.playSound("ItemSound.quest_middle")
   if event == "31739-8.htm" :
     if cond == 4 and st.player.isSubClassActive() :
       st.set("cond","5")
       st.takeItems(LEGEND_OF_SEVENTEEN,1)
       st.playSound("ItemSound.quest_middle")
   if event == "31042-2.htm" :
     if cond == 5 and st.player.isSubClassActive() :
       st.set("cond","6")
       st.playSound("ItemSound.quest_middle")
   if event == "31042-5.htm" :
     if cond == 7 and st.player.isSubClassActive() :
       st.set("cond","8")
       st.takeItems(MALRUK_SUCCUBUS_CLAW,10)
       st.giveItems(ECHO_CRYSTAL,1)
       st.playSound("ItemSound.quest_middle")
   if event == "31739-12.htm" :
     if cond == 8 and st.player.isSubClassActive() :
       st.set("cond","9")
       st.takeItems(ECHO_CRYSTAL,1)
       st.playSound("ItemSound.quest_accept")
   if event == "30692-2.htm" :
     if cond == 9 and st.player.isSubClassActive() :
       st.set("cond","10")
       st.giveItems(POETRY_BOOK,1)
       st.playSound("ItemSound.quest_accept")
   if event == "31739-15.htm" :
     if cond == 10 and st.player.isSubClassActive() :
       st.set("cond","11")
       st.takeItems(POETRY_BOOK,1)
       st.playSound("ItemSound.quest_accept")
   if event == "31742-2.htm" :
     if cond == 11 and st.player.isSubClassActive() :
       st.set("cond","12")
       st.playSound("ItemSound.quest_accept")
   if event == "31744-2.htm" :
     if cond == 12 and st.player.isSubClassActive() :
       st.set("cond","13")
       st.playSound("ItemSound.quest_accept")
   if event == "31336-2.htm" :
     if cond == 13 and st.player.isSubClassActive() :
       st.set("cond","14")
       st.playSound("ItemSound.quest_accept")
   if event == "31336-5.htm" :
     if cond == 15 and st.player.isSubClassActive() :
       st.set("cond","16")
       st.takeItems(CRIMSON_MOSS,5)
       st.giveItems(RAHORAKTIS_MEDICINE,1)
       st.playSound("ItemSound.quest_accept")
   if event == "31743-2.htm" :
     if cond == 16 and st.player.isSubClassActive() :
       st.set("cond","17")
       st.takeItems(RAHORAKTIS_MEDICINE,1)
       st.playSound("ItemSound.quest_accept")
   if event == "31742-5.htm" :
     if cond == 17 and st.player.isSubClassActive() :
       st.set("cond","18")
       st.playSound("ItemSound.quest_accept")
   if event == "31740-2.htm" :
     if cond == 18 and st.player.isSubClassActive() :
       st.set("cond","19")
       st.playSound("ItemSound.quest_accept")
   if event == "31272-2.htm" :
     if cond == 19 and st.player.isSubClassActive() :
       st.set("cond","20")
       st.playSound("ItemSound.quest_accept")
   if event == "31272-5.htm" :
     if cond == 20 and st.player.isSubClassActive() :
       st.takeItems(LUNARGENT,5)
       st.takeItems(HELLFIRE_OIL,1)
       st.set("cond","21")
       st.playSound("ItemSound.quest_accept")
   if event == "31740-5.htm" :
     if cond == 21 and st.player.isSubClassActive() :
       st.giveItems(VIRGILS_LETTER,1)
       st.set("cond","0")
       st.playSound("ItemSound.quest_finish")
       st.setState(COMPLETED)
   return htmltext

 def onTalk (Self,npc,st) :
   htmltext = "<html><body>I have nothing to say you</body></html>"
   npcId = npc.getNpcId()
   cond = st.getInt("cond")
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   if st.getPlayer().isSubClassActive() :
     if npcId == TALIEN :
       if cond == 0 :
         if id == COMPLETED :
           htmltext = "<html><head><body>This quest have already been completed.</body></html>"
         elif st.getPlayer().getLevel() < 50 : 
           htmltext = "31739-2.htm"
           st.exitQuest(1)
         elif st.getPlayer().getLevel() >= 50 :
           htmltext = "31739-1.htm"
       if cond == 1 :
         htmltext = "31739-5.htm"
       if cond == 4 and st.getQuestItemsCount(LEGEND_OF_SEVENTEEN) == 1 :
         htmltext = "31739-6.htm"
       if cond == 5 :
         htmltext = "31739-9.htm"
       if cond == 8 and st.getQuestItemsCount(ECHO_CRYSTAL) == 1 :
         htmltext = "31739-11.htm"
       if cond == 9 :
         htmltext = "31739-13.htm"
       if cond == 10 and st.getQuestItemsCount(POETRY_BOOK) == 1 :
         htmltext = "31739-14.htm"
       if cond == 11 :
         htmltext = "31739-16.htm"
     if npcId == GABRIELLE :
       if cond == 1 :
         htmltext = "30753-1.htm"
       if cond == 2 :
         htmltext = "30753-3.htm"
     if npcId == GILMORE :
       if cond == 2 :
         htmltext = "30754-1.htm"
       if cond == 3 :
         htmltext = "30754-3.htm"
     if npcId == KANTABILON :
       if cond == 5 :
         htmltext = "31042-1.htm"
       if cond == 6 :
         htmltext = "31042-4.htm"
       if cond == 7 and st.getQuestItemsCount(MALRUK_SUCCUBUS_CLAW) == 10 :
         htmltext = "31042-3.htm"
       if cond == 8 :
         htmltext = "31042-6.htm"
     if npcId == STEDMIEL :
       if cond == 9 :
         htmltext = "30692-1.htm"
       if cond == 10 :
         htmltext = "30692-3.htm"
     if npcId == VIRGIL :
       if cond == 11 :
         htmltext = "31742-1.htm"
       if cond == 12 :
         htmltext = "31742-3.htm"
       if cond == 17 :
         htmltext = "31742-4.htm"
       if cond == 18 :
         htmltext = "31742-6.htm"
     if npcId == OGMAR :
       if cond == 12 :
         htmltext = "31744-1.htm"
       if cond == 13 :
         htmltext = "31744-3.htm"
     if npcId == RAHORAKTI :
       if cond == 13 :
         htmltext = "31336-1.htm"
       if cond == 14 :
         htmltext = "31336-4.htm"
       if cond == 15 and st.getQuestItemsCount(CRIMSON_MOSS) == 5 :
         htmltext = "31336-3.htm"
       if cond == 16 :
         htmltext = "31336-6.htm"
     if npcId == KASSANDRA :
       if cond == 16 and st.getQuestItemsCount(RAHORAKTIS_MEDICINE) == 1 :
         htmltext = "31743-1.htm"
       if cond == 17 :
         htmltext = "31743-3.htm"
     if npcId == CARADINE :
       if cond == 18 :
         htmltext = "31740-1.htm"
       if cond == 19 :
         htmltext = "31740-3.htm"
       if cond == 21 :
         htmltext = "31740-4.htm"
     if npcId == NOEL :
       if cond == 19 :
         htmltext = "31272-1.htm"
       if cond == 20 and st.getQuestItemsCount(LUNARGENT) < 5 and st.getQuestItemsCount(HELLFIRE_OIL) < 1 :
         htmltext = "31272-4.htm"
       if cond == 20 and st.getQuestItemsCount(LUNARGENT) >= 5 and st.getQuestItemsCount(HELLFIRE_OIL) >= 1 :
         htmltext = "31272-3.htm"
       if cond == 21 :
         htmltext = "31272-7.htm"
   else :
     htmltext = "31739-2.htm"
     st.exitQuest(1)
   return htmltext

 def onKill (self,npc,st) :
   npcId = npc.getNpcId()
   cond = st.getInt("cond")
   chance = st.getRandom(100)
   if npcId == BARAHAM and cond == 3 :
     st.set("cond","4")
     st.giveItems(LEGEND_OF_SEVENTEEN,1)
     st.playSound("ItemSound.quest_itemget")
   if npcId in [20244,20245,20283,21508] :
     if cond == 6 and CHANCE_FOR_QUEST_ITEMS > chance and st.getQuestItemsCount(MALRUK_SUCCUBUS_CLAW) < 10 :
       st.giveItems(MALRUK_SUCCUBUS_CLAW,1)
       st.playSound("ItemSound.quest_itemget")
       if st.getQuestItemsCount(MALRUK_SUCCUBUS_CLAW) == 10 :
         st.set("cond","7")
         st.playSound("ItemSound.quest_middle")
   if npcId in range(21508,215013) :
     if cond == 14 and CHANCE_FOR_QUEST_ITEMS > chance and st.getQuestItemsCount(CRIMSON_MOSS) < 5 :
       st.giveItems(CRIMSON_MOSS,1)
       st.playSound("ItemSound.quest_itemget")
       if st.getQuestItemsCount(CRIMSON_MOSS) == 5 :
         st.set("cond","15")
         st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(241,"241_PossessorOfAPreciousSoul_1","Possessor Of A Precious Soul - 1",True)
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(TALIEN)
CREATED.addTalkId(TALIEN)
STARTED.addTalkId(TALIEN)
STARTED.addTalkId(STEDMIEL)
STARTED.addTalkId(GABRIELLE)
STARTED.addTalkId(GILMORE)
STARTED.addTalkId(KANTABILON)
STARTED.addTalkId(NOEL)
STARTED.addTalkId(RAHORAKTI)
STARTED.addTalkId(CARADINE)
STARTED.addTalkId(VIRGIL)
STARTED.addTalkId(KASSANDRA)
STARTED.addTalkId(OGMAR)

STARTED.addKillId(BARAHAM)
STARTED.addKillId(20244)
STARTED.addKillId(20245)
STARTED.addKillId(20283)
STARTED.addKillId(21508)

STARTED.addKillId(21508)
STARTED.addKillId(21509)
STARTED.addKillId(21510)
STARTED.addKillId(21511)
STARTED.addKillId(21512)

STARTED.addQuestDrop(BARAHAM,LEGEND_OF_SEVENTEEN,1)
STARTED.addQuestDrop(BARAHAM,MALRUK_SUCCUBUS_CLAW,1)
STARTED.addQuestDrop(BARAHAM,ECHO_CRYSTAL,1)
STARTED.addQuestDrop(BARAHAM,POETRY_BOOK,1)
STARTED.addQuestDrop(BARAHAM,CRIMSON_MOSS,1)
STARTED.addQuestDrop(BARAHAM,RAHORAKTIS_MEDICINE,1)
STARTED.addQuestDrop(BARAHAM,VIRGILS_LETTER,1)

print "importing quests: 241: Possessor Of A Precious Soul - 1"
