# Made by Mr. Have fun! Version 0.2
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "214_TrialOfScholar"

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
        htmltext = "30461-04.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.giveItems(MIRIENS_SIGIL1_ID,1)
    elif event == "30461_1" :
          if st.getPlayer().getLevel()<36 :
            htmltext = "30461-09.htm"
            st.takeItems(SYMBOL_OF_JUREK_ID,1)
            st.giveItems(MIRIENS_INSTRUCTION_ID,1)
            st.takeItems(MIRIENS_SIGIL2_ID,1)
          else:
            htmltext = "30461-10.htm"
            st.takeItems(SYMBOL_OF_JUREK_ID,1)
            st.giveItems(MIRIENS_SIGIL3_ID,1)
            st.takeItems(MIRIENS_SIGIL2_ID,1)
    elif event == "30070_1" :
          htmltext = "30070-02.htm"
          st.giveItems(HIGHPRIESTS_SIGIL_ID,1)
          st.giveItems(SYLVAINS_LETTER_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "30608_1" :
          htmltext = "30608-02.htm"
          st.giveItems(MARIAS_LETTER1_ID,1)
          st.takeItems(SYLVAINS_LETTER_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "30608_2" :
          htmltext = "30608-07.htm"
    elif event == "30608_3" :
          htmltext = "30608-08.htm"
          st.giveItems(LUCILLAS_HANDBAG_ID,1)
          st.takeItems(CRETAS_LETTER1_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "30608_4" :
          htmltext = "30608-14.htm"
          st.takeItems(BROWN_SCROLL_SCRAP_ID,st.getQuestItemsCount(BROWN_SCROLL_SCRAP_ID))
          st.giveItems(CRYSTAL_OF_PURITY1_ID,1)
          st.takeItems(CRETAS_PAINTING3_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "30115_1" :
          htmltext = "30115-02.htm"
    elif event == "30115_2" :
          htmltext = "30115-03.htm"
          st.giveItems(JUREKS_LIST_ID,1)
          st.giveItems(GMAGISTERS_SIGIL_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "30071_1" :
          htmltext = "30071-04.htm"
          st.giveItems(CRETAS_PAINTING3_ID,1)
          st.takeItems(CRETAS_PAINTING2_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "30609_1" :
          htmltext = "30609-02.htm"
    elif event == "30609_2" :
          htmltext = "30609-03.htm"
    elif event == "30609_3" :
          htmltext = "30609-04.htm"
    elif event == "30609_4" :
          htmltext = "30609-05.htm"
          st.giveItems(CRETAS_LETTER1_ID,1)
          st.takeItems(MARIAS_LETTER2_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "30609_5" :
          htmltext = "30609-08.htm"
    elif event == "30609_6" :
          htmltext = "30609-09.htm"
          st.giveItems(CRETAS_PAINTING1_ID,1)
          st.takeItems(LUCILLAS_HANDBAG_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "30609_7" :
          htmltext = "30609-13.htm"
    elif event == "30609_8" :
          htmltext = "30609-14.htm"
          st.giveItems(CRETAS_LETTER2_ID,1)
          st.takeItems(DIETERS_KEY_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "30610_1" :
          htmltext = "30610-02.htm"
    elif event == "30610_2" :
          htmltext = "30610-03.htm"
    elif event == "30610_3" :
          htmltext = "30610-04.htm"
    elif event == "30610_4" :
          htmltext = "30610-05.htm"
    elif event == "30610_5" :
          htmltext = "30610-06.htm"
    elif event == "30610_6" :
          htmltext = "30610-07.htm"
    elif event == "30610_7" :
          htmltext = "30610-08.htm"
    elif event == "30610_8" :
          htmltext = "30610-09.htm"
    elif event == "30610_9" :
          htmltext = "30610-10.htm"
          st.giveItems(CRONOS_SIGIL_ID,1)
          st.giveItems(CRONOS_LETTER_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "30610_10" :
          htmltext = "30610-13.htm"
    elif event == "30610_11" :
          htmltext = "30610-14.htm"
          st.takeItems(SCRIPTURE_CHAPTER_1_ID,1)
          st.takeItems(SCRIPTURE_CHAPTER_2_ID,1)
          st.takeItems(SCRIPTURE_CHAPTER_3_ID,1)
          st.takeItems(SCRIPTURE_CHAPTER_4_ID,1)
          st.takeItems(CRONOS_SIGIL_ID,1)
          st.takeItems(TRIFFS_RING_ID,1)
          st.giveItems(SYMBOL_OF_CRONOS_ID,1)
          st.takeItems(DIETERS_DIARY_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "30111_1" :
          htmltext = "30111-02.htm"
    elif event == "30111_2" :
          htmltext = "30111-03.htm"
    elif event == "30111_3" :
          htmltext = "30111-04.htm"
    elif event == "30111_4" :
          htmltext = "30111-05.htm"
          st.giveItems(DIETERS_KEY_ID,1)
          st.takeItems(CRONOS_LETTER_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "30111_5" :
          htmltext = "30111-08.htm"
    elif event == "30111_6" :
          htmltext = "30111-09.htm"
          st.giveItems(DIETERS_LETTER_ID,1)
          st.takeItems(CRETAS_LETTER2_ID,1)
          st.giveItems(DIETERS_DIARY_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "30230_1" :
          htmltext = "30230-02.htm"
          st.giveItems(RAUTS_LETTER_ENVELOPE_ID,1)
          st.takeItems(DIETERS_LETTER_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "30316_1" :
          htmltext = "30316-02.htm"
          st.giveItems(SCRIPTURE_CHAPTER_1_ID,1)
          st.takeItems(RAUTS_LETTER_ENVELOPE_ID,1)
          st.giveItems(STRONG_LIQUOR_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "30611_1" :
          htmltext = "30611-02.htm"
    elif event == "30611_2" :
          htmltext = "30611-03.htm"
    elif event == "30611_3" :
          htmltext = "30611-04.htm"
          st.giveItems(TRIFFS_RING_ID,1)
          st.takeItems(STRONG_LIQUOR_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "30103_1" :
          htmltext = "30103-02.htm"
    elif event == "30103_2" :
          htmltext = "30103-03.htm"
    elif event == "30103_3" :
          htmltext = "30103-04.htm"
          st.giveItems(VALKONS_REQUEST_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "30612_1" :
          htmltext = "30612-03.htm"
    elif event == "30612_2" :
          htmltext = "30612-04.htm"
          st.giveItems(CASIANS_LIST_ID,1)
          st.playSound("ItemSound.quest_middle")
    elif event == "30612_3" :
          htmltext = "30612-07.htm"
          st.giveItems(SCRIPTURE_CHAPTER_4_ID,1)
          st.takeItems(CASIANS_LIST_ID,1)
          st.takeItems(GHOULS_SKIN_ID,st.getQuestItemsCount(GHOULS_SKIN_ID))
          st.takeItems(MEDUSAS_BLOOD_ID,st.getQuestItemsCount(MEDUSAS_BLOOD_ID))
          st.takeItems(FETTEREDSOULS_ICHOR_ID,st.getQuestItemsCount(FETTEREDSOULS_ICHOR_ID))
          st.takeItems(ENCHT_GARGOYLES_NAIL_ID,st.getQuestItemsCount(ENCHT_GARGOYLES_NAIL_ID))
          st.takeItems(POITANS_NOTES_ID,1)
          st.playSound("ItemSound.quest_middle")
    return htmltext


 def onTalk (self,npc,player):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   id = st.getState()
   if npcId != 30461 and id != STARTED : return htmltext

   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 30461 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
     if st.getPlayer().getClassId().getId() == 0x0b or st.getPlayer().getClassId().getId() == 0x1a or st.getPlayer().getClassId().getId() == 0x27 :
       if st.getPlayer().getLevel() >= 35 :
         htmltext = "30461-03.htm"
       else:
         htmltext = "30461-02.htm"
         st.exitQuest(1)
     else:
       htmltext = "30461-01.htm"
       st.exitQuest(1)
   elif npcId == 30461 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 30461 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID)==1 and st.getQuestItemsCount(SYMBOL_OF_SYLVAIN_ID)==0 :
        htmltext = "30461-05.htm"
   elif npcId == 30461 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID)==1 and st.getQuestItemsCount(SYMBOL_OF_SYLVAIN_ID)==1 :
        htmltext = "30461-06.htm"
        st.takeItems(SYMBOL_OF_SYLVAIN_ID,1)
        st.giveItems(MIRIENS_SIGIL2_ID,1)
        st.takeItems(MIRIENS_SIGIL1_ID,1)
        st.playSound("ItemSound.quest_middle")
   elif npcId == 30461 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL2_ID)==1 and st.getQuestItemsCount(SYMBOL_OF_JUREK_ID)==0 :
        htmltext = "30461-07.htm"
   elif npcId == 30461 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL2_ID)==1 and st.getQuestItemsCount(SYMBOL_OF_JUREK_ID)==1 :
        htmltext = "30461-08.htm"
   elif npcId == 30461 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_INSTRUCTION_ID)==1 :
        if st.getPlayer().getLevel()<36 :
          htmltext = "30461-11.htm"
        else:
          htmltext = "30461-12.htm"
          st.giveItems(MIRIENS_SIGIL3_ID,1)
          st.takeItems(MIRIENS_INSTRUCTION_ID,1)
          st.playSound("ItemSound.quest_middle")
   elif npcId == 30461 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID)==1 :
        if st.getQuestItemsCount(SYMBOL_OF_CRONOS_ID) == 0 :
          htmltext = "30461-13.htm"
        else:
            st.addExpAndSp(80265,30000)
            st.giveItems(7562,8)
            htmltext = "30461-14.htm"
            st.set("cond","0")
            st.set("onlyone","1")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
            st.takeItems(SYMBOL_OF_CRONOS_ID,1)
            st.giveItems(MARK_OF_SCHOLAR_ID,1)
            st.takeItems(MIRIENS_SIGIL3_ID,1)
   elif npcId == 30070 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID)==1 and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) == 0 and st.getQuestItemsCount(SYMBOL_OF_SYLVAIN_ID) == 0 :
        htmltext = "30070-01.htm"
   elif npcId == 30070 and int(st.get("cond"))==1 and st.getQuestItemsCount(CRYSTAL_OF_PURITY1_ID)==0 and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) :
        htmltext = "30070-03.htm"
   elif npcId == 30070 and int(st.get("cond"))==1 and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(CRYSTAL_OF_PURITY1_ID) :
        htmltext = "30070-04.htm"
        st.giveItems(SYMBOL_OF_SYLVAIN_ID,1)
        st.takeItems(HIGHPRIESTS_SIGIL_ID,1)
        st.takeItems(CRYSTAL_OF_PURITY1_ID,1)
        st.playSound("ItemSound.quest_middle")
   elif npcId == 30070 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(SYMBOL_OF_SYLVAIN_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID)==0 :
        htmltext = "30070-05.htm"
   elif npcId == 30070 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL2_ID) or st.getQuestItemsCount(MIRIENS_SIGIL3_ID)) :
        htmltext = "30070-06.htm"
   elif npcId == 30608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(SYLVAINS_LETTER_ID)) :
        htmltext = "30608-01.htm"
   elif npcId == 30608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(MARIAS_LETTER1_ID)) :
        htmltext = "30608-03.htm"
   elif npcId == 30608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(LUKAS_LETTER_ID)) :
        htmltext = "30608-04.htm"
        st.giveItems(MARIAS_LETTER2_ID,1)
        st.takeItems(LUKAS_LETTER_ID,1)
        st.playSound("ItemSound.quest_middle")
   elif npcId == 30608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(MARIAS_LETTER2_ID)) :
        htmltext = "30608-05.htm"
   elif npcId == 30608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(CRETAS_LETTER1_ID)) :
        htmltext = "30608-06.htm"
   elif npcId == 30608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(LUCILLAS_HANDBAG_ID)) :
        htmltext = "30608-09.htm"
   elif npcId == 30608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(CRETAS_PAINTING1_ID)) :
        htmltext = "30608-10.htm"
        st.giveItems(CRETAS_PAINTING2_ID,1)
        st.takeItems(CRETAS_PAINTING1_ID,1)
        st.playSound("ItemSound.quest_middle")
   elif npcId == 30608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(CRETAS_PAINTING2_ID)) :
        htmltext = "30608-11.htm"
   elif npcId == 30608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(CRETAS_PAINTING3_ID)) and st.getQuestItemsCount(BROWN_SCROLL_SCRAP_ID)<5 :
        htmltext = "30608-12.htm"
   elif npcId == 30608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(CRETAS_PAINTING3_ID)) and st.getQuestItemsCount(BROWN_SCROLL_SCRAP_ID)>=5 :
        htmltext = "30608-13.htm"
   elif npcId == 30608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(CRYSTAL_OF_PURITY1_ID)) :
        htmltext = "30608-15.htm"
   elif npcId == 30608 and int(st.get("cond"))==1 and (st.getQuestItemsCount(SYMBOL_OF_SYLVAIN_ID) or st.getQuestItemsCount(MIRIENS_SIGIL2_ID)) :
        htmltext = "30608-16.htm"
   elif npcId == 30608 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID)==1 and st.getQuestItemsCount(VALKONS_REQUEST_ID)==0 :
        htmltext = "30608-17.htm"
   elif npcId == 30608 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID)==1 and st.getQuestItemsCount(VALKONS_REQUEST_ID)==1 :
        htmltext = "30608-18.htm"
        st.giveItems(CRYSTAL_OF_PURITY2_ID,1)
        st.takeItems(VALKONS_REQUEST_ID,1)
        st.playSound("ItemSound.quest_middle")
   elif npcId == 30115 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL2_ID)==1 and st.getQuestItemsCount(GMAGISTERS_SIGIL_ID)==0 and st.getQuestItemsCount(SYMBOL_OF_JUREK_ID)==0 :
        htmltext = "30115-01.htm"
   elif npcId == 30115 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL2_ID)==1 and st.getQuestItemsCount(JUREKS_LIST_ID)==1 :
        if st.getQuestItemsCount(MEYEDESTROYERS_SKIN_ID)+st.getQuestItemsCount(SHAMANS_NECKLACE_ID)+st.getQuestItemsCount(SHACKLES_SCALP_ID)<12 :
          htmltext = "30115-04.htm"
        else:
          htmltext = "30115-05.htm"
          st.takeItems(JUREKS_LIST_ID,1)
          st.takeItems(MEYEDESTROYERS_SKIN_ID,st.getQuestItemsCount(MEYEDESTROYERS_SKIN_ID))
          st.takeItems(SHAMANS_NECKLACE_ID,st.getQuestItemsCount(SHAMANS_NECKLACE_ID))
          st.takeItems(SHACKLES_SCALP_ID,st.getQuestItemsCount(SHACKLES_SCALP_ID))
          st.giveItems(SYMBOL_OF_JUREK_ID,1)
          st.takeItems(GMAGISTERS_SIGIL_ID,1)
          st.playSound("ItemSound.quest_middle")
   elif npcId == 30115 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL2_ID) and st.getQuestItemsCount(SYMBOL_OF_JUREK_ID)) and st.getQuestItemsCount(GMAGISTERS_SIGIL_ID)==0 :
        htmltext = "30115-06.htm"
   elif npcId == 30115 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL1_ID) or st.getQuestItemsCount(MIRIENS_SIGIL3_ID)) :
        htmltext = "30115-07.htm"
   elif npcId == 30071 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(MARIAS_LETTER1_ID) :
        htmltext = "30071-01.htm"
        st.giveItems(LUKAS_LETTER_ID,1)
        st.takeItems(MARIAS_LETTER1_ID,1)
        st.playSound("ItemSound.quest_middle")
   elif npcId == 30071 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and (st.getQuestItemsCount(MARIAS_LETTER2_ID) or st.getQuestItemsCount(CRETAS_LETTER1_ID) or st.getQuestItemsCount(LUCILLAS_HANDBAG_ID) or st.getQuestItemsCount(CRETAS_PAINTING1_ID) or st.getQuestItemsCount(LUKAS_LETTER_ID)) :
        htmltext = "30071-02.htm"
   elif npcId == 30071 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(CRETAS_PAINTING2_ID) :
        htmltext = "30071-03.htm"
   elif npcId == 30071 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(CRETAS_PAINTING3_ID) :
        if st.getQuestItemsCount(BROWN_SCROLL_SCRAP_ID)<5 :
          htmltext = "30071-05.htm"
        else:
          htmltext = "30071-06.htm"
   elif npcId == 30071 and int(st.get("cond"))==1 and (st.getQuestItemsCount(SYMBOL_OF_SYLVAIN_ID) or st.getQuestItemsCount(MIRIENS_SIGIL2_ID) or st.getQuestItemsCount(MIRIENS_SIGIL3_ID) or st.getQuestItemsCount(CRYSTAL_OF_PURITY1_ID)) :
        htmltext = "30071-07.htm"
   elif npcId == 30609 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(MARIAS_LETTER2_ID) :
        htmltext = "30609-01.htm"
   elif npcId == 30609 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(CRETAS_LETTER1_ID) :
        htmltext = "30609-06.htm"
   elif npcId == 30609 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(LUCILLAS_HANDBAG_ID) :
        htmltext = "30609-07.htm"
   elif npcId == 30609 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and (st.getQuestItemsCount(CRETAS_PAINTING1_ID) or st.getQuestItemsCount(CRETAS_PAINTING2_ID) or st.getQuestItemsCount(CRETAS_PAINTING3_ID)) :
        htmltext = "30609-10.htm"
   elif npcId == 30609 and int(st.get("cond"))==1 and (st.getQuestItemsCount(CRYSTAL_OF_PURITY1_ID) or st.getQuestItemsCount(SYMBOL_OF_SYLVAIN_ID) or st.getQuestItemsCount(MIRIENS_SIGIL2_ID)) :
        htmltext = "30609-11.htm"
   elif npcId == 30609 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID) and st.getQuestItemsCount(DIETERS_KEY_ID) :
        htmltext = "30609-12.htm"
   elif npcId == 30609 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID)==1 and st.getQuestItemsCount(DIETERS_KEY_ID)==0 :
        htmltext = "30609-15.htm"
   elif npcId == 30610 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID)==1 and st.getQuestItemsCount(CRONOS_SIGIL_ID)==0 and st.getQuestItemsCount(SYMBOL_OF_CRONOS_ID)==0 :
        htmltext = "30610-01.htm"
   elif npcId == 30610 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MIRIENS_SIGIL3_ID) or st.getQuestItemsCount(CRONOS_SIGIL_ID)) :
        if st.getQuestItemsCount(SCRIPTURE_CHAPTER_1_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_2_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_3_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_4_ID) :
          htmltext = "30610-12.htm"
        else:
          htmltext = "30610-11.htm"
   elif npcId == 30610 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID)==1 and st.getQuestItemsCount(SYMBOL_OF_CRONOS_ID)==1 and st.getQuestItemsCount(CRONOS_SIGIL_ID)==0 :
        htmltext = "30610-15.htm"
   elif npcId == 30111 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID) and st.getQuestItemsCount(CRONOS_SIGIL_ID) and st.getQuestItemsCount(CRONOS_LETTER_ID) :
        htmltext = "30111-01.htm"
   elif npcId == 30111 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID) and st.getQuestItemsCount(CRONOS_SIGIL_ID) and st.getQuestItemsCount(DIETERS_KEY_ID) :
          htmltext = "30111-06.htm"
   elif npcId == 30111 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID) and st.getQuestItemsCount(CRONOS_SIGIL_ID) and st.getQuestItemsCount(CRETAS_LETTER2_ID) :
          htmltext = "30111-07.htm"
   elif npcId == 30111 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID) and st.getQuestItemsCount(CRONOS_SIGIL_ID) and st.getQuestItemsCount(DIETERS_DIARY_ID) and st.getQuestItemsCount(DIETERS_LETTER_ID) :
          htmltext = "30111-10.htm"
   elif npcId == 30111 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID) and st.getQuestItemsCount(CRONOS_SIGIL_ID) and st.getQuestItemsCount(DIETERS_DIARY_ID) and st.getQuestItemsCount(RAUTS_LETTER_ENVELOPE_ID) :
          htmltext = "30111-11.htm"
   elif npcId == 30111 and int(st.get("cond"))==1 and st.getQuestItemsCount(MIRIENS_SIGIL3_ID) and st.getQuestItemsCount(CRONOS_SIGIL_ID) and st.getQuestItemsCount(DIETERS_DIARY_ID) and st.getQuestItemsCount(DIETERS_LETTER_ID)==0 and st.getQuestItemsCount(RAUTS_LETTER_ENVELOPE_ID)==0 :
          if st.getQuestItemsCount(SCRIPTURE_CHAPTER_1_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_2_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_3_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_4_ID) :
            htmltext = "30111-13.htm"
          else:
            htmltext = "30111-12.htm"
   elif npcId == 30111 and int(st.get("cond"))==1 and st.getQuestItemsCount(SYMBOL_OF_CRONOS_ID)==1 :
          htmltext = "30111-15.htm"
   elif npcId == 30230 and int(st.get("cond"))==1 and st.getQuestItemsCount(DIETERS_DIARY_ID) and st.getQuestItemsCount(DIETERS_LETTER_ID) :
        htmltext = "30230-01.htm"
   elif npcId == 30230 and int(st.get("cond"))==1 and st.getQuestItemsCount(DIETERS_DIARY_ID) and st.getQuestItemsCount(RAUTS_LETTER_ENVELOPE_ID) :
        htmltext = "30230-03.htm"
   elif npcId == 30230 and int(st.get("cond"))==1 and st.getQuestItemsCount(DIETERS_DIARY_ID)==1 and (st.getQuestItemsCount(STRONG_LIQUOR_ID) or st.getQuestItemsCount(TRIFFS_RING_ID)) :
        htmltext = "30230-04.htm"
   elif npcId == 30316 and int(st.get("cond"))==1 and st.getQuestItemsCount(DIETERS_DIARY_ID) and st.getQuestItemsCount(RAUTS_LETTER_ENVELOPE_ID) :
        htmltext = "30316-01.htm"
   elif npcId == 30316 and int(st.get("cond"))==1 and st.getQuestItemsCount(DIETERS_DIARY_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_1_ID) and st.getQuestItemsCount(STRONG_LIQUOR_ID) :
        htmltext = "30316-04.htm"
   elif npcId == 30316 and int(st.get("cond"))==1 and st.getQuestItemsCount(DIETERS_DIARY_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_1_ID) and st.getQuestItemsCount(TRIFFS_RING_ID) :
        htmltext = "30316-05.htm"
   elif npcId == 30611 and int(st.get("cond"))==1 and st.getQuestItemsCount(DIETERS_DIARY_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_1_ID) and st.getQuestItemsCount(STRONG_LIQUOR_ID) :
        htmltext = "30611-01.htm"
   elif npcId == 30611 and int(st.get("cond"))==1 and (st.getQuestItemsCount(TRIFFS_RING_ID) or st.getQuestItemsCount(SYMBOL_OF_CRONOS_ID)) :
        htmltext = "30611-05.htm"
   elif npcId == 30103 and int(st.get("cond"))==1 and st.getQuestItemsCount(TRIFFS_RING_ID)==1 and st.getQuestItemsCount(VALKONS_REQUEST_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_PURITY2_ID)==0 and st.getQuestItemsCount(SCRIPTURE_CHAPTER_2_ID)==0 :
        htmltext = "30103-01.htm"
   elif npcId == 30103 and int(st.get("cond"))==1 and st.getQuestItemsCount(TRIFFS_RING_ID)==1 and st.getQuestItemsCount(VALKONS_REQUEST_ID)==1 and st.getQuestItemsCount(CRYSTAL_OF_PURITY2_ID)==0 and st.getQuestItemsCount(SCRIPTURE_CHAPTER_2_ID)==0 :
        htmltext = "30103-05.htm"
   elif npcId == 30103 and int(st.get("cond"))==1 and st.getQuestItemsCount(TRIFFS_RING_ID)==1 and st.getQuestItemsCount(VALKONS_REQUEST_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_PURITY2_ID)==1 and st.getQuestItemsCount(SCRIPTURE_CHAPTER_2_ID)==0 :
        htmltext = "30103-06.htm"
        st.giveItems(SCRIPTURE_CHAPTER_2_ID,1)
        st.takeItems(CRYSTAL_OF_PURITY2_ID,1)
        st.playSound("ItemSound.quest_middle")
   elif npcId == 30103 and int(st.get("cond"))==1 and st.getQuestItemsCount(TRIFFS_RING_ID)==1 and st.getQuestItemsCount(VALKONS_REQUEST_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_PURITY2_ID)==0 and st.getQuestItemsCount(SCRIPTURE_CHAPTER_2_ID)==1 :
        htmltext = "30103-07.htm"
   elif npcId == 30458 and int(st.get("cond"))==1 and st.getQuestItemsCount(TRIFFS_RING_ID)==1 and st.getQuestItemsCount(POITANS_NOTES_ID) == 0 and st.getQuestItemsCount(CASIANS_LIST_ID) == 0 and st.getQuestItemsCount(SCRIPTURE_CHAPTER_4_ID) == 0 :
        htmltext = "30458-01.htm"
        st.giveItems(POITANS_NOTES_ID,1)
        st.playSound("ItemSound.quest_middle")
   elif npcId == 30458 and int(st.get("cond"))==1 and st.getQuestItemsCount(TRIFFS_RING_ID) and st.getQuestItemsCount(POITANS_NOTES_ID) and st.getQuestItemsCount(CASIANS_LIST_ID) == 0 and st.getQuestItemsCount(SCRIPTURE_CHAPTER_4_ID) == 0 :
        htmltext = "30458-02.htm"
   elif npcId == 30458 and int(st.get("cond"))==1 and st.getQuestItemsCount(TRIFFS_RING_ID) and st.getQuestItemsCount(POITANS_NOTES_ID) and st.getQuestItemsCount(CASIANS_LIST_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_4_ID)==0 :
        htmltext = "30458-03.htm"
   elif npcId == 30458 and int(st.get("cond"))==1 and st.getQuestItemsCount(TRIFFS_RING_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_4_ID) and st.getQuestItemsCount(POITANS_NOTES_ID) == 0 and st.getQuestItemsCount(CASIANS_LIST_ID) == 0 :
        htmltext = "30458-04.htm"
   elif npcId == 30612 and int(st.get("cond"))==1 and st.getQuestItemsCount(TRIFFS_RING_ID) and st.getQuestItemsCount(POITANS_NOTES_ID) and st.getQuestItemsCount(CASIANS_LIST_ID)==0 :
        if st.getQuestItemsCount(SCRIPTURE_CHAPTER_1_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_2_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_3_ID) :
          htmltext = "30612-02.htm"
        else:
          htmltext = "30612-01.htm"
   elif npcId == 30612 and int(st.get("cond"))==1 and st.getQuestItemsCount(TRIFFS_RING_ID) and st.getQuestItemsCount(POITANS_NOTES_ID) and st.getQuestItemsCount(CASIANS_LIST_ID) :
        if st.getQuestItemsCount(GHOULS_SKIN_ID)+st.getQuestItemsCount(MEDUSAS_BLOOD_ID)+st.getQuestItemsCount(FETTEREDSOULS_ICHOR_ID)+st.getQuestItemsCount(ENCHT_GARGOYLES_NAIL_ID)<32 :
          htmltext = "30612-05.htm"
        else:
          htmltext = "30612-06.htm"
   elif npcId == 30612 and int(st.get("cond"))==1 and st.getQuestItemsCount(POITANS_NOTES_ID) == 0 and st.getQuestItemsCount(CASIANS_LIST_ID) == 0 and st.getQuestItemsCount(TRIFFS_RING_ID)==1 and st.getQuestItemsCount(SCRIPTURE_CHAPTER_1_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_2_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_3_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_4_ID) :
        htmltext = "30612-08.htm"
   return htmltext

 def onKill (self,npc,player):
   st = player.getQuestState(qn)
   if not st : return
   if st.getState() != STARTED : return
   
   npcId = npc.getNpcId()
   if npcId == 20580 :
    if int(st.get("cond")) and st.getQuestItemsCount(MIRIENS_SIGIL1_ID) and st.getQuestItemsCount(HIGHPRIESTS_SIGIL_ID) and st.getQuestItemsCount(CRETAS_PAINTING3_ID) and st.getQuestItemsCount(BROWN_SCROLL_SCRAP_ID)<5 :
      if st.getRandom(100) < 50 :
        st.giveItems(BROWN_SCROLL_SCRAP_ID,1)
        if st.getQuestItemsCount(BROWN_SCROLL_SCRAP_ID) < 5 :
          st.playSound("ItemSound.quest_itemget")
        else:
          st.playSound("ItemSound.quest_middle")
   elif npcId == 20068 :
    if int(st.get("cond")) and st.getQuestItemsCount(MIRIENS_SIGIL2_ID) and st.getQuestItemsCount(GMAGISTERS_SIGIL_ID) and st.getQuestItemsCount(JUREKS_LIST_ID) and st.getQuestItemsCount(MEYEDESTROYERS_SKIN_ID)<5 :
      if st.getRandom(100) < 50 :
        st.giveItems(MEYEDESTROYERS_SKIN_ID,1)
        if st.getQuestItemsCount(MEYEDESTROYERS_SKIN_ID) < 5 :
          st.playSound("ItemSound.quest_itemget")
        else:
          st.playSound("ItemSound.quest_middle")
   elif npcId == 20269 :
    if int(st.get("cond")) and st.getQuestItemsCount(MIRIENS_SIGIL2_ID) and st.getQuestItemsCount(GMAGISTERS_SIGIL_ID) and st.getQuestItemsCount(JUREKS_LIST_ID) and st.getQuestItemsCount(SHAMANS_NECKLACE_ID)<5 :
      if st.getRandom(100) < 50 :
        st.giveItems(SHAMANS_NECKLACE_ID,1)
        if st.getQuestItemsCount(SHAMANS_NECKLACE_ID) < 5 :
          st.playSound("ItemSound.quest_itemget")
        else:
          st.playSound("ItemSound.quest_middle")
   elif npcId == 20235 :
    if int(st.get("cond")) and st.getQuestItemsCount(MIRIENS_SIGIL2_ID) and st.getQuestItemsCount(GMAGISTERS_SIGIL_ID) and st.getQuestItemsCount(JUREKS_LIST_ID) and st.getQuestItemsCount(SHACKLES_SCALP_ID)<2 :
      st.giveItems(SHACKLES_SCALP_ID,1)
      if st.getQuestItemsCount(SHACKLES_SCALP_ID) < 2 :
        st.playSound("ItemSound.quest_itemget")
      else:
        st.playSound("ItemSound.quest_middle")
   elif npcId == 20554 :
    if int(st.get("cond")) and st.getQuestItemsCount(MIRIENS_SIGIL3_ID) and st.getQuestItemsCount(CRONOS_SIGIL_ID) and st.getQuestItemsCount(TRIFFS_RING_ID) and st.getQuestItemsCount(SCRIPTURE_CHAPTER_3_ID) == 0 :
      if st.getRandom(100) < 30 :
        st.giveItems(SCRIPTURE_CHAPTER_3_ID,1)
        st.playSound("ItemSound.quest_middle")
   elif npcId == 20201 :
    if int(st.get("cond")) and st.getQuestItemsCount(TRIFFS_RING_ID) and st.getQuestItemsCount(POITANS_NOTES_ID) and st.getQuestItemsCount(CASIANS_LIST_ID) and st.getQuestItemsCount(GHOULS_SKIN_ID)<10 :
      st.giveItems(GHOULS_SKIN_ID,1)
      if st.getQuestItemsCount(GHOULS_SKIN_ID) < 10 :
        st.playSound("ItemSound.quest_itemget")
      else:
        st.playSound("ItemSound.quest_middle")
   elif npcId == 20158 :
    if int(st.get("cond")) and st.getQuestItemsCount(TRIFFS_RING_ID) and st.getQuestItemsCount(POITANS_NOTES_ID) and st.getQuestItemsCount(CASIANS_LIST_ID) and st.getQuestItemsCount(MEDUSAS_BLOOD_ID)<12 :
      st.giveItems(MEDUSAS_BLOOD_ID,1)
      if st.getQuestItemsCount(MEDUSAS_BLOOD_ID) < 12 :
        st.playSound("ItemSound.quest_itemget")
      else:
        st.playSound("ItemSound.quest_middle")
   elif npcId == 20552 :
    if int(st.get("cond")) and st.getQuestItemsCount(TRIFFS_RING_ID) and st.getQuestItemsCount(POITANS_NOTES_ID) and st.getQuestItemsCount(CASIANS_LIST_ID) and st.getQuestItemsCount(FETTEREDSOULS_ICHOR_ID)<5 :
      st.giveItems(FETTEREDSOULS_ICHOR_ID,1)
      if st.getQuestItemsCount(FETTEREDSOULS_ICHOR_ID) < 5 :
        st.playSound("ItemSound.quest_itemget")
      else:
        st.playSound("ItemSound.quest_middle")
   elif npcId == 20567 :
    if int(st.get("cond")) and st.getQuestItemsCount(TRIFFS_RING_ID) and st.getQuestItemsCount(POITANS_NOTES_ID) and st.getQuestItemsCount(CASIANS_LIST_ID) and st.getQuestItemsCount(ENCHT_GARGOYLES_NAIL_ID)<5 :
      st.giveItems(ENCHT_GARGOYLES_NAIL_ID,1)
      if st.getQuestItemsCount(ENCHT_GARGOYLES_NAIL_ID) < 5 :
        st.playSound("ItemSound.quest_itemget")
      else:
        st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(214,qn,"Trial Of Scholar")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30461)

