# Maked by Mr. Have fun! Version 0.2
print "importing quests: 272: Wrath Of Ancestors"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GRAVE_ROBBERS_HEAD_ID = 1474
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
      htmltext = "7572-03.htm"
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
   if npcId == 7572 and int(st.get("cond"))==0 :
      if int(st.get("cond")) < 15 :
        if st.getPlayer().getRace().ordinal() != 3 :
          htmltext = "7572-00.htm"
          st.exitQuest(1)
        elif st.getPlayer().getLevel() < 5 :
          htmltext = "7572-01.htm"
          st.exitQuest(1)
        else:
          htmltext = "7572-02.htm"
          return htmltext
      else:
        htmltext = "7572-01.htm"
        st.exitQuest(1)
   elif npcId == 7572 and int(st.get("cond")) :
      if st.getQuestItemsCount(GRAVE_ROBBERS_HEAD_ID) < 50 :
        htmltext = "7572-04.htm"
      else:
        if int(st.get("id")) != 272 :
          st.set("id","272")
          htmltext = "7572-05.htm"
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.giveItems(ADENA_ID,1500)
          st.takeItems(GRAVE_ROBBERS_HEAD_ID,st.getQuestItemsCount(GRAVE_ROBBERS_HEAD_ID))
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 319 :
      st.set("id","0")
      if int(st.get("cond")) and st.getQuestItemsCount(GRAVE_ROBBERS_HEAD_ID) < 50 :
        if st.getQuestItemsCount(GRAVE_ROBBERS_HEAD_ID) < 49 :
          st.playSound("ItemSound.quest_itemget")
        else:
          st.playSound("ItemSound.quest_middle")
        st.giveItems(GRAVE_ROBBERS_HEAD_ID,1)
   elif npcId == 320 :
      st.set("id","0")
      if int(st.get("cond")) and st.getQuestItemsCount(GRAVE_ROBBERS_HEAD_ID) < 50 :
        if st.getQuestItemsCount(GRAVE_ROBBERS_HEAD_ID) < 49 :
          st.playSound("ItemSound.quest_itemget")
        else:
          st.playSound("ItemSound.quest_middle")
        st.giveItems(GRAVE_ROBBERS_HEAD_ID,1)
   return

QUEST       = Quest(272,"272_WrathOfAncestors","Wrath Of Ancestors")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7572)

STARTING.addTalkId(7572)

STARTED.addTalkId(7572)

STARTED.addKillId(319)
STARTED.addKillId(320)

STARTED.addQuestDrop(319,GRAVE_ROBBERS_HEAD_ID,1)
STARTED.addQuestDrop(320,GRAVE_ROBBERS_HEAD_ID,1)
