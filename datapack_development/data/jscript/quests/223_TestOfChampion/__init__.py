# Maked by Mr. Have fun! Version 0.2
print "importing quests: 223: Test Of Champion"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_CHAMPION_ID = 3276
ASCALONS_LETTER1_ID = 3277
MASONS_LETTER_ID = 3278
IRON_ROSE_RING_ID = 3279
ASCALONS_LETTER2_ID = 3280
WHITE_ROSE_INSIGNIA_ID = 3281
GROOTS_LETTER_ID = 3282
ASCALONS_LETTER3_ID = 3283
MOUENS_ORDER1_ID = 3284
MOUENS_ORDER2_ID = 3285
MOUENS_LETTER_ID = 3286
HARPYS_EGG1_ID = 3287
MEDUSA_VENOM1_ID = 3288
WINDSUS_BILE1_ID = 3289
BLOODY_AXE_HEAD_ID = 3290
ROAD_RATMAN_HEAD_ID = 3291
LETO_LIZARDMAN_FANG1_ID = 3292

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      htmlfile = "7624-06.htm"
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      st.giveItems(ASCALONS_LETTER1_ID,1)
      st.set("cond",str(0))
    elif event == "7624_1" :
          htmltext = "7624-05.htm"
          st.set("cond","1")
          return htmltext
    elif event == "7624_2" :
          htmltext = "7624-10.htm"
          st.giveItems(ASCALONS_LETTER2_ID,1)
          st.takeItems(MASONS_LETTER_ID,1)
    elif event == "7624_3" :
          htmltext = "7624-14.htm"
          st.giveItems(ASCALONS_LETTER3_ID,1)
          st.takeItems(GROOTS_LETTER_ID,1)
    elif event == "7625_1" :
          htmltext = "7625-02.htm"
    elif event == "7625_2" :
          htmltext = "7625-03.htm"
          st.giveItems(IRON_ROSE_RING_ID,1)
          st.takeItems(ASCALONS_LETTER1_ID,1)
    elif event == "7093_1" :
          htmltext = "7093-02.htm"
          st.giveItems(WHITE_ROSE_INSIGNIA_ID,1)
          st.takeItems(ASCALONS_LETTER2_ID,1)
    elif event == "7196_1" :
          htmltext = "7196-02.htm"
    elif event == "7196_2" :
          htmltext = "7196-03.htm"
          st.giveItems(MOUENS_ORDER1_ID,1)
          st.takeItems(ASCALONS_LETTER3_ID,1)
    elif event == "7196_3" :
          htmltext = "7196-06.htm"
          st.giveItems(MOUENS_ORDER2_ID,1)
          st.takeItems(MOUENS_ORDER1_ID,1)
          st.takeItems(ROAD_RATMAN_HEAD_ID,st.getQuestItemsCount(ROAD_RATMAN_HEAD_ID))
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7624 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if int(st.get("cond")) < 15 :
        if (st.getPlayer().getClassId().getId() == 0x01 or st.getPlayer().getClassId().getId() == 0x2d) and st.getPlayer().getLevel() >= 3 :
          if st.getPlayer().getClassId().getId() == 0x01 :
            htmltext = "7624-03.htm"
          else:
            htmltext = "7624-04.htm"
        elif st.getPlayer().getClassId().getId() == 0x01 or st.getPlayer().getClassId().getId() == 0x2d :
          htmltext = "7624-02.htm"
        else:
          htmltext = "7624-01.htm"
      else:
        htmltext = "7624-01.htm"
   elif npcId == 7624 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7624 and int(st.get("cond"))==1 and st.getQuestItemsCount(ASCALONS_LETTER1_ID) :
      htmltext = "7624-07.htm"
   elif npcId == 7624 and int(st.get("cond"))==1 and st.getQuestItemsCount(IRON_ROSE_RING_ID) :
      htmltext = "7624-08.htm"
   elif npcId == 7624 and int(st.get("cond"))==1 and st.getQuestItemsCount(MASONS_LETTER_ID) :
      htmltext = "7624-09.htm"
   elif npcId == 7624 and int(st.get("cond"))==1 and st.getQuestItemsCount(ASCALONS_LETTER2_ID) :
      htmltext = "7624-11.htm"
   elif npcId == 7624 and int(st.get("cond"))==1 and st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) :
      htmltext = "7624-12.htm"
   elif npcId == 7624 and int(st.get("cond"))==1 and st.getQuestItemsCount(GROOTS_LETTER_ID) :
      htmltext = "7624-13.htm"
   elif npcId == 7624 and int(st.get("cond"))==1 and st.getQuestItemsCount(ASCALONS_LETTER3_ID) :
      htmltext = "7624-15.htm"
   elif npcId == 7624 and int(st.get("cond"))==1 and (st.getQuestItemsCount(MOUENS_ORDER1_ID) or st.getQuestItemsCount(MOUENS_ORDER2_ID)) :
      htmltext = "7624-16.htm"
   elif npcId == 7624 and int(st.get("cond"))==1 and st.getQuestItemsCount(MOUENS_LETTER_ID) :
      if st.getGameTicks() != int(st.get("id")) :
        st.set("id",str(st.getGameTicks()))
        st.addExpAndSp(36000,4600)
      htmltext = "7624-17.htm"
      st.giveItems(MARK_OF_CHAMPION_ID,1)
      st.takeItems(MOUENS_LETTER_ID,1)
      st.set("cond","0")
      st.setState(COMPLETED)
      st.playSound("ItemSound.quest_finish")
      st.set("onlyone","1")
   elif npcId == 7625 and int(st.get("cond"))==1 and st.getQuestItemsCount(ASCALONS_LETTER1_ID) :
      htmltext = "7625-01.htm"
   elif npcId == 7625 and int(st.get("cond"))==1 and st.getQuestItemsCount(IRON_ROSE_RING_ID) and st.getQuestItemsCount(BLOODY_AXE_HEAD_ID)<100 :
      htmltext = "7625-04.htm"
   elif npcId == 7625 and int(st.get("cond"))==1 and st.getQuestItemsCount(IRON_ROSE_RING_ID) and st.getQuestItemsCount(BLOODY_AXE_HEAD_ID)>=100 :
      htmltext = "7625-05.htm"
      st.giveItems(MASONS_LETTER_ID,1)
      st.takeItems(IRON_ROSE_RING_ID,1)
      st.takeItems(BLOODY_AXE_HEAD_ID,st.getQuestItemsCount(BLOODY_AXE_HEAD_ID))
   elif npcId == 7625 and int(st.get("cond"))==1 and st.getQuestItemsCount(MASONS_LETTER_ID) :
      htmltext = "7625-06.htm"
   elif npcId == 7625 and int(st.get("cond"))==1 and (st.getQuestItemsCount(ASCALONS_LETTER2_ID) or st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) or st.getQuestItemsCount(ASCALONS_LETTER2_ID) or st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) or st.getQuestItemsCount(GROOTS_LETTER_ID) or st.getQuestItemsCount(ASCALONS_LETTER3_ID) or st.getQuestItemsCount(MOUENS_ORDER1_ID) or st.getQuestItemsCount(MOUENS_ORDER2_ID) or st.getQuestItemsCount(MOUENS_LETTER_ID) or st.getQuestItemsCount(GROOTS_LETTER_ID)) :
      htmltext = "7625-07.htm"
   elif npcId == 7093 and int(st.get("cond"))==1 and st.getQuestItemsCount(ASCALONS_LETTER2_ID) :
      htmltext = "7093-01.htm"
   elif npcId == 7093 and int(st.get("cond"))==1 and st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) and (st.getQuestItemsCount(HARPYS_EGG1_ID)<30 or st.getQuestItemsCount(MEDUSA_VENOM1_ID)<30 or st.getQuestItemsCount(WINDSUS_BILE1_ID)<30) :
      htmltext = "7093-03.htm"
   elif npcId == 7093 and int(st.get("cond"))==1 and st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) and st.getQuestItemsCount(HARPYS_EGG1_ID)>=30 and st.getQuestItemsCount(MEDUSA_VENOM1_ID)>=30 and st.getQuestItemsCount(WINDSUS_BILE1_ID)>=30 :
      htmltext = "7093-04.htm"
      st.giveItems(GROOTS_LETTER_ID,1)
      st.takeItems(WHITE_ROSE_INSIGNIA_ID,1)
      st.takeItems(HARPYS_EGG1_ID,st.getQuestItemsCount(HARPYS_EGG1_ID))
      st.takeItems(MEDUSA_VENOM1_ID,st.getQuestItemsCount(MEDUSA_VENOM1_ID))
      st.takeItems(WINDSUS_BILE1_ID,st.getQuestItemsCount(WINDSUS_BILE1_ID))
   elif npcId == 7093 and int(st.get("cond"))==1 and st.getQuestItemsCount(GROOTS_LETTER_ID) :
      htmltext = "7093-05.htm"
   elif npcId == 7093 and int(st.get("cond"))==1 and (st.getQuestItemsCount(ASCALONS_LETTER3_ID) or st.getQuestItemsCount(MOUENS_ORDER1_ID) or st.getQuestItemsCount(MOUENS_ORDER2_ID) or st.getQuestItemsCount(MOUENS_LETTER_ID)) :
      htmltext = "7093-06.htm"
   elif npcId == 7196 and int(st.get("cond"))==1 and st.getQuestItemsCount(ASCALONS_LETTER3_ID) :
      htmltext = "7196-01.htm"
   elif npcId == 7196 and int(st.get("cond"))==1 and st.getQuestItemsCount(MOUENS_ORDER1_ID) and st.getQuestItemsCount(ROAD_RATMAN_HEAD_ID)<100 :
      htmltext = "7196-04.htm"
   elif npcId == 7196 and int(st.get("cond"))==1 and st.getQuestItemsCount(MOUENS_ORDER1_ID) and st.getQuestItemsCount(ROAD_RATMAN_HEAD_ID)>=100 :
      htmltext = "7196-05.htm"
   elif npcId == 7196 and int(st.get("cond"))==1 and st.getQuestItemsCount(MOUENS_ORDER2_ID) and st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID)<100 :
      htmltext = "7196-07.htm"
   elif npcId == 7196 and int(st.get("cond"))==1 and st.getQuestItemsCount(MOUENS_ORDER2_ID) and st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID)>=100 :
      htmltext = "7196-08.htm"
      st.giveItems(MOUENS_LETTER_ID,1)
      st.takeItems(MOUENS_ORDER2_ID,1)
      st.takeItems(LETO_LIZARDMAN_FANG1_ID,st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID))
   elif npcId == 7196 and int(st.get("cond"))==1 and st.getQuestItemsCount(MOUENS_LETTER_ID) :
      htmltext = "7196-09.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 780 :
      if int(st.get("cond")) and st.getQuestItemsCount(IRON_ROSE_RING_ID) and st.getQuestItemsCount(BLOODY_AXE_HEAD_ID) < 100 :
        if st.getQuestItemsCount(BLOODY_AXE_HEAD_ID) == 99 :
          st.giveItems(BLOODY_AXE_HEAD_ID,1)
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(BLOODY_AXE_HEAD_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 145 :
      if int(st.get("cond")) and st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) and st.getQuestItemsCount(HARPYS_EGG1_ID) < 30 :
        if st.getRandom(2) == 1 :
          if st.getQuestItemsCount(HARPYS_EGG1_ID) == 29 :
            st.giveItems(HARPYS_EGG1_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(HARPYS_EGG1_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 5088 :
      if int(st.get("cond")) and st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) and st.getQuestItemsCount(HARPYS_EGG1_ID) < 30 :
        if st.getQuestItemsCount(HARPYS_EGG1_ID) == 29 :
          st.giveItems(HARPYS_EGG1_ID,1)
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(HARPYS_EGG1_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 158 :
      if int(st.get("cond")) and st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) and st.getQuestItemsCount(MEDUSA_VENOM1_ID) < 30 :
        if st.getRandom(2) == 1 :
          if st.getQuestItemsCount(MEDUSA_VENOM1_ID) == 29 :
            st.giveItems(MEDUSA_VENOM1_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(MEDUSA_VENOM1_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 553 :
      if int(st.get("cond")) and st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) and st.getQuestItemsCount(WINDSUS_BILE1_ID) < 30 :
        if st.getRandom(2) == 1 :
          if st.getQuestItemsCount(WINDSUS_BILE1_ID) == 29 :
            st.giveItems(WINDSUS_BILE1_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(WINDSUS_BILE1_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 551 :
      if int(st.get("cond")) and st.getQuestItemsCount(MOUENS_ORDER1_ID) and st.getQuestItemsCount(ROAD_RATMAN_HEAD_ID) < 100 :
        if st.getQuestItemsCount(ROAD_RATMAN_HEAD_ID) == 99 :
          st.giveItems(ROAD_RATMAN_HEAD_ID,1)
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(ROAD_RATMAN_HEAD_ID,1)
          st.playSound("Itemsound.quest_middle")
   elif npcId == 5089 :
      if int(st.get("cond")) and st.getQuestItemsCount(MOUENS_ORDER1_ID) and st.getQuestItemsCount(ROAD_RATMAN_HEAD_ID) < 100 :
        if st.getQuestItemsCount(ROAD_RATMAN_HEAD_ID) == 99 :
          st.giveItems(ROAD_RATMAN_HEAD_ID,1)
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(ROAD_RATMAN_HEAD_ID,1)
          st.playSound("Itemsound.quest_itemget")
   elif npcId == 577 :
      if int(st.get("cond")) and st.getQuestItemsCount(MOUENS_ORDER2_ID) and st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID) < 100 :
        if st.getRandom(2) == 1 :
          if st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID) == 99 :
            st.giveItems(LETO_LIZARDMAN_FANG1_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(LETO_LIZARDMAN_FANG1_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 578 :
      if int(st.get("cond")) and st.getQuestItemsCount(MOUENS_ORDER2_ID) and st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID) < 100 :
        if st.getRandom(10) < 6 :
          if st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID) == 99 :
            st.giveItems(LETO_LIZARDMAN_FANG1_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(LETO_LIZARDMAN_FANG1_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 579 :
      if int(st.get("cond")) and st.getQuestItemsCount(MOUENS_ORDER2_ID) and st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID) < 100 :
        if st.getRandom(10) < 7 :
          if st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID) == 99 :
            st.giveItems(LETO_LIZARDMAN_FANG1_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(LETO_LIZARDMAN_FANG1_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 580 :
      if int(st.get("cond")) and st.getQuestItemsCount(MOUENS_ORDER2_ID) and st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID) < 100 :
        if st.getRandom(10) < 8 :
          if st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID) == 99 :
            st.giveItems(LETO_LIZARDMAN_FANG1_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(LETO_LIZARDMAN_FANG1_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 581 :
      if int(st.get("cond")) and st.getQuestItemsCount(MOUENS_ORDER2_ID) and st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID) < 100 :
        if st.getRandom(10) < 9 :
          if st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID) == 99 :
            st.giveItems(LETO_LIZARDMAN_FANG1_ID,1)
            st.playSound("Itemsound.quest_middle")
          else:
            st.giveItems(LETO_LIZARDMAN_FANG1_ID,1)
            st.playSound("Itemsound.quest_itemget")
   elif npcId == 582 :
      if int(st.get("cond")) and st.getQuestItemsCount(MOUENS_ORDER2_ID) and st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID) < 100 :
        if st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID) == 99 :
          st.giveItems(LETO_LIZARDMAN_FANG1_ID,1)
          st.playSound("Itemsound.quest_middle")
        else:
          st.giveItems(LETO_LIZARDMAN_FANG1_ID,1)
          st.playSound("Itemsound.quest_itemget")
   return

