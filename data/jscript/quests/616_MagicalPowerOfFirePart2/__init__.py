#Made by Emperorc
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.actor.instance import L2NpcInstance
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
from net.sf.l2j.gameserver.datatables import SpawnTable
from net.sf.l2j.gameserver.serverpackets import CreatureSay
from java.util import Iterator
from net.sf.l2j.gameserver.datatables import NpcTable
from net.sf.l2j.gameserver.model import L2Spawn
from net.sf.l2j.gameserver.model.actor.instance import L2MonsterInstance

qn = "616_MagicalPowerOfFirePart2"

#NPC
Udan = 31379
Alter = 31558


#MOBS
Varka_Mobs = [ 21350, 21351, 21353, 21354, 21355, 21357, 21358, 21360, 21361, \
21362, 21369, 21370, 21364, 21365, 21366, 21368, 21371, 21372, 21373, 21374, 21375 ]
Nastron = 25306

#ITEMS
Totem2 = 7243
Fire_Heart = 7244

def FindTemplate (npcId) :
    for spawn in SpawnTable.getInstance().getSpawnTable().values():
        if spawn.getNpcid() == npcId:
            npcinstance=spawn.getLastSpawn()
            break
    return npcinstance

def AutoChat(npc,text) :
    chars = npc.getKnownList().getKnownPlayers().values().toArray()
    if chars != None:
       for pc in chars :
          sm = CreatureSay(npc.getObjectId(), 0, npc.getName(), text)
          pc.sendPacket(sm)

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self, event, st) :
   cond = st.getInt("cond")
   id = st.getInt("id")
   Green_Totem = st.getQuestItemsCount(Totem2)
   Heart = st.getQuestItemsCount(Fire_Heart)
   htmltext = event
   if event == "31379-04.htm" :
       if st.getPlayer().getLevel() >= 75 and st.getPlayer().getAllianceWithVarkaKetra() <= -2 :
           if Green_Totem :
                st.set("cond","1")
                st.set("id","1")
                st.setState(STARTED)
                st.playSound("ItemSound.quest_accept")
                htmltext = "31379-04.htm"
           else :
                htmltext = "31379-02.htm"
                st.exitQuest(1)
       else :
           htmltext = "31379-03.htm"
           st.exitQuest(1)
   elif event == "31379-08.htm" :
       if Heart:
           htmltext = "31379-08.htm"
           st.takeItems(Fire_Heart,-1)
           st.addExpAndSp(10000,0)
           st.unset("id")
           st.unset("cond")
           st.playSound("ItemSound.quest_finish")
           st.exitQuest(1)
       else :
           htmltext = "31379-09.htm"

   elif event == "31558-02.htm" :
       if Green_Totem :
           htmletext = "31558-02.htm"   #TODO add lights from above
           spawnId = st.getPcSpawn().addSpawn(Nastron,142528,-82528,-6496)
           st.set("spawnId",str(spawnId))
           st.takeItems(Totem2,1)
           st.set("id","2")
           FindTemplate(Alter).setBusy(True)
           st.set("cond","2")
           st.startQuestTimer("Soul of Fire Nastron has despawned",1200000)#1200000)
           AutoChat(st.getPcSpawn().getSpawn(spawnId).getLastSpawn(),"Hey! I'll kick your aarse!")#this is only a temp message until we find out what it actually is! string = 61050
       else :
           htmltext = "31558-04.htm"
   elif event == "Soul of Fire Nastron has despawned" :
       npc1 = st.getPcSpawn().getSpawn(st.getInt("spawnId")).getLastSpawn()
       npc1.reduceCurrentHp(9999999,npc1)
       st.getPcSpawn().removeAllSpawn()
       st.unset("id")
       st.unset("cond")
       st.unset("spawnId")
       FindTemplate(Alter).setBusy(False)
       st.exitQuest(1)
       AutoChat(npc1,"May the gods forever condemn you! Udan Mardui, your power weakens!") #this is only a temp message until we find out what it actually is! string = 61051
   return htmltext

 def onTalk (self, npc, player):
    st = player.getQuestState(qn)
    htmltext = "<html><head><body>I have nothing to say you</body></html>"
    if st :
        npcId = npc.getNpcId()
        cond = st.getInt("cond")
        id = st.getInt("id")
        Green_Totem = st.getQuestItemsCount(Totem2)
        Heart = st.getQuestItemsCount(Fire_Heart)
        if npcId == Udan :
            if st.getState()== CREATED :
                htmltext = "31379-01.htm"
            elif id == 1 or id == 2 :
                htmltext = "31379-05.htm"
            elif id == 3:
                if Heart :
                    htmltext = "31379-06.htm"
                else :
                    htmltext = "31379-07.htm"
        elif npcId == Alter :
           if npc.isBusy() :
               htmltext = "31558-03.htm"
           else :
            if id == 1 :
                htmltext = "31558-01.htm"
            elif id == 2 or id == 3 :
                htmltext = "31558-05.htm"
    return htmltext

 def onKill (self, npc, player):
   st = player.getQuestState(qn)
   if st :
    if st.getState() == STARTED :
        npcId = npc.getNpcId()
        cond = st.getInt("cond")
        id = st.getInt("id")
        Heart = st.getQuestItemsCount(Fire_Heart)
        if npcId == Nastron :
            st.giveItems(Fire_Heart,1)
            FindTemplate(Alter).setBusy(False)
            st.set("id","3")
            st.set("cond","3")
            st.playSound("ItemSound.quest_middle")
            st.getQuestTimer("Soul of Fire Nastron has despawned").cancel()
        elif npcId in Varka_Mobs :
            if Heart :
                st.takeItems(Fire_Heart,-1)
            st.unset("cond")
            st.unset("id")
            st.exitQuest(1)
    return


QUEST       = Quest(616,qn,"Magical Power of Fire - Part 2")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(Udan)

QUEST.addTalkId(Udan)
QUEST.addTalkId(Alter)

QUEST.addKillId(Nastron)
STARTED.addQuestDrop(Nastron,Fire_Heart,1)

for mobId in Varka_Mobs:
    QUEST.addKillId(mobId)

print "importing quests: 616: Magical Power of Fire - Part 2"