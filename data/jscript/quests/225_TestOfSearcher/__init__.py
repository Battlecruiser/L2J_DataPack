# completely rewritten by Rolarga, original from Mr
# modified by Ariakas 08.12.2005
# Version 0.4 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "225_TestOfSearcher"

LUTHERS_LETTER_ID,     ALANKELLS_WARRANT_ID,LEIRYNNS_ORDER1_ID,DELU_TOTEM_ID,  \
LEIRYNNS_ORDER2_ID,    CHIEF_KALKIS_FANG_ID,LEIRYNNS_REPORT_ID,STRANGE_MAP_ID, \
LAMBERTS_MAP_ID,       ALANKELLS_LETTER_ID, ALANKELLS_ORDER_ID,WINE_CATALOG_ID,\
TWEETYS_CONTRACT_ID,   RED_SPORE_DUST_ID,   MALRUKIAN_WINE_ID, OLD_ORDER_ID,   \
REXS_DIARY_ID,         TORN_MAP_PIECE1_ID,  TORN_MAP_PIECE2_ID,SOLTS_MAP_ID,   \
MAKELS_MAP_ID,         COMBINED_MAP_ID,     RUSTED_KEY1_ID,    GOLD_BAR_ID,    \
ALANKELLS_RECOMMEND_ID,MARK_OF_SEARCHER_ID = range(2784,2810)

#This handle all mob drops   npcId:[condition,maxcount,chance,itemid]
DROPLIST={
20781:["phase",3,10,100,DELU_TOTEM_ID],
27094:["phase",3,10,100,DELU_TOTEM_ID],
27093:["phase",5,1,100,CHIEF_KALKIS_FANG_ID],
20555:["phase",10,10,100,RED_SPORE_DUST_ID],
20551:["soltsMap",1,4,50,TORN_MAP_PIECE1_ID],
20144:["makelsMap",1,4,50,TORN_MAP_PIECE2_ID]
}

NPC=[30291,30420,30628,30690,30728,30729,30730,30627]

MOB=[20144,27093,20551,20555,20781,27094]

STATS=[["phase","cond"],["makelsMap","soltsMap"]]

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "30690-05.htm" :
        for var in STATS[0]:
         st.set(var,"1")
        for var in STATS[1]:
         st.set(var,"0")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.giveItems(LUTHERS_LETTER_ID,1)
    elif event == "30291-07.htm" :
        st.giveItems(LAMBERTS_MAP_ID,1)
        st.takeItems(LEIRYNNS_REPORT_ID,1)
        st.giveItems(ALANKELLS_LETTER_ID,1)
        st.takeItems(STRANGE_MAP_ID,1)
        st.giveItems(ALANKELLS_ORDER_ID,1)
        st.set("phase","8")
    elif event == "30420-01a.htm" :
        st.giveItems(TWEETYS_CONTRACT_ID,1)
        st.takeItems(WINE_CATALOG_ID,1)
        st.set("phase","10")
    elif event == "30730-01d.htm" :
        st.giveItems(REXS_DIARY_ID,1)
        st.takeItems(OLD_ORDER_ID,1)
        st.set("phase","14")
        for var in STATS[1]:
          st.set(var,"1")
    elif event == "30627-01a.htm" :
        st.giveItems(RUSTED_KEY1_ID,1)
