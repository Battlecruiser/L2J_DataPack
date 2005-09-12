#
# Created by DraX on 2005.08.12
# minor fixes by DrLecter 2005.09.10

print "importing village master data: clan                   ...done"

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest


NPC=[7026,7031,7037,7066,7070,7109,7115,7120,7154,7174,7175,7176,7187,7191,7195,7288,7289,7290,7297,7358,7373,7462,7474,7498,7499,7500,7503,7504,7505,7508,7511,7512,7513,7520,7525,7565,7594,7595,7676,7677,7681,7685,7687,7689,7694,7699,7704,7845,7847,7849,7854,7857,7862,7865,7894,7897,7900,7905,7910,7913]

class Quest (JQuest) :

 def onEvent (self,event,st):
   htmltext     = event
   Level        = st.getPlayer().getLevel()
   ClanLeader   = st.player.isClanLeader()
   PlayerinClan = st.player.getClanId()

   if event == "9000-01.htm": htmltext = "9000-01.htm"
   # Player must be Level 10 or above! (so cannot create clan)
   elif event == "9000-02.htm" and Level <= 9: htmltext = "9000-06.htm"
   # player is always clanleader! (so cannot create clan)
   elif event == "9000-02.htm" and ClanLeader == 1: htmltext = "9000-07.htm"
   # player is already in a clan! (so cannot create clan)
   elif event == "9000-02.htm" and PlayerinClan != 0: htmltext = "9000-09.htm"
   # always shown the clan raise page!
   elif event == "9000-03.htm": htmltext = "9000-03.htm"
   # player must be clanleader to dissolve clan!
   elif event == "9000-04.htm" and ClanLeader == 1: htmltext = "9000-04.htm"
   # player must be clanleader to dissolve clan! 
   elif event == "9000-04.htm" and PlayerinClan != 0: htmltext = "9000-08.htm"
   # player must be in a clan to dissolve clan! 
   elif event == "9000-04.htm" and PlayerinClan == 0: htmltext = "9000-11.htm"
   elif event == "9000-05.htm": htmltext = "9000-05.htm"
   else: htmltext = "9000-02.htm"
   st.exitQuest(1)
   return htmltext

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onTalk (Self,npcId,st):
   if npcId in NPC:
     st.exitQuest(1)
     return "9000-01.htm"

QUEST   = Quest(9000,"9000_clan","village_master")
CREATED = State('Start',QUEST)

QUEST.setInitialState(CREATED)


for item in NPC:
### Quest NPC starter initialization
   QUEST.addStartNpc(item)
### Quest NPC initialization
   STARTED.addTalkId(item)
