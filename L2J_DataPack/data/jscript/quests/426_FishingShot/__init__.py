# Made by Drov.
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

SWEET_FLUID = 7586

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "02.htm" :
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
    elif event == "07.htm" :
      st.exitQuest(1)
      st.playSound("ItemSound.quest_finish")
    return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   cond=st.getInt("cond")
   if cond==0 :
     htmltext = "01.htm"
   elif st.getQuestItemsCount(SWEET_FLUID) :
     htmltext = "04.htm"
   else :
     htmltext = "03.htm"
   return htmltext

 def onKill(self,npc,st) :
   if st.getRandom(20) == 0 :
     st.giveItems(SWEET_FLUID,1)
     st.playSound("ItemSound.quest_itemget")  
   return

QUEST       = Quest(426,"426_FishingShot","Quest for Fishing Shot")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)

for npc in range(8562,8580)+[8616,8696,8697]:
    QUEST.addStartNpc(npc)
    CREATED.addTalkId(npc)
    STARTED.addTalkId(npc)


for mob in [5,13,16,17,24,25,43,44,46,47,50,58,63,66,70,74,77,78,79,80,81,82,83,84,85,88,89,100,106,115,120,131,132,135,157,176,211,225,227,230,232,234,241,267,268,269,270,271,286,308,312,317,324,333,\
341,345,346,349,350,356,357,363,368,386,387,403,404,433,448,456,463,470,471,475,476,498,511,525,528,536,537,538,539,544,547,550,551,552,554,555,557,559,560,562,573,630,632,634,636,639,641,643,644,646,\
648,650,651,652,655,656,657,658,659,661,663,772,781,783,784,786,788,790,791,792,794,796,798,800,802,804,808,809,811,812,814,815,816,819,822,824,825,828,829,830,833,834,836,837,839,841,843,847,849,859,\
936,937,939,940,941,943,944,978,979,983,985,991,994,1023,1024,1025,1026,1058,1060,1061,1066,1067,1068,1070,1071,1072,1075,1078,1081,1100,1101,1102,1103,1104,1105,1106,1107,1117,1125,1261,1262,1263,1264,\
1269,1270,1271,1272,1273,1314,1316,1318,1320,1322,1376,1378,1380,1393,1508,1510,1511,1514,1516,1518,1520,1526,1529,1530,1532,1536,1544,1605,1606,1611,1612,1617,1618,1629,1630,1635,1636,1652,1657,7739,\
12377,12544,12545]:
    STARTED.addKillId(mob)

STARTED.addQuestDrop(5,SWEET_FLUID,1)

print "importing quests: 426: Quest for Fishing Shot"
