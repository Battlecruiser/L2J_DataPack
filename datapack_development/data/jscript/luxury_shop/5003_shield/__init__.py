### ---------------------------------------------------------------------------
### <history>
###		[TI]Blue blue@teamimprovision.com	7/26/2005	Created
### </history>
### ---------------------------------------------------------------------------

### Settings
NPC         = [7098]
QuestId     = 5003
QuestName   = "shield"
QuestDesc   = "luxury_shop"
#QuestDesc   = "Buy Shield with crystals"
InitialHtml = "1.htm"
SuccessMsg  = ""
FailureMsg  = "You do not have enough materials."
CancelMsg   = "1.htm"

### Items - Format [name, eventId, [giveItems], [takeitems], [teleLocation x, teleLocation y, teleLocation z]]
### giveItems - Format [itemId, qty]
### takeItems - Format [itemId, qty]
### example: 
### Items = [
###     ["MyItem1", 1001, [[ 234,   10], [ 333,    1]], [[ 563,  100], [ 363,  150]], [-80826,149775,-3043]],
###     ["MyItem2", 1002, [[ 453,    1], [  63,    1]], [[  23,   10], [ 774,  100]], [-80826,149775,-3043]]
### ]
Items       = [
    ["Shield Eldarake", 631, [[631, 1]], [[1459, 34], [1458, 170]], []],
    ["Shield Tower", 103, [[103, 1]], [[1459, 65], [1458, 325]], []]
]

### ---------------------------------------------------------------------------
### DO NOT MODIFY BELOW THIS LINE
### ---------------------------------------------------------------------------

print "importing " + str(QuestId) + ": " + QuestDesc,
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

### Events
def do_Validate(st, items) :
    if len(items) > 0 :
        for item in items:
            if st.getQuestItemsCount(item[0]) < item[1] :
                return False
    return True

def do_GiveItems(st, items) :
    if len(items) > 0 :
        for item in items:
            st.giveItems(item[0], item[1])

def do_TakeItems(st, items) :
    if len(items) > 0 :
        for item in items:
            st.takeItems(item[0], item[1])

def do_Teleport(st, items) :
    if len(items) > 0 :
        st.player.teleToLocation(items[0], items[1], items[2])

def do_RequestedEvent(event, st, item) :
    if do_Validate(st, item[3]) :
        do_TakeItems(st, item[3])
        do_GiveItems(st, item[2])
        do_Teleport(st, item[4])
        if SuccessMsg != "" :
            return SuccessMsg
        return event + ".htm"
    else :
        if FailureMsg != "" :
            return FailureMsg
        return event + "-0.htm"

def do_RequestEvent(event,st) :
    htmltext = event

    if event == "0":
        if CancelMsg != "" :
            return CancelMsg
        return "Transaction has been canceled."

    for item in Items:
        if event == str(item[1]):
            return do_RequestedEvent(event, st, item)

	if htmltext != event:
		st.setState(COMPLETED)
		st.exitQuest(1)

    return htmltext

### main code
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    return do_RequestEvent(event,st)

 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say with you</body></html>"
   st.setState(STARTED)
   if InitialHtml == "onEvent" :
     return do_RequestEvent(str(npcId),st)
   elif InitialHtml != "" :
     return InitialHtml
   return htmltext

### Quest class and state definition
QUEST       = Quest(QuestId, str(QuestId) + "_" + QuestName, QuestDesc)
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

print  ": Loaded " + str(len(Items)) + " item(s)"
