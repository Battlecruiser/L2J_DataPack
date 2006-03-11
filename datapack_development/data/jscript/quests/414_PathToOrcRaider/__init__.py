# Maked by Mr. Have fun! Version 0.2
print "importing quests: 414: Path To Orc Raider"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GREEN_BLOOD_ID = 1578
GOBLIN_DWELLING_MAP_ID = 1579
KURUKA_RATMAN_TOOTH_ID = 1580
BETRAYER_UMBAR_REPORT_ID = 1589
HEAD_OF_BETRAYER_ID = 1591
MARK_OF_RAIDER_ID = 1592

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
          st.set("id","0")
          st.set("cond","1")
          st.setState(STARTED)
          st.playSound("ItemSound.quest_accept")
          st.giveItems(GOBLIN_DWELLING_MAP_ID,1)
          htmltext = "7570-05.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7570 and int(st.get("cond"))==0 :
          if st.getPlayer().getLevel() >= 19 and st.getPlayer().getClassId().getId() == 0x2c and st.getQuestItemsCount(MARK_OF_RAIDER_ID) == 0 and st.getQuestItemsCount(GOBLIN_DWELLING_MAP_ID) == 0 :
              htmltext = "7570-01.htm"
          elif st.getPlayer().getClassId().getId() != 0x2c :
              if st.getPlayer().getClassId().getId() == 0x2d :
                htmltext = "7570-02a.htm"
              else:
                htmltext = "7570-03.htm"
          elif st.getPlayer().getLevel()<19 and st.getPlayer().getClassId().getId() == 0x2c :
              htmltext = "7570-02.htm"
          elif st.getPlayer().getLevel() >= 19 and st.getPlayer().getClassId().getId() == 0x2c and st.getQuestItemsCount(MARK_OF_RAIDER_ID) == 1 :
              htmltext = "7570-04.htm"
          else:
            htmltext = "7570-02.htm"
   elif npcId == 7570 and int(st.get("cond")) and st.getQuestItemsCount(GOBLIN_DWELLING_MAP_ID)==1 and st.getQuestItemsCount(KURUKA_RATMAN_TOOTH_ID)<10 :
          htmltext = "7570-06.htm"
   elif npcId == 7570 and int(st.get("cond")) and st.getQuestItemsCount(GOBLIN_DWELLING_MAP_ID)==1 and st.getQuestItemsCount(KURUKA_RATMAN_TOOTH_ID)>=10 and st.getQuestItemsCount(BETRAYER_UMBAR_REPORT_ID)==0 :
          htmltext = "7570-07.htm"
          st.takeItems(KURUKA_RATMAN_TOOTH_ID,st.getQuestItemsCount(KURUKA_RATMAN_TOOTH_ID))
          st.takeItems(GOBLIN_DWELLING_MAP_ID,1)
          st.giveItems(BETRAYER_UMBAR_REPORT_ID,1)
          st.addRadar(-16760, 78268, -3480)
          st.set("cond","3")
   elif npcId == 7570 and int(st.get("cond")) and st.getQuestItemsCount(BETRAYER_UMBAR_REPORT_ID) and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)<2 :
          htmltext = "7570-08.htm"
   elif npcId == 7570 and int(st.get("cond")) and st.getQuestItemsCount(BETRAYER_UMBAR_REPORT_ID) and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)==2 :
          htmltext = "7570-09.htm"
   elif npcId == 7501 and int(st.get("cond")) and st.getQuestItemsCount(BETRAYER_UMBAR_REPORT_ID) and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)==0 :
        htmltext = "7501-01.htm"
   elif npcId == 7501 and int(st.get("cond")) and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)>0 and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)<2 :
        htmltext = "7501-02.htm"
   elif npcId == 7501 and int(st.get("cond")) and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)==2 :
        htmltext = "7501-03.htm"
        st.takeItems(HEAD_OF_BETRAYER_ID,st.getQuestItemsCount(HEAD_OF_BETRAYER_ID))
        st.takeItems(BETRAYER_UMBAR_REPORT_ID,1)
        st.giveItems(MARK_OF_RAIDER_ID,1)
        st.set("cond","0")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 320 :
          st.set("id","0")
          if int(st.get("cond")) and st.getQuestItemsCount(GOBLIN_DWELLING_MAP_ID) == 1 and st.getQuestItemsCount(KURUKA_RATMAN_TOOTH_ID)<10 and st.getQuestItemsCount(GREEN_BLOOD_ID)<40 :
            if st.getQuestItemsCount(GREEN_BLOOD_ID)>20 :
              if st.getRandom(100)<((st.getQuestItemsCount(GREEN_BLOOD_ID)-20)*5) :
                st.takeItems(GREEN_BLOOD_ID,st.getQuestItemsCount(GREEN_BLOOD_ID))
                st.spawnNpc(5045)
              else:
                st.giveItems(GREEN_BLOOD_ID,1)
                st.playSound("ItemSound.quest_itemget")
            else:
              st.giveItems(GREEN_BLOOD_ID,1)
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 5045 :
          st.set("id","0")
          if int(st.get("cond")) and st.getQuestItemsCount(GOBLIN_DWELLING_MAP_ID) == 1 and st.getQuestItemsCount(KURUKA_RATMAN_TOOTH_ID)<10 :
            st.takeItems(GREEN_BLOOD_ID,st.getQuestItemsCount(GREEN_BLOOD_ID))
            if st.getQuestItemsCount(KURUKA_RATMAN_TOOTH_ID) == 9 :
              st.giveItems(KURUKA_RATMAN_TOOTH_ID,1)
              st.playSound("ItemSound.quest_middle")
              st.set("cond","2")
            else:
              st.giveItems(KURUKA_RATMAN_TOOTH_ID,1)
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 5054 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(BETRAYER_UMBAR_REPORT_ID)>0 and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)<2 :
          st.giveItems(HEAD_OF_BETRAYER_ID,1)
          st.playSound("ItemSound.quest_itemget")
          if st.getQuestItemsCount(HEAD_OF_BETRAYER_ID) > 1 :
            st.playSound("ItemSound.quest_middle")
            st.set("cond","4")
   return

QUEST       = Quest(414,"414_PathToOrcRaider","Path To Orc Raider")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7570)

STARTING.addTalkId(7570)

STARTED.addTalkId(7501)
STARTED.addTalkId(7570)

STARTED.addKillId(320)
STARTED.addKillId(5045)
STARTED.addKillId(5054)

STARTED.addQuestDrop(5045,KURUKA_RATMAN_TOOTH_ID,1)
STARTED.addQuestDrop(7570,GOBLIN_DWELLING_MAP_ID,1)
STARTED.addQuestDrop(320,GREEN_BLOOD_ID,1)
STARTED.addQuestDrop(5054,HEAD_OF_BETRAYER_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_UMBAR_REPORT_ID,1)
