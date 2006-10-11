# Made by Mr. Have fun! Version 0.2
# Updated by ElgarL
# Improved a lil' bit by DrLecter
import sys
from net.sf.l2j import Config
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_PILGRIM_ID = 2721
BOOK_OF_SAGE_ID = 2722
VOUCHER_OF_TRIAL_ID = 2723
SPIRIT_OF_FLAME_ID = 2724
ESSENSE_OF_FLAME_ID = 2725
BOOK_OF_GERALD_ID = 2726
GREY_BADGE_ID = 2727
PICTURE_OF_NAHIR_ID = 2728
HAIR_OF_NAHIR_ID = 2729
STATUE_OF_EINHASAD_ID = 2730
BOOK_OF_DARKNESS_ID = 2731
DEBRIS_OF_WILLOW_ID = 2732
TAG_OF_RUMOR_ID = 2733
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      htmltext = "30648-04.htm"
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      st.giveItems(VOUCHER_OF_TRIAL_ID,1)
    elif event == "30648_1" :
          htmltext = "30648-05.htm"
    elif event == "30648_2" :
          htmltext = "30648-06.htm"
    elif event == "30648_3" :
          htmltext = "30648-07.htm"
    elif event == "30648_4" :
          htmltext = "30648-08.htm"
    elif event == "30648_5" :
          htmltext = "30648-05.htm"
    elif event == "30649_1" :
          htmltext = "30649-04.htm"
          st.giveItems(SPIRIT_OF_FLAME_ID,1)
          st.takeItems(ESSENSE_OF_FLAME_ID,1)
          st.set("cond","5")
    elif event == "30650_1" :
          if st.getQuestItemsCount(ADENA_ID) >= 100000*int(Config.RATE_DROP_ADENA) :
            htmltext = "30650-02.htm"
            st.giveItems(BOOK_OF_GERALD_ID,1)
            st.takeItems(ADENA_ID,100000*int(Config.RATE_DROP_ADENA))
            st.set("cond","7")
          else:
            htmltext = "30650-03.htm"
    elif event == "30650_2" :
          htmltext = "30650-03.htm"
    elif event == "30362_1" :
          htmltext = "30362-05.htm"
          st.takeItems(BOOK_OF_DARKNESS_ID,1)
          st.set("cond","16")
    elif event == "30362_2" :
          htmltext = "30362-04.htm"
          st.set("cond","16")
    elif event == "30652_1" :
          htmltext = "30652-02.htm"
          st.giveItems(BOOK_OF_DARKNESS_ID,1)
          st.takeItems(DEBRIS_OF_WILLOW_ID,1)
          st.set("cond","15")
    return htmltext


 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 30648 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if (st.getPlayer().getClassId().getId() in [0x0f,0x1d,0x2a,0x32]) :
           if st.getPlayer().getLevel() >= 35 :
              htmltext = "30648-03.htm"
           else :
              htmltext = "30648-01.htm"
              st.exitQuest(1)
        else:
          htmltext = "30648-02.htm"
          st.exitQuest(1)
   elif npcId == 30648 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 30648 and int(st.get("cond"))==1 and st.getQuestItemsCount(VOUCHER_OF_TRIAL_ID) :
      htmltext = "30648-09.htm"
   elif npcId == 30648 and int(st.get("cond"))==17 and st.getQuestItemsCount(BOOK_OF_SAGE_ID) :
      st.addExpAndSp(77832,16000)
      st.giveItems(7562,8)
      htmltext = "30648-10.htm"
      st.giveItems(MARK_OF_PILGRIM_ID,1)
      st.takeItems(BOOK_OF_SAGE_ID,1)
      st.setState(COMPLETED)
      st.playSound("ItemSound.quest_finish")
      st.set("onlyone","1")
      st.set("cond","0")
   elif npcId == 30571 and int(st.get("cond"))==1 and st.getQuestItemsCount(VOUCHER_OF_TRIAL_ID) :
      htmltext = "30571-01.htm"
      st.takeItems(VOUCHER_OF_TRIAL_ID,1)
      st.set("cond","2")
   elif npcId == 30571 and int(st.get("cond"))==2 :
      htmltext = "30571-02.htm"
   elif npcId == 30571 and int(st.get("cond"))==5 and st.getQuestItemsCount(SPIRIT_OF_FLAME_ID) :
      htmltext = "30571-03.htm"
   elif npcId == 30649 and int(st.get("cond"))==2 :
      htmltext = "30649-01.htm"
      st.set("cond","3")
   elif npcId == 30649 and int(st.get("cond"))==3 :
      htmltext = "30649-02.htm"
   elif npcId == 30649 and int(st.get("cond"))==4 and st.getQuestItemsCount(ESSENSE_OF_FLAME_ID) :
      htmltext = "30649-03.htm"
   elif npcId == 30550 and int(st.get("cond"))==5 and st.getQuestItemsCount(SPIRIT_OF_FLAME_ID) :
      htmltext = "30550-01.htm"
      st.giveItems(TAG_OF_RUMOR_ID,1)
      st.set("cond","6")
   elif npcId == 30550 and int(st.get("cond"))==6 :
      htmltext = "30550-02.htm"
   elif npcId == 30650 and int(st.get("cond"))==6 and st.getQuestItemsCount(TAG_OF_RUMOR_ID) :
      htmltext = st.showHtmlFile("30650-01.htm").replace("RequiredAdena", str(100000*int(Config.RATE_DROP_ADENA)))
   elif npcId == 30650 and int(st.get("cond"))>=8 and st.getQuestItemsCount(GREY_BADGE_ID) and st.getQuestItemsCount(BOOK_OF_GERALD_ID) :
      htmltext = "30650-04.htm"
      rate=int(Config.RATE_QUESTS_REWARD)
      if rate == 0 : rate = 1
      st.giveItems(ADENA_ID,int(100000*Config.RATE_DROP_ADENA/rate))
      st.takeItems(BOOK_OF_GERALD_ID,1)
   elif npcId == 30651 and int(st.get("cond"))==6 and st.getQuestItemsCount(TAG_OF_RUMOR_ID) :
      htmltext = "30651-01.htm"
      st.giveItems(GREY_BADGE_ID,1)
      st.takeItems(TAG_OF_RUMOR_ID,1)
      st.set("cond","8")
   elif npcId == 30651 and int(st.get("cond"))==7 and st.getQuestItemsCount(TAG_OF_RUMOR_ID) :
      htmltext = "30651-02.htm"
      st.giveItems(GREY_BADGE_ID,1)
      st.takeItems(TAG_OF_RUMOR_ID,1)
      st.set("cond","8")
   elif npcId == 30651 and int(st.get("cond"))==8 :
      htmltext = "30651-03.htm"
   elif npcId == 30117 and int(st.get("cond"))==8 :
      htmltext = "30117-01.htm"
      st.set("cond","9")
   elif npcId == 30117 and int(st.get("cond"))==9 :
      htmltext = "30117-02.htm"
   elif npcId == 30036 and int(st.get("cond"))==9 :
      htmltext = "30036-01.htm"
      st.giveItems(PICTURE_OF_NAHIR_ID,1)
      st.set("cond","10")
   elif npcId == 30036 and int(st.get("cond"))==10 :
      htmltext = "30036-02.htm"
   elif npcId == 30036 and int(st.get("cond"))==11 :
      htmltext = "30036-03.htm"
      st.giveItems(STATUE_OF_EINHASAD_ID,1)
      st.takeItems(PICTURE_OF_NAHIR_ID,1)
      st.takeItems(HAIR_OF_NAHIR_ID,1)
      st.set("cond","12")
   elif npcId == 30036 and int(st.get("cond"))==12 and st.getQuestItemsCount(STATUE_OF_EINHASAD_ID) :
      htmltext = "30036-04.htm"
   elif npcId == 30362 and int(st.get("cond"))==12 :
      htmltext = "30362-01.htm"
      st.set("cond","13")
   elif npcId == 30362 and int(st.get("cond"))==13 :
      htmltext = "30362-02.htm"
   elif npcId == 30362 and int(st.get("cond"))==15 and st.getQuestItemsCount(BOOK_OF_DARKNESS_ID) :
      htmltext = "30362-03.htm"
   elif npcId == 30362 and int(st.get("cond"))==16 :
      htmltext = "30362-06.htm"
   elif npcId == 30362 and int(st.get("cond"))==15 and st.getQuestItemsCount(BOOK_OF_DARKNESS_ID)==0 :
      htmltext = "30362-07.htm"
   elif npcId == 30652 and int(st.get("cond"))==14 and st.getQuestItemsCount(DEBRIS_OF_WILLOW_ID) :
      htmltext = "30652-01.htm"
   elif npcId == 30652 and int(st.get("cond"))==15 and st.getQuestItemsCount(BOOK_OF_DARKNESS_ID) :
      htmltext = "30652-03.htm"
   elif npcId == 30612 and int(st.get("cond"))==16 :
      htmltext = "30612-01.htm"
      st.giveItems(BOOK_OF_SAGE_ID,1)
      if st.getQuestItemsCount(BOOK_OF_DARKNESS_ID) :
        st.takeItems(BOOK_OF_DARKNESS_ID,1)
      st.set("cond","17")
      st.takeItems(GREY_BADGE_ID,1)
      st.takeItems(SPIRIT_OF_FLAME_ID,1)
      st.takeItems(STATUE_OF_EINHASAD_ID,1)
   elif npcId == 30612 and int(st.get("cond"))==17 :
      htmltext = "30612-02.htm"
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   if npcId == 27116 :
      if int(st.get("cond")) and int(st.get("cond")) == 3 and st.getQuestItemsCount(ESSENSE_OF_FLAME_ID) == 0 :
        if st.getRandom(5) == 1 :
          st.giveItems(ESSENSE_OF_FLAME_ID,1)
          st.set("cond","4")
          st.playSound("ItemSound.quest_middle")
   elif npcId == 27117 :
      if int(st.get("cond")) and int(st.get("cond")) == 10 and st.getQuestItemsCount(HAIR_OF_NAHIR_ID) == 0 :
        st.giveItems(HAIR_OF_NAHIR_ID,1)
        st.set("cond","11")
        st.playSound("ItemSound.quest_middle")
   elif npcId == 27118 :
      if int(st.get("cond")) and int(st.get("cond")) == 13 and st.getQuestItemsCount(DEBRIS_OF_WILLOW_ID) == 0 :
        if st.getRandom(5) == 1 :
          st.giveItems(DEBRIS_OF_WILLOW_ID,1)
          st.set("cond","14")
          st.playSound("ItemSound.quest_middle")
   return

