# Maked by Mr. Have fun! Version 0.2
# version 0.3 - fixed on 2005.11.08

import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

ADENA_ID           = 57
RING_OF_ANGUISH_ID = 876
PLAGUE_DUST_ID     = 1035
HYACINTH_CHARM1_ID = 1071
HYACINTH_CHARM2_ID = 1072

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("cond","1")
        st.set("id","0")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        if st.getQuestItemsCount(HYACINTH_CHARM1_ID) == 0 : st.giveItems(HYACINTH_CHARM1_ID,1)
        htmltext = "7154-04.htm"
    return htmltext

 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you.</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7154 and int(st.get("cond")) == 0 and int(st.get("onlyone")) == 0 :
        if int(st.get("cond")) < 15 :
          if st.getPlayer().getRace().ordinal() != 1 :
            htmltext = "7154-00.htm"
          elif st.getPlayer().getLevel() >= 12 :
            htmltext = "7154-03.htm"
            return htmltext
          else:
            st.exitQuest(1)
        else:
          htmltext = "7154-02.htm"
          st.exitQuest(1)
   elif npcId == 7154 and int(st.get("cond")) == 0 and int(st.get("onlyone")) == 1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7154 and int(st.get("cond")) != 0 and st.getQuestItemsCount(HYACINTH_CHARM1_ID) != 0 and 

st.getQuestItemsCount(PLAGUE_DUST_ID) == 0 :
        htmltext = "7154-05.htm"
   elif npcId == 7154 and int(st.get("cond")) != 0 and st.getQuestItemsCount(HYACINTH_CHARM1_ID) != 0 and 

st.getQuestItemsCount(PLAGUE_DUST_ID) != 0 :
        st.takeItems(PLAGUE_DUST_ID,st.getQuestItemsCount(PLAGUE_DUST_ID))
        st.takeItems(HYACINTH_CHARM1_ID,st.getQuestItemsCount(HYACINTH_CHARM1_ID))
        if st.getQuestItemsCount(HYACINTH_CHARM2_ID) == 0 : st.giveItems(HYACINTH_CHARM2_ID,1)
        htmltext = "7154-06.htm"
   elif npcId == 7154 and int(st.get("cond")) != 0 and st.getQuestItemsCount(HYACINTH_CHARM2_ID) != 0 and 

st.getQuestItemsCount(PLAGUE_DUST_ID) < 5 :
        htmltext = "7154-07.htm"
   elif npcId == 7154 and int(st.get("cond")) != 0 and st.getQuestItemsCount(HYACINTH_CHARM2_ID) != 0 and 

st.getQuestItemsCount(PLAGUE_DUST_ID) >= 5 and int(st.get("onlyone")) == 0 :
        if int(st.get("id")) != 159 :
          st.set("id","159")
          st.takeItems(PLAGUE_DUST_ID,st.getQuestItemsCount(PLAGUE_DUST_ID))
          st.takeItems(HYACINTH_CHARM2_ID,st.getQuestItemsCount(HYACINTH_CHARM2_ID))
          st.giveItems(RING_OF_ANGUISH_ID,1)
          st.giveItems(ADENA_ID,2500)
          htmltext = "7154-08.htm"
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.set("onlyone","1")
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 5017 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(HYACINTH_CHARM2_ID) and st.getRandom(100) < 40 and 

st.getQuestItemsCount(PLAGUE_DUST_ID) < 5 :
            if st.getQuestItemsCount(PLAGUE_DUST_ID) == 4 :
              st.giveItems(PLAGUE_DUST_ID,1)
              st.playSound("ItemSound.quest_middle")
            else:
              st.giveItems(PLAGUE_DUST_ID,1)
              st.playSound("ItemSound.quest_itemget")
        if int(st.get("cond")) and st.getQuestItemsCount(HYACINTH_CHARM1_ID) and st.getRandom(100) < 40 and 

st.getQuestItemsCount(PLAGUE_DUST_ID) == 0 :
            st.giveItems(PLAGUE_DUST_ID,1)
            st.playSound("ItemSound.quest_middle")
   return

QUEST     = Quest(159,"159_ProtectHeadsprings","Protect Headsprings")
CREATED   = State('Start',     QUEST)
STARTING  = State('Starting',  QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7154)

STARTING.addTalkId(7154)

STARTED.addTalkId(7154)

STARTED.addKillId(5017)

STARTED.addQuestDrop(5017,PLAGUE_DUST_ID,1)
STARTED.addQuestDrop(5017,PLAGUE_DUST_ID,1)
STARTED.addQuestDrop(5017,PLAGUE_DUST_ID,1)
STARTED.addQuestDrop(7154,HYACINTH_CHARM1_ID,1)
STARTED.addQuestDrop(7154,HYACINTH_CHARM2_ID,1)

print "importing quests: 159: Protect Headsprings"