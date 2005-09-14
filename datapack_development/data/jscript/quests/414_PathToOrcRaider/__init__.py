# Maked by Mr. Have fun! Version 0.2
print "importing quests: 414: Path To Orc Raider"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

GREEN_BLOOD_ID = 1578
GOBLIN_DWELLING_MAP_ID = 1579
KURUKA_RATMAN_TOOTH_ID = 1580
BETRAYER_SUE_REPORT_ID = 1581
BETRAYER_WANUK_REPORT_ID = 1582
BETRAYER_CHEWBA_REPORT_ID = 1583
BETRAYER_HEITAFU_REPORT_ID = 1584
BETRAYER_PICUBO_REPORT_ID = 1585
BETRAYER_BUMBUM_REPORT_ID = 1586
BETRAYER_MINSKU_REPORT_ID = 1587
BETRAYER_CHUCHU_REPORT_ID = 1588
BETRAYER_UMBAR_REPORT_ID = 1589
BETRAYER_ZAKAN_REPORT_ID = 1590
HEAD_OF_BETRAYER_ID = 1591
MARK_OF_RAIDER_ID = 1592

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
          st.set("id","0")
          if st.getPlayer().getLevel() >= 19 and st.getPlayer().getClassId().getId() == 0x2c and st.getQuestItemsCount(MARK_OF_RAIDER_ID) == 0 and st.getQuestItemsCount(GOBLIN_DWELLING_MAP_ID) == 0 :
            st.set("cond","1")
            st.setState(STARTED)
            st.playSound("ItemSound.quest_accept")
            st.giveItems(GOBLIN_DWELLING_MAP_ID,1)
            htmltext = "7570-05.htm"
          elif st.getPlayer().getClassId().getId() != 0x2c :
              if st.getPlayer().getClassId().getId() == 0x2d :
                htmltext = "7570-02a.htm"
              else:
                htmltext = "7570-03.htm"
          elif st.getPlayer().getLevel()<19 and st.getPlayer().getClassId().getId() == 0x2c :
              htmltext = "7570-02.htm"
          elif st.getPlayer().getLevel() >= 19 and st.getPlayer().getClassId().getId() == 0x2c and st.getQuestItemsCount(MARK_OF_RAIDER_ID) == 1 :
              htmltext = "7570-04.htm"
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
          if int(st.get("cond"))<15 :
            htmltext = "7570-01.htm"
            return htmltext
          else:
            htmltext = "7570-01.htm"
   elif npcId == 7570 and int(st.get("cond")) and st.getQuestItemsCount(GOBLIN_DWELLING_MAP_ID)==1 and st.getQuestItemsCount(KURUKA_RATMAN_TOOTH_ID)<10 :
          htmltext = "7570-06.htm"
   elif npcId == 7570 and int(st.get("cond")) and st.getQuestItemsCount(GOBLIN_DWELLING_MAP_ID)==1 and st.getQuestItemsCount(KURUKA_RATMAN_TOOTH_ID)>=10 and ((st.getQuestItemsCount(BETRAYER_SUE_REPORT_ID)+st.getQuestItemsCount(BETRAYER_CHEWBA_REPORT_ID)+st.getQuestItemsCount(BETRAYER_WANUK_REPORT_ID)+st.getQuestItemsCount(BETRAYER_HEITAFU_REPORT_ID)+st.getQuestItemsCount(BETRAYER_PICUBO_REPORT_ID)+st.getQuestItemsCount(BETRAYER_BUMBUM_REPORT_ID)+st.getQuestItemsCount(BETRAYER_MINSKU_REPORT_ID)+st.getQuestItemsCount(BETRAYER_CHUCHU_REPORT_ID)+st.getQuestItemsCount(BETRAYER_UMBAR_REPORT_ID)+st.getQuestItemsCount(BETRAYER_ZAKAN_REPORT_ID))==0) :
          htmltext = "7570-07.htm"
          st.takeItems(KURUKA_RATMAN_TOOTH_ID,st.getQuestItemsCount(KURUKA_RATMAN_TOOTH_ID))
          st.takeItems(GOBLIN_DWELLING_MAP_ID,1)
          n1 = st.getRandom(10)
          n2 = st.getRandom(10)
          while n1 == n2 :
            n2 = st.getRandom(10)
          if n1 == 0 :
              st.giveItems(BETRAYER_SUE_REPORT_ID,1)
          elif n1 == 1 :
              st.giveItems(BETRAYER_WANUK_REPORT_ID,1)
          elif n1 == 2 :
              st.giveItems(BETRAYER_CHEWBA_REPORT_ID,1)
          elif n1 == 3 :
              st.giveItems(BETRAYER_HEITAFU_REPORT_ID,1)
          elif n1 == 4 :
              st.giveItems(BETRAYER_PICUBO_REPORT_ID,1)
          elif n1 == 5 :
              st.giveItems(BETRAYER_BUMBUM_REPORT_ID,1)
          elif n1 == 6 :
              st.giveItems(BETRAYER_MINSKU_REPORT_ID,1)
          elif n1 == 7 :
              st.giveItems(BETRAYER_CHUCHU_REPORT_ID,1)
          elif n1 == 8 :
              st.giveItems(BETRAYER_UMBAR_REPORT_ID,1)
          elif n1 == 9 :
              st.giveItems(BETRAYER_ZAKAN_REPORT_ID,1)
          if n2 == 0 :
              st.giveItems(BETRAYER_SUE_REPORT_ID,1)
          elif n2 == 1 :
              st.giveItems(BETRAYER_WANUK_REPORT_ID,1)
          elif n2 == 2 :
              st.giveItems(BETRAYER_CHEWBA_REPORT_ID,1)
          elif n2 == 3 :
              st.giveItems(BETRAYER_HEITAFU_REPORT_ID,1)
          elif n2 == 4 :
              st.giveItems(BETRAYER_PICUBO_REPORT_ID,1)
          elif n2 == 5 :
              st.giveItems(BETRAYER_BUMBUM_REPORT_ID,1)
          elif n2 == 6 :
              st.giveItems(BETRAYER_MINSKU_REPORT_ID,1)
          elif n2 == 7 :
              st.giveItems(BETRAYER_CHUCHU_REPORT_ID,1)
          elif n2 == 8 :
              st.giveItems(BETRAYER_UMBAR_REPORT_ID,1)
          elif n2 == 9 :
              st.giveItems(BETRAYER_ZAKAN_REPORT_ID,1)
          st.set("cond","3")
   elif npcId == 7570 and int(st.get("cond")) and ((st.getQuestItemsCount(BETRAYER_SUE_REPORT_ID)+st.getQuestItemsCount(BETRAYER_CHEWBA_REPORT_ID)+st.getQuestItemsCount(BETRAYER_WANUK_REPORT_ID)+st.getQuestItemsCount(BETRAYER_HEITAFU_REPORT_ID)+st.getQuestItemsCount(BETRAYER_PICUBO_REPORT_ID)+st.getQuestItemsCount(BETRAYER_BUMBUM_REPORT_ID)+st.getQuestItemsCount(BETRAYER_MINSKU_REPORT_ID)+st.getQuestItemsCount(BETRAYER_CHUCHU_REPORT_ID)+st.getQuestItemsCount(BETRAYER_UMBAR_REPORT_ID)+st.getQuestItemsCount(BETRAYER_ZAKAN_REPORT_ID))>0)  and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)>0 :
          htmltext = "7570-08.htm"
   elif npcId == 7501 and int(st.get("cond")) and ((st.getQuestItemsCount(BETRAYER_SUE_REPORT_ID)+st.getQuestItemsCount(BETRAYER_CHEWBA_REPORT_ID)+st.getQuestItemsCount(BETRAYER_WANUK_REPORT_ID)+st.getQuestItemsCount(BETRAYER_HEITAFU_REPORT_ID)+st.getQuestItemsCount(BETRAYER_PICUBO_REPORT_ID)+st.getQuestItemsCount(BETRAYER_BUMBUM_REPORT_ID)+st.getQuestItemsCount(BETRAYER_MINSKU_REPORT_ID)+st.getQuestItemsCount(BETRAYER_CHUCHU_REPORT_ID)+st.getQuestItemsCount(BETRAYER_UMBAR_REPORT_ID)+st.getQuestItemsCount(BETRAYER_ZAKAN_REPORT_ID))==2) and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)==0 :
        htmltext = "7501-01.htm"
   elif npcId == 7501 and int(st.get("cond")) and (st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)>0 and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)<2) :
        htmltext = "7501-02.htm"
   elif npcId == 7501 and int(st.get("cond")) and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)==2 :
        htmltext = "7501-03.htm"
        st.takeItems(HEAD_OF_BETRAYER_ID,st.getQuestItemsCount(HEAD_OF_BETRAYER_ID))
        st.takeItems(BETRAYER_SUE_REPORT_ID,1)
        st.takeItems(BETRAYER_CHEWBA_REPORT_ID,1)
        st.takeItems(BETRAYER_WANUK_REPORT_ID,1)
        st.takeItems(BETRAYER_HEITAFU_REPORT_ID,1)
        st.takeItems(BETRAYER_PICUBO_REPORT_ID,1)
        st.takeItems(BETRAYER_BUMBUM_REPORT_ID,1)
        st.takeItems(BETRAYER_MINSKU_REPORT_ID,1)
        st.takeItems(BETRAYER_CHUCHU_REPORT_ID,1)
        st.takeItems(BETRAYER_UMBAR_REPORT_ID,1)
        st.takeItems(BETRAYER_ZAKAN_REPORT_ID,1)
        st.giveItems(MARK_OF_RAIDER_ID,1)
        st.set("cond","0")
        st.setState(COMPLETED)
        st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 319 :
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
   elif npcId == 320 :
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
   elif npcId == 5046 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(BETRAYER_SUE_REPORT_ID) and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)<2 :
          st.giveItems(HEAD_OF_BETRAYER_ID,1)
          st.takeItems(BETRAYER_SUE_REPORT_ID,1)
          st.playSound("ItemSound.quest_itemget")
          if st.getQuestItemsCount(HEAD_OF_BETRAYER_ID) > 1:
            st.set("cond","4")
   elif npcId == 5047 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(BETRAYER_WANUK_REPORT_ID) and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)<2 :
          st.giveItems(HEAD_OF_BETRAYER_ID,1)
          st.takeItems(BETRAYER_WANUK_REPORT_ID,1)
          st.playSound("ItemSound.quest_itemget")
          if st.getQuestItemsCount(HEAD_OF_BETRAYER_ID) > 1:
            st.set("cond","4")
   elif npcId == 5048 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(BETRAYER_CHEWBA_REPORT_ID) and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)<2 :
          st.giveItems(HEAD_OF_BETRAYER_ID,1)
          st.takeItems(BETRAYER_CHEWBA_REPORT_ID,1)
          st.playSound("ItemSound.quest_itemget")
          if st.getQuestItemsCount(HEAD_OF_BETRAYER_ID) > 1 :
            st.set("cond","4")                             
   elif npcId == 5049 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(BETRAYER_HEITAFU_REPORT_ID) and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)<2 :
          st.giveItems(HEAD_OF_BETRAYER_ID,1)
          st.takeItems(BETRAYER_HEITAFU_REPORT_ID,1)
          st.playSound("ItemSound.quest_itemget")
          if st.getQuestItemsCount(HEAD_OF_BETRAYER_ID) > 1 :
            st.set("cond","4")
   elif npcId == 5050 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(BETRAYER_PICUBO_REPORT_ID) and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)<2 :
          st.giveItems(HEAD_OF_BETRAYER_ID,1)
          st.takeItems(BETRAYER_PICUBO_REPORT_ID,1)
          st.playSound("ItemSound.quest_itemget")
          if st.getQuestItemsCount(HEAD_OF_BETRAYER_ID) > 1 :
            st.set("cond","4")
   elif npcId == 5051 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(BETRAYER_BUMBUM_REPORT_ID) and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)<2 :
          st.giveItems(HEAD_OF_BETRAYER_ID,1)
          st.takeItems(BETRAYER_BUMBUM_REPORT_ID,1)
          st.playSound("ItemSound.quest_itemget")
          if st.getQuestItemsCount(HEAD_OF_BETRAYER_ID) > 1 :
            st.set("cond","4")
   elif npcId == 5052 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(BETRAYER_MINSKU_REPORT_ID) and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)<2 :
          st.giveItems(HEAD_OF_BETRAYER_ID,1)
          st.takeItems(BETRAYER_MINSKU_REPORT_ID,1)
          st.playSound("ItemSound.quest_itemget")
          if st.getQuestItemsCount(HEAD_OF_BETRAYER_ID) > 1 :
            st.set("cond","4")
   elif npcId == 5053 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(BETRAYER_CHUCHU_REPORT_ID) and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)<2 :
          st.giveItems(HEAD_OF_BETRAYER_ID,1)
          st.takeItems(BETRAYER_CHUCHU_REPORT_ID,1)
          st.playSound("ItemSound.quest_itemget")
          if st.getQuestItemsCount(HEAD_OF_BETRAYER_ID) > 1 :
            st.set("cond","4")
   elif npcId == 5054 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(BETRAYER_UMBAR_REPORT_ID) and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)<2 :
          st.giveItems(HEAD_OF_BETRAYER_ID,1)
          st.takeItems(BETRAYER_UMBAR_REPORT_ID,1)
          st.playSound("ItemSound.quest_itemget")
          if st.getQuestItemsCount(HEAD_OF_BETRAYER_ID) > 1 :
            st.set("cond","4")
   elif npcId == 5055 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(BETRAYER_ZAKAN_REPORT_ID) and st.getQuestItemsCount(HEAD_OF_BETRAYER_ID)<2 :
          st.giveItems(HEAD_OF_BETRAYER_ID,1)
          st.takeItems(BETRAYER_ZAKAN_REPORT_ID,1)
          st.playSound("ItemSound.quest_itemget")
          if st.getQuestItemsCount(HEAD_OF_BETRAYER_ID) > 1 :
            st.set("cond","4")
   return

