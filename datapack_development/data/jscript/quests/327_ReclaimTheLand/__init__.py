# Maked by Mr. Have fun! Version 0.2
print "importing quests: 327: Reclaim The Land"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

TUREK_DOGTAG_ID = 1846
TUREK_MEDALLION_ID = 1847
ADENA_ID = 57
CLAY_URN_FRAGMENT_ID = 1848
ANCIENT_CLAY_URN_ID = 1852
BRASS_TRINKET_PIECE_ID = 1849
ANCIENT_BRASS_TIARA_ID = 1853
BRONZE_MIRROR_PIECE_ID = 1850
ANCIENT_BRONZE_MIRROR_ID = 1854
JADE_NECKLACE_BEAD_ID = 1851
ANCIENT_JADE_NECKLACE_ID = 1855

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    n=st.getRandom(100)
    if event == "1" :
      st.set("id","0")
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      htmltext = "7597-03.htm"
    elif event == "7597_1" :
          htmltext = "7597-06.htm"
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
    elif event == "7597_2" :
          htmltext = "7597-07.htm"
    elif event == "7313_1" and st.getGameTicks() != int(st.get("id")) :
          st.set("id",str(st.getGameTicks()))
          if st.getQuestItemsCount(CLAY_URN_FRAGMENT_ID) < 5 :
            htmltext = "7313-02.htm"
          elif n < 80 :
            htmltext = "7313-03.htm"
            st.takeItems(CLAY_URN_FRAGMENT_ID,5)
            st.giveItems(ANCIENT_CLAY_URN_ID,1)
          else:
            htmltext = "7313-10.htm"
            st.takeItems(CLAY_URN_FRAGMENT_ID,5)
    elif event == "7313_2" and st.getGameTicks() != int(st.get("id")) :
          st.set("id",str(st.getGameTicks()))
          if st.getQuestItemsCount(BRASS_TRINKET_PIECE_ID) < 5 :
            htmltext = "7313-04.htm"
          elif n < 80 :
            htmltext = "7313-05.htm"
            st.takeItems(BRASS_TRINKET_PIECE_ID,5)
            st.giveItems(ANCIENT_BRASS_TIARA_ID,1)
          else:
            htmltext = "7313-10.htm"
            st.takeItems(BRASS_TRINKET_PIECE_ID,5)
    elif event == "7313_3" and st.getGameTicks() != int(st.get("id")) :
          st.set("id",str(st.getGameTicks()))
          if st.getQuestItemsCount(BRONZE_MIRROR_PIECE_ID) < 5 :
            htmltext = "7313-06.htm"
          elif n < 80 :
            htmltext = "7313-07.htm"
            st.takeItems(BRONZE_MIRROR_PIECE_ID,5)
            st.giveItems(ANCIENT_BRONZE_MIRROR_ID,1)
          else:
            htmltext = "7313-10.htm"
            st.takeItems(BRONZE_MIRROR_PIECE_ID,5)
    elif event == "7313_4" and st.getGameTicks() != int(st.get("id")) :
          st.set("id",str(st.getGameTicks()))
          if st.getQuestItemsCount(JADE_NECKLACE_BEAD_ID) < 5 :
            htmltext = "7313-08.htm"
          elif n < 80 :
            htmltext = "7313-09.htm"
            st.takeItems(JADE_NECKLACE_BEAD_ID,5)
            st.giveItems(ANCIENT_JADE_NECKLACE_ID,1)
          else:
            htmltext = "7313-10.htm"
            st.takeItems(JADE_NECKLACE_BEAD_ID,5)
    elif event == "7313_4" :
          htmltext = "7313-01.htm"
    elif event == "7034_1" and st.getGameTicks() != int(st.get("id")) :
          st.set("id",str(st.getGameTicks()))
          n = st.getQuestItemsCount(CLAY_URN_FRAGMENT_ID)
          if n < 1 :
            htmltext = "7034-02.htm"
          else:
            htmltext = "7034-03.htm"
            st.takeItems(CLAY_URN_FRAGMENT_ID,n)
            st.addExpAndSp(n*152,0)
            st.playSound("ItemSound.quest_itemget")
    elif event == "7034_2" and st.getGameTicks() != int(st.get("id")) :
          st.set("id",str(st.getGameTicks()))
          n = st.getQuestItemsCount(BRASS_TRINKET_PIECE_ID)
          if n < 1 :
            htmltext = "7034-02.htm"
          else:
            htmltext = "7034-04.htm"
            st.takeItems(BRASS_TRINKET_PIECE_ID,n)
            st.addExpAndSp(n*182,0)
            st.playSound("ItemSound.quest_itemget")
    elif event == "7034_3" and st.getGameTicks() != int(st.get("id")) :
          st.set("id",str(st.getGameTicks()))
          n = st.getQuestItemsCount(BRONZE_MIRROR_PIECE_ID)
          if n < 1 :
            htmltext = "7034-02.htm"
          else:
            htmltext = "7034-05.htm"
            st.takeItems(BRONZE_MIRROR_PIECE_ID,n)
            st.addExpAndSp(n*182,0)
            st.playSound("ItemSound.quest_itemget")
    elif event == "7034_4" and st.getGameTicks() != int(st.get("id")) :
          st.set("id",str(st.getGameTicks()))
          n = st.getQuestItemsCount(JADE_NECKLACE_BEAD_ID)
          if n < 1 :
            htmltext = "7034-02.htm"
          else:
            htmltext = "7034-06.htm"
            st.takeItems(JADE_NECKLACE_BEAD_ID,n)
            st.addExpAndSp(n*182,0)
            st.playSound("ItemSound.quest_itemget")
    elif event == "7034_6" and st.getGameTicks() != int(st.get("id")) :
          st.set("id",str(st.getGameTicks()))
          n1 = 0
          if st.getQuestItemsCount(ANCIENT_CLAY_URN_ID) :
	    n1 = 1
	    n=st.getQuestItemsCount(ANCIENT_CLAY_URN_ID)
            st.takeItems(ANCIENT_CLAY_URN_ID,n)
            st.addExpAndSp(n*913,0)
            st.playSound("ItemSound.quest_itemget")
          if st.getQuestItemsCount(ANCIENT_BRASS_TIARA_ID) :
	    n1 = 1
	    n=st.getQuestItemsCount(ANCIENT_BRASS_TIARA_ID)
            st.takeItems(ANCIENT_BRASS_TIARA_ID,n)
            st.addExpAndSp(n*1065,0)
            st.playSound("ItemSound.quest_itemget")
          if st.getQuestItemsCount(ANCIENT_BRONZE_MIRROR_ID) :
	    n1 = 1
	    n=st.getQuestItemsCount(ANCIENT_BRONZE_MIRROR_ID)
            st.takeItems(ANCIENT_BRONZE_MIRROR_ID,n)
            st.addExpAndSp(n*1065,0)
            st.playSound("ItemSound.quest_itemget")
          if st.getQuestItemsCount(ANCIENT_JADE_NECKLACE_ID) :
	    n1 = 1
	    n=st.getQuestItemsCount(ANCIENT_JADE_NECKLACE_ID)
            st.takeItems(ANCIENT_BRONZE_JADE_NECKLACE_ID,n)
            st.addExpAndSp(n*1294,0)
            st.playSound("ItemSound.quest_itemget")
          if n1 :
            htmltext = "7034-07.htm"
          else:
            htmltext = "7034-02.htm"
    elif event == "7034_6" :
          htmltext = "7034-01.htm"
    return htmltext


 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7597 and int(st.get("cond"))==0 :
      if st.getPlayer().getLevel() < 25 :
         htmltext = "7597-01.htm"
         st.exitQuest(1)
      else :
         htmltext = "7597-02.htm"
   elif npcId == 7597 and int(st.get("cond")) :
      if st.getQuestItemsCount(TUREK_DOGTAG_ID)+st.getQuestItemsCount(TUREK_MEDALLION_ID) < 1 :
        htmltext = "7597-04.htm"
      else:
        htmltext = "7597-05.htm"
        n = st.getQuestItemsCount(TUREK_DOGTAG_ID)
        st.giveItems(ADENA_ID,n*20)
        st.takeItems(TUREK_DOGTAG_ID,n)
        n = st.getQuestItemsCount(TUREK_MEDALLION_ID)
        st.giveItems(ADENA_ID,n*25)
        st.takeItems(TUREK_MEDALLION_ID,n)
   elif npcId == 7313 and int(st.get("cond")) :
      htmltext = "7313-01.htm"
   elif npcId == 7034 and int(st.get("cond")) :
      htmltext = "7034-01.htm"
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   if npcId == 500 :
    st.set("id","0")
    if int(st.get("cond")) == 1 :
     st.giveItems(TUREK_DOGTAG_ID,1)
     st.playSound("ItemSound.quest_itemget")
     n = st.getRandom(100)
     if n<7 :
      n = st.getRandom(100)
      if n < 25 :
        st.giveItems(CLAY_URN_FRAGMENT_ID,1)
      elif n < 50 :
        st.giveItems(BRASS_TRINKET_PIECE_ID,1)
      elif n < 75 :
        st.giveItems(BRONZE_MIRROR_PIECE_ID,1)
      else:
        st.giveItems(JADE_NECKLACE_BEAD_ID,1)
   elif npcId == 499 :
    st.set("id","0")
    if int(st.get("cond")) == 1 :
     st.giveItems(TUREK_DOGTAG_ID,1)
     st.playSound("ItemSound.quest_itemget")
     n = st.getRandom(100)
     if n<8 :
      n = st.getRandom(100)
      if n < 25 :
        st.giveItems(CLAY_URN_FRAGMENT_ID,1)
      elif n < 50 :
        st.giveItems(BRASS_TRINKET_PIECE_ID,1)
      elif n < 75 :
        st.giveItems(BRONZE_MIRROR_PIECE_ID,1)
      else:
        st.giveItems(JADE_NECKLACE_BEAD_ID,1)
   elif npcId == 496 :
    st.set("id","0")
    if int(st.get("cond")) == 1 :
     st.giveItems(TUREK_DOGTAG_ID,1)
     st.playSound("ItemSound.quest_itemget")
     n = st.getRandom(100)
     if n<9 :
      n = st.getRandom(100)
      if n < 25 :
        st.giveItems(CLAY_URN_FRAGMENT_ID,1)
      elif n < 50 :
        st.giveItems(BRASS_TRINKET_PIECE_ID,1)
      elif n < 75 :
        st.giveItems(BRONZE_MIRROR_PIECE_ID,1)
      else:
        st.giveItems(JADE_NECKLACE_BEAD_ID,1)
   elif npcId == 498 :
    st.set("id","0")
    if int(st.get("cond")) == 1 :
     st.giveItems(TUREK_DOGTAG_ID,1)
     st.playSound("ItemSound.quest_itemget")
     n = st.getRandom(100)
     if n<10 :
      n = st.getRandom(100)
      if n < 25 :
        st.giveItems(CLAY_URN_FRAGMENT_ID,1)
      elif n < 50 :
        st.giveItems(BRASS_TRINKET_PIECE_ID,1)
      elif n < 75 :
        st.giveItems(BRONZE_MIRROR_PIECE_ID,1)
      else:
        st.giveItems(JADE_NECKLACE_BEAD_ID,1)
   elif npcId == 497 :
    st.set("id","0")
    if int(st.get("cond")) == 1 :
     st.giveItems(TUREK_MEDALLION_ID,1)
     st.playSound("ItemSound.quest_itemget")
     n = st.getRandom(100)
     if n<11 :
      n = st.getRandom(100)
      if n < 25 :
        st.giveItems(CLAY_URN_FRAGMENT_ID,1)
      elif n < 50 :
        st.giveItems(BRASS_TRINKET_PIECE_ID,1)
      elif n < 75 :
        st.giveItems(BRONZE_MIRROR_PIECE_ID,1)
      else:
        st.giveItems(JADE_NECKLACE_BEAD_ID,1)
   elif npcId == 501 :
    st.set("id","0")
    if int(st.get("cond")) == 1 :
     st.giveItems(TUREK_MEDALLION_ID,1)
     st.playSound("ItemSound.quest_itemget")
     n = st.getRandom(100)
     if n<12 :
      n = st.getRandom(100)
      if n < 25 :
        st.giveItems(CLAY_URN_FRAGMENT_ID,1)
      elif n < 50 :
        st.giveItems(BRASS_TRINKET_PIECE_ID,1)
      elif n < 75 :
        st.giveItems(BRONZE_MIRROR_PIECE_ID,1)
      else:
        st.giveItems(JADE_NECKLACE_BEAD_ID,1)
   elif npcId == 495 :
    st.set("id","0")
    if int(st.get("cond")) == 1 :
     st.giveItems(TUREK_MEDALLION_ID,1)
     st.playSound("ItemSound.quest_itemget")
     n = st.getRandom(100)
     if n<13 :
      n = st.getRandom(100)
      if n < 25 :
        st.giveItems(CLAY_URN_FRAGMENT_ID,1)
      elif n < 50 :
        st.giveItems(BRASS_TRINKET_PIECE_ID,1)
      elif n < 75 :
        st.giveItems(BRONZE_MIRROR_PIECE_ID,1)
      else:
        st.giveItems(JADE_NECKLACE_BEAD_ID,1)
   return

QUEST       = Quest(327,"327_ReclaimTheLand","Reclaim The Land")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7597)

CREATED.addTalkId(7597)

STARTED.addTalkId(7034)
STARTED.addTalkId(7313)
STARTED.addTalkId(7597)

STARTED.addKillId(495)
STARTED.addKillId(496)
STARTED.addKillId(497)
STARTED.addKillId(498)
STARTED.addKillId(499)
STARTED.addKillId(500)
STARTED.addKillId(501)

for i in range(495,502) :
    STARTED.addQuestDrop(i,CLAY_URN_FRAGMENT_ID,1)
    STARTED.addQuestDrop(i,BRASS_TRINKET_PIECE_ID,1)
    STARTED.addQuestDrop(i,BRONZE_MIRROR_PIECE_ID,1)
    STARTED.addQuestDrop(i,JADE_NECKLACE_BEAD_ID,1)
