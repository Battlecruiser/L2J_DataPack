# Maked by Mr. Have fun! Version 0.2
print "importing quests: 324: Sweetest Venom"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

VENOM_SAC_ID = 1077
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      htmltext = "7351-04.htm"
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
   if npcId == 7351 and int(st.get("cond"))==0 :
      if int(st.get("cond"))<15 :
        if st.getPlayer().getLevel() >= 18 :
          htmltext = "7351-03.htm"
          st.set("cond","1")
          return htmltext
        else:
          htmltext = "7351-02.htm"
      else:
        htmltext = "7351-02.htm"
   elif npcId == 7351 and int(st.get("cond"))==1 and st.getQuestItemsCount(VENOM_SAC_ID)<10 :
      htmltext = "7351-05.htm"
   elif npcId == 7351 and int(st.get("cond"))==1 and st.getQuestItemsCount(VENOM_SAC_ID)>=10 :
      if int(st.get("id")) != 324 :
        st.set("id","324")
        st.takeItems(VENOM_SAC_ID,st.getQuestItemsCount(VENOM_SAC_ID))
        st.giveItems(ADENA_ID,3500)
        st.set("cond","0")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
        htmltext = "7351-06.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 34 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(VENOM_SAC_ID)<10 :
          if st.getRandom(100)<12 :
            st.giveItems(VENOM_SAC_ID,1)
            if st.getQuestItemsCount(VENOM_SAC_ID) == 10 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 38 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(VENOM_SAC_ID)<10 :
          if st.getRandom(100)<24 :
            st.giveItems(VENOM_SAC_ID,1)
            if st.getQuestItemsCount(VENOM_SAC_ID) == 10 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 43 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(VENOM_SAC_ID)<10 :
          if st.getRandom(100)<36 :
            st.giveItems(VENOM_SAC_ID,1)
            if st.getQuestItemsCount(VENOM_SAC_ID) == 10 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(324,"324_SweetestVenom","Sweetest Venom")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7351)

STARTED.addTalkId(7351)

STARTED.addKillId(34)
STARTED.addKillId(38)
STARTED.addKillId(43)

STARTED.addQuestDrop(34,VENOM_SAC_ID,1)
STARTED.addQuestDrop(38,VENOM_SAC_ID,1)
STARTED.addQuestDrop(43,VENOM_SAC_ID,1)
