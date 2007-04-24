# Maked by Mr. Have fun! Version 0.2
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "405_PathToCleric"

LETTER_OF_ORDER1_ID = 1191
LETTER_OF_ORDER2_ID = 1192
BOOK_OF_LEMONIELL_ID = 1193
BOOK_OF_VIVI_ID = 1194
BOOK_OF_SIMLON_ID = 1195
BOOK_OF_PRAGA_ID = 1196
CERTIFICATE_OF_GALLINT_ID = 1197
PENDANT_OF_MOTHER_ID = 1198
NECKLACE_OF_MOTHER_ID = 1199
LEMONIELLS_COVENANT_ID = 1200
MARK_OF_FAITH_ID = 1201

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        if st.getPlayer().getLevel() >= 19 and st.getPlayer().getClassId().getId() == 0x0a and st.getQuestItemsCount(MARK_OF_FAITH_ID) == 0 :
          st.set("cond","1")
          st.setState(STARTED)
          st.playSound("ItemSound.quest_accept")
          st.giveItems(LETTER_OF_ORDER1_ID,1)
          htmltext = "30022-05.htm"
        elif st.getPlayer().getClassId().getId() != 0x0a :
            if st.getPlayer().getClassId().getId() == 0x0f :
              htmltext = "30022-02a.htm"
            else:
              htmltext = "30022-02.htm"
        elif st.getPlayer().getLevel()<19 and st.getPlayer().getClassId().getId() == 0x0a :
            htmltext = "30022-03.htm"
        elif st.getPlayer().getLevel() >= 19 and st.getPlayer().getClassId().getId() == 0x0a and st.getQuestItemsCount(MARK_OF_FAITH_ID) == 1 :
            htmltext = "30022-04.htm"
    return htmltext

 def onTalk (self,npc,player):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   id = st.getState()
   if npcId != 30022 and id != STARTED : return htmltext
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 30022 and int(st.get("cond"))==0 :
        if int(st.get("cond"))<15 :
          if st.getQuestItemsCount(MARK_OF_FAITH_ID) == 0 :
              htmltext = "30022-01.htm"
              return htmltext
          else:
              htmltext = "30022-04.htm"
        else:
            htmltext = "30022-04.htm"
   elif npcId == 30022 and int(st.get("cond")) and st.getQuestItemsCount(LETTER_OF_ORDER2_ID)==1 and st.getQuestItemsCount(LEMONIELLS_COVENANT_ID)==0 :
        htmltext = "30022-07.htm"
   elif npcId == 30022 and int(st.get("cond")) and st.getQuestItemsCount(LETTER_OF_ORDER2_ID)==1 and st.getQuestItemsCount(LEMONIELLS_COVENANT_ID)==1 :
        htmltext = "30022-09.htm"
        st.takeItems(LEMONIELLS_COVENANT_ID,1)
        st.takeItems(LETTER_OF_ORDER2_ID,1)
        st.giveItems(MARK_OF_FAITH_ID,1)
        st.set("cond","0")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
   elif npcId == 30022 and int(st.get("cond")) and st.getQuestItemsCount(LETTER_OF_ORDER1_ID)==1 :
        if st.getQuestItemsCount(BOOK_OF_VIVI_ID) == 1 and st.getQuestItemsCount(BOOK_OF_SIMLON_ID)>0 and st.getQuestItemsCount(BOOK_OF_PRAGA_ID) == 1 :
            htmltext = "30022-08.htm"
            st.takeItems(BOOK_OF_PRAGA_ID,1)
            st.takeItems(BOOK_OF_VIVI_ID,1)
            st.takeItems(BOOK_OF_SIMLON_ID,3)
            st.takeItems(LETTER_OF_ORDER1_ID,1)
            st.giveItems(LETTER_OF_ORDER2_ID,1)
            st.set("cond","3")
        else:
            htmltext = "30022-06.htm"
   elif npcId == 30253 and int(st.get("cond")) and st.getQuestItemsCount(LETTER_OF_ORDER1_ID)==1 :
        if st.getQuestItemsCount(BOOK_OF_SIMLON_ID) == 0 :
            htmltext = "30253-01.htm"
            st.giveItems(BOOK_OF_SIMLON_ID,3)
        elif st.getQuestItemsCount(BOOK_OF_SIMLON_ID)>0 :
            htmltext = "30253-02.htm"
   elif npcId == 30030 and int(st.get("cond")) and st.getQuestItemsCount(LETTER_OF_ORDER1_ID)==1 :
        if st.getQuestItemsCount(BOOK_OF_VIVI_ID) == 0 :
            htmltext = "30030-01.htm"
            st.giveItems(BOOK_OF_VIVI_ID,1)
        elif st.getQuestItemsCount(BOOK_OF_VIVI_ID) == 1 :
            htmltext = "30030-02.htm"
   elif npcId == 30333 and int(st.get("cond")) and st.getQuestItemsCount(LETTER_OF_ORDER1_ID)==1 :
        if st.getQuestItemsCount(BOOK_OF_PRAGA_ID) == 0 and st.getQuestItemsCount(NECKLACE_OF_MOTHER_ID) == 0 :
            htmltext = "30333-01.htm"
            st.giveItems(NECKLACE_OF_MOTHER_ID,1)
        elif st.getQuestItemsCount(BOOK_OF_PRAGA_ID) == 0 and st.getQuestItemsCount(NECKLACE_OF_MOTHER_ID) == 1 and st.getQuestItemsCount(PENDANT_OF_MOTHER_ID) == 0 :
            htmltext = "30333-02.htm"
        elif st.getQuestItemsCount(BOOK_OF_PRAGA_ID) == 0 and st.getQuestItemsCount(NECKLACE_OF_MOTHER_ID) == 1 and st.getQuestItemsCount(PENDANT_OF_MOTHER_ID) == 1 :
            htmltext = "30333-03.htm"
            st.takeItems(NECKLACE_OF_MOTHER_ID,1)
            st.takeItems(PENDANT_OF_MOTHER_ID,1)
            st.giveItems(BOOK_OF_PRAGA_ID,1)
            st.set("cond","2")
        elif st.getQuestItemsCount(BOOK_OF_PRAGA_ID)>0 :
            htmltext = "30333-04.htm"
   elif npcId == 30408 and int(st.get("cond")) :
        if st.getQuestItemsCount(LETTER_OF_ORDER2_ID) == 0 :
          htmltext = "30408-02.htm"
        elif st.getQuestItemsCount(LETTER_OF_ORDER2_ID) == 1 and st.getQuestItemsCount(BOOK_OF_LEMONIELL_ID) == 0 and st.getQuestItemsCount(LEMONIELLS_COVENANT_ID) == 0 and st.getQuestItemsCount(CERTIFICATE_OF_GALLINT_ID) == 0 :
            htmltext = "30408-01.htm"
            st.giveItems(BOOK_OF_LEMONIELL_ID,1)
            st.set("cond","4")
        elif st.getQuestItemsCount(LETTER_OF_ORDER2_ID) == 1 and st.getQuestItemsCount(BOOK_OF_LEMONIELL_ID) == 1 and st.getQuestItemsCount(LEMONIELLS_COVENANT_ID) == 0 and st.getQuestItemsCount(CERTIFICATE_OF_GALLINT_ID) == 0 :
            htmltext = "30408-03.htm"
        elif st.getQuestItemsCount(LETTER_OF_ORDER2_ID) == 1 and st.getQuestItemsCount(BOOK_OF_LEMONIELL_ID) == 0 and st.getQuestItemsCount(LEMONIELLS_COVENANT_ID) == 0 and st.getQuestItemsCount(CERTIFICATE_OF_GALLINT_ID) == 1 :
            htmltext = "30408-04.htm"
            st.takeItems(CERTIFICATE_OF_GALLINT_ID,1)
            st.giveItems(LEMONIELLS_COVENANT_ID,1)
            st.set("cond","6")
        elif st.getQuestItemsCount(LETTER_OF_ORDER2_ID) == 1 and st.getQuestItemsCount(BOOK_OF_LEMONIELL_ID) == 0 and st.getQuestItemsCount(LEMONIELLS_COVENANT_ID) == 1 and st.getQuestItemsCount(CERTIFICATE_OF_GALLINT_ID) == 0 :
            htmltext = "30408-05.htm"
   elif npcId == 30017 and int(st.get("cond")) and st.getQuestItemsCount(LETTER_OF_ORDER2_ID)==1 and st.getQuestItemsCount(LEMONIELLS_COVENANT_ID)==0 :
        if st.getQuestItemsCount(BOOK_OF_LEMONIELL_ID) == 1 and st.getQuestItemsCount(CERTIFICATE_OF_GALLINT_ID) == 0 :
            htmltext = "30017-01.htm"
            st.takeItems(BOOK_OF_LEMONIELL_ID,1)
            st.giveItems(CERTIFICATE_OF_GALLINT_ID,1)
            st.set("cond","5")
        elif st.getQuestItemsCount(BOOK_OF_LEMONIELL_ID) == 0 and st.getQuestItemsCount(CERTIFICATE_OF_GALLINT_ID) == 1 :
            htmltext = "30017-02.htm"
   return htmltext

 def onKill (self,npc,player):
   st = player.getQuestState(qn)
   if not st : return 
   if st.getState() != STARTED : return 
   
   npcId = npc.getNpcId()
   if npcId == 20026 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(PENDANT_OF_MOTHER_ID) == 0 :
          st.giveItems(PENDANT_OF_MOTHER_ID,1)
          st.playSound("ItemSound.quest_middle")
   elif npcId == 20029 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(PENDANT_OF_MOTHER_ID) == 0 :
          st.giveItems(PENDANT_OF_MOTHER_ID,1)
          st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(405,qn,"Path To Cleric")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30022)

