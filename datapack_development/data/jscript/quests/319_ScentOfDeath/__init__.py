# Maked by Mr. Have fun! Version 0.2
print "importing quests: 319: Scent Of Death"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

ZOMBIE_SKIN_ID = 1045
ADENA_ID = 57
HEALING_POTION_ID = 1061

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        htmltext = "7138-04.htm"
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
   if npcId == 7138 and int(st.get("cond"))==0 :
      if int(st.get("cond"))<15 :
        if st.getPlayer().getLevel() >= 11 :
          htmltext = "7138-03.htm"
          st.set("cond","1")
          return htmltext
        else:
          htmltext = "7138-02.htm"
      else:
        htmltext = "7138-02.htm"
   elif npcId == 7138 and int(st.get("cond")) :
      if st.getQuestItemsCount(ZOMBIE_SKIN_ID)<5 :
        htmltext = "7138-05.htm"
      elif st.getQuestItemsCount(ZOMBIE_SKIN_ID) >= 5 :
          if int(st.get("id")) != 319 :
            st.set("id","319")
            htmltext = "7138-06.htm"
            st.giveItems(ADENA_ID,2000)
            st.giveItems(HEALING_POTION_ID,1)
            st.takeItems(ZOMBIE_SKIN_ID,st.getQuestItemsCount(ZOMBIE_SKIN_ID))
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 20 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(ZOMBIE_SKIN_ID)<5 :
          if st.getRandom(10)>7 :
            st.giveItems(ZOMBIE_SKIN_ID,1)
            if st.getQuestItemsCount(ZOMBIE_SKIN_ID) == 5 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 15 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(ZOMBIE_SKIN_ID)<5 :
          if st.getRandom(10)>7 :
            st.giveItems(ZOMBIE_SKIN_ID,1)
            if st.getQuestItemsCount(ZOMBIE_SKIN_ID) == 5 :
              st.playSound("ItemSound.quest_middle")
            else:
              st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(319,"319_ScentOfDeath","Scent Of Death")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7138)

STARTED.addTalkId(7138)

STARTED.addKillId(15)
STARTED.addKillId(20)

STARTED.addQuestDrop(20,ZOMBIE_SKIN_ID,1)
STARTED.addQuestDrop(15,ZOMBIE_SKIN_ID,1)
