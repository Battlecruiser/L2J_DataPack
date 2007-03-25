# Maked by Mr. Have fun! Version 0.2
# rewritten by Rolarga Version 0.3
# version 0.4 - fixed on 2005.11.08

import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "223_TestOfChampion"

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
20780:(2,100,100,BLOODY_AXE_HEAD_ID),
20145:(3,30,50,HARPYS_EGG1_ID),
27088:(3,30,50,HARPYS_EGG1_ID),
20158:(3,30,50,MEDUSA_VENOM1_ID),
20553:(3,30,50,WINDSUS_BILE1_ID),
20551:(4,100,100,ROAD_RATMAN_HEAD_ID),
27089:(4,100,100,ROAD_RATMAN_HEAD_ID),
20577:(5,100,50,LETO_LIZARDMAN_FANG1_ID),  
20578:(5,100,60,LETO_LIZARDMAN_FANG1_ID),   
20579:(5,100,70,LETO_LIZARDMAN_FANG1_ID),   
20580:(5,100,80,LETO_LIZARDMAN_FANG1_ID),   
20581:(5,100,90,LETO_LIZARDMAN_FANG1_ID),   
20582:(5,100,95,LETO_LIZARDMAN_FANG1_ID)
}


class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      htmltext = "30624-06.htm"
      st.set("step","1")
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      st.giveItems(ASCALONS_LETTER1_ID,1)
    elif event == "30624_1" :
          htmltext = "30624-05.htm"
    elif event == "30624_2" :
          htmltext = "30624-10.htm"
          st.giveItems(ASCALONS_LETTER2_ID,1)
          st.takeItems(MASONS_LETTER_ID,1)
    elif event == "30624_3" :
          htmltext = "30624-14.htm"
          st.giveItems(ASCALONS_LETTER3_ID,1)
          st.takeItems(GROOTS_LETTER_ID,1)
    elif event == "30625_1" :
          htmltext = "30625-02.htm"
    elif event == "30625_2" :
          htmltext = "30625-03.htm"
          st.giveItems(IRON_ROSE_RING_ID,1)
          st.takeItems(ASCALONS_LETTER1_ID,1)
          st.set("step","2")
    elif event == "30093_1" :
          htmltext = "30093-02.htm"
          st.giveItems(WHITE_ROSE_INSIGNIA_ID,1)
          st.takeItems(ASCALONS_LETTER2_ID,1)
          st.set("step","3")
    elif event == "30196_1" :
          htmltext = "30196-02.htm"
    elif event == "30196_2" :
          htmltext = "30196-03.htm"
          st.giveItems(MOUENS_ORDER1_ID,1)
          st.takeItems(ASCALONS_LETTER3_ID,1)
          st.set("step","4")
    elif event == "30196_3" :
          htmltext = "30196-06.htm"
          st.giveItems(MOUENS_ORDER2_ID,1)
          st.takeItems(MOUENS_ORDER1_ID,1)
          st.takeItems(ROAD_RATMAN_HEAD_ID,st.getQuestItemsCount(ROAD_RATMAN_HEAD_ID))
          st.set("step","5")
    return htmltext


 def onTalk (self,npc,player):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   id = st.getState()
   if npcId != 30624 and id != STARTED : return htmltext
   if id == CREATED :
     st.set("cond","0")
     st.set("step","0")
   if npcId == 30624 and st.getInt("step") == 0 :
        if st.getPlayer().getClassId().getId() in [0x01, 0x2d] and st.getPlayer().getLevel() > 38 :
          if st.getPlayer().getClassId().getId() == 0x01 :
            htmltext = "30624-03.htm"
          else:
            htmltext = "30624-04.htm"
        elif st.getPlayer().getClassId().getId() in [0x01, 0x2d] :
          htmltext = "30624-02.htm"
        else:
          htmltext = "30624-01.htm"
          st.exitQuest(1)
   elif npcId == 30624 and id == COMPLETED :
      htmltext = "<html><head><body>This quest has already been completed.</body></html>"
   elif npcId == 30624 and int(st.get("step")) == 1 :
      htmltext = "30624-07.htm"
   elif npcId == 30624 and int(st.get("step")) == 2  and st.getQuestItemsCount(MASONS_LETTER_ID) :
      htmltext = "30624-09.htm"
   elif npcId == 30624 and int(st.get("step")) == 2  and st.getQuestItemsCount(ASCALONS_LETTER2_ID) :
      htmltext = "30624-11.htm"
   elif npcId == 30624 and int(st.get("step")) == 2 :
      htmltext = "30624-08.htm"
   elif npcId == 30624 and int(st.get("step")) == 3 and st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) :
      htmltext = "30624-12.htm"
   elif npcId == 30624 and int(st.get("step")) == 3 and st.getQuestItemsCount(GROOTS_LETTER_ID) :
      htmltext = "30624-13.htm"
   elif npcId == 30624 and int(st.get("step")) == 3 and st.getQuestItemsCount(ASCALONS_LETTER3_ID) :
      htmltext = "30624-15.htm"
   elif npcId == 30624 and int(st.get("step")) == 5 and st.getQuestItemsCount(MOUENS_LETTER_ID) :
      st.addExpAndSp(117454,25000)
      htmltext = "30624-17.htm"
      st.giveItems(MARK_OF_CHAMPION_ID,1)
      st.takeItems(MOUENS_LETTER_ID,1)
      st.unset("step")
      st.set("cond","0")
      st.setState(COMPLETED)
      st.playSound("ItemSound.quest_finish")
   elif npcId == 30624 and int(st.get("step")) in [4,5] :
      htmltext = "30624-16.htm"
   elif npcId == 30625 and int(st.get("step")) == 1 and st.getQuestItemsCount(ASCALONS_LETTER1_ID) :
      htmltext = "30625-01.htm"
   elif npcId == 30625 and int(st.get("step")) == 2 and st.getQuestItemsCount(BLOODY_AXE_HEAD_ID) < 100 :
      htmltext = "30625-04.htm"
   elif npcId == 30625 and int(st.get("step")) == 2 and st.getQuestItemsCount(BLOODY_AXE_HEAD_ID) > 99 :
      htmltext = "30625-05.htm"
      st.giveItems(MASONS_LETTER_ID,1)
      st.takeItems(IRON_ROSE_RING_ID,1)
      st.takeItems(BLOODY_AXE_HEAD_ID,st.getQuestItemsCount(BLOODY_AXE_HEAD_ID))
   elif npcId == 30625 and int(st.get("step")) == 2 and st.getQuestItemsCount(MASONS_LETTER_ID) :
      htmltext = "30625-06.htm"
   elif npcId == 30625 and int(st.get("step")) == 2 and (st.getQuestItemsCount(ASCALONS_LETTER2_ID) or st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) or st.getQuestItemsCount(ASCALONS_LETTER2_ID) or st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) or st.getQuestItemsCount(GROOTS_LETTER_ID) or st.getQuestItemsCount(ASCALONS_LETTER3_ID) or st.getQuestItemsCount(MOUENS_ORDER1_ID) or st.getQuestItemsCount(MOUENS_ORDER2_ID) or st.getQuestItemsCount(MOUENS_LETTER_ID) or st.getQuestItemsCount(GROOTS_LETTER_ID)) :
      htmltext = "30625-07.htm"
   elif npcId == 30093 and int(st.get("step")) == 2 and st.getQuestItemsCount(ASCALONS_LETTER2_ID) :
      htmltext = "30093-01.htm"
   elif npcId == 30093 and int(st.get("step")) == 3 and st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) and (st.getQuestItemsCount(HARPYS_EGG1_ID) < 30 or st.getQuestItemsCount(MEDUSA_VENOM1_ID) < 30 or st.getQuestItemsCount(WINDSUS_BILE1_ID)<30) :
      htmltext = "30093-03.htm"
   elif npcId == 30093 and int(st.get("step")) == 3 and st.getQuestItemsCount(WHITE_ROSE_INSIGNIA_ID) and st.getQuestItemsCount(HARPYS_EGG1_ID) >= 30 and st.getQuestItemsCount(MEDUSA_VENOM1_ID) >= 30 and st.getQuestItemsCount(WINDSUS_BILE1_ID) >= 30 :
      htmltext = "30093-04.htm"
      st.giveItems(GROOTS_LETTER_ID,1)
      st.takeItems(WHITE_ROSE_INSIGNIA_ID,1)
      st.takeItems(HARPYS_EGG1_ID,st.getQuestItemsCount(HARPYS_EGG1_ID))
      st.takeItems(MEDUSA_VENOM1_ID,st.getQuestItemsCount(MEDUSA_VENOM1_ID))
      st.takeItems(WINDSUS_BILE1_ID,st.getQuestItemsCount(WINDSUS_BILE1_ID))
   elif npcId == 30093 and int(st.get("step")) == 3 and st.getQuestItemsCount(GROOTS_LETTER_ID) :
      htmltext = "30093-05.htm"
   elif npcId == 30093 and int(st.get("step")) == 3 and (st.getQuestItemsCount(ASCALONS_LETTER3_ID) or st.getQuestItemsCount(MOUENS_ORDER1_ID) or st.getQuestItemsCount(MOUENS_ORDER2_ID) or st.getQuestItemsCount(MOUENS_LETTER_ID)) :
      htmltext = "30093-06.htm"
   elif npcId == 30196 and int(st.get("step")) == 3 and st.getQuestItemsCount(ASCALONS_LETTER3_ID) :
      htmltext = "30196-01.htm"
   elif npcId == 30196 and int(st.get("step")) == 4 and st.getQuestItemsCount(MOUENS_ORDER1_ID) and st.getQuestItemsCount(ROAD_RATMAN_HEAD_ID) < 100 :
      htmltext = "30196-04.htm"
   elif npcId == 30196 and int(st.get("step")) == 4 and st.getQuestItemsCount(MOUENS_ORDER1_ID) and st.getQuestItemsCount(ROAD_RATMAN_HEAD_ID) >= 100 :
      htmltext = "30196-05.htm"
   elif npcId == 30196 and int(st.get("step")) == 5 and st.getQuestItemsCount(MOUENS_ORDER2_ID) and st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID) < 100 :
      htmltext = "30196-07.htm"
   elif npcId == 30196 and int(st.get("step")) == 5 and st.getQuestItemsCount(MOUENS_ORDER2_ID) and st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID) >= 100 :
      htmltext = "30196-08.htm"
      st.giveItems(MOUENS_LETTER_ID,1)
      st.takeItems(MOUENS_ORDER2_ID,1)
      st.takeItems(LETO_LIZARDMAN_FANG1_ID,st.getQuestItemsCount(LETO_LIZARDMAN_FANG1_ID))
   elif npcId == 30196 and int(st.get("step")) == 5 and st.getQuestItemsCount(MOUENS_LETTER_ID) :
      htmltext = "30196-09.htm"
   return htmltext


 def onKill (self,npc,player):
   st = player.getQuestState(qn)
   if not st : return 
   if st.getState() != STARTED : return 
   
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

QUEST     = Quest(223,qn,"Test Of Champion")
CREATED   = State('Start',     QUEST)
STARTING  = State('Starting',  QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30624)

QUEST.addTalkId(30624)

for npcId in [30093,30196,30625]:
    QUEST.addTalkId(npcId)

for mobId in [20145,20158,27088,27089,20551,20553,20577,20578,20579,20580,20581,20582,20780]:
    QUEST.addKillId(mobId)

for item in range(3277,3293):
    STARTED.addQuestDrop(30093,item,1)

print "importing quests: 223: Test Of Champion"
