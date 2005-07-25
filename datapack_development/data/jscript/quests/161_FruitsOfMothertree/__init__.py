# Maked by Mr. Have fun! Version 0.2
print "importing quests: 161: Fruits Of Mothertree"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

ANDELLRIAS_LETTER_ID = 1036
MOTHERTREE_FRUIT_ID = 1037
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      htmltext = "7362-04.htm"
      st.giveItems(ANDELLRIAS_LETTER_ID,1)
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7362 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if int(st.get("cond"))<15 :
        if st.getPlayer().getRace().ordinal() != 1 :
          htmltext = "7362-00.htm"
        elif st.getPlayer().getLevel() >= 3 :
          htmltext = "7362-03.htm"
          st.set("cond","1")
          return htmltext
        else:
          htmltext = "7362-02.htm"
      else:
        htmltext = "7362-02.htm"
   elif npcId == 7362 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7362 and int(st.get("cond")) :
      if st.getQuestItemsCount(ANDELLRIAS_LETTER_ID) == 1 and st.getQuestItemsCount(MOTHERTREE_FRUIT_ID) == 0 :
        htmltext = "7362-05.htm"
      elif st.getQuestItemsCount(MOTHERTREE_FRUIT_ID) == 1 and int(st.get("onlyone")) == 0 :
          htmltext = "7362-06.htm"
          st.giveItems(ADENA_ID,500)
          st.addExpAndSp(1000,0)
          st.takeItems(MOTHERTREE_FRUIT_ID,1)
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.set("onlyone","1")
   elif npcId == 7371 and int(st.get("cond"))==1 :
      if st.getQuestItemsCount(ANDELLRIAS_LETTER_ID) == 1 :
        if int(st.get("id")) != 161 :
          st.set("id","161")
          htmltext = "7371-01.htm"
          st.giveItems(MOTHERTREE_FRUIT_ID,1)
          st.takeItems(ANDELLRIAS_LETTER_ID,1)
      elif st.getQuestItemsCount(MOTHERTREE_FRUIT_ID) == 1 :
        htmltext = "7371-02.htm"
   return htmltext

QUEST       = Quest(161,"161_FruitsOfMothertree","Fruits Of Mothertree")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7362)

STARTED.addTalkId(7362)
STARTED.addTalkId(7371)


STARTED.addQuestDrop(7371,MOTHERTREE_FRUIT_ID,1)
STARTED.addQuestDrop(7362,ANDELLRIAS_LETTER_ID,1)
