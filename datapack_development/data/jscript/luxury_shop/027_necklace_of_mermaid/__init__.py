print "importing shop data: 027_necklace_of_mermaid"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
### vars
ITEM                    = 917
COSTS_CC                = 99
COSTS_DC                = 495
NPC                     = 7098
DC                      = 1458
CC                      = 1459
### count
def getCount_1458(st) :
  return st.getQuestItemsCount(DC)
def getCount_1459(st) :
  return st.getQuestItemsCount(CC)
### main code
class Quest (JQuest):
  def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)
  def onEvent (self,event,st):
    if getCount_1458(st) >= COSTS_DC and getCount_1459(st) >= COSTS_CC :
      st.takeItems(DC,COSTS_DC)
      st.takeItems(CC,COSTS_CC)
      st.giveItems(ITEM,1)
      st.exitQuest(True)
      return "purchased"
    else :
      return "not enough crystals"
### Quest class and state definition
QUEST     = Quest(027,"necklace_of_mermaid", "necklace_of_mermaid")
COMPLETED = State('Completed', QUEST)
### Quest initialization
QUEST.setInitialState(COMPLETED)
### Quest NPC starter initialization
QUEST.addStartNpc(NPC)
### Quest NPC initialization
STARTED.addTalkId(NPC)
