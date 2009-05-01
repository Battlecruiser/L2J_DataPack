# Made by Emperorc
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "66_CertifiedArbalester"

#NPCs
Rindy = 32201
Clayton = 30464
Poitan = 30458
Holvas = 30058
Meldina = 32214
Selsia = 32220
Gaius = 30171
Gauen = 30717
Kaiena = 30720

#Mobs
Floran = range(21102,21108) + [20781]
EG = range (20199,20203) + [20083,20144]
Grandis = 20554
Gargoyle = 20563
Timaks = [20584,20585]
Lady = 27336

#Items
Diamond = 7562
En_Crys,En_Crys_Core,Page,Page_Comp,Mark_Train,Order_Frag,Order_Comp,Talisman,Research,Mark = range(9773,9783)

class Quest (JQuest) :
    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)
        self.questItemIds = range(9773,9782)

    def onEvent (self,event,st) :
        htmltext = event
        player = st.getPlayer()
        if event == "32201-02.htm" :
            st.set("cond","1")
            st.setState(State.STARTED)
            #st.giveItems(Diamond,64)
            st.playSound("ItemSound.quest_accept") 
        elif event == "32201-03.htm" :
            st.set("cond","2")
            st.playSound("ItemSound.quest_middle") 
        elif event == "30464-05.htm" :
            st.set("cond","3")
            st.playSound("ItemSound.quest_middle") 
        elif event == "30464-08.htm" :
            st.takeItems(En_Crys,-1)
        elif event == "30464-09.htm" :
            st.giveItems(En_Crys_Core,1)
            st.set("cond","5")
            st.playSound("ItemSound.quest_middle") 
        elif event == "30458-03.htm" :
            st.takeItems(En_Crys_Core,-1)
        elif event == "30458-07.htm" :
            st.set("cond","6")
            st.playSound("ItemSound.quest_middle") 
        elif event == "30058-04.htm" :
            st.set("cond","7")
            st.playSound("ItemSound.quest_middle") 
        elif event == "30058-07.htm" :
            st.set("cond","9")
            st.playSound("ItemSound.quest_middle") 
            st.giveItems(Page_Comp,1)
        elif event == "32214-03.htm" :
            st.set("cond","10")
            st.playSound("ItemSound.quest_middle") 
            st.takeItems(Page_Comp,-1)
            st.giveItems(Mark_Train,1)
        elif event == "32220-11.htm" :
            st.set("cond","11")
            st.playSound("ItemSound.quest_middle") 
        elif event == "30171-02.htm" :
            st.takeItems(Order_Comp,-1)
        elif event == "30171-05.htm" :
            st.set("cond","14")
            st.playSound("ItemSound.quest_middle") 
        elif event == "30717-02.htm" :
            st.takeItems(Talisman,-1)
        elif event == "30717-07.htm" :
            st.set("cond","17")
            st.playSound("ItemSound.quest_middle") 
        elif event == "30720-03.htm" :
            st.set("cond","18")
            st.playSound("ItemSound.quest_middle") 
        elif event == "32220-19.htm" :
            st.set("cond","19")
            st.playSound("ItemSound.quest_middle") 
        elif event == "Despawn Crimson Lady" :
            st.set("spawned","0")
        return htmltext

    def onTalk (self,npc,player):
        htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
        st = player.getQuestState(qn)
        if not st : return htmltext
        npcId = npc.getNpcId()
        id = st.getState()
        cond = st.getInt("cond")
        if id == State.COMPLETED :
            htmltext = "<html><body>This quest has already been completed.</body></html>"

        elif npcId == Rindy :
            if player.getClassId().getId() != 126 or player.getLevel() < 39:
                htmltext = "<html><body>Only Warders of level 39 and above are allowed to take this quest! Go away before I get angry!</body></html>"
                st.exitQuest(1)
            elif id == State.CREATED :
                htmltext = "32201-01.htm"
            elif cond == 1 :
                htmltext = "32201-03.htm"
            elif cond == 2 :
                htmltext = "32201-04.htm"
        elif npcId == Clayton :
            if cond == 2 :
                htmltext = "30464-01.htm"
            elif cond == 3 :
                htmltext = "30464-06.htm"
            elif cond == 4 :
                htmltext = "30464-07.htm"
            elif cond == 5 :
                htmltet = "30464-09.htm"
        elif npcId == Poitan :
            if cond == 5 :
                htmltext = "30458-01.htm"
            elif cond == 6 :
                htmltext = "30458-08.htm"
        elif npcId == Holvas :
            if cond == 6 :
                htmltext = "30058-01.htm"
            elif cond == 7 :
                htmltext = "30058-05.htm"
            elif cond == 8 :
                htmltext = "30058-06.htm"
                st.takeItems(Page,-1)
            elif cond == 9 :
                htmltext = "30058-08.htm"
        elif npcId == Meldina :
            if cond == 9 :
                htmltext = "32214-01.htm"
            elif cond == 10 :
                htmltext = "32214-04.htm"
        elif npcId == Selsia :
            if cond == 10 :
                htmltext = "32220-01.htm" #3220-07.htm,3220-08.htm,3220-10.htm are completely custom. Need to find 
                                          #out what she actually says and if it is the same result as 3220-09.htm.
            elif cond == 11 :
                htmltext = "32220-11.htm"
            elif cond == 18 :
                htmltext = "32220-12.htm"
            elif cond == 19 :
                htmltext = "32220-19.htm"
            elif cond == 20 :
                htmltext = "32220-20.htm"
                st.takeItems(Research,-1)
                st.giveItems(Mark,1)
                st.exitQuest(False)
                st.playSound("ItemSound.quest_finish")
                st.addExpAndSp(108974,12357)
                st.unset("cond")
        elif npcId == Gaius :
            if cond == 13 :
                htmltext = "30171-01.htm"
            elif cond == 14 :
                htmltext = "30171-06.htm"
            elif cond == 16 :
                htmltext = "30171-07.htm"
        elif npcId == Gauen :
            if cond == 16 :
                htmltext = "30717-01.htm"
            elif cond == 17 :
                htmltext = "30717-08.htm"
        elif npcId == Kaiena :
            if cond == 17 :
                htmltext = "30720-01.htm"
            elif cond == 18 :
                htmltext = "30720-04.htm"
        return htmltext

    def onKill(self,npc,player,isPet):
        st = player.getQuestState(qn)
        if not st : return
        if st.getState() != State.STARTED : return
        npcId = npc.getNpcId()
        cond = st.getInt("cond")
        if npcId in Floran :
            if st.getQuestItemsCount(En_Crys) < 30 and cond == 3 :
                st.giveItems(En_Crys,1)
                if st.getQuestItemsCount(En_Crys) == 30 :
                    st.playSound("ItemSound.quest_middle")
                    st.set("cond","4")
                else:
                    st.playSound("ItemSound.quest_itemget")
        elif npcId in EG :
            if st.getQuestItemsCount(Page) < 30 and cond == 7 :
                st.giveItems(Page,1)
                if st.getQuestItemsCount(Page) == 30 :
                    st.playSound("ItemSound.quest_middle")
                    st.set("cond","8")
                else:
                    st.playSound("ItemSound.quest_itemget")
        elif npcId == Grandis :
            count = st.getQuestItemsCount(Order_Frag)
            if count < 10 and (cond == 11 or cond == 12):
                if count == 9 :
                    st.playSound("ItemSound.quest_middle")
                    st.takeItems(Order_Frag,-1)
                    st.giveItems(Order_Comp,1)
                    st.set("cond","13")
                else :
                    st.giveItems(Order_Frag,1)
                    st.playSound("ItemSound.quest_itemget")
                    if count == 0 :
                        st.set("cond","12")
        elif npcId == Gargoyle :
            count = st.getQuestItemsCount(Talisman)
            if count < 10 and (cond == 14 or cond == 15):
                st.giveItems(Talisman,1)
                if count == 9 :
                    st.playSound("ItemSound.quest_middle")
                    st.set("cond","16")
                else :
                    st.playSound("ItemSound.quest_itemget")
                    if count == 0 :
                        st.set("cond","15")
        elif npcId in Timaks :
            if st.getRandom(40) < 1 and cond == 19 and not st.getInt("spawned") :
                st.addSpawn(Lady,180000)
                st.set("spawned","1")
                st.startQuestTimer("Despawn Crimson Lady",180000)
        elif npcId == Lady :
            if cond == 19 and not st.getQuestItemsCount(Research) :
                st.giveItems(Research,1)
                st.set("cond","20")
                st.unset("spawned")
        return

QUEST       = Quest(66,qn,"Certified Arbalester")

QUEST.addStartNpc(Rindy)

QUEST.addTalkId(Rindy)
QUEST.addTalkId(Clayton)
QUEST.addTalkId(Poitan)
QUEST.addTalkId(Holvas)
QUEST.addTalkId(Meldina)
QUEST.addTalkId(Selsia)
QUEST.addTalkId(Gaius)
QUEST.addTalkId(Gauen)
QUEST.addTalkId(Kaiena)

for mob in Floran + EG + Timaks : 
    QUEST.addKillId(mob)
QUEST.addKillId(Grandis)
QUEST.addKillId(Gargoyle)
QUEST.addKillId(Lady)