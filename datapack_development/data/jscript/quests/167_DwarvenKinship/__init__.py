# Made by Mr. Have fun! Version 0.2
# version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

COLLETTE_LETTER_ID = 1076
NORMANS_LETTER_ID = 1106
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      htmltext = "7350-04.htm"
      st.giveItems(COLLETTE_LETTER_ID,1)
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
    elif event == "167_1" :
          htmltext = "7255-03.htm"
          st.set("cond","2")
          st.takeItems(COLLETTE_LETTER_ID,1)
          st.giveItems(NORMANS_LETTER_ID,1)
          st.giveItems(ADENA_ID,1000)
    elif event == "167_2" :
          st.takeItems(COLLETTE_LETTER_ID,1)
          htmltext = "7255-04.htm"
          st.giveItems(ADENA_ID,3000)
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.set("onlyone","1")
    elif event == "167_3" :
          htmltext = "7210-02.htm"
          st.takeItems(NORMANS_LETTER_ID,1)
          st.giveItems(ADENA_ID,5100)
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.set("onlyone","1")
    return htmltext


 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
     st.set("onlyone","0")
   if npcId == 7350 and int(st.get("cond"))==0 :
      if int(st.get("onlyone"))==1 :
         htmltext = "<html><head><body>This quest have already been completed.</body></html>"
      else :
         if st.getPlayer().getLevel() >= 15 :
            htmltext = "7350-03.htm"
         else:
            htmltext = "7350-02.htm"
            st.exitQuest(1)
   elif npcId == 7350 and int(st.get("cond"))==1 and st.getQuestItemsCount(COLLETTE_LETTER_ID) :
      htmltext = "7350-05.htm"
   elif npcId == 7255 and int(st.get("cond"))==1 and st.getQuestItemsCount(COLLETTE_LETTER_ID) :
      htmltext = "7255-01.htm"
   elif npcId == 7255 and int(st.get("cond"))==2 and st.getQuestItemsCount(NORMANS_LETTER_ID) :
      htmltext = "7255-05.htm"
   elif npcId == 7210 and int(st.get("cond"))==2 and st.getQuestItemsCount(NORMANS_LETTER_ID) :
      htmltext = "7210-01.htm"
   return htmltext

QUEST       = Quest(167,"167_DwarvenKinship","Dwarven Kinship")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7350)

CREATED.addTalkId(7350)

STARTED.addTalkId(7210)
STARTED.addTalkId(7255)
STARTED.addTalkId(7350)


STARTED.addQuestDrop(7350,CALCULAINS_LETTER_ID,1)
STARTED.addQuestDrop(7255,NORMANS_LETTER_ID,1)

print "importing quests: 167: Dwarven Kinship"
