# Made by Kerberos v1.0 on 2008/02/03
# this script is part of the Official L2J Datapack Project.
# Visit http://www.l2jdp.com/forum/ for more details.
import sys
from com.l2jserver.gameserver.ai import CtrlIntention
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest import Quest as JQuest
from com.l2jserver.gameserver.network.serverpackets import NpcSay
from com.l2jserver.gameserver.network.serverpackets import SocialAction
from com.l2jserver.gameserver.network.serverpackets import ActionFailed

qn = "65_CertifiedSoulBreaker"

#NPCs
Lucas    = 30071
Jacob    = 30073
Harlan   = 30074
Xaber    = 30075
Liam     = 30076
Vesa     = 30123
Zerome   = 30124
Felton   = 30879
Kekropus = 32138
Casca    = 32139
Holst    = 32199
Vitus    = 32213
Meldina  = 32214
Katenar  = 32242
CargoBox = 32243

#Mobs
Wyrm     = 20176
Angel    = 27332

#Items
Diamond  = 7562
Document = 9803
Heart    = 9804
Recommend= 9805
certificate=9806

class Quest (JQuest) :
    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)
        self.isAngelSpawned = 0
        self.isKatenarSpawned = 0
        self.questItemIds = [Document,Heart,Recommend]

    def onEvent (self,event,st) :
        htmltext = event
        player = st.getPlayer()
        if event == "32213-03.htm" :
            st.playSound("ItemSound.quest_accept")
            st.set("cond","1")
            st.setState(State.STARTED)
            #st.giveItems(Diamond,47)
        elif event == "32138-03.htm" :
            st.set("cond","2")
            st.playSound("ItemSound.quest_middle")
        elif event == "32139-02.htm" :
            st.set("cond","3")
            st.playSound("ItemSound.quest_middle")
        elif event == "32139-04.htm" :
            st.set("cond","4")
            st.playSound("ItemSound.quest_middle")
        elif event == "32199-02.htm" :
            st.set("cond","5")
            st.playSound("ItemSound.quest_middle")
        elif event == "30071-02.htm" :
            st.set("cond","8")
            st.playSound("ItemSound.quest_middle")
        elif event == "32214-02.htm" :
            st.set("cond","11")
            st.playSound("ItemSound.quest_middle")
        elif event == "30879-03.htm" :
            st.set("cond","12")
            st.set("angel","0")
            st.playSound("ItemSound.quest_middle")
        elif event == "angel_cleanup" :
            self.isAngelSpawned = 0
            return
        elif event == "katenar_cleanup" :
            self.isKatenarSpawned = 0
            return
        elif event == "32139-08.htm" :
            st.set("cond","14")
            st.takeItems(Document,1)
            st.playSound("ItemSound.quest_middle")
        elif event == "32138-06.htm" :
            st.set("cond","15")
            st.playSound("ItemSound.quest_middle")
        elif event == "32138-11.htm" :
            st.set("cond","17")
            st.takeItems(Heart,-1)
            st.giveItems(Recommend,1)
            st.playSound("ItemSound.quest_middle")
        return htmltext

    def onFirstTalk (self,npc,player):
        st = self.getQuestState(player, False)
        if st :
            if npc.getId() == Katenar and st.getInt("cond") == 12:
                st.unset("angel")
                st.playSound("ItemSound.quest_itemget")
                st.set("cond","13")
                self.isAngelSpawned = 0
                self.isKatenarSpawned = 0
                st.giveItems(Document,1)
                return "32242-01.htm"
        player.sendPacket(ActionFailed.STATIC_PACKET)
        return None

    def onTalk (self,npc,player):
        htmltext = Quest.getNoQuestMsg(player)
        st = self.getQuestState(player, True)
        if not st : return htmltext
        npcId = npc.getId()
        id = st.getState()
        cond = st.getInt("cond")
        if id == State.COMPLETED :
            htmltext = Quest.getAlreadyCompletedMsg(player)
        elif npcId == Vitus :
            if player.getClassId().getId() not in [125,126] or player.getLevel() < 39:
                htmltext = "<html><body>Only Troopers or Warders are allowed to take this quest! Go away before I get angry!<br>You must be level 39 or higher to undertake this quest.</body></html>"
                st.exitQuest(1)
            elif id == State.CREATED :
                htmltext = "32213-01.htm"
            elif cond >= 1 and cond <= 3 :
                htmltext = "32213-04.htm"
            elif cond >= 4 and cond <17 :
                htmltext = "32213-05.htm"
            elif cond == 17 and st.getQuestItemsCount(Recommend) == 1 :
                htmltext = "32213-06.htm"
                player.sendPacket(SocialAction(player.getObjectId(),3))
                st.takeItems(Recommend,-1)
                st.exitQuest(False)
                st.playSound("ItemSound.quest_finish")
                st.addExpAndSp(393750,27020)
                st.giveItems(57,71194)
                st.giveItems(certificate,1)
        elif npcId == Kekropus :
            if cond == 1 :
                htmltext = "32138-00.htm"
            elif cond == 2 :
                htmltext = "32138-04.htm"
            elif cond == 14 :
                htmltext = "32138-05.htm"
            elif cond == 15 :
                htmltext = "32138-07.htm"
            elif cond == 16 :
                htmltext = "32138-08.htm"
            elif cond == 17 :
                htmltext = "32138-12.htm"
        elif npcId == Casca :
            if cond == 2 :
                htmltext = "32139-01.htm"
            elif cond == 3 :
                htmltext = "32139-03.htm"
            elif cond == 4 :
                htmltext = "32139-05.htm"
            elif cond == 13 :
                htmltext = "32139-06.htm"
            elif cond == 14 :
                htmltext = "32139-09.htm"
        elif npcId == Holst :
            if cond == 4 :
                htmltext = "32199-01.htm"
            elif cond == 5 :
                htmltext = "32199-03.htm"
                st.set("cond","6")
                st.playSound("ItemSound.quest_middle")
            elif cond == 6 :
                htmltext = "32199-04.htm"
        elif npcId == Harlan :
            if cond == 6 :
                htmltext = "30074-01.htm"
            elif cond == 7 :
                htmltext = "30074-02.htm"
        elif npcId == Jacob :
            if cond == 6 :
                htmltext = "30073-01.htm"
                st.set("cond","7")
                st.playSound("ItemSound.quest_middle")
            elif cond == 7 :
                htmltext = "30073-02.htm"
        elif npcId == Lucas :
            if cond == 7 :
                htmltext = "30071-01.htm"
            elif cond == 8 :
                htmltext = "30071-03.htm"
        elif npcId == Xaber :
            if cond == 8 :
                htmltext = "30075-01.htm"
            elif cond == 9 :
                htmltext = "30075-02.htm"
        elif npcId == Liam :
            if cond == 8 :
                htmltext = "30076-01.htm"
                st.set("cond","9")
                st.playSound("ItemSound.quest_middle")
            elif cond == 9 :
                htmltext = "30076-02.htm"
        elif npcId == Zerome :
            if cond == 9 :
                htmltext = "30124-01.htm"
            elif cond == 10 :
                htmltext = "30124-02.htm"
        elif npcId == Vesa :
            if cond == 9 :
                htmltext = "30123-01.htm"
                st.set("cond","10")
                st.playSound("ItemSound.quest_middle")
            elif cond == 10 :
                htmltext = "30123-02.htm"
        elif npcId == Meldina :
            if cond == 10 :
                htmltext = "32214-01.htm"
            elif cond == 11 :
                htmltext = "32214-03.htm"
        elif npcId == Felton :
            if cond == 11 :
                htmltext = "30879-01.htm"
            elif cond == 12 :
                htmltext = "30879-04.htm"
        elif npcId == CargoBox :
            if cond == 12 :
               htmltext = "32243-01.htm"
               if st.getInt("angel") == 0 and self.isAngelSpawned == 0 :
                  angel = st.addSpawn(27332,36198,191949,-3728,180000)
                  angel.broadcastPacket(NpcSay(angel.getObjectId(),0,angel.getId(),player.getName()+"! Step back from the confounded box! I will take it myself!"))
                  angel.setRunning()
                  angel.addDamageHate(player,0,999)
                  angel.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player)
                  self.isAngelSpawned = 1
                  self.startQuestTimer("angel_cleanup",180000,angel,player)
               elif self.isKatenarSpawned == 0 and st.getInt("angel") == 1:
                  katenar = st.addSpawn(32242,36110,191921,-3712,60000)
                  katenar.broadcastPacket(NpcSay(katenar.getObjectId(),0,katenar.getId(),"I am late!"))
                  self.isKatenarSpawned == 1
                  self.startQuestTimer("katenar_cleanup",60000,katenar,player)
                  htmltext = "32243-02.htm"
            elif cond == 13 :
                htmltext = "32243-03.htm"
        return htmltext

    def onKill(self,npc,player,isPet):
        st = self.getQuestState(player, False)
        if not st : return
        if st.getState() != State.STARTED : return
        npcId = npc.getId()
        cond = st.getInt("cond")
        if npcId == Angel and cond == 12:
            st.set("angel","1")
            self.isAngelSpawned = 0
            npc.broadcastPacket(NpcSay(npc.getObjectId(),0,npc.getId(),"Grr. I've been hit..."))
            if self.isKatenarSpawned == 0 :
                  katenar = st.addSpawn(32242,36110,191921,-3712,60000)
                  katenar.broadcastPacket(NpcSay(katenar.getObjectId(),0,katenar.getId(),"I am late!"))
                  self.isKatenarSpawned == 1
                  self.startQuestTimer("katenar_cleanup",60000,katenar,player)
        if npcId == Wyrm and st.getQuestItemsCount(Heart) < 10 and cond == 15 and self.getRandom(100) <= 25:
            if st.getQuestItemsCount(Heart) == 9 :
                  st.giveItems(Heart,1)
                  st.set("cond","16")
                  st.playSound("ItemSound.quest_middle")
            else :
                  st.giveItems(Heart,1)
                  st.playSound("ItemSound.quest_itemget")
        return

QUEST       = Quest(65,qn,"Certified Soul Breaker")

QUEST.addStartNpc(Vitus)

QUEST.addTalkId(Vitus)
QUEST.addTalkId(Kekropus)
QUEST.addTalkId(Casca)
QUEST.addTalkId(Holst)
QUEST.addTalkId(Harlan)
QUEST.addTalkId(Jacob)
QUEST.addTalkId(Lucas)
QUEST.addTalkId(Xaber)
QUEST.addTalkId(Liam)
QUEST.addTalkId(Vesa)
QUEST.addTalkId(Zerome)
QUEST.addTalkId(Meldina)
QUEST.addTalkId(Felton)
QUEST.addTalkId(CargoBox)

QUEST.addFirstTalkId(Katenar)

QUEST.addKillId(Angel)
QUEST.addKillId(Wyrm)