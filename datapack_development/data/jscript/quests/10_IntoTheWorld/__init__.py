# Created by CubicVirtuoso
# Any problems feel free to drop by #l2j-datapack on irc.freenode.net
import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

htmlhead = "<html><head><body>"
htmlfoot = "</body></html>"

#NPCs
BALANKI   = 7533
REED      = 7520
GERALDINE = 7650

#ITEMS
VERY_EXPENSIVE_NECKLACE = 7574

#REWARDS
SCROLL_OF_ESCAPE_GIRAN = 7559
MARK_OF_TRAVELER       = 7570

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "7533-03.htm" :
     st.set("cond","1")
     st.set("id","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "7520-02.htm" :
     st.giveItems(VERY_EXPENSIVE_NECKLACE,1)
     st.set("cond","2")
     st.set("id","2")
     st.playSound("ItemSound.quest_middle")
   elif event == "7650-02.htm" :
     st.takeItems(VERY_EXPENSIVE_NECKLACE,-1)
     st.set("cond","3")
     st.set("id","3")
     st.playSound("ItemSound.quest_middle")
   elif event == "7533-06.htm" :
     st.giveItems(SCROLL_OF_ESCAPE_GIRAN,1)
     st.giveItems(MARK_OF_TRAVELER, 1)
     st.unset("cond")
     st.setState(COMPLETED)
     st.playSound("ItemSound.quest_finish")
   return htmltext

 def onTalk (self,npc,st):
   htmltext = htmlhead + "I have nothing to say you" + htmlfoot
   npcId = npc.getNpcId()
   id    = st.getState()
   cond  = st.getInt("cond")
   count_NECKLACE = st.getQuestItemsCount(VERY_EXPENSIVE_NECKLACE)

   if id == CREATED :
     if st.getPlayer().getRace().ordinal() == 4 :
       if st.getPlayer().getLevel() >= 3 :
         htmltext = "7533-02.htm"
       else :
         htmltext = htmlhead + "Quest for characters level 3 and above." + htmlfoot
         st.exitQuest(1)
     else :
       htmltext = "7533-01.htm"
       st.exitQuest(1)
   elif id == COMPLETED :
     htmltext = htmlhead + "I can't supply you with another Giran Scroll of Escape. Sorry traveller." + htmlfoot
   elif npcId == BALANKI and cond == 1 :
     htmltext = "7533-04.htm"
   elif npcId == REED and cond == 3 :
     htmltext = "7520-04.htm"
     st.set("cond","4")
     st.set("id","4")
     st.playSound("ItemSound.quest_middle")
   elif npcId == REED and cond >= 1 :
     if count_NECKLACE == 0 :
       htmltext = "7520-01.htm"
     else :
       htmltext = "7520-03.htm"
   elif npcId == GERALDINE and cond == 2 and count_NECKLACE :
     htmltext = "7650-01.htm"
   elif npcId == BALANKI and cond == 4 :
     htmltext = "7533-05.htm"
   return htmltext

qnum  = 10
qdef  = str(qnum) + "_IntoTheWorld"
qname = "Into the World"

QUEST     = Quest(qnum,qdef,qname)
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(BALANKI)

CREATED.addTalkId(BALANKI)

STARTED.addTalkId(BALANKI)
STARTED.addTalkId(REED)
STARTED.addTalkId(GERALDINE)

COMPLETED.addTalkId(BALANKI)

STARTED.addQuestDrop(BALANKI,VERY_EXPENSIVE_NECKLACE,1)

print "importing quests: " + str(qnum) + ": " + qname