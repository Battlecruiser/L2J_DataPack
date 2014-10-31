# Made by Mr. Have fun! Version 0.2
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

RECOMMENDATION_01 = 1067
RECOMMENDATION_02 = 1068
LEAF_OF_MOTHERTREE = 1069
BLOOD_OF_JUNDIN = 1070
LICENSE_OF_MINER = 1498
VOUCHER_OF_FLAME = 1496
SOULSHOT_NOVICE = 5789
SPIRITSHOT_NOVICE = 5790
BLUE_GEM=6353

# event:[htmlfile,radarX,radarY,radarZ,item,classId1,gift1,count1,classId2,gift2,count2]
EVENTS={
"7008_02":["7008-03.htm",-84058,243239,-3730,RECOMMENDATION_01,0x00,SOULSHOT_NOVICE,200,0,0,0],
"7017_02":["7017-03.htm",-84058,243239,-3730,RECOMMENDATION_02,0x0a,SPIRITSHOT_NOVICE,100,0,0,0],
"7370_02":["7370-03.htm",45491,48359,-3086,LEAF_OF_MOTHERTREE,0x19,SPIRITSHOT_NOVICE,100,0x12,SOULSHOT_NOVICE,200],
"7129_02":["7129-03.htm",12116,16666,-4610,BLOOD_OF_JUNDIN,0x26,SPIRITSHOT_NOVICE,100,0x1f,SOULSHOT_NOVICE,200],
"7528_02":["7528-03.htm",115642,-178046,-941,LICENSE_OF_MINER,0x35,SOULSHOT_NOVICE,200,0,0,0],
"7573_02":["7573-03.htm",-45067,-113549,-235,VOUCHER_OF_FLAME,0x31,SPIRITSHOT_NOVICE,100,0x2c,SOULSHOT_NOVICE,200]
}

