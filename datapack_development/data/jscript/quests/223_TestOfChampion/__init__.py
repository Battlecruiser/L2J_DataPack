# Maked by Mr. Have fun! Version 0.2
# rewritten by Rolarga Version 0.3
# version 0.4 - fixed on 2005.11.08

import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_CHAMPION_ID     = 3276
ASCALONS_LETTER1_ID     = 3277
MASONS_LETTER_ID        = 3278
IRON_ROSE_RING_ID       = 3279
ASCALONS_LETTER2_ID     = 3280
WHITE_ROSE_INSIGNIA_ID  = 3281
GROOTS_LETTER_ID        = 3282
ASCALONS_LETTER3_ID     = 3283
MOUENS_ORDER1_ID        = 3284
MOUENS_ORDER2_ID        = 3285
MOUENS_LETTER_ID        = 3286
HARPYS_EGG1_ID          = 3287
MEDUSA_VENOM1_ID        = 3288
WINDSUS_BILE1_ID        = 3289
BLOODY_AXE_HEAD_ID      = 3290
ROAD_RATMAN_HEAD_ID     = 3291
LETO_LIZARDMAN_FANG1_ID = 3292

DROPLIST ={
780:(2,100,100,BLOODY_AXE_HEAD_ID),
145:(3,30,50,HARPYS_EGG1_ID),
5088:(3,30,50,HARPYS_EGG1_ID),
158:(3,30,50,MEDUSA_VENOM1_ID),
553:(3,30,50,WINDSUS_BILE1_ID),
551:(4,100,100,ROAD_RATMAN_HEAD_ID),
5089:(4,100,100,ROAD_RATMAN_HEAD_ID),
577:(5,100,50,LETO_LIZARDMAN_FANG1_ID),  
578:(5,100,60,LETO_LIZARDMAN_FANG1_ID),   
579:(5,100,70,LETO_LIZARDMAN_FANG1_ID),   
580:(5,100,80,LETO_LIZARDMAN_FANG1_ID),   
581:(5,100,90,LETO_LIZARDMAN_FANG1_ID),   
582:(5,100,95,LETO_LIZARDMAN_FANG1_ID)
}


