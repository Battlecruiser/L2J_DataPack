print "importing quests: 399: Yoke of the Past"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

ANCIENT_SCROLL_ID = 986

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      st.set("id","0")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      htmltext = "14.htm"
    elif event == "2" :
          htmltext = "16.htm"
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say to you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if int(st.get("cond")) == 0 :
       htmltext = "10.htm"
       st.set("cond","1")
   elif int(st.get("cond")) == 1 and st.getQuestItemsCount(ANCIENT_SCROLL_ID) == 0 :
       htmltext = "17.htm"
   elif int(st.get("cond")) == 1 and st.getQuestItemsCount(ANCIENT_SCROLL_ID) > 0 :
       if int(st.get("id")) != 2 :
        st.set("id","2")
        htmltext = "16.htm"
        numancientscrolls = st.getQuestItemsCount(ANCIENT_SCROLL_ID)
        st.giveItems(5965,numancientscrolls)
        st.takeItems(ANCIENT_SCROLL_ID,numancientscrolls)
        
   return htmltext

 def onKill (self,npcId,st):
    if npcId == 1208 or npcId == 1210 or npcId == 1212 or npcId == 1213 or npcId == 1214 or npcId == 1214 or npcId == 1215 or npcId == 1217 or npcId == 1219 or npcId == 1220 or npcId == 1221 or npcId == 1222 or npcId == 1223 or npcId == 1232 or npcId == 1234 or npcId == 1236 or npcId == 1237 or npcId == 1238 or npcId == 1239 or npcId == 1241 or npcId == 1243 or npcId == 1244 or npcId == 1245 or npcId == 1246 or npcId == 1247 :
       st.set("id","0")
       if int(st.get("cond")) :
         if st.getRandom(10)>4 :
           st.giveItems(ANCIENT_SCROLL_ID,1)
           st.playSound("ItemSound.quest_itemget")

    return


QUEST       = Quest(399,"399_YokeofthePast","Yoke of the Past")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(8095)
QUEST.addStartNpc(8096)
QUEST.addStartNpc(8097)
QUEST.addStartNpc(8098)
QUEST.addStartNpc(8099)
QUEST.addStartNpc(8101)
QUEST.addStartNpc(8102)
QUEST.addStartNpc(8103)
QUEST.addStartNpc(8104)
QUEST.addStartNpc(8105)
QUEST.addStartNpc(8106)
QUEST.addStartNpc(8107)
QUEST.addStartNpc(8108)
QUEST.addStartNpc(8109)
QUEST.addStartNpc(8110)
QUEST.addStartNpc(8114)
QUEST.addStartNpc(8115)
QUEST.addStartNpc(8116)
QUEST.addStartNpc(8117)
QUEST.addStartNpc(8118)
QUEST.addStartNpc(8119)
QUEST.addStartNpc(8120)
QUEST.addStartNpc(8121)
QUEST.addStartNpc(8122)
QUEST.addStartNpc(8123)
QUEST.addStartNpc(8124)
QUEST.addStartNpc(8125)

STARTED.addTalkId(8095)
STARTED.addTalkId(8096)
STARTED.addTalkId(8097)
STARTED.addTalkId(8098)
STARTED.addTalkId(8099)
STARTED.addTalkId(8101)
STARTED.addTalkId(8102)
STARTED.addTalkId(8103)
STARTED.addTalkId(8104)
STARTED.addTalkId(8105)
STARTED.addTalkId(8106)
STARTED.addTalkId(8107)
STARTED.addTalkId(8108)
STARTED.addTalkId(8109)
STARTED.addTalkId(8110)
STARTED.addTalkId(8114)
STARTED.addTalkId(8115)
STARTED.addTalkId(8116)
STARTED.addTalkId(8117)
STARTED.addTalkId(8118)
STARTED.addTalkId(8119)
STARTED.addTalkId(8120)
STARTED.addTalkId(8121)
STARTED.addTalkId(8122)
STARTED.addTalkId(8123)
STARTED.addTalkId(8124)
STARTED.addTalkId(8125)

STARTED.addKillId(1208)
STARTED.addKillId(1210)
STARTED.addKillId(1212)
STARTED.addKillId(1213)
STARTED.addKillId(1214)
STARTED.addKillId(1215)
STARTED.addKillId(1217)
STARTED.addKillId(1219)
STARTED.addKillId(1220)
STARTED.addKillId(1221)
STARTED.addKillId(1222)
STARTED.addKillId(1223)
STARTED.addKillId(1232)
STARTED.addKillId(1234)
STARTED.addKillId(1236)
STARTED.addKillId(1237)
STARTED.addKillId(1238)
STARTED.addKillId(1239)
STARTED.addKillId(1241)
STARTED.addKillId(1243)
STARTED.addKillId(1244)
STARTED.addKillId(1245)
STARTED.addKillId(1246)
STARTED.addKillId(1247)

STARTED.addQuestDrop(986,ANCIENT_SCROLL_ID,1)