# npcId:[raceId,[htmlfiles],npcTyp,item]
TALKS={
7017:[0,["7017-01.htm","7017-02.htm","7017-04.htm"],0,0],
7008:[0,["7008-01.htm","7008-02.htm","7008-04.htm"],0,0],
7370:[1,["7370-01.htm","7370-02.htm","7370-04.htm"],0,0],
7129:[2,["7129-01.htm","7129-02.htm","7129-04.htm"],0,0],
7573:[3,["7573-01.htm","7573-02.htm","7573-04.htm"],0,0],
7528:[4,["7528-01.htm","7528-02.htm","7528-04.htm"],0,0],
7018:[0,["7131-01.htm",0,"7019-03a.htm","7019-04.htm",],1,RECOMMENDATION_02],
7019:[0,["7131-01.htm",0,"7019-03a.htm","7019-04.htm",],1,RECOMMENDATION_02],
7020:[0,["7131-01.htm",0,"7019-03a.htm","7019-04.htm",],1,RECOMMENDATION_02],
7021:[0,["7131-01.htm",0,"7019-03a.htm","7019-04.htm",],1,RECOMMENDATION_02],
7009:[0,["7530-01.htm","7009-03.htm",0,"7009-04.htm",],1,RECOMMENDATION_01],
7011:[0,["7530-01.htm","7009-03.htm",0,"7009-04.htm",],1,RECOMMENDATION_01],
7012:[0,["7530-01.htm","7009-03.htm",0,"7009-04.htm",],1,RECOMMENDATION_01],
7056:[0,["7530-01.htm","7009-03.htm",0,"7009-04.htm",],1,RECOMMENDATION_01],
7400:[1,["7131-01.htm","7400-03.htm","7400-03a.htm","7400-04.htm",],1,LEAF_OF_MOTHERTREE],
7401:[1,["7131-01.htm","7400-03.htm","7400-03a.htm","7400-04.htm",],1,LEAF_OF_MOTHERTREE],
7402:[1,["7131-01.htm","7400-03.htm","7400-03a.htm","7400-04.htm",],1,LEAF_OF_MOTHERTREE],
7403:[1,["7131-01.htm","7400-03.htm","7400-03a.htm","7400-04.htm",],1,LEAF_OF_MOTHERTREE],
7131:[2,["7131-01.htm","7131-03.htm","7131-03a.htm","7131-04.htm",],1,BLOOD_OF_JUNDIN],
7404:[2,["7131-01.htm","7131-03.htm","7131-03a.htm","7131-04.htm",],1,BLOOD_OF_JUNDIN],
7574:[3,["7575-01.htm","7575-03.htm","7575-03a.htm","7575-04.htm",],1,VOUCHER_OF_FLAME],
7575:[3,["7575-01.htm","7575-03.htm","7575-03a.htm","7575-04.htm",],1,VOUCHER_OF_FLAME],
7530:[4,["7530-01.htm","7530-03.htm",0,"7530-04.htm",],1,LICENSE_OF_MINER]
}    

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    htmlfile,radarX,radarY,radarZ,item,classId1,gift1,count1,classId2,gift2,count2 = EVENTS[event]
    st.addRadar(radarX,radarY,radarZ);
    htmltext=htmlfile
    if st.getQuestItemsCount(item) and int(st.get("onlyone")) == 0:
         if st.getPlayer().getClassId().getId() == classId1 :
          st.addExpAndSp(0,50)
          st.takeItems(item,1)
          st.giveItems(gift1,count1)
          st.set("cond","0")
          st.set("onlyone","1")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
         elif st.getPlayer().getClassId().getId() == classId2 :
          st.addExpAndSp(0,50)
          st.takeItems(item,1)
          if gift2:
           st.giveItems(gift2,count2)
          st.set("cond","0")
          st.set("onlyone","1")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
    return htmltext

 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have no tasks for you right now.</body></html>"
   raceId,htmlfiles,npcTyp,item = TALKS[npcId]
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if (st.getPlayer().getLevel() >= 10 or int(st.get("onlyone"))) and npcTyp == 1:
       htmltext = "7575-05.htm"
   elif int(st.get("onlyone")) == 0 and st.getPlayer().getLevel() < 10 :
    if st.getPlayer().getRace().ordinal() == raceId :
      htmltext=htmlfiles[0]
      if npcTyp==1:
       if int(st.get("cond"))==0 :
        if st.getPlayer().getClassId().isMage() :
         st.set("cond","1")
         st.setState(STARTED)
         st.playSound("ItemSound.quest_tutorial")
        else:
         htmltext="7530-01.htm"
         st.set("cond","1")
         st.setState(STARTED)
         st.playSound("ItemSound.quest_tutorial")
       elif int(st.get("cond"))==1 and st.getQuestItemsCount(item)==0 :
         if st.getQuestItemsCount(BLUE_GEM) :
           st.takeItems(BLUE_GEM,st.getQuestItemsCount(BLUE_GEM))
           st.giveItems(item,1)
           st.set("cond","2")
           st.playSound("ItemSound.quest_middle")
           if st.getPlayer().getClassId().isMage() :
             st.giveItems(SPIRITSHOT_NOVICE,100)
             htmltext = htmlfiles[2]
           else:
             st.giveItems(SOULSHOT_NOVICE,200)
             htmltext = htmlfiles[1]
         else:
           if st.getPlayer().getClassId().isMage() :
             htmltext = "7131-02.htm"
             if st.getPlayer().getRace().ordinal() == 3 :
              htmltext = "7575-02.htm"
           else:
             htmltext = "7530-02.htm"
       elif int(st.get("cond"))==2 :
        htmltext = htmlfiles[3]
      elif npcTyp == 0 :
        if int(st.get("cond"))==1 :
          htmltext = htmlfiles[0]
        elif int(st.get("cond"))==2 :
          htmltext = htmlfiles[1]
        elif int(st.get("cond"))==3 :
          htmltext = htmlfiles[2] 
   else:
       htmltext = "<html><head><body>You are too experienced now.</body></html>"
   return htmltext

 def onKill (self,npc,st):
   if int(st.get("cond"))==1 and st.getRandom(100) < 25 and st.getQuestItemsCount(BLUE_GEM) == 0 :
      st.giveItems(BLUE_GEM,1)
      st.playSound("ItemSound.quest_itemget")
      st.playSound("ItemSound.quest_tutorial")
   return

QUEST       = Quest(999,"999_C3Tutorial","C3 Tutorial")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)

for startNpc in [7008,7009,7017,7019,7129,7131,7404,7056,7011,7012,7401,7403,7402,7018,7021,7020,7574,7370,7400,7528,7530,7573,7575]:
  QUEST.addStartNpc(startNpc)
  STARTING.addTalkId(startNpc)
  STARTED.addTalkId(startNpc)


STARTED.addKillId(1)
STARTED.addKillId(5198)

print "importing quests: 999: C3 Tutorial"
