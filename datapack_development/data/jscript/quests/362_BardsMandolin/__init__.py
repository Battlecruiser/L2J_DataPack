# Bards Mandolin Written By MickyLee
print "importing quests: 362: Bards Mandolin"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest


class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("cond","1")
        st.set("id","0")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        htmltext = "7957_2.htm"
    
    elif event == "2" :
        st.giveItems(57,10000)
        st.giveItems(4410,1)
        st.set("cond","0")
        st.setState(COMPLETED)
        htmltext = "7957_5.htm"
        st.exitQuest(1)
        
    return htmltext


 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say to you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7957 and int(st.get("cond")) == 0 :   
        htmltext = "7957_1.htm"
   elif npcId == 7837 and int(st.get("cond")) == 1 :
        htmltext = "7837_1.htm"
   elif npcId == 7958 and int(st.get("cond")) == 1 :
        st.giveItems(4316,1)
        htmltext = "7958_1.htm"
   elif npcId == 7957 and int(st.get("cond")) == 1 and st.getQuestItemsCount(4316) == 1 and st.getQuestItemsCount(4317) == 0 :
        st.giveItems(4317,1)
        htmltext = "7957_3.htm"
   elif npcId == 7957 and int(st.get("cond")) == 1 and st.getQuestItemsCount(4316) == 1 and st.getQuestItemsCount(4317) == 1 :
        htmltext = "7957_6.htm"
   elif npcId == 7956 and int(st.get("cond")) == 1 and st.getQuestItemsCount(4316) == 1 and st.getQuestItemsCount(4317) == 1 :
        st.takeItems(4316,1)
        st.takeItems(4317,1)
        st.set("id","1")
        htmltext = "7956_1.htm"
   elif npcId == 7957 and int(st.get("cond")) == 1 and int(st.get("id")) == 1 :
        st.set("id","0")
        htmltext = "7957_4.htm"
        
   return htmltext


QUEST       = Quest(362,"362_BardsMandolin","Bards Mandolin")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7957)

STARTING.addTalkId(7957)

STARTED.addTalkId(7957)
STARTED.addTalkId(7956)
STARTED.addTalkId(7958)
STARTED.addTalkId(7837)

#STARTED.addQuestDrop(4629,RED_SOUL_CRYSTAL0_ID,1)
#STARTED.addQuestDrop(4630,RED_SOUL_CRYSTAL1_ID,1)
#STARTED.addQuestDrop(4631,RED_SOUL_CRYSTAL2_ID,1)