QUEST       = Quest(223,"223_TestOfChampion","Test Of Champion")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7624)

STARTED.addTalkId(7093)
STARTED.addTalkId(7196)
STARTED.addTalkId(7624)
STARTED.addTalkId(7625)

STARTED.addKillId(145)
STARTED.addKillId(158)
STARTED.addKillId(5088)
STARTED.addKillId(5089)
STARTED.addKillId(551)
STARTED.addKillId(553)
STARTED.addKillId(577)
STARTED.addKillId(578)
STARTED.addKillId(579)
STARTED.addKillId(580)
STARTED.addKillId(581)
STARTED.addKillId(582)
STARTED.addKillId(780)

STARTED.addQuestDrop(7625,MASONS_LETTER_ID,1)
STARTED.addQuestDrop(7093,GROOTS_LETTER_ID,1)
STARTED.addQuestDrop(7196,MOUENS_LETTER_ID,1)
STARTED.addQuestDrop(7624,ASCALONS_LETTER1_ID,1)
STARTED.addQuestDrop(7625,IRON_ROSE_RING_ID,1)
STARTED.addQuestDrop(780,BLOODY_AXE_HEAD_ID,1)
STARTED.addQuestDrop(7093,WHITE_ROSE_INSIGNIA_ID,1)
STARTED.addQuestDrop(145,HARPYS_EGG1_ID,1)
STARTED.addQuestDrop(5088,HARPYS_EGG1_ID,1)
STARTED.addQuestDrop(158,MEDUSA_VENOM1_ID,1)
STARTED.addQuestDrop(553,WINDSUS_BILE1_ID,1)
STARTED.addQuestDrop(7624,ASCALONS_LETTER2_ID,1)
STARTED.addQuestDrop(7624,ASCALONS_LETTER3_ID,1)
STARTED.addQuestDrop(7196,MOUENS_ORDER1_ID,1)
STARTED.addQuestDrop(551,ROAD_RATMAN_HEAD_ID,1)
STARTED.addQuestDrop(5089,ROAD_RATMAN_HEAD_ID,1)
STARTED.addQuestDrop(7196,MOUENS_ORDER2_ID,1)
STARTED.addQuestDrop(577,LETO_LIZARDMAN_FANG1_ID,1)
STARTED.addQuestDrop(578,LETO_LIZARDMAN_FANG1_ID,1)
STARTED.addQuestDrop(579,LETO_LIZARDMAN_FANG1_ID,1)
STARTED.addQuestDrop(580,LETO_LIZARDMAN_FANG1_ID,1)
STARTED.addQuestDrop(581,LETO_LIZARDMAN_FANG1_ID,1)
STARTED.addQuestDrop(582,LETO_LIZARDMAN_FANG1_ID,1)
