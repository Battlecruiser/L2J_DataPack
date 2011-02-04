# Made by Kerberos v1.0 on 2009/05/2
# this script is part of the Official L2J Datapack Project.
# Visit http://www.l2jdp.com/forum for more details.

import sys

from com.l2jserver.gameserver.datatables import SkillTable
from com.l2jserver.gameserver.model.quest        			import State
from com.l2jserver.gameserver.model.quest        			import QuestState
from com.l2jserver.gameserver.model.quest.jython 			import QuestJython as JQuest

qn = "10267_JourneyToGracia"

#NPCs
Orven = 30857
Keucereus = 32548
Papiku = 32564

#items
Letter = 13810

class Quest (JQuest) :
    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)
        self.questItemIds = [Letter]

    def onAdvEvent (self,event,npc, player) :
        htmltext = event
        st = player.getQuestState(qn)
        if not st : return
        if event == "30857-06.htm" :
            st.set("cond","1")
            st.setState(State.STARTED)
            st.playSound("ItemSound.quest_accept")
            st.giveItems(Letter,1)
        elif event == "32564-02.htm" :
            st.set("cond","2")
            st.playSound("ItemSound.quest_middle")
        elif event == "32548-02.htm" :
            st.giveItems(57,92500)
            st.addExpAndSp(75480,7570)
            st.unset("cond")
            st.exitQuest(False)
            st.playSound("ItemSound.quest_finish")
        return htmltext

    def onTalk (self,npc,player):
        htmltext = Quest.getNoQuestMsg(player)
        st = player.getQuestState(qn)
        if not st : return htmltext
        npcId = npc.getNpcId()
        id = st.getState()
        cond = st.getInt("cond")
        if id == State.COMPLETED :
            if npcId == Keucereus :
                htmltext = "32548-03.htm"
            elif npcId == Orven :
                htmltext = "30857-0a.htm"
        elif id == State.CREATED and npcId == Orven:
            if player.getLevel() < 75 :
                htmltext = "30857-00.htm"
            else :
                htmltext = "30857-01.htm"
        elif id == State.STARTED and npcId == Orven:
            htmltext = "30857-07.htm"
        elif id == State.STARTED and npcId == Papiku:
            if cond == 1 :
                htmltext = "32564-01.htm"
            else :
                htmltext = "32564-03.htm"
        elif id == State.STARTED and npcId == Keucereus and cond == 2:
            htmltext = "32548-01.htm"
        return htmltext

QUEST       = Quest(10267,qn,"Journey to Gracia")

QUEST.addStartNpc(Orven)
QUEST.addTalkId(Orven)
QUEST.addTalkId(Keucereus)
QUEST.addTalkId(Papiku)