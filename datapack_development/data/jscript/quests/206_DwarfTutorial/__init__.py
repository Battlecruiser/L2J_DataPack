# Maked by Mr. Have fun! Version 0.2
print "importing quests: 206: Dwarf Tutorial"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

LICENSE_OF_MINER_ID = 1498
FOX_FANG6_ID = 1862
WORLD_MAP_2_ID = 1863

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
   if npcId == 7530 and int(st.get("cond"))==0 :
      st.set("id","0")
      if int(st.get("onlyone")) == 0 and int(st.get("cond")) == 0 and st.getPlayer().getLevel() < 10 and st.getPlayer().getRace().ordinal() == 4 :
        htmltext = "7530-01.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.playSound("ItemSound.quest_tutorial")
      elif st.getPlayer().getRace().ordinal() != 4 :
          htmltext = "7530-06.htm"
      elif st.getPlayer().getLevel() >= 10 :
          htmltext = "7530-05.htm"
   elif npcId == 7529 and int(st.get("cond"))==0 :
      st.set("id","0")
      if int(st.get("onlyone")) == 0 and int(st.get("cond")) == 0 and st.getPlayer().getLevel() < 10 and st.getPlayer().getRace().ordinal() == 4 :
        htmltext = "7529-01.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.playSound("ItemSound.quest_tutorial")
      elif st.getPlayer().getRace().ordinal() != 4 :
          htmltext = "7529-06.htm"
      elif st.getPlayer().getLevel() >= 10 :
          htmltext = "7529-05.htm"
   elif npcId == 7529 and int(st.get("cond")) and st.getQuestItemsCount(LICENSE_OF_MINER_ID)==0 :
      if st.getQuestItemsCount(FOX_FANG6_ID) == 4 :
        if int(st.get("id")) != 206 :
          st.set("id","206")
          st.takeItems(FOX_FANG6_ID,st.getQuestItemsCount(FOX_FANG6_ID))
          st.giveItems(LICENSE_OF_MINER_ID,1)
          st.giveItems(WORLD_MAP_2_ID,1)
          htmltext = "7529-02.htm"
          st.set("cond","1")
      elif st.getQuestItemsCount(FOX_FANG6_ID) < 4 :
          htmltext = "7529-03.htm"
   elif npcId == 7529 and int(st.get("cond")) and st.getQuestItemsCount(LICENSE_OF_MINER_ID) :
      htmltext = "7529-04.htm"
   elif npcId == 7530 and int(st.get("cond")) and st.getQuestItemsCount(LICENSE_OF_MINER_ID)==0 :
      if st.getQuestItemsCount(FOX_FANG6_ID) == 4 :
        st.takeItems(FOX_FANG6_ID,st.getQuestItemsCount(FOX_FANG6_ID))
        st.giveItems(LICENSE_OF_MINER_ID,1)
        st.giveItems(WORLD_MAP_2_ID,1)
        htmltext = "7530-02.htm"
        st.set("cond","1")
      elif st.getQuestItemsCount(FOX_FANG6_ID) < 4 :
          htmltext = "7530-03.htm"
   elif npcId == 7530 and int(st.get("cond")) and st.getQuestItemsCount(LICENSE_OF_MINER_ID) :
      htmltext = "7530-04.htm"
   elif npcId == 7528 and int(st.get("cond")) :
      if st.getQuestItemsCount(LICENSE_OF_MINER_ID) and int(st.get("onlyone")) == 0 :
        htmltext = "7528-01.htm"
        st.addExpAndSp(0,50)
        st.takeItems(LICENSE_OF_MINER_ID,1)
        st.set("cond","0")
        st.set("onlyone","1")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 12082 :
      st.set("id","0")
      if int(st.get("cond")) and st.getQuestItemsCount(FOX_FANG6_ID) < 4 :
        if int(st.get("cond")) <= 0 :
          st.playSound("ItemSound.quest_tutorial")
          st.set("cond","1")
        elif int(st.get("cond")) == 1 :
            st.giveItems(FOX_FANG6_ID,1)
            st.playSound("ItemSound.quest_itemget")
            st.set("cond","2")
            st.playSound("ItemSound.quest_tutorial")
        elif int(st.get("cond")) == 2 and st.getQuestItemsCount(FOX_FANG6_ID) == 3 :
            st.giveItems(FOX_FANG6_ID,1)
            st.playSound("ItemSound.quest_middle")
            st.set("cond","3")
        elif int(st.get("cond")) == 2 and st.getQuestItemsCount(FOX_FANG6_ID) < 3 :
            st.giveItems(FOX_FANG6_ID,1)
            st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(206,"206_DwarfTutorial","Dwarf Tutorial")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7529)
QUEST.addStartNpc(7530)

STARTED.addTalkId(7528)
STARTED.addTalkId(7529)
STARTED.addTalkId(7530)

STARTED.addKillId(12082)

STARTED.addQuestDrop(12082,FOX_FANG6_ID,1)
STARTED.addQuestDrop(7529,LICENSE_OF_MINER_ID,1)
STARTED.addQuestDrop(7530,LICENSE_OF_MINER_ID,1)
