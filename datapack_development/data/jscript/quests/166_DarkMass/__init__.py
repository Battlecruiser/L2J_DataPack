# Maked by Mr. Have fun! Version 0.2
print "importing quests: 166: Dark Mass"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

UNDRES_LETTER_ID = 1088
CEREMONIAL_DAGGER_ID = 1089
DREVIANT_WINE_ID = 1090
GARMIELS_SCRIPTURE_ID = 1091
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      htmltext = "7130-04.htm"
      st.giveItems(UNDRES_LETTER_ID,1)
      st.set("cond","1")
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
   if npcId == 7130 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if int(st.get("cond"))<15 :
        if st.getPlayer().getRace().ordinal() != 2 and st.getPlayer().getRace().ordinal() != 0 :
          htmltext = "7130-00.htm"
        elif st.getPlayer().getLevel() >= 2 :
          htmltext = "7130-03.htm"
          return htmltext
        else:
          htmltext = "7130-02.htm"
      else:
        htmltext = "7130-02.htm"
   elif npcId == 7130 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7130 and int(st.get("cond"))==1 and st.getQuestItemsCount(UNDRES_LETTER_ID)==1 and (st.getQuestItemsCount(GARMIELS_SCRIPTURE_ID)<1 or st.getQuestItemsCount(DREVIANT_WINE_ID)<1 or st.getQuestItemsCount(CEREMONIAL_DAGGER_ID)<1) :
      htmltext = "7130-05.htm"
   elif npcId == 7135 and int(st.get("cond"))==1 and st.getQuestItemsCount(UNDRES_LETTER_ID)==1 and st.getQuestItemsCount(CEREMONIAL_DAGGER_ID)==0 :
      st.giveItems(CEREMONIAL_DAGGER_ID,1)
      htmltext = "7135-01.htm"
   elif npcId == 7135 and int(st.get("cond"))==1 and st.getQuestItemsCount(CEREMONIAL_DAGGER_ID)==1 :
      htmltext = "7135-02.htm"
   elif npcId == 7139 and int(st.get("cond"))==1 and st.getQuestItemsCount(UNDRES_LETTER_ID)==1 and st.getQuestItemsCount(DREVIANT_WINE_ID)==0 :
      st.giveItems(DREVIANT_WINE_ID,1)
      htmltext = "7139-01.htm"
   elif npcId == 7139 and int(st.get("cond"))==1 and st.getQuestItemsCount(DREVIANT_WINE_ID)==1 :
      htmltext = "7139-02.htm"
   elif npcId == 7143 and int(st.get("cond"))==1 and st.getQuestItemsCount(UNDRES_LETTER_ID)==1 and st.getQuestItemsCount(GARMIELS_SCRIPTURE_ID)==0 :
      st.giveItems(GARMIELS_SCRIPTURE_ID,1)
      htmltext = "7143-01.htm"
   elif npcId == 7143 and int(st.get("cond"))==1 and st.getQuestItemsCount(GARMIELS_SCRIPTURE_ID)==1 :
      htmltext = "7143-02.htm"
   elif npcId == 7130 and int(st.get("cond"))==1 and st.getQuestItemsCount(UNDRES_LETTER_ID)==1 and st.getQuestItemsCount(CEREMONIAL_DAGGER_ID)==1 and st.getQuestItemsCount(DREVIANT_WINE_ID)==1 and st.getQuestItemsCount(GARMIELS_SCRIPTURE_ID)==1 and int(st.get("onlyone"))==0 :
      if int(st.get("id")) != 166 :
        st.set("id","166")
        htmltext = "7130-06.htm"
        st.takeItems(CEREMONIAL_DAGGER_ID,1)
        st.takeItems(DREVIANT_WINE_ID,1)
        st.takeItems(GARMIELS_SCRIPTURE_ID,1)
        st.takeItems(UNDRES_LETTER_ID,1)
        st.giveItems(ADENA_ID,250)
        st.addExpAndSp(500,0)
        st.set("cond","0")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
        st.set("onlyone","1")
   return htmltext

QUEST       = Quest(166,"166_DarkMass","Dark Mass")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7130)

STARTED.addTalkId(7130)
STARTED.addTalkId(7135)
STARTED.addTalkId(7139)
STARTED.addTalkId(7143)


STARTED.addQuestDrop(7135,CEREMONIAL_DAGGER_ID,1)
STARTED.addQuestDrop(7139,DREVIANT_WINE_ID,1)
STARTED.addQuestDrop(7143,GARMIELS_SCRIPTURE_ID,1)
STARTED.addQuestDrop(7130,UNDRES_LETTER_ID,1)
