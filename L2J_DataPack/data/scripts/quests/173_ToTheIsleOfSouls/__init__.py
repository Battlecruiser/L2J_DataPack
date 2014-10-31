# Contributed by t0rm3nt0r (tormentor2000@mail.ru) to the Official L2J Datapack Project
# Visit http://forum.l2jdp.com for more details.

import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#Complete - 100%. 
qn = "173_ToTheIsleOfSouls"

#NPC'S
GALLADUCCI = 30097
GENTLER = 30094

#ITEM'S
SCROLL_OF_ESCAPE_KAMAEL_VILLAGE = 9716
MARK_OF_TRAVELER       = 7570
GWAINS_DOCUMENT = 7563
MAGIC_SWORD_HILT = 7568
 
class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr) 
     self.questItemIds = [MARK_OF_TRAVELER, GWAINS_DOCUMENT, MAGIC_SWORD_HILT]

 def onEvent (self,event,st) :
     htmltext = event
     if event == "30097-03.htm" :
       st.set("cond","1")
       st.setState(State.STARTED)
       st.giveItems(GWAINS_DOCUMENT,1)
       st.playSound("ItemSound.quest_accept")
     elif event == "30094-02.htm" :
       st.set("cond","2")
       st.takeItems(GWAINS_DOCUMENT,-1)
       st.giveItems(MAGIC_SWORD_HILT,1)
       st.playSound("ItemSound.quest_middle")
     elif event == "30097-06.htm" :
       st.takeItems(MAGIC_SWORD_HILT,-1)
       st.takeItems(MARK_OF_TRAVELER,-1)
       st.giveItems(SCROLL_OF_ESCAPE_KAMAEL_VILLAGE,1)
       st.playSound("ItemSound.quest_finish")
       st.exitQuest(False)
       st.unset("cond")
     return htmltext

 def onTalk (self,npc,player):
     npcId = npc.getNpcId()
     htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
     st = player.getQuestState(qn)
     if not st : return htmltext
     id = st.getState()
     cond = st.getInt("cond")
     if id == State.COMPLETED :
       htmltext = "<html><body>This quest has already been completed.</body></html>"
     elif id == State.CREATED and npcId == GALLADUCCI :
       if st.getQuestItemsCount(MARK_OF_TRAVELER) > 0 and player.getRace().ordinal() == 5 :
         htmltext = "30097-02.htm"
       else :
         htmltext = "30097-01.htm"
         st.exitQuest(1)       
     elif id == State.STARTED :
       if npcId == GALLADUCCI : 
         if cond == 1 :
           htmltext = "30097-04.htm"
         elif cond == 2 :
           htmltext = "30097-05.htm"
       elif npcId == GENTLER :
         if cond == 1 :
           htmltext = "30094-01.htm"
         elif cond == 2 :
           htmltext = "30094-03.htm"
     return htmltext
 
QUEST     = Quest(173,qn,"To the Isle of Souls") 
 
QUEST.addStartNpc(GALLADUCCI) 

QUEST.addTalkId(GALLADUCCI) 
QUEST.addTalkId(GENTLER) 
 

