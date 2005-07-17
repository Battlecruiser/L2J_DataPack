# Maked by Mr. Have fun! Version 0.2
print "importing quests: 265: Chains Of Slavery"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

IMP_SHACKLES_ID = 1368
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        htmltext = "7357-03.htm"
    elif event == "7357_1" :
            htmltext = "7357-06.htm"
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
    elif event == "7357_2" :
            htmltext = "7357-07.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7357 and int(st.get("cond"))==0 :
        if int(st.get("cond"))<15 :
          if st.getPlayer().getLevel() >= 5 and st.getPlayer().getRace().ordinal() == 2 :
            htmltext = "7357-02.htm"
            st.set("cond","1")
            return htmltext
          elif st.getPlayer().getRace().ordinal() != 2 :
            htmltext = "7357-00.htm"
          elif st.getPlayer().getLevel()<5 :
            htmltext = "7357-01.htm"
        else:
          htmltext = "7357-01.htm"
   elif npcId == 7357 and int(st.get("cond"))==1 :
        if st.getQuestItemsCount(IMP_SHACKLES_ID)>0 :
          if int(st.get("id")) != 265 :
            st.set("id","265")
            st.giveItems(ADENA_ID,13*st.getQuestItemsCount(IMP_SHACKLES_ID))
            st.takeItems(IMP_SHACKLES_ID,st.getQuestItemsCount(IMP_SHACKLES_ID))
            htmltext = "7357-05.htm"
        else:
          htmltext = "7357-04.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 4 :
      st.set("id","0")
      if int(st.get("cond")) :
        if st.getRandom(10)<5 :
          st.giveItems(IMP_SHACKLES_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 5 :
      st.set("id","0")
      if int(st.get("cond")) :
        if st.getRandom(10)<6 :
          st.giveItems(IMP_SHACKLES_ID,1)
          st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(265,"265_ChainsOfSlavery","Chains Of Slavery")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7357)

STARTED.addTalkId(7357)

STARTED.addKillId(4)
STARTED.addKillId(5)

STARTED.addQuestDrop(4,IMP_SHACKLES_ID,1)
STARTED.addQuestDrop(5,IMP_SHACKLES_ID,1)