QUEST.addTalkId(30022)

QUEST.addTalkId(30017)
QUEST.addTalkId(30030)
QUEST.addTalkId(30253)
QUEST.addTalkId(30333)
QUEST.addTalkId(30408)

QUEST.addKillId(20026)
QUEST.addKillId(20029)

STARTED.addQuestDrop(30408,LEMONIELLS_COVENANT_ID,1)
STARTED.addQuestDrop(30022,LETTER_OF_ORDER2_ID,1)
STARTED.addQuestDrop(30333,BOOK_OF_PRAGA_ID,1)
STARTED.addQuestDrop(30030,BOOK_OF_VIVI_ID,1)
STARTED.addQuestDrop(30253,BOOK_OF_SIMLON_ID,1)
STARTED.addQuestDrop(30022,LETTER_OF_ORDER1_ID,1)
STARTED.addQuestDrop(30333,NECKLACE_OF_MOTHER_ID,1)
STARTED.addQuestDrop(20026,PENDANT_OF_MOTHER_ID,1)
STARTED.addQuestDrop(20029,PENDANT_OF_MOTHER_ID,1)
STARTED.addQuestDrop(30017,CERTIFICATE_OF_GALLINT_ID,1)
STARTED.addQuestDrop(30408,BOOK_OF_LEMONIELL_ID,1)

print "importing quests: 405: Path To Cleric"