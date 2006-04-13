NPC         = [7731,7827,7828,7829,7830,7831,7869,8067,8265,8309]
InitialHtml = "1.htm"

import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest


class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
# Kookaburra
    if event == "1" :
        if st.getQuestItemsCount(7585) > 0:
            st.takeItems(7585,1)
            st.giveItems(6650,1)
            st.giveItems(7582,500)
            htmltext = "Exchanged succesfully."
	else:
            htmltext = "You don't have required item: Pet Exchange Ticket: Kookaburra."

# Buffalo
    if event == "2" :
        if st.getQuestItemsCount(7583) > 0:
            st.takeItems(7583,1)
            st.giveItems(6648,1)
            st.giveItems(7582,500)
            htmltext = "Exchanged succesfully."
	else:
            htmltext = "You don't have required item: Pet Exchange Ticket: Buffalo."

# Cougar
    if event == "3" :
        if st.getQuestItemsCount(7584) > 0:
            st.takeItems(7584,1)
            st.giveItems(6649,1)
            st.giveItems(7582,500)
            htmltext = "Exchanged succesfully."
  	else:
            htmltext = "You don't have required item: Pet Exchange Ticket: Cougar."

    if htmltext != event :
      st.setState(COMPLETED)
      st.exitQuest(1)
    return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say with you</body></html>"
   return InitialHtml

### Quest class and state definition
QUEST       = Quest(5007,"5007_pet_exchange","custom")
CREATED     = State('Start',     QUEST)
COMPLETED   = State('Completed', QUEST)

### Quest initialization
QUEST.setInitialState(CREATED)

for item in NPC:
### Quest NPC starter initialization
   QUEST.addStartNpc(item)
### Quest NPC initialization
   CREATED.addTalkId(item)

print "importing custom data: 5007_pet_exchange"
