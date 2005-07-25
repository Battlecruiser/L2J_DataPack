# Maked by Mr. Have fun! Version 0.2
print "importing quests: 205: Orc Tutorial"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

BOLTERS_LIST_ID = 1547
MINING_BOOTS_ID = 1548
MINERS_PICK_ID = 1549
BOOMBOOM_POWDER_ID = 1550
REDSTONE_BEER_ID = 1551
BOLTERS_SMELLY_SOCKS_ID = 1552
FOX_FANG5_ID = 1861
VOUCHER_OF_FLAME_ID = 1496
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
   if (npcId == 7574 or npcId == 7575) and int(st.get("onlyone")) == 0 and int(st.get("cond")) == 0 and st.getPlayer().getLevel() < 10 and st.getPlayer().getRace().ordinal() == 3 :
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.playSound("ItemSound.quest_tutorial")
	if npcId == 7575 :
          htmltext = "7575-01.htm"
	if npcId == 7574 :
          htmltext = "7574-01.htm"
   elif int(st.get("cond"))==0 and st.getPlayer().getRace().ordinal() != 3 :
	if npcId == 7575 :
          htmltext = "7575-06.htm"
	if npcId == 7574 :
          htmltext = "7574-06.htm"
   elif st.getPlayer().getLevel() >= 10 :
	if npcId == 7575 :
          htmltext = "7575-05.htm"
	if npcId == 7574 :
          htmltext = "7575-05.htm"
   elif npcId == 7574 and int(st.get("cond")) and st.getQuestItemsCount(VOUCHER_OF_FLAME_ID)==0 :
      if st.getQuestItemsCount(FOX_FANG5_ID) == 4 :
        if int(st.get("id")) != 205 :
          st.set("id","205")
          st.takeItems(FOX_FANG5_ID,st.getQuestItemsCount(FOX_FANG5_ID))
          st.giveItems(VOUCHER_OF_FLAME_ID,1)
          st.giveItems(WORLD_MAP_2_ID,1)
          htmltext = "7574-02.htm"
          st.set("cond","1")
      elif st.getQuestItemsCount(FOX_FANG5_ID) < 4 :
          htmltext = "7574-03.htm"
   elif npcId == 7574 and int(st.get("cond")) and st.getQuestItemsCount(VOUCHER_OF_FLAME_ID) :
      htmltext = "7574-04.htm"
   elif npcId == 7575 and int(st.get("cond")) and st.getQuestItemsCount(VOUCHER_OF_FLAME_ID)==0 :
      if st.getQuestItemsCount(FOX_FANG5_ID) == 4 :
        if int(st.get("id")) != 205 :
          st.set("id","205")
          st.takeItems(FOX_FANG5_ID,st.getQuestItemsCount(FOX_FANG5_ID))
          st.giveItems(VOUCHER_OF_FLAME_ID,1)
          st.giveItems(WORLD_MAP_2_ID,1)
          htmltext = "7575-02.htm"
          st.set("cond","1")
      elif st.getQuestItemsCount(FOX_FANG5_ID) < 4 :
          htmltext = "7575-03.htm"
   elif npcId == 7575 and int(st.get("cond")) and st.getQuestItemsCount(VOUCHER_OF_FLAME_ID) :
      htmltext = "7575-04.htm"
   elif npcId == 7573 and int(st.get("cond")) :
      if st.getQuestItemsCount(VOUCHER_OF_FLAME_ID) and int(st.get("onlyone")) == 0 :
        htmltext = "7573-01.htm"
        st.addExpAndSp(0,50)
        st.takeItems(VOUCHER_OF_FLAME_ID,1)
        st.set("cond","0")
        st.set("onlyone","1")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 12082 :
      st.set("id","0")
      if int(st.get("cond")) and st.getQuestItemsCount(FOX_FANG5_ID) < 4 :
        if int(st.get("cond")) <= 0 :
          st.playSound("ItemSound.quest_tutorial")
          st.set("cond","1")
        elif int(st.get("cond")) == 1 :
            st.giveItems(FOX_FANG5_ID,1)
            st.playSound("ItemSound.quest_itemget")
            st.set("cond","2")
            st.playSound("ItemSound.quest_tutorial")
        elif int(st.get("cond")) == 2 and st.getQuestItemsCount(FOX_FANG5_ID) == 3 :
            st.giveItems(FOX_FANG5_ID,1)
            st.playSound("ItemSound.quest_middle")
            st.set("cond","3")
        elif int(st.get("cond")) == 2 and st.getQuestItemsCount(FOX_FANG5_ID) < 3 :
            st.giveItems(FOX_FANG5_ID,1)
            st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(205,"205_OrcTutorial","Orc Tutorial")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7574)
QUEST.addStartNpc(7575)

STARTED.addTalkId(7573)
STARTED.addTalkId(7574)
STARTED.addTalkId(7575)

STARTED.addKillId(12082)

STARTED.addQuestDrop(12082,FOX_FANG5_ID,1)
STARTED.addQuestDrop(7574,VOUCHER_OF_FLAME_ID,1)
STARTED.addQuestDrop(7575,VOUCHER_OF_FLAME_ID,1)
