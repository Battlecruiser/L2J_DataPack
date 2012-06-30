# Made by Emperorc
import sys
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest

qn = "64_CertifiedBerserker"

#NPCs
Orkurus = 32207
Tenain = 32215
Gort = 32252
Entien = 32200
Harkilgamed = 32253

#Mobs
Brekas = range(20267,20272)
Scavenger = 20551
Seeker = 20202
Drone = 20234
Emissary = 27323

#Items
Diamond = 7562
Head,Plate,Rep_E,Rep_N,Hark_Let,Tenain_Rec,Orkurus_Rec = range(9754,9761)

class Quest (JQuest) :
    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)
        self.questItemIds = range(9754,9760)

    def onEvent (self,event,st) :
        htmltext = event
        player = st.getPlayer()
        if event == "32207-02.htm" :
            st.set("cond","1")
            st.setState(State.STARTED)
            #st.giveItems(Diamond,48)
        elif event == "32215-02.htm" :
            st.set("cond","2")
        elif event == "32252-02.htm" :
            st.set("cond","5")
        elif event == "32215-08.htm" :
            st.takeItems(Plate,-1)
        elif event == "32215-10.htm" :
            st.set("cond","8")
        elif event == "Despawn Harkilgamed" :
            st.set("spawned","0")
        elif event == "32236-02.htm" :
            st.set("cond","13")
            st.giveItems(Hark_Let,1)
        elif event == "32215-15.htm" :
            st.takeItems(Hark_Let,-1)
            st.giveItems(Tenain_Rec,1)
            st.set("cond","14")
        if event == "32207-05.htm" :
            st.takeItems(Tenain_Rec,-1)
            st.exitQuest(False)
            st.playSound("ItemSound.quest_finish")
            st.addExpAndSp(349006,23948)
            st.giveItems(57,63104)
            st.giveItems(Orkurus_Rec,1)
            st.unset("cond")
            st.unset("kills")
            st.unset("spawned")
        return htmltext

    def onTalk (self,npc,player):
        htmltext = Quest.getNoQuestMsg(player)
        st = player.getQuestState(qn)
        if not st : return htmltext
        npcId = npc.getNpcId()
        id = st.getState()
        cond = st.getInt("cond")
        if id == State.COMPLETED :
            htmltext = Quest.getAlreadyCompletedMsg(player)

        elif npcId == Orkurus :
            if player.getClassId().getId() != 125 or player.getLevel() < 39:
                htmltext = "<html><body>Only Troopers are allowed to take this quest! Go away before I get angry!<br>You must be level 39 or higher to undertake this quest.</body></html>"
                st.exitQuest(1)
            elif id == State.CREATED :
                htmltext = "32207-01.htm"
            elif cond == 1 :
                htmltext = "32207-03.htm"
            elif cond == 14 :
                htmltext = "32207-04.htm"
        elif npcId == Tenain :
            if cond == 1 :
                htmltext = "32215-01.htm"
            elif cond == 2 :
                htmltext = "32215-03.htm"
            elif cond == 3 :
                htmltext = "32215-04.htm"
                st.takeItems(Head,-1)
                st.set("cond","4")
            elif cond == 4 :
                htmltext = "32215-05.htm"
            elif cond == 7 :
                htmltext = "32215-06.htm"
            elif cond == 8 :
                htmltext = "32215-11.htm"
            elif cond == 11 :
                htmltext = "32215-12.htm"
                st.set("cond","12")
                st.set("kills","0")
                st.set("spawned","0")
            elif cond == 12 :
                htmltext = "32215-13.htm"
            elif cond == 13 :
                htmltext = "32215-14.htm"
        elif npcId == Gort :
            if cond == 4 :
                htmltext = "32252-01.htm"
            if cond == 5 :
                htmltext = "32252-03.htm"
            if cond == 6 :
                htmltext = "32252-04.htm"
                st.set("cond","7")
            elif cond == 7 :
                htmltext = "32252-05.htm"
        elif npcId == Entien :
            if cond == 8 :
                htmltext = "32200-01.htm"
                st.set("cond","9")
            elif cond == 9 :
                htmltext = "32200-02.htm"
            elif cond == 10 :
                htmltext = "32200-03.htm"
                st.takeItems(Rep_E,-1)
                st.takeItems(Rep_N,-1)
                st.set("cond","11")
            elif cond == 11 :
                htmltext = "32200-04.htm"
        elif npcId == Harkilgamed :
            if cond == 12 :
                htmltext = "32236-01.htm"
            elif cond == 13 :
                htmltext = "32236-03.htm"
        return htmltext

    def onKill(self,npc,player,isPet):
        st = player.getQuestState(qn)
        if not st : return
        if st.getState() != State.STARTED : return
        npcId = npc.getNpcId()
        cond = st.getInt("cond")
        if npcId in Brekas :
            if st.getQuestItemsCount(Head) < 20 and cond == 2 :
                st.giveItems(Head,1)
                if st.getQuestItemsCount(Head) == 20 :
                    st.playSound("ItemSound.quest_middle")
                    st.set("cond","3")
                else:
                    st.playSound("ItemSound.quest_itemget")
        elif npcId == Scavenger :
            if not st.getQuestItemsCount(Plate) and self.getRandom(20) == 1 and cond == 5 :
                st.giveItems(Plate,1)
                st.playSound("ItemSound.quest_middle")
                st.set("cond","6")
        elif npcId == Seeker :
            if not st.getQuestItemsCount(Rep_E) and self.getRandom(30) == 1 and cond == 9 :
                st.giveItems(Rep_E,1)
                st.playSound("ItemSound.quest_middle")
                if st.getQuestItemsCount(Rep_N) :
                    st.set("cond","10")
        elif npcId == Drone :
            if not st.getQuestItemsCount(Rep_N) and self.getRandom(30) == 1 and cond == 9 :
                st.giveItems(Rep_N,1)
                st.playSound("ItemSound.quest_middle")
                if st.getQuestItemsCount(Rep_E) :
                    st.set("cond","10")
        elif npcId == Emissary :
            if cond == 12 and not st.getInt("spawned") :
                if st.getInt("kills") < 5 :
                    st.set("kills",str(st.getInt("kills")+1))
                else :
                    st.addSpawn(Harkilgamed,120000)
                    st.set("spawned","1")
                    st.set("kills","0")
                    st.startQuestTimer("Despawn Harkilgamed",120000)
        return

QUEST       = Quest(64,qn,"Certified Berserker")

QUEST.addStartNpc(Orkurus)

QUEST.addTalkId(Orkurus)
QUEST.addTalkId(Tenain)
QUEST.addTalkId(Gort)
QUEST.addTalkId(Entien)
QUEST.addTalkId(Harkilgamed)

for mob in Brekas :
    QUEST.addKillId(mob)
QUEST.addKillId(Scavenger)
QUEST.addKillId(Seeker)
QUEST.addKillId(Drone)
QUEST.addKillId(Emissary)