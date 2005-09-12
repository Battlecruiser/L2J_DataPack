# Maked by Mr. Have fun! Version 0.2
print "importing quests: 211: Trial Of Challenger"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

LETTER_OF_KASH_ID = 2628
SCROLL_OF_SHYSLASSY_ID = 2631
WATCHERS_EYE1_ID = 2629
BROKEN_KEY_ID = 2632
MITHRIL_SCALE_GAITERS_MATERIAL_ID = 2918
BRIGANDINE_GAUNTLET_PATTERN_ID = 2927
MANTICOR_SKIN_GAITERS_PATTERN_ID = 1943
GAUNTLET_OF_REPOSE_OF_THE_SOUL_PATTERN_ID = 1946
IRON_BOOTS_DESIGN_ID = 1940
TOME_OF_BLOOD_PAGE_ID = 2030
ELVEN_NECKLACE_BEADS_ID = 1904
WHITE_TUNIC_PATTERN_ID = 1936
ADENA_ID = 57
MARK_OF_CHALLENGER_ID = 2627
WATCHERS_EYE2_ID = 2630

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      htmlfile = "7644-05.htm"
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      st.set("cond","1")
    elif event == "7644_1" :
          htmltext = "7644-04.htm"
          st.set("cond","1")
          return htmltext
    elif event == "7645_1" :
          htmltext = "7645-02.htm"
          st.takeItems(LETTER_OF_KASH_ID,1)
          st.set("cond","4")
    elif event == "7647_1" :
          if st.getQuestItemsCount(BROKEN_KEY_ID) == 1 :
            if st.getRandom(10) < 2 :
              htmltext = "7647-03.htm"
              st.takeItems(BROKEN_KEY_ID,1)
              st.playSound("ItemSound.quest_jackpot")
              if st.getGameTicks() != int(st.get("id")) :
                st.set("id",str(st.getGameTicks()))
                n = st.getRandom(100)
                if n > 90 :
                  st.giveItems(MITHRIL_SCALE_GAITERS_MATERIAL_ID,1)
                  st.giveItems(BRIGANDINE_GAUNTLET_PATTERN_ID,1)
                  st.giveItems(MANTICOR_SKIN_GAITERS_PATTERN_ID,1)
                  st.giveItems(GAUNTLET_OF_REPOSE_OF_THE_SOUL_PATTERN_ID,1)
                  st.giveItems(IRON_BOOTS_DESIGN_ID,1)
                elif n > 70 :
                  st.giveItems(TOME_OF_BLOOD_PAGE_ID,1)
                  st.giveItems(ELVEN_NECKLACE_BEADS_ID,1)
                elif n > 40 :
                  st.giveItems(WHITE_TUNIC_PATTERN_ID,1)
                else:
                  st.giveItems(IRON_BOOTS_DESIGN_ID,1)
            else:
              htmltext = "7647-02.htm"
              n = st.getRandom(1000)+1
              st.giveItems(ADENA_ID,i1)
              st.takeItems(BROKEN_KEY_ID,1)
          else:
            htmltext = "7647-04.htm"
            st.takeItems(BROKEN_KEY_ID,1)
    elif event == "7646_1" :
          htmltext = "7646-02.htm"
    elif event == "7646_2" :
          htmltext = "7646-03.htm"
    elif event == "7646_3" :
          htmltext = "7646-04.htm"
          st.set("cond","7")
          st.takeItems(WATCHERS_EYE2_ID,1)
    elif event == "7646_4" :
          htmltext = "7646-06.htm"
          st.set("cond","7")
          st.takeItems(WATCHERS_EYE2_ID,1)
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7644 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if int(st.get("cond")) < 15 :
        if (st.getPlayer().getClassId()==0x01 or st.getPlayer().getClassId()==0x13 or st.getPlayer().getClassId()==0x20 or st.getPlayer().getClassId()==0x2d or st.getPlayer().getClassId()==0x2f) and st.getPlayer().getLevel() >= 35 :
          htmltext = "7644-03.htm"
        elif (st.getPlayer().getClassId()==0x01 or st.getPlayer().getClassId()==0x13 or st.getPlayer().getClassId()==0x20 or st.getPlayer().getClassId()==0x2d or st.getPlayer().getClassId()==0x2f) :
          htmltext = "7644-01.htm"
        else:
          htmltext = "7644-02.htm"
      else:
        htmltext = "7644-02.htm"
   elif npcId == 7644 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7644 and int(st.get("cond"))==1 :
      htmltext = "7644-06.htm"
   elif npcId == 7644 and int(st.get("cond"))==1 and st.getQuestItemsCount(SCROLL_OF_SHYSLASSY_ID)==1 :
      htmltext = "7644-07.htm"
      st.giveItems(LETTER_OF_KASH_ID,1)
      st.takeItems(SCROLL_OF_SHYSLASSY_ID,1)
      st.set("cond","3")
   elif npcId == 7644 and int(st.get("cond"))==1 and st.getQuestItemsCount(LETTER_OF_KASH_ID)==1 :
      htmltext = "7644-08.htm"
   elif npcId == 7644 and int(st.get("cond"))>=7 :
      htmltext = "7644-09.htm"
   elif npcId == 7645 and int(st.get("cond"))==1 and st.getQuestItemsCount(LETTER_OF_KASH_ID)==1 :
      htmltext = "7645-01.htm"
   elif npcId == 7645 and int(st.get("cond"))==4 and st.getQuestItemsCount(WATCHERS_EYE1_ID)==0 :
      htmltext = "7645-03.htm"
   elif npcId == 7645 and int(st.get("cond"))==1 and st.getQuestItemsCount(WATCHERS_EYE1_ID) :
      htmltext = "7645-04.htm"
      st.takeItems(WATCHERS_EYE1_ID,1)
      st.set("cond","5")
   elif npcId == 7645 and int(st.get("cond"))==5 :
      htmltext = "7645-05.htm"
   elif npcId == 7645 and int(st.get("cond"))>=7 :
      htmltext = "7645-06.htm"
   elif npcId == 7647 and int(st.get("cond"))==1 :
      htmltext = "7647-01.htm"
   elif npcId == 7646 and int(st.get("cond"))==1 and st.getQuestItemsCount(WATCHERS_EYE2_ID) :
      htmltext = "7646-01.htm"
   elif npcId == 7646 and int(st.get("cond"))==7 :
      htmltext = "7646-06a.htm"
   elif npcId == 7646 and int(st.get("cond"))==9 :
      if st.getGameTicks() != int(st.get("id")) :
        st.set("id",str(st.getGameTicks()))
        st.addExpAndSp(26000,3100)
      htmltext = "7646-07.htm"
      st.giveItems(MARK_OF_CHALLENGER_ID,1)
      st.takeItems(BROKEN_KEY_ID,1)
      st.setState(COMPLETED)
      st.playSound("ItemSound.quest_finish")
      st.set("onlyone","1")
      st.set("cond","0")
   elif npcId == 7535 and int(st.get("cond"))==7 :
      if st.getPlayer().getLevel() >= 36 :
        htmltext = "7535-01.htm"
        st.addRadar(176560,-184969,-3729);
        st.set("cond","8")
      else:
        htmltext = "7535-03.htm"
   elif npcId == 7535 and int(st.get("cond"))==8 :
      htmltext = "7535-02.htm"
      st.addRadar(176560,-184969,-3729);
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 5110 :
        if int(st.get("cond")) and int(st.get("cond")) == 1 and st.getQuestItemsCount(SCROLL_OF_SHYSLASSY_ID) == 0 and st.getQuestItemsCount(BROKEN_KEY_ID) == 0 :
          st.giveItems(SCROLL_OF_SHYSLASSY_ID,1)
          st.giveItems(BROKEN_KEY_ID,1)
          st.spawnNpc(7647)
          st.playSound("ItemSound.quest_middle")
          st.set("cond","2")
   elif npcId == 5112 :
        if int(st.get("cond")) and int(st.get("cond")) == 4 and st.getQuestItemsCount(WATCHERS_EYE1_ID) == 0 :
          st.giveItems(WATCHERS_EYE1_ID,1)
          st.playSound("ItemSound.quest_middle")
          st.set("cond","4")
   elif npcId == 5113 :
        if int(st.get("cond")) :
          if int(st.get("cond")) == 5 and st.getQuestItemsCount(WATCHERS_EYE2_ID) == 0 :
            st.giveItems(WATCHERS_EYE2_ID,1)
            st.playSound("ItemSound.quest_middle")
            st.set("cond","6")
