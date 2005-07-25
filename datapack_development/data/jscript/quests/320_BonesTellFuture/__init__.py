# Maked by Mr. Have fun! Version 0.2
print "importing quests: 320: Bones Tell Future"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

BONE_FRAGMENT_ID = 809
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        htmltext = "7359-04.htm"
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7359 and int(st.get("cond"))==0 :
      if int(st.get("cond"))<15 :
        if st.getPlayer().getRace().ordinal() != 2 :
          htmltext = "7359-00.htm"
        elif st.getPlayer().getLevel() >= 10 :
          htmltext = "7359-03.htm"
          st.set("cond","1")
          return htmltext
        else:
          htmltext = "7359-02.htm"
      else:
        htmltext = "7359-02.htm"
   elif npcId == 7359 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7359 and int(st.get("cond")) :
      if st.getQuestItemsCount(BONE_FRAGMENT_ID) >= 0 and st.getQuestItemsCount(BONE_FRAGMENT_ID)<10 :
        htmltext = "7359-05.htm"
      elif st.getQuestItemsCount(BONE_FRAGMENT_ID) >= 10 :
          if int(st.get("id")) != 320 :
            st.set("id","320")
            htmltext = "7359-06.htm"
            st.giveItems(ADENA_ID,100)
            st.addExpAndSp(3000,0)
            st.takeItems(BONE_FRAGMENT_ID,st.getQuestItemsCount(BONE_FRAGMENT_ID))
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 517 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(BONE_FRAGMENT_ID)<10 :
          if st.getRandom(10)>7 :
            st.giveItems(BONE_FRAGMENT_ID,1)
            if st.getQuestItemsCount(BONE_FRAGMENT_ID) == 10 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 518 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(BONE_FRAGMENT_ID)<10 :
          if st.getRandom(10)>7 :
            st.giveItems(BONE_FRAGMENT_ID,1)
            if st.getQuestItemsCount(BONE_FRAGMENT_ID) == 10 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(320,"320_BonesTellFuture","Bones Tell Future")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7359)

STARTED.addTalkId(7359)

STARTED.addKillId(517)
STARTED.addKillId(518)

STARTED.addQuestDrop(517,BONE_FRAGMENT_ID,1)
STARTED.addQuestDrop(518,BONE_FRAGMENT_ID,1)
