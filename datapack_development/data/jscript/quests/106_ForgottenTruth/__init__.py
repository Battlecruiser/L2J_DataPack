# Maked by Mr. Have fun! Version 0.2
print "importing quests: 106: Forgotten Truth"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

ONYX_TALISMAN1_ID = 984
ONYX_TALISMAN2_ID = 985
ANCIENT_SCROLL_ID = 986
ANCIENT_CLAY_TABLET_ID = 987
KARTAS_TRANSLATION_ID = 988
ELDRITCH_DAGGER_ID = 989

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        htmltext = "7358-05.htm"
        st.giveItems(ONYX_TALISMAN1_ID,1)
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
    elif event == "7358_1" :
            htmltext = "7358-04.htm"
            return htmltext
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
   if npcId == 7358 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if int(st.get("cond")) < 15 :
          if st.getPlayer().getRace().ordinal() != 2 :
            htmltext = "7358-00.htm"
            st.exitQuest(1)
          elif st.getPlayer().getLevel() >= 10 :
            htmltext = "7358-03.htm"
          else:
            htmltext = "7358-02.htm"
            st.exitQuest(1)
        else:
          htmltext = "7358-02.htm"
          st.exitQuest(1)
   elif npcId == 7358 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
        htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7358 and int(st.get("cond")) :
        if (st.getQuestItemsCount(ONYX_TALISMAN1_ID) or st.getQuestItemsCount(ONYX_TALISMAN2_ID)) and st.getQuestItemsCount(KARTAS_TRANSLATION_ID) == 0 :
          htmltext = "7358-06.htm"
        elif st.getQuestItemsCount(KARTAS_TRANSLATION_ID) and int(st.get("onlyone")) == 0 :
          if int(st.get("id")) != 106 :
            st.set("id","106")
            htmltext = "7358-07.htm"
            st.takeItems(KARTAS_TRANSLATION_ID,1)
            st.giveItems(ELDRITCH_DAGGER_ID,1)
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
            st.set("onlyone","1")
   elif npcId == 7133 and int(st.get("cond")) :
        if st.getQuestItemsCount(ONYX_TALISMAN1_ID) :
          htmltext = "7133-01.htm"
          st.takeItems(ONYX_TALISMAN1_ID,1)
          st.giveItems(ONYX_TALISMAN2_ID,1)
        elif st.getQuestItemsCount(ONYX_TALISMAN2_ID) and st.getQuestItemsCount(ANCIENT_SCROLL_ID) == 0 or st.getQuestItemsCount(ANCIENT_CLAY_TABLET_ID) == 0 :
          htmltext = "7133-02.htm"
        elif st.getQuestItemsCount(ANCIENT_SCROLL_ID) and st.getQuestItemsCount(ANCIENT_CLAY_TABLET_ID) :
          htmltext = "7133-03.htm"
          st.takeItems(ONYX_TALISMAN2_ID,1)
          st.takeItems(ANCIENT_SCROLL_ID,1)
          st.takeItems(ANCIENT_CLAY_TABLET_ID,1)
          st.giveItems(KARTAS_TRANSLATION_ID,1)
        elif st.getQuestItemsCount(KARTAS_TRANSLATION_ID) :
          htmltext = "7133-04.htm"
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 5070 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(ONYX_TALISMAN2_ID) and st.getRandom(100) < 20 :
          if st.getQuestItemsCount(ANCIENT_SCROLL_ID) == 0 :
            st.giveItems(ANCIENT_SCROLL_ID,1)
            st.playSound("ItemSound.quest_middle")
          elif st.getQuestItemsCount(ANCIENT_CLAY_TABLET_ID) == 0 :
            st.giveItems(ANCIENT_CLAY_TABLET_ID,1)
            st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(106,"106_ForgottenTruth","Forgotten Truth")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7358)

STARTING.addTalkId(7358)

STARTED.addTalkId(7133)
STARTED.addTalkId(7358)

STARTED.addKillId(5070)

STARTED.addQuestDrop(7133,KARTAS_TRANSLATION_ID,1)
STARTED.addQuestDrop(7358,ONYX_TALISMAN1_ID,1)
STARTED.addQuestDrop(7133,ONYX_TALISMAN2_ID,1)
STARTED.addQuestDrop(5070,ANCIENT_SCROLL_ID,1)
STARTED.addQuestDrop(5070,ANCIENT_CLAY_TABLET_ID,1)
