# Maked by Mr. Have fun! Version 0.2
print "importing quests: 222: Test Of Duelist"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_DUELIST_ID = 2762
ORDER_GLUDIO_ID = 2763
ORDER_DION_ID = 2764
ORDER_GIRAN_ID = 2765
ORDER_OREN_ID = 2766
ORDER_ADEN_ID = 2767
PUNCHERS_SHARD_ID = 2768
NOBLE_ANTS_FEELER_ID = 2769
DEADSEEKER_FANG_ID = 2771
DRONES_CHITIN_ID = 2770
OVERLORD_NECKLACE_ID = 2772
CRIMSONBINDS_CHAIN_ID = 2773
CHIEFS_AMULET_ID = 2774
TEMPERED_EYE_MEAT_ID = 2775
TAMRIN_ORCS_RING_ID = 2776
TAMRIN_ORCS_ARROW_ID = 2777
FINAL_ORDER_ID = 2778
EXCUROS_SKIN_ID = 2779
KRATORS_SHARD_ID = 2780
GRANDIS_SKIN_ID = 2781
TIMAK_ORCS_BELT_ID = 2782
RAKINS_MACE_ID = 2783

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        htmlfile = "7623-07.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.giveItems(ORDER_GLUDIO_ID)
        st.giveItems(ORDER_DION_ID,1)
        st.giveItems(ORDER_GIRAN_ID,1)
        st.giveItems(ORDER_OREN_ID,1)
        st.giveItems(ORDER_ADEN_ID,1)
        st.set("cond","1")
    elif event == "7623_1" :
          if st.getPlayer().getRace().ordinal() != 3 :
            htmltext = "7623-05.htm"
            htmltext = "7623-04.htm"
    elif event == "7623_2" :
          htmltext = "7623-06.htm"
          st.set("cond","1")
          return htmltext
    elif event == "7623_3" :
          htmltext = "7623-08.htm"
    elif event == "7623_4" :
          htmltext = "7623-09.htm"
    elif event == "7623_5" :
          htmltext = "7623-10.htm"
    elif event == "7623_6" :
          htmltext = "7623-11.htm"
    elif event == "7623_7" :
          htmltext = "7623-12.htm"
    elif event == "7623_8" :
          htmltext = "7623-07.htm"
    elif event == "7623_9" :
          htmltext = "7623-15.htm"
    elif event == "7623_10" :
          htmltext = "7623-16.htm"
          st.takeItems(PUNCHERS_SHARD_ID,st.getQuestItemsCount(PUNCHERS_SHARD_ID))
          st.takeItems(NOBLE_ANTS_FEELER_ID,st.getQuestItemsCount(NOBLE_ANTS_FEELER_ID))
          st.takeItems(DEADSEEKER_FANG_ID,st.getQuestItemsCount(DEADSEEKER_FANG_ID))
          st.takeItems(DRONES_CHITIN_ID,st.getQuestItemsCount(DRONES_CHITIN_ID))
          st.takeItems(OVERLORD_NECKLACE_ID,st.getQuestItemsCount(OVERLORD_NECKLACE_ID))
          st.takeItems(CRIMSONBINDS_CHAIN_ID,st.getQuestItemsCount(CRIMSONBINDS_CHAIN_ID))
          st.takeItems(CHIEFS_AMULET_ID,st.getQuestItemsCount(CHIEFS_AMULET_ID))
          st.takeItems(TEMPERED_EYE_MEAT_ID,st.getQuestItemsCount(TEMPERED_EYE_MEAT_ID))
          st.takeItems(TAMRIN_ORCS_RING_ID,st.getQuestItemsCount(TAMRIN_ORCS_RING_ID))
          st.takeItems(TAMRIN_ORCS_ARROW_ID,st.getQuestItemsCount(TAMRIN_ORCS_ARROW_ID))
          st.takeItems(ORDER_GLUDIO_ID,1)
          st.takeItems(ORDER_DION_ID,1)
          st.takeItems(ORDER_GIRAN_ID,1)
          st.takeItems(ORDER_OREN_ID,1)
          st.takeItems(ORDER_ADEN_ID,1)
          st.set("cond","2")
          st.giveItems(FINAL_ORDER_ID,1)
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7623 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if int(st.get("cond"))<15 :
          if (st.getPlayer().getClassId().getId() == 0x01 or st.getPlayer().getClassId().getId() == 0x2f or st.getPlayer().getClassId().getId() == 0x13 or st.getPlayer().getClassId().getId() == 0x20) :
            if st.getPlayer().getLevel() >= 39 :
              htmltext = "7623-03.htm"
            else:
              htmltext = "7623-01.htm"
          else:
            htmltext = "7623-02.htm"
        else:
          htmltext = "7623-02.htm"
   elif npcId == 7623 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7623 and int(st.get("cond"))==1 and st.getQuestItemsCount((ORDER_GLUDIO_ID) and st.getQuestItemsCount(ORDER_DION_ID) and st.getQuestItemsCount(ORDER_GIRAN_ID) and st.getQuestItemsCount(ORDER_OREN_ID) and st.getQuestItemsCount(ORDER_ADEN_ID)) :
        if st.getQuestItemsCount(PUNCHERS_SHARD_ID) == 10 and st.getQuestItemsCount(NOBLE_ANTS_FEELER_ID) == 10 and st.getQuestItemsCount(DRONES_CHITIN_ID) == 10 and st.getQuestItemsCount(DEADSEEKER_FANG_ID) == 10 and st.getQuestItemsCount(OVERLORD_NECKLACE_ID) == 10 and st.getQuestItemsCount(CRIMSONBINDS_CHAIN_ID) == 10 and st.getQuestItemsCount(CHIEFS_AMULET_ID) == 10 and st.getQuestItemsCount(TEMPERED_EYE_MEAT_ID) == 10 and st.getQuestItemsCount(TAMRIN_ORCS_RING_ID) == 10 and st.getQuestItemsCount(TAMRIN_ORCS_ARROW_ID) == 10 :
          htmltext = "7623-13.htm"
        else:
          htmltext = "7623-14.htm"
   elif npcId == 7623 and int(st.get("cond"))==1 and st.getQuestItemsCount(FINAL_ORDER_ID)==1 :
        if st.getQuestItemsCount(EXCUROS_SKIN_ID) >= 3 and st.getQuestItemsCount(KRATORS_SHARD_ID) >= 3 and st.getQuestItemsCount(RAKINS_MACE_ID) >= 3 and st.getQuestItemsCount(GRANDIS_SKIN_ID) >= 3 and st.getQuestItemsCount(TIMAK_ORCS_BELT_ID) >= 3 :
          if st.getGameTicks() != int(st.get("id")) :
            st.set("id",str(st.getGameTicks()))
            st.takeItems(EXCUROS_SKIN_ID,st.getQuestItemsCount(EXCUROS_SKIN_ID))
            st.takeItems(KRATORS_SHARD_ID,st.getQuestItemsCount(KRATORS_SHARD_ID))
            st.takeItems(GRANDIS_SKIN_ID,st.getQuestItemsCount(GRANDIS_SKIN_ID))
            st.takeItems(TIMAK_ORCS_BELT_ID,st.getQuestItemsCount(TIMAK_ORCS_BELT_ID))
            st.takeItems(RAKINS_MACE_ID,st.getQuestItemsCount(RAKINS_MACE_ID))
            st.addExpAndSp(24000,0)
            st.addExpAndSp(0,3100)
            st.giveItems(MARK_OF_DUELIST_ID,1)
            st.takeItems(FINAL_ORDER_ID,1)
            htmlfile = "7623-18.htm"
            st.set("cond","0")
            st.set("onlyone","1")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
        else:
          htmltext = "7623-17.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 85 :
    if int(st.get("cond")) == 1 and st.getQuestItemsCount() <= 10 :
      st.giveItems(PUNCHERS_SHARD_ID,1)
   elif npcId == 90 :
    if int(st.get("cond")) == 1 and st.getQuestItemsCount() <= 10 :
      st.giveItems(NOBLE_ANTS_FEELER_ID,1)
   elif npcId == 234 :
    if int(st.get("cond")) == 1 and st.getQuestItemsCount() <= 10 :
      st.giveItems(DRONES_CHITIN_ID,1)
   elif npcId == 202 :
    if int(st.get("cond")) == 1 and st.getQuestItemsCount() <= 10 :
      st.giveItems(DEADSEEKER_FANG_ID,1)
   elif npcId == 217 :
    if int(st.get("cond")) == 2 and st.getQuestItemsCount() <= 3 :
      st.giveItems(KRATORS_SHARD_ID,1)
   elif npcId == 270 :
    if int(st.get("cond")) == 1 and st.getQuestItemsCount() <= 10 :
      st.giveItems(OVERLORD_NECKLACE_ID,1)
   elif npcId == 552 :
    if int(st.get("cond")) == 1 and st.getQuestItemsCount() <= 10 :
      st.giveItems(CRIMSONBINDS_CHAIN_ID,1)
   elif npcId == 554 :
    if int(st.get("cond")) == 2 and st.getQuestItemsCount() <= 3 :
      st.giveItems(GRANDIS_SKIN_ID,1)
   elif npcId == 582 :
    if int(st.get("cond")) == 1 and st.getQuestItemsCount() <= 10 :
      st.giveItems(CHIEFS_AMULET_ID,1)
   elif npcId == 564 :
    if int(st.get("cond")) == 1 and st.getQuestItemsCount() <= 10 :
      st.giveItems(TEMPERED_EYE_MEAT_ID,1)
   elif npcId == 588 :
    if int(st.get("cond")) == 2 and st.getQuestItemsCount() <= 3 :
      st.giveItems(TIMAK_ORCS_BELT_ID,1)
   elif npcId == 601 :
    if int(st.get("cond")) == 1 and st.getQuestItemsCount() <= 10 :
      st.giveItems(TAMRIN_ORCS_RING_ID,1)
   elif npcId == 602 :
    if int(st.get("cond")) == 1 and st.getQuestItemsCount() <= 10 :
      st.giveItems(TAMRIN_ORCS_ARROW_ID,1)
   elif npcId == 604 :
    if int(st.get("cond")) == 2 and st.getQuestItemsCount() <= 3 :
      st.giveItems(RAKINS_MACE_ID,1)
   elif npcId == 214 :
    if int(st.get("cond")) == 2 and st.getQuestItemsCount() <= 3 :
      st.giveItems(EXCUROS_SKIN_ID,1)
   return

