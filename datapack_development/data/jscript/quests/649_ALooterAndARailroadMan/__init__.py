# Made by Kilkenny
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
OBI = 32052

#ITEMS
THIEF_GUILD_MARK = 8099

#REWARDS
ADENA = 57

#Chances
CHANCE = 50

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   count = st.getQuestItemsCount(THIEF_GUILD_MARK)
   if event == "32052-1.htm" :
     st.set("cond","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "32052-3.htm" :
     if count < 200 :
        htmltext = "32052-3a.htm"
     else :
        st.giveItems(ADENA,21698)
        st.takeItems(THIEF_GUILD_MARK,-1)
        st.playSound("ItemSound.quest_finish")
        st.exitQuest(1)
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   npcId = npc.getNpcId()
   id = st.getState()
   cond = st.getInt("cond")
   if cond == 0 :
     if st.getPlayer().getLevel() >= 30 :
       htmltext = "32052-0.htm"
     else:
       htmltext = "32052-0a.htm"
       st.exitQuest(1)
   elif st.getQuestItemsCount(THIEF_GUILD_MARK) == 200 :
     htmltext = "32052-2.htm"
   else :
     htmltext = "32052-2a.htm"
   return htmltext

 def onKill (self,npc,st):
   count = st.getQuestItemsCount(THIEF_GUILD_MARK)
   if st.getInt("cond") == 1 and count < 200 and st.getRandom(100)<CHANCE :
      st.giveItems(THIEF_GUILD_MARK,1)
      if count == 199 :
        st.playSound("ItemSound.quest_middle")
        st.set("cond","2")
      else:
        st.playSound("ItemSound.quest_itemget")	
   return

QUEST       = Quest(649,"649_ALooterAndARailroadMan","A Looter and a Railroad Man")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(OBI)
CREATED.addTalkId(OBI)
STARTED.addTalkId(OBI)

for BANDITS in [22017,22018,22019,22021,22022,22023,22024,22026]:
  STARTED.addKillId(BANDITS)

STARTED.addQuestDrop(OBI,THIEF_GUILD_MARK,1)

print "importing quests: 649: A Looter and a Railroad Man"
