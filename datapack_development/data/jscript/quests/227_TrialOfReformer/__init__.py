# Maked by Mr. Have fun! Version 0.2
print "importing quests: 227: Trial Of Reformer"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_REFORMER_ID = 2821
BOOK_OF_REFORM_ID = 2822
LETTER_OF_INTRODUCTION_ID = 2823
SLAS_LETTER_ID = 2824
GREETINGS_ID = 2825
OLMAHUMS_MONEY_ID = 2826
KATARIS_LETTER_ID = 2827
NYAKURIS_LETTER_ID = 2828
KAKANS_LETTER_ID = 3037
UNDEAD_LIST_ID = 2829
RAMUSS_LETTER_ID = 2830
RIPPED_DIARY_ID = 2831
HUGE_NAIL_ID = 2832
LETTER_OF_BETRAYER_ID = 2833
BONE_FRAGMENT4_ID = 2834
BONE_FRAGMENT5_ID = 2835
BONE_FRAGMENT6_ID = 2836
BONE_FRAGMENT7_ID = 2837
BONE_FRAGMENT8_ID = 2838
BONE_FRAGMENT9_ID = 2839

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        htmlfile = "7118-04.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.giveItems(BOOK_OF_REFORM_ID)
        st.set("cond","1")
    elif event == "7118_1" :
          htmltext = "7118-06.htm"
          st.giveItems(LETTER_OF_INTRODUCTION_ID,1)
          st.takeItems(BOOK_OF_REFORM_ID,1)
          st.set("cond","4")
          st.takeItems(HUGE_NAIL_ID,1)
    elif event == "7666_1" :
          htmltext = "7666-03.htm"
    elif event == "7666_2" :
          htmltext = "7666-02.htm"
    elif event == "7666_3" :
          htmltext = "7666-04.htm"
          st.giveItems(SLAS_LETTER_ID,1)
          st.takeItems(LETTER_OF_INTRODUCTION_ID,1)
          st.set("cond","5")
    elif event == "7666_4" :
          htmltext = "7666-02.htm"
    elif event == "7669_1" :
          htmltext = "7669-02.htm"
    elif event == "7669_2" :
          htmltext = "7669-03.htm"
#          if Maker_GetNpcCount() == 1 :
          st.spawnNpc(7732,-9282,-89975,-2331)
          st.spawnNpc(5131,-9382,-89852,-2333)
    elif event == "7669_3" :
          htmltext = "7669-05.htm"
    elif event == "7670_1" :
          htmltext = "7670-03.htm"
