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
        htmltext = "7623-07.htm"
        if int(st.get("cond"))==0 :
           st.set("cond","1")
           st.setState(STARTED)
           st.playSound("ItemSound.quest_accept")
           st.giveItems(ORDER_GLUDIO_ID,1)
           st.giveItems(ORDER_DION_ID,1)
           st.giveItems(ORDER_GIRAN_ID,1)
           st.giveItems(ORDER_OREN_ID,1)
           st.giveItems(ORDER_ADEN_ID,1)
    elif event == "7623_1" :
          if st.getPlayer().getRace().ordinal() == 3 :
            htmltext = "7623-05.htm"
          else:
            htmltext = "7623-04.htm"
    elif event == "7623_2" :
          htmltext = "7623-06.htm"
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
        if st.getQuestItemsCount(FINAL_ORDER_ID)==0:
            for i in [
            PUNCHERS_SHARD_ID,
            NOBLE_ANTS_FEELER_ID,
            DEADSEEKER_FANG_ID,
            DRONES_CHITIN_ID,
            OVERLORD_NECKLACE_ID,
            CRIMSONBINDS_CHAIN_ID,
            CHIEFS_AMULET_ID,
            TEMPERED_EYE_MEAT_ID,
            TAMRIN_ORCS_RING_ID,
            TAMRIN_ORCS_ARROW_ID,
            ORDER_GLUDIO_ID,
            ORDER_DION_ID,
            ORDER_GIRAN_ID,
            ORDER_OREN_ID,
            ORDER_ADEN_ID
            ]:
             st.takeItems(i,st.getQuestItemsCount(i))
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
        if int(st.get("cond")) < 15 :
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
      htmltext = "<html><head><body>This quest has already been completed.</body></html>"
   elif npcId == 7623 and int(st.get("cond"))==1 :
      if st.getQuestItemsCount(ORDER_GLUDIO_ID)>0 and st.getQuestItemsCount(ORDER_DION_ID)>0 and st.getQuestItemsCount(ORDER_GIRAN_ID)>0 and st.getQuestItemsCount(ORDER_OREN_ID)>0 and st.getQuestItemsCount(ORDER_ADEN_ID)>0 :
        if st.getQuestItemsCount(PUNCHERS_SHARD_ID) == 10 and st.getQuestItemsCount(NOBLE_ANTS_FEELER_ID) == 10 and st.getQuestItemsCount(DRONES_CHITIN_ID) == 10 and st.getQuestItemsCount(DEADSEEKER_FANG_ID) == 10 and st.getQuestItemsCount(OVERLORD_NECKLACE_ID) == 10 and st.getQuestItemsCount(CRIMSONBINDS_CHAIN_ID) == 10 and st.getQuestItemsCount(CHIEFS_AMULET_ID) == 10 and st.getQuestItemsCount(TEMPERED_EYE_MEAT_ID) == 10 and st.getQuestItemsCount(TAMRIN_ORCS_RING_ID) == 10 and st.getQuestItemsCount(TAMRIN_ORCS_ARROW_ID) == 10 :
          htmltext = "7623-13.htm"
        else:
          htmltext = "7623-14.htm"
      else:
          htmltext = "7623-14.htm"
          for i in [ORDER_GLUDIO_ID,ORDER_DION_ID,ORDER_GIRAN_ID,ORDER_OREN_ID,ORDER_ADEN_ID]:
            if st.getQuestItemsCount(i)==0:
                st.giveItems(i,1) 
   elif npcId == 7623 and int(st.get("cond"))==2 and st.getQuestItemsCount(FINAL_ORDER_ID)>0 :
        if st.getQuestItemsCount(EXCUROS_SKIN_ID)>2 and st.getQuestItemsCount(KRATORS_SHARD_ID)>2 and st.getQuestItemsCount(RAKINS_MACE_ID)>2 and st.getQuestItemsCount(GRANDIS_SKIN_ID)>2 and st.getQuestItemsCount(TIMAK_ORCS_BELT_ID)>2 :
            st.takeItems(EXCUROS_SKIN_ID,st.getQuestItemsCount(EXCUROS_SKIN_ID))
            st.takeItems(KRATORS_SHARD_ID,st.getQuestItemsCount(KRATORS_SHARD_ID))
            st.takeItems(GRANDIS_SKIN_ID,st.getQuestItemsCount(GRANDIS_SKIN_ID))
            st.takeItems(TIMAK_ORCS_BELT_ID,st.getQuestItemsCount(TIMAK_ORCS_BELT_ID))
            st.takeItems(RAKINS_MACE_ID,st.getQuestItemsCount(RAKINS_MACE_ID))
            st.addExpAndSp(24000,3100)
            st.giveItems(MARK_OF_DUELIST_ID,1)
            st.takeItems(FINAL_ORDER_ID,1)
            htmltext = "7623-18.htm"
            st.set("cond","0")
            st.set("onlyone","1")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
        else:
          htmltext = "7623-17.htm"
   return htmltext

 def onKill (self,npcId,st):
  i={
85:(1,10,PUNCHERS_SHARD_ID),
90:(1,10,NOBLE_ANTS_FEELER_ID),
234:(1,10,DRONES_CHITIN_ID),
202:(1,10,DEADSEEKER_FANG_ID),
270:(1,10,OVERLORD_NECKLACE_ID),
552:(1,10,CRIMSONBINDS_CHAIN_ID),
582:(1,10,CHIEFS_AMULET_ID),
564:(1,10,TEMPERED_EYE_MEAT_ID),
601:(1,10,TAMRIN_ORCS_RING_ID),
602:(1,10,TAMRIN_ORCS_ARROW_ID),
604:(2,3,RAKINS_MACE_ID),
214:(2,3,EXCUROS_SKIN_ID),
217:(2,3,KRATORS_SHARD_ID),
588:(2,3,TIMAK_ORCS_BELT_ID),
554:(2,3,GRANDIS_SKIN_ID)
}
  if int(st.get("cond"))==i[npcId][0] and st.getQuestItemsCount(i[npcId][2])<i[npcId][1]:
   st.giveItems(i[npcId][2],1)
   st.playSound("ItemSound.quest_middle")
  return

QUEST       = Quest(222,"222_TestOfDuelist","Test Of Duelist")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7623)

STARTING.addTalkId(7623)

STARTED.addTalkId(7623)

for i in [202,214,217,234,270,552,554,564,582,588,601,602,604,85,90]:
    STARTED.addKillId(i)