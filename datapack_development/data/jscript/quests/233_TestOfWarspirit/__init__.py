# Maked by Mr. Have fun! Version 0.2
print "importing quests: 233: Test Of Warspirit"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_WARSPIRIT_ID = 2879
VENDETTA_TOTEM_ID = 2880
TAMLIN_ORC_HEAD_ID = 2881
WARSPIRIT_TOTEM_ID = 2882
ORIMS_CONTRACT_ID = 2883
PORTAS_EYE_ID = 2884
BRAKIS_REMAINS1_ID = 2887
HERMODTS_REMAINS1_ID = 2901
KIRUNAS_REMAINS1_ID = 2910
TONARS_REMAINS1_ID = 2894
BRAKIS_REMAINS2_ID = 2911
HERMODTS_REMAINS2_ID = 2913
KIRUNAS_REMAINS2_ID = 2914
TONARS_REMAINS2_ID = 2912
EXCUROS_SCALE_ID = 2885
MORDEOS_TALON_ID = 2886
PEKIRONS_TOTEM_ID = 2888
TONARS_SKULL_ID = 2889
TONARS_RIB_BONE_ID = 2890
TONARS_SPINE_ID = 2891
TONARS_ARM_BONE_ID = 2892
TONARS_THIGH_BONE_ID = 2893
MANAKIAS_TOTEM_ID = 2895
HERMODTS_SKULL_ID = 2896
HERMODTS_RIB_BONE_ID = 2897
HERMODTS_SPINE_ID = 2898
HERMODTS_ARM_BONE_ID = 2899
HERMODTS_THIGH_BONE_ID = 2900
RACOYS_TOTEM_ID = 2902
KIRUNAS_SKULL_ID = 2905
KIRUNAS_RIB_BONE_ID = 2906
KIRUNAS_SPINE_ID = 2907
KIRUNAS_ARM_BONE_ID = 2908
KIRUNAS_THIGH_BONE_ID = 2909
INSECT_DIAGRAM_BOOK_ID = 2904
VIVIANTES_LETTER_ID = 2903

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        htmlfile = "7510-05.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
    elif event == "7630_1" :
          htmltext = "7630-02.htm"
    elif event == "7630_2" :
          htmltext = "7630-03.htm"
    elif event == "7630_3" :
          htmltext = "7630-04.htm"
          st.giveItems(ORIMS_CONTRACT_ID,1)
    elif event == "7682_1" :
          htmltext = "7682-02.htm"
          st.giveItems(PEKIRONS_TOTEM_ID,1)
    elif event == "7515_1" :
          htmltext = "7515-02.htm"
          st.giveItems(MANAKIAS_TOTEM_ID,1)
    elif event == "7507_1" :
          htmltext = "7507-02.htm"
          st.giveItems(RACOYS_TOTEM_ID,1)
    elif event == "7030_1" :
          htmltext = "7030-02.htm"
    elif event == "7030_2" :
          htmltext = "7030-03.htm"
    elif event == "7030_3" :
          htmltext = "7030-04.htm"
          st.giveItems(VIVIANTES_LETTER_ID,1)
    elif event == "7649_1" :
          htmltext = "7649-02.htm"
    elif event == "7649_2" :
          if st.getGameTicks() != int(st.get("id")) :
            st.set("id",str(st.getGameTicks()))
            st.takeItems(WARSPIRIT_TOTEM_ID,1)
            st.takeItems(BRAKIS_REMAINS2_ID,1)
            st.takeItems(HERMODTS_REMAINS2_ID,1)
            st.takeItems(KIRUNAS_REMAINS2_ID,1)
            st.takeItems(TAMLIN_ORC_HEAD_ID,st.getQuestItemsCount(TAMLIN_ORC_HEAD_ID))
            st.addExpAndSp(23000,0)
            st.addExpAndSp(0,2900)
            st.giveItems(MARK_OF_WARSPIRIT_ID,1)
            st.takeItems(TONARS_REMAINS2_ID,1)
            htmlfile = "7649-03.htm"
            st.set("cond","0")
            st.set("onlyone","1")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7510 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if int(st.get("cond"))<15 :
          if st.getPlayer().getRace().ordinal() != 3 :
            htmltext = "7510-01.htm"
          elif st.getPlayer().getRace().ordinal() == 3 and st.getPlayer().getClassId().getId() != 0x32 :
            htmltext = "7510-02.htm"
          elif st.getPlayer().getRace().ordinal() == 3 and st.getPlayer().getClassId().getId() == 0x32 :
            if st.getPlayer().getLevel()<39 :
              htmltext = "7510-03.htm"
            else:
              htmltext = "7510-04.htm"
              st.set("cond","1")
              return htmltext
        else:
          htmltext = "7510-04.htm"
   elif npcId == 7510 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7510 and int(st.get("cond"))==1 and st.getQuestItemsCount((VENDETTA_TOTEM_ID) == 0 and st.getQuestItemsCount(WARSPIRIT_TOTEM_ID)) == 0 :
        if st.getQuestItemsCount((BRAKIS_REMAINS1_ID) and st.getQuestItemsCount(HERMODTS_REMAINS1_ID) and st.getQuestItemsCount(KIRUNAS_REMAINS1_ID) and st.getQuestItemsCount(TONARS_REMAINS1_ID)) :
          htmltext = "7510-07.htm"
          st.takeItems(BRAKIS_REMAINS1_ID,1)
          st.takeItems(HERMODTS_REMAINS1_ID,1)
          st.takeItems(KIRUNAS_REMAINS1_ID,1)
          st.giveItems(VENDETTA_TOTEM_ID,1)
          st.takeItems(TONARS_REMAINS1_ID,1)
        else:
          htmltext = "7510-06.htm"
   elif npcId == 7510 and int(st.get("cond"))==1 and st.getQuestItemsCount(VENDETTA_TOTEM_ID)==1 :
        if st.getQuestItemsCount(TAMLIN_ORC_HEAD_ID)<13 :
          htmltext = "7510-08.htm"
        else:
          htmltext = "7510-09.htm"
          st.giveItems(WARSPIRIT_TOTEM_ID,1)
          st.takeItems(VENDETTA_TOTEM_ID,1)
          st.giveItems(BRAKIS_REMAINS2_ID,1)
          st.giveItems(HERMODTS_REMAINS2_ID,1)
          st.giveItems(KIRUNAS_REMAINS2_ID,1)
          st.giveItems(TONARS_REMAINS2_ID,1)
   elif npcId == 7510 and int(st.get("cond"))==1 and st.getQuestItemsCount(WARSPIRIT_TOTEM_ID)==1 :
        htmltext = "7510-10.htm"
   elif npcId == 7630 and int(st.get("cond"))==1 and st.getQuestItemsCount((ORIMS_CONTRACT_ID) == 0 and st.getQuestItemsCount(BRAKIS_REMAINS1_ID) == 0 and st.getQuestItemsCount(BRAKIS_REMAINS2_ID) == 0 and st.getQuestItemsCount(VENDETTA_TOTEM_ID)) == 0 :
        htmltext = "7630-01.htm"
   elif npcId == 7630 and int(st.get("cond"))==1 and st.getQuestItemsCount(ORIMS_CONTRACT_ID)==1 :
        if st.getQuestItemsCount(PORTAS_EYE_ID)+st.getQuestItemsCount(EXCUROS_SCALE_ID)+st.getQuestItemsCount(MORDEOS_TALON_ID)<30 :
          htmltext = "7630-05.htm"
        else:
          htmltext = "7630-06.htm"
          st.takeItems(ORIMS_CONTRACT_ID,st.getQuestItemsCount(ORIMS_CONTRACT_ID))
          st.takeItems(PORTAS_EYE_ID,st.getQuestItemsCount(PORTAS_EYE_ID))
          st.takeItems(EXCUROS_SCALE_ID,st.getQuestItemsCount(EXCUROS_SCALE_ID))
          st.takeItems(MORDEOS_TALON_ID,st.getQuestItemsCount(MORDEOS_TALON_ID))
          st.giveItems(BRAKIS_REMAINS1_ID,1)
   elif npcId == 7630 and int(st.get("cond"))==1 and st.getQuestItemsCount(ORIMS_CONTRACT_ID)==0 and st.getQuestItemsCount((BRAKIS_REMAINS1_ID) or st.getQuestItemsCount(BRAKIS_REMAINS2_ID) or st.getQuestItemsCount(VENDETTA_TOTEM_ID)) :
        htmltext = "7630-07.htm"
   elif npcId == 7682 and int(st.get("cond"))==1 and st.getQuestItemsCount((PEKIRONS_TOTEM_ID) == 0 and st.getQuestItemsCount(TONARS_REMAINS1_ID) == 0 and st.getQuestItemsCount(TONARS_REMAINS2_ID) == 0 and st.getQuestItemsCount(VENDETTA_TOTEM_ID)) == 0 :
        htmltext = "7682-01.htm"
   elif npcId == 7682 and int(st.get("cond"))==1 and st.getQuestItemsCount(PEKIRONS_TOTEM_ID)==1 :
        if st.getQuestItemsCount((TONARS_SKULL_ID) and st.getQuestItemsCount(TONARS_RIB_BONE_ID) and st.getQuestItemsCount(TONARS_SPINE_ID) and st.getQuestItemsCount(TONARS_ARM_BONE_ID) and st.getQuestItemsCount(TONARS_THIGH_BONE_ID)) :
          htmltext = "7682-04.htm"
          st.takeItems(PEKIRONS_TOTEM_ID,1)
          st.takeItems(TONARS_SKULL_ID,1)
          st.takeItems(TONARS_RIB_BONE_ID,1)
          st.takeItems(TONARS_SPINE_ID,1)
          st.takeItems(TONARS_ARM_BONE_ID,1)
          st.giveItems(TONARS_REMAINS1_ID,1)
          st.takeItems(TONARS_THIGH_BONE_ID,1)
        else:
          htmltext = "7682-03.htm"
   elif npcId == 7682 and int(st.get("cond"))==1 and st.getQuestItemsCount(PEKIRONS_TOTEM_ID)==0 and st.getQuestItemsCount((TONARS_REMAINS1_ID) or st.getQuestItemsCount(TONARS_REMAINS2_ID) or st.getQuestItemsCount(VENDETTA_TOTEM_ID)) :
        htmltext = "7682-05.htm"
   elif npcId == 7515 and int(st.get("cond"))==1 and st.getQuestItemsCount((MANAKIAS_TOTEM_ID) == 0 and st.getQuestItemsCount(HERMODTS_REMAINS2_ID) == 0 and st.getQuestItemsCount(VENDETTA_TOTEM_ID)) == 0 and st.getQuestItemsCount(HERMODTS_REMAINS1_ID)==0 :
        htmltext = "7515-01.htm"
   elif npcId == 7515 and int(st.get("cond"))==1 and st.getQuestItemsCount(MANAKIAS_TOTEM_ID)==1 :
        if st.getQuestItemsCount((HERMODTS_SKULL_ID) and st.getQuestItemsCount(HERMODTS_RIB_BONE_ID) and st.getQuestItemsCount(HERMODTS_SPINE_ID) and st.getQuestItemsCount(HERMODTS_ARM_BONE_ID) and st.getQuestItemsCount(HERMODTS_THIGH_BONE_ID)) :
          htmltext = "7515-04.htm"
          st.takeItems(MANAKIAS_TOTEM_ID,st.getQuestItemsCount(MANAKIAS_TOTEM_ID))
          st.takeItems(HERMODTS_SKULL_ID,st.getQuestItemsCount(HERMODTS_SKULL_ID))
          st.takeItems(HERMODTS_RIB_BONE_ID,st.getQuestItemsCount(HERMODTS_RIB_BONE_ID))
          st.takeItems(HERMODTS_SPINE_ID,st.getQuestItemsCount(HERMODTS_SPINE_ID))
          st.takeItems(HERMODTS_ARM_BONE_ID,st.getQuestItemsCount(HERMODTS_ARM_BONE_ID))
          st.giveItems(HERMODTS_REMAINS1_ID,1)
          st.takeItems(HERMODTS_THIGH_BONE_ID,1)
        else:
          htmltext = "7515-03.htm"
   elif npcId == 7515 and int(st.get("cond"))==1 and st.getQuestItemsCount(RACOYS_TOTEM_ID)==0 and st.getQuestItemsCount((KIRUNAS_REMAINS1_ID) or st.getQuestItemsCount(KIRUNAS_REMAINS2_ID) or st.getQuestItemsCount(VENDETTA_TOTEM_ID)) :
        htmltext = "7515-05.htm"
   elif npcId == 7507 and int(st.get("cond"))==1 and st.getQuestItemsCount((RACOYS_TOTEM_ID) == 0 and st.getQuestItemsCount(KIRUNAS_REMAINS1_ID) == 0 and st.getQuestItemsCount(KIRUNAS_REMAINS2_ID) == 0 and st.getQuestItemsCount(VENDETTA_TOTEM_ID)) == 0 :
        htmltext = "7507-01.htm"
   elif npcId == 7507 and int(st.get("cond"))==1 and st.getQuestItemsCount(RACOYS_TOTEM_ID)==1 and st.getQuestItemsCount((VIVIANTES_LETTER_ID) == 0 and st.getQuestItemsCount(INSECT_DIAGRAM_BOOK_ID)) == 0 :
        htmltext = "7507-03.htm"
   elif npcId == 7507 and int(st.get("cond"))==1 and st.getQuestItemsCount(INSECT_DIAGRAM_BOOK_ID)==0 and st.getQuestItemsCount((RACOYS_TOTEM_ID) and st.getQuestItemsCount(VIVIANTES_LETTER_ID)) :
        htmltext = "7507-04.htm"
   elif npcId == 7507 and int(st.get("cond"))==1 and st.getQuestItemsCount(VIVIANTES_LETTER_ID)==0 and st.getQuestItemsCount((RACOYS_TOTEM_ID) and st.getQuestItemsCount(INSECT_DIAGRAM_BOOK_ID)) :
        if st.getQuestItemsCount((KIRUNAS_SKULL_ID) and st.getQuestItemsCount(KIRUNAS_RIB_BONE_ID) and st.getQuestItemsCount(KIRUNAS_SPINE_ID) and st.getQuestItemsCount(KIRUNAS_ARM_BONE_ID) and st.getQuestItemsCount(KIRUNAS_THIGH_BONE_ID)) :
          htmltext = "7507-06.htm"
          st.takeItems(RACOYS_TOTEM_ID,1)
          st.takeItems(KIRUNAS_SKULL_ID,1)
          st.takeItems(KIRUNAS_RIB_BONE_ID,1)
          st.takeItems(KIRUNAS_SPINE_ID,1)
          st.takeItems(KIRUNAS_ARM_BONE_ID,1)
          st.takeItems(KIRUNAS_THIGH_BONE_ID,1)
          st.giveItems(KIRUNAS_REMAINS1_ID,1)
          st.takeItems(INSECT_DIAGRAM_BOOK_ID,1)
        else:
          htmltext = "7507-05.htm"
   elif npcId == 7507 and int(st.get("cond"))==1 and st.getQuestItemsCount(RACOYS_TOTEM_ID)==0 and st.getQuestItemsCount((KIRUNAS_REMAINS1_ID) or st.getQuestItemsCount(KIRUNAS_REMAINS2_ID) or st.getQuestItemsCount(VENDETTA_TOTEM_ID)) :
        htmltext = "7507-07.htm"
   elif npcId == 7030 and int(st.get("cond"))==1 and st.getQuestItemsCount(RACOYS_TOTEM_ID)==1 and st.getQuestItemsCount((VIVIANTES_LETTER_ID) == 0 and st.getQuestItemsCount(INSECT_DIAGRAM_BOOK_ID)) == 0 :
        htmltext = "7030-01.htm"
   elif npcId == 7030 and int(st.get("cond"))==1 and st.getQuestItemsCount(INSECT_DIAGRAM_BOOK_ID)==0 and st.getQuestItemsCount((RACOYS_TOTEM_ID) and st.getQuestItemsCount(VIVIANTES_LETTER_ID)) :
        htmltext = "7030-05.htm"
   elif npcId == 7030 and int(st.get("cond"))==1 and st.getQuestItemsCount(VIVIANTES_LETTER_ID)==0 and st.getQuestItemsCount((RACOYS_TOTEM_ID) and st.getQuestItemsCount(INSECT_DIAGRAM_BOOK_ID)) :
        htmltext = "7030-06.htm"
   elif npcId == 7030 and int(st.get("cond"))==1 and st.getQuestItemsCount(RACOYS_TOTEM_ID)==0 and st.getQuestItemsCount((KIRUNAS_REMAINS1_ID) or st.getQuestItemsCount(KIRUNAS_REMAINS2_ID) or st.getQuestItemsCount(VENDETTA_TOTEM_ID)) :
        htmltext = "7030-07.htm"
   elif npcId == 7436 and int(st.get("cond"))==1 and st.getQuestItemsCount(INSECT_DIAGRAM_BOOK_ID)==0 and st.getQuestItemsCount((RACOYS_TOTEM_ID) and st.getQuestItemsCount(VIVIANTES_LETTER_ID)) :
        htmltext = "7436-01.htm"
        st.giveItems(INSECT_DIAGRAM_BOOK_ID,1)
        st.takeItems(VIVIANTES_LETTER_ID,1)
   elif npcId == 7436 and int(st.get("cond"))==1 and st.getQuestItemsCount(VIVIANTES_LETTER_ID)==0 and st.getQuestItemsCount((RACOYS_TOTEM_ID) and st.getQuestItemsCount(INSECT_DIAGRAM_BOOK_ID)) :
        htmltext = "7436-02.htm"
   elif npcId == 7436 and int(st.get("cond"))==1 and st.getQuestItemsCount(RACOYS_TOTEM_ID)==0 and st.getQuestItemsCount((KIRUNAS_REMAINS1_ID) or st.getQuestItemsCount(KIRUNAS_REMAINS2_ID) or st.getQuestItemsCount(VENDETTA_TOTEM_ID)) :
        htmltext = "7436-03.htm"
   elif npcId == 7649 and int(st.get("cond"))==1 and st.getQuestItemsCount(WARSPIRIT_TOTEM_ID)==1 :
        htmltext = "7649-01.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 213 :
    if int(st.get("cond")) and st.getQuestItemsCount(ORIMS_CONTRACT_ID) == 1 and st.getQuestItemsCount(PORTAS_EYE_ID)<10 :
      st.giveItems(PORTAS_EYE_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 214 :
    if int(st.get("cond")) and st.getQuestItemsCount(ORIMS_CONTRACT_ID) == 1 and st.getQuestItemsCount(EXCUROS_SCALE_ID)<10 :
      st.giveItems(EXCUROS_SCALE_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 215 :
    if int(st.get("cond")) and st.getQuestItemsCount(ORIMS_CONTRACT_ID) == 1 and st.getQuestItemsCount(MORDEOS_TALON_ID)<10 :
      st.giveItems(MORDEOS_TALON_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 89 :
    if int(st.get("cond")) and st.getQuestItemsCount(RACOYS_TOTEM_ID) == 1 and st.getQuestItemsCount(INSECT_DIAGRAM_BOOK_ID) == 1 :
      n = st.getRandom(100)
      if n>70 :
                st.giveItems(KIRUNAS_THIGH_BONE_ID,1)
                st.giveItems(KIRUNAS_ARM_BONE_ID,1)
      elif n>40 :
                st.giveItems(KIRUNAS_SPINE_ID,1)
                st.giveItems(KIRUNAS_RIB_BONE_ID,1)
      elif n>10 :
        if st.getQuestItemsCount(KIRUNAS_SKULL_ID) == 0 :
          st.giveItems(KIRUNAS_SKULL_ID,1)
          st.playSound("ItemSound.quest_middle")
   elif npcId == 90 :
    if int(st.get("cond")) and st.getQuestItemsCount(RACOYS_TOTEM_ID) == 1 and st.getQuestItemsCount(INSECT_DIAGRAM_BOOK_ID) == 1 :
      n = st.getRandom(100)
      if n>70 :
                st.giveItems(KIRUNAS_THIGH_BONE_ID,1)
                st.giveItems(KIRUNAS_ARM_BONE_ID,1)
      elif n>40 :
                st.giveItems(KIRUNAS_SPINE_ID,1)
                st.giveItems(KIRUNAS_RIB_BONE_ID,1)
      elif n>10 :
        if st.getQuestItemsCount(KIRUNAS_SKULL_ID) == 0 :
          st.giveItems(KIRUNAS_SKULL_ID,1)
          st.playSound("ItemSound.quest_middle")
   elif npcId == 581 :
    if int(st.get("cond")) and st.getQuestItemsCount(PEKIRONS_TOTEM_ID) == 1 :
      n = st.getRandom(100)
      if n>50 :
        if st.getQuestItemsCount(TONARS_SKULL_ID) == 0 :
          st.giveItems(TONARS_SKULL_ID,1)
          st.playSound("ItemSound.quest_middle")
        elif st.getQuestItemsCount(TONARS_RIB_BONE_ID) == 0 :
          st.giveItems(TONARS_RIB_BONE_ID,1)
          st.playSound("ItemSound.quest_middle")
        elif st.getQuestItemsCount(TONARS_SPINE_ID) == 0 :
          st.giveItems(TONARS_SPINE_ID,1)
          st.playSound("ItemSound.quest_middle")
        elif st.getQuestItemsCount(TONARS_ARM_BONE_ID) == 0 :
          st.giveItems(TONARS_ARM_BONE_ID,1)
          st.playSound("ItemSound.quest_middle")
        elif st.getQuestItemsCount(TONARS_THIGH_BONE_ID) == 0 :
          st.giveItems(TONARS_THIGH_BONE_ID,1)
          st.playSound("ItemSound.quest_middle")
   elif npcId == 582 :
    if int(st.get("cond")) and st.getQuestItemsCount(PEKIRONS_TOTEM_ID) == 1 :
      n = st.getRandom(100)
      if n>50 :
        if st.getQuestItemsCount(TONARS_SKULL_ID) == 0 :
          st.giveItems(TONARS_SKULL_ID,1)
          st.playSound("ItemSound.quest_middle")
        elif st.getQuestItemsCount(TONARS_RIB_BONE_ID) == 0 :
          st.giveItems(TONARS_RIB_BONE_ID,1)
          st.playSound("ItemSound.quest_middle")
        elif st.getQuestItemsCount(TONARS_SPINE_ID) == 0 :
          st.giveItems(TONARS_SPINE_ID,1)
          st.playSound("ItemSound.quest_middle")
        elif st.getQuestItemsCount(TONARS_ARM_BONE_ID) == 0 :
          st.giveItems(TONARS_ARM_BONE_ID,1)
          st.playSound("ItemSound.quest_middle")
        elif st.getQuestItemsCount(TONARS_THIGH_BONE_ID) == 0 :
          st.giveItems(TONARS_THIGH_BONE_ID,1)
          st.playSound("ItemSound.quest_middle")
   elif npcId == 601 :
    if int(st.get("cond")) and st.getQuestItemsCount(VENDETTA_TOTEM_ID) == 1 and st.getQuestItemsCount(TAMLIN_ORC_HEAD_ID)<13 :
      if st.getRandom(100) < 50 :
        st.giveItems(TAMLIN_ORC_HEAD_ID,1)
   elif npcId == 602 :
    if int(st.get("cond")) and st.getQuestItemsCount(VENDETTA_TOTEM_ID) == 1 and st.getQuestItemsCount(TAMLIN_ORC_HEAD_ID)<13 :
      if st.getRandom(100) < 50 :
        st.giveItems(TAMLIN_ORC_HEAD_ID,1)
   elif npcId == 5108 :
    if int(st.get("cond")) and st.getQuestItemsCount(MANAKIAS_TOTEM_ID) == 1 and st.getQuestItemsCount(HERMODTS_SKULL_ID) == 0 :
      st.giveItems(HERMODTS_SKULL_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 158 :
    if int(st.get("cond")) and st.getQuestItemsCount(MANAKIAS_TOTEM_ID) == 1 :
      n = st.getRandom(100)
      if n>50 :
        if st.getQuestItemsCount(HERMODTS_RIB_BONE_ID) == 0 :
          st.giveItems(HERMODTS_RIB_BONE_ID,1)
          st.playSound("ItemSound.quest_middle")
        elif st.getQuestItemsCount(HERMODTS_SPINE_ID) == 0 :
          st.giveItems(HERMODTS_SPINE_ID,1)
          st.playSound("ItemSound.quest_middle")
        elif st.getQuestItemsCount(HERMODTS_ARM_BONE_ID) == 0 :
          st.giveItems(HERMODTS_ARM_BONE_ID,1)
          st.playSound("ItemSound.quest_middle")
        elif st.getQuestItemsCount(HERMODTS_THIGH_BONE_ID) == 0 :
          st.giveItems(HERMODTS_THIGH_BONE_ID,1)
          st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(233,"233_TestOfWarspirit","Test Of Warspirit")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7510)

STARTED.addTalkId(7030)
STARTED.addTalkId(7436)
STARTED.addTalkId(7507)
STARTED.addTalkId(7510)
STARTED.addTalkId(7515)
STARTED.addTalkId(7630)
STARTED.addTalkId(7649)
STARTED.addTalkId(7682)

STARTED.addKillId(158)
STARTED.addKillId(213)
STARTED.addKillId(214)
STARTED.addKillId(215)
STARTED.addKillId(5108)
STARTED.addKillId(581)
STARTED.addKillId(582)
STARTED.addKillId(601)
STARTED.addKillId(602)
STARTED.addKillId(89)
STARTED.addKillId(90)

STARTED.addQuestDrop(7630,BRAKIS_REMAINS1_ID,1)
STARTED.addQuestDrop(7515,HERMODTS_REMAINS1_ID,1)
STARTED.addQuestDrop(7507,KIRUNAS_REMAINS1_ID,1)
STARTED.addQuestDrop(7682,TONARS_REMAINS1_ID,1)
STARTED.addQuestDrop(7510,VENDETTA_TOTEM_ID,1)
STARTED.addQuestDrop(7630,ORIMS_CONTRACT_ID,1)
STARTED.addQuestDrop(213,PORTAS_EYE_ID,1)
STARTED.addQuestDrop(214,EXCUROS_SCALE_ID,1)
STARTED.addQuestDrop(215,MORDEOS_TALON_ID,1)
STARTED.addQuestDrop(7682,PEKIRONS_TOTEM_ID,1)
STARTED.addQuestDrop(581,TONARS_SKULL_ID,1)
STARTED.addQuestDrop(582,TONARS_SKULL_ID,1)
STARTED.addQuestDrop(581,TONARS_RIB_BONE_ID,1)
STARTED.addQuestDrop(582,TONARS_RIB_BONE_ID,1)
STARTED.addQuestDrop(581,TONARS_SPINE_ID,1)
STARTED.addQuestDrop(582,TONARS_SPINE_ID,1)
STARTED.addQuestDrop(581,TONARS_ARM_BONE_ID,1)
STARTED.addQuestDrop(582,TONARS_ARM_BONE_ID,1)
STARTED.addQuestDrop(581,TONARS_THIGH_BONE_ID,1)
STARTED.addQuestDrop(582,TONARS_THIGH_BONE_ID,1)
STARTED.addQuestDrop(7515,MANAKIAS_TOTEM_ID,1)
STARTED.addQuestDrop(5108,HERMODTS_SKULL_ID,1)
STARTED.addQuestDrop(158,HERMODTS_RIB_BONE_ID,1)
STARTED.addQuestDrop(158,HERMODTS_SPINE_ID,1)
STARTED.addQuestDrop(158,HERMODTS_ARM_BONE_ID,1)
STARTED.addQuestDrop(158,HERMODTS_THIGH_BONE_ID,1)
STARTED.addQuestDrop(7507,RACOYS_TOTEM_ID,1)
STARTED.addQuestDrop(89,KIRUNAS_SKULL_ID,1)
STARTED.addQuestDrop(90,KIRUNAS_SKULL_ID,1)
STARTED.addQuestDrop(89,KIRUNAS_RIB_BONE_ID,1)
STARTED.addQuestDrop(90,KIRUNAS_RIB_BONE_ID,1)
STARTED.addQuestDrop(89,KIRUNAS_SPINE_ID,1)
STARTED.addQuestDrop(90,KIRUNAS_SPINE_ID,1)
STARTED.addQuestDrop(89,KIRUNAS_ARM_BONE_ID,1)
STARTED.addQuestDrop(90,KIRUNAS_ARM_BONE_ID,1)
STARTED.addQuestDrop(89,KIRUNAS_THIGH_BONE_ID,1)
STARTED.addQuestDrop(90,KIRUNAS_THIGH_BONE_ID,1)
STARTED.addQuestDrop(7436,INSECT_DIAGRAM_BOOK_ID,1)
STARTED.addQuestDrop(7030,VIVIANTES_LETTER_ID,1)
STARTED.addQuestDrop(7510,WARSPIRIT_TOTEM_ID,1)
STARTED.addQuestDrop(7510,BRAKIS_REMAINS2_ID,1)
STARTED.addQuestDrop(7510,HERMODTS_REMAINS2_ID,1)
STARTED.addQuestDrop(7510,KIRUNAS_REMAINS2_ID,1)
STARTED.addQuestDrop(601,TAMLIN_ORC_HEAD_ID,1)
STARTED.addQuestDrop(602,TAMLIN_ORC_HEAD_ID,1)
STARTED.addQuestDrop(7510,TONARS_REMAINS2_ID,1)
