# Maked by Mr. Have fun! Version 0.2
print "importing quests: 230: Test Of Summoner"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_SUMMONER_ID = 3336
LETOLIZARDMAN_AMULET_ID = 3337
KARULBUGBEAR_TOTEM_ID = 3339
SAC_OF_REDSPORES_ID = 3338
SHARDS_OF_MANASHEN_ID = 3340
BREKAORC_TOTEM_ID = 3341
CRIMSON_BLOODSTONE_ID = 3342
GALATEAS_LETTER_ID = 3352
LARS_LIST1_ID = 3347
LARS_LIST2_ID = 3348
LARS_LIST3_ID = 3349
LARS_LIST4_ID = 3350
LARS_LIST5_ID = 3351
ALMORS_ARCANA_ID = 3354
BASILLIA_ARCANA_ID = 3357
CAMONIELL_ARCANA_ID = 3355
CELESTIEL_ARCANA_ID = 3358
BELTHUS_ARCANA_ID = 3356
BRYNTHEA_ARCANA_ID = 3359
BEGINNERS_ARCANA_ID = 3353
TALONS_OF_TYRANT_ID = 3343
TUSK_OF_WINDSUS_ID = 3345
WINGS_OF_DRONEANT_ID = 3344
FANGS_OF_WYRM_ID = 3346
CRYSTAL_OF_VICTORY1_ID = 3364
CRYSTAL_OF_STARTING1_ID = 3360
CRYSTAL_OF_FOUL1_ID = 3362
CRYSTAL_OF_DEFEAT1_ID = 3363
CRYSTAL_OF_VICTORY2_ID = 3369
CRYSTAL_OF_STARTING2_ID = 3365
CRYSTAL_OF_FOUL2_ID = 3367
CRYSTAL_OF_DEFEAT2_ID = 3368
CRYSTAL_OF_VICTORY3_ID = 3374
CRYSTAL_OF_STARTING3_ID = 3370
CRYSTAL_OF_FOUL3_ID = 3372
CRYSTAL_OF_DEFEAT3_ID = 3373
CRYSTAL_OF_VICTORY4_ID = 3379
CRYSTAL_OF_STARTING4_ID = 3375
CRYSTAL_OF_FOUL4_ID = 3377
CRYSTAL_OF_DEFEAT4_ID = 3378
CRYSTAL_OF_VICTORY5_ID = 3384
CRYSTAL_OF_STARTING5_ID = 3380
CRYSTAL_OF_FOUL5_ID = 3382
CRYSTAL_OF_DEFEAT5_ID = 3383
CRYSTAL_OF_VICTORY6_ID = 3389
CRYSTAL_OF_STARTING6_ID = 3385
CRYSTAL_OF_FOUL6_ID = 3387
CRYSTAL_OF_DEFEAT6_ID = 3388
CRYSTAL_OF_INPROGRESS3_ID = 3371
CRYSTAL_OF_INPROGRESS1_ID = 3361
CRYSTAL_OF_INPROGRESS2_ID = 3366
CRYSTAL_OF_INPROGRESS4_ID = 3376
CRYSTAL_OF_INPROGRESS6_ID = 3386
CRYSTAL_OF_INPROGRESS5_ID = 3381

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      htmltext = "7634-08.htm"
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      st.giveItems(GALATEAS_LETTER_ID,1)
    elif event == "7634_1" :
          htmltext = "7634-04.htm"
    elif event == "7634_2" :
          htmltext = "7634-05.htm"
    elif event == "7634_3" :
          htmltext = "7634-06.htm"
    elif event == "7634_4" :
          htmltext = "7634-07.htm"
    elif event == "7063_1" :
          htmltext = "7063-02.htm"
          n = st.getRandom(5)
          if n == 0:
            st.giveItems(LARS_LIST1_ID,1)
          if n == 1:
            st.giveItems(LARS_LIST2_ID,1)
          if n == 2:
            st.giveItems(LARS_LIST3_ID,1)
          if n == 3:
            st.giveItems(LARS_LIST4_ID,1)
          if n == 4:
            st.giveItems(LARS_LIST5_ID,1)
          st.takeItems(GALATEAS_LETTER_ID,1)
    elif event == "7063_2" :
          htmltext = "7063-04.htm"
          n = st.getRandom(5)
          if n == 0:
            st.giveItems(LARS_LIST1_ID,1)
          if n == 1:
            st.giveItems(LARS_LIST2_ID,1)
          if n == 2:
            st.giveItems(LARS_LIST3_ID,1)
          if n == 3:
            st.giveItems(LARS_LIST4_ID,1)
          if n == 4:
            st.giveItems(LARS_LIST5_ID,1)
    elif event == "7635_1" :
          if st.getQuestItemsCount(BEGINNERS_ARCANA_ID) >= 1 :
            htmltext = "7635-03.htm"
          else:
            htmltext = "7635-02.htm"
    elif event == "7635_2" :
          htmltext = "7635-04.htm"
          st.giveItems(CRYSTAL_OF_STARTING1_ID,1)
          st.takeItems(CRYSTAL_OF_FOUL1_ID,1)
          st.takeItems(CRYSTAL_OF_DEFEAT1_ID,1)
          st.takeItems(BEGINNERS_ARCANA_ID,1)
    elif event == "7638_1" :
          if st.getQuestItemsCount(BEGINNERS_ARCANA_ID) >= 1 :
            htmltext = "7638-03.htm"
          else:
            htmltext = "7638-02.htm"
    elif event == "7638_2" :
          htmltext = "7638-04.htm"
          st.giveItems(CRYSTAL_OF_STARTING2_ID,1)
          st.takeItems(CRYSTAL_OF_FOUL2_ID,1)
          st.takeItems(CRYSTAL_OF_DEFEAT2_ID,1)
          st.takeItems(BEGINNERS_ARCANA_ID,1)
    elif event == "7636_1" :
          if st.getQuestItemsCount(BEGINNERS_ARCANA_ID) >= 1 :
            htmltext = "7636-03.htm"
          else:
            htmltext = "7636-02.htm"
    elif event == "7636_2" :
          htmltext = "7636-04.htm"
          st.giveItems(CRYSTAL_OF_STARTING3_ID,1)
          st.takeItems(CRYSTAL_OF_FOUL3_ID,1)
          st.takeItems(CRYSTAL_OF_DEFEAT3_ID,1)
          st.takeItems(BEGINNERS_ARCANA_ID,1)
    elif event == "7639_1" :
          if st.getQuestItemsCount(BEGINNERS_ARCANA_ID) >= 1 :
            htmltext = "7639-03.htm"
          else:
            htmltext = "7639-02.htm"
    elif event == "7639_2" :
          htmltext = "7639-04.htm"
          st.giveItems(CRYSTAL_OF_STARTING4_ID,1)
          st.takeItems(CRYSTAL_OF_FOUL4_ID,1)
          st.takeItems(CRYSTAL_OF_DEFEAT4_ID,1)
          st.takeItems(BEGINNERS_ARCANA_ID,1)
    elif event == "7637_1" :
          if st.getQuestItemsCount(BEGINNERS_ARCANA_ID) >= 1 :
            htmltext = "7637-03.htm"
          else:
            htmltext = "7637-02.htm"
    elif event == "7637_2" :
          htmltext = "7637-04.htm"
          st.giveItems(CRYSTAL_OF_STARTING5_ID,1)
          st.takeItems(CRYSTAL_OF_FOUL5_ID,1)
          st.takeItems(CRYSTAL_OF_DEFEAT5_ID,1)
          st.takeItems(BEGINNERS_ARCANA_ID,1)
    elif event == "7640_1" :
          if st.getQuestItemsCount(BEGINNERS_ARCANA_ID) >= 1 :
            htmltext = "7640-03.htm"
          else:
            htmltext = "7640-02.htm"
    elif event == "7640_2" :
          htmltext = "7640-04.htm"
          st.giveItems(CRYSTAL_OF_STARTING6_ID,1)
          st.takeItems(CRYSTAL_OF_FOUL6_ID,1)
          st.takeItems(CRYSTAL_OF_DEFEAT6_ID,1)
          st.takeItems(BEGINNERS_ARCANA_ID,1)
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7634 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if int(st.get("cond")) < 15 :
        if (st.getPlayer().getClassId().getId() == 0x0b or st.getPlayer().getClassId().getId() == 0x1a or st.getPlayer().getClassId().getId() == 0x27) and st.getPlayer().getLevel() >= 3 :
          htmltext = "7634-03.htm"
        elif st.getPlayer().getClassId().getId() == 0x0b or st.getPlayer().getClassId().getId() == 0x1a or st.getPlayer().getClassId().getId() == 0x27 :
          htmltext = "7634-02.htm"
        else:
          htmltext = "7634-01.htm"
      else:
        htmltext = "7634-01.htm"
   elif npcId == 7634 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7634 and int(st.get("cond"))==1 and st.getQuestItemsCount(GALATEAS_LETTER_ID) :
      htmltext = "7634-09.htm"
   elif npcId == 7634 and int(st.get("cond"))==1 and st.getQuestItemsCount(GALATEAS_LETTER_ID)==0 and (st.getQuestItemsCount(ALMORS_ARCANA_ID)+st.getQuestItemsCount(BASILLIA_ARCANA_ID)+st.getQuestItemsCount(CAMONIELL_ARCANA_ID)+st.getQuestItemsCount(CELESTIEL_ARCANA_ID)+st.getQuestItemsCount(BELTHUS_ARCANA_ID)+st.getQuestItemsCount(BRYNTHEA_ARCANA_ID))<6 and st.getQuestItemsCount(BEGINNERS_ARCANA_ID)<1 :
      htmltext = "7634-10.htm"
   elif npcId == 7634 and int(st.get("cond"))==1 and st.getQuestItemsCount(GALATEAS_LETTER_ID)==0 and (st.getQuestItemsCount(ALMORS_ARCANA_ID)+st.getQuestItemsCount(BASILLIA_ARCANA_ID)+st.getQuestItemsCount(CAMONIELL_ARCANA_ID)+st.getQuestItemsCount(CELESTIEL_ARCANA_ID)+st.getQuestItemsCount(BELTHUS_ARCANA_ID)+st.getQuestItemsCount(BRYNTHEA_ARCANA_ID))<6 and st.getQuestItemsCount(BEGINNERS_ARCANA_ID)>=1 :
      htmltext = "7634-11.htm"
   elif npcId == 7634 and int(st.get("cond"))==1 and st.getQuestItemsCount(GALATEAS_LETTER_ID)==0 and (st.getQuestItemsCount(ALMORS_ARCANA_ID)+st.getQuestItemsCount(BASILLIA_ARCANA_ID)+st.getQuestItemsCount(CAMONIELL_ARCANA_ID)+st.getQuestItemsCount(CELESTIEL_ARCANA_ID)+st.getQuestItemsCount(BELTHUS_ARCANA_ID)+st.getQuestItemsCount(BRYNTHEA_ARCANA_ID))>=6 :
      if st.getGameTicks() != int(st.get("id")) :
        st.set("id",str(st.getGameTicks()))
        st.addExpAndSp(41000,5000)
      htmltext = "7634-12.htm"
      st.set("cond","0")
      st.setState(COMPLETED)
      st.playSound("ItemSound.quest_finish")
      st.set("onlyone","1")
      st.giveItems(MARK_OF_SUMMONER_ID,1)
      st.takeItems(LARS_LIST1_ID,st.getQuestItemsCount(LARS_LIST1_ID))
      st.takeItems(LARS_LIST2_ID,st.getQuestItemsCount(LARS_LIST2_ID))
      st.takeItems(LARS_LIST3_ID,st.getQuestItemsCount(LARS_LIST3_ID))
      st.takeItems(LARS_LIST4_ID,st.getQuestItemsCount(LARS_LIST4_ID))
      st.takeItems(LARS_LIST5_ID,st.getQuestItemsCount(LARS_LIST5_ID))
      st.takeItems(ALMORS_ARCANA_ID,1)
      st.takeItems(BASILLIA_ARCANA_ID,1)
      st.takeItems(CAMONIELL_ARCANA_ID,1)
      st.takeItems(CELESTIEL_ARCANA_ID,1)
      st.takeItems(BELTHUS_ARCANA_ID,1)
      st.takeItems(BRYNTHEA_ARCANA_ID,1)
   elif npcId == 7063 and int(st.get("cond"))==1 and st.getQuestItemsCount(GALATEAS_LETTER_ID) :
      htmltext = "7063-01.htm"
   elif npcId == 7063 and int(st.get("cond"))==1 and st.getQuestItemsCount(GALATEAS_LETTER_ID)==0 and st.getQuestItemsCount(LARS_LIST1_ID)==0 and st.getQuestItemsCount(LARS_LIST2_ID)==0 and st.getQuestItemsCount(LARS_LIST3_ID)==0 and st.getQuestItemsCount(LARS_LIST4_ID)==0 and st.getQuestItemsCount(LARS_LIST5_ID)==0 :
      htmltext = "7063-03.htm"
   elif npcId == 7063 and int(st.get("cond"))==1 and st.getQuestItemsCount(GALATEAS_LETTER_ID)==0 and st.getQuestItemsCount(LARS_LIST1_ID) and (st.getQuestItemsCount(LETOLIZARDMAN_AMULET_ID)<30 or st.getQuestItemsCount(SAC_OF_REDSPORES_ID)<30) :
      htmltext = "7063-05.htm"
   elif npcId == 7063 and int(st.get("cond"))==1 and st.getQuestItemsCount(GALATEAS_LETTER_ID)==0 and st.getQuestItemsCount(LARS_LIST1_ID) and st.getQuestItemsCount(LETOLIZARDMAN_AMULET_ID)>=30 and st.getQuestItemsCount(SAC_OF_REDSPORES_ID)>=30 :
      htmltext = "7063-06.htm"
      st.giveItems(BEGINNERS_ARCANA_ID,2)
      st.takeItems(LARS_LIST1_ID,1)
      st.takeItems(LETOLIZARDMAN_AMULET_ID,st.getQuestItemsCount(LETOLIZARDMAN_AMULET_ID))
      st.takeItems(SAC_OF_REDSPORES_ID,st.getQuestItemsCount(SAC_OF_REDSPORES_ID))
   elif npcId == 7063 and int(st.get("cond"))==1 and st.getQuestItemsCount(GALATEAS_LETTER_ID)==0 and st.getQuestItemsCount(LARS_LIST2_ID) and (st.getQuestItemsCount(KARULBUGBEAR_TOTEM_ID)<30 or st.getQuestItemsCount(SHARDS_OF_MANASHEN_ID)<30) :
      htmltext = "7063-07.htm"
   elif npcId == 7063 and int(st.get("cond"))==1 and st.getQuestItemsCount(GALATEAS_LETTER_ID)==0 and st.getQuestItemsCount(LARS_LIST2_ID) and st.getQuestItemsCount(KARULBUGBEAR_TOTEM_ID)>=30 and st.getQuestItemsCount(SHARDS_OF_MANASHEN_ID) :
      htmltext = "7063-08.htm"
      st.giveItems(BEGINNERS_ARCANA_ID,2)
      st.takeItems(LARS_LIST2_ID,1)
      st.takeItems(KARULBUGBEAR_TOTEM_ID,st.getQuestItemsCount(KARULBUGBEAR_TOTEM_ID))
      st.takeItems(SHARDS_OF_MANASHEN_ID,st.getQuestItemsCount(SHARDS_OF_MANASHEN_ID))
   elif npcId == 7063 and int(st.get("cond"))==1 and st.getQuestItemsCount(GALATEAS_LETTER_ID)==0 and st.getQuestItemsCount(LARS_LIST3_ID) and (st.getQuestItemsCount(BREKAORC_TOTEM_ID)<30 or st.getQuestItemsCount(CRIMSON_BLOODSTONE_ID)<30) :
      htmltext = "7063-09.htm"
   elif npcId == 7063 and int(st.get("cond"))==1 and st.getQuestItemsCount(GALATEAS_LETTER_ID)==0 and st.getQuestItemsCount(LARS_LIST3_ID) and st.getQuestItemsCount(BREKAORC_TOTEM_ID)>=30 and st.getQuestItemsCount(CRIMSON_BLOODSTONE_ID)>=30 :
      htmltext = "7063-10.htm"
      st.giveItems(BEGINNERS_ARCANA_ID,2)
      st.takeItems(LARS_LIST3_ID,1)
      st.takeItems(BREKAORC_TOTEM_ID,st.getQuestItemsCount(BREKAORC_TOTEM_ID))
      st.takeItems(CRIMSON_BLOODSTONE_ID,st.getQuestItemsCount(CRIMSON_BLOODSTONE_ID))
   elif npcId == 7063 and int(st.get("cond"))==1 and st.getQuestItemsCount(GALATEAS_LETTER_ID)==0 and st.getQuestItemsCount(LARS_LIST4_ID) and (st.getQuestItemsCount(TALONS_OF_TYRANT_ID)<30 or st.getQuestItemsCount(TUSK_OF_WINDSUS_ID)<30) :
      htmltext = "7063-11.htm"
   elif npcId == 7063 and int(st.get("cond"))==1 and st.getQuestItemsCount(GALATEAS_LETTER_ID)==0 and st.getQuestItemsCount(LARS_LIST4_ID) and st.getQuestItemsCount(TALONS_OF_TYRANT_ID)>=30 and st.getQuestItemsCount(TUSK_OF_WINDSUS_ID)>=30 :
      htmltext = "7063-12.htm"
      st.giveItems(BEGINNERS_ARCANA_ID,2)
      st.takeItems(LARS_LIST4_ID,1)
      st.takeItems(TALONS_OF_TYRANT_ID,st.getQuestItemsCount(TALONS_OF_TYRANT_ID))
      st.takeItems(TUSK_OF_WINDSUS_ID,st.getQuestItemsCount(TUSK_OF_WINDSUS_ID))
   elif npcId == 7063 and int(st.get("cond"))==1 and st.getQuestItemsCount(GALATEAS_LETTER_ID)==0 and st.getQuestItemsCount(LARS_LIST5_ID) and (st.getQuestItemsCount(WINGS_OF_DRONEANT_ID)<30 or st.getQuestItemsCount(FANGS_OF_WYRM_ID)<30) :
      htmltext = "7063-13.htm"
   elif npcId == 7063 and int(st.get("cond"))==1 and st.getQuestItemsCount(GALATEAS_LETTER_ID)==0 and st.getQuestItemsCount(LARS_LIST5_ID) and st.getQuestItemsCount(WINGS_OF_DRONEANT_ID)>=30 and st.getQuestItemsCount(FANGS_OF_WYRM_ID)>=30 :
      htmltext = "7063-14.htm"
      st.giveItems(BEGINNERS_ARCANA_ID,2)
      st.takeItems(LARS_LIST5_ID,1)
      st.takeItems(WINGS_OF_DRONEANT_ID,st.getQuestItemsCount(WINGS_OF_DRONEANT_ID))
      st.takeItems(FANGS_OF_WYRM_ID,st.getQuestItemsCount(FANGS_OF_WYRM_ID))
   elif npcId == 7635 and int(st.get("cond"))==1 and st.getQuestItemsCount(ALMORS_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING1_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS1_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL1_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT1_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY1_ID)==0 :
      htmltext = "7635-01.htm"
   elif npcId == 7635 and int(st.get("cond"))==1 and st.getQuestItemsCount(ALMORS_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING1_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS1_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL1_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT1_ID) and st.getQuestItemsCount(CRYSTAL_OF_VICTORY1_ID)==0 :
      htmltext = "7635-05.htm"
   elif npcId == 7635 and int(st.get("cond"))==1 and st.getQuestItemsCount(ALMORS_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING1_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS1_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL1_ID) and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT1_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY1_ID)==0 :
      htmltext = "7635-06.htm"
   elif npcId == 7635 and int(st.get("cond"))==1 and st.getQuestItemsCount(ALMORS_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING1_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS1_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL1_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT1_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY1_ID) :
      htmltext = "7635-07.htm"
      st.giveItems(ALMORS_ARCANA_ID,1)
      st.takeItems(CRYSTAL_OF_VICTORY1_ID,1)
   elif npcId == 7635 and int(st.get("cond"))==1 and st.getQuestItemsCount(ALMORS_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING1_ID) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS1_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL1_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT1_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY1_ID)==0 :
      htmltext = "7635-08.htm"
   elif npcId == 7635 and int(st.get("cond"))==1 and st.getQuestItemsCount(ALMORS_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING1_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS1_ID) and st.getQuestItemsCount(CRYSTAL_OF_FOUL1_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT1_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY1_ID)==0 :
      htmltext = "7635-09.htm"
   elif npcId == 7635 and int(st.get("cond"))==1 and st.getQuestItemsCount(ALMORS_ARCANA_ID)==1 :
      htmltext = "7635-10.htm"
   elif npcId == 7638 and int(st.get("cond"))==1 and st.getQuestItemsCount(BASILLIA_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING2_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS2_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL2_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT2_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY2_ID)==0 :
      htmltext = "7638-01.htm"
   elif npcId == 7638 and int(st.get("cond"))==1 and st.getQuestItemsCount(BASILLIA_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING2_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS2_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL2_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT2_ID) and st.getQuestItemsCount(CRYSTAL_OF_VICTORY2_ID)==0 :
      htmltext = "7638-05.htm"
   elif npcId == 7638 and int(st.get("cond"))==1 and st.getQuestItemsCount(BASILLIA_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING2_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS2_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL2_ID) and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT2_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY2_ID)==0 :
      htmltext = "7638-06.htm"
   elif npcId == 7638 and int(st.get("cond"))==1 and st.getQuestItemsCount(BASILLIA_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING2_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS2_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL2_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT2_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY2_ID) :
      htmltext = "7638-07.htm"
      st.giveItems(BASILLIA_ARCANA_ID,1)
      st.takeItems(CRYSTAL_OF_VICTORY2_ID,1)
   elif npcId == 7638 and int(st.get("cond"))==1 and st.getQuestItemsCount(BASILLIA_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING2_ID) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS2_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL2_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT2_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY2_ID)==0 :
      htmltext = "7638-08.htm"
   elif npcId == 7638 and int(st.get("cond"))==1 and st.getQuestItemsCount(BASILLIA_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING2_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS2_ID) and st.getQuestItemsCount(CRYSTAL_OF_FOUL2_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT2_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY2_ID)==0 :
      htmltext = "7638-09.htm"
   elif npcId == 7638 and int(st.get("cond"))==1 and st.getQuestItemsCount(BASILLIA_ARCANA_ID)==1 :
      htmltext = "7638-10.htm"
   elif npcId == 7636 and int(st.get("cond"))==1 and st.getQuestItemsCount(CAMONIELL_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING3_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS3_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL3_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT3_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY3_ID)==0 :
      htmltext = "7636-01.htm"
   elif npcId == 7636 and int(st.get("cond"))==1 and st.getQuestItemsCount(CAMONIELL_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING3_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS3_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL3_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT3_ID) and st.getQuestItemsCount(CRYSTAL_OF_VICTORY3_ID)==0 :
      htmltext = "7636-05.htm"
   elif npcId == 7636 and int(st.get("cond"))==1 and st.getQuestItemsCount(CAMONIELL_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING3_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS3_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL3_ID) and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT3_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY3_ID)==0 :
      htmltext = "7636-06.htm"
   elif npcId == 7636 and int(st.get("cond"))==1 and st.getQuestItemsCount(CAMONIELL_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING3_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS3_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL3_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT3_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY3_ID) :
      htmltext = "7636-07.htm"
      st.giveItems(CAMONIELL_ARCANA_ID,1)
      st.takeItems(CRYSTAL_OF_VICTORY3_ID,1)
   elif npcId == 7636 and int(st.get("cond"))==1 and st.getQuestItemsCount(CAMONIELL_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING3_ID) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS3_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL3_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT3_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY3_ID)==0 :
      htmltext = "7636-08.htm"
   elif npcId == 7636 and int(st.get("cond"))==1 and st.getQuestItemsCount(CAMONIELL_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING3_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS3_ID) and st.getQuestItemsCount(CRYSTAL_OF_FOUL3_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT3_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY3_ID)==0 :
      htmltext = "7636-09.htm"
   elif npcId == 7636 and int(st.get("cond"))==1 and st.getQuestItemsCount(CAMONIELL_ARCANA_ID)==1 :
      htmltext = "7636-10.htm"
   elif npcId == 7639 and int(st.get("cond"))==1 and st.getQuestItemsCount(CELESTIEL_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING4_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS4_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL4_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT4_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY4_ID)==0 :
      htmltext = "7639-01.htm"
   elif npcId == 7639 and int(st.get("cond"))==1 and st.getQuestItemsCount(CELESTIEL_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING4_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS4_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL4_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT4_ID) and st.getQuestItemsCount(CRYSTAL_OF_VICTORY4_ID)==0 :
      htmltext = "7639-05.htm"
   elif npcId == 7639 and int(st.get("cond"))==1 and st.getQuestItemsCount(CELESTIEL_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING4_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS4_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL4_ID) and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT4_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY4_ID)==0 :
      htmltext = "7639-06.htm"
   elif npcId == 7639 and int(st.get("cond"))==1 and st.getQuestItemsCount(CELESTIEL_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING4_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS4_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL4_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT4_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY4_ID) :
      htmltext = "7639-07.htm"
      st.giveItems(CELESTIEL_ARCANA_ID,1)
      st.takeItems(CRYSTAL_OF_VICTORY4_ID,1)
   elif npcId == 7639 and int(st.get("cond"))==1 and st.getQuestItemsCount(CELESTIEL_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING4_ID) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS4_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL4_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT4_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY4_ID)==0 :
      htmltext = "7639-08.htm"
   elif npcId == 7639 and int(st.get("cond"))==1 and st.getQuestItemsCount(CELESTIEL_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING4_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS4_ID) and st.getQuestItemsCount(CRYSTAL_OF_FOUL4_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT4_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY4_ID)==0 :
      htmltext = "7639-09.htm"
   elif npcId == 7639 and int(st.get("cond"))==1 and st.getQuestItemsCount(CELESTIEL_ARCANA_ID)==1 :
      htmltext = "7639-10.htm"
   elif npcId == 7637 and int(st.get("cond"))==1 and st.getQuestItemsCount(BELTHUS_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING5_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS5_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL5_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT5_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY5_ID)==0 :
      htmltext = "7637-01.htm"
   elif npcId == 7637 and int(st.get("cond"))==1 and st.getQuestItemsCount(BELTHUS_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING5_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS5_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL5_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT5_ID) and st.getQuestItemsCount(CRYSTAL_OF_VICTORY5_ID)==0 :
      htmltext = "7637-05.htm"
   elif npcId == 7637 and int(st.get("cond"))==1 and st.getQuestItemsCount(BELTHUS_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING5_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS5_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL5_ID) and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT5_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY5_ID)==0 :
      htmltext = "7637-06.htm"
   elif npcId == 7637 and int(st.get("cond"))==1 and st.getQuestItemsCount(BELTHUS_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING5_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS5_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL5_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT5_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY5_ID) :
      htmltext = "7637-07.htm"
      st.giveItems(BELTHUS_ARCANA_ID,1)
      st.takeItems(CRYSTAL_OF_VICTORY5_ID,1)
   elif npcId == 7637 and int(st.get("cond"))==1 and st.getQuestItemsCount(BELTHUS_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING5_ID) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS5_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL5_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT5_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY5_ID)==0 :
      htmltext = "7637-08.htm"
   elif npcId == 7637 and int(st.get("cond"))==1 and st.getQuestItemsCount(BELTHUS_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING5_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS5_ID) and st.getQuestItemsCount(CRYSTAL_OF_FOUL5_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT5_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY5_ID)==0 :
      htmltext = "7637-09.htm"
   elif npcId == 7637 and int(st.get("cond"))==1 and st.getQuestItemsCount(BELTHUS_ARCANA_ID)==1 :
      htmltext = "7637-10.htm"
   elif npcId == 7640 and int(st.get("cond"))==1 and st.getQuestItemsCount(BRYNTHEA_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING6_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS6_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL6_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT6_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY6_ID)==0 :
      htmltext = "7640-01.htm"
   elif npcId == 7640 and int(st.get("cond"))==1 and st.getQuestItemsCount(BRYNTHEA_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING6_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS6_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL6_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT6_ID) and st.getQuestItemsCount(CRYSTAL_OF_VICTORY6_ID)==0 :
      htmltext = "7640-05.htm"
   elif npcId == 7640 and int(st.get("cond"))==1 and st.getQuestItemsCount(BRYNTHEA_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING6_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS6_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL6_ID) and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT6_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY6_ID)==0 :
      htmltext = "7640-06.htm"
   elif npcId == 7640 and int(st.get("cond"))==1 and st.getQuestItemsCount(BRYNTHEA_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING6_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS6_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL6_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT6_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY6_ID) :
      htmltext = "7640-07.htm"
      st.giveItems(BRYNTHEA_ARCANA_ID,1)
      st.takeItems(CRYSTAL_OF_VICTORY6_ID,1)
   elif npcId == 7640 and int(st.get("cond"))==1 and st.getQuestItemsCount(BRYNTHEA_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING6_ID) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS6_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_FOUL6_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT6_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY6_ID)==0 :
      htmltext = "7640-08.htm"
   elif npcId == 7640 and int(st.get("cond"))==1 and st.getQuestItemsCount(BRYNTHEA_ARCANA_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_STARTING6_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS6_ID) and st.getQuestItemsCount(CRYSTAL_OF_FOUL6_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_DEFEAT6_ID)==0 and st.getQuestItemsCount(CRYSTAL_OF_VICTORY6_ID)==0 :
      htmltext = "7640-09.htm"
   elif npcId == 7640 and int(st.get("cond"))==1 and st.getQuestItemsCount(BRYNTHEA_ARCANA_ID)==1 :
      htmltext = "7640-10.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 5103 :
      if talker.master :
        c0 = talker.master
        if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS3_ID) :
          st.playSound("Itemsound.quest_middle")
   elif npcId == 5102 :
      if talker.master :
        c0 = talker.master
        if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS1_ID) :
          st.playSound("Itemsound.quest_middle")
   elif npcId == 5105 :
      if talker.master :
        c0 = talker.master
        if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS2_ID) :
          st.playSound("Itemsound.quest_middle")
   elif npcId == 5106 :
      if talker.master :
        c0 = talker.master
        if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS4_ID) :
          st.playSound("Itemsound.quest_middle")
   elif npcId == 5104 :
      if talker.master :
        c0 = talker.master
        if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS5_ID) :
          st.playSound("Itemsound.quest_middle")
   elif npcId == 5107 :
      if talker.master :
        c0 = talker.master
        if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS6_ID) :
          st.playSound("Itemsound.quest_middle")
   elif npcId == 12062 :
      c0 = GetLastAttacker()
      if c0.npc_class_id == @unicorn_racer :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS3_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT3_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS3_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING3_ID,1)
            st.despawnNpc(12062)
      elif c0.npc_class_id == @pako_the_cat :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS1_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT1_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS1_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING1_ID,1)
            st.despawnNpc(12062)
      elif c0.npc_class_id == @mimi_the_cat :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS2_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT2_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS2_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING2_ID,1)
            st.despawnNpc(12062)
      elif c0.npc_class_id == @unicorn_phantasm :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS4_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT4_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS4_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING4_ID,1)
            st.despawnNpc(12062)
      elif c0.npc_class_id == @silhouette_tilfo :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS6_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT6_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS6_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING6_ID,1)
            st.despawnNpc(12062)
      elif c0.npc_class_id == @shadow_turen :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS5_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT5_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS5_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING5_ID,1)
            st.despawnNpc(12062)
   elif npcId == 12061 :
      c0 = GetLastAttacker()
      if c0.npc_class_id == @unicorn_racer :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS3_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT3_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS3_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING3_ID,1)
      elif c0.npc_class_id == @pako_the_cat :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS1_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT1_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS1_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING1_ID,1)
      elif c0.npc_class_id == @mimi_the_cat :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS2_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT2_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS2_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING2_ID,1)
      elif c0.npc_class_id == @unicorn_phantasm :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS4_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT4_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS4_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING4_ID,1)
      elif c0.npc_class_id == @silhouette_tilfo :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS6_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT6_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS6_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING6_ID,1)
      elif c0.npc_class_id == @shadow_turen :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS5_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT5_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS5_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING5_ID,1)
   elif npcId == 12006 :
      c0 = GetLastAttacker()
      if c0.npc_class_id == @unicorn_racer :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS3_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT3_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS3_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING3_ID,1)
      elif c0.npc_class_id == @pako_the_cat :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS1_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT1_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS1_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING1_ID,1)
      elif c0.npc_class_id == @mimi_the_cat :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS2_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT2_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS2_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING2_ID,1)
      elif c0.npc_class_id == @unicorn_phantasm :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS4_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT4_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS4_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING4_ID,1)
      elif c0.npc_class_id == @silhouette_tilfo :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS6_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT6_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS6_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING6_ID,1)
      elif c0.npc_class_id == @shadow_turen :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS5_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT5_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS5_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING5_ID,1)
   elif npcId == 12007 :
      c0 = GetLastAttacker()
      if c0.npc_class_id == @unicorn_racer :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS3_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT3_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS3_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING3_ID,1)
      elif c0.npc_class_id == @pako_the_cat :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS1_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT1_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS1_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING1_ID,1)
      elif c0.npc_class_id == @mimi_the_cat :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS2_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT2_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS2_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING2_ID,1)
      elif c0.npc_class_id == @unicorn_phantasm :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS4_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT4_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS4_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING4_ID,1)
      elif c0.npc_class_id == @silhouette_tilfo :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS6_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT6_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS6_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING6_ID,1)
      elif c0.npc_class_id == @shadow_turen :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS5_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT5_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS5_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING5_ID,1)
   elif npcId == 12065 :
      c0 = GetLastAttacker()
      if c0.npc_class_id == @unicorn_racer :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS3_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT3_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS3_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING3_ID,1)
      elif c0.npc_class_id == @pako_the_cat :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS1_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT1_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS1_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING1_ID,1)
      elif c0.npc_class_id == @mimi_the_cat :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS2_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT2_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS2_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING2_ID,1)
      elif c0.npc_class_id == @unicorn_phantasm :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS4_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT4_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS4_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING4_ID,1)
      elif c0.npc_class_id == @silhouette_tilfo :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS6_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT6_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS6_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING6_ID,1)
      elif c0.npc_class_id == @shadow_turen :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS5_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT5_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS5_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING5_ID,1)
   elif npcId == 12064 :
      c0 = GetLastAttacker()
      if c0.npc_class_id == @unicorn_racer :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS3_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT3_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS3_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING3_ID,1)
      elif c0.npc_class_id == @pako_the_cat :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS1_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT1_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS1_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING1_ID,1)
      elif c0.npc_class_id == @mimi_the_cat :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS2_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT2_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS2_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING2_ID,1)
      elif c0.npc_class_id == @unicorn_phantasm :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS4_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT4_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS4_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING4_ID,1)
      elif c0.npc_class_id == @silhouette_tilfo :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS6_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT6_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS6_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING6_ID,1)
      elif c0.npc_class_id == @shadow_turen :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS5_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT5_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS5_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING5_ID,1)
   elif npcId == 12071 :
      c0 = GetLastAttacker()
      if c0.npc_class_id == @unicorn_racer :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS3_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT3_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS3_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING3_ID,1)
      elif c0.npc_class_id == @pako_the_cat :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS1_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT1_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS1_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING1_ID,1)
      elif c0.npc_class_id == @mimi_the_cat :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS2_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT2_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS2_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING2_ID,1)
      elif c0.npc_class_id == @unicorn_phantasm :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS4_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT4_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS4_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING4_ID,1)
      elif c0.npc_class_id == @silhouette_tilfo :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS6_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT6_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS6_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING6_ID,1)
      elif c0.npc_class_id == @shadow_turen :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS5_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT5_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS5_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING5_ID,1)
   elif npcId == 12070 :
      c0 = GetLastAttacker()
      if c0.npc_class_id == @unicorn_racer :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS3_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT3_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS3_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING3_ID,1)
      elif c0.npc_class_id == @pako_the_cat :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS1_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT1_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS1_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING1_ID,1)
      elif c0.npc_class_id == @mimi_the_cat :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS2_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT2_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS2_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING2_ID,1)
      elif c0.npc_class_id == @unicorn_phantasm :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS4_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT4_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS4_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING4_ID,1)
      elif c0.npc_class_id == @silhouette_tilfo :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS6_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT6_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS6_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING6_ID,1)
      elif c0.npc_class_id == @shadow_turen :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS5_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT5_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS5_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING5_ID,1)
   elif npcId == 12074 :
      c0 = GetLastAttacker()
      if c0.npc_class_id == @unicorn_racer :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS3_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT3_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS3_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING3_ID,1)
      elif c0.npc_class_id == @pako_the_cat :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS1_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT1_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS1_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING1_ID,1)
      elif c0.npc_class_id == @mimi_the_cat :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS2_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT2_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS2_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING2_ID,1)
      elif c0.npc_class_id == @unicorn_phantasm :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS4_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT4_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS4_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING4_ID,1)
      elif c0.npc_class_id == @silhouette_tilfo :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS6_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT6_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS6_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING6_ID,1)
      elif c0.npc_class_id == @shadow_turen :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS5_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT5_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS5_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING5_ID,1)
   elif npcId == 12073 :
      c0 = GetLastAttacker()
      if c0.npc_class_id == @unicorn_racer :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS3_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT3_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS3_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING3_ID,1)
      elif c0.npc_class_id == @pako_the_cat :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS1_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT1_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS1_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING1_ID,1)
      elif c0.npc_class_id == @mimi_the_cat :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS2_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT2_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS2_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING2_ID,1)
      elif c0.npc_class_id == @unicorn_phantasm :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS4_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT4_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS4_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING4_ID,1)
      elif c0.npc_class_id == @silhouette_tilfo :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS6_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT6_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS6_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING6_ID,1)
      elif c0.npc_class_id == @shadow_turen :
        if n0.master :
          talker = n0.master
          if int(st.get("cond")) and st.getQuestItemsCount(CRYSTAL_OF_INPROGRESS5_ID) :
            st.giveItems(CRYSTAL_OF_DEFEAT5_ID,1)
            st.takeItems(CRYSTAL_OF_INPROGRESS5_ID,1)
            st.takeItems(CRYSTAL_OF_STARTING5_ID,1)
   elif npcId == 176 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST5_ID) and st.getQuestItemsCount(FANGS_OF_WYRM_ID) < 30 :
        if st.getRandom(2) == 1 :
          if st.getQuestItemsCount(FANGS_OF_WYRM_ID) == 29 :
            st.giveItems(FANGS_OF_WYRM_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(FANGS_OF_WYRM_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 552 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST3_ID) and st.getQuestItemsCount(CRIMSON_BLOODSTONE_ID) < 30 :
        if st.getRandom(10) < 6 :
          if st.getQuestItemsCount(CRIMSON_BLOODSTONE_ID) == 29 :
            st.giveItems(CRIMSON_BLOODSTONE_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(CRIMSON_BLOODSTONE_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 553 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST4_ID) and st.getQuestItemsCount(TUSK_OF_WINDSUS_ID) < 30 :
        if st.getRandom(10) < 7 :
          if st.getQuestItemsCount(TUSK_OF_WINDSUS_ID) == 29 :
            st.giveItems(TUSK_OF_WINDSUS_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(TUSK_OF_WINDSUS_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 89 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST5_ID) and st.getQuestItemsCount(WINGS_OF_DRONEANT_ID) < 30 :
        if st.getRandom(10) < 6 :
          if st.getQuestItemsCount(WINGS_OF_DRONEANT_ID) == 29 :
            st.giveItems(WINGS_OF_DRONEANT_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(WINGS_OF_DRONEANT_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 90 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST5_ID) and st.getQuestItemsCount(WINGS_OF_DRONEANT_ID) < 30 :
        if st.getRandom(10) < 6 :
          if st.getQuestItemsCount(WINGS_OF_DRONEANT_ID) == 29 :
            st.giveItems(WINGS_OF_DRONEANT_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(WINGS_OF_DRONEANT_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 192 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST4_ID) and st.getQuestItemsCount(TALONS_OF_TYRANT_ID) < 30 :
        if st.getRandom(10) < 5 :
          if st.getQuestItemsCount(TALONS_OF_TYRANT_ID) == 29 :
            st.giveItems(TALONS_OF_TYRANT_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(TALONS_OF_TYRANT_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 193 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST4_ID) and st.getQuestItemsCount(TALONS_OF_TYRANT_ID) < 30 :
        if st.getRandom(10) < 5 :
          if st.getQuestItemsCount(TALONS_OF_TYRANT_ID) == 29 :
            st.giveItems(TALONS_OF_TYRANT_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(TALONS_OF_TYRANT_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 563 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST2_ID) and st.getQuestItemsCount(SHARDS_OF_MANASHEN_ID) < 30 :
        if st.getRandom(10) < 8 :
          if st.getQuestItemsCount(SHARDS_OF_MANASHEN_ID) == 29 :
            st.giveItems(SHARDS_OF_MANASHEN_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(SHARDS_OF_MANASHEN_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 555 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST1_ID) and st.getQuestItemsCount(SAC_OF_REDSPORES_ID) < 30 :
        if st.getRandom(10) < 8 :
          if st.getQuestItemsCount(SAC_OF_REDSPORES_ID) == 29 :
            st.giveItems(SAC_OF_REDSPORES_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(SAC_OF_REDSPORES_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 600 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST2_ID) and st.getQuestItemsCount(KARULBUGBEAR_TOTEM_ID) < 30 :
        if st.getRandom(10) < 8 :
          if st.getQuestItemsCount(KARULBUGBEAR_TOTEM_ID) == 29 :
            st.giveItems(KARULBUGBEAR_TOTEM_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(KARULBUGBEAR_TOTEM_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 267 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST3_ID) and st.getQuestItemsCount(BREKAORC_TOTEM_ID) < 30 :
        if st.getRandom(20) < 5 :
          if st.getQuestItemsCount(BREKAORC_TOTEM_ID) == 29 :
            st.giveItems(BREKAORC_TOTEM_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(BREKAORC_TOTEM_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 268 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST3_ID) and st.getQuestItemsCount(BREKAORC_TOTEM_ID) < 30 :
        if st.getRandom(20) < 5 :
          if st.getQuestItemsCount(BREKAORC_TOTEM_ID) == 29 :
            st.giveItems(BREKAORC_TOTEM_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(BREKAORC_TOTEM_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 271 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST3_ID) and st.getQuestItemsCount(BREKAORC_TOTEM_ID) < 30 :
        if st.getRandom(20) < 5 :
          if st.getQuestItemsCount(BREKAORC_TOTEM_ID) == 29 :
            st.giveItems(BREKAORC_TOTEM_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(BREKAORC_TOTEM_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 269 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST3_ID) and st.getQuestItemsCount(BREKAORC_TOTEM_ID) < 30 :
        if st.getRandom(10) < 5 :
          if st.getQuestItemsCount(BREKAORC_TOTEM_ID) == 29 :
            st.giveItems(BREKAORC_TOTEM_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(BREKAORC_TOTEM_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 270 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST3_ID) and st.getQuestItemsCount(BREKAORC_TOTEM_ID) < 30 :
        if st.getRandom(10) < 5 :
          if st.getQuestItemsCount(BREKAORC_TOTEM_ID) == 29 :
            st.giveItems(BREKAORC_TOTEM_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(BREKAORC_TOTEM_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 577 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST1_ID) and st.getQuestItemsCount(LETOLIZARDMAN_AMULET_ID) < 30 :
        if st.getRandom(20) < 5 :
          if st.getQuestItemsCount(LETOLIZARDMAN_AMULET_ID) == 29 :
            st.giveItems(LETOLIZARDMAN_AMULET_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(LETOLIZARDMAN_AMULET_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 578 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST1_ID) and st.getQuestItemsCount(LETOLIZARDMAN_AMULET_ID) < 30 :
        if st.getRandom(20) < 5 :
          if st.getQuestItemsCount(LETOLIZARDMAN_AMULET_ID) == 29 :
            st.giveItems(LETOLIZARDMAN_AMULET_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(LETOLIZARDMAN_AMULET_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 579 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST1_ID) and st.getQuestItemsCount(LETOLIZARDMAN_AMULET_ID) < 30 :
        if st.getRandom(10) < 5 :
          if st.getQuestItemsCount(LETOLIZARDMAN_AMULET_ID) == 29 :
            st.giveItems(LETOLIZARDMAN_AMULET_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(LETOLIZARDMAN_AMULET_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 580 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST1_ID) and st.getQuestItemsCount(LETOLIZARDMAN_AMULET_ID) < 30 :
        if st.getRandom(10) < 5 :
          if st.getQuestItemsCount(LETOLIZARDMAN_AMULET_ID) == 29 :
            st.giveItems(LETOLIZARDMAN_AMULET_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(LETOLIZARDMAN_AMULET_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 581 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST1_ID) and st.getQuestItemsCount(LETOLIZARDMAN_AMULET_ID) < 30 :
        if st.getRandom(20) < 15 :
          if st.getQuestItemsCount(LETOLIZARDMAN_AMULET_ID) == 29 :
            st.giveItems(LETOLIZARDMAN_AMULET_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(LETOLIZARDMAN_AMULET_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 582 :
      if int(st.get("cond")) and st.getQuestItemsCount(GALATEAS_LETTER_ID) == 0 and st.getQuestItemsCount(LARS_LIST1_ID) and st.getQuestItemsCount(LETOLIZARDMAN_AMULET_ID) < 30 :
        if st.getRandom(20) < 15 :
          if st.getQuestItemsCount(LETOLIZARDMAN_AMULET_ID) == 29 :
            st.giveItems(LETOLIZARDMAN_AMULET_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(LETOLIZARDMAN_AMULET_ID,1)
            st.playSound("Itemsound.quest_itemget")
   return

QUEST       = Quest(230,"230_TestOfSummoner","Test Of Summoner")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7634)

STARTING.addTalkId(7634)

STARTED.addTalkId(7063)
STARTED.addTalkId(7634)
STARTED.addTalkId(7635)
STARTED.addTalkId(7636)
STARTED.addTalkId(7637)
STARTED.addTalkId(7638)
STARTED.addTalkId(7639)
STARTED.addTalkId(7640)

STARTED.addKillId(12006)
STARTED.addKillId(12007)
STARTED.addKillId(12061)
STARTED.addKillId(12062)
STARTED.addKillId(12064)
STARTED.addKillId(12065)
STARTED.addKillId(12070)
STARTED.addKillId(12071)
STARTED.addKillId(12073)
STARTED.addKillId(12074)
STARTED.addKillId(176)
STARTED.addKillId(192)
STARTED.addKillId(193)
STARTED.addKillId(267)
STARTED.addKillId(268)
STARTED.addKillId(269)
STARTED.addKillId(270)
STARTED.addKillId(271)
STARTED.addKillId(5102)
STARTED.addKillId(5103)
STARTED.addKillId(5104)
STARTED.addKillId(5105)
STARTED.addKillId(5106)
STARTED.addKillId(5107)
STARTED.addKillId(552)
STARTED.addKillId(553)
STARTED.addKillId(555)
STARTED.addKillId(563)
STARTED.addKillId(577)
STARTED.addKillId(578)
STARTED.addKillId(579)
STARTED.addKillId(580)
STARTED.addKillId(581)
STARTED.addKillId(582)
STARTED.addKillId(600)
STARTED.addKillId(89)
STARTED.addKillId(90)

STARTED.addQuestDrop(7063,LARS_LIST1_ID,1)
STARTED.addQuestDrop(7063,LARS_LIST2_ID,1)
STARTED.addQuestDrop(7063,LARS_LIST3_ID,1)
STARTED.addQuestDrop(7063,LARS_LIST4_ID,1)
STARTED.addQuestDrop(7063,LARS_LIST5_ID,1)
STARTED.addQuestDrop(7635,ALMORS_ARCANA_ID,1)
STARTED.addQuestDrop(7638,BASILLIA_ARCANA_ID,1)
STARTED.addQuestDrop(7636,CAMONIELL_ARCANA_ID,1)
STARTED.addQuestDrop(7639,CELESTIEL_ARCANA_ID,1)
STARTED.addQuestDrop(7637,BELTHUS_ARCANA_ID,1)
STARTED.addQuestDrop(7640,BRYNTHEA_ARCANA_ID,1)
STARTED.addQuestDrop(577,LETOLIZARDMAN_AMULET_ID,1)
STARTED.addQuestDrop(578,LETOLIZARDMAN_AMULET_ID,1)
STARTED.addQuestDrop(579,LETOLIZARDMAN_AMULET_ID,1)
STARTED.addQuestDrop(580,LETOLIZARDMAN_AMULET_ID,1)
STARTED.addQuestDrop(581,LETOLIZARDMAN_AMULET_ID,1)
STARTED.addQuestDrop(582,LETOLIZARDMAN_AMULET_ID,1)
STARTED.addQuestDrop(555,SAC_OF_REDSPORES_ID,1)
STARTED.addQuestDrop(600,KARULBUGBEAR_TOTEM_ID,1)
STARTED.addQuestDrop(563,SHARDS_OF_MANASHEN_ID,1)
STARTED.addQuestDrop(267,BREKAORC_TOTEM_ID,1)
STARTED.addQuestDrop(268,BREKAORC_TOTEM_ID,1)
STARTED.addQuestDrop(271,BREKAORC_TOTEM_ID,1)
STARTED.addQuestDrop(269,BREKAORC_TOTEM_ID,1)
STARTED.addQuestDrop(270,BREKAORC_TOTEM_ID,1)
STARTED.addQuestDrop(552,CRIMSON_BLOODSTONE_ID,1)
STARTED.addQuestDrop(192,TALONS_OF_TYRANT_ID,1)
STARTED.addQuestDrop(193,TALONS_OF_TYRANT_ID,1)
STARTED.addQuestDrop(553,TUSK_OF_WINDSUS_ID,1)
STARTED.addQuestDrop(89,WINGS_OF_DRONEANT_ID,1)
STARTED.addQuestDrop(90,WINGS_OF_DRONEANT_ID,1)
STARTED.addQuestDrop(176,FANGS_OF_WYRM_ID,1)
STARTED.addQuestDrop(7634,GALATEAS_LETTER_ID,1)
STARTED.addQuestDrop(12062,CRYSTAL_OF_DEFEAT1_ID,1)
STARTED.addQuestDrop(12061,CRYSTAL_OF_DEFEAT1_ID,1)
STARTED.addQuestDrop(12006,CRYSTAL_OF_DEFEAT1_ID,1)
STARTED.addQuestDrop(12007,CRYSTAL_OF_DEFEAT1_ID,1)
STARTED.addQuestDrop(12065,CRYSTAL_OF_DEFEAT1_ID,1)
STARTED.addQuestDrop(12064,CRYSTAL_OF_DEFEAT1_ID,1)
STARTED.addQuestDrop(12071,CRYSTAL_OF_DEFEAT1_ID,1)
STARTED.addQuestDrop(12070,CRYSTAL_OF_DEFEAT1_ID,1)
STARTED.addQuestDrop(12074,CRYSTAL_OF_DEFEAT1_ID,1)
STARTED.addQuestDrop(12073,CRYSTAL_OF_DEFEAT1_ID,1)
STARTED.addQuestDrop(7063,BEGINNERS_ARCANA_ID,1)
STARTED.addQuestDrop(12062,CRYSTAL_OF_DEFEAT2_ID,1)
STARTED.addQuestDrop(12061,CRYSTAL_OF_DEFEAT2_ID,1)
STARTED.addQuestDrop(12006,CRYSTAL_OF_DEFEAT2_ID,1)
STARTED.addQuestDrop(12007,CRYSTAL_OF_DEFEAT2_ID,1)
STARTED.addQuestDrop(12065,CRYSTAL_OF_DEFEAT2_ID,1)
STARTED.addQuestDrop(12064,CRYSTAL_OF_DEFEAT2_ID,1)
STARTED.addQuestDrop(12071,CRYSTAL_OF_DEFEAT2_ID,1)
STARTED.addQuestDrop(12070,CRYSTAL_OF_DEFEAT2_ID,1)
STARTED.addQuestDrop(12074,CRYSTAL_OF_DEFEAT2_ID,1)
STARTED.addQuestDrop(12073,CRYSTAL_OF_DEFEAT2_ID,1)
STARTED.addQuestDrop(12062,CRYSTAL_OF_DEFEAT3_ID,1)
STARTED.addQuestDrop(12061,CRYSTAL_OF_DEFEAT3_ID,1)
STARTED.addQuestDrop(12006,CRYSTAL_OF_DEFEAT3_ID,1)
STARTED.addQuestDrop(12007,CRYSTAL_OF_DEFEAT3_ID,1)
STARTED.addQuestDrop(12065,CRYSTAL_OF_DEFEAT3_ID,1)
STARTED.addQuestDrop(12064,CRYSTAL_OF_DEFEAT3_ID,1)
STARTED.addQuestDrop(12071,CRYSTAL_OF_DEFEAT3_ID,1)
STARTED.addQuestDrop(12070,CRYSTAL_OF_DEFEAT3_ID,1)
STARTED.addQuestDrop(12074,CRYSTAL_OF_DEFEAT3_ID,1)
STARTED.addQuestDrop(12073,CRYSTAL_OF_DEFEAT3_ID,1)
STARTED.addQuestDrop(12062,CRYSTAL_OF_DEFEAT4_ID,1)
STARTED.addQuestDrop(12061,CRYSTAL_OF_DEFEAT4_ID,1)
STARTED.addQuestDrop(12006,CRYSTAL_OF_DEFEAT4_ID,1)
STARTED.addQuestDrop(12007,CRYSTAL_OF_DEFEAT4_ID,1)
STARTED.addQuestDrop(12065,CRYSTAL_OF_DEFEAT4_ID,1)
STARTED.addQuestDrop(12064,CRYSTAL_OF_DEFEAT4_ID,1)
STARTED.addQuestDrop(12071,CRYSTAL_OF_DEFEAT4_ID,1)
STARTED.addQuestDrop(12070,CRYSTAL_OF_DEFEAT4_ID,1)
STARTED.addQuestDrop(12074,CRYSTAL_OF_DEFEAT4_ID,1)
STARTED.addQuestDrop(12073,CRYSTAL_OF_DEFEAT4_ID,1)
STARTED.addQuestDrop(12062,CRYSTAL_OF_DEFEAT5_ID,1)
STARTED.addQuestDrop(12061,CRYSTAL_OF_DEFEAT5_ID,1)
STARTED.addQuestDrop(12006,CRYSTAL_OF_DEFEAT5_ID,1)
STARTED.addQuestDrop(12007,CRYSTAL_OF_DEFEAT5_ID,1)
STARTED.addQuestDrop(12065,CRYSTAL_OF_DEFEAT5_ID,1)
STARTED.addQuestDrop(12064,CRYSTAL_OF_DEFEAT5_ID,1)
STARTED.addQuestDrop(12071,CRYSTAL_OF_DEFEAT5_ID,1)
STARTED.addQuestDrop(12070,CRYSTAL_OF_DEFEAT5_ID,1)
STARTED.addQuestDrop(12074,CRYSTAL_OF_DEFEAT5_ID,1)
STARTED.addQuestDrop(12073,CRYSTAL_OF_DEFEAT5_ID,1)
STARTED.addQuestDrop(12062,CRYSTAL_OF_DEFEAT6_ID,1)
STARTED.addQuestDrop(12061,CRYSTAL_OF_DEFEAT6_ID,1)
STARTED.addQuestDrop(12006,CRYSTAL_OF_DEFEAT6_ID,1)
STARTED.addQuestDrop(12007,CRYSTAL_OF_DEFEAT6_ID,1)
STARTED.addQuestDrop(12065,CRYSTAL_OF_DEFEAT6_ID,1)
STARTED.addQuestDrop(12064,CRYSTAL_OF_DEFEAT6_ID,1)
STARTED.addQuestDrop(12071,CRYSTAL_OF_DEFEAT6_ID,1)
STARTED.addQuestDrop(12070,CRYSTAL_OF_DEFEAT6_ID,1)
STARTED.addQuestDrop(12074,CRYSTAL_OF_DEFEAT6_ID,1)
STARTED.addQuestDrop(12073,CRYSTAL_OF_DEFEAT6_ID,1)
STARTED.addQuestDrop(7636,CRYSTAL_OF_STARTING3_ID,1)
STARTED.addQuestDrop(7635,CRYSTAL_OF_STARTING1_ID,1)
STARTED.addQuestDrop(7638,CRYSTAL_OF_STARTING2_ID,1)
STARTED.addQuestDrop(7639,CRYSTAL_OF_STARTING4_ID,1)
STARTED.addQuestDrop(7640,CRYSTAL_OF_STARTING6_ID,1)
STARTED.addQuestDrop(7637,CRYSTAL_OF_STARTING5_ID,1)
