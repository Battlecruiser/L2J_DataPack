# Maked by Mr. Have fun! Version 0.2
print "importing quests: 217: Testimony Of Trust"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_TRUST_ID = 2734
LETTER_TO_ELF_ID = 1558
LETTER_TO_DARKELF_ID = 1556
LETTER_TO_DWARF_ID = 2737
LETTER_TO_ORC_ID = 2738
LETTER_TO_SERESIN_ID = 2739
SCROLL_OF_DARKELF_TRUST_ID = 2740
SCROLL_OF_ELF_TRUST_ID = 2741
SCROLL_OF_DWARF_TRUST_ID = 2742
SCROLL_OF_ORC_TRUST_ID = 2743
RECOMMENDATION_OF_HOLLIN_ID = 2744
ORDER_OF_OZZY_ID = 2745
BREATH_OF_WINDS_ID = 2746
SEED_OF_VERDURE_ID = 2747
LETTER_OF_THIFIELL_ID = 2748
BLOOD_OF_GUARDIAN_BASILISK_ID = 2749
GIANT_APHID_ID = 2750
STAKATOS_FLUIDS_ID = 2751
BASILISK_PLASMA_ID = 2752
HONEY_DEW_ID = 2753
STAKATO_ICHOR_ID = 2754
ORDER_OF_CLAYTON_ID = 2755
PARASITE_OF_LOTA_ID = 2756
LETTER_TO_MANAKIA_ID = 2757
LETTER_OF_MANAKIA_ID = 2758
LETTER_TO_NICHOLA_ID = 2759
ORDER_OF_NICHOLA_ID = 2760
HEART_OF_PORTA_ID = 2761

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      htmltext = "7191-04.htm"
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      st.giveItems(LETTER_TO_ELF_ID,1)
      st.giveItems(LETTER_TO_DARKELF_ID,1)
    elif event == "7154_1" :
          htmltext = "7154-02.htm"
    elif event == "7154_2" :
          htmltext = "7154-03.htm"
          st.giveItems(ORDER_OF_OZZY_ID,1)
          st.takeItems(LETTER_TO_ELF_ID,1)
          st.set("cond","2")
    elif event == "7358_1" :
          htmltext = "7358-02.htm"
          st.giveItems(LETTER_OF_THIFIELL_ID,1)
          st.takeItems(LETTER_TO_DARKELF_ID,1)
          st.set("cond","5")
    elif event == "7657_1" :
          if st.getPlayer().getLevel() >= 38 :
            htmltext = "7657-03.htm"
            st.giveItems(LETTER_TO_ORC_ID,1)
            st.giveItems(LETTER_TO_DWARF_ID,1)
            st.takeItems(LETTER_TO_SERESIN_ID,1)
            st.set("cond","9")
          else:
            htmltext = "7657-02.htm"
    elif event == "7565_1" :
          htmltext = "7565-02.htm"
          st.giveItems(LETTER_TO_MANAKIA_ID,1)
          st.takeItems(LETTER_TO_ORC_ID,1)
          st.set("cond","10")
    elif event == "7515_1" :
          htmltext = "7515-02.htm"
          st.takeItems(LETTER_TO_MANAKIA_ID,1)
          st.set("cond","11")
    elif event == "7531_1" :
          htmltext = "7531-02.htm"
          st.giveItems(LETTER_TO_NICHOLA_ID,1)
          st.takeItems(LETTER_TO_DWARF_ID,1)
          st.set("cond","15")
    elif event == "7621_1" :
          htmltext = "7621-02.htm"
          st.giveItems(ORDER_OF_NICHOLA_ID,1)
          st.takeItems(LETTER_TO_NICHOLA_ID,1)
          st.set("cond","16")
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7191 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if int(st.get("cond")) < 15 :
        if st.getPlayer().getRace().ordinal() == 0 and st.getPlayer().getLevel() >= 37 :
          htmltext = "7191-03.htm"
        elif st.getPlayer().getRace().ordinal() == 0 :
          htmltext = "7191-01.htm"
        else:
          htmltext = "7191-02.htm"
      else:
        htmltext = "7191-02.htm"
   elif npcId == 7191 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7191 and int(st.get("cond"))==7 and st.getQuestItemsCount(SCROLL_OF_ELF_TRUST_ID) and st.getQuestItemsCount(SCROLL_OF_DARKELF_TRUST_ID) :
      htmltext = "7191-05.htm"
      st.giveItems(LETTER_TO_SERESIN_ID,1)
      st.takeItems(SCROLL_OF_DARKELF_TRUST_ID,1)
      st.takeItems(SCROLL_OF_ELF_TRUST_ID,1)
      st.set("cond","8")
   elif npcId == 7191 and int(st.get("cond"))==18 and st.getQuestItemsCount(SCROLL_OF_DWARF_TRUST_ID) and st.getQuestItemsCount(SCROLL_OF_ORC_TRUST_ID) :
      htmltext = "7191-06.htm"
      st.giveItems(RECOMMENDATION_OF_HOLLIN_ID,1)
      st.takeItems(SCROLL_OF_DWARF_TRUST_ID,1)
      st.takeItems(SCROLL_OF_ORC_TRUST_ID,1)
      st.set("cond","19")
   elif npcId == 7191 and int(st.get("cond"))==19 :
      htmltext = "7191-07.htm"
   elif npcId == 7191 and int(st.get("cond"))==1 :
      htmltext = "7191-08.htm"
   elif npcId == 7191 and int(st.get("cond"))==8 :
      htmltext = "7191-09.htm"
   elif npcId == 7154 and int(st.get("cond"))==1 and st.getQuestItemsCount(LETTER_TO_ELF_ID) :
      htmltext = "7154-01.htm"
   elif npcId == 7154 and int(st.get("cond"))==2 and st.getQuestItemsCount(ORDER_OF_OZZY_ID) :
      htmltext = "7154-04.htm"
   elif npcId == 7154 and int(st.get("cond"))==3 and st.getQuestItemsCount(BREATH_OF_WINDS_ID) and st.getQuestItemsCount(SEED_OF_VERDURE_ID) :
      htmltext = "7154-05.htm"
      st.giveItems(SCROLL_OF_ELF_TRUST_ID,1)
      st.takeItems(BREATH_OF_WINDS_ID,1)
      st.takeItems(SEED_OF_VERDURE_ID,1)
      st.takeItems(ORDER_OF_OZZY_ID,1)
      st.set("cond","4")
   elif npcId == 7154 and int(st.get("cond"))==4 :
      htmltext = "7154-06.htm"
   elif npcId == 7358 and int(st.get("cond"))==4 and st.getQuestItemsCount(LETTER_TO_DARKELF_ID) :
      htmltext = "7358-01.htm"
   elif npcId == 7358 and int(st.get("cond"))==6 and st.getQuestItemsCount(ORDER_OF_CLAYTON_ID) and ((st.getQuestItemsCount(STAKATO_ICHOR_ID)+st.getQuestItemsCount(HONEY_DEW_ID)+st.getQuestItemsCount(BASILISK_PLASMA_ID))==3) :
      htmltext = "7358-03.htm"
      st.giveItems(SCROLL_OF_DARKELF_TRUST_ID,1)
      st.takeItems(ORDER_OF_CLAYTON_ID,1)
      st.takeItems(BASILISK_PLASMA_ID,1)
      st.takeItems(STAKATO_ICHOR_ID,1)
      st.takeItems(HONEY_DEW_ID,1)
      st.set("cond","7")
   elif npcId == 7358 and int(st.get("cond"))==7 :
      htmltext = "7358-04.htm"
   elif npcId == 7358 and int(st.get("cond"))==5 :
      htmltext = "7358-05.htm"
   elif npcId == 7464 and int(st.get("cond"))==5 and st.getQuestItemsCount(LETTER_OF_THIFIELL_ID) :
      htmltext = "7464-01.htm"
      st.giveItems(ORDER_OF_CLAYTON_ID,1)
      st.takeItems(LETTER_OF_THIFIELL_ID,1)
      st.set("cond","6")
   elif npcId == 7464 and int(st.get("cond"))==6 and st.getQuestItemsCount(ORDER_OF_CLAYTON_ID) and ((st.getQuestItemsCount(STAKATO_ICHOR_ID)+st.getQuestItemsCount(HONEY_DEW_ID)+st.getQuestItemsCount(BASILISK_PLASMA_ID))<3) :
      htmltext = "7464-02.htm"
   elif npcId == 7464 and int(st.get("cond"))==6 and st.getQuestItemsCount(ORDER_OF_CLAYTON_ID) and ((st.getQuestItemsCount(STAKATO_ICHOR_ID)+st.getQuestItemsCount(HONEY_DEW_ID)+st.getQuestItemsCount(BASILISK_PLASMA_ID))==3) :
      htmltext = "7464-03.htm"
   elif npcId == 7657 and int(st.get("cond"))==8 and st.getQuestItemsCount(LETTER_TO_SERESIN_ID) :
      htmltext = "7657-01.htm"
   elif npcId == 7657 and int(st.get("cond"))==9 :
      htmltext = "7657-04.htm"
   elif npcId == 7657 and int(st.get("cond"))==18 :
      htmltext = "7657-05.htm"
   elif npcId == 7565 and int(st.get("cond"))==9 and st.getQuestItemsCount(LETTER_TO_ORC_ID) :
      htmltext = "7565-01.htm"
   elif npcId == 7565 and int(st.get("cond"))==10 :
      htmltext = "7565-03.htm"
   elif npcId == 7565 and int(st.get("cond"))==13 :
      htmltext = "7565-04.htm"
      st.giveItems(SCROLL_OF_ORC_TRUST_ID,1)
      st.takeItems(LETTER_OF_MANAKIA_ID,1)
      st.set("cond","14")
   elif npcId == 7565 and int(st.get("cond"))==14 :
      htmltext = "7565-05.htm"
   elif npcId == 7515 and int(st.get("cond"))==1 and st.getQuestItemsCount(LETTER_TO_MANAKIA_ID) :
      htmltext = "7515-01.htm"
   elif npcId == 7515 and int(st.get("cond"))==11 :
      htmltext = "7515-03.htm"
   elif npcId == 7515 and int(st.get("cond"))==12 and st.getQuestItemsCount(PARASITE_OF_LOTA_ID)==10 :
      htmltext = "7515-04.htm"
      st.giveItems(LETTER_OF_MANAKIA_ID,1)
      st.takeItems(PARASITE_OF_LOTA_ID,st.getQuestItemsCount(PARASITE_OF_LOTA_ID))
      st.set("cond","13")
   elif npcId == 7515 and int(st.get("cond"))==13 :
      htmltext = "7515-05.htm"
   elif npcId == 7531 and int(st.get("cond"))==14 and st.getQuestItemsCount(LETTER_TO_DWARF_ID) :
      htmltext = "7531-01.htm"
   elif npcId == 7531 and int(st.get("cond"))==15 :
      htmltext = "7531-03.htm"
   elif npcId == 7531 and int(st.get("cond"))==17 :
      htmltext = "7531-04.htm"
      st.giveItems(SCROLL_OF_DWARF_TRUST_ID,1)
      st.set("cond","18")
   elif npcId == 7531 and int(st.get("cond"))==18 :
      htmltext = "7531-05.htm"
   elif npcId == 7621 and int(st.get("cond"))==15 and st.getQuestItemsCount(LETTER_TO_NICHOLA_ID) :
      htmltext = "7621-01.htm"
   elif npcId == 7621 and int(st.get("cond"))==16 and st.getQuestItemsCount(HEART_OF_PORTA_ID)<10 :
      htmltext = "7621-03.htm"
   elif npcId == 7621 and int(st.get("cond"))==16 and st.getQuestItemsCount(HEART_OF_PORTA_ID)==10 :
      htmltext = "7621-04.htm"
      st.takeItems(HEART_OF_PORTA_ID,st.getQuestItemsCount(HEART_OF_PORTA_ID))
      st.takeItems(ORDER_OF_NICHOLA_ID,1)
      st.set("cond","17")
   elif npcId == 7621 and int(st.get("cond"))==17 :
      htmltext = "7621-05.htm"
   elif npcId == 7031 and int(st.get("cond"))==19 and st.getQuestItemsCount(RECOMMENDATION_OF_HOLLIN_ID) :
      if st.getGameTicks() != int(st.get("id")) :
        st.set("id",str(st.getGameTicks()))
        st.addExpAndSp(32600,4000)
      htmltext = "7031-01.htm"
      st.takeItems(RECOMMENDATION_OF_HOLLIN_ID,1)
      st.giveItems(MARK_OF_TRUST_ID,1)
      st.set("cond","0")
      st.setState(COMPLETED)
      st.playSound("ItemSound.quest_finish")
      st.set("onlyone","1")
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 36 :
      if int(st.get("cond")) and int(st.get("cond")) == 2 :
        st.set("cond",str(int(st.get("id"))+1))
        if st.getRandom(100)<(int(st.get("id"))*33) :
          st.spawnNpc(5120)
          st.playSound("Itemsound.quest_before_battle")
          st.set("cond",str(0))
   elif npcId == 44 :
      if int(st.get("cond")) and int(st.get("cond")) == 2 :
        st.set("cond",str(int(st.get("id"))+1))
        if st.getRandom(100)<(int(st.get("id"))*33) :
          st.spawnNpc(5120)
          st.playSound("Itemsound.quest_before_battle")
          st.set("cond",str(0))
   elif npcId == 13 :
      if int(st.get("cond")) and int(st.get("cond")) == 2 :
        st.set("cond",str(int(st.get("id"))+1))
        if st.getRandom(100)<(int(st.get("id"))*33) :
          st.spawnNpc(5121)
          st.playSound("Itemsound.quest_before_battle")
          st.set("cond",str(0))
   elif npcId == 19 :
      if int(st.get("cond")) and int(st.get("cond")) == 2 :
        st.set("cond",str(int(st.get("id"))+1))
        if st.getRandom(100)<(int(st.get("id"))*33) :
          st.spawnNpc(5121)
          st.playSound("Itemsound.quest_before_battle")
          st.set("cond",str(0))
   elif npcId == 5120 :
      if int(st.get("cond")) and int(st.get("cond")) == 2 and st.getQuestItemsCount(BREATH_OF_WINDS_ID) == 0 :
        if st.getQuestItemsCount(SEED_OF_VERDURE_ID) :
          st.giveItems(BREATH_OF_WINDS_ID,1)
          st.set("cond","3")
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(BREATH_OF_WINDS_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 5121 :
      if int(st.get("cond")) and int(st.get("cond")) == 2 and st.getQuestItemsCount(SEED_OF_VERDURE_ID) == 0 :
        if st.getQuestItemsCount(BREATH_OF_WINDS_ID) :
          st.giveItems(SEED_OF_VERDURE_ID,1)
          st.set("cond","3")
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(SEED_OF_VERDURE_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 550 :
      if int(st.get("cond")) and int(st.get("cond")) == 6 and st.getQuestItemsCount(BLOOD_OF_GUARDIAN_BASILISK_ID) < 10 and st.getQuestItemsCount(ORDER_OF_CLAYTON_ID) and st.getQuestItemsCount(BASILISK_PLASMA_ID) == 0 :
        if st.getQuestItemsCount(BLOOD_OF_GUARDIAN_BASILISK_ID) == 9 :
          st.giveItems(BASILISK_PLASMA_ID,1)
          st.takeItems(BLOOD_OF_GUARDIAN_BASILISK_ID,st.getQuestItemsCount(BLOOD_OF_GUARDIAN_BASILISK_ID))
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(BLOOD_OF_GUARDIAN_BASILISK_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 157 :
      if int(st.get("cond")) and int(st.get("cond")) == 6 and st.getQuestItemsCount(STAKATOS_FLUIDS_ID) < 10 and st.getQuestItemsCount(ORDER_OF_CLAYTON_ID) and st.getQuestItemsCount(STAKATO_ICHOR_ID) == 0 :
        if st.getQuestItemsCount(STAKATOS_FLUIDS_ID) == 9 :
          st.giveItems(STAKATO_ICHOR_ID,1)
          st.takeItems(STAKATOS_FLUIDS_ID,st.getQuestItemsCount(STAKATOS_FLUIDS_ID))
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(STAKATOS_FLUIDS_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 230 :
      if int(st.get("cond")) and int(st.get("cond")) == 6 and st.getQuestItemsCount(STAKATOS_FLUIDS_ID) < 10 and st.getQuestItemsCount(ORDER_OF_CLAYTON_ID) and st.getQuestItemsCount(STAKATO_ICHOR_ID) == 0 :
        if st.getQuestItemsCount(STAKATOS_FLUIDS_ID) == 9 :
          st.giveItems(STAKATO_ICHOR_ID,1)
          st.takeItems(STAKATOS_FLUIDS_ID,st.getQuestItemsCount(STAKATOS_FLUIDS_ID))
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(STAKATOS_FLUIDS_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 232 :
      if int(st.get("cond")) and int(st.get("cond")) == 6 and st.getQuestItemsCount(STAKATOS_FLUIDS_ID) < 10 and st.getQuestItemsCount(ORDER_OF_CLAYTON_ID) and st.getQuestItemsCount(STAKATO_ICHOR_ID) == 0 :
        if st.getQuestItemsCount(STAKATOS_FLUIDS_ID) == 9 :
          st.giveItems(STAKATO_ICHOR_ID,1)
          st.takeItems(STAKATOS_FLUIDS_ID,st.getQuestItemsCount(STAKATOS_FLUIDS_ID))
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(STAKATOS_FLUIDS_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 234 :
      if int(st.get("cond")) and int(st.get("cond")) == 6 and st.getQuestItemsCount(STAKATOS_FLUIDS_ID) < 10 and st.getQuestItemsCount(ORDER_OF_CLAYTON_ID) and st.getQuestItemsCount(STAKATO_ICHOR_ID) == 0 :
        if st.getQuestItemsCount(STAKATOS_FLUIDS_ID) == 9 :
          st.giveItems(STAKATO_ICHOR_ID,1)
          st.takeItems(STAKATOS_FLUIDS_ID,st.getQuestItemsCount(STAKATOS_FLUIDS_ID))
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(STAKATOS_FLUIDS_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 82 :
      if int(st.get("cond")) and int(st.get("cond")) == 6 and st.getQuestItemsCount(GIANT_APHID_ID) < 10 and st.getQuestItemsCount(ORDER_OF_CLAYTON_ID) and st.getQuestItemsCount(HONEY_DEW_ID) == 0 :
        if st.getQuestItemsCount(GIANT_APHID_ID) == 9 :
          st.giveItems(HONEY_DEW_ID,1)
          st.takeItems(GIANT_APHID_ID,st.getQuestItemsCount(GIANT_APHID_ID))
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(GIANT_APHID_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 86 :
      if int(st.get("cond")) and int(st.get("cond")) == 6 and st.getQuestItemsCount(GIANT_APHID_ID) < 10 and st.getQuestItemsCount(ORDER_OF_CLAYTON_ID) and st.getQuestItemsCount(HONEY_DEW_ID) == 0 :
        if st.getQuestItemsCount(GIANT_APHID_ID) == 9 :
          st.giveItems(HONEY_DEW_ID,1)
          st.takeItems(GIANT_APHID_ID,st.getQuestItemsCount(GIANT_APHID_ID))
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(GIANT_APHID_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 87 :
      if int(st.get("cond")) and int(st.get("cond")) == 6 and st.getQuestItemsCount(GIANT_APHID_ID) < 10 and st.getQuestItemsCount(ORDER_OF_CLAYTON_ID) and st.getQuestItemsCount(HONEY_DEW_ID) == 0 :
        if st.getQuestItemsCount(GIANT_APHID_ID) == 9 :
          st.giveItems(HONEY_DEW_ID,1)
          st.takeItems(GIANT_APHID_ID,st.getQuestItemsCount(GIANT_APHID_ID))
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(GIANT_APHID_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 84 :
      if int(st.get("cond")) and int(st.get("cond")) == 6 and st.getQuestItemsCount(GIANT_APHID_ID) < 10 and st.getQuestItemsCount(ORDER_OF_CLAYTON_ID) and st.getQuestItemsCount(HONEY_DEW_ID) == 0 :
        if st.getQuestItemsCount(GIANT_APHID_ID) == 9 :
          st.giveItems(HONEY_DEW_ID,1)
          st.takeItems(GIANT_APHID_ID,st.getQuestItemsCount(GIANT_APHID_ID))
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(GIANT_APHID_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 88 :
      if int(st.get("cond")) and int(st.get("cond")) == 6 and st.getQuestItemsCount(GIANT_APHID_ID) < 10 and st.getQuestItemsCount(ORDER_OF_CLAYTON_ID) and st.getQuestItemsCount(HONEY_DEW_ID) == 0 :
        if st.getQuestItemsCount(GIANT_APHID_ID) == 9 :
          st.giveItems(HONEY_DEW_ID,1)
          st.takeItems(GIANT_APHID_ID,st.getQuestItemsCount(GIANT_APHID_ID))
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(GIANT_APHID_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 553 :
      if int(st.get("cond")) and int(st.get("cond")) == 11 and st.getQuestItemsCount(PARASITE_OF_LOTA_ID) < 10 :
        if st.getRandom(2) == 1 :
          if st.getQuestItemsCount(PARASITE_OF_LOTA_ID) == 9 :
            st.giveItems(PARASITE_OF_LOTA_ID,1)
            st.set("cond","12")
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(PARASITE_OF_LOTA_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 213 :
      if int(st.get("cond")) and int(st.get("cond")) == 16 and st.getQuestItemsCount(HEART_OF_PORTA_ID) < 10 :
        if st.getQuestItemsCount(HEART_OF_PORTA_ID) == 9 :
          st.giveItems(HEART_OF_PORTA_ID,1)
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(HEART_OF_PORTA_ID,1)
          st.playSound("Itemsound.quest_itemget")
   return

QUEST       = Quest(217,"217_TestimonyOfTrust","Testimony Of Trust")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7191)

STARTING.addTalkId(7191)

STARTED.addTalkId(7031)
STARTED.addTalkId(7154)
STARTED.addTalkId(7191)
STARTED.addTalkId(7358)
STARTED.addTalkId(7464)
STARTED.addTalkId(7515)
STARTED.addTalkId(7531)
STARTED.addTalkId(7565)
STARTED.addTalkId(7621)
STARTED.addTalkId(7657)

STARTED.addKillId(13)
STARTED.addKillId(157)
STARTED.addKillId(19)
STARTED.addKillId(213)
STARTED.addKillId(230)
STARTED.addKillId(232)
STARTED.addKillId(234)
STARTED.addKillId(36)
STARTED.addKillId(44)
STARTED.addKillId(5120)
STARTED.addKillId(5121)
STARTED.addKillId(550)
STARTED.addKillId(553)
STARTED.addKillId(82)
STARTED.addKillId(84)
STARTED.addKillId(86)
STARTED.addKillId(87)
STARTED.addKillId(88)

STARTED.addQuestDrop(7358,SCROLL_OF_DARKELF_TRUST_ID,1)
STARTED.addQuestDrop(7154,SCROLL_OF_ELF_TRUST_ID,1)
STARTED.addQuestDrop(7531,SCROLL_OF_DWARF_TRUST_ID,1)
STARTED.addQuestDrop(7565,SCROLL_OF_ORC_TRUST_ID,1)
STARTED.addQuestDrop(5120,BREATH_OF_WINDS_ID,1)
STARTED.addQuestDrop(5121,SEED_OF_VERDURE_ID,1)
STARTED.addQuestDrop(7154,ORDER_OF_OZZY_ID,1)
STARTED.addQuestDrop(7191,LETTER_TO_ELF_ID,1)
STARTED.addQuestDrop(7464,ORDER_OF_CLAYTON_ID,1)
STARTED.addQuestDrop(550,BASILISK_PLASMA_ID,1)
STARTED.addQuestDrop(157,STAKATO_ICHOR_ID,1)
STARTED.addQuestDrop(230,STAKATO_ICHOR_ID,1)
STARTED.addQuestDrop(232,STAKATO_ICHOR_ID,1)
STARTED.addQuestDrop(234,STAKATO_ICHOR_ID,1)
STARTED.addQuestDrop(82,HONEY_DEW_ID,1)
STARTED.addQuestDrop(86,HONEY_DEW_ID,1)
STARTED.addQuestDrop(87,HONEY_DEW_ID,1)
STARTED.addQuestDrop(84,HONEY_DEW_ID,1)
STARTED.addQuestDrop(88,HONEY_DEW_ID,1)
STARTED.addQuestDrop(7191,LETTER_TO_DARKELF_ID,1)
STARTED.addQuestDrop(7358,LETTER_OF_THIFIELL_ID,1)
STARTED.addQuestDrop(7191,LETTER_TO_SERESIN_ID,1)
STARTED.addQuestDrop(7657,LETTER_TO_ORC_ID,1)
STARTED.addQuestDrop(7515,LETTER_OF_MANAKIA_ID,1)
STARTED.addQuestDrop(7565,LETTER_TO_MANAKIA_ID,1)
STARTED.addQuestDrop(553,PARASITE_OF_LOTA_ID,1)
STARTED.addQuestDrop(7657,LETTER_TO_DWARF_ID,1)
STARTED.addQuestDrop(7531,LETTER_TO_NICHOLA_ID,1)
STARTED.addQuestDrop(213,HEART_OF_PORTA_ID,1)
STARTED.addQuestDrop(7621,ORDER_OF_NICHOLA_ID,1)
STARTED.addQuestDrop(7191,RECOMMENDATION_OF_HOLLIN_ID,1)
STARTED.addQuestDrop(550,BLOOD_OF_GUARDIAN_BASILISK_ID,1)
STARTED.addQuestDrop(157,STAKATOS_FLUIDS_ID,1)
STARTED.addQuestDrop(230,STAKATOS_FLUIDS_ID,1)
STARTED.addQuestDrop(232,STAKATOS_FLUIDS_ID,1)
STARTED.addQuestDrop(234,STAKATOS_FLUIDS_ID,1)
STARTED.addQuestDrop(82,GIANT_APHID_ID,1)
STARTED.addQuestDrop(86,GIANT_APHID_ID,1)
STARTED.addQuestDrop(87,GIANT_APHID_ID,1)
STARTED.addQuestDrop(84,GIANT_APHID_ID,1)
STARTED.addQuestDrop(88,GIANT_APHID_ID,1)
