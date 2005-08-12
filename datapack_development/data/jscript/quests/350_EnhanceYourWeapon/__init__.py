# Weapon SA Quest Written By MickyLee
print "importing quests: 350: Enhance Your Weapon"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest


RED_SOUL_CRYSTAL0_ID = 4629
RED_SOUL_CRYSTAL1_ID = 4630
RED_SOUL_CRYSTAL2_ID = 4631
RED_SOUL_CRYSTAL3_ID = 4632
RED_SOUL_CRYSTAL4_ID = 4633
RED_SOUL_CRYSTAL5_ID = 4634
RED_SOUL_CRYSTAL6_ID = 4635
RED_SOUL_CRYSTAL7_ID = 4636
RED_SOUL_CRYSTAL8_ID = 4637
RED_SOUL_CRYSTAL9_ID = 4638
RED_SOUL_CRYSTAL10_ID = 4639
RED_SOUL_CRYSTALX_ID = 4662
GREEN_SOUL_CRYSTAL0_ID = 4640
GREEN_SOUL_CRYSTAL1_ID = 4641
GREEN_SOUL_CRYSTAL2_ID = 4642
GREEN_SOUL_CRYSTAL3_ID = 4643
GREEN_SOUL_CRYSTAL4_ID = 4644
GREEN_SOUL_CRYSTAL5_ID = 4645
GREEN_SOUL_CRYSTAL6_ID = 4646
GREEN_SOUL_CRYSTAL7_ID = 4647
GREEN_SOUL_CRYSTAL8_ID = 4648
GREEN_SOUL_CRYSTAL9_ID = 4649
GREEN_SOUL_CRYSTAL10_ID = 4650
GREEN_SOUL_CRYSTALX_ID = 4663
BLUE_SOUL_CRYSTAL0_ID = 4651
BLUE_SOUL_CRYSTAL1_ID = 4652
BLUE_SOUL_CRYSTAL2_ID = 4653
BLUE_SOUL_CRYSTAL3_ID = 4654
BLUE_SOUL_CRYSTAL4_ID = 4655
BLUE_SOUL_CRYSTAL5_ID = 4656
BLUE_SOUL_CRYSTAL6_ID = 4657
BLUE_SOUL_CRYSTAL7_ID = 4658
BLUE_SOUL_CRYSTAL8_ID = 4659
BLUE_SOUL_CRYSTAL9_ID = 4660
BLUE_SOUL_CRYSTAL10_ID = 4661
BLUE_SOUL_CRYSTALX_ID = 4664

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "7856_1" :
       htmltext = "7856-02.htm"
    elif event == "1" :
        st.set("cond","1")
        st.set("id","0")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        htmltext = "7856-04.htm"
    elif event == "7856_2" :
        htmltext = "7856-05.htm"
    elif event == "7856_3" :
        htmltext = "7856-06.htm"
    elif event == "7856_4" :
        htmltext = "7856-07.htm"
    elif event == "7856_5" :
        htmltext = "7856-08.htm"
    elif event == "7856_6" :
        st.giveItems(RED_SOUL_CRYSTAL0_ID,1)
        htmltext = "7856-09.htm"
    elif event == "7856_7" :
        st.giveItems(GREEN_SOUL_CRYSTAL0_ID,1)
        htmltext = "7856-10.htm"
    elif event == "7856_8" :
        st.giveItems(BLUE_SOUL_CRYSTAL0_ID,1)
        htmltext = "7856-11.htm"
    elif event == "7856_9" :
        htmltext = "7856-12.htm"
    elif event == "7856_10" :
        htmltext = "7856-13.htm"
    elif event == "7856_11" :
        htmltext = "7856-14.htm"
    elif event == "7856_12" :
        htmltext = "7856-15.htm"
    elif event == "7856_13" :
        htmltext = "7856-16.htm"
    elif event == "7856_14" :
        htmltext = "7856-17.htm"
    elif event == "7856_15" :
        htmltext = "7856-18.htm"
    elif event == "7856_16" :
        htmltext = "7856-19.htm"
    elif event == "7856_17" :
        htmltext = "7856-20.htm"
    elif event == "7856_18" :
        st.set("cond","0")
        st.setState(COMPLETED)
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7856 and int(st.get("cond")) == 0:   
        htmltext = "7856-01.htm"
   elif npcId == 7856 and int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL0_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL0_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL0_ID) != 0 or st.getQuestItemsCount(RED_SOUL_CRYSTAL1_ID) != 0 or st.getQuestItemsCount(RED_SOUL_CRYSTAL2_ID) != 0 or st.getQuestItemsCount(RED_SOUL_CRYSTAL3_ID) != 0 or st.getQuestItemsCount(RED_SOUL_CRYSTAL4_ID) != 0 or st.getQuestItemsCount(RED_SOUL_CRYSTAL5_ID) != 0 or st.getQuestItemsCount(RED_SOUL_CRYSTAL6_ID) != 0 or st.getQuestItemsCount(RED_SOUL_CRYSTAL7_ID) != 0 or st.getQuestItemsCount(RED_SOUL_CRYSTAL8_ID) != 0 or st.getQuestItemsCount(RED_SOUL_CRYSTAL9_ID) != 0 or st.getQuestItemsCount(RED_SOUL_CRYSTAL10_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL1_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL2_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL3_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL4_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL5_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL6_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL7_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL8_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL9_ID) != 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL10_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL1_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL2_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL3_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL4_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL5_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL6_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL7_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL8_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL9_ID) != 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL10_ID) != 0 :
        htmltext = "7856-03.htm"
   elif npcId == 7856 and int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL0_ID) == 0 and st.getQuestItemsCount(GREEN_SOUL_CRYSTAL0_ID) == 0 and st.getQuestItemsCount(BLUE_SOUL_CRYSTAL0_ID) == 0 :
        htmltext = "7856-21.htm"
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 588 or npcId == 583 or npcId == 584 or npcId == 585 or npcId == 586 or npcId == 587 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL0_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL0_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL0_ID) > 0 :
          if st.getQuestItemsCount(RED_SOUL_CRYSTAL0_ID) == 1 :
            if st.getRandom(100) < 10 :
                st.takeItems(RED_SOUL_CRYSTAL0_ID,1)
                st.giveItems(RED_SOUL_CRYSTALX_ID,1)
            elif st.getRandom(100) < 40 :
                st.takeItems(RED_SOUL_CRYSTAL0_ID,1)
                st.giveItems(RED_SOUL_CRYSTAL1_ID,1)
                st.playSound("ItemSound.quest_itemget")
          elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL0_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL0_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL0_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL1_ID,1)
                  st.playSound("ItemSound.quest_itemget")
          elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL0_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL0_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL0_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL1_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL1_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL1_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL1_ID) > 0 :
            if st.getQuestItemsCount(RED_SOUL_CRYSTAL1_ID) == 1 :
              if st.getRandom(100) < 10 :
                 st.takeItems(RED_SOUL_CRYSTAL1_ID,1)
                 st.giveItems(RED_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                 st.takeItems(RED_SOUL_CRYSTAL1_ID,1)
                 st.giveItems(RED_SOUL_CRYSTAL2_ID,1)
                 st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL1_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL1_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL1_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL2_ID,1)
                  st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL1_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL1_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL1_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL2_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL2_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL2_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL2_ID) > 0 :
            if st.getQuestItemsCount(RED_SOUL_CRYSTAL2_ID) == 1 :
              if st.getRandom(100) < 10 :
                 st.takeItems(RED_SOUL_CRYSTAL2_ID,1)
                 st.giveItems(RED_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                 st.takeItems(RED_SOUL_CRYSTAL2_ID,1)
                 st.giveItems(RED_SOUL_CRYSTAL3_ID,1)
                 st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL2_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL2_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL2_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL3_ID,1)
                  st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL2_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL2_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL2_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL3_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL3_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL3_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL3_ID) > 0 :
            if st.getQuestItemsCount(RED_SOUL_CRYSTAL3_ID) == 1 :
              if st.getRandom(100) < 10 :
                 st.takeItems(RED_SOUL_CRYSTAL3_ID,1)
                 st.giveItems(RED_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                 st.takeItems(RED_SOUL_CRYSTAL3_ID,1)
                 st.giveItems(RED_SOUL_CRYSTAL4_ID,1)
                 st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL3_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL3_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL3_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL4_ID,1)
                  st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL3_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL3_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL3_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL4_ID,1)
                  st.playSound("ItemSound.quest_itemget")
   elif npcId == 636 or npcId == 637 or npcId == 638 or npcId == 639 or npcId == 640 or npcId == 641 or npcId == 642 or npcId == 643 or npcId == 644 or npcId == 645 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL0_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL0_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL0_ID) > 0 :
          if st.getQuestItemsCount(RED_SOUL_CRYSTAL0_ID) == 1 :
            if st.getRandom(100) < 10 :
                st.takeItems(RED_SOUL_CRYSTAL0_ID,1)
                st.giveItems(RED_SOUL_CRYSTALX_ID,1)
            elif st.getRandom(100) < 40 :
                st.takeItems(RED_SOUL_CRYSTAL0_ID,1)
                st.giveItems(RED_SOUL_CRYSTAL1_ID,1)
                st.playSound("ItemSound.quest_itemget")
          elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL0_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL0_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL0_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL1_ID,1)
                  st.playSound("ItemSound.quest_itemget")
          elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL0_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL0_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL0_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL1_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL1_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL1_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL1_ID) > 0 :
            if st.getQuestItemsCount(RED_SOUL_CRYSTAL1_ID) == 1 :
              if st.getRandom(100) < 10 :
                 st.takeItems(RED_SOUL_CRYSTAL1_ID,1)
                 st.giveItems(RED_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                 st.takeItems(RED_SOUL_CRYSTAL1_ID,1)
                 st.giveItems(RED_SOUL_CRYSTAL2_ID,1)
                 st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL1_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL1_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL1_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL2_ID,1)
                  st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL1_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL1_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL1_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL2_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL2_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL2_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL2_ID) > 0 :
            if st.getQuestItemsCount(RED_SOUL_CRYSTAL2_ID) == 1 :
              if st.getRandom(100) < 10 :
                 st.takeItems(RED_SOUL_CRYSTAL2_ID,1)
                 st.giveItems(RED_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                 st.takeItems(RED_SOUL_CRYSTAL2_ID,1)
                 st.giveItems(RED_SOUL_CRYSTAL3_ID,1)
                 st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL2_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL2_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL2_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL3_ID,1)
                  st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL2_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL2_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL2_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL3_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL3_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL3_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL3_ID) > 0 :
            if st.getQuestItemsCount(RED_SOUL_CRYSTAL3_ID) == 1 :
              if st.getRandom(100) < 10 :
                 st.takeItems(RED_SOUL_CRYSTAL3_ID,1)
                 st.giveItems(RED_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                 st.takeItems(RED_SOUL_CRYSTAL3_ID,1)
                 st.giveItems(RED_SOUL_CRYSTAL4_ID,1)
                 st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL3_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL3_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL3_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL4_ID,1)
                  st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL3_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL3_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL3_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL4_ID,1)
                  st.playSound("ItemSound.quest_itemget")
   elif npcId == 646 or npcId == 647 or npcId == 648 or npcId == 649 or npcId == 650 or npcId == 1006 or npcId == 1007 or npcId == 1008 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL0_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL0_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL0_ID) > 0 :
          if st.getQuestItemsCount(RED_SOUL_CRYSTAL0_ID) == 1 :
            if st.getRandom(100) < 10 :
                st.takeItems(RED_SOUL_CRYSTAL0_ID,1)
                st.giveItems(RED_SOUL_CRYSTALX_ID,1)
            elif st.getRandom(100) < 40 :
                st.takeItems(RED_SOUL_CRYSTAL0_ID,1)
                st.giveItems(RED_SOUL_CRYSTAL1_ID,1)
                st.playSound("ItemSound.quest_itemget")
          elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL0_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL0_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL0_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL1_ID,1)
                  st.playSound("ItemSound.quest_itemget")
          elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL0_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL0_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL0_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL1_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL1_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL1_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL1_ID) > 0 :
            if st.getQuestItemsCount(RED_SOUL_CRYSTAL1_ID) == 1 :
              if st.getRandom(100) < 10 :
                 st.takeItems(RED_SOUL_CRYSTAL1_ID,1)
                 st.giveItems(RED_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                 st.takeItems(RED_SOUL_CRYSTAL1_ID,1)
                 st.giveItems(RED_SOUL_CRYSTAL2_ID,1)
                 st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL1_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL1_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL1_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL2_ID,1)
                  st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL1_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL1_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL1_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL2_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL2_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL2_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL2_ID) > 0 :
            if st.getQuestItemsCount(RED_SOUL_CRYSTAL2_ID) == 1 :
              if st.getRandom(100) < 10 :
                 st.takeItems(RED_SOUL_CRYSTAL2_ID,1)
                 st.giveItems(RED_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                 st.takeItems(RED_SOUL_CRYSTAL2_ID,1)
                 st.giveItems(RED_SOUL_CRYSTAL3_ID,1)
                 st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL2_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL2_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL2_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL3_ID,1)
                  st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL2_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL2_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL2_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL3_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL3_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL3_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL3_ID) > 0 :
            if st.getQuestItemsCount(RED_SOUL_CRYSTAL3_ID) == 1 :
              if st.getRandom(100) < 10 :
                 st.takeItems(RED_SOUL_CRYSTAL3_ID,1)
                 st.giveItems(RED_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                 st.takeItems(RED_SOUL_CRYSTAL3_ID,1)
                 st.giveItems(RED_SOUL_CRYSTAL4_ID,1)
                 st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL3_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL3_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL3_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL4_ID,1)
                  st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL3_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL3_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL3_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL4_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL4_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL4_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL4_ID) > 0 :
          if st.getQuestItemsCount(RED_SOUL_CRYSTAL4_ID) == 1 :
            if st.getRandom(100) < 10 :
                st.takeItems(RED_SOUL_CRYSTAL4_ID,1)
                st.giveItems(RED_SOUL_CRYSTALX_ID,1)
            elif st.getRandom(100) < 40 :
                st.takeItems(RED_SOUL_CRYSTAL4_ID,1)
                st.giveItems(RED_SOUL_CRYSTAL5_ID,1)
                st.playSound("ItemSound.quest_itemget")
          elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL4_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL4_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL4_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL5_ID,1)
                  st.playSound("ItemSound.quest_itemget")
          elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL4_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL4_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL4_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL5_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL5_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL5_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL5_ID) > 0 :
            if st.getQuestItemsCount(RED_SOUL_CRYSTAL5_ID) == 1 :
              if st.getRandom(100) < 10 :
                 st.takeItems(RED_SOUL_CRYSTAL5_ID,1)
                 st.giveItems(RED_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                 st.takeItems(RED_SOUL_CRYSTAL5_ID,1)
                 st.giveItems(RED_SOUL_CRYSTAL6_ID,1)
                 st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL5_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL5_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL5_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL6_ID,1)
                  st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL5_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL5_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL5_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL6_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL6_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL6_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL6_ID) > 0 :
            if st.getQuestItemsCount(RED_SOUL_CRYSTAL6_ID) == 1 :
              if st.getRandom(100) < 10 :
                 st.takeItems(RED_SOUL_CRYSTAL6_ID,1)
                 st.giveItems(RED_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                 st.takeItems(RED_SOUL_CRYSTAL6_ID,1)
                 st.giveItems(RED_SOUL_CRYSTAL7_ID,1)
                 st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL6_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL6_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL6_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL7_ID,1)
                  st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL6_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL6_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL6_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL7_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL7_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL7_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL7_ID) > 0 :
            if st.getQuestItemsCount(RED_SOUL_CRYSTAL7_ID) == 1 :
              if st.getRandom(100) < 10 :
                 st.takeItems(RED_SOUL_CRYSTAL7_ID,1)
                 st.giveItems(RED_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                 st.takeItems(RED_SOUL_CRYSTAL7_ID,1)
                 st.giveItems(RED_SOUL_CRYSTAL8_ID,1)
                 st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL7_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL7_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL7_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL8_ID,1)
                  st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL7_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL7_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL7_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL8_ID,1)
                  st.playSound("ItemSound.quest_itemget")
   elif npcId == 1009 or npcId == 1010 or npcId == 674 or npcId == 821 or npcId == 823 or npcId == 826 or npcId == 827 or npcId == 828 or npcId == 829 or npcId == 830 or npcId == 831 or npcId == 858 or npcId == 859 or npcId == 860 or npcId == 1062 or npcId == 1063 or npcId == 1068 or npcId == 1070 or npcId == 627 or npcId == 628 or npcId == 629 or npcId == 761 or npcId == 762 :
        st.set("id","0")
        if int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL0_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL0_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL0_ID) > 0 :
          if st.getQuestItemsCount(RED_SOUL_CRYSTAL0_ID) == 1 :
            if st.getRandom(100) < 10 :
                st.takeItems(RED_SOUL_CRYSTAL0_ID,1)
                st.giveItems(RED_SOUL_CRYSTALX_ID,1)
            elif st.getRandom(100) < 40 :
                st.takeItems(RED_SOUL_CRYSTAL0_ID,1)
                st.giveItems(RED_SOUL_CRYSTAL1_ID,1)
                st.playSound("ItemSound.quest_itemget")
          elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL0_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL0_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL0_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL1_ID,1)
                  st.playSound("ItemSound.quest_itemget")
          elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL0_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL0_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL0_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL1_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL1_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL1_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL1_ID) > 0 :
            if st.getQuestItemsCount(RED_SOUL_CRYSTAL1_ID) == 1 :
              if st.getRandom(100) < 10 :
                 st.takeItems(RED_SOUL_CRYSTAL1_ID,1)
                 st.giveItems(RED_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                 st.takeItems(RED_SOUL_CRYSTAL1_ID,1)
                 st.giveItems(RED_SOUL_CRYSTAL2_ID,1)
                 st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL1_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL1_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL1_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL2_ID,1)
                  st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL1_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL1_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL1_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL2_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL2_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL2_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL2_ID) > 0 :
            if st.getQuestItemsCount(RED_SOUL_CRYSTAL2_ID) == 1 :
              if st.getRandom(100) < 10 :
                 st.takeItems(RED_SOUL_CRYSTAL2_ID,1)
                 st.giveItems(RED_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                 st.takeItems(RED_SOUL_CRYSTAL2_ID,1)
                 st.giveItems(RED_SOUL_CRYSTAL3_ID,1)
                 st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL2_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL2_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL2_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL3_ID,1)
                  st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL2_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL2_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL2_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL3_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL3_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL3_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL3_ID) > 0 :
            if st.getQuestItemsCount(RED_SOUL_CRYSTAL3_ID) == 1 :
              if st.getRandom(100) < 10 :
                 st.takeItems(RED_SOUL_CRYSTAL3_ID,1)
                 st.giveItems(RED_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                 st.takeItems(RED_SOUL_CRYSTAL3_ID,1)
                 st.giveItems(RED_SOUL_CRYSTAL4_ID,1)
                 st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL3_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL3_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL3_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL4_ID,1)
                  st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL3_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL3_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL3_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL4_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL4_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL4_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL4_ID) > 0 :
          if st.getQuestItemsCount(RED_SOUL_CRYSTAL4_ID) == 1 :
            if st.getRandom(100) < 10 :
                st.takeItems(RED_SOUL_CRYSTAL4_ID,1)
                st.giveItems(RED_SOUL_CRYSTALX_ID,1)
            elif st.getRandom(100) < 40 :
                st.takeItems(RED_SOUL_CRYSTAL4_ID,1)
                st.giveItems(RED_SOUL_CRYSTAL5_ID,1)
                st.playSound("ItemSound.quest_itemget")
          elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL4_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL4_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL4_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL5_ID,1)
                  st.playSound("ItemSound.quest_itemget")
          elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL4_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL4_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL4_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL5_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL5_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL5_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL5_ID) > 0 :
            if st.getQuestItemsCount(RED_SOUL_CRYSTAL5_ID) == 1 :
              if st.getRandom(100) < 10 :
                 st.takeItems(RED_SOUL_CRYSTAL5_ID,1)
                 st.giveItems(RED_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                 st.takeItems(RED_SOUL_CRYSTAL5_ID,1)
                 st.giveItems(RED_SOUL_CRYSTAL6_ID,1)
                 st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL5_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL5_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL5_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL6_ID,1)
                  st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL5_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL5_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL5_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL6_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL6_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL6_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL6_ID) > 0 :
            if st.getQuestItemsCount(RED_SOUL_CRYSTAL6_ID) == 1 :
              if st.getRandom(100) < 10 :
                 st.takeItems(RED_SOUL_CRYSTAL6_ID,1)
                 st.giveItems(RED_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                 st.takeItems(RED_SOUL_CRYSTAL6_ID,1)
                 st.giveItems(RED_SOUL_CRYSTAL7_ID,1)
                 st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL6_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL6_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL6_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL7_ID,1)
                  st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL6_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL6_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL6_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL7_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL7_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL7_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL7_ID) > 0 :
            if st.getQuestItemsCount(RED_SOUL_CRYSTAL7_ID) == 1 :
              if st.getRandom(100) < 10 :
                 st.takeItems(RED_SOUL_CRYSTAL7_ID,1)
                 st.giveItems(RED_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                 st.takeItems(RED_SOUL_CRYSTAL7_ID,1)
                 st.giveItems(RED_SOUL_CRYSTAL8_ID,1)
                 st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL7_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL7_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL7_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL8_ID,1)
                  st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL7_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL7_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL7_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL8_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL8_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL8_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL8_ID) > 0 :
            if st.getQuestItemsCount(RED_SOUL_CRYSTAL8_ID) == 1 :
              if st.getRandom(100) < 10 :
                 st.takeItems(RED_SOUL_CRYSTAL8_ID,1)
                 st.giveItems(RED_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                 st.takeItems(RED_SOUL_CRYSTAL8_ID,1)
                 st.giveItems(RED_SOUL_CRYSTAL9_ID,1)
                 st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL8_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL8_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL8_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL9_ID,1)
                  st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL8_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL8_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL8_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL9_ID,1)
                  st.playSound("ItemSound.quest_itemget")
        elif int(st.get("cond")) == 1 and st.getQuestItemsCount(RED_SOUL_CRYSTAL9_ID) > 0 or st.getQuestItemsCount(GREEN_SOUL_CRYSTAL9_ID) > 0 or st.getQuestItemsCount(BLUE_SOUL_CRYSTAL9_ID) > 0 :
            if st.getQuestItemsCount(RED_SOUL_CRYSTAL9_ID) == 1 :
              if st.getRandom(100) < 10 :
                 st.takeItems(RED_SOUL_CRYSTAL9_ID,1)
                 st.giveItems(RED_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                 st.takeItems(RED_SOUL_CRYSTAL9_ID,1)
                 st.giveItems(RED_SOUL_CRYSTAL10_ID,1)
                 st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(GREEN_SOUL_CRYSTAL9_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(GREEN_SOUL_CRYSTAL9_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(GREEN_SOUL_CRYSTAL9_ID,1)
                  st.giveItems(GREEN_SOUL_CRYSTAL10_ID,1)
                  st.playSound("ItemSound.quest_itemget")
            elif st.getQuestItemsCount(BLUE_SOUL_CRYSTAL9_ID) == 1 :
              if st.getRandom(100) < 10 :
                  st.takeItems(BLUE_SOUL_CRYSTAL9_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTALX_ID,1)
              elif st.getRandom(100) < 40 :
                  st.takeItems(BLUE_SOUL_CRYSTAL9_ID,1)
                  st.giveItems(BLUE_SOUL_CRYSTAL10_ID,1)
                  st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(350,"350_EnhanceYourWeapon","Enhance Your Weapon")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7856)

STARTED.addTalkId(7856)

STARTED.addKillId(588)
STARTED.addKillId(583)
STARTED.addKillId(584)
STARTED.addKillId(585)
STARTED.addKillId(586)
STARTED.addKillId(587)
STARTED.addKillId(636)
STARTED.addKillId(637)
STARTED.addKillId(638)
STARTED.addKillId(639)
STARTED.addKillId(640)
STARTED.addKillId(641)
STARTED.addKillId(642)
STARTED.addKillId(643)
STARTED.addKillId(644)
STARTED.addKillId(645)
STARTED.addKillId(646)
STARTED.addKillId(647)
STARTED.addKillId(648)
STARTED.addKillId(649)
STARTED.addKillId(650)
STARTED.addKillId(1006)
STARTED.addKillId(1007)
STARTED.addKillId(1008)
STARTED.addKillId(1009)
STARTED.addKillId(1010)
STARTED.addKillId(674)
STARTED.addKillId(821)
STARTED.addKillId(823)
STARTED.addKillId(826)
STARTED.addKillId(827)
STARTED.addKillId(828)
STARTED.addKillId(829)
STARTED.addKillId(830)
STARTED.addKillId(831)
STARTED.addKillId(858)
STARTED.addKillId(859)
STARTED.addKillId(860)
STARTED.addKillId(1062)
STARTED.addKillId(1063)
STARTED.addKillId(1068)
STARTED.addKillId(1070)
STARTED.addKillId(627)
STARTED.addKillId(628)
STARTED.addKillId(629)


STARTED.addQuestDrop(4629,RED_SOUL_CRYSTAL0_ID,1)
STARTED.addQuestDrop(4630,RED_SOUL_CRYSTAL1_ID,1)
STARTED.addQuestDrop(4631,RED_SOUL_CRYSTAL2_ID,1)
STARTED.addQuestDrop(4632,RED_SOUL_CRYSTAL3_ID,1)
STARTED.addQuestDrop(4633,RED_SOUL_CRYSTAL4_ID,1)
STARTED.addQuestDrop(4634,RED_SOUL_CRYSTAL5_ID,1)
STARTED.addQuestDrop(4635,RED_SOUL_CRYSTAL6_ID,1)
STARTED.addQuestDrop(4636,RED_SOUL_CRYSTAL7_ID,1)
STARTED.addQuestDrop(4637,RED_SOUL_CRYSTAL8_ID,1)
STARTED.addQuestDrop(4638,RED_SOUL_CRYSTAL9_ID,1)
STARTED.addQuestDrop(4639,RED_SOUL_CRYSTAL10_ID,1)
STARTED.addQuestDrop(4662,RED_SOUL_CRYSTALX_ID,1)
STARTED.addQuestDrop(4640,GREEN_SOUL_CRYSTAL0_ID,1)
STARTED.addQuestDrop(4641,GREEN_SOUL_CRYSTAL1_ID,1)
STARTED.addQuestDrop(4642,GREEN_SOUL_CRYSTAL2_ID,1)
STARTED.addQuestDrop(4643,GREEN_SOUL_CRYSTAL3_ID,1)
STARTED.addQuestDrop(4644,GREEN_SOUL_CRYSTAL4_ID,1)
STARTED.addQuestDrop(4645,GREEN_SOUL_CRYSTAL5_ID,1)
STARTED.addQuestDrop(4646,GREEN_SOUL_CRYSTAL6_ID,1)
STARTED.addQuestDrop(4647,GREEN_SOUL_CRYSTAL7_ID,1)
STARTED.addQuestDrop(4648,GREEN_SOUL_CRYSTAL8_ID,1)
STARTED.addQuestDrop(4649,GREEN_SOUL_CRYSTAL9_ID,1)
STARTED.addQuestDrop(4650,GREEN_SOUL_CRYSTAL10_ID,1)
STARTED.addQuestDrop(4663,GREEN_SOUL_CRYSTALX_ID,1)
STARTED.addQuestDrop(4651,BLUE_SOUL_CRYSTAL0_ID,1)
STARTED.addQuestDrop(4652,BLUE_SOUL_CRYSTAL1_ID,1)
STARTED.addQuestDrop(4653,BLUE_SOUL_CRYSTAL2_ID,1)
STARTED.addQuestDrop(4654,BLUE_SOUL_CRYSTAL3_ID,1)
STARTED.addQuestDrop(4655,BLUE_SOUL_CRYSTAL4_ID,1)
STARTED.addQuestDrop(4656,BLUE_SOUL_CRYSTAL5_ID,1)
STARTED.addQuestDrop(4657,BLUE_SOUL_CRYSTAL6_ID,1)
STARTED.addQuestDrop(4658,BLUE_SOUL_CRYSTAL7_ID,1)
STARTED.addQuestDrop(4659,BLUE_SOUL_CRYSTAL8_ID,1)
STARTED.addQuestDrop(4660,BLUE_SOUL_CRYSTAL9_ID,1)
STARTED.addQuestDrop(4661,BLUE_SOUL_CRYSTAL10_ID,1)
STARTED.addQuestDrop(4664,BLUE_SOUL_CRYSTALX_ID,1)
