print "importing blacksmith data: 1002_unseal"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event

# BW Gloves*BW Gloves Heavy
    if event == "1":
        if st.getQuestItemsCount(2487) >= 1:
            st.takeItems(2487,1)
            st.giveItems(5718,1)
            htmltext = "Item has been succesfully unsealed."
	else:
             htmltext = "You do not have enough materials."

# BW Gloves*BW Gloves Light
    if event == "2":
        if st.getQuestItemsCount(2487) >= 1:
            st.takeItems(2487,1)
            st.giveItems(5719,1)
            htmltext = "Item has been succesfully unsealed."
	else:
             htmltext = "You do not have enough materials."

# BW Gloves*BW Gloves Robe
    if event == "3":
        if st.getQuestItemsCount(2487) >= 1:
            st.takeItems(2487,1)
            st.giveItems(5720,1)
            htmltext = "Item has been succesfully unsealed."
    	else:
             htmltext = "You do not have enough materials."

# Doom Gloves*Doom Gloves Heavy
    if event == "4":
        if st.getQuestItemsCount(2475) >= 1:
            st.takeItems(2475,1)
            st.giveItems(5722,1)
            htmltext = "Item has been succesfully unsealed."
  	else:
             htmltext = "You do not have enough materials."

# Doom Gloves*Doom Gloves Light
    if event == "5":
         if st.getQuestItemsCount(2475) >= 1:
            st.takeItems(2475,1)
            st.giveItems(5723,1)
            htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Doom Gloves*Doom Gloves Robe
    if event == "6":
         if st.getQuestItemsCount(2475) >= 1:
            st.takeItems(2475,1)
            st.giveItems(5724,1)
            htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Zubei Gauntlets*Zubei Gauntlets Heavy
    if event == "7":
         if st.getQuestItemsCount(612) >= 1:
            st.takeItems(612,1)
            st.giveItems(5710,1)
            htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Zubei Gauntlets*Zubei Gauntlets Light
    if event == "8":
         if st.getQuestItemsCount(612) >= 1:
            st.takeItems(612,1)
            st.giveItems(5711,1)
            htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Zubei Gauntlets*Zubei Gauntlets Robe
    if event == "9":
         if st.getQuestItemsCount(612) >= 1:
            st.takeItems(612,1)
            st.giveItems(5712,1)
            htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Avadon Gloves*Avadon Gloves Heavy
    if event == "10":
         if st.getQuestItemsCount(2464) >= 1:
            st.takeItems(2464,1)
            st.giveItems(5714,1)
            htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Avadon Gloves*Avadon Gloves Light
    if event == "11":
         if st.getQuestItemsCount(2464) >= 1:
            st.takeItems(2464,1)
            st.giveItems(5715,1)
            htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Avadon Gloves*Avadon Gloves Robe
    if event == "12":
         if st.getQuestItemsCount(2464) >= 1:
            st.takeItems(2464,1)
            st.giveItems(5716,1)
            htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# BW Boots*BW Boots Heavy
    if event == "13":
        if st.getQuestItemsCount(2439) >= 1:
            st.takeItems(2439,1)
            st.giveItems(5734,1)
            htmltext = "Item has been succesfully unsealed."
	else:
             htmltext = "You do not have enough materials."

# BW Boots*BW Boots Light
    if event == "14":
        if st.getQuestItemsCount(2439) >= 1:
            st.takeItems(2439,1)
            st.giveItems(5735,1)
            htmltext = "Item has been succesfully unsealed."
	else:
             htmltext = "You do not have enough materials."

# BW Boots*BW Boots Robe
    if event == "15":
        if st.getQuestItemsCount(2439) >= 1:
            st.takeItems(2439,1)
            st.giveItems(5736,1)
            htmltext = "Item has been succesfully unsealed."
    	else:
             htmltext = "You do not have enough materials."

# Doom Boots*Doom Boots Heavy
    if event == "16":
        if st.getQuestItemsCount(601) >= 1:
            st.takeItems(601,1)
            st.giveItems(5738,1)
            htmltext = "Item has been succesfully unsealed."
  	else:
             htmltext = "You do not have enough materials."

# Doom Boots*Doom Boots Light
    if event == "17":
         if st.getQuestItemsCount(601) >= 1:
            st.takeItems(601,1)
            st.giveItems(5739,1)
            htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Doom Boots*Doom Boots Robe
    if event == "18":
         if st.getQuestItemsCount(601) >= 1:
            st.takeItems(601,1)
            st.giveItems(5740,1)
            htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Zubei Boots*Zubei Boots Heavy
    if event == "19":
         if st.getQuestItemsCount(554) >= 1:
            st.takeItems(554,1)
            st.giveItems(5726,1)
            htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Zubei Boots*Zubei Boots Light
    if event == "20":
         if st.getQuestItemsCount(554) >= 1:
            st.takeItems(554,1)
            st.giveItems(5727,1)
            htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Zubei Boots*Zubei Boots Robe
    if event == "21":
         if st.getQuestItemsCount(554) >= 1:
            st.takeItems(554,1)
            st.giveItems(5728,1)
            htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Avadon Boots*Avadon Boots Heavy
    if event == "22":
         if st.getQuestItemsCount(600) >= 1:
            st.takeItems(600,1)
            st.giveItems(5730,1)
            htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Avadon Boots*Avadon Boots Light
    if event == "23":
         if st.getQuestItemsCount(600) >= 1:
            st.takeItems(600,1)
            st.giveItems(5731,1)
            htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

# Avadon Boots*Avadon Boots Robe
    if event == "24":
         if st.getQuestItemsCount(600) >= 1:
            st.takeItems(600,1)
            st.giveItems(5732,1)
            htmltext = "Item has been succesfully unsealed."
         else:
             htmltext = "You do not have enough materials."

    if event == "0":
	htmltext = "Trade has been canceled."
    return htmltext

 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say to you.</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(COMPLETED)
#   if npcId == 7471 or npcId == 7300 or npcId == 7688 or npcId == 7458 or npcId == 7317 or npcId == 7298 or npcId == 7846 or npcId == 7678 :
#      htmltext = "1.htm"
   return "1.htm"

QUEST       = Quest(1002,"1002_unseal","Blacksmith")
CREATED     = State('Start',     QUEST)
STARTED     = State('Started',   QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7300)
QUEST.addStartNpc(7846)
QUEST.addStartNpc(7678)
QUEST.addStartNpc(7471)

STARTED.addTalkId(7300)
STARTED.addTalkId(7846)
STARTED.addTalkId(7678)
STARTED.addTalkId(7471)
