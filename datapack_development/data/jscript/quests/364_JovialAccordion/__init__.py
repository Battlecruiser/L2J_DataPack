# Maked by Mr. Have fun! Version 0.2
# fixed by Elektra and Rolarga Version 0.3
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#KEY_1 = 4100
KEY_1 = 4323
KEY_2 = 4324
BEER_ID = 4321
ECHO_ID = 4421

ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        htmltext = "7959-02.htm"
    elif event == "2" :
        st.set("cond","2")
        st.giveItems(KEY_1,1)
        st.giveItems(KEY_2,1)
        htmltext = "7957-02.htm" 
    elif event == "3" :
      if st.getQuestItemsCount(KEY_1)>0 :
        st.takeItems(KEY_1,1)
        htmltext = "7961-02.htm"
      else :
        htmltext = "7961-03.htm"
    elif event == "4" :
      if st.getQuestItemsCount(KEY_2)>0 :
        st.takeItems(KEY_2,1)
        st.giveItems(BEER_ID,1)
        htmltext = "7960-02.htm"
      else :
        htmltext = "7960-03.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   if npcId == 7959 :
     if int(st.get("cond"))==0 :
        htmltext = "7959-01.htm"
     elif int(st.get("cond"))==3 :
        st.playSound("ItemSound.quest_finish")
        st.giveItems(ECHO_ID,1)
        st.exitQuest(1)
        htmltext = "7959-03.htm"
     elif int(st.get("cond"))>=1 :
        htmltext = "7959-02.htm"
   elif npcId == 7957 :
     if int(st.get("cond"))==1 :
        htmltext = "7957-01.htm"
     elif int(st.get("cond"))==2 and st.getQuestItemsCount(KEY_1)==0 and st.getQuestItemsCount(KEY_2)==0 and st.getQuestItemsCount(BEER_ID)==0 :
        st.set("cond","3")
        htmltext = "7957-04.htm"
     elif int(st.get("cond"))==3 :
        htmltext = "7957-05.htm"
     elif int(st.get("cond"))==2 :
        htmltext = "7957-03.htm"
   elif npcId == 7960 and int(st.get("cond"))>1 :
        htmltext = "7960-01.htm"
   elif npcId == 7961 and int(st.get("cond"))>1 :
        htmltext = "7961-01.htm"
   elif npcId == 7060 and st.getQuestItemsCount(BEER_ID)>0 :
        st.takeItems(BEER_ID,1)
        htmltext = "7060-01.htm"
   return htmltext

QUEST       = Quest(364,"364_JovialAccordion","Jovial Accordion")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7959)

for npcId in [7959,7957,7060,7961,7960]:
 STARTED.addTalkId(npcId)


STARTED.addQuestDrop(7959,KEY_1,1)
STARTED.addQuestDrop(7959,KEY_2,1)
STARTED.addQuestDrop(7960,BEER_ID,1)

print "importing quests: 364: Jovial Accordion"