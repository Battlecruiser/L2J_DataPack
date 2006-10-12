# Made by Mr. Have fun! Version 0.2
# version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

COLLETTE_LETTER = 1076
NORMANS_LETTER = 1106
ADENA = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "30350-04.htm" :
      st.giveItems(COLLETTE_LETTER,1)
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
    elif event == "30255-03.htm" :
      st.set("cond","2")
      st.takeItems(COLLETTE_LETTER,1)
      st.giveItems(NORMANS_LETTER,1)
      st.giveItems(ADENA,2000)
    elif event == "30255-04.htm" :
      st.takeItems(COLLETTE_LETTER,1)
      st.giveItems(ADENA,3000)
      st.unset("cond")
      st.setState(COMPLETED)
      st.playSound("ItemSound.quest_finish")
    elif event == "30210-02.htm" :
      st.takeItems(NORMANS_LETTER,1)
      st.giveItems(ADENA,20000)
      st.unset("cond")
      st.setState(COMPLETED)
      st.playSound("ItemSound.quest_finish")
    return htmltext


 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   if id==COMPLETED :
     htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 30350 :
     if int(st.get("cond"))==0 :
       if st.getPlayer().getLevel() >= 15 :
         htmltext = "30350-03.htm"
       else:
         htmltext = "30350-02.htm"
         st.exitQuest(1)
     elif int(st.get("cond"))==1 and st.getQuestItemsCount(COLLETTE_LETTER) :
       htmltext = "30350-05.htm"
   elif npcId == 30255 :
     if int(st.get("cond"))==1 and st.getQuestItemsCount(COLLETTE_LETTER) :
       htmltext = "30255-01.htm"
     elif int(st.get("cond"))==2 and st.getQuestItemsCount(NORMANS_LETTER) :
       htmltext = "30255-05.htm"
   elif npcId == 30210 and int(st.get("cond"))==2 and st.getQuestItemsCount(NORMANS_LETTER) :
      htmltext = "30210-01.htm"
   return htmltext

QUEST       = Quest(167,"167_DwarvenKinship","Dwarven Kinship")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30350)

CREATED.addTalkId(30350)
COMPLETED.addTalkId(30350)

STARTED.addTalkId(30210)
STARTED.addTalkId(30255)
STARTED.addTalkId(30350)

STARTED.addQuestDrop(30350,COLLETTE_LETTER,1)
STARTED.addQuestDrop(30255,NORMANS_LETTER,1)

print "importing quests: 167: Dwarven Kinship"
