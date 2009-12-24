#made by Kerberos
import sys
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest

qn = "1003_Survivor"

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onAdvEvent (self,event,npc,player):
    st = player.getQuestState(qn)
    if not st: return
    if event:
       if player.getLevel() < 75:
          return "32632-3.htm"
       if st.getQuestItemsCount(57) >= 150000 :
          st.takeItems(57,150000)
          player.teleToLocation(-149406, 255247, -80)
          return
    return event

 def onTalk (self,npc,player):
   st = player.getQuestState(qn)
   if not st :
      return ""
   return "32632-1.htm"

QUEST       = Quest(-1,qn,"Teleports")
QUEST.addStartNpc(32632)
QUEST.addTalkId(32632)