QUEST       = Quest(414,"414_PathToOrcRaider","Path To Orc Raider")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7570)

STARTED.addTalkId(7501)
STARTED.addTalkId(7570)

STARTED.addKillId(319)
STARTED.addKillId(320)
STARTED.addKillId(5045)
STARTED.addKillId(5046)
STARTED.addKillId(5047)
STARTED.addKillId(5048)
STARTED.addKillId(5049)
STARTED.addKillId(5050)
STARTED.addKillId(5051)
STARTED.addKillId(5052)
STARTED.addKillId(5053)
STARTED.addKillId(5054)
STARTED.addKillId(5055)

STARTED.addQuestDrop(5045,KURUKA_RATMAN_TOOTH_ID,1)
STARTED.addQuestDrop(7570,GOBLIN_DWELLING_MAP_ID,1)
STARTED.addQuestDrop(319,GREEN_BLOOD_ID,1)
STARTED.addQuestDrop(320,GREEN_BLOOD_ID,1)
STARTED.addQuestDrop(319,GREEN_BLOOD_ID,1)
STARTED.addQuestDrop(320,GREEN_BLOOD_ID,1)
STARTED.addQuestDrop(319,GREEN_BLOOD_ID,1)
STARTED.addQuestDrop(320,GREEN_BLOOD_ID,1)
STARTED.addQuestDrop(5046,HEAD_OF_BETRAYER_ID,1)
STARTED.addQuestDrop(5047,HEAD_OF_BETRAYER_ID,1)
STARTED.addQuestDrop(5048,HEAD_OF_BETRAYER_ID,1)
STARTED.addQuestDrop(5049,HEAD_OF_BETRAYER_ID,1)
STARTED.addQuestDrop(5050,HEAD_OF_BETRAYER_ID,1)
STARTED.addQuestDrop(5051,HEAD_OF_BETRAYER_ID,1)
STARTED.addQuestDrop(5052,HEAD_OF_BETRAYER_ID,1)
STARTED.addQuestDrop(5053,HEAD_OF_BETRAYER_ID,1)
STARTED.addQuestDrop(5054,HEAD_OF_BETRAYER_ID,1)
STARTED.addQuestDrop(5055,HEAD_OF_BETRAYER_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_SUE_REPORT_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_CHEWBA_REPORT_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_WANUK_REPORT_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_HEITAFU_REPORT_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_PICUBO_REPORT_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_BUMBUM_REPORT_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_MINSKU_REPORT_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_CHUCHU_REPORT_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_UMBAR_REPORT_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_ZAKAN_REPORT_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_SUE_REPORT_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_WANUK_REPORT_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_CHEWBA_REPORT_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_HEITAFU_REPORT_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_PICUBO_REPORT_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_BUMBUM_REPORT_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_MINSKU_REPORT_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_CHUCHU_REPORT_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_UMBAR_REPORT_ID,1)
STARTED.addQuestDrop(7570,BETRAYER_ZAKAN_REPORT_ID,1)
