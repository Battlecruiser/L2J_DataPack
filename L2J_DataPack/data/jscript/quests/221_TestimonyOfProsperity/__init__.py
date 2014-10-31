# Maked by Mr. Have fun! Version 0.2
print "importing quests: 221: Testimony Of Prosperity"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_PROSPERITY_ID = 3238
RING_OF_TESTIMONY1_ID = 3239
RING_OF_TESTIMONY2_ID = 3240
OLD_ACCOUNT_BOOK_ID = 3241
BLESSED_SEED_ID = 3242
RECIPE_OF_EMILLY_ID = 3243
LILITH_ELVEN_WAFER_ID = 3244
MAPHR_TABLET_FRAGMENT_ID = 3245
COLLECTION_LICENSE_ID = 3246
LOCKIRINS_NOTICE1_ID = 3247
LOCKIRINS_NOTICE2_ID = 3248
LOCKIRINS_NOTICE3_ID = 3249
LOCKIRINS_NOTICE4_ID = 3250
LOCKIRINS_NOTICE5_ID = 3251
CONTRIBUTION_OF_CHALI_ID = 3252
CONTRIBUTION_OF_MION_ID = 3253
CONTRIBUTION_OF_MARIFE_ID = 3254
MARIFES_REQUEST_ID = 3255
CONTRIBUTION_OF_TOMA_ID = 3256
RECEIPT_OF_BOLTER_ID = 3257
RECEIPT_OF_CONTRIBUTION1_ID = 3258
RECEIPT_OF_CONTRIBUTION2_ID = 3259
RECEIPT_OF_CONTRIBUTION3_ID = 3260
RECEIPT_OF_CONTRIBUTION4_ID = 3261
RECEIPT_OF_CONTRIBUTION5_ID = 3262
PROCURATION_OF_TOROCCO_ID = 3263
BRIGHTS_LIST_ID = 3264
MANDRAGORA_PETAL_ID = 3265
CRIMSON_MOSS_ID = 3266
MANDRAGORA_BOUQUET_ID = 3267
PARMANS_INSTRUCTIONS_ID = 3268
PARMANS_LETTER_ID = 3269
CLAY_DOUGH_ID = 3270
PATTERN_OF_KEYHOLE_ID = 3271
NIKOLAS_LIST_ID = 3272
STAKATO_SHELL_ID = 3273
INPICIO_SAC_ID = 3274
SPIDER_THORN_ID = 3275
CRYSTAL_BROOCH_ID = 3428
ADENA_ID = 57
ANIMAL_SKIN_ID = 1867
RP_TITAN_KEY_ID = 3023
KEY_OF_TITAN_ID = 3030

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        htmltext = "7104-04.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.giveItems(RING_OF_TESTIMONY1_ID,1)
    elif event == "7104_1" :
          if st.getPlayer().getLevel() < 38 :
            htmltext = "7104-07.htm"
            st.takeItems(RING_OF_TESTIMONY1_ID,1)
            st.takeItems(OLD_ACCOUNT_BOOK_ID,1)
            st.takeItems(BLESSED_SEED_ID,1)
            st.takeItems(RECIPE_OF_EMILLY_ID,1)
            st.takeItems(LILITH_ELVEN_WAFER_ID,1)
            st.giveItems(PARMANS_INSTRUCTIONS_ID,1)
          else:
            htmltext = "7104-08.htm"
            st.takeItems(RING_OF_TESTIMONY1_ID,1)
            st.takeItems(OLD_ACCOUNT_BOOK_ID,1)
            st.takeItems(BLESSED_SEED_ID,1)
            st.takeItems(RECIPE_OF_EMILLY_ID,1)
            st.takeItems(LILITH_ELVEN_WAFER_ID,1)
            st.giveItems(RING_OF_TESTIMONY2_ID,1)
            st.giveItems(PARMANS_LETTER_ID,1)
    elif event == "7531_1" and st.getQuestItemsCount(COLLECTION_LICENSE_ID) :
          htmltext = "7531-04.htm"
    elif event == "7531_1" :
          htmltext = "7531-02.htm"
    elif event == "7531_2" and st.getQuestItemsCount(COLLECTION_LICENSE_ID) == 0 :
          htmltext = "7531-03.htm"
          st.giveItems(COLLECTION_LICENSE_ID,1)
          st.giveItems(LOCKIRINS_NOTICE1_ID,1)
          st.giveItems(LOCKIRINS_NOTICE2_ID,1)
          st.giveItems(LOCKIRINS_NOTICE3_ID,1)
          st.giveItems(LOCKIRINS_NOTICE4_ID,1)
          st.giveItems(LOCKIRINS_NOTICE5_ID,1)
    elif event == "7534_1" :
          if st.getQuestItemsCount(ADENA_ID) < 5000 :
            htmltext = "7534-03a.htm"
          else:
            htmltext = "7534-03b.htm"
            st.takeItems(ADENA_ID,5000)
            st.giveItems(RECEIPT_OF_CONTRIBUTION3_ID,1)
            st.takeItems(PROCURATION_OF_TOROCCO_ID,1)
    elif event == "7555_1" :
          htmltext = "7555-02.htm"
          st.giveItems(PROCURATION_OF_TOROCCO_ID,1)
    elif event == "7597_1" :
          htmltext = "7597-02.htm"
          st.giveItems(BLESSED_SEED_ID,1)
    elif event == "7005_1" :
          htmltext = "7005-02.htm"
    elif event == "7005_2" :
          htmltext = "7005-03.htm"
    elif event == "7005_3" :
          htmltext = "7005-04.htm"
          st.giveItems(CRYSTAL_BROOCH_ID,1)
    elif event == "7368_1" :
          htmltext = "7368-02.htm"
    elif event == "7368_2" :
          htmltext = "7368-03.htm"
          st.giveItems(LILITH_ELVEN_WAFER_ID,1)
          st.takeItems(CRYSTAL_BROOCH_ID,1)
    elif event == "7466_1" :
          htmltext = "7466-02.htm"
    elif event == "7466_2" :
          htmltext = "7466-03.htm"
          st.giveItems(BRIGHTS_LIST_ID,1)
    elif event == "7620_1" :
          htmltext = "7620-02.htm"
    elif event == "7620_2" :
          htmltext = "7620-03.htm"
          st.giveItems(RECIPE_OF_EMILLY_ID,1)
          st.takeItems(MANDRAGORA_BOUQUET_ID,1)
    elif event == "7621_1" :
          htmltext = "7621-02.htm"
    elif event == "7621_2" :
          htmltext = "7621-03.htm"
    elif event == "7621_3" :
          htmltext = "7621-04.htm"
          st.giveItems(CLAY_DOUGH_ID,1)
    elif event == "7622_1" :
          htmltext = "7622-02.htm"
          st.giveItems(PATTERN_OF_KEYHOLE_ID,1)
          st.takeItems(CLAY_DOUGH_ID,1)
    elif event == "7622_2" :
          htmltext = "7622-04.htm"
          st.takeItems(NIKOLAS_LIST_ID,1)
          st.takeItems(RP_TITAN_KEY_ID,1)
          st.takeItems(STAKATO_SHELL_ID,st.getQuestItemsCount(STAKATO_SHELL_ID))
          st.takeItems(INPICIO_SAC_ID,st.getQuestItemsCount(INPICIO_SAC_ID))
          st.takeItems(SPIDER_THORN_ID,st.getQuestItemsCount(SPIDER_THORN_ID))
          st.giveItems(MAPHR_TABLET_FRAGMENT_ID,1)
          st.takeItems(KEY_OF_TITAN_ID,1)
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
   if npcId == 7104 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if st.getPlayer().getRace().ordinal() != 4 :
          htmltext = "7104-01.htm"
          st.exitQuest(1)
        else:
          if st.getPlayer().getLevel() < 37 :
            htmltext = "7104-02.htm"
            st.exitQuest(1)
          else:
            htmltext = "7104-03.htm"
   elif npcId == 7104 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7104 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID)==1 :
        if st.getQuestItemsCount(OLD_ACCOUNT_BOOK_ID) and st.getQuestItemsCount(BLESSED_SEED_ID) and st.getQuestItemsCount(RECIPE_OF_EMILLY_ID) and st.getQuestItemsCount(LILITH_ELVEN_WAFER_ID) :
          htmltext = "7104-06.htm"
        else:
          htmltext = "7104-05.htm"
   elif npcId == 7104 and int(st.get("cond"))>=1 and st.getQuestItemsCount(PARMANS_INSTRUCTIONS_ID)==1 :
        if st.getPlayer().getLevel() < 38 :
          htmltext = "7104-09.htm"
        else:
          htmltext = "7104-10.htm"
          st.giveItems(RING_OF_TESTIMONY2_ID,1)
          st.takeItems(PARMANS_INSTRUCTIONS_ID,1)
          st.giveItems(PARMANS_LETTER_ID,1)
   elif npcId == 7104 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID) and st.getQuestItemsCount(PARMANS_LETTER_ID) and st.getQuestItemsCount(MAPHR_TABLET_FRAGMENT_ID) == 0 :
        htmltext = "7104-11.htm"
   elif npcId == 7104 and int(st.get("cond"))>=1 and (st.getQuestItemsCount(CLAY_DOUGH_ID) or st.getQuestItemsCount(PATTERN_OF_KEYHOLE_ID) or st.getQuestItemsCount(NIKOLAS_LIST_ID)) and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID)==1 :
        htmltext = "7104-12.htm"
   elif npcId == 7104 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID) and st.getQuestItemsCount(MAPHR_TABLET_FRAGMENT_ID) :
          st.addExpAndSp(12969,1000)
          st.giveItems(7562,16)
          st.takeItems(RING_OF_TESTIMONY2_ID,1)
          st.giveItems(MARK_OF_PROSPERITY_ID,1)
          st.takeItems(MAPHR_TABLET_FRAGMENT_ID,1)
          htmltext = "7104-13.htm"
          st.set("cond","0")
          st.set("onlyone","1")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
   elif npcId == 7531 and int(st.get("cond"))>=1 and st.getQuestItemsCount(OLD_ACCOUNT_BOOK_ID) == 0 and st.getQuestItemsCount(COLLECTION_LICENSE_ID) == 0 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID)==1 :
        htmltext = "7531-01.htm"
   elif npcId == 7531 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) :
        if st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION1_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION2_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION3_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION4_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION5_ID) :
          htmltext = "7531-05.htm"
          st.giveItems(OLD_ACCOUNT_BOOK_ID,1)
          st.takeItems(COLLECTION_LICENSE_ID,1)
          st.takeItems(RECEIPT_OF_CONTRIBUTION1_ID,1)
          st.takeItems(RECEIPT_OF_CONTRIBUTION2_ID,1)
          st.takeItems(RECEIPT_OF_CONTRIBUTION3_ID,1)
          st.takeItems(RECEIPT_OF_CONTRIBUTION4_ID,1)
          st.takeItems(RECEIPT_OF_CONTRIBUTION5_ID,1)
        else:
          htmltext = "7531-04.htm"
   elif npcId == 7531 and int(st.get("cond")) >= 1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(OLD_ACCOUNT_BOOK_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID)==0 :
        htmltext = "7531-06.htm"
   elif npcId == 7531 and int(st.get("cond")) >= 1 and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID) == 1 :
        htmltext = "7531-07.htm"
   elif npcId == 7532 and int(st.get("cond")) >= 1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(LOCKIRINS_NOTICE1_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION1_ID) == 0 and st.getQuestItemsCount(CONTRIBUTION_OF_CHALI_ID) == 0 :
        htmltext = "7532-01.htm"
        st.takeItems(LOCKIRINS_NOTICE1_ID,1)
   elif npcId == 7532 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION1_ID) == 0 and st.getQuestItemsCount(CONTRIBUTION_OF_CHALI_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE1_ID) == 0 :
        htmltext = "7532-02.htm"
   elif npcId == 7532 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(CONTRIBUTION_OF_CHALI_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION1_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE1_ID) == 0 :
        htmltext = "7532-03.htm"
        st.giveItems(RECEIPT_OF_CONTRIBUTION1_ID,1)
        st.takeItems(CONTRIBUTION_OF_CHALI_ID,1)
   elif npcId == 7532 and int(st.get("cond"))>=1 and st.getQuestItemsCount(CONTRIBUTION_OF_CHALI_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE1_ID) == 0 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION1_ID) :
        htmltext = "7532-04.htm"
   elif npcId == 7533 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(LOCKIRINS_NOTICE2_ID) and st.getQuestItemsCount(LOCKIRINS_NOTICE2_ID)==1 and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION2_ID)==0 and (st.getQuestItemsCount(CONTRIBUTION_OF_MION_ID)+st.getQuestItemsCount(CONTRIBUTION_OF_MARIFE_ID)<2) :
        htmltext = "7533-01.htm"
        st.takeItems(LOCKIRINS_NOTICE2_ID,1)
   elif npcId == 7533 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(LOCKIRINS_NOTICE2_ID)==0 and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION2_ID)==0 and (st.getQuestItemsCount(CONTRIBUTION_OF_MION_ID)+st.getQuestItemsCount(CONTRIBUTION_OF_MARIFE_ID)<2) :
        htmltext = "7533-02.htm"
   elif npcId == 7533 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION2_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE2_ID) == 0 and (st.getQuestItemsCount(CONTRIBUTION_OF_MION_ID)+st.getQuestItemsCount(CONTRIBUTION_OF_MARIFE_ID)>=2) :
        htmltext = "7533-03.htm"
        st.takeItems(CONTRIBUTION_OF_MARIFE_ID,1)
        st.giveItems(RECEIPT_OF_CONTRIBUTION2_ID,1)
        st.takeItems(CONTRIBUTION_OF_MION_ID,1)
   elif npcId == 7533 and int(st.get("cond"))>=1 and st.getQuestItemsCount(LOCKIRINS_NOTICE2_ID)==0 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION2_ID) and (st.getQuestItemsCount(CONTRIBUTION_OF_MION_ID)+st.getQuestItemsCount(CONTRIBUTION_OF_MARIFE_ID)<2) :
        htmltext = "7533-04.htm"
   elif npcId == 7534 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(LOCKIRINS_NOTICE3_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION3_ID) == 0 and st.getQuestItemsCount(PROCURATION_OF_TOROCCO_ID) == 0 :
        htmltext = "7534-01.htm"
        st.takeItems(LOCKIRINS_NOTICE3_ID,1)
   elif npcId == 7534 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION3_ID) == 0 and st.getQuestItemsCount(PROCURATION_OF_TOROCCO_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE3_ID) == 0 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) :
        htmltext = "7534-02.htm"
   elif npcId == 7534 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(PROCURATION_OF_TOROCCO_ID) and st.getQuestItemsCount(LOCKIRINS_NOTICE3_ID) == 0 and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION3_ID) == 0 :
        htmltext = "7534-03.htm"
   elif npcId == 7534 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION3_ID) and st.getQuestItemsCount(PROCURATION_OF_TOROCCO_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE3_ID) == 0 :
        htmltext = "7534-04.htm"
   elif npcId == 7535 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(LOCKIRINS_NOTICE4_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION4_ID) == 0 and st.getQuestItemsCount(RECEIPT_OF_BOLTER_ID) == 0 :
        htmltext = "7535-01.htm"
        st.takeItems(LOCKIRINS_NOTICE4_ID,1)
   elif npcId == 7535 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION4_ID) == 0 and st.getQuestItemsCount(RECEIPT_OF_BOLTER_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE4_ID) == 0 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) :
        htmltext = "7535-02.htm"
   elif npcId == 7535 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(RECEIPT_OF_BOLTER_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION4_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE4_ID) == 0 :
        htmltext = "7535-03.htm"
        st.giveItems(RECEIPT_OF_CONTRIBUTION4_ID,1)
        st.takeItems(RECEIPT_OF_BOLTER_ID,1)
   elif npcId == 7535 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION4_ID) and st.getQuestItemsCount(RECEIPT_OF_BOLTER_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE4_ID) == 0 :
        htmltext = "7535-04.htm"
   elif npcId == 7536 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(LOCKIRINS_NOTICE5_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION5_ID) == 0 and st.getQuestItemsCount(CONTRIBUTION_OF_TOMA_ID) == 0 :
        htmltext = "7536-01.htm"
        st.takeItems(LOCKIRINS_NOTICE5_ID,1)
   elif npcId == 7536 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION5_ID) == 0 and st.getQuestItemsCount(CONTRIBUTION_OF_TOMA_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE5_ID) == 0 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) :
        htmltext = "7536-02.htm"
   elif npcId == 7536 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(CONTRIBUTION_OF_TOMA_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION5_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE5_ID) == 0 :
        htmltext = "7536-03.htm"
        st.giveItems(RECEIPT_OF_CONTRIBUTION5_ID,1)
        st.takeItems(CONTRIBUTION_OF_TOMA_ID,1)
   elif npcId == 7536 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION5_ID) and st.getQuestItemsCount(CONTRIBUTION_OF_TOMA_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE5_ID) == 0 :
        htmltext = "7536-04.htm"
   elif npcId == 7517 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION1_ID) == 0 and st.getQuestItemsCount(CONTRIBUTION_OF_CHALI_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE1_ID) == 0 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) :
        htmltext = "7517-01.htm"
        st.giveItems(CONTRIBUTION_OF_CHALI_ID,1)
   elif npcId == 7517 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(CONTRIBUTION_OF_CHALI_ID) and st.getQuestItemsCount(LOCKIRINS_NOTICE1_ID) == 0 and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION1_ID) == 0 :
        htmltext = "7517-02.htm"
   elif npcId == 7519 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION2_ID) == 0 and st.getQuestItemsCount(CONTRIBUTION_OF_MION_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE2_ID) == 0 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) :
        htmltext = "7519-01.htm"
        st.giveItems(CONTRIBUTION_OF_MION_ID,1)
   elif npcId == 7519 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(CONTRIBUTION_OF_MION_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION2_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE2_ID) == 0 :
        htmltext = "7519-02.htm"
   elif npcId == 7553 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION2_ID) == 0 and st.getQuestItemsCount(CONTRIBUTION_OF_MARIFE_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE2_ID) == 0 and st.getQuestItemsCount(MARIFES_REQUEST_ID) == 0 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) :
        htmltext = "7553-01.htm"
        st.giveItems(MARIFES_REQUEST_ID,1)
   elif npcId == 7553 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(MARIFES_REQUEST_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION2_ID) == 0 and st.getQuestItemsCount(CONTRIBUTION_OF_MARIFE_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE2_ID) == 0 :
        if st.getQuestItemsCount(ANIMAL_SKIN_ID) < 100 :
          htmltext = "7553-02.htm"
        else:
          htmltext = "7553-03.htm"
          st.takeItems(ANIMAL_SKIN_ID,100)
          st.giveItems(CONTRIBUTION_OF_MARIFE_ID,1)
          st.takeItems(MARIFES_REQUEST_ID,1)
   elif npcId == 7553 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(CONTRIBUTION_OF_MARIFE_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION2_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE2_ID) == 0 and st.getQuestItemsCount(MARIFES_REQUEST_ID) == 0 :
        htmltext = "7553-04.htm"
   elif npcId == 7555 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION3_ID) == 0 and st.getQuestItemsCount(PROCURATION_OF_TOROCCO_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE3_ID) == 0 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) :
        htmltext = "7555-01.htm"
   elif npcId == 7555 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(PROCURATION_OF_TOROCCO_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION3_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE3_ID) == 0 :
        htmltext = "7555-03.htm"
   elif npcId == 7554 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION4_ID) == 0 and st.getQuestItemsCount(RECEIPT_OF_BOLTER_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE4_ID) == 0 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) :
        htmltext = "7554-01.htm"
        st.giveItems(RECEIPT_OF_BOLTER_ID,1)
   elif npcId == 7554 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(RECEIPT_OF_BOLTER_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION4_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE4_ID) == 0 :
        htmltext = "7554-02.htm"
   elif npcId == 7556 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION5_ID) == 0 and st.getQuestItemsCount(CONTRIBUTION_OF_TOMA_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE5_ID) == 0 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) :
        htmltext = "7556-01.htm"
        st.giveItems(CONTRIBUTION_OF_TOMA_ID,1)
   elif npcId == 7556 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(COLLECTION_LICENSE_ID) and st.getQuestItemsCount(CONTRIBUTION_OF_TOMA_ID) and st.getQuestItemsCount(RECEIPT_OF_CONTRIBUTION5_ID) == 0 and st.getQuestItemsCount(LOCKIRINS_NOTICE5_ID) == 0 :
        htmltext = "7556-02.htm"
   elif npcId == 7597 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID)==1 and st.getQuestItemsCount(BLESSED_SEED_ID)==0 :
        htmltext = "7597-01.htm"
   elif npcId == 7597 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID)==1 and st.getQuestItemsCount(BLESSED_SEED_ID)==1 :
        htmltext = "7597-03.htm"
   elif npcId == 7597 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID)==1 :
        htmltext = "7597-04.htm"
   elif npcId == 7005 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID)==1 and st.getQuestItemsCount(LILITH_ELVEN_WAFER_ID) == 0 and st.getQuestItemsCount(CRYSTAL_BROOCH_ID) == 0 :
        htmltext = "7005-01.htm"
   elif npcId == 7005 and int(st.get("cond"))>=1 and st.getQuestItemsCount(LILITH_ELVEN_WAFER_ID)==0 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(CRYSTAL_BROOCH_ID) :
        htmltext = "7005-05.htm"
   elif npcId == 7005 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(LILITH_ELVEN_WAFER_ID) :
        htmltext = "7005-06.htm"
   elif npcId == 7005 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID)==1 :
        htmltext = "7005-07.htm"
   elif npcId == 7368 and int(st.get("cond"))>=1 and st.getQuestItemsCount(CRYSTAL_BROOCH_ID) and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(LILITH_ELVEN_WAFER_ID)==0 :
        htmltext = "7368-01.htm"
   elif npcId == 7368 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(LILITH_ELVEN_WAFER_ID) and st.getQuestItemsCount(CRYSTAL_BROOCH_ID)==0 :
        htmltext = "7368-04.htm"
   elif npcId == 7368 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID)==1 :
        htmltext = "7368-05.htm"
   elif npcId == 7466 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID)==1 and st.getQuestItemsCount(RECIPE_OF_EMILLY_ID) == 0 and st.getQuestItemsCount(BRIGHTS_LIST_ID) == 0 and st.getQuestItemsCount(MANDRAGORA_BOUQUET_ID) == 0 :
        htmltext = "7466-01.htm"
   elif npcId == 7466 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RECIPE_OF_EMILLY_ID)==0 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(BRIGHTS_LIST_ID) :
        if st.getQuestItemsCount(MANDRAGORA_PETAL_ID) < 20 or st.getQuestItemsCount(CRIMSON_MOSS_ID) < 10 :
          htmltext = "7466-04.htm"
        else:
          htmltext = "7466-05.htm"
          st.takeItems(MANDRAGORA_PETAL_ID,st.getQuestItemsCount(MANDRAGORA_PETAL_ID))
          st.takeItems(CRIMSON_MOSS_ID,st.getQuestItemsCount(CRIMSON_MOSS_ID))
          st.giveItems(MANDRAGORA_BOUQUET_ID,1)
          st.takeItems(BRIGHTS_LIST_ID,1)
   elif npcId == 7466 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(MANDRAGORA_BOUQUET_ID) and st.getQuestItemsCount(RECIPE_OF_EMILLY_ID) == 0 and st.getQuestItemsCount(BRIGHTS_LIST_ID) == 0 :
        htmltext = "7466-06.htm"
   elif npcId == 7466 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(RECIPE_OF_EMILLY_ID) :
        htmltext = "7466-07.htm"
   elif npcId == 7466 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID)==1 :
        htmltext = "7466-08.htm"
   elif npcId == 7620 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(MANDRAGORA_BOUQUET_ID) and st.getQuestItemsCount(RECIPE_OF_EMILLY_ID) == 0 and st.getQuestItemsCount(BRIGHTS_LIST_ID) == 0 :
        htmltext = "7620-01.htm"
   elif npcId == 7620 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) and st.getQuestItemsCount(RECIPE_OF_EMILLY_ID) :
        htmltext = "7620-04.htm"
   elif npcId == 7620 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID)==1 :
        htmltext = "7620-05.htm"
   elif npcId == 7621 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID)==1 and st.getQuestItemsCount(CLAY_DOUGH_ID) == 0 and st.getQuestItemsCount(PATTERN_OF_KEYHOLE_ID) == 0 and st.getQuestItemsCount(NIKOLAS_LIST_ID) == 0 and st.getQuestItemsCount(MAPHR_TABLET_FRAGMENT_ID) == 0 :
        htmltext = "7621-01.htm"
   elif npcId == 7621 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID) and st.getQuestItemsCount(CLAY_DOUGH_ID) and st.getQuestItemsCount(PATTERN_OF_KEYHOLE_ID) == 0 and st.getQuestItemsCount(NIKOLAS_LIST_ID) == 0 and st.getQuestItemsCount(MAPHR_TABLET_FRAGMENT_ID) == 0 :
        htmltext = "7621-05.htm"
   elif npcId == 7621 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID) and st.getQuestItemsCount(PATTERN_OF_KEYHOLE_ID) and st.getQuestItemsCount(CLAY_DOUGH_ID) == 0 and st.getQuestItemsCount(NIKOLAS_LIST_ID) == 0 and st.getQuestItemsCount(MAPHR_TABLET_FRAGMENT_ID) == 0 :
        htmltext = "7621-06.htm"
        st.giveItems(NIKOLAS_LIST_ID,1)
        st.takeItems(PATTERN_OF_KEYHOLE_ID,1)
        st.giveItems(RP_TITAN_KEY_ID,1)
        st.takeItems(PARMANS_LETTER_ID,1)
   elif npcId == 7621 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID) and st.getQuestItemsCount(NIKOLAS_LIST_ID) and st.getQuestItemsCount(CLAY_DOUGH_ID) == 0 and st.getQuestItemsCount(PATTERN_OF_KEYHOLE_ID) == 0 and st.getQuestItemsCount(MAPHR_TABLET_FRAGMENT_ID) == 0 and st.getQuestItemsCount(KEY_OF_TITAN_ID) == 0 :
        htmltext = "7621-07.htm"
   elif npcId == 7621 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID) and st.getQuestItemsCount(NIKOLAS_LIST_ID) and st.getQuestItemsCount(KEY_OF_TITAN_ID) and st.getQuestItemsCount(CLAY_DOUGH_ID) == 0 and st.getQuestItemsCount(PATTERN_OF_KEYHOLE_ID) == 0 and st.getQuestItemsCount(MAPHR_TABLET_FRAGMENT_ID) == 0 :
        htmltext = "7621-08.htm"
   elif npcId == 7621 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID) and st.getQuestItemsCount(MAPHR_TABLET_FRAGMENT_ID) and st.getQuestItemsCount(CLAY_DOUGH_ID) == 0 and st.getQuestItemsCount(PATTERN_OF_KEYHOLE_ID) == 0 and st.getQuestItemsCount(NIKOLAS_LIST_ID) == 0 :
        htmltext = "7621-09.htm"
   elif npcId == 7622 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID) and st.getQuestItemsCount(CLAY_DOUGH_ID) and st.getQuestItemsCount(PATTERN_OF_KEYHOLE_ID)==0 :
        htmltext = "7622-01.htm"
   elif npcId == 7622 and int(st.get("cond"))>=1 and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID) and st.getQuestItemsCount(KEY_OF_TITAN_ID) and st.getQuestItemsCount(MAPHR_TABLET_FRAGMENT_ID)==0 :
        htmltext = "7622-03.htm"
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 223 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) == 1  :
     if st.getQuestItemsCount(MANDRAGORA_PETAL_ID)<20 and st.getRandom(100)<30 :
      st.giveItems(MANDRAGORA_PETAL_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 154 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) == 1  :
     if st.getQuestItemsCount(MANDRAGORA_PETAL_ID)<20 and st.getRandom(100)<60 :
      st.giveItems(MANDRAGORA_PETAL_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 155 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) == 1  :
     if st.getQuestItemsCount(MANDRAGORA_PETAL_ID)<20 and st.getRandom(100)<80 :
      st.giveItems(MANDRAGORA_PETAL_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 156 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) == 1  :
     if st.getQuestItemsCount(MANDRAGORA_PETAL_ID)<20 :
      st.giveItems(MANDRAGORA_PETAL_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 228 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(RING_OF_TESTIMONY1_ID) == 1 and st.getQuestItemsCount(CRIMSON_MOSS_ID)<10 :
     if st.getQuestItemsCount(CRIMSON_MOSS_ID) == 9 :
      st.giveItems(CRIMSON_MOSS_ID,1)
      st.playSound("ItemSound.quest_middle")
     else :
      st.giveItems(CRIMSON_MOSS_ID,1)
      st.playSound("ItemSound.quest_itemget")
   elif npcId == 157 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID) == 1 and st.getQuestItemsCount(STAKATO_SHELL_ID) <20  :
     if st.getRandom(100)<20 :
      st.giveItems(STAKATO_SHELL_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 230 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID) == 1 and st.getQuestItemsCount(STAKATO_SHELL_ID) <20  :
     if st.getRandom(100)<30 :
      st.giveItems(STAKATO_SHELL_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 232 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID) == 1 and st.getQuestItemsCount(STAKATO_SHELL_ID) <20  :
     if st.getRandom(100)<50 :
      st.giveItems(STAKATO_SHELL_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 234 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID) == 1 and st.getQuestItemsCount(STAKATO_SHELL_ID) <20 :
     if st.getRandom(100)<60 :
      st.giveItems(STAKATO_SHELL_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 231 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID) == 1 and st.getQuestItemsCount(INPICIO_SAC_ID) <20  :
     if st.getRandom(100)<50 :
      st.giveItems(INPICIO_SAC_ID,1)
      st.playSound("ItemSound.quest_middle")
   elif npcId == 233 :
    st.set("id","0")
    if int(st.get("cond")) and st.getQuestItemsCount(RING_OF_TESTIMONY2_ID) == 1 and st.getQuestItemsCount(SPIDER_THORN_ID) <20  :
     if st.getRandom(100)<50 :
      st.giveItems(SPIDER_THORN_ID,1)
      st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(221,"221_TestimonyOfProsperity","Testimony Of Prosperity")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7104)

