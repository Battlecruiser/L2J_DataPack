# Made by mtrix
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

ADENA = 57
ALLIGATOR_TOOTH = 5863
TORN_MAP_FRAGMENT = 5864
PIRATES_TREASURE_MAP = 5915
CHANCE = 45
CHANCE2 = 10
#These items are custom, since we don't have info about them. Feel free to change them as you see fit (DrLecter)
#Syntax: [itemid,max qty],
RANDOM_REWARDS=[[736,15], #SoE
                [1061,20],#Healing Potion
                [734,15], #Haste Potion
                [735,15], #Alacrity Potion
                [1878,35],#Braided Hemp
                [1875,15],#Stone of Purity
                [1879,15],#Cokes
                [1880,15],#Steel
                [956,1],  #Enchant Armor D
                [955,1],  #Enchant Weapon D
               ]

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
     htmltext = event
     amount = st.getQuestItemsCount(ALLIGATOR_TOOTH)
     maps = divmod(st.getQuestItemsCount(TORN_MAP_FRAGMENT),10)
     if event == "7895-00a.htm" :
         st.exitQuest(1)
     elif event == "1" :
         st.setState(STARTED)
         st.set("cond","1")
         htmltext = "7895-02.htm"
         st.playSound("ItemSound.quest_accept")
     elif event == "7895-06.htm" :
         if st.getQuestItemsCount(TORN_MAP_FRAGMENT)>=10 :
             htmltext = "7895-07.htm"
     elif event == "7895-05.htm" :
         if amount :
             st.giveItems(ADENA,amount*300)
             st.takeItems(ALLIGATOR_TOOTH,-1)
             st.playSound("ItemSound.quest_itemget")
             htmltext = "7895-05a.htm"
             if amount > 99 :
                htmltext = "7895-05b.htm"
                item=RANDOM_REWARDS[st.getRandom(len(RANDOM_REWARDS))]
                st.giveItems(item[0],st.getRandom(item[1])+1)
     elif event == "7895-08.htm" :
         st.giveItems(PIRATES_TREASURE_MAP,maps[0])
         st.takeItems(TORN_MAP_FRAGMENT,maps[0]*10)
     elif event == "7895-09.htm" :
         st.exitQuest(1)
         st.playSound("ItemSound.quest_finish")
     return htmltext

 def onTalk (Self,npc,st):
     npcId = npc.getNpcId()
     htmltext = "<html><head><body>I have nothing to say you</body></html>"
     id = st.getState()
     level = st.getPlayer().getLevel()
     cond = st.getInt("cond")
     if id == CREATED :
        if level>=38 :
           htmltext = "7895-01.htm"
        else :
           htmltext = "7895-00.htm"
     elif cond==1 :
         htmltext = "7895-03.htm"
     return htmltext

 def onKill (self,npc,st):
     npcId = npc.getNpcId()
     cond = st.getInt("cond")
     random = st.getRandom(100)
     if random<=CHANCE :
         st.giveItems(ALLIGATOR_TOOTH,1)
         st.playSound("ItemSound.quest_itemget")
     if random<=CHANCE2 and st.getQuestItemsCount(TORN_MAP_FRAGMENT)<10 :
         st.giveItems(TORN_MAP_FRAGMENT,1)
     return

QUEST       = Quest(354,"354_ConquestOfAlligatorIsland","Conquest Of Alligator Island")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7895)

CREATED.addTalkId(7895)
STARTED.addTalkId(7895)

STARTED.addQuestDrop(991,ALLIGATOR_TOOTH,1)
STARTED.addQuestDrop(991,TORN_MAP_FRAGMENT,1)

for i in range(804,809)+[991] :
    STARTED.addKillId(i)

print "importing quests: 354: Conquest Of Alligator Island"
