# Created by Gigiikun
import sys
from com.l2jserver import Config
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest

qn = "643_RiseandFalloftheElrokiTribe"

#Settings: drop chance in %
DROP_CHANCE = 75

BONES_OF_A_PLAINS_DINOSAUR = 8776
PLAIN_DINOSAURS = [22201,22202,22204,22205,22209,22210,22212,22213,22219,22220,22221,22222,22224,22225,22742,22743,22744,22745]
REWARDS = range(8712,8723)
REWARDS_DYNA = {
    "1" : [9492 , 400], #Recipe: Sealed Dynasty Tunic (60%)
    "2" : [9493 , 250], #Recipe: Sealed Dynasty Stockings (60%)
    "3" : [9494 , 200], #Recipe: Sealed Dynasty Circlet (60%)
    "4" : [9495 , 134], #Recipe: Sealed Dynasty Gloves (60%)
    "5" : [9496 , 134], #Recipe: Sealed Dynasty Shoes (60%)
    "6" : [10115, 287], #Recipe: Sealed Dynasty Sigil (60%)
    }

class Quest (JQuest) :

 def __init__(self,id,name,descr):
 	JQuest.__init__(self,id,name,descr)
 	self.questItemIds = [BONES_OF_A_PLAINS_DINOSAUR]

 def onAdvEvent (self,event,npc, player) :
    htmltext = event
    st = player.getQuestState(qn)
    if not st : return
    count = st.getQuestItemsCount(BONES_OF_A_PLAINS_DINOSAUR)
    if event == "None" :
        return
    elif event == "32106-03.htm" :
       st.set("cond","1")
       st.setState(State.STARTED)
       st.playSound("ItemSound.quest_accept")
    elif event == "32117-03.htm" :
       if count >= 300 :
          st.takeItems(BONES_OF_A_PLAINS_DINOSAUR,300)
          st.rewardItems(REWARDS[getRandom(len(REWARDS))],5)
       else :
          htmltext = "32117-04.htm"
    elif event in REWARDS_DYNA.keys() :
       if count >= REWARDS_DYNA[event][1] :
          st.takeItems(BONES_OF_A_PLAINS_DINOSAUR,REWARDS_DYNA[event][1])
          st.rewardItems(REWARDS_DYNA[event][0],1)
          htmltext = "32117-06.htm"
       else :
          htmltext = "32117-07.htm"
    elif event == "Quit" :
       st.playSound("ItemSound.quest_finish")
       st.exitQuest(1)
       return
    return htmltext

 def onTalk (self, npc, player):
    st = player.getQuestState(qn)
    htmltext = Quest.getNoQuestMsg(player)
    if st :
       npcId = npc.getNpcId()
       cond = st.getInt("cond")
       count = st.getQuestItemsCount(BONES_OF_A_PLAINS_DINOSAUR)
       if cond == 0 and npcId == 32106:
          if player.getLevel() >= 75 :
             htmltext = "32106-01.htm"
          else :
             htmltext = "32106-00.htm"
             st.exitQuest(1)
       elif st.getState() == State.STARTED :
          if npcId == 32106 :
             if count == 0 :
                htmltext = "32106-05.htm"
             else :
                htmltext = "32106-06.htm"
                st.takeItems(BONES_OF_A_PLAINS_DINOSAUR,-1)
                st.giveItems(57,count*1374)
          elif npcId == 32117 :
             htmltext = "32117-01.htm"
    return htmltext

 def onKill (self, npc, player,isPet):
    partyMember = self.getRandomPartyMember(player,"1")
    if not partyMember: return
    st = partyMember.getQuestState(qn)
    if st :
       if st.getState() == State.STARTED :
          npcId = npc.getNpcId()
          cond = st.getInt("cond")
          count = st.getQuestItemsCount(BONES_OF_A_PLAINS_DINOSAUR)
          if cond == 1 :
             chance = DROP_CHANCE*Config.RATE_QUEST_DROP
             numItems, chance = divmod(chance,100)
             if getRandom(100) < chance : 
                numItems += 1
             if numItems :
                if int(count + numItems)/300 > int(count)/300 :
                   st.playSound("ItemSound.quest_middle")
                else :
                   st.playSound("ItemSound.quest_itemget")
                st.giveItems(BONES_OF_A_PLAINS_DINOSAUR,int(numItems))
    return

QUEST = Quest(643,qn,"Rise and Fall of the Elroki Tribe")

QUEST.addStartNpc(32106)

QUEST.addTalkId(32106)
QUEST.addTalkId(32117)

for mob in PLAIN_DINOSAURS :
   QUEST.addKillId(mob)
