# Maked by Mr. Have fun! Version 0.2
print "importing quests: 226: Test Of Healer"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

REPORT_OF_PERRIN_ID = 2810
CRISTINAS_LETTER_ID = 2811
PICTURE_OF_WINDY_ID = 2812
GOLDEN_STATUE_ID = 2813
WINDYS_PEBBLES_ID = 2814
ORDER_OF_SORIUS_ID = 2815
SECRET_LETTER1_ID = 2816
SECRET_LETTER2_ID = 2817
SECRET_LETTER3_ID = 2818
SECRET_LETTER4_ID = 2819
MARK_OF_HEALER_ID = 2820
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      htmltext = "7473-04.htm"
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      st.giveItems(REPORT_OF_PERRIN_ID,1)
    elif event == "7473_1" :
          htmltext = "7473-08.htm"
    elif event == "7473_2" :
          htmltext = "7473-09.htm"
          if st.getGameTicks() != int(st.get("id")) :
            st.set("id",str(st.getGameTicks()))
            st.addExpAndSp(37000,4500)
          st.giveItems(MARK_OF_HEALER_ID,1)
          st.takeItems(GOLDEN_STATUE_ID,1)
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.set("onlyone","1")
    elif event == "7428_1" :
          htmltext = "7428-02.htm"
