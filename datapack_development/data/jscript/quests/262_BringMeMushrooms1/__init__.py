# Maked by Mr. Have fun! Version 0.2
print "importing quests: 262: Bring Me Mushrooms1"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

FUNGUS_SAC_ID = 707
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        htmltext = "7137-03.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7137 and int(st.get("cond"))==0 :
        if int(st.get("cond"))<15 :
          if st.getPlayer().getLevel() >= 8 :
            htmltext = "7137-02.htm"
            return htmltext
          else:
            htmltext = "7137-01.htm"
        else:
          htmltext = "7137-01.htm"
   elif npcId == 7137 and int(st.get("cond"))==1 and st.getQuestItemsCount(FUNGUS_SAC_ID)<10 :
        htmltext = "7137-04.htm"
   elif npcId == 7137 and int(st.get("cond"))==1 and st.getQuestItemsCount(FUNGUS_SAC_ID)>=10 :
        if int(st.get("id")) != 262 :
          st.set("id","262")
          st.giveItems(ADENA_ID,2000)
          st.takeItems(FUNGUS_SAC_ID,st.getQuestItemsCount(FUNGUS_SAC_ID))
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          htmltext = "7137-05.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 7 :
        st.set("id","0")
        if st.getQuestItemsCount(FUNGUS_SAC_ID)<10 and int(st.get("cond")) :
          if st.getRandom(10)<3 :
            st.giveItems(FUNGUS_SAC_ID,1)
            if st.getQuestItemsCount(FUNGUS_SAC_ID) == 10 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 400 :
        st.set("id","0")
        if st.getQuestItemsCount(FUNGUS_SAC_ID)<10 and int(st.get("cond")) :
          if st.getRandom(10)<4 :
            st.giveItems(FUNGUS_SAC_ID,1)
            if st.getQuestItemsCount(FUNGUS_SAC_ID) == 10 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(262,"262_BringMeMushrooms1","Bring Me Mushrooms1")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7137)

STARTING.addTalkId(7137)

STARTED.addTalkId(7137)

STARTED.addKillId(400)
STARTED.addKillId(7)

STARTED.addQuestDrop(7,FUNGUS_SAC_ID,1)
STARTED.addQuestDrop(400,FUNGUS_SAC_ID,1)
