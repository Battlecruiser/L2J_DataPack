# Made by Emperorc
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "62_PathoftheTrooper"

#NPCs
Shubain = 32194
Gwain = 32197

#Mobs
Warrior = 20014
Spider = 20038
Tumran = 20062

#Items
Head,Leg,Heart,Shubain_Rec,Gwain_Rec = range(9749,9754)

class Quest (JQuest) :
    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)
        self.questItemIds = range(9749,9753)

    def onEvent (self,event,st) :
        htmltext = event
        player = st.getPlayer()
        if event == "32197-02.htm" :
           st.set("cond","1")
           st.setState(STARTED)
        elif event == "32194-02.htm" :
            st.set("cond","2")
        return htmltext

    def onTalk (self,npc,player):
        htmltext = "<html><body>You are either not carrying out your quest or don't meet the criteria.</body></html>"
        st = player.getQuestState(qn)
        if not st : return htmltext
        npcId = npc.getNpcId()
        id = st.getState()
        cond = st.getInt("cond")
        if id == COMPLETED :
            htmltext = "<html><body>This quest has already been completed.</body></html>"
        elif npcId == Gwain :
            if player.getClassId().getId() != 123 or player.getLevel() < 19:
                htmltext = "32197-00.htm"
                st.exitQuest(1)
            elif id == CREATED :
                htmltext = "32197-01.htm"
            elif cond < 4 :
                htmltext = "32197-03.htm"
            elif cond == 4 :
                htmltext = "32197-04.htm"
                st.takeItems(Shubain_Rec,-1)
                st.set("cond","5")
            elif cond == 5 :
                if not st.getQuestItemsCount(Heart) :
                    htmltext = "32197-05.htm"
                else :
                    st.takeItems(Heart,-1)
                    st.giveItems(Gwain_Rec,1)
                    st.addExpAndSp(3200,4736)
                    st.setState(COMPLETED)
                    st.playSound("ItemSound.quest_finish")
                    st.unset("cond")
                    htmltext = "32197-06.htm"
        elif npcId == Shubain :
            if cond == 1 :
                htmltext = "32194-01.htm"
            elif cond == 2 :
                if st.getQuestItemsCount(Head) < 5 :
                    htmltext = "32194-03.htm"
                else :
                    htmltext = "32194-04.htm"
                    st.takeItems(Head,-1)
                    st.set("cond","3")
            elif cond == 3 :
                if st.getQuestItemsCount(Leg) < 10 :
                    htmltext = "32194-05.htm"
                else :
                    htmltext = "32194-06.htm"
                    st.takeItems(Leg,-1)
                    st.giveItems(Shubain_Rec,1)
                    st.set("cond","4")
            elif cond > 3 :
                htmltext = "32194-07.htm"
        return htmltext

    def onKill(self,npc,player,isPet):
        st = player.getQuestState(qn)
        if not st : return
        if st.getState() != STARTED : return
        npcId = npc.getNpcId()
        cond = st.getInt("cond")
        if npcId == Warrior :
            if st.getQuestItemsCount(Head) < 5 and st.getRandom(10) < 4 and cond == 2 :
                st.giveItems(Head,1)
                if st.getQuestItemsCount(Head) == 5 :
                    st.playSound("ItemSound.quest_middle")
                else:
                    st.playSound("ItemSound.quest_itemget")
        elif npcId == Spider :
            if st.getQuestItemsCount(Leg) < 10 and st.getRandom(10) < 6 and cond == 3 :
                st.giveItems(Leg,1)
                if st.getQuestItemsCount(Leg) == 10 :
                    st.playSound("ItemSound.quest_middle")
                else:
                    st.playSound("ItemSound.quest_itemget")
        elif npcId == Tumran :
            if not st.getQuestItemsCount(Heart) and st.getRandom(10) < 3 and cond == 4 :
                st.giveItems(Heart,1)
                st.playSound("ItemSound.quest_middle")
        return

QUEST       = Quest(62,qn,"Path of the Trooper")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(Gwain)

QUEST.addTalkId(Gwain)
QUEST.addTalkId(Shubain)

QUEST.addKillId(Warrior)
QUEST.addKillId(Spider)
QUEST.addKillId(Tumran)

STARTED.addQuestDrop(Gwain,Head,1)
STARTED.addQuestDrop(Gwain,Leg,1)
STARTED.addQuestDrop(Gwain,Heart,1)
STARTED.addQuestDrop(Gwain,Shubain_Rec,1)