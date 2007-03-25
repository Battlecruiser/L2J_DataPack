# Maked by Mr. Have fun! Version 0.2
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "404_PathToWizard"

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
            htmltext = "30391-03.htm"
          else:
            htmltext = "30391-08.htm"
            st.set("cond","1")
            st.setState(STARTED)
            st.playSound("ItemSound.quest_accept")
        else:
            htmltext = "30391-02.htm"
      else:
        if st.getPlayer().getClassId().getId() == 0x0b :
          htmltext = "30391-02a.htm"
        else:
          htmltext = "30391-01.htm"
    elif event == "30410_1" :
          if st.getQuestItemsCount(WIND_FEATHER_ID) == 0 :
            htmltext = "30410-03.htm"
            st.giveItems(WIND_FEATHER_ID,1)
            st.set("cond","6")
    return htmltext


 def onTalk (self,npc,player):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   id = st.getState()
   if npcId != 30391 and id != STARTED : return htmltext
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 30391 and int(st.get("cond"))==0 :
      #Talking to Parina before completing this quest
      if int(st.get("cond"))<15 :
        htmltext = "30391-04.htm"
        return htmltext
      else:
        htmltext = "30391-04.htm"
   elif npcId == 30391 and int(st.get("cond"))!=0 and (st.getQuestItemsCount(FLAME_EARING_ID)==0 or st.getQuestItemsCount(WIND_BANGEL_ID)==0 or st.getQuestItemsCount(WATER_NECKLACE_ID)==0 or st.getQuestItemsCount(EARTH_RING_ID)==0) :
      htmltext = "30391-05.htm"
   elif npcId == 30411 and int(st.get("cond"))!=0 and st.getQuestItemsCount(MAP_OF_LUSTER_ID)==0 and st.getQuestItemsCount(FLAME_EARING_ID)==0 :
        #Taking to the Flame salamander for the first time
        #gains us the MAP_OF_LUSTER_ID
        #and flags cond = 2
        if st.getQuestItemsCount(MAP_OF_LUSTER_ID) == 0 :
          st.giveItems(MAP_OF_LUSTER_ID,1)
        htmltext = "30411-01.htm"
        st.set("cond","2")
   elif npcId == 30411 and int(st.get("cond"))!=0 and st.getQuestItemsCount(MAP_OF_LUSTER_ID)!=0 and st.getQuestItemsCount(KEY_OF_FLAME_ID)==0 :
        #Talking to the Flame Salamander more than once
        #without the KEY_OF_FLAME_ID
        #But with the MAP_OF_LUSTER_ID
        #results in the following text
        htmltext = "30411-02.htm"
   elif npcId == 30411 and int(st.get("cond"))!=0 and st.getQuestItemsCount(MAP_OF_LUSTER_ID)!=0 and st.getQuestItemsCount(KEY_OF_FLAME_ID)!=0 :
        #Talking to the Flame Salamander when Cond != 0
        #while we have a KEY_OF_FLAME_ID from the ratmen and the MAP_OF_LUSTER_ID

        #Remove both Items and give a FLAME_EARING_ID
        #Set the cond flag to 4 to signify we have completed the first part
        st.takeItems(KEY_OF_FLAME_ID,st.getQuestItemsCount(KEY_OF_FLAME_ID))
        st.takeItems(MAP_OF_LUSTER_ID,st.getQuestItemsCount(MAP_OF_LUSTER_ID))
        if st.getQuestItemsCount(FLAME_EARING_ID) == 0 :
          st.giveItems(FLAME_EARING_ID,1)
        htmltext = "30411-03.htm"
        st.set("cond","4")
   elif npcId == 30411 and int(st.get("cond"))!=0 and st.getQuestItemsCount(FLAME_EARING_ID)!=0 :
        #Talking to the Flame Salamander
        #after finishing the Fire component results
        #in the following text
        htmltext = "30411-04.htm"
   elif npcId == 30412 and int(st.get("cond"))!=0 and st.getQuestItemsCount(FLAME_EARING_ID)!=0 and st.getQuestItemsCount(BROKEN_BRONZE_MIRROR_ID)==0 and st.getQuestItemsCount(WIND_BANGEL_ID)==0 :
        #Talking to the Wind Sylph for the first time
        #With a FLAME_EARING_ID (fire component complete)

        #Gives us a BROKEN_BRONZE_MIRROR_ID
        #and sets cond = 5
        if st.getQuestItemsCount(BROKEN_BRONZE_MIRROR_ID) == 0 :
          st.giveItems(BROKEN_BRONZE_MIRROR_ID,1)
        htmltext = "30412-01.htm"
        st.set("cond","5")
   elif npcId == 30412 and int(st.get("cond"))!=0 and st.getQuestItemsCount(BROKEN_BRONZE_MIRROR_ID)!=0 and st.getQuestItemsCount(WIND_FEATHER_ID)==0 :
        #Talking to the Wind Sylph for a second time
        #results in the following text
        htmltext = "30412-02.htm"
   elif npcId == 30412 and int(st.get("cond"))!=0 and st.getQuestItemsCount(BROKEN_BRONZE_MIRROR_ID)!=0 and st.getQuestItemsCount(WIND_FEATHER_ID)!=0 :
        #Talking to the Wind Sylph with cond != 0
        #while having a BROKEN_BRONZE_MIRROR_ID and a WIND_FEATHER_ID

        #Removes both items
        #Gives a WIND_BANGEL_ID
        #and sets cond = 7
        st.takeItems(WIND_FEATHER_ID,st.getQuestItemsCount(WIND_FEATHER_ID))
        st.takeItems(BROKEN_BRONZE_MIRROR_ID,st.getQuestItemsCount(BROKEN_BRONZE_MIRROR_ID))
        if st.getQuestItemsCount(WIND_BANGEL_ID) == 0 :
          st.giveItems(WIND_BANGEL_ID,1)
        htmltext = "30412-03.htm"
        st.set("cond","7")
   elif npcId == 30412 and int(st.get("cond"))!=0 and st.getQuestItemsCount(WIND_BANGEL_ID)!=0 :
        #Talking to the Wind Sylph after we get the WIND_BANGLE_ID
        #results in the following text
        htmltext = "30412-04.htm"
   elif npcId == 30410 and int(st.get("cond"))!=0 and st.getQuestItemsCount(BROKEN_BRONZE_MIRROR_ID)!=0 and st.getQuestItemsCount(WIND_FEATHER_ID)==0 :
        #Talking to the Lizardman of the Wastelands for the first time
        #begins this conversation
        htmltext = "30410-01.htm"
   elif npcId == 30410 and int(st.get("cond"))!=0 and st.getQuestItemsCount(BROKEN_BRONZE_MIRROR_ID)!=0 and st.getQuestItemsCount(WIND_FEATHER_ID)!=0 :
        #Talking to the Lizardman of the Wastelands after obtaining
        #the WIND_FEATHER_ID
        htmltext = "30410-04.htm"
   elif npcId == 30413 and int(st.get("cond"))!=0 and st.getQuestItemsCount(WIND_BANGEL_ID)!=0 and st.getQuestItemsCount(RAMAS_DIARY_ID)==0 and st.getQuestItemsCount(WATER_NECKLACE_ID)==0 :
        #Talking to the Water Undine for the first time
        #gives RAMAS_DIARY_ID
        #and sets cond = 8
        if st.getQuestItemsCount(RAMAS_DIARY_ID) == 0 :
          st.giveItems(RAMAS_DIARY_ID,1)
        htmltext = "30413-01.htm"
        st.set("cond","8")
   elif npcId == 30413 and int(st.get("cond"))!=0 and st.getQuestItemsCount(RAMAS_DIARY_ID)!=0 and st.getQuestItemsCount(SPARKLE_PEBBLE_ID)<2 :
        #Talking to the Water Undine for a second time
        #without 2 SPARKLE_PEBLE_ID
        htmltext = "30413-02.htm"
   elif npcId == 30413 and int(st.get("cond"))!=0 and st.getQuestItemsCount(RAMAS_DIARY_ID)!=0 and st.getQuestItemsCount(SPARKLE_PEBBLE_ID)>=2 :
        #Talking to the Water Undine with the 2 SPARKLE_PEBLE_ID

        #removes both items
        #and gives WATER_NECKLACE_ID
        #sets cond = 10
        st.takeItems(SPARKLE_PEBBLE_ID,st.getQuestItemsCount(SPARKLE_PEBBLE_ID))
        st.takeItems(RAMAS_DIARY_ID,st.getQuestItemsCount(RAMAS_DIARY_ID))
        if st.getQuestItemsCount(WATER_NECKLACE_ID) == 0 :
          st.giveItems(WATER_NECKLACE_ID,1)
        htmltext = "30413-03.htm"
        st.set("cond","10")
   elif npcId == 30413 and int(st.get("cond"))!=0 and st.getQuestItemsCount(WATER_NECKLACE_ID)!=0 :
        #Talking to the Water Undine after completing it's task
        htmltext = "30413-04.htm"
   elif npcId == 30409 and int(st.get("cond"))!=0 and st.getQuestItemsCount(WATER_NECKLACE_ID)!=0 and st.getQuestItemsCount(RUST_GOLD_COIN_ID)==0 and st.getQuestItemsCount(EARTH_RING_ID)==0 :
        #Talking to the Earth Snake for the first time
        if st.getQuestItemsCount(RUST_GOLD_COIN_ID) == 0 :
          st.giveItems(RUST_GOLD_COIN_ID,1)
        htmltext = "30409-01.htm"
        st.set("cond","11")
   elif npcId == 30409 and int(st.get("cond"))!=0 and st.getQuestItemsCount(RUST_GOLD_COIN_ID)!=0 and st.getQuestItemsCount(RED_SOIL_ID)==0 :
        #Talking to the Earth Snake for a second time
        #without RED_SOIL_ID
        htmltext = "30409-02.htm"
   elif npcId == 30409 and int(st.get("cond"))!=0 and st.getQuestItemsCount(RUST_GOLD_COIN_ID)!=0 and st.getQuestItemsCount(RED_SOIL_ID)!=0 :
        #Talking to the Earth Snake afket collecting the RED_SOIL_ID

        #Gives EARTH_RING_ID
        #and sets cond = 13
        st.takeItems(RED_SOIL_ID,st.getQuestItemsCount(RED_SOIL_ID))
        st.takeItems(RUST_GOLD_COIN_ID,st.getQuestItemsCount(RUST_GOLD_COIN_ID))
        if st.getQuestItemsCount(EARTH_RING_ID) == 0 :
          st.giveItems(EARTH_RING_ID,1)
        htmltext = "30409-03.htm"
        st.set("cond","13")
   elif npcId == 30409 and int(st.get("cond"))!=0 and st.getQuestItemsCount(EARTH_RING_ID)!=0 :
        #Talking to the Earth Snake after completing his task
        htmltext = "30409-03.htm"
   elif npcId == 30391 and int(st.get("cond"))!=0 and st.getQuestItemsCount(FLAME_EARING_ID)!=0 and st.getQuestItemsCount(WIND_BANGEL_ID)!=0 and st.getQuestItemsCount(WATER_NECKLACE_ID)!=0 and st.getQuestItemsCount(EARTH_RING_ID)!=0 :
        #Talking to Parina after gathering all 4 tokens
        #Gains BEAD_OF_SEASON
        #Resets cond so these NPC's will no longer speak to you
        #and Sets the quest as completed
        st.takeItems(FLAME_EARING_ID,st.getQuestItemsCount(FLAME_EARING_ID))
        st.takeItems(WIND_BANGEL_ID,st.getQuestItemsCount(WIND_BANGEL_ID))
        st.takeItems(WATER_NECKLACE_ID,st.getQuestItemsCount(WATER_NECKLACE_ID))
        st.takeItems(EARTH_RING_ID,st.getQuestItemsCount(EARTH_RING_ID))
        st.set("cond","0")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
        if st.getQuestItemsCount(BEAD_OF_SEASON_ID) == 0 :
          st.giveItems(BEAD_OF_SEASON_ID,1)
        htmltext = "30391-06.htm"
        
   return htmltext

 def onKill (self,npc,player):
   st = player.getQuestState(qn)
   if not st : return 
   if st.getState() != STARTED : return 
   
   npcId = npc.getNpcId()
   if npcId == 20359 :    #Ratman Warrior, as of C3.
        st.set("id","0")
        #Only get a KEY_OF_FLAME_ID if we are on the quest for the Fire Salamander
        if int(st.get("cond")) == 2 :
            st.giveItems(KEY_OF_FLAME_ID,1)
            st.playSound("ItemSound.quest_middle")
            #Increase the Cond so we can only get one key
            st.set("cond","3")
   elif npcId == 27030 : #water seer
        st.set("id","0")
        #Only get a SPARKLE_PEBBLE_ID if we are on the quest for the Water Undine
        if int(st.get("cond")) == 8 and st.getQuestItemsCount(SPARKLE_PEBBLE_ID) < 2:
            st.giveItems(SPARKLE_PEBBLE_ID,1)
            if st.getQuestItemsCount(SPARKLE_PEBBLE_ID) == 2 :
              st.playSound("ItemSound.quest_middle")
              st.set("cond","9")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 20021 :   #Red Bear
        st.set("id","0")
        #Only get a RED_SOIL_ID if we are on the quest for the Earth Snake
        if int(st.get("cond")) == 11 :
            st.giveItems(RED_SOIL_ID,1)
            st.playSound("ItemSound.quest_middle")
            st.set("cond","12")
   return

