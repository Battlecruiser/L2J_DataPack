# Made by disKret
import sys
from net.sf.l2j import Config 
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "39_RedEyedInvaders"

#NPC
BABENCO = 30334
BATHIS = 30332

#MOBS
M_LIZARDMAN = 20919
M_LIZARDMAN_SCOUT = 20920
M_LIZARDMAN_GUARD = 20921

#QUEST DROPS
BLACK_BONE_NECKLACE = 7178
RED_BONE_NECKLACE = 7179
INCENSE_POUCH = 7180
GEM_OF_MAILLE = 7181

#REWARDS
GREEN_COLORED_LURE_HG = 6521
BABy_DUCK_RODE = 6529
FISHING_SHOT_NG = 6535

DROPLIST={20925:[GEM_OF_MAILLE,30,INCENSE_POUCH,"5"],
          20919:[INCENSE_POUCH,30,GEM_OF_MAILLE,"5"],
          20920:[INCENSE_POUCH,30,GEM_OF_MAILLE,"5"]
}
NECKLACE={20921:[RED_BONE_NECKLACE,100,BLACK_BONE_NECKLACE,"3"],
          20919:[BLACK_BONE_NECKLACE,100,RED_BONE_NECKLACE,"3"],
          20920:[BLACK_BONE_NECKLACE,100,RED_BONE_NECKLACE,"3"]
}
def drop(partyMember,array) :
    item,max,item2,condition = array
    st = partyMember.getQuestState(qn)
    count = st.getQuestItemsCount(item)
    numItems,chance = divmod(100*Config.RATE_QUESTS_REWARD,100)
    if st.getRandom(100) < chance :
        numItems = numItems + 1
    if count+numItems > max :
        numItems = max - count
    st.giveItems(item,int(numItems))
    if st.getQuestItemsCount(item) == max and st.getQuestItemsCount(item2) == max:
        st.playSound("ItemSound.quest_middle")
        st.set("cond",condition)
    else:
        st.playSound("ItemSound.quest_itemget")
    return
    
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   cond = st.getInt("cond")
   if st.getState() != COMPLETED :
    if event == "30334-1.htm" and cond == 0 :
     st.set("cond","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
    elif event == "30332-1.htm" and cond == 1 :
     st.set("cond","2")
    elif event == "30332-3.htm" :
     if st.getQuestItemsCount(BLACK_BONE_NECKLACE) == st.getQuestItemsCount(RED_BONE_NECKLACE) == 100 and cond == 3:
       st.takeItems(BLACK_BONE_NECKLACE,100)
       st.takeItems(RED_BONE_NECKLACE,100)       
       st.set("cond","4")
     else :
       htmltext = "You don't have required items"
    elif event == "30332-5.htm" :
     if st.getQuestItemsCount(INCENSE_POUCH) == st.getQuestItemsCount(GEM_OF_MAILLE) == 30 and cond == 5 :
       st.takeItems(INCENSE_POUCH,30)
       st.takeItems(GEM_OF_MAILLE,30)  
       st.giveItems(GREEN_COLORED_LURE_HG,60)
       st.giveItems(BABy_DUCK_RODE,1)
       st.giveItems(FISHING_SHOT_NG,500)
       st.setState(COMPLETED)
       st.unset("cond")
       st.playSound("ItemSound.quest_finish")
     else :
       htmltext = "You don't have required items"
   return htmltext

 def onTalk (self,npc,player):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   id = st.getState()
   cond = st.getInt("cond")
   if npcId == BABENCO and cond == 0 :
     if id == COMPLETED :
       htmltext = "<html><head><body>This quest have already been completed.</body></html>"
     elif st.getPlayer().getLevel() >= 20 : # and st.getPlayer().getLevel() <= 28:
       htmltext = "30334-0.htm"
     else :
       st.exitQuest(1)
   elif npcId == BATHIS and id == STARTED:
     if cond == 1 :
       htmltext = "30332-0.htm"
     elif st.getQuestItemsCount(BLACK_BONE_NECKLACE) == st.getQuestItemsCount(RED_BONE_NECKLACE) == 100 :
       htmltext = "30332-2.htm"
     elif st.getQuestItemsCount(INCENSE_POUCH) == st.getQuestItemsCount(GEM_OF_MAILLE) == 30 :
       htmltext = "30332-4.htm"
   return htmltext

 def onKill (self,npc,player):
   npcId = npc.getNpcId()
   partyMember = self.getRandomPartyMember(player,"2")
   if (partyMember and npcId != 20925) :
       drop(partyMember,NECKLACE[npcId])
   else:
       partyMember = self.getRandomPartyMember(player,"4")
       if (partyMember and npcId != 20921) :     
           drop(partyMember,DROPLIST[npcId])
   return

QUEST       = Quest(39,qn,"Red Eyed Invaders")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(BABENCO)
QUEST.addTalkId(BABENCO)

QUEST.addTalkId(BATHIS)

QUEST.addKillId(20919)
QUEST.addKillId(20920)
QUEST.addKillId(20921)
QUEST.addKillId(20925)
STARTED.addQuestDrop(BABENCO,BLACK_BONE_NECKLACE,1)
STARTED.addQuestDrop(BABENCO,RED_BONE_NECKLACE,1)
STARTED.addQuestDrop(BABENCO,INCENSE_POUCH,1)
STARTED.addQuestDrop(BABENCO,GEM_OF_MAILLE,1)

print "importing quests: 39: Red Eyed Invaders"
