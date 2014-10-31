# Jovial Accordian Written By Elektra
# Fixed by mr
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest


class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        htmltext = "7956_2.htm"
    elif event == "5" :
        st.giveItems(4420,1)
        st.playSound("ItemSound.quest_finish")
        st.exitQuest(1)
        htmltext = "7956_5.htm"
    return htmltext


 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say to you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   if npcId == 7956 and int(st.get("cond")) == 0 :
        htmltext = "7956_1.htm"
   elif npcId == 7956 and int(st.get("cond")) == 1 :
        htmltext = "<html><head><body>Find Blacksmith Opix..</body></html>"
   elif npcId == 7595 and int(st.get("cond")) == 1 :
        st.set("cond","2")
        htmltext = "7595_1.htm"
   elif npcId == 7595 and int(st.get("cond")) > 1 :
        htmltext = "<html><head><body>Go back to Nanarin..</body></html>"
   elif npcId == 7956 and int(st.get("cond")) == 2 :
        st.giveItems(4319,1)
        st.set("cond","3")
        htmltext = "7956_3.htm"
   elif npcId == 7956 and int(st.get("cond")) == 3 :
        htmltext = "<html><head><body>Find Barbado..</body></html>"
   elif npcId == 7959 and int(st.get("cond")) == 3 :
        st.takeItems(4319,1)
        st.set("cond","4")
        htmltext = "7959_1.htm"
   elif npcId == 7959 and int(st.get("cond")) == 4 :
        htmltext = "<html><head><body>Go back to Nanarin..</body></html>"
   elif npcId == 7956 and int(st.get("cond")) == 4 :
        htmltext = "7956_4.htm"
   return htmltext


QUEST       = Quest(363,"363_SorrowfulSoundofFlute","Sorrowful Sounds of Flute")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7956)
CREATED.addTalkId(7956)

STARTED.addTalkId(7595)
STARTED.addTalkId(7959)
STARTED.addTalkId(7956)

STARTED.addQuestDrop(7959,4319,1)

print "importing quests: 363: Sorrowful Sound of Flute"
