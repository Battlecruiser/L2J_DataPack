# Maked by Mr. Have fun! Version 0.2
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "228_TestOfMagus"

MARK_OF_MAGUS_ID = 2840
RUKALS_LETTER_ID = 2841
PARINAS_LETTER_ID = 2842
LILAC_CHARM_ID = 2843
GOLDEN_SEED1_ID = 2844
GOLDEN_SEED2_ID = 2845
GOLDEN_SEED3_ID = 2846
SCORE_OF_ELEMENTS_ID = 2847
TONE_OF_WATER_ID = 2856
TONE_OF_FIRE_ID = 2857
TONE_OF_WIND_ID = 2858
TONE_OF_EARTH_ID = 2859
UNDINE_CHARM_ID = 2862
DAZZLING_DROP_ID = 2848
SALAMANDER_CHARM_ID = 2860
FLAME_CRYSTAL_ID = 2849
SYLPH_CHARM_ID = 2861
HARPYS_FEATHER_ID = 2850
WYRMS_WINGBONE_ID = 2851
WINDSUS_MANE_ID = 2852
SERPENT_CHARM_ID = 2863
EN_MONSTEREYE_SHELL_ID = 2853
EN_STONEGOLEM_POWDER_ID = 2854
EN_IRONGOLEM_SCRAP_ID = 2855

#This handels all drops from mobs.   npcId:[condition,maxcount,chance,item,part]
DROPLIST={
27095:[3,1,100,GOLDEN_SEED1_ID,1],
27096:[3,1,100,GOLDEN_SEED2_ID,1],
27097:[3,1,100,GOLDEN_SEED3_ID,1],
27098:[7,5,50,FLAME_CRYSTAL_ID,2],
20230:[7,20,30,DAZZLING_DROP_ID,2],
20231:[7,20,30,DAZZLING_DROP_ID,2],
20157:[7,20,30,DAZZLING_DROP_ID,2],
20232:[7,20,40,DAZZLING_DROP_ID,2],
20234:[7,20,50,DAZZLING_DROP_ID,2],
20145:[7,20,50,HARPYS_FEATHER_ID,2],
20176:[7,10,50,WYRMS_WINGBONE_ID,2],
20553:[7,10,50,WINDSUS_MANE_ID,2],
20564:[7,10,100,EN_MONSTEREYE_SHELL_ID,2],
20565:[7,10,100,EN_STONEGOLEM_POWDER_ID,2],
20566:[7,10,100,EN_IRONGOLEM_SCRAP_ID,2]
}


