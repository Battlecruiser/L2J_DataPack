# Made by Kerberos v1.0 on 2009/23/02
# this script is part of the Official L2J Datapack Project.
# Visit http://www.l2jdp.com/forum for more details.

import sys

from com.l2jserver.gameserver.model.quest			import State
from com.l2jserver.gameserver.model.quest			import QuestState
from com.l2jserver.gameserver.model.quest.jython		import QuestJython as JQuest

qn = "190_Lost_Dream"

#NPCs
Kusto = 30512
Nikola = 30621
Lorain = 30673
Juris = 30113

class Quest (JQuest) :
    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)

    def onAdvEvent (self,event,npc, player) :
        st = player.getQuestState(qn)
        if not st: return
        htmltext = event
        if event == "30512-02.htm" :
            st.playSound("ItemSound.quest_accept")
            st.set("cond","1")
        elif event == "30512-05.htm" :
            st.playSound("ItemSound.quest_middle")
            st.set("cond","3")
        elif event == "30113-03.htm" :
            st.playSound("ItemSound.quest_middle")
            st.set("cond","2")
        return htmltext

    def onTalk (self,npc,player):
        htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
        st = player.getQuestState(qn)
        if not st : return htmltext
        npcId = npc.getNpcId()
        id = st.getState()
        cond = st.getInt("cond")
        if id == State.COMPLETED:
            htmltext = "<html><body>This quest has already been completed.</body></html>"
        elif id == State.STARTED:
            if npcId == Kusto:
                if not cond :
                    if player.getLevel() < 42 :
                        htmltext = "30512-00.htm"
                    else :
                        htmltext = "30512-01.htm"
                elif cond  == 1:
                    htmltext = "30512-03.htm"
                elif cond  == 2:
                    htmltext = "30512-04.htm"
                elif cond  == 3:
                    htmltext = "30512-06.htm"
                elif cond  == 5:
                    htmltext = "30512-07.htm"
                    if player.getLevel() < 50 :
                        st.addExpAndSp(309467,20614)
                    st.giveItems(57,109427)
                    st.exitQuest(False)
                    st.playSound("ItemSound.quest_finish")
            elif npcId == Juris:
                if cond  == 1:
                    htmltext = "30113-01.htm"
                elif cond  == 2:
                    htmltext = "30113-04.htm"
            elif npcId == Lorain:
                if cond  == 3:
                    htmltext = "30673-01.htm"
                    st.playSound("ItemSound.quest_middle")
                    st.set("cond","4")
                elif cond  == 4:
                    htmltext = "30673-02.htm"
            elif npcId == Nikola:
                if cond  == 4:
                    htmltext = "30621-01.htm"
                    st.playSound("ItemSound.quest_middle")
                    st.set("cond","5")
                elif cond  == 5:
                    htmltext = "30621-02.htm"
        return htmltext

    def onFirstTalk (self,npc,player):
       st = player.getQuestState(qn)
       qs = player.getQuestState("187_Nikolas_Heart")
       if not st and qs and qs.getState() == State.COMPLETED:
           st = self.newQuestState(player)
           st.setState(State.STARTED)
       npc.showChatWindow(player)
       return None

QUEST       = Quest(190,qn,"Lost Dream")

QUEST.addTalkId(Kusto)
QUEST.addTalkId(Lorain)
QUEST.addTalkId(Nikola)
QUEST.addTalkId(Juris)
QUEST.addFirstTalkId(Kusto)