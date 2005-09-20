# Maked by Mr. Have fun! Version 0.2
print "importing quests: 313: Collect Spores"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

FUNGUS_SAC1_ID = 1118
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
        htmltext = "7150-05.htm"
    elif event == "313_1" :
            htmltext = "7150-04.htm"
            return htmltext
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7150 and int(st.get("cond"))==0 :
        if int(st.get("cond"))<15 :
          if st.getPlayer().getLevel() >= 8 :
            htmltext = "7150-03.htm"
            st.set("cond","1")
            return htmltext
          else:
            htmltext = "7150-02.htm"
          htmltext = "7150-02.htm"
   elif npcId == 7150 and int(st.get("cond"))!=0 and st.getQuestItemsCount(FUNGUS_SAC1_ID)<10 :
        htmltext = "7150-06.htm"
   elif npcId == 7150 and int(st.get("cond"))!=0 and st.getQuestItemsCount(FUNGUS_SAC1_ID)>=10 :
        if int(st.get("id")) != 313 :
          st.set("id","313")
          st.takeItems(FUNGUS_SAC1_ID,st.getQuestItemsCount(FUNGUS_SAC1_ID))
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.giveItems(ADENA_ID,2000)
          htmltext = "7150-07.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 509 :
        st.set("id","0")
        if int(st.get("cond")) != 0 and st.getQuestItemsCount(FUNGUS_SAC1_ID)<10 :
          st.giveItems(FUNGUS_SAC1_ID,1)
          if st.getQuestItemsCount(FUNGUS_SAC1_ID) == 10 :
            st.playSound("ItemSound.quest_middle")
          else:
            st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(313,"313_CollectSpores","Collect Spores")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7150)

STARTING.addTalkId(7150)

STARTED.addTalkId(7150)

STARTED.addKillId(509)

STARTED.addQuestDrop(509,FUNGUS_SAC1_ID,1)
