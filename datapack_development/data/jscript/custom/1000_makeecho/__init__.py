print "importing custom data: 1000_makeecho"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event

# Theme Of Journey
    if event == "1":
        if st.getQuestItemsCount(4410) >= 1:
            st.takeItems(57,250)
            st.giveItems(4411,1)
            htmltext = "Echo Crystal Formed."
    else:
             htmltext = "You do not have the right materials."

# Theme of Battle
    if event == "2":
        if st.getQuestItemsCount(4409) >= 1:
            st.takeItems(57,250)
            st.giveItems(4412,1)
            htmltext = "Echo Crystal Formed."
    else:
             htmltext = "You do not have the right materials."

# Theme of Love
    if event == "3":
        if st.getQuestItemsCount(4408) >= 1:
            st.takeItems(57,250)
            st.giveItems(4413,1)
            htmltext = "Echo Crystal Formed."
    else:
             htmltext = "You do not have the right materials."

# Theme of Solitude
    if event == "4":
        if st.getQuestItemsCount(4420) >= 1:
            st.takeItems(57,250)
            st.giveItems(4414,1)
            htmltext = "Echo Crystal Formed."
    else:
             htmltext = "You do not have the right materials."

# Theme of Feast
    if event == "5":
        if st.getQuestItemsCount(4421) >= 1:
            st.takeItems(57,250)
            st.giveItems(4415,1)
            htmltext = "Echo Crystal Formed."
    else:
             htmltext = "You do not have the right materials."

# Theme of Celebration
    if event == "6":
        if st.getQuestItemsCount(4418) >= 1:
            st.takeItems(57,250)
            st.giveItems(4416,1)
            htmltext = "Echo Crystal Formed."
    else:
             htmltext = "You do not have the right materials."

# Theme of Comedy
    if event == "7":
        if st.getQuestItemsCount(4419) >= 1:
            st.takeItems(57,250)
            st.giveItems(4417,1)
            htmltext = "Echo Crystal Formed."
    else:
             htmltext = "You do not have the right materials."


    if event == "0":
           htmltext = "Trade has been canceled."
    return htmltext

 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say to you.</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(COMPLETED)
#   if npcId == 8042 or npcId == 8043 :
#      htmltext = "1.htm"
   return "1.htm"

QUEST       = Quest(1000,"1000_makeecho","Custom")
CREATED     = State('Start',     QUEST)
STARTED     = State('Started',   QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(8042)
QUEST.addStartNpc(8043)

STARTED.addTalkId(8042)
STARTED.addTalkId(8043)
