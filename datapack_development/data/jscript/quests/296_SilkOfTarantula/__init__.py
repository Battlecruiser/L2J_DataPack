# Maked by Mr. Have fun! Version 0.2
print "importing quests: 296: Silk Of Tarantula"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

TARANTULA_SPIDER_SILK_ID = 1493
TARANTULA_SPINNERETTE_ID = 1494
RING_OF_RACCOON_ID = 1508
RING_OF_FIREFLY_ID = 1509
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      htmltext = "7519-03.htm"
    elif event == "7519_1" :
          htmltext = "7519-06.htm"
          st.takeItems(TARANTULA_SPINNERETTE_ID,st.getQuestItemsCount(TARANTULA_SPINNERETTE_ID))
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
    elif event == "7519_2" :
          htmltext = "7519-07.htm"
    elif event == "7548_1" :
          if st.getQuestItemsCount(TARANTULA_SPINNERETTE_ID) >= 1 :
            htmltext = "7548-03.htm"
            st.giveItems(TARANTULA_SPIDER_SILK_ID,15+st.getRandom(9))
            st.takeItems(TARANTULA_SPINNERETTE_ID,1)
          else:
            htmltext = "7548-02.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7519 and int(st.get("cond"))==0 :
      if int(st.get("cond")) < 15 :
        if st.getPlayer().getLevel() >= 15 :
          if st.getQuestItemsCount(RING_OF_RACCOON_ID) < 1 and st.getQuestItemsCount(RING_OF_FIREFLY_ID) < 1 :
            htmltext = "7519-08.htm"
          else:
            htmltext = "7519-02.htm"
            st.set("cond","1")
            return htmltext
        else:
          htmltext = "7519-01.htm"
      else:
        htmltext = "7519-01.htm"
   elif npcId == 7519 and int(st.get("cond"))==1 and st.getQuestItemsCount(TARANTULA_SPIDER_SILK_ID)<1 :
      htmltext = "7519-04.htm"
   elif npcId == 7519 and int(st.get("cond"))==1 and st.getQuestItemsCount(TARANTULA_SPIDER_SILK_ID)>=1 :
      if int(st.get("id")) != 296 :
        st.set("id","296")
      htmltext = "7519-05.htm"
      st.giveItems(ADENA_ID,st.getQuestItemsCount(TARANTULA_SPIDER_SILK_ID)*23)
      st.takeItems(TARANTULA_SPIDER_SILK_ID,st.getQuestItemsCount(TARANTULA_SPIDER_SILK_ID))
   elif npcId == 7548 and int(st.get("cond"))==1 :
      htmltext = "7548-01.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 394 :
      st.set("id","0")
      if int(st.get("cond")) :
        n = st.getRandom(100)
        if n > 95 :
          st.giveItems(TARANTULA_SPINNERETTE_ID,1)
          st.playSound("ItemSound.quest_itemget")
        elif n > 45 :
          st.giveItems(TARANTULA_SPIDER_SILK_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 403 :
      st.set("id","0")
      if int(st.get("cond")) :
        i5 = st.getRandom(100)
        if i5 > 95 :
          st.giveItems(TARANTULA_SPINNERETTE_ID,1)
          st.playSound("ItemSound.quest_itemget")
        elif i5 > 45 :
          st.giveItems(TARANTULA_SPIDER_SILK_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 508 :
      st.set("id","0")
      if int(st.get("cond")) :
        i5 = st.getRandom(100)
        if i5 > 95 :
          st.giveItems(TARANTULA_SPINNERETTE_ID,1)
          st.playSound("ItemSound.quest_itemget")
        elif i5 > 45 :
          st.giveItems(TARANTULA_SPIDER_SILK_ID,1)
          st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(296,"296_SilkOfTarantula","Silk Of Tarantula")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7519)

STARTED.addTalkId(7519)
STARTED.addTalkId(7548)

STARTED.addKillId(394)
STARTED.addKillId(403)
STARTED.addKillId(508)

STARTED.addQuestDrop(7548,TARANTULA_SPIDER_SILK_ID,1)
STARTED.addQuestDrop(394,TARANTULA_SPIDER_SILK_ID,1)
STARTED.addQuestDrop(403,TARANTULA_SPIDER_SILK_ID,1)
STARTED.addQuestDrop(508,TARANTULA_SPIDER_SILK_ID,1)
STARTED.addQuestDrop(394,TARANTULA_SPINNERETTE_ID,1)
STARTED.addQuestDrop(403,TARANTULA_SPINNERETTE_ID,1)
STARTED.addQuestDrop(508,TARANTULA_SPINNERETTE_ID,1)
