# Made by Kerberos v1.0 on 2009/04/25
# this script is part of the Official L2J Datapack Project.
# Visit http://www.l2jdp.com/forum for more details.

import sys

from com.l2jserver.gameserver.datatables import SkillTable
from com.l2jserver.gameserver.model.quest        			import State
from com.l2jserver.gameserver.model.quest        			import QuestState
from com.l2jserver.gameserver.model.quest.jython 			import QuestJython as JQuest

qn = "10273_GoodDayToFly"

#NPCs
Lekon = 32557

#items
Mark = 13856

class Quest (JQuest) :
    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)
        self.questItemIds = [Mark]

    def onAdvEvent (self,event,npc, player) :
        htmltext = event
        st = player.getQuestState(qn)
        if not st : return
        if event == "32557-06.htm" :
            st.set("cond","1")
            st.setState(State.STARTED)
            st.playSound("ItemSound.quest_accept")
        elif event == "32557-09.htm" :
            st.set("transform","1")
            SkillTable.getInstance().getInfo(5982,1).getEffects(player,player)
        elif event == "32557-10.htm" :
            st.set("transform","2")
            SkillTable.getInstance().getInfo(5983,1).getEffects(player,player)
        elif event == "32557-13.htm" :
            if st.getInt("transform") == 1 :
                SkillTable.getInstance().getInfo(5982,1).getEffects(player,player)
            elif st.getInt("transform") == 2 :
                SkillTable.getInstance().getInfo(5983,1).getEffects(player,player)
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
            if player.getLevel() < 75 :
                htmltext = "32557-00.htm"
            else :
                htmltext = "32557-01.htm"
        else :
            if st.getQuestItemsCount(Mark) >= 5 :
                htmltext = "32557-14.htm"
                if transform == 1 :
                    st.giveItems(13553,1)
                elif transform == 2 :
                    st.giveItems(13554,1)
                st.giveItems(13857,1)
                st.addExpAndSp(25160,2525)
                st.unset("transform")
                st.unset("cond")
                st.exitQuest(False)
                st.playSound("ItemSound.quest_finish")
            elif not transform :
                htmltext = "32557-07.htm"
            else :
                htmltext = "32557-11.htm"
        return htmltext


    def onKill(self,npc,player,isPet):
        st = player.getQuestState(qn)
        if not st : return
        if st.getState() != State.STARTED : return

        count=st.getQuestItemsCount(Mark)
        if st.getInt("cond")==1 and count < 5 :
            st.giveItems(Mark,1)
            if count == 4 :
                st.playSound("ItemSound.quest_middle")
                st.set("cond","2")
            else :
                st.playSound("ItemSound.quest_itemget")
        return

QUEST       = Quest(10273,qn,"Good Day To Fly")

QUEST.addStartNpc(Lekon)
QUEST.addTalkId(Lekon)
QUEST.addKillId(22614)
QUEST.addKillId(22615)