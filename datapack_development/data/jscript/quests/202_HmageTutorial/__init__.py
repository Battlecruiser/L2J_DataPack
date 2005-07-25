# Maked by Mr. Have fun! Version 0.2
print "importing quests: 202: Hmage Tutorial"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

RECOMMENDATION_02_ID = 1068
WORLD_MAP_ID = 1665
FOX_FANG2_ID = 1858

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
   if int(st.get("onlyone")) == 0 and int(st.get("cond")) == 0 and st.getPlayer().getLevel()<10 and st.getPlayer().getClassId().getId() == 0x0a :
     if npcId == 7018 :
        htmltext = "7018-01.htm"
     elif npcId == 7019 :
        htmltext = "7019-01.htm"
     elif npcId == 7020 :
        htmltext = "7020-01.htm"
     elif npcId == 7021 :
        htmltext = "7021-01.htm"
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.set("cond","1")
   elif st.getPlayer().getClassId().getId() != 0x0a :
     if npcId == 7018 :
        htmltext = "7018-06.htm"
     elif npcId == 7019 :
        htmltext = "7019-06.htm"
     elif npcId == 7020 :
        htmltext = "7020-06.htm"
     elif npcId == 7021 :
        htmltext = "7021-06.htm"
   elif st.getPlayer().getLevel() >= 10 :
     if npcId == 7018 :
        htmltext = "7018-05.htm"
     elif npcId == 7019 :
        htmltext = "7019-05.htm"
     elif npcId == 7020 :
        htmltext = "7020-05.htm"
     elif npcId == 7021 :
        htmltext = "7021-05.htm"
   elif npcId == 7018 and int(st.get("cond")) and st.getQuestItemsCount(RECOMMENDATION_02_ID)==0 :
      if st.getQuestItemsCount(FOX_FANG2_ID) == 4 :
        if int(st.get("id")) != 202 :
          st.set("id","202")
          st.takeItems(FOX_FANG2_ID,st.getQuestItemsCount(FOX_FANG2_ID))
          st.giveItems(RECOMMENDATION_02_ID,1)
          st.giveItems(WORLD_MAP_ID,1)
          htmltext = "7018-02.htm"
          st.set("cond","1")
      elif st.getQuestItemsCount(FOX_FANG2_ID)<4 :
          htmltext = "7018-03.htm"
   elif npcId == 7018 and int(st.get("cond")) and st.getQuestItemsCount(RECOMMENDATION_02_ID) :
      htmltext = "7018-04.htm"
   elif npcId == 7019 and int(st.get("cond")) and st.getQuestItemsCount(RECOMMENDATION_02_ID)==0 :
      if st.getQuestItemsCount(FOX_FANG2_ID) == 4 :
        st.takeItems(FOX_FANG2_ID,st.getQuestItemsCount(FOX_FANG2_ID))
        st.giveItems(RECOMMENDATION_02_ID,1)
        st.giveItems(WORLD_MAP_ID,1)
        htmltext = "7019-02.htm"
        st.set("cond","1")
      elif st.getQuestItemsCount(FOX_FANG2_ID)<4 :
          htmltext = "7019-03.htm"
   elif npcId == 7019 and int(st.get("cond")) and st.getQuestItemsCount(RECOMMENDATION_02_ID) :
      htmltext = "7019-04.htm"
   elif npcId == 7021 and int(st.get("cond")) and st.getQuestItemsCount(RECOMMENDATION_02_ID)==0 :
      if st.getQuestItemsCount(FOX_FANG2_ID) == 4 :
        st.takeItems(FOX_FANG2_ID,st.getQuestItemsCount(FOX_FANG2_ID))
        st.giveItems(RECOMMENDATION_02_ID,1)
        st.giveItems(WORLD_MAP_ID,1)
        htmltext = "7021-02.htm"
        st.set("cond","1")
      elif st.getQuestItemsCount(FOX_FANG2_ID)<4 :
          htmltext = "7021-03.htm"
   elif npcId == 7021 and int(st.get("cond")) and st.getQuestItemsCount(RECOMMENDATION_02_ID) :
      htmltext = "7021-04.htm"
   elif npcId == 7020 and int(st.get("cond")) and st.getQuestItemsCount(RECOMMENDATION_02_ID)==0 :
      if st.getQuestItemsCount(FOX_FANG2_ID) == 4 :
        st.takeItems(FOX_FANG2_ID,st.getQuestItemsCount(FOX_FANG2_ID))
        st.giveItems(RECOMMENDATION_02_ID,1)
        st.giveItems(WORLD_MAP_ID,1)
        htmltext = "7020-02.htm"
        st.set("cond","1")
      elif st.getQuestItemsCount(FOX_FANG2_ID)<4 :
          htmltext = "7020-03.htm"
   elif npcId == 7020 and int(st.get("cond")) and st.getQuestItemsCount(RECOMMENDATION_02_ID) :
      htmltext = "7020-04.htm"
   elif npcId == 7017 and int(st.get("cond")) :
      if st.getQuestItemsCount(RECOMMENDATION_02_ID) and int(st.get("onlyone")) == 0 :
        htmltext = "7017-01.htm"
        st.addExpAndSp(0,50)
        st.takeItems(RECOMMENDATION_02_ID,1)
        st.set("cond","0")
        st.set("onlyone","1")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
      else:
        htmltext = "7017001.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 12082 :
      st.set("id","0")
      if int(st.get("cond")) and st.getQuestItemsCount(FOX_FANG2_ID)<4 :
        if int(st.get("cond")) == 1 :
            st.giveItems(FOX_FANG2_ID,1)
            st.playSound("ItemSound.quest_itemget")
            st.set("cond","2")
            st.playSound("ItemSound.quest_tutorial")
        elif int(st.get("cond")) == 2 and st.getQuestItemsCount(FOX_FANG2_ID) == 3 :
            st.giveItems(FOX_FANG2_ID,1)
            st.playSound("ItemSound.quest_middle")
            st.set("cond","3")
        else :
            st.giveItems(FOX_FANG2_ID,1)
            st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(202,"202_HmageTutorial","Hmage Tutorial")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7018)
QUEST.addStartNpc(7019)
QUEST.addStartNpc(7021)
QUEST.addStartNpc(7020)

STARTED.addTalkId(7017)
STARTED.addTalkId(7018)
STARTED.addTalkId(7019)
STARTED.addTalkId(7020)
STARTED.addTalkId(7021)

STARTED.addKillId(12082)

STARTED.addQuestDrop(12082,FOX_FANG2_ID,1)
STARTED.addQuestDrop(7018,RECOMMENDATION_02_ID,1)
STARTED.addQuestDrop(7019,RECOMMENDATION_02_ID,1)
STARTED.addQuestDrop(7021,RECOMMENDATION_02_ID,1)
STARTED.addQuestDrop(7020,RECOMMENDATION_02_ID,1)
