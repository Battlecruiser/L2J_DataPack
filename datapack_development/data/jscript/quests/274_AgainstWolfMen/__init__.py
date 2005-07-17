# Maked by Mr. Have fun! Version 0.2
print "importing quests: 274: Against Wolf Men"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARAKU_WEREWOLF_HEAD_ID = 1477
NECKLACE_OF_VALOR_ID = 1507
NECKLACE_OF_COURAGE_ID = 1506
ADENA_ID = 57
MARAKU_WOLFMEN_TOTEM_ID = 1501

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      htmltext = "7569-03.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7569 and int(st.get("cond"))==0 :
      if int(st.get("cond")) < 15 :
        if st.getPlayer().getRace().ordinal() != 3 :
          htmltext = "7569-00.htm"
        elif st.getPlayer().getLevel() < 9 :
          htmltext = "7569-01.htm"
        elif st.getQuestItemsCount(NECKLACE_OF_VALOR_ID) or st.getQuestItemsCount(NECKLACE_OF_COURAGE_ID) :
          htmltext = "7569-02.htm"
          st.set("cond","1")
          return htmltext
        else:
          htmltext = "7569-07.htm"
      else:
        htmltext = "7569-07.htm"
   elif npcId == 7569 and int(st.get("cond")) :
      if int(st.get("id")) != 274 :
        st.set("id","274")
        if st.getQuestItemsCount(MARAKU_WEREWOLF_HEAD_ID) < 40 :
          htmltext = "7569-04.htm"
        elif st.getQuestItemsCount(MARAKU_WOLFMEN_TOTEM_ID) < 1 :
          htmltext = "7569-05.htm"
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.giveItems(ADENA_ID,st.getQuestItemsCount(MARAKU_WEREWOLF_HEAD_ID)*30)
          st.takeItems(MARAKU_WEREWOLF_HEAD_ID,st.getQuestItemsCount(MARAKU_WEREWOLF_HEAD_ID))
        else:
          htmltext = "7569-06.htm"
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.giveItems(ADENA_ID,st.getQuestItemsCount(MARAKU_WEREWOLF_HEAD_ID)*30)
          st.takeItems(MARAKU_WEREWOLF_HEAD_ID,st.getQuestItemsCount(MARAKU_WEREWOLF_HEAD_ID))
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 363 :
      st.set("id","0")
      if int(st.get("cond")) and st.getQuestItemsCount(MARAKU_WEREWOLF_HEAD_ID) < 40 :
        if st.getQuestItemsCount(MARAKU_WEREWOLF_HEAD_ID) < 39 :
          st.playSound("ItemSound.quest_itemget")
        else:
          st.playSound("ItemSound.quest_middle")
        st.giveItems(MARAKU_WEREWOLF_HEAD_ID,1)
        n = st.getRandom(100)
        if n <= 5 :
          st.giveItems(MARAKU_WOLFMEN_TOTEM_ID,1)
   elif npcId == 364 :
      st.set("id","0")
      if int(st.get("cond")) and st.getQuestItemsCount(MARAKU_WEREWOLF_HEAD_ID) < 40 :
        if st.getQuestItemsCount(MARAKU_WEREWOLF_HEAD_ID) < 39 :
          st.playSound("ItemSound.quest_itemget")
        else:
          st.playSound("ItemSound.quest_middle")
        st.giveItems(MARAKU_WEREWOLF_HEAD_ID,1)
        n = st.getRandom(100)
        if n <= 5 :
          st.giveItems(MARAKU_WOLFMEN_TOTEM_ID,1)
   return

QUEST       = Quest(274,"274_AgainstWolfMen","Against Wolf Men")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7569)

STARTED.addTalkId(7569)

STARTED.addKillId(363)
STARTED.addKillId(364)

STARTED.addQuestDrop(363,MARAKU_WEREWOLF_HEAD_ID,1)
STARTED.addQuestDrop(364,MARAKU_WEREWOLF_HEAD_ID,1)
