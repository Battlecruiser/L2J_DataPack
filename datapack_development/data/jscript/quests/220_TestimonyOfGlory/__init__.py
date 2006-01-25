# Maked by Mr. Have fun! Version 0.2
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_GLORY_ID = 3203
VOKIYANS_ORDER1_ID = 3204
MANASHEN_SHARD_ID = 3205
TYRANT_TALON_ID = 3206
GUARDIAN_BASILISK_FANG_ID = 3207
VOKIYANS_ORDER2_ID = 3208
NECKLACE_OF_AUTHORITY_ID = 3209
CHIANTAS_ORDER1_ID = 3210
CHIANTAS_ORDER2_ID = 3216
SCEPTER_OF_BREKA_ID = 3211
SCEPTER_OF_ENKU_ID = 3212
SCEPTER_OF_VUKU_ID = 3213
SCEPTER_OF_TUREK_ID = 3214
SCEPTER_OF_TUNATH_ID = 3215
MANAKIAS_LETTER1_ID = 3228
MANAKIAS_LETTER2_ID = 3229
KASMANS_LETTER1_ID = 3230
CHIANTAS_ORDER3_ID = 3217
SCEPTER_BOX_ID = 3220
TAMLIN_ORC_SKULL_ID = 3218
TIMAK_ORC_HEAD_ID = 3219
GLOVE_OF_VOLTAR_ID = 3223
GLOVE_OF_KEPRA_ID = 3225
KASMANS_LETTER2_ID = 3231
GLOVE_OF_BURAI_ID = 3227
KASMANS_LETTER3_ID = 3232
PASHIKAS_HEAD_ID = 3221
VULTUS_HEAD_ID = 3222
ENKU_OVERLORD_HEAD_ID = 3224
MAKUM_BUGBEAR_HEAD_ID = 3226
STAKATO_DRONE_HUSK1_ID = 3234
DRIKOS_CONTRACT_ID = 3233
RITUAL_BOX_ID = 3237
SCEPTER_OF_TANTOS_ID = 3236
TANAPIS_ORDER1_ID = 3235

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      htmltext = "7514-05.htm"
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      st.giveItems(VOKIYANS_ORDER1_ID,1)
    elif event == "7514_1" :
          htmltext = "7514-04.htm"
    elif event == "7642_1" :
          htmltext = "7642-02.htm"
    elif event == "7642_2" :
          htmltext = "7642-03.htm"
          st.giveItems(CHIANTAS_ORDER1_ID,1)
          st.takeItems(VOKIYANS_ORDER2_ID,1)
    elif event == "7642_3" :
          if st.getPlayer().getLevel() < 38 :
            htmltext = "7642-06.htm"
            st.giveItems(CHIANTAS_ORDER2_ID,1)
            st.takeItems(SCEPTER_OF_BREKA_ID,1)
            st.takeItems(SCEPTER_OF_ENKU_ID,1)
            st.takeItems(SCEPTER_OF_VUKU_ID,1)
            st.takeItems(SCEPTER_OF_TUREK_ID,1)
            st.takeItems(SCEPTER_OF_TUNATH_ID,1)
            st.takeItems(CHIANTAS_ORDER1_ID,1)
            st.takeItems(MANAKIAS_LETTER1_ID,st.getQuestItemsCount(MANAKIAS_LETTER1_ID))
            st.takeItems(MANAKIAS_LETTER2_ID,st.getQuestItemsCount(MANAKIAS_LETTER2_ID))
            st.takeItems(KASMANS_LETTER1_ID,st.getQuestItemsCount(KASMANS_LETTER1_ID))
          else:
            htmltext = "7642-07.htm"
            st.giveItems(CHIANTAS_ORDER3_ID,1)
            st.takeItems(SCEPTER_OF_BREKA_ID,1)
            st.takeItems(SCEPTER_OF_ENKU_ID,1)
            st.takeItems(SCEPTER_OF_VUKU_ID,1)
            st.takeItems(SCEPTER_OF_TUREK_ID,1)
            st.takeItems(SCEPTER_OF_TUNATH_ID,1)
            st.takeItems(CHIANTAS_ORDER1_ID,1)
            st.takeItems(MANAKIAS_LETTER1_ID,st.getQuestItemsCount(MANAKIAS_LETTER1_ID))
            st.takeItems(MANAKIAS_LETTER2_ID,st.getQuestItemsCount(MANAKIAS_LETTER2_ID))
            st.takeItems(KASMANS_LETTER1_ID,st.getQuestItemsCount(KASMANS_LETTER1_ID))
    elif event == "7515_1" :
          if st.getQuestItemsCount(SCEPTER_OF_BREKA_ID) == 0 and st.getQuestItemsCount(MANAKIAS_LETTER1_ID) :
            htmltext = "7515-04.htm"
          elif st.getQuestItemsCount(SCEPTER_OF_BREKA_ID) :
            htmltext = "7515-02.htm"
          elif st.getQuestItemsCount(SCEPTER_OF_BREKA_ID) == 0 and st.getQuestItemsCount(MANAKIAS_LETTER1_ID) == 0 :
            htmltext = "7515-03.htm"
            st.giveItems(MANAKIAS_LETTER1_ID,1)
            st.takeItems(GLOVE_OF_VOLTAR_ID,1)
    elif event == "7515_2" :
          if st.getQuestItemsCount(SCEPTER_OF_ENKU_ID) :
            htmltext = "7515-05.htm"
          elif st.getQuestItemsCount(SCEPTER_OF_ENKU_ID) == 0 and st.getQuestItemsCount(MANAKIAS_LETTER2_ID) == 0 :
            htmltext = "7515-06.htm"
            st.giveItems(MANAKIAS_LETTER2_ID,1)
            st.takeItems(GLOVE_OF_KEPRA_ID,1)
          elif st.getQuestItemsCount(SCEPTER_OF_ENKU_ID) == 0 and st.getQuestItemsCount(MANAKIAS_LETTER2_ID) :
            htmltext = "7515-07.htm"
    elif event == "7501_1" :
          if st.getQuestItemsCount(SCEPTER_OF_VUKU_ID) :
            htmltext = "7501-02.htm"
          elif st.getQuestItemsCount(SCEPTER_OF_VUKU_ID) == 0 and st.getQuestItemsCount(KASMANS_LETTER1_ID) == 0 :
            htmltext = "7501-03.htm"
            st.giveItems(KASMANS_LETTER1_ID,1)
          elif st.getQuestItemsCount(SCEPTER_OF_BREKA_ID) == 0 and st.getQuestItemsCount(KASMANS_LETTER1_ID) or st.getQuestItemsCount(DRIKOS_CONTRACT_ID) :
            htmltext = "7501-04.htm"
    elif event == "7501_2" :
          if st.getQuestItemsCount(SCEPTER_OF_TUREK_ID) :
            htmltext = "7501-05.htm"
          elif st.getQuestItemsCount(SCEPTER_OF_TUREK_ID) == 0 and st.getQuestItemsCount(KASMANS_LETTER2_ID) == 0 :
            htmltext = "7501-06.htm"
            st.giveItems(KASMANS_LETTER2_ID,1)
            st.takeItems(GLOVE_OF_BURAI_ID,1)
          elif st.getQuestItemsCount(SCEPTER_OF_TUREK_ID) == 0 and st.getQuestItemsCount(KASMANS_LETTER2_ID) :
            htmltext = "7501-07.htm"
    elif event == "7501_3" :
          if st.getQuestItemsCount(SCEPTER_OF_TUNATH_ID) :
            htmltext = "7501-08.htm"
          elif st.getQuestItemsCount(SCEPTER_OF_TUNATH_ID) == 0 and st.getQuestItemsCount(KASMANS_LETTER3_ID) == 0 :
            htmltext = "7501-09.htm"
            st.giveItems(KASMANS_LETTER3_ID,1)
          elif st.getQuestItemsCount(SCEPTER_OF_TUNATH_ID) == 0 and st.getQuestItemsCount(KASMANS_LETTER3_ID) :
            htmltext = "7501-10.htm"
    elif event == "7615_1" :
          htmltext = "7615-03.htm"
    elif event == "7615_2" :
          htmltext = "7615-04.htm"
          st.getPcSpawn().addSpawn(5080)
          st.getPcSpawn().addSpawn(5081)
          st.giveItems(GLOVE_OF_VOLTAR_ID,1)
          st.takeItems(MANAKIAS_LETTER1_ID,1)
    elif event == "7616_1" :
          htmltext = "7616-03.htm"
    elif event == "7616_2" :
          htmltext = "7616-04.htm"
          st.getPcSpawn().addSpawn(5082)
          st.getPcSpawn().addSpawn(5082)
          st.getPcSpawn().addSpawn(5082)
          st.getPcSpawn().addSpawn(5082)
          st.giveItems(GLOVE_OF_KEPRA_ID,1)
          st.takeItems(MANAKIAS_LETTER2_ID,1)
    elif event == "7616_3" :
          return
    elif event == "7617_1" :
          htmltext = "7617-03.htm"
          st.getPcSpawn().addSpawn(5083)
          st.getPcSpawn().addSpawn(5083)
          st.giveItems(GLOVE_OF_BURAI_ID,1)
          st.takeItems(KASMANS_LETTER2_ID,1)
    elif event == "7618_1" :
          htmltext = "7618-03.htm"
          st.giveItems(SCEPTER_OF_TUNATH_ID,1)
          st.takeItems(KASMANS_LETTER3_ID,1)
    elif event == "7619_1" :
          htmltext = "7619-03.htm"
          st.giveItems(DRIKOS_CONTRACT_ID,1)
          st.takeItems(KASMANS_LETTER1_ID,1)
    elif event == "7571_1" :
          htmltext = "7571-02.htm"
    elif event == "7571_2" :
          htmltext = "7571-03.htm"
          st.giveItems(TANAPIS_ORDER1_ID,1)
          st.takeItems(SCEPTER_BOX_ID,1)
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
   if npcId == 7514 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if int(st.get("cond")) < 15 :
        if st.getPlayer().getRace().ordinal() == 3 and st.getPlayer().getLevel() >= 37 :
          htmltext = "7514-03.htm"
        elif st.getPlayer().getClassId().getId() == 0x0f or st.getPlayer().getClassId().getId() == 0x1d or st.getPlayer().getClassId().getId() == 0x13 :
          htmltext = "7514-01.htm"
          st.exitQuest(1)
        else:
          htmltext = "7514-02.htm"
          st.exitQuest(1)
      else:
        htmltext = "7514-02.htm"
        st.exitQuest(1)
   elif npcId == 7514 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7514 and int(st.get("cond"))==1 and st.getQuestItemsCount(VOKIYANS_ORDER1_ID) and (st.getQuestItemsCount(MANASHEN_SHARD_ID)<10 or st.getQuestItemsCount(MANASHEN_SHARD_ID)<10 or st.getQuestItemsCount(TYRANT_TALON_ID)<10 or st.getQuestItemsCount(GUARDIAN_BASILISK_FANG_ID)<10) :
      htmltext = "7514-06.htm"
   elif npcId == 7514 and int(st.get("cond"))==1 and st.getQuestItemsCount(VOKIYANS_ORDER1_ID) and st.getQuestItemsCount(MANASHEN_SHARD_ID)>=10 and st.getQuestItemsCount(TYRANT_TALON_ID)>=10 and st.getQuestItemsCount(GUARDIAN_BASILISK_FANG_ID)>=10 :
      htmltext = "7514-08.htm"
      st.giveItems(VOKIYANS_ORDER2_ID,1)
      st.giveItems(NECKLACE_OF_AUTHORITY_ID,1)
      st.takeItems(VOKIYANS_ORDER1_ID,1)
      st.takeItems(MANASHEN_SHARD_ID,st.getQuestItemsCount(MANASHEN_SHARD_ID))
      st.takeItems(TYRANT_TALON_ID,st.getQuestItemsCount(TYRANT_TALON_ID))
      st.takeItems(GUARDIAN_BASILISK_FANG_ID,st.getQuestItemsCount(GUARDIAN_BASILISK_FANG_ID))
   elif npcId == 7514 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(VOKIYANS_ORDER2_ID) :
      htmltext = "7514-09.htm"
   elif npcId == 7514 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID)==0 and (st.getQuestItemsCount(VOKIYANS_ORDER2_ID) or st.getQuestItemsCount(SCEPTER_BOX_ID)) :
      htmltext = "7514-10.htm"
   elif npcId == 7642 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(VOKIYANS_ORDER2_ID) :
      htmltext = "7642-01.htm"
   elif npcId == 7642 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and ((st.getQuestItemsCount(SCEPTER_OF_BREKA_ID)+st.getQuestItemsCount(SCEPTER_OF_VUKU_ID)+st.getQuestItemsCount(SCEPTER_OF_TUREK_ID)+st.getQuestItemsCount(SCEPTER_OF_TUNATH_ID)+st.getQuestItemsCount(SCEPTER_OF_ENKU_ID))<5) :
      htmltext = "7642-04.htm"
   elif npcId == 7642 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_BREKA_ID) and st.getQuestItemsCount(SCEPTER_OF_VUKU_ID) and st.getQuestItemsCount(SCEPTER_OF_TUREK_ID) and st.getQuestItemsCount(SCEPTER_OF_TUNATH_ID) and st.getQuestItemsCount(SCEPTER_OF_ENKU_ID) :
      htmltext = "7642-05.htm"
   elif npcId == 7642 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER2_ID) :
      if st.getPlayer().getLevel() < 38 :
        htmltext = "7642-08.htm"
      else:
        htmltext = "7642-09.htm"
        st.giveItems(CHIANTAS_ORDER3_ID,1)
        st.takeItems(CHIANTAS_ORDER2_ID,1)
   elif npcId == 7642 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER3_ID) and (st.getQuestItemsCount(TAMLIN_ORC_SKULL_ID)<20 or st.getQuestItemsCount(TIMAK_ORC_HEAD_ID)<20) :
      htmltext = "7642-10.htm"
   elif npcId == 7642 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER3_ID) and st.getQuestItemsCount(TAMLIN_ORC_SKULL_ID)>=20 and st.getQuestItemsCount(TIMAK_ORC_HEAD_ID)>=20 :
      st.giveItems(SCEPTER_BOX_ID,1)
      st.takeItems(NECKLACE_OF_AUTHORITY_ID,1)
      st.takeItems(CHIANTAS_ORDER3_ID,1)
      st.takeItems(TAMLIN_ORC_SKULL_ID,st.getQuestItemsCount(TAMLIN_ORC_SKULL_ID))
      st.takeItems(TIMAK_ORC_HEAD_ID,st.getQuestItemsCount(TIMAK_ORC_HEAD_ID))
      htmltext = "7642-11.htm"
   elif npcId == 7642 and int(st.get("cond"))==1 and st.getQuestItemsCount(SCEPTER_BOX_ID) :
      htmltext = "7642-12.htm"
   elif npcId == 7642 and int(st.get("cond"))==1 and st.getQuestItemsCount(TANAPIS_ORDER1_ID) and st.getQuestItemsCount(RITUAL_BOX_ID) :
      htmltext = "7642-13.htm"
   elif npcId == 7515 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) :
      htmltext = "7515-01.htm"
   elif npcId == 7515 and int(st.get("cond"))==1 and (st.getQuestItemsCount(CHIANTAS_ORDER2_ID) or st.getQuestItemsCount(CHIANTAS_ORDER3_ID) or st.getQuestItemsCount(SCEPTER_BOX_ID)) :
      htmltext = "7515-08.htm"
   elif npcId == 7501 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) :
      htmltext = "7501-01.htm"
   elif npcId == 7501 and int(st.get("cond"))==1 and (st.getQuestItemsCount(CHIANTAS_ORDER2_ID) or st.getQuestItemsCount(CHIANTAS_ORDER3_ID) or st.getQuestItemsCount(SCEPTER_BOX_ID)) :
      htmltext = "7501-11.htm"
   elif npcId == 7615 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_BREKA_ID)==0 and st.getQuestItemsCount(MANAKIAS_LETTER1_ID)==0 and st.getQuestItemsCount(GLOVE_OF_VOLTAR_ID)==0 and st.getQuestItemsCount(PASHIKAS_HEAD_ID)==0 and st.getQuestItemsCount(VULTUS_HEAD_ID)==0 :
      htmltext = "7615-01.htm"
   elif npcId == 7615 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(MANAKIAS_LETTER1_ID) :
      htmltext = "7615-02.htm"
   elif npcId == 7615 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(GLOVE_OF_VOLTAR_ID) and ((st.getQuestItemsCount(PASHIKAS_HEAD_ID)+st.getQuestItemsCount(VULTUS_HEAD_ID))<2) and st.getQuestItemsCount(SCEPTER_OF_BREKA_ID)==0 :
      htmltext = "7615-05.htm"
