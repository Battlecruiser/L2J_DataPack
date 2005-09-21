# Maked by Mr. Have fun! Version 0.2
print "importing quests: 271: Proof Of Valor"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

KASHA_WOLF_FANG_ID = 1473
NECKLACE_OF_VALOR_ID = 1507
NECKLACE_OF_COURAGE_ID = 1506

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      if st.getQuestItemsCount(NECKLACE_OF_COURAGE_ID) or st.getQuestItemsCount(NECKLACE_OF_VALOR_ID) :
        htmltext = "7577-07.htm"
      else:
        htmltext = "7577-03.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7577 and int(st.get("cond"))==0 :
      if int(st.get("cond")) < 15 :
        if st.getPlayer().getRace().ordinal() != 3 :
          htmltext = "7577-00.htm"
        elif st.getPlayer().getLevel() < 4 :
          htmltext = "7577-01.htm"
        elif st.getQuestItemsCount(NECKLACE_OF_COURAGE_ID) or st.getQuestItemsCount(NECKLACE_OF_VALOR_ID) :
          htmltext = "7577-06.htm"
        else:
          htmltext = "7577-02.htm"
      else:
        htmltext = "7577-02.htm"
   elif npcId == 7577 and int(st.get("cond")) :
      if st.getQuestItemsCount(KASHA_WOLF_FANG_ID) < 50 :
        htmltext = "7577-04.htm"
      else:
        if int(st.get("id")) != 271 :
          st.set("id","271")
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.takeItems(KASHA_WOLF_FANG_ID,st.getQuestItemsCount(KASHA_WOLF_FANG_ID))
          n = st.getRandom(100)
          if n <= 13 :
            st.giveItems(NECKLACE_OF_VALOR_ID,1)
          else:
            st.giveItems(NECKLACE_OF_COURAGE_ID,1)
          htmltext = "7577-05.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 475 :
      st.set("id","0")
      if st.getQuestItemsCount(KASHA_WOLF_FANG_ID) < 50 and int(st.get("cond")) :
        n = st.getRandom(100)
        i1 = st.getQuestItemsCount(KASHA_WOLF_FANG_ID)
        if n <= 25 and st.getQuestItemsCount(KASHA_WOLF_FANG_ID) < 49 :
          st.giveItems(KASHA_WOLF_FANG_ID,2)
          i1 = i1+2
        else:
          st.giveItems(KASHA_WOLF_FANG_ID,1)
          i1 = i1+1
        if i1 >= 50 :
          st.playSound("ItemSound.quest_middle")
        else:
          st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(271,"271_ProofOfValor","Proof Of Valor")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7577)

STARTING.addTalkId(7577)

STARTED.addTalkId(7577)

STARTED.addKillId(475)

STARTED.addQuestDrop(475,KASHA_WOLF_FANG_ID,1)
