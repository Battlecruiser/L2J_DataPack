# Maked by Mr. Have fun! Version 0.2
print "importing quests: 203: Elf Tutorial"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

LEAF_OF_MOTHERTREE_ID = 1069
WORLD_MAP_ID = 1665
FOX_FANG3_ID = 1859

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
   if int(st.get("onlyone")) == 0 and int(st.get("cond")) == 0 and st.getPlayer().getLevel()<10 and st.getPlayer().getRace().ordinal() == 1 :
        if npcId == 7400 :
          htmltext = "7400-01.htm"
        elif npcId == 7401 :
          htmltext = "7401-01.htm"
        elif npcId == 7402 :
          htmltext = "7402-01.htm"
        elif npcId == 7403 :
          htmltext = "7403-01.htm"
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.set("cond","1")
   elif st.getPlayer().getRace().ordinal() != 1 :
        if npcId == 7400 :
          htmltext = "7400-06.htm"
        elif npcId == 7401 :
          htmltext = "7401-06.htm"
        elif npcId == 7402 :
          htmltext = "7402-06.htm"
        elif npcId == 7403 :
          htmltext = "7403-06.htm"
   elif st.getPlayer().getLevel() >= 10 :
        if npcId == 7400 :
          htmltext = "7400-05.htm"
        elif npcId == 7401 :
          htmltext = "7401-05.htm"
        elif npcId == 7402 :
          htmltext = "7402-05.htm"
        elif npcId == 7403 :
          htmltext = "7403-05.htm"
   elif npcId == 7400 and int(st.get("cond")) and st.getQuestItemsCount(LEAF_OF_MOTHERTREE_ID)==0 :
      if st.getQuestItemsCount(FOX_FANG3_ID) == 4 :
        if int(st.get("id")) != 203 :
          st.set("id","203")
          st.takeItems(FOX_FANG3_ID,st.getQuestItemsCount(FOX_FANG3_ID))
          st.giveItems(LEAF_OF_MOTHERTREE_ID,1)
          st.giveItems(WORLD_MAP_ID,1)
          htmltext = "7400-02.htm"
          st.set("cond","1")
      elif st.getQuestItemsCount(FOX_FANG3_ID)<4 :
          htmltext = "7400-03.htm"
   elif npcId == 7400 and int(st.get("cond")) and st.getQuestItemsCount(LEAF_OF_MOTHERTREE_ID) :
      htmltext = "7400-04.htm"
   elif npcId == 7401 and int(st.get("cond")) and st.getQuestItemsCount(LEAF_OF_MOTHERTREE_ID)==0 :
      if st.getQuestItemsCount(FOX_FANG3_ID) == 4 :
        st.takeItems(FOX_FANG3_ID,st.getQuestItemsCount(FOX_FANG3_ID))
        st.giveItems(LEAF_OF_MOTHERTREE_ID,1)
        st.giveItems(WORLD_MAP_ID,1)
        htmltext = "7401-02.htm"
        st.set("cond","1")
      elif st.getQuestItemsCount(FOX_FANG3_ID)<4 :
          htmltext = "7401-03.htm"
   elif npcId == 7401 and int(st.get("cond")) and st.getQuestItemsCount(LEAF_OF_MOTHERTREE_ID) :
      htmltext = "7401-04.htm"
   elif npcId == 7402 and int(st.get("cond")) and st.getQuestItemsCount(LEAF_OF_MOTHERTREE_ID)==0 :
      if st.getQuestItemsCount(FOX_FANG3_ID) == 4 :
        st.takeItems(FOX_FANG3_ID,st.getQuestItemsCount(FOX_FANG3_ID))
        st.giveItems(LEAF_OF_MOTHERTREE_ID,1)
        st.giveItems(WORLD_MAP_ID,1)
        htmltext = "7402-02.htm"
        st.set("cond","1")
      elif st.getQuestItemsCount(FOX_FANG3_ID)<4 :
          htmltext = "7402-03.htm"
   elif npcId == 7402 and int(st.get("cond")) and st.getQuestItemsCount(LEAF_OF_MOTHERTREE_ID) :
      htmltext = "7402-04.htm"
   elif npcId == 7403 and int(st.get("cond")) and st.getQuestItemsCount(LEAF_OF_MOTHERTREE_ID)==0 :
      if st.getQuestItemsCount(FOX_FANG3_ID) == 4 :
        st.takeItems(FOX_FANG3_ID,st.getQuestItemsCount(FOX_FANG3_ID))
        st.giveItems(LEAF_OF_MOTHERTREE_ID,1)
        st.giveItems(WORLD_MAP_ID,1)
        htmltext = "7403-02.htm"
        st.set("cond","1")
      elif st.getQuestItemsCount(FOX_FANG3_ID)<4 :
          htmltext = "7403-03.htm"
   elif npcId == 7403 and int(st.get("cond")) and st.getQuestItemsCount(LEAF_OF_MOTHERTREE_ID) :
      htmltext = "7403-04.htm"
   elif npcId == 7370 and int(st.get("cond")) :
      if st.getQuestItemsCount(LEAF_OF_MOTHERTREE_ID) and int(st.get("onlyone")) == 0 :
        htmltext = "7370-01.htm"
        st.addExpAndSp(0,50)
        st.takeItems(LEAF_OF_MOTHERTREE_ID,1)
        st.set("cond","0")
        st.set("onlyone","1")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
      else:
        htmltext = "7370001.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 12082 :
      st.set("id","0")
      if int(st.get("cond")) and st.getQuestItemsCount(FOX_FANG3_ID)<4 :
        if int(st.get("cond")) == 1 :
            st.giveItems(FOX_FANG3_ID,1)
            st.playSound("ItemSound.quest_itemget")
            st.set("cond","2")
            st.playSound("ItemSound.quest_tutorial")
        elif int(st.get("cond")) == 2 and st.getQuestItemsCount(FOX_FANG3_ID) == 3 :
            st.giveItems(FOX_FANG3_ID,1)
            st.playSound("ItemSound.quest_middle")
            st.set("cond","3")
        else :
            st.giveItems(FOX_FANG3_ID,1)
            st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(203,"203_ElfTutorial","Elf Tutorial")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7400)
QUEST.addStartNpc(7401)
QUEST.addStartNpc(7402)
QUEST.addStartNpc(7403)

STARTED.addTalkId(7370)
STARTED.addTalkId(7400)
STARTED.addTalkId(7401)
STARTED.addTalkId(7402)
STARTED.addTalkId(7403)

STARTED.addKillId(12082)

STARTED.addQuestDrop(12082,FOX_FANG3_ID,1)
STARTED.addQuestDrop(7400,LEAF_OF_MOTHERTREE_ID,1)
STARTED.addQuestDrop(7401,LEAF_OF_MOTHERTREE_ID,1)
STARTED.addQuestDrop(7402,LEAF_OF_MOTHERTREE_ID,1)
STARTED.addQuestDrop(7403,LEAF_OF_MOTHERTREE_ID,1)