STARTING.addTalkId(7104)

STARTED.addTalkId(7005)
STARTED.addTalkId(7104)
STARTED.addTalkId(7368)
STARTED.addTalkId(7466)
STARTED.addTalkId(7517)
STARTED.addTalkId(7519)
STARTED.addTalkId(7531)
STARTED.addTalkId(7532)
STARTED.addTalkId(7533)
STARTED.addTalkId(7534)
STARTED.addTalkId(7535)
STARTED.addTalkId(7536)
STARTED.addTalkId(7553)
STARTED.addTalkId(7554)
STARTED.addTalkId(7555)
STARTED.addTalkId(7556)
STARTED.addTalkId(7597)
STARTED.addTalkId(7620)
STARTED.addTalkId(7621)
STARTED.addTalkId(7622)

STARTED.addKillId(154)
STARTED.addKillId(155)
STARTED.addKillId(156)
STARTED.addKillId(157)
STARTED.addKillId(223)
STARTED.addKillId(228)
STARTED.addKillId(230)
STARTED.addKillId(231)
STARTED.addKillId(232)
STARTED.addKillId(233)
STARTED.addKillId(234)

STARTED.addQuestDrop(7104,RING_OF_TESTIMONY1_ID,1)
STARTED.addQuestDrop(7531,OLD_ACCOUNT_BOOK_ID,1)
STARTED.addQuestDrop(7597,BLESSED_SEED_ID,1)
STARTED.addQuestDrop(7620,RECIPE_OF_EMILLY_ID,1)
STARTED.addQuestDrop(7368,LILITH_ELVEN_WAFER_ID,1)
STARTED.addQuestDrop(7104,PARMANS_INSTRUCTIONS_ID,1)
STARTED.addQuestDrop(7104,RING_OF_TESTIMONY2_ID,1)
STARTED.addQuestDrop(7622,MAPHR_TABLET_FRAGMENT_ID,1)
STARTED.addQuestDrop(7531,COLLECTION_LICENSE_ID,1)
STARTED.addQuestDrop(7532,RECEIPT_OF_CONTRIBUTION1_ID,1)
STARTED.addQuestDrop(7533,RECEIPT_OF_CONTRIBUTION2_ID,1)
STARTED.addQuestDrop(7534,RECEIPT_OF_CONTRIBUTION3_ID,1)
STARTED.addQuestDrop(7535,RECEIPT_OF_CONTRIBUTION4_ID,1)
STARTED.addQuestDrop(7536,RECEIPT_OF_CONTRIBUTION5_ID,1)
STARTED.addQuestDrop(7531,LOCKIRINS_NOTICE1_ID,1)
STARTED.addQuestDrop(7517,CONTRIBUTION_OF_CHALI_ID,1)
STARTED.addQuestDrop(7531,LOCKIRINS_NOTICE2_ID,1)
STARTED.addQuestDrop(7553,CONTRIBUTION_OF_MARIFE_ID,1)
STARTED.addQuestDrop(7519,CONTRIBUTION_OF_MION_ID,1)
STARTED.addQuestDrop(7531,LOCKIRINS_NOTICE3_ID,1)
STARTED.addQuestDrop(7555,PROCURATION_OF_TOROCCO_ID,1)
STARTED.addQuestDrop(7531,LOCKIRINS_NOTICE4_ID,1)
STARTED.addQuestDrop(7554,RECEIPT_OF_BOLTER_ID,1)
STARTED.addQuestDrop(7531,LOCKIRINS_NOTICE5_ID,1)
STARTED.addQuestDrop(7556,CONTRIBUTION_OF_TOMA_ID,1)
STARTED.addQuestDrop(7553,MARIFES_REQUEST_ID,1)
STARTED.addQuestDrop(7005,CRYSTAL_BROOCH_ID,1)
STARTED.addQuestDrop(7466,BRIGHTS_LIST_ID,1)
STARTED.addQuestDrop(7466,MANDRAGORA_BOUQUET_ID,1)
STARTED.addQuestDrop(7622,PATTERN_OF_KEYHOLE_ID,1)
STARTED.addQuestDrop(7621,CLAY_DOUGH_ID,1)
STARTED.addQuestDrop(7621,NIKOLAS_LIST_ID,1)
STARTED.addQuestDrop(7621,RP_TITAN_KEY_ID,1)
