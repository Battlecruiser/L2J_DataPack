# Created by CubicVirtuoso
# Any problems feel free to drop by #l2j-datapack on irc.freenode.net
import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

htmlhead = "<html><head><body>"
htmlfoot = "</body></html>"

#NPCs
PETUKAI = 7583
TANAPI  = 7571
TAMIL   = 7576

#REWARDS
ADENA = 57
SCROLL_OF_ESCAPE_GIRAN = 7559
MARK_OF_TRAVELER = 7570

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "7583-03.htm" :
     st.set("cond","1")
     st.set("id","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "7571-02.htm" :
     st.set("cond","2")
     st.set("id","2")
     st.playSound("ItemSound.quest_middle")
   elif event == "7576-02.htm" :
     st.giveItems(SCROLL_OF_ESCAPE_GIRAN,1)
     st.giveItems(MARK_OF_TRAVELER, 1)
     st.unset("cond")
     st.setState(COMPLETED)
     st.playSound("ItemSound.quest_finish")
   return htmltext

 def onTalk (self,npc,st):
   htmltext = htmlhead + "I have nothing to say you" + htmlfoot
   npcId = npc.getNpcId()
   cond  = st.getInt("cond")
   id    = st.getState()

   if id == CREATED :
     if st.getPlayer().getRace().ordinal() == 3 :
       if st.getPlayer().getLevel() >= 3 :
         htmltext = "7583-02.htm"
       else:
         htmltext = htmlhead + "Quest for characters level 3 and above." + htmlfoot
         st.exitQuest(1)
     else :
       htmltext = "7583-01.htm"
       st.exitQuest(1)
   elif id == COMPLETED :
     htmltext = htmlhead + "I can't supply you with another Giran Scroll of Escape. Sorry traveller." + htmlfoot
   elif npcId == PETUKAI and cond == 1 :
     htmltext = "7583-04.htm"
   elif npcId == TANAPI and cond >= 1 :
     htmltext = "7571-01.htm"
   elif npcId == TAMIL and cond == 2 :
     htmltext = "7576-01.htm"

   return htmltext

qnum  = 9
qdef  = str(qnum) + "_IntoTheCityOfHumans"
qname = "Into the City of Humans"

QUEST     = Quest(qnum,qdef,qname)
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(PETUKAI)

CREATED.addTalkId(PETUKAI)

STARTED.addTalkId(PETUKAI)
STARTED.addTalkId(TANAPI)
STARTED.addTalkId(TAMIL)

COMPLETED.addTalkId(PETUKAI)

print "importing quests: " + str(qnum) + ": " + qname