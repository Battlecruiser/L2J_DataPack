# Created by CubicVirtuoso
# Any problems feel free to drop by #l2j-datapack on irc.freenode.net
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

CENTURION_PETUKAI_ID = 7583
SEER_TANAPI_ID = 7571
GATEKEEPER_TAMIL_ID = 7576
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
            htmltext = "7583-03.htm"
        elif event == "2" :
            st.set("cond","2")
            htmltext = "7571-02.htm"
        elif event == "3" :
            st.giveItems(SCROLL_OF_ESCAPE_GIRAN_ID,1)
            st.giveItems(MARK_OF_TRAVELER_ID, 1)
            htmltext = "7576-02.htm"
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
            if st.getPlayer().getRace().ordinal() == 3 :
                htmltext = "7583-02.htm"
            else :
                htmltext = "7583-01.htm"
                st.exitQuest(1)
        elif npc == 7583 and id == COMPLETED :
            htmltext = "<html><head><body>I can't supply you with another Giran Scroll of Escape. Sorry traveller.</body></html>"
        elif npc == 7583 and int(st.get("cond"))==1 :
            htmltext = "7583-04.htm"
        elif npcId == 7571 and int(st.get("cond")) :
            htmltext = "7571-01.htm"
        elif npcId == 7576 and int(st.get("cond"))==2 :
            htmltext = "7576-01.htm"

        return htmltext

QUEST       = Quest(9,"9_IntoTheCityOfHumans","Into The City Of Humans")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7583)

CREATED.addTalkId(7583)
COMPLETED.addTalkId(7576)

STARTED.addTalkId(7583)
STARTED.addTalkId(7571)
STARTED.addTalkId(7576)

print "importing quests: 9: Into the City of Humans"
