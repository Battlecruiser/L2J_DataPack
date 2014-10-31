# Made by disKret
import sys
from net.sf.l2j import Config 
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "90_SagaOfTheStormScreamer"

#NPC
FAIREN = 30175
KAMILEN = 31287
MEDINA_BLACKHEART = 31598
MIST = 31627
TABLET_OF_VISION1 = 31646
TABLET_OF_VISION2 = 31649
TABLET_OF_VISION3 = 31652
TABLET_OF_VISION4 = 31654
TABLET_OF_VISION5 = 31655
TABLET_OF_VISION6 = 31659

#QUEST ITEM
CRYOLITE = 7080
INVESTIGATIVE_REPORT = 7531
DIVINE_STONE = 7081
RESONANCE_AMULET_1 = 7288
RESONANCE_AMULET_2 = 7318
RESONANCE_AMULET_3 = 7350
RESONANCE_AMULET_4 = 7381
RESONANCE_AMULET_5 = 7412
RESONANCE_AMULET_6 = 7443
HALISHA_MARKS = 7485

#MOB
GUARDIAN_OF_KNOWLEDGE = 27216
ANGEL_ALLECTOR = 27250
ARCHON_OF_HALISHA = 27219

max = 700

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   cond = st.getInt("cond")
   if event == "30175-3.htm" :
     if cond == 0 :
       st.setState(STARTED)
       st.set("cond","1")
       st.playSound("ItemSound.quest_accept")
   if event == "31287-1.htm" :
     if cond == 1 :
       st.set("cond","2")
       st.playSound("ItemSound.quest_middle")
   if event == "31627-1.htm" :
     if cond == 2 :
       st.set("cond","3")
       st.playSound("ItemSound.quest_middle")
   if event == "31627-3.htm" :
     if cond == 3 :
       st.set("cond","4")
       st.takeItems(CRYOLITE,1)
       st.giveItems(INVESTIGATIVE_REPORT,1)
       st.playSound("ItemSound.quest_middle")
   if event == "31287-3.htm" :
     if cond == 4 :
       st.set("cond","5")
       st.takeItems(INVESTIGATIVE_REPORT,1)
       st.giveItems(RESONANCE_AMULET_1,1)
       st.playSound("ItemSound.quest_middle")
   if event == "31646-1.htm" :
     if cond == 5 :
       st.set("cond","6")
       st.takeItems(RESONANCE_AMULET_1,1)
       st.playSound("ItemSound.quest_middle")
   if event == "31649-2.htm" :
     if cond == 7 :
       st.set("cond","8")
       st.takeItems(RESONANCE_AMULET_2,1)
       st.playSound("ItemSound.quest_middle")
   if event == "31652-1.htm" :
     if cond == 8 :
       st.getPcSpawn().addSpawn(ANGEL_ALLECTOR) 
   if event == "31652-5.htm" :
     if cond == 9 :
       st.set("cond","10")
       st.takeItems(RESONANCE_AMULET_3,1)
       st.playSound("ItemSound.quest_middle")
   if event == "31287-12.htm" :
     if cond == 10 :
       return htmltext
   if event == "31287-7.htm" :
     if cond == 10 :
       st.set("cond","11")
       st.playSound("ItemSound.quest_middle")
   if event == "31287-8.htm" :
     if cond == 10 :
       st.set("cond","12")
       st.playSound("ItemSound.quest_middle")
   if event == "31287-13.htm" :
     if st.getQuestItemsCount(DIVINE_STONE) == 1 :
       st.set("cond","13")
       st.takeItems(DIVINE_STONE,1)
       st.giveItems(RESONANCE_AMULET_4,1)
       st.playSound("ItemSound.quest_middle")
   if event == "31654-1.htm" :
     if cond == 13 :
       st.set("cond","14")
       st.takeItems(RESONANCE_AMULET_4,1)
       st.playSound("ItemSound.quest_middle")
   if event == "31287-15.htm" :
     if cond == 14 :
       st.set("cond","15")
       st.playSound("ItemSound.quest_middle")
   if event == "31655-2.htm" :
     if cond == 16 :
       st.set("cond","17")
       st.takeItems(RESONANCE_AMULET_5,1)
       st.playSound("ItemSound.quest_middle")
   if event == "31659-1.htm" :
     if cond == 17 :
       st.getPcSpawn().addSpawn(MEDINA_BLACKHEART)
       st.playSound("ItemSound.quest_middle")
   if event == "31598-9.htm" :
     if cond == 17 :
       st.playSound("ItemSound.quest_middle")
   if event == "31598-8.htm" :
     if cond == 17 :
       st.set("cond","18")
       st.giveItems(RESONANCE_AMULET_6,1)
       st.playSound("ItemSound.quest_middle")
   if event == "31659-5.htm" :
     if cond == 18 :
       st.set("cond","19")
       st.takeItems(RESONANCE_AMULET_6,1)
       st.playSound("ItemSound.quest_middle")
   if event == "30175-6.htm" :
     if cond >= 19 :
       st.getPlayer().setClassId(110)
       st.getPlayer().setBaseClass(110)
       st.getPlayer().broadcastUserInfo()
       st.playSound("ItemSound.quest_fanfare_2")
       st.set("cond","0")
       st.setState(COMPLETED)
   return htmltext

 def onTalk (self,npc,player) :
   htmltext = "<html><body>I have nothing to say you</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   ClassId = st.getPlayer().getClassId()
   npcId = npc.getNpcId()
   cond = st.getInt("cond")
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   if npcId == FAIREN :
     if cond == 0 :
       if id == COMPLETED :
         htmltext = "<html><head><body>This quest have already been completed.</body></html>"
       elif st.getPlayer().getLevel() < 76 and ClassId == ClassId.spellhowler : 
         htmltext = "30175-1.htm"
         st.exitQuest(1)
       elif st.getPlayer().getLevel() >= 76 and ClassId == ClassId.spellhowler :
         htmltext = "30175-0.htm"
     if cond == 1 :
       htmltext = "30175-2.htm"
     if cond == 19 :
       if st.getPlayer().getLevel() >= 76 :
         htmltext = "30175-5.htm"
       else :
         st.set("cond","20")
         htmltext = "30175-7.htm"
     if cond == 20 :
       if st.getPlayer().getLevel() >= 76 :
         htmltext = "30175-5.htm"
       else :
         htmltext = "30175-7.htm"
   elif id == STARTED :      
       if npcId == KAMILEN :
         if cond == 1 :
           htmltext = "31287-0.htm"
         if cond == 2 :
           htmltext = "31287-4.htm"
         if cond == 4 and st.getQuestItemsCount(INVESTIGATIVE_REPORT) == 1 :
           htmltext = "31287-2.htm"
         if cond == 5 :
           htmltext = "31287-5.htm"
         if cond == 10 :
           htmltext = "31287-6.htm"
         if cond == 11 :
           if st.getQuestItemsCount(DIVINE_STONE) == 1 :
             htmltext = "31287-10.htm"
           else :
             htmltext = "31287-9.htm"
         if cond == 12 :
           if st.getQuestItemsCount(DIVINE_STONE) == 1 :
             htmltext = "31287-10.htm"
           else :
             htmltext = "31287-9.htm"
         if cond == 13 :
           htmltext = "31287-11.htm"
         if cond == 14 :
           htmltext = "31287-14.htm"
         if cond == 15 :
           htmltext = "31287-15.htm"
       if npcId == MIST :
         if cond == 2 :
           htmltext = "31627-0.htm"
         if cond == 3 :
           htmltext = "31627-4.htm"
         if cond == 3 and st.getQuestItemsCount(CRYOLITE) == 1 :
           htmltext = "31627-2.htm"
         if cond == 4 :
           htmltext = "31627-3.htm"
       if npcId == TABLET_OF_VISION1 and st.getQuestItemsCount(RESONANCE_AMULET_1) == 1 :
         if cond == 5 :
           htmltext = "31646-0.htm"
         if cond == 6 :
           htmltext = "31646-2.htm"
       if npcId == TABLET_OF_VISION2 :
         if cond == 6 and st.getQuestItemsCount(RESONANCE_AMULET_2) == 0 :
           htmltext = "31649-0.htm"
         if cond == 7 and st.getQuestItemsCount(RESONANCE_AMULET_2) == 1 :
           htmltext = "31649-1.htm"
         if cond == 8 :
           htmltext = "31649-3.htm"
       if npcId == TABLET_OF_VISION3 :
         if cond == 8 :
           htmltext = "31652-0.htm"
         if cond == 9 and st.getQuestItemsCount(RESONANCE_AMULET_3) == 1 :
           htmltext = "31652-4.htm"
         if cond == 10 :
           htmltext = "31652-6.htm"
       if npcId == TABLET_OF_VISION4 :
         if cond == 13 and st.getQuestItemsCount(RESONANCE_AMULET_4) == 1 :
           htmltext = "31654-0.htm"
         if cond == 14 :
           htmltext = "31654-2.htm"
       if npcId == TABLET_OF_VISION5 :
         if cond == 15 :
           htmltext = "31655-0.htm"
         if cond == 16 and st.getQuestItemsCount(RESONANCE_AMULET_5) == 1 :
           htmltext = "31655-1.htm"
         if cond == 17 :
           htmltext = "31655-3.htm"
       if npcId == MEDINA_BLACKHEART :
         if cond == 17 :
           htmltext = "31598-0.htm"
         if cond == 18 :
           htmltext = "31598-7.htm"
       if npcId == TABLET_OF_VISION6 :
         if cond == 17 :
           htmltext = "31659-0.htm"
         if cond == 18 and st.getQuestItemsCount(RESONANCE_AMULET_6) == 1 :
           htmltext = "31659-4.htm"
         if cond == 19 :
           htmltext = "31659-6.htm"
   return htmltext

 def onKill (self,npc,player) :
   st = player.getQuestState(qn)
   if not st : return 
   if st.getState() != STARTED : return 

   npcId = npc.getNpcId()
   cond = st.getInt("cond")
   chance = st.getRandom(100)
   if npcId == GUARDIAN_OF_KNOWLEDGE and cond == 6 :
     st.set("cond","7")
     st.giveItems(RESONANCE_AMULET_2,1)
     st.playSound("ItemSound.quest_itemget")
   if npcId == ANGEL_ALLECTOR and cond == 8 :
     st.set("cond","9")
     st.giveItems(RESONANCE_AMULET_3,1)
     st.playSound("ItemSound.quest_itemget")
   if npcId in range(21646,21652) and cond == 15 :
     numItems,chance = divmod(100*Config.RATE_QUESTS_REWARD,100)
     if st.getRandom(100) < chance :
       numItems = numItems + 1
     count = st.getQuestItemsCount(HALISHA_MARKS)
     if count + numItems > max :
       numItems = max - count
     else :
       st.playSound("ItemSound.quest_itemget")
     st.giveItems(HALISHA_MARKS,int(numItems))
     if st.getQuestItemsCount(HALISHA_MARKS) == 700 :
       st.takeItems(HALISHA_MARKS,700)
       st.playSound("ItemSound.quest_middle")
       st.getPcSpawn().addSpawn(ARCHON_OF_HALISHA)
   if npcId == ARCHON_OF_HALISHA and cond == 15 :
     st.set("cond","16")
     st.giveItems(RESONANCE_AMULET_5,1)
     st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(90,qn,"Saga Of The Storm Screamer")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(FAIREN)
