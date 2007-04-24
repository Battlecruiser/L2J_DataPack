# Created by Emperorc
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "606_WarWithVarkaSilenos"

#NPC
Kadun = 31370

#Mobs
Varka_Mobs = [ 21350, 21351, 21353, 21354, 21355, 21357, 21358, 21360, 21361, \
21362, 21369, 21370, 21364, 21365, 21366, 21368, 21371, 21372, 21373, 21374, 21375 ]
Ketra_Orcs = [ 21324, 21325, 21327, 21328, 21329, 21331, 21332, 21334, 21335, \
21336, 21338, 21339, 21340, 21342, 21343, 21344, 21345, 21346, 21347, 21348, 21349 ]

#Items
Horn = 7186
Mane = 7233

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
     htmltext = event
     manes = st.getQuestItemsCount(Mane)
     if event == "31370-03.htm" :
       if st.getPlayer().getLevel() >= 74 and st.getPlayer().getAllianceWithVarkaKetra() >= 1 : #the alliance check is only temporary, should be done on core side/AI
            st.set("cond","1")
            st.set("id","1")
            st.setState(STARTED)
            st.playSound("ItemSound.quest_accept")
            htmltext = "31370-03.htm"
       else :
            htmltext = "31370-02.htm"
            st.exitQuest(1)
     elif event == "31370-06.htm" :
         htmltext = "31370-06.htm"
     elif event == "31370-07.htm" :
         if manes >= 100 :
             htmltext = "31370-07.htm"
             st.takeItems(Mane,100)
             st.giveItems(Horn,20)
         else :
             htmltext = "31370-08.htm"
     elif event == "31370-09.htm" :
         htmltext == "31370-09.htm"
         st.unset("id")
         st.takeItems(Mane,-1)
         st.exitQuest(1)
     return htmltext

 def onTalk (Self,npc,player):
     htmltext = "<html><head><body>I have nothing to say to you.</body></html>"
     st = player.getQuestState(qn)
     if st :
        npcId = npc.getNpcId()
        id = st.getInt("id")
        cond = st.getInt("cond")
        manes = st.getQuestItemsCount(Mane)
        if npcId == Kadun :
         if id == 1 :
             if manes :
                 htmltext = "31370-04.htm"
                 st.set("cond","2")
             else :
                htmltext = "31370-05.htm"
         else :
             htmltext = "31370-01.htm"
     return htmltext

 def onKill (self,npc,player):
     partyMember = self.getRandomPartyMemberState(player, STARTED)
     if not partyMember: return
     st = partyMember.getQuestState(qn)
     if st :
        if st.getState() == STARTED :
         npcId = npc.getNpcId()
         manes = st.getQuestItemsCount(Mane)
         st2 = st.getPlayer().getQuestState("605_AllianceWithKetraOrcs")
         if npcId in Varka_Mobs and st.getPlayer().getAllianceWithVarkaKetra() >= 1 :
        #see comments in 605 : Alliance with Ketra Orcs for reason for doing st2 check
            if not st2 :
                st.giveItems(Mane,1)
                if manes == 100 :
                    st.playSound("ItemSound.quest_middle")
                else :
                    st.playSound("ItemSound.quest_itemget")
         elif npcId in Ketra_Orcs :
             st.unset("id")
             st.takeItems(Mane,-1)
             st.exitQuest(1)
     return

QUEST       = Quest(606, qn, "War With Varka Silenos")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(Kadun)
QUEST.addTalkId(Kadun)

for mobId in Varka_Mobs :
  QUEST.addKillId(mobId)
  STARTED.addQuestDrop(mobId,Mane,1)
for mobId in Ketra_Orcs :
  QUEST.addKillId(mobId)

print "importing quests: 606: War With Varka Silenos"
