# Maked by Mr. Have fun! Version 0.2
print "importing quests: 214: Trial Of Scholar"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_SCHOLAR_ID = 2674
MIRIENS_SIGIL1_ID = 2675
MIRIENS_SIGIL2_ID = 2676
MIRIENS_SIGIL3_ID = 2677
MIRIENS_INSTRUCTION_ID = 2678
MARIAS_LETTER1_ID = 2679
SYMBOL_OF_JUREK_ID = 2698
SYMBOL_OF_SYLVAIN_ID = 2693
SYMBOL_OF_CRONOS_ID = 2720
HIGHPRIESTS_SIGIL_ID = 2689
SYLVAINS_LETTER_ID = 2692
CRYSTAL_OF_PURITY1_ID = 2688
LUCILLAS_HANDBAG_ID = 2682
CRETAS_LETTER1_ID = 2683
BROWN_SCROLL_SCRAP_ID = 2687
CRETAS_PAINTING3_ID = 2686
MARIAS_LETTER2_ID = 2680
LUKAS_LETTER_ID = 2681
CRETAS_PAINTING2_ID = 2685
CRETAS_PAINTING1_ID = 2684
CRYSTAL_OF_PURITY2_ID = 2714
VALKONS_REQUEST_ID = 2710
JUREKS_LIST_ID = 2694
GMAGISTERS_SIGIL_ID = 2690
MEYEDESTROYERS_SKIN_ID = 2695
SHAMANS_NECKLACE_ID = 2696
SHACKLES_SCALP_ID = 2697
CRETAS_LETTER2_ID = 2701
DIETERS_KEY_ID = 2700
CRONOS_SIGIL_ID = 2691
CRONOS_LETTER_ID = 2699
SCRIPTURE_CHAPTER_1_ID = 2706
SCRIPTURE_CHAPTER_2_ID = 2707
SCRIPTURE_CHAPTER_3_ID = 2708
SCRIPTURE_CHAPTER_4_ID = 2709
TRIFFS_RING_ID = 2705
DIETERS_DIARY_ID = 2703
DIETERS_LETTER_ID = 2702
RAUTS_LETTER_ENVELOPE_ID = 2704
STRONG_LIQUOR_ID = 2713
POITANS_NOTES_ID = 2711
CASIANS_LIST_ID = 2715
GHOULS_SKIN_ID = 2716
MEDUSAS_BLOOD_ID = 2717
FETTEREDSOULS_ICHOR_ID = 2718
ENCHT_GARGOYLES_NAIL_ID = 2719

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        htmltext = "7461-04.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.giveItems(MIRIENS_SIGIL1_ID,1)
    elif event == "7461_1" :
          if st.getPlayer().getLevel()<36 :
            htmltext = "7461-09.htm"
            st.takeItems(SYMBOL_OF_JUREK_ID,1)
            st.giveItems(MIRIENS_INSTRUCTION_ID,1)
            st.takeItems(MIRIENS_SIGIL2_ID,1)
          else:
            htmltext = "7461-10.htm"
            st.takeItems(SYMBOL_OF_JUREK_ID,1)
            st.giveItems(MIRIENS_SIGIL3_ID,1)
            st.takeItems(MIRIENS_SIGIL2_ID,1)
    elif event == "7070_1" :
          htmltext = "7070-02.htm"
          st.giveItems(HIGHPRIESTS_SIGIL_ID,1)
          st.giveItems(SYLVAINS_LETTER_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "7608_1" :
          htmltext = "7608-02.htm"
          st.giveItems(MARIAS_LETTER1_ID,1)
          st.takeItems(SYLVAINS_LETTER_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "7608_2" :
          htmltext = "7608-07.htm"
    elif event == "7608_3" :
          htmltext = "7608-08.htm"
          st.giveItems(LUCILLAS_HANDBAG_ID,1)
          st.takeItems(CRETAS_LETTER1_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "7608_4" :
          htmltext = "7608-14.htm"
          st.takeItems(BROWN_SCROLL_SCRAP_ID,st.getQuestItemsCount(BROWN_SCROLL_SCRAP_ID))
          st.giveItems(CRYSTAL_OF_PURITY1_ID,1)
          st.takeItems(CRETAS_PAINTING3_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "7115_1" :
          htmltext = "7115-02.htm"
    elif event == "7115_2" :
          htmltext = "7115-03.htm"
          st.giveItems(JUREKS_LIST_ID,1)
          st.giveItems(GMAGISTERS_SIGIL_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "7071_1" :
          htmltext = "7071-04.htm"
          st.giveItems(CRETAS_PAINTING3_ID,1)
          st.takeItems(CRETAS_PAINTING2_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "7609_1" :
          htmltext = "7609-02.htm"
    elif event == "7609_2" :
          htmltext = "7609-03.htm"
    elif event == "7609_3" :
          htmltext = "7609-04.htm"
    elif event == "7609_4" :
          htmltext = "7609-05.htm"
          st.giveItems(CRETAS_LETTER1_ID,1)
          st.takeItems(MARIAS_LETTER2_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "7609_5" :
          htmltext = "7609-08.htm"
    elif event == "7609_6" :
          htmltext = "7609-09.htm"
          st.giveItems(CRETAS_PAINTING1_ID,1)
          st.takeItems(LUCILLAS_HANDBAG_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "7609_7" :
          htmltext = "7609-13.htm"
    elif event == "7609_8" :
          htmltext = "7609-14.htm"
          st.giveItems(CRETAS_LETTER2_ID,1)
          st.takeItems(DIETERS_KEY_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "7610_1" :
          htmltext = "7610-02.htm"
    elif event == "7610_2" :
          htmltext = "7610-03.htm"
    elif event == "7610_3" :
          htmltext = "7610-04.htm"
    elif event == "7610_4" :
          htmltext = "7610-05.htm"
    elif event == "7610_5" :
          htmltext = "7610-06.htm"
    elif event == "7610_6" :
          htmltext = "7610-07.htm"
    elif event == "7610_7" :
          htmltext = "7610-08.htm"
    elif event == "7610_8" :
          htmltext = "7610-09.htm"
    elif event == "7610_9" :
          htmltext = "7610-10.htm"
          st.giveItems(CRONOS_SIGIL_ID,1)
          st.giveItems(CRONOS_LETTER_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "7610_10" :
          htmltext = "7610-13.htm"
    elif event == "7610_11" :
          htmltext = "7610-14.htm"
          st.takeItems(SCRIPTURE_CHAPTER_1_ID,1)
          st.takeItems(SCRIPTURE_CHAPTER_2_ID,1)
          st.takeItems(SCRIPTURE_CHAPTER_3_ID,1)
          st.takeItems(SCRIPTURE_CHAPTER_4_ID,1)
          st.takeItems(CRONOS_SIGIL_ID,1)
          st.takeItems(TRIFFS_RING_ID,1)
          st.giveItems(SYMBOL_OF_CRONOS_ID,1)
          st.takeItems(DIETERS_DIARY_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "7111_1" :
          htmltext = "7111-02.htm"
    elif event == "7111_2" :
          htmltext = "7111-03.htm"
    elif event == "7111_3" :
          htmltext = "7111-04.htm"
    elif event == "7111_4" :
          htmltext = "7111-05.htm"
          st.giveItems(DIETERS_KEY_ID,1)
          st.takeItems(CRONOS_LETTER_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "7111_5" :
          htmltext = "7111-08.htm"
    elif event == "7111_6" :
          htmltext = "7111-09.htm"
          st.giveItems(DIETERS_LETTER_ID,1)
          st.takeItems(CRETAS_LETTER2_ID,1)
          st.giveItems(DIETERS_DIARY_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "7230_1" :
          htmltext = "7230-02.htm"
          st.giveItems(RAUTS_LETTER_ENVELOPE_ID,1)
          st.takeItems(DIETERS_LETTER_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "7316_1" :
          htmltext = "7316-02.htm"
          st.giveItems(SCRIPTURE_CHAPTER_1_ID,1)
          st.takeItems(RAUTS_LETTER_ENVELOPE_ID,1)
          st.giveItems(STRONG_LIQUOR_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "7611_1" :
          htmltext = "7611-02.htm"
    elif event == "7611_2" :
          htmltext = "7611-03.htm"
    elif event == "7611_3" :
          htmltext = "7611-04.htm"
          st.giveItems(TRIFFS_RING_ID,1)
          st.takeItems(STRONG_LIQUOR_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "7103_1" :
          htmltext = "7103-02.htm"
    elif event == "7103_2" :
          htmltext = "7103-03.htm"
    elif event == "7103_3" :
          htmltext = "7103-04.htm"
          st.giveItems(VALKONS_REQUEST_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "7612_1" :
          htmltext = "7612-03.htm"
    elif event == "7612_2" :
          htmltext = "7612-04.htm"
          st.giveItems(CASIANS_LIST_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "7612_3" :
          htmltext = "7612-07.htm"
          st.giveItems(SCRIPTURE_CHAPTER_4_ID,1)
          st.takeItems(CASIANS_LIST_ID,1)
          st.takeItems(GHOULS_SKIN_ID,st.getQuestItemsCount(GHOULS_SKIN_ID))
          st.takeItems(MEDUSAS_BLOOD_ID,st.getQuestItemsCount(MEDUSAS_BLOOD_ID))
          st.takeItems(FETTEREDSOULS_ICHOR_ID,st.getQuestItemsCount(FETTEREDSOULS_ICHOR_ID))
          st.takeItems(ENCHT_GARGOYLES_NAIL_ID,st.getQuestItemsCount(ENCHT_GARGOYLES_NAIL_ID))
          st.takeItems(POITANS_NOTES_ID,1)
          st.playSound("ItemSound.quest_middle")
    return htmltext


 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7461 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
     if st.getPlayer().getClassId().getId() == 0x0b or st.getPlayer().getClassId().getId() == 0x1a or st.getPlayer().getClassId().getId() == 0x27 :
       if st.getPlayer().getLevel() >= 35 :
         htmltext = "7461-03.htm"
       else:
         htmltext = "7461-02.htm"
         st.exitQuest(1)
     else:
       htmltext = "7461-01.htm"
       st.exitQuest(1)
   elif npcId == 7461 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7461 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID)==1 and st.getQuestItemsCount(SYMBOL_OF_SYLVAIN_ID)==0 :
        htmltext = "7461-05.htm"
   elif npcId == 7461 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID)==1 and st.getQuestItemsCount(SYMBOL_OF_SYLVAIN_ID)==1 :
        htmltext = "7461-06.htm"
        st.takeItems(SYMBOL_OF_SYLVAIN_ID,1)
        st.giveItems(MIRIENS_SIGIL2_ID,1)
        st.takeItems(MIRIENS_SIGIL1_ID,1)
        st.playSound("ItemSound.quest_middle")
   elif npcId == 7461 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL2_ID)==1 and st.getQuestItemsCount(SYMBOL_OF_JUREK_ID)==0 :
        htmltext = "7461-07.htm"
   elif npcId == 7461 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL2_ID)==1 and st.getQuestItemsCount(SYMBOL_OF_JUREK_ID)==1 :
        htmltext = "7461-08.htm"
   elif npcId == 7461 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_INSTRUCTION_ID)==1 :
        if st.getPlayer().getLevel()<36 :
          htmltext = "7461-11.htm"
        else:
          htmltext = "7461-12.htm"
          st.giveItems(MIRIENS_SIGIL3_ID,1)
          st.takeItems(MIRIENS_INSTRUCTION_ID,1)
          st.playSound("ItemSound.quest_middle")
   elif npcId == 7461 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID)==1 :
        if st.getQuestItemsCount(SYMBOL_OF_CRONOS_ID) == 0 :
          htmltext = "7461-13.htm"
        else:
            st.addExpAndSp(80265,30000)
            st.giveItems(7562,8)
            htmltext = "7461-14.htm"
            st.set("cond","0")
            st.set("onlyone","1")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
            st.takeItems(SYMBOL_OF_CRONOS_ID,1)
            st.giveItems(MARK_OF_SCHOLAR_ID,1)
            st.takeItems(MIRIENS_SIGIL3_ID,1)
   elif npcId == 7070 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID)==1 and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) == 0 and st.getQuestItemsCount(SYMBOL_OF_SYLVAIN_ID) == 0 :
        htmltext = "7070-01.htm"
   elif npcId == 7070 and int(st.get("cond"))==1 and st.getQuestItemsCount(CRYSTAL_OF_PURITY1_ID)==0 and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) :
        htmltext = "7070-03.htm"
   elif npcId == 7070 and int(st.get("cond"))==1 and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(CRYSTAL_OF_PURITY1_ID) :
        htmltext = "7070-04.htm"
        st.giveItems(SYMBOL_OF_SYLVAIN_ID,1)
        st.takeItems(HIGHPRIESTS_SIGIL_ID,1)
        st.takeItems(CRYSTAL_OF_PURITY1_ID,1)
        st.playSound("ItemSound.quest_middle")
   elif npcId == 7070 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(SYMBOL_OF_SYLVAIN_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID)==0 :
        htmltext = "7070-05.htm"
   elif npcId == 7070 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL2_ID) or st.getQuestItemsCount(MIRIENS_SIGIL3_ID)) :
        htmltext = "7070-06.htm"
   elif npcId == 7608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(SYLVAINS_LETTER_ID)) :
        htmltext = "7608-01.htm"
   elif npcId == 7608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(MARIAS_LETTER1_ID)) :
        htmltext = "7608-03.htm"
   elif npcId == 7608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(LUKAS_LETTER_ID)) :
        htmltext = "7608-04.htm"
        st.giveItems(MARIAS_LETTER2_ID,1)
        st.takeItems(LUKAS_LETTER_ID,1)
        st.playSound("ItemSound.quest_middle")
   elif npcId == 7608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(MARIAS_LETTER2_ID)) :
        htmltext = "7608-05.htm"
   elif npcId == 7608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(CRETAS_LETTER1_ID)) :
        htmltext = "7608-06.htm"
   elif npcId == 7608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(LUCILLAS_HANDBAG_ID)) :
        htmltext = "7608-09.htm"
   elif npcId == 7608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(CRETAS_PAINTING1_ID)) :
        htmltext = "7608-10.htm"
        st.giveItems(CRETAS_PAINTING2_ID,1)
        st.takeItems(CRETAS_PAINTING1_ID,1)
        st.playSound("ItemSound.quest_middle")
   elif npcId == 7608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(CRETAS_PAINTING2_ID)) :
        htmltext = "7608-11.htm"
   elif npcId == 7608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(CRETAS_PAINTING3_ID)) and st.getQuestItemsCount(BROWN_SCROLL_SCRAP_ID)<5 :
        htmltext = "7608-12.htm"
   elif npcId == 7608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(CRETAS_PAINTING3_ID)) and st.getQuestItemsCount(BROWN_SCROLL_SCRAP_ID)>=5 :
        htmltext = "7608-13.htm"
   elif npcId == 7608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(CRYSTAL_OF_PURITY1_ID)) :
        htmltext = "7608-15.htm"
   elif npcId == 7608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(SYMBOL_OF_SYLVAIN_ID) or st.getQuestItemsCount(MIRIENS_SIGIL2_ID)) :
        htmltext = "7608-16.htm"
   elif npcId == 7608 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID)==1 and st.getQuestItemsCount(VALKONS_REQUEST_ID)==0 :
        htmltext = "7608-17.htm"
   elif npcId == 7608 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID)==1 and st.getQuestItemsCount(VALKONS_REQUEST_ID)==1 :
        htmltext = "7608-18.htm"
        st.giveItems(CRYSTAL_OF_PURITY2_ID,1)
        st.takeItems(VALKONS_REQUEST_ID,1)
        st.playSound("ItemSound.quest_middle")
   elif npcId == 7115 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL2_ID)==1 and st.getQuestItemsCount(GMAGISTERS_SIGIL_ID)==0 and st.getQuestItemsCount(SYMBOL_OF_JUREK_ID)==0 :
        htmltext = "7115-01.htm"
   elif npcId == 7115 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL2_ID)==1 and st.getQuestItemsCount(JUREKS_LIST_ID)==1 :
        if st.getQuestItemsCount(MEYEDESTROYERS_SKIN_ID)+st.getQuestItemsCount(SHAMANS_NECKLACE_ID)+st.getQuestItemsCount(SHACKLES_SCALP_ID)<12 :
          htmltext = "7115-04.htm"
        else:
          htmltext = "7115-05.htm"
          st.takeItems(JUREKS_LIST_ID,1)
          st.takeItems(MEYEDESTROYERS_SKIN_ID,st.getQuestItemsCount(MEYEDESTROYERS_SKIN_ID))
          st.takeItems(SHAMANS_NECKLACE_ID,st.getQuestItemsCount(SHAMANS_NECKLACE_ID))
          st.takeItems(SHACKLES_SCALP_ID,st.getQuestItemsCount(SHACKLES_SCALP_ID))
          st.giveItems(SYMBOL_OF_JUREK_ID,1)
          st.takeItems(GMAGISTERS_SIGIL_ID,1)
          st.playSound("ItemSound.quest_middle")
   elif npcId == 7115 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL2_ID) and st.getQuestItemsCount(SYMBOL_OF_JUREK_ID)) and st.getQuestItemsCount(GMAGISTERS_SIGIL_ID)==0 :
        htmltext = "7115-06.htm"
   elif npcId == 7115 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) or st.getQuestItemsCount(MIRIENS_SIGIL3_ID)) :
        htmltext = "7115-07.htm"
   elif npcId == 7071 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(MARIAS_LETTER1_ID) :
        htmltext = "7071-01.htm"
        st.giveItems(LUKAS_LETTER_ID,1)
        st.takeItems(MARIAS_LETTER1_ID,1)
        st.playSound("ItemSound.quest_middle")
   elif npcId == 7071 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and (st.getQuestItemsCount(MARIAS_LETTER2_ID) or st.getQuestItemsCount(CRETAS_LETTER1_ID) or st.getQuestItemsCount(LUCILLAS_HANDBAG_ID) or st.getQuestItemsCount(CRETAS_PAINTING1_ID) or st.getQuestItemsCount(LUKAS_LETTER_ID)) :
        htmltext = "7071-02.htm"
   elif npcId == 7071 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(CRETAS_PAINTING2_ID) :
        htmltext = "7071-03.htm"
   elif npcId == 7071 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(CRETAS_PAINTING3_ID) :
        if st.getQuestItemsCount(BROWN_SCROLL_SCRAP_ID)<5 :
          htmltext = "7071-05.htm"
        else:
          htmltext = "7071-06.htm"
   elif npcId == 7071 and int(st.get("cond"))==1 and (st.getQuestItemsCount(SYMBOL_OF_SYLVAIN_ID) or st.getQuestItemsCount(MIRIENS_SIGIL2_ID) or st.getQuestItemsCount(MIRIENS_SIGIL3_ID) or st.getQuestItemsCount(CRYSTAL_OF_PURITY1_ID)) :
        htmltext = "7071-07.htm"
   elif npcId == 7609 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(MARIAS_LETTER2_ID) :
        htmltext = "7609-01.htm"
   elif npcId == 7609 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(CRETAS_LETTER1_ID) :
        htmltext = "7609-06.htm"
   elif npcId == 7609 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(LUCILLAS_HANDBAG_ID) :
        htmltext = "7609-07.htm"
   elif npcId == 7609 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and (st.getQuestItemsCount(CRETAS_PAINTING1_ID) or st.getQuestItemsCount(CRETAS_PAINTING2_ID) or st.getQuestItemsCount(CRETAS_PAINTING3_ID)) :
        htmltext = "7609-10.htm"
   elif npcId == 7609 and int(st.get("cond"))==1 and (st.getQuestItemsCount(CRYSTAL_OF_PURITY1_ID) or st.getQuestItemsCount(SYMBOL_OF_SYLVAIN_ID) or st.getQuestItemsCount(MIRIENS_SIGIL2_ID)) :
        htmltext = "7609-11.htm"
   elif npcId == 7609 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID) and st.getQuestItemsCount(DIETERS_KEY_ID) :
        htmltext = "7609-12.htm"
   elif npcId == 7609 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID)==1 and st.getQuestItemsCount(DIETERS_KEY_ID)==0 :
        htmltext = "7609-15.htm"
   elif npcId == 7610 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID)==1 and st.getQuestItemsCount(CRONOS_SIGIL_ID)==0 and st.getQuestItemsCount(SYMBOL_OF_CRONOS_ID)==0 :
        htmltext = "7610-01.htm"
   elif npcId == 7610 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL3_ID) or st.getQuestItemsCount(CRONOS_SIGIL_ID)) :
        if st.getQuestItemsCount(SCRIPTURE_CHAPTER_1_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_2_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_3_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_4_ID) :
          htmltext = "7610-12.htm"
        else:
          htmltext = "7610-11.htm"
   elif npcId == 7610 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID)==1 and st.getQuestItemsCount(SYMBOL_OF_CRONOS_ID)==1 and st.getQuestItemsCount(CRONOS_SIGIL_ID)==0 :
        htmltext = "7610-15.htm"
   elif npcId == 7111 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID) and st.getQuestItemsCount(CRONOS_SIGIL_ID) and st.getQuestItemsCount(CRONOS_LETTER_ID) :
        htmltext = "7111-01.htm"
   elif npcId == 7111 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID) and st.getQuestItemsCount(CRONOS_SIGIL_ID) and st.getQuestItemsCount(DIETERS_KEY_ID) :
          htmltext = "7111-06.htm"
   elif npcId == 7111 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID) and st.getQuestItemsCount(CRONOS_SIGIL_ID) and st.getQuestItemsCount(CRETAS_LETTER2_ID) :
          htmltext = "7111-07.htm"
   elif npcId == 7111 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID) and st.getQuestItemsCount(CRONOS_SIGIL_ID) and st.getQuestItemsCount(DIETERS_DIARY_ID) and st.getQuestItemsCount(DIETERS_LETTER_ID) :
          htmltext = "7111-10.htm"
   elif npcId == 7111 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID) and st.getQuestItemsCount(CRONOS_SIGIL_ID) and st.getQuestItemsCount(DIETERS_DIARY_ID) and st.getQuestItemsCount(RAUTS_LETTER_ENVELOPE_ID) :
          htmltext = "7111-11.htm"
   elif npcId == 7111 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID) and st.getQuestItemsCount(CRONOS_SIGIL_ID) and st.getQuestItemsCount(DIETERS_DIARY_ID) and st.getQuestItemsCount(DIETERS_LETTER_ID)==0 and st.getQuestItemsCount(RAUTS_LETTER_ENVELOPE_ID)==0 :
          if st.getQuestItemsCount(SCRIPTURE_CHAPTER_1_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_2_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_3_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_4_ID) :
            htmltext = "7111-13.htm"
          else:
            htmltext = "7111-12.htm"
   elif npcId == 7111 and int(st.get("cond"))==1 and st.getQuestItemsCount(SYMBOL_OF_CRONOS_ID)==1 :
          htmltext = "7111-15.htm"
   elif npcId == 7230 and int(st.get("cond"))==1 and st.getQuestItemsCount(DIETERS_DIARY_ID) and st.getQuestItemsCount(DIETERS_LETTER_ID) :
        htmltext = "7230-01.htm"
   elif npcId == 7230 and int(st.get("cond"))==1 and st.getQuestItemsCount(DIETERS_DIARY_ID) and st.getQuestItemsCount(RAUTS_LETTER_ENVELOPE_ID) :
        htmltext = "7230-03.htm"
   elif npcId == 7230 and int(st.get("cond"))==1 and st.getQuestItemsCount(DIETERS_DIARY_ID)==1 and (st.getQuestItemsCount(STRONG_LIQUOR_ID) or st.getQuestItemsCount(TRIFFS_RING_ID)) :
        htmltext = "7230-04.htm"
   elif npcId == 7316 and int(st.get("cond"))==1 and st.getQuestItemsCount(DIETERS_DIARY_ID) and st.getQuestItemsCount(RAUTS_LETTER_ENVELOPE_ID) :
        htmltext = "7316-01.htm"
   elif npcId == 7316 and int(st.get("cond"))==1 and st.getQuestItemsCount(DIETERS_DIARY_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_1_ID) and st.getQuestItemsCount(STRONG_LIQUOR_ID) :
        htmltext = "7316-04.htm"
   elif npcId == 7316 and int(st.get("cond"))==1 and st.getQuestItemsCount(DIETERS_DIARY_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_1_ID) and st.getQuestItemsCount(TRIFFS_RING_ID) :
        htmltext = "7316-05.htm"
   elif npcId == 7611 and int(st.get("cond"))==1 and st.getQuestItemsCount(DIETERS_DIARY_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_1_ID) and st.getQuestItemsCount(STRONG_LIQUOR_ID) :
        htmltext = "7611-01.htm"
   elif npcId == 7611 and int(st.get("cond"))==1 and (st.getQuestItemsCount(TRIFFS_RING_ID) or st.getQuestItemsCount(SYMBOL_OF_CRONOS_ID)) :
        htmltext = "7611-05.htm"
   elif npcId == 7103 and int(st.get("cond"))==1 and st.getQuestItemsCount(TRIFFS_RING_ID)==1 and st.getQuestItemsCount(VALKONS_REQUEST_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_PURITY2_ID)==0 and st.getQuestItemsCount(SCRIPTURE_CHAPTER_2_ID)==0 :
        htmltext = "7103-01.htm"
   elif npcId == 7103 and int(st.get("cond"))==1 and st.getQuestItemsCount(TRIFFS_RING_ID)==1 and st.getQuestItemsCount(VALKONS_REQUEST_ID)==1 and st.getQuestItemsCount(CRYSTAL_OF_PURITY2_ID)==0 and st.getQuestItemsCount(SCRIPTURE_CHAPTER_2_ID)==0 :
        htmltext = "7103-05.htm"
   elif npcId == 7103 and int(st.get("cond"))==1 and st.getQuestItemsCount(TRIFFS_RING_ID)==1 and st.getQuestItemsCount(VALKONS_REQUEST_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_PURITY2_ID)==1 and st.getQuestItemsCount(SCRIPTURE_CHAPTER_2_ID)==0 :
        htmltext = "7103-06.htm"
        st.giveItems(SCRIPTURE_CHAPTER_2_ID,1)
        st.takeItems(CRYSTAL_OF_PURITY2_ID,1)
        st.playSound("ItemSound.quest_middle")
   elif npcId == 7103 and int(st.get("cond"))==1 and st.getQuestItemsCount(TRIFFS_RING_ID)==1 and st.getQuestItemsCount(VALKONS_REQUEST_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_PURITY2_ID)==0 and st.getQuestItemsCount(SCRIPTURE_CHAPTER_2_ID)==1 :
        htmltext = "7103-07.htm"
   elif npcId == 7458 and int(st.get("cond"))==1 and st.getQuestItemsCount(TRIFFS_RING_ID)==1 and st.getQuestItemsCount(POITANS_NOTES_ID) == 0 and st.getQuestItemsCount(CASIANS_LIST_ID) == 0 and st.getQuestItemsCount(SCRIPTURE_CHAPTER_4_ID) == 0 :
        htmltext = "7458-01.htm"
        st.giveItems(POITANS_NOTES_ID,1)
        st.playSound("ItemSound.quest_middle")
   elif npcId == 7458 and int(st.get("cond"))==1 and st.getQuestItemsCount(TRIFFS_RING_ID) and st.getQuestItemsCount(POITANS_NOTES_ID) and st.getQuestItemsCount(CASIANS_LIST_ID) == 0 and st.getQuestItemsCount(SCRIPTURE_CHAPTER_4_ID) == 0 :
        htmltext = "7458-02.htm"
   elif npcId == 7458 and int(st.get("cond"))==1 and st.getQuestItemsCount(TRIFFS_RING_ID) and st.getQuestItemsCount(POITANS_NOTES_ID) and st.getQuestItemsCount(CASIANS_LIST_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_4_ID)==0 :
        htmltext = "7458-03.htm"
   elif npcId == 7458 and int(st.get("cond"))==1 and st.getQuestItemsCount(TRIFFS_RING_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_4_ID) and st.getQuestItemsCount(POITANS_NOTES_ID) == 0 and st.getQuestItemsCount(CASIANS_LIST_ID) == 0 :
        htmltext = "7458-04.htm"
   elif npcId == 7612 and int(st.get("cond"))==1 and st.getQuestItemsCount(TRIFFS_RING_ID) and st.getQuestItemsCount(POITANS_NOTES_ID) and st.getQuestItemsCount(CASIANS_LIST_ID)==0 :
        if st.getQuestItemsCount(SCRIPTURE_CHAPTER_1_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_2_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_3_ID) :
          htmltext = "7612-02.htm"
        else:
          htmltext = "7612-01.htm"
   elif npcId == 7612 and int(st.get("cond"))==1 and st.getQuestItemsCount(TRIFFS_RING_ID) and st.getQuestItemsCount(POITANS_NOTES_ID) and st.getQuestItemsCount(CASIANS_LIST_ID) :
        if st.getQuestItemsCount(GHOULS_SKIN_ID)+st.getQuestItemsCount(MEDUSAS_BLOOD_ID)+st.getQuestItemsCount(FETTEREDSOULS_ICHOR_ID)+st.getQuestItemsCount(ENCHT_GARGOYLES_NAIL_ID)<32 :
          htmltext = "7612-05.htm"
        else:
          htmltext = "7612-06.htm"
   elif npcId == 7612 and int(st.get("cond"))==1 and st.getQuestItemsCount(POITANS_NOTES_ID) == 0 and st.getQuestItemsCount(CASIANS_LIST_ID) == 0 and st.getQuestItemsCount(TRIFFS_RING_ID)==1 and st.getQuestItemsCount(SCRIPTURE_CHAPTER_1_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_2_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_3_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_4_ID) :
        htmltext = "7612-08.htm"
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 580 :
    if int(st.get("cond")) and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(CRETAS_PAINTING3_ID) and st.getQuestItemsCount(BROWN_SCROLL_SCRAP_ID)<5 :
      if st.getRandom(100) < 50 :
        st.giveItems(BROWN_SCROLL_SCRAP_ID,1)
        if st.getQuestItemsCount(BROWN_SCROLL_SCRAP_ID) < 5 :
          st.playSound("ItemSound.quest_itemget")
        else:
          st.playSound("ItemSound.quest_middle")
   elif npcId == 68 :
    if int(st.get("cond")) and st.getQuestItemsCount(MIRIENS_SIGIL2_ID) and st.getQuestItemsCount(GMAGISTERS_SIGIL_ID) and st.getQuestItemsCount(JUREKS_LIST_ID) and st.getQuestItemsCount(MEYEDESTROYERS_SKIN_ID)<5 :
      if st.getRandom(100) < 50 :
        st.giveItems(MEYEDESTROYERS_SKIN_ID,1)
        if st.getQuestItemsCount(MEYEDESTROYERS_SKIN_ID) < 5 :
          st.playSound("ItemSound.quest_itemget")
        else:
          st.playSound("ItemSound.quest_middle")
   elif npcId == 269 :
    if int(st.get("cond")) and st.getQuestItemsCount(MIRIENS_SIGIL2_ID) and st.getQuestItemsCount(GMAGISTERS_SIGIL_ID) and st.getQuestItemsCount(JUREKS_LIST_ID) and st.getQuestItemsCount(SHAMANS_NECKLACE_ID)<5 :
      if st.getRandom(100) < 50 :
        st.giveItems(SHAMANS_NECKLACE_ID,1)
        if st.getQuestItemsCount(SHAMANS_NECKLACE_ID) < 5 :
          st.playSound("ItemSound.quest_itemget")
        else:
          st.playSound("ItemSound.quest_middle")
   elif npcId == 235 :
    if int(st.get("cond")) and st.getQuestItemsCount(MIRIENS_SIGIL2_ID) and st.getQuestItemsCount(GMAGISTERS_SIGIL_ID) and st.getQuestItemsCount(JUREKS_LIST_ID) and st.getQuestItemsCount(SHACKLES_SCALP_ID)<2 :
      st.giveItems(SHACKLES_SCALP_ID,1)
      if st.getQuestItemsCount(SHACKLES_SCALP_ID) < 2 :
        st.playSound("ItemSound.quest_itemget")
      else:
        st.playSound("ItemSound.quest_middle")
   elif npcId == 554 :
    if int(st.get("cond")) and st.getQuestItemsCount(MIRIENS_SIGIL3_ID) and st.getQuestItemsCount(CRONOS_SIGIL_ID) and st.getQuestItemsCount(TRIFFS_RING_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_3_ID) == 0 :
      if st.getRandom(100) < 30 :
        st.giveItems(SCRIPTURE_CHAPTER_3_ID,1)
        st.playSound("ItemSound.quest_middle")
   elif npcId == 201 :
    if int(st.get("cond")) and st.getQuestItemsCount(TRIFFS_RING_ID) and st.getQuestItemsCount(POITANS_NOTES_ID) and st.getQuestItemsCount(CASIANS_LIST_ID) and st.getQuestItemsCount(GHOULS_SKIN_ID)<10 :
      st.giveItems(GHOULS_SKIN_ID,1)
      if st.getQuestItemsCount(GHOULS_SKIN_ID) < 10 :
        st.playSound("ItemSound.quest_itemget")
      else:
        st.playSound("ItemSound.quest_middle")
   elif npcId == 158 :
    if int(st.get("cond")) and st.getQuestItemsCount(TRIFFS_RING_ID) and st.getQuestItemsCount(POITANS_NOTES_ID) and st.getQuestItemsCount(CASIANS_LIST_ID) and st.getQuestItemsCount(MEDUSAS_BLOOD_ID)<12 :
      st.giveItems(MEDUSAS_BLOOD_ID,1)
      if st.getQuestItemsCount(MEDUSAS_BLOOD_ID) < 12 :
        st.playSound("ItemSound.quest_itemget")
      else:
        st.playSound("ItemSound.quest_middle")
   elif npcId == 552 :
    if int(st.get("cond")) and st.getQuestItemsCount(TRIFFS_RING_ID) and st.getQuestItemsCount(POITANS_NOTES_ID) and st.getQuestItemsCount(CASIANS_LIST_ID) and st.getQuestItemsCount(FETTEREDSOULS_ICHOR_ID)<5 :
      st.giveItems(FETTEREDSOULS_ICHOR_ID,1)
      if st.getQuestItemsCount(FETTEREDSOULS_ICHOR_ID) < 5 :
        st.playSound("ItemSound.quest_itemget")
      else:
        st.playSound("ItemSound.quest_middle")
   elif npcId == 567 :
    if int(st.get("cond")) and st.getQuestItemsCount(TRIFFS_RING_ID) and st.getQuestItemsCount(POITANS_NOTES_ID) and st.getQuestItemsCount(CASIANS_LIST_ID) and st.getQuestItemsCount(ENCHT_GARGOYLES_NAIL_ID)<5 :
      st.giveItems(ENCHT_GARGOYLES_NAIL_ID,1)
      if st.getQuestItemsCount(ENCHT_GARGOYLES_NAIL_ID) < 5 :
        st.playSound("ItemSound.quest_itemget")
      else:
        st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(214,"214_TrialOfScholar","Trial Of Scholar")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7461)

