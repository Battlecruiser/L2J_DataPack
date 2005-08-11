### ---------------------------------------------------------------------------
### <history>
###		[TI]Blue blue@teamimprovision.com	7/26/2005	Created
### </history>
### ---------------------------------------------------------------------------

### Settings
NPC         = [7098]
QuestId     = 5000
QuestName   = "crystal"
QuestDesc   = "luxury_shop"
InitialHtml = "1.htm"

### Items - Format [name, eventId, giveItemId, giveItemQty, takeItem1Id, takeItem1Qty]
Items       = [
["S to A", 1001, 1461, 5, 1462, 1],
["S to A", 1011, 1461, 50, 1462, 10],
["S to A", 1101, 1461, 500, 1462, 100],
["A to B", 1002, 1460, 5, 1461, 1],
["A to B", 1012, 1460, 50, 1461, 10],
["A to B", 1102, 1460, 500, 1461, 100],
["B to C", 1003, 1459, 5, 1460, 1],
["B to C", 1013, 1459, 50, 1460, 10],
["B to C", 1103, 1459, 500, 1460, 100],
["C to D", 1004, 1458, 5, 1459, 1],
["C to D", 1014, 1458, 50, 1459, 10],
["C to D", 1104, 1458, 500, 1459, 100],
["D to C", 1006, 1459, 1, 1458, 7],
["D to C", 1016, 1459, 10, 1458, 70],
["D to C", 1106, 1459, 100, 1458, 700],
["C to B", 1007, 1460, 1, 1459, 7],
["C to B", 1017, 1460, 10, 1459, 70],
["C to B", 1107, 1460, 100, 1459, 700],
["B to A", 1008, 1461, 1, 1460, 7],
["B to A", 1018, 1461, 10, 1460, 70],
["B to A", 1108, 1461, 100, 1460, 700],
["A to S", 1009, 1462, 1, 1461, 7],
["A to S", 1019, 1462, 10, 1461, 70],
["A to S", 1109, 1462, 100, 1461, 700]
]

### ---------------------------------------------------------------------------
### DO NOT MODIFY BELOW THIS LINE
### ---------------------------------------------------------------------------

print "importing " + QuestDesc + ": " + str(QuestId) + ": " + QuestName + ": " + str(len(Items)) + " item(s)",
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

### doRequestedEvent
def do_RequestedEvent(event, st, giveItemId, giveItemQty, takeItem1Id, takeItem1Qty) :
    if st.getQuestItemsCount(takeItem1Id) >= takeItem1Qty :
        st.takeItems(takeItem1Id, takeItem1Qty)
        st.giveItems(giveItemId, giveItemQty)
        st.exitQuest(1)
        return event + ".htm" 
    else :
        return "You do not have enough crystals."

### main code
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event

    if event == "0":
        return InitialHtml

    for item in Items:
        if event == str(item[1]):
            htmltext = do_RequestedEvent(event, st, item[2], item[3], item[4], item[5])

    return htmltext

 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say with you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(COMPLETED)
   return InitialHtml

### Quest class and state definition
QUEST       = Quest(QuestId,str(QuestId) + "_" + QuestName,QuestDesc)
CREATED     = State('Start',     QUEST)
STARTED     = State('Started',   QUEST)
COMPLETED   = State('Completed', QUEST)

### Quest initialization
QUEST.setInitialState(CREATED)

for item in NPC:
### Quest NPC starter initialization
   QUEST.addStartNpc(item)

### Quest NPC initialization
   STARTED.addTalkId(item)

print "...done"