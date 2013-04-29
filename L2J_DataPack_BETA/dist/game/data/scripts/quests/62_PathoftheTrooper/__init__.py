# Made by Emperorc
# Update 17-01-08 by t0rm3nt0r
import sys
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest import Quest as JQuest
from com.l2jserver.gameserver.network.serverpackets      import SocialAction

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
           st.setState(State.STARTED)
        elif event == "32194-02.htm" :
            st.set("cond","2")
        return htmltext

    def onTalk (self,npc,player):
        htmltext = Quest.getNoQuestMsg(player)
        st = player.getQuestState(qn)
        if not st : return htmltext
        npcId = npc.getNpcId()
        id = st.getState()
        cond = st.getInt("cond")
        if id == State.COMPLETED :
            htmltext = "32197-07.htm"
        elif npcId == Gwain :
            if player.getLevel() < 18 :
                htmltext = "32197-00a.htm"
                st.exitQuest(1)
            elif player.getClassId().getId() != 123 :
                htmltext = "32197-00b.htm"
                st.exitQuest(1)
            elif id == State.CREATED :
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
                    isFinished = st.getGlobalQuestVar("1ClassQuestFinished")
                    if isFinished == "" :
                      st.addExpAndSp(8064,2368)
                      st.giveItems(57, 163800)
                      st.giveItems(Gwain_Rec,1)
                    st.exitQuest(False)
                    st.saveGlobalQuestVar("1ClassQuestFinished","1")
                    st.playSound("ItemSound.quest_finish")
                    player.sendPacket(SocialAction(player.getObjectId(),3))
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
        if st.getState() != State.STARTED : return
        npcId = npc.getNpcId()
        cond = st.getInt("cond")
        if npcId == Warrior :
            if st.getQuestItemsCount(Head) < 5 and cond == 2 :
                st.giveItems(Head,1)
                if st.getQuestItemsCount(Head) == 5 :
                    st.playSound("ItemSound.quest_middle")
                else:
                    st.playSound("ItemSound.quest_itemget")
        elif npcId == Spider :
            if st.getQuestItemsCount(Leg) < 10 and cond == 3 :
                st.giveItems(Leg,1)
                if st.getQuestItemsCount(Leg) == 10 :
                    st.playSound("ItemSound.quest_middle")
                else:
                    st.playSound("ItemSound.quest_itemget")
        elif npcId == Tumran :
            if not st.getQuestItemsCount(Heart) and cond == 5 :
                st.giveItems(Heart,1)
                st.playSound("ItemSound.quest_middle")
        return

QUEST       = Quest(62,qn,"Path of the Trooper")

QUEST.addStartNpc(Gwain)

QUEST.addTalkId(Gwain)
QUEST.addTalkId(Shubain)

QUEST.addKillId(Warrior)
QUEST.addKillId(Spider)
QUEST.addKillId(Tumran)