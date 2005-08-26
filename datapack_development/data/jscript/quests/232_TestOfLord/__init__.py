# Maked by Mr. Have fun! Version 0.2
print "importing quests: 232: Test Of Lord"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_LORD_ID = 3390
ORDEAL_NECKLACE_ID = 3391
VARKEES_CHARM_ID = 3392
TANTUS_CHARM_ID = 3393
HATOS_CHARM_ID = 3394
TAKUNA_CHARM_ID = 3395
CHIANTA_CHARM_ID = 3396
MANAKIAS_ORDERS_ID = 3397
BREKA_ORC_FANG_ID = 3398
MANAKIAS_AMULET_ID = 3399
HUGE_ORC_FANG_ID = 3400
SUMARIS_LETTER_ID = 3401
URUTU_BLADE_ID = 3402
TIMAK_ORC_SKULL_ID = 3403
SWORD_INTO_SKULL_ID = 3404
NERUGA_AXE_BLADE_ID = 3405
AXE_OF_CEREMONY_ID = 3406
MARSH_SPIDER_FEELER_ID = 3407
MARSH_SPIDER_FEET_ID = 3408
HANDIWORK_SPIDER_BROOCH_ID = 3409
CORNEA_OF_EN_MONSTEREYE_ID = 3410
MONSTEREYE_WOODCARVING_ID = 3411
BEAR_FANG_NECKLACE_ID = 3412
MARTANKUS_CHARM_ID = 3413
RAGNA_ORC_HEAD_ID = 3414
RAGNA_CHIEF_NOTICE_ID = 3415
IMMORTAL_FLAME_ID = 3416
BONE_ARROW_ID = 1341
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        htmlfile = "7565-05.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.giveItems(ORDEAL_NECKLACE_ID)
    elif event == "7565_1" :
          htmltext = "7565-08.htm"
          st.takeItems(SWORD_INTO_SKULL_ID,1)
          st.takeItems(AXE_OF_CEREMONY_ID,1)
          st.takeItems(MONSTEREYE_WOODCARVING_ID,1)
          st.takeItems(HANDIWORK_SPIDER_BROOCH_ID,1)
          st.takeItems(ORDEAL_NECKLACE_ID,1)
          st.giveItems(BEAR_FANG_NECKLACE_ID,1)
          st.takeItems(HUGE_ORC_FANG_ID,1)
    elif event == "7566_1" :
          htmltext = "7566-02.htm"
          st.giveItems(VARKEES_CHARM_ID,1)
    elif event == "7567_1" :
          htmltext = "7567-02.htm"
          st.giveItems(TANTUS_CHARM_ID,1)
    elif event == "7558_1" :
          htmltext = "7558-02.htm"
          st.giveItems(NERUGA_AXE_BLADE_ID,1)
          st.takeItems(ADENA_ID,1000)
    elif event == "7568_1" :
          htmltext = "7568-02.htm"
          st.giveItems(HATOS_CHARM_ID,1)
    elif event == "7641_1" :
          htmltext = "7641-02.htm"
          st.giveItems(TAKUNA_CHARM_ID,1)
    elif event == "7642_1" :
          htmltext = "7642-02.htm"
          st.giveItems(CHIANTA_CHARM_ID,1)
    elif event == "7649_1" :
          htmltext = "7649-02.htm"
    elif event == "7649_2" :
          htmltext = "7649-03.htm"
    elif event == "7649_3" :
          htmltext = "7649-04.htm"
          st.giveItems(MARTANKUS_CHARM_ID,1)
          st.takeItems(BEAR_FANG_NECKLACE_ID,1)
    elif event == "7649_4" :
          htmltext = "7649-07.htm"
          if Maker_GetNpcCount() == 1 :
            st.spawnNpc(7643,21036,-107690,-3038)
    elif event == "7643_1" :
          htmltext = "7643-02.htm"
    elif event == "7643_2" :
          htmltext = "7643-03.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7565 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if int(st.get("cond")) < 15 :
          if st.getPlayer().getRace().ordinal() != 3 :
            htmltext = "7565-01.htm"
          else:
            if st.getPlayer().getClassId().getId() != 0x32 :
              htmltext = "7565-02.htm"
            else:
              if st.getPlayer().getLevel() < 39 :
                htmltext = "7565-03.htm"
              else:
                htmltext = "7565-04.htm"
                st.set("cond","1")
                return htmltext
        else:
          htmltext = "7565-04.htm"
   elif npcId == 7565 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7565 and int(st.get("cond"))==1 and st.getQuestItemsCount(ORDEAL_NECKLACE_ID)==1 :
        if st.getQuestItemsCount((HUGE_ORC_FANG_ID) and st.getQuestItemsCount(SWORD_INTO_SKULL_ID)) and st.getQuestItemsCount((AXE_OF_CEREMONY_ID) and st.getQuestItemsCount(MONSTEREYE_WOODCARVING_ID) and st.getQuestItemsCount(HANDIWORK_SPIDER_BROOCH_ID)) :
          htmltext = "7565-07.htm"
        else:
          htmltext = "7565-06.htm"
   elif npcId == 7565 and int(st.get("cond"))==1 and st.getQuestItemsCount(BEAR_FANG_NECKLACE_ID)==1 :
        htmltext = "7565-09.htm"
   elif npcId == 7565 and int(st.get("cond"))==1 and st.getQuestItemsCount(MARTANKUS_CHARM_ID)==1 :
        htmltext = "7565-10.htm"
   elif npcId == 7565 and int(st.get("cond"))==1 and st.getQuestItemsCount(IMMORTAL_FLAME_ID)==1 :
        if st.getGameTicks() != int(st.get("id")) :
          st.set("id",str(st.getGameTicks()))
          st.getPlayer().addExpAndSp(25000,0)
          st.getPlayer().addExpAndSp(0,3000)
          st.giveItems(MARK_OF_LORD_ID,1)
          st.takeItems(IMMORTAL_FLAME_ID,1)
          htmlfile = "7565-11.htm"
          st.set("cond","0")
          st.set("onlyone","1")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
   elif npcId == 7566 and int(st.get("cond"))==1 and st.getQuestItemsCount(ORDEAL_NECKLACE_ID)==1 and st.getQuestItemsCount((HUGE_ORC_FANG_ID) == 0 and st.getQuestItemsCount(VARKEES_CHARM_ID)) == 0 :
        htmltext = "7566-01.htm"
   elif npcId == 7566 and int(st.get("cond"))==1 and st.getQuestItemsCount((HUGE_ORC_FANG_ID) == 0 and st.getQuestItemsCount(MANAKIAS_AMULET_ID)) == 0 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(VARKEES_CHARM_ID)) :
        htmltext = "7566-03.htm"
   elif npcId == 7566 and int(st.get("cond"))==1 and st.getQuestItemsCount(HUGE_ORC_FANG_ID)==0 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(VARKEES_CHARM_ID) and st.getQuestItemsCount(MANAKIAS_AMULET_ID)) :
        htmltext = "7566-04.htm"
        st.takeItems(VARKEES_CHARM_ID,1)
        st.giveItems(HUGE_ORC_FANG_ID,1)
        st.takeItems(MANAKIAS_AMULET_ID,1)
   elif npcId == 7566 and int(st.get("cond"))==1 and st.getQuestItemsCount(VARKEES_CHARM_ID)==0 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(HUGE_ORC_FANG_ID)) :
        htmltext = "7566-05.htm"
   elif npcId == 7515 and int(st.get("cond"))==1 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(VARKEES_CHARM_ID)) and st.getQuestItemsCount((HUGE_ORC_FANG_ID) == 0 and st.getQuestItemsCount(MANAKIAS_AMULET_ID) == 0 and st.getQuestItemsCount(MANAKIAS_ORDERS_ID)) == 0 :
        htmltext = "7515-01.htm"
        st.giveItems(MANAKIAS_ORDERS_ID,1)
   elif npcId == 7515 and int(st.get("cond"))==1 and st.getQuestItemsCount((VARKEES_CHARM_ID) and st.getQuestItemsCount(ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(MANAKIAS_ORDERS_ID)) and st.getQuestItemsCount((HUGE_ORC_FANG_ID) == 0 and st.getQuestItemsCount(MANAKIAS_AMULET_ID)) == 0 :
        if st.getQuestItemsCount(BREKA_ORC_FANG_ID) < 20 :
          htmltext = "7515-02.htm"
        else:
          htmltext = "7515-03.htm"
          st.giveItems(MANAKIAS_AMULET_ID,1)
          st.takeItems(MANAKIAS_ORDERS_ID,1)
          st.takeItems(BREKA_ORC_FANG_ID,st.getQuestItemsCount(BREKA_ORC_FANG_ID))
   elif npcId == 7515 and int(st.get("cond"))==1 and st.getQuestItemsCount((HUGE_ORC_FANG_ID) == 0 and st.getQuestItemsCount(MANAKIAS_ORDERS_ID)) == 0 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(VARKEES_CHARM_ID) and st.getQuestItemsCount(MANAKIAS_AMULET_ID)) :
        htmltext = "7515-04.htm"
   elif npcId == 7515 and int(st.get("cond"))==1 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(HUGE_ORC_FANG_ID)) and st.getQuestItemsCount((VARKEES_CHARM_ID) == 0 and st.getQuestItemsCount(MANAKIAS_AMULET_ID) == 0 and st.getQuestItemsCount(MANAKIAS_ORDERS_ID)) == 0 :
        htmltext = "7515-05.htm"
   elif npcId == 7567 and int(st.get("cond"))==1 and st.getQuestItemsCount(ORDEAL_NECKLACE_ID)==1 and st.getQuestItemsCount((AXE_OF_CEREMONY_ID) == 0 and st.getQuestItemsCount(TANTUS_CHARM_ID)) == 0 :
        htmltext = "7567-01.htm"
   elif npcId == 7567 and int(st.get("cond"))==1 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(TANTUS_CHARM_ID)) and st.getQuestItemsCount(AXE_OF_CEREMONY_ID)==0 :
        if st.getQuestItemsCount(BONE_ARROW_ID) < 1000 or st.getQuestItemsCount(NERUGA_AXE_BLADE_ID) == 0 :
          htmltext = "7567-03.htm"
        else:
          htmltext = "7567-04.htm"
          st.takeItems(NERUGA_AXE_BLADE_ID,1)
          st.takeItems(BONE_ARROW_ID,1000)
          st.giveItems(AXE_OF_CEREMONY_ID,1)
          st.takeItems(TANTUS_CHARM_ID,1)
   elif npcId == 7567 and int(st.get("cond"))==1 and st.getQuestItemsCount(TANTUS_CHARM_ID)==0 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(AXE_OF_CEREMONY_ID)) :
        htmltext = "7567-05.htm"
   elif npcId == 7558 and int(st.get("cond"))==1 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(TANTUS_CHARM_ID)) and st.getQuestItemsCount((AXE_OF_CEREMONY_ID) == 0 and st.getQuestItemsCount(NERUGA_AXE_BLADE_ID)) == 0 :
        if st.getQuestItemsCount(ADENA_ID) >= 1000 :
          htmltext = "7558-01.htm"
        else:
          htmltext = "7558-03.htm"
   elif npcId == 7558 and int(st.get("cond"))==1 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(TANTUS_CHARM_ID) and st.getQuestItemsCount(NERUGA_AXE_BLADE_ID)) and st.getQuestItemsCount(AXE_OF_CEREMONY_ID)==0 :
        htmltext = "7558-04.htm"
   elif npcId == 7558 and int(st.get("cond"))==1 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(AXE_OF_CEREMONY_ID)) and st.getQuestItemsCount(TANTUS_CHARM_ID)==0 :
        htmltext = "7558-05.htm"
   elif npcId == 7568 and int(st.get("cond"))==1 and st.getQuestItemsCount(ORDEAL_NECKLACE_ID)==1 and st.getQuestItemsCount((SWORD_INTO_SKULL_ID) == 0 and st.getQuestItemsCount(HATOS_CHARM_ID)) == 0 :
        htmltext = "7568-01.htm"
   elif npcId == 7568 and int(st.get("cond"))==1 and st.getQuestItemsCount(SWORD_INTO_SKULL_ID)==0 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(HATOS_CHARM_ID)) :
        if st.getQuestItemsCount(URUTU_BLADE_ID) == 1 and st.getQuestItemsCount(TIMAK_ORC_SKULL_ID) >= 10 :
          htmltext = "7568-04.htm"
          st.takeItems(HATOS_CHARM_ID,1)
          st.takeItems(URUTU_BLADE_ID,1)
          st.takeItems(TIMAK_ORC_SKULL_ID,st.getQuestItemsCount(TIMAK_ORC_SKULL_ID))
          st.giveItems(SWORD_INTO_SKULL_ID,1)
        else:
          htmltext = "7568-03.htm"
   elif npcId == 7568 and int(st.get("cond"))==1 and st.getQuestItemsCount(HATOS_CHARM_ID)==0 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(SWORD_INTO_SKULL_ID)) :
        htmltext = "7568-05.htm"
   elif npcId == 7564 and int(st.get("cond"))==1 and st.getQuestItemsCount((HATOS_CHARM_ID) and st.getQuestItemsCount(ORDEAL_NECKLACE_ID)) and st.getQuestItemsCount((SWORD_INTO_SKULL_ID) == 0 and st.getQuestItemsCount(URUTU_BLADE_ID) == 0 and st.getQuestItemsCount(SUMARIS_LETTER_ID)) == 0 :
        htmltext = "7564-01.htm"
        st.giveItems(SUMARIS_LETTER_ID,1)
   elif npcId == 7564 and int(st.get("cond"))==1 and st.getQuestItemsCount((SWORD_INTO_SKULL_ID) == 0 and st.getQuestItemsCount(URUTU_BLADE_ID)) == 0 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(HATOS_CHARM_ID) and st.getQuestItemsCount(SUMARIS_LETTER_ID)) :
        htmltext = "7564-02.htm"
   elif npcId == 7564 and int(st.get("cond"))==1 and st.getQuestItemsCount((SUMARIS_LETTER_ID) == 0 and st.getQuestItemsCount(SWORD_INTO_SKULL_ID)) == 0 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(HATOS_CHARM_ID) and st.getQuestItemsCount(URUTU_BLADE_ID)) :
        htmltext = "7564-03.htm"
   elif npcId == 7564 and int(st.get("cond"))==1 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(SWORD_INTO_SKULL_ID)) and st.getQuestItemsCount((HATOS_CHARM_ID) == 0 and st.getQuestItemsCount(URUTU_BLADE_ID) == 0 and st.getQuestItemsCount(SUMARIS_LETTER_ID)) == 0 :
        htmltext = "7564-04.htm"
   elif npcId == 7510 and int(st.get("cond"))==1 and st.getQuestItemsCount((SWORD_INTO_SKULL_ID) == 0 and st.getQuestItemsCount(URUTU_BLADE_ID)) == 0 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(HATOS_CHARM_ID) and st.getQuestItemsCount(SUMARIS_LETTER_ID)) :
        htmltext = "7510-01.htm"
        st.giveItems(URUTU_BLADE_ID,1)
        st.takeItems(SUMARIS_LETTER_ID,1)
   elif npcId == 7510 and int(st.get("cond"))==1 and st.getQuestItemsCount((SWORD_INTO_SKULL_ID) == 0 and st.getQuestItemsCount(SUMARIS_LETTER_ID)) == 0 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(HATOS_CHARM_ID) and st.getQuestItemsCount(URUTU_BLADE_ID)) :
        htmltext = "7510-02.htm"
   elif npcId == 7510 and int(st.get("cond"))==1 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(SWORD_INTO_SKULL_ID)) and st.getQuestItemsCount((HATOS_CHARM_ID) == 0 and st.getQuestItemsCount(URUTU_BLADE_ID) == 0 and st.getQuestItemsCount(SUMARIS_LETTER_ID)) == 0 :
        htmltext = "7510-03.htm"
   elif npcId == 7641 and int(st.get("cond"))==1 and st.getQuestItemsCount((HANDIWORK_SPIDER_BROOCH_ID) == 0 and st.getQuestItemsCount(TAKUNA_CHARM_ID)) == 0 and st.getQuestItemsCount(ORDEAL_NECKLACE_ID)==1 :
        htmltext = "7641-01.htm"
   elif npcId == 7641 and int(st.get("cond"))==1 and st.getQuestItemsCount((TAKUNA_CHARM_ID) and st.getQuestItemsCount(ORDEAL_NECKLACE_ID)) and st.getQuestItemsCount(HANDIWORK_SPIDER_BROOCH_ID)==0 :
        if st.getQuestItemsCount(MARSH_SPIDER_FEELER_ID) >= 10 and st.getQuestItemsCount(MARSH_SPIDER_FEET_ID) >= 10 :
          htmltext = "7641-04.htm"
          st.takeItems(MARSH_SPIDER_FEELER_ID,st.getQuestItemsCount(MARSH_SPIDER_FEELER_ID))
          st.takeItems(MARSH_SPIDER_FEET_ID,st.getQuestItemsCount(MARSH_SPIDER_FEET_ID))
          st.giveItems(HANDIWORK_SPIDER_BROOCH_ID,1)
          st.takeItems(TAKUNA_CHARM_ID,1)
        else:
          htmltext = "7641-03.htm"
   elif npcId == 7641 and int(st.get("cond"))==1 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(HANDIWORK_SPIDER_BROOCH_ID)) and st.getQuestItemsCount(TAKUNA_CHARM_ID)==0 :
        htmltext = "7641-05.htm"
   elif npcId == 7642 and int(st.get("cond"))==1 and st.getQuestItemsCount((MONSTEREYE_WOODCARVING_ID) == 0 and st.getQuestItemsCount(CHIANTA_CHARM_ID)) == 0 and st.getQuestItemsCount(ORDEAL_NECKLACE_ID)==1 :
        htmltext = "7642-01.htm"
   elif npcId == 7642 and int(st.get("cond"))==1 and st.getQuestItemsCount((CHIANTA_CHARM_ID) and st.getQuestItemsCount(ORDEAL_NECKLACE_ID)) and st.getQuestItemsCount(MONSTEREYE_WOODCARVING_ID)==0 :
        if st.getQuestItemsCount(CORNEA_OF_EN_MONSTEREYE_ID) < 20 :
          htmltext = "7642-03.htm"
        else:
          htmltext = "7642-04.htm"
          st.takeItems(CORNEA_OF_EN_MONSTEREYE_ID,st.getQuestItemsCount(CORNEA_OF_EN_MONSTEREYE_ID))
          st.giveItems(MONSTEREYE_WOODCARVING_ID,1)
          st.takeItems(CHIANTA_CHARM_ID,1)
   elif npcId == 7642 and int(st.get("cond"))==1 and st.getQuestItemsCount((ORDEAL_NECKLACE_ID) and st.getQuestItemsCount(MONSTEREYE_WOODCARVING_ID)) and st.getQuestItemsCount(CHIANTA_CHARM_ID)==0 :
        htmltext = "7642-05.htm"
   elif npcId == 7649 and int(st.get("cond"))==1 and st.getQuestItemsCount(BEAR_FANG_NECKLACE_ID)==1 :
        htmltext = "7649-01.htm"
   elif npcId == 7649 and int(st.get("cond"))==1 and st.getQuestItemsCount(MARTANKUS_CHARM_ID)==1 and st.getQuestItemsCount((RAGNA_CHIEF_NOTICE_ID) == 0 and st.getQuestItemsCount(RAGNA_ORC_HEAD_ID)) == 0 :
        htmltext = "7649-05.htm"
   elif npcId == 7649 and int(st.get("cond"))==1 and st.getQuestItemsCount((MARTANKUS_CHARM_ID) and st.getQuestItemsCount(RAGNA_CHIEF_NOTICE_ID) and st.getQuestItemsCount(RAGNA_ORC_HEAD_ID)) :
        htmltext = "7649-06.htm"
        st.takeItems(MARTANKUS_CHARM_ID,1)
        st.takeItems(RAGNA_ORC_HEAD_ID,1)
        st.giveItems(IMMORTAL_FLAME_ID,1)
        st.takeItems(RAGNA_CHIEF_NOTICE_ID,1)
   elif npcId == 7649 and int(st.get("cond"))==1 and st.getQuestItemsCount(IMMORTAL_FLAME_ID)==1 :
        htmltext = "7649-08.htm"
        if Maker_GetNpcCount() == 1 :
          st.spawnNpc(7643,21036,-107690,-3038)
   elif npcId == 7643 and int(st.get("cond"))==1 and st.getQuestItemsCount((MARTANKUS_CHARM_ID) or st.getQuestItemsCount(IMMORTAL_FLAME_ID)) :
        htmltext = "7643-01.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 583 or npcId == 584 or npcId == 585 :
    st.set("id","0")
    if int(st.get("cond")) == 1 and st.getQuestItemsCount(ORDEAL_NECKLACE_ID) == 1 and st.getQuestItemsCount(HATOS_CHARM_ID) == 1 and st.getQuestItemsCount(SWORD_INTO_SKULL_ID) == 0 :
     if st.getQuestItemsCount(TIMAK_ORC_SKULL_ID) < 10 and st.getRandom(100) <= 70 :
      st.giveItems(TIMAK_ORC_SKULL_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 586 :
    st.set("id","0")
    if int(st.get("cond")) == 1 and st.getQuestItemsCount(ORDEAL_NECKLACE_ID) == 1 and st.getQuestItemsCount(HATOS_CHARM_ID) == 1 and st.getQuestItemsCount(SWORD_INTO_SKULL_ID) == 0 :
     if st.getQuestItemsCount(TIMAK_ORC_SKULL_ID) < 10 and st.getRandom(100) <= 80 :
      st.giveItems(TIMAK_ORC_SKULL_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 587 or npcId == 588 :
    st.set("id","0")
    if int(st.get("cond")) == 1 and st.getQuestItemsCount(ORDEAL_NECKLACE_ID) == 1 and st.getQuestItemsCount(HATOS_CHARM_ID) == 1 and st.getQuestItemsCount(SWORD_INTO_SKULL_ID) == 0 :
     if st.getQuestItemsCount(TIMAK_ORC_SKULL_ID) < 10 :
      st.giveItems(TIMAK_ORC_SKULL_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 269 :
    st.set("id","0")
    if int(st.get("cond")) == 1 and st.getQuestItemsCount(ORDEAL_NECKLACE_ID) == 1 and st.getQuestItemsCount(VARKEES_CHARM_ID) == 1 and st.getQuestItemsCount(MANAKIAS_ORDERS_ID) == 1  :
     if st.getQuestItemsCount(BREKA_ORC_FANG_ID) < 20 and st.getRandom(100) <= 40 :
      st.giveItems(BREKA_ORC_FANG_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 270 :
    st.set("id","0")
    if int(st.get("cond")) == 1 and st.getQuestItemsCount(ORDEAL_NECKLACE_ID) == 1 and st.getQuestItemsCount(VARKEES_CHARM_ID) == 1 and st.getQuestItemsCount(MANAKIAS_ORDERS_ID) == 1  :
     if st.getQuestItemsCount(BREKA_ORC_FANG_ID) < 20 and st.getRandom(100) <= 50 :
      st.giveItems(BREKA_ORC_FANG_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 778 or npcId == 779 :
    st.set("id","0")
    if int(st.get("cond")) == 1 and st.getQuestItemsCount(MARTANKUS_CHARM_ID) == 1 :
     if st.getQuestItemsCount(RAGNA_CHIEF_NOTICE_ID) == 0 :
      st.giveItems(RAGNA_CHIEF_NOTICE_ID,1)
      st.playSound("ItemSound.quest_middle")
     elif st.getQuestItemsCount(RAGNA_ORC_HEAD_ID) == 0 :
      st.giveItems(RAGNA_ORC_HEAD_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 233 :
    if int(st.get("cond")) == 1 and st.getQuestItemsCount(ORDEAL_NECKLACE_ID) == 1 and st.getQuestItemsCount(TAKUNA_CHARM_ID) == 1  :
      if st.getRandom(100) < 50 :
        if st.getQuestItemsCount(MARSH_SPIDER_FEELER_ID) < 10 :
          st.giveItems(MARSH_SPIDER_FEELER_ID,1)
        elif st.getQuestItemsCount(MARSH_SPIDER_FEET_ID) < 10 :
          st.giveItems(MARSH_SPIDER_FEET_ID,1)
   elif npcId == 564 :
    if int(st.get("cond")) == 1 and st.getQuestItemsCount(ORDEAL_NECKLACE_ID) == 1 and st.getQuestItemsCount(CHIANTA_CHARM_ID) == 1  :
      if st.getQuestItemsCount(CORNEA_OF_EN_MONSTEREYE_ID) < 20 :
        st.giveItems(CORNEA_OF_EN_MONSTEREYE_ID,1)
   return

QUEST       = Quest(232,"232_TestOfLord","Test Of Lord")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7565)

STARTED.addTalkId(7510)
STARTED.addTalkId(7515)
STARTED.addTalkId(7558)
STARTED.addTalkId(7564)
STARTED.addTalkId(7565)
STARTED.addTalkId(7566)
STARTED.addTalkId(7567)
STARTED.addTalkId(7568)
STARTED.addTalkId(7641)
STARTED.addTalkId(7642)
STARTED.addTalkId(7643)
STARTED.addTalkId(7649)

STARTED.addKillId(233)
STARTED.addKillId(269)
STARTED.addKillId(270)
STARTED.addKillId(564)
STARTED.addKillId(583)
STARTED.addKillId(584)
STARTED.addKillId(585)
STARTED.addKillId(586)
STARTED.addKillId(587)
STARTED.addKillId(588)
STARTED.addKillId(778)
STARTED.addKillId(779)

STARTED.addQuestDrop(7568,SWORD_INTO_SKULL_ID,1)
STARTED.addQuestDrop(7567,AXE_OF_CEREMONY_ID,1)
STARTED.addQuestDrop(7642,MONSTEREYE_WOODCARVING_ID,1)
STARTED.addQuestDrop(7641,HANDIWORK_SPIDER_BROOCH_ID,1)
STARTED.addQuestDrop(7565,ORDEAL_NECKLACE_ID,1)
STARTED.addQuestDrop(7566,HUGE_ORC_FANG_ID,1)
STARTED.addQuestDrop(7649,IMMORTAL_FLAME_ID,1)
STARTED.addQuestDrop(7566,VARKEES_CHARM_ID,1)
STARTED.addQuestDrop(7515,MANAKIAS_AMULET_ID,1)
STARTED.addQuestDrop(7515,MANAKIAS_ORDERS_ID,1)
STARTED.addQuestDrop(269,BREKA_ORC_FANG_ID,1)
STARTED.addQuestDrop(270,BREKA_ORC_FANG_ID,1)
STARTED.addQuestDrop(7558,NERUGA_AXE_BLADE_ID,1)
STARTED.addQuestDrop(7567,TANTUS_CHARM_ID,1)
STARTED.addQuestDrop(7568,HATOS_CHARM_ID,1)
STARTED.addQuestDrop(7510,URUTU_BLADE_ID,1)
STARTED.addQuestDrop(583,TIMAK_ORC_SKULL_ID,1)
STARTED.addQuestDrop(584,TIMAK_ORC_SKULL_ID,1)
STARTED.addQuestDrop(585,TIMAK_ORC_SKULL_ID,1)
STARTED.addQuestDrop(586,TIMAK_ORC_SKULL_ID,1)
STARTED.addQuestDrop(587,TIMAK_ORC_SKULL_ID,1)
STARTED.addQuestDrop(588,TIMAK_ORC_SKULL_ID,1)
STARTED.addQuestDrop(7564,SUMARIS_LETTER_ID,1)
STARTED.addQuestDrop(233,MARSH_SPIDER_FEELER_ID,1)
STARTED.addQuestDrop(233,MARSH_SPIDER_FEET_ID,1)
STARTED.addQuestDrop(7641,TAKUNA_CHARM_ID,1)
STARTED.addQuestDrop(564,CORNEA_OF_EN_MONSTEREYE_ID,1)
STARTED.addQuestDrop(7642,CHIANTA_CHARM_ID,1)
STARTED.addQuestDrop(7565,BEAR_FANG_NECKLACE_ID,1)
STARTED.addQuestDrop(7649,MARTANKUS_CHARM_ID,1)
STARTED.addQuestDrop(778,RAGNA_ORC_HEAD_ID,1)
STARTED.addQuestDrop(779,RAGNA_ORC_HEAD_ID,1)
STARTED.addQuestDrop(778,RAGNA_CHIEF_NOTICE_ID,1)
STARTED.addQuestDrop(779,RAGNA_CHIEF_NOTICE_ID,1)