#        st.getPcSpawn().addSpawn(30628,10011,157449,-2374,300000)
        st.getPcSpawn().addSpawn(30628,10098,157287,-2406,300000)
        st.set("phase","20")
    elif event == "30628-01a.htm" :
        st.giveItems(GOLD_BAR_ID,20)
        st.takeItems(RUSTED_KEY1_ID,1)
        st.set("phase","21")
    return htmltext


 def onTalk (self,npc,player):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   id = st.getState()
   if npcId != 30690 and id != STARTED : return htmltext

   if id == CREATED :
     st.set("cond","0")
     st.set("phase","0")
     if npcId == NPC[3]:
          if st.getPlayer().getClassId().getId() in [ 0x07, 0x16, 0x23, 0x36] :
           if st.getPlayer().getLevel() > 38 :
            if st.getPlayer().getClassId().getId() == 0x36 :
              htmltext = "30690-04.htm"
            else:
              htmltext = "30690-03.htm"
           else:
             htmltext = "30690-02.htm"
             st.exitQuest(1)
          else:
           htmltext = "30690-01.htm"
           st.exitQuest(1)
   elif id==COMPLETED :
     htmltext = "<html><head><body>This quest has already been completed.</body></html>"
   else:
     phase=int(st.get("phase"))
     if npcId== NPC[3]:
       if phase==1 :
         htmltext = "30690-06.htm"
       elif phase>1 and phase<22 :
         htmltext = "30690-07.htm"
       elif phase==22 :
         st.addExpAndSp(37831,18750)
         htmltext = "30690-08.htm"
         for var in STATS[0]:
          st.unset(var)
         for var in STATS[1]:
          st.unset(var)
         st.setState(COMPLETED)
         st.playSound("ItemSound.quest_finish")
         st.giveItems(MARK_OF_SEARCHER_ID,1)
         st.takeItems(ALANKELLS_RECOMMEND_ID,1)
     elif npcId == NPC[0] :
      if phase==1 :
        htmltext = "30291-01.htm"
        st.giveItems(ALANKELLS_WARRANT_ID,1)
        st.takeItems(LUTHERS_LETTER_ID,1)
        st.set("phase","2")
      elif phase == 2:
        htmltext = "30291-02.htm"
      elif phase>2 and phase<7 :
        htmltext = "30291-03.htm"
      elif phase==7 :
        htmltext = "30291-04.htm"
      elif phase==8 :
        htmltext = "30291-08.htm"
      elif phase==13 or phase==14 :
        htmltext = "30291-09.htm"
      elif phase==18 :
        htmltext = "30291-10.htm"
      elif phase==21 :
        htmltext = "30291-11.htm"
        st.giveItems(ALANKELLS_RECOMMEND_ID,1)
        st.takeItems(ALANKELLS_ORDER_ID,1)
        st.takeItems(COMBINED_MAP_ID,1)
        st.takeItems(GOLD_BAR_ID,-1)
        st.removeRadar(10133,157155,-2383);
        st.set("phase","22")
      elif phase==22 :
        htmltext = "30291-12.htm"
     elif npcId == NPC[4] :
      if phase==2 :
        htmltext = "30728-01.htm"
        st.giveItems(LEIRYNNS_ORDER1_ID,1)
        st.takeItems(ALANKELLS_WARRANT_ID,1)
        st.set("phase","3")
      elif phase==3 :
        htmltext = "30728-02.htm"
      elif phase==4 :
        htmltext = "30728-03.htm"
        st.takeItems(DELU_TOTEM_ID,-1)
        st.takeItems(LEIRYNNS_ORDER1_ID,1)
        st.giveItems(LEIRYNNS_ORDER2_ID,1)
        st.set("phase","5")
      elif phase==5 :
        htmltext = "30728-04.htm"
      elif phase==6 :
        htmltext = "30728-05.htm"
        st.giveItems(LEIRYNNS_REPORT_ID,1)
        st.takeItems(CHIEF_KALKIS_FANG_ID,1)
        st.takeItems(LEIRYNNS_ORDER2_ID,1)
        st.set("phase","7")
      elif phase==7 :
        htmltext = "30728-06.htm"
      elif phase==8 :
        htmltext = "30728-07.htm"
     elif npcId == NPC[5]: 
      if phase==8 :
        htmltext = "30729-01.htm"
        st.giveItems(WINE_CATALOG_ID,1)
        st.takeItems(ALANKELLS_LETTER_ID,1)
        st.set("phase","9")
      elif phase==9 :
        htmltext = "30729-02.htm"
      elif phase==12 :
        htmltext = "30729-03.htm"
        st.giveItems(OLD_ORDER_ID,1)
        st.takeItems(WINE_CATALOG_ID,1)
        st.takeItems(MALRUKIAN_WINE_ID,1)
        st.set("phase","13")
      elif phase==13 :
        htmltext = "30729-04.htm"
      elif phase in [8,14] :
        htmltext = "30729-05.htm"
     elif npcId == NPC[1] :
      if phase==10 :
        htmltext = "30420-02.htm"
      elif phase==11 :
          htmltext = "30420-03.htm"
          st.giveItems(MALRUKIAN_WINE_ID,1)
          st.takeItems(TWEETYS_CONTRACT_ID,1)
          st.takeItems(RED_SPORE_DUST_ID,-1)
          st.set("phase","12")
      elif phase in [12,13]  :
        htmltext = "30420-04.htm"
      elif phase==9 :
        htmltext = "30420-01.htm"
     elif npcId == NPC[6] :
      if phase==13 :
        htmltext = "30730-01.htm"
      elif phase == 14:
       if int(st.get("soltsMap"))==2 and int(st.get("makelsMap"))==2:
         htmltext = "30730-03.htm"
         st.takeItems(LAMBERTS_MAP_ID,1)
         st.takeItems(TORN_MAP_PIECE2_ID,4)
         st.takeItems(TORN_MAP_PIECE1_ID,4)
         st.takeItems(REXS_DIARY_ID,1)
         st.takeItems(SOLTS_MAP_ID,1)
         st.giveItems(COMBINED_MAP_ID,1)
         st.takeItems(MAKELS_MAP_ID,1)
         st.addRadar(10133,157155,-2383);
         st.set("phase","18")
       else:
         htmltext = "30730-02.htm"
      elif phase>17 :
        htmltext = "30730-04.htm"
     elif npcId == NPC[7] and phase==18:
        htmltext = "30627-01.htm"
     elif npcId == NPC[2] :
        if phase==20 :
          htmltext = "30628-01.htm"
        else:
          htmltext = "<html><head><body>You haven't got a Key for this Chest.</body></html>"
   return htmltext

 def onKill (self,npc,player):
   st = player.getQuestState(qn)
   if not st : return 
   if st.getState() != STARTED : return 
   
   npcId = npc.getNpcId()
   var,status,maxcount,chance,itemid=DROPLIST[npcId]
   random = st.getRandom(100)
   count=st.getQuestItemsCount(itemid)
   if int(st.get(var))==status and count<maxcount and random<chance :
     st.giveItems(itemid,1)
     if count==maxcount-1:
      st.playSound("ItemSound.quest_middle")
      st.set(var,str(status+1))
      if npcId == 27093:
       st.giveItems(STRANGE_MAP_ID,1)
      elif npcId==20144:
       st.giveItems(MAKELS_MAP_ID,1)
       st.takeItems(TORN_MAP_PIECE2_ID,4)
      elif npcId==20551:
       st.giveItems(SOLTS_MAP_ID,1)
       st.takeItems(TORN_MAP_PIECE1_ID,4)
     else:
      st.playSound("Itemsound.quest_itemget")
   if npcId==20781 and random<30 and count<maxcount:
     st.getPcSpawn().addSpawn(27094,55841,176464,-2993,300000)
     return "Delu Lizardman Assassin has spawned at X=55841 Y=176464 Z=-2993"
   return


QUEST       = Quest(225,qn,"Test Of Searcher")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30690)

for npcId in NPC:
 QUEST.addTalkId(npcId)

for mobId in MOB:
 QUEST.addKillId(mobId)

for item in range(2784,2809):
    STARTED.addQuestDrop(30690,item,1)

print "importing quests: 225: Test Of Searcher"