#          if Maker_GetNpcCount() == 1 :
          st.spawnNpc(7732,125947,-180049,-1778)
          st.spawnNpc(5132,126019,-179983,-1781)
    elif event == "7670_2" :
          htmltext = "7670-02.htm"
    return htmltext


 def onTalk (Self,npcId,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7118 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
        if int(st.get("cond")) < 15 :
          if (st.getPlayer().getClassId().getId() == 0x0f or st.getPlayer().getClassId().getId() == 0x2a) :
            if st.getPlayer().getLevel() >= 39 :
              htmltext = "7118-03.htm"
              st.set("cond","1")
              return htmltext
            else:
              htmltext = "7118-01.htm"
          else:
            htmltext = "7118-02.htm"
        else:
          htmltext = "7118-02.htm"
   elif npcId == 7118 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7118 and int(st.get("cond"))==3 and st.getQuestItemsCount(HUGE_NAIL_ID)>=1:
        htmltext = "7118-05.htm"
   elif npcId == 7118 and int(st.get("cond"))>=4 :
        htmltext = "7118-07.htm"
   elif npcId == 7666 and int(st.get("cond"))==1 and st.getQuestItemsCount(LETTER_OF_INTRODUCTION_ID)==4 :
        htmltext = "7666-01.htm"
   elif npcId == 7666 and int(st.get("cond"))==1 and st.getQuestItemsCount(SLAS_LETTER_ID)==5 :
        htmltext = "7666-05.htm"
   elif npcId == 7666 and int(st.get("cond"))==10 :
        htmltext = "7666-06.htm"
        st.set("cond","11")
        st.takeItems(OLMAHUMS_MONEY_ID,1)
        st.giveItems(GREETINGS_ID,3)
   elif npcId == 7666 and int(st.get("cond"))==18 and st.getQuestItemsCount((KATARIS_LETTER_ID) and st.getQuestItemsCount(KAKANS_LETTER_ID) and st.getQuestItemsCount(NYAKURIS_LETTER_ID) and st.getQuestItemsCount(RAMUSS_LETTER_ID)) :
        if st.getGameTicks() != int(st.get("id")) :
          st.set("id",str(st.getGameTicks()))
          st.giveItems(MARK_OF_REFORMER_ID,1)
          st.addExpAndSp(28000,3600)
          htmlfile = "7666-07.htm"
          st.set("cond","0")
          st.set("onlyone","1")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.takeItems(KATARIS_LETTER_ID,1)
          st.takeItems(KAKANS_LETTER_ID,1)
          st.takeItems(NYAKURIS_LETTER_ID,1)
          st.takeItems(RAMUSS_LETTER_ID,1)
   elif npcId == 7668 and (int(st.get("cond"))==5 or int(st.get("cond"))==6) :
        htmltext = "7668-01.htm"
        st.set("cond","6")
        st.takeItems(SLAS_LETTER_ID,1)
#        if Maker_GetNpcCount() == 1 :
        st.spawnNpc(7732,-4015,40141,-3664)
        st.spawnNpc(5129,-4034,40201,-3665)
   elif npcId == 7668 and int(st.get("cond"))==8 and st.getQuestItemsCount(OLMAHUMS_MONEY_ID)>=1 :
        htmltext = "7668-02.htm"
#        if Maker_GetNpcCount() < 3 :
        st.spawnNpc(5130,-4106,40174,-3660)
   elif npcId == 7668 and int(st.get("cond"))==9 :
        htmltext = "7668-03.htm"
        st.set("cond","10")
        st.giveItems(KATARIS_LETTER_ID,1)
        st.takeItems(LETTER_OF_BETRAYER_ID,1)
   elif npcId == 7732 and int(st.get("cond"))==7 :
        htmltext = "7732-01.htm"
        st.set("cond","8")
        st.giveItems(OLMAHUMS_MONEY_ID,1)
   elif npcId == 7669 and int(st.get("cond"))==11 and st.getQuestItemsCount(GREETINGS_ID)>0 :
        htmltext = "7669-01.htm"
   elif npcId == 7669 and int(st.get("cond"))==12 :
        htmltext = "7669-04.htm"
        st.set("cond","13")
        st.giveItems(KAKANS_LETTER_ID,1)
        st.takeItems(GREETINGS_ID,1)
   elif npcId == 7670 and int(st.get("cond"))==13 and st.getQuestItemsCount(GREETINGS_ID)>0 :
        htmltext = "7670-01.htm"
   elif npcId == 7670 and int(st.get("cond"))==14 and st.getQuestItemsCount(GREETINGS_ID)>0 :
        htmltext = "7670-04.htm"
        st.set("cond","15")
        st.giveItems(NYAKURIS_LETTER_ID,1)
        st.takeItems(GREETINGS_ID,1)
   elif npcId == 7667 and int(st.get("cond"))==15 and st.getQuestItemsCount(GREETINGS_ID)>0 :
        htmltext = "7667-01.htm"
        st.set("cond","16")
        st.giveItems(UNDEAD_LIST_ID,1)
        st.takeItems(GREETINGS_ID,1)
   elif npcId == 7667 and int(st.get("cond"))==16 :
        htmltext = "7667-02.htm"
   elif npcId == 7667 and int(st.get("cond"))==17 :
        htmltext = "7667-03.htm"
        st.set("cond","18")
        st.takeItems(BONE_FRAGMENT4_ID,1)
        st.takeItems(BONE_FRAGMENT5_ID,1)
        st.takeItems(BONE_FRAGMENT6_ID,1)
        st.takeItems(BONE_FRAGMENT7_ID,1)
        st.takeItems(BONE_FRAGMENT8_ID,1)
        st.giveItems(RAMUSS_LETTER_ID,1)
        st.takeItems(UNDEAD_LIST_ID,1)
   return htmltext

 def onKill (self,npcId,st):
   if npcId == 5099 :
    if int(st.get("cond")) == 1 and int(st.get("cond")) == 1 and st.getQuestItemsCount(RIPPED_DIARY_ID) < 7 and st.getQuestItemsCount(BOOK_OF_REFORM_ID) >= 1 :
      if st.getQuestItemsCount(RIPPED_DIARY_ID) == 6 :
        st.set("cond","2")
        st.spawnNpc(5128)
        st.takeItems(RIPPED_DIARY_ID,st.getQuestItemsCount(RIPPED_DIARY_ID))
      else:
        st.giveItems(RIPPED_DIARY_ID,1)
   elif npcId == 5128 :
    if int(st.get("cond")) == 1 and int(st.get("cond")) == 2 and st.getQuestItemsCount(HUGE_NAIL_ID) == 0 :
      st.giveItems(HUGE_NAIL_ID,1)
      st.set("cond","3")
   elif npcId == 5129 :
    if int(st.get("cond")) == 1 and int(st.get("cond")) == 6 :
      st.set("cond","7")
   elif npcId == 5130 :
    if int(st.get("cond")) == 1 and int(st.get("cond")) == 8 :
      st.set("cond","9")
      st.giveItems(LETTER_OF_BETRAYER_ID,1)
   elif npcId == 5131 :
    if int(st.get("cond")) == 1 and int(st.get("cond")) == 11 :
      st.set("cond","12")
   elif npcId == 5132 :
    if int(st.get("cond")) == 1 and int(st.get("cond")) == 13 :
      st.set("cond","14")
   elif npcId == 105 :
    if int(st.get("cond")) == 1 and int(st.get("cond")) == 16 and st.getQuestItemsCount(BONE_FRAGMENT4_ID) == 0 :
      st.giveItems(BONE_FRAGMENT4_ID,1)
      if st.getQuestItemsCount((BONE_FRAGMENT5_ID) and st.getQuestItemsCount(BONE_FRAGMENT6_ID) and st.getQuestItemsCount(BONE_FRAGMENT7_ID) and st.getQuestItemsCount(BONE_FRAGMENT8_ID)) :
        st.set("cond","17")
   elif npcId == 104 :
    if int(st.get("cond")) == 1 and int(st.get("cond")) == 16 and st.getQuestItemsCount(BONE_FRAGMENT5_ID) == 0 :
      st.giveItems(BONE_FRAGMENT5_ID,1)
      if st.getQuestItemsCount((BONE_FRAGMENT4_ID) and st.getQuestItemsCount(BONE_FRAGMENT6_ID) and st.getQuestItemsCount(BONE_FRAGMENT7_ID) and st.getQuestItemsCount(BONE_FRAGMENT8_ID)) :
        st.set("cond","17")
   elif npcId == 102 :
    if int(st.get("cond")) == 1 and int(st.get("cond")) == 16 and st.getQuestItemsCount(BONE_FRAGMENT6_ID) == 0 :
      st.giveItems(BONE_FRAGMENT6_ID,1)
      if st.getQuestItemsCount((BONE_FRAGMENT4_ID) and st.getQuestItemsCount(BONE_FRAGMENT5_ID) and st.getQuestItemsCount(BONE_FRAGMENT7_ID) and st.getQuestItemsCount(BONE_FRAGMENT8_ID)) :
        st.set("cond","17")
   elif npcId == 22 :
    if int(st.get("cond")) == 1 and int(st.get("cond")) == 16 and st.getQuestItemsCount(BONE_FRAGMENT7_ID) == 0 :
      st.giveItems(BONE_FRAGMENT7_ID,1)
      if st.getQuestItemsCount((BONE_FRAGMENT4_ID) and st.getQuestItemsCount(BONE_FRAGMENT5_ID) and st.getQuestItemsCount(BONE_FRAGMENT6_ID) and st.getQuestItemsCount(BONE_FRAGMENT8_ID)) :
        st.set("cond","17")
   elif npcId == 100 :
    if int(st.get("cond")) == 1 and int(st.get("cond")) == 16 and st.getQuestItemsCount(BONE_FRAGMENT8_ID) == 0 :
      st.giveItems(BONE_FRAGMENT8_ID,1)
      if st.getQuestItemsCount((BONE_FRAGMENT4_ID) and st.getQuestItemsCount(BONE_FRAGMENT5_ID) and st.getQuestItemsCount(BONE_FRAGMENT6_ID) and st.getQuestItemsCount(BONE_FRAGMENT7_ID)) :
        st.set("cond","17")
   return

QUEST       = Quest(227,"227_TrialOfReformer","Trial Of Reformer")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7118)

