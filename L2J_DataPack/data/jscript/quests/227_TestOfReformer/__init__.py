# Made by Mr. Have fun! Version 0.2
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_REFORMER = 2821
BOOK_OF_REFORM = 2822
LETTER_OF_INTRODUCTION = 2823
SLAS_LETTER = 2824
GREETINGS = 2825
OLMAHUMS_MONEY = 2826
KATARIS_LETTER = 2827
NYAKURIS_LETTER = 2828
UNDEAD_LIST = 2829
RAMUSS_LETTER = 2830
RIPPED_DIARY = 2831
HUGE_NAIL = 2832
LETTER_OF_BETRAYER = 2833
BONE_FRAGMENT4 = 2834
BONE_FRAGMENT5 = 2835
BONE_FRAGMENT6 = 2836
BONE_FRAGMENT7 = 2837
BONE_FRAGMENT8 = 2838
BONE_FRAGMENT9 = 2839
KAKANS_LETTER = 3037

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        htmltext = "7118-04.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.giveItems(BOOK_OF_REFORM,1)
    elif event == "7118_1" :
          htmltext = "7118-06.htm"
          st.giveItems(LETTER_OF_INTRODUCTION,1)
          st.takeItems(BOOK_OF_REFORM,1)
          st.set("cond","4")
          st.takeItems(HUGE_NAIL,1)
    elif event == "7666_1" :
          htmltext = "7666-03.htm"
    elif event == "7666_2" :
          htmltext = "7666-02.htm"
    elif event == "7666_3" :
          htmltext = "7666-04.htm"
          st.giveItems(SLAS_LETTER,1)
          st.takeItems(LETTER_OF_INTRODUCTION,1)
          st.set("cond","5")
    elif event == "7666_4" :
          htmltext = "7666-02.htm"
    elif event == "7669_1" :
          htmltext = "7669-02.htm"
    elif event == "7669_2" :
          htmltext = "7669-03.htm"
          st.getPcSpawn().addSpawn(5131,-9382,-89852,-2333)
    elif event == "7669_3" :
          htmltext = "7669-05.htm"
    elif event == "7670_1" :
          htmltext = "7670-03.htm"
          st.getPcSpawn().addSpawn(5132,126019,-179983,-1781)
    elif event == "7670_2" :
          htmltext = "7670-02.htm"
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
   if npcId == 7118 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if st.getPlayer().getClassId().getId() in [ 0x0f,0x2a ] :
         if st.getPlayer().getLevel() >= 39 :
            htmltext = "7118-03.htm"
         else:
            htmltext = "7118-01.htm"
            st.exitQuest(1)
      else:
         htmltext = "7118-02.htm"
         st.exitQuest(1)
   elif npcId == 7118 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest has already been completed.</body></html>"
   elif npcId == 7118 and int(st.get("cond"))==3 and st.getQuestItemsCount(HUGE_NAIL)>=1:
        htmltext = "7118-05.htm"
   elif npcId == 7118 and int(st.get("cond"))>=4 :
        htmltext = "7118-07.htm"
   elif npcId == 7666 and int(st.get("cond"))==4 and st.getQuestItemsCount(LETTER_OF_INTRODUCTION)>0 :
        htmltext = "7666-01.htm"
   elif npcId == 7666 and int(st.get("cond"))==5 and st.getQuestItemsCount(SLAS_LETTER)>0 :
        htmltext = "7666-05.htm"
   elif npcId == 7666 and int(st.get("cond"))==10 :
        htmltext = "7666-06.htm"
        st.set("cond","11")
        st.takeItems(OLMAHUMS_MONEY,1)
        st.giveItems(GREETINGS,3)
   elif npcId == 7666 and int(st.get("cond"))==18 and st.getQuestItemsCount(KATARIS_LETTER)>0 and st.getQuestItemsCount(KAKANS_LETTER)>0 and st.getQuestItemsCount(NYAKURIS_LETTER)>0 and st.getQuestItemsCount(RAMUSS_LETTER)>0 :
          st.giveItems(MARK_OF_REFORMER,1)
          st.addExpAndSp(164032,17500)
          htmltext = "7666-07.htm"
          st.set("cond","0")
          st.set("onlyone","1")
          st.getPcSpawn().removeAllSpawn()
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
          st.takeItems(KATARIS_LETTER,1)
          st.takeItems(KAKANS_LETTER,1)
          st.takeItems(NYAKURIS_LETTER,1)
          st.takeItems(RAMUSS_LETTER,1)
   elif npcId == 7668 and (int(st.get("cond"))==5 or int(st.get("cond"))==6) :
        htmltext = "7668-01.htm"
        st.set("cond","6")
        st.takeItems(SLAS_LETTER,1)
        st.getPcSpawn().addSpawn(7732,-4015,40141,-3664)
        st.getPcSpawn().addSpawn(5129,-4034,40201,-3665)
   elif npcId == 7668 and int(st.get("cond"))==8:
        htmltext = "7668-02.htm"
        st.getPcSpawn().addSpawn(5130,-4106,40174,-3660)
   elif npcId == 7668 and int(st.get("cond"))==9 :
        htmltext = "7668-03.htm"
        st.set("cond","10")
        st.giveItems(KATARIS_LETTER,1)
        st.takeItems(LETTER_OF_BETRAYER,1)
   elif npcId == 7732 and int(st.get("cond"))==7 :
        htmltext = "7732-01.htm"
        st.set("cond","8")
        st.giveItems(OLMAHUMS_MONEY,1)
   elif npcId == 7669 and int(st.get("cond"))==11 and st.getQuestItemsCount(GREETINGS)>0 :
        htmltext = "7669-01.htm"
   elif npcId == 7669 and int(st.get("cond"))==12 :
        htmltext = "7669-04.htm"
        st.set("cond","13")
        st.giveItems(KAKANS_LETTER,1)
        st.takeItems(GREETINGS,1)
   elif npcId == 7670 and int(st.get("cond"))==13 and st.getQuestItemsCount(GREETINGS)>0 :
        htmltext = "7670-01.htm"
   elif npcId == 7670 and int(st.get("cond"))==14 and st.getQuestItemsCount(GREETINGS)>0 :
        htmltext = "7670-04.htm"
        st.set("cond","15")
        st.giveItems(NYAKURIS_LETTER,1)
        st.takeItems(GREETINGS,1)
   elif npcId == 7667 and int(st.get("cond"))==15 and st.getQuestItemsCount(GREETINGS)>0 :
        htmltext = "7667-01.htm"
        st.set("cond","16")
        st.giveItems(UNDEAD_LIST,1)
        st.takeItems(GREETINGS,1)
   elif npcId == 7667 and st.getQuestItemsCount(BONE_FRAGMENT4)>0 and st.getQuestItemsCount(BONE_FRAGMENT5)>0 and st.getQuestItemsCount(BONE_FRAGMENT6)>0 and st.getQuestItemsCount(BONE_FRAGMENT7)>0 and st.getQuestItemsCount(BONE_FRAGMENT8)>0 :
        htmltext = "7667-03.htm"
        st.set("cond","18")
        st.takeItems(BONE_FRAGMENT4,1)
        st.takeItems(BONE_FRAGMENT5,1)
        st.takeItems(BONE_FRAGMENT6,1)
        st.takeItems(BONE_FRAGMENT7,1)
        st.takeItems(BONE_FRAGMENT8,1)
        st.giveItems(RAMUSS_LETTER,1)
        st.takeItems(UNDEAD_LIST,1)
   elif npcId == 7667 and int(st.get("cond"))==16 :
        htmltext = "7667-02.htm"
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   if npcId == 5099 :
    if int(st.get("cond")) == 1 and st.getQuestItemsCount(RIPPED_DIARY) < 7 and st.getQuestItemsCount(BOOK_OF_REFORM) >= 1 :
      if st.getQuestItemsCount(RIPPED_DIARY) == 6 :
        st.set("cond","2")
        st.getPcSpawn().addSpawn(5128,53668,143283,-3863,300000)
        return "Aruraune has spawned at X=53668 Y=143283 Z=-3863"
        st.takeItems(RIPPED_DIARY,st.getQuestItemsCount(RIPPED_DIARY))
      else:
        st.giveItems(RIPPED_DIARY,1)
      st.playSound("ItemSound.quest_itemget")
   elif npcId == 5128 :
    if int(st.get("cond")) == 2 and st.getQuestItemsCount(HUGE_NAIL) == 0 :
      st.giveItems(HUGE_NAIL,1)
      st.playSound("ItemSound.quest_middle")
      st.set("cond","3")
   elif npcId == 5129:
      st.set("cond","7")
   elif npcId == 5130 :
    if int(st.get("cond")) == 8 :
      st.set("cond","9")
      st.giveItems(LETTER_OF_BETRAYER,1)
   elif npcId == 5131 :
    if int(st.get("cond")) == 11 :
      st.set("cond","12")
   elif npcId == 5132 :
    if int(st.get("cond")) == 13 :
      st.set("cond","14")
   elif npcId == 404 :
    if int(st.get("cond")) == 16 and st.getQuestItemsCount(BONE_FRAGMENT4) == 0 :
      st.giveItems(BONE_FRAGMENT4,1)
      st.playSound("ItemSound.quest_itemget")
   elif npcId == 104 :
    if int(st.get("cond")) == 16 and st.getQuestItemsCount(BONE_FRAGMENT5) == 0 :
      st.giveItems(BONE_FRAGMENT5,1)
      st.playSound("ItemSound.quest_itemget")
   elif npcId == 102 :
    if int(st.get("cond")) == 16 and st.getQuestItemsCount(BONE_FRAGMENT6) == 0 :
      st.giveItems(BONE_FRAGMENT6,1)
      st.playSound("ItemSound.quest_itemget")
   elif npcId == 22 :
    if int(st.get("cond")) == 16 and st.getQuestItemsCount(BONE_FRAGMENT7) == 0 :
      st.giveItems(BONE_FRAGMENT7,1)
      st.playSound("ItemSound.quest_itemget")
   elif npcId == 100 :
    if int(st.get("cond")) == 16 and st.getQuestItemsCount(BONE_FRAGMENT8) == 0 :
      st.giveItems(BONE_FRAGMENT8,1)
      st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(227,"227_TestOfReformer","Test Of Reformer")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7118)

CREATED.addTalkId(7118)
STARTING.addTalkId(7118)
COMPLETED.addTalkId(7118)

for npcId in [7118,7666,7667,7669,7670,7732,7668]:
 STARTED.addTalkId(npcId)
for mobId in [100,102,104,404,22,5099,5128,5130,5129,5132,5131]:
 STARTED.addKillId(mobId)
for item in range(2822,2840)+[3037]:
 STARTED.addQuestDrop(102,item,1)

print "importing quests: 227: Test Of Reformer"
