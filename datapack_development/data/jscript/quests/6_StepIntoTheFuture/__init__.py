# Created by CubicVirtuoso
# Any problems feel free to drop by #l2j-datapack on irc.freenode.net
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

BAULRO_LETTER = 7571
SCROLL_OF_ESCAPE_GIRAN = 7559
MARK_OF_TRAVELER = 7570

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7006-03.htm" :
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
    elif event == "7033-02.htm" :
        st.set("cond","2")
        st.giveItems(BAULRO_LETTER,1)
    elif event == "7311-03.htm" :
        st.set("cond","3")
        st.takeItems(BAULRO_LETTER,1)
    elif event == "7006-06.htm" :
        st.giveItems(SCROLL_OF_ESCAPE_GIRAN,1)
        st.giveItems(MARK_OF_TRAVELER, 1)
        st.unset("cond")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
    return htmltext

 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
     if st.getPlayer().getRace().ordinal() == 0 :
       htmltext = "7006-02.htm"
     else :
       htmltext = "7006-01.htm"
       st.exitQuest(1)
   elif id == COMPLETED :
      htmltext = "<html><head><body>I can't supply you with another Giran Scroll of Escape. Sorry traveller.</body></html>"
   elif npcId == 7006 and int(st.get("cond"))==1 :
      htmltext = "7006-04.htm"
   elif npcId == 7033 :
      if int(st.get("cond"))==1:
         htmltext = "7033-01.htm"
      elif int(st.get("cond"))==2 and st.getQuestItemsCount(BAULRO_LETTER):
         htmltext = "7033-03.htm"
   elif npcId == 7311 and int(st.get("cond"))==2 :
      if st.getQuestItemsCount(BAULRO_LETTER) :
         htmltext = "7311-02.htm"
   elif npcId == 7006 and int(st.get("cond"))==3 :
      htmltext = "7006-05.htm"
   return htmltext

QUEST       = Quest(6,"6_StepIntoTheFuture","Step Into The Future")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7006)

CREATED.addTalkId(7006)
COMPLETED.addTalkId(7006)

STARTED.addTalkId(7006)
STARTED.addTalkId(7033)
STARTED.addTalkId(7311)

STARTED.addQuestDrop(7033,BAULRO_LETTER,1)

print "importing quests: 6: Step Into the Future"