STARTED.addTalkId(7118)
STARTED.addTalkId(7666)
STARTED.addTalkId(7667)
STARTED.addTalkId(7669)
STARTED.addTalkId(7670)
STARTED.addTalkId(7732)

STARTED.addKillId(100)
STARTED.addKillId(102)
STARTED.addKillId(104)
STARTED.addKillId(105)
STARTED.addKillId(22)
STARTED.addKillId(5099)
STARTED.addKillId(5128)
STARTED.addKillId(5130)

STARTED.addQuestDrop(7118,BOOK_OF_REFORM_ID,1)
STARTED.addQuestDrop(5128,HUGE_NAIL_ID,1)
STARTED.addQuestDrop(7118,LETTER_OF_INTRODUCTION_ID,1)
STARTED.addQuestDrop(7732,OLMAHUMS_MONEY_ID,1)
STARTED.addQuestDrop(7668,KATARIS_LETTER_ID,1)
STARTED.addQuestDrop(7669,KAKANS_LETTER_ID,1)
STARTED.addQuestDrop(7670,NYAKURIS_LETTER_ID,1)
STARTED.addQuestDrop(7667,RAMUSS_LETTER_ID,1)
STARTED.addQuestDrop(7666,SLAS_LETTER_ID,1)
STARTED.addQuestDrop(5130,LETTER_OF_BETRAYER_ID,1)
STARTED.addQuestDrop(7666,GREETINGS_ID,1)
STARTED.addQuestDrop(105,BONE_FRAGMENT4_ID,1)
STARTED.addQuestDrop(104,BONE_FRAGMENT5_ID,1)
STARTED.addQuestDrop(102,BONE_FRAGMENT6_ID,1)
STARTED.addQuestDrop(22,BONE_FRAGMENT7_ID,1)
STARTED.addQuestDrop(100,BONE_FRAGMENT8_ID,1)
STARTED.addQuestDrop(7667,UNDEAD_LIST_ID,1)
STARTED.addQuestDrop(5099,RIPPED_DIARY_ID,1)