#          if Maker_GetNpcCount() < 2 :
          st.spawnNpc(7646)
   elif npcId == 5114 :
        if int(st.get("cond")) :
          if int(st.get("cond")) == 8 :
            st.set("cond","9")
            st.playSound("ItemSound.quest_middle")
            st.removeRadar(176560,-184969,-3729);
#          if Maker_GetNpcCount() == 1 :
	  st.spawnNpc(7646)
   return

QUEST       = Quest(211,"211_TrialOfChallenger","Trial Of Challenger")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7644)

STARTED.addTalkId(7535)
STARTED.addTalkId(7644)
STARTED.addTalkId(7645)
STARTED.addTalkId(7646)
STARTED.addTalkId(7647)

STARTED.addKillId(5110)
STARTED.addKillId(5112)
STARTED.addKillId(5113)
STARTED.addKillId(5114)

STARTED.addQuestDrop(5110,SCROLL_OF_SHYSLASSY_ID,1)
STARTED.addQuestDrop(7644,LETTER_OF_KASH_ID,1)
STARTED.addQuestDrop(5112,WATCHERS_EYE1_ID,1)
STARTED.addQuestDrop(5110,BROKEN_KEY_ID,1)
STARTED.addQuestDrop(5113,WATCHERS_EYE2_ID,1)
