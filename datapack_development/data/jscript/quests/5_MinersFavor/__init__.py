# Maked by Mr. Have fun! Version 0.2
print "importing quests: 5: Miners Favor"
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
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
          st.set("id","0")
          htmltext = "7554-03.htm"
          st.giveItems(BOLTERS_LIST_ID,1)
          st.giveItems(BOLTERS_SMELLY_SOCKS_ID,1)
          st.setState(STARTED)
          st.playSound("ItemSound.quest_accept")
    elif event == "7526_1" :
          htmltext = "7526-02.htm"
          st.takeItems(BOLTERS_SMELLY_SOCKS_ID,1)
          st.giveItems(MINERS_PICK_ID,1)
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7554 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
          if int(st.get("cond")) < 15 :
            if st.getPlayer().getLevel() >= 2 :
              htmltext = "7554-02.htm"
              st.set("cond","1")
              return htmltext
            else:
              htmltext = "7554-01.htm"
          else:
            htmltext = "7554-01.htm"
   elif npcId == 7554 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7554 and int(st.get("cond"))==1 and st.getQuestItemsCount(BOLTERS_LIST_ID) and (st.getQuestItemsCount(MINING_BOOTS_ID)+st.getQuestItemsCount(MINERS_PICK_ID)+st.getQuestItemsCount(BOOMBOOM_POWDER_ID)+st.getQuestItemsCount(REDSTONE_BEER_ID)<4) :
          htmltext = "7554-04.htm"
   elif npcId == 7554 and int(st.get("cond"))==1 and st.getQuestItemsCount(BOLTERS_LIST_ID) and (st.getQuestItemsCount(MINING_BOOTS_ID)+st.getQuestItemsCount(MINERS_PICK_ID)+st.getQuestItemsCount(BOOMBOOM_POWDER_ID)+st.getQuestItemsCount(REDSTONE_BEER_ID)>=4) and int(st.get("onlyone"))==0 :
          if int(st.get("id")) != 5 :
            st.set("id","5")
            htmltext = "7554-06.htm"
            st.takeItems(MINING_BOOTS_ID,1)
            st.takeItems(MINERS_PICK_ID,1)
            st.takeItems(BOOMBOOM_POWDER_ID,1)
            st.takeItems(REDSTONE_BEER_ID,1)
            st.takeItems(BOLTERS_LIST_ID,1)
            st.giveItems(ADENA_ID,450)
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
            st.set("onlyone","1")
   elif npcId == 7517 and int(st.get("cond")) and st.getQuestItemsCount(BOLTERS_LIST_ID) and st.getQuestItemsCount(BOOMBOOM_POWDER_ID)==0 :
          htmltext = "7517-01.htm"
          st.giveItems(BOOMBOOM_POWDER_ID,1)
   elif npcId == 7517 and int(st.get("cond")) and st.getQuestItemsCount(BOLTERS_LIST_ID) and st.getQuestItemsCount(BOOMBOOM_POWDER_ID) :
          htmltext = "7517-02.htm"
   elif npcId == 7518 and int(st.get("cond")) and st.getQuestItemsCount(BOLTERS_LIST_ID) and st.getQuestItemsCount(MINING_BOOTS_ID)==0 :
          htmltext = "7518-01.htm"
          st.giveItems(MINING_BOOTS_ID,1)
   elif npcId == 7518 and int(st.get("cond")) and st.getQuestItemsCount(BOLTERS_LIST_ID) and st.getQuestItemsCount(MINING_BOOTS_ID) :
          htmltext = "7518-02.htm"
   elif npcId == 7520 and int(st.get("cond")) and st.getQuestItemsCount(BOLTERS_LIST_ID) and st.getQuestItemsCount(REDSTONE_BEER_ID)==0 :
          htmltext = "7520-01.htm"
          st.giveItems(REDSTONE_BEER_ID,1)
   elif npcId == 7520 and int(st.get("cond")) and st.getQuestItemsCount(BOLTERS_LIST_ID) and st.getQuestItemsCount(REDSTONE_BEER_ID) :
          htmltext = "7520-02.htm"
   elif npcId == 7526 and int(st.get("cond")) and st.getQuestItemsCount(BOLTERS_LIST_ID) and st.getQuestItemsCount(MINERS_PICK_ID)==0 :
          htmltext = "7526-01.htm"
   elif npcId == 7526 and int(st.get("cond")) and st.getQuestItemsCount(BOLTERS_LIST_ID) and st.getQuestItemsCount(MINERS_PICK_ID) :
          htmltext = "7526-03.htm"
   return htmltext

QUEST       = Quest(5,"5_MinersFavor","Miners Favor")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7554)

STARTED.addTalkId(7517)
STARTED.addTalkId(7518)
STARTED.addTalkId(7520)
STARTED.addTalkId(7526)
STARTED.addTalkId(7554)


STARTED.addQuestDrop(7518,MINING_BOOTS_ID,1)
STARTED.addQuestDrop(7526,MINERS_PICK_ID,1)
STARTED.addQuestDrop(7517,BOOMBOOM_POWDER_ID,1)
STARTED.addQuestDrop(7520,REDSTONE_BEER_ID,1)
STARTED.addQuestDrop(7554,BOLTERS_LIST_ID,1)
STARTED.addQuestDrop(7554,BOLTERS_SMELLY_SOCKS_ID,1)