QUEST       = Quest(215,"215_TrialOfPilgrim","Trial Of Pilgrim")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30648)

STARTING.addTalkId(30648)

STARTED.addTalkId(30036)
STARTED.addTalkId(30117)
STARTED.addTalkId(30362)
STARTED.addTalkId(30550)
STARTED.addTalkId(30571)
STARTED.addTalkId(30612)
STARTED.addTalkId(30648)
STARTED.addTalkId(30649)
STARTED.addTalkId(30650)
STARTED.addTalkId(30651)
STARTED.addTalkId(30652)

STARTED.addKillId(27116)
STARTED.addKillId(27117)
STARTED.addKillId(27118)

STARTED.addQuestDrop(30612,BOOK_OF_SAGE_ID,1)
STARTED.addQuestDrop(30648,VOUCHER_OF_TRIAL_ID,1)
STARTED.addQuestDrop(27116,ESSENSE_OF_FLAME_ID,1)
STARTED.addQuestDrop(30650,BOOK_OF_GERALD_ID,1)
STARTED.addQuestDrop(30550,TAG_OF_RUMOR_ID,1)
STARTED.addQuestDrop(30036,PICTURE_OF_NAHIR_ID,1)
STARTED.addQuestDrop(27117,HAIR_OF_NAHIR_ID,1)
STARTED.addQuestDrop(30652,BOOK_OF_DARKNESS_ID,1)
STARTED.addQuestDrop(27118,DEBRIS_OF_WILLOW_ID,1)
STARTED.addQuestDrop(30651,GREY_BADGE_ID,1)
STARTED.addQuestDrop(30649,SPIRIT_OF_FLAME_ID,1)
STARTED.addQuestDrop(30036,STATUE_OF_EINHASAD_ID,1)

print "importing quests: 215: Trial Of Pilgrim"
