# Contributed by t0rm3nt0r (tormentor2000@mail.ru) to the Official L2J Datapack Project.
# Visit http://forum.l2jdp.com for more details.

import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#Complete - 95%. Need add other reward's for 50 item's and check newbie player level from retail
qn = "281_HeadForTheHills"

#NPC'S
MARCELA = 32173

#ITEM'S
HILLS = 9796
SOULSHOT_FOR_BEGINNERS  = 5789
REWARD = 736 #Scroll of Escape. Maybe need add other reward.

#MOB'S
MOBS = range(22234,22240)

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
     self.questItemIds = [HILLS]
  
 def onEvent (self,event,st) :
     htmltext = event
     player = st.getPlayer()
     hills = st.getQuestItemsCount(HILLS)
     onlyone = st.getInt("onlyone")
     if event == "32173-03.htm" :
       st.set("cond","1")
       st.setState(State.STARTED)
       st.playSound("ItemSound.quest_accept")
     elif event == "32173-06.htm" :
       if player.getLevel() < 25 and player.isNewbie() and not onlyone :
         if hills > 20 : 
           st.giveItems(57,hills*23+400)
         else :
           st.giveItems(57,hills*23)
         st.giveItems(SOULSHOT_FOR_BEGINNERS,6000)
         st.takeItems(HILLS,-1)
         st.set("onlyone","1")
       else:
         if hills > 20 : 
           st.giveItems(57,hills*23+400)
         else :
           st.giveItems(57,hills*23)
         st.takeItems(HILLS,-1)
     elif event == "32173-07.htm" :
       if hills < 50 :
         htmltext = "32173-07a.htm"
       else:
         if player.getLevel() < 25 and player.isNewbie() and not onlyone :
           st.giveItems(SOULSHOT_FOR_BEGINNERS,6000)
           st.giveItems(REWARD,1)
           st.takeItems(HILLS,50)
           st.set("onlyone","1")
         else :
           st.giveItems(REWARD,1)
           st.takeItems(HILLS,50)
     elif event == "32173-09.htm" :
       st.takeItems(HILLS,-1)
       st.exitQuest(1)
     return htmltext

 def onTalk (self,npc,player):
     npcId = npc.getNpcId()
     htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
     st = player.getQuestState(qn)
     if not st : return htmltext
     id = st.getState()
     cond = st.getInt("cond")
     hills = st.getQuestItemsCount(HILLS)
     if id == State.CREATED and npcId == MARCELA :
       if player.getLevel() < 6 :
         htmltext = "32173-02.htm"
         st.exitQuest(1)
       else :
         htmltext = "32173-01.htm"
     elif id == State.STARTED and npcId == MARCELA :
       if not hills :
         htmltext = "32173-04.htm"
       else :
         htmltext = "32173-05.htm"
     return htmltext
    
 def onKill(self,npc,player,isPet) :
     st = player.getQuestState(qn)
     if not st: return
     if st.getState() == State.STARTED :
       npcId = npc.getNpcId()
       chance = st.getRandom(100)
       if (npcId in MOBS) and (chance < 50) : #Retail statistic info. 53 mob's - 28 hills
         st.giveItems(HILLS,1)
         st.playSound("ItemSound.quest_itemget")
     return

QUEST       = Quest(281, qn, "Head for the Hills!")

QUEST.addStartNpc(MARCELA)

QUEST.addTalkId(MARCELA)

for mob in MOBS :
    QUEST.addKillId(mob)