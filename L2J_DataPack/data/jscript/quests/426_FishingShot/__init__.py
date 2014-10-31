# Made by Drov.
import sys
from net.sf.l2j import Config
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "426_FishingShot"

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

 def onTalk (self,npc,player):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   cond=st.getInt("cond")
   if cond==0 :
     htmltext = "01.htm"
   elif st.getQuestItemsCount(SWEET_FLUID) :
     htmltext = "04.htm"
   else :
     htmltext = "03.htm"
   return htmltext

 def onKill(self,npc,player) :
   partyMember = self.getRandomPartyMemberState(player, STARTED)
   if not partyMember : return 
   st = partyMember.getQuestState(qn)
   
   if st.getRandom(20) == 0 :
     st.giveItems(SWEET_FLUID,int(Config.RATE_DROP_QUEST))
     st.playSound("ItemSound.quest_itemget")  
   return

QUEST       = Quest(426,qn,"Quest for Fishing Shot")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)

for npc in range(31562,31580)+[31616,31696,31697]:
    QUEST.addStartNpc(npc)
    QUEST.addTalkId(npc)

for mob in [20005,20013,20016,20017,20024,20025,20043,20044,20046,20047,20050,20058,20063,20066,20070,20074,20077,20078,20079,20080,20081,20082,20083,20084,20085,20088,20089,20100,20106,20115,20120,20131,20132,20135,20157,20176,20211,20225,20227,20230,20232,20234,20241,20267,20268,20269,20270,20271,20286,20308,20312,20317,20324,20333,\
20341,20345,20346,20349,20350,20356,20357,20363,20368,20386,20387,20403,20404,20433,20448,20456,20463,20470,20471,20475,20476,20498,20511,20525,20528,20536,20537,20538,20539,20544,20547,20550,20551,20552,20554,20555,20557,20559,20560,20562,20573,20630,20632,20634,20636,20639,20641,20643,20644,20646,\
20648,20650,20651,20652,20655,20656,20657,20658,20659,20661,20663,20772,20781,20783,20784,20786,20788,20790,20791,20792,20794,20796,20798,20800,20802,20804,20808,20809,20811,20812,20814,20815,20816,20819,20822,20824,20825,20828,20829,20830,20833,20834,20836,20837,20839,20841,20843,20847,20849,20859,\
20936,20937,20939,20940,20941,20943,20944,20978,20979,20983,20985,20991,20994,21023,21024,21025,21026,21058,21060,21061,21066,21067,21068,21070,21071,21072,21075,21078,21081,21100,21101,21102,21103,21104,21105,21106,21107,21117,21125,21261,21262,21263,21264,\
21269,21270,21271,21272,21273,21314,21316,21318,21320,21322,21376,21378,21380,21393,21508,21510,21511,21514,21516,21518,21520,21526,21529,21530,21532,21536,21544,21605,21606,21611,21612,21617,21618,21629,21630,21635,21636,21652,21657,30739,\
29024,29026,29027]:
    QUEST.addKillId(mob)

STARTED.addQuestDrop(20005,SWEET_FLUID,1)

print "importing quests: 426: Quest for Fishing Shot"