QUEST.addTalkId(30461)

QUEST.addTalkId(30070)
QUEST.addTalkId(30071)
QUEST.addTalkId(30103)
QUEST.addTalkId(30111)
QUEST.addTalkId(30115)
QUEST.addTalkId(30230)
QUEST.addTalkId(30316)
QUEST.addTalkId(30458)
QUEST.addTalkId(30608)
QUEST.addTalkId(30609)
QUEST.addTalkId(30610)
QUEST.addTalkId(30611)
QUEST.addTalkId(30612)

QUEST.addKillId(20158)
QUEST.addKillId(20201)
QUEST.addKillId(20235)
QUEST.addKillId(20269)
QUEST.addKillId(20552)
QUEST.addKillId(20554)
QUEST.addKillId(20567)
QUEST.addKillId(20580)
QUEST.addKillId(20068)

STARTED.addQuestDrop(30115,SYMBOL_OF_JUREK_ID,1)
STARTED.addQuestDrop(30461,MIRIENS_SIGIL2_ID,1)
STARTED.addQuestDrop(30070,SYMBOL_OF_SYLVAIN_ID,1)
STARTED.addQuestDrop(30461,MIRIENS_SIGIL1_ID,1)
STARTED.addQuestDrop(30461,MIRIENS_INSTRUCTION_ID,1)
STARTED.addQuestDrop(30610,SYMBOL_OF_CRONOS_ID,1)
STARTED.addQuestDrop(30461,MIRIENS_SIGIL3_ID,1)
STARTED.addQuestDrop(30070,HIGHPRIESTS_SIGIL_ID,1)
STARTED.addQuestDrop(30608,CRYSTAL_OF_PURITY1_ID,1)
STARTED.addQuestDrop(30070,SYLVAINS_LETTER_ID,1)
STARTED.addQuestDrop(30609,CRETAS_LETTER1_ID,1)
STARTED.addQuestDrop(20580,BROWN_SCROLL_SCRAP_ID,1)
STARTED.addQuestDrop(30071,CRETAS_PAINTING3_ID,1)
STARTED.addQuestDrop(30071,LUKAS_LETTER_ID,1)
STARTED.addQuestDrop(30609,CRETAS_PAINTING1_ID,1)
STARTED.addQuestDrop(30103,VALKONS_REQUEST_ID,1)
STARTED.addQuestDrop(30115,JUREKS_LIST_ID,1)
STARTED.addQuestDrop(20068,MEYEDESTROYERS_SKIN_ID,1)
STARTED.addQuestDrop(20269,SHAMANS_NECKLACE_ID,1)
STARTED.addQuestDrop(20235,SHACKLES_SCALP_ID,1)
STARTED.addQuestDrop(30115,GMAGISTERS_SIGIL_ID,1)
STARTED.addQuestDrop(30608,MARIAS_LETTER1_ID,1)
STARTED.addQuestDrop(30608,CRETAS_PAINTING2_ID,1)
STARTED.addQuestDrop(30608,MARIAS_LETTER2_ID,1)
STARTED.addQuestDrop(30608,LUCILLAS_HANDBAG_ID,1)
STARTED.addQuestDrop(30111,DIETERS_KEY_ID,1)
STARTED.addQuestDrop(30316,SCRIPTURE_CHAPTER_1_ID,1)
STARTED.addQuestDrop(30103,SCRIPTURE_CHAPTER_2_ID,1)
STARTED.addQuestDrop(20554,SCRIPTURE_CHAPTER_3_ID,1)
STARTED.addQuestDrop(30612,SCRIPTURE_CHAPTER_4_ID,1)
STARTED.addQuestDrop(30610,CRONOS_SIGIL_ID,1)
STARTED.addQuestDrop(30611,TRIFFS_RING_ID,1)
STARTED.addQuestDrop(30111,DIETERS_DIARY_ID,1)
STARTED.addQuestDrop(30610,CRONOS_LETTER_ID,1)
STARTED.addQuestDrop(30609,CRETAS_LETTER2_ID,1)
STARTED.addQuestDrop(30111,DIETERS_LETTER_ID,1)
STARTED.addQuestDrop(30230,RAUTS_LETTER_ENVELOPE_ID,1)
STARTED.addQuestDrop(30316,STRONG_LIQUOR_ID,1)
STARTED.addQuestDrop(30608,CRYSTAL_OF_PURITY2_ID,1)
STARTED.addQuestDrop(30612,CASIANS_LIST_ID,1)
STARTED.addQuestDrop(20201,GHOULS_SKIN_ID,1)
STARTED.addQuestDrop(20158,MEDUSAS_BLOOD_ID,1)
STARTED.addQuestDrop(20552,FETTEREDSOULS_ICHOR_ID,1)
STARTED.addQuestDrop(20567,ENCHT_GARGOYLES_NAIL_ID,1)
STARTED.addQuestDrop(30458,POITANS_NOTES_ID,1)

print "importing quests: 214: Trial Of Scholar"