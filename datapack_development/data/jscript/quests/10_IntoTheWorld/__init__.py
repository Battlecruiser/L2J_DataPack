# Created by CubicVirtuoso
# Any problems feel free to drop by #l2j-datapack on irc.freenode.net
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

SILVER_SCALE_BALANKI_ID = 7533
WAREHOUSE_CHIEF_REED_ID = 7520
VERY_EXPENSIVE_NECKLACE_ID = 7574
PRIESTESS_OF_THE_EARTH_GERALDINE_ID = 7650
ADENA_ID = 57
SCROLL_OF_ESCAPE_GIRAN_ID = 7559
MARK_OF_TRAVELER_ID = 7570

class Quest (JQuest) :

    def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

    def onEvent (self,event,st) :
        htmltext = event
        if event == "1" :
            st.set("cond","1")
            st.setState(STARTED)
            st.playSound("ItemSound.quest_accept")
            htmltext = "7533-03.htm"
        elif event == "2" :
            st.set("cond","2")
            st.giveItems(VERY_EXPENSIVE_NECKLACE_ID,1)
            htmltext = "7520-02.htm"
        elif event == "3" :
            st.set("cond","3")
            st.takeItems(VERY_EXPENSIVE_NECKLACE_ID,1)
            htmltext = "7650-02.htm"
        elif event == "5" :
            st.giveItems(SCROLL_OF_ESCAPE_GIRAN_ID,1)
            st.giveItems(MARK_OF_TRAVELER_ID, 1)
            htmltext = "7533-06.htm"
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
        return htmltext

    def onTalk (Self,npc,st):
        npcId = npc.getNpcId()
        htmltext = "<html><head><body>I have nothing to say you</body></html>"
        id = st.getState()
        if id == CREATED :
            st.set("cond","0")
            if st.getPlayer().getRace().ordinal() == 4 :
                htmltext = "7533-02.htm"
            else :
                htmltext = "7533-01.htm"
                st.exitQuest(1)
        elif npcId == 7533 and id == COMPLETED :
            htmltext = "<html><head><body>I can't supply you with another Giran Scroll of Escape. Sorry traveller.</body></html>"
        elif npcId == 7533 and int(st.get("cond"))==1 :
            htmltext = "7533-04.htm"
        elif npcId == 7520 and int(st.get("cond")) == 3 :
            htmltext = "7520-04.htm"
            st.set("cond","4")
        elif npcId == 7520 and int(st.get("cond")) :
            if st.getQuestItemsCount(VERY_EXPENSIVE_NECKLACE_ID) == 0 :
                htmltext = "7520-01.htm"
            elif st.getQuestItemsCount(VERY_EXPENSIVE_NECKLACE_ID) > 0 :
                htmltext = "7520-03.htm"
        elif npcId == 7650 and int(st.get("cond"))==2 :
            if st.getQuestItemsCount(VERY_EXPENSIVE_NECKLACE_ID) > 0 :
                htmltext = "7650-01.htm"
        elif npcId == 7533 and int(st.get("cond"))==4 :
            htmltext = "7533-05.htm"

        return htmltext

QUEST       = Quest(10,"10_IntoTheWorld","Into The World")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7533)

CREATED.addTalkId(7533)
COMPLETED.addTalkId(7533)

STARTED.addTalkId(7533)
STARTED.addTalkId(7520)
STARTED.addTalkId(7650)

STARTED.addQuestDrop(7520,VERY_EXPENSIVE_NECKLACE_ID,1)

print "importing quests: 10: Into The World"