QUEST       = Quest(222,"222_TestOfDuelist","Test Of Duelist")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7623)

STARTED.addTalkId(7623)

STARTED.addKillId(202)
STARTED.addKillId(214)
STARTED.addKillId(217)
STARTED.addKillId(234)
STARTED.addKillId(270)
STARTED.addKillId(552)
STARTED.addKillId(554)
STARTED.addKillId(564)
STARTED.addKillId(582)
STARTED.addKillId(588)
STARTED.addKillId(601)
STARTED.addKillId(602)
STARTED.addKillId(604)
STARTED.addKillId(85)
STARTED.addKillId(90)

STARTED.addQuestDrop(85,PUNCHERS_SHARD_ID,1)
STARTED.addQuestDrop(90,NOBLE_ANTS_FEELER_ID,1)
STARTED.addQuestDrop(202,DEADSEEKER_FANG_ID,1)
STARTED.addQuestDrop(234,DRONES_CHITIN_ID,1)
STARTED.addQuestDrop(270,OVERLORD_NECKLACE_ID,1)
STARTED.addQuestDrop(552,CRIMSONBINDS_CHAIN_ID,1)
STARTED.addQuestDrop(582,CHIEFS_AMULET_ID,1)
STARTED.addQuestDrop(564,TEMPERED_EYE_MEAT_ID,1)
STARTED.addQuestDrop(601,TAMRIN_ORCS_RING_ID,1)
STARTED.addQuestDrop(602,TAMRIN_ORCS_ARROW_ID,1)
STARTED.addQuestDrop(7623,ORDER_GLUDIO_ID,1)
STARTED.addQuestDrop(7623,ORDER_DION_ID,1)
STARTED.addQuestDrop(7623,ORDER_GIRAN_ID,1)
STARTED.addQuestDrop(7623,ORDER_OREN_ID,1)
STARTED.addQuestDrop(7623,ORDER_ADEN_ID,1)
STARTED.addQuestDrop(214,EXCUROS_SKIN_ID,1)
STARTED.addQuestDrop(217,KRATORS_SHARD_ID,1)
STARTED.addQuestDrop(554,GRANDIS_SKIN_ID,1)
STARTED.addQuestDrop(588,TIMAK_ORCS_BELT_ID,1)
STARTED.addQuestDrop(604,RAKINS_MACE_ID,1)
STARTED.addQuestDrop(7623,FINAL_ORDER_ID,1)
