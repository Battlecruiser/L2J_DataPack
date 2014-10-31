import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

DIMENSION_FRAGMENT_ID = 7079

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      htmltext = "4.htm"
      st.set("cond","1")
    elif event == "2" :
          htmltext = "5.htm"
          st.playSound("ItemSound.quest_finish")
          st.exitQuest(1)
          
    return htmltext


 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say to you</body></html>"
   id = st.getState()
   if id == CREATED :
       st.set("cond","0")
       htmltext = "4.htm"
   elif id == STARTED :
       st.set("cond","1")
       htmltext = "4.htm"
       
   return htmltext


 def onKill (self,npc,st):
    npcId = npc.getNpcId()
    if npcId in range(1208,1256) :
         if st.getRandom(10)<6 :
           st.giveItems(DIMENSION_FRAGMENT_ID,1)
           st.playSound("ItemSound.quest_itemget")

    return


QUEST       = Quest(634, "634_InSearchofDimensionalFragments", "In Search of Dimensional Fragments")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)

for npcId in range(8494,8508):
	CREATED.addTalkId(npcId)
	STARTED.addTalkId(npcId)
	QUEST.addStartNpc(npcId)

for mobs in range(1208,1256):
	STARTED.addKillId(mobs)
	
STARTED.addQuestDrop(7079,DIMENSION_FRAGMENT_ID,1)

print "importing quests: 634: In Search of Dimensional Fragments"
