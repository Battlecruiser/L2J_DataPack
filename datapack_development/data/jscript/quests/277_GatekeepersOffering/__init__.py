# Made by Mr. Have fun! Version 0.2                           # 
# Fixed by Pela Version 0.3                              # 

print "importing quests: 277: Gatekeepers Offering" 
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
          st.set("id","0") 
          if st.getPlayer().getLevel() >= 15 : 
            htmltext = "7576-03.htm" 
            st.set("cond","1") 
            st.setState(STARTED) 
            st.playSound("ItemSound.quest_accept") 
          else: 
            htmltext = "7576-01.htm" 
    return htmltext 


 def onTalk (Self,npcId,st): 
   htmltext = "<html><head><body>I have nothing to say you</body></html>" 
   id = st.getState() 
   if id == CREATED : 
     st.setState(STARTING) 
     st.set("cond","0") 
     st.set("onlyone","0") 
     st.set("id","0") 
   if npcId == 7576 and int(st.get("cond"))==0 : 
          if int(st.get("cond")) < 15 : 
            htmltext = "7576-02.htm" 
            return htmltext 
   elif npcId == 7576 and int(st.get("cond"))==1 and st.getQuestItemsCount(STARSTONE1_ID)<20 : 
     if id == STARTING or id == COMPLETED : 
            htmltext = "7576-02.htm" 
     else : 
            htmltext = "7576-04.htm" 
   elif npcId == 7576 and int(st.get("cond"))==1 and st.getQuestItemsCount(STARSTONE1_ID)>=20 : 
          if int(st.get("id")) != 277 : 
            st.set("id","277") 
            htmltext = "7576-05.htm" 
            st.takeItems(STARSTONE1_ID,st.getQuestItemsCount(STARSTONE1_ID)) 
            st.giveItems(GATEKEEPER_CHARM_ID,1) 
            st.set("cond","0") 
            st.setState(COMPLETED) 
            st.playSound("ItemSound.quest_finish") 
          else: 
            htmltext = "7576-02.htm" 
   return htmltext 

 def onKill (self,npcId,st): 
   if npcId == 333 : 
        st.set("id","0") 
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(STARSTONE1_ID) < 20 : 
          if st.getRandom(2) == 0 : 
            if st.getQuestItemsCount(STARSTONE1_ID) == 19 : 
              st.giveItems(STARSTONE1_ID,1) 
              st.playSound("ItemSound.quest_middle") 
            else: 
              st.giveItems(STARSTONE1_ID,1) 
              st.playSound("ItemSound.quest_itemget") 
   return 

QUEST       = Quest(277,"277_GatekeepersOffering","Gatekeepers Offering") 
CREATED     = State('Start', QUEST) 
STARTING     = State('Starting', QUEST) 
STARTED     = State('Started', QUEST) 
COMPLETED   = State('Completed', QUEST) 


QUEST.setInitialState(CREATED) 
QUEST.addStartNpc(7576) 

STARTING.addTalkId(7576)

STARTED.addTalkId(7576) 

STARTED.addKillId(333) 

STARTED.addQuestDrop(333,STARSTONE1_ID,1)