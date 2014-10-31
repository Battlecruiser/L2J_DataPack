# Maked by Mr. Have fun! Version 0.2
print "importing quests: 158: Seed Of Evil"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

CLAY_TABLET_ID = 1025
ENCHANT_ARMOR_D = 956

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        htmltext = "7031-04.htm"
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
   if npcId == 7031 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if int(st.get("cond"))<15 :
          if st.getPlayer().getLevel() >= 21 :
            htmltext = "7031-03.htm"
            return htmltext
          else:
            htmltext = "7031-02.htm"
            st.exitQuest(1)
        else:
          htmltext = "7031-02.htm"
          st.exitQuest(1)
   elif npcId == 7031 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7031 and int(st.get("cond"))!=0 and st.getQuestItemsCount(CLAY_TABLET_ID)==0 :
        htmltext = "7031-05.htm"
   elif npcId == 7031 and int(st.get("cond"))!=0 and st.getQuestItemsCount(CLAY_TABLET_ID)!=0 and int(st.get("onlyone"))==0 :
        if int(st.get("id")) != 158 :
          st.set("id","158")
          st.takeItems(CLAY_TABLET_ID,st.getQuestItemsCount(CLAY_TABLET_ID))
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.set("onlyone","1")
          st.giveItems(ENCHANT_ARMOR_D,1)
          htmltext = "7031-06.htm"
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 5016 :
        st.set("id","0")
        if int(st.get("cond")) != 0 and st.getQuestItemsCount(CLAY_TABLET_ID) == 0 :
          st.giveItems(CLAY_TABLET_ID,1)
          st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(158,"158_SeedOfEvil","Seed Of Evil")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7031)

STARTING.addTalkId(7031)

STARTED.addTalkId(7031)

STARTED.addKillId(5016)

STARTED.addQuestDrop(5016,CLAY_TABLET_ID,1)
