#Made by Emperorc
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
from net.sf.l2j.gameserver.datatables import SpawnTable
from net.sf.l2j.gameserver.serverpackets import NpcSay
from net.sf.l2j.util import Rnd

qn = "610_MagicalPowerOfWaterPart2"

#NPC
Asefa = 31372
Alter = 31560

#MOBS
Ketra_Orcs = [ 21324, 21325, 21327, 21328, 21329, 21331, 21332, 21334, 21335, \
21336, 21338, 21339, 21340, 21342, 21343, 21344, 21345, 21346, 21347, 21348, 21349 ]
Ashutar = 25316

#ITEMS
Totem2 = 7238
Ice_Heart = 7239

def FindTemplate (npcId) :
    npcinstance = 0
    for spawn in SpawnTable.getInstance().getSpawnTable().values():
        if spawn :
            if spawn.getNpcid() == npcId:
                npcinstance=spawn.getLastSpawn()
                break
    return npcinstance

def AutoChat(npc,text) :
    chars = npc.getKnownList().getKnownPlayers().values().toArray()
    if chars != None:
       for pc in chars :
          sm = NpcSay(npc.getObjectId(), 0, npc.getNpcId(), text)
          pc.sendPacket(sm)

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
     self.questItemIds = [Ice_Heart]

 def onAdvEvent (self, event, npc, player) :
   if event == "Soul of Water Ashutar has despawned" :
       npc.reduceCurrentHp(9999999,npc)
       FindTemplate(Alter).setBusy(False)
       AutoChat(npc,"The fetter strength is weaken Your consciousness has been defeated!")
       return
   st = player.getQuestState(qn)
   if not st: return
   cond = st.getInt("cond")
   id = st.getInt("id")
   Green_Totem = st.getQuestItemsCount(Totem2)
   Heart = st.getQuestItemsCount(Ice_Heart)
   htmltext = event
   if event == "31372-04.htm" :
       if st.getPlayer().getLevel() >= 75 and st.getPlayer().getAllianceWithVarkaKetra() >= 2 :
           if Green_Totem :
                st.set("cond","1")
                st.set("id","1")
                st.setState(State.STARTED)
                st.playSound("ItemSound.quest_accept")
                htmltext = "31372-04.htm"
           else :
                htmltext = "31372-02.htm"
                st.exitQuest(1)
       else :
           htmltext = "31372-03.htm"
           st.exitQuest(1)
   elif event == "31372-08.htm" :
       if Heart:
           htmltext = "31372-08.htm"
           st.takeItems(Ice_Heart,-1)
           st.addExpAndSp(10000,0)
           st.unset("id")
           st.unset("cond")
           st.playSound("ItemSound.quest_finish")
           st.exitQuest(1)
       else :
           htmltext = "31372-09.htm"
   elif event == "31560-02.htm" :
       if Green_Totem == 0 :
           htmltext = "31560-04.htm"
       elif npc.isBusy() :
           htmltext = "31560-03.htm"
       else:
           spawnedNpc = st.addSpawn(Ashutar,104825,-36926,-1136)
           st.takeItems(Totem2,1)
           st.set("id","2")
           npc.setBusy(True)
           st.set("cond","2")
           self.startQuestTimer("Soul of Water Ashutar has despawned",1200000,spawnedNpc,None)
           AutoChat(spawnedNpc,"The water charm then is the storm and the tsunami strength! Opposes with it only has the blind alley!")
   return htmltext

 def onTalk (self, npc, player):
   st = player.getQuestState(qn)
   htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
   if st :
    npcId = npc.getNpcId()
    cond = st.getInt("cond")
    id = st.getInt("id")
    Green_Totem = st.getQuestItemsCount(Totem2)
    Heart = st.getQuestItemsCount(Ice_Heart)
    if npcId == Asefa :
        if st.getState()== State.CREATED :
            htmltext = "31372-01.htm"
        elif id == 1 or id == 2 :
            htmltext = "31372-05.htm"
        elif id == 3:
            if Heart :
                htmltext = "31372-06.htm"
            else :
                htmltext = "31372-07.htm"
    elif npcId == Alter :
        if id == 1 :
            htmltext = "31560-01.htm"
        elif id == 2 :
            if npc.isBusy() :
                htmltext = "31560-03.htm"
            else :
                htmltext = "31560-02.htm"
                spawnedNpc = st.addSpawn(Ashutar,104825,-36926,-1136)
                npc.setBusy(True)
                self.startQuestTimer("Soul of Water Ashutar has despawned",1200000,spawnedNpc,None)
                AutoChat(spawnedNpc,"The water charm then is the storm and the tsunami strength! Opposes with it only has the blind alley!")
        elif id == 3 :
            htmltext = "31560-05.htm"
    return htmltext

 def onKill(self,npc,player,isPet):
    npcId = npc.getNpcId()
    if npcId == Ashutar :
        FindTemplate(Alter).setBusy(False)
        self.cancelQuestTimer("Soul of Water Ashutar has despawned",npc,None)
        party = player.getParty()
        if party :
            PartyQuestMembers = []
            for player1 in party.getPartyMembers().toArray() :
                st1 = player1.getQuestState(qn)
                if st1 :
                    if st1.getState() == State.STARTED and (st1.getInt("cond") == 1 or st1.getInt("cond") == 2) :
                        PartyQuestMembers.append(st1)
            if len(PartyQuestMembers) == 0 : return
            st = PartyQuestMembers[Rnd.get(len(PartyQuestMembers))]
            if st.getQuestItemsCount(Totem2) > 0 :
                st.takeItems(Totem2,1)
            st.giveItems(Ice_Heart,1) 
            st.set("cond","3")
            st.set("id","3")
            st.playSound("ItemSound.quest_middle")
        else :
            st = player.getQuestState(qn)
            if not st : return
            if st.getState() == State.STARTED and (st.getInt("cond") == 1 or st.getInt("cond") == 2) :
                if st.getQuestItemsCount(Totem2) > 0 :
                    st.takeItems(Totem2,1)
                st.giveItems(Ice_Heart,1) 
                st.set("cond","3")
                st.set("id","3")
                st.playSound("ItemSound.quest_middle")
    elif npcId in Ketra_Orcs :
      st = player.getQuestState(qn)
      if st :
         if st.getQuestItemsCount(Ice_Heart) :
             st.takeItems(Ice_Heart,-1)
         st.unset("cond")
         st.unset("id")
         st.exitQuest(1)
    return

QUEST = Quest(610,qn,"Magical Power of Water - Part 2")

QUEST.addStartNpc(Asefa)

QUEST.addTalkId(Asefa)
QUEST.addTalkId(Alter)

QUEST.addKillId(Ashutar)

for mobId in Ketra_Orcs:
    QUEST.addKillId(mobId)