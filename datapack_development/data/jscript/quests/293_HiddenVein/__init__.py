# Maked by Mr. Have fun! Version 0.2
print "importing quests: 293: Hidden Vein"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

ORIHARUKON_ORE_1_ID = 1488
TORN_MAP_FRAGMENT_ID = 1489
HIDDEN_VEIN_MAP_ID = 1490
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
      htmltext = "7535-03.htm"
    elif event == "7535_1" :
          htmltext = "7535-06.htm"
          st.takeItems(TORN_MAP_FRAGMENT_ID,st.getQuestItemsCount(TORN_MAP_FRAGMENT_ID))
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
    elif event == "7535_2" :
          htmltext = "7535-07.htm"
    elif event == "7539_1" and st.getQuestItemsCount(TORN_MAP_FRAGMENT_ID) < 4 :
          htmltext = "7539-02.htm"
    elif event == "7539_1" and st.getQuestItemsCount(TORN_MAP_FRAGMENT_ID) >= 4 :
          htmltext = "7539-03.htm"
          st.giveItems(HIDDEN_VEIN_MAP_ID,1)
          st.takeItems(TORN_MAP_FRAGMENT_ID,4)
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7535 and int(st.get("cond"))==0 :
      if int(st.get("cond")) < 15 :
        if st.getPlayer().getRace().ordinal() != 4 :
          htmltext = "7535-00.htm"
        elif st.getPlayer().getLevel() >= 6 :
          htmltext = "7535-02.htm"
          return htmltext
        else:
          htmltext = "7535-01.htm"
      else:
        htmltext = "7535-01.htm"
   elif npcId == 7535 and int(st.get("cond"))==1 and st.getQuestItemsCount(ORIHARUKON_ORE_1_ID)<1 and st.getQuestItemsCount(HIDDEN_VEIN_MAP_ID)<1 :
      htmltext = "7535-04.htm"
   elif npcId == 7535 and int(st.get("cond"))==1 and st.getQuestItemsCount(ORIHARUKON_ORE_1_ID)<1 and st.getQuestItemsCount(HIDDEN_VEIN_MAP_ID)>=1 :
      htmltext = "7535-08.htm"
      st.giveItems(ADENA_ID,st.getQuestItemsCount(HIDDEN_VEIN_MAP_ID)*1000)
      st.takeItems(HIDDEN_VEIN_MAP_ID,st.getQuestItemsCount(HIDDEN_VEIN_MAP_ID))
   elif npcId == 7535 and int(st.get("cond"))==1 and st.getQuestItemsCount(ORIHARUKON_ORE_1_ID)>=1 and st.getQuestItemsCount(HIDDEN_VEIN_MAP_ID)<1 :
      htmltext = "7535-05.htm"
      st.giveItems(ADENA_ID,st.getQuestItemsCount(ORIHARUKON_ORE_1_ID)*10)
      st.takeItems(ORIHARUKON_ORE_1_ID,st.getQuestItemsCount(ORIHARUKON_ORE_1_ID))
   elif npcId == 7535 and int(st.get("cond"))==1 and st.getQuestItemsCount(ORIHARUKON_ORE_1_ID)>=1 and st.getQuestItemsCount(HIDDEN_VEIN_MAP_ID)>=1 :
      htmltext = "7535-09.htm"
      st.giveItems(ADENA_ID,st.getQuestItemsCount(ORIHARUKON_ORE_1_ID)*10+st.getQuestItemsCount(HIDDEN_VEIN_MAP_ID)*1000)
      st.takeItems(HIDDEN_VEIN_MAP_ID,st.getQuestItemsCount(HIDDEN_VEIN_MAP_ID))
      st.takeItems(ORIHARUKON_ORE_1_ID,st.getQuestItemsCount(ORIHARUKON_ORE_1_ID))
   elif npcId == 7539 and int(st.get("cond"))==1 :
      htmltext = "7539-01.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 446 :
      st.set("id","0")
      if int(st.get("cond")) :
        n = st.getRandom(100)
        if n > 50 :
          st.giveItems(ORIHARUKON_ORE_1_ID,1)
          st.playSound("ItemSound.quest_itemget")
        elif n < 5 :
          st.giveItems(TORN_MAP_FRAGMENT_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 448 :
      st.set("id","0")
      if int(st.get("cond")) :
        n = st.getRandom(100)
        if n > 50 :
          st.giveItems(ORIHARUKON_ORE_1_ID,1)
          st.playSound("ItemSound.quest_itemget")
        elif n < 5 :
          st.giveItems(TORN_MAP_FRAGMENT_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 447 :
      st.set("id","0")
      if int(st.get("cond")) :
        n = st.getRandom(100)
        if n > 50 :
          st.giveItems(ORIHARUKON_ORE_1_ID,1)
          st.playSound("ItemSound.quest_itemget")
        elif n < 5 :
          st.giveItems(TORN_MAP_FRAGMENT_ID,1)
          st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(293,"293_HiddenVein","Hidden Vein")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7535)

STARTING.addTalkId(7535)

STARTED.addTalkId(7535)
STARTED.addTalkId(7539)

STARTED.addKillId(446)
STARTED.addKillId(447)
STARTED.addKillId(448)

STARTED.addQuestDrop(7539,HIDDEN_VEIN_MAP_ID,1)
STARTED.addQuestDrop(446,ORIHARUKON_ORE_1_ID,1)
STARTED.addQuestDrop(448,ORIHARUKON_ORE_1_ID,1)
STARTED.addQuestDrop(447,ORIHARUKON_ORE_1_ID,1)
STARTED.addQuestDrop(446,TORN_MAP_FRAGMENT_ID,1)
STARTED.addQuestDrop(448,TORN_MAP_FRAGMENT_ID,1)
STARTED.addQuestDrop(447,TORN_MAP_FRAGMENT_ID,1)
