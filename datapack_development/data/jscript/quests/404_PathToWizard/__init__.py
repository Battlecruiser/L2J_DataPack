# Maked by Mr. Have fun! Version 0.2
print "importing quests: 404: Path To Wizard"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MAP_OF_LUSTER_ID = 1280
KEY_OF_FLAME_ID = 1281
FLAME_EARING_ID = 1282
BROKEN_BRONZE_MIRROR_ID = 1283
WIND_FEATHER_ID = 1284
WIND_BANGEL_ID = 1285
RAMAS_DIARY_ID = 1286
SPARKLE_PEBBLE_ID = 1287
WATER_NECKLACE_ID = 1288
RUST_GOLD_COIN_ID = 1289
RED_SOIL_ID = 1290
EARTH_RING_ID = 1291
BEAD_OF_SEASON_ID = 1292

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      if st.getPlayer().getClassId().getId() == 0x0a :
        if st.getPlayer().getLevel() >= 19 :
          if st.getQuestItemsCount(BEAD_OF_SEASON_ID) :
            htmltext = "7391-03.htm"
          else:
            htmltext = "7391-08.htm"
            st.setState(STARTED)
            st.playSound("ItemSound.quest_accept")
        else:
            htmltext = "7391-02.htm"
      else:
        if st.getPlayer().getClassId().getId() == 0x0b :
          htmltext = "7391-02a.htm"
        else:
          htmltext = "7391-01.htm"
    elif event == "7410_1" :
          if st.getQuestItemsCount(WIND_FEATHER_ID) == 0 :
            htmltext = "7410-03.htm"
            st.giveItems(WIND_FEATHER_ID,1)
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7391 and int(st.get("cond"))==0 :
      if int(st.get("cond"))<15 :
        htmltext = "7391-04.htm"
        st.set("cond","1")
        return htmltext
      else:
        htmltext = "7391-04.htm"
   elif npcId == 7391 and int(st.get("cond"))!=0 and (st.getQuestItemsCount(FLAME_EARING_ID)==0 or st.getQuestItemsCount(WIND_BANGEL_ID)==0 or st.getQuestItemsCount(WATER_NECKLACE_ID)==0 or st.getQuestItemsCount(EARTH_RING_ID)==0) :
      htmltext = "7391-05.htm"
   elif npcId == 7411 and int(st.get("cond"))!=0 and st.getQuestItemsCount(MAP_OF_LUSTER_ID)==0 and st.getQuestItemsCount(FLAME_EARING_ID)==0 :
        if st.getQuestItemsCount(MAP_OF_LUSTER_ID) == 0 :
          st.giveItems(MAP_OF_LUSTER_ID,1)
        htmltext = "7411-01.htm"
   elif npcId == 7411 and int(st.get("cond"))!=0 and st.getQuestItemsCount(MAP_OF_LUSTER_ID)!=0 and st.getQuestItemsCount(KEY_OF_FLAME_ID)==0 :
        htmltext = "7411-02.htm"
   elif npcId == 7411 and int(st.get("cond"))!=0 and st.getQuestItemsCount(MAP_OF_LUSTER_ID)!=0 and st.getQuestItemsCount(KEY_OF_FLAME_ID)!=0 :
        st.takeItems(KEY_OF_FLAME_ID,st.getQuestItemsCount(KEY_OF_FLAME_ID))
        st.takeItems(MAP_OF_LUSTER_ID,st.getQuestItemsCount(MAP_OF_LUSTER_ID))
        if st.getQuestItemsCount(FLAME_EARING_ID) == 0 :
          st.giveItems(FLAME_EARING_ID,1)
        htmltext = "7411-03.htm"
   elif npcId == 7411 and int(st.get("cond"))!=0 and st.getQuestItemsCount(FLAME_EARING_ID)!=0 :
        htmltext = "7411-04.htm"
   elif npcId == 7412 and int(st.get("cond"))!=0 and st.getQuestItemsCount(FLAME_EARING_ID)!=0 and st.getQuestItemsCount(BROKEN_BRONZE_MIRROR_ID)==0 and st.getQuestItemsCount(WIND_BANGEL_ID)==0 :
        if st.getQuestItemsCount(BROKEN_BRONZE_MIRROR_ID) == 0 :
          st.giveItems(BROKEN_BRONZE_MIRROR_ID,1)
        htmltext = "7412-01.htm"
   elif npcId == 7412 and int(st.get("cond"))!=0 and st.getQuestItemsCount(BROKEN_BRONZE_MIRROR_ID)!=0 and st.getQuestItemsCount(WIND_FEATHER_ID)==0 :
        htmltext = "7412-02.htm"
   elif npcId == 7412 and int(st.get("cond"))!=0 and st.getQuestItemsCount(BROKEN_BRONZE_MIRROR_ID)!=0 and st.getQuestItemsCount(WIND_FEATHER_ID)!=0 :
        st.takeItems(WIND_FEATHER_ID,st.getQuestItemsCount(WIND_FEATHER_ID))
        st.takeItems(BROKEN_BRONZE_MIRROR_ID,st.getQuestItemsCount(BROKEN_BRONZE_MIRROR_ID))
        if st.getQuestItemsCount(WIND_BANGEL_ID) == 0 :
          st.giveItems(WIND_BANGEL_ID,1)
        htmltext = "7412-03.htm"
   elif npcId == 7412 and int(st.get("cond"))!=0 and st.getQuestItemsCount(WIND_BANGEL_ID)!=0 :
        htmltext = "7412-04.htm"
   elif npcId == 7410 and int(st.get("cond"))!=0 and st.getQuestItemsCount(BROKEN_BRONZE_MIRROR_ID)!=0 and st.getQuestItemsCount(WIND_FEATHER_ID)==0 :
        htmltext = "7410-01.htm"
   elif npcId == 7410 and int(st.get("cond"))!=0 and st.getQuestItemsCount(BROKEN_BRONZE_MIRROR_ID)!=0 and st.getQuestItemsCount(WIND_FEATHER_ID)!=0 :
        htmltext = "7410-04.htm"
   elif npcId == 7413 and int(st.get("cond"))!=0 and st.getQuestItemsCount(WIND_BANGEL_ID)!=0 and st.getQuestItemsCount(RAMAS_DIARY_ID)==0 and st.getQuestItemsCount(WATER_NECKLACE_ID)==0 :
        if st.getQuestItemsCount(RAMAS_DIARY_ID) == 0 :
          st.giveItems(RAMAS_DIARY_ID,1)
        htmltext = "7413-01.htm"
   elif npcId == 7413 and int(st.get("cond"))!=0 and st.getQuestItemsCount(RAMAS_DIARY_ID)!=0 and st.getQuestItemsCount(SPARKLE_PEBBLE_ID)<2 :
        htmltext = "7413-02.htm"
   elif npcId == 7413 and int(st.get("cond"))!=0 and st.getQuestItemsCount(RAMAS_DIARY_ID)!=0 and st.getQuestItemsCount(SPARKLE_PEBBLE_ID)>=2 :
        st.takeItems(SPARKLE_PEBBLE_ID,st.getQuestItemsCount(SPARKLE_PEBBLE_ID))
        st.takeItems(RAMAS_DIARY_ID,st.getQuestItemsCount(RAMAS_DIARY_ID))
        if st.getQuestItemsCount(WATER_NECKLACE_ID) == 0 :
          st.giveItems(WATER_NECKLACE_ID,1)
        htmltext = "7413-03.htm"
   elif npcId == 7413 and int(st.get("cond"))!=0 and st.getQuestItemsCount(WATER_NECKLACE_ID)!=0 :
        htmltext = "7413-04.htm"
   elif npcId == 7409 and int(st.get("cond"))!=0 and st.getQuestItemsCount(WATER_NECKLACE_ID)!=0 and st.getQuestItemsCount(RUST_GOLD_COIN_ID)==0 and st.getQuestItemsCount(EARTH_RING_ID)==0 :
        if st.getQuestItemsCount(RUST_GOLD_COIN_ID) == 0 :
          st.giveItems(RUST_GOLD_COIN_ID,1)
        htmltext = "7409-01.htm"
   elif npcId == 7409 and int(st.get("cond"))!=0 and st.getQuestItemsCount(RUST_GOLD_COIN_ID)!=0 and st.getQuestItemsCount(RED_SOIL_ID)==0 :
        htmltext = "7409-02.htm"
   elif npcId == 7409 and int(st.get("cond"))!=0 and st.getQuestItemsCount(RUST_GOLD_COIN_ID)!=0 and st.getQuestItemsCount(RED_SOIL_ID)!=0 :
        st.takeItems(RED_SOIL_ID,st.getQuestItemsCount(RED_SOIL_ID))
        st.takeItems(RUST_GOLD_COIN_ID,st.getQuestItemsCount(RUST_GOLD_COIN_ID))
        if st.getQuestItemsCount(EARTH_RING_ID) == 0 :
          st.giveItems(EARTH_RING_ID,1)
        htmltext = "7409-03.htm"
   elif npcId == 7409 and int(st.get("cond"))!=0 and st.getQuestItemsCount(EARTH_RING_ID)!=0 :
        htmltext = "7409-03.htm"
   elif npcId == 7391 and int(st.get("cond"))!=0 and st.getQuestItemsCount(FLAME_EARING_ID)!=0 and st.getQuestItemsCount(WIND_BANGEL_ID)!=0 and st.getQuestItemsCount(WATER_NECKLACE_ID)!=0 and st.getQuestItemsCount(EARTH_RING_ID)!=0 :
        st.takeItems(FLAME_EARING_ID,st.getQuestItemsCount(FLAME_EARING_ID))
        st.takeItems(WIND_BANGEL_ID,st.getQuestItemsCount(WIND_BANGEL_ID))
        st.takeItems(WATER_NECKLACE_ID,st.getQuestItemsCount(WATER_NECKLACE_ID))
        st.takeItems(EARTH_RING_ID,st.getQuestItemsCount(EARTH_RING_ID))
        st.set("cond","0")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
        if st.getQuestItemsCount(BEAD_OF_SEASON_ID) == 0 :
          st.giveItems(BEAD_OF_SEASON_ID,1)
        htmltext = "7391-06.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 32 :
        st.set("id","0")
        if int(st.get("cond")) != 0 :
            st.giveItems(KEY_OF_FLAME_ID,1)
            st.playSound("ItemSound.quest_middle")
   elif npcId == 5030 :
        st.set("id","0")
        if int(st.get("cond")) != 0 :
            st.giveItems(SPARKLE_PEBBLE_ID,1)
            if st.getQuestItemsCount(SPARKLE_PEBBLE_ID) == 10 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 21 :
        st.set("id","0")
        if int(st.get("cond")) != 0 :
            st.giveItems(RED_SOIL_ID,1)
            st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(404,"404_PathToWizard","Path To Wizard")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7391)

