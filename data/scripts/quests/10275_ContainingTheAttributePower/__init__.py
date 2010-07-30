# Made by Kerberos v1.0 on 2009/05/03
# this script is part of the Official L2J Datapack Project.
# Visit http://www.l2jdp.com/forum for more details.

import sys

from com.l2jserver.gameserver.datatables import SkillTable
from com.l2jserver.gameserver.model						import Elementals
from com.l2jserver.gameserver.model.quest        			import State
from com.l2jserver.gameserver.model.quest        			import QuestState
from com.l2jserver.gameserver.model.quest.jython 			import QuestJython as JQuest

qn = "10275_ContainingTheAttributePower"

#NPCs
Holly = 30839
Weber = 31307
Yin = 32325
Yang = 32326
Water = 27380
Air = 27381

#items
YinSword = 13845
YangSword = 13881
SoulPieceWater = 13861
SoulPieceAir = 13862

class Quest (JQuest) :
    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)
        self.questItemIds = [YinSword, YangSword, SoulPieceWater, SoulPieceAir]

    def onAdvEvent (self,event,npc, player) :
        htmltext = event
        st = player.getQuestState(qn)
        if not st : return
        if event in ["30839-02.htm","31307-02.htm"] :
            st.set("cond","1")
            st.setState(State.STARTED)
            st.playSound("ItemSound.quest_accept")
        elif event == "30839-05.htm" :
            st.set("cond","2")
            st.playSound("ItemSound.quest_middle")
        elif event == "31307-05.htm" :
            st.set("cond","7")
            st.playSound("ItemSound.quest_middle")
        elif event == "32325-03.htm" :
            st.set("cond","3")
            st.giveItems(YinSword,1,Elementals.FIRE,10)
            st.playSound("ItemSound.quest_middle")
        elif event == "32326-03.htm" :
            st.set("cond","8")
            st.giveItems(YangSword,1,Elementals.EARTH,10)
            st.playSound("ItemSound.quest_middle")
        elif event == "32325-06.htm" :
            if st.getQuestItemsCount(YinSword):
               st.takeItems(YinSword,1)
               htmltext = "32325-07.htm"
            st.giveItems(YinSword,1,Elementals.FIRE,10)
        elif event == "32326-06.htm" :
            if st.getQuestItemsCount(YangSword):
               st.takeItems(YangSword,1)
               htmltext = "32326-07.htm"
            st.giveItems(YangSword,1,Elementals.EARTH,10)
        elif event == "32325-09.htm" :
            st.set("cond","5")
            SkillTable.getInstance().getInfo(2635,1).getEffects(player,player)
            st.giveItems(YinSword,1,Elementals.FIRE,10)
            st.playSound("ItemSound.quest_middle")
        elif event == "32326-09.htm" :
            st.set("cond","10")
            SkillTable.getInstance().getInfo(2636,1).getEffects(player,player)
            st.giveItems(YangSword,1,Elementals.EARTH,10)
            st.playSound("ItemSound.quest_middle")
        elif event.isdigit() :
            st.giveItems(10520+int(event),2)
            st.addExpAndSp(202160,20375)
            st.unset("cond")
            st.exitQuest(False)
            st.playSound("ItemSound.quest_finish")
            htmltext = str(npc.getNpcId())+"-1"+event+".htm"
        return htmltext

    def onTalk (self,npc,player):
        htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
        st = player.getQuestState(qn)
        if not st : return htmltext
        npcId = npc.getNpcId()
        id = st.getState()
        cond = st.getInt("cond")
        if id == State.COMPLETED :
            if npcId == Holly :
                htmltext = "30839-0a.htm"
            elif npcId == Weber:
                htmltext = "31307-0a.htm"
        elif id == State.CREATED :
            if player.getLevel() >= 76 :
                if npcId == Holly :
                    htmltext = "30839-01.htm"
                else:
                    htmltext = "31307-01.htm"
            else:
                if npcId == Holly :
                    htmltext = "30839-00.htm"
                else:
                    htmltext = "31307-00.htm"
        else :
            if npcId == Holly :
                if cond == 1:
                   htmltext = "30839-03.htm"
                elif cond == 2:
                   htmltext = "30839-05.htm"
            elif npcId == Weber:
                if cond == 1:
                   htmltext = "31307-03.htm"
                elif cond == 7:
                   htmltext = "31307-05.htm"
            elif npcId == Yin:
                if cond == 2:
                   htmltext = "32325-01.htm"
                elif cond in [3,5]:
                   htmltext = "32325-04.htm"
                elif cond == 4:
                   htmltext = "32325-08.htm"
                   st.takeItems(YinSword,1)
                   st.takeItems(SoulPieceWater,-1)
                elif cond == 6:
                   htmltext = "32325-10.htm"
            elif npcId == Yang:
                if cond == 7:
                   htmltext = "32326-01.htm"
                elif cond in [8,10]:
                   htmltext = "32326-04.htm"
                elif cond == 9:
                   htmltext = "32326-08.htm"
                   st.takeItems(YangSword,1)
                   st.takeItems(SoulPieceAir,-1)
                elif cond == 11:
                   htmltext = "32326-10.htm"
        return htmltext

    def onKill(self,npc,player,isPet):
        st = player.getQuestState(qn)
        if not st or isPet: return
        if st.getState() != State.STARTED : return
        npcId = npc.getNpcId()
        if npcId == Air :
            if st.getItemEquipped(9) == YangSword and st.getInt("cond") in [8,10] and st.getQuestItemsCount(SoulPieceAir) < 6 and st.getRandom(100) < 30:
                st.giveItems(SoulPieceAir,1)
                if st.getQuestItemsCount(SoulPieceAir) >= 6 :
                    st.set("cond",str(st.getInt("cond")+1))
                    st.playSound("ItemSound.quest_middle")
                else:
                    st.playSound("ItemSound.quest_itemget")
        elif npcId == Water :
            if st.getItemEquipped(9) == YinSword and st.getInt("cond") in [3,5] and st.getQuestItemsCount(SoulPieceWater) < 6 and st.getRandom(100) < 30:
                st.giveItems(SoulPieceWater,1)
                if st.getQuestItemsCount(SoulPieceWater) >= 6 :
                    st.set("cond",str(st.getInt("cond")+1))
                    st.playSound("ItemSound.quest_middle")
                else:
                    st.playSound("ItemSound.quest_itemget")
        return

QUEST       = Quest(10275,qn,"Containing the Attribute Power")

QUEST.addStartNpc(Holly)
QUEST.addStartNpc(Weber)
QUEST.addTalkId(Holly)
QUEST.addTalkId(Weber)
QUEST.addTalkId(Yin)
QUEST.addTalkId(Yang)
QUEST.addKillId(Air)
QUEST.addKillId(Water)