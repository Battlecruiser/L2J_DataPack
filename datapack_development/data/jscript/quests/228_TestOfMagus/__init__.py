# Maked by Mr. Have fun! Version 0.2
print "importing quests: 228: Test Of Magus"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_MAGUS_ID = 2840
RUKALS_LETTER_ID = 2841
PARINAS_LETTER_ID = 2842
LILAC_CHARM_ID = 2843
GOLDEN_SEED1_ID = 2844
GOLDEN_SEED2_ID = 2845
GOLDEN_SEED3_ID = 2846
SCORE_OF_ELEMENTS_ID = 2847
TONE_OF_WATER_ID = 2856
TONE_OF_FIRE_ID = 2857
TONE_OF_WIND_ID = 2858
TONE_OF_EARTH_ID = 2859
UNDINE_CHARM_ID = 2862
DAZZLING_DROP_ID = 2848
SALAMANDER_CHARM_ID = 2860
FLAME_CRYSTAL_ID = 2849
SYLPH_CHARM_ID = 2861
HARPYS_FEATHER_ID = 2850
WYRMS_WINGBONE_ID = 2851
WINDSUS_MANE_ID = 2852
SERPENT_CHARM_ID = 2863
EN_MONSTEREYE_SHELL_ID = 2853
EN_STONEGOLEM_POWDER_ID = 2854
EN_IRONGOLEM_SCRAP_ID = 2855

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        htmlfile = "7629-04.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.giveItems(RUKALS_LETTER_ID)
    elif event == "7629_1" :
          htmltext = "7629-09.htm"
    elif event == "7629_2" :
          htmltext = "7629-10.htm"
          st.takeItems(LILAC_CHARM_ID,1)
          st.takeItems(GOLDEN_SEED1_ID,1)
          st.takeItems(GOLDEN_SEED2_ID,1)
          st.giveItems(SCORE_OF_ELEMENTS_ID,1)
          st.takeItems(GOLDEN_SEED3_ID,1)
    elif event == "7391_1" :
          htmltext = "7391-02.htm"
          st.giveItems(PARINAS_LETTER_ID,1)
          st.takeItems(RUKALS_LETTER_ID,1)
    elif event == "7612_1" :
          htmltext = "7612-02.htm"
          st.giveItems(LILAC_CHARM_ID,1)
          st.takeItems(PARINAS_LETTER_ID,1)
    elif event == "7412_1" :
          htmltext = "7412-02.htm"
          st.giveItems(SYLPH_CHARM_ID,1)
    elif event == "7409_1" :
          htmltext = "7409-02.htm"
    elif event == "7409_2" :
          htmltext = "7409-03.htm"
          st.giveItems(SERPENT_CHARM_ID,1)
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7629 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if int(st.get("cond")) < 15 :
          if (st.getPlayer().getClassId().getId() == 0x0b or st.getPlayer().getClassId().getId() == 0x1a or st.getPlayer().getClassId().getId() == 0x27) :
            if st.getPlayer().getLevel() < 39 :
              htmltext = "7629-02.htm"
            else:
              htmltext = "7629-03.htm"
              st.set("cond","1")
              return htmltext
          else:
            htmltext = "7629-01.htm"
        else:
          htmltext = "7629-01.htm"
   elif npcId == 7629 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7629 and int(st.get("cond"))==1 and st.getQuestItemsCount(RUKALS_LETTER_ID)==1 :
        htmltext = "7629-05.htm"
   elif npcId == 7629 and int(st.get("cond"))==1 and st.getQuestItemsCount(PARINAS_LETTER_ID)==1 :
        htmltext = "7629-06.htm"
   elif npcId == 7629 and int(st.get("cond"))==1 and st.getQuestItemsCount(LILAC_CHARM_ID)==1 :
        if st.getQuestItemsCount((GOLDEN_SEED1_ID) and st.getQuestItemsCount(GOLDEN_SEED2_ID) and st.getQuestItemsCount(GOLDEN_SEED3_ID)) :
          htmltext = "7629-08.htm"
        else:
          htmltext = "7629-07.htm"
   elif npcId == 7629 and int(st.get("cond"))==1 and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 :
        if st.getQuestItemsCount((TONE_OF_WATER_ID) and st.getQuestItemsCount(TONE_OF_FIRE_ID) and st.getQuestItemsCount(TONE_OF_WIND_ID) and st.getQuestItemsCount(TONE_OF_EARTH_ID)) :
          if st.getGameTicks() != int(st.get("id")) :
            st.set("id",str(st.getGameTicks()))
            st.takeItems(SCORE_OF_ELEMENTS_ID,1)
            st.takeItems(TONE_OF_WATER_ID,1)
            st.takeItems(TONE_OF_FIRE_ID,1)
            st.takeItems(TONE_OF_WIND_ID,1)
            st.takeItems(TONE_OF_EARTH_ID,1)
            st.giveItems(MARK_OF_MAGUS_ID,1)
            st.addExpAndSp(50000,0)
            st.addExpAndSp(0,6400)
            htmlfile = "7629-12.htm"
            st.set("cond","0")
            st.set("onlyone","1")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
        else:
          htmltext = "7629-11.htm"
   elif npcId == 7391 and int(st.get("cond"))==1 and st.getQuestItemsCount(RUKALS_LETTER_ID)==1 :
        htmltext = "7391-01.htm"
   elif npcId == 7391 and int(st.get("cond"))==1 and st.getQuestItemsCount(PARINAS_LETTER_ID)==1 :
        htmltext = "7391-03.htm"
   elif npcId == 7391 and int(st.get("cond"))==1 and st.getQuestItemsCount(LILAC_CHARM_ID)==1 :
        htmltext = "7391-04.htm"
   elif npcId == 7391 and int(st.get("cond"))==1 and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 :
        htmltext = "7391-05.htm"
   elif npcId == 7612 and int(st.get("cond"))==1 and st.getQuestItemsCount(PARINAS_LETTER_ID)==1 :
        htmltext = "7612-01.htm"
   elif npcId == 7612 and int(st.get("cond"))==1 and st.getQuestItemsCount(LILAC_CHARM_ID)==1 :
        if st.getQuestItemsCount((GOLDEN_SEED1_ID) and st.getQuestItemsCount(GOLDEN_SEED2_ID) and st.getQuestItemsCount(GOLDEN_SEED3_ID)) :
          htmltext = "7612-04.htm"
        else:
          htmltext = "7612-03.htm"
   elif npcId == 7612 and int(st.get("cond"))==1 and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 :
        htmltext = "7612-05.htm"
   elif npcId == 7413 and int(st.get("cond"))==1 and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(TONE_OF_WATER_ID)==0 and st.getQuestItemsCount(UNDINE_CHARM_ID)==0 :
        htmltext = "7413-01.htm"
        st.giveItems(UNDINE_CHARM_ID,1)
   elif npcId == 7413 and int(st.get("cond"))==1 and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(UNDINE_CHARM_ID)==1 :
        if st.getQuestItemsCount(DAZZLING_DROP_ID) < 20 :
          htmltext = "7413-02.htm"
        else:
          htmltext = "7413-03.htm"
          st.takeItems(DAZZLING_DROP_ID,st.getQuestItemsCount(DAZZLING_DROP_ID))
          st.giveItems(TONE_OF_WATER_ID,1)
          st.takeItems(UNDINE_CHARM_ID,1)
   elif npcId == 7413 and int(st.get("cond"))==1 and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(TONE_OF_WATER_ID)==1 and st.getQuestItemsCount(UNDINE_CHARM_ID)==0 :
        htmltext = "7413-04.htm"
   elif npcId == 7411 and int(st.get("cond"))==1 and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(TONE_OF_FIRE_ID)==0 and st.getQuestItemsCount(SALAMANDER_CHARM_ID)==0 :
        htmltext = "7411-01.htm"
        st.giveItems(SALAMANDER_CHARM_ID,1)
   elif npcId == 7411 and int(st.get("cond"))==1 and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(SALAMANDER_CHARM_ID)==1 :
        if st.getQuestItemsCount(FLAME_CRYSTAL_ID) < 5 :
          htmltext = "7411-02.htm"
        else:
          htmltext = "7411-03.htm"
          st.takeItems(FLAME_CRYSTAL_ID,st.getQuestItemsCount(FLAME_CRYSTAL_ID))
          st.giveItems(TONE_OF_FIRE_ID,1)
          st.takeItems(SALAMANDER_CHARM_ID,1)
   elif npcId == 7411 and int(st.get("cond"))==1 and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(TONE_OF_FIRE_ID)==1 and st.getQuestItemsCount(SALAMANDER_CHARM_ID)==0 :
        htmltext = "7411-04.htm"
   elif npcId == 7412 and int(st.get("cond"))==1 and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(TONE_OF_WIND_ID)==0 and st.getQuestItemsCount(SYLPH_CHARM_ID)==0 :
        htmltext = "7412-01.htm"
   elif npcId == 7412 and int(st.get("cond"))==1 and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(SYLPH_CHARM_ID)==1 :
        if st.getQuestItemsCount(HARPYS_FEATHER_ID)+st.getQuestItemsCount(WYRMS_WINGBONE_ID)+st.getQuestItemsCount(WINDSUS_MANE_ID) < 40 :
          htmltext = "7412-03.htm"
        else:
          htmltext = "7412-04.htm"
          st.takeItems(HARPYS_FEATHER_ID,st.getQuestItemsCount(HARPYS_FEATHER_ID))
          st.takeItems(WYRMS_WINGBONE_ID,st.getQuestItemsCount(WYRMS_WINGBONE_ID))
          st.takeItems(WINDSUS_MANE_ID,st.getQuestItemsCount(WINDSUS_MANE_ID))
          st.giveItems(TONE_OF_WIND_ID,1)
          st.takeItems(SYLPH_CHARM_ID,1)
   elif npcId == 7412 and int(st.get("cond"))==1 and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(TONE_OF_WIND_ID)==1 and st.getQuestItemsCount(SYLPH_CHARM_ID)==0 :
        htmltext = "7412-05.htm"
   elif npcId == 7409 and int(st.get("cond"))==1 and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(TONE_OF_EARTH_ID)==0 and st.getQuestItemsCount(SERPENT_CHARM_ID)==0 :
        htmltext = "7409-01.htm"
   elif npcId == 7409 and int(st.get("cond"))==1 and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(SERPENT_CHARM_ID)==1 :
        if st.getQuestItemsCount(EN_MONSTEREYE_SHELL_ID)+st.getQuestItemsCount(EN_STONEGOLEM_POWDER_ID)+st.getQuestItemsCount(EN_IRONGOLEM_SCRAP_ID) < 30 :
          htmltext = "7409-04.htm"
        else:
          htmltext = "7409-05.htm"
          st.takeItems(EN_MONSTEREYE_SHELL_ID,st.getQuestItemsCount(EN_MONSTEREYE_SHELL_ID))
          st.takeItems(EN_STONEGOLEM_POWDER_ID,st.getQuestItemsCount(EN_STONEGOLEM_POWDER_ID))
          st.takeItems(EN_IRONGOLEM_SCRAP_ID,st.getQuestItemsCount(EN_IRONGOLEM_SCRAP_ID))
          st.giveItems(TONE_OF_EARTH_ID,1)
          st.takeItems(SERPENT_CHARM_ID,1)
   elif npcId == 7409 and int(st.get("cond"))==1 and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(TONE_OF_EARTH_ID)==1 and st.getQuestItemsCount(SERPENT_CHARM_ID)==0 :
        htmltext = "7409-06.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 5095 :
    if int(st.get("cond")) and st.getQuestItemsCount(LILAC_CHARM_ID) == 1 and st.getQuestItemsCount(GOLDEN_SEED1_ID) == 0 :
      st.giveItems(GOLDEN_SEED1_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 5096 :
    if int(st.get("cond")) and st.getQuestItemsCount(LILAC_CHARM_ID) == 1 and st.getQuestItemsCount(GOLDEN_SEED2_ID) == 0 :
      st.giveItems(GOLDEN_SEED2_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 5097 :
    if int(st.get("cond")) and st.getQuestItemsCount(LILAC_CHARM_ID) == 1 and st.getQuestItemsCount(GOLDEN_SEED3_ID) == 0 :
      st.giveItems(GOLDEN_SEED3_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 230 or npcId == 231 or npcId == 157 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID) == 1 and st.getQuestItemsCount(UNDINE_CHARM_ID) == 1  and st.getQuestItemsCount(DAZZLING_DROP_ID) < 20 :
     if st.getRandom(100)<30 :
      st.giveItems(DAZZLING_DROP_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 232 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID) == 1 and st.getQuestItemsCount(UNDINE_CHARM_ID) == 1  and st.getQuestItemsCount(DAZZLING_DROP_ID) < 20 :
     if st.getRandom(100)<40 :
      st.giveItems(DAZZLING_DROP_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 234 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID) == 1 and st.getQuestItemsCount(UNDINE_CHARM_ID) == 1  and st.getQuestItemsCount(DAZZLING_DROP_ID) < 20 :
     if st.getRandom(100)<50 :
      st.giveItems(DAZZLING_DROP_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 145 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID) == 1 and st.getQuestItemsCount(SYLPH_CHARM_ID) == 1  and st.getQuestItemsCount(HARPYS_FEATHER_ID) < 20 :
      st.giveItems(HARPYS_FEATHER_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 176 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID) == 1 and st.getQuestItemsCount(SYLPH_CHARM_ID) == 1  and st.getQuestItemsCount(WYRMS_WINGBONE_ID) < 10 :
     if st.getRandom(100)<50 :
      st.giveItems(WYRMS_WINGBONE_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 553 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID) == 1 and st.getQuestItemsCount(SYLPH_CHARM_ID) == 1  and st.getQuestItemsCount(WINDSUS_MANE_ID) < 10 :
     if st.getRandom(100)<50 :
      st.giveItems(WINDSUS_MANE_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 564 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID) == 1 and st.getQuestItemsCount(SERPENT_CHARM_ID) == 1  and st.getQuestItemsCount(EN_MONSTEREYE_SHELL_ID) < 10 :
      st.giveItems(EN_MONSTEREYE_SHELL_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 565 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID) == 1 and st.getQuestItemsCount(SERPENT_CHARM_ID) == 1  and st.getQuestItemsCount(EN_STONEGOLEM_POWDER_ID) < 10 :
      st.giveItems(EN_STONEGOLEM_POWDER_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 566 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID) == 1 and st.getQuestItemsCount(SERPENT_CHARM_ID) == 1  and st.getQuestItemsCount(EN_IRONGOLEM_SCRAP_ID) < 10 :
      st.giveItems(EN_IRONGOLEM_SCRAP_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 5098 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID) == 1 and st.getQuestItemsCount(SALAMANDER_CHARM_ID) == 1  and st.getQuestItemsCount(FLAME_CRYSTAL_ID) < 5 :
     if st.getRandom(100)<50 :
      st.giveItems(FLAME_CRYSTAL_ID,1)
      st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(228,"228_TestOfMagus","Test Of Magus")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7629)

STARTED.addTalkId(7391)
STARTED.addTalkId(7409)
STARTED.addTalkId(7411)
STARTED.addTalkId(7412)
STARTED.addTalkId(7413)
STARTED.addTalkId(7612)
STARTED.addTalkId(7629)

STARTED.addKillId(145)
STARTED.addKillId(157)
STARTED.addKillId(176)
STARTED.addKillId(230)
STARTED.addKillId(231)
STARTED.addKillId(232)
STARTED.addKillId(234)
STARTED.addKillId(5095)
STARTED.addKillId(5096)
STARTED.addKillId(5097)
STARTED.addKillId(5098)
STARTED.addKillId(553)
STARTED.addKillId(564)
STARTED.addKillId(565)
STARTED.addKillId(566)

STARTED.addQuestDrop(7612,LILAC_CHARM_ID,1)
STARTED.addQuestDrop(5095,GOLDEN_SEED1_ID,1)
STARTED.addQuestDrop(5096,GOLDEN_SEED2_ID,1)
STARTED.addQuestDrop(5097,GOLDEN_SEED3_ID,1)
STARTED.addQuestDrop(7629,SCORE_OF_ELEMENTS_ID,1)
STARTED.addQuestDrop(7413,TONE_OF_WATER_ID,1)
STARTED.addQuestDrop(7411,TONE_OF_FIRE_ID,1)
STARTED.addQuestDrop(7412,TONE_OF_WIND_ID,1)
STARTED.addQuestDrop(7409,TONE_OF_EARTH_ID,1)
STARTED.addQuestDrop(7629,RUKALS_LETTER_ID,1)
STARTED.addQuestDrop(7391,PARINAS_LETTER_ID,1)
STARTED.addQuestDrop(7413,UNDINE_CHARM_ID,1)
STARTED.addQuestDrop(7411,SALAMANDER_CHARM_ID,1)
STARTED.addQuestDrop(7412,SYLPH_CHARM_ID,1)
STARTED.addQuestDrop(7409,SERPENT_CHARM_ID,1)
