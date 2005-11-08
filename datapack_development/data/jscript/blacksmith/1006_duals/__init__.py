#Make by GreenHope and fix by Prograsso (Baghak)
print "importing quests: 1006: Blacksmith"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event

    if event == "1":
         if st.getQuestItemsCount(142)>=1 and st.getQuestItemsCount(5126)>=1 and st.getQuestItemsCount(5575)>=1953000:
            st.takeItems(142,1)
            if st.getQuestItemsCount(142)>=1:
               st.takeItems(142,1)
               st.takeItems(5126,1)
               st.takeItems(5575,1953000)
               st.giveItems(5233,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(142,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."
   
   
    if event == "2":
         if st.getQuestItemsCount(79)>=1 and st.getQuestItemsCount(142)>=1 and st.getQuestItemsCount(5126)>=1 and st.getQuestItemsCount(5575)>=3989000:
             st.takeItems(79,1)
             st.takeItems(142,1)
             st.takeItems(5126,1)
             st.takeItems(5575,3989000)
             st.giveItems(5705,1)
             htmltext = "Item has been succesfully created." 
         else:
             htmltext = "You do not have enough materials."

    if event == "3":
         if st.getQuestItemsCount(79)>=1 and st.getQuestItemsCount(5126)>=1 and st.getQuestItemsCount(5575)>=2310000:
            st.takeItems(79,1)
            if st.getQuestItemsCount(79)>=1:
               st.takeItems(79,1)
               st.takeItems(5126,1)
               st.takeItems(5575,2310000)
               st.giveItems(5706,1)
               htmltext = "Item has been succesfully created."
            else:
               st.giveItems(79,1)
               htmltext = "You need one more sword."
         else:
             htmltext = "You do not have enough materials."

    if event == "0":
           htmltext = "Cancel."
    
    if htmltext != event:
      st.setState(COMPLETED)
      st.exitQuest(1)

    return htmltext

 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say to you</body></html>"
   st.setState(STARTED)
   if npcId == 8126 :
      htmltext = "dual.htm"
   return htmltext

QUEST       = Quest(1006,"1006_duals","Blacksmith")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(8126)

STARTED.addTalkId(8126) 
