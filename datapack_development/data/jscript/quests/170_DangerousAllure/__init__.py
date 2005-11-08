# Maked by Mr. Have fun! Version 0.2
print "importing quests: 170: Dangerous Allure"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

NIGHTMARE_CRYSTAL_ID = 1046
PIECE_BONE_BREASTPLATE_ID = 25

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        htmltext = "7305-04.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
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
   if npcId == 7305 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if int(st.get("cond"))<15 :
        if st.getPlayer().getRace().ordinal() != 2 :
          htmltext = "7305-00.htm"
        elif st.getPlayer().getLevel() >= 21 :
          htmltext = "7305-03.htm"
          return htmltext
        else:
          htmltext = "7305-02.htm"
          st.exitQuest(1)
      else:
        htmltext = "7305-02.htm"
        st.exitQuest(1)
   elif npcId == 7305 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7305 and int(st.get("cond")) :
      if st.getQuestItemsCount(NIGHTMARE_CRYSTAL_ID)<1 :
        htmltext = "7305-05.htm"
      elif st.getQuestItemsCount(NIGHTMARE_CRYSTAL_ID) >= 1 and int(st.get("onlyone")) == 0 :
          if int(st.get("id")) != 170 :
            st.set("id","170")
            htmltext = "7305-06.htm"
            st.giveItems(PIECE_BONE_BREASTPLATE_ID,1)
            st.takeItems(NIGHTMARE_CRYSTAL_ID,st.getQuestItemsCount(NIGHTMARE_CRYSTAL_ID))
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
            st.set("onlyone","1")
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 5022 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(NIGHTMARE_CRYSTAL_ID) == 0 :
          st.giveItems(NIGHTMARE_CRYSTAL_ID,1)
          st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(170,"170_DangerousAllure","Dangerous Allure")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7305)

STARTING.addTalkId(7305)

STARTED.addTalkId(7305)

STARTED.addKillId(5022)

STARTED.addQuestDrop(5022,NIGHTMARE_CRYSTAL_ID,1)
