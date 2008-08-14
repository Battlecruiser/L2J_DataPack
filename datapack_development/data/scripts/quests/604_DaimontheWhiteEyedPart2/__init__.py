#Made by Kerb 
import sys 
from net.sf.l2j.gameserver.model.quest import State 
from net.sf.l2j.gameserver.model.quest import QuestState 
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest 
from net.sf.l2j.gameserver.datatables import SpawnTable 
from net.sf.l2j.gameserver.network.serverpackets import NpcSay 
from net.sf.l2j.util import Rnd

qn = "604_DaimontheWhiteEyedPart2" 
#Npcs 
EYE = 31683 
ALTAR = 31541 
#RaidBoss 
DAIMON = 25290 
#Items 
U_SUMMON,S_SUMMON,ESSENCE = range(7192,7195) 
#Rewards dye +2int-2men/+2int-2wit/+2men-2int/+2men-2wit/+2wit-2int/+2wit-2men 
REWARDS = range(4595,4601) 

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
     self.questItemIds = range(7193,7195)

 def onAdvEvent (self, event, npc, player) :
   if event == "Daimon the White-Eyed has despawned" : 
      npc.reduceCurrentHp(9999999,npc) 
      FindTemplate(ALTAR).setBusy(False) 
      AutoChat(npc,"Darkness could not have ray?")
      return
   st = player.getQuestState(qn)
   if not st: return
   cond = st.getInt("cond") 
   htmltext = event 
   if event == "31683-02.htm" : 
      if st.getPlayer().getLevel() < 73 : 
         htmltext = "31683-00b.htm" 
         st.exitQuest(1) 
      else: 
         st.set("cond","1") 
         st.setState(State.STARTED) 
         st.takeItems(U_SUMMON,1) 
         st.giveItems(S_SUMMON,1) 
         st.playSound("ItemSound.quest_accept") 
   elif event == "31541-02.htm" :
       if st.getQuestItemsCount(S_SUMMON) == 0 :
           htmltext = "31541-04.htm"
       elif npc.isBusy() :
           htmltext = "31541-03.htm"
       else:
         spawnId = st.addSpawn(DAIMON,186320,-43904,-3175) 
         st.takeItems(S_SUMMON,1) 
         npc.setBusy(True) 
         st.set("cond","2") 
         self.startQuestTimer("Daimon the White-Eyed has despawned",1200000,spawnId,None) 
         AutoChat(spawnId,"Who called me?") 
   elif event == "31683-04.htm" : 
      if st.getQuestItemsCount(ESSENCE) >= 1 : 
         st.takeItems(ESSENCE,1) 
         st.giveItems(REWARDS[st.getRandom(len(REWARDS))],5) 
         st.playSound("ItemSound.quest_finish") 
         st.exitQuest(1) 
         htmletext = "31683-04.htm" 
      else: 
         htmletext = "31683-05.htm" 
         st.exitQuest(1) 
   return htmltext 

 def onTalk (self,npc,player): 
   htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>" 
   st = player.getQuestState(qn) 
   if st : 
     npcId = npc.getNpcId() 
     id = st.getState() 
     cond = st.getInt("cond") 
     if cond == 0 : 
       if npcId == EYE : 
         if st.getQuestItemsCount(U_SUMMON) >= 1 : 
           htmltext = "31683-01.htm" 
         else: 
           htmltext = "31683-00a.htm" 
     elif cond == 1 : 
       if npcId == EYE : 
         htmltext = "31683-02a.htm" 
       if npcId == ALTAR : 
         htmltext = "31541-01.htm" 
     elif cond == 2 : 
       if npcId == ALTAR :
           if npc.isBusy() :
               htmltext = "31541-03.htm"
           else :
               htmltext = "31541-02.htm"
               spawnId = st.addSpawn(DAIMON,186320,-43904,-3175) 
               npc.setBusy(True) 
               self.startQuestTimer("Daimon the White-Eyed has despawned",1200000,spawnId,None) 
               AutoChat(spawnId,"Who called me?") 
     elif cond == 3 : 
       if npcId == EYE :
            if st.getQuestItemsCount(ESSENCE) >= 1 :
                htmltext = "31683-03.htm"
            else :
                htmltext = "31683-06.htm"
       if npcId == ALTAR : 
         htmltext = "31541-05.htm" 
     return htmltext 

 def onKill(self,npc,player,isPet):
     npcId = npc.getNpcId() 
     if npcId == DAIMON :
        FindTemplate(ALTAR).setBusy(False) 
        self.cancelQuestTimer("Daimon the White-Eyed has despawned",npc,None)
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
            if st.getQuestItemsCount(S_SUMMON) > 0 :
                st.takeItems(S_SUMMON,1)
            st.giveItems(ESSENCE,1) 
            st.set("cond","3") 
            st.playSound("ItemSound.quest_middle")
        else :
            st = player.getQuestState(qn)
            if not st : return
            if st.getState() == State.STARTED and (st.getInt("cond") == 1 or st.getInt("cond") == 2) :
                if st.getQuestItemsCount(S_SUMMON) > 0 :
                    st.takeItems(S_SUMMON,1)
                st.giveItems(ESSENCE,1) 
                st.set("cond","3") 
                st.playSound("ItemSound.quest_middle")
     return


QUEST = Quest(604,qn,"Daimon the White-Eyed - Part 2") 

QUEST.addStartNpc(EYE) 

QUEST.addTalkId(EYE) 
QUEST.addTalkId(ALTAR) 

QUEST.addKillId(DAIMON)