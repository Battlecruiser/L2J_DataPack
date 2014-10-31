# by disKret
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
ABERCROMBIE = 8555
FORGOTTEN_MONUMENT_1,FORGOTTEN_MONUMENT_2,FORGOTTEN_MONUMENT_3,FORGOTTEN_MONUMENT_4,CORPSE_OF_DWARF = range(8661,8666)
#ITEMS
KRORINS_JOURNAL = 7252
#MESSAGES
default = "<html><head><body>I have nothing to say you</body></html>"
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   id = st.getState()
   cond = st.getInt("cond")
   htmltext = event
   if event == "8555-1.htm" and id == CREATED:
     st.set("cond","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "8665-1.htm" and cond == 1:
     st.set("cond","2")
     st.playSound("ItemSound.quest_itemget")
     st.giveItems(KRORINS_JOURNAL,1)
   elif event == "8555-4.htm" and cond == 2:
     st.set("cond","3")
   elif event == "8661-1.htm" and cond == 3:
     st.set("cond","4")
   elif event == "8662-1.htm" and cond == 4:
     st.set("cond","5")
   elif event == "8663-1.htm" and cond == 5:
     st.set("cond","6")
   elif event == "8664-1.htm" and cond == 6:
     st.set("cond","7")
     st.playSound("ItemSound.quest_middle")
   elif event == "8555-7.htm" and cond == 7:
     st.takeItems(KRORINS_JOURNAL,-1)
     st.addExpAndSp(130000,0)
     st.giveItems(57,40000)
     st.playSound("ItemSound.quest_finish")
     st.setState(COMPLETED)
   elif event <> "8663-0a.htm":
     htmltext = default
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = default
   npcId = npc.getNpcId()
   id = st.getState()
   cond = st.getInt("cond")
   if id == COMPLETED :
     htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == ABERCROMBIE :
     if cond == 0 :
       if st.getPlayer().getLevel() >= 66 :
         htmltext = "8555-0.htm"
       else :
         htmltext = "8555-0a.htm"
         st.exitQuest(1)
     elif cond == 1 :
       htmltext = "8555-2.htm"
     elif cond == 2 :
       htmltext = "8555-3.htm"
     elif cond == 3 :
       htmltext = "8555-5.htm"
     elif cond == 7 :
       htmltext = "8555-6.htm"
   elif npcId == CORPSE_OF_DWARF :
     if cond == 1 :
       htmltext = "8665-0.htm"
     elif cond == 2 :
       htmltext = "8665-2.htm"
   elif npcId == FORGOTTEN_MONUMENT_1 :
     if cond == 3 :
       htmltext = "8661-0.htm"
     elif cond > 3 :
       htmltext = "8661-2.htm"
   elif npcId == FORGOTTEN_MONUMENT_2:
     if cond == 4 :
       htmltext = "8662-0.htm"
     elif cond > 4 :
       htmltext = "8662-2.htm"
   elif npcId == FORGOTTEN_MONUMENT_3 :
     if cond == 5 :
       htmltext = "8663-0.htm"
     elif cond > 5 :
       htmltext = "8663-2.htm"
   elif npcId == FORGOTTEN_MONUMENT_4 :
     if cond == 6 :
       htmltext = "8664-0.htm"
     elif cond > 6 :
       htmltext = "8664-2.htm"
   return htmltext

QUEST       = Quest(31,"31_SecretBuriedInTheSwamp","Secret Buried In The Swamp")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(ABERCROMBIE)

CREATED.addTalkId(ABERCROMBIE)
STARTED.addTalkId(ABERCROMBIE)
COMPLETED.addTalkId(ABERCROMBIE)

for i in range(8661,8666):
    STARTED.addTalkId(i)

STARTED.addQuestDrop(ABERCROMBIE,KRORINS_JOURNAL,1)

print "importing quests: 31: Secret Buried In The Swamp"