QUEST       = Quest(404,qn,"Path To Wizard")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30391)

QUEST.addTalkId(30391)

QUEST.addTalkId(30409)
QUEST.addTalkId(30410)
QUEST.addTalkId(30411)
QUEST.addTalkId(30412)
QUEST.addTalkId(30413)

QUEST.addKillId(20021)
QUEST.addKillId(20359)
QUEST.addKillId(27030)

STARTED.addQuestDrop(20359,KEY_OF_FLAME_ID,1)
STARTED.addQuestDrop(30411,MAP_OF_LUSTER_ID,1)
STARTED.addQuestDrop(30410,WIND_FEATHER_ID,1)
STARTED.addQuestDrop(30412,BROKEN_BRONZE_MIRROR_ID,1)
STARTED.addQuestDrop(27030,SPARKLE_PEBBLE_ID,1)
STARTED.addQuestDrop(30413,RAMAS_DIARY_ID,1)
STARTED.addQuestDrop(20021,RED_SOIL_ID,1)
STARTED.addQuestDrop(30409,RUST_GOLD_COIN_ID,1)
STARTED.addQuestDrop(30411,FLAME_EARING_ID,1)
STARTED.addQuestDrop(30412,WIND_BANGEL_ID,1)
STARTED.addQuestDrop(30413,WATER_NECKLACE_ID,1)
STARTED.addQuestDrop(30409,EARTH_RING_ID,1)

print "importing quests: 404: Path To Wizard"