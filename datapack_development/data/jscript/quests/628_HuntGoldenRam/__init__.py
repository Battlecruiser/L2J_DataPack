# Made by Polo - Have fun!
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#Npcs
KAHMAN = 31554

#Items
CHITIN = 7248   #Splinter Stakato Chitin
CHITIN2 = 7249  #Needle Stakato Chitin
RECRUIT = 7246  #Golden Ram Badge - Recruit
SOLDIER = 7247  #Golden Ram Badge - Soldier

CHANCE = 49   #Base dropchance of the Badges

class Quest (JQuest) :

 def __init__(self,id,name,descr,party): JQuest.__init__(self,id,name,descr,party)

 def onEvent (self,event,st) :
    htmltext = event
    if htmltext == "31554-03a.htm" :
       if st.getQuestItemsCount(CHITIN)>=100 and st.getInt("cond")==1 :
          st.set("cond","2")
          st.takeItems(CHITIN,100)
          st.giveItems(RECRUIT,1)
          htmltext = "31554-04.htm"
    elif event == "31554-07.htm" :
       st.exitQuest(1)
       st.playSound("ItemSound.quest_giveup")
    return htmltext

 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say to you.</body></html>"
   id = st.getState()
   cond = st.getInt("cond")
   chitin1=st.getQuestItemsCount(CHITIN)
   chitin2=st.getQuestItemsCount(CHITIN2)
   if id == COMPLETED :
      htmltext = "31554-05a.htm"
   elif cond==0 :
      if st.getPlayer().getLevel()>=66 :
         htmltext = "31554-02.htm"
         st.set("cond","1")
         st.setState(STARTED)
         st.playSound("ItemSound.quest_accept")
      else :
         htmltext = "31554-01.htm"
         st.exitQuest(1)
   elif cond==1 :
      if chitin1>=100 :
         htmltext = "31554-03.htm"
      else:
         htmltext = "31554-03a.htm"
   elif cond==2 :
      if chitin1>=100 and chitin2>=100 :
         htmltext = "31554-05.htm"
         st.takeItems(CHITIN,100)
         st.takeItems(CHITIN2,100)
         st.takeItems(RECRUIT,1)       
         st.giveItems(SOLDIER,1)
         st.setState(COMPLETED)
         st.unset("cond")
         st.playSound("ItemSound.quest_finish")
      elif not chitin1 and not chitin2:
         htmltext = "31554-04b.htm"
      else :
         htmltext = "31554-04a.htm"
   return htmltext

 def onKill (Self,npc,st):
   npcId = npc.getNpcId()
   cond = st.getInt("cond")
   if cond>=1 and 21507<npcId<21513:
      if st.getRandom(100) < CHANCE+((npcId-21506)*2) :
           st.giveItems(CHITIN,1)
           if st.getQuestItemsCount(CHITIN) < 100 :
              st.playSound("ItemSound.quest_itemget")
           else :
              st.playSound("ItemSound.quest_middle")
   elif cond==2 and npcId in range(21513,21518):
      if st.getRandom(100) < CHANCE((npcId-21512)*3) :
           st.giveItems(CHITIN2,1)
           if st.getQuestItemsCount(CHITIN2) < 100 :
              st.playSound("ItemSound.quest_itemget")
           else :
              st.playSound("ItemSound.quest_middle")
   return
           
QUEST       = Quest(628,"628_HuntGoldenRam","Hunt of the Golden Ram Mercenary Force",True)
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(KAHMAN)

CREATED.addTalkId(KAHMAN)
STARTING.addTalkId(KAHMAN)
STARTED.addTalkId(KAHMAN)
COMPLETED.addTalkId(KAHMAN)

for mob in range(21508,21518):
    STARTED.addKillId(mob)

for item in range(7246,7250):
    STARTED.addQuestDrop(KAHMAN,item,1)

print "importing quests: 628: Hunt of the Golden Ram Mercenary Force"
