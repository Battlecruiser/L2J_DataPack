# Created by CubicVirtuoso
# Any problems feel free to drop by #l2j-datapack on irc.freenode.net
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GATEKEEPER_JASMINE_ID = 7134
SENTRY_ROSELYN_ID = 7355
ROSELYNS_NOTE_ID = 7573
MAGISTER_HARNE_ID = 7144
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
            htmltext = "7134-03.htm"
        elif event == "2" :
            st.set("cond","2")
            st.giveItems(ROSELYNS_NOTE_ID,1)
            htmltext = "7355-02.htm"
        elif event == "3" :
            st.set("cond","3")
            st.takeItems(ROSELYNS_NOTE_ID,1)
            htmltext = "7144-02.htm"
        elif event == "4" :
            st.giveItems(SCROLL_OF_ESCAPE_GIRAN_ID,1)
            st.giveItems(MARK_OF_TRAVELER_ID, 1)
            htmltext = "7134-06.htm"
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
            if st.getPlayer().getRace().ordinal() == 2 :
                htmltext = "7134-02.htm"
            else :
                htmltext = "7134-01.htm"
                st.exitQuest(1)
        elif npcId == 7134 and id == COMPLETED :
            htmltext = "<html><head><body>I can't supply you with another Giran Scroll of Escape. Sorry traveller.</body></html>"
        elif npcId == 7134 and int(st.get("cond"))==1 :
            htmltext = "7134-04.htm"
        elif npcId == 7355 and int(st.get("cond")) :
            if st.getQuestItemsCount(ROSELYNS_NOTE_ID) == 0 :
                htmltext = "7355-01.htm"
            elif st.getQuestItemsCount(ROSELYNS_NOTE_ID) > 0 :
                htmltext = "7355-03.htm"
        elif npcId == 7144 and int(st.get("cond"))==2 :
            if st.getQuestItemsCount(ROSELYNS_NOTE_ID) > 0 :
                htmltext = "7144-01.htm"
        elif npcId == 7134 and int(st.get("cond"))==3 :
            htmltext = "7134-05.htm"

        return htmltext

QUEST       = Quest(8,"8_AnAdventureBegins","An Adventure Begins")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7134)

CREATED.addTalkId(7134)
COMPLETED.addTalkId(7134)

STARTED.addTalkId(7134)
STARTED.addTalkId(7355)
STARTED.addTalkId(7144)

STARTED.addQuestDrop(7355,ROSELYNS_NOTE_ID,1)

print "importing quests: 8: An Adventure Begins"
