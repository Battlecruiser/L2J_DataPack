# Maked by Mr. Have fun! Version 0.2
print "importing quests: 4: Long Live Lord Of Flame"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

HONEY_KHANDAR_ID = 1541
BEAR_FUR_CLOAK_ID = 1542
BLOODY_AXE_ID = 1543
ANCESTOR_SKULL_ID = 1544
SPIDER_DUST_ID = 1545
DEEP_SEA_ORB_ID = 1546
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      htmltext = "7578-03.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7578 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if int(st.get("cond")) < 15 :
        if st.getPlayer().getRace().ordinal() != 3 :
          htmltext = "7578-00.htm"
        elif st.getPlayer().getLevel() >= 2 :
          htmltext = "7578-02.htm"
          st.set("cond","1")
          return htmltext
        else:
          htmltext = "7578-01.htm"
      else:
        htmltext = "7578-01.htm"
   elif npcId == 7578 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7578 and int(st.get("cond"))==1 and st.getQuestItemsCount(HONEY_KHANDAR_ID)+st.getQuestItemsCount(BEAR_FUR_CLOAK_ID)+st.getQuestItemsCount(BLOODY_AXE_ID)+st.getQuestItemsCount(ANCESTOR_SKULL_ID)+st.getQuestItemsCount(SPIDER_DUST_ID)+st.getQuestItemsCount(DEEP_SEA_ORB_ID)<6 :
      htmltext = "7578-04.htm"
   elif npcId == 7578 and int(st.get("cond"))==1 and st.getQuestItemsCount(HONEY_KHANDAR_ID)+st.getQuestItemsCount(BEAR_FUR_CLOAK_ID)+st.getQuestItemsCount(BLOODY_AXE_ID)+st.getQuestItemsCount(ANCESTOR_SKULL_ID)+st.getQuestItemsCount(SPIDER_DUST_ID)+st.getQuestItemsCount(DEEP_SEA_ORB_ID)>=6 and int(st.get("onlyone"))==0 :
      if int(st.get("id")) != 4 :
        st.set("id","4")
        htmltext = "7578-06.htm"
        st.giveItems(ADENA_ID,450)
        st.takeItems(HONEY_KHANDAR_ID,st.getQuestItemsCount(HONEY_KHANDAR_ID))
        st.takeItems(BEAR_FUR_CLOAK_ID,st.getQuestItemsCount(BEAR_FUR_CLOAK_ID))
        st.takeItems(BLOODY_AXE_ID,st.getQuestItemsCount(BLOODY_AXE_ID))
        st.takeItems(ANCESTOR_SKULL_ID,st.getQuestItemsCount(ANCESTOR_SKULL_ID))
        st.takeItems(SPIDER_DUST_ID,st.getQuestItemsCount(SPIDER_DUST_ID))
        st.takeItems(DEEP_SEA_ORB_ID,st.getQuestItemsCount(DEEP_SEA_ORB_ID))
        st.set("onlyone","1")
        st.set("cond","0")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
   elif npcId == 7585 and int(st.get("cond"))==1 :
      if st.getQuestItemsCount(BEAR_FUR_CLOAK_ID) < 1 :
        htmltext = "7585-01.htm"
        st.giveItems(BEAR_FUR_CLOAK_ID,1)
        st.playSound("ItemSound.quest_itemget")
      elif st.getQuestItemsCount(BEAR_FUR_CLOAK_ID) >= 1 :
        htmltext = "7585-02.htm"
   elif npcId == 7566 and int(st.get("cond"))==1 :
      if st.getQuestItemsCount(HONEY_KHANDAR_ID) < 1 :
        htmltext = "7566-01.htm"
        st.giveItems(HONEY_KHANDAR_ID,1)
        st.playSound("ItemSound.quest_itemget")
      elif st.getQuestItemsCount(HONEY_KHANDAR_ID) >= 1 :
        htmltext = "7566-02.htm"
   elif npcId == 7562 and int(st.get("cond"))==1 :
      if st.getQuestItemsCount(BLOODY_AXE_ID) < 1 :
        htmltext = "7562-01.htm"
        st.giveItems(BLOODY_AXE_ID,1)
        st.playSound("ItemSound.quest_itemget")
      elif st.getQuestItemsCount(BLOODY_AXE_ID) >= 1 :
        htmltext = "7562-02.htm"
   elif npcId == 7560 and int(st.get("cond"))==1 :
      if st.getQuestItemsCount(ANCESTOR_SKULL_ID) < 1 :
        htmltext = "7560-01.htm"
        st.giveItems(ANCESTOR_SKULL_ID,1)
        st.playSound("ItemSound.quest_itemget")
      elif st.getQuestItemsCount(ANCESTOR_SKULL_ID) >= 1 :
        htmltext = "7560-02.htm"
   elif npcId == 7559 and int(st.get("cond"))==1 :
      if st.getQuestItemsCount(SPIDER_DUST_ID) < 1 :
        htmltext = "7559-01.htm"
        st.giveItems(SPIDER_DUST_ID,1)
        st.playSound("ItemSound.quest_itemget")
      elif st.getQuestItemsCount(SPIDER_DUST_ID) >= 1 :
        htmltext = "7559-02.htm"
   elif npcId == 7587 and int(st.get("cond"))==1 :
      if st.getQuestItemsCount(DEEP_SEA_ORB_ID) < 1 :
        htmltext = "7587-01.htm"
        st.giveItems(DEEP_SEA_ORB_ID,1)
        st.playSound("ItemSound.quest_itemget")
      elif st.getQuestItemsCount(DEEP_SEA_ORB_ID) >= 1 :
        htmltext = "7587-02.htm"
   return htmltext

QUEST       = Quest(4,"4_LongLiveLordOfFlame","Long Live Lord Of Flame")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7578)

STARTED.addTalkId(7559)
STARTED.addTalkId(7560)
STARTED.addTalkId(7562)
STARTED.addTalkId(7566)
STARTED.addTalkId(7578)
STARTED.addTalkId(7585)
STARTED.addTalkId(7587)


STARTED.addQuestDrop(7566,HONEY_KHANDAR_ID,1)
STARTED.addQuestDrop(7585,BEAR_FUR_CLOAK_ID,1)
STARTED.addQuestDrop(7562,BLOODY_AXE_ID,1)
STARTED.addQuestDrop(7560,ANCESTOR_SKULL_ID,1)
STARTED.addQuestDrop(7559,SPIDER_DUST_ID,1)
STARTED.addQuestDrop(7587,DEEP_SEA_ORB_ID,1)
