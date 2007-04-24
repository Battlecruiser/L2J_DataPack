# Maked by Mr. - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "157_RecoverSmuggled"

ADAMANTITE_ORE = 1024
BUCKLER = 20

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
       st.set("cond","1")
       st.setState(STARTED)
       st.playSound("ItemSound.quest_accept")
       htmltext = "30005-05.htm"
    elif event == "157_1" :
       htmltext = "30005-04.htm"
    return htmltext

 def onTalk (self,npc,player):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   id = st.getState()
   cond = st.getInt("cond")
   if id == COMPLETED :
     htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif cond == 0 :
     if st.getPlayer().getLevel() >= 5 :
        htmltext = "30005-03.htm"
     else :
        htmltext = "30005-02.htm"
        st.exitQuest(1)
   elif cond :
     if st.getQuestItemsCount(ADAMANTITE_ORE)>=20 :
        st.takeItems(ADAMANTITE_ORE,-1)
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
        st.giveItems(BUCKLER,1)
        htmltext = "30005-07.htm"
     else :
        htmltext = "30005-06.htm"
   return htmltext

 def onKill (self,npc,player):
   st = player.getQuestState(qn)
   if not st : return 
   if st.getState() != STARTED : return 

   npcId = npc.getNpcId()
   if int(st.get("cond"))==1 and st.getQuestItemsCount(ADAMANTITE_ORE)<20 and st.getRandom(10)<4 :
      st.giveItems(ADAMANTITE_ORE,1)
      if st.getQuestItemsCount(ADAMANTITE_ORE) == 20 :
         st.playSound("ItemSound.quest_middle")
         st.set("cond","2")
      else:
         st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(157,qn,"Recover Smuggled")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30005)

QUEST.addTalkId(30005)
QUEST.addKillId(20121)

STARTED.addQuestDrop(20121,ADAMANTITE_ORE,1)

print "importing quests: 157: Recover Smuggled"
