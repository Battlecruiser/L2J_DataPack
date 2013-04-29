# Made by Kerberos v1.0 on 2009/21/02
# this script is part of the Official L2J Datapack Project.
# Visit http://www.l2jdp.com/forum for more details.

import sys
import time

from com.l2jserver.gameserver.instancemanager import QuestManager
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest import Quest as JQuest
from com.l2jserver.gameserver.network.serverpackets	import NpcSay

qn = "185_Nikolas_Cooperation_Consideration"

#NPCs
Nikola = 30621
Lorain = 30673
Device = 32366
Alarm = 32367

#Items
Certificate = 10362
Metal = 10363
BrokenMetal = 10364
NicolasMap = 10365

class Quest (JQuest) :
    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)
        self.questItemIds = [NicolasMap,BrokenMetal,Metal]

    def onAdvEvent (self,event,npc, player) :
        st = player.getQuestState(qn)
        if not st: return
        htmltext = event
        if event == "30621-01.htm":
            if player.getLevel() < 40 :
                htmltext = "30621-00.htm"
        elif event == "30621-04.htm" :
            st.playSound("ItemSound.quest_accept")
            st.set("cond","1")
            st.giveItems(NicolasMap,1)
        elif event == "30673-03.htm" :
            st.playSound("ItemSound.quest_middle")
            st.set("cond","2")
            st.takeItems(NicolasMap,-1)
        elif event == "30673-05.htm" :
            st.playSound("ItemSound.quest_middle")
            st.set("cond","3")
        elif event == "30673-09.htm" :
            if st.getQuestItemsCount(BrokenMetal) :
                htmltext = "30673-10.htm"
            elif st.getQuestItemsCount(Metal) :
                st.giveItems(Certificate,1)
            if player.getLevel() < 50 :
               st.addExpAndSp(203717,14032)
            st.giveItems(57,72527)
            st.exitQuest(False)
            st.playSound("ItemSound.quest_finish")
        elif event == "32366-02.htm" :
            alarm = st.addSpawn(32367,16491,113563,-9064)
            st.set("step","1")
            st.playSound("ItemSound3.sys_siren")
            self.startQuestTimer("1",60000, alarm, player)
            time.sleep(1)
            player.sendPacket(NpcSay(alarm.getObjectId(), 0, alarm.getNpcId(), "Intruder Alert! The alarm will self-destruct in 2 minutes."))
        elif event == "32366-05.htm" :
            st.unset("step")
            st.playSound("ItemSound.quest_middle")
            st.set("cond","5")
            st.giveItems(BrokenMetal,1)
        elif event == "32366-06.htm" :
            st.unset("step")
            st.playSound("ItemSound.quest_middle")
            st.set("cond","4")
            st.giveItems(Metal,1)
        elif event == "32367-02.htm" :
            st.set("pass","0")
        elif event[0:7] == "correct" :
            st.set("pass",str(st.getInt("pass")+1))
            htmltext = event[8:]
            if htmltext == "32367-07.htm":
                if st.getInt("pass") == 4 :
                    st.set("step","3")
                    self.cancelQuestTimer("1",npc,player)
                    self.cancelQuestTimer("2",npc,player)
                    self.cancelQuestTimer("3",npc,player)
                    self.cancelQuestTimer("4",npc,player)
                    st.unset("pass")
                    npc.deleteMe()
                else :
                    htmltext == "32367-06.htm"
        elif event == "1" :
            player.sendPacket(NpcSay(npc.getObjectId(), 0, npc.getNpcId(), "The alarm will self-destruct in 60 seconds. Enter passcode to override."))
            self.startQuestTimer("2",30000, npc, player)
            return
        elif event == "2" :
            player.sendPacket(NpcSay(npc.getObjectId(), 0, npc.getNpcId(), "The alarm will self-destruct in 30 seconds. Enter passcode to override."))
            self.startQuestTimer("3",20000, npc, player)
            return
        elif event == "3" :
            player.sendPacket(NpcSay(npc.getObjectId(), 0, npc.getNpcId(), "The alarm will self-destruct in 10 seconds. Enter passcode to override."))
            self.startQuestTimer("4",10000, npc, player)
            return
        elif event == "4" :
            player.sendPacket(NpcSay(npc.getObjectId(), 0, npc.getNpcId(), "Recorder crushed."))
            npc.deleteMe()
            st.set("step","2")
            return
        return htmltext

    def onTalk (self,npc,player):
        htmltext = Quest.getNoQuestMsg(player)
        st = player.getQuestState(qn)
        if not st : return htmltext
        npcId = npc.getNpcId()
        id = st.getState()
        cond = st.getInt("cond")
        if id == State.COMPLETED:
            htmltext = Quest.getAlreadyCompletedMsg(player)
        elif id == State.STARTED:
            if npcId == Nikola :
                if not cond :
                    if player.getLevel() < 40 :
                        htmltext = "30621-00.htm"
                    else :
                        htmltext = "30621-01.htm"
                elif cond == 1 :
                    htmltext = "30621-05.htm"
            elif npcId == Lorain:
                if cond == 1 :
                    htmltext = "30673-01.htm"
                elif cond == 2 :
                    htmltext = "30673-04.htm"
                elif cond == 3 :
                    htmltext = "30673-06.htm"
                elif cond in [4,5] :
                    htmltext = "30673-07.htm"
            elif npcId == Device:
                step = st.getInt("step")
                if cond == 3 :
                    if not step:
                        htmltext = "32366-01.htm"
                    elif step == 1 :
                        htmltext = "32366-02.htm"
                    elif step == 2 :
                        htmltext = "32366-04.htm"
                    elif step == 3 :
                        htmltext = "32366-03.htm"
        return htmltext

    def onFirstTalk (self,npc,player):
       st = player.getQuestState(qn)
       q2 = QuestManager.getInstance().getQuest("184_Nikolas_Cooperation_Contract")
       if st:
           player.setLastQuestNpcObject(npc.getObjectId())
           return "32367-01.htm"
       elif q2:
           player.setLastQuestNpcObject(npc.getObjectId())
           q2.notifyEvent("32367-01.htm",npc,player)
       return None

QUEST       = Quest(185,qn,"Nikola's Cooperation - Consideration")

QUEST.addTalkId(Nikola)
QUEST.addTalkId(Lorain)
QUEST.addTalkId(Device)
QUEST.addTalkId(Alarm)
QUEST.addFirstTalkId(Alarm)
QUEST.addStartNpc(Alarm)