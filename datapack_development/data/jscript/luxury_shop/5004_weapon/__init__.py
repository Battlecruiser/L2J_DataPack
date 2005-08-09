### ---------------------------------------------------------------------------
### <history>
###		[TI]Blue blue@teamimprovision.com	7/26/2005	Created
### </history>
### ---------------------------------------------------------------------------

### Settings
NPC         = [7097]
QuestId     = 5004
QuestName   = "weapon"
QuestDesc   = "luxury_shop"
InitialHtml = "1.htm"

### Items - Format [name, giveItemId, giveItemQty, takeItem1Id, takeItem1Qty, takeItem2Id, takeItem2Qty]
Items       = [
["Flamberge", 71, 1, 1459, 573, 1458, 2865],
["Stormbringer", 72, 1, 1459, 573, 1458, 2865],
["Sword of Delusion", 76, 1, 1459, 1075, 1458, 5375],
["Silver Axe", 161, 1, 1459, 573, 1458, 2865],
["War Axe", 162, 1, 1459, 1075, 1458, 5375],
["Crystal Staff", 192, 1, 1459, 573, 1458, 2865],
["Sages Staff", 200, 1, 1459, 1075, 1458, 5375],
["Cursed Dagger", 226, 1, 1459, 573, 1458, 2865],
["Dark Screamer", 233, 1, 1459, 1075, 1458, 5375],
["Chakram", 263, 1, 1459, 573, 1458, 2865],
["Fist Blade", 265, 1, 1459, 1075, 1458, 5375],
["Crystalized Ice Bow", 281, 1, 1459, 573, 1458, 2865],
["Akat Long Bow", 283, 1, 1459, 1075, 1458, 5375],
["Orcish Glaive", 298, 1, 1459, 573, 1458, 2865],
["Poleaxe", 95, 1, 1459, 1075, 1458, 5375]
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
def do_RequestedEvent(event, st, giveItemId, giveItemQty, takeItem1Id, takeItem1Qty, takeItem2Id, takeItem2Qty) :
    if st.getQuestItemsCount(takeItem1Id) >= takeItem1Qty and st.getQuestItemsCount(takeItem2Id) >= takeItem2Qty :
        st.takeItems(takeItem1Id, takeItem1Qty)
        st.takeItems(takeItem2Id, takeItem2Qty)
        st.giveItems(giveItemId, giveItemQty)
        st.exitQuest(True)
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
            htmltext = do_RequestedEvent(event, st, item[1], item[2], item[3], item[4], item[5], item[6])

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