class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        htmltext = "30629-04.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.giveItems(RUKALS_LETTER_ID,1)
    elif event == "30629_1" :
          htmltext = "30629-09.htm"
    elif event == "30629_2" :
          htmltext = "30629-10.htm"
          st.takeItems(LILAC_CHARM_ID,1)
          st.takeItems(GOLDEN_SEED1_ID,1)
          st.takeItems(GOLDEN_SEED2_ID,1)
          st.takeItems(GOLDEN_SEED3_ID,1)
          st.giveItems(SCORE_OF_ELEMENTS_ID,1)
          st.playSound("ItemSound.quest_middle")
          st.set("cond","6")
    elif event == "30391_1" :
          htmltext = "30391-02.htm"
          st.giveItems(PARINAS_LETTER_ID,1)
          st.takeItems(RUKALS_LETTER_ID,1)
          st.playSound("ItemSound.quest_middle")
          st.set("cond","2")
    elif event == "30612_1" :
          htmltext = "30612-02.htm"
          st.giveItems(LILAC_CHARM_ID,1)
          st.takeItems(PARINAS_LETTER_ID,1)
          st.playSound("ItemSound.quest_middle")
          st.set("cond","3")
    elif event == "30412_1" :
          htmltext = "30412-02.htm"
          st.giveItems(SYLPH_CHARM_ID,1)
          st.playSound("ItemSound.quest_middle")
          st.set("cond","7")
    elif event == "30409_1" :
          htmltext = "30409-02.htm"
    elif event == "30409_2" :
          htmltext = "30409-03.htm"
          st.giveItems(SERPENT_CHARM_ID,1)
          st.playSound("ItemSound.quest_middle")
          st.set("cond","7")
    return htmltext


 def onTalk (self,npc,player):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   id = st.getState()
   if npcId != 30629 and id != STARTED : return htmltext
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 30629 :
     if int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if int(st.get("cond")) < 15 :
          if st.getPlayer().getClassId().getId() == 0x0b or st.getPlayer().getClassId().getId() == 0x1a or st.getPlayer().getClassId().getId() == 0x27 :
            if st.getPlayer().getLevel() < 39 :
              htmltext = "30629-02.htm"
            else:
              htmltext = "30629-03.htm"
          else:
            htmltext = "30629-01.htm"
            st.exitQuest(1)
        else:
          htmltext = "30629-01.htm"
          st.exitQuest(1)
     elif int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest has already been completed.</body></html>"
     elif int(st.get("cond"))==1:
        htmltext = "30629-05.htm"
     elif int(st.get("cond"))==2:
        htmltext = "30629-06.htm"
     elif int(st.get("cond"))==3:
        htmltext = "30629-07.htm"
     elif int(st.get("cond"))==5:
        htmltext = "30629-08.htm"
     elif int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 :
        if st.getQuestItemsCount(TONE_OF_WATER_ID) and st.getQuestItemsCount(TONE_OF_FIRE_ID) and st.getQuestItemsCount(TONE_OF_WIND_ID) and st.getQuestItemsCount(TONE_OF_EARTH_ID) :
            st.takeItems(SCORE_OF_ELEMENTS_ID,1)
            st.takeItems(TONE_OF_WATER_ID,1)
            st.takeItems(TONE_OF_FIRE_ID,1)
            st.takeItems(TONE_OF_WIND_ID,1)
            st.takeItems(TONE_OF_EARTH_ID,1)
            st.giveItems(MARK_OF_MAGUS_ID,1)
            st.addExpAndSp(139039,40000)
            htmltext = "30629-12.htm"
            st.set("cond","0")
            st.set("onlyone","1")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
        else:
          htmltext = "30629-11.htm"
   elif npcId == 30391:
     if int(st.get("cond"))==1:
        htmltext = "30391-01.htm"
     elif int(st.get("cond"))==2:
        htmltext = "30391-03.htm"
     elif int(st.get("cond"))<6 and int(st.get("cond"))>2:
        htmltext = "30391-04.htm"
     elif int(st.get("cond"))>5 :
        htmltext = "30391-05.htm"
   elif npcId == 30612:
     if int(st.get("cond"))==2 :
        htmltext = "30612-01.htm"
     elif int(st.get("cond"))<5 and int(st.get("cond"))>2:
        htmltext = "30612-03.htm"
     elif int(st.get("cond"))==5:
        htmltext = "30612-04.htm"
     elif int(st.get("cond"))>5:
        htmltext = "30612-05.htm"
   elif npcId == 30413:
     if int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(TONE_OF_WATER_ID)==0 and st.getQuestItemsCount(UNDINE_CHARM_ID)==0 :
        htmltext = "30413-01.htm"
        st.giveItems(UNDINE_CHARM_ID,1)
        st.set("cond","7")
     elif int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(UNDINE_CHARM_ID)==1 :
        if st.getQuestItemsCount(DAZZLING_DROP_ID) < 20 :
          htmltext = "30413-02.htm"
        else:
          htmltext = "30413-03.htm"
          st.takeItems(DAZZLING_DROP_ID,st.getQuestItemsCount(DAZZLING_DROP_ID))
          st.takeItems(UNDINE_CHARM_ID,1)
          st.giveItems(TONE_OF_WATER_ID,1)
          st.playSound("ItemSound.quest_middle")
     elif int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(TONE_OF_WATER_ID)==1 and st.getQuestItemsCount(UNDINE_CHARM_ID)==0 :
        htmltext = "30413-04.htm"
   elif npcId == 30411 :
     if int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(TONE_OF_FIRE_ID)==0 and st.getQuestItemsCount(SALAMANDER_CHARM_ID)==0 :
        htmltext = "30411-01.htm"
        st.giveItems(SALAMANDER_CHARM_ID,1)
        st.playSound("ItemSound.quest_middle")
        st.set("cond","7")
     elif int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(SALAMANDER_CHARM_ID)==1 :
        if st.getQuestItemsCount(FLAME_CRYSTAL_ID) < 5 :
          htmltext = "30411-02.htm"
        else:
          htmltext = "30411-03.htm"
          st.takeItems(FLAME_CRYSTAL_ID,st.getQuestItemsCount(FLAME_CRYSTAL_ID))
          st.giveItems(TONE_OF_FIRE_ID,1)
          st.takeItems(SALAMANDER_CHARM_ID,1)
          st.playSound("ItemSound.quest_middle")
     elif int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(TONE_OF_FIRE_ID)==1 and st.getQuestItemsCount(SALAMANDER_CHARM_ID)==0 :
        htmltext = "30411-04.htm"
   elif npcId == 30412 :
     if int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(TONE_OF_WIND_ID)==0 and st.getQuestItemsCount(SYLPH_CHARM_ID)==0 :
        htmltext = "30412-01.htm"
     elif int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(SYLPH_CHARM_ID)==1 :
        if st.getQuestItemsCount(HARPYS_FEATHER_ID)+st.getQuestItemsCount(WYRMS_WINGBONE_ID)+st.getQuestItemsCount(WINDSUS_MANE_ID) < 40 :
          htmltext = "30412-03.htm"
        else:
          htmltext = "30412-04.htm"
          st.takeItems(HARPYS_FEATHER_ID,st.getQuestItemsCount(HARPYS_FEATHER_ID))
          st.takeItems(WYRMS_WINGBONE_ID,st.getQuestItemsCount(WYRMS_WINGBONE_ID))
          st.takeItems(WINDSUS_MANE_ID,st.getQuestItemsCount(WINDSUS_MANE_ID))
          st.giveItems(TONE_OF_WIND_ID,1)
          st.takeItems(SYLPH_CHARM_ID,1)
          st.playSound("ItemSound.quest_middle")
     elif int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(TONE_OF_WIND_ID)==1 and st.getQuestItemsCount(SYLPH_CHARM_ID)==0 :
        htmltext = "30412-05.htm"
   elif npcId == 30409 :
     if int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(TONE_OF_EARTH_ID)==0 and st.getQuestItemsCount(SERPENT_CHARM_ID)==0 :
        htmltext = "30409-01.htm"
     elif int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(SERPENT_CHARM_ID)==1 :
        if st.getQuestItemsCount(EN_MONSTEREYE_SHELL_ID)+st.getQuestItemsCount(EN_STONEGOLEM_POWDER_ID)+st.getQuestItemsCount(EN_IRONGOLEM_SCRAP_ID) < 30 :
          htmltext = "30409-04.htm"
        else:
          htmltext = "30409-05.htm"
          st.takeItems(EN_MONSTEREYE_SHELL_ID,st.getQuestItemsCount(EN_MONSTEREYE_SHELL_ID))
          st.takeItems(EN_STONEGOLEM_POWDER_ID,st.getQuestItemsCount(EN_STONEGOLEM_POWDER_ID))
          st.takeItems(EN_IRONGOLEM_SCRAP_ID,st.getQuestItemsCount(EN_IRONGOLEM_SCRAP_ID))
          st.giveItems(TONE_OF_EARTH_ID,1)
          st.takeItems(SERPENT_CHARM_ID,1)
          st.playSound("ItemSound.quest_middle")
     elif int(st.get("cond")) and st.getQuestItemsCount(SCORE_OF_ELEMENTS_ID)==1 and st.getQuestItemsCount(TONE_OF_EARTH_ID)==1 and st.getQuestItemsCount(SERPENT_CHARM_ID)==0 :
        htmltext = "30409-06.htm"
   return htmltext
                             
 def onKill (self,npc,player):
   st = player.getQuestState(qn)
   if not st : return 
   if st.getState() != STARTED : return 
   
   npcId = npc.getNpcId()
   condition,maxcount,chance,item,part = DROPLIST[npcId]
   random = st.getRandom(100)
   itemcount = st.getQuestItemsCount(item)
   if int(st.get("cond")) == condition and itemcount < maxcount and random < chance :
    if itemcount == maxcount-1:
     st.giveItems(item,1)
     st.playSound("ItemSound.quest_middle")
     if part==1:
       count=0
       for items in [GOLDEN_SEED1_ID,GOLDEN_SEED2_ID,GOLDEN_SEED3_ID]:
        count+=st.getQuestItemsCount(items)
       if count>2:
        st.set("cond","5")
    else:
     st.giveItems(item,1)
     st.playSound("ItemSound.quest_itemget")
   return


QUEST       = Quest(228,qn,"Test Of Magus")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30629)

QUEST.addTalkId(30629)

for npcId in [30391,30409,30411,30412,30413,30612]:
   QUEST.addTalkId(npcId)
  
for mobId in [20145,20157,20176,20230,20231,20232,20234,27095,27096,27097,27098,20553,20564,20565,20566]:
   QUEST.addKillId(mobId)

for item in range(2841,2864):
   STARTED.addQuestDrop(30629,item,1)
  
print "importing quests: 228: Test Of Magus"