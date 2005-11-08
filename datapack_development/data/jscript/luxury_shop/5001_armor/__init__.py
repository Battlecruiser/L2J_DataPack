### ---------------------------------------------------------------------------
### <history>
###		[TI]Blue blue@teamimprovision.com	7/26/2005	Created
### </history>
### ---------------------------------------------------------------------------

### Settings
NPC         = [7098]
QuestId     = 5001
QuestName   = "armor"
QuestDesc   = "luxury_shop"
#QuestDesc   = "Buy armor with crystals"
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
    ["Chain Mail Shirt", 354, [[354, 1]], [[1459, 127], [1458, 635]], []],
    ["Chain Gaiters", 381, [[381, 1]], [[1459, 79], [1458, 395]], []],
    ["Composite Armor", 60, [[60, 1]], [[1459, 380], [1458, 1800]], []],
    ["Tempered Mithril Shirt", 397, [[397, 1]], [[1459, 95], [1458, 475]], []],
    ["Tempered Mithril Gaiters", 2387, [[2387, 1]], [[1459, 59], [1458, 295]], []],
    ["Theca Leather Mail", 400, [[400, 1]], [[1459, 207], [1458, 1035]], []],
    ["Theca Leather Gaiters", 420, [[420, 1]], [[1459, 129], [1458, 645]], []],
    ["Karmian Tunic", 439, [[439, 1]], [[1459, 95], [1458, 475]], []],
    ["Karmian Stockings", 471, [[471, 1]], [[1459, 59], [1458, 295]], []],
    ["Demon's Tunic", 441, [[441, 1]], [[1459, 184], [1458, 920]], []],
    ["Demon's Stockings", 472, [[472, 1]], [[1459, 115], [1458, 575]], []],
    ["Chain Boots", 2429, [[2429, 1]], [[1459, 32], [1458, 160]], []],
    ["Composite Boots", 64, [[64, 1]], [[1459, 62], [1458, 310]], []],
    ["Reinforced Mithril Gloves", 2452, [[2452, 1]], [[1459, 32], [1458, 160]], []],
    ["Mithril Gauntlets", 608, [[608, 1]], [[1459, 62], [1458, 310]], []]
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
