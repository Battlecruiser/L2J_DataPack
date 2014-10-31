#
# Created by DraX on 2005.08.12
# minor fixes by DrLecter 2005.09.10

print "importing village master data: Alliance               ...done"

import sys

from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

NPC=[7026,7031,7037,7066,7070,7109,7115,7120,7154,7174,7175,7176,7187,7191,7195,7288,7289,7290,7297,7358,7373,7462,7474,7498,7499,7500,7503,7504,7505,7508,7511,7512,7513,7520,7525,7565,7594,7595,7676,7677,7681,7685,7687,7689,7694,7699,7704,7845,7847,7849,7854,7857,7862,7865,7894,7897,7900,7905,7910,7913,8269,8272,8276,8279,8285,8288,8314,8317,8321,8324,8326,8328,8331,8334,8755]

class Quest (JQuest) :

 def onEvent (self,event,st):
   ClanLeader = st.player.isClanLeader();
   Clan = st.player.getClanId();
   htmltext = event
   if event == "9001-01.htm": htmltext = "9001-01.htm"
   elif (Clan == 0):
	st.exitQuest(1);
	htmltext = "<html><body>You must be in Clan.</body></html";
   elif event == "9001-02.htm": htmltext = "9001-02.htm"
   return htmltext

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   ClanLeader = st.player.isClanLeader();
   Clan = st.player.getClan();
   if npcId in NPC:
     st.set("cond","0")
     st.setState(STARTED)
     return "9001-01.htm"

QUEST      = Quest(9001,"9001_alliance","village_master")
CREATED    = State('Start',     QUEST)
STARTED    = State('Started',   QUEST)
COMPLETED  = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

for item in NPC:
### Quest NPC starter initialization
   QUEST.addStartNpc(item)
### Quest NPC initialization
   STARTED.addTalkId(item)
