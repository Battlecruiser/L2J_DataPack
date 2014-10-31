# Made by Mr. Have fun! Version 0.2
# Fixed by Pela Version 0.3 - Enough credits, but DrLecter was here :D
import sys 
from net.sf.l2j.gameserver.model.quest import State 
from net.sf.l2j.gameserver.model.quest import QuestState 
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest 

STARSTONE1_ID = 1572 
GATEKEEPER_CHARM_ID = 1658 

class Quest (JQuest) : 

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr) 

 def onEvent (self,event,st) : 
    htmltext = event 
    if event == "1" : 
       if st.getPlayer().getLevel() >= 15 :
          htmltext = "7576-03.htm"
          st.set("cond","1")
          st.setState(STARTED)
          st.playSound("ItemSound.quest_accept")
       else :
          htmltext = "7576-01.htm"
    return htmltext

 def onTalk (Self,npc,st): 
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>" 
   id = st.getState() 
   if id == CREATED : 
      st.set("cond","0")
   if npcId == 7576 :
      if int(st.get("cond"))==0 : 
         htmltext = "7576-02.htm" 
      elif int(st.get("cond"))==1 and st.getQuestItemsCount(STARSTONE1_ID)<20 : 
         htmltext = "7576-04.htm" 
      elif int(st.get("cond"))==2 and st.getQuestItemsCount(STARSTONE1_ID)>=20 : 
         htmltext = "7576-05.htm" 
         st.takeItems(STARSTONE1_ID,-1) 
         st.giveItems(GATEKEEPER_CHARM_ID,2) 
         st.exitQuest(1)
         st.playSound("ItemSound.quest_finish") 
   return htmltext 

 def onKill (self,npc,st): 
   npcId = npc.getNpcId()
   if npcId == 333 : 
      if int(st.get("cond")) == 1 and st.getQuestItemsCount(STARSTONE1_ID) < 20 :
         if st.getRandom(2) == 0 :
            st.giveItems(STARSTONE1_ID,1)
            if st.getQuestItemsCount(STARSTONE1_ID) == 20 :
               st.playSound("ItemSound.quest_middle")
               st.set("cond","2")
            else :
               st.playSound("ItemSound.quest_itemget")
   return 

QUEST       = Quest(277,"277_GatekeepersOffering","Gatekeepers Offering") 
CREATED     = State('Start', QUEST) 
STARTED     = State('Started', QUEST) 
COMPLETED   = State('Completed', QUEST) 


QUEST.setInitialState(CREATED) 

QUEST.addStartNpc(7576) 
CREATED.addTalkId(7576)
STARTED.addTalkId(7576) 

STARTED.addKillId(333) 
STARTED.addQuestDrop(333,STARSTONE1_ID,1)

print "importing quests: 277: Gatekeepers Offering" 
