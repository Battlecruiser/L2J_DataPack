# Made by Mr. Have fun! Version 0.2
# fixed by Elektra and Rolarga Version 0.3
# fixed by Mr and Drlecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

KEY_1 = 4323
KEY_2 = 4324
BEER = 4321
ECHO = 4421

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7959-02.htm" :
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
    elif event == "7957-02.htm" :
        st.set("cond","2")
        st.giveItems(KEY_1,1)
        st.giveItems(KEY_2,1)
    elif event == "7961-03.htm" :
      if st.getQuestItemsCount(KEY_1) :
        st.takeItems(KEY_1,1)
        htmltext = "7961-02.htm"
    elif event == "7960-03.htm" :
      if st.getQuestItemsCount(KEY_2) :
        st.takeItems(KEY_2,1)
        st.giveItems(BEER,1)
        htmltext = "7960-02.htm"
    return htmltext

 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
     st.set("ok","0")
   cond=int(st.get("cond"))
   if npcId == 7959 :
     if cond == 0 :
        htmltext = "7959-01.htm"
     elif cond == 3 :
        st.playSound("ItemSound.quest_finish")
        st.giveItems(ECHO,1)
        st.exitQuest(1)
        htmltext = "7959-03.htm"
     elif cond >= 1 :
        htmltext = "7959-02.htm"
   elif npcId == 7957 :
     if cond == 1 :
        htmltext = "7957-01.htm"
     elif cond == 2 and not st.getQuestItemsCount(KEY_1) and int(st.get("ok")):
        st.set("cond","3")
        htmltext = "7957-04.htm"
     elif cond == 3 :
        htmltext = "7957-05.htm"
     elif cond == 2 :
        htmltext = "7957-03.htm"
   elif npcId == 7960 and cond :
        htmltext = "7960-01.htm"
   elif npcId == 7961 and cond :
        htmltext = "7961-01.htm"
   elif npcId == 7060 and st.getQuestItemsCount(BEER) :
        st.set("ok","1")
        st.takeItems(BEER,1)
        htmltext = "7060-01.htm"
   return htmltext

QUEST       = Quest(364,"364_JovialAccordion","Ask What You Need to Do")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7959)

CREATED.addTalkId(7959)

for npcId in [7959,7957,7060,7961,7960]:
  STARTED.addTalkId(npcId)

STARTED.addQuestDrop(7959,KEY_1,1)
STARTED.addQuestDrop(7959,KEY_2,1)
STARTED.addQuestDrop(7960,BEER,1)

print "importing quests: 364: Jovial Accordion"
