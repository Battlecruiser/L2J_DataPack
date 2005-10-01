# Maked by Mr. Have fun! Version 0.2
print "importing quests: 225: Test Of Searcher"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

LUTHERS_LETTER_ID = 2784
ALANKELLS_WARRANT_ID = 2785
LEIRYNNS_ORDER1_ID = 2786
DELU_TOTEM_ID = 2787
LEIRYNNS_ORDER2_ID = 2788
CHIEF_KALKIS_FANG_ID = 2789
MARK_OF_SEARCHER_ID = 2809
ALANKELLS_RECOMMEND_ID = 2808
LAMBERTS_MAP_ID = 2792
LEIRYNNS_REPORT_ID = 2790
ALANKELLS_LETTER_ID = 2793
STRANGE_MAP_ID = 2791
ALANKELLS_ORDER_ID = 2794
COMBINED_MAP_ID = 2805
GOLD_BAR_ID = 2807
WINE_CATALOG_ID = 2795
OLD_ORDER_ID = 2799
MALRUKIAN_WINE_ID = 2798
TWEETYS_CONTRACT_ID = 2796
RED_SPORE_DUST_ID = 2797
REXS_DIARY_ID = 2800
SOLTS_MAP_ID = 2803
MAKELS_MAP_ID = 2804
RUSTED_KEY1_ID = 2806
TORN_MAP_PIECE1_ID = 2801
TORN_MAP_PIECE2_ID = 2802