#          if Maker_GetNpcCount() == 1 :
          st.spawnNpc(5134)
    elif event == "7658_1" :
          if st.getQuestItemsCount(ADENA_ID) >= 100000 :
            htmltext = "7658-02.htm"
            st.takeItems(ADENA_ID,100000)
            st.giveItems(PICTURE_OF_WINDY_ID,1)
          else:
            htmltext = "7658-05.htm"
    elif event == "7658_2" :
          htmltext = "7658-03.htm"
          st.set("cond","5")
    elif event == "7660_1" :
          htmltext = "7660-02.htm"
    elif event == "7660_2" :
          htmltext = "7660-03.htm"
          st.takeItems(PICTURE_OF_WINDY_ID,1)
          st.giveItems(WINDYS_PEBBLES_ID,1)
    elif event == "7674_1" :
          htmltext = "7674-02.htm"
          st.takeItems(ORDER_OF_SORIUS_ID,1)
          st.spawnNpc(5122)
          st.spawnNpc(5122)
          st.spawnNpc(5123)
          st.playSound("Itemsound.quest_before_battle")
    elif event == "7665_1" :
          htmltext = "7665-02.htm"
          st.takeItems(SECRET_LETTER1_ID,1)
          st.takeItems(SECRET_LETTER2_ID,1)
          st.takeItems(SECRET_LETTER3_ID,1)
          st.takeItems(SECRET_LETTER4_ID,1)
          st.giveItems(CRISTINAS_LETTER_ID,1)
          st.set("cond","9")
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7473 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if int(st.get("cond")) < 15 :
        if (st.getPlayer().getClassId()==0x0f or st.getPlayer().getClassId()==0x1d or st.getPlayer().getClassId()==0x13) and st.getPlayer().getLevel() >= 39 :
          htmltext = "7473-03.htm"
        elif st.getPlayer().getClassId()==0x0f or st.getPlayer().getClassId()==0x1d or st.getPlayer().getClassId()==0x13 :
          htmltext = "7473-01.htm"
        else:
          htmltext = "7473-02.htm"
      else:
        htmltext = "7473-02.htm"
   elif npcId == 7473 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7473 and int(st.get("cond"))<10 and int(st.get("cond"))>=1 :
      htmltext = "7473-05.htm"
   elif npcId == 7473 and int(st.get("cond"))==10 and st.getQuestItemsCount(GOLDEN_STATUE_ID)==0 :
      htmltext = "7473-06.htm"
      if st.getGameTicks() != int(st.get("id")) :
        st.set("id",str(st.getGameTicks()))
        st.addExpAndSp(32000,4100)
      st.giveItems(MARK_OF_HEALER_ID,1)
      st.set("cond","0")
      st.setState(COMPLETED)
      st.playSound("ItemSound.quest_finish")
      st.set("onlyone","1")
   elif npcId == 7473 and int(st.get("cond"))==10 and st.getQuestItemsCount(GOLDEN_STATUE_ID) :
      htmltext = "7473-07.htm"
   elif npcId == 7428 and int(st.get("cond"))==1 and st.getQuestItemsCount(REPORT_OF_PERRIN_ID) :
      htmltext = "7428-01.htm"
   elif npcId == 7428 and int(st.get("cond"))==2 :
      htmltext = "7428-03.htm"
      st.set("cond","3")
      st.takeItems(REPORT_OF_PERRIN_ID,1)
   elif npcId == 7428 and int(st.get("cond"))==3 :
      htmltext = "7428-04.htm"
   elif npcId == 7659 and int(st.get("cond"))==1 :
        n = st.getRandom(5)
        if n == 0:
          htmltext = "7659-01.htm"
        if n == 1:
          htmltext = "7659-02.htm"
        if n == 2:
          htmltext = "7659-03.htm"
        if n == 3:
          htmltext = "7659-04.htm"
        if n == 4:
          htmltext = "7659-05.htm"
   elif npcId == 7424 and int(st.get("cond"))==3 :
      htmltext = "7424-01.htm"
      st.set("cond","4")
   elif npcId == 7424 and int(st.get("cond"))==4 :
      htmltext = "7424-02.htm"
      st.set("cond","4")
   elif npcId == 7658 and int(st.get("cond"))==4 and st.getQuestItemsCount(PICTURE_OF_WINDY_ID)==0 and st.getQuestItemsCount(WINDYS_PEBBLES_ID)==0 and st.getQuestItemsCount(GOLDEN_STATUE_ID)==0 :
      htmltext = "7658-01.htm"
   elif npcId == 7658 and int(st.get("cond"))==1 and st.getQuestItemsCount(PICTURE_OF_WINDY_ID) :
      htmltext = "7658-04.htm"
   elif npcId == 7658 and int(st.get("cond"))==1 and st.getQuestItemsCount(WINDYS_PEBBLES_ID) :
      htmltext = "7658-06.htm"
      st.giveItems(GOLDEN_STATUE_ID,1)
      st.takeItems(WINDYS_PEBBLES_ID,1)
      st.set("cond","5")
   elif npcId == 7658 and int(st.get("cond"))==5 :
      htmltext = "7658-07.htm"
   elif npcId == 7660 and int(st.get("cond"))==1 and st.getQuestItemsCount(PICTURE_OF_WINDY_ID) :
      htmltext = "7660-01.htm"
   elif npcId == 7660 and int(st.get("cond"))==1 and st.getQuestItemsCount(WINDYS_PEBBLES_ID) :
      htmltext = "7660-04.htm"
   elif npcId == 7327 and int(st.get("cond"))==5 :
      htmltext = "7327-01.htm"
      st.giveItems(ORDER_OF_SORIUS_ID,1)
      st.set("cond","6")
   elif npcId == 7327 and int(st.get("cond"))>=6 and int(st.get("cond"))<9 :
      htmltext = "7327-02.htm"
   elif npcId == 7327 and int(st.get("cond"))==9 and st.getQuestItemsCount(CRISTINAS_LETTER_ID) :
      htmltext = "7327-03.htm"
      st.takeItems(CRISTINAS_LETTER_ID,1)
      st.set("cond","10")
   elif npcId == 7674 and int(st.get("cond"))==6 and st.getQuestItemsCount(ORDER_OF_SORIUS_ID) :
      htmltext = "7674-01.htm"
   elif npcId == 7674 and int(st.get("cond"))==6 and st.getQuestItemsCount(SECRET_LETTER1_ID) :
      htmltext = "7674-03.htm"
      st.set("cond","8")
   elif npcId == 7662 and int(st.get("cond"))==8 and st.getQuestItemsCount(SECRET_LETTER1_ID) and st.getQuestItemsCount(SECRET_LETTER2_ID)==0 :
      htmltext = "7662-01.htm"
   elif npcId == 7662 and int(st.get("cond"))==1 and st.getQuestItemsCount(SECRET_LETTER2_ID) :
      htmltext = "7662-02.htm"
   elif npcId == 7662 and int(st.get("cond"))==1 and st.getQuestItemsCount(SECRET_LETTER2_ID) and st.getQuestItemsCount(SECRET_LETTER3_ID) and st.getQuestItemsCount(SECRET_LETTER4_ID) :
      htmltext = "7662-03.htm"
   elif npcId == 7663 and int(st.get("cond"))==8 and st.getQuestItemsCount(SECRET_LETTER1_ID) and st.getQuestItemsCount(SECRET_LETTER3_ID)==0 :
      htmltext = "7663-01.htm"
   elif npcId == 7663 and int(st.get("cond"))==1 and st.getQuestItemsCount(SECRET_LETTER3_ID) :
      htmltext = "7663-02.htm"
   elif npcId == 7663 and int(st.get("cond"))==1 and st.getQuestItemsCount(SECRET_LETTER2_ID) and st.getQuestItemsCount(SECRET_LETTER3_ID) and st.getQuestItemsCount(SECRET_LETTER4_ID) :
      htmltext = "7663-03.htm"
   elif npcId == 7664 and int(st.get("cond"))==8 and st.getQuestItemsCount(SECRET_LETTER1_ID) and st.getQuestItemsCount(SECRET_LETTER4_ID)==0 :
      htmltext = "7664-01.htm"
   elif npcId == 7664 and int(st.get("cond"))==1 and st.getQuestItemsCount(SECRET_LETTER4_ID) :
      htmltext = "7664-02.htm"
   elif npcId == 7664 and int(st.get("cond"))==1 and st.getQuestItemsCount(SECRET_LETTER2_ID) and st.getQuestItemsCount(SECRET_LETTER3_ID) and st.getQuestItemsCount(SECRET_LETTER4_ID) :
      htmltext = "7664-03.htm"
   elif npcId == 7661 and int(st.get("cond"))==8 and ((st.getQuestItemsCount(SECRET_LETTER2_ID)+st.getQuestItemsCount(SECRET_LETTER3_ID)+st.getQuestItemsCount(SECRET_LETTER4_ID)+st.getQuestItemsCount(SECRET_LETTER1_ID))==1) :
      htmltext = "7661-01.htm"
      st.spawnNpc(5124)
      st.spawnNpc(5124)
      st.spawnNpc(5124)
      st.playSound("Itemsound.quest_before_battle")
      st.despawnNpc(7661)
   elif npcId == 7661 and int(st.get("cond"))==8 and ((st.getQuestItemsCount(SECRET_LETTER2_ID)+st.getQuestItemsCount(SECRET_LETTER3_ID)+st.getQuestItemsCount(SECRET_LETTER4_ID)+st.getQuestItemsCount(SECRET_LETTER1_ID))==2) :
      htmltext = "7661-02.htm"
      st.spawnNpc(5125)
      st.spawnNpc(5125)
      st.spawnNpc(5125)
      st.playSound("Itemsound.quest_before_battle")
      st.despawnNpc(7661)
   elif npcId == 7661 and int(st.get("cond"))==8 and ((st.getQuestItemsCount(SECRET_LETTER2_ID)+st.getQuestItemsCount(SECRET_LETTER3_ID)+st.getQuestItemsCount(SECRET_LETTER4_ID)+st.getQuestItemsCount(SECRET_LETTER1_ID))==3) :
      htmltext = "7661-03.htm"
      st.spawnNpc(5126)
      st.spawnNpc(5126)
      st.spawnNpc(5127)
      st.playSound("Itemsound.quest_before_battle")
      st.despawnNpc(7661)
   elif npcId == 7661 and int(st.get("cond"))==8 and ((st.getQuestItemsCount(SECRET_LETTER2_ID)+st.getQuestItemsCount(SECRET_LETTER3_ID)+st.getQuestItemsCount(SECRET_LETTER4_ID)+st.getQuestItemsCount(SECRET_LETTER1_ID))==4) :
      htmltext = "7661-04.htm"
   elif npcId == 7665 and int(st.get("cond"))==1 and ((st.getQuestItemsCount(SECRET_LETTER2_ID)+st.getQuestItemsCount(SECRET_LETTER3_ID)+st.getQuestItemsCount(SECRET_LETTER4_ID)+st.getQuestItemsCount(SECRET_LETTER1_ID))==4) :
      htmltext = "7665-01.htm"
   elif npcId == 7665 and int(st.get("cond"))==1 and ((st.getQuestItemsCount(SECRET_LETTER2_ID)+st.getQuestItemsCount(SECRET_LETTER3_ID)+st.getQuestItemsCount(SECRET_LETTER4_ID)+st.getQuestItemsCount(SECRET_LETTER1_ID))<4) :
      htmltext = "7665-03.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 5134 :
      if int(st.get("cond")) and int(st.get("cond")) == 1 :
        st.set("cond","2")
   elif npcId == 5123 :
      if int(st.get("cond")) and int(st.get("cond")) == 6 :
        st.giveItems(SECRET_LETTER1_ID,1)
        st.playSound("Itemsound.quest_middle")
   elif npcId == 5124 :
      if int(st.get("cond")) and int(st.get("cond")) == 8 and st.getQuestItemsCount(SECRET_LETTER1_ID) and st.getQuestItemsCount(SECRET_LETTER2_ID) == 0 :
        st.giveItems(SECRET_LETTER2_ID,1)
        st.playSound("Itemsound.quest_middle")
   elif npcId == 5125 :
      if int(st.get("cond")) and int(st.get("cond")) == 8 and st.getQuestItemsCount(SECRET_LETTER1_ID) and st.getQuestItemsCount(SECRET_LETTER3_ID) == 0 :
        st.giveItems(SECRET_LETTER3_ID,1)
        st.playSound("Itemsound.quest_middle")
   elif npcId == 5127 :
      if int(st.get("cond")) and int(st.get("cond")) == 8 and st.getQuestItemsCount(SECRET_LETTER1_ID) and st.getQuestItemsCount(SECRET_LETTER4_ID) == 0 :
        st.giveItems(SECRET_LETTER4_ID,1)
        st.playSound("Itemsound.quest_middle")
   return

