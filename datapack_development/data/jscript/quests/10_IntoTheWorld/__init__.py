# Created by CubicVirtuoso
# Any problems feel free to drop by #l2j-datapack on irc.freenode.net
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "10_IntoTheWorld"

VERY_EXPENSIVE_NECKLACE = 7574
SCROLL_OF_ESCAPE_GIRAN = 7559
MARK_OF_TRAVELER = 7570

class Quest (JQuest) :

    def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

    def onEvent (self,event,st) :
        htmltext = event
        if event == "30533-03.htm" :
            st.set("cond","1")
            st.setState(STARTED)
            st.playSound("ItemSound.quest_accept")
        elif event == "30520-02.htm" :
            st.set("cond","2")
            st.giveItems(VERY_EXPENSIVE_NECKLACE,1)
        elif event == "30650-02.htm" :
            st.set("cond","3")
            st.takeItems(VERY_EXPENSIVE_NECKLACE,1)
        elif event == "30533-06.htm" :
            st.giveItems(SCROLL_OF_ESCAPE_GIRAN,1)
            st.giveItems(MARK_OF_TRAVELER, 1)
            st.unset("cond")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
        return htmltext

    def onTalk (Self,npc,player):
        npcId = npc.getNpcId()
        htmltext = "<html><head><body>I have nothing to say you</body></html>"
        st = player.getQuestState(qn)
        if not st: return htmltext
        id = st.getState()
        if id == CREATED :
            st.set("cond","0")
            if st.getPlayer().getRace().ordinal() == 4 :
                htmltext = "30533-02.htm"
            else :
                htmltext = "30533-01.htm"
                st.exitQuest(1)
        elif npcId == 30533 and id == COMPLETED :
            htmltext = "<html><head><body>I can't supply you with another Giran Scroll of Escape. Sorry traveller.</body></html>"
        elif id == STARTED: 
            if npcId == 30533 and int(st.get("cond"))==1 :
                htmltext = "30533-04.htm"
            elif npcId == 30520 and int(st.get("cond")) == 3 :
                htmltext = "30520-04.htm"
                st.set("cond","4")
            elif npcId == 30520 and int(st.get("cond")) :
                if st.getQuestItemsCount(VERY_EXPENSIVE_NECKLACE) == 0 :
                    htmltext = "30520-01.htm"
                else :
                    htmltext = "30520-03.htm"
            elif npcId == 30650 and int(st.get("cond"))==2 :
                if st.getQuestItemsCount(VERY_EXPENSIVE_NECKLACE) :
                    htmltext = "30650-01.htm"
            elif npcId == 30533 and int(st.get("cond"))==4 :
                htmltext = "30533-05.htm"
            return htmltext

QUEST       = Quest(10,qn,"Into The World")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30533)

QUEST.addTalkId(30533)
QUEST.addTalkId(30520)
QUEST.addTalkId(30650)

STARTED.addQuestDrop(30520,VERY_EXPENSIVE_NECKLACE,1)

print "importing quests: 10: Into The World"
