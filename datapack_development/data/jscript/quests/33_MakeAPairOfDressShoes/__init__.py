# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

htmlhead = "<html><head><body>"
htmlfoot = "</body></html>"

#NPCs
WOODLEY = 7838
IAN     = 7164
LEIKAR  = 8520

#ITEMS
LEATHER = 1882
THREAD  = 1868
ADENA   = 57

#REWARDS
DRESS_SHOES_BOX = 7113

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "7838-1.htm" :
     st.set("cond","1")
     st.set("id","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "8520-1.htm" :
     st.set("cond","2")
     st.set("id","2")
     st.playSound("ItemSound.quest_middle")
   elif event == "7838-3.htm" :
     st.set("cond","3")
     st.set("id","3")
     st.playSound("ItemSound.quest_middle")
   elif event == "7838-5.htm" :
     if st.getQuestItemsCount(LEATHER) >= 200 and st.getQuestItemsCount(THREAD) >= 600 and st.getQuestItemsCount(ADENA) >= 200000 :
       st.takeItems(LEATHER,200)
       st.takeItems(THREAD,600)
       st.takeItems(ADENA,200000)
       st.set("cond","4")
       st.set("id","4")
       st.playSound("ItemSound.quest_middle")
     else :
       htmltext = "You don't have enough materials"
   elif event == "7164-1.htm" :
     if st.getQuestItemsCount(ADENA) >= 300000 :
       st.takeItems(ADENA,300000)
       st.set("cond","5")
       st.set("id","5")
       st.playSound("ItemSound.quest_middle")
     else :
       htmltext = "You don't have enough materials"
   elif event == "7838-7.htm" :
     st.giveItems(DRESS_SHOES_BOX,1)
     st.unset("cond")
     st.setState(COMPLETED)
     st.playSound("ItemSound.quest_finish")
   return htmltext

 def onTalk (self,npc,st):
   htmltext = htmlhead + "I have nothing to say you" + htmlfoot
   npcId = npc.getNpcId()
   id    = st.getState()
   cond  = st.getInt("cond")

   if id == CREATED :
     if st.getPlayer().getLevel() >= 60 :
       if st.getQuestItemsCount(DRESS_SHOES_BOX) == 0 :
         fwear = st.getPlayer().getQuestState("37_PleaseMakeMeFormalWear")
         if not fwear is None :
           if fwear.get("cond") == "7" :
             htmltext = "7838-0.htm"
             return htmltext
       st.exitQuest(1)
     else :
       htmltext = "7838-8.htm"
       st.exitQuest(1)
   elif id == COMPLETED :
     htmltext = htmlhead + "This quest has already been completed." + htmlfoot
   elif npcId == LEIKAR and cond == 1 :
     htmltext = "8520-0.htm"
   elif npcId == WOODLEY and cond == 2 :
     htmltext = "7838-2.htm"
   elif npcId == WOODLEY and cond == 3 :
     htmltext = "7838-4.htm"
   elif npcId == IAN and cond == 4 :
     htmltext = "7164-0.htm"
   elif npcId == WOODLEY and cond == 5 :
     htmltext = "7838-6.htm"
   return htmltext

qnum  = 33
qdef  = str(qnum) + "_MakeAPairOfDressShoes"
qname = "Make a pair of dress shoes"

QUEST     = Quest(qnum,qdef,qname)
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(WOODLEY)

CREATED.addTalkId(WOODLEY)

STARTED.addTalkId(WOODLEY)
STARTED.addTalkId(IAN)
STARTED.addTalkId(LEIKAR)

COMPLETED.addTalkId(WOODLEY)

print "importing quests: " + str(qnum) + ": " + qname