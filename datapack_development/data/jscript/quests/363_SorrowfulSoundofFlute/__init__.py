# Jovial Accordian Written By Elektra
# Fixed by mr
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "363_SorrowfulSoundofFlute"

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        htmltext = "30956_2.htm"
    elif event == "5" :
        st.giveItems(4420,1)
        st.playSound("ItemSound.quest_finish")
        st.exitQuest(1)
        htmltext = "30956_5.htm"
    return htmltext


 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say to you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   if npcId == 30956 and int(st.get("cond")) == 0 :
        htmltext = "30956_1.htm"
   elif npcId == 30956 and int(st.get("cond")) == 1 :
        htmltext = "<html><head><body>Find Blacksmith Opix..</body></html>"
   elif npcId == 30595 and int(st.get("cond")) == 1 :
        st.set("cond","2")
        htmltext = "30595_1.htm"
   elif npcId == 30595 and int(st.get("cond")) > 1 :
        htmltext = "<html><head><body>Go back to Nanarin..</body></html>"
   elif npcId == 30956 and int(st.get("cond")) == 2 :
        st.giveItems(4319,1)
        st.set("cond","3")
        htmltext = "30956_3.htm"
   elif npcId == 30956 and int(st.get("cond")) == 3 :
        htmltext = "<html><head><body>Find Barbado..</body></html>"
   elif npcId == 30959 and int(st.get("cond")) == 3 :
        st.takeItems(4319,1)
        st.set("cond","4")
        htmltext = "30959_1.htm"
   elif npcId == 30959 and int(st.get("cond")) == 4 :
        htmltext = "<html><head><body>Go back to Nanarin..</body></html>"
   elif npcId == 30956 and int(st.get("cond")) == 4 :
        htmltext = "30956_4.htm"
   return htmltext


QUEST       = Quest(363,qn,"Sorrowful Sounds of Flute")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30956)
CREATED.addTalkId(30956)

STARTED.addTalkId(30595)
STARTED.addTalkId(30959)
STARTED.addTalkId(30956)

STARTED.addQuestDrop(30959,4319,1)

print "importing quests: 363: Sorrowful Sound of Flute"
