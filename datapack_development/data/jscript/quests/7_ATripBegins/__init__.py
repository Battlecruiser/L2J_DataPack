# Created by CubicVirtuoso
# Any problems feel free to drop by #l2j-datapack on irc.freenode.net
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPCs 
MIRABEL  = 7146 
ARIEL    = 7148 
ASTERIOS = 7154 

#ITEM 
ARIELS_RECOMMENDATION = 7572 
 
#REWARDS 
ADENA                  = 57 
SCROLL_OF_ESCAPE_GIRAN = 7559 
MARK_OF_TRAVELER       = 7570 
 
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr) 

 def onEvent (self,event,st) : 
   htmltext = event 
   if event == "7146-03.htm" : 
     st.set("cond","1") 
     st.setState(STARTED) 
     st.playSound("ItemSound.quest_accept") 
   elif event == "7148-02.htm" : 
     st.giveItems(ARIELS_RECOMMENDATION,1) 
     st.set("cond","2") 
     st.set("id","2") 
     st.playSound("ItemSound.quest_middle") 
   elif event == "7154-02.htm" : 
     st.takeItems(ARIELS_RECOMMENDATION,-1) 
     st.set("cond","3") 
     st.set("id","3") 
     st.playSound("ItemSound.quest_middle") 
   elif event == "7146-06.htm" : 
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
     if st.getPlayer().getRace().ordinal() == 1 : 
       if st.getPlayer().getLevel() >= 3 : 
         htmltext = "7146-02.htm" 
       else : 
         htmltext = "<html><head><body>Quest for characters level 3 above.</body></html>" 
         st.exitQuest(1) 
     else : 
       htmltext = "7146-01.htm" 
       st.exitQuest(1) 
   elif npcId == MIRABEL and id == COMPLETED : 
     htmltext = "<html><head><body>I can't supply you with another Giran Scroll of Escape. Sorry traveller.</body></html>" 
   elif npcId == MIRABEL and cond == 1 : 
     htmltext = "7146-04.htm" 
   elif npcId == ARIEL and cond : 
     if st.getQuestItemsCount(ARIELS_RECOMMENDATION) == 0 : 
       htmltext = "7148-01.htm" 
     else : 
       htmltext = "7148-03.htm" 
   elif npcId == ASTERIOS and cond == 2 and st.getQuestItemsCount(ARIELS_RECOMMENDATION) > 0 : 
     htmltext = "7154-01.htm" 
   elif npcId == MIRABEL and cond == 3 : 
     htmltext = "7146-05.htm" 

   return htmltext 

QUEST     = Quest(7,"7_ATripBegins","A Trip Begins") 
CREATED   = State('Start',     QUEST) 
STARTED   = State('Started',   QUEST) 
COMPLETED = State('Completed', QUEST) 
 
QUEST.setInitialState(CREATED)
QUEST.addStartNpc(MIRABEL) 

CREATED.addTalkId(MIRABEL) 
COMPLETED.addTalkId(MIRABEL) 

STARTED.addTalkId(MIRABEL) 
STARTED.addTalkId(ARIEL) 
STARTED.addTalkId(ASTERIOS) 

STARTED.addQuestDrop(MIRABEL,ARIELS_RECOMMENDATION,1) 

print "importing quests: 7: A Trip Begins" 
