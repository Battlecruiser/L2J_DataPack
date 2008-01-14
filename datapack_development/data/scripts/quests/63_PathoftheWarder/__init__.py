# Made by Emperorc
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "63_PathoftheWarder"

#NPCs
Sione = 32195
Gobie = 32198
Bathis = 30332
Tobias = 30297

#Mobs
Novice = 20782
Patrol = 20053
Lizard = 20919
Tak = 27337

#Items
Order,Chart,Gobie_Rep,Hum_Let,Hum_Rep,DE_Let,DE_Rep,Sione_Rep,Soul_E,Soul_C,Eval = range(9762,9773)

class Quest (JQuest) :
    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)
        self.questItemIds = range(9762,9772)

    def onEvent (self,event,st) :
        htmltext = event
        player = st.getPlayer()
        if event == "32195-02.htm" :
            st.set("cond","1")
            st.playSound("ItemSound.quest_accept")
            st.setState(State.STARTED)
        elif event == "32195-04.htm" :
            st.set("cond","2")
            st.playSound("ItemSound.quest_middle")
        elif event == "32198-02.htm" :
            st.set("cond","5")
            st.playSound("ItemSound.quest_middle")
            st.takeItems(Gobie_Rep,-1)
            st.giveItems(Hum_Let,1)
        elif event == "30332-01.htm" :
            st.giveItems(Hum_Rep,1)
            st.takeItems(Hum_Let,-1)
        elif event == "30332-03.htm" :
            st.set("cond","6")
            st.playSound("ItemSound.quest_middle")
        elif event == "32198-06.htm" :
            st.giveItems(DE_Let,1)
            st.set("cond","7")
            st.playSound("ItemSound.quest_middle")
        elif event == "30297-04.htm" :
            st.giveItems(DE_Rep,1)
            st.set("cond","8")
            st.playSound("ItemSound.quest_middle")
        elif event == "32198-09.htm" :
            st.giveItems(Sione_Rep,1)
            st.set("cond","9")
            st.playSound("ItemSound.quest_middle")
        elif event == "32198-13.htm" :
            st.giveItems(Soul_E,1)
            st.set("cond","11")
            st.playSound("ItemSound.quest_middle")
        return htmltext

    def onTalk (self,npc,player):
        htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
        st = player.getQuestState(qn)
        if not st : return htmltext
        npcId = npc.getNpcId()
        id = st.getState()
        cond = st.getInt("cond")
        if id == State.COMPLETED :
            if npcId == Gobie:
               htmltext = "32198-16.htm"
            else:
               htmltext = "<html><body>This quest has already been completed.</body></html>"

        elif npcId == Sione :
            if player.getClassId().getId() != 124 or player.getLevel() < 19:
                htmltext = "32195-00.htm"
                st.exitQuest(1)
            elif id == State.CREATED :
                htmltext = "32195-01.htm"
            elif cond == 1 :
                htmltext = "32195-03.htm"
            elif cond == 2 :
                htmltext = "32195-05.htm"
            elif cond == 3 :
                htmltext = "32195-06.htm"
                st.set("cond","4")
                st.playSound("ItemSound.quest_middle")
                st.giveItems(Gobie_Rep,1)
                st.takeItems(Order,-1)
                st.takeItems(Chart,-1)
            elif cond >= 4 and cond <9 :
                htmltext = "32195-07.htm"
            elif cond == 9 :
                htmltext = "32195-08.htm"
                st.set("cond","10")
                st.playSound("ItemSound.quest_middle")
                st.takeItems(Sione_Rep,-1)
            elif cond == 10 :
                htmltext = "32195-09.htm"
        elif npcId == Gobie :
            if cond == 4 :
                htmltext = "32198-01.htm"
            elif cond == 5 :
                htmltext = "32198-03.htm"
            elif cond == 6 :
                if st.getQuestItemsCount(Hum_Rep) == 1:
                   st.takeItems(Hum_Rep,-1)
                   htmltext = "32198-04.htm"
                else:
                   htmltext = "32198-05.htm"
            elif cond == 7 :
                htmltext = "32198-07.htm"
            elif cond == 8 :
               if st.getQuestItemsCount(DE_Let) == 1:
                  htmltext = "32198-08.htm"
                  st.takeItems(DE_Rep,-1)
               else:
                  htmltext = "32198-09.htm"
                  st.giveItems(Sione_Rep,1)
                  st.set("cond","9")
                  st.playSound("ItemSound.quest_middle")
            elif cond == 9 :
                htmltext = "32198-10.htm"
            elif cond == 10 :
                htmltext = "32198-11.htm"
            elif cond == 11 :
                htmltext = "32198-14.htm"
            elif cond == 12 :
                htmltext = "32198-15.htm"
                st.takeItems(Soul_C,-1)
                st.giveItems(Eval,1)
                st.playSound("ItemSound.quest_finish")
                st.addExpAndSp(3200,5934)
                st.exitQuest(False)
                st.unset("cond")
        elif npcId == Bathis :
            if cond == 5 :
               if st.getQuestItemsCount(Hum_Rep) == 1:
                  htmltext = "30332-02.htm"
               else:
                  htmltext = "30332-00.htm"
            elif cond > 5 :
                htmltext = "30332-04.htm"
        elif npcId == Tobias :
            if cond == 7 :
               if st.getQuestItemsCount(DE_Let) == 1:
                  htmltext = "30297-01.htm"
                  st.takeItems(DE_Let,-1)
               else:
                  htmltext = "30297-04.htm"
                  st.giveItems(DE_Rep,1)
                  st.set("cond","8")
                  st.playSound("ItemSound.quest_middle")
            elif cond == 8 :
                htmltext = "30297-05.htm"
        return htmltext

    def onKill(self,npc,player,isPet):
        st = player.getQuestState(qn)
        if not st : return
        if st.getState() != State.STARTED : return
        npcId = npc.getNpcId()
        cond = st.getInt("cond")
        if npcId == Novice :
            if st.getQuestItemsCount(Order) < 10  and cond == 2 :
                st.giveItems(Order,1)
                if st.getQuestItemsCount(Order) == 10 :
                    if st.getQuestItemsCount(Chart) == 5 :
                        st.playSound("ItemSound.quest_middle")
                        st.set("cond","3")
                else:
                    st.playSound("ItemSound.quest_itemget")
        elif npcId == Patrol :
            if st.getQuestItemsCount(Chart) < 5 and cond == 2 :
                st.giveItems(Chart,1)
                if st.getQuestItemsCount(Chart) == 5 :
                    if st.getQuestItemsCount(Order) == 10 :
                        st.playSound("ItemSound.quest_middle")
                        st.set("cond","3")
                else:
                    st.playSound("ItemSound.quest_itemget")
        elif npcId == Lizard :
            if not st.getQuestItemsCount(Soul_C) and st.getRandom(10) < 2 and cond == 11 :
                npc = st.addSpawn(Tak,180000)
        elif npcId == Tak :
            if not st.getQuestItemsCount(Soul_C) and cond == 11 :
                st.playSound("ItemSound.quest_middle")
                st.takeItems(Soul_E,-1)
                st.giveItems(Soul_C,1)
                st.set("cond","12")
        return

QUEST       = Quest(63,qn,"Path of the Warder")

QUEST.addStartNpc(Sione)

QUEST.addTalkId(Sione)
QUEST.addTalkId(Gobie)
QUEST.addTalkId(Bathis)
QUEST.addTalkId(Tobias)

QUEST.addKillId(Novice)
QUEST.addKillId(Patrol)
QUEST.addKillId(Tak)
QUEST.addKillId(Lizard)