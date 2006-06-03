# Created by CubicVirtuoso
# Any problems feel free to drop by #l2j-datapack on irc.freenode.net
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GATEKEEPER_MIRABEL_ID = 7146
TRADER_ARIEL_ID = 7148
ARIELS_RECOMMENDATION_ID = 7572
HIERARCH_ASTERIOS_ID = 7154
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
            htmltext = "7146-03.htm"
        elif event == "2" :
            st.set("cond","2")
            st.giveItems(ARIELS_RECOMMENDATION_ID,1)
            htmltext = "7148-02.htm"
        elif event == "3" :
            st.set("cond","3")
            st.takeItems(ARIELS_RECOMMENDATION_ID,1)
            htmltext = "7154-02.htm"
        elif event == "4" :
            st.giveItems(SCROLL_OF_ESCAPE_GIRAN_ID,1)
            st.giveItems(MARK_OF_TRAVELER_ID, 1)
            htmltext = "7146-06.htm"
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
            if st.getPlayer().getRace().ordinal() == 1 :
                htmltext = "7146-02.htm"
            else :
                htmltext = "7146-01.htm"
                st.exitQuest(1)
        elif npcId == 7146 and id == COMPLETED :
            htmltext = "<html><head><body>I can't supply you with another Giran Scroll of Escape. Sorry traveller.</body></html>"
        elif npcId == 7146 and int(st.get("cond"))==1 :
            htmltext = "7146-04.htm"
        elif npcId == 7148 and int(st.get("cond")) :
            if st.getQuestItemsCount(ARIELS_RECOMMENDATION_ID) == 0 :
                htmltext = "7148-01.htm"
            elif st.getQuestItemsCount(ARIELS_RECOMMENDATION_ID) > 0 :
                htmltext = "7148-03.htm"
        elif npcId == 7154 and int(st.get("cond"))==2 :
            if st.getQuestItemsCount(ARIELS_RECOMMENDATION_ID) > 0 :
                htmltext = "7154-01.htm"
        elif npcId == 7146 and int(st.get("cond"))==3 :
            htmltext = "7146-05.htm"

        return htmltext

QUEST       = Quest(7,"7_ATripBegins","A Trip Begins")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7146)

CREATED.addTalkId(7146)
COMPLETED.addTalkId(7146)

STARTED.addTalkId(7146)
STARTED.addTalkId(7148)
STARTED.addTalkId(7154)

STARTED.addQuestDrop(7148,ARIELS_RECOMMENDATION_ID,1)

print "importing quests: 7: A Trip Begins"
