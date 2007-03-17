import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "634_InSearchofDimensionalFragments"

DIMENSION_FRAGMENT_ID = 7079

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "2a.htm" :
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      st.set("cond","1")
    elif event == "5.htm" :
      st.playSound("ItemSound.quest_finish")
      st.exitQuest(1)
    return htmltext

 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say to you</body></html>"
   id = st.getState()
   if id == CREATED :
      if st.getPlayer().getLevel() < 20 :
         st.exitQuest(1)
         htmltext="1.htm"
      else:
         htmltext="2.htm"
   elif id == STARTED :
      htmltext = "4.htm"
   return htmltext

 def onKill (self,npc,st):
   if st.getRandom(10)<6 :
      st.giveItems(DIMENSION_FRAGMENT_ID,1)
      st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(634, qn, "In Search of Dimensional Fragments")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST,True)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

for npcId in range(31494,31508):
	CREATED.addTalkId(npcId)
	STARTED.addTalkId(npcId)
	QUEST.addStartNpc(npcId)

for mobs in range(21208,21256):
	STARTED.addKillId(mobs)

STARTED.addQuestDrop(7079,DIMENSION_FRAGMENT_ID,1)

print "importing quests: 634: In Search of Dimensional Fragments"
