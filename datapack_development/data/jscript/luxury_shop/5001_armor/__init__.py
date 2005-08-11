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
InitialHtml = "1.htm"

### Items - Format [name, giveItemId, giveItemQty, takeItem1Id, takeItem1Qty, takeItem2Id, takeItem2Qty]
Items       = [
["Chain Mail Shirt", 354, 1, 1459, 127, 1458, 635],
["Chain Gaiters", 381, 1, 1459, 79, 1458, 395],
["Composite Armor", 60, 1, 1459, 380, 1458, 1800],
["Tempered Mithril Shirt", 397, 1, 1459, 95, 1458, 475],
["Tempered Mithril Gaiters", 2387, 1, 1459, 59, 1458, 295],
["Theca Leather Mail", 400, 1, 1459, 207, 1458, 1035],
["Theca Leather Gaiters", 420, 1, 1459, 129, 1458, 645],
["Karmian Tunic", 439, 1, 1459, 95, 1458, 475],
["Karmian Stockings", 471, 1, 1459, 59, 1458, 295],
["Demon's Tunic", 441, 1, 1459, 184, 1458, 920],
["Demon's Stockings", 472, 1, 1459, 115, 1458, 575],
["Chain Boots", 2429, 1, 1459, 32, 1458, 160],
["Composite Boots", 64, 1, 1459, 62, 1458, 310],
["Reinforced Mithril Gloves", 2452, 1, 1459, 32, 1458, 160],
["Mithril Gauntlets", 608, 1, 1459, 62, 1458, 310],
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