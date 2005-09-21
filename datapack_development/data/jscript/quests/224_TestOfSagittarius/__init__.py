# Maked by Mr. Have fun! Version 0.2
print "importing quests: 224: Test Of Sagittarius"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

RECOMMENDATION_OF_BALANKI_ID = 2864
RECOMMENDATION_OF_FILAUR_ID = 2865
RECOMMENDATION_OF_ARIN_ID = 2866
MARK_OF_MAESTRO_ID = 2867
LETTER_OF_SOLDER_DETACHMENT_ID = 2868
PAINT_OF_KAMURU_ID = 2869
NECKLACE_OF_KAMURU_ID = 2870
PAINT_OF_TELEPORT_DEVICE_ID = 2871
TELEPORT_DEVICE_ID = 2872
ARCHITECTURE_OF_KRUMA_ID = 2873
REPORT_OF_KRUMA_ID = 2874
INGREDIENTS_OF_ANTIDOTE_ID = 2875
WEIRD_BEES_NEEDLE_ID = 2876
MARSH_SPIDERS_WEB_ID = 2877
BLOOD_OF_LEECH_ID = 2878
BERNARDS_INTRODUCTION_ID = 3294
LETTER_OF_HAMIL3_ID = 3297
HUNTERS_RUNE2_ID = 3299
MARK_OF_SAGITTARIUS_ID = 3293
CRESCENT_MOON_BOW_ID = 3028
TALISMAN_OF_KADESH_ID = 3300
BLOOD_OF_LIZARDMAN_ID = 3306
LETTER_OF_HAMIL1_ID = 3295
LETTER_OF_HAMIL2_ID = 3296
HUNTERS_RUNE1_ID = 3298
TALISMAN_OF_SNAKE_ID = 3301
MITHRIL_CLIP_ID = 3302
STAKATO_CHITIN_ID = 3303
ST_BOWSTRING_ID = 3304
MANASHENS_HORN_ID = 3305
WOODEN_ARROW_ID = 17

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      htmltext = "7702-04.htm"
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      st.giveItems(BERNARDS_INTRODUCTION_ID,1)
    elif event == "7626_1" :
          htmltext = "7626-02.htm"
    elif event == "7626_2" :
          htmltext = "7626-03.htm"
          st.giveItems(LETTER_OF_HAMIL1_ID,1)
          st.takeItems(BERNARDS_INTRODUCTION_ID,1)
          st.set("cond","2")
    elif event == "7626_3" :
          htmltext = "7626-06.htm"
    elif event == "7626_4" :
          htmltext = "7626-07.htm"
          st.giveItems(LETTER_OF_HAMIL2_ID,1)
          st.takeItems(HUNTERS_RUNE1_ID,10)
          st.set("cond","5")
    elif event == "7653_1" :
          htmltext = "7653-02.htm"
          st.takeItems(LETTER_OF_HAMIL1_ID,1)
          st.set("cond","3")
    elif event == "7514_1" :
          htmltext = "7514-02.htm"
          st.takeItems(LETTER_OF_HAMIL2_ID,1)
          st.set("cond","6")
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7702 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if int(st.get("cond")) < 15 :
        if (st.getPlayer().getClassId().getId() == 0x07 or st.getPlayer().getClassId().getId() == 0x16 or st.getPlayer().getClassId().getId() == 0x23) and st.getPlayer().getLevel() >= 3 :
          htmltext = "7702-03.htm"
        elif st.getPlayer().getClassId().getId() == 0x07 or st.getPlayer().getClassId().getId() == 0x16 or st.getPlayer().getClassId().getId() == 0x23 :
          htmltext = "7702-01.htm"
        else:
          htmltext = "7702-02.htm"
      else:
        htmltext = "7702-02.htm"
   elif npcId == 7702 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7702 and int(st.get("cond"))==1 and st.getQuestItemsCount(BERNARDS_INTRODUCTION_ID) :
      htmltext = "7702-05.htm"
   elif npcId == 7626 and int(st.get("cond"))==1 and st.getQuestItemsCount(BERNARDS_INTRODUCTION_ID) :
      htmltext = "7626-01.htm"
   elif npcId == 7626 and int(st.get("cond"))==2 and st.getQuestItemsCount(LETTER_OF_HAMIL1_ID) :
      htmltext = "7626-04.htm"
   elif npcId == 7626 and int(st.get("cond"))==4 and st.getQuestItemsCount(HUNTERS_RUNE1_ID)==10 :
      htmltext = "7626-05.htm"
   elif npcId == 7626 and int(st.get("cond"))==5 and st.getQuestItemsCount(LETTER_OF_HAMIL2_ID) :
      htmltext = "7626-08.htm"
   elif npcId == 7626 and int(st.get("cond"))==8 :
      htmltext = "7626-09.htm"
      st.giveItems(LETTER_OF_HAMIL3_ID,1)
      st.takeItems(HUNTERS_RUNE2_ID,10)
      st.set("cond","9")
   elif npcId == 7626 and int(st.get("cond"))==9 and st.getQuestItemsCount(LETTER_OF_HAMIL3_ID) :
      htmltext = "7626-10.htm"
   elif npcId == 7626 and int(st.get("cond"))==12 and st.getQuestItemsCount(CRESCENT_MOON_BOW_ID) :
      htmltext = "7626-11.htm"
      st.set("cond","13")
   elif npcId == 7626 and int(st.get("cond"))==13 :
      htmltext = "7626-12.htm"
   elif npcId == 7626 and int(st.get("cond"))==14 and st.getQuestItemsCount(TALISMAN_OF_KADESH_ID) :
      htmltext = "7626-13.htm"
      st.giveItems(MARK_OF_SAGITTARIUS_ID,1)
      st.takeItems(CRESCENT_MOON_BOW_ID,1)
      st.takeItems(TALISMAN_OF_KADESH_ID,1)
      st.takeItems(BLOOD_OF_LIZARDMAN_ID,st.getQuestItemsCount(BLOOD_OF_LIZARDMAN_ID))
      if st.getGameTicks() != int(st.get("id")) :
        st.set("id",str(st.getGameTicks()))
        st.addExpAndSp(28000,3600)
      st.set("cond","0")
      st.setState(COMPLETED)
      st.playSound("ItemSound.quest_finish")
      st.set("onlyone","1")
   elif npcId == 7653 and int(st.get("cond"))==2 and st.getQuestItemsCount(LETTER_OF_HAMIL1_ID) :
      htmltext = "7653-01.htm"
   elif npcId == 7653 and int(st.get("cond"))==3 :
      htmltext = "7653-03.htm"
   elif npcId == 7514 and int(st.get("cond"))==5 and st.getQuestItemsCount(LETTER_OF_HAMIL2_ID) :
      htmltext = "7514-01.htm"
   elif npcId == 7514 and int(st.get("cond"))==6 :
      htmltext = "7514-03.htm"
   elif npcId == 7514 and int(st.get("cond"))==7 and st.getQuestItemsCount(TALISMAN_OF_SNAKE_ID) :
      htmltext = "7514-04.htm"
      st.takeItems(TALISMAN_OF_SNAKE_ID,1)
      st.set("cond","8")
   elif npcId == 7514 and int(st.get("cond"))==8 :
      htmltext = "7514-05.htm"
   elif npcId == 7717 and int(st.get("cond"))==9 and st.getQuestItemsCount(LETTER_OF_HAMIL3_ID) :
      htmltext = "7717-01.htm"
      st.takeItems(LETTER_OF_HAMIL3_ID,1)
      st.set("cond","10")
   elif npcId == 7717 and int(st.get("cond"))==10 :
      htmltext = "7717-03.htm"
   elif npcId == 7717 and int(st.get("cond"))==12 :
      htmltext = "7717-04.htm"
   elif npcId == 7717 and int(st.get("cond"))==11 and st.getQuestItemsCount(STAKATO_CHITIN_ID) and st.getQuestItemsCount(MITHRIL_CLIP_ID) and st.getQuestItemsCount(ST_BOWSTRING_ID) and st.getQuestItemsCount(MANASHENS_HORN_ID) :
      htmltext = "7717-02.htm"
      st.takeItems(MITHRIL_CLIP_ID,1)
      st.takeItems(STAKATO_CHITIN_ID,1)
      st.takeItems(ST_BOWSTRING_ID,1)
      st.takeItems(MANASHENS_HORN_ID,1)
      st.giveItems(CRESCENT_MOON_BOW_ID,1)
      st.giveItems(WOODEN_ARROW_ID,10)
      st.set("cond","12")
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 79 :
      if int(st.get("cond")) and int(st.get("cond")) == 3 and st.getQuestItemsCount(HUNTERS_RUNE1_ID) < 10 :
        if st.getRandom(2) == 1 :
          if st.getQuestItemsCount(HUNTERS_RUNE1_ID) == 9 :
            st.giveItems(HUNTERS_RUNE1_ID,1)
            st.set("cond","4")
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(HUNTERS_RUNE1_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 80 :
      if int(st.get("cond")) and int(st.get("cond")) == 3 and st.getQuestItemsCount(HUNTERS_RUNE1_ID) < 10 :
        if st.getRandom(2) == 1 :
          if st.getQuestItemsCount(HUNTERS_RUNE1_ID) == 9 :
            st.giveItems(HUNTERS_RUNE1_ID,1)
            st.set("cond","4")
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(HUNTERS_RUNE1_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 81 :
      if int(st.get("cond")) and int(st.get("cond")) == 3 and st.getQuestItemsCount(HUNTERS_RUNE1_ID) < 10 :
        if st.getRandom(2) == 1 :
          if st.getQuestItemsCount(HUNTERS_RUNE1_ID) == 9 :
            st.giveItems(HUNTERS_RUNE1_ID,1)
            st.set("cond","4")
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(HUNTERS_RUNE1_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 82 :
      if int(st.get("cond")) and int(st.get("cond")) == 3 and st.getQuestItemsCount(HUNTERS_RUNE1_ID) < 10 :
        if st.getRandom(2) == 1 :
          if st.getQuestItemsCount(HUNTERS_RUNE1_ID) == 9 :
            st.giveItems(HUNTERS_RUNE1_ID,1)
            st.set("cond","4")
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(HUNTERS_RUNE1_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 84 :
      if int(st.get("cond")) and int(st.get("cond")) == 3 and st.getQuestItemsCount(HUNTERS_RUNE1_ID) < 10 :
        if st.getRandom(2) == 1 :
          if st.getQuestItemsCount(HUNTERS_RUNE1_ID) == 9 :
            st.giveItems(HUNTERS_RUNE1_ID,1)
            st.set("cond","4")
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(HUNTERS_RUNE1_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 86 :
      if int(st.get("cond")) and int(st.get("cond")) == 3 and st.getQuestItemsCount(HUNTERS_RUNE1_ID) < 10 :
        if st.getRandom(2) == 1 :
          if st.getQuestItemsCount(HUNTERS_RUNE1_ID) == 9 :
            st.giveItems(HUNTERS_RUNE1_ID,1)
            st.set("cond","4")
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(HUNTERS_RUNE1_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 89 :
      if int(st.get("cond")) and int(st.get("cond")) == 3 and st.getQuestItemsCount(HUNTERS_RUNE1_ID) < 10 :
        if st.getRandom(2) == 1 :
          if st.getQuestItemsCount(HUNTERS_RUNE1_ID) == 9 :
            st.giveItems(HUNTERS_RUNE1_ID,1)
            st.set("cond","4")
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(HUNTERS_RUNE1_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 90 :
      if int(st.get("cond")) and int(st.get("cond")) == 3 and st.getQuestItemsCount(HUNTERS_RUNE1_ID) < 10 :
        if st.getRandom(2) == 1 :
          if st.getQuestItemsCount(HUNTERS_RUNE1_ID) == 9 :
            st.giveItems(HUNTERS_RUNE1_ID,1)
            st.set("cond","4")
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(HUNTERS_RUNE1_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 577 :
      if int(st.get("cond")) and int(st.get("cond")) == 13 and st.getQuestItemsCount(BLOOD_OF_LIZARDMAN_ID) < 140 :
        if ((st.getQuestItemsCount(BLOOD_OF_LIZARDMAN_ID)-120)*5) > st.getRandom(100) :
          st.spawnNpc(5090)
          st.takeItems(BLOOD_OF_LIZARDMAN_ID,st.getQuestItemsCount(BLOOD_OF_LIZARDMAN_ID))
          st.playSound("Itemsound.quest_before_battle")
        else:
          st.giveItems(BLOOD_OF_LIZARDMAN_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 578 :
      if int(st.get("cond")) and int(st.get("cond")) == 13 and st.getQuestItemsCount(BLOOD_OF_LIZARDMAN_ID) < 140 :
        if ((st.getQuestItemsCount(BLOOD_OF_LIZARDMAN_ID)-120)*5) > st.getRandom(100) :
          st.spawnNpc(5090)
          st.takeItems(BLOOD_OF_LIZARDMAN_ID,st.getQuestItemsCount(BLOOD_OF_LIZARDMAN_ID))
          st.playSound("Itemsound.quest_before_battle")
        else:
          st.giveItems(BLOOD_OF_LIZARDMAN_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 579 :
      if int(st.get("cond")) and int(st.get("cond")) == 13 and st.getQuestItemsCount(BLOOD_OF_LIZARDMAN_ID) < 140 :
        if ((st.getQuestItemsCount(BLOOD_OF_LIZARDMAN_ID)-120)*5) > st.getRandom(100) :
          st.spawnNpc(5090)
          st.takeItems(BLOOD_OF_LIZARDMAN_ID,st.getQuestItemsCount(BLOOD_OF_LIZARDMAN_ID))
          st.playSound("Itemsound.quest_before_battle")
        else:
          st.giveItems(BLOOD_OF_LIZARDMAN_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 580 :
      if int(st.get("cond")) and int(st.get("cond")) == 13 and st.getQuestItemsCount(BLOOD_OF_LIZARDMAN_ID) < 140 :
        if ((st.getQuestItemsCount(BLOOD_OF_LIZARDMAN_ID)-120)*5) > st.getRandom(100) :
          st.spawnNpc(5090)
          st.takeItems(BLOOD_OF_LIZARDMAN_ID,st.getQuestItemsCount(BLOOD_OF_LIZARDMAN_ID))
          st.playSound("Itemsound.quest_before_battle")
        else:
          st.giveItems(BLOOD_OF_LIZARDMAN_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 581 :
      if int(st.get("cond")) and int(st.get("cond")) == 13 and st.getQuestItemsCount(BLOOD_OF_LIZARDMAN_ID) < 140 :
        if ((st.getQuestItemsCount(BLOOD_OF_LIZARDMAN_ID)-120)*5) > st.getRandom(100) :
          st.spawnNpc(5090)
          st.takeItems(BLOOD_OF_LIZARDMAN_ID,st.getQuestItemsCount(BLOOD_OF_LIZARDMAN_ID))
          st.playSound("Itemsound.quest_before_battle")
        else:
          st.giveItems(BLOOD_OF_LIZARDMAN_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 582 :
      if int(st.get("cond")) and int(st.get("cond")) == 13 and st.getQuestItemsCount(BLOOD_OF_LIZARDMAN_ID) < 140 :
        if ((st.getQuestItemsCount(BLOOD_OF_LIZARDMAN_ID)-120)*5) > st.getRandom(100) :
          st.spawnNpc(5090)
          st.takeItems(BLOOD_OF_LIZARDMAN_ID,st.getQuestItemsCount(BLOOD_OF_LIZARDMAN_ID))
          st.playSound("Itemsound.quest_before_battle")
        else:
          st.giveItems(BLOOD_OF_LIZARDMAN_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 230 :
      if int(st.get("cond")) and int(st.get("cond")) == 10 and st.getQuestItemsCount(STAKATO_CHITIN_ID) == 0 :
        if st.getRandom(10) == 1 :
          if st.getQuestItemsCount(MITHRIL_CLIP_ID) and st.getQuestItemsCount(ST_BOWSTRING_ID) and st.getQuestItemsCount(MANASHENS_HORN_ID) :
            st.giveItems(STAKATO_CHITIN_ID,1)
            st.set("cond","11")
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(STAKATO_CHITIN_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 232 :
      if int(st.get("cond")) and int(st.get("cond")) == 10 and st.getQuestItemsCount(STAKATO_CHITIN_ID) == 0 :
        if st.getRandom(10) == 1 :
          if st.getQuestItemsCount(MITHRIL_CLIP_ID) and st.getQuestItemsCount(ST_BOWSTRING_ID) and st.getQuestItemsCount(MANASHENS_HORN_ID) :
            st.giveItems(STAKATO_CHITIN_ID,1)
            st.set("cond","11")
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(STAKATO_CHITIN_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 234 :
      if int(st.get("cond")) and int(st.get("cond")) == 10 and st.getQuestItemsCount(STAKATO_CHITIN_ID) == 0 :
        if st.getRandom(10) == 1 :
          if st.getQuestItemsCount(MITHRIL_CLIP_ID) and st.getQuestItemsCount(ST_BOWSTRING_ID) and st.getQuestItemsCount(MANASHENS_HORN_ID) :
            st.giveItems(STAKATO_CHITIN_ID,1)
            st.set("cond","11")
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(STAKATO_CHITIN_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 563 :
      if int(st.get("cond")) and int(st.get("cond")) == 10 and st.getQuestItemsCount(MANASHENS_HORN_ID) == 0 :
        if st.getRandom(10) == 1 :
          if st.getQuestItemsCount(MITHRIL_CLIP_ID) and st.getQuestItemsCount(ST_BOWSTRING_ID) and st.getQuestItemsCount(STAKATO_CHITIN_ID) :
            st.giveItems(MANASHENS_HORN_ID,1)
            st.set("cond","11")
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(MANASHENS_HORN_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 233 :
      if int(st.get("cond")) and int(st.get("cond")) == 10 and st.getQuestItemsCount(ST_BOWSTRING_ID) == 0 :
        if st.getRandom(10) == 1 :
          if st.getQuestItemsCount(MITHRIL_CLIP_ID) and st.getQuestItemsCount(MANASHENS_HORN_ID) and st.getQuestItemsCount(STAKATO_CHITIN_ID) :
            st.giveItems(ST_BOWSTRING_ID,1)
            st.set("cond","11")
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(ST_BOWSTRING_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 551 :
      if int(st.get("cond")) and int(st.get("cond")) == 10 and st.getQuestItemsCount(MITHRIL_CLIP_ID) == 0 :
        if st.getRandom(10) == 1 :
          if st.getQuestItemsCount(ST_BOWSTRING_ID) and st.getQuestItemsCount(MANASHENS_HORN_ID) and st.getQuestItemsCount(STAKATO_CHITIN_ID) :
            st.giveItems(MITHRIL_CLIP_ID,1)
            st.set("cond","11")
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(MITHRIL_CLIP_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 269 :
      if int(st.get("cond")) and int(st.get("cond")) == 6 and st.getQuestItemsCount(HUNTERS_RUNE2_ID) < 10 :
        if st.getRandom(2) == 1 :
          if st.getQuestItemsCount(HUNTERS_RUNE2_ID) == 9 :
            st.giveItems(HUNTERS_RUNE2_ID,1)
            st.giveItems(TALISMAN_OF_SNAKE_ID,1)
            st.set("cond","7")
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(HUNTERS_RUNE2_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 270 :
      if int(st.get("cond")) and int(st.get("cond")) == 6 and st.getQuestItemsCount(HUNTERS_RUNE2_ID) < 10 :
        if st.getRandom(2) == 1 :
          if st.getQuestItemsCount(HUNTERS_RUNE2_ID) == 9 :
            st.giveItems(HUNTERS_RUNE2_ID,1)
            st.giveItems(TALISMAN_OF_SNAKE_ID,1)
            st.set("cond","7")
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(HUNTERS_RUNE2_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 5090 :
      if int(st.get("cond")) and int(st.get("cond")) == 13 and st.getQuestItemsCount(TALISMAN_OF_KADESH_ID) == 0 :
        if st.getQuestItemsCount(CRESCENT_MOON_BOW_ID) > 0 :
          st.giveItems(TALISMAN_OF_KADESH_ID,1)
          st.set("cond","14")
          st.playSound("Itemsound.quest_middle")
        else:
          st.spawnNpc(5090)
   elif npcId == 551 :
      if int(st.get("cond")) and int(st.get("cond")) == 10 and st.getQuestItemsCount(MITHRIL_CLIP_ID) == 0 :
        if st.getRandom(10) == 1 :
          if st.getQuestItemsCount(ST_BOWSTRING_ID) and st.getQuestItemsCount(MANASHENS_HORN_ID) and st.getQuestItemsCount(STAKATO_CHITIN_ID) :
            st.giveItems(MITHRIL_CLIP_ID,1)
            st.set("cond","11")
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(MITHRIL_CLIP_ID,1)
            st.playSound("Itemsound.quest_itemget")
   return

QUEST       = Quest(224,"224_TestOfSagittarius","Test Of Sagittarius")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7702)

STARTING.addTalkId(7702)

STARTED.addTalkId(7514)
STARTED.addTalkId(7626)
STARTED.addTalkId(7653)
STARTED.addTalkId(7702)
STARTED.addTalkId(7717)

STARTED.addKillId(230)
STARTED.addKillId(232)
STARTED.addKillId(233)
STARTED.addKillId(234)
STARTED.addKillId(269)
STARTED.addKillId(270)
STARTED.addKillId(5090)
STARTED.addKillId(551)
STARTED.addKillId(563)
STARTED.addKillId(577)
STARTED.addKillId(578)
STARTED.addKillId(579)
STARTED.addKillId(580)
STARTED.addKillId(581)
STARTED.addKillId(582)
STARTED.addKillId(79)
STARTED.addKillId(80)
STARTED.addKillId(81)
STARTED.addKillId(82)
STARTED.addKillId(84)
STARTED.addKillId(86)
STARTED.addKillId(89)
STARTED.addKillId(90)

STARTED.addQuestDrop(269,HUNTERS_RUNE2_ID,1)
STARTED.addQuestDrop(270,HUNTERS_RUNE2_ID,1)
STARTED.addQuestDrop(7717,CRESCENT_MOON_BOW_ID,1)
STARTED.addQuestDrop(5090,TALISMAN_OF_KADESH_ID,1)
STARTED.addQuestDrop(577,BLOOD_OF_LIZARDMAN_ID,1)
STARTED.addQuestDrop(578,BLOOD_OF_LIZARDMAN_ID,1)
STARTED.addQuestDrop(579,BLOOD_OF_LIZARDMAN_ID,1)
STARTED.addQuestDrop(580,BLOOD_OF_LIZARDMAN_ID,1)
STARTED.addQuestDrop(581,BLOOD_OF_LIZARDMAN_ID,1)
STARTED.addQuestDrop(582,BLOOD_OF_LIZARDMAN_ID,1)
STARTED.addQuestDrop(7702,BERNARDS_INTRODUCTION_ID,1)
STARTED.addQuestDrop(79,HUNTERS_RUNE1_ID,1)
STARTED.addQuestDrop(80,HUNTERS_RUNE1_ID,1)
STARTED.addQuestDrop(81,HUNTERS_RUNE1_ID,1)
STARTED.addQuestDrop(82,HUNTERS_RUNE1_ID,1)
STARTED.addQuestDrop(84,HUNTERS_RUNE1_ID,1)
STARTED.addQuestDrop(86,HUNTERS_RUNE1_ID,1)
STARTED.addQuestDrop(89,HUNTERS_RUNE1_ID,1)
STARTED.addQuestDrop(90,HUNTERS_RUNE1_ID,1)
STARTED.addQuestDrop(7626,LETTER_OF_HAMIL1_ID,1)
STARTED.addQuestDrop(269,TALISMAN_OF_SNAKE_ID,1)
STARTED.addQuestDrop(270,TALISMAN_OF_SNAKE_ID,1)
STARTED.addQuestDrop(7626,LETTER_OF_HAMIL2_ID,1)
STARTED.addQuestDrop(7626,LETTER_OF_HAMIL3_ID,1)
STARTED.addQuestDrop(551,MITHRIL_CLIP_ID,1)
STARTED.addQuestDrop(230,STAKATO_CHITIN_ID,1)
STARTED.addQuestDrop(232,STAKATO_CHITIN_ID,1)
STARTED.addQuestDrop(234,STAKATO_CHITIN_ID,1)
STARTED.addQuestDrop(233,ST_BOWSTRING_ID,1)
STARTED.addQuestDrop(563,MANASHENS_HORN_ID,1)