STARTED.addTalkId(7391)
STARTED.addTalkId(7409)
STARTED.addTalkId(7410)
STARTED.addTalkId(7411)
STARTED.addTalkId(7412)
STARTED.addTalkId(7413)

STARTED.addKillId(21)
STARTED.addKillId(32)
STARTED.addKillId(5030)

STARTED.addQuestDrop(32,KEY_OF_FLAME_ID,1)
STARTED.addQuestDrop(7411,MAP_OF_LUSTER_ID,1)
STARTED.addQuestDrop(7410,WIND_FEATHER_ID,1)
STARTED.addQuestDrop(7412,BROKEN_BRONZE_MIRROR_ID,1)
STARTED.addQuestDrop(5030,SPARKLE_PEBBLE_ID,1)
STARTED.addQuestDrop(7413,RAMAS_DIARY_ID,1)
STARTED.addQuestDrop(21,RED_SOIL_ID,1)
STARTED.addQuestDrop(7409,RUST_GOLD_COIN_ID,1)
STARTED.addQuestDrop(7411,FLAME_EARING_ID,1)
STARTED.addQuestDrop(7412,WIND_BANGEL_ID,1)
STARTED.addQuestDrop(7413,WATER_NECKLACE_ID,1)
STARTED.addQuestDrop(7409,EARTH_RING_ID,1)