class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      htmltext = "7624-06.htm"
      st.set("step","1")
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      st.giveItems(ASCALONS_LETTER1_ID,1)
    elif event == "7624_1" :
          htmltext = "7624-05.htm"
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
          st.set("step","2")
    elif event == "7093_1" :
          htmltext = "7093-02.htm"
          st.giveItems(WHITE_ROSE_INSIGNIA_ID,1)
          st.takeItems(ASCALONS_LETTER2_ID,1)
          st.set("step","3")
    elif event == "7196_1" :
          htmltext = "7196-02.htm"
    elif event == "7196_2" :
          htmltext = "7196-03.htm"
          st.giveItems(MOUENS_ORDER1_ID,1)
          st.takeItems(ASCALONS_LETTER3_ID,1)
          st.set("step","4")
    elif event == "7196_3" :
          htmltext = "7196-06.htm"
          st.giveItems(MOUENS_ORDER2_ID,1)
          st.takeItems(MOUENS_ORDER1_ID,1)
          st.takeItems(ROAD_RATMAN_HEAD_ID,st.getQuestItemsCount(ROAD_RATMAN_HEAD_ID))
          st.set("step","5")
    return htmltext


 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
     st.set("step","0")
   if npcId == 7624 and st.getInt("step") == 0 :
        if st.getPlayer().getClassId().getId() in [0x01, 0x2d] and st.getPlayer().getLevel() > 38 :
          if st.getPlayer().getClassId().getId() == 0x01 :
            htmltext = "7624-03.htm"
          else:
            htmltext = "7624-04.htm"
        elif st.getPlayer().getClassId().getId() in [0x01, 0x2d] :
          htmltext = "7624-02.htm"
        else:
          htmltext = "7624-01.htm"
          st.exitQuest(1)
   elif npcId == 7624 and id == COMPLETED :
      htmltext = "<html><head><body>This quest has already been completed.</body></html>"
   elif npcId == 7624 and int(st.get("step")) == 1 :
      htmltext = "7624-07.htm"
   elif npcId == 7624 and int(st.get("step")) == 2  and st.getQuestItemsCount(MASONS_LETTER_ID) :
      htmltext = "7624-09.htm"
   elif npcId == 7624 and int(st.get("step")) == 2  and st.getQuestItemsCount(ASCALONS_LETTER2_ID) :
      htmltext = "7624-11.htm"
   elif npcId == 7624 and int(st.get("step")) == 2 :
      htmltext = "7624-08.htm"
   elif npcId == 7624 and int(st.get("step")) == 3 and st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) :
      htmltext = "7624-12.htm"
   elif npcId == 7624 and int(st.get("step")) == 3 and st.getQuestItemsCount(GROOTS_LETTER_ID) :
      htmltext = "7624-13.htm"
   elif npcId == 7624 and int(st.get("step")) == 3 and st.getQuestItemsCount(ASCALONS_LETTER3_ID) :
      htmltext = "7624-15.htm"
   elif npcId == 7624 and int(st.get("step")) == 5 and st.getQuestItemsCount(MOUENS_LETTER_ID) :
      st.addExpAndSp(117454,25000)
      htmltext = "7624-17.htm"
      st.giveItems(MARK_OF_CHAMPION_ID,1)
      st.takeItems(MOUENS_LETTER_ID,1)
      st.unset("step")
      st.set("cond","0")
      st.setState(COMPLETED)
      st.playSound("ItemSound.quest_finish")
   elif npcId == 7624 and int(st.get("step")) in [4,5] :
      htmltext = "7624-16.htm"
   elif npcId == 7625 and int(st.get("step")) == 1 and st.getQuestItemsCount(ASCALONS_LETTER1_ID) :
      htmltext = "7625-01.htm"
   elif npcId == 7625 and int(st.get("step")) == 2 and st.getQuestItemsCount(BLOODY_AXE_HEAD_ID) < 100 :
      htmltext = "7625-04.htm"
   elif npcId == 7625 and int(st.get("step")) == 2 and st.getQuestItemsCount(BLOODY_AXE_HEAD_ID) > 99 :
      htmltext = "7625-05.htm"
      st.giveItems(MASONS_LETTER_ID,1)
      st.takeItems(IRON_ROSE_RING_ID,1)
      st.takeItems(BLOODY_AXE_HEAD_ID,st.getQuestItemsCount(BLOODY_AXE_HEAD_ID))
   elif npcId == 7625 and int(st.get("step")) == 2 and st.getQuestItemsCount(MASONS_LETTER_ID) :
      htmltext = "7625-06.htm"
   elif npcId == 7625 and int(st.get("step")) == 2 and (st.getQuestItemsCount(ASCALONS_LETTER2_ID) or st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) or st.getQuestItemsCount(ASCALONS_LETTER2_ID) or st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) or st.getQuestItemsCount(GROOTS_LETTER_ID) or st.getQuestItemsCount(ASCALONS_LETTER3_ID) or st.getQuestItemsCount(MOUENS_ORDER1_ID) or st.getQuestItemsCount(MOUENS_ORDER2_ID) or st.getQuestItemsCount(MOUENS_LETTER_ID) or st.getQuestItemsCount(GROOTS_LETTER_ID)) :
      htmltext = "7625-07.htm"
   elif npcId == 7093 and int(st.get("step")) == 2 and st.getQuestItemsCount(ASCALONS_LETTER2_ID) :
      htmltext = "7093-01.htm"
   elif npcId == 7093 and int(st.get("step")) == 3 and st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) and (st.getQuestItemsCount(HARPYS_EGG1_ID) < 30 or st.getQuestItemsCount(MEDUSA_VENOM1_ID) < 30 or st.getQuestItemsCount(WINDSUS_BILE1_ID)<30) :
      htmltext = "7093-03.htm"
   elif npcId == 7093 and int(st.get("step")) == 3 and st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) and st.getQuestItemsCount(HARPYS_EGG1_ID) >= 30 and st.getQuestItemsCount(MEDUSA_VENOM1_ID) >= 30 and st.getQuestItemsCount(WINDSUS_BILE1_ID) >= 30 :
      htmltext = "7093-04.htm"
      st.giveItems(GROOTS_LETTER_ID,1)
      st.takeItems(WHITE_ROSE_INSIGNIA_ID,1)
      st.takeItems(HARPYS_EGG1_ID,st.getQuestItemsCount(HARPYS_EGG1_ID))
      st.takeItems(MEDUSA_VENOM1_ID,st.getQuestItemsCount(MEDUSA_VENOM1_ID))
      st.takeItems(WINDSUS_BILE1_ID,st.getQuestItemsCount(WINDSUS_BILE1_ID))
   elif npcId == 7093 and int(st.get("step")) == 3 and st.getQuestItemsCount(GROOTS_LETTER_ID) :
      htmltext = "7093-05.htm"
   elif npcId == 7093 and int(st.get("step")) == 3 and (st.getQuestItemsCount(ASCALONS_LETTER3_ID) or st.getQuestItemsCount(MOUENS_ORDER1_ID) or st.getQuestItemsCount(MOUENS_ORDER2_ID) or st.getQuestItemsCount(MOUENS_LETTER_ID)) :
      htmltext = "7093-06.htm"
   elif npcId == 7196 and int(st.get("step")) == 3 and st.getQuestItemsCount(ASCALONS_LETTER3_ID) :
      htmltext = "7196-01.htm"
   elif npcId == 7196 and int(st.get("step")) == 4 and st.getQuestItemsCount(MOUENS_ORDER1_ID) and st.getQuestItemsCount(ROAD_RATMAN_HEAD_ID) < 100 :
      htmltext = "7196-04.htm"
   elif npcId == 7196 and int(st.get("step")) == 4 and st.getQuestItemsCount(MOUENS_ORDER1_ID) and st.getQuestItemsCount(ROAD_RATMAN_HEAD_ID) >= 100 :
      htmltext = "7196-05.htm"
   elif npcId == 7196 and int(st.get("step")) == 5 and st.getQuestItemsCount(MOUENS_ORDER2_ID) and st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID) < 100 :
      htmltext = "7196-07.htm"
   elif npcId == 7196 and int(st.get("step")) == 5 and st.getQuestItemsCount(MOUENS_ORDER2_ID) and st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID) >= 100 :
      htmltext = "7196-08.htm"
      st.giveItems(MOUENS_LETTER_ID,1)
      st.takeItems(MOUENS_ORDER2_ID,1)
      st.takeItems(LETO_LIZARDMAN_FANG1_ID,st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID))
   elif npcId == 7196 and int(st.get("step")) == 5 and st.getQuestItemsCount(MOUENS_LETTER_ID) :
      htmltext = "7196-09.htm"
   return htmltext


 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   step, maxcount, chance, itemid = DROPLIST[npcId]
   if int(st.get("step")) == step and st.getQuestItemsCount(itemid) < maxcount and st.getRandom(100) < chance:
     if st.getQuestItemsCount(itemid) == (maxcount-1):
       st.giveItems(itemid,1)
       st.playSound("Itemsound.quest_middle")
     else:
       st.giveItems(itemid,1)
       st.playSound("Itemsound.quest_itemget")
   return

QUEST     = Quest(223,"223_TestOfChampion","Test Of Champion")
CREATED   = State('Start',     QUEST)
STARTING  = State('Starting',  QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7624)

CREATED.addTalkId(7624)
STARTING.addTalkId(7624)
COMPLETED.addTalkId(7624)

for npcId in [7093,7196,7624,7625]:
    STARTED.addTalkId(npcId)

for mobId in [145,158,5088,5089,551,553,577,578,579,580,581,582,780]:
    STARTED.addKillId(mobId)

print "importing quests: 223: Test Of Champion"