#      if Maker_GetNpcCount() < 3 :
      st.getPcSpawn().addSpawn(5080)
      st.getPcSpawn().addSpawn(5081)
   elif npcId == 7615 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(PASHIKAS_HEAD_ID) and st.getQuestItemsCount(VULTUS_HEAD_ID) :
      htmltext = "7615-06.htm"
      st.giveItems(SCEPTER_OF_BREKA_ID,1)
      st.takeItems(PASHIKAS_HEAD_ID,1)
      st.takeItems(VULTUS_HEAD_ID,1)
   elif npcId == 7615 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_BREKA_ID) :
      htmltext = "7615-07.htm"
   elif npcId == 7615 and int(st.get("cond"))==1 and (st.getQuestItemsCount(CHIANTAS_ORDER2_ID) or st.getQuestItemsCount(CHIANTAS_ORDER3_ID) or st.getQuestItemsCount(SCEPTER_BOX_ID)) :
      htmltext = "7615-08.htm"
   elif npcId == 7616 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_ENKU_ID)==0 and st.getQuestItemsCount(MANAKIAS_LETTER2_ID)==0 and st.getQuestItemsCount(GLOVE_OF_KEPRA_ID)==0 and st.getQuestItemsCount(ENKU_OVERLORD_HEAD_ID)<4 :
      htmltext = "7616-01.htm"
   elif npcId == 7616 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(MANAKIAS_LETTER2_ID) :
      htmltext = "7616-02.htm"
   elif npcId == 7616 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(GLOVE_OF_KEPRA_ID) and st.getQuestItemsCount(ENKU_OVERLORD_HEAD_ID)<4 :
      htmltext = "7616-05.htm"
      st.getPcSpawn().addSpawn(5082)
   elif npcId == 7616 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(ENKU_OVERLORD_HEAD_ID)>=4 :
      htmltext = "7616-06.htm"
      st.giveItems(SCEPTER_OF_ENKU_ID,1)
      st.takeItems(ENKU_OVERLORD_HEAD_ID,st.getQuestItemsCount(ENKU_OVERLORD_HEAD_ID))
   elif npcId == 7616 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_ENKU_ID) :
      htmltext = "7616-07.htm"
   elif npcId == 7616 and int(st.get("cond"))==1 and (st.getQuestItemsCount(CHIANTAS_ORDER2_ID) or st.getQuestItemsCount(CHIANTAS_ORDER3_ID) or st.getQuestItemsCount(SCEPTER_BOX_ID)) :
      htmltext = "7616-08.htm"
   elif npcId == 7617 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_TUREK_ID)==0 and st.getQuestItemsCount(KASMANS_LETTER2_ID)==0 and st.getQuestItemsCount(GLOVE_OF_BURAI_ID)==0 and st.getQuestItemsCount(MAKUM_BUGBEAR_HEAD_ID)==0 :
      htmltext = "7617-01.htm"
   elif npcId == 7617 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(KASMANS_LETTER2_ID) :
      htmltext = "7617-02.htm"
   elif npcId == 7617 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(GLOVE_OF_BURAI_ID) :
      htmltext = "7617-04.htm"
      st.getPcSpawn().addSpawn(5083)
      st.getPcSpawn().addSpawn(5083)
   elif npcId == 7617 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(MAKUM_BUGBEAR_HEAD_ID)>=2 :
      htmltext = "7617-05.htm"
      st.giveItems(SCEPTER_OF_TUREK_ID,1)
      st.takeItems(MAKUM_BUGBEAR_HEAD_ID,st.getQuestItemsCount(MAKUM_BUGBEAR_HEAD_ID))
   elif npcId == 7617 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_TUREK_ID) :
      htmltext = "7617-06.htm"
   elif npcId == 7617 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and (st.getQuestItemsCount(CHIANTAS_ORDER2_ID) or st.getQuestItemsCount(CHIANTAS_ORDER3_ID) or st.getQuestItemsCount(SCEPTER_BOX_ID)) :
      htmltext = "7617-07.htm"
   elif npcId == 7618 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_TUNATH_ID)==0 and st.getQuestItemsCount(KASMANS_LETTER3_ID)==0 :
      htmltext = "7618-01.htm"
   elif npcId == 7618 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_TUNATH_ID)==0 and st.getQuestItemsCount(KASMANS_LETTER3_ID) :
      htmltext = "7618-02.htm"
   elif npcId == 7618 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_TUNATH_ID) :
      htmltext = "7618-04.htm"
   elif npcId == 7618 and int(st.get("cond"))==1 and (st.getQuestItemsCount(CHIANTAS_ORDER2_ID) or st.getQuestItemsCount(CHIANTAS_ORDER3_ID) or st.getQuestItemsCount(SCEPTER_BOX_ID)) :
      htmltext = "7618-05.htm"
   elif npcId == 7619 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_VUKU_ID)==0 and st.getQuestItemsCount(KASMANS_LETTER1_ID)==0 and st.getQuestItemsCount(DRIKOS_CONTRACT_ID)==0 :
      htmltext = "7619-01.htm"
   elif npcId == 7619 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_VUKU_ID)==0 and st.getQuestItemsCount(KASMANS_LETTER1_ID)==1 :
      htmltext = "7619-02.htm"
   elif npcId == 7619 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_VUKU_ID)==0 and st.getQuestItemsCount(DRIKOS_CONTRACT_ID) and st.getQuestItemsCount(STAKATO_DRONE_HUSK1_ID)<30 :
      htmltext = "7619-04.htm"
   elif npcId == 7619 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_VUKU_ID)==0 and st.getQuestItemsCount(DRIKOS_CONTRACT_ID) and st.getQuestItemsCount(STAKATO_DRONE_HUSK1_ID)>=30 :
      htmltext = "7619-05.htm"
      st.giveItems(SCEPTER_OF_VUKU_ID,1)
      st.takeItems(STAKATO_DRONE_HUSK1_ID,st.getQuestItemsCount(STAKATO_DRONE_HUSK1_ID))
      st.takeItems(DRIKOS_CONTRACT_ID,st.getQuestItemsCount(DRIKOS_CONTRACT_ID))
   elif npcId == 7619 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_VUKU_ID) :
      htmltext = "7619-06.htm"
   elif npcId == 7619 and int(st.get("cond"))==1 and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and (st.getQuestItemsCount(CHIANTAS_ORDER2_ID) or st.getQuestItemsCount(CHIANTAS_ORDER3_ID) or st.getQuestItemsCount(SCEPTER_BOX_ID)) :
      htmltext = "7619-07.htm"
   elif npcId == 7571 and int(st.get("cond"))==1 and st.getQuestItemsCount(SCEPTER_BOX_ID) :
      htmltext = "7571-01.htm"
   elif npcId == 7571 and int(st.get("cond"))==1 and st.getQuestItemsCount(TANAPIS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_TANTOS_ID)==0 :
      htmltext = "7571-04.htm"
   elif npcId == 7571 and int(st.get("cond"))==1 and st.getQuestItemsCount(TANAPIS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_TANTOS_ID)==1 :
      htmltext = "7571-05.htm"
      st.giveItems(RITUAL_BOX_ID,1)
      st.takeItems(SCEPTER_OF_TANTOS_ID,1)
   elif npcId == 7571 and int(st.get("cond"))==1 and st.getQuestItemsCount(RITUAL_BOX_ID) :
      htmltext = "7571-06.htm"
   elif npcId == 7565 and int(st.get("cond"))==1 and st.getQuestItemsCount(RITUAL_BOX_ID)==0 and (st.getQuestItemsCount(SCEPTER_BOX_ID) or st.getQuestItemsCount(TANAPIS_ORDER1_ID)) :
      htmltext = "7565-01.htm"
   elif npcId == 7565 and int(st.get("cond"))==1 and st.getQuestItemsCount(RITUAL_BOX_ID) :
      if st.getGameTicks() != int(st.get("id")) :
        st.set("id",str(st.getGameTicks()))
        st.addExpAndSp(40000,5000)
      htmltext = "7565-02.htm"
      st.giveItems(MARK_OF_GLORY_ID,1)
      st.takeItems(RITUAL_BOX_ID,1)
      st.set("cond","0")
      st.setState(COMPLETED)
      st.playSound("ItemSound.quest_finish")
      st.set("onlyone","1")
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 5080 :
      if int(st.get("cond")) and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(GLOVE_OF_VOLTAR_ID) and st.getQuestItemsCount(PASHIKAS_HEAD_ID) == 0 :
        if st.getQuestItemsCount(VULTUS_HEAD_ID) :
          st.giveItems(PASHIKAS_HEAD_ID,1)
          st.takeItems(GLOVE_OF_VOLTAR_ID,1)
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(PASHIKAS_HEAD_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 5081 :
      if int(st.get("cond")) and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(GLOVE_OF_VOLTAR_ID) and st.getQuestItemsCount(VULTUS_HEAD_ID) == 0 :
        if st.getQuestItemsCount(PASHIKAS_HEAD_ID) :
          st.giveItems(VULTUS_HEAD_ID,1)
          st.takeItems(GLOVE_OF_VOLTAR_ID,1)
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(VULTUS_HEAD_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 5082 :
      if int(st.get("cond")) and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(GLOVE_OF_KEPRA_ID) and st.getQuestItemsCount(ENKU_OVERLORD_HEAD_ID) < 4 :
        if st.getQuestItemsCount(ENKU_OVERLORD_HEAD_ID) == 3 :
          st.giveItems(ENKU_OVERLORD_HEAD_ID,1)
          st.takeItems(GLOVE_OF_KEPRA_ID,1)
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(ENKU_OVERLORD_HEAD_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 5083 :
      if int(st.get("cond")) and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(GLOVE_OF_BURAI_ID) and st.getQuestItemsCount(MAKUM_BUGBEAR_HEAD_ID) < 2 :
        if st.getQuestItemsCount(MAKUM_BUGBEAR_HEAD_ID) == 1 :
          st.giveItems(MAKUM_BUGBEAR_HEAD_ID,1)
          st.takeItems(GLOVE_OF_BURAI_ID,1)
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(MAKUM_BUGBEAR_HEAD_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 778 :
      if int(st.get("cond")) and st.getQuestItemsCount(TANAPIS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_TANTOS_ID) == 0 :
        st.getPcSpawn().addSpawn(5086)
   elif npcId == 779 :
      if int(st.get("cond")) and st.getQuestItemsCount(TANAPIS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_TANTOS_ID) == 0 :
        st.getPcSpawn().addSpawn(5086)
   elif npcId == 5086 :
      if int(st.get("cond")) and st.getQuestItemsCount(TANAPIS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_TANTOS_ID) == 0 :
        st.giveItems(SCEPTER_OF_TANTOS_ID,1)
        st.playSound("Itemsound.quest_middle")
   elif npcId == 234 :
      if int(st.get("cond")) and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER1_ID) and st.getQuestItemsCount(SCEPTER_OF_VUKU_ID) == 0 and st.getQuestItemsCount(DRIKOS_CONTRACT_ID) and st.getQuestItemsCount(STAKATO_DRONE_HUSK1_ID) < 30 :
        if st.getRandom(20) < 15 :
          if st.getQuestItemsCount(STAKATO_DRONE_HUSK1_ID) == 29 :
            st.giveItems(STAKATO_DRONE_HUSK1_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(STAKATO_DRONE_HUSK1_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 563 :
      if int(st.get("cond")) and st.getQuestItemsCount(VOKIYANS_ORDER1_ID) and st.getQuestItemsCount(MANASHEN_SHARD_ID) < 10 or st.getQuestItemsCount(TYRANT_TALON_ID) < 10 :
        if st.getRandom(20) < 15 :
          if st.getQuestItemsCount(MANASHEN_SHARD_ID) == 9 :
            st.giveItems(MANASHEN_SHARD_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(MANASHEN_SHARD_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 193 :
      if int(st.get("cond")) and st.getQuestItemsCount(VOKIYANS_ORDER1_ID) and st.getQuestItemsCount(TYRANT_TALON_ID) < 10 :
        if st.getRandom(20) < 10 :
          if st.getQuestItemsCount(TYRANT_TALON_ID) == 9 :
            st.giveItems(TYRANT_TALON_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(TYRANT_TALON_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 192 :
      if int(st.get("cond")) and st.getQuestItemsCount(VOKIYANS_ORDER1_ID) and st.getQuestItemsCount(TYRANT_TALON_ID) < 10 :
        if st.getRandom(20) < 10 :
          if st.getQuestItemsCount(TYRANT_TALON_ID) == 9 :
            st.giveItems(TYRANT_TALON_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(TYRANT_TALON_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 550 :
      if int(st.get("cond")) and st.getQuestItemsCount(VOKIYANS_ORDER1_ID) and st.getQuestItemsCount(GUARDIAN_BASILISK_FANG_ID) < 10 :
        if st.getRandom(20) < 10 :
          if st.getQuestItemsCount(GUARDIAN_BASILISK_FANG_ID) == 9 :
            st.giveItems(GUARDIAN_BASILISK_FANG_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(GUARDIAN_BASILISK_FANG_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 601 :
      if int(st.get("cond")) and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER3_ID) and st.getQuestItemsCount(TAMLIN_ORC_SKULL_ID) < 20 :
        if st.getRandom(10) < 5 :
          if st.getQuestItemsCount(TAMLIN_ORC_SKULL_ID) == 19 :
            st.giveItems(TAMLIN_ORC_SKULL_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(TAMLIN_ORC_SKULL_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 602 :
      if int(st.get("cond")) and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER3_ID) and st.getQuestItemsCount(TAMLIN_ORC_SKULL_ID) < 20 :
        if st.getRandom(10) < 6 :
          if st.getQuestItemsCount(TAMLIN_ORC_SKULL_ID) == 19 :
            st.giveItems(TAMLIN_ORC_SKULL_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(TAMLIN_ORC_SKULL_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 583 :
      if int(st.get("cond")) and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER3_ID) and st.getQuestItemsCount(TIMAK_ORC_HEAD_ID) < 20 :
        if st.getRandom(10) < 5 :
          if st.getQuestItemsCount(TIMAK_ORC_HEAD_ID) == 19 :
            st.giveItems(TIMAK_ORC_HEAD_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(TIMAK_ORC_HEAD_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 584 :
      if int(st.get("cond")) and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER3_ID) and st.getQuestItemsCount(TIMAK_ORC_HEAD_ID) < 20 :
        if st.getRandom(10) < 6 :
          if st.getQuestItemsCount(TIMAK_ORC_HEAD_ID) == 19 :
            st.giveItems(TIMAK_ORC_HEAD_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(TIMAK_ORC_HEAD_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 585 :
      if int(st.get("cond")) and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER3_ID) and st.getQuestItemsCount(TIMAK_ORC_HEAD_ID) < 20 :
        if st.getRandom(10) < 7 :
          if st.getQuestItemsCount(TIMAK_ORC_HEAD_ID) == 19 :
            st.giveItems(TIMAK_ORC_HEAD_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(TIMAK_ORC_HEAD_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 586 :
      if int(st.get("cond")) and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER3_ID) and st.getQuestItemsCount(TIMAK_ORC_HEAD_ID) < 20 :
        if st.getRandom(10) < 8 :
          if st.getQuestItemsCount(TIMAK_ORC_HEAD_ID) == 19 :
            st.giveItems(TIMAK_ORC_HEAD_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(TIMAK_ORC_HEAD_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 587 :
      if int(st.get("cond")) and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER3_ID) and st.getQuestItemsCount(TIMAK_ORC_HEAD_ID) < 20 :
        if st.getRandom(10) < 9 :
          if st.getQuestItemsCount(TIMAK_ORC_HEAD_ID) == 19 :
            st.giveItems(TIMAK_ORC_HEAD_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(TIMAK_ORC_HEAD_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 588 :
      if int(st.get("cond")) and st.getQuestItemsCount(NECKLACE_OF_AUTHORITY_ID) and st.getQuestItemsCount(CHIANTAS_ORDER3_ID) and st.getQuestItemsCount(TIMAK_ORC_HEAD_ID) < 20 :
        if st.getQuestItemsCount(TIMAK_ORC_HEAD_ID) == 19 :
          st.giveItems(TIMAK_ORC_HEAD_ID,1)
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(TIMAK_ORC_HEAD_ID,1)
          st.playSound("Itemsound.quest_itemget")
   return

QUEST       = Quest(220,"220_TestimonyOfGlory","Testimony Of Glory")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7514)

STARTING.addTalkId(7514)

STARTED.addTalkId(7501)
STARTED.addTalkId(7514)
STARTED.addTalkId(7515)
STARTED.addTalkId(7565)
STARTED.addTalkId(7571)
STARTED.addTalkId(7615)
STARTED.addTalkId(7616)
STARTED.addTalkId(7617)
STARTED.addTalkId(7618)
STARTED.addTalkId(7619)
STARTED.addTalkId(7642)

STARTED.addKillId(192)
STARTED.addKillId(193)
STARTED.addKillId(234)
STARTED.addKillId(5080)
STARTED.addKillId(5081)
STARTED.addKillId(5082)
STARTED.addKillId(5083)
STARTED.addKillId(5086)
STARTED.addKillId(550)
STARTED.addKillId(563)
STARTED.addKillId(583)
STARTED.addKillId(584)
STARTED.addKillId(585)
STARTED.addKillId(586)
STARTED.addKillId(587)
STARTED.addKillId(588)
STARTED.addKillId(601)
STARTED.addKillId(602)
STARTED.addKillId(778)
STARTED.addKillId(779)

STARTED.addQuestDrop(7514,VOKIYANS_ORDER1_ID,1)
STARTED.addQuestDrop(563,MANASHEN_SHARD_ID,1)
STARTED.addQuestDrop(193,TYRANT_TALON_ID,1)
STARTED.addQuestDrop(192,TYRANT_TALON_ID,1)
STARTED.addQuestDrop(550,GUARDIAN_BASILISK_FANG_ID,1)
STARTED.addQuestDrop(7514,VOKIYANS_ORDER2_ID,1)
STARTED.addQuestDrop(7615,SCEPTER_OF_BREKA_ID,1)
STARTED.addQuestDrop(7616,SCEPTER_OF_ENKU_ID,1)
STARTED.addQuestDrop(7619,SCEPTER_OF_VUKU_ID,1)
STARTED.addQuestDrop(7617,SCEPTER_OF_TUREK_ID,1)
STARTED.addQuestDrop(7618,SCEPTER_OF_TUNATH_ID,1)
STARTED.addQuestDrop(7642,CHIANTAS_ORDER1_ID,1)
STARTED.addQuestDrop(7515,MANAKIAS_LETTER1_ID,1)
STARTED.addQuestDrop(7515,MANAKIAS_LETTER2_ID,1)
STARTED.addQuestDrop(7501,KASMANS_LETTER1_ID,1)
STARTED.addQuestDrop(7642,CHIANTAS_ORDER2_ID,1)
STARTED.addQuestDrop(7514,NECKLACE_OF_AUTHORITY_ID,1)
STARTED.addQuestDrop(7642,CHIANTAS_ORDER3_ID,1)
STARTED.addQuestDrop(601,TAMLIN_ORC_SKULL_ID,1)
STARTED.addQuestDrop(602,TAMLIN_ORC_SKULL_ID,1)
STARTED.addQuestDrop(583,TIMAK_ORC_HEAD_ID,1)
STARTED.addQuestDrop(584,TIMAK_ORC_HEAD_ID,1)
STARTED.addQuestDrop(585,TIMAK_ORC_HEAD_ID,1)
STARTED.addQuestDrop(586,TIMAK_ORC_HEAD_ID,1)
STARTED.addQuestDrop(587,TIMAK_ORC_HEAD_ID,1)
STARTED.addQuestDrop(588,TIMAK_ORC_HEAD_ID,1)
STARTED.addQuestDrop(7615,GLOVE_OF_VOLTAR_ID,1)
STARTED.addQuestDrop(7616,GLOVE_OF_KEPRA_ID,1)
STARTED.addQuestDrop(7617,GLOVE_OF_BURAI_ID,1)
STARTED.addQuestDrop(5080,PASHIKAS_HEAD_ID,1)
STARTED.addQuestDrop(5081,VULTUS_HEAD_ID,1)
STARTED.addQuestDrop(5082,ENKU_OVERLORD_HEAD_ID,1)
STARTED.addQuestDrop(7501,KASMANS_LETTER2_ID,1)
STARTED.addQuestDrop(5083,MAKUM_BUGBEAR_HEAD_ID,1)
STARTED.addQuestDrop(7501,KASMANS_LETTER3_ID,1)
STARTED.addQuestDrop(234,STAKATO_DRONE_HUSK1_ID,1)
STARTED.addQuestDrop(7619,DRIKOS_CONTRACT_ID,1)
STARTED.addQuestDrop(5086,SCEPTER_OF_TANTOS_ID,1)
STARTED.addQuestDrop(7642,SCEPTER_BOX_ID,1)
STARTED.addQuestDrop(7571,RITUAL_BOX_ID,1)

print "importing quests: 220: Testimony Of Glory"
