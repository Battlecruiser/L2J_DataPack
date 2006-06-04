# Created by CubicVirtuoso
# Any problems feel free to drop by #l2j-datapack on irc.freenode.net
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPCs 
JASMINE = 7134 
ROSELYN = 7355 
HARNE   = 7144 

#ITEM 
ROSELYNS_NOTE = 7573 
 
#REWARDS 
ADENA      = 57 
SCROLL_OF_ESCAPE_GIRAN = 7559 
MARK_OF_TRAVELER       = 7570 
 
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr) 

 def onEvent (self,event,st) : 
   htmltext = event 
   if event == "7134-03.htm" : 
     st.set("cond","1") 
     st.setState(STARTED) 
     st.playSound("ItemSound.quest_accept") 
   elif event == "7355-02.htm" : 
     st.giveItems(ROSELYNS_NOTE,1) 
     st.set("cond","2") 
     st.set("id","2") 
     st.playSound("ItemSound.quest_middle") 
   elif event == "7144-02.htm" : 
     st.takeItems(ROSELYNS_NOTE,-1) 
     st.set("cond","3") 
     st.set("id","3") 
     st.playSound("ItemSound.quest_middle") 
   elif event == "7134-06.htm" : 
     st.giveItems(SCROLL_OF_ESCAPE_GIRAN,1) 
     st.giveItems(MARK_OF_TRAVELER, 1) 
     st.set("cond","0") 
     st.setState(COMPLETED) 
     st.playSound("ItemSound.quest_finish") 
   return htmltext 

 def onTalk (Self,npc,st): 
   htmltext = "<html><head><body>I have nothing to say you</body></html>" 
   npcId = npc.getNpcId() 
   cond  = st.getInt("cond") 
   id    = st.getState() 

   if id == CREATED : 
     st.set("cond","0") 
     if st.getPlayer().getRace().ordinal() == 2 : 
       if st.getPlayer().getLevel() >= 3 : 
         htmltext = "7134-02.htm" 
       else : 
         htmltext = "<html><head><body>Quest for characters level 3 and above.</body></html>" 
         st.exitQuest(1) 
     else : 
       htmltext = "7134-01.htm" 
       st.exitQuest(1) 
   elif npcId == JASMINE and id == COMPLETED : 
     htmltext = "<html><head><body>I can't supply you with another Giran Scroll of Escape. Sorry traveller.</body></html>" 
   elif npcId == JASMINE and cond == 1 : 
     htmltext = "7134-04.htm" 
   elif npcId == ROSELYN and cond : 
     if st.getQuestItemsCount(ROSELYNS_NOTE) == 0 : 
       htmltext = "7355-01.htm" 
     else : 
       htmltext = "7355-03.htm" 
   elif npcId == HARNE and cond == 2 and st.getQuestItemsCount(ROSELYNS_NOTE) > 0 : 
     htmltext = "7144-01.htm" 
   elif npcId == JASMINE and cond == 3 : 
     htmltext = "7134-05.htm" 

   return htmltext 

QUEST     = Quest(8,"8_AnAdventureBegins","An Adventure Begins") 
CREATED   = State('Start',     QUEST) 
STARTED   = State('Started',   QUEST) 
COMPLETED = State('Completed', QUEST) 
 
QUEST.setInitialState(CREATED)
QUEST.addStartNpc(JASMINE) 

CREATED.addTalkId(JASMINE) 
COMPLETED.addTalkId(JASMINE) 

STARTED.addTalkId(JASMINE) 
STARTED.addTalkId(ROSELYN) 
STARTED.addTalkId(HARNE) 

STARTED.addQuestDrop(JASMINE,ROSELYNS_NOTE,1) 

print "importing quests: 8: An Adventure Begins" 
