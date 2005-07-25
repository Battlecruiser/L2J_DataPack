# Maked by Mr. Have fun! Version 0.2
print "importing quests: 201: Hfighter Tutorial"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

RECOMMENDATION_01_ID = 1067
WORLD_MAP_ID = 1665
FOX_FANG1_ID = 1857

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
   if int(st.get("onlyone")) == 0 and int(st.get("cond")) == 0 and st.getPlayer().getLevel()<10 and st.getPlayer().getClassId().getId() == 0x00 :
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.set("cond","1")
	if npcId == 7009:
          htmltext = "7009-01.htm"
	elif npcId == 7012:
          htmltext = "7012-01.htm"
	elif npcId == 7056:
          htmltext = "7056-01.htm"
	elif npcId == 7011:
          htmltext = "7011-01.htm"
   elif st.getPlayer().getClassId().getId() != 0x00 :
	if npcId == 7009:
          htmltext = "7009-06.htm"
	elif npcId == 7012:
          htmltext = "7012-06.htm"
	elif npcId == 7056:
          htmltext = "7056-06.htm"
	elif npcId == 7011:
          htmltext = "7011-06.htm"
   elif st.getPlayer().getLevel() >= 10 :
	if npcId == 7009:
          htmltext = "7009-05.htm"
	elif npcId == 7012:
          htmltext = "7012-05.htm"
	elif npcId == 7056:
          htmltext = "7056-05.htm"
	elif npcId == 7011:
          htmltext = "7011-05.htm"
   elif npcId == 7009 and int(st.get("cond")) and st.getQuestItemsCount(RECOMMENDATION_01_ID)==0 :
      if st.getQuestItemsCount(FOX_FANG1_ID) >= 4 :
        st.takeItems(FOX_FANG1_ID,st.getQuestItemsCount(FOX_FANG1_ID))
        st.giveItems(RECOMMENDATION_01_ID,1)
        st.giveItems(WORLD_MAP_ID,1)
        htmltext = "7009-02.htm"
        st.set("cond","1")
      elif st.getQuestItemsCount(FOX_FANG1_ID)<4 :
          htmltext = "7009-03.htm"
   elif npcId == 7009 and int(st.get("cond")) and st.getQuestItemsCount(RECOMMENDATION_01_ID) :
      htmltext = "7009-04.htm"
   elif npcId == 7012 and int(st.get("cond")) and st.getQuestItemsCount(RECOMMENDATION_01_ID)==0 :
      if st.getQuestItemsCount(FOX_FANG1_ID) >= 4 :
        if int(st.get("id")) != 201 :
          st.set("id","201")
          st.takeItems(FOX_FANG1_ID,st.getQuestItemsCount(FOX_FANG1_ID))
          st.giveItems(RECOMMENDATION_01_ID,1)
          st.giveItems(WORLD_MAP_ID,1)
          htmltext = "7012-02.htm"
          st.set("cond","1")
      elif st.getQuestItemsCount(FOX_FANG1_ID)<4 :
          htmltext = "7012-03.htm"
   elif npcId == 7012 and int(st.get("cond")) and st.getQuestItemsCount(RECOMMENDATION_01_ID) :
      htmltext = "7012-04.htm"
   elif npcId == 7056 and int(st.get("cond")) and st.getQuestItemsCount(RECOMMENDATION_01_ID)==0 :
      if st.getQuestItemsCount(FOX_FANG1_ID) >= 4 :
        st.takeItems(FOX_FANG1_ID,st.getQuestItemsCount(FOX_FANG1_ID))
        st.giveItems(RECOMMENDATION_01_ID,1)
        st.giveItems(WORLD_MAP_ID,1)
        htmltext = "7056-02.htm"
        st.set("cond","1")
      elif st.getQuestItemsCount(FOX_FANG1_ID)<4 :
          htmltext = "7056-03.htm"
   elif npcId == 7056 and int(st.get("cond")) and st.getQuestItemsCount(RECOMMENDATION_01_ID) :
      htmltext = "7056-04.htm"
   elif npcId == 7011 and int(st.get("cond")) and st.getQuestItemsCount(RECOMMENDATION_01_ID)==0 :
      if st.getQuestItemsCount(FOX_FANG1_ID) >= 4 :
        st.takeItems(FOX_FANG1_ID,st.getQuestItemsCount(FOX_FANG1_ID))
        st.giveItems(RECOMMENDATION_01_ID,1)
        st.giveItems(WORLD_MAP_ID,1)
        htmltext = "7011-02.htm"
        st.set("cond","1")
      elif st.getQuestItemsCount(FOX_FANG1_ID)<4 :
          htmltext = "7011-03.htm"
   elif npcId == 7011 and int(st.get("cond")) and st.getQuestItemsCount(RECOMMENDATION_01_ID) :
      htmltext = "7011-04.htm"
   elif npcId == 7008 and int(st.get("cond")) :
      if st.getQuestItemsCount(RECOMMENDATION_01_ID) and int(st.get("onlyone")) == 0 :
        htmltext = "7008-01.htm"
        st.addExpAndSp(0,50)
        st.takeItems(RECOMMENDATION_01_ID,1)
        st.set("cond","0")
        st.set("onlyone","1")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
      else:
        htmltext = "7008001.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 12082 :
      st.set("id","0")
      if int(st.get("cond")) and st.getQuestItemsCount(FOX_FANG1_ID)<4 :
        if int(st.get("cond")) == 1 :
            st.giveItems(FOX_FANG1_ID,1)
            st.playSound("ItemSound.quest_itemget")
            st.set("cond","2")
            st.playSound("ItemSound.quest_tutorial")
        elif int(st.get("cond")) == 2 and st.getQuestItemsCount(FOX_FANG1_ID) == 3 :
            st.giveItems(FOX_FANG1_ID,1)
            st.playSound("ItemSound.quest_middle")
            st.set("cond","3")
	else :
            st.giveItems(FOX_FANG1_ID,1)
            st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(201,"201_HfighterTutorial","Hfighter Tutorial")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7009)
QUEST.addStartNpc(7012)
QUEST.addStartNpc(7056)
QUEST.addStartNpc(7011)

STARTED.addTalkId(7008)
STARTED.addTalkId(7009)
STARTED.addTalkId(7011)
STARTED.addTalkId(7012)
STARTED.addTalkId(7056)

STARTED.addKillId(12082)

STARTED.addQuestDrop(12082,FOX_FANG1_ID,1)
STARTED.addQuestDrop(7009,RECOMMENDATION_01_ID,1)
STARTED.addQuestDrop(7012,RECOMMENDATION_01_ID,1)
STARTED.addQuestDrop(7056,RECOMMENDATION_01_ID,1)
STARTED.addQuestDrop(7011,RECOMMENDATION_01_ID,1)
