# Maked by Mr. Have fun! Version 0.2
print "importing quests: 204: Delf Tutorial"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

BLOOD_OF_JUNDIN_ID = 1070
WORLD_MAP_ID = 1665
FOX_FANG4_ID = 1860

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if int(st.get("onlyone")) == 0 and int(st.get("cond")) == 0 and st.getPlayer().getLevel()<10 and st.getPlayer().getRace().ordinal() == 2 :
      st.setState(STARTED)
      st.set("cond","1")
      st.playSound("ItemSound.quest_accept")
      st.playSound("ItemSound.quest_tutorial")
      if npcId == 7404 :
        htmltext = "7404-01.htm"
      elif npcId == 7131 :
        htmltext = "7131-01.htm"
      elif npcId == 7132 :
        htmltext = "7132-01.htm"
      elif npcId == 7133 :
        htmltext = "7133-01.htm"
   elif st.getPlayer().getRace().ordinal() != 2 :
      if npcId == 7404 :
        htmltext = "7404-06.htm"
      elif npcId == 7131 :
        htmltext = "7131-06.htm"
      elif npcId == 7132 :
        htmltext = "7132-06.htm"
      elif npcId == 7133 :
        htmltext = "7133-06.htm"
   elif st.getPlayer().getLevel() >= 10 :
      if npcId == 7404 :
        htmltext = "7404-05.htm"
      elif npcId == 7131 :
        htmltext = "7131-05.htm"
      elif npcId == 7132 :
        htmltext = "7132-05.htm"
      elif npcId == 7133 :
        htmltext = "7133-05.htm"
   elif npcId == 7404 and int(st.get("cond")) and st.getQuestItemsCount(BLOOD_OF_JUNDIN_ID)==0 :
      if st.getQuestItemsCount(FOX_FANG4_ID) == 4 :
        if int(st.get("id")) != 204 :
          st.set("id","204")
          st.takeItems(FOX_FANG4_ID,st.getQuestItemsCount(FOX_FANG4_ID))
          st.giveItems(BLOOD_OF_JUNDIN_ID,1)
          st.giveItems(WORLD_MAP_ID,1)
          htmltext = "7404-02.htm"
          st.set("cond","1")
      elif st.getQuestItemsCount(FOX_FANG4_ID)<4 :
          htmltext = "7404-03.htm"
   elif npcId == 7404 and int(st.get("cond")) and st.getQuestItemsCount(BLOOD_OF_JUNDIN_ID) :
      htmltext = "7404-04.htm"
   elif npcId == 7131 and int(st.get("cond"))==0 :
      htmltext = "7131001.htm"
   elif npcId == 7131 and int(st.get("cond")) and st.getQuestItemsCount(BLOOD_OF_JUNDIN_ID)==0 :
      if st.getQuestItemsCount(FOX_FANG4_ID) == 4 :
        st.takeItems(FOX_FANG4_ID,st.getQuestItemsCount(FOX_FANG4_ID))
        st.giveItems(BLOOD_OF_JUNDIN_ID,1)
        st.giveItems(WORLD_MAP_ID,1)
        htmltext = "7131-02.htm"
        st.set("cond","1")
      elif st.getQuestItemsCount(FOX_FANG4_ID)<4 :
          htmltext = "7131-03.htm"
   elif npcId == 7131 and int(st.get("cond")) and st.getQuestItemsCount(BLOOD_OF_JUNDIN_ID) :
      htmltext = "7131-04.htm"
   elif npcId == 7132 and int(st.get("cond"))==0 :
      htmltext = "7132001.htm"
   elif npcId == 7132 and int(st.get("cond")) and st.getQuestItemsCount(BLOOD_OF_JUNDIN_ID)==0 :
      if st.getQuestItemsCount(FOX_FANG4_ID) == 4 :
        st.takeItems(FOX_FANG4_ID,st.getQuestItemsCount(FOX_FANG4_ID))
        st.giveItems(BLOOD_OF_JUNDIN_ID,1)
        st.giveItems(WORLD_MAP_ID,1)
        htmltext = "7132-02.htm"
        st.set("cond","1")
      elif st.getQuestItemsCount(FOX_FANG4_ID)<4 :
          htmltext = "7132-03.htm"
   elif npcId == 7132 and int(st.get("cond")) and st.getQuestItemsCount(BLOOD_OF_JUNDIN_ID) :
      htmltext = "7132-04.htm"
   elif npcId == 7133 and int(st.get("cond")) and st.getQuestItemsCount(BLOOD_OF_JUNDIN_ID)==0 :
      if st.getQuestItemsCount(FOX_FANG4_ID) == 4 :
        st.takeItems(FOX_FANG4_ID,st.getQuestItemsCount(FOX_FANG4_ID))
        st.giveItems(BLOOD_OF_JUNDIN_ID,1)
        st.giveItems(WORLD_MAP_ID,1)
        htmltext = "7133-02.htm"
        st.set("cond","1")
      elif st.getQuestItemsCount(FOX_FANG4_ID)<4 :
          htmltext = "7133-03.htm"
   elif npcId == 7133 and int(st.get("cond")) and st.getQuestItemsCount(BLOOD_OF_JUNDIN_ID) :
      htmltext = "7133-04.htm"
   elif npcId == 7129 and int(st.get("cond")) :
      if st.getQuestItemsCount(BLOOD_OF_JUNDIN_ID) and int(st.get("onlyone")) == 0 :
        htmltext = "7129-01.htm"
        st.addExpAndSp(0,50)
        st.takeItems(BLOOD_OF_JUNDIN_ID,1)
        st.set("cond","0")
        st.set("onlyone","1")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
      else:
        htmltext = "7129001.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 12082 :
      st.set("id","0")
      if int(st.get("cond")) and st.getQuestItemsCount(FOX_FANG4_ID)<4 :
        if int(st.get("cond")) <= 0 :
          st.playSound("ItemSound.quest_tutorial")
          st.set("cond","1")
        elif int(st.get("cond")) == 1 :
            st.giveItems(FOX_FANG4_ID,1)
            st.playSound("ItemSound.quest_itemget")
            st.set("cond","2")
            st.playSound("ItemSound.quest_tutorial")
        elif int(st.get("cond")) == 2 and st.getQuestItemsCount(FOX_FANG4_ID) == 3 :
            st.giveItems(FOX_FANG4_ID,1)
            st.playSound("ItemSound.quest_middle")
            st.set("cond","3")
        else :
            st.giveItems(FOX_FANG4_ID,1)
            st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(204,"204_DelfTutorial","Delf Tutorial")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7404)
QUEST.addStartNpc(7131)
QUEST.addStartNpc(7132)
QUEST.addStartNpc(7133)

STARTED.addTalkId(7129)
STARTED.addTalkId(7131)
STARTED.addTalkId(7132)
STARTED.addTalkId(7133)
STARTED.addTalkId(7404)

STARTED.addKillId(12082)

STARTED.addQuestDrop(12082,FOX_FANG4_ID,1)
STARTED.addQuestDrop(7404,BLOOD_OF_JUNDIN_ID,1)
STARTED.addQuestDrop(7131,BLOOD_OF_JUNDIN_ID,1)
STARTED.addQuestDrop(7132,BLOOD_OF_JUNDIN_ID,1)
STARTED.addQuestDrop(7133,BLOOD_OF_JUNDIN_ID,1)