#This handle all mob drops   npcId:[condition,maxcount,chance,itemid]
DROPLIST={
781:[3,10,100,DELU_TOTEM_ID],
5093:[5,1,100,CHIEF_KALKIS_FANG_ID],
555:[10,10,100,RED_SPORE_DUST_ID],
551:[14,4,50,TORN_MAP_PIECE1_ID],
144:[14,4,50,TORN_MAP_PIECE2_ID]
}

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        htmltext = "7690-05.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.giveItems(LUTHERS_LETTER_ID,1)
    elif event == "7291_1" :
          htmltext = "7291-05.htm"
    elif event == "7291_2" :
          htmltext = "7291-06.htm"
    elif event == "7291_3" :
          htmltext = "7291-07.htm"
          st.giveItems(LAMBERTS_MAP_ID,1)
          st.takeItems(LEIRYNNS_REPORT_ID,1)
          st.giveItems(ALANKELLS_LETTER_ID,1)
          st.takeItems(STRANGE_MAP_ID,1)
          st.giveItems(ALANKELLS_ORDER_ID,1)
          st.set("cond","8")
    elif event == "7420_1" :
          htmltext = "7420-01a.htm"
          st.giveItems(TWEETYS_CONTRACT_ID,1)
          st.takeItems(WINE_CATALOG_ID,1)
          st.set("cond","10")
    elif event == "7730_1" :
          htmltext = "7730-01a.htm"
    elif event == "7730_2" :
          htmltext = "7730-01b.htm"
    elif event == "7730_3" :
          htmltext = "7730-01c.htm"
    elif event == "7730_4" :
          htmltext = "7730-01d.htm"
          st.giveItems(REXS_DIARY_ID,1)
          st.takeItems(OLD_ORDER_ID,1)
          st.set("cond","14")
    elif event == "7627_1" :
          htmltext = "7627-01a.htm"
          st.giveItems(RUSTED_KEY1_ID,1)
          st.spawnNpc(7628)
          st.set("cond","20")
    elif event == "7628_1" :
          htmltext = "7628-01a.htm"
          st.giveItems(GOLD_BAR_ID,20)
          st.takeItems(RUSTED_KEY1_ID,1)
          #st.despawnNpc(7628)
          st.set("cond","21")
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7690 :
       if int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if int(st.get("cond")) < 15 :
          if (st.getPlayer().getClassId().getId() == 0x07 or st.getPlayer().getClassId().getId() == 0x16 or st.getPlayer().getClassId().getId() == 0x23 or st.getPlayer().getClassId().getId() == 0x36) :
           if st.getPlayer().getLevel()> 38:
            if st.getPlayer().getClassId().getId() == 0x36 :
              htmltext = "7690-04.htm"
            else:
              htmltext = "7690-03.htm"
           else:
             htmltext = "7690-02.htm"
          else:
           htmltext = "7690-01.htm"
           st.exitQuest(1)
       elif int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
         htmltext = "<html><head><body>This quest has already been completed.</body></html>"
       elif int(st.get("cond"))==1 :
         htmltext = "7690-06.htm"
       elif int(st.get("cond"))==2 or int(st.get("cond"))==3 :
          htmltext = "7690-07.htm"
       elif int(st.get("cond"))==22 :
            st.addExpAndSp(21000,2600)
            htmltext = "7690-08.htm"
            st.set("cond","0")
            st.set("onlyone","1")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
            st.giveItems(MARK_OF_SEARCHER_ID,1)
            st.takeItems(ALANKELLS_RECOMMEND_ID,1)
   elif npcId == 7291 :
      if int(st.get("cond"))==1 :
        htmltext = "7291-01.htm"
        st.giveItems(ALANKELLS_WARRANT_ID,1)
        st.takeItems(LUTHERS_LETTER_ID,1)
        st.set("cond","2")
      elif int(st.get("cond"))==2 or int(st.get("cond"))==3:
        htmltext = "7291-02.htm"
      elif int(st.get("cond"))==3 or int(st.get("cond"))==5 :
        htmltext = "7291-03.htm"
      elif int(st.get("cond"))==7 :
        htmltext = "7291-04.htm"
      elif int(st.get("cond"))==8 :
        htmltext = "7291-08.htm"
      elif int(st.get("cond"))==13 or int(st.get("cond"))==14 :
        htmltext = "7291-09.htm"
      elif int(st.get("cond"))==18 :
        htmltext = "7291-10.htm"
        st.set("cond","19")
      elif int(st.get("cond"))==21 :
        htmltext = "7291-11.htm"
        st.giveItems(ALANKELLS_RECOMMEND_ID,1)
        st.takeItems(ALANKELLS_ORDER_ID,1)
        st.takeItems(COMBINED_MAP_ID,1)
        st.takeItems(GOLD_BAR_ID,st.getQuestItemsCount(GOLD_BAR_ID))
        st.removeRadar(10133,157155,-2383);
        st.set("cond","22")
      elif int(st.get("cond"))==22 :
        htmltext = "7291-12.htm"
   elif npcId == 7728 :
      if int(st.get("cond"))==2 :
        htmltext = "7728-01.htm"
        st.giveItems(LEIRYNNS_ORDER1_ID,1)
        st.takeItems(ALANKELLS_WARRANT_ID,1)
        st.set("cond","3")
      elif int(st.get("cond"))==3 :
        htmltext = "7728-02.htm"
      elif int(st.get("cond"))==4 :
        htmltext = "7728-03.htm"
        st.takeItems(DELU_TOTEM_ID,st.getQuestItemsCount(DELU_TOTEM_ID))
        st.takeItems(LEIRYNNS_ORDER1_ID,1)
        st.giveItems(LEIRYNNS_ORDER2_ID,1)
        st.set("cond","5")
      elif int(st.get("cond"))==5 :
        htmltext = "7728-04.htm"
      elif int(st.get("cond"))==6 :
        htmltext = "7728-05.htm"
        st.giveItems(LEIRYNNS_REPORT_ID,1)
        st.takeItems(CHIEF_KALKIS_FANG_ID,1)
        st.takeItems(LEIRYNNS_ORDER2_ID,1)
        st.set("cond","7")
      elif int(st.get("cond"))==7 :
        htmltext = "7728-06.htm"
      elif int(st.get("cond"))==8 :
        htmltext = "7728-07.htm"
   elif npcId == 7729: 
      if int(st.get("cond"))==8 :
        htmltext = "7729-01.htm"
        st.giveItems(WINE_CATALOG_ID,1)
        st.takeItems(ALANKELLS_LETTER_ID,1)
        st.set("cond","9")
      elif int(st.get("cond"))==9 :
        htmltext = "7729-02.htm"
      elif int(st.get("cond"))==12 :
        htmltext = "7729-03.htm"
        st.giveItems(OLD_ORDER_ID,1)
        st.takeItems(WINE_CATALOG_ID,1)
        st.takeItems(MALRUKIAN_WINE_ID,1)
        st.set("cond","13")
      elif int(st.get("cond"))==13 :
        htmltext = "7729-04.htm"
      elif int(st.get("cond"))==8 or int(st.get("cond"))==14 :
        htmltext = "7729-05.htm"
   elif npcId == 7420 :
      if int(st.get("cond"))==10 :
        htmltext = "7420-02.htm"
      elif int(st.get("cond"))==11 :
          htmltext = "7420-03.htm"
          st.giveItems(MALRUKIAN_WINE_ID,1)
          st.takeItems(TWEETYS_CONTRACT_ID,1)
          st.takeItems(RED_SPORE_DUST_ID,st.getQuestItemsCount(RED_SPORE_DUST_ID))
          st.set("cond","12")
      elif int(st.get("cond"))==12 or int(st.get("cond"))==13  :
        htmltext = "7420-04.htm"
      elif int(st.get("cond"))==9 :
        htmltext = "7420-01.htm"
   elif npcId == 7730 :
      if int(st.get("cond"))==13 :
        htmltext = "7730-01.htm"
      elif int(st.get("cond"))==18 :
        htmltext = "7730-04.htm"
      elif (int(st.get("cond"))==14 or int(st.get("cond"))==15) :
       if st.getQuestItemsCount(SOLTS_MAP_ID)>0 and st.getQuestItemsCount(MAKELS_MAP_ID)>0:
         htmltext = "7730-03.htm"
         st.takeItems(LAMBERTS_MAP_ID,1)
         st.takeItems(TORN_MAP_PIECE2_ID,4)
         st.takeItems(TORN_MAP_PIECE1_ID,4)
         st.takeItems(REXS_DIARY_ID,1)
         st.takeItems(SOLTS_MAP_ID,1)
         st.giveItems(COMBINED_MAP_ID,1)
         st.takeItems(MAKELS_MAP_ID,1)
         st.addRadar(10133,157155,-2383);
         st.set("cond","18")
       else:
         htmltext = "7730-02.htm"
   elif npcId == 7627 :
      if int(st.get("cond"))==19 :
        htmltext = "7627-01.htm"
      elif int(st.get("cond"))==21 :
        htmltext = "7627-01.htm"
   elif npcId == 7628 and int(st.get("cond"))==20 :
        htmltext = "7628-01.htm"
   return htmltext
   

 def onKill (self,npcId,st):
   
   condition, maxcount, chance, itemid = DROPLIST[npcId]
   random = st.getRandom(100)
   
   
   if int(st.get("cond"))==condition and st.getQuestItemsCount(itemid)<maxcount and random<chance :
     if st.getQuestItemsCount(itemid)==maxcount-1:
      st.giveItems(itemid,1)
      st.playSound("ItemSound.quest_middle")
      st.set("cond",str(condition+1))
      if npcId == 5093:
       st.giveItems(STRANGE_MAP_ID,1)
     else:
      st.giveItems(itemid,1)
      st.playSound("Itemsound.quest_itemget")
      if npcId==781 and random<30:
       st.spawnNpc(5094)
   elif int(st.get("cond"))==15 and random<chance and st.getQuestItemsCount(itemid)==maxcount:
    if npcId == 144 and st.getQuestItemsCount(MAKELS_MAP_ID)==0:
     st.giveItems(MAKELS_MAP_ID,1)
     st.takeItems(TORN_MAP_PIECE2_ID,4)
     st.set("cond","14")
    elif npcId == 551 and st.getQuestItemsCount(SOLTS_MAP_ID)==0 :
     st.giveItems(SOLTS_MAP_ID,1)
     st.takeItems(TORN_MAP_PIECE1_ID,4)
     st.set("cond","14")
   return

QUEST       = Quest(225,"225_TestOfSearcher","Test Of Searcher")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7690)

STARTING.addTalkId(7690)

for npcId in [7291,7420,7628,7690,7728,7729,7730,7627]:
 STARTED.addTalkId(npcId)

for mobId in [144,5093,551,555,781,]:
 STARTED.addKillId(mobId)