QUEST.addTalkId(FAIREN)

QUEST.addTalkId(KAMILEN)
QUEST.addTalkId(MEDINA_BLACKHEART)
QUEST.addTalkId(MIST)
QUEST.addTalkId(TABLET_OF_VISION1)
QUEST.addTalkId(TABLET_OF_VISION2)
QUEST.addTalkId(TABLET_OF_VISION3)
QUEST.addTalkId(TABLET_OF_VISION4)
QUEST.addTalkId(TABLET_OF_VISION5)
QUEST.addTalkId(TABLET_OF_VISION6)

QUEST.addKillId(GUARDIAN_OF_KNOWLEDGE)
QUEST.addKillId(ANGEL_ALLECTOR)
QUEST.addKillId(ARCHON_OF_HALISHA)
for i in range(21646,21652) :
  QUEST.addKillId(i)

STARTED.addQuestDrop(KAMILEN,CRYOLITE,1)
STARTED.addQuestDrop(KAMILEN,DIVINE_STONE,1)
STARTED.addQuestDrop(KAMILEN,INVESTIGATIVE_REPORT,1)
STARTED.addQuestDrop(KAMILEN,RESONANCE_AMULET_1,1)
STARTED.addQuestDrop(KAMILEN,RESONANCE_AMULET_2,1)
STARTED.addQuestDrop(KAMILEN,RESONANCE_AMULET_3,1)
STARTED.addQuestDrop(KAMILEN,RESONANCE_AMULET_4,1)
STARTED.addQuestDrop(KAMILEN,RESONANCE_AMULET_5,1)
STARTED.addQuestDrop(KAMILEN,RESONANCE_AMULET_6,1)
STARTED.addQuestDrop(KAMILEN,HALISHA_MARKS,1)

print "importing quests: 90: Saga Of The Storm Screamer"