QUEST       = Quest(226,"226_TestOfHealer","Test Of Healer")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7473)

STARTING.addTalkId(7473)

STARTED.addTalkId(7327)
STARTED.addTalkId(7424)
STARTED.addTalkId(7428)
STARTED.addTalkId(7473)
STARTED.addTalkId(7658)
STARTED.addTalkId(7659)
STARTED.addTalkId(7660)
STARTED.addTalkId(7661)
STARTED.addTalkId(7662)
STARTED.addTalkId(7663)
STARTED.addTalkId(7664)
STARTED.addTalkId(7665)
STARTED.addTalkId(7674)

STARTED.addKillId(150)
STARTED.addKillId(5123)
STARTED.addKillId(5124)
STARTED.addKillId(5125)
STARTED.addKillId(5127)
STARTED.addKillId(5134)

STARTED.addQuestDrop(7658,GOLDEN_STATUE_ID,1)
STARTED.addQuestDrop(7473,REPORT_OF_PERRIN_ID,1)
STARTED.addQuestDrop(7660,WINDYS_PEBBLES_ID,1)
STARTED.addQuestDrop(7658,PICTURE_OF_WINDY_ID,1)
STARTED.addQuestDrop(7665,CRISTINAS_LETTER_ID,1)
STARTED.addQuestDrop(7327,ORDER_OF_SORIUS_ID,1)
STARTED.addQuestDrop(5123,SECRET_LETTER1_ID,1)
STARTED.addQuestDrop(5124,SECRET_LETTER2_ID,1)
STARTED.addQuestDrop(5125,SECRET_LETTER3_ID,1)
STARTED.addQuestDrop(5127,SECRET_LETTER4_ID,1)