STARTING.addTalkId(7461)

STARTED.addTalkId(7070)
STARTED.addTalkId(7071)
STARTED.addTalkId(7103)
STARTED.addTalkId(7111)
STARTED.addTalkId(7115)
STARTED.addTalkId(7230)
STARTED.addTalkId(7316)
STARTED.addTalkId(7458)
STARTED.addTalkId(7461)
STARTED.addTalkId(7608)
STARTED.addTalkId(7609)
STARTED.addTalkId(7610)
STARTED.addTalkId(7611)
STARTED.addTalkId(7612)

STARTED.addKillId(158)
STARTED.addKillId(201)
STARTED.addKillId(235)
STARTED.addKillId(269)
STARTED.addKillId(552)
STARTED.addKillId(554)
STARTED.addKillId(567)
STARTED.addKillId(580)
STARTED.addKillId(68)

STARTED.addQuestDrop(7115,SYMBOL_OF_JUREK_ID,1)
STARTED.addQuestDrop(7461,MIRIENS_SIGIL2_ID,1)
STARTED.addQuestDrop(7070,SYMBOL_OF_SYLVAIN_ID,1)
STARTED.addQuestDrop(7461,MIRIENS_SIGIL1_ID,1)
STARTED.addQuestDrop(7461,MIRIENS_INSTRUCTION_ID,1)
STARTED.addQuestDrop(7610,SYMBOL_OF_CRONOS_ID,1)
STARTED.addQuestDrop(7461,MIRIENS_SIGIL3_ID,1)
STARTED.addQuestDrop(7070,HIGHPRIESTS_SIGIL_ID,1)
STARTED.addQuestDrop(7608,CRYSTAL_OF_PURITY1_ID,1)
STARTED.addQuestDrop(7070,SYLVAINS_LETTER_ID,1)
STARTED.addQuestDrop(7609,CRETAS_LETTER1_ID,1)
STARTED.addQuestDrop(580,BROWN_SCROLL_SCRAP_ID,1)
STARTED.addQuestDrop(7071,CRETAS_PAINTING3_ID,1)
STARTED.addQuestDrop(7071,LUKAS_LETTER_ID,1)
STARTED.addQuestDrop(7609,CRETAS_PAINTING1_ID,1)
STARTED.addQuestDrop(7103,VALKONS_REQUEST_ID,1)
STARTED.addQuestDrop(7115,JUREKS_LIST_ID,1)
STARTED.addQuestDrop(68,MEYEDESTROYERS_SKIN_ID,1)
STARTED.addQuestDrop(269,SHAMANS_NECKLACE_ID,1)
STARTED.addQuestDrop(235,SHACKLES_SCALP_ID,1)
STARTED.addQuestDrop(7115,GMAGISTERS_SIGIL_ID,1)
STARTED.addQuestDrop(7608,MARIAS_LETTER1_ID,1)
STARTED.addQuestDrop(7608,CRETAS_PAINTING2_ID,1)
STARTED.addQuestDrop(7608,MARIAS_LETTER2_ID,1)
STARTED.addQuestDrop(7608,LUCILLAS_HANDBAG_ID,1)
STARTED.addQuestDrop(7111,DIETERS_KEY_ID,1)
STARTED.addQuestDrop(7316,SCRIPTURE_CHAPTER_1_ID,1)
STARTED.addQuestDrop(7103,SCRIPTURE_CHAPTER_2_ID,1)
STARTED.addQuestDrop(554,SCRIPTURE_CHAPTER_3_ID,1)
STARTED.addQuestDrop(7612,SCRIPTURE_CHAPTER_4_ID,1)
STARTED.addQuestDrop(7610,CRONOS_SIGIL_ID,1)
STARTED.addQuestDrop(7611,TRIFFS_RING_ID,1)
STARTED.addQuestDrop(7111,DIETERS_DIARY_ID,1)
STARTED.addQuestDrop(7610,CRONOS_LETTER_ID,1)
STARTED.addQuestDrop(7609,CRETAS_LETTER2_ID,1)
STARTED.addQuestDrop(7111,DIETERS_LETTER_ID,1)
STARTED.addQuestDrop(7230,RAUTS_LETTER_ENVELOPE_ID,1)
STARTED.addQuestDrop(7316,STRONG_LIQUOR_ID,1)
STARTED.addQuestDrop(7608,CRYSTAL_OF_PURITY2_ID,1)
STARTED.addQuestDrop(7612,CASIANS_LIST_ID,1)
STARTED.addQuestDrop(201,GHOULS_SKIN_ID,1)
STARTED.addQuestDrop(158,MEDUSAS_BLOOD_ID,1)
STARTED.addQuestDrop(552,FETTEREDSOULS_ICHOR_ID,1)
STARTED.addQuestDrop(567,ENCHT_GARGOYLES_NAIL_ID,1)
STARTED.addQuestDrop(7458,POITANS_NOTES_ID,1)
