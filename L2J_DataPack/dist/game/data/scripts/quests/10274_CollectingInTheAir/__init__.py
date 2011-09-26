# Made by Kerberos v1.0 on 2009/04/26
# this script is part of the Official L2J Datapack Project.
# Visit http://www.l2jdp.com/forum for more details.

import sys

from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest
from com.l2jserver.gameserver.util import Util

qn = "10274_CollectingInTheAir"

#NPCs
Lekon = 32557

#items
Scroll = 13844
red = 13858
blue = 13859
green = 13860

class Quest (JQuest) :
    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)
        self.questItemIds = [Scroll,red,blue,green]

    def onAdvEvent (self,event,npc, player) :
        htmltext = event
        st = player.getQuestState(qn)
        if not st : return
        if event == "32557-03.htm" :
            st.set("cond","1")
            st.giveItems(Scroll,8)
            st.setState(State.STARTED)
            st.playSound("ItemSound.quest_accept")
        return htmltext

    def onTalk (self,npc,player):
        htmltext = Quest.getNoQuestMsg(player)
        st = player.getQuestState(qn)
        if not st : return htmltext
        npcId = npc.getNpcId()
        id = st.getState()
        cond = st.getInt("cond")
        transform = st.getInt("transform")
        if id == State.COMPLETED :
            htmltext = "32557-0a.htm"
        elif id == State.CREATED :
            qs = player.getQuestState("10273_GoodDayToFly")
            if qs:
                if qs.getState() == State.COMPLETED and player.getLevel() >= 75 :
                    htmltext = "32557-01.htm"
                else:
                    htmltext = "32557-00.htm"
            else :
                htmltext = "32557-00.htm"
        else :
            if st.getQuestItemsCount(red) + st.getQuestItemsCount(blue) + st.getQuestItemsCount(green) >= 8 :
                htmltext = "32557-05.htm"
                st.giveItems(13728,1)
                st.addExpAndSp(25160,2525)
                st.unset("transform")
                st.unset("cond")
                st.exitQuest(False)
                st.playSound("ItemSound.quest_finish")
            else:
                htmltext = "32557-04.htm"
        return htmltext

    def onSkillSee (self, npc, player, skill, targets, isPet):
        st = player.getQuestState(qn)
        if not st : return
        if Util.contains(targets, npc) and st.getInt("cond") == 1 and skill.getId() == 2630:
            st.playSound("ItemSound.quest_itemget")
            npcId = npc.getNpcId()
            if npcId in range(18684,18687):
                st.giveItems(red,1)
            elif npcId in range(18687,18690):
                st.giveItems(blue,1)
            elif npcId in range(18690,18693):
                st.giveItems(green,1)
            npc.doDie(player)
        return

QUEST       = Quest(10274,qn,"Collecting in the Air")

QUEST.addStartNpc(Lekon)
QUEST.addTalkId(Lekon)
QUEST.addSkillSeeId(18684)
QUEST.addSkillSeeId(18685)
QUEST.addSkillSeeId(18686)
QUEST.addSkillSeeId(18687)
QUEST.addSkillSeeId(18688)
QUEST.addSkillSeeId(18689)
QUEST.addSkillSeeId(18690)
QUEST.addSkillSeeId(18691)
QUEST.addSkillSeeId(18692)