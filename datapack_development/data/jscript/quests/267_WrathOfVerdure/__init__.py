# Maked by Mr. Have fun! Version 0.2
print "importing quests: 267: Wrath Of Verdure"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GOBLIN_CLUB_ID = 1335
SILVERY_LEAF_ID = 1340

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        htmltext = "12092-03.htm"
    elif event == "12092_1" :
            htmltext = "12092-06.htm"
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
    elif event == "12092_2" :
            htmltext = "12092-07.htm"
    return htmltext


 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 12092 and int(st.get("cond"))==0 :
        if int(st.get("cond"))<15 :
          if st.getPlayer().getLevel() >= 4 and st.getPlayer().getRace().ordinal() == 1 :
            htmltext = "12092-02.htm"
            return htmltext
          elif st.getPlayer().getRace().ordinal() != 1 :
            htmltext = "12092-00.htm"
            st.exitQuest(1)
          elif st.getPlayer().getLevel()<4 :
            htmltext = "12092-01.htm"
            st.exitQuest(1)
        else:
          htmltext = "12092-01.htm"
          st.exitQuest(1)
   elif npcId == 12092 and int(st.get("cond"))==1 :
        if st.getQuestItemsCount(GOBLIN_CLUB_ID)>0 :
          if int(st.get("id")) != 267 :
            st.set("id","267")
            st.giveItems(SILVERY_LEAF_ID,1*st.getQuestItemsCount(GOBLIN_CLUB_ID))
            st.takeItems(GOBLIN_CLUB_ID,st.getQuestItemsCount(GOBLIN_CLUB_ID))
            htmltext = "12092-05.htm"
        else:
          htmltext = "12092-04.htm"
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 325 :
      st.set("id","0")
      if int(st.get("cond")) :
        if st.getRandom(10)<5 :
          st.giveItems(GOBLIN_CLUB_ID,1)
          st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(267,"267_WrathOfVerdure","Wrath Of Verdure")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(12092)

STARTING.addTalkId(12092)

STARTED.addTalkId(12092)

STARTED.addKillId(325)

STARTED.addQuestDrop(325,GOBLIN_CLUB_ID,1